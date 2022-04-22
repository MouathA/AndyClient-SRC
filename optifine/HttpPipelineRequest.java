package optifine;

public class HttpPipelineRequest
{
    private HttpRequest httpRequest;
    private HttpListener httpListener;
    private boolean closed;
    
    public HttpPipelineRequest(final HttpRequest httpRequest, final HttpListener httpListener) {
        this.httpRequest = null;
        this.httpListener = null;
        this.closed = false;
        this.httpRequest = httpRequest;
        this.httpListener = httpListener;
    }
    
    public HttpRequest getHttpRequest() {
        return this.httpRequest;
    }
    
    public HttpListener getHttpListener() {
        return this.httpListener;
    }
    
    public boolean isClosed() {
        return this.closed;
    }
    
    public void setClosed(final boolean closed) {
        this.closed = closed;
    }
}
