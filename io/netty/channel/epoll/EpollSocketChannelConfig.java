package io.netty.channel.epoll;

import io.netty.channel.socket.*;
import io.netty.util.internal.*;
import java.util.*;
import io.netty.buffer.*;
import io.netty.channel.*;

public final class EpollSocketChannelConfig extends DefaultChannelConfig implements SocketChannelConfig
{
    private final EpollSocketChannel channel;
    private boolean allowHalfClosure;
    
    EpollSocketChannelConfig(final EpollSocketChannel channel) {
        super(channel);
        this.channel = channel;
        if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
            this.setTcpNoDelay(true);
        }
    }
    
    @Override
    public Map getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.TCP_NODELAY, ChannelOption.SO_KEEPALIVE, ChannelOption.SO_REUSEADDR, ChannelOption.SO_LINGER, ChannelOption.IP_TOS, ChannelOption.ALLOW_HALF_CLOSURE, EpollChannelOption.TCP_CORK, EpollChannelOption.TCP_KEEPCNT, EpollChannelOption.TCP_KEEPIDLE, EpollChannelOption.TCP_KEEPINTVL);
    }
    
    @Override
    public Object getOption(final ChannelOption channelOption) {
        if (channelOption == ChannelOption.SO_RCVBUF) {
            return this.getReceiveBufferSize();
        }
        if (channelOption == ChannelOption.SO_SNDBUF) {
            return this.getSendBufferSize();
        }
        if (channelOption == ChannelOption.TCP_NODELAY) {
            return this.isTcpNoDelay();
        }
        if (channelOption == ChannelOption.SO_KEEPALIVE) {
            return this.isKeepAlive();
        }
        if (channelOption == ChannelOption.SO_REUSEADDR) {
            return this.isReuseAddress();
        }
        if (channelOption == ChannelOption.SO_LINGER) {
            return this.getSoLinger();
        }
        if (channelOption == ChannelOption.IP_TOS) {
            return this.getTrafficClass();
        }
        if (channelOption == ChannelOption.ALLOW_HALF_CLOSURE) {
            return this.isAllowHalfClosure();
        }
        if (channelOption == EpollChannelOption.TCP_CORK) {
            return this.isTcpCork();
        }
        if (channelOption == EpollChannelOption.TCP_KEEPIDLE) {
            return this.getTcpKeepIdle();
        }
        if (channelOption == EpollChannelOption.TCP_KEEPINTVL) {
            return this.getTcpKeepIntvl();
        }
        if (channelOption == EpollChannelOption.TCP_KEEPCNT) {
            return this.getTcpKeepCnt();
        }
        return super.getOption(channelOption);
    }
    
    @Override
    public boolean setOption(final ChannelOption channelOption, final Object o) {
        this.validate(channelOption, o);
        if (channelOption == ChannelOption.SO_RCVBUF) {
            this.setReceiveBufferSize((int)o);
        }
        else if (channelOption == ChannelOption.SO_SNDBUF) {
            this.setSendBufferSize((int)o);
        }
        else if (channelOption == ChannelOption.TCP_NODELAY) {
            this.setTcpNoDelay((boolean)o);
        }
        else if (channelOption == ChannelOption.SO_KEEPALIVE) {
            this.setKeepAlive((boolean)o);
        }
        else if (channelOption == ChannelOption.SO_REUSEADDR) {
            this.setReuseAddress((boolean)o);
        }
        else if (channelOption == ChannelOption.SO_LINGER) {
            this.setSoLinger((int)o);
        }
        else if (channelOption == ChannelOption.IP_TOS) {
            this.setTrafficClass((int)o);
        }
        else if (channelOption == ChannelOption.ALLOW_HALF_CLOSURE) {
            this.setAllowHalfClosure((boolean)o);
        }
        else if (channelOption == EpollChannelOption.TCP_CORK) {
            this.setTcpCork((boolean)o);
        }
        else if (channelOption == EpollChannelOption.TCP_KEEPIDLE) {
            this.setTcpKeepIdle((int)o);
        }
        else if (channelOption == EpollChannelOption.TCP_KEEPCNT) {
            this.setTcpKeepCntl((int)o);
        }
        else {
            if (channelOption != EpollChannelOption.TCP_KEEPINTVL) {
                return super.setOption(channelOption, o);
            }
            this.setTcpKeepIntvl((int)o);
        }
        return true;
    }
    
    @Override
    public int getReceiveBufferSize() {
        return Native.getReceiveBufferSize(this.channel.fd);
    }
    
    @Override
    public int getSendBufferSize() {
        return Native.getSendBufferSize(this.channel.fd);
    }
    
    @Override
    public int getSoLinger() {
        return Native.getSoLinger(this.channel.fd);
    }
    
    @Override
    public int getTrafficClass() {
        return Native.getTrafficClass(this.channel.fd);
    }
    
    @Override
    public boolean isKeepAlive() {
        return Native.isKeepAlive(this.channel.fd) == 1;
    }
    
    @Override
    public boolean isReuseAddress() {
        return Native.isReuseAddress(this.channel.fd) == 1;
    }
    
    @Override
    public boolean isTcpNoDelay() {
        return Native.isTcpNoDelay(this.channel.fd) == 1;
    }
    
    public boolean isTcpCork() {
        return Native.isTcpCork(this.channel.fd) == 1;
    }
    
    public int getTcpKeepIdle() {
        return Native.getTcpKeepIdle(this.channel.fd);
    }
    
    public int getTcpKeepIntvl() {
        return Native.getTcpKeepIntvl(this.channel.fd);
    }
    
    public int getTcpKeepCnt() {
        return Native.getTcpKeepCnt(this.channel.fd);
    }
    
    @Override
    public EpollSocketChannelConfig setKeepAlive(final boolean b) {
        Native.setKeepAlive(this.channel.fd, b ? 1 : 0);
        return this;
    }
    
    @Override
    public EpollSocketChannelConfig setPerformancePreferences(final int n, final int n2, final int n3) {
        return this;
    }
    
    @Override
    public EpollSocketChannelConfig setReceiveBufferSize(final int n) {
        Native.setReceiveBufferSize(this.channel.fd, n);
        return this;
    }
    
    @Override
    public EpollSocketChannelConfig setReuseAddress(final boolean b) {
        Native.setReuseAddress(this.channel.fd, b ? 1 : 0);
        return this;
    }
    
    @Override
    public EpollSocketChannelConfig setSendBufferSize(final int n) {
        Native.setSendBufferSize(this.channel.fd, n);
        return this;
    }
    
    @Override
    public EpollSocketChannelConfig setSoLinger(final int n) {
        Native.setSoLinger(this.channel.fd, n);
        return this;
    }
    
    @Override
    public EpollSocketChannelConfig setTcpNoDelay(final boolean b) {
        Native.setTcpNoDelay(this.channel.fd, b ? 1 : 0);
        return this;
    }
    
    public EpollSocketChannelConfig setTcpCork(final boolean b) {
        Native.setTcpCork(this.channel.fd, b ? 1 : 0);
        return this;
    }
    
    @Override
    public EpollSocketChannelConfig setTrafficClass(final int n) {
        Native.setTrafficClass(this.channel.fd, n);
        return this;
    }
    
    public EpollSocketChannelConfig setTcpKeepIdle(final int n) {
        Native.setTcpKeepIdle(this.channel.fd, n);
        return this;
    }
    
    public EpollSocketChannelConfig setTcpKeepIntvl(final int n) {
        Native.setTcpKeepIntvl(this.channel.fd, n);
        return this;
    }
    
    public EpollSocketChannelConfig setTcpKeepCntl(final int n) {
        Native.setTcpKeepCnt(this.channel.fd, n);
        return this;
    }
    
    @Override
    public boolean isAllowHalfClosure() {
        return this.allowHalfClosure;
    }
    
    @Override
    public EpollSocketChannelConfig setAllowHalfClosure(final boolean allowHalfClosure) {
        this.allowHalfClosure = allowHalfClosure;
        return this;
    }
    
    @Override
    public EpollSocketChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }
    
    @Override
    public EpollSocketChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }
    
    @Override
    public EpollSocketChannelConfig setWriteSpinCount(final int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }
    
    @Override
    public EpollSocketChannelConfig setAllocator(final ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }
    
    @Override
    public EpollSocketChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }
    
    @Override
    public EpollSocketChannelConfig setAutoRead(final boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }
    
    @Override
    public EpollSocketChannelConfig setAutoClose(final boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }
    
    @Override
    public EpollSocketChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }
    
    @Override
    public EpollSocketChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }
    
    @Override
    public EpollSocketChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
    
    @Override
    protected void autoReadCleared() {
        this.channel.clearEpollIn();
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
    public SocketChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
        return this.setMessageSizeEstimator(messageSizeEstimator);
    }
    
    @Override
    public SocketChannelConfig setAutoClose(final boolean autoClose) {
        return this.setAutoClose(autoClose);
    }
    
    @Override
    public SocketChannelConfig setAutoRead(final boolean autoRead) {
        return this.setAutoRead(autoRead);
    }
    
    @Override
    public SocketChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        return this.setRecvByteBufAllocator(recvByteBufAllocator);
    }
    
    @Override
    public SocketChannelConfig setAllocator(final ByteBufAllocator allocator) {
        return this.setAllocator(allocator);
    }
    
    @Override
    public SocketChannelConfig setWriteSpinCount(final int writeSpinCount) {
        return this.setWriteSpinCount(writeSpinCount);
    }
    
    @Override
    public SocketChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        return this.setMaxMessagesPerRead(maxMessagesPerRead);
    }
    
    @Override
    public SocketChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        return this.setConnectTimeoutMillis(connectTimeoutMillis);
    }
    
    @Override
    public SocketChannelConfig setAllowHalfClosure(final boolean allowHalfClosure) {
        return this.setAllowHalfClosure(allowHalfClosure);
    }
    
    @Override
    public SocketChannelConfig setPerformancePreferences(final int n, final int n2, final int n3) {
        return this.setPerformancePreferences(n, n2, n3);
    }
    
    @Override
    public SocketChannelConfig setReuseAddress(final boolean reuseAddress) {
        return this.setReuseAddress(reuseAddress);
    }
    
    @Override
    public SocketChannelConfig setTrafficClass(final int trafficClass) {
        return this.setTrafficClass(trafficClass);
    }
    
    @Override
    public SocketChannelConfig setKeepAlive(final boolean keepAlive) {
        return this.setKeepAlive(keepAlive);
    }
    
    @Override
    public SocketChannelConfig setReceiveBufferSize(final int receiveBufferSize) {
        return this.setReceiveBufferSize(receiveBufferSize);
    }
    
    @Override
    public SocketChannelConfig setSendBufferSize(final int sendBufferSize) {
        return this.setSendBufferSize(sendBufferSize);
    }
    
    @Override
    public SocketChannelConfig setSoLinger(final int soLinger) {
        return this.setSoLinger(soLinger);
    }
    
    @Override
    public SocketChannelConfig setTcpNoDelay(final boolean tcpNoDelay) {
        return this.setTcpNoDelay(tcpNoDelay);
    }
}
