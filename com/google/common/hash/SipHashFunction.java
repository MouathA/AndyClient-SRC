package com.google.common.hash;

import java.io.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.nio.*;

final class SipHashFunction extends AbstractStreamingHashFunction implements Serializable
{
    private final int c;
    private final int d;
    private final long k0;
    private final long k1;
    private static final long serialVersionUID = 0L;
    
    SipHashFunction(final int c, final int d, final long k0, final long k2) {
        Preconditions.checkArgument(c > 0, "The number of SipRound iterations (c=%s) during Compression must be positive.", c);
        Preconditions.checkArgument(d > 0, "The number of SipRound iterations (d=%s) during Finalization must be positive.", d);
        this.c = c;
        this.d = d;
        this.k0 = k0;
        this.k1 = k2;
    }
    
    @Override
    public int bits() {
        return 64;
    }
    
    @Override
    public Hasher newHasher() {
        return new SipHasher(this.c, this.d, this.k0, this.k1);
    }
    
    @Override
    public String toString() {
        return "Hashing.sipHash" + this.c + "" + this.d + "(" + this.k0 + ", " + this.k1 + ")";
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        if (o instanceof SipHashFunction) {
            final SipHashFunction sipHashFunction = (SipHashFunction)o;
            return this.c == sipHashFunction.c && this.d == sipHashFunction.d && this.k0 == sipHashFunction.k0 && this.k1 == sipHashFunction.k1;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return (int)((long)(this.getClass().hashCode() ^ this.c ^ this.d) ^ this.k0 ^ this.k1);
    }
    
    private static final class SipHasher extends AbstractStreamingHasher
    {
        private static final int CHUNK_SIZE = 8;
        private final int c;
        private final int d;
        private long v0;
        private long v1;
        private long v2;
        private long v3;
        private long b;
        private long finalM;
        
        SipHasher(final int c, final int d, final long n, final long n2) {
            super(8);
            this.v0 = 8317987319222330741L;
            this.v1 = 7237128888997146477L;
            this.v2 = 7816392313619706465L;
            this.v3 = 8387220255154660723L;
            this.b = 0L;
            this.finalM = 0L;
            this.c = c;
            this.d = d;
            this.v0 ^= n;
            this.v1 ^= n2;
            this.v2 ^= n;
            this.v3 ^= n2;
        }
        
        @Override
        protected void process(final ByteBuffer byteBuffer) {
            this.b += 8L;
            this.processM(byteBuffer.getLong());
        }
        
        @Override
        protected void processRemaining(final ByteBuffer byteBuffer) {
            this.b += byteBuffer.remaining();
            while (byteBuffer.hasRemaining()) {
                this.finalM ^= ((long)byteBuffer.get() & 0xFFL) << 0;
                final int n;
                n += 8;
            }
        }
        
        public HashCode makeHash() {
            this.processM(this.finalM ^= this.b << 56);
            this.v2 ^= 0xFFL;
            this.sipRound(this.d);
            return HashCode.fromLong(this.v0 ^ this.v1 ^ this.v2 ^ this.v3);
        }
        
        private void processM(final long n) {
            this.v3 ^= n;
            this.sipRound(this.c);
            this.v0 ^= n;
        }
        
        private void sipRound(final int n) {
            while (0 < n) {
                this.v0 += this.v1;
                this.v2 += this.v3;
                this.v1 = Long.rotateLeft(this.v1, 13);
                this.v3 = Long.rotateLeft(this.v3, 16);
                this.v1 ^= this.v0;
                this.v3 ^= this.v2;
                this.v0 = Long.rotateLeft(this.v0, 32);
                this.v2 += this.v1;
                this.v0 += this.v3;
                this.v1 = Long.rotateLeft(this.v1, 17);
                this.v3 = Long.rotateLeft(this.v3, 21);
                this.v1 ^= this.v2;
                this.v3 ^= this.v0;
                this.v2 = Long.rotateLeft(this.v2, 32);
                int n2 = 0;
                ++n2;
            }
        }
    }
}
