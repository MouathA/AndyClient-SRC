package com.google.common.hash;

import java.io.*;
import javax.annotation.*;
import com.google.common.primitives.*;
import java.nio.*;

final class Murmur3_128HashFunction extends AbstractStreamingHashFunction implements Serializable
{
    private final int seed;
    private static final long serialVersionUID = 0L;
    
    Murmur3_128HashFunction(final int seed) {
        this.seed = seed;
    }
    
    @Override
    public int bits() {
        return 128;
    }
    
    @Override
    public Hasher newHasher() {
        return new Murmur3_128Hasher(this.seed);
    }
    
    @Override
    public String toString() {
        return "Hashing.murmur3_128(" + this.seed + ")";
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o instanceof Murmur3_128HashFunction && this.seed == ((Murmur3_128HashFunction)o).seed;
    }
    
    @Override
    public int hashCode() {
        return this.getClass().hashCode() ^ this.seed;
    }
    
    private static final class Murmur3_128Hasher extends AbstractStreamingHasher
    {
        private static final int CHUNK_SIZE = 16;
        private static final long C1 = -8663945395140668459L;
        private static final long C2 = 5545529020109919103L;
        private long h1;
        private long h2;
        private int length;
        
        Murmur3_128Hasher(final int n) {
            super(16);
            this.h1 = n;
            this.h2 = n;
            this.length = 0;
        }
        
        @Override
        protected void process(final ByteBuffer byteBuffer) {
            this.bmix64(byteBuffer.getLong(), byteBuffer.getLong());
            this.length += 16;
        }
        
        private void bmix64(final long n, final long n2) {
            this.h1 ^= mixK1(n);
            this.h1 = Long.rotateLeft(this.h1, 27);
            this.h1 += this.h2;
            this.h1 = this.h1 * 5L + 1390208809L;
            this.h2 ^= mixK2(n2);
            this.h2 = Long.rotateLeft(this.h2, 31);
            this.h2 += this.h1;
            this.h2 = this.h2 * 5L + 944331445L;
        }
        
        @Override
        protected void processRemaining(final ByteBuffer byteBuffer) {
            long n = 0L;
            long n2 = 0L;
            this.length += byteBuffer.remaining();
            long n3 = 0L;
            switch (byteBuffer.remaining()) {
                case 15: {
                    n2 ^= (long)UnsignedBytes.toInt(byteBuffer.get(14)) << 48;
                }
                case 14: {
                    n2 ^= (long)UnsignedBytes.toInt(byteBuffer.get(13)) << 40;
                }
                case 13: {
                    n2 ^= (long)UnsignedBytes.toInt(byteBuffer.get(12)) << 32;
                }
                case 12: {
                    n2 ^= (long)UnsignedBytes.toInt(byteBuffer.get(11)) << 24;
                }
                case 11: {
                    n2 ^= (long)UnsignedBytes.toInt(byteBuffer.get(10)) << 16;
                }
                case 10: {
                    n2 ^= (long)UnsignedBytes.toInt(byteBuffer.get(9)) << 8;
                }
                case 9: {
                    n2 ^= UnsignedBytes.toInt(byteBuffer.get(8));
                }
                case 8: {
                    n3 = (n ^ byteBuffer.getLong());
                    break;
                }
                case 7: {
                    n ^= (long)UnsignedBytes.toInt(byteBuffer.get(6)) << 48;
                }
                case 6: {
                    n ^= (long)UnsignedBytes.toInt(byteBuffer.get(5)) << 40;
                }
                case 5: {
                    n ^= (long)UnsignedBytes.toInt(byteBuffer.get(4)) << 32;
                }
                case 4: {
                    n ^= (long)UnsignedBytes.toInt(byteBuffer.get(3)) << 24;
                }
                case 3: {
                    n ^= (long)UnsignedBytes.toInt(byteBuffer.get(2)) << 16;
                }
                case 2: {
                    n ^= (long)UnsignedBytes.toInt(byteBuffer.get(1)) << 8;
                }
                case 1: {
                    n3 = (n ^ (long)UnsignedBytes.toInt(byteBuffer.get(0)));
                    break;
                }
                default: {
                    throw new AssertionError((Object)"Should never get here.");
                }
            }
            this.h1 ^= mixK1(n3);
            this.h2 ^= mixK2(n2);
        }
        
        public HashCode makeHash() {
            this.h1 ^= this.length;
            this.h2 ^= this.length;
            this.h1 += this.h2;
            this.h2 += this.h1;
            this.h1 = fmix64(this.h1);
            this.h2 = fmix64(this.h2);
            this.h1 += this.h2;
            this.h2 += this.h1;
            return HashCode.fromBytesNoCopy(ByteBuffer.wrap(new byte[16]).order(ByteOrder.LITTLE_ENDIAN).putLong(this.h1).putLong(this.h2).array());
        }
        
        private static long fmix64(long n) {
            n ^= n >>> 33;
            n *= -49064778989728563L;
            n ^= n >>> 33;
            n *= -4265267296055464877L;
            n ^= n >>> 33;
            return n;
        }
        
        private static long mixK1(long rotateLeft) {
            rotateLeft *= -8663945395140668459L;
            rotateLeft = Long.rotateLeft(rotateLeft, 31);
            rotateLeft *= 5545529020109919103L;
            return rotateLeft;
        }
        
        private static long mixK2(long rotateLeft) {
            rotateLeft *= 5545529020109919103L;
            rotateLeft = Long.rotateLeft(rotateLeft, 33);
            rotateLeft *= -8663945395140668459L;
            return rotateLeft;
        }
    }
}
