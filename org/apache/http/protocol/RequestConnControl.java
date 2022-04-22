package org.apache.http.protocol;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.io.*;

@Immutable
public class RequestConnControl implements HttpRequestInterceptor
{
    public void process(final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        if (httpRequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
            return;
        }
        if (!httpRequest.containsHeader("Connection")) {
            httpRequest.addHeader("Connection", "Keep-Alive");
        }
    }
}
