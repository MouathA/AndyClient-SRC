package io.netty.handler.codec.http;

public class HttpResponseDecoder extends HttpObjectDecoder
{
    private static final HttpResponseStatus UNKNOWN_STATUS;
    
    public HttpResponseDecoder() {
    }
    
    public HttpResponseDecoder(final int n, final int n2, final int n3) {
        super(n, n2, n3, true);
    }
    
    public HttpResponseDecoder(final int n, final int n2, final int n3, final boolean b) {
        super(n, n2, n3, true, b);
    }
    
    @Override
    protected HttpMessage createMessage(final String[] array) {
        return new DefaultHttpResponse(HttpVersion.valueOf(array[0]), new HttpResponseStatus(Integer.parseInt(array[1]), array[2]), this.validateHeaders);
    }
    
    @Override
    protected HttpMessage createInvalidMessage() {
        return new DefaultHttpResponse(HttpVersion.HTTP_1_0, HttpResponseDecoder.UNKNOWN_STATUS, this.validateHeaders);
    }
    
    @Override
    protected boolean isDecodingRequest() {
        return false;
    }
    
    static {
        UNKNOWN_STATUS = new HttpResponseStatus(999, "Unknown");
    }
}
