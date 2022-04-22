package io.netty.handler.codec.http;

import io.netty.channel.*;

public final class HttpServerCodec extends CombinedChannelDuplexHandler
{
    public HttpServerCodec() {
        this(4096, 8192, 8192);
    }
    
    public HttpServerCodec(final int n, final int n2, final int n3) {
        super(new HttpRequestDecoder(n, n2, n3), new HttpResponseEncoder());
    }
    
    public HttpServerCodec(final int n, final int n2, final int n3, final boolean b) {
        super(new HttpRequestDecoder(n, n2, n3, b), new HttpResponseEncoder());
    }
}
