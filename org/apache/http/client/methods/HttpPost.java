package org.apache.http.client.methods;

import org.apache.http.annotation.*;
import java.net.*;

@NotThreadSafe
public class HttpPost extends HttpEntityEnclosingRequestBase
{
    public static final String METHOD_NAME = "POST";
    
    public HttpPost() {
    }
    
    public HttpPost(final URI uri) {
        this.setURI(uri);
    }
    
    public HttpPost(final String s) {
        this.setURI(URI.create(s));
    }
    
    @Override
    public String getMethod() {
        return "POST";
    }
}
