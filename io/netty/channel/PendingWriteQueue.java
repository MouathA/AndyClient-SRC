package io.netty.channel;

import io.netty.util.internal.logging.*;
import io.netty.util.*;

public final class PendingWriteQueue
{
    private static final InternalLogger logger;
    private final ChannelHandlerContext ctx;
    private final ChannelOutboundBuffer buffer;
    private final MessageSizeEstimator.Handle estimatorHandle;
    private PendingWrite head;
    private PendingWrite tail;
    private int size;
    static final boolean $assertionsDisabled;
    
    public PendingWriteQueue(final ChannelHandlerContext ctx) {
        if (ctx == null) {
            throw new NullPointerException("ctx");
        }
        this.ctx = ctx;
        this.buffer = ctx.channel().unsafe().outboundBuffer();
        this.estimatorHandle = ctx.channel().config().getMessageSizeEstimator().newHandle();
    }
    
    public boolean isEmpty() {
        assert this.ctx.executor().inEventLoop();
        return this.head == null;
    }
    
    public int size() {
        assert this.ctx.executor().inEventLoop();
        return this.size;
    }
    
    public void add(final Object o, final ChannelPromise channelPromise) {
        assert this.ctx.executor().inEventLoop();
        if (o == null) {
            throw new NullPointerException("msg");
        }
        if (channelPromise == null) {
            throw new NullPointerException("promise");
        }
        this.estimatorHandle.size(o);
        final PendingWrite instance = PendingWrite.newInstance(o, 0, channelPromise);
        final PendingWrite tail = this.tail;
        if (tail == null) {
            final PendingWrite pendingWrite = instance;
            this.head = pendingWrite;
            this.tail = pendingWrite;
        }
        else {
            PendingWrite.access$002(tail, instance);
            this.tail = instance;
        }
        ++this.size;
        this.buffer.incrementPendingOutboundBytes(PendingWrite.access$100(instance));
    }
    
    public void removeAndFailAll(final Throwable t) {
        assert this.ctx.executor().inEventLoop();
        if (t == null) {
            throw new NullPointerException("cause");
        }
        PendingWrite access$000;
        for (PendingWrite head = this.head; head != null; head = access$000) {
            access$000 = PendingWrite.access$000(head);
            ReferenceCountUtil.safeRelease(PendingWrite.access$200(head));
            final ChannelPromise access$2 = PendingWrite.access$300(head);
            this.recycle(head);
            safeFail(access$2, t);
        }
        this.assertEmpty();
    }
    
    public void removeAndFail(final Throwable t) {
        assert this.ctx.executor().inEventLoop();
        if (t == null) {
            throw new NullPointerException("cause");
        }
        final PendingWrite head = this.head;
        if (head == null) {
            return;
        }
        ReferenceCountUtil.safeRelease(PendingWrite.access$200(head));
        safeFail(PendingWrite.access$300(head), t);
        this.recycle(head);
    }
    
    public ChannelFuture removeAndWriteAll() {
        assert this.ctx.executor().inEventLoop();
        PendingWrite head = this.head;
        if (head == null) {
            return null;
        }
        if (this.size == 1) {
            return this.removeAndWrite();
        }
        final ChannelPromise promise = this.ctx.newPromise();
        final ChannelPromiseAggregator channelPromiseAggregator = new ChannelPromiseAggregator(promise);
        while (head != null) {
            final PendingWrite access$000 = PendingWrite.access$000(head);
            final Object access$2 = PendingWrite.access$200(head);
            final ChannelPromise access$3 = PendingWrite.access$300(head);
            this.recycle(head);
            this.ctx.write(access$2, access$3);
            channelPromiseAggregator.add(access$3);
            head = access$000;
        }
        this.assertEmpty();
        return promise;
    }
    
    private void assertEmpty() {
        assert this.tail == null && this.head == null && this.size == 0;
    }
    
    public ChannelFuture removeAndWrite() {
        assert this.ctx.executor().inEventLoop();
        final PendingWrite head = this.head;
        if (head == null) {
            return null;
        }
        final Object access$200 = PendingWrite.access$200(head);
        final ChannelPromise access$201 = PendingWrite.access$300(head);
        this.recycle(head);
        return this.ctx.write(access$200, access$201);
    }
    
    public ChannelPromise remove() {
        assert this.ctx.executor().inEventLoop();
        final PendingWrite head = this.head;
        if (head == null) {
            return null;
        }
        final ChannelPromise access$300 = PendingWrite.access$300(head);
        ReferenceCountUtil.safeRelease(PendingWrite.access$200(head));
        this.recycle(head);
        return access$300;
    }
    
    public Object current() {
        assert this.ctx.executor().inEventLoop();
        final PendingWrite head = this.head;
        if (head == null) {
            return null;
        }
        return PendingWrite.access$200(head);
    }
    
    private void recycle(final PendingWrite pendingWrite) {
        final PendingWrite access$000 = PendingWrite.access$000(pendingWrite);
        this.buffer.decrementPendingOutboundBytes(PendingWrite.access$100(pendingWrite));
        PendingWrite.access$400(pendingWrite);
        --this.size;
        if (access$000 == null) {
            final PendingWrite pendingWrite2 = null;
            this.tail = pendingWrite2;
            this.head = pendingWrite2;
            assert this.size == 0;
        }
        else {
            this.head = access$000;
            assert this.size > 0;
        }
    }
    
    private static void safeFail(final ChannelPromise channelPromise, final Throwable t) {
        if (!(channelPromise instanceof VoidChannelPromise) && !channelPromise.tryFailure(t)) {
            PendingWriteQueue.logger.warn("Failed to mark a promise as failure because it's done already: {}", channelPromise, t);
        }
    }
    
    static {
        $assertionsDisabled = !PendingWriteQueue.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(PendingWriteQueue.class);
    }
    
    static final class PendingWrite
    {
        private static final Recycler RECYCLER;
        private final Recycler.Handle handle;
        private PendingWrite next;
        private long size;
        private ChannelPromise promise;
        private Object msg;
        
        private PendingWrite(final Recycler.Handle handle) {
            this.handle = handle;
        }
        
        static PendingWrite newInstance(final Object msg, final int n, final ChannelPromise promise) {
            final PendingWrite pendingWrite = (PendingWrite)PendingWrite.RECYCLER.get();
            pendingWrite.size = n;
            pendingWrite.msg = msg;
            pendingWrite.promise = promise;
            return pendingWrite;
        }
        
        private void recycle() {
            this.size = 0L;
            this.next = null;
            this.msg = null;
            this.promise = null;
            PendingWrite.RECYCLER.recycle(this, this.handle);
        }
        
        static PendingWrite access$002(final PendingWrite pendingWrite, final PendingWrite next) {
            return pendingWrite.next = next;
        }
        
        static long access$100(final PendingWrite pendingWrite) {
            return pendingWrite.size;
        }
        
        static PendingWrite access$000(final PendingWrite pendingWrite) {
            return pendingWrite.next;
        }
        
        static Object access$200(final PendingWrite pendingWrite) {
            return pendingWrite.msg;
        }
        
        static ChannelPromise access$300(final PendingWrite pendingWrite) {
            return pendingWrite.promise;
        }
        
        static void access$400(final PendingWrite pendingWrite) {
            pendingWrite.recycle();
        }
        
        PendingWrite(final Recycler.Handle handle, final PendingWriteQueue$1 object) {
            this(handle);
        }
        
        static {
            RECYCLER = new Recycler() {
                @Override
                protected PendingWrite newObject(final Handle handle) {
                    return new PendingWrite(handle, null);
                }
                
                @Override
                protected Object newObject(final Handle handle) {
                    return this.newObject(handle);
                }
            };
        }
    }
}
