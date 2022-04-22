package org.apache.http.client.methods;

import org.apache.http.annotation.*;
import java.net.*;

@NotThreadSafe
public class HttpPut extends HttpEntityEnclosingRequestBase
{
    public static final String METHOD_NAME = "PUT";
    
    public HttpPut() {
    }
    
    public HttpPut(final URI uri) {
        this.setURI(uri);
    }
    
    public HttpPut(final String s) {
        this.setURI(URI.create(s));
    }
    
    @Override
    public String getMethod() {
        return "PUT";
    }
}
