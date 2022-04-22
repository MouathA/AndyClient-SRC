package io.netty.handler.codec.spdy;

import io.netty.buffer.*;

abstract class SpdyHeaderBlockDecoder
{
    static SpdyHeaderBlockDecoder newInstance(final SpdyVersion spdyVersion, final int n) {
        return new SpdyHeaderBlockZlibDecoder(spdyVersion, n);
    }
    
    abstract void decode(final ByteBuf p0, final SpdyHeadersFrame p1) throws Exception;
    
    abstract void endHeaderBlock(final SpdyHeadersFrame p0) throws Exception;
    
    abstract void end();
}
