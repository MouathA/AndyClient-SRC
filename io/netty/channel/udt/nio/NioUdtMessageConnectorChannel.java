package io.netty.channel.udt.nio;

import io.netty.channel.nio.*;
import com.barchart.udt.*;
import com.barchart.udt.nio.*;
import java.net.*;
import java.util.*;
import java.nio.channels.*;
import io.netty.channel.udt.*;
import io.netty.buffer.*;
import io.netty.util.internal.*;
import io.netty.channel.*;
import io.netty.util.internal.logging.*;

public class NioUdtMessageConnectorChannel extends AbstractNioMessageChannel implements UdtChannel
{
    private static final InternalLogger logger;
    private static final ChannelMetadata METADATA;
    private static final String EXPECTED_TYPE;
    private final UdtChannelConfig config;
    
    public NioUdtMessageConnectorChannel() {
        this(TypeUDT.DATAGRAM);
    }
    
    public NioUdtMessageConnectorChannel(final Channel channel, final SocketChannelUDT socketChannelUDT) {
        super(channel, (SelectableChannel)socketChannelUDT, 1);
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
    
    public NioUdtMessageConnectorChannel(final SocketChannelUDT socketChannelUDT) {
        this(null, socketChannelUDT);
    }
    
    public NioUdtMessageConnectorChannel(final TypeUDT typeUDT) {
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
        return connect;
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
    protected int doReadMessages(final List list) throws Exception {
        final int receiveBufferSize = this.config.getReceiveBufferSize();
        final ByteBuf directBuffer = this.config.getAllocator().directBuffer(receiveBufferSize);
        final int writeBytes = directBuffer.writeBytes((ScatteringByteChannel)this.javaChannel(), receiveBufferSize);
        if (writeBytes <= 0) {
            directBuffer.release();
            return 0;
        }
        if (writeBytes >= receiveBufferSize) {
            this.javaChannel().close();
            throw new ChannelException("Invalid config : increase receive buffer size to avoid message truncation");
        }
        list.add(new UdtMessage(directBuffer));
        return 1;
    }
    
    @Override
    protected boolean doWriteMessage(final Object o, final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        final ByteBuf content = ((UdtMessage)o).content();
        final int readableBytes = content.readableBytes();
        long write;
        if (content.nioBufferCount() == 1) {
            write = this.javaChannel().write(content.nioBuffer());
        }
        else {
            write = this.javaChannel().write(content.nioBuffers());
        }
        if (write <= 0L && readableBytes > 0) {
            return false;
        }
        if (write != readableBytes) {
            throw new Error("Provider error: failed to write message. Provider library should be upgraded.");
        }
        return true;
    }
    
    @Override
    protected final Object filterOutboundMessage(final Object o) throws Exception {
        if (o instanceof UdtMessage) {
            return o;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(o) + NioUdtMessageConnectorChannel.EXPECTED_TYPE);
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
        return NioUdtMessageConnectorChannel.METADATA;
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
        logger = InternalLoggerFactory.getInstance(NioUdtMessageConnectorChannel.class);
        METADATA = new ChannelMetadata(false);
        EXPECTED_TYPE = " (expected: " + StringUtil.simpleClassName(UdtMessage.class) + ')';
    }
}
