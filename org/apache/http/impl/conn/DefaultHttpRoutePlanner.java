package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import org.apache.http.conn.scheme.*;
import org.apache.http.protocol.*;
import org.apache.http.conn.routing.*;
import org.apache.http.conn.params.*;
import org.apache.http.util.*;
import java.net.*;
import org.apache.http.*;

@Deprecated
@ThreadSafe
public class DefaultHttpRoutePlanner implements HttpRoutePlanner
{
    protected final SchemeRegistry schemeRegistry;
    
    public DefaultHttpRoutePlanner(final SchemeRegistry schemeRegistry) {
        Args.notNull(schemeRegistry, "Scheme registry");
        this.schemeRegistry = schemeRegistry;
    }
    
    public HttpRoute determineRoute(final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException {
        Args.notNull(httpRequest, "HTTP request");
        final HttpRoute forcedRoute = ConnRouteParams.getForcedRoute(httpRequest.getParams());
        if (forcedRoute != null) {
            return forcedRoute;
        }
        Asserts.notNull(httpHost, "Target host");
        final InetAddress localAddress = ConnRouteParams.getLocalAddress(httpRequest.getParams());
        final HttpHost defaultProxy = ConnRouteParams.getDefaultProxy(httpRequest.getParams());
        final boolean layered = this.schemeRegistry.getScheme(httpHost.getSchemeName()).isLayered();
        HttpRoute httpRoute;
        if (defaultProxy == null) {
            httpRoute = new HttpRoute(httpHost, localAddress, layered);
        }
        else {
            httpRoute = new HttpRoute(httpHost, localAddress, defaultProxy, layered);
        }
        return httpRoute;
    }
}
