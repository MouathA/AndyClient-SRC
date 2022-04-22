package io.netty.channel.epoll;

import io.netty.channel.socket.*;
import io.netty.util.*;
import java.util.*;
import io.netty.buffer.*;
import io.netty.channel.*;

public final class EpollServerSocketChannelConfig extends DefaultChannelConfig implements ServerSocketChannelConfig
{
    private final EpollServerSocketChannel channel;
    private int backlog;
    
    EpollServerSocketChannelConfig(final EpollServerSocketChannel channel) {
        super(channel);
        this.backlog = NetUtil.SOMAXCONN;
        this.channel = channel;
        this.setReuseAddress(true);
    }
    
    @Override
    public Map getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_REUSEADDR, ChannelOption.SO_BACKLOG, EpollChannelOption.SO_REUSEPORT);
    }
    
    @Override
    public Object getOption(final ChannelOption channelOption) {
        if (channelOption == ChannelOption.SO_RCVBUF) {
            return this.getReceiveBufferSize();
        }
        if (channelOption == ChannelOption.SO_REUSEADDR) {
            return this.isReuseAddress();
        }
        if (channelOption == ChannelOption.SO_BACKLOG) {
            return this.getBacklog();
        }
        if (channelOption == EpollChannelOption.SO_REUSEPORT) {
            return this.isReusePort();
        }
        return super.getOption(channelOption);
    }
    
    @Override
    public boolean setOption(final ChannelOption channelOption, final Object o) {
        this.validate(channelOption, o);
        if (channelOption == ChannelOption.SO_RCVBUF) {
            this.setReceiveBufferSize((int)o);
        }
        else if (channelOption == ChannelOption.SO_REUSEADDR) {
            this.setReuseAddress((boolean)o);
        }
        else if (channelOption == ChannelOption.SO_BACKLOG) {
            this.setBacklog((int)o);
        }
        else {
            if (channelOption != EpollChannelOption.SO_REUSEPORT) {
                return super.setOption(channelOption, o);
            }
            this.setReusePort((boolean)o);
        }
        return true;
    }
    
    @Override
    public boolean isReuseAddress() {
        return Native.isReuseAddress(this.channel.fd) == 1;
    }
    
    @Override
    public EpollServerSocketChannelConfig setReuseAddress(final boolean b) {
        Native.setReuseAddress(this.channel.fd, b ? 1 : 0);
        return this;
    }
    
    @Override
    public int getReceiveBufferSize() {
        return Native.getReceiveBufferSize(this.channel.fd);
    }
    
    @Override
    public EpollServerSocketChannelConfig setReceiveBufferSize(final int n) {
        Native.setReceiveBufferSize(this.channel.fd, n);
        return this;
    }
    
    @Override
    public EpollServerSocketChannelConfig setPerformancePreferences(final int n, final int n2, final int n3) {
        return this;
    }
    
    @Override
    public int getBacklog() {
        return this.backlog;
    }
    
    @Override
    public EpollServerSocketChannelConfig setBacklog(final int backlog) {
        if (backlog < 0) {
            throw new IllegalArgumentException("backlog: " + backlog);
        }
        this.backlog = backlog;
        return this;
    }
    
    @Override
    public EpollServerSocketChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }
    
    @Override
    public EpollServerSocketChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }
    
    @Override
    public EpollServerSocketChannelConfig setWriteSpinCount(final int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }
    
    @Override
    public EpollServerSocketChannelConfig setAllocator(final ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }
    
    @Override
    public EpollServerSocketChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }
    
    @Override
    public EpollServerSocketChannelConfig setAutoRead(final boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }
    
    @Override
    public EpollServerSocketChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }
    
    @Override
    public EpollServerSocketChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }
    
    @Override
    public EpollServerSocketChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
    
    public boolean isReusePort() {
        return Native.isReusePort(this.channel.fd) == 1;
    }
    
    public EpollServerSocketChannelConfig setReusePort(final boolean b) {
        Native.setReusePort(this.channel.fd, b ? 1 : 0);
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
    public ServerSocketChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
        return this.setMessageSizeEstimator(messageSizeEstimator);
    }
    
    @Override
    public ServerSocketChannelConfig setAutoRead(final boolean autoRead) {
        return this.setAutoRead(autoRead);
    }
    
    @Override
    public ServerSocketChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        return this.setRecvByteBufAllocator(recvByteBufAllocator);
    }
    
    @Override
    public ServerSocketChannelConfig setAllocator(final ByteBufAllocator allocator) {
        return this.setAllocator(allocator);
    }
    
    @Override
    public ServerSocketChannelConfig setWriteSpinCount(final int writeSpinCount) {
        return this.setWriteSpinCount(writeSpinCount);
    }
    
    @Override
    public ServerSocketChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        return this.setMaxMessagesPerRead(maxMessagesPerRead);
    }
    
    @Override
    public ServerSocketChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        return this.setConnectTimeoutMillis(connectTimeoutMillis);
    }
    
    @Override
    public ServerSocketChannelConfig setPerformancePreferences(final int n, final int n2, final int n3) {
        return this.setPerformancePreferences(n, n2, n3);
    }
    
    @Override
    public ServerSocketChannelConfig setReceiveBufferSize(final int receiveBufferSize) {
        return this.setReceiveBufferSize(receiveBufferSize);
    }
    
    @Override
    public ServerSocketChannelConfig setReuseAddress(final boolean reuseAddress) {
        return this.setReuseAddress(reuseAddress);
    }
    
    @Override
    public ServerSocketChannelConfig setBacklog(final int backlog) {
        return this.setBacklog(backlog);
    }
}
