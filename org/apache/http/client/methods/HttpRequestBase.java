package org.apache.http.client.methods;

import org.apache.http.annotation.*;
import java.net.*;
import org.apache.http.client.config.*;
import org.apache.http.params.*;
import org.apache.http.*;
import org.apache.http.message.*;

@NotThreadSafe
public abstract class HttpRequestBase extends AbstractExecutionAwareRequest implements HttpUriRequest, Configurable
{
    private ProtocolVersion version;
    private URI uri;
    private RequestConfig config;
    
    public abstract String getMethod();
    
    public void setProtocolVersion(final ProtocolVersion version) {
        this.version = version;
    }
    
    public ProtocolVersion getProtocolVersion() {
        return (this.version != null) ? this.version : HttpProtocolParams.getVersion(this.getParams());
    }
    
    public URI getURI() {
        return this.uri;
    }
    
    public RequestLine getRequestLine() {
        final String method = this.getMethod();
        final ProtocolVersion protocolVersion = this.getProtocolVersion();
        final URI uri = this.getURI();
        String asciiString = null;
        if (uri != null) {
            asciiString = uri.toASCIIString();
        }
        if (asciiString == null || asciiString.length() == 0) {
            asciiString = "/";
        }
        return new BasicRequestLine(method, asciiString, protocolVersion);
    }
    
    public RequestConfig getConfig() {
        return this.config;
    }
    
    public void setConfig(final RequestConfig config) {
        this.config = config;
    }
    
    public void setURI(final URI uri) {
        this.uri = uri;
    }
    
    public void started() {
    }
    
    public void releaseConnection() {
        this.reset();
    }
    
    @Override
    public String toString() {
        return this.getMethod() + " " + this.getURI() + " " + this.getProtocolVersion();
    }
}
