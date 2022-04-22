package io.netty.channel.socket;

import java.net.*;
import io.netty.util.internal.*;
import java.util.*;
import io.netty.buffer.*;
import io.netty.channel.*;

public class DefaultSocketChannelConfig extends DefaultChannelConfig implements SocketChannelConfig
{
    protected final Socket javaSocket;
    private boolean allowHalfClosure;
    
    public DefaultSocketChannelConfig(final SocketChannel socketChannel, final Socket javaSocket) {
        super(socketChannel);
        if (javaSocket == null) {
            throw new NullPointerException("javaSocket");
        }
        this.javaSocket = javaSocket;
        if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
            this.setTcpNoDelay(true);
        }
    }
    
    @Override
    public Map getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.TCP_NODELAY, ChannelOption.SO_KEEPALIVE, ChannelOption.SO_REUSEADDR, ChannelOption.SO_LINGER, ChannelOption.IP_TOS, ChannelOption.ALLOW_HALF_CLOSURE);
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
        else {
            if (channelOption != ChannelOption.ALLOW_HALF_CLOSURE) {
                return super.setOption(channelOption, o);
            }
            this.setAllowHalfClosure((boolean)o);
        }
        return true;
    }
    
    @Override
    public int getReceiveBufferSize() {
        return this.javaSocket.getReceiveBufferSize();
    }
    
    @Override
    public int getSendBufferSize() {
        return this.javaSocket.getSendBufferSize();
    }
    
    @Override
    public int getSoLinger() {
        return this.javaSocket.getSoLinger();
    }
    
    @Override
    public int getTrafficClass() {
        return this.javaSocket.getTrafficClass();
    }
    
    @Override
    public boolean isKeepAlive() {
        return this.javaSocket.getKeepAlive();
    }
    
    @Override
    public boolean isReuseAddress() {
        return this.javaSocket.getReuseAddress();
    }
    
    @Override
    public boolean isTcpNoDelay() {
        return this.javaSocket.getTcpNoDelay();
    }
    
    @Override
    public SocketChannelConfig setKeepAlive(final boolean keepAlive) {
        this.javaSocket.setKeepAlive(keepAlive);
        return this;
    }
    
    @Override
    public SocketChannelConfig setPerformancePreferences(final int n, final int n2, final int n3) {
        this.javaSocket.setPerformancePreferences(n, n2, n3);
        return this;
    }
    
    @Override
    public SocketChannelConfig setReceiveBufferSize(final int receiveBufferSize) {
        this.javaSocket.setReceiveBufferSize(receiveBufferSize);
        return this;
    }
    
    @Override
    public SocketChannelConfig setReuseAddress(final boolean reuseAddress) {
        this.javaSocket.setReuseAddress(reuseAddress);
        return this;
    }
    
    @Override
    public SocketChannelConfig setSendBufferSize(final int sendBufferSize) {
        this.javaSocket.setSendBufferSize(sendBufferSize);
        return this;
    }
    
    @Override
    public SocketChannelConfig setSoLinger(final int n) {
        if (n < 0) {
            this.javaSocket.setSoLinger(false, 0);
        }
        else {
            this.javaSocket.setSoLinger(true, n);
        }
        return this;
    }
    
    @Override
    public SocketChannelConfig setTcpNoDelay(final boolean tcpNoDelay) {
        this.javaSocket.setTcpNoDelay(tcpNoDelay);
        return this;
    }
    
    @Override
    public SocketChannelConfig setTrafficClass(final int trafficClass) {
        this.javaSocket.setTrafficClass(trafficClass);
        return this;
    }
    
    @Override
    public boolean isAllowHalfClosure() {
        return this.allowHalfClosure;
    }
    
    @Override
    public SocketChannelConfig setAllowHalfClosure(final boolean allowHalfClosure) {
        this.allowHalfClosure = allowHalfClosure;
        return this;
    }
    
    @Override
    public SocketChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }
    
    @Override
    public SocketChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }
    
    @Override
    public SocketChannelConfig setWriteSpinCount(final int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }
    
    @Override
    public SocketChannelConfig setAllocator(final ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }
    
    @Override
    public SocketChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }
    
    @Override
    public SocketChannelConfig setAutoRead(final boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }
    
    @Override
    public SocketChannelConfig setAutoClose(final boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }
    
    @Override
    public SocketChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }
    
    @Override
    public SocketChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }
    
    @Override
    public SocketChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
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
}
