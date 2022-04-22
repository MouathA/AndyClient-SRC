package org.apache.http.client.methods;

import org.apache.http.annotation.*;
import java.net.*;

@NotThreadSafe
public class HttpHead extends HttpRequestBase
{
    public static final String METHOD_NAME = "HEAD";
    
    public HttpHead() {
    }
    
    public HttpHead(final URI uri) {
        this.setURI(uri);
    }
    
    public HttpHead(final String s) {
        this.setURI(URI.create(s));
    }
    
    @Override
    public String getMethod() {
        return "HEAD";
    }
}
