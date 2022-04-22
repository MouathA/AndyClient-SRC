package org.apache.http.conn.params;

import org.apache.http.annotation.*;
import java.util.concurrent.*;
import org.apache.http.util.*;
import org.apache.http.conn.routing.*;
import java.util.*;

@Deprecated
@ThreadSafe
public final class ConnPerRouteBean implements ConnPerRoute
{
    public static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 2;
    private final ConcurrentHashMap maxPerHostMap;
    private int defaultMax;
    
    public ConnPerRouteBean(final int defaultMaxPerRoute) {
        this.maxPerHostMap = new ConcurrentHashMap();
        this.setDefaultMaxPerRoute(defaultMaxPerRoute);
    }
    
    public ConnPerRouteBean() {
        this(2);
    }
    
    public int getDefaultMax() {
        return this.defaultMax;
    }
    
    public int getDefaultMaxPerRoute() {
        return this.defaultMax;
    }
    
    public void setDefaultMaxPerRoute(final int defaultMax) {
        Args.positive(defaultMax, "Defautl max per route");
        this.defaultMax = defaultMax;
    }
    
    public void setMaxForRoute(final HttpRoute httpRoute, final int n) {
        Args.notNull(httpRoute, "HTTP route");
        Args.positive(n, "Max per route");
        this.maxPerHostMap.put(httpRoute, n);
    }
    
    public int getMaxForRoute(final HttpRoute httpRoute) {
        Args.notNull(httpRoute, "HTTP route");
        final Integer n = this.maxPerHostMap.get(httpRoute);
        if (n != null) {
            return n;
        }
        return this.defaultMax;
    }
    
    public void setMaxForRoutes(final Map map) {
        if (map == null) {
            return;
        }
        this.maxPerHostMap.clear();
        this.maxPerHostMap.putAll(map);
    }
    
    @Override
    public String toString() {
        return this.maxPerHostMap.toString();
    }
}
