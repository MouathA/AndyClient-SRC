package org.apache.http.impl.conn;

import org.apache.http.pool.*;
import org.apache.commons.logging.*;
import org.apache.http.conn.routing.*;
import org.apache.http.conn.*;
import java.util.concurrent.*;
import java.util.*;

@Deprecated
class HttpPoolEntry extends PoolEntry
{
    private final Log log;
    private final RouteTracker tracker;
    
    public HttpPoolEntry(final Log log, final String s, final HttpRoute httpRoute, final OperatedClientConnection operatedClientConnection, final long n, final TimeUnit timeUnit) {
        super(s, httpRoute, operatedClientConnection, n, timeUnit);
        this.log = log;
        this.tracker = new RouteTracker(httpRoute);
    }
    
    @Override
    public boolean isExpired(final long n) {
        final boolean expired = super.isExpired(n);
        if (expired && this.log.isDebugEnabled()) {
            this.log.debug("Connection " + this + " expired @ " + new Date(this.getExpiry()));
        }
        return expired;
    }
    
    RouteTracker getTracker() {
        return this.tracker;
    }
    
    HttpRoute getPlannedRoute() {
        return (HttpRoute)this.getRoute();
    }
    
    HttpRoute getEffectiveRoute() {
        return this.tracker.toRoute();
    }
    
    @Override
    public boolean isClosed() {
        return !((OperatedClientConnection)this.getConnection()).isOpen();
    }
    
    @Override
    public void close() {
        ((OperatedClientConnection)this.getConnection()).close();
    }
}
