package io.netty.handler.codec.spdy;

import io.netty.util.internal.*;
import io.netty.buffer.*;
import io.netty.util.*;

public class DefaultSpdyDataFrame extends DefaultSpdyStreamFrame implements SpdyDataFrame
{
    private final ByteBuf data;
    
    public DefaultSpdyDataFrame(final int n) {
        this(n, Unpooled.buffer(0));
    }
    
    public DefaultSpdyDataFrame(final int n, final ByteBuf byteBuf) {
        super(n);
        if (byteBuf == null) {
            throw new NullPointerException("data");
        }
        this.data = validate(byteBuf);
    }
    
    private static ByteBuf validate(final ByteBuf byteBuf) {
        if (byteBuf.readableBytes() > 16777215) {
            throw new IllegalArgumentException("data payload cannot exceed 16777215 bytes");
        }
        return byteBuf;
    }
    
    @Override
    public SpdyDataFrame setStreamId(final int streamId) {
        super.setStreamId(streamId);
        return this;
    }
    
    @Override
    public SpdyDataFrame setLast(final boolean last) {
        super.setLast(last);
        return this;
    }
    
    @Override
    public ByteBuf content() {
        if (this.data.refCnt() <= 0) {
            throw new IllegalReferenceCountException(this.data.refCnt());
        }
        return this.data;
    }
    
    @Override
    public SpdyDataFrame copy() {
        final DefaultSpdyDataFrame defaultSpdyDataFrame = new DefaultSpdyDataFrame(this.streamId(), this.content().copy());
        defaultSpdyDataFrame.setLast(this.isLast());
        return defaultSpdyDataFrame;
    }
    
    @Override
    public SpdyDataFrame duplicate() {
        final DefaultSpdyDataFrame defaultSpdyDataFrame = new DefaultSpdyDataFrame(this.streamId(), this.content().duplicate());
        defaultSpdyDataFrame.setLast(this.isLast());
        return defaultSpdyDataFrame;
    }
    
    @Override
    public int refCnt() {
        return this.data.refCnt();
    }
    
    @Override
    public SpdyDataFrame retain() {
        this.data.retain();
        return this;
    }
    
    @Override
    public SpdyDataFrame retain(final int n) {
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
        final StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(this));
        sb.append("(last: ");
        sb.append(this.isLast());
        sb.append(')');
        sb.append(StringUtil.NEWLINE);
        sb.append("--> Stream-ID = ");
        sb.append(this.streamId());
        sb.append(StringUtil.NEWLINE);
        sb.append("--> Size = ");
        if (this.refCnt() == 0) {
            sb.append("(freed)");
        }
        else {
            sb.append(this.content().readableBytes());
        }
        return sb.toString();
    }
    
    @Override
    public SpdyStreamFrame setLast(final boolean last) {
        return this.setLast(last);
    }
    
    @Override
    public SpdyStreamFrame setStreamId(final int streamId) {
        return this.setStreamId(streamId);
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
