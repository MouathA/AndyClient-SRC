package io.netty.channel.socket.oio;

import java.net.*;
import java.util.*;
import io.netty.buffer.*;
import io.netty.channel.socket.*;
import io.netty.channel.*;

public class DefaultOioSocketChannelConfig extends DefaultSocketChannelConfig implements OioSocketChannelConfig
{
    @Deprecated
    public DefaultOioSocketChannelConfig(final SocketChannel socketChannel, final Socket socket) {
        super(socketChannel, socket);
    }
    
    DefaultOioSocketChannelConfig(final OioSocketChannel oioSocketChannel, final Socket socket) {
        super(oioSocketChannel, socket);
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
    public OioSocketChannelConfig setSoTimeout(final int soTimeout) {
        this.javaSocket.setSoTimeout(soTimeout);
        return this;
    }
    
    @Override
    public int getSoTimeout() {
        return this.javaSocket.getSoTimeout();
    }
    
    @Override
    public OioSocketChannelConfig setTcpNoDelay(final boolean tcpNoDelay) {
        super.setTcpNoDelay(tcpNoDelay);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setSoLinger(final int soLinger) {
        super.setSoLinger(soLinger);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setSendBufferSize(final int sendBufferSize) {
        super.setSendBufferSize(sendBufferSize);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setReceiveBufferSize(final int receiveBufferSize) {
        super.setReceiveBufferSize(receiveBufferSize);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setKeepAlive(final boolean keepAlive) {
        super.setKeepAlive(keepAlive);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setTrafficClass(final int trafficClass) {
        super.setTrafficClass(trafficClass);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setReuseAddress(final boolean reuseAddress) {
        super.setReuseAddress(reuseAddress);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setPerformancePreferences(final int n, final int n2, final int n3) {
        super.setPerformancePreferences(n, n2, n3);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setAllowHalfClosure(final boolean allowHalfClosure) {
        super.setAllowHalfClosure(allowHalfClosure);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setWriteSpinCount(final int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setAllocator(final ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setAutoRead(final boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }
    
    @Override
    protected void autoReadCleared() {
        if (this.channel instanceof OioSocketChannel) {
            ((OioSocketChannel)this.channel).setReadPending(false);
        }
    }
    
    @Override
    public OioSocketChannelConfig setAutoClose(final boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
    
    @Override
    public SocketChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
        return this.setMessageSizeEstimator(messageSizeEstimator);
    }
    
    @Override
    public SocketChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        return this.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
    }
    
    @Override
    public SocketChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        return this.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
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
    public SocketChannelConfig setTrafficClass(final int trafficClass) {
        return this.setTrafficClass(trafficClass);
    }
    
    @Override
    public SocketChannelConfig setTcpNoDelay(final boolean tcpNoDelay) {
        return this.setTcpNoDelay(tcpNoDelay);
    }
    
    @Override
    public SocketChannelConfig setSoLinger(final int soLinger) {
        return this.setSoLinger(soLinger);
    }
    
    @Override
    public SocketChannelConfig setSendBufferSize(final int sendBufferSize) {
        return this.setSendBufferSize(sendBufferSize);
    }
    
    @Override
    public SocketChannelConfig setReuseAddress(final boolean reuseAddress) {
        return this.setReuseAddress(reuseAddress);
    }
    
    @Override
    public SocketChannelConfig setReceiveBufferSize(final int receiveBufferSize) {
        return this.setReceiveBufferSize(receiveBufferSize);
    }
    
    @Override
    public SocketChannelConfig setPerformancePreferences(final int n, final int n2, final int n3) {
        return this.setPerformancePreferences(n, n2, n3);
    }
    
    @Override
    public SocketChannelConfig setKeepAlive(final boolean keepAlive) {
        return this.setKeepAlive(keepAlive);
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
