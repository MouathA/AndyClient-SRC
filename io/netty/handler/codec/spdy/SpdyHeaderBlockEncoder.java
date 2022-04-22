package io.netty.handler.codec.spdy;

import io.netty.util.internal.*;
import io.netty.buffer.*;

abstract class SpdyHeaderBlockEncoder
{
    static SpdyHeaderBlockEncoder newInstance(final SpdyVersion spdyVersion, final int n, final int n2, final int n3) {
        if (PlatformDependent.javaVersion() >= 7) {
            return new SpdyHeaderBlockZlibEncoder(spdyVersion, n);
        }
        return new SpdyHeaderBlockJZlibEncoder(spdyVersion, n, n2, n3);
    }
    
    abstract ByteBuf encode(final SpdyHeadersFrame p0) throws Exception;
    
    abstract void end();
}
