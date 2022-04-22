package org.apache.http.client.methods;

import org.apache.http.annotation.*;
import java.net.*;

@NotThreadSafe
public class HttpPatch extends HttpEntityEnclosingRequestBase
{
    public static final String METHOD_NAME = "PATCH";
    
    public HttpPatch() {
    }
    
    public HttpPatch(final URI uri) {
        this.setURI(uri);
    }
    
    public HttpPatch(final String s) {
        this.setURI(URI.create(s));
    }
    
    @Override
    public String getMethod() {
        return "PATCH";
    }
}
