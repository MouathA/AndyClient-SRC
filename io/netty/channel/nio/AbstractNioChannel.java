package io.netty.channel.nio;

import java.nio.channels.*;
import java.net.*;
import io.netty.buffer.*;
import io.netty.util.*;
import io.netty.util.internal.logging.*;
import io.netty.util.internal.*;
import java.util.concurrent.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;

public abstract class AbstractNioChannel extends AbstractChannel
{
    private static final InternalLogger logger;
    private final SelectableChannel ch;
    protected final int readInterestOp;
    SelectionKey selectionKey;
    private boolean inputShutdown;
    private boolean readPending;
    private ChannelPromise connectPromise;
    private ScheduledFuture connectTimeoutFuture;
    private SocketAddress requestedRemoteAddress;
    static final boolean $assertionsDisabled;
    
    protected AbstractNioChannel(final Channel channel, final SelectableChannel ch, final int readInterestOp) {
        super(channel);
        this.ch = ch;
        this.readInterestOp = readInterestOp;
        ch.configureBlocking(false);
    }
    
    @Override
    public boolean isOpen() {
        return this.ch.isOpen();
    }
    
    @Override
    public NioUnsafe unsafe() {
        return (NioUnsafe)super.unsafe();
    }
    
    protected SelectableChannel javaChannel() {
        return this.ch;
    }
    
    @Override
    public NioEventLoop eventLoop() {
        return (NioEventLoop)super.eventLoop();
    }
    
    protected SelectionKey selectionKey() {
        assert this.selectionKey != null;
        return this.selectionKey;
    }
    
    protected boolean isReadPending() {
        return this.readPending;
    }
    
    protected void setReadPending(final boolean readPending) {
        this.readPending = readPending;
    }
    
    protected boolean isInputShutdown() {
        return this.inputShutdown;
    }
    
    void setInputShutdown() {
        this.inputShutdown = true;
    }
    
    @Override
    protected boolean isCompatible(final EventLoop eventLoop) {
        return eventLoop instanceof NioEventLoop;
    }
    
    @Override
    protected void doRegister() throws Exception {
        this.selectionKey = this.javaChannel().register(this.eventLoop().selector, 0, this);
    }
    
    @Override
    protected void doDeregister() throws Exception {
        this.eventLoop().cancel(this.selectionKey());
    }
    
    @Override
    protected void doBeginRead() throws Exception {
        if (this.inputShutdown) {
            return;
        }
        final SelectionKey selectionKey = this.selectionKey;
        if (!selectionKey.isValid()) {
            return;
        }
        this.readPending = true;
        final int interestOps = selectionKey.interestOps();
        if ((interestOps & this.readInterestOp) == 0x0) {
            selectionKey.interestOps(interestOps | this.readInterestOp);
        }
    }
    
    protected abstract boolean doConnect(final SocketAddress p0, final SocketAddress p1) throws Exception;
    
    protected abstract void doFinishConnect() throws Exception;
    
    protected final ByteBuf newDirectBuffer(final ByteBuf byteBuf) {
        final int readableBytes = byteBuf.readableBytes();
        if (readableBytes == 0) {
            ReferenceCountUtil.safeRelease(byteBuf);
            return Unpooled.EMPTY_BUFFER;
        }
        final ByteBufAllocator alloc = this.alloc();
        if (alloc.isDirectBufferPooled()) {
            final ByteBuf directBuffer = alloc.directBuffer(readableBytes);
            directBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(byteBuf);
            return directBuffer;
        }
        final ByteBuf threadLocalDirectBuffer = ByteBufUtil.threadLocalDirectBuffer();
        if (threadLocalDirectBuffer != null) {
            threadLocalDirectBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(byteBuf);
            return threadLocalDirectBuffer;
        }
        return byteBuf;
    }
    
    protected final ByteBuf newDirectBuffer(final ReferenceCounted referenceCounted, final ByteBuf byteBuf) {
        final int readableBytes = byteBuf.readableBytes();
        if (readableBytes == 0) {
            ReferenceCountUtil.safeRelease(referenceCounted);
            return Unpooled.EMPTY_BUFFER;
        }
        final ByteBufAllocator alloc = this.alloc();
        if (alloc.isDirectBufferPooled()) {
            final ByteBuf directBuffer = alloc.directBuffer(readableBytes);
            directBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(referenceCounted);
            return directBuffer;
        }
        final ByteBuf threadLocalDirectBuffer = ByteBufUtil.threadLocalDirectBuffer();
        if (threadLocalDirectBuffer != null) {
            threadLocalDirectBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(referenceCounted);
            return threadLocalDirectBuffer;
        }
        if (referenceCounted != byteBuf) {
            byteBuf.retain();
            ReferenceCountUtil.safeRelease(referenceCounted);
        }
        return byteBuf;
    }
    
    @Override
    public Channel.Unsafe unsafe() {
        return this.unsafe();
    }
    
    @Override
    public EventLoop eventLoop() {
        return this.eventLoop();
    }
    
    static ChannelPromise access$000(final AbstractNioChannel abstractNioChannel) {
        return abstractNioChannel.connectPromise;
    }
    
    static ChannelPromise access$002(final AbstractNioChannel abstractNioChannel, final ChannelPromise connectPromise) {
        return abstractNioChannel.connectPromise = connectPromise;
    }
    
    static SocketAddress access$102(final AbstractNioChannel abstractNioChannel, final SocketAddress requestedRemoteAddress) {
        return abstractNioChannel.requestedRemoteAddress = requestedRemoteAddress;
    }
    
    static ScheduledFuture access$202(final AbstractNioChannel abstractNioChannel, final ScheduledFuture connectTimeoutFuture) {
        return abstractNioChannel.connectTimeoutFuture = connectTimeoutFuture;
    }
    
    static ScheduledFuture access$200(final AbstractNioChannel abstractNioChannel) {
        return abstractNioChannel.connectTimeoutFuture;
    }
    
    static SocketAddress access$100(final AbstractNioChannel abstractNioChannel) {
        return abstractNioChannel.requestedRemoteAddress;
    }
    
    static {
        $assertionsDisabled = !AbstractNioChannel.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(AbstractNioChannel.class);
    }
    
    protected abstract class AbstractNioUnsafe extends AbstractUnsafe implements NioUnsafe
    {
        static final boolean $assertionsDisabled;
        final AbstractNioChannel this$0;
        
        protected AbstractNioUnsafe(final AbstractNioChannel this$0) {
            this.this$0 = this$0.super();
        }
        
        protected final void removeReadOp() {
            final SelectionKey selectionKey = this.this$0.selectionKey();
            if (!selectionKey.isValid()) {
                return;
            }
            final int interestOps = selectionKey.interestOps();
            if ((interestOps & this.this$0.readInterestOp) != 0x0) {
                selectionKey.interestOps(interestOps & ~this.this$0.readInterestOp);
            }
        }
        
        @Override
        public final SelectableChannel ch() {
            return this.this$0.javaChannel();
        }
        
        @Override
        public final void connect(final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
            if (!channelPromise.setUncancellable() || !this.ensureOpen(channelPromise)) {
                return;
            }
            if (AbstractNioChannel.access$000(this.this$0) != null) {
                throw new IllegalStateException("connection attempt already made");
            }
            final boolean active = this.this$0.isActive();
            if (this.this$0.doConnect(socketAddress, socketAddress2)) {
                this.fulfillConnectPromise(channelPromise, active);
            }
            else {
                AbstractNioChannel.access$002(this.this$0, channelPromise);
                AbstractNioChannel.access$102(this.this$0, socketAddress);
                final int connectTimeoutMillis = this.this$0.config().getConnectTimeoutMillis();
                if (connectTimeoutMillis > 0) {
                    AbstractNioChannel.access$202(this.this$0, this.this$0.eventLoop().schedule((Runnable)new OneTimeTask(socketAddress) {
                        final SocketAddress val$remoteAddress;
                        final AbstractNioUnsafe this$1;
                        
                        @Override
                        public void run() {
                            final ChannelPromise access$000 = AbstractNioChannel.access$000(this.this$1.this$0);
                            final ConnectTimeoutException ex = new ConnectTimeoutException("connection timed out: " + this.val$remoteAddress);
                            if (access$000 != null && access$000.tryFailure(ex)) {
                                this.this$1.close(this.this$1.voidPromise());
                            }
                        }
                    }, (long)connectTimeoutMillis, TimeUnit.MILLISECONDS));
                }
                channelPromise.addListener((GenericFutureListener)new ChannelFutureListener() {
                    final AbstractNioUnsafe this$1;
                    
                    public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                        if (channelFuture.isCancelled()) {
                            if (AbstractNioChannel.access$200(this.this$1.this$0) != null) {
                                AbstractNioChannel.access$200(this.this$1.this$0).cancel(false);
                            }
                            AbstractNioChannel.access$002(this.this$1.this$0, null);
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
        
        @Override
        public final void finishConnect() {
            assert this.this$0.eventLoop().inEventLoop();
            final boolean active = this.this$0.isActive();
            this.this$0.doFinishConnect();
            this.fulfillConnectPromise(AbstractNioChannel.access$000(this.this$0), active);
            if (AbstractNioChannel.access$200(this.this$0) != null) {
                AbstractNioChannel.access$200(this.this$0).cancel(false);
            }
            AbstractNioChannel.access$002(this.this$0, null);
        }
        
        @Override
        protected final void flush0() {
            if (this.isFlushPending()) {
                return;
            }
            super.flush0();
        }
        
        @Override
        public final void forceFlush() {
            super.flush0();
        }
        
        private boolean isFlushPending() {
            final SelectionKey selectionKey = this.this$0.selectionKey();
            return selectionKey.isValid() && (selectionKey.interestOps() & 0x4) != 0x0;
        }
        
        static {
            $assertionsDisabled = !AbstractNioChannel.class.desiredAssertionStatus();
        }
    }
    
    public interface NioUnsafe extends Channel.Unsafe
    {
        SelectableChannel ch();
        
        void finishConnect();
        
        void read();
        
        void forceFlush();
    }
}
