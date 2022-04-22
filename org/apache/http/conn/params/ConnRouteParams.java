package org.apache.http.conn.params;

import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.conn.routing.*;
import org.apache.http.params.*;
import org.apache.http.util.*;
import java.net.*;

@Deprecated
@Immutable
public class ConnRouteParams implements ConnRoutePNames
{
    public static final HttpHost NO_HOST;
    public static final HttpRoute NO_ROUTE;
    
    private ConnRouteParams() {
    }
    
    public static HttpHost getDefaultProxy(final HttpParams httpParams) {
        Args.notNull(httpParams, "Parameters");
        HttpHost httpHost = (HttpHost)httpParams.getParameter("http.route.default-proxy");
        if (httpHost != null && ConnRouteParams.NO_HOST.equals(httpHost)) {
            httpHost = null;
        }
        return httpHost;
    }
    
    public static void setDefaultProxy(final HttpParams httpParams, final HttpHost httpHost) {
        Args.notNull(httpParams, "Parameters");
        httpParams.setParameter("http.route.default-proxy", httpHost);
    }
    
    public static HttpRoute getForcedRoute(final HttpParams httpParams) {
        Args.notNull(httpParams, "Parameters");
        HttpRoute httpRoute = (HttpRoute)httpParams.getParameter("http.route.forced-route");
        if (httpRoute != null && ConnRouteParams.NO_ROUTE.equals(httpRoute)) {
            httpRoute = null;
        }
        return httpRoute;
    }
    
    public static void setForcedRoute(final HttpParams httpParams, final HttpRoute httpRoute) {
        Args.notNull(httpParams, "Parameters");
        httpParams.setParameter("http.route.forced-route", httpRoute);
    }
    
    public static InetAddress getLocalAddress(final HttpParams httpParams) {
        Args.notNull(httpParams, "Parameters");
        return (InetAddress)httpParams.getParameter("http.route.local-address");
    }
    
    public static void setLocalAddress(final HttpParams httpParams, final InetAddress inetAddress) {
        Args.notNull(httpParams, "Parameters");
        httpParams.setParameter("http.route.local-address", inetAddress);
    }
    
    static {
        NO_HOST = new HttpHost("127.0.0.255", 0, "no-host");
        NO_ROUTE = new HttpRoute(ConnRouteParams.NO_HOST);
    }
}
