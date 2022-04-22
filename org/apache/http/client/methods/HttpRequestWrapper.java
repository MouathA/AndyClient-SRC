package org.apache.http.client.methods;

import org.apache.http.annotation.*;
import java.net.*;
import org.apache.http.message.*;
import org.apache.http.params.*;
import org.apache.http.*;

@NotThreadSafe
public class HttpRequestWrapper extends AbstractHttpMessage implements HttpUriRequest
{
    private final HttpRequest original;
    private final String method;
    private ProtocolVersion version;
    private URI uri;
    
    private HttpRequestWrapper(final HttpRequest original) {
        this.original = original;
        this.version = this.original.getRequestLine().getProtocolVersion();
        this.method = this.original.getRequestLine().getMethod();
        if (original instanceof HttpUriRequest) {
            this.uri = ((HttpUriRequest)original).getURI();
        }
        else {
            this.uri = null;
        }
        this.setHeaders(original.getAllHeaders());
    }
    
    public ProtocolVersion getProtocolVersion() {
        return (this.version != null) ? this.version : this.original.getProtocolVersion();
    }
    
    public void setProtocolVersion(final ProtocolVersion version) {
        this.version = version;
    }
    
    public URI getURI() {
        return this.uri;
    }
    
    public void setURI(final URI uri) {
        this.uri = uri;
    }
    
    public String getMethod() {
        return this.method;
    }
    
    public void abort() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
    public boolean isAborted() {
        return false;
    }
    
    public RequestLine getRequestLine() {
        String s;
        if (this.uri != null) {
            s = this.uri.toASCIIString();
        }
        else {
            s = this.original.getRequestLine().getUri();
        }
        if (s == null || s.length() == 0) {
            s = "/";
        }
        return new BasicRequestLine(this.method, s, this.getProtocolVersion());
    }
    
    public HttpRequest getOriginal() {
        return this.original;
    }
    
    @Override
    public String toString() {
        return this.getRequestLine() + " " + this.headergroup;
    }
    
    public static HttpRequestWrapper wrap(final HttpRequest httpRequest) {
        if (httpRequest == null) {
            return null;
        }
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            return new HttpEntityEnclosingRequestWrapper((HttpEntityEnclosingRequest)httpRequest);
        }
        return new HttpRequestWrapper(httpRequest);
    }
    
    @Deprecated
    @Override
    public HttpParams getParams() {
        if (this.params == null) {
            this.params = this.original.getParams().copy();
        }
        return this.params;
    }
    
    HttpRequestWrapper(final HttpRequest httpRequest, final HttpRequestWrapper$1 object) {
        this(httpRequest);
    }
    
    static class HttpEntityEnclosingRequestWrapper extends HttpRequestWrapper implements HttpEntityEnclosingRequest
    {
        private HttpEntity entity;
        
        public HttpEntityEnclosingRequestWrapper(final HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
            super(httpEntityEnclosingRequest, null);
            this.entity = httpEntityEnclosingRequest.getEntity();
        }
        
        public HttpEntity getEntity() {
            return this.entity;
        }
        
        public void setEntity(final HttpEntity entity) {
            this.entity = entity;
        }
        
        public boolean expectContinue() {
            final Header firstHeader = this.getFirstHeader("Expect");
            return firstHeader != null && "100-continue".equalsIgnoreCase(firstHeader.getValue());
        }
    }
}
