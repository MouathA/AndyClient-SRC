package io.netty.channel.socket.nio;

import io.netty.channel.nio.*;
import java.nio.channels.spi.*;
import io.netty.channel.socket.*;
import io.netty.buffer.*;
import java.nio.*;
import io.netty.util.*;
import io.netty.util.internal.*;
import java.net.*;
import java.nio.channels.*;
import java.util.*;
import io.netty.channel.*;

public final class NioDatagramChannel extends AbstractNioMessageChannel implements DatagramChannel
{
    private static final ChannelMetadata METADATA;
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER;
    private static final String EXPECTED_TYPES;
    private final DatagramChannelConfig config;
    private Map memberships;
    private RecvByteBufAllocator.Handle allocHandle;
    
    private static java.nio.channels.DatagramChannel newSocket(final SelectorProvider selectorProvider) {
        return selectorProvider.openDatagramChannel();
    }
    
    private static java.nio.channels.DatagramChannel newSocket(final SelectorProvider selectorProvider, final InternetProtocolFamily internetProtocolFamily) {
        if (internetProtocolFamily == null) {
            return newSocket(selectorProvider);
        }
        return selectorProvider.openDatagramChannel(ProtocolFamilyConverter.convert(internetProtocolFamily));
    }
    
    private static void checkJavaVersion() {
        if (PlatformDependent.javaVersion() < 7) {
            throw new UnsupportedOperationException("Only supported on java 7+.");
        }
    }
    
    public NioDatagramChannel() {
        this(newSocket(NioDatagramChannel.DEFAULT_SELECTOR_PROVIDER));
    }
    
    public NioDatagramChannel(final SelectorProvider selectorProvider) {
        this(newSocket(selectorProvider));
    }
    
    public NioDatagramChannel(final InternetProtocolFamily internetProtocolFamily) {
        this(newSocket(NioDatagramChannel.DEFAULT_SELECTOR_PROVIDER, internetProtocolFamily));
    }
    
    public NioDatagramChannel(final SelectorProvider selectorProvider, final InternetProtocolFamily internetProtocolFamily) {
        this(newSocket(selectorProvider, internetProtocolFamily));
    }
    
    public NioDatagramChannel(final java.nio.channels.DatagramChannel datagramChannel) {
        super(null, datagramChannel, 1);
        this.config = new NioDatagramChannelConfig(this, datagramChannel);
    }
    
    @Override
    public ChannelMetadata metadata() {
        return NioDatagramChannel.METADATA;
    }
    
    @Override
    public DatagramChannelConfig config() {
        return this.config;
    }
    
    @Override
    public boolean isActive() {
        final java.nio.channels.DatagramChannel javaChannel = this.javaChannel();
        return javaChannel.isOpen() && (((boolean)this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) && this.isRegistered()) || javaChannel.socket().isBound());
    }
    
    @Override
    public boolean isConnected() {
        return this.javaChannel().isConnected();
    }
    
    @Override
    protected java.nio.channels.DatagramChannel javaChannel() {
        return (java.nio.channels.DatagramChannel)super.javaChannel();
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.javaChannel().socket().getLocalSocketAddress();
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return this.javaChannel().socket().getRemoteSocketAddress();
    }
    
    @Override
    protected void doBind(final SocketAddress socketAddress) throws Exception {
        this.javaChannel().socket().bind(socketAddress);
    }
    
    @Override
    protected boolean doConnect(final SocketAddress socketAddress, final SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            this.javaChannel().socket().bind(socketAddress2);
        }
        this.javaChannel().connect(socketAddress);
        return true;
    }
    
    @Override
    protected void doFinishConnect() throws Exception {
        throw new Error();
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.javaChannel().disconnect();
    }
    
    @Override
    protected void doClose() throws Exception {
        this.javaChannel().close();
    }
    
    @Override
    protected int doReadMessages(final List list) throws Exception {
        final java.nio.channels.DatagramChannel javaChannel = this.javaChannel();
        final DatagramChannelConfig config = this.config();
        RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
        if (allocHandle == null) {
            allocHandle = (this.allocHandle = config.getRecvByteBufAllocator().newHandle());
        }
        final ByteBuf allocate = allocHandle.allocate(config.getAllocator());
        final ByteBuffer internalNioBuffer = allocate.internalNioBuffer(allocate.writerIndex(), allocate.writableBytes());
        internalNioBuffer.position();
        final InetSocketAddress inetSocketAddress = (InetSocketAddress)javaChannel.receive(internalNioBuffer);
        if (inetSocketAddress == null) {
            return 0;
        }
        final int n = internalNioBuffer.position() + 1;
        allocate.writerIndex(allocate.writerIndex() + 0);
        allocHandle.record(0);
        list.add(new DatagramPacket(allocate, this.localAddress(), inetSocketAddress));
        return 1;
    }
    
    @Override
    protected boolean doWriteMessage(final Object o, final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        SocketAddress recipient;
        ByteBuf byteBuf;
        if (o instanceof AddressedEnvelope) {
            final AddressedEnvelope addressedEnvelope = (AddressedEnvelope)o;
            recipient = addressedEnvelope.recipient();
            byteBuf = (ByteBuf)addressedEnvelope.content();
        }
        else {
            byteBuf = (ByteBuf)o;
            recipient = null;
        }
        final int readableBytes = byteBuf.readableBytes();
        if (readableBytes == 0) {
            return true;
        }
        final ByteBuffer internalNioBuffer = byteBuf.internalNioBuffer(byteBuf.readerIndex(), readableBytes);
        int n;
        if (recipient != null) {
            n = this.javaChannel().send(internalNioBuffer, recipient);
        }
        else {
            n = this.javaChannel().write(internalNioBuffer);
        }
        return n > 0;
    }
    
    @Override
    protected Object filterOutboundMessage(final Object o) {
        if (o instanceof DatagramPacket) {
            final DatagramPacket datagramPacket = (DatagramPacket)o;
            final ByteBuf byteBuf = (ByteBuf)datagramPacket.content();
            if (byteBuf != 0) {
                return datagramPacket;
            }
            return new DatagramPacket(this.newDirectBuffer(datagramPacket, byteBuf), (InetSocketAddress)datagramPacket.recipient());
        }
        else {
            if (!(o instanceof ByteBuf)) {
                if (o instanceof AddressedEnvelope) {
                    final AddressedEnvelope addressedEnvelope = (AddressedEnvelope)o;
                    if (addressedEnvelope.content() instanceof ByteBuf) {
                        final ByteBuf byteBuf2 = (ByteBuf)addressedEnvelope.content();
                        if (byteBuf2 != 0) {
                            return addressedEnvelope;
                        }
                        return new DefaultAddressedEnvelope(this.newDirectBuffer(addressedEnvelope, byteBuf2), addressedEnvelope.recipient());
                    }
                }
                throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(o) + NioDatagramChannel.EXPECTED_TYPES);
            }
            final ByteBuf byteBuf3 = (ByteBuf)o;
            if (byteBuf3 != 0) {
                return byteBuf3;
            }
            return this.newDirectBuffer(byteBuf3);
        }
    }
    
    @Override
    protected boolean continueOnWriteError() {
        return true;
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
    public ChannelFuture joinGroup(final InetAddress inetAddress) {
        return this.joinGroup(inetAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture joinGroup(final InetAddress inetAddress, final ChannelPromise channelPromise) {
        return this.joinGroup(inetAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), null, channelPromise);
    }
    
    @Override
    public ChannelFuture joinGroup(final InetSocketAddress inetSocketAddress, final NetworkInterface networkInterface) {
        return this.joinGroup(inetSocketAddress, networkInterface, this.newPromise());
    }
    
    @Override
    public ChannelFuture joinGroup(final InetSocketAddress inetSocketAddress, final NetworkInterface networkInterface, final ChannelPromise channelPromise) {
        return this.joinGroup(inetSocketAddress.getAddress(), networkInterface, null, channelPromise);
    }
    
    @Override
    public ChannelFuture joinGroup(final InetAddress inetAddress, final NetworkInterface networkInterface, final InetAddress inetAddress2) {
        return this.joinGroup(inetAddress, networkInterface, inetAddress2, this.newPromise());
    }
    
    @Override
    public ChannelFuture joinGroup(final InetAddress inetAddress, final NetworkInterface networkInterface, final InetAddress inetAddress2, final ChannelPromise channelPromise) {
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        }
        if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        }
        MembershipKey membershipKey;
        if (inetAddress2 == null) {
            membershipKey = this.javaChannel().join(inetAddress, networkInterface);
        }
        else {
            membershipKey = this.javaChannel().join(inetAddress, networkInterface, inetAddress2);
        }
        // monitorenter(this)
        List<?> list = null;
        if (this.memberships == null) {
            this.memberships = new HashMap();
        }
        else {
            list = this.memberships.get(inetAddress);
        }
        if (list == null) {
            list = new ArrayList<Object>();
            this.memberships.put(inetAddress, list);
        }
        list.add(membershipKey);
        // monitorexit(this)
        channelPromise.setSuccess();
        return channelPromise;
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetAddress inetAddress) {
        return this.leaveGroup(inetAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetAddress inetAddress, final ChannelPromise channelPromise) {
        return this.leaveGroup(inetAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), null, channelPromise);
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetSocketAddress inetSocketAddress, final NetworkInterface networkInterface) {
        return this.leaveGroup(inetSocketAddress, networkInterface, this.newPromise());
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetSocketAddress inetSocketAddress, final NetworkInterface networkInterface, final ChannelPromise channelPromise) {
        return this.leaveGroup(inetSocketAddress.getAddress(), networkInterface, null, channelPromise);
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetAddress inetAddress, final NetworkInterface networkInterface, final InetAddress inetAddress2) {
        return this.leaveGroup(inetAddress, networkInterface, inetAddress2, this.newPromise());
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetAddress inetAddress, final NetworkInterface networkInterface, final InetAddress inetAddress2, final ChannelPromise channelPromise) {
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        }
        if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        }
        // monitorenter(this)
        if (this.memberships != null) {
            final List<MembershipKey> list = this.memberships.get(inetAddress);
            if (list != null) {
                final Iterator<MembershipKey> iterator = list.iterator();
                while (iterator.hasNext()) {
                    final MembershipKey membershipKey = iterator.next();
                    if (networkInterface.equals(membershipKey.networkInterface()) && ((inetAddress2 == null && membershipKey.sourceAddress() == null) || (inetAddress2 != null && inetAddress2.equals(membershipKey.sourceAddress())))) {
                        membershipKey.drop();
                        iterator.remove();
                    }
                }
                if (list.isEmpty()) {
                    this.memberships.remove(inetAddress);
                }
            }
        }
        // monitorexit(this)
        channelPromise.setSuccess();
        return channelPromise;
    }
    
    @Override
    public ChannelFuture block(final InetAddress inetAddress, final NetworkInterface networkInterface, final InetAddress inetAddress2) {
        return this.block(inetAddress, networkInterface, inetAddress2, this.newPromise());
    }
    
    @Override
    public ChannelFuture block(final InetAddress inetAddress, final NetworkInterface networkInterface, final InetAddress inetAddress2, final ChannelPromise channelPromise) {
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        }
        if (inetAddress2 == null) {
            throw new NullPointerException("sourceToBlock");
        }
        if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        }
        // monitorenter(this)
        if (this.memberships != null) {
            for (final MembershipKey membershipKey : this.memberships.get(inetAddress)) {
                if (networkInterface.equals(membershipKey.networkInterface())) {
                    membershipKey.block(inetAddress2);
                }
            }
        }
        // monitorexit(this)
        channelPromise.setSuccess();
        return channelPromise;
    }
    
    @Override
    public ChannelFuture block(final InetAddress inetAddress, final InetAddress inetAddress2) {
        return this.block(inetAddress, inetAddress2, this.newPromise());
    }
    
    @Override
    public ChannelFuture block(final InetAddress inetAddress, final InetAddress inetAddress2, final ChannelPromise channelPromise) {
        return this.block(inetAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), inetAddress2, channelPromise);
    }
    
    @Override
    protected void setReadPending(final boolean readPending) {
        super.setReadPending(readPending);
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
    
    static {
        METADATA = new ChannelMetadata(true);
        DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
        EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(DatagramPacket.class) + ", " + StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(SocketAddress.class) + ">, " + StringUtil.simpleClassName(ByteBuf.class) + ')';
    }
}
