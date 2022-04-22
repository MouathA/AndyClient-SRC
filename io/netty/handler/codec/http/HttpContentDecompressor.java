package io.netty.handler.codec.http;

import io.netty.channel.embedded.*;
import io.netty.channel.*;
import io.netty.handler.codec.compression.*;

public class HttpContentDecompressor extends HttpContentDecoder
{
    private final boolean strict;
    
    public HttpContentDecompressor() {
        this(false);
    }
    
    public HttpContentDecompressor(final boolean strict) {
        this.strict = strict;
    }
    
    @Override
    protected EmbeddedChannel newContentDecoder(final String s) throws Exception {
        if ("gzip".equalsIgnoreCase(s) || "x-gzip".equalsIgnoreCase(s)) {
            return new EmbeddedChannel(new ChannelHandler[] { ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP) });
        }
        if ("deflate".equalsIgnoreCase(s) || "x-deflate".equalsIgnoreCase(s)) {
            ZlibWrapper zlibWrapper;
            if (this.strict) {
                zlibWrapper = ZlibWrapper.ZLIB;
            }
            else {
                zlibWrapper = ZlibWrapper.ZLIB_OR_NONE;
            }
            return new EmbeddedChannel(new ChannelHandler[] { ZlibCodecFactory.newZlibDecoder(zlibWrapper) });
        }
        return null;
    }
}
