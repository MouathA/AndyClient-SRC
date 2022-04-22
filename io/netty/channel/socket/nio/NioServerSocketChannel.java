package io.netty.channel.socket.nio;

import io.netty.channel.nio.*;
import java.nio.channels.spi.*;
import java.util.*;
import java.nio.channels.*;
import io.netty.channel.*;
import io.netty.util.internal.logging.*;
import io.netty.channel.socket.*;
import java.net.*;

public class NioServerSocketChannel extends AbstractNioMessageChannel implements ServerSocketChannel
{
    private static final ChannelMetadata METADATA;
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER;
    private static final InternalLogger logger;
    private final ServerSocketChannelConfig config;
    
    private static java.nio.channels.ServerSocketChannel newSocket(final SelectorProvider selectorProvider) {
        return selectorProvider.openServerSocketChannel();
    }
    
    public NioServerSocketChannel() {
        this(newSocket(NioServerSocketChannel.DEFAULT_SELECTOR_PROVIDER));
    }
    
    public NioServerSocketChannel(final SelectorProvider selectorProvider) {
        this(newSocket(selectorProvider));
    }
    
    public NioServerSocketChannel(final java.nio.channels.ServerSocketChannel serverSocketChannel) {
        super(null, serverSocketChannel, 16);
        this.config = new NioServerSocketChannelConfig(this, this.javaChannel().socket(), null);
    }
    
    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }
    
    @Override
    public ChannelMetadata metadata() {
        return NioServerSocketChannel.METADATA;
    }
    
    @Override
    public ServerSocketChannelConfig config() {
        return this.config;
    }
    
    @Override
    public boolean isActive() {
        return this.javaChannel().socket().isBound();
    }
    
    @Override
    public InetSocketAddress remoteAddress() {
        return null;
    }
    
    @Override
    protected java.nio.channels.ServerSocketChannel javaChannel() {
        return (java.nio.channels.ServerSocketChannel)super.javaChannel();
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.javaChannel().socket().getLocalSocketAddress();
    }
    
    @Override
    protected void doBind(final SocketAddress socketAddress) throws Exception {
        this.javaChannel().socket().bind(socketAddress, this.config.getBacklog());
    }
    
    @Override
    protected void doClose() throws Exception {
        this.javaChannel().close();
    }
    
    @Override
    protected int doReadMessages(final List list) throws Exception {
        final SocketChannel accept = this.javaChannel().accept();
        if (accept != null) {
            list.add(new NioSocketChannel(this, accept));
            return 1;
        }
        return 0;
    }
    
    @Override
    protected boolean doConnect(final SocketAddress socketAddress, final SocketAddress socketAddress2) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected void doFinishConnect() throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return null;
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected boolean doWriteMessage(final Object o, final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected final Object filterOutboundMessage(final Object o) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected SelectableChannel javaChannel() {
        return this.javaChannel();
    }
    
    @Override
    public SocketAddress remoteAddress() {
        return this.remoteAddress();
    }
    
    @Override
    public SocketAddress localAddress() {
        return this.localAddress();
    }
    
    @Override
    public ChannelConfig config() {
        return this.config();
    }
    
    static void access$100(final NioServerSocketChannel nioServerSocketChannel, final boolean readPending) {
        nioServerSocketChannel.setReadPending(readPending);
    }
    
    static {
        METADATA = new ChannelMetadata(false);
        DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
        logger = InternalLoggerFactory.getInstance(NioServerSocketChannel.class);
    }
    
    private final class NioServerSocketChannelConfig extends DefaultServerSocketChannelConfig
    {
        final NioServerSocketChannel this$0;
        
        private NioServerSocketChannelConfig(final NioServerSocketChannel this$0, final NioServerSocketChannel nioServerSocketChannel, final ServerSocket serverSocket) {
            this.this$0 = this$0;
            super(nioServerSocketChannel, serverSocket);
        }
        
        @Override
        protected void autoReadCleared() {
            NioServerSocketChannel.access$100(this.this$0, false);
        }
        
        NioServerSocketChannelConfig(final NioServerSocketChannel nioServerSocketChannel, final NioServerSocketChannel nioServerSocketChannel2, final ServerSocket serverSocket, final NioServerSocketChannel$1 object) {
            this(nioServerSocketChannel, nioServerSocketChannel2, serverSocket);
        }
    }
}
