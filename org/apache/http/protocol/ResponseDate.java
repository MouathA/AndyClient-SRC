package org.apache.http.protocol;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.io.*;

@ThreadSafe
public class ResponseDate implements HttpResponseInterceptor
{
    private static final HttpDateGenerator DATE_GENERATOR;
    
    public void process(final HttpResponse httpResponse, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        if (httpResponse.getStatusLine().getStatusCode() >= 200 && !httpResponse.containsHeader("Date")) {
            httpResponse.setHeader("Date", ResponseDate.DATE_GENERATOR.getCurrentDate());
        }
    }
    
    static {
        DATE_GENERATOR = new HttpDateGenerator();
    }
}
