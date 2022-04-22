package com.google.common.hash;

import com.google.common.base.*;
import java.nio.charset.*;
import java.io.*;

abstract class AbstractNonStreamingHashFunction implements HashFunction
{
    @Override
    public Hasher newHasher() {
        return new BufferingHasher(32);
    }
    
    @Override
    public Hasher newHasher(final int n) {
        Preconditions.checkArgument(n >= 0);
        return new BufferingHasher(n);
    }
    
    @Override
    public HashCode hashObject(final Object o, final Funnel funnel) {
        return this.newHasher().putObject(o, funnel).hash();
    }
    
    @Override
    public HashCode hashUnencodedChars(final CharSequence charSequence) {
        final int length = charSequence.length();
        final Hasher hasher = this.newHasher(length * 2);
        while (0 < length) {
            hasher.putChar(charSequence.charAt(0));
            int n = 0;
            ++n;
        }
        return hasher.hash();
    }
    
    @Override
    public HashCode hashString(final CharSequence charSequence, final Charset charset) {
        return this.hashBytes(charSequence.toString().getBytes(charset));
    }
    
    @Override
    public HashCode hashInt(final int n) {
        return this.newHasher(4).putInt(n).hash();
    }
    
    @Override
    public HashCode hashLong(final long n) {
        return this.newHasher(8).putLong(n).hash();
    }
    
    @Override
    public HashCode hashBytes(final byte[] array) {
        return this.hashBytes(array, 0, array.length);
    }
    
    private static final class ExposedByteArrayOutputStream extends ByteArrayOutputStream
    {
        ExposedByteArrayOutputStream(final int n) {
            super(n);
        }
        
        byte[] byteArray() {
            return this.buf;
        }
        
        int length() {
            return this.count;
        }
    }
    
    private final class BufferingHasher extends AbstractHasher
    {
        final ExposedByteArrayOutputStream stream;
        static final int BOTTOM_BYTE = 255;
        final AbstractNonStreamingHashFunction this$0;
        
        BufferingHasher(final AbstractNonStreamingHashFunction this$0, final int n) {
            this.this$0 = this$0;
            this.stream = new ExposedByteArrayOutputStream(n);
        }
        
        @Override
        public Hasher putByte(final byte b) {
            this.stream.write(b);
            return this;
        }
        
        @Override
        public Hasher putBytes(final byte[] array) {
            this.stream.write(array);
            return this;
        }
        
        @Override
        public Hasher putBytes(final byte[] array, final int n, final int n2) {
            this.stream.write(array, n, n2);
            return this;
        }
        
        @Override
        public Hasher putShort(final short n) {
            this.stream.write(n & 0xFF);
            this.stream.write(n >>> 8 & 0xFF);
            return this;
        }
        
        @Override
        public Hasher putInt(final int n) {
            this.stream.write(n & 0xFF);
            this.stream.write(n >>> 8 & 0xFF);
            this.stream.write(n >>> 16 & 0xFF);
            this.stream.write(n >>> 24 & 0xFF);
            return this;
        }
        
        @Override
        public Hasher putLong(final long n) {
            while (true) {
                this.stream.write((byte)(n >>> 0 & 0xFFL));
                final int n2;
                n2 += 8;
            }
        }
        
        @Override
        public Hasher putChar(final char c) {
            this.stream.write(c & '\u00ff');
            this.stream.write(c >>> 8 & 0xFF);
            return this;
        }
        
        @Override
        public Hasher putObject(final Object o, final Funnel funnel) {
            funnel.funnel(o, this);
            return this;
        }
        
        @Override
        public HashCode hash() {
            return this.this$0.hashBytes(this.stream.byteArray(), 0, this.stream.length());
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
