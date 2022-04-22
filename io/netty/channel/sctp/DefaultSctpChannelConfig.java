package io.netty.channel.sctp;

import io.netty.util.internal.*;
import java.util.*;
import com.sun.nio.sctp.*;
import io.netty.buffer.*;
import io.netty.channel.*;

public class DefaultSctpChannelConfig extends DefaultChannelConfig implements SctpChannelConfig
{
    private final SctpChannel javaChannel;
    
    public DefaultSctpChannelConfig(final io.netty.channel.sctp.SctpChannel sctpChannel, final SctpChannel javaChannel) {
        super(sctpChannel);
        if (javaChannel == null) {
            throw new NullPointerException("javaChannel");
        }
        this.javaChannel = javaChannel;
        if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
            this.setSctpNoDelay(true);
        }
    }
    
    @Override
    public Map getOptions() {
        return this.getOptions(super.getOptions(), SctpChannelOption.SO_RCVBUF, SctpChannelOption.SO_SNDBUF, SctpChannelOption.SCTP_NODELAY, SctpChannelOption.SCTP_INIT_MAXSTREAMS);
    }
    
    @Override
    public Object getOption(final ChannelOption channelOption) {
        if (channelOption == SctpChannelOption.SO_RCVBUF) {
            return this.getReceiveBufferSize();
        }
        if (channelOption == SctpChannelOption.SO_SNDBUF) {
            return this.getSendBufferSize();
        }
        if (channelOption == SctpChannelOption.SCTP_NODELAY) {
            return this.isSctpNoDelay();
        }
        return super.getOption(channelOption);
    }
    
    @Override
    public boolean setOption(final ChannelOption channelOption, final Object o) {
        this.validate(channelOption, o);
        if (channelOption == SctpChannelOption.SO_RCVBUF) {
            this.setReceiveBufferSize((int)o);
        }
        else if (channelOption == SctpChannelOption.SO_SNDBUF) {
            this.setSendBufferSize((int)o);
        }
        else if (channelOption == SctpChannelOption.SCTP_NODELAY) {
            this.setSctpNoDelay((boolean)o);
        }
        else {
            if (channelOption != SctpChannelOption.SCTP_INIT_MAXSTREAMS) {
                return super.setOption(channelOption, o);
            }
            this.setInitMaxStreams((SctpStandardSocketOptions.InitMaxStreams)o);
        }
        return true;
    }
    
    @Override
    public boolean isSctpNoDelay() {
        return this.javaChannel.getOption(SctpStandardSocketOptions.SCTP_NODELAY);
    }
    
    @Override
    public SctpChannelConfig setSctpNoDelay(final boolean b) {
        this.javaChannel.setOption(SctpStandardSocketOptions.SCTP_NODELAY, b);
        return this;
    }
    
    @Override
    public int getSendBufferSize() {
        return this.javaChannel.getOption(SctpStandardSocketOptions.SO_SNDBUF);
    }
    
    @Override
    public SctpChannelConfig setSendBufferSize(final int n) {
        this.javaChannel.setOption(SctpStandardSocketOptions.SO_SNDBUF, n);
        return this;
    }
    
    @Override
    public int getReceiveBufferSize() {
        return this.javaChannel.getOption(SctpStandardSocketOptions.SO_RCVBUF);
    }
    
    @Override
    public SctpChannelConfig setReceiveBufferSize(final int n) {
        this.javaChannel.setOption(SctpStandardSocketOptions.SO_RCVBUF, n);
        return this;
    }
    
    @Override
    public SctpStandardSocketOptions.InitMaxStreams getInitMaxStreams() {
        return this.javaChannel.getOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS);
    }
    
    @Override
    public SctpChannelConfig setInitMaxStreams(final SctpStandardSocketOptions.InitMaxStreams initMaxStreams) {
        this.javaChannel.setOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS, initMaxStreams);
        return this;
    }
    
    @Override
    public SctpChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }
    
    @Override
    public SctpChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }
    
    @Override
    public SctpChannelConfig setWriteSpinCount(final int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }
    
    @Override
    public SctpChannelConfig setAllocator(final ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }
    
    @Override
    public SctpChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }
    
    @Override
    public SctpChannelConfig setAutoRead(final boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }
    
    @Override
    public SctpChannelConfig setAutoClose(final boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }
    
    @Override
    public SctpChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }
    
    @Override
    public SctpChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }
    
    @Override
    public SctpChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
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
