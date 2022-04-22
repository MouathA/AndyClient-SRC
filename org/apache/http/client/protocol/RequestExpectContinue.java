package org.apache.http.client.protocol;

import org.apache.http.annotation.*;
import org.apache.http.protocol.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.io.*;

@Immutable
public class RequestExpectContinue implements HttpRequestInterceptor
{
    public void process(final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        if (!httpRequest.containsHeader("Expect") && httpRequest instanceof HttpEntityEnclosingRequest) {
            final ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
            final HttpEntity entity = ((HttpEntityEnclosingRequest)httpRequest).getEntity();
            if (entity != null && entity.getContentLength() != 0L && !protocolVersion.lessEquals(HttpVersion.HTTP_1_0) && HttpClientContext.adapt(httpContext).getRequestConfig().isExpectContinueEnabled()) {
                httpRequest.addHeader("Expect", "100-continue");
            }
        }
    }
}
