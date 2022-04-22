package io.netty.channel;

import io.netty.buffer.*;
import io.netty.channel.nio.*;
import java.util.*;

public class DefaultChannelConfig implements ChannelConfig
{
    private static final RecvByteBufAllocator DEFAULT_RCVBUF_ALLOCATOR;
    private static final MessageSizeEstimator DEFAULT_MSG_SIZE_ESTIMATOR;
    private static final int DEFAULT_CONNECT_TIMEOUT = 30000;
    protected final Channel channel;
    private ByteBufAllocator allocator;
    private RecvByteBufAllocator rcvBufAllocator;
    private MessageSizeEstimator msgSizeEstimator;
    private int connectTimeoutMillis;
    private int maxMessagesPerRead;
    private int writeSpinCount;
    private boolean autoRead;
    private boolean autoClose;
    private int writeBufferHighWaterMark;
    private int writeBufferLowWaterMark;
    
    public DefaultChannelConfig(final Channel channel) {
        this.allocator = ByteBufAllocator.DEFAULT;
        this.rcvBufAllocator = DefaultChannelConfig.DEFAULT_RCVBUF_ALLOCATOR;
        this.msgSizeEstimator = DefaultChannelConfig.DEFAULT_MSG_SIZE_ESTIMATOR;
        this.connectTimeoutMillis = 30000;
        this.writeSpinCount = 16;
        this.autoRead = true;
        this.autoClose = true;
        this.writeBufferHighWaterMark = 65536;
        this.writeBufferLowWaterMark = 32768;
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        this.channel = channel;
        if (channel instanceof ServerChannel || channel instanceof AbstractNioByteChannel) {
            this.maxMessagesPerRead = 16;
        }
        else {
            this.maxMessagesPerRead = 1;
        }
    }
    
    @Override
    public Map getOptions() {
        return this.getOptions(null, ChannelOption.CONNECT_TIMEOUT_MILLIS, ChannelOption.MAX_MESSAGES_PER_READ, ChannelOption.WRITE_SPIN_COUNT, ChannelOption.ALLOCATOR, ChannelOption.AUTO_READ, ChannelOption.AUTO_CLOSE, ChannelOption.RCVBUF_ALLOCATOR, ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, ChannelOption.MESSAGE_SIZE_ESTIMATOR);
    }
    
    protected Map getOptions(Map identityHashMap, final ChannelOption... array) {
        if (identityHashMap == null) {
            identityHashMap = new IdentityHashMap<ChannelOption, Object>();
        }
        while (0 < array.length) {
            final ChannelOption channelOption = array[0];
            identityHashMap.put(channelOption, this.getOption(channelOption));
            int n = 0;
            ++n;
        }
        return identityHashMap;
    }
    
    @Override
    public boolean setOptions(final Map map) {
        if (map == null) {
            throw new NullPointerException("options");
        }
        for (final Map.Entry<ChannelOption, V> entry : map.entrySet()) {
            if (!this.setOption(entry.getKey(), entry.getValue())) {}
        }
        return false;
    }
    
    @Override
    public Object getOption(final ChannelOption channelOption) {
        if (channelOption == null) {
            throw new NullPointerException("option");
        }
        if (channelOption == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
            return this.getConnectTimeoutMillis();
        }
        if (channelOption == ChannelOption.MAX_MESSAGES_PER_READ) {
            return this.getMaxMessagesPerRead();
        }
        if (channelOption == ChannelOption.WRITE_SPIN_COUNT) {
            return this.getWriteSpinCount();
        }
        if (channelOption == ChannelOption.ALLOCATOR) {
            return this.getAllocator();
        }
        if (channelOption == ChannelOption.RCVBUF_ALLOCATOR) {
            return this.getRecvByteBufAllocator();
        }
        if (channelOption == ChannelOption.AUTO_READ) {
            return this.isAutoRead();
        }
        if (channelOption == ChannelOption.AUTO_CLOSE) {
            return this.isAutoClose();
        }
        if (channelOption == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
            return this.getWriteBufferHighWaterMark();
        }
        if (channelOption == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
            return this.getWriteBufferLowWaterMark();
        }
        if (channelOption == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
            return this.getMessageSizeEstimator();
        }
        return null;
    }
    
    @Override
    public boolean setOption(final ChannelOption channelOption, final Object o) {
        this.validate(channelOption, o);
        if (channelOption == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
            this.setConnectTimeoutMillis((int)o);
        }
        else if (channelOption == ChannelOption.MAX_MESSAGES_PER_READ) {
            this.setMaxMessagesPerRead((int)o);
        }
        else if (channelOption == ChannelOption.WRITE_SPIN_COUNT) {
            this.setWriteSpinCount((int)o);
        }
        else if (channelOption == ChannelOption.ALLOCATOR) {
            this.setAllocator((ByteBufAllocator)o);
        }
        else if (channelOption == ChannelOption.RCVBUF_ALLOCATOR) {
            this.setRecvByteBufAllocator((RecvByteBufAllocator)o);
        }
        else if (channelOption == ChannelOption.AUTO_READ) {
            this.setAutoRead((boolean)o);
        }
        else if (channelOption == ChannelOption.AUTO_CLOSE) {
            this.setAutoClose((boolean)o);
        }
        else if (channelOption == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
            this.setWriteBufferHighWaterMark((int)o);
        }
        else if (channelOption == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
            this.setWriteBufferLowWaterMark((int)o);
        }
        else {
            if (channelOption != ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
                return false;
            }
            this.setMessageSizeEstimator((MessageSizeEstimator)o);
        }
        return true;
    }
    
    protected void validate(final ChannelOption channelOption, final Object o) {
        if (channelOption == null) {
            throw new NullPointerException("option");
        }
        channelOption.validate(o);
    }
    
    @Override
    public int getConnectTimeoutMillis() {
        return this.connectTimeoutMillis;
    }
    
    @Override
    public ChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        if (connectTimeoutMillis < 0) {
            throw new IllegalArgumentException(String.format("connectTimeoutMillis: %d (expected: >= 0)", connectTimeoutMillis));
        }
        this.connectTimeoutMillis = connectTimeoutMillis;
        return this;
    }
    
    @Override
    public int getMaxMessagesPerRead() {
        return this.maxMessagesPerRead;
    }
    
    @Override
    public ChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        if (maxMessagesPerRead <= 0) {
            throw new IllegalArgumentException("maxMessagesPerRead: " + maxMessagesPerRead + " (expected: > 0)");
        }
        this.maxMessagesPerRead = maxMessagesPerRead;
        return this;
    }
    
    @Override
    public int getWriteSpinCount() {
        return this.writeSpinCount;
    }
    
    @Override
    public ChannelConfig setWriteSpinCount(final int writeSpinCount) {
        if (writeSpinCount <= 0) {
            throw new IllegalArgumentException("writeSpinCount must be a positive integer.");
        }
        this.writeSpinCount = writeSpinCount;
        return this;
    }
    
    @Override
    public ByteBufAllocator getAllocator() {
        return this.allocator;
    }
    
    @Override
    public ChannelConfig setAllocator(final ByteBufAllocator allocator) {
        if (allocator == null) {
            throw new NullPointerException("allocator");
        }
        this.allocator = allocator;
        return this;
    }
    
    @Override
    public RecvByteBufAllocator getRecvByteBufAllocator() {
        return this.rcvBufAllocator;
    }
    
    @Override
    public ChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator rcvBufAllocator) {
        if (rcvBufAllocator == null) {
            throw new NullPointerException("allocator");
        }
        this.rcvBufAllocator = rcvBufAllocator;
        return this;
    }
    
    @Override
    public boolean isAutoRead() {
        return this.autoRead;
    }
    
    @Override
    public ChannelConfig setAutoRead(final boolean autoRead) {
        final boolean autoRead2 = this.autoRead;
        this.autoRead = autoRead;
        if (autoRead && !autoRead2) {
            this.channel.read();
        }
        else if (!autoRead && autoRead2) {
            this.autoReadCleared();
        }
        return this;
    }
    
    protected void autoReadCleared() {
    }
    
    @Override
    public boolean isAutoClose() {
        return this.autoClose;
    }
    
    @Override
    public ChannelConfig setAutoClose(final boolean autoClose) {
        this.autoClose = autoClose;
        return this;
    }
    
    @Override
    public int getWriteBufferHighWaterMark() {
        return this.writeBufferHighWaterMark;
    }
    
    @Override
    public ChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        if (writeBufferHighWaterMark < this.getWriteBufferLowWaterMark()) {
            throw new IllegalArgumentException("writeBufferHighWaterMark cannot be less than writeBufferLowWaterMark (" + this.getWriteBufferLowWaterMark() + "): " + writeBufferHighWaterMark);
        }
        if (writeBufferHighWaterMark < 0) {
            throw new IllegalArgumentException("writeBufferHighWaterMark must be >= 0");
        }
        this.writeBufferHighWaterMark = writeBufferHighWaterMark;
        return this;
    }
    
    @Override
    public int getWriteBufferLowWaterMark() {
        return this.writeBufferLowWaterMark;
    }
    
    @Override
    public ChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        if (writeBufferLowWaterMark > this.getWriteBufferHighWaterMark()) {
            throw new IllegalArgumentException("writeBufferLowWaterMark cannot be greater than writeBufferHighWaterMark (" + this.getWriteBufferHighWaterMark() + "): " + writeBufferLowWaterMark);
        }
        if (writeBufferLowWaterMark < 0) {
            throw new IllegalArgumentException("writeBufferLowWaterMark must be >= 0");
        }
        this.writeBufferLowWaterMark = writeBufferLowWaterMark;
        return this;
    }
    
    @Override
    public MessageSizeEstimator getMessageSizeEstimator() {
        return this.msgSizeEstimator;
    }
    
    @Override
    public ChannelConfig setMessageSizeEstimator(final MessageSizeEstimator msgSizeEstimator) {
        if (msgSizeEstimator == null) {
            throw new NullPointerException("estimator");
        }
        this.msgSizeEstimator = msgSizeEstimator;
        return this;
    }
    
    static {
        DEFAULT_RCVBUF_ALLOCATOR = AdaptiveRecvByteBufAllocator.DEFAULT;
        DEFAULT_MSG_SIZE_ESTIMATOR = DefaultMessageSizeEstimator.DEFAULT;
    }
}
