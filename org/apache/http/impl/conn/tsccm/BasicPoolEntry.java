package org.apache.http.impl.conn.tsccm;

import org.apache.http.impl.conn.*;
import org.apache.http.conn.routing.*;
import java.lang.ref.*;
import org.apache.http.util.*;
import java.util.concurrent.*;
import org.apache.http.conn.*;

@Deprecated
public class BasicPoolEntry extends AbstractPoolEntry
{
    private final long created;
    private long updated;
    private final long validUntil;
    private long expiry;
    
    public BasicPoolEntry(final ClientConnectionOperator clientConnectionOperator, final HttpRoute httpRoute, final ReferenceQueue referenceQueue) {
        super(clientConnectionOperator, httpRoute);
        Args.notNull(httpRoute, "HTTP route");
        this.created = System.currentTimeMillis();
        this.validUntil = Long.MAX_VALUE;
        this.expiry = this.validUntil;
    }
    
    public BasicPoolEntry(final ClientConnectionOperator clientConnectionOperator, final HttpRoute httpRoute) {
        this(clientConnectionOperator, httpRoute, -1L, TimeUnit.MILLISECONDS);
    }
    
    public BasicPoolEntry(final ClientConnectionOperator clientConnectionOperator, final HttpRoute httpRoute, final long n, final TimeUnit timeUnit) {
        super(clientConnectionOperator, httpRoute);
        Args.notNull(httpRoute, "HTTP route");
        this.created = System.currentTimeMillis();
        if (n > 0L) {
            this.validUntil = this.created + timeUnit.toMillis(n);
        }
        else {
            this.validUntil = Long.MAX_VALUE;
        }
        this.expiry = this.validUntil;
    }
    
    protected final OperatedClientConnection getConnection() {
        return super.connection;
    }
    
    protected final HttpRoute getPlannedRoute() {
        return super.route;
    }
    
    protected final BasicPoolEntryRef getWeakRef() {
        return null;
    }
    
    @Override
    protected void shutdownEntry() {
        super.shutdownEntry();
    }
    
    public long getCreated() {
        return this.created;
    }
    
    public long getUpdated() {
        return this.updated;
    }
    
    public long getExpiry() {
        return this.expiry;
    }
    
    public long getValidUntil() {
        return this.validUntil;
    }
    
    public void updateExpiry(final long n, final TimeUnit timeUnit) {
        this.updated = System.currentTimeMillis();
        long n2;
        if (n > 0L) {
            n2 = this.updated + timeUnit.toMillis(n);
        }
        else {
            n2 = Long.MAX_VALUE;
        }
        this.expiry = Math.min(this.validUntil, n2);
    }
    
    public boolean isExpired(final long n) {
        return n >= this.expiry;
    }
}
