package io.netty.handler.codec.spdy;

import io.netty.util.internal.*;

public class DefaultSpdyGoAwayFrame implements SpdyGoAwayFrame
{
    private int lastGoodStreamId;
    private SpdySessionStatus status;
    
    public DefaultSpdyGoAwayFrame(final int n) {
        this(n, 0);
    }
    
    public DefaultSpdyGoAwayFrame(final int n, final int n2) {
        this(n, SpdySessionStatus.valueOf(n2));
    }
    
    public DefaultSpdyGoAwayFrame(final int lastGoodStreamId, final SpdySessionStatus status) {
        this.setLastGoodStreamId(lastGoodStreamId);
        this.setStatus(status);
    }
    
    @Override
    public int lastGoodStreamId() {
        return this.lastGoodStreamId;
    }
    
    @Override
    public SpdyGoAwayFrame setLastGoodStreamId(final int lastGoodStreamId) {
        if (lastGoodStreamId < 0) {
            throw new IllegalArgumentException("Last-good-stream-ID cannot be negative: " + lastGoodStreamId);
        }
        this.lastGoodStreamId = lastGoodStreamId;
        return this;
    }
    
    @Override
    public SpdySessionStatus status() {
        return this.status;
    }
    
    @Override
    public SpdyGoAwayFrame setStatus(final SpdySessionStatus status) {
        this.status = status;
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(this));
        sb.append(StringUtil.NEWLINE);
        sb.append("--> Last-good-stream-ID = ");
        sb.append(this.lastGoodStreamId());
        sb.append(StringUtil.NEWLINE);
        sb.append("--> Status: ");
        sb.append(this.status());
        return sb.toString();
    }
}
