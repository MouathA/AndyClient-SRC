package io.netty.channel.socket;

import java.net.*;
import io.netty.util.*;
import java.util.*;
import io.netty.buffer.*;
import io.netty.channel.*;

public class DefaultServerSocketChannelConfig extends DefaultChannelConfig implements ServerSocketChannelConfig
{
    protected final ServerSocket javaSocket;
    private int backlog;
    
    public DefaultServerSocketChannelConfig(final ServerSocketChannel serverSocketChannel, final ServerSocket javaSocket) {
        super(serverSocketChannel);
        this.backlog = NetUtil.SOMAXCONN;
        if (javaSocket == null) {
            throw new NullPointerException("javaSocket");
        }
        this.javaSocket = javaSocket;
    }
    
    @Override
    public Map getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_REUSEADDR, ChannelOption.SO_BACKLOG);
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
        else {
            if (channelOption != ChannelOption.SO_BACKLOG) {
                return super.setOption(channelOption, o);
            }
            this.setBacklog((int)o);
        }
        return true;
    }
    
    @Override
    public boolean isReuseAddress() {
        return this.javaSocket.getReuseAddress();
    }
    
    @Override
    public ServerSocketChannelConfig setReuseAddress(final boolean reuseAddress) {
        this.javaSocket.setReuseAddress(reuseAddress);
        return this;
    }
    
    @Override
    public int getReceiveBufferSize() {
        return this.javaSocket.getReceiveBufferSize();
    }
    
    @Override
    public ServerSocketChannelConfig setReceiveBufferSize(final int receiveBufferSize) {
        this.javaSocket.setReceiveBufferSize(receiveBufferSize);
        return this;
    }
    
    @Override
    public ServerSocketChannelConfig setPerformancePreferences(final int n, final int n2, final int n3) {
        this.javaSocket.setPerformancePreferences(n, n2, n3);
        return this;
    }
    
    @Override
    public int getBacklog() {
        return this.backlog;
    }
    
    @Override
    public ServerSocketChannelConfig setBacklog(final int backlog) {
        if (backlog < 0) {
            throw new IllegalArgumentException("backlog: " + backlog);
        }
        this.backlog = backlog;
        return this;
    }
    
    @Override
    public ServerSocketChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }
    
    @Override
    public ServerSocketChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }
    
    @Override
    public ServerSocketChannelConfig setWriteSpinCount(final int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }
    
    @Override
    public ServerSocketChannelConfig setAllocator(final ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }
    
    @Override
    public ServerSocketChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }
    
    @Override
    public ServerSocketChannelConfig setAutoRead(final boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }
    
    @Override
    public ServerSocketChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }
    
    @Override
    public ServerSocketChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }
    
    @Override
    public ServerSocketChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
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
}
