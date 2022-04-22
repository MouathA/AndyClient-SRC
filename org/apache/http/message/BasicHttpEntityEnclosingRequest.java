package org.apache.http.message;

import org.apache.http.annotation.*;
import org.apache.http.*;

@NotThreadSafe
public class BasicHttpEntityEnclosingRequest extends BasicHttpRequest implements HttpEntityEnclosingRequest
{
    private HttpEntity entity;
    
    public BasicHttpEntityEnclosingRequest(final String s, final String s2) {
        super(s, s2);
    }
    
    public BasicHttpEntityEnclosingRequest(final String s, final String s2, final ProtocolVersion protocolVersion) {
        super(s, s2, protocolVersion);
    }
    
    public BasicHttpEntityEnclosingRequest(final RequestLine requestLine) {
        super(requestLine);
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
