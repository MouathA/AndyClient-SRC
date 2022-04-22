package io.netty.channel.epoll;

import io.netty.channel.socket.*;
import java.util.*;
import java.net.*;
import io.netty.buffer.*;
import io.netty.channel.*;

public final class EpollDatagramChannelConfig extends DefaultChannelConfig implements DatagramChannelConfig
{
    private static final RecvByteBufAllocator DEFAULT_RCVBUF_ALLOCATOR;
    private final EpollDatagramChannel datagramChannel;
    private boolean activeOnOpen;
    
    EpollDatagramChannelConfig(final EpollDatagramChannel datagramChannel) {
        super(datagramChannel);
        this.datagramChannel = datagramChannel;
        this.setRecvByteBufAllocator(EpollDatagramChannelConfig.DEFAULT_RCVBUF_ALLOCATOR);
    }
    
    @Override
    public Map getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_BROADCAST, ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.SO_REUSEADDR, ChannelOption.IP_MULTICAST_LOOP_DISABLED, ChannelOption.IP_MULTICAST_ADDR, ChannelOption.IP_MULTICAST_IF, ChannelOption.IP_MULTICAST_TTL, ChannelOption.IP_TOS, ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION, EpollChannelOption.SO_REUSEPORT);
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
        if (channelOption == EpollChannelOption.SO_REUSEPORT) {
            return this.isReusePort();
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
        else if (channelOption == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
            this.setActiveOnOpen((boolean)o);
        }
        else {
            if (channelOption != EpollChannelOption.SO_REUSEPORT) {
                return super.setOption(channelOption, o);
            }
            this.setReusePort((boolean)o);
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
    public EpollDatagramChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
    
    @Override
    public EpollDatagramChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }
    
    @Override
    public EpollDatagramChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }
    
    @Override
    public EpollDatagramChannelConfig setAutoClose(final boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }
    
    @Override
    public EpollDatagramChannelConfig setAutoRead(final boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }
    
    @Override
    public EpollDatagramChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }
    
    @Override
    public EpollDatagramChannelConfig setWriteSpinCount(final int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }
    
    @Override
    public EpollDatagramChannelConfig setAllocator(final ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }
    
    @Override
    public EpollDatagramChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }
    
    @Override
    public EpollDatagramChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }
    
    @Override
    public int getSendBufferSize() {
        return Native.getSendBufferSize(this.datagramChannel.fd);
    }
    
    @Override
    public EpollDatagramChannelConfig setSendBufferSize(final int n) {
        Native.setSendBufferSize(this.datagramChannel.fd, n);
        return this;
    }
    
    @Override
    public int getReceiveBufferSize() {
        return Native.getReceiveBufferSize(this.datagramChannel.fd);
    }
    
    @Override
    public EpollDatagramChannelConfig setReceiveBufferSize(final int n) {
        Native.setReceiveBufferSize(this.datagramChannel.fd, n);
        return this;
    }
    
    @Override
    public int getTrafficClass() {
        return Native.getTrafficClass(this.datagramChannel.fd);
    }
    
    @Override
    public EpollDatagramChannelConfig setTrafficClass(final int n) {
        Native.setTrafficClass(this.datagramChannel.fd, n);
        return this;
    }
    
    @Override
    public boolean isReuseAddress() {
        return Native.isReuseAddress(this.datagramChannel.fd) == 1;
    }
    
    @Override
    public EpollDatagramChannelConfig setReuseAddress(final boolean b) {
        Native.setReuseAddress(this.datagramChannel.fd, b ? 1 : 0);
        return this;
    }
    
    @Override
    public boolean isBroadcast() {
        return Native.isBroadcast(this.datagramChannel.fd) == 1;
    }
    
    @Override
    public EpollDatagramChannelConfig setBroadcast(final boolean b) {
        Native.setBroadcast(this.datagramChannel.fd, b ? 1 : 0);
        return this;
    }
    
    @Override
    public boolean isLoopbackModeDisabled() {
        return false;
    }
    
    @Override
    public DatagramChannelConfig setLoopbackModeDisabled(final boolean b) {
        throw new UnsupportedOperationException("Multicast not supported");
    }
    
    @Override
    public int getTimeToLive() {
        return -1;
    }
    
    @Override
    public EpollDatagramChannelConfig setTimeToLive(final int n) {
        throw new UnsupportedOperationException("Multicast not supported");
    }
    
    @Override
    public InetAddress getInterface() {
        return null;
    }
    
    @Override
    public EpollDatagramChannelConfig setInterface(final InetAddress inetAddress) {
        throw new UnsupportedOperationException("Multicast not supported");
    }
    
    @Override
    public NetworkInterface getNetworkInterface() {
        return null;
    }
    
    @Override
    public EpollDatagramChannelConfig setNetworkInterface(final NetworkInterface networkInterface) {
        throw new UnsupportedOperationException("Multicast not supported");
    }
    
    public boolean isReusePort() {
        return Native.isReusePort(this.datagramChannel.fd) == 1;
    }
    
    public EpollDatagramChannelConfig setReusePort(final boolean b) {
        Native.setReusePort(this.datagramChannel.fd, b ? 1 : 0);
        return this;
    }
    
    @Override
    protected void autoReadCleared() {
        this.datagramChannel.clearEpollIn();
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
    
    @Override
    public DatagramChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
        return this.setMessageSizeEstimator(messageSizeEstimator);
    }
    
    @Override
    public DatagramChannelConfig setAutoClose(final boolean autoClose) {
        return this.setAutoClose(autoClose);
    }
    
    @Override
    public DatagramChannelConfig setAutoRead(final boolean autoRead) {
        return this.setAutoRead(autoRead);
    }
    
    @Override
    public DatagramChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        return this.setRecvByteBufAllocator(recvByteBufAllocator);
    }
    
    @Override
    public DatagramChannelConfig setAllocator(final ByteBufAllocator allocator) {
        return this.setAllocator(allocator);
    }
    
    @Override
    public DatagramChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        return this.setConnectTimeoutMillis(connectTimeoutMillis);
    }
    
    @Override
    public DatagramChannelConfig setWriteSpinCount(final int writeSpinCount) {
        return this.setWriteSpinCount(writeSpinCount);
    }
    
    @Override
    public DatagramChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        return this.setMaxMessagesPerRead(maxMessagesPerRead);
    }
    
    @Override
    public DatagramChannelConfig setNetworkInterface(final NetworkInterface networkInterface) {
        return this.setNetworkInterface(networkInterface);
    }
    
    @Override
    public DatagramChannelConfig setInterface(final InetAddress interface1) {
        return this.setInterface(interface1);
    }
    
    @Override
    public DatagramChannelConfig setTimeToLive(final int timeToLive) {
        return this.setTimeToLive(timeToLive);
    }
    
    @Override
    public DatagramChannelConfig setBroadcast(final boolean broadcast) {
        return this.setBroadcast(broadcast);
    }
    
    @Override
    public DatagramChannelConfig setReuseAddress(final boolean reuseAddress) {
        return this.setReuseAddress(reuseAddress);
    }
    
    @Override
    public DatagramChannelConfig setTrafficClass(final int trafficClass) {
        return this.setTrafficClass(trafficClass);
    }
    
    @Override
    public DatagramChannelConfig setReceiveBufferSize(final int receiveBufferSize) {
        return this.setReceiveBufferSize(receiveBufferSize);
    }
    
    @Override
    public DatagramChannelConfig setSendBufferSize(final int sendBufferSize) {
        return this.setSendBufferSize(sendBufferSize);
    }
    
    static {
        DEFAULT_RCVBUF_ALLOCATOR = new FixedRecvByteBufAllocator(2048);
    }
}
