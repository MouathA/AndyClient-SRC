package io.netty.channel.rxtx;

import java.util.*;
import io.netty.buffer.*;
import io.netty.channel.*;

final class DefaultRxtxChannelConfig extends DefaultChannelConfig implements RxtxChannelConfig
{
    private int baudrate;
    private boolean dtr;
    private boolean rts;
    private Stopbits stopbits;
    private Databits databits;
    private Paritybit paritybit;
    private int waitTime;
    private int readTimeout;
    
    DefaultRxtxChannelConfig(final RxtxChannel rxtxChannel) {
        super(rxtxChannel);
        this.baudrate = 115200;
        this.stopbits = Stopbits.STOPBITS_1;
        this.databits = Databits.DATABITS_8;
        this.paritybit = Paritybit.NONE;
        this.readTimeout = 1000;
    }
    
    @Override
    public Map getOptions() {
        return this.getOptions(super.getOptions(), RxtxChannelOption.BAUD_RATE, RxtxChannelOption.DTR, RxtxChannelOption.RTS, RxtxChannelOption.STOP_BITS, RxtxChannelOption.DATA_BITS, RxtxChannelOption.PARITY_BIT, RxtxChannelOption.WAIT_TIME);
    }
    
    @Override
    public Object getOption(final ChannelOption channelOption) {
        if (channelOption == RxtxChannelOption.BAUD_RATE) {
            return this.getBaudrate();
        }
        if (channelOption == RxtxChannelOption.DTR) {
            return this.isDtr();
        }
        if (channelOption == RxtxChannelOption.RTS) {
            return this.isRts();
        }
        if (channelOption == RxtxChannelOption.STOP_BITS) {
            return this.getStopbits();
        }
        if (channelOption == RxtxChannelOption.DATA_BITS) {
            return this.getDatabits();
        }
        if (channelOption == RxtxChannelOption.PARITY_BIT) {
            return this.getParitybit();
        }
        if (channelOption == RxtxChannelOption.WAIT_TIME) {
            return this.getWaitTimeMillis();
        }
        if (channelOption == RxtxChannelOption.READ_TIMEOUT) {
            return this.getReadTimeout();
        }
        return super.getOption(channelOption);
    }
    
    @Override
    public boolean setOption(final ChannelOption channelOption, final Object o) {
        this.validate(channelOption, o);
        if (channelOption == RxtxChannelOption.BAUD_RATE) {
            this.setBaudrate((int)o);
        }
        else if (channelOption == RxtxChannelOption.DTR) {
            this.setDtr((boolean)o);
        }
        else if (channelOption == RxtxChannelOption.RTS) {
            this.setRts((boolean)o);
        }
        else if (channelOption == RxtxChannelOption.STOP_BITS) {
            this.setStopbits((Stopbits)o);
        }
        else if (channelOption == RxtxChannelOption.DATA_BITS) {
            this.setDatabits((Databits)o);
        }
        else if (channelOption == RxtxChannelOption.PARITY_BIT) {
            this.setParitybit((Paritybit)o);
        }
        else if (channelOption == RxtxChannelOption.WAIT_TIME) {
            this.setWaitTimeMillis((int)o);
        }
        else {
            if (channelOption != RxtxChannelOption.READ_TIMEOUT) {
                return super.setOption(channelOption, o);
            }
            this.setReadTimeout((int)o);
        }
        return true;
    }
    
    @Override
    public RxtxChannelConfig setBaudrate(final int baudrate) {
        this.baudrate = baudrate;
        return this;
    }
    
    @Override
    public RxtxChannelConfig setStopbits(final Stopbits stopbits) {
        this.stopbits = stopbits;
        return this;
    }
    
    @Override
    public RxtxChannelConfig setDatabits(final Databits databits) {
        this.databits = databits;
        return this;
    }
    
    @Override
    public RxtxChannelConfig setParitybit(final Paritybit paritybit) {
        this.paritybit = paritybit;
        return this;
    }
    
    @Override
    public int getBaudrate() {
        return this.baudrate;
    }
    
    @Override
    public Stopbits getStopbits() {
        return this.stopbits;
    }
    
    @Override
    public Databits getDatabits() {
        return this.databits;
    }
    
    @Override
    public Paritybit getParitybit() {
        return this.paritybit;
    }
    
    @Override
    public boolean isDtr() {
        return this.dtr;
    }
    
    @Override
    public RxtxChannelConfig setDtr(final boolean dtr) {
        this.dtr = dtr;
        return this;
    }
    
    @Override
    public boolean isRts() {
        return this.rts;
    }
    
    @Override
    public RxtxChannelConfig setRts(final boolean rts) {
        this.rts = rts;
        return this;
    }
    
    @Override
    public int getWaitTimeMillis() {
        return this.waitTime;
    }
    
    @Override
    public RxtxChannelConfig setWaitTimeMillis(final int waitTime) {
        if (waitTime < 0) {
            throw new IllegalArgumentException("Wait time must be >= 0");
        }
        this.waitTime = waitTime;
        return this;
    }
    
    @Override
    public RxtxChannelConfig setReadTimeout(final int readTimeout) {
        if (readTimeout < 0) {
            throw new IllegalArgumentException("readTime must be >= 0");
        }
        this.readTimeout = readTimeout;
        return this;
    }
    
    @Override
    public int getReadTimeout() {
        return this.readTimeout;
    }
    
    @Override
    public RxtxChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }
    
    @Override
    public RxtxChannelConfig setMaxMessagesPerRead(final int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }
    
    @Override
    public RxtxChannelConfig setWriteSpinCount(final int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }
    
    @Override
    public RxtxChannelConfig setAllocator(final ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }
    
    @Override
    public RxtxChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }
    
    @Override
    public RxtxChannelConfig setAutoRead(final boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }
    
    @Override
    public RxtxChannelConfig setAutoClose(final boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }
    
    @Override
    public RxtxChannelConfig setWriteBufferHighWaterMark(final int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }
    
    @Override
    public RxtxChannelConfig setWriteBufferLowWaterMark(final int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }
    
    @Override
    public RxtxChannelConfig setMessageSizeEstimator(final MessageSizeEstimator messageSizeEstimator) {
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
