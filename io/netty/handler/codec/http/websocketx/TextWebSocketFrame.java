package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.*;
import io.netty.util.*;

public class TextWebSocketFrame extends WebSocketFrame
{
    public TextWebSocketFrame() {
        super(Unpooled.buffer(0));
    }
    
    public TextWebSocketFrame(final String s) {
        super(fromText(s));
    }
    
    public TextWebSocketFrame(final ByteBuf byteBuf) {
        super(byteBuf);
    }
    
    public TextWebSocketFrame(final boolean b, final int n, final String s) {
        super(b, n, fromText(s));
    }
    
    private static ByteBuf fromText(final String s) {
        if (s == null || s.isEmpty()) {
            return Unpooled.EMPTY_BUFFER;
        }
        return Unpooled.copiedBuffer(s, CharsetUtil.UTF_8);
    }
    
    public TextWebSocketFrame(final boolean b, final int n, final ByteBuf byteBuf) {
        super(b, n, byteBuf);
    }
    
    public String text() {
        return this.content().toString(CharsetUtil.UTF_8);
    }
    
    @Override
    public TextWebSocketFrame copy() {
        return new TextWebSocketFrame(this.isFinalFragment(), this.rsv(), this.content().copy());
    }
    
    @Override
    public TextWebSocketFrame duplicate() {
        return new TextWebSocketFrame(this.isFinalFragment(), this.rsv(), this.content().duplicate());
    }
    
    @Override
    public TextWebSocketFrame retain() {
        super.retain();
        return this;
    }
    
    @Override
    public TextWebSocketFrame retain(final int n) {
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
