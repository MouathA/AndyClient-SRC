package org.apache.http.protocol;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.io.*;

@Immutable
public class RequestExpectContinue implements HttpRequestInterceptor
{
    private final boolean activeByDefault;
    
    @Deprecated
    public RequestExpectContinue() {
        this(false);
    }
    
    public RequestExpectContinue(final boolean activeByDefault) {
        this.activeByDefault = activeByDefault;
    }
    
    public void process(final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        if (!httpRequest.containsHeader("Expect") && httpRequest instanceof HttpEntityEnclosingRequest) {
            final ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
            final HttpEntity entity = ((HttpEntityEnclosingRequest)httpRequest).getEntity();
            if (entity != null && entity.getContentLength() != 0L && !protocolVersion.lessEquals(HttpVersion.HTTP_1_0) && httpRequest.getParams().getBooleanParameter("http.protocol.expect-continue", this.activeByDefault)) {
                httpRequest.addHeader("Expect", "100-continue");
            }
        }
    }
}
