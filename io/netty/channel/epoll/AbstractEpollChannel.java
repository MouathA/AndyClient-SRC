package io.netty.channel.epoll;

import io.netty.channel.*;
import io.netty.util.internal.*;
import io.netty.util.*;
import io.netty.buffer.*;
import java.nio.channels.*;
import java.net.*;

abstract class AbstractEpollChannel extends AbstractChannel
{
    private static final ChannelMetadata DATA;
    private final int readFlag;
    protected int flags;
    protected boolean active;
    int fd;
    int id;
    
    AbstractEpollChannel(final int n, final int n2) {
        this(null, n, n2, false);
    }
    
    AbstractEpollChannel(final Channel channel, final int fd, final int readFlag, final boolean active) {
        super(channel);
        this.fd = fd;
        this.readFlag = readFlag;
        this.flags |= readFlag;
        this.active = active;
    }
    
    @Override
    public boolean isActive() {
        return this.active;
    }
    
    @Override
    public ChannelMetadata metadata() {
        return AbstractEpollChannel.DATA;
    }
    
    @Override
    protected void doClose() throws Exception {
        this.active = false;
        this.doDeregister();
        final int fd = this.fd;
        this.fd = -1;
        Native.close(fd);
    }
    
    @Override
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress)super.remoteAddress();
    }
    
    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }
    
    @Override
    protected boolean isCompatible(final EventLoop eventLoop) {
        return eventLoop instanceof EpollEventLoop;
    }
    
    @Override
    public boolean isOpen() {
        return this.fd != -1;
    }
    
    @Override
    protected void doDeregister() throws Exception {
        ((EpollEventLoop)this.eventLoop()).remove(this);
    }
    
    @Override
    protected void doBeginRead() throws Exception {
        ((AbstractEpollUnsafe)this.unsafe()).readPending = true;
        if ((this.flags & this.readFlag) == 0x0) {
            this.flags |= this.readFlag;
            this.modifyEvents();
        }
    }
    
    final void clearEpollIn() {
        if (this.isRegistered()) {
            final EventLoop eventLoop = this.eventLoop();
            final AbstractEpollUnsafe abstractEpollUnsafe = (AbstractEpollUnsafe)this.unsafe();
            if (eventLoop.inEventLoop()) {
                abstractEpollUnsafe.clearEpollIn0();
            }
            else {
                eventLoop.execute(new OneTimeTask(abstractEpollUnsafe) {
                    final AbstractEpollUnsafe val$unsafe;
                    final AbstractEpollChannel this$0;
                    
                    @Override
                    public void run() {
                        if (!this.this$0.config().isAutoRead() && !this.val$unsafe.readPending) {
                            this.val$unsafe.clearEpollIn0();
                        }
                    }
                });
            }
        }
        else {
            this.flags &= ~this.readFlag;
        }
    }
    
    protected final void setEpollOut() {
        if ((this.flags & 0x2) == 0x0) {
            this.flags |= 0x2;
            this.modifyEvents();
        }
    }
    
    protected final void clearEpollOut() {
        if ((this.flags & 0x2) != 0x0) {
            this.flags &= 0xFFFFFFFD;
            this.modifyEvents();
        }
    }
    
    private void modifyEvents() {
        if (this.isOpen()) {
            ((EpollEventLoop)this.eventLoop()).modify(this);
        }
    }
    
    @Override
    protected void doRegister() throws Exception {
        ((EpollEventLoop)this.eventLoop()).add(this);
    }
    
    @Override
    protected abstract AbstractEpollUnsafe newUnsafe();
    
    protected final ByteBuf newDirectBuffer(final ByteBuf byteBuf) {
        return this.newDirectBuffer(byteBuf, byteBuf);
    }
    
    protected final ByteBuf newDirectBuffer(final Object o, final ByteBuf byteBuf) {
        final int readableBytes = byteBuf.readableBytes();
        if (readableBytes == 0) {
            ReferenceCountUtil.safeRelease(o);
            return Unpooled.EMPTY_BUFFER;
        }
        final ByteBufAllocator alloc = this.alloc();
        if (alloc.isDirectBufferPooled()) {
            return newDirectBuffer0(o, byteBuf, alloc, readableBytes);
        }
        final ByteBuf threadLocalDirectBuffer = ByteBufUtil.threadLocalDirectBuffer();
        if (threadLocalDirectBuffer == null) {
            return newDirectBuffer0(o, byteBuf, alloc, readableBytes);
        }
        threadLocalDirectBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
        ReferenceCountUtil.safeRelease(o);
        return threadLocalDirectBuffer;
    }
    
    private static ByteBuf newDirectBuffer0(final Object o, final ByteBuf byteBuf, final ByteBufAllocator byteBufAllocator, final int n) {
        final ByteBuf directBuffer = byteBufAllocator.directBuffer(n);
        directBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), n);
        ReferenceCountUtil.safeRelease(o);
        return directBuffer;
    }
    
    protected static void checkResolvable(final InetSocketAddress inetSocketAddress) {
        if (inetSocketAddress.isUnresolved()) {
            throw new UnresolvedAddressException();
        }
    }
    
    @Override
    protected AbstractUnsafe newUnsafe() {
        return this.newUnsafe();
    }
    
    @Override
    public SocketAddress remoteAddress() {
        return this.remoteAddress();
    }
    
    @Override
    public SocketAddress localAddress() {
        return this.localAddress();
    }
    
    static int access$000(final AbstractEpollChannel abstractEpollChannel) {
        return abstractEpollChannel.readFlag;
    }
    
    static void access$100(final AbstractEpollChannel abstractEpollChannel) {
        abstractEpollChannel.modifyEvents();
    }
    
    static {
        DATA = new ChannelMetadata(false);
    }
    
    protected abstract class AbstractEpollUnsafe extends AbstractUnsafe
    {
        protected boolean readPending;
        final AbstractEpollChannel this$0;
        
        protected AbstractEpollUnsafe(final AbstractEpollChannel this$0) {
            this.this$0 = this$0.super();
        }
        
        abstract void epollInReady();
        
        void epollRdHupReady() {
        }
        
        @Override
        protected void flush0() {
            if (this.isFlushPending()) {
                return;
            }
            super.flush0();
        }
        
        void epollOutReady() {
            super.flush0();
        }
        
        private boolean isFlushPending() {
            return (this.this$0.flags & 0x2) != 0x0;
        }
        
        protected final void clearEpollIn0() {
            if ((this.this$0.flags & AbstractEpollChannel.access$000(this.this$0)) != 0x0) {
                final AbstractEpollChannel this$0 = this.this$0;
                this$0.flags &= ~AbstractEpollChannel.access$000(this.this$0);
                AbstractEpollChannel.access$100(this.this$0);
            }
        }
    }
}
