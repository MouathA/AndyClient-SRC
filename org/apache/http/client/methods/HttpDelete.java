package org.apache.http.client.methods;

import org.apache.http.annotation.*;
import java.net.*;

@NotThreadSafe
public class HttpDelete extends HttpRequestBase
{
    public static final String METHOD_NAME = "DELETE";
    
    public HttpDelete() {
    }
    
    public HttpDelete(final URI uri) {
        this.setURI(uri);
    }
    
    public HttpDelete(final String s) {
        this.setURI(URI.create(s));
    }
    
    @Override
    public String getMethod() {
        return "DELETE";
    }
}
