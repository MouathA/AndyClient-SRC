package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.*;
import io.netty.util.*;

public class PingWebSocketFrame extends WebSocketFrame
{
    public PingWebSocketFrame() {
        super(true, 0, Unpooled.buffer(0));
    }
    
    public PingWebSocketFrame(final ByteBuf byteBuf) {
        super(byteBuf);
    }
    
    public PingWebSocketFrame(final boolean b, final int n, final ByteBuf byteBuf) {
        super(b, n, byteBuf);
    }
    
    @Override
    public PingWebSocketFrame copy() {
        return new PingWebSocketFrame(this.isFinalFragment(), this.rsv(), this.content().copy());
    }
    
    @Override
    public PingWebSocketFrame duplicate() {
        return new PingWebSocketFrame(this.isFinalFragment(), this.rsv(), this.content().duplicate());
    }
    
    @Override
    public PingWebSocketFrame retain() {
        super.retain();
        return this;
    }
    
    @Override
    public PingWebSocketFrame retain(final int n) {
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
