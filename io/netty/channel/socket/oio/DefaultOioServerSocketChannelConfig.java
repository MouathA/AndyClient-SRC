package io.netty.channel.socket.oio;

import java.net.*;
import java.util.*;
import io.netty.buffer.*;
import io.netty.channel.socket.*;
import io.netty.channel.*;

public class DefaultOioServerSocketChannelConfig extends DefaultServerSocketChannelConfig implements OioServerSocketChannelConfig
{
    @Deprecated
    public DefaultOioServerSocketChannelConfig(final ServerSocketChannel serverSocketChannel, final ServerSocket serverSocket) {
        super(serverSocketChannel, serverSocket);
    }
    
    DefaultOioServerSocketChannelConfig(final OioServerSocketChannel oioServerSocketChannel, final ServerSocket serverSocket) {
        super(oioServerSocketChannel, serverSocket);
    }
    
    @Override
    public Map getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_TIMEOUT);
    }
    
    @Override
    public Object getOption(final ChannelOption channelOption) {
        if (channelOption == ChannelOption.SO_TIMEOUT) {
            return this.getSoTimeout();
        }
        return super.getOption(channelOption);
    }
    
    @Override
    public boolean setOption(final ChannelOption channelOption, final Object o) {
        this.validate(channelOption, o);
        if (channelOption == ChannelOption.SO_TIMEOUT) {
            this.setSoTimeout((int)o);
            return true;
        }
        return super.setOption(channelOption, o);
    }
    
    @Override
    public OioServerSocketChannelConfig setSoTimeout(final int soTimeout) {
        this.javaSocket.setSoTimeout(soTimeout);
        return this;
    }
    
    @Override
    public int getSoTimeout() {
        return this.javaSocket.getSoTimeout();
    }
    
    @Override
    public OioServerSocketChannelConfig setBacklog(final int backlog) {
        super.setBacklog(backlog);
        return this;
    }
    
    @Override
    public OioServerSocketChannelConfig setReuseAddress(final boolean reuseAddress) {
        super.setReuseAddress(reuseAddress);
        return this;
    }
    
    @Override
    public OioServerSocketChannelConfig setReceiveBufferSize(final int receiveBufferSize) {
        super.setReceiveBufferSize(receiveBufferSize);
        return this;
    }
    
    @Override
    public OioServerSocketChannelConfig setPerformancePreferences(final int n, final int n2, final int n3) {
        super.setPerformancePreferences(n, n2, n3);
        return this;
    }
    
    @Override
    public OioServerSocketChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }
    
    @Override
    public OioServerSocketChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }
    
    @Override
    public OioServerSocketChannelConfig setWriteSpinCount(final int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }
    
    @Override
    public OioServerSocketChannelConfig setAllocator(final ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }
    
    @Override
    public OioServerSocketChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }
    
    @Override
    public OioServerSocketChannelConfig setAutoRead(final boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }
    
    @Override
    protected void autoReadCleared() {
        if (this.channel instanceof OioServerSocketChannel) {
            ((OioServerSocketChannel)this.channel).setReadPending(false);
        }
    }
    
    @Override
    public OioServerSocketChannelConfig setAutoClose(final boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }
    
    @Override
    public OioServerSocketChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }
    
    @Override
    public OioServerSocketChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }
    
    @Override
    public OioServerSocketChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
    
    @Override
    public ServerSocketChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
        return this.setMessageSizeEstimator(messageSizeEstimator);
    }
    
    @Override
    public ServerSocketChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        return this.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
    }
    
    @Override
    public ServerSocketChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        return this.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
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
    public ServerSocketChannelConfig setBacklog(final int backlog) {
        return this.setBacklog(backlog);
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
}
