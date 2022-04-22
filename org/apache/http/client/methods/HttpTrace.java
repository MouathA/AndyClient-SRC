package org.apache.http.client.methods;

import org.apache.http.annotation.*;
import java.net.*;

@NotThreadSafe
public class HttpTrace extends HttpRequestBase
{
    public static final String METHOD_NAME = "TRACE";
    
    public HttpTrace() {
    }
    
    public HttpTrace(final URI uri) {
        this.setURI(uri);
    }
    
    public HttpTrace(final String s) {
        this.setURI(URI.create(s));
    }
    
    @Override
    public String getMethod() {
        return "TRACE";
    }
}
