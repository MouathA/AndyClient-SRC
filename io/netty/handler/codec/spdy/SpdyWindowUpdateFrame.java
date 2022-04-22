package io.netty.handler.codec.spdy;

public interface SpdyWindowUpdateFrame extends SpdyFrame
{
    int streamId();
    
    SpdyWindowUpdateFrame setStreamId(final int p0);
    
    int deltaWindowSize();
    
    SpdyWindowUpdateFrame setDeltaWindowSize(final int p0);
}
