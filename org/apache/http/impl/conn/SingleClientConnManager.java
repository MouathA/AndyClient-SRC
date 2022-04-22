package org.apache.http.impl.conn;

import org.apache.http.conn.scheme.*;
import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.commons.logging.*;
import org.apache.http.util.*;
import java.util.concurrent.*;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.*;
import java.io.*;

@Deprecated
@ThreadSafe
public class SingleClientConnManager implements ClientConnectionManager
{
    private final Log log;
    public static final String MISUSE_MESSAGE = "Invalid use of SingleClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.";
    protected final SchemeRegistry schemeRegistry;
    protected final ClientConnectionOperator connOperator;
    protected final boolean alwaysShutDown;
    @GuardedBy("this")
    protected PoolEntry uniquePoolEntry;
    @GuardedBy("this")
    protected ConnAdapter managedConn;
    @GuardedBy("this")
    protected long lastReleaseTime;
    @GuardedBy("this")
    protected long connectionExpiresTime;
    protected boolean isShutDown;
    
    @Deprecated
    public SingleClientConnManager(final HttpParams httpParams, final SchemeRegistry schemeRegistry) {
        this(schemeRegistry);
    }
    
    public SingleClientConnManager(final SchemeRegistry schemeRegistry) {
        this.log = LogFactory.getLog(this.getClass());
        Args.notNull(schemeRegistry, "Scheme registry");
        this.schemeRegistry = schemeRegistry;
        this.connOperator = this.createConnectionOperator(schemeRegistry);
        this.uniquePoolEntry = new PoolEntry();
        this.managedConn = null;
        this.lastReleaseTime = -1L;
        this.alwaysShutDown = false;
        this.isShutDown = false;
    }
    
    public SingleClientConnManager() {
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
    
    protected final void assertStillUp() throws IllegalStateException {
        Asserts.check(!this.isShutDown, "Manager is shut down");
    }
    
    public final ClientConnectionRequest requestConnection(final HttpRoute httpRoute, final Object o) {
        return new ClientConnectionRequest(httpRoute, o) {
            final HttpRoute val$route;
            final Object val$state;
            final SingleClientConnManager this$0;
            
            public void abortRequest() {
            }
            
            public ManagedClientConnection getConnection(final long n, final TimeUnit timeUnit) {
                return this.this$0.getConnection(this.val$route, this.val$state);
            }
        };
    }
    
    public ManagedClientConnection getConnection(final HttpRoute httpRoute, final Object o) {
        Args.notNull(httpRoute, "Route");
        this.assertStillUp();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Get connection for route " + httpRoute);
        }
        // monitorenter(this)
        Asserts.check(this.managedConn == null, "Invalid use of SingleClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.");
        this.closeExpiredConnections();
        if (this.uniquePoolEntry.connection.isOpen()) {
            final RouteTracker tracker = this.uniquePoolEntry.tracker;
            final boolean b = tracker == null || !tracker.toRoute().equals(httpRoute);
        }
        if (false) {
            this.uniquePoolEntry.shutdown();
        }
        if (true) {
            this.uniquePoolEntry = new PoolEntry();
        }
        this.managedConn = new ConnAdapter(this.uniquePoolEntry, httpRoute);
        // monitorexit(this)
        return this.managedConn;
    }
    
    public void releaseConnection(final ManagedClientConnection managedClientConnection, final long n, final TimeUnit timeUnit) {
        Args.check(managedClientConnection instanceof ConnAdapter, "Connection class mismatch, connection not obtained from this manager");
        this.assertStillUp();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Releasing connection " + managedClientConnection);
        }
        final ConnAdapter connAdapter = (ConnAdapter)managedClientConnection;
        // monitorenter(connAdapter2 = connAdapter)
        if (connAdapter.poolEntry == null) {
            // monitorexit(connAdapter2)
            return;
        }
        Asserts.check(connAdapter.getManager() == this, "Connection not obtained from this manager");
        if (connAdapter.isOpen() && (this.alwaysShutDown || !connAdapter.isMarkedReusable())) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Released connection open but not reusable.");
            }
            connAdapter.shutdown();
        }
        connAdapter.detach();
        // monitorenter(this)
        this.managedConn = null;
        this.lastReleaseTime = System.currentTimeMillis();
        if (n > 0L) {
            this.connectionExpiresTime = timeUnit.toMillis(n) + this.lastReleaseTime;
        }
        else {
            this.connectionExpiresTime = Long.MAX_VALUE;
        }
    }
    // monitorexit(this)
    // monitorexit(connAdapter2)
    
    public void closeExpiredConnections() {
        if (System.currentTimeMillis() >= this.connectionExpiresTime) {
            this.closeIdleConnections(0L, TimeUnit.MILLISECONDS);
        }
    }
    
    public void closeIdleConnections(final long n, final TimeUnit timeUnit) {
        this.assertStillUp();
        Args.notNull(timeUnit, "Time unit");
        // monitorenter(this)
        if (this.managedConn == null && this.uniquePoolEntry.connection.isOpen() && this.lastReleaseTime <= System.currentTimeMillis() - timeUnit.toMillis(n)) {
            this.uniquePoolEntry.close();
        }
    }
    // monitorexit(this)
    
    public void shutdown() {
        this.isShutDown = true;
        // monitorenter(this)
        if (this.uniquePoolEntry != null) {
            this.uniquePoolEntry.shutdown();
        }
        this.uniquePoolEntry = null;
        this.managedConn = null;
    }
    // monitorexit(this)
    
    protected void revokeConnection() {
        final ConnAdapter managedConn = this.managedConn;
        if (managedConn == null) {
            return;
        }
        managedConn.detach();
        // monitorenter(this)
        this.uniquePoolEntry.shutdown();
    }
    // monitorexit(this)
    
    protected class ConnAdapter extends AbstractPooledConnAdapter
    {
        final SingleClientConnManager this$0;
        
        protected ConnAdapter(final SingleClientConnManager this$0, final PoolEntry poolEntry, final HttpRoute route) {
            super(this.this$0 = this$0, poolEntry);
            this.markReusable();
            poolEntry.route = route;
        }
    }
    
    protected class PoolEntry extends AbstractPoolEntry
    {
        final SingleClientConnManager this$0;
        
        protected PoolEntry(final SingleClientConnManager this$0) {
            this.this$0 = this$0;
            super(this$0.connOperator, null);
        }
        
        protected void close() throws IOException {
            this.shutdownEntry();
            if (this.connection.isOpen()) {
                this.connection.close();
            }
        }
        
        protected void shutdown() throws IOException {
            this.shutdownEntry();
            if (this.connection.isOpen()) {
                this.connection.shutdown();
            }
        }
    }
}
