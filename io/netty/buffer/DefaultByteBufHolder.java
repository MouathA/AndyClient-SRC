package io.netty.buffer;

import io.netty.util.internal.*;
import io.netty.util.*;

public class DefaultByteBufHolder implements ByteBufHolder
{
    private final ByteBuf data;
    
    public DefaultByteBufHolder(final ByteBuf data) {
        if (data == null) {
            throw new NullPointerException("data");
        }
        this.data = data;
    }
    
    @Override
    public ByteBuf content() {
        if (this.data.refCnt() <= 0) {
            throw new IllegalReferenceCountException(this.data.refCnt());
        }
        return this.data;
    }
    
    @Override
    public ByteBufHolder copy() {
        return new DefaultByteBufHolder(this.data.copy());
    }
    
    @Override
    public ByteBufHolder duplicate() {
        return new DefaultByteBufHolder(this.data.duplicate());
    }
    
    @Override
    public int refCnt() {
        return this.data.refCnt();
    }
    
    @Override
    public ByteBufHolder retain() {
        this.data.retain();
        return this;
    }
    
    @Override
    public ByteBufHolder retain(final int n) {
        this.data.retain(n);
        return this;
    }
    
    @Override
    public boolean release() {
        return this.data.release();
    }
    
    @Override
    public boolean release(final int n) {
        return this.data.release(n);
    }
    
    @Override
    public String toString() {
        return StringUtil.simpleClassName(this) + '(' + this.content().toString() + ')';
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
