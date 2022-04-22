package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.*;
import io.netty.util.*;

public class BinaryWebSocketFrame extends WebSocketFrame
{
    public BinaryWebSocketFrame() {
        super(Unpooled.buffer(0));
    }
    
    public BinaryWebSocketFrame(final ByteBuf byteBuf) {
        super(byteBuf);
    }
    
    public BinaryWebSocketFrame(final boolean b, final int n, final ByteBuf byteBuf) {
        super(b, n, byteBuf);
    }
    
    @Override
    public BinaryWebSocketFrame copy() {
        return new BinaryWebSocketFrame(this.isFinalFragment(), this.rsv(), this.content().copy());
    }
    
    @Override
    public BinaryWebSocketFrame duplicate() {
        return new BinaryWebSocketFrame(this.isFinalFragment(), this.rsv(), this.content().duplicate());
    }
    
    @Override
    public BinaryWebSocketFrame retain() {
        super.retain();
        return this;
    }
    
    @Override
    public BinaryWebSocketFrame retain(final int n) {
        super.retain(n);
        return this;
    }
    
    @Override
    public WebSocketFrame retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public WebSocketFrame retain() {
        return this.retain();
    }
    
    @Override
    public WebSocketFrame duplicate() {
        return this.duplicate();
    }
    
    @Override
    public WebSocketFrame copy() {
        return this.copy();
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
