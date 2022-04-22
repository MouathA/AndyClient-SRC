package org.apache.http.protocol;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.params.*;
import org.apache.http.*;
import java.io.*;

@Immutable
public class RequestUserAgent implements HttpRequestInterceptor
{
    private final String userAgent;
    
    public RequestUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }
    
    public RequestUserAgent() {
        this(null);
    }
    
    public void process(final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        if (!httpRequest.containsHeader("User-Agent")) {
            String userAgent = null;
            final HttpParams params = httpRequest.getParams();
            if (params != null) {
                userAgent = (String)params.getParameter("http.useragent");
            }
            if (userAgent == null) {
                userAgent = this.userAgent;
            }
            if (userAgent != null) {
                httpRequest.addHeader("User-Agent", userAgent);
            }
        }
    }
}
