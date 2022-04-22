package io.netty.channel.socket.oio;

import io.netty.channel.oio.*;
import java.util.concurrent.locks.*;
import java.net.*;
import java.util.*;
import io.netty.channel.*;
import io.netty.channel.socket.*;
import io.netty.util.internal.logging.*;

public class OioServerSocketChannel extends AbstractOioMessageChannel implements ServerSocketChannel
{
    private static final InternalLogger logger;
    private static final ChannelMetadata METADATA;
    final ServerSocket socket;
    final Lock shutdownLock;
    private final OioServerSocketChannelConfig config;
    
    private static ServerSocket newServerSocket() {
        return new ServerSocket();
    }
    
    public OioServerSocketChannel() {
        this(newServerSocket());
    }
    
    public OioServerSocketChannel(final ServerSocket socket) {
        super(null);
        this.shutdownLock = new ReentrantLock();
        if (socket == null) {
            throw new NullPointerException("socket");
        }
        socket.setSoTimeout(1000);
        if (!true) {
            socket.close();
        }
        this.socket = socket;
        this.config = new DefaultOioServerSocketChannelConfig(this, socket);
    }
    
    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }
    
    @Override
    public ChannelMetadata metadata() {
        return OioServerSocketChannel.METADATA;
    }
    
    @Override
    public OioServerSocketChannelConfig config() {
        return this.config;
    }
    
    @Override
    public InetSocketAddress remoteAddress() {
        return null;
    }
    
    @Override
    public boolean isOpen() {
        return !this.socket.isClosed();
    }
    
    @Override
    public boolean isActive() {
        return this.isOpen() && this.socket.isBound();
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.socket.getLocalSocketAddress();
    }
    
    @Override
    protected void doBind(final SocketAddress socketAddress) throws Exception {
        this.socket.bind(socketAddress, this.config.getBacklog());
    }
    
    @Override
    protected void doClose() throws Exception {
        this.socket.close();
    }
    
    @Override
    protected int doReadMessages(final List list) throws Exception {
        if (this.socket.isClosed()) {
            return -1;
        }
        list.add(new OioSocketChannel(this, this.socket.accept()));
        return 1;
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
    protected void doConnect(final SocketAddress socketAddress, final SocketAddress socketAddress2) throws Exception {
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
    protected void setReadPending(final boolean readPending) {
        super.setReadPending(readPending);
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
    
    @Override
    public ServerSocketChannelConfig config() {
        return this.config();
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(OioServerSocketChannel.class);
        METADATA = new ChannelMetadata(false);
    }
}
