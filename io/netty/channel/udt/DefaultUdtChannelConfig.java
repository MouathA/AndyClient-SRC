package io.netty.channel.udt;

import com.barchart.udt.nio.*;
import java.io.*;
import com.barchart.udt.*;
import java.util.*;
import io.netty.buffer.*;
import io.netty.channel.*;

public class DefaultUdtChannelConfig extends DefaultChannelConfig implements UdtChannelConfig
{
    private static final int K = 1024;
    private static final int M = 1048576;
    private int protocolReceiveBuferSize;
    private int protocolSendBuferSize;
    private int systemReceiveBufferSize;
    private int systemSendBuferSize;
    private int allocatorReceiveBufferSize;
    private int allocatorSendBufferSize;
    private int soLinger;
    private boolean reuseAddress;
    
    public DefaultUdtChannelConfig(final UdtChannel udtChannel, final ChannelUDT channelUDT, final boolean b) throws IOException {
        super(udtChannel);
        this.protocolReceiveBuferSize = 10485760;
        this.protocolSendBuferSize = 10485760;
        this.systemReceiveBufferSize = 1048576;
        this.systemSendBuferSize = 1048576;
        this.allocatorReceiveBufferSize = 131072;
        this.allocatorSendBufferSize = 131072;
        this.reuseAddress = true;
        if (b) {
            this.apply(channelUDT);
        }
    }
    
    protected void apply(final ChannelUDT channelUDT) throws IOException {
        final SocketUDT socketUDT = channelUDT.socketUDT();
        socketUDT.setReuseAddress(this.isReuseAddress());
        socketUDT.setSendBufferSize(this.getSendBufferSize());
        if (this.getSoLinger() <= 0) {
            socketUDT.setSoLinger(false, 0);
        }
        else {
            socketUDT.setSoLinger(true, this.getSoLinger());
        }
        socketUDT.setOption(OptionUDT.Protocol_Receive_Buffer_Size, (Object)this.getProtocolReceiveBufferSize());
        socketUDT.setOption(OptionUDT.Protocol_Send_Buffer_Size, (Object)this.getProtocolSendBufferSize());
        socketUDT.setOption(OptionUDT.System_Receive_Buffer_Size, (Object)this.getSystemReceiveBufferSize());
        socketUDT.setOption(OptionUDT.System_Send_Buffer_Size, (Object)this.getSystemSendBufferSize());
    }
    
    @Override
    public int getProtocolReceiveBufferSize() {
        return this.protocolReceiveBuferSize;
    }
    
    @Override
    public Object getOption(final ChannelOption channelOption) {
        if (channelOption == UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE) {
            return this.getProtocolReceiveBufferSize();
        }
        if (channelOption == UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE) {
            return this.getProtocolSendBufferSize();
        }
        if (channelOption == UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE) {
            return this.getSystemReceiveBufferSize();
        }
        if (channelOption == UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE) {
            return this.getSystemSendBufferSize();
        }
        if (channelOption == UdtChannelOption.SO_RCVBUF) {
            return this.getReceiveBufferSize();
        }
        if (channelOption == UdtChannelOption.SO_SNDBUF) {
            return this.getSendBufferSize();
        }
        if (channelOption == UdtChannelOption.SO_REUSEADDR) {
            return this.isReuseAddress();
        }
        if (channelOption == UdtChannelOption.SO_LINGER) {
            return this.getSoLinger();
        }
        return super.getOption(channelOption);
    }
    
    @Override
    public Map getOptions() {
        return this.getOptions(super.getOptions(), UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE, UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE, UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE, UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE, UdtChannelOption.SO_RCVBUF, UdtChannelOption.SO_SNDBUF, UdtChannelOption.SO_REUSEADDR, UdtChannelOption.SO_LINGER);
    }
    
    @Override
    public int getReceiveBufferSize() {
        return this.allocatorReceiveBufferSize;
    }
    
    @Override
    public int getSendBufferSize() {
        return this.allocatorSendBufferSize;
    }
    
    @Override
    public int getSoLinger() {
        return this.soLinger;
    }
    
    @Override
    public boolean isReuseAddress() {
        return this.reuseAddress;
    }
    
    @Override
    public UdtChannelConfig setProtocolReceiveBufferSize(final int protocolReceiveBuferSize) {
        this.protocolReceiveBuferSize = protocolReceiveBuferSize;
        return this;
    }
    
    @Override
    public boolean setOption(final ChannelOption channelOption, final Object o) {
        this.validate(channelOption, o);
        if (channelOption == UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE) {
            this.setProtocolReceiveBufferSize((int)o);
        }
        else if (channelOption == UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE) {
            this.setProtocolSendBufferSize((int)o);
        }
        else if (channelOption == UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE) {
            this.setSystemReceiveBufferSize((int)o);
        }
        else if (channelOption == UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE) {
            this.setSystemSendBufferSize((int)o);
        }
        else if (channelOption == UdtChannelOption.SO_RCVBUF) {
            this.setReceiveBufferSize((int)o);
        }
        else if (channelOption == UdtChannelOption.SO_SNDBUF) {
            this.setSendBufferSize((int)o);
        }
        else if (channelOption == UdtChannelOption.SO_REUSEADDR) {
            this.setReuseAddress((boolean)o);
        }
        else {
            if (channelOption != UdtChannelOption.SO_LINGER) {
                return super.setOption(channelOption, o);
            }
            this.setSoLinger((int)o);
        }
        return true;
    }
    
    @Override
    public UdtChannelConfig setReceiveBufferSize(final int allocatorReceiveBufferSize) {
        this.allocatorReceiveBufferSize = allocatorReceiveBufferSize;
        return this;
    }
    
    @Override
    public UdtChannelConfig setReuseAddress(final boolean reuseAddress) {
        this.reuseAddress = reuseAddress;
        return this;
    }
    
    @Override
    public UdtChannelConfig setSendBufferSize(final int allocatorSendBufferSize) {
        this.allocatorSendBufferSize = allocatorSendBufferSize;
        return this;
    }
    
    @Override
    public UdtChannelConfig setSoLinger(final int soLinger) {
        this.soLinger = soLinger;
        return this;
    }
    
    @Override
    public int getSystemReceiveBufferSize() {
        return this.systemReceiveBufferSize;
    }
    
    @Override
    public UdtChannelConfig setSystemSendBufferSize(final int systemReceiveBufferSize) {
        this.systemReceiveBufferSize = systemReceiveBufferSize;
        return this;
    }
    
    @Override
    public int getProtocolSendBufferSize() {
        return this.protocolSendBuferSize;
    }
    
    @Override
    public UdtChannelConfig setProtocolSendBufferSize(final int protocolSendBuferSize) {
        this.protocolSendBuferSize = protocolSendBuferSize;
        return this;
    }
    
    @Override
    public UdtChannelConfig setSystemReceiveBufferSize(final int systemSendBuferSize) {
        this.systemSendBuferSize = systemSendBuferSize;
        return this;
    }
    
    @Override
    public int getSystemSendBufferSize() {
        return this.systemSendBuferSize;
    }
    
    @Override
    public UdtChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }
    
    @Override
    public UdtChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }
    
    @Override
    public UdtChannelConfig setWriteSpinCount(final int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }
    
    @Override
    public UdtChannelConfig setAllocator(final ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }
    
    @Override
    public UdtChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }
    
    @Override
    public UdtChannelConfig setAutoRead(final boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }
    
    @Override
    public UdtChannelConfig setAutoClose(final boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }
    
    @Override
    public UdtChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }
    
    @Override
    public UdtChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }
    
    @Override
    public UdtChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
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
