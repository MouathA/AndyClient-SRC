package org.apache.http.pool;

import org.apache.http.annotation.*;
import java.util.concurrent.*;
import org.apache.http.util.*;

@ThreadSafe
public abstract class PoolEntry
{
    private final String id;
    private final Object route;
    private final Object conn;
    private final long created;
    private final long validUnit;
    @GuardedBy("this")
    private long updated;
    @GuardedBy("this")
    private long expiry;
    private Object state;
    
    public PoolEntry(final String id, final Object route, final Object conn, final long n, final TimeUnit timeUnit) {
        Args.notNull(route, "Route");
        Args.notNull(conn, "Connection");
        Args.notNull(timeUnit, "Time unit");
        this.id = id;
        this.route = route;
        this.conn = conn;
        this.created = System.currentTimeMillis();
        if (n > 0L) {
            this.validUnit = this.created + timeUnit.toMillis(n);
        }
        else {
            this.validUnit = Long.MAX_VALUE;
        }
        this.expiry = this.validUnit;
    }
    
    public PoolEntry(final String s, final Object o, final Object o2) {
        this(s, o, o2, 0L, TimeUnit.MILLISECONDS);
    }
    
    public String getId() {
        return this.id;
    }
    
    public Object getRoute() {
        return this.route;
    }
    
    public Object getConnection() {
        return this.conn;
    }
    
    public long getCreated() {
        return this.created;
    }
    
    public long getValidUnit() {
        return this.validUnit;
    }
    
    public Object getState() {
        return this.state;
    }
    
    public void setState(final Object state) {
        this.state = state;
    }
    
    public synchronized long getUpdated() {
        return this.updated;
    }
    
    public synchronized long getExpiry() {
        return this.expiry;
    }
    
    public synchronized void updateExpiry(final long n, final TimeUnit timeUnit) {
        Args.notNull(timeUnit, "Time unit");
        this.updated = System.currentTimeMillis();
        long n2;
        if (n > 0L) {
            n2 = this.updated + timeUnit.toMillis(n);
        }
        else {
            n2 = Long.MAX_VALUE;
        }
        this.expiry = Math.min(n2, this.validUnit);
    }
    
    public synchronized boolean isExpired(final long n) {
        return n >= this.expiry;
    }
    
    public abstract void close();
    
    public abstract boolean isClosed();
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[id:");
        sb.append(this.id);
        sb.append("][route:");
        sb.append(this.route);
        sb.append("][state:");
        sb.append(this.state);
        sb.append("]");
        return sb.toString();
    }
}
