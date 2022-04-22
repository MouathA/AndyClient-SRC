package io.netty.channel.udt;

import io.netty.buffer.*;
import io.netty.util.*;

public final class UdtMessage extends DefaultByteBufHolder
{
    public UdtMessage(final ByteBuf byteBuf) {
        super(byteBuf);
    }
    
    @Override
    public UdtMessage copy() {
        return new UdtMessage(this.content().copy());
    }
    
    @Override
    public UdtMessage duplicate() {
        return new UdtMessage(this.content().duplicate());
    }
    
    @Override
    public UdtMessage retain() {
        super.retain();
        return this;
    }
    
    @Override
    public UdtMessage retain(final int n) {
        super.retain(n);
        return this;
    }
    
    @Override
    public ByteBufHolder retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public ByteBufHolder retain() {
        return this.retain();
    }
    
    @Override
    public ByteBufHolder duplicate() {
        return this.duplicate();
    }
    
    @Override
    public ByteBufHolder copy() {
        return this.copy();
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
