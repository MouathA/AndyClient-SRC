package org.apache.http.client.protocol;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.protocol.*;
import org.apache.http.util.*;
import org.apache.http.conn.routing.*;
import org.apache.http.*;
import java.io.*;

@Immutable
public class RequestClientConnControl implements HttpRequestInterceptor
{
    private final Log log;
    private static final String PROXY_CONN_DIRECTIVE = "Proxy-Connection";
    
    public RequestClientConnControl() {
        this.log = LogFactory.getLog(this.getClass());
    }
    
    public void process(final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        if (httpRequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
            httpRequest.setHeader("Proxy-Connection", "Keep-Alive");
            return;
        }
        final RouteInfo httpRoute = HttpClientContext.adapt(httpContext).getHttpRoute();
        if (httpRoute == null) {
            this.log.debug("Connection route not set in the context");
            return;
        }
        if ((httpRoute.getHopCount() == 1 || httpRoute.isTunnelled()) && !httpRequest.containsHeader("Connection")) {
            httpRequest.addHeader("Connection", "Keep-Alive");
        }
        if (httpRoute.getHopCount() == 2 && !httpRoute.isTunnelled() && !httpRequest.containsHeader("Proxy-Connection")) {
            httpRequest.addHeader("Proxy-Connection", "Keep-Alive");
        }
    }
}
