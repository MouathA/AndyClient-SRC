package io.netty.channel.udt;

import com.barchart.udt.nio.*;
import java.io.*;
import java.util.*;
import io.netty.buffer.*;
import io.netty.channel.*;

public class DefaultUdtServerChannelConfig extends DefaultUdtChannelConfig implements UdtServerChannelConfig
{
    private int backlog;
    
    public DefaultUdtServerChannelConfig(final UdtChannel udtChannel, final ChannelUDT channelUDT, final boolean b) throws IOException {
        super(udtChannel, channelUDT, b);
        this.backlog = 64;
        if (b) {
            this.apply(channelUDT);
        }
    }
    
    @Override
    protected void apply(final ChannelUDT channelUDT) throws IOException {
    }
    
    @Override
    public int getBacklog() {
        return this.backlog;
    }
    
    @Override
    public Object getOption(final ChannelOption channelOption) {
        if (channelOption == ChannelOption.SO_BACKLOG) {
            return this.getBacklog();
        }
        return super.getOption(channelOption);
    }
    
    @Override
    public Map getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_BACKLOG);
    }
    
    @Override
    public UdtServerChannelConfig setBacklog(final int backlog) {
        this.backlog = backlog;
        return this;
    }
    
    @Override
    public boolean setOption(final ChannelOption channelOption, final Object o) {
        this.validate(channelOption, o);
        if (channelOption == ChannelOption.SO_BACKLOG) {
            this.setBacklog((int)o);
            return true;
        }
        return super.setOption(channelOption, o);
    }
    
    @Override
    public UdtServerChannelConfig setProtocolReceiveBufferSize(final int protocolReceiveBufferSize) {
        super.setProtocolReceiveBufferSize(protocolReceiveBufferSize);
        return this;
    }
    
    @Override
    public UdtServerChannelConfig setProtocolSendBufferSize(final int protocolSendBufferSize) {
        super.setProtocolSendBufferSize(protocolSendBufferSize);
        return this;
    }
    
    @Override
    public UdtServerChannelConfig setReceiveBufferSize(final int receiveBufferSize) {
        super.setReceiveBufferSize(receiveBufferSize);
        return this;
    }
    
    @Override
    public UdtServerChannelConfig setReuseAddress(final boolean reuseAddress) {
        super.setReuseAddress(reuseAddress);
        return this;
    }
    
    @Override
    public UdtServerChannelConfig setSendBufferSize(final int sendBufferSize) {
        super.setSendBufferSize(sendBufferSize);
        return this;
    }
    
    @Override
    public UdtServerChannelConfig setSoLinger(final int soLinger) {
        super.setSoLinger(soLinger);
        return this;
    }
    
    @Override
    public UdtServerChannelConfig setSystemReceiveBufferSize(final int systemReceiveBufferSize) {
        super.setSystemReceiveBufferSize(systemReceiveBufferSize);
        return this;
    }
    
    @Override
    public UdtServerChannelConfig setSystemSendBufferSize(final int systemSendBufferSize) {
        super.setSystemSendBufferSize(systemSendBufferSize);
        return this;
    }
    
    @Override
    public UdtServerChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }
    
    @Override
    public UdtServerChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }
    
    @Override
    public UdtServerChannelConfig setWriteSpinCount(final int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }
    
    @Override
    public UdtServerChannelConfig setAllocator(final ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }
    
    @Override
    public UdtServerChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }
    
    @Override
    public UdtServerChannelConfig setAutoRead(final boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }
    
    @Override
    public UdtServerChannelConfig setAutoClose(final boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }
    
    @Override
    public UdtServerChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }
    
    @Override
    public UdtServerChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }
    
    @Override
    public UdtServerChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
    
    @Override
    public UdtChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
        return this.setMessageSizeEstimator(messageSizeEstimator);
    }
    
    @Override
    public UdtChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        return this.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
    }
    
    @Override
    public UdtChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        return this.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
    }
    
    @Override
    public UdtChannelConfig setAutoClose(final boolean autoClose) {
        return this.setAutoClose(autoClose);
    }
    
    @Override
    public UdtChannelConfig setAutoRead(final boolean autoRead) {
        return this.setAutoRead(autoRead);
    }
    
    @Override
    public UdtChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        return this.setRecvByteBufAllocator(recvByteBufAllocator);
    }
    
    @Override
    public UdtChannelConfig setAllocator(final ByteBufAllocator allocator) {
        return this.setAllocator(allocator);
    }
    
    @Override
    public UdtChannelConfig setWriteSpinCount(final int writeSpinCount) {
        return this.setWriteSpinCount(writeSpinCount);
    }
    
    @Override
    public UdtChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        return this.setMaxMessagesPerRead(maxMessagesPerRead);
    }
    
    @Override
    public UdtChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        return this.setConnectTimeoutMillis(connectTimeoutMillis);
    }
    
    @Override
    public UdtChannelConfig setSystemReceiveBufferSize(final int systemReceiveBufferSize) {
        return this.setSystemReceiveBufferSize(systemReceiveBufferSize);
    }
    
    @Override
    public UdtChannelConfig setProtocolSendBufferSize(final int protocolSendBufferSize) {
        return this.setProtocolSendBufferSize(protocolSendBufferSize);
    }
    
    @Override
    public UdtChannelConfig setSystemSendBufferSize(final int systemSendBufferSize) {
        return this.setSystemSendBufferSize(systemSendBufferSize);
    }
    
    @Override
    public UdtChannelConfig setSoLinger(final int soLinger) {
        return this.setSoLinger(soLinger);
    }
    
    @Override
    public UdtChannelConfig setSendBufferSize(final int sendBufferSize) {
        return this.setSendBufferSize(sendBufferSize);
    }
    
    @Override
    public UdtChannelConfig setReuseAddress(final boolean reuseAddress) {
        return this.setReuseAddress(reuseAddress);
    }
    
    @Override
    public UdtChannelConfig setReceiveBufferSize(final int receiveBufferSize) {
        return this.setReceiveBufferSize(receiveBufferSize);
    }
    
    @Override
    public UdtChannelConfig setProtocolReceiveBufferSize(final int protocolReceiveBufferSize) {
        return this.setProtocolReceiveBufferSize(protocolReceiveBufferSize);
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
