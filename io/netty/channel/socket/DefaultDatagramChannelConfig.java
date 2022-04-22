package io.netty.channel.socket;

import java.util.*;
import io.netty.util.internal.*;
import java.net.*;
import io.netty.buffer.*;
import io.netty.util.internal.logging.*;
import io.netty.channel.*;

public class DefaultDatagramChannelConfig extends DefaultChannelConfig implements DatagramChannelConfig
{
    private static final InternalLogger logger;
    private static final RecvByteBufAllocator DEFAULT_RCVBUF_ALLOCATOR;
    private final DatagramSocket javaSocket;
    private boolean activeOnOpen;
    
    public DefaultDatagramChannelConfig(final DatagramChannel datagramChannel, final DatagramSocket javaSocket) {
        super(datagramChannel);
        if (javaSocket == null) {
            throw new NullPointerException("javaSocket");
        }
        this.javaSocket = javaSocket;
        this.setRecvByteBufAllocator(DefaultDatagramChannelConfig.DEFAULT_RCVBUF_ALLOCATOR);
    }
    
    @Override
    public Map getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_BROADCAST, ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.SO_REUSEADDR, ChannelOption.IP_MULTICAST_LOOP_DISABLED, ChannelOption.IP_MULTICAST_ADDR, ChannelOption.IP_MULTICAST_IF, ChannelOption.IP_MULTICAST_TTL, ChannelOption.IP_TOS, ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION);
    }
    
    @Override
    public Object getOption(final ChannelOption channelOption) {
        if (channelOption == ChannelOption.SO_BROADCAST) {
            return this.isBroadcast();
        }
        if (channelOption == ChannelOption.SO_RCVBUF) {
            return this.getReceiveBufferSize();
        }
        if (channelOption == ChannelOption.SO_SNDBUF) {
            return this.getSendBufferSize();
        }
        if (channelOption == ChannelOption.SO_REUSEADDR) {
            return this.isReuseAddress();
        }
        if (channelOption == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
            return this.isLoopbackModeDisabled();
        }
        if (channelOption == ChannelOption.IP_MULTICAST_ADDR) {
            return this.getInterface();
        }
        if (channelOption == ChannelOption.IP_MULTICAST_IF) {
            return this.getNetworkInterface();
        }
        if (channelOption == ChannelOption.IP_MULTICAST_TTL) {
            return this.getTimeToLive();
        }
        if (channelOption == ChannelOption.IP_TOS) {
            return this.getTrafficClass();
        }
        if (channelOption == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
            return this.activeOnOpen;
        }
        return super.getOption(channelOption);
    }
    
    @Override
    public boolean setOption(final ChannelOption channelOption, final Object o) {
        this.validate(channelOption, o);
        if (channelOption == ChannelOption.SO_BROADCAST) {
            this.setBroadcast((boolean)o);
        }
        else if (channelOption == ChannelOption.SO_RCVBUF) {
            this.setReceiveBufferSize((int)o);
        }
        else if (channelOption == ChannelOption.SO_SNDBUF) {
            this.setSendBufferSize((int)o);
        }
        else if (channelOption == ChannelOption.SO_REUSEADDR) {
            this.setReuseAddress((boolean)o);
        }
        else if (channelOption == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
            this.setLoopbackModeDisabled((boolean)o);
        }
        else if (channelOption == ChannelOption.IP_MULTICAST_ADDR) {
            this.setInterface((InetAddress)o);
        }
        else if (channelOption == ChannelOption.IP_MULTICAST_IF) {
            this.setNetworkInterface((NetworkInterface)o);
        }
        else if (channelOption == ChannelOption.IP_MULTICAST_TTL) {
            this.setTimeToLive((int)o);
        }
        else if (channelOption == ChannelOption.IP_TOS) {
            this.setTrafficClass((int)o);
        }
        else {
            if (channelOption != ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
                return super.setOption(channelOption, o);
            }
            this.setActiveOnOpen((boolean)o);
        }
        return true;
    }
    
    private void setActiveOnOpen(final boolean activeOnOpen) {
        if (this.channel.isRegistered()) {
            throw new IllegalStateException("Can only changed before channel was registered");
        }
        this.activeOnOpen = activeOnOpen;
    }
    
    @Override
    public boolean isBroadcast() {
        return this.javaSocket.getBroadcast();
    }
    
    @Override
    public DatagramChannelConfig setBroadcast(final boolean broadcast) {
        if (broadcast && !PlatformDependent.isWindows() && !PlatformDependent.isRoot() && !this.javaSocket.getLocalAddress().isAnyLocalAddress()) {
            DefaultDatagramChannelConfig.logger.warn("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; setting the SO_BROADCAST flag anyway as requested on the socket which is bound to " + this.javaSocket.getLocalSocketAddress() + '.');
        }
        this.javaSocket.setBroadcast(broadcast);
        return this;
    }
    
    @Override
    public InetAddress getInterface() {
        if (this.javaSocket instanceof MulticastSocket) {
            return ((MulticastSocket)this.javaSocket).getInterface();
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public DatagramChannelConfig setInterface(final InetAddress interface1) {
        if (this.javaSocket instanceof MulticastSocket) {
            ((MulticastSocket)this.javaSocket).setInterface(interface1);
            return this;
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean isLoopbackModeDisabled() {
        if (this.javaSocket instanceof MulticastSocket) {
            return ((MulticastSocket)this.javaSocket).getLoopbackMode();
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public DatagramChannelConfig setLoopbackModeDisabled(final boolean loopbackMode) {
        if (this.javaSocket instanceof MulticastSocket) {
            ((MulticastSocket)this.javaSocket).setLoopbackMode(loopbackMode);
            return this;
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public NetworkInterface getNetworkInterface() {
        if (this.javaSocket instanceof MulticastSocket) {
            return ((MulticastSocket)this.javaSocket).getNetworkInterface();
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public DatagramChannelConfig setNetworkInterface(final NetworkInterface networkInterface) {
        if (this.javaSocket instanceof MulticastSocket) {
            ((MulticastSocket)this.javaSocket).setNetworkInterface(networkInterface);
            return this;
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean isReuseAddress() {
        return this.javaSocket.getReuseAddress();
    }
    
    @Override
    public DatagramChannelConfig setReuseAddress(final boolean reuseAddress) {
        this.javaSocket.setReuseAddress(reuseAddress);
        return this;
    }
    
    @Override
    public int getReceiveBufferSize() {
        return this.javaSocket.getReceiveBufferSize();
    }
    
    @Override
    public DatagramChannelConfig setReceiveBufferSize(final int receiveBufferSize) {
        this.javaSocket.setReceiveBufferSize(receiveBufferSize);
        return this;
    }
    
    @Override
    public int getSendBufferSize() {
        return this.javaSocket.getSendBufferSize();
    }
    
    @Override
    public DatagramChannelConfig setSendBufferSize(final int sendBufferSize) {
        this.javaSocket.setSendBufferSize(sendBufferSize);
        return this;
    }
    
    @Override
    public int getTimeToLive() {
        if (this.javaSocket instanceof MulticastSocket) {
            return ((MulticastSocket)this.javaSocket).getTimeToLive();
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public DatagramChannelConfig setTimeToLive(final int timeToLive) {
        if (this.javaSocket instanceof MulticastSocket) {
            ((MulticastSocket)this.javaSocket).setTimeToLive(timeToLive);
            return this;
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int getTrafficClass() {
        return this.javaSocket.getTrafficClass();
    }
    
    @Override
    public DatagramChannelConfig setTrafficClass(final int trafficClass) {
        this.javaSocket.setTrafficClass(trafficClass);
        return this;
    }
    
    @Override
    public DatagramChannelConfig setWriteSpinCount(final int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }
    
    @Override
    public DatagramChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }
    
    @Override
    public DatagramChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }
    
    @Override
    public DatagramChannelConfig setAllocator(final ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }
    
    @Override
    public DatagramChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }
    
    @Override
    public DatagramChannelConfig setAutoRead(final boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }
    
    @Override
    public DatagramChannelConfig setAutoClose(final boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }
    
    @Override
    public DatagramChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }
    
    @Override
    public DatagramChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }
    
    @Override
    public DatagramChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
    
    @Override
    public ChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
        return this.setMessageSizeEstimator(messageSizeEstimator);
    }
    
    @Override
    public ChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        return this.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
    }
    
    @Override
    public ChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        return this.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
    }
    
    @Override
    public ChannelConfig setAutoClose(final boolean autoClose) {
        return this.setAutoClose(autoClose);
    }
    
    @Override
    public ChannelConfig setAutoRead(final boolean autoRead) {
        return this.setAutoRead(autoRead);
    }
    
    @Override
    public ChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        return this.setRecvByteBufAllocator(recvByteBufAllocator);
    }
    
    @Override
    public ChannelConfig setAllocator(final ByteBufAllocator allocator) {
        return this.setAllocator(allocator);
    }
    
    @Override
    public ChannelConfig setWriteSpinCount(final int writeSpinCount) {
        return this.setWriteSpinCount(writeSpinCount);
    }
    
    @Override
    public ChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        return this.setMaxMessagesPerRead(maxMessagesPerRead);
    }
    
    @Override
    public ChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        return this.setConnectTimeoutMillis(connectTimeoutMillis);
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(DefaultDatagramChannelConfig.class);
        DEFAULT_RCVBUF_ALLOCATOR = new FixedRecvByteBufAllocator(2048);
    }
}
