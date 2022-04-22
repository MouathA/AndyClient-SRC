package org.apache.http.impl.client;

import org.apache.http.client.*;
import org.apache.http.pool.*;
import java.util.*;
import org.apache.http.conn.routing.*;
import org.apache.http.util.*;

public class AIMDBackoffManager implements BackoffManager
{
    private final ConnPoolControl connPerRoute;
    private final Clock clock;
    private final Map lastRouteProbes;
    private final Map lastRouteBackoffs;
    private long coolDown;
    private double backoffFactor;
    private int cap;
    
    public AIMDBackoffManager(final ConnPoolControl connPoolControl) {
        this(connPoolControl, new SystemClock());
    }
    
    AIMDBackoffManager(final ConnPoolControl connPerRoute, final Clock clock) {
        this.coolDown = 5000L;
        this.backoffFactor = 0.5;
        this.cap = 2;
        this.clock = clock;
        this.connPerRoute = connPerRoute;
        this.lastRouteProbes = new HashMap();
        this.lastRouteBackoffs = new HashMap();
    }
    
    public void backOff(final HttpRoute httpRoute) {
        // monitorenter(connPerRoute = this.connPerRoute)
        final int maxPerRoute = this.connPerRoute.getMaxPerRoute(httpRoute);
        final Long lastUpdate = this.getLastUpdate(this.lastRouteBackoffs, httpRoute);
        final long currentTime = this.clock.getCurrentTime();
        if (currentTime - lastUpdate < this.coolDown) {
            // monitorexit(connPerRoute)
            return;
        }
        this.connPerRoute.setMaxPerRoute(httpRoute, this.getBackedOffPoolSize(maxPerRoute));
        this.lastRouteBackoffs.put(httpRoute, currentTime);
    }
    // monitorexit(connPerRoute)
    
    private int getBackedOffPoolSize(final int n) {
        if (n <= 1) {
            return 1;
        }
        return (int)Math.floor(this.backoffFactor * n);
    }
    
    public void probe(final HttpRoute httpRoute) {
        // monitorenter(connPerRoute = this.connPerRoute)
        final int maxPerRoute = this.connPerRoute.getMaxPerRoute(httpRoute);
        final int n = (maxPerRoute >= this.cap) ? this.cap : (maxPerRoute + 1);
        final Long lastUpdate = this.getLastUpdate(this.lastRouteProbes, httpRoute);
        final Long lastUpdate2 = this.getLastUpdate(this.lastRouteBackoffs, httpRoute);
        final long currentTime = this.clock.getCurrentTime();
        if (currentTime - lastUpdate < this.coolDown || currentTime - lastUpdate2 < this.coolDown) {
            // monitorexit(connPerRoute)
            return;
        }
        this.connPerRoute.setMaxPerRoute(httpRoute, n);
        this.lastRouteProbes.put(httpRoute, currentTime);
    }
    // monitorexit(connPerRoute)
    
    private Long getLastUpdate(final Map map, final HttpRoute httpRoute) {
        Long value = map.get(httpRoute);
        if (value == null) {
            value = 0L;
        }
        return value;
    }
    
    public void setBackoffFactor(final double backoffFactor) {
        Args.check(backoffFactor > 0.0 && backoffFactor < 1.0, "Backoff factor must be 0.0 < f < 1.0");
        this.backoffFactor = backoffFactor;
    }
    
    public void setCooldownMillis(final long coolDown) {
        Args.positive(this.coolDown, "Cool down");
        this.coolDown = coolDown;
    }
    
    public void setPerHostConnectionCap(final int cap) {
        Args.positive(cap, "Per host connection cap");
        this.cap = cap;
    }
}
