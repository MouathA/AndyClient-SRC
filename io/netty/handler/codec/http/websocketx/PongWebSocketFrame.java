package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.*;
import io.netty.util.*;

public class PongWebSocketFrame extends WebSocketFrame
{
    public PongWebSocketFrame() {
        super(Unpooled.buffer(0));
    }
    
    public PongWebSocketFrame(final ByteBuf byteBuf) {
        super(byteBuf);
    }
    
    public PongWebSocketFrame(final boolean b, final int n, final ByteBuf byteBuf) {
        super(b, n, byteBuf);
    }
    
    @Override
    public PongWebSocketFrame copy() {
        return new PongWebSocketFrame(this.isFinalFragment(), this.rsv(), this.content().copy());
    }
    
    @Override
    public PongWebSocketFrame duplicate() {
        return new PongWebSocketFrame(this.isFinalFragment(), this.rsv(), this.content().duplicate());
    }
    
    @Override
    public PongWebSocketFrame retain() {
        super.retain();
        return this;
    }
    
    @Override
    public PongWebSocketFrame retain(final int n) {
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