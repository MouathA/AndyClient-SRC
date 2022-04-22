package org.apache.http.impl;

import org.apache.http.annotation.*;
import org.apache.http.protocol.*;
import org.apache.http.util.*;
import org.apache.http.*;
import org.apache.http.message.*;

@Immutable
public class DefaultConnectionReuseStrategy implements ConnectionReuseStrategy
{
    public static final DefaultConnectionReuseStrategy INSTANCE;
    
    public boolean keepAlive(final HttpResponse httpResponse, final HttpContext httpContext) {
        Args.notNull(httpResponse, "HTTP response");
        Args.notNull(httpContext, "HTTP context");
        final ProtocolVersion protocolVersion = httpResponse.getStatusLine().getProtocolVersion();
        final Header firstHeader = httpResponse.getFirstHeader("Transfer-Encoding");
        if (firstHeader != null) {
            if (!"chunked".equalsIgnoreCase(firstHeader.getValue())) {
                return false;
            }
        }
        else if (this.canResponseHaveBody(httpResponse)) {
            final Header[] headers = httpResponse.getHeaders("Content-Length");
            if (headers.length != 1) {
                return false;
            }
            Integer.parseInt(headers[0].getValue());
            if (1 < 0) {
                return false;
            }
        }
        HeaderIterator headerIterator = httpResponse.headerIterator("Connection");
        if (!headerIterator.hasNext()) {
            headerIterator = httpResponse.headerIterator("Proxy-Connection");
        }
        if (headerIterator.hasNext()) {
            final TokenIterator tokenIterator = this.createTokenIterator(headerIterator);
            while (tokenIterator.hasNext()) {
                final String nextToken = tokenIterator.nextToken();
                if ("Close".equalsIgnoreCase(nextToken)) {
                    return false;
                }
                if ("Keep-Alive".equalsIgnoreCase(nextToken)) {}
            }
            if (true) {
                return true;
            }
        }
        return !protocolVersion.lessEquals(HttpVersion.HTTP_1_0);
    }
    
    protected TokenIterator createTokenIterator(final HeaderIterator headerIterator) {
        return new BasicTokenIterator(headerIterator);
    }
    
    private boolean canResponseHaveBody(final HttpResponse httpResponse) {
        final int statusCode = httpResponse.getStatusLine().getStatusCode();
        return statusCode >= 200 && statusCode != 204 && statusCode != 304 && statusCode != 205;
    }
    
    static {
        INSTANCE = new DefaultConnectionReuseStrategy();
    }
}
