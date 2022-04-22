package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.*;

public class RtspResponseDecoder extends RtspObjectDecoder
{
    private static final HttpResponseStatus UNKNOWN_STATUS;
    
    public RtspResponseDecoder() {
    }
    
    public RtspResponseDecoder(final int n, final int n2, final int n3) {
        super(n, n2, n3);
    }
    
    public RtspResponseDecoder(final int n, final int n2, final int n3, final boolean b) {
        super(n, n2, n3, b);
    }
    
    @Override
    protected HttpMessage createMessage(final String[] array) throws Exception {
        return new DefaultHttpResponse(RtspVersions.valueOf(array[0]), new HttpResponseStatus(Integer.parseInt(array[1]), array[2]), this.validateHeaders);
    }
    
    @Override
    protected HttpMessage createInvalidMessage() {
        return new DefaultHttpResponse(RtspVersions.RTSP_1_0, RtspResponseDecoder.UNKNOWN_STATUS, this.validateHeaders);
    }
    
    @Override
    protected boolean isDecodingRequest() {
        return false;
    }
    
    static {
        UNKNOWN_STATUS = new HttpResponseStatus(999, "Unknown");
    }
}
