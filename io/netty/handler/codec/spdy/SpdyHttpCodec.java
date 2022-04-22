package io.netty.handler.codec.spdy;

import io.netty.channel.*;

public final class SpdyHttpCodec extends CombinedChannelDuplexHandler
{
    public SpdyHttpCodec(final SpdyVersion spdyVersion, final int n) {
        super(new SpdyHttpDecoder(spdyVersion, n), new SpdyHttpEncoder(spdyVersion));
    }
    
    public SpdyHttpCodec(final SpdyVersion spdyVersion, final int n, final boolean b) {
        super(new SpdyHttpDecoder(spdyVersion, n, b), new SpdyHttpEncoder(spdyVersion));
    }
}
