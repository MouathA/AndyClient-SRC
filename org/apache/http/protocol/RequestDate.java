package org.apache.http.protocol;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.io.*;

@ThreadSafe
public class RequestDate implements HttpRequestInterceptor
{
    private static final HttpDateGenerator DATE_GENERATOR;
    
    public void process(final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        if (httpRequest instanceof HttpEntityEnclosingRequest && !httpRequest.containsHeader("Date")) {
            httpRequest.setHeader("Date", RequestDate.DATE_GENERATOR.getCurrentDate());
        }
    }
    
    static {
        DATE_GENERATOR = new HttpDateGenerator();
    }
}
