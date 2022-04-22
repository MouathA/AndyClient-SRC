package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.*;

public abstract class RtspObjectDecoder extends HttpObjectDecoder
{
    protected RtspObjectDecoder() {
        this(4096, 8192, 8192);
    }
    
    protected RtspObjectDecoder(final int n, final int n2, final int n3) {
        super(n, n2, n3 * 2, false);
    }
    
    protected RtspObjectDecoder(final int n, final int n2, final int n3, final boolean b) {
        super(n, n2, n3 * 2, false, b);
    }
    
    @Override
    protected boolean isContentAlwaysEmpty(final HttpMessage httpMessage) {
        final boolean contentAlwaysEmpty = super.isContentAlwaysEmpty(httpMessage);
        return contentAlwaysEmpty || !httpMessage.headers().contains("Content-Length") || contentAlwaysEmpty;
    }
}
