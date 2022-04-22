package io.netty.handler.codec.spdy;

import io.netty.util.internal.*;

public class DefaultSpdyRstStreamFrame extends DefaultSpdyStreamFrame implements SpdyRstStreamFrame
{
    private SpdyStreamStatus status;
    
    public DefaultSpdyRstStreamFrame(final int n, final int n2) {
        this(n, SpdyStreamStatus.valueOf(n2));
    }
    
    public DefaultSpdyRstStreamFrame(final int n, final SpdyStreamStatus status) {
        super(n);
        this.setStatus(status);
    }
    
    @Override
    public SpdyRstStreamFrame setStreamId(final int streamId) {
        super.setStreamId(streamId);
        return this;
    }
    
    @Override
    public SpdyRstStreamFrame setLast(final boolean last) {
        super.setLast(last);
        return this;
    }
    
    @Override
    public SpdyStreamStatus status() {
        return this.status;
    }
    
    @Override
    public SpdyRstStreamFrame setStatus(final SpdyStreamStatus status) {
        this.status = status;
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(this));
        sb.append(StringUtil.NEWLINE);
        sb.append("--> Stream-ID = ");
        sb.append(this.streamId());
        sb.append(StringUtil.NEWLINE);
        sb.append("--> Status: ");
        sb.append(this.status());
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
}
