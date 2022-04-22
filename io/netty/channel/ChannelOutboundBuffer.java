package io.netty.channel;

import io.netty.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.nio.*;
import java.nio.channels.*;
import io.netty.util.internal.logging.*;
import io.netty.util.internal.*;
import io.netty.util.*;
import io.netty.buffer.*;

public final class ChannelOutboundBuffer
{
    private static final InternalLogger logger;
    private static final FastThreadLocal NIO_BUFFERS;
    private final Channel channel;
    private Entry flushedEntry;
    private Entry unflushedEntry;
    private Entry tailEntry;
    private int flushed;
    private int nioBufferCount;
    private long nioBufferSize;
    private boolean inFail;
    private static final AtomicLongFieldUpdater TOTAL_PENDING_SIZE_UPDATER;
    private long totalPendingSize;
    private static final AtomicIntegerFieldUpdater WRITABLE_UPDATER;
    private int writable;
    static final boolean $assertionsDisabled;
    
    ChannelOutboundBuffer(final AbstractChannel channel) {
        this.writable = 1;
        this.channel = channel;
    }
    
    public void addMessage(final Object o, final int n, final ChannelPromise channelPromise) {
        final Entry instance = Entry.newInstance(o, n, total(o), channelPromise);
        if (this.tailEntry == null) {
            this.flushedEntry = null;
            this.tailEntry = instance;
        }
        else {
            this.tailEntry.next = instance;
            this.tailEntry = instance;
        }
        if (this.unflushedEntry == null) {
            this.unflushedEntry = instance;
        }
        this.incrementPendingOutboundBytes(n);
    }
    
    public void addFlush() {
        Entry flushedEntry = this.unflushedEntry;
        if (flushedEntry != null) {
            if (this.flushedEntry == null) {
                this.flushedEntry = flushedEntry;
            }
            do {
                ++this.flushed;
                if (!flushedEntry.promise.setUncancellable()) {
                    this.decrementPendingOutboundBytes(flushedEntry.cancel());
                }
                flushedEntry = flushedEntry.next;
            } while (flushedEntry != null);
            this.unflushedEntry = null;
        }
    }
    
    void incrementPendingOutboundBytes(final long n) {
        if (n == 0L) {
            return;
        }
        if (ChannelOutboundBuffer.TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, n) > this.channel.config().getWriteBufferHighWaterMark() && ChannelOutboundBuffer.WRITABLE_UPDATER.compareAndSet(this, 1, 0)) {
            this.channel.pipeline().fireChannelWritabilityChanged();
        }
    }
    
    void decrementPendingOutboundBytes(final long n) {
        if (n == 0L) {
            return;
        }
        final long addAndGet = ChannelOutboundBuffer.TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, -n);
        if ((addAndGet == 0L || addAndGet < this.channel.config().getWriteBufferLowWaterMark()) && ChannelOutboundBuffer.WRITABLE_UPDATER.compareAndSet(this, 0, 1)) {
            this.channel.pipeline().fireChannelWritabilityChanged();
        }
    }
    
    private static long total(final Object o) {
        if (o instanceof ByteBuf) {
            return ((ByteBuf)o).readableBytes();
        }
        if (o instanceof FileRegion) {
            return ((FileRegion)o).count();
        }
        if (o instanceof ByteBufHolder) {
            return ((ByteBufHolder)o).content().readableBytes();
        }
        return -1L;
    }
    
    public Object current() {
        final Entry flushedEntry = this.flushedEntry;
        if (flushedEntry == null) {
            return null;
        }
        return flushedEntry.msg;
    }
    
    public void progress(final long n) {
        final Entry flushedEntry = this.flushedEntry;
        assert flushedEntry != null;
        final ChannelPromise promise = flushedEntry.promise;
        if (promise instanceof ChannelProgressivePromise) {
            final long progress = flushedEntry.progress + n;
            flushedEntry.progress = progress;
            ((ChannelProgressivePromise)promise).tryProgress(progress, flushedEntry.total);
        }
    }
    
    public boolean remove() {
        final Entry flushedEntry = this.flushedEntry;
        if (flushedEntry == null) {
            return false;
        }
        final Object msg = flushedEntry.msg;
        final ChannelPromise promise = flushedEntry.promise;
        final int pendingSize = flushedEntry.pendingSize;
        this.removeEntry(flushedEntry);
        if (!flushedEntry.cancelled) {
            ReferenceCountUtil.safeRelease(msg);
            safeSuccess(promise);
            this.decrementPendingOutboundBytes(pendingSize);
        }
        flushedEntry.recycle();
        return true;
    }
    
    public boolean remove(final Throwable t) {
        final Entry flushedEntry = this.flushedEntry;
        if (flushedEntry == null) {
            return false;
        }
        final Object msg = flushedEntry.msg;
        final ChannelPromise promise = flushedEntry.promise;
        final int pendingSize = flushedEntry.pendingSize;
        this.removeEntry(flushedEntry);
        if (!flushedEntry.cancelled) {
            ReferenceCountUtil.safeRelease(msg);
            safeFail(promise, t);
            this.decrementPendingOutboundBytes(pendingSize);
        }
        flushedEntry.recycle();
        return true;
    }
    
    private void removeEntry(final Entry entry) {
        final int flushed = this.flushed - 1;
        this.flushed = flushed;
        if (flushed == 0) {
            this.flushedEntry = null;
            if (entry == this.tailEntry) {
                this.tailEntry = null;
                this.unflushedEntry = null;
            }
        }
        else {
            this.flushedEntry = entry.next;
        }
    }
    
    public void removeBytes(long n) {
        while (true) {
            final Object current = this.current();
            if (!(current instanceof ByteBuf)) {
                assert n == 0L;
                break;
            }
            else {
                final ByteBuf byteBuf = (ByteBuf)current;
                final int readerIndex = byteBuf.readerIndex();
                final int n2 = byteBuf.writerIndex() - readerIndex;
                if (n2 <= n) {
                    if (n != 0L) {
                        this.progress(n2);
                        n -= n2;
                    }
                    this.remove();
                }
                else {
                    if (n != 0L) {
                        byteBuf.readerIndex(readerIndex + (int)n);
                        this.progress(n);
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public ByteBuffer[] nioBuffers() {
        long nioBufferSize = 0L;
        final InternalThreadLocalMap value = InternalThreadLocalMap.get();
        ByteBuffer[] expandNioBufferArray = (ByteBuffer[])ChannelOutboundBuffer.NIO_BUFFERS.get(value);
        for (Entry entry = this.flushedEntry; this.isFlushedEntry(entry) && entry.msg instanceof ByteBuf; entry = entry.next) {
            if (!entry.cancelled) {
                final ByteBuf byteBuf = (ByteBuf)entry.msg;
                final int readerIndex = byteBuf.readerIndex();
                final int n = byteBuf.writerIndex() - readerIndex;
                if (n > 0) {
                    nioBufferSize += n;
                    int count = entry.count;
                    if (count == -1) {
                        count = (entry.count = byteBuf.nioBufferCount());
                    }
                    final int n2 = 0 + count;
                    if (n2 > expandNioBufferArray.length) {
                        expandNioBufferArray = expandNioBufferArray(expandNioBufferArray, n2, 0);
                        ChannelOutboundBuffer.NIO_BUFFERS.set(value, expandNioBufferArray);
                    }
                    if (count == 1) {
                        ByteBuffer buf = entry.buf;
                        if (buf == null) {
                            buf = (entry.buf = byteBuf.internalNioBuffer(readerIndex, n));
                        }
                        final ByteBuffer[] array = expandNioBufferArray;
                        final int n3 = 0;
                        int fillBufferArray = 0;
                        ++fillBufferArray;
                        array[n3] = buf;
                    }
                    else {
                        ByteBuffer[] bufs = entry.bufs;
                        if (bufs == null) {
                            bufs = (entry.bufs = byteBuf.nioBuffers());
                        }
                        final int fillBufferArray = fillBufferArray(bufs, expandNioBufferArray, 0);
                    }
                }
            }
        }
        this.nioBufferCount = 0;
        this.nioBufferSize = nioBufferSize;
        return expandNioBufferArray;
    }
    
    private static int fillBufferArray(final ByteBuffer[] array, final ByteBuffer[] array2, int n) {
        while (0 < array.length) {
            final ByteBuffer byteBuffer = array[0];
            if (byteBuffer == null) {
                break;
            }
            array2[n++] = byteBuffer;
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    private static ByteBuffer[] expandNioBufferArray(final ByteBuffer[] array, final int i, final int n) {
        int length = array.length;
        do {
            length <<= 1;
            if (length < 0) {
                throw new IllegalStateException();
            }
        } while (i > length);
        final ByteBuffer[] array2 = new ByteBuffer[length];
        System.arraycopy(array, 0, array2, 0, n);
        return array2;
    }
    
    public int nioBufferCount() {
        return this.nioBufferCount;
    }
    
    public long nioBufferSize() {
        return this.nioBufferSize;
    }
    
    boolean isWritable() {
        return this.writable != 0;
    }
    
    public int size() {
        return this.flushed;
    }
    
    public boolean isEmpty() {
        return this.flushed == 0;
    }
    
    void failFlushed(final Throwable t) {
        if (this.inFail) {
            return;
        }
        this.inFail = true;
        while (this.remove(t)) {}
        this.inFail = false;
    }
    
    void close(final ClosedChannelException ex) {
        if (this.inFail) {
            this.channel.eventLoop().execute(new Runnable(ex) {
                final ClosedChannelException val$cause;
                final ChannelOutboundBuffer this$0;
                
                @Override
                public void run() {
                    this.this$0.close(this.val$cause);
                }
            });
            return;
        }
        this.inFail = true;
        if (this.channel.isOpen()) {
            throw new IllegalStateException("close() must be invoked after the channel is closed.");
        }
        if (!this.isEmpty()) {
            throw new IllegalStateException("close() must be invoked after all flushed writes are handled.");
        }
        for (Entry entry = this.unflushedEntry; entry != null; entry = entry.recycleAndGetNext()) {
            ChannelOutboundBuffer.TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, -entry.pendingSize);
            if (!entry.cancelled) {
                ReferenceCountUtil.safeRelease(entry.msg);
                safeFail(entry.promise, ex);
            }
        }
        this.inFail = false;
    }
    
    private static void safeSuccess(final ChannelPromise channelPromise) {
        if (!(channelPromise instanceof VoidChannelPromise) && !channelPromise.trySuccess()) {
            ChannelOutboundBuffer.logger.warn("Failed to mark a promise as success because it is done already: {}", channelPromise);
        }
    }
    
    private static void safeFail(final ChannelPromise channelPromise, final Throwable t) {
        if (!(channelPromise instanceof VoidChannelPromise) && !channelPromise.tryFailure(t)) {
            ChannelOutboundBuffer.logger.warn("Failed to mark a promise as failure because it's done already: {}", channelPromise, t);
        }
    }
    
    @Deprecated
    public void recycle() {
    }
    
    public long totalPendingWriteBytes() {
        return this.totalPendingSize;
    }
    
    public void forEachFlushedMessage(final MessageProcessor messageProcessor) throws Exception {
        if (messageProcessor == null) {
            throw new NullPointerException("processor");
        }
        Entry entry = this.flushedEntry;
        if (entry == null) {
            return;
        }
        while (entry.cancelled || messageProcessor.processMessage(entry.msg)) {
            entry = entry.next;
            if (!this.isFlushedEntry(entry)) {
                return;
            }
        }
    }
    
    private boolean isFlushedEntry(final Entry entry) {
        return entry != null && entry != this.unflushedEntry;
    }
    
    static {
        $assertionsDisabled = !ChannelOutboundBuffer.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(ChannelOutboundBuffer.class);
        NIO_BUFFERS = new FastThreadLocal() {
            @Override
            protected ByteBuffer[] initialValue() throws Exception {
                return new ByteBuffer[1024];
            }
            
            @Override
            protected Object initialValue() throws Exception {
                return this.initialValue();
            }
        };
        AtomicIntegerFieldUpdater<ChannelOutboundBuffer> writable_UPDATER = (AtomicIntegerFieldUpdater<ChannelOutboundBuffer>)PlatformDependent.newAtomicIntegerFieldUpdater(ChannelOutboundBuffer.class, "writable");
        if (writable_UPDATER == null) {
            writable_UPDATER = AtomicIntegerFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "writable");
        }
        WRITABLE_UPDATER = writable_UPDATER;
        AtomicLongFieldUpdater<ChannelOutboundBuffer> total_PENDING_SIZE_UPDATER = (AtomicLongFieldUpdater<ChannelOutboundBuffer>)PlatformDependent.newAtomicLongFieldUpdater(ChannelOutboundBuffer.class, "totalPendingSize");
        if (total_PENDING_SIZE_UPDATER == null) {
            total_PENDING_SIZE_UPDATER = AtomicLongFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "totalPendingSize");
        }
        TOTAL_PENDING_SIZE_UPDATER = total_PENDING_SIZE_UPDATER;
    }
    
    static final class Entry
    {
        private static final Recycler RECYCLER;
        private final Recycler.Handle handle;
        Entry next;
        Object msg;
        ByteBuffer[] bufs;
        ByteBuffer buf;
        ChannelPromise promise;
        long progress;
        long total;
        int pendingSize;
        int count;
        boolean cancelled;
        
        private Entry(final Recycler.Handle handle) {
            this.count = -1;
            this.handle = handle;
        }
        
        static Entry newInstance(final Object msg, final int pendingSize, final long total, final ChannelPromise promise) {
            final Entry entry = (Entry)Entry.RECYCLER.get();
            entry.msg = msg;
            entry.pendingSize = pendingSize;
            entry.total = total;
            entry.promise = promise;
            return entry;
        }
        
        int cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                final int pendingSize = this.pendingSize;
                ReferenceCountUtil.safeRelease(this.msg);
                this.msg = Unpooled.EMPTY_BUFFER;
                this.pendingSize = 0;
                this.total = 0L;
                this.progress = 0L;
                this.bufs = null;
                this.buf = null;
                return pendingSize;
            }
            return 0;
        }
        
        void recycle() {
            this.next = null;
            this.bufs = null;
            this.buf = null;
            this.msg = null;
            this.promise = null;
            this.progress = 0L;
            this.total = 0L;
            this.pendingSize = 0;
            this.count = -1;
            this.cancelled = false;
            Entry.RECYCLER.recycle(this, this.handle);
        }
        
        Entry recycleAndGetNext() {
            final Entry next = this.next;
            this.recycle();
            return next;
        }
        
        Entry(final Recycler.Handle handle, final ChannelOutboundBuffer$1 fastThreadLocal) {
            this(handle);
        }
        
        static {
            RECYCLER = new Recycler() {
                @Override
                protected Entry newObject(final Handle handle) {
                    return new Entry(handle, null);
                }
                
                @Override
                protected Object newObject(final Handle handle) {
                    return this.newObject(handle);
                }
            };
        }
    }
    
    public interface MessageProcessor
    {
        boolean processMessage(final Object p0) throws Exception;
    }
}
