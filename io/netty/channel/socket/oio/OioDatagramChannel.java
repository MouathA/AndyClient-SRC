package io.netty.channel.socket.oio;

import io.netty.channel.oio.*;
import io.netty.channel.socket.*;
import java.util.*;
import io.netty.buffer.*;
import io.netty.util.internal.*;
import java.net.*;
import io.netty.channel.*;
import io.netty.util.internal.logging.*;

public class OioDatagramChannel extends AbstractOioMessageChannel implements DatagramChannel
{
    private static final InternalLogger logger;
    private static final ChannelMetadata METADATA;
    private static final String EXPECTED_TYPES;
    private final MulticastSocket socket;
    private final DatagramChannelConfig config;
    private final DatagramPacket tmpPacket;
    private RecvByteBufAllocator.Handle allocHandle;
    
    private static MulticastSocket newSocket() {
        return new MulticastSocket((SocketAddress)null);
    }
    
    public OioDatagramChannel() {
        this(newSocket());
    }
    
    public OioDatagramChannel(final MulticastSocket socket) {
        super(null);
        this.tmpPacket = new DatagramPacket(EmptyArrays.EMPTY_BYTES, 0);
        socket.setSoTimeout(1000);
        socket.setBroadcast(false);
        this.socket = socket;
        this.config = new DefaultDatagramChannelConfig(this, socket);
    }
    
    @Override
    public ChannelMetadata metadata() {
        return OioDatagramChannel.METADATA;
    }
    
    @Override
    public DatagramChannelConfig config() {
        return this.config;
    }
    
    @Override
    public boolean isConnected() {
        return this.socket.isConnected();
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
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }
    
    @Override
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress)super.remoteAddress();
    }
    
    @Override
    protected void doConnect(final SocketAddress socketAddress, final SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            this.socket.bind(socketAddress2);
        }
        this.socket.connect(socketAddress);
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.socket.disconnect();
    }
    
    @Override
    protected void doClose() throws Exception {
        this.socket.close();
    }
    
    @Override
    protected int doReadMessages(final List list) throws Exception {
        final DatagramChannelConfig config = this.config();
        RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
        if (allocHandle == null) {
            allocHandle = (this.allocHandle = config.getRecvByteBufAllocator().newHandle());
        }
        final ByteBuf heapBuffer = config.getAllocator().heapBuffer(allocHandle.guess());
        this.tmpPacket.setData(heapBuffer.array(), heapBuffer.arrayOffset(), heapBuffer.capacity());
        this.socket.receive(this.tmpPacket);
        final InetSocketAddress inetSocketAddress = (InetSocketAddress)this.tmpPacket.getSocketAddress();
        this.tmpPacket.getLength();
        allocHandle.record(-1);
        list.add(new io.netty.channel.socket.DatagramPacket(heapBuffer.writerIndex(-1), this.localAddress(), inetSocketAddress));
        return 1;
    }
    
    @Override
    protected void doWrite(final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        while (true) {
            final Object current = channelOutboundBuffer.current();
            if (current == null) {
                break;
            }
            SocketAddress recipient;
            ByteBuf byteBuf;
            if (current instanceof AddressedEnvelope) {
                final AddressedEnvelope addressedEnvelope = (AddressedEnvelope)current;
                recipient = addressedEnvelope.recipient();
                byteBuf = (ByteBuf)addressedEnvelope.content();
            }
            else {
                byteBuf = (ByteBuf)current;
                recipient = null;
            }
            final int readableBytes = byteBuf.readableBytes();
            if (recipient != null) {
                this.tmpPacket.setSocketAddress(recipient);
            }
            if (byteBuf.hasArray()) {
                this.tmpPacket.setData(byteBuf.array(), byteBuf.arrayOffset() + byteBuf.readerIndex(), readableBytes);
            }
            else {
                final byte[] data = new byte[readableBytes];
                byteBuf.getBytes(byteBuf.readerIndex(), data);
                this.tmpPacket.setData(data);
            }
            this.socket.send(this.tmpPacket);
            channelOutboundBuffer.remove();
        }
    }
    
    @Override
    protected Object filterOutboundMessage(final Object o) {
        if (o instanceof io.netty.channel.socket.DatagramPacket || o instanceof ByteBuf) {
            return o;
        }
        if (o instanceof AddressedEnvelope && ((AddressedEnvelope)o).content() instanceof ByteBuf) {
            return o;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(o) + OioDatagramChannel.EXPECTED_TYPES);
    }
    
    @Override
    public ChannelFuture joinGroup(final InetAddress inetAddress) {
        return this.joinGroup(inetAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture joinGroup(final InetAddress inetAddress, final ChannelPromise channelPromise) {
        this.ensureBound();
        this.socket.joinGroup(inetAddress);
        channelPromise.setSuccess();
        return channelPromise;
    }
    
    @Override
    public ChannelFuture joinGroup(final InetSocketAddress inetSocketAddress, final NetworkInterface networkInterface) {
        return this.joinGroup(inetSocketAddress, networkInterface, this.newPromise());
    }
    
    @Override
    public ChannelFuture joinGroup(final InetSocketAddress inetSocketAddress, final NetworkInterface networkInterface, final ChannelPromise channelPromise) {
        this.ensureBound();
        this.socket.joinGroup(inetSocketAddress, networkInterface);
        channelPromise.setSuccess();
        return channelPromise;
    }
    
    @Override
    public ChannelFuture joinGroup(final InetAddress inetAddress, final NetworkInterface networkInterface, final InetAddress inetAddress2) {
        return this.newFailedFuture(new UnsupportedOperationException());
    }
    
    @Override
    public ChannelFuture joinGroup(final InetAddress inetAddress, final NetworkInterface networkInterface, final InetAddress inetAddress2, final ChannelPromise channelPromise) {
        channelPromise.setFailure((Throwable)new UnsupportedOperationException());
        return channelPromise;
    }
    
    private void ensureBound() {
        if (this == 0) {
            throw new IllegalStateException(DatagramChannel.class.getName() + " must be bound to join a group.");
        }
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetAddress inetAddress) {
        return this.leaveGroup(inetAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetAddress inetAddress, final ChannelPromise channelPromise) {
        this.socket.leaveGroup(inetAddress);
        channelPromise.setSuccess();
        return channelPromise;
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetSocketAddress inetSocketAddress, final NetworkInterface networkInterface) {
        return this.leaveGroup(inetSocketAddress, networkInterface, this.newPromise());
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetSocketAddress inetSocketAddress, final NetworkInterface networkInterface, final ChannelPromise channelPromise) {
        this.socket.leaveGroup(inetSocketAddress, networkInterface);
        channelPromise.setSuccess();
        return channelPromise;
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetAddress inetAddress, final NetworkInterface networkInterface, final InetAddress inetAddress2) {
        return this.newFailedFuture(new UnsupportedOperationException());
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetAddress inetAddress, final NetworkInterface networkInterface, final InetAddress inetAddress2, final ChannelPromise channelPromise) {
        channelPromise.setFailure((Throwable)new UnsupportedOperationException());
        return channelPromise;
    }
    
    @Override
    public ChannelFuture block(final InetAddress inetAddress, final NetworkInterface networkInterface, final InetAddress inetAddress2) {
        return this.newFailedFuture(new UnsupportedOperationException());
    }
    
    @Override
    public ChannelFuture block(final InetAddress inetAddress, final NetworkInterface networkInterface, final InetAddress inetAddress2, final ChannelPromise channelPromise) {
        channelPromise.setFailure((Throwable)new UnsupportedOperationException());
        return channelPromise;
    }
    
    @Override
    public ChannelFuture block(final InetAddress inetAddress, final InetAddress inetAddress2) {
        return this.newFailedFuture(new UnsupportedOperationException());
    }
    
    @Override
    public ChannelFuture block(final InetAddress inetAddress, final InetAddress inetAddress2, final ChannelPromise channelPromise) {
        channelPromise.setFailure((Throwable)new UnsupportedOperationException());
        return channelPromise;
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
        logger = InternalLoggerFactory.getInstance(OioDatagramChannel.class);
        METADATA = new ChannelMetadata(true);
        EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(io.netty.channel.socket.DatagramPacket.class) + ", " + StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(SocketAddress.class) + ">, " + StringUtil.simpleClassName(ByteBuf.class) + ')';
    }
}
