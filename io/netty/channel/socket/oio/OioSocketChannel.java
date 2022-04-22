package io.netty.channel.socket.oio;

import io.netty.channel.oio.*;
import io.netty.buffer.*;
import java.net.*;
import io.netty.channel.*;
import io.netty.channel.socket.*;
import io.netty.util.internal.logging.*;

public class OioSocketChannel extends OioByteStreamChannel implements SocketChannel
{
    private static final InternalLogger logger;
    private final Socket socket;
    private final OioSocketChannelConfig config;
    
    public OioSocketChannel() {
        this(new Socket());
    }
    
    public OioSocketChannel(final Socket socket) {
        this(null, socket);
    }
    
    public OioSocketChannel(final Channel channel, final Socket socket) {
        super(channel);
        this.socket = socket;
        this.config = new DefaultOioSocketChannelConfig(this, socket);
        if (socket.isConnected()) {
            this.activate(socket.getInputStream(), socket.getOutputStream());
        }
        socket.setSoTimeout(1000);
    }
    
    @Override
    public ServerSocketChannel parent() {
        return (ServerSocketChannel)super.parent();
    }
    
    @Override
    public OioSocketChannelConfig config() {
        return this.config;
    }
    
    @Override
    public boolean isOpen() {
        return !this.socket.isClosed();
    }
    
    @Override
    public boolean isInputShutdown() {
        return super.isInputShutdown();
    }
    
    @Override
    public boolean isOutputShutdown() {
        return this.socket.isOutputShutdown() || this == 0;
    }
    
    @Override
    public ChannelFuture shutdownOutput() {
        return this.shutdownOutput(this.newPromise());
    }
    
    @Override
    protected int doReadBytes(final ByteBuf byteBuf) throws Exception {
        if (this.socket.isClosed()) {
            return -1;
        }
        return super.doReadBytes(byteBuf);
    }
    
    @Override
    public ChannelFuture shutdownOutput(final ChannelPromise channelPromise) {
        final EventLoop eventLoop = this.eventLoop();
        if (eventLoop.inEventLoop()) {
            this.socket.shutdownOutput();
            channelPromise.setSuccess();
        }
        else {
            eventLoop.execute(new Runnable(channelPromise) {
                final ChannelPromise val$future;
                final OioSocketChannel this$0;
                
                @Override
                public void run() {
                    this.this$0.shutdownOutput(this.val$future);
                }
            });
        }
        return channelPromise;
    }
    
    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }
    
    @Override
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress)super.remoteAddress();
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.socket.getLocalSocketAddress();
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return this.socket.getRemoteSocketAddress();
    }
    
    @Override
    protected void doBind(final SocketAddress socketAddress) throws Exception {
        this.socket.bind(socketAddress);
    }
    
    @Override
    protected void doConnect(final SocketAddress socketAddress, final SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            this.socket.bind(socketAddress2);
        }
        this.socket.connect(socketAddress, this.config().getConnectTimeoutMillis());
        this.activate(this.socket.getInputStream(), this.socket.getOutputStream());
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }
    
    @Override
    protected void doClose() throws Exception {
        this.socket.close();
    }
    
    protected boolean checkInputShutdown() {
        if (this.isInputShutdown()) {
            Thread.sleep(this.config().getSoTimeout());
            return true;
        }
        return false;
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
    
    static {
        logger = InternalLoggerFactory.getInstance(OioSocketChannel.class);
    }
}
