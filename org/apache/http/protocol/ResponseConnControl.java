package org.apache.http.protocol;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.io.*;

@Immutable
public class ResponseConnControl implements HttpResponseInterceptor
{
    public void process(final HttpResponse httpResponse, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        final HttpCoreContext adapt = HttpCoreContext.adapt(httpContext);
        final int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 400 || statusCode == 408 || statusCode == 411 || statusCode == 413 || statusCode == 414 || statusCode == 503 || statusCode == 501) {
            httpResponse.setHeader("Connection", "Close");
            return;
        }
        final Header firstHeader = httpResponse.getFirstHeader("Connection");
        if (firstHeader != null && "Close".equalsIgnoreCase(firstHeader.getValue())) {
            return;
        }
        final HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            final ProtocolVersion protocolVersion = httpResponse.getStatusLine().getProtocolVersion();
            if (entity.getContentLength() < 0L && (!entity.isChunked() || protocolVersion.lessEquals(HttpVersion.HTTP_1_0))) {
                httpResponse.setHeader("Connection", "Close");
                return;
            }
        }
        final HttpRequest request = adapt.getRequest();
        if (request != null) {
            final Header firstHeader2 = request.getFirstHeader("Connection");
            if (firstHeader2 != null) {
                httpResponse.setHeader("Connection", firstHeader2.getValue());
            }
            else if (request.getProtocolVersion().lessEquals(HttpVersion.HTTP_1_0)) {
                httpResponse.setHeader("Connection", "Close");
            }
        }
    }
}
