package com.google.common.hash;

import java.nio.charset.*;
import com.google.common.base.*;
import java.nio.*;

abstract class AbstractStreamingHashFunction implements HashFunction
{
    @Override
    public HashCode hashObject(final Object o, final Funnel funnel) {
        return this.newHasher().putObject(o, funnel).hash();
    }
    
    @Override
    public HashCode hashUnencodedChars(final CharSequence charSequence) {
        return this.newHasher().putUnencodedChars(charSequence).hash();
    }
    
    @Override
    public HashCode hashString(final CharSequence charSequence, final Charset charset) {
        return this.newHasher().putString(charSequence, charset).hash();
    }
    
    @Override
    public HashCode hashInt(final int n) {
        return this.newHasher().putInt(n).hash();
    }
    
    @Override
    public HashCode hashLong(final long n) {
        return this.newHasher().putLong(n).hash();
    }
    
    @Override
    public HashCode hashBytes(final byte[] array) {
        return this.newHasher().putBytes(array).hash();
    }
    
    @Override
    public HashCode hashBytes(final byte[] array, final int n, final int n2) {
        return this.newHasher().putBytes(array, n, n2).hash();
    }
    
    @Override
    public Hasher newHasher(final int n) {
        Preconditions.checkArgument(n >= 0);
        return this.newHasher();
    }
    
    protected abstract static class AbstractStreamingHasher extends AbstractHasher
    {
        private final ByteBuffer buffer;
        private final int bufferSize;
        private final int chunkSize;
        
        protected AbstractStreamingHasher(final int n) {
            this(n, n);
        }
        
        protected AbstractStreamingHasher(final int chunkSize, final int bufferSize) {
            Preconditions.checkArgument(bufferSize % chunkSize == 0);
            this.buffer = ByteBuffer.allocate(bufferSize + 7).order(ByteOrder.LITTLE_ENDIAN);
            this.bufferSize = bufferSize;
            this.chunkSize = chunkSize;
        }
        
        protected abstract void process(final ByteBuffer p0);
        
        protected void processRemaining(final ByteBuffer byteBuffer) {
            byteBuffer.position(byteBuffer.limit());
            byteBuffer.limit(this.chunkSize + 7);
            while (byteBuffer.position() < this.chunkSize) {
                byteBuffer.putLong(0L);
            }
            byteBuffer.limit(this.chunkSize);
            byteBuffer.flip();
            this.process(byteBuffer);
        }
        
        @Override
        public final Hasher putBytes(final byte[] array) {
            return this.putBytes(array, 0, array.length);
        }
        
        @Override
        public final Hasher putBytes(final byte[] array, final int n, final int n2) {
            return this.putBytes(ByteBuffer.wrap(array, n, n2).order(ByteOrder.LITTLE_ENDIAN));
        }
        
        private Hasher putBytes(final ByteBuffer byteBuffer) {
            if (byteBuffer.remaining() <= this.buffer.remaining()) {
                this.buffer.put(byteBuffer);
                this.munchIfFull();
                return this;
            }
            while (0 < this.bufferSize - this.buffer.position()) {
                this.buffer.put(byteBuffer.get());
                int n = 0;
                ++n;
            }
            this.munch();
            while (byteBuffer.remaining() >= this.chunkSize) {
                this.process(byteBuffer);
            }
            this.buffer.put(byteBuffer);
            return this;
        }
        
        @Override
        public final Hasher putUnencodedChars(final CharSequence charSequence) {
            while (0 < charSequence.length()) {
                this.putChar(charSequence.charAt(0));
                int n = 0;
                ++n;
            }
            return this;
        }
        
        @Override
        public final Hasher putByte(final byte b) {
            this.buffer.put(b);
            this.munchIfFull();
            return this;
        }
        
        @Override
        public final Hasher putShort(final short n) {
            this.buffer.putShort(n);
            this.munchIfFull();
            return this;
        }
        
        @Override
        public final Hasher putChar(final char c) {
            this.buffer.putChar(c);
            this.munchIfFull();
            return this;
        }
        
        @Override
        public final Hasher putInt(final int n) {
            this.buffer.putInt(n);
            this.munchIfFull();
            return this;
        }
        
        @Override
        public final Hasher putLong(final long n) {
            this.buffer.putLong(n);
            this.munchIfFull();
            return this;
        }
        
        @Override
        public final Hasher putObject(final Object o, final Funnel funnel) {
            funnel.funnel(o, this);
            return this;
        }
        
        @Override
        public final HashCode hash() {
            this.munch();
            this.buffer.flip();
            if (this.buffer.remaining() > 0) {
                this.processRemaining(this.buffer);
            }
            return this.makeHash();
        }
        
        abstract HashCode makeHash();
        
        private void munchIfFull() {
            if (this.buffer.remaining() < 8) {
                this.munch();
            }
        }
        
        private void munch() {
            this.buffer.flip();
            while (this.buffer.remaining() >= this.chunkSize) {
                this.process(this.buffer);
            }
            this.buffer.compact();
        }
        
        @Override
        public PrimitiveSink putUnencodedChars(final CharSequence charSequence) {
            return this.putUnencodedChars(charSequence);
        }
        
        @Override
        public PrimitiveSink putChar(final char c) {
            return this.putChar(c);
        }
        
        @Override
        public PrimitiveSink putLong(final long n) {
            return this.putLong(n);
        }
        
        @Override
        public PrimitiveSink putInt(final int n) {
            return this.putInt(n);
        }
        
        @Override
        public PrimitiveSink putShort(final short n) {
            return this.putShort(n);
        }
        
        @Override
        public PrimitiveSink putBytes(final byte[] array, final int n, final int n2) {
            return this.putBytes(array, n, n2);
        }
        
        @Override
        public PrimitiveSink putBytes(final byte[] array) {
            return this.putBytes(array);
        }
        
        @Override
        public PrimitiveSink putByte(final byte b) {
            return this.putByte(b);
        }
    }
}
