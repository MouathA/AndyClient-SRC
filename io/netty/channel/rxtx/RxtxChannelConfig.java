package io.netty.channel.rxtx;

import io.netty.buffer.*;
import io.netty.channel.*;

public interface RxtxChannelConfig extends ChannelConfig
{
    RxtxChannelConfig setBaudrate(final int p0);
    
    RxtxChannelConfig setStopbits(final Stopbits p0);
    
    RxtxChannelConfig setDatabits(final Databits p0);
    
    RxtxChannelConfig setParitybit(final Paritybit p0);
    
    int getBaudrate();
    
    Stopbits getStopbits();
    
    Databits getDatabits();
    
    Paritybit getParitybit();
    
    boolean isDtr();
    
    RxtxChannelConfig setDtr(final boolean p0);
    
    boolean isRts();
    
    RxtxChannelConfig setRts(final boolean p0);
    
    int getWaitTimeMillis();
    
    RxtxChannelConfig setWaitTimeMillis(final int p0);
    
    RxtxChannelConfig setReadTimeout(final int p0);
    
    int getReadTimeout();
    
    RxtxChannelConfig setConnectTimeoutMillis(final int p0);
    
    RxtxChannelConfig setMaxMessagesPerRead(final int p0);
    
    RxtxChannelConfig setWriteSpinCount(final int p0);
    
    RxtxChannelConfig setAllocator(final ByteBufAllocator p0);
    
    RxtxChannelConfig setRecvByteBufAllocator(final RecvByteBufAllocator p0);
    
    RxtxChannelConfig setAutoRead(final boolean p0);
    
    RxtxChannelConfig setAutoClose(final boolean p0);
    
    RxtxChannelConfig setWriteBufferHighWaterMark(final int p0);
    
    RxtxChannelConfig setWriteBufferLowWaterMark(final int p0);
    
    RxtxChannelConfig setMessageSizeEstimator(final MessageSizeEstimator p0);
    
    public enum Paritybit
    {
        NONE("NONE", 0, 0), 
        ODD("ODD", 1, 1), 
        EVEN("EVEN", 2, 2), 
        MARK("MARK", 3, 3), 
        SPACE("SPACE", 4, 4);
        
        private final int value;
        private static final Paritybit[] $VALUES;
        
        private Paritybit(final String s, final int n, final int value) {
            this.value = value;
        }
        
        public int value() {
            return this.value;
        }
        
        public static Paritybit valueOf(final int n) {
            final Paritybit[] values = values();
            while (0 < values.length) {
                final Paritybit paritybit = values[0];
                if (paritybit.value == n) {
                    return paritybit;
                }
                int n2 = 0;
                ++n2;
            }
            throw new IllegalArgumentException("unknown " + Paritybit.class.getSimpleName() + " value: " + n);
        }
        
        static {
            $VALUES = new Paritybit[] { Paritybit.NONE, Paritybit.ODD, Paritybit.EVEN, Paritybit.MARK, Paritybit.SPACE };
        }
    }
    
    public enum Databits
    {
        DATABITS_5("DATABITS_5", 0, 5), 
        DATABITS_6("DATABITS_6", 1, 6), 
        DATABITS_7("DATABITS_7", 2, 7), 
        DATABITS_8("DATABITS_8", 3, 8);
        
        private final int value;
        private static final Databits[] $VALUES;
        
        private Databits(final String s, final int n, final int value) {
            this.value = value;
        }
        
        public int value() {
            return this.value;
        }
        
        public static Databits valueOf(final int n) {
            final Databits[] values = values();
            while (0 < values.length) {
                final Databits databits = values[0];
                if (databits.value == n) {
                    return databits;
                }
                int n2 = 0;
                ++n2;
            }
            throw new IllegalArgumentException("unknown " + Databits.class.getSimpleName() + " value: " + n);
        }
        
        static {
            $VALUES = new Databits[] { Databits.DATABITS_5, Databits.DATABITS_6, Databits.DATABITS_7, Databits.DATABITS_8 };
        }
    }
    
    public enum Stopbits
    {
        STOPBITS_1("STOPBITS_1", 0, 1), 
        STOPBITS_2("STOPBITS_2", 1, 2), 
        STOPBITS_1_5("STOPBITS_1_5", 2, 3);
        
        private final int value;
        private static final Stopbits[] $VALUES;
        
        private Stopbits(final String s, final int n, final int value) {
            this.value = value;
        }
        
        public int value() {
            return this.value;
        }
        
        public static Stopbits valueOf(final int n) {
            final Stopbits[] values = values();
            while (0 < values.length) {
                final Stopbits stopbits = values[0];
                if (stopbits.value == n) {
                    return stopbits;
                }
                int n2 = 0;
                ++n2;
            }
            throw new IllegalArgumentException("unknown " + Stopbits.class.getSimpleName() + " value: " + n);
        }
        
        static {
            $VALUES = new Stopbits[] { Stopbits.STOPBITS_1, Stopbits.STOPBITS_2, Stopbits.STOPBITS_1_5 };
        }
    }
}
