package com.google.common.hash;

import java.io.*;
import javax.annotation.*;
import java.nio.*;
import com.google.common.primitives.*;

final class Murmur3_32HashFunction extends AbstractStreamingHashFunction implements Serializable
{
    private static final int C1 = -862048943;
    private static final int C2 = 461845907;
    private final int seed;
    private static final long serialVersionUID = 0L;
    
    Murmur3_32HashFunction(final int seed) {
        this.seed = seed;
    }
    
    @Override
    public int bits() {
        return 32;
    }
    
    @Override
    public Hasher newHasher() {
        return new Murmur3_32Hasher(this.seed);
    }
    
    @Override
    public String toString() {
        return "Hashing.murmur3_32(" + this.seed + ")";
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o instanceof Murmur3_32HashFunction && this.seed == ((Murmur3_32HashFunction)o).seed;
    }
    
    @Override
    public int hashCode() {
        return this.getClass().hashCode() ^ this.seed;
    }
    
    @Override
    public HashCode hashInt(final int n) {
        return fmix(mixH1(this.seed, mixK1(n)), 4);
    }
    
    @Override
    public HashCode hashLong(final long n) {
        return fmix(mixH1(mixH1(this.seed, mixK1((int)n)), mixK1((int)(n >>> 32))), 8);
    }
    
    @Override
    public HashCode hashUnencodedChars(final CharSequence charSequence) {
        int n = this.seed;
        while (1 < charSequence.length()) {
            n = mixH1(n, mixK1(charSequence.charAt(0) | charSequence.charAt(1) << 16));
            final int n2;
            n2 += 2;
        }
        if ((charSequence.length() & 0x1) == 0x1) {
            int n2 = charSequence.charAt(charSequence.length() - 1);
            n2 = mixK1(1);
            n ^= 0x1;
        }
        return fmix(n, 2 * charSequence.length());
    }
    
    private static int mixK1(int rotateLeft) {
        rotateLeft *= -862048943;
        rotateLeft = Integer.rotateLeft(rotateLeft, 15);
        rotateLeft *= 461845907;
        return rotateLeft;
    }
    
    private static int mixH1(int rotateLeft, final int n) {
        rotateLeft ^= n;
        rotateLeft = Integer.rotateLeft(rotateLeft, 13);
        rotateLeft = rotateLeft * 5 - 430675100;
        return rotateLeft;
    }
    
    private static HashCode fmix(int n, final int n2) {
        n ^= n2;
        n ^= n >>> 16;
        n *= -2048144789;
        n ^= n >>> 13;
        n *= -1028477387;
        n ^= n >>> 16;
        return HashCode.fromInt(n);
    }
    
    static int access$000(final int n) {
        return mixK1(n);
    }
    
    static int access$100(final int n, final int n2) {
        return mixH1(n, n2);
    }
    
    static HashCode access$200(final int n, final int n2) {
        return fmix(n, n2);
    }
    
    private static final class Murmur3_32Hasher extends AbstractStreamingHasher
    {
        private static final int CHUNK_SIZE = 4;
        private int h1;
        private int length;
        
        Murmur3_32Hasher(final int h1) {
            super(4);
            this.h1 = h1;
            this.length = 0;
        }
        
        @Override
        protected void process(final ByteBuffer byteBuffer) {
            this.h1 = Murmur3_32HashFunction.access$100(this.h1, Murmur3_32HashFunction.access$000(byteBuffer.getInt()));
            this.length += 4;
        }
        
        @Override
        protected void processRemaining(final ByteBuffer byteBuffer) {
            this.length += byteBuffer.remaining();
            while (byteBuffer.hasRemaining()) {
                final int n = 0x0 ^ UnsignedBytes.toInt(byteBuffer.get()) << 0;
                final int n2;
                n2 += 8;
            }
            this.h1 ^= Murmur3_32HashFunction.access$000(0);
        }
        
        public HashCode makeHash() {
            return Murmur3_32HashFunction.access$200(this.h1, this.length);
        }
    }
}
