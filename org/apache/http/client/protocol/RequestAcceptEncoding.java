package org.apache.http.client.protocol;

import org.apache.http.annotation.*;
import org.apache.http.protocol.*;
import org.apache.http.*;
import java.io.*;

@Immutable
public class RequestAcceptEncoding implements HttpRequestInterceptor
{
    public void process(final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException, IOException {
        if (!httpRequest.containsHeader("Accept-Encoding")) {
            httpRequest.addHeader("Accept-Encoding", "gzip,deflate");
        }
    }
}
