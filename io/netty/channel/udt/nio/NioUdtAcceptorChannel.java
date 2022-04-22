package io.netty.channel.udt.nio;

import io.netty.channel.nio.*;
import java.nio.channels.*;
import com.barchart.udt.nio.*;
import com.barchart.udt.*;
import java.net.*;
import io.netty.channel.*;
import io.netty.channel.udt.*;
import io.netty.util.internal.logging.*;

public abstract class NioUdtAcceptorChannel extends AbstractNioMessageChannel implements UdtServerChannel
{
    protected static final InternalLogger logger;
    private final UdtServerChannelConfig config;
    
    protected NioUdtAcceptorChannel(final ServerSocketChannelUDT serverSocketChannelUDT) {
        super(null, (SelectableChannel)serverSocketChannelUDT, 16);
        serverSocketChannelUDT.configureBlocking(false);
        this.config = new DefaultUdtServerChannelConfig(this, (ChannelUDT)serverSocketChannelUDT, true);
    }
    
    protected NioUdtAcceptorChannel(final TypeUDT typeUDT) {
        this(NioUdtProvider.newAcceptorChannelUDT(typeUDT));
    }
    
    @Override
    public UdtServerChannelConfig config() {
        return this.config;
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
    protected boolean doConnect(final SocketAddress socketAddress, final SocketAddress socketAddress2) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected void doFinishConnect() throws Exception {
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
    public boolean isActive() {
        return this.javaChannel().socket().isBound();
    }
    
    protected ServerSocketChannelUDT javaChannel() {
        return (ServerSocketChannelUDT)super.javaChannel();
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.javaChannel().socket().getLocalSocketAddress();
    }
    
    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }
    
    @Override
    public InetSocketAddress remoteAddress() {
        return null;
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return null;
    }
    
    @Override
    protected SelectableChannel javaChannel() {
        return (SelectableChannel)this.javaChannel();
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
    public UdtChannelConfig config() {
        return this.config();
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(NioUdtAcceptorChannel.class);
    }
}
