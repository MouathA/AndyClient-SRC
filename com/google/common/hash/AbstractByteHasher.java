package com.google.common.hash;

import java.nio.*;
import com.google.common.base.*;

abstract class AbstractByteHasher extends AbstractHasher
{
    private final ByteBuffer scratch;
    
    AbstractByteHasher() {
        this.scratch = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
    }
    
    protected abstract void update(final byte p0);
    
    protected void update(final byte[] array) {
        this.update(array, 0, array.length);
    }
    
    protected void update(final byte[] array, final int n, final int n2) {
        for (int i = n; i < n + n2; ++i) {
            this.update(array[i]);
        }
    }
    
    @Override
    public Hasher putByte(final byte b) {
        this.update(b);
        return this;
    }
    
    @Override
    public Hasher putBytes(final byte[] array) {
        Preconditions.checkNotNull(array);
        this.update(array);
        return this;
    }
    
    @Override
    public Hasher putBytes(final byte[] array, final int n, final int n2) {
        Preconditions.checkPositionIndexes(n, n + n2, array.length);
        this.update(array, n, n2);
        return this;
    }
    
    private Hasher update(final int n) {
        this.update(this.scratch.array(), 0, n);
        this.scratch.clear();
        return this;
    }
    
    @Override
    public Hasher putShort(final short n) {
        this.scratch.putShort(n);
        return this.update(2);
    }
    
    @Override
    public Hasher putInt(final int n) {
        this.scratch.putInt(n);
        return this.update(4);
    }
    
    @Override
    public Hasher putLong(final long n) {
        this.scratch.putLong(n);
        return this.update(8);
    }
    
    @Override
    public Hasher putChar(final char c) {
        this.scratch.putChar(c);
        return this.update(2);
    }
    
    @Override
    public Hasher putObject(final Object o, final Funnel funnel) {
        funnel.funnel(o, this);
        return this;
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
