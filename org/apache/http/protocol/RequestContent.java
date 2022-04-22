package org.apache.http.protocol;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.io.*;

@Immutable
public class RequestContent implements HttpRequestInterceptor
{
    private final boolean overwrite;
    
    public RequestContent() {
        this(false);
    }
    
    public RequestContent(final boolean overwrite) {
        this.overwrite = overwrite;
    }
    
    public void process(final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            if (this.overwrite) {
                httpRequest.removeHeaders("Transfer-Encoding");
                httpRequest.removeHeaders("Content-Length");
            }
            else {
                if (httpRequest.containsHeader("Transfer-Encoding")) {
                    throw new ProtocolException("Transfer-encoding header already present");
                }
                if (httpRequest.containsHeader("Content-Length")) {
                    throw new ProtocolException("Content-Length header already present");
                }
            }
            final ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
            final HttpEntity entity = ((HttpEntityEnclosingRequest)httpRequest).getEntity();
            if (entity == null) {
                httpRequest.addHeader("Content-Length", "0");
                return;
            }
            if (entity.isChunked() || entity.getContentLength() < 0L) {
                if (protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                    throw new ProtocolException("Chunked transfer encoding not allowed for " + protocolVersion);
                }
                httpRequest.addHeader("Transfer-Encoding", "chunked");
            }
            else {
                httpRequest.addHeader("Content-Length", Long.toString(entity.getContentLength()));
            }
            if (entity.getContentType() != null && !httpRequest.containsHeader("Content-Type")) {
                httpRequest.addHeader(entity.getContentType());
            }
            if (entity.getContentEncoding() != null && !httpRequest.containsHeader("Content-Encoding")) {
                httpRequest.addHeader(entity.getContentEncoding());
            }
        }
    }
}
