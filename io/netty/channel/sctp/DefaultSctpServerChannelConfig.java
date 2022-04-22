package io.netty.channel.sctp;

import io.netty.util.*;
import java.util.*;
import com.sun.nio.sctp.*;
import io.netty.buffer.*;
import io.netty.channel.*;

public class DefaultSctpServerChannelConfig extends DefaultChannelConfig implements SctpServerChannelConfig
{
    private final SctpServerChannel javaChannel;
    private int backlog;
    
    public DefaultSctpServerChannelConfig(final io.netty.channel.sctp.SctpServerChannel sctpServerChannel, final SctpServerChannel javaChannel) {
        super(sctpServerChannel);
        this.backlog = NetUtil.SOMAXCONN;
        if (javaChannel == null) {
            throw new NullPointerException("javaChannel");
        }
        this.javaChannel = javaChannel;
    }
    
    @Override
    public Map getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, SctpChannelOption.SCTP_INIT_MAXSTREAMS);
    }
    
    @Override
    public Object getOption(final ChannelOption channelOption) {
        if (channelOption == ChannelOption.SO_RCVBUF) {
            return this.getReceiveBufferSize();
        }
        if (channelOption == ChannelOption.SO_SNDBUF) {
            return this.getSendBufferSize();
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
        else {
            if (channelOption != SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS) {
                return super.setOption(channelOption, o);
            }
            this.setInitMaxStreams((SctpStandardSocketOptions.InitMaxStreams)o);
        }
        return true;
    }
    
    @Override
    public int getSendBufferSize() {
        return this.javaChannel.getOption(SctpStandardSocketOptions.SO_SNDBUF);
    }
    
    @Override
    public SctpServerChannelConfig setSendBufferSize(final int n) {
        this.javaChannel.setOption(SctpStandardSocketOptions.SO_SNDBUF, n);
        return this;
    }
    
    @Override
    public int getReceiveBufferSize() {
        return this.javaChannel.getOption(SctpStandardSocketOptions.SO_RCVBUF);
    }
    
    @Override
    public SctpServerChannelConfig setReceiveBufferSize(final int n) {
        this.javaChannel.setOption(SctpStandardSocketOptions.SO_RCVBUF, n);
        return this;
    }
    
    @Override
    public SctpStandardSocketOptions.InitMaxStreams getInitMaxStreams() {
        return this.javaChannel.getOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS);
    }
    
    @Override
    public SctpServerChannelConfig setInitMaxStreams(final SctpStandardSocketOptions.InitMaxStreams initMaxStreams) {
        this.javaChannel.setOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS, initMaxStreams);
        return this;
    }
    
    @Override
    public int getBacklog() {
        return this.backlog;
    }
    
    @Override
    public SctpServerChannelConfig setBacklog(final int backlog) {
        if (backlog < 0) {
            throw new IllegalArgumentException("backlog: " + backlog);
        }
        this.backlog = backlog;
        return this;
    }
    
    @Override
    public SctpServerChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }
    
    @Override
    public SctpServerChannelConfig setWriteSpinCount(final int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }
    
    @Override
    public SctpServerChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }
    
    @Override
    public SctpServerChannelConfig setAllocator(final ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }
    
    @Override
    public SctpServerChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }
    
    @Override
    public SctpServerChannelConfig setAutoRead(final boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }
    
    @Override
    public SctpServerChannelConfig setAutoClose(final boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }
    
    @Override
    public SctpServerChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }
    
    @Override
    public SctpServerChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }
    
    @Override
    public SctpServerChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
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
