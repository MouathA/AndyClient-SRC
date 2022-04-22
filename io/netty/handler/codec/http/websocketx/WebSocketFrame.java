package io.netty.handler.codec.http.websocketx;

import io.netty.util.internal.*;
import io.netty.buffer.*;
import io.netty.util.*;

public abstract class WebSocketFrame extends DefaultByteBufHolder
{
    private final boolean finalFragment;
    private final int rsv;
    
    protected WebSocketFrame(final ByteBuf byteBuf) {
        this(true, 0, byteBuf);
    }
    
    protected WebSocketFrame(final boolean finalFragment, final int rsv, final ByteBuf byteBuf) {
        super(byteBuf);
        this.finalFragment = finalFragment;
        this.rsv = rsv;
    }
    
    public boolean isFinalFragment() {
        return this.finalFragment;
    }
    
    public int rsv() {
        return this.rsv;
    }
    
    @Override
    public abstract WebSocketFrame copy();
    
    @Override
    public abstract WebSocketFrame duplicate();
    
    @Override
    public String toString() {
        return StringUtil.simpleClassName(this) + "(data: " + this.content().toString() + ')';
    }
    
    @Override
    public WebSocketFrame retain() {
        super.retain();
        return this;
    }
    
    @Override
    public WebSocketFrame retain(final int n) {
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
