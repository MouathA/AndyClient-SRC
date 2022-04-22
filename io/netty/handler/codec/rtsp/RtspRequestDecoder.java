package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.*;

public class RtspRequestDecoder extends RtspObjectDecoder
{
    public RtspRequestDecoder() {
    }
    
    public RtspRequestDecoder(final int n, final int n2, final int n3) {
        super(n, n2, n3);
    }
    
    public RtspRequestDecoder(final int n, final int n2, final int n3, final boolean b) {
        super(n, n2, n3, b);
    }
    
    @Override
    protected HttpMessage createMessage(final String[] array) throws Exception {
        return new DefaultHttpRequest(RtspVersions.valueOf(array[2]), RtspMethods.valueOf(array[0]), array[1], this.validateHeaders);
    }
    
    @Override
    protected HttpMessage createInvalidMessage() {
        return new DefaultHttpRequest(RtspVersions.RTSP_1_0, RtspMethods.OPTIONS, "/bad-request", this.validateHeaders);
    }
    
    @Override
    protected boolean isDecodingRequest() {
        return true;
    }
}
