package org.apache.http.client.methods;

import org.apache.http.annotation.*;
import java.net.*;
import org.apache.http.util.*;
import java.util.*;
import org.apache.http.*;

@NotThreadSafe
public class HttpOptions extends HttpRequestBase
{
    public static final String METHOD_NAME = "OPTIONS";
    
    public HttpOptions() {
    }
    
    public HttpOptions(final URI uri) {
        this.setURI(uri);
    }
    
    public HttpOptions(final String s) {
        this.setURI(URI.create(s));
    }
    
    @Override
    public String getMethod() {
        return "OPTIONS";
    }
    
    public Set getAllowedMethods(final HttpResponse httpResponse) {
        Args.notNull(httpResponse, "HTTP response");
        final HeaderIterator headerIterator = httpResponse.headerIterator("Allow");
        final HashSet<String> set = new HashSet<String>();
        while (headerIterator.hasNext()) {
            final HeaderElement[] elements = headerIterator.nextHeader().getElements();
            while (0 < elements.length) {
                set.add(elements[0].getName());
                int n = 0;
                ++n;
            }
        }
        return set;
    }
}
