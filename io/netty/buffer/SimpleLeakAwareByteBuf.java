package io.netty.buffer;

import io.netty.util.*;
import java.nio.*;

final class SimpleLeakAwareByteBuf extends WrappedByteBuf
{
    private final ResourceLeak leak;
    
    SimpleLeakAwareByteBuf(final ByteBuf byteBuf, final ResourceLeak leak) {
        super(byteBuf);
        this.leak = leak;
    }
    
    @Override
    public boolean release() {
        final boolean release = super.release();
        if (release) {
            this.leak.close();
        }
        return release;
    }
    
    @Override
    public boolean release(final int n) {
        final boolean release = super.release(n);
        if (release) {
            this.leak.close();
        }
        return release;
    }
    
    @Override
    public ByteBuf order(final ByteOrder byteOrder) {
        this.leak.record();
        if (this.order() == byteOrder) {
            return this;
        }
        return new SimpleLeakAwareByteBuf(super.order(byteOrder), this.leak);
    }
    
    @Override
    public ByteBuf slice() {
        return new SimpleLeakAwareByteBuf(super.slice(), this.leak);
    }
    
    @Override
    public ByteBuf slice(final int n, final int n2) {
        return new SimpleLeakAwareByteBuf(super.slice(n, n2), this.leak);
    }
    
    @Override
    public ByteBuf duplicate() {
        return new SimpleLeakAwareByteBuf(super.duplicate(), this.leak);
    }
    
    @Override
    public ByteBuf readSlice(final int n) {
        return new SimpleLeakAwareByteBuf(super.readSlice(n), this.leak);
    }
}
