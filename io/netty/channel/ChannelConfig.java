package io.netty.channel;

import java.util.*;
import io.netty.buffer.*;

public interface ChannelConfig
{
    Map getOptions();
    
    boolean setOptions(final Map p0);
    
    Object getOption(final ChannelOption p0);
    
    boolean setOption(final ChannelOption p0, final Object p1);
    
    int getConnectTimeoutMillis();
    
    ChannelConfig setConnectTimeoutMillis(final int p0);
    
    int getMaxMessagesPerRead();
    
    ChannelConfig setMaxMessagesPerRead(final int p0);
    
    int getWriteSpinCount();
    
    ChannelConfig setWriteSpinCount(final int p0);
    
    ByteBufAllocator getAllocator();
    
    ChannelConfig setAllocator(final ByteBufAllocator p0);
    
    RecvByteBufAllocator getRecvByteBufAllocator();
    
    ChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator p0);
    
    boolean isAutoRead();
    
    ChannelConfig setAutoRead(final boolean p0);
    
    @Deprecated
    boolean isAutoClose();
    
    @Deprecated
    ChannelConfig setAutoClose(final boolean p0);
    
    int getWriteBufferHighWaterMark();
    
    ChannelConfig setWriteBufferHighWaterMark(final int p0);
    
    int getWriteBufferLowWaterMark();
    
    ChannelConfig setWriteBufferLowWaterMark(final int p0);
    
    MessageSizeEstimator getMessageSizeEstimator();
    
    ChannelConfig setMessageSizeEstimator(final MessageSizeEstimator p0);
}
