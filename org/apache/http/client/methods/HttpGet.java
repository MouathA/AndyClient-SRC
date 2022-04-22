package org.apache.http.client.methods;

import org.apache.http.annotation.*;
import java.net.*;

@NotThreadSafe
public class HttpGet extends HttpRequestBase
{
    public static final String METHOD_NAME = "GET";
    
    public HttpGet() {
    }
    
    public HttpGet(final URI uri) {
        this.setURI(uri);
    }
    
    public HttpGet(final String s) {
        this.setURI(URI.create(s));
    }
    
    @Override
    public String getMethod() {
        return "GET";
    }
}
