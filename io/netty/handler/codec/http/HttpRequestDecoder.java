package io.netty.handler.codec.http;

public class HttpRequestDecoder extends HttpObjectDecoder
{
    public HttpRequestDecoder() {
    }
    
    public HttpRequestDecoder(final int n, final int n2, final int n3) {
        super(n, n2, n3, true);
    }
    
    public HttpRequestDecoder(final int n, final int n2, final int n3, final boolean b) {
        super(n, n2, n3, true, b);
    }
    
    @Override
    protected HttpMessage createMessage(final String[] array) throws Exception {
        return new DefaultHttpRequest(HttpVersion.valueOf(array[2]), HttpMethod.valueOf(array[0]), array[1], this.validateHeaders);
    }
    
    @Override
    protected HttpMessage createInvalidMessage() {
        return new DefaultHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, "/bad-request", this.validateHeaders);
    }
    
    @Override
    protected boolean isDecodingRequest() {
        return true;
    }
}
