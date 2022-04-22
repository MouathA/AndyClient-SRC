package org.apache.http.message;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;

@NotThreadSafe
public class BasicHttpRequest extends AbstractHttpMessage implements HttpRequest
{
    private final String method;
    private final String uri;
    private RequestLine requestline;
    
    public BasicHttpRequest(final String s, final String s2) {
        this.method = (String)Args.notNull(s, "Method name");
        this.uri = (String)Args.notNull(s2, "Request URI");
        this.requestline = null;
    }
    
    public BasicHttpRequest(final String s, final String s2, final ProtocolVersion protocolVersion) {
        this(new BasicRequestLine(s, s2, protocolVersion));
    }
    
    public BasicHttpRequest(final RequestLine requestLine) {
        this.requestline = (RequestLine)Args.notNull(requestLine, "Request line");
        this.method = requestLine.getMethod();
        this.uri = requestLine.getUri();
    }
    
    public ProtocolVersion getProtocolVersion() {
        return this.getRequestLine().getProtocolVersion();
    }
    
    public RequestLine getRequestLine() {
        if (this.requestline == null) {
            this.requestline = new BasicRequestLine(this.method, this.uri, HttpVersion.HTTP_1_1);
        }
        return this.requestline;
    }
    
    @Override
    public String toString() {
        return this.method + " " + this.uri + " " + this.headergroup;
    }
}
