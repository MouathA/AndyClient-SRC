package org.apache.http.client.protocol;

import org.apache.http.annotation.*;
import org.apache.http.protocol.*;
import org.apache.http.util.*;
import java.util.*;
import org.apache.http.*;
import java.io.*;

@Immutable
public class RequestDefaultHeaders implements HttpRequestInterceptor
{
    private final Collection defaultHeaders;
    
    public RequestDefaultHeaders(final Collection defaultHeaders) {
        this.defaultHeaders = defaultHeaders;
    }
    
    public RequestDefaultHeaders() {
        this(null);
    }
    
    public void process(final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        if (httpRequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
            return;
        }
        Collection defaultHeaders = (Collection)httpRequest.getParams().getParameter("http.default-headers");
        if (defaultHeaders == null) {
            defaultHeaders = this.defaultHeaders;
        }
        if (defaultHeaders != null) {
            final Iterator<Header> iterator = defaultHeaders.iterator();
            while (iterator.hasNext()) {
                httpRequest.addHeader(iterator.next());
            }
        }
    }
}
