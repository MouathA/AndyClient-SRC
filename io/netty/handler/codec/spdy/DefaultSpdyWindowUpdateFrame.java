package io.netty.handler.codec.spdy;

import io.netty.util.internal.*;

public class DefaultSpdyWindowUpdateFrame implements SpdyWindowUpdateFrame
{
    private int streamId;
    private int deltaWindowSize;
    
    public DefaultSpdyWindowUpdateFrame(final int streamId, final int deltaWindowSize) {
        this.setStreamId(streamId);
        this.setDeltaWindowSize(deltaWindowSize);
    }
    
    @Override
    public int streamId() {
        return this.streamId;
    }
    
    @Override
    public SpdyWindowUpdateFrame setStreamId(final int streamId) {
        if (streamId < 0) {
            throw new IllegalArgumentException("Stream-ID cannot be negative: " + streamId);
        }
        this.streamId = streamId;
        return this;
    }
    
    @Override
    public int deltaWindowSize() {
        return this.deltaWindowSize;
    }
    
    @Override
    public SpdyWindowUpdateFrame setDeltaWindowSize(final int deltaWindowSize) {
        if (deltaWindowSize <= 0) {
            throw new IllegalArgumentException("Delta-Window-Size must be positive: " + deltaWindowSize);
        }
        this.deltaWindowSize = deltaWindowSize;
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
        sb.append("--> Delta-Window-Size = ");
        sb.append(this.deltaWindowSize());
        return sb.toString();
    }
}
