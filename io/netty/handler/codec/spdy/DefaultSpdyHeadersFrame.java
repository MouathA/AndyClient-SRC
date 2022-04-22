package io.netty.handler.codec.spdy;

import io.netty.util.internal.*;
import java.util.*;

public class DefaultSpdyHeadersFrame extends DefaultSpdyStreamFrame implements SpdyHeadersFrame
{
    private boolean invalid;
    private boolean truncated;
    private final SpdyHeaders headers;
    
    public DefaultSpdyHeadersFrame(final int n) {
        super(n);
        this.headers = new DefaultSpdyHeaders();
    }
    
    @Override
    public SpdyHeadersFrame setStreamId(final int streamId) {
        super.setStreamId(streamId);
        return this;
    }
    
    @Override
    public SpdyHeadersFrame setLast(final boolean last) {
        super.setLast(last);
        return this;
    }
    
    @Override
    public boolean isInvalid() {
        return this.invalid;
    }
    
    @Override
    public SpdyHeadersFrame setInvalid() {
        this.invalid = true;
        return this;
    }
    
    @Override
    public boolean isTruncated() {
        return this.truncated;
    }
    
    @Override
    public SpdyHeadersFrame setTruncated() {
        this.truncated = true;
        return this;
    }
    
    @Override
    public SpdyHeaders headers() {
        return this.headers;
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
        sb.append("--> Headers:");
        sb.append(StringUtil.NEWLINE);
        this.appendHeaders(sb);
        sb.setLength(sb.length() - StringUtil.NEWLINE.length());
        return sb.toString();
    }
    
    protected void appendHeaders(final StringBuilder sb) {
        for (final Map.Entry<String, V> entry : this.headers()) {
            sb.append("    ");
            sb.append(entry.getKey());
            sb.append(": ");
            sb.append((String)entry.getValue());
            sb.append(StringUtil.NEWLINE);
        }
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
