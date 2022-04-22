package org.apache.http.conn.params;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.http.util.*;
import org.apache.http.conn.routing.*;

@Deprecated
@Immutable
public final class ConnManagerParams implements ConnManagerPNames
{
    public static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 20;
    private static final ConnPerRoute DEFAULT_CONN_PER_ROUTE;
    
    @Deprecated
    public static long getTimeout(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getLongParameter("http.conn-manager.timeout", 0L);
    }
    
    @Deprecated
    public static void setTimeout(final HttpParams httpParams, final long n) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setLongParameter("http.conn-manager.timeout", n);
    }
    
    public static void setMaxConnectionsPerRoute(final HttpParams httpParams, final ConnPerRoute connPerRoute) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setParameter("http.conn-manager.max-per-route", connPerRoute);
    }
    
    public static ConnPerRoute getMaxConnectionsPerRoute(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        ConnPerRoute default_CONN_PER_ROUTE = (ConnPerRoute)httpParams.getParameter("http.conn-manager.max-per-route");
        if (default_CONN_PER_ROUTE == null) {
            default_CONN_PER_ROUTE = ConnManagerParams.DEFAULT_CONN_PER_ROUTE;
        }
        return default_CONN_PER_ROUTE;
    }
    
    public static void setMaxTotalConnections(final HttpParams httpParams, final int n) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setIntParameter("http.conn-manager.max-total", n);
    }
    
    public static int getMaxTotalConnections(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getIntParameter("http.conn-manager.max-total", 20);
    }
    
    static {
        DEFAULT_CONN_PER_ROUTE = new ConnPerRoute() {
            public int getMaxForRoute(final HttpRoute httpRoute) {
                return 2;
            }
        };
    }
}
