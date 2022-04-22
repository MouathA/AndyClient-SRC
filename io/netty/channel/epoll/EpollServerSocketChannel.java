package io.netty.channel.epoll;

import java.net.*;
import io.netty.channel.socket.*;
import io.netty.channel.*;

public final class EpollServerSocketChannel extends AbstractEpollChannel implements ServerSocketChannel
{
    private final EpollServerSocketChannelConfig config;
    private InetSocketAddress local;
    
    public EpollServerSocketChannel() {
        super(Native.socketStreamFd(), 4);
        this.config = new EpollServerSocketChannelConfig(this);
    }
    
    @Override
    protected boolean isCompatible(final EventLoop eventLoop) {
        return eventLoop instanceof EpollEventLoop;
    }
    
    @Override
    protected void doBind(final SocketAddress socketAddress) throws Exception {
        final InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
        AbstractEpollChannel.checkResolvable(inetSocketAddress);
        Native.bind(this.fd, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
        this.local = Native.localAddress(this.fd);
        Native.listen(this.fd, this.config.getBacklog());
        this.active = true;
    }
    
    @Override
    public EpollServerSocketChannelConfig config() {
        return this.config;
    }
    
    @Override
    protected InetSocketAddress localAddress0() {
        return this.local;
    }
    
    @Override
    protected InetSocketAddress remoteAddress0() {
        return null;
    }
    
    @Override
    protected AbstractEpollUnsafe newUnsafe() {
        return new EpollServerSocketUnsafe();
    }
    
    @Override
    protected void doWrite(final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected Object filterOutboundMessage(final Object o) throws Exception {
        throw new UnsupportedOperationException();
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
    protected SocketAddress remoteAddress0() {
        return this.remoteAddress0();
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.localAddress0();
    }
    
    @Override
    protected AbstractUnsafe newUnsafe() {
        return this.newUnsafe();
    }
    
    @Override
    public ChannelConfig config() {
        return this.config();
    }
    
    @Override
    public ServerSocketChannelConfig config() {
        return this.config();
    }
    
    static EpollServerSocketChannelConfig access$000(final EpollServerSocketChannel epollServerSocketChannel) {
        return epollServerSocketChannel.config;
    }
    
    final class EpollServerSocketUnsafe extends AbstractEpollUnsafe
    {
        static final boolean $assertionsDisabled;
        final EpollServerSocketChannel this$0;
        
        EpollServerSocketUnsafe(final EpollServerSocketChannel this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        public void connect(final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
            channelPromise.setFailure((Throwable)new UnsupportedOperationException());
        }
        
        @Override
        void epollInReady() {
            assert this.this$0.eventLoop().inEventLoop();
            final ChannelPipeline pipeline = this.this$0.pipeline();
            final Throwable t = null;
            while (true) {
                final int accept = Native.accept(this.this$0.fd);
                if (accept == -1) {
                    break;
                }
                this.readPending = false;
                pipeline.fireChannelRead(new EpollSocketChannel(this.this$0, accept));
            }
            pipeline.fireChannelReadComplete();
            if (t != null) {
                pipeline.fireExceptionCaught(t);
            }
            if (!EpollServerSocketChannel.access$000(this.this$0).isAutoRead() && !this.readPending) {
                this.clearEpollIn0();
            }
        }
        
        static {
            $assertionsDisabled = !EpollServerSocketChannel.class.desiredAssertionStatus();
        }
    }
}
