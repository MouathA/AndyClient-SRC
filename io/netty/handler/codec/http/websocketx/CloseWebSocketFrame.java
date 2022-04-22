package io.netty.handler.codec.http.websocketx;

import io.netty.util.internal.*;
import io.netty.buffer.*;
import io.netty.util.*;

public class CloseWebSocketFrame extends WebSocketFrame
{
    public CloseWebSocketFrame() {
        super(Unpooled.buffer(0));
    }
    
    public CloseWebSocketFrame(final int n, final String s) {
        this(true, 0, n, s);
    }
    
    public CloseWebSocketFrame(final boolean b, final int n) {
        this(b, n, Unpooled.buffer(0));
    }
    
    public CloseWebSocketFrame(final boolean b, final int n, final int n2, final String s) {
        super(b, n, newBinaryData(n2, s));
    }
    
    private static ByteBuf newBinaryData(final int n, final String s) {
        byte[] array = EmptyArrays.EMPTY_BYTES;
        if (s != null) {
            array = s.getBytes(CharsetUtil.UTF_8);
        }
        final ByteBuf buffer = Unpooled.buffer(2 + array.length);
        buffer.writeShort(n);
        if (array.length > 0) {
            buffer.writeBytes(array);
        }
        buffer.readerIndex(0);
        return buffer;
    }
    
    public CloseWebSocketFrame(final boolean b, final int n, final ByteBuf byteBuf) {
        super(b, n, byteBuf);
    }
    
    public int statusCode() {
        final ByteBuf content = this.content();
        if (content == null || content.capacity() == 0) {
            return -1;
        }
        content.readerIndex(0);
        final short short1 = content.readShort();
        content.readerIndex(0);
        return short1;
    }
    
    public String reasonText() {
        final ByteBuf content = this.content();
        if (content == null || content.capacity() <= 2) {
            return "";
        }
        content.readerIndex(2);
        final String string = content.toString(CharsetUtil.UTF_8);
        content.readerIndex(0);
        return string;
    }
    
    @Override
    public CloseWebSocketFrame copy() {
        return new CloseWebSocketFrame(this.isFinalFragment(), this.rsv(), this.content().copy());
    }
    
    @Override
    public CloseWebSocketFrame duplicate() {
        return new CloseWebSocketFrame(this.isFinalFragment(), this.rsv(), this.content().duplicate());
    }
    
    @Override
    public CloseWebSocketFrame retain() {
        super.retain();
        return this;
    }
    
    @Override
    public CloseWebSocketFrame retain(final int n) {
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
