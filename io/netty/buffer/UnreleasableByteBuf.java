package io.netty.buffer;

import java.nio.*;
import io.netty.util.*;

final class UnreleasableByteBuf extends WrappedByteBuf
{
    private SwappedByteBuf swappedBuf;
    
    UnreleasableByteBuf(final ByteBuf byteBuf) {
        super(byteBuf);
    }
    
    @Override
    public ByteBuf order(final ByteOrder byteOrder) {
        if (byteOrder == null) {
            throw new NullPointerException("endianness");
        }
        if (byteOrder == this.order()) {
            return this;
        }
        SwappedByteBuf swappedBuf = this.swappedBuf;
        if (swappedBuf == null) {
            swappedBuf = (this.swappedBuf = new SwappedByteBuf(this));
        }
        return swappedBuf;
    }
    
    @Override
    public ByteBuf readSlice(final int n) {
        return new UnreleasableByteBuf(this.buf.readSlice(n));
    }
    
    @Override
    public ByteBuf slice() {
        return new UnreleasableByteBuf(this.buf.slice());
    }
    
    @Override
    public ByteBuf slice(final int n, final int n2) {
        return new UnreleasableByteBuf(this.buf.slice(n, n2));
    }
    
    @Override
    public ByteBuf duplicate() {
        return new UnreleasableByteBuf(this.buf.duplicate());
    }
    
    @Override
    public ByteBuf retain(final int n) {
        return this;
    }
    
    @Override
    public ByteBuf retain() {
        return this;
    }
    
    @Override
    public boolean release() {
        return false;
    }
    
    @Override
    public boolean release(final int n) {
        return false;
    }
    
    @Override
    public ReferenceCounted retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }
}
