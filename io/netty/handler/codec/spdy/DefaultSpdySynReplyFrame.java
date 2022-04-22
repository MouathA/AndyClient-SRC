package io.netty.handler.codec.spdy;

import io.netty.util.internal.*;

public class DefaultSpdySynReplyFrame extends DefaultSpdyHeadersFrame implements SpdySynReplyFrame
{
    public DefaultSpdySynReplyFrame(final int n) {
        super(n);
    }
    
    @Override
    public SpdySynReplyFrame setStreamId(final int streamId) {
        super.setStreamId(streamId);
        return this;
    }
    
    @Override
    public SpdySynReplyFrame setLast(final boolean last) {
        super.setLast(last);
        return this;
    }
    
    @Override
    public SpdySynReplyFrame setInvalid() {
        super.setInvalid();
        return this;
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
    
    @Override
    public SpdyHeadersFrame setInvalid() {
        return this.setInvalid();
    }
    
    @Override
    public SpdyHeadersFrame setLast(final boolean last) {
        return this.setLast(last);
    }
    
    @Override
    public SpdyHeadersFrame setStreamId(final int streamId) {
        return this.setStreamId(streamId);
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
