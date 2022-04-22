package io.netty.handler.codec.spdy;

import io.netty.util.internal.*;

public class DefaultSpdySynStreamFrame extends DefaultSpdyHeadersFrame implements SpdySynStreamFrame
{
    private int associatedStreamId;
    private byte priority;
    private boolean unidirectional;
    
    public DefaultSpdySynStreamFrame(final int n, final int associatedStreamId, final byte priority) {
        super(n);
        this.setAssociatedStreamId(associatedStreamId);
        this.setPriority(priority);
    }
    
    @Override
    public SpdySynStreamFrame setStreamId(final int streamId) {
        super.setStreamId(streamId);
        return this;
    }
    
    @Override
    public SpdySynStreamFrame setLast(final boolean last) {
        super.setLast(last);
        return this;
    }
    
    @Override
    public SpdySynStreamFrame setInvalid() {
        super.setInvalid();
        return this;
    }
    
    @Override
    public int associatedStreamId() {
        return this.associatedStreamId;
    }
    
    @Override
    public SpdySynStreamFrame setAssociatedStreamId(final int associatedStreamId) {
        if (associatedStreamId < 0) {
            throw new IllegalArgumentException("Associated-To-Stream-ID cannot be negative: " + associatedStreamId);
        }
        this.associatedStreamId = associatedStreamId;
        return this;
    }
    
    @Override
    public byte priority() {
        return this.priority;
    }
    
    @Override
    public SpdySynStreamFrame setPriority(final byte priority) {
        if (priority < 0 || priority > 7) {
            throw new IllegalArgumentException("Priority must be between 0 and 7 inclusive: " + priority);
        }
        this.priority = priority;
        return this;
    }
    
    @Override
    public boolean isUnidirectional() {
        return this.unidirectional;
    }
    
    @Override
    public SpdySynStreamFrame setUnidirectional(final boolean unidirectional) {
        this.unidirectional = unidirectional;
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(this));
        sb.append("(last: ");
        sb.append(this.isLast());
        sb.append("; unidirectional: ");
        sb.append(this.isUnidirectional());
        sb.append(')');
        sb.append(StringUtil.NEWLINE);
        sb.append("--> Stream-ID = ");
        sb.append(this.streamId());
        sb.append(StringUtil.NEWLINE);
        if (this.associatedStreamId != 0) {
            sb.append("--> Associated-To-Stream-ID = ");
            sb.append(this.associatedStreamId());
            sb.append(StringUtil.NEWLINE);
        }
        sb.append("--> Priority = ");
        sb.append(this.priority());
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
