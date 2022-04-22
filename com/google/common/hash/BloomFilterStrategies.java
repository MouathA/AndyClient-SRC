package com.google.common.hash;

import java.math.*;
import com.google.common.math.*;
import com.google.common.primitives.*;
import com.google.common.base.*;
import java.util.*;

enum BloomFilterStrategies implements BloomFilter.Strategy
{
    MURMUR128_MITZ_32 {
        @Override
        public boolean put(final Object o, final Funnel funnel, final int n, final BitArray bitArray) {
            final long bitSize = bitArray.bitSize();
            final long long1 = Hashing.murmur3_128().hashObject(o, funnel).asLong();
            final int n2 = (int)long1;
            final int n3 = (int)(long1 >>> 32);
            while (1 <= n) {
                int n4 = n2 + 1 * n3;
                if (n4 < 0) {
                    n4 ^= -1;
                }
                final boolean b = false | bitArray.set(n4 % bitSize);
                int n5 = 0;
                ++n5;
            }
            return false;
        }
        
        @Override
        public boolean mightContain(final Object o, final Funnel funnel, final int n, final BitArray bitArray) {
            final long bitSize = bitArray.bitSize();
            final long long1 = Hashing.murmur3_128().hashObject(o, funnel).asLong();
            final int n2 = (int)long1;
            final int n3 = (int)(long1 >>> 32);
            while (1 <= n) {
                int n4 = n2 + 1 * n3;
                if (n4 < 0) {
                    n4 ^= -1;
                }
                if (!bitArray.get(n4 % bitSize)) {
                    return false;
                }
                int n5 = 0;
                ++n5;
            }
            return true;
        }
    }, 
    MURMUR128_MITZ_64 {
        @Override
        public boolean put(final Object o, final Funnel funnel, final int n, final BitArray bitArray) {
            final long bitSize = bitArray.bitSize();
            final byte[] bytesInternal = Hashing.murmur3_128().hashObject(o, funnel).getBytesInternal();
            final long lowerEight = this.lowerEight(bytesInternal);
            final long upperEight = this.upperEight(bytesInternal);
            long n2 = lowerEight;
            while (0 < n) {
                final boolean b = false | bitArray.set((n2 & Long.MAX_VALUE) % bitSize);
                n2 += upperEight;
                int n3 = 0;
                ++n3;
            }
            return false;
        }
        
        @Override
        public boolean mightContain(final Object o, final Funnel funnel, final int n, final BitArray bitArray) {
            final long bitSize = bitArray.bitSize();
            final byte[] bytesInternal = Hashing.murmur3_128().hashObject(o, funnel).getBytesInternal();
            final long lowerEight = this.lowerEight(bytesInternal);
            final long upperEight = this.upperEight(bytesInternal);
            long n2 = lowerEight;
            while (0 < n) {
                if (!bitArray.get((n2 & Long.MAX_VALUE) % bitSize)) {
                    return false;
                }
                n2 += upperEight;
                int n3 = 0;
                ++n3;
            }
            return true;
        }
        
        private long lowerEight(final byte[] array) {
            return Longs.fromBytes(array[7], array[6], array[5], array[4], array[3], array[2], array[1], array[0]);
        }
        
        private long upperEight(final byte[] array) {
            return Longs.fromBytes(array[15], array[14], array[13], array[12], array[11], array[10], array[9], array[8]);
        }
    };
    
    private static final BloomFilterStrategies[] $VALUES;
    
    private BloomFilterStrategies(final String s, final int n) {
    }
    
    BloomFilterStrategies(final String s, final int n, final BloomFilterStrategies$1 bloomFilterStrategies) {
        this(s, n);
    }
    
    static {
        $VALUES = new BloomFilterStrategies[] { BloomFilterStrategies.MURMUR128_MITZ_32, BloomFilterStrategies.MURMUR128_MITZ_64 };
    }
    
    static final class BitArray
    {
        final long[] data;
        long bitCount;
        
        BitArray(final long n) {
            this(new long[Ints.checkedCast(LongMath.divide(n, 64L, RoundingMode.CEILING))]);
        }
        
        BitArray(final long[] data) {
            Preconditions.checkArgument(data.length > 0, (Object)"data length is zero!");
            this.data = data;
            long bitCount = 0L;
            while (0 < data.length) {
                bitCount += Long.bitCount(data[0]);
                int n = 0;
                ++n;
            }
            this.bitCount = bitCount;
        }
        
        boolean set(final long n) {
            if (!this.get(n)) {
                final long[] data = this.data;
                final int n2 = (int)(n >>> 6);
                data[n2] |= 1L << (int)n;
                ++this.bitCount;
                return true;
            }
            return false;
        }
        
        boolean get(final long n) {
            return (this.data[(int)(n >>> 6)] & 1L << (int)n) != 0x0L;
        }
        
        long bitSize() {
            return this.data.length * 64L;
        }
        
        long bitCount() {
            return this.bitCount;
        }
        
        BitArray copy() {
            return new BitArray(this.data.clone());
        }
        
        void putAll(final BitArray bitArray) {
            Preconditions.checkArgument(this.data.length == bitArray.data.length, "BitArrays must be of equal length (%s != %s)", this.data.length, bitArray.data.length);
            this.bitCount = 0L;
            while (0 < this.data.length) {
                final long[] data = this.data;
                final int n = 0;
                data[n] |= bitArray.data[0];
                this.bitCount += Long.bitCount(this.data[0]);
                int n2 = 0;
                ++n2;
            }
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof BitArray && Arrays.equals(this.data, ((BitArray)o).data);
        }
        
        @Override
        public int hashCode() {
            return Arrays.hashCode(this.data);
        }
    }
}
