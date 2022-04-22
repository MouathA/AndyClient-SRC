package org.apache.http.impl.conn;

import java.util.concurrent.atomic.*;
import org.apache.http.conn.scheme.*;
import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.conn.routing.*;
import java.util.concurrent.*;
import org.apache.http.conn.*;
import org.apache.http.util.*;
import org.apache.http.*;

@Deprecated
@ThreadSafe
public class BasicClientConnectionManager implements ClientConnectionManager
{
    private final Log log;
    private static final AtomicLong COUNTER;
    public static final String MISUSE_MESSAGE = "Invalid use of BasicClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.";
    private final SchemeRegistry schemeRegistry;
    private final ClientConnectionOperator connOperator;
    @GuardedBy("this")
    private HttpPoolEntry poolEntry;
    @GuardedBy("this")
    private ManagedClientConnectionImpl conn;
    @GuardedBy("this")
    private boolean shutdown;
    
    public BasicClientConnectionManager(final SchemeRegistry schemeRegistry) {
        this.log = LogFactory.getLog(this.getClass());
        Args.notNull(schemeRegistry, "Scheme registry");
        this.schemeRegistry = schemeRegistry;
        this.connOperator = this.createConnectionOperator(schemeRegistry);
    }
    
    public BasicClientConnectionManager() {
        this(SchemeRegistryFactory.createDefault());
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.shutdown();
        super.finalize();
    }
    
    public SchemeRegistry getSchemeRegistry() {
        return this.schemeRegistry;
    }
    
    protected ClientConnectionOperator createConnectionOperator(final SchemeRegistry schemeRegistry) {
        return new DefaultClientConnectionOperator(schemeRegistry);
    }
    
    public final ClientConnectionRequest requestConnection(final HttpRoute httpRoute, final Object o) {
        return new ClientConnectionRequest(httpRoute, o) {
            final HttpRoute val$route;
            final Object val$state;
            final BasicClientConnectionManager this$0;
            
            public void abortRequest() {
            }
            
            public ManagedClientConnection getConnection(final long n, final TimeUnit timeUnit) {
                return this.this$0.getConnection(this.val$route, this.val$state);
            }
        };
    }
    
    private void assertNotShutdown() {
        Asserts.check(!this.shutdown, "Connection manager has been shut down");
    }
    
    ManagedClientConnection getConnection(final HttpRoute httpRoute, final Object o) {
        Args.notNull(httpRoute, "Route");
        // monitorenter(this)
        this.assertNotShutdown();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Get connection for route " + httpRoute);
        }
        Asserts.check(this.conn == null, "Invalid use of BasicClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.");
        if (this.poolEntry != null && !this.poolEntry.getPlannedRoute().equals(httpRoute)) {
            this.poolEntry.close();
            this.poolEntry = null;
        }
        if (this.poolEntry == null) {
            this.poolEntry = new HttpPoolEntry(this.log, Long.toString(BasicClientConnectionManager.COUNTER.getAndIncrement()), httpRoute, this.connOperator.createConnection(), 0L, TimeUnit.MILLISECONDS);
        }
        if (this.poolEntry.isExpired(System.currentTimeMillis())) {
            this.poolEntry.close();
            this.poolEntry.getTracker().reset();
        }
        this.conn = new ManagedClientConnectionImpl(this, this.connOperator, this.poolEntry);
        // monitorexit(this)
        return this.conn;
    }
    
    private void shutdownConnection(final HttpClientConnection httpClientConnection) {
        httpClientConnection.shutdown();
    }
    
    public void releaseConnection(final ManagedClientConnection managedClientConnection, final long n, final TimeUnit timeUnit) {
        Args.check(managedClientConnection instanceof ManagedClientConnectionImpl, "Connection class mismatch, connection not obtained from this manager");
        final ManagedClientConnectionImpl managedClientConnectionImpl = (ManagedClientConnectionImpl)managedClientConnection;
        // monitorenter(managedClientConnectionImpl2 = managedClientConnectionImpl)
        if (this.log.isDebugEnabled()) {
            this.log.debug("Releasing connection " + managedClientConnection);
        }
        if (managedClientConnectionImpl.getPoolEntry() == null) {
            // monitorexit(managedClientConnectionImpl2)
            return;
        }
        Asserts.check(managedClientConnectionImpl.getManager() == this, "Connection not obtained from this manager");
        // monitorenter(this)
        if (this.shutdown) {
            this.shutdownConnection(managedClientConnectionImpl);
            // monitorexit(this)
            // monitorexit(managedClientConnectionImpl2)
            return;
        }
        if (managedClientConnectionImpl.isOpen() && !managedClientConnectionImpl.isMarkedReusable()) {
            this.shutdownConnection(managedClientConnectionImpl);
        }
        if (managedClientConnectionImpl.isMarkedReusable()) {
            this.poolEntry.updateExpiry(n, (timeUnit != null) ? timeUnit : TimeUnit.MILLISECONDS);
            if (this.log.isDebugEnabled()) {
                String string;
                if (n > 0L) {
                    string = "for " + n + " " + timeUnit;
                }
                else {
                    string = "indefinitely";
                }
                this.log.debug("Connection can be kept alive " + string);
            }
        }
        managedClientConnectionImpl.detach();
        this.conn = null;
        if (this.poolEntry.isClosed()) {
            this.poolEntry = null;
        }
    }
    // monitorexit(this)
    // monitorexit(managedClientConnectionImpl2)
    
    public void closeExpiredConnections() {
        // monitorenter(this)
        this.assertNotShutdown();
        final long currentTimeMillis = System.currentTimeMillis();
        if (this.poolEntry != null && this.poolEntry.isExpired(currentTimeMillis)) {
            this.poolEntry.close();
            this.poolEntry.getTracker().reset();
        }
    }
    // monitorexit(this)
    
    public void closeIdleConnections(final long n, final TimeUnit timeUnit) {
        Args.notNull(timeUnit, "Time unit");
        // monitorenter(this)
        this.assertNotShutdown();
        long millis = timeUnit.toMillis(n);
        if (millis < 0L) {
            millis = 0L;
        }
        final long n2 = System.currentTimeMillis() - millis;
        if (this.poolEntry != null && this.poolEntry.getUpdated() <= n2) {
            this.poolEntry.close();
            this.poolEntry.getTracker().reset();
        }
    }
    // monitorexit(this)
    
    public void shutdown() {
        // monitorenter(this)
        this.shutdown = true;
        if (this.poolEntry != null) {
            this.poolEntry.close();
        }
        this.poolEntry = null;
        this.conn = null;
    }
    // monitorexit(this)
    
    static {
        COUNTER = new AtomicLong();
    }
}
