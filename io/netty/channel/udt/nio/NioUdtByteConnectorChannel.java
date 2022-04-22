package io.netty.channel.udt.nio;

import io.netty.channel.nio.*;
import com.barchart.udt.*;
import io.netty.channel.udt.*;
import com.barchart.udt.nio.*;
import java.net.*;
import io.netty.buffer.*;
import java.nio.channels.*;
import io.netty.channel.*;
import io.netty.util.internal.logging.*;

public class NioUdtByteConnectorChannel extends AbstractNioByteChannel implements UdtChannel
{
    private static final InternalLogger logger;
    private static final ChannelMetadata METADATA;
    private final UdtChannelConfig config;
    
    public NioUdtByteConnectorChannel() {
        this(TypeUDT.STREAM);
    }
    
    public NioUdtByteConnectorChannel(final Channel channel, final SocketChannelUDT socketChannelUDT) {
        super(channel, (SelectableChannel)socketChannelUDT);
        socketChannelUDT.configureBlocking(false);
        switch (socketChannelUDT.socketUDT().status()) {
            case INIT:
            case OPENED: {
                this.config = new DefaultUdtChannelConfig(this, (ChannelUDT)socketChannelUDT, true);
                break;
            }
            default: {
                this.config = new DefaultUdtChannelConfig(this, (ChannelUDT)socketChannelUDT, false);
                break;
            }
        }
    }
    
    public NioUdtByteConnectorChannel(final SocketChannelUDT socketChannelUDT) {
        this(null, socketChannelUDT);
    }
    
    public NioUdtByteConnectorChannel(final TypeUDT typeUDT) {
        this(NioUdtProvider.newConnectorChannelUDT(typeUDT));
    }
    
    @Override
    public UdtChannelConfig config() {
        return this.config;
    }
    
    @Override
    protected void doBind(final SocketAddress socketAddress) throws Exception {
        this.javaChannel().bind(socketAddress);
    }
    
    @Override
    protected void doClose() throws Exception {
        this.javaChannel().close();
    }
    
    @Override
    protected boolean doConnect(final SocketAddress socketAddress, final SocketAddress socketAddress2) throws Exception {
        this.doBind((socketAddress2 != null) ? socketAddress2 : new InetSocketAddress(0));
        final boolean connect = this.javaChannel().connect(socketAddress);
        if (!connect) {
            this.selectionKey().interestOps(this.selectionKey().interestOps() | 0x8);
        }
        final boolean b = connect;
        if (!true) {
            this.doClose();
        }
        return b;
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }
    
    @Override
    protected void doFinishConnect() throws Exception {
        if (this.javaChannel().finishConnect()) {
            this.selectionKey().interestOps(this.selectionKey().interestOps() & 0xFFFFFFF7);
            return;
        }
        throw new Error("Provider error: failed to finish connect. Provider library should be upgraded.");
    }
    
    @Override
    protected int doReadBytes(final ByteBuf byteBuf) throws Exception {
        return byteBuf.writeBytes((ScatteringByteChannel)this.javaChannel(), byteBuf.writableBytes());
    }
    
    @Override
    protected int doWriteBytes(final ByteBuf byteBuf) throws Exception {
        return byteBuf.readBytes((GatheringByteChannel)this.javaChannel(), byteBuf.readableBytes());
    }
    
    @Override
    protected long doWriteFileRegion(final FileRegion fileRegion) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean isActive() {
        final SocketChannelUDT javaChannel = this.javaChannel();
        return javaChannel.isOpen() && javaChannel.isConnectFinished();
    }
    
    protected SocketChannelUDT javaChannel() {
        return (SocketChannelUDT)super.javaChannel();
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.javaChannel().socket().getLocalSocketAddress();
    }
    
    @Override
    public ChannelMetadata metadata() {
        return NioUdtByteConnectorChannel.METADATA;
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return this.javaChannel().socket().getRemoteSocketAddress();
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
    
    static {
        logger = InternalLoggerFactory.getInstance(NioUdtByteConnectorChannel.class);
        METADATA = new ChannelMetadata(false);
    }
}
