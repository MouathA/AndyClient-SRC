package io.netty.buffer;

import java.nio.*;
import io.netty.util.*;

public abstract class AbstractDerivedByteBuf extends AbstractByteBuf
{
    protected AbstractDerivedByteBuf(final int n) {
        super(n);
    }
    
    @Override
    public final int refCnt() {
        return this.unwrap().refCnt();
    }
    
    @Override
    public final ByteBuf retain() {
        this.unwrap().retain();
        return this;
    }
    
    @Override
    public final ByteBuf retain(final int n) {
        this.unwrap().retain(n);
        return this;
    }
    
    @Override
    public final boolean release() {
        return this.unwrap().release();
    }
    
    @Override
    public final boolean release(final int n) {
        return this.unwrap().release(n);
    }
    
    @Override
    public ByteBuffer internalNioBuffer(final int n, final int n2) {
        return this.nioBuffer(n, n2);
    }
    
    @Override
    public ByteBuffer nioBuffer(final int n, final int n2) {
        return this.unwrap().nioBuffer(n, n2);
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
