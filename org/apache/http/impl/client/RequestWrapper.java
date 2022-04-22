package org.apache.http.impl.client;

import org.apache.http.client.methods.*;
import org.apache.http.annotation.*;
import java.net.*;
import org.apache.http.util.*;
import org.apache.http.*;
import org.apache.http.params.*;
import org.apache.http.message.*;

@Deprecated
@NotThreadSafe
public class RequestWrapper extends AbstractHttpMessage implements HttpUriRequest
{
    private final HttpRequest original;
    private URI uri;
    private String method;
    private ProtocolVersion version;
    private int execCount;
    
    public RequestWrapper(final HttpRequest original) throws ProtocolException {
        Args.notNull(original, "HTTP request");
        this.original = original;
        this.setParams(original.getParams());
        this.setHeaders(original.getAllHeaders());
        if (original instanceof HttpUriRequest) {
            this.uri = ((HttpUriRequest)original).getURI();
            this.method = ((HttpUriRequest)original).getMethod();
            this.version = null;
        }
        else {
            final RequestLine requestLine = original.getRequestLine();
            this.uri = new URI(requestLine.getUri());
            this.method = requestLine.getMethod();
            this.version = original.getProtocolVersion();
        }
        this.execCount = 0;
    }
    
    public void resetHeaders() {
        this.headergroup.clear();
        this.setHeaders(this.original.getAllHeaders());
    }
    
    public String getMethod() {
        return this.method;
    }
    
    public void setMethod(final String method) {
        Args.notNull(method, "Method name");
        this.method = method;
    }
    
    public ProtocolVersion getProtocolVersion() {
        if (this.version == null) {
            this.version = HttpProtocolParams.getVersion(this.getParams());
        }
        return this.version;
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
    
    public RequestLine getRequestLine() {
        final String method = this.getMethod();
        final ProtocolVersion protocolVersion = this.getProtocolVersion();
        String asciiString = null;
        if (this.uri != null) {
            asciiString = this.uri.toASCIIString();
        }
        if (asciiString == null || asciiString.length() == 0) {
            asciiString = "/";
        }
        return new BasicRequestLine(method, asciiString, protocolVersion);
    }
    
    public void abort() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
    public boolean isAborted() {
        return false;
    }
    
    public HttpRequest getOriginal() {
        return this.original;
    }
    
    public boolean isRepeatable() {
        return true;
    }
    
    public int getExecCount() {
        return this.execCount;
    }
    
    public void incrementExecCount() {
        ++this.execCount;
    }
}
