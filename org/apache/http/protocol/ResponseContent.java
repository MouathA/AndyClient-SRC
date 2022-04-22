package org.apache.http.protocol;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.io.*;

@Immutable
public class ResponseContent implements HttpResponseInterceptor
{
    private final boolean overwrite;
    
    public ResponseContent() {
        this(false);
    }
    
    public ResponseContent(final boolean overwrite) {
        this.overwrite = overwrite;
    }
    
    public void process(final HttpResponse httpResponse, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        if (this.overwrite) {
            httpResponse.removeHeaders("Transfer-Encoding");
            httpResponse.removeHeaders("Content-Length");
        }
        else {
            if (httpResponse.containsHeader("Transfer-Encoding")) {
                throw new ProtocolException("Transfer-encoding header already present");
            }
            if (httpResponse.containsHeader("Content-Length")) {
                throw new ProtocolException("Content-Length header already present");
            }
        }
        final ProtocolVersion protocolVersion = httpResponse.getStatusLine().getProtocolVersion();
        final HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            final long contentLength = entity.getContentLength();
            if (entity.isChunked() && !protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                httpResponse.addHeader("Transfer-Encoding", "chunked");
            }
            else if (contentLength >= 0L) {
                httpResponse.addHeader("Content-Length", Long.toString(entity.getContentLength()));
            }
            if (entity.getContentType() != null && !httpResponse.containsHeader("Content-Type")) {
                httpResponse.addHeader(entity.getContentType());
            }
            if (entity.getContentEncoding() != null && !httpResponse.containsHeader("Content-Encoding")) {
                httpResponse.addHeader(entity.getContentEncoding());
            }
        }
        else {
            final int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != 204 && statusCode != 304 && statusCode != 205) {
                httpResponse.addHeader("Content-Length", "0");
            }
        }
    }
}
