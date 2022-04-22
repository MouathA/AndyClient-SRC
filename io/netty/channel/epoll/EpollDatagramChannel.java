package io.netty.channel.epoll;

import java.net.*;
import io.netty.buffer.*;
import java.nio.channels.*;
import java.nio.*;
import java.io.*;
import io.netty.util.internal.*;
import io.netty.channel.socket.*;
import io.netty.channel.*;
import io.netty.util.*;

public final class EpollDatagramChannel extends AbstractEpollChannel implements DatagramChannel
{
    private static final ChannelMetadata METADATA;
    private static final String EXPECTED_TYPES;
    private InetSocketAddress local;
    private InetSocketAddress remote;
    private boolean connected;
    private final EpollDatagramChannelConfig config;
    
    public EpollDatagramChannel() {
        super(Native.socketDgramFd(), 1);
        this.config = new EpollDatagramChannelConfig(this);
    }
    
    @Override
    public ChannelMetadata metadata() {
        return EpollDatagramChannel.METADATA;
    }
    
    @Override
    public boolean isActive() {
        return this.fd != -1 && (((boolean)this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) && this.isRegistered()) || this.active);
    }
    
    @Override
    public boolean isConnected() {
        return this.connected;
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
        channelPromise.setFailure((Throwable)new UnsupportedOperationException("Multicast not supported"));
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
        channelPromise.setFailure((Throwable)new UnsupportedOperationException("Multicast not supported"));
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
        channelPromise.setFailure((Throwable)new UnsupportedOperationException("Multicast not supported"));
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
    protected AbstractEpollUnsafe newUnsafe() {
        return new EpollDatagramChannelUnsafe();
    }
    
    @Override
    protected InetSocketAddress localAddress0() {
        return this.local;
    }
    
    @Override
    protected InetSocketAddress remoteAddress0() {
        return this.remote;
    }
    
    @Override
    protected void doBind(final SocketAddress socketAddress) throws Exception {
        final InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
        AbstractEpollChannel.checkResolvable(inetSocketAddress);
        Native.bind(this.fd, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
        this.local = Native.localAddress(this.fd);
        this.active = true;
    }
    
    @Override
    protected void doWrite(final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        while (true) {
            final Object current = channelOutboundBuffer.current();
            if (current == null) {
                this.clearEpollOut();
                break;
            }
            for (int n = this.config().getWriteSpinCount() - 1; n >= 0 && !this.doWriteMessage(current); --n) {}
            if (!true) {
                this.setEpollOut();
                break;
            }
            channelOutboundBuffer.remove();
        }
    }
    
    private boolean doWriteMessage(final Object o) throws IOException {
        ByteBuf byteBuf;
        InetSocketAddress remote;
        if (o instanceof AddressedEnvelope) {
            final AddressedEnvelope addressedEnvelope = (AddressedEnvelope)o;
            byteBuf = (ByteBuf)addressedEnvelope.content();
            remote = (InetSocketAddress)addressedEnvelope.recipient();
        }
        else {
            byteBuf = (ByteBuf)o;
            remote = null;
        }
        if (byteBuf.readableBytes() == 0) {
            return true;
        }
        if (remote == null) {
            remote = this.remote;
            if (remote == null) {
                throw new NotYetConnectedException();
            }
        }
        int n;
        if (byteBuf.hasMemoryAddress()) {
            n = Native.sendToAddress(this.fd, byteBuf.memoryAddress(), byteBuf.readerIndex(), byteBuf.writerIndex(), remote.getAddress(), remote.getPort());
        }
        else {
            final ByteBuffer internalNioBuffer = byteBuf.internalNioBuffer(byteBuf.readerIndex(), byteBuf.readableBytes());
            n = Native.sendTo(this.fd, internalNioBuffer, internalNioBuffer.position(), internalNioBuffer.limit(), remote.getAddress(), remote.getPort());
        }
        return n > 0;
    }
    
    @Override
    protected Object filterOutboundMessage(final Object o) {
        if (o instanceof DatagramPacket) {
            final DatagramPacket datagramPacket = (DatagramPacket)o;
            final ByteBuf byteBuf = (ByteBuf)datagramPacket.content();
            if (byteBuf.hasMemoryAddress()) {
                return o;
            }
            return new DatagramPacket(this.newDirectBuffer(datagramPacket, byteBuf), (InetSocketAddress)datagramPacket.recipient());
        }
        else {
            if (!(o instanceof ByteBuf)) {
                if (o instanceof AddressedEnvelope) {
                    final AddressedEnvelope addressedEnvelope = (AddressedEnvelope)o;
                    if (addressedEnvelope.content() instanceof ByteBuf && (addressedEnvelope.recipient() == null || addressedEnvelope.recipient() instanceof InetSocketAddress)) {
                        final ByteBuf byteBuf2 = (ByteBuf)addressedEnvelope.content();
                        if (byteBuf2.hasMemoryAddress()) {
                            return addressedEnvelope;
                        }
                        return new DefaultAddressedEnvelope(this.newDirectBuffer(addressedEnvelope, byteBuf2), addressedEnvelope.recipient());
                    }
                }
                throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(o) + EpollDatagramChannel.EXPECTED_TYPES);
            }
            final ByteBuf byteBuf3 = (ByteBuf)o;
            if (byteBuf3.hasMemoryAddress()) {
                return o;
            }
            return this.newDirectBuffer(byteBuf3);
        }
    }
    
    @Override
    public EpollDatagramChannelConfig config() {
        return this.config;
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.connected = false;
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
    public DatagramChannelConfig config() {
        return this.config();
    }
    
    static InetSocketAddress access$002(final EpollDatagramChannel epollDatagramChannel, final InetSocketAddress remote) {
        return epollDatagramChannel.remote = remote;
    }
    
    static InetSocketAddress access$102(final EpollDatagramChannel epollDatagramChannel, final InetSocketAddress local) {
        return epollDatagramChannel.local = local;
    }
    
    static boolean access$202(final EpollDatagramChannel epollDatagramChannel, final boolean connected) {
        return epollDatagramChannel.connected = connected;
    }
    
    static {
        METADATA = new ChannelMetadata(true);
        EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(DatagramPacket.class) + ", " + StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(InetSocketAddress.class) + ">, " + StringUtil.simpleClassName(ByteBuf.class) + ')';
    }
    
    static final class DatagramSocketAddress extends InetSocketAddress
    {
        private static final long serialVersionUID = 1348596211215015739L;
        final int receivedAmount;
        
        DatagramSocketAddress(final String s, final int n, final int receivedAmount) {
            super(s, n);
            this.receivedAmount = receivedAmount;
        }
    }
    
    final class EpollDatagramChannelUnsafe extends AbstractEpollUnsafe
    {
        private RecvByteBufAllocator.Handle allocHandle;
        static final boolean $assertionsDisabled;
        final EpollDatagramChannel this$0;
        
        EpollDatagramChannelUnsafe(final EpollDatagramChannel this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        public void connect(final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
            final InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
            if (socketAddress2 != null) {
                this.this$0.doBind(socketAddress2);
            }
            AbstractEpollChannel.checkResolvable(inetSocketAddress);
            EpollDatagramChannel.access$002(this.this$0, inetSocketAddress);
            EpollDatagramChannel.access$102(this.this$0, Native.localAddress(this.this$0.fd));
            if (!true) {
                this.this$0.doClose();
            }
            else {
                channelPromise.setSuccess();
                EpollDatagramChannel.access$202(this.this$0, true);
            }
        }
        
        @Override
        void epollInReady() {
            final EpollDatagramChannelConfig config = this.this$0.config();
            RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
            if (allocHandle == null) {
                allocHandle = (this.allocHandle = config.getRecvByteBufAllocator().newHandle());
            }
            assert this.this$0.eventLoop().inEventLoop();
            final ChannelPipeline pipeline = this.this$0.pipeline();
            ByteBuf allocate;
            while (true) {
                allocate = allocHandle.allocate(config.getAllocator());
                final int writerIndex = allocate.writerIndex();
                DatagramSocketAddress datagramSocketAddress;
                if (allocate.hasMemoryAddress()) {
                    datagramSocketAddress = Native.recvFromAddress(this.this$0.fd, allocate.memoryAddress(), writerIndex, allocate.capacity());
                }
                else {
                    final ByteBuffer internalNioBuffer = allocate.internalNioBuffer(writerIndex, allocate.writableBytes());
                    datagramSocketAddress = Native.recvFrom(this.this$0.fd, internalNioBuffer, internalNioBuffer.position(), internalNioBuffer.limit());
                }
                if (datagramSocketAddress == null) {
                    break;
                }
                final int receivedAmount = datagramSocketAddress.receivedAmount;
                allocate.writerIndex(allocate.writerIndex() + receivedAmount);
                allocHandle.record(receivedAmount);
                this.readPending = false;
                pipeline.fireChannelRead(new DatagramPacket(allocate, (InetSocketAddress)this.localAddress(), datagramSocketAddress));
                final ReferenceCounted referenceCounted = null;
                if (referenceCounted == null) {
                    continue;
                }
                referenceCounted.release();
            }
            if (allocate != null) {
                allocate.release();
            }
            if (!this.this$0.config().isAutoRead() && !this.readPending) {
                this.this$0.clearEpollIn();
            }
        }
        
        static {
            $assertionsDisabled = !EpollDatagramChannel.class.desiredAssertionStatus();
        }
    }
}
