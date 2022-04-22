package io.netty.channel.epoll;

import java.net.*;
import java.nio.*;
import java.io.*;
import io.netty.util.internal.*;
import io.netty.channel.socket.*;
import java.util.concurrent.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;
import io.netty.buffer.*;

public final class EpollSocketChannel extends AbstractEpollChannel implements SocketChannel
{
    private static final String EXPECTED_TYPES;
    private final EpollSocketChannelConfig config;
    private ChannelPromise connectPromise;
    private ScheduledFuture connectTimeoutFuture;
    private SocketAddress requestedRemoteAddress;
    private InetSocketAddress local;
    private InetSocketAddress remote;
    private boolean inputShutdown;
    private boolean outputShutdown;
    static final boolean $assertionsDisabled;
    
    EpollSocketChannel(final Channel channel, final int n) {
        super(channel, n, 1, true);
        this.config = new EpollSocketChannelConfig(this);
        this.remote = Native.remoteAddress(n);
        this.local = Native.localAddress(n);
    }
    
    public EpollSocketChannel() {
        super(Native.socketStreamFd(), 1);
        this.config = new EpollSocketChannelConfig(this);
    }
    
    @Override
    protected AbstractEpollUnsafe newUnsafe() {
        return new EpollSocketUnsafe();
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.local;
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return this.remote;
    }
    
    @Override
    protected void doBind(final SocketAddress socketAddress) throws Exception {
        final InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
        Native.bind(this.fd, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
        this.local = Native.localAddress(this.fd);
    }
    
    private boolean writeBytes(final ChannelOutboundBuffer channelOutboundBuffer, final ByteBuf byteBuf) throws Exception {
        final int readableBytes = byteBuf.readableBytes();
        if (readableBytes == 0) {
            channelOutboundBuffer.remove();
            return true;
        }
        long n = 0L;
        if (byteBuf.hasMemoryAddress()) {
            final long memoryAddress = byteBuf.memoryAddress();
            int readerIndex = byteBuf.readerIndex();
            final int writerIndex = byteBuf.writerIndex();
            while (true) {
                final int writeAddress = Native.writeAddress(this.fd, memoryAddress, readerIndex, writerIndex);
                if (writeAddress <= 0) {
                    this.setEpollOut();
                    break;
                }
                n += writeAddress;
                if (n == readableBytes) {
                    break;
                }
                readerIndex += writeAddress;
            }
            channelOutboundBuffer.removeBytes(n);
            return true;
        }
        if (byteBuf.nioBufferCount() == 1) {
            final ByteBuffer internalNioBuffer = byteBuf.internalNioBuffer(byteBuf.readerIndex(), byteBuf.readableBytes());
            do {
                final int position = internalNioBuffer.position();
                final int write = Native.write(this.fd, internalNioBuffer, position, internalNioBuffer.limit());
                if (write <= 0) {
                    this.setEpollOut();
                    break;
                }
                internalNioBuffer.position(position + write);
                n += write;
            } while (n != readableBytes);
            channelOutboundBuffer.removeBytes(n);
            return true;
        }
        final ByteBuffer[] nioBuffers = byteBuf.nioBuffers();
        return this.writeBytesMultiple(channelOutboundBuffer, nioBuffers, nioBuffers.length, readableBytes);
    }
    
    private boolean writeBytesMultiple(final ChannelOutboundBuffer channelOutboundBuffer, final IovArray iovArray) throws IOException {
        long size = iovArray.size();
        int count = iovArray.count();
        assert size != 0L;
        assert count != 0;
        long n = 0L;
        final int n2 = 0 + count;
        while (true) {
            long writevAddresses = Native.writevAddresses(this.fd, iovArray.memoryAddress(0), count);
            if (writevAddresses == 0L) {
                this.setEpollOut();
                break;
            }
            size -= writevAddresses;
            n += writevAddresses;
            if (size == 0L) {
                break;
            }
            do {
                final long processWritten = iovArray.processWritten(0, writevAddresses);
                if (processWritten == -1L) {
                    break;
                }
                int n3 = 0;
                ++n3;
                --count;
                writevAddresses -= processWritten;
            } while (0 < n2 && writevAddresses > 0L);
        }
        channelOutboundBuffer.removeBytes(n);
        return true;
    }
    
    private boolean writeBytesMultiple(final ChannelOutboundBuffer channelOutboundBuffer, final ByteBuffer[] array, int n, long n2) throws IOException {
        assert n2 != 0L;
        long n3 = 0L;
        final int n4 = 0 + n;
        while (true) {
            long writev = Native.writev(this.fd, array, 0, n);
            if (writev == 0L) {
                this.setEpollOut();
                break;
            }
            n2 -= writev;
            n3 += writev;
            if (n2 == 0L) {
                break;
            }
            do {
                final ByteBuffer byteBuffer = array[0];
                final int position = byteBuffer.position();
                final int n5 = byteBuffer.limit() - position;
                if (n5 > writev) {
                    byteBuffer.position(position + (int)writev);
                    break;
                }
                int n6 = 0;
                ++n6;
                --n;
                writev -= n5;
            } while (0 < n4 && writev > 0L);
        }
        channelOutboundBuffer.removeBytes(n3);
        return true;
    }
    
    private boolean writeFileRegion(final ChannelOutboundBuffer channelOutboundBuffer, final DefaultFileRegion defaultFileRegion) throws Exception {
        final long count = defaultFileRegion.count();
        if (defaultFileRegion.transfered() >= count) {
            channelOutboundBuffer.remove();
            return true;
        }
        final long position = defaultFileRegion.position();
        long n = 0L;
        for (int i = this.config().getWriteSpinCount() - 1; i >= 0; --i) {
            final long transfered = defaultFileRegion.transfered();
            final long sendfile = Native.sendfile(this.fd, defaultFileRegion, position, transfered, count - transfered);
            if (sendfile == 0L) {
                this.setEpollOut();
                break;
            }
            n += sendfile;
            if (defaultFileRegion.transfered() >= count) {
                break;
            }
        }
        if (n > 0L) {
            channelOutboundBuffer.progress(n);
        }
        if (true) {
            channelOutboundBuffer.remove();
        }
        return true;
    }
    
    @Override
    protected void doWrite(final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        while (true) {
            final int size = channelOutboundBuffer.size();
            if (size == 0) {
                this.clearEpollOut();
                break;
            }
            if (size > 1 && channelOutboundBuffer.current() instanceof ByteBuf) {
                if (!this.doWriteMultiple(channelOutboundBuffer)) {
                    break;
                }
                continue;
            }
            else {
                if (!this.doWriteSingle(channelOutboundBuffer)) {
                    break;
                }
                continue;
            }
        }
    }
    
    private boolean doWriteSingle(final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        final Object current = channelOutboundBuffer.current();
        if (current instanceof ByteBuf) {
            if (!this.writeBytes(channelOutboundBuffer, (ByteBuf)current)) {
                return false;
            }
        }
        else {
            if (!(current instanceof DefaultFileRegion)) {
                throw new Error();
            }
            if (!this.writeFileRegion(channelOutboundBuffer, (DefaultFileRegion)current)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean doWriteMultiple(final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        if (PlatformDependent.hasUnsafe()) {
            final IovArray value = IovArray.get(channelOutboundBuffer);
            if (value.count() >= 1) {
                if (!this.writeBytesMultiple(channelOutboundBuffer, value)) {
                    return false;
                }
            }
            else {
                channelOutboundBuffer.removeBytes(0L);
            }
        }
        else {
            final ByteBuffer[] nioBuffers = channelOutboundBuffer.nioBuffers();
            final int nioBufferCount = channelOutboundBuffer.nioBufferCount();
            if (nioBufferCount >= 1) {
                if (!this.writeBytesMultiple(channelOutboundBuffer, nioBuffers, nioBufferCount, channelOutboundBuffer.nioBufferSize())) {
                    return false;
                }
            }
            else {
                channelOutboundBuffer.removeBytes(0L);
            }
        }
        return true;
    }
    
    @Override
    protected Object filterOutboundMessage(final Object o) {
        if (o instanceof ByteBuf) {
            ByteBuf directBuffer = (ByteBuf)o;
            if (!directBuffer.hasMemoryAddress() && (PlatformDependent.hasUnsafe() || !directBuffer.isDirect())) {
                directBuffer = this.newDirectBuffer(directBuffer);
                assert directBuffer.hasMemoryAddress();
            }
            return directBuffer;
        }
        if (o instanceof DefaultFileRegion) {
            return o;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(o) + EpollSocketChannel.EXPECTED_TYPES);
    }
    
    @Override
    public EpollSocketChannelConfig config() {
        return this.config;
    }
    
    @Override
    public boolean isInputShutdown() {
        return this.inputShutdown;
    }
    
    @Override
    public boolean isOutputShutdown() {
        return this.outputShutdown || !this.isActive();
    }
    
    @Override
    public ChannelFuture shutdownOutput() {
        return this.shutdownOutput(this.newPromise());
    }
    
    @Override
    public ChannelFuture shutdownOutput(final ChannelPromise channelPromise) {
        final EventLoop eventLoop = this.eventLoop();
        if (eventLoop.inEventLoop()) {
            Native.shutdown(this.fd, false, true);
            this.outputShutdown = true;
            channelPromise.setSuccess();
        }
        else {
            eventLoop.execute(new Runnable(channelPromise) {
                final ChannelPromise val$promise;
                final EpollSocketChannel this$0;
                
                @Override
                public void run() {
                    this.this$0.shutdownOutput(this.val$promise);
                }
            });
        }
        return channelPromise;
    }
    
    @Override
    public ServerSocketChannel parent() {
        return (ServerSocketChannel)super.parent();
    }
    
    @Override
    public boolean isOpen() {
        return super.isOpen();
    }
    
    @Override
    public InetSocketAddress localAddress() {
        return super.localAddress();
    }
    
    @Override
    public InetSocketAddress remoteAddress() {
        return super.remoteAddress();
    }
    
    @Override
    public ChannelMetadata metadata() {
        return super.metadata();
    }
    
    @Override
    public boolean isActive() {
        return super.isActive();
    }
    
    @Override
    protected AbstractUnsafe newUnsafe() {
        return this.newUnsafe();
    }
    
    @Override
    public Channel parent() {
        return this.parent();
    }
    
    @Override
    public ChannelConfig config() {
        return this.config();
    }
    
    @Override
    public SocketChannelConfig config() {
        return this.config();
    }
    
    static boolean access$002(final EpollSocketChannel epollSocketChannel, final boolean inputShutdown) {
        return epollSocketChannel.inputShutdown = inputShutdown;
    }
    
    static ChannelPromise access$100(final EpollSocketChannel epollSocketChannel) {
        return epollSocketChannel.connectPromise;
    }
    
    static ChannelPromise access$102(final EpollSocketChannel epollSocketChannel, final ChannelPromise connectPromise) {
        return epollSocketChannel.connectPromise = connectPromise;
    }
    
    static SocketAddress access$202(final EpollSocketChannel epollSocketChannel, final SocketAddress requestedRemoteAddress) {
        return epollSocketChannel.requestedRemoteAddress = requestedRemoteAddress;
    }
    
    static ScheduledFuture access$302(final EpollSocketChannel epollSocketChannel, final ScheduledFuture connectTimeoutFuture) {
        return epollSocketChannel.connectTimeoutFuture = connectTimeoutFuture;
    }
    
    static ScheduledFuture access$300(final EpollSocketChannel epollSocketChannel) {
        return epollSocketChannel.connectTimeoutFuture;
    }
    
    static SocketAddress access$200(final EpollSocketChannel epollSocketChannel) {
        return epollSocketChannel.requestedRemoteAddress;
    }
    
    static InetSocketAddress access$402(final EpollSocketChannel epollSocketChannel, final InetSocketAddress remote) {
        return epollSocketChannel.remote = remote;
    }
    
    static InetSocketAddress access$502(final EpollSocketChannel epollSocketChannel, final InetSocketAddress local) {
        return epollSocketChannel.local = local;
    }
    
    static {
        $assertionsDisabled = !EpollSocketChannel.class.desiredAssertionStatus();
        EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(DefaultFileRegion.class) + ')';
    }
    
    final class EpollSocketUnsafe extends AbstractEpollUnsafe
    {
        private RecvByteBufAllocator.Handle allocHandle;
        static final boolean $assertionsDisabled;
        final EpollSocketChannel this$0;
        
        EpollSocketUnsafe(final EpollSocketChannel this$0) {
            this.this$0 = this$0.super();
        }
        
        private void closeOnRead(final ChannelPipeline channelPipeline) {
            EpollSocketChannel.access$002(this.this$0, true);
            if (this.this$0.isOpen()) {
                if (Boolean.TRUE.equals(this.this$0.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
                    this.clearEpollIn0();
                    channelPipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
                }
                else {
                    this.close(this.voidPromise());
                }
            }
        }
        
        private boolean handleReadException(final ChannelPipeline channelPipeline, final ByteBuf byteBuf, final Throwable t, final boolean b) {
            if (byteBuf != null) {
                if (byteBuf.isReadable()) {
                    this.readPending = false;
                    channelPipeline.fireChannelRead(byteBuf);
                }
                else {
                    byteBuf.release();
                }
            }
            channelPipeline.fireChannelReadComplete();
            channelPipeline.fireExceptionCaught(t);
            if (b || t instanceof IOException) {
                this.closeOnRead(channelPipeline);
                return true;
            }
            return false;
        }
        
        @Override
        public void connect(final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
            if (!channelPromise.setUncancellable() || !this.ensureOpen(channelPromise)) {
                return;
            }
            if (EpollSocketChannel.access$100(this.this$0) != null) {
                throw new IllegalStateException("connection attempt already made");
            }
            final boolean active = this.this$0.isActive();
            (InetSocketAddress)socketAddress;
            if (socketAddress2 != null) {
                this.fulfillConnectPromise(channelPromise, active);
            }
            else {
                EpollSocketChannel.access$102(this.this$0, channelPromise);
                EpollSocketChannel.access$202(this.this$0, socketAddress);
                final int connectTimeoutMillis = this.this$0.config().getConnectTimeoutMillis();
                if (connectTimeoutMillis > 0) {
                    EpollSocketChannel.access$302(this.this$0, this.this$0.eventLoop().schedule((Runnable)new Runnable(socketAddress) {
                        final SocketAddress val$remoteAddress;
                        final EpollSocketUnsafe this$1;
                        
                        @Override
                        public void run() {
                            final ChannelPromise access$100 = EpollSocketChannel.access$100(this.this$1.this$0);
                            final ConnectTimeoutException ex = new ConnectTimeoutException("connection timed out: " + this.val$remoteAddress);
                            if (access$100 != null && access$100.tryFailure(ex)) {
                                this.this$1.close(this.this$1.voidPromise());
                            }
                        }
                    }, (long)connectTimeoutMillis, TimeUnit.MILLISECONDS));
                }
                channelPromise.addListener((GenericFutureListener)new ChannelFutureListener() {
                    final EpollSocketUnsafe this$1;
                    
                    public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                        if (channelFuture.isCancelled()) {
                            if (EpollSocketChannel.access$300(this.this$1.this$0) != null) {
                                EpollSocketChannel.access$300(this.this$1.this$0).cancel(false);
                            }
                            EpollSocketChannel.access$102(this.this$1.this$0, null);
                            this.this$1.close(this.this$1.voidPromise());
                        }
                    }
                    
                    @Override
                    public void operationComplete(final Future future) throws Exception {
                        this.operationComplete((ChannelFuture)future);
                    }
                });
            }
        }
        
        private void fulfillConnectPromise(final ChannelPromise channelPromise, final boolean b) {
            if (channelPromise == null) {
                return;
            }
            this.this$0.active = true;
            final boolean trySuccess = channelPromise.trySuccess();
            if (!b && this.this$0.isActive()) {
                this.this$0.pipeline().fireChannelActive();
            }
            if (!trySuccess) {
                this.close(this.voidPromise());
            }
        }
        
        private void fulfillConnectPromise(final ChannelPromise channelPromise, final Throwable t) {
            if (channelPromise == null) {
                return;
            }
            channelPromise.tryFailure(t);
            this.closeIfClosed();
        }
        
        private void finishConnect() {
            assert this.this$0.eventLoop().inEventLoop();
            final boolean active = this.this$0.isActive();
            if (this != 0) {
                return;
            }
            this.fulfillConnectPromise(EpollSocketChannel.access$100(this.this$0), active);
        }
        
        @Override
        void epollOutReady() {
            if (EpollSocketChannel.access$100(this.this$0) != null) {
                this.finishConnect();
            }
            else {
                super.epollOutReady();
            }
        }
        
        private int doReadBytes(final ByteBuf byteBuf) throws Exception {
            final int writerIndex = byteBuf.writerIndex();
            int n;
            if (byteBuf.hasMemoryAddress()) {
                n = Native.readAddress(this.this$0.fd, byteBuf.memoryAddress(), writerIndex, byteBuf.capacity());
            }
            else {
                final ByteBuffer internalNioBuffer = byteBuf.internalNioBuffer(writerIndex, byteBuf.writableBytes());
                n = Native.read(this.this$0.fd, internalNioBuffer, internalNioBuffer.position(), internalNioBuffer.limit());
            }
            if (n > 0) {
                byteBuf.writerIndex(writerIndex + n);
            }
            return n;
        }
        
        @Override
        void epollRdHupReady() {
            if (this.this$0.isActive()) {
                this.epollInReady();
            }
            else {
                this.closeOnRead(this.this$0.pipeline());
            }
        }
        
        @Override
        void epollInReady() {
            final EpollSocketChannelConfig config = this.this$0.config();
            final ChannelPipeline pipeline = this.this$0.pipeline();
            final ByteBufAllocator allocator = config.getAllocator();
            RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
            if (allocHandle == null) {
                allocHandle = (this.allocHandle = config.getRecvByteBufAllocator().newHandle());
            }
            int writableBytes;
            int doReadBytes;
            do {
                final ByteBuf allocate = allocHandle.allocate(allocator);
                writableBytes = allocate.writableBytes();
                doReadBytes = this.doReadBytes(allocate);
                if (doReadBytes <= 0) {
                    allocate.release();
                    final boolean b = doReadBytes < 0;
                    break;
                }
                this.readPending = false;
                pipeline.fireChannelRead(allocate);
                if (0 >= Integer.MAX_VALUE - doReadBytes) {
                    allocHandle.record(0);
                }
            } while (doReadBytes >= writableBytes);
            pipeline.fireChannelReadComplete();
            allocHandle.record(0);
            if (!config.isAutoRead() && !this.readPending) {
                this.clearEpollIn0();
            }
        }
        
        static {
            $assertionsDisabled = !EpollSocketChannel.class.desiredAssertionStatus();
        }
    }
}
