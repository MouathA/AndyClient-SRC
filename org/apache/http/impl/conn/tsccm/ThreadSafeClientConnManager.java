package org.apache.http.impl.conn.tsccm;

import org.apache.http.annotation.*;
import org.apache.http.conn.scheme.*;
import java.util.concurrent.*;
import org.apache.commons.logging.*;
import org.apache.http.params.*;
import org.apache.http.conn.params.*;
import org.apache.http.conn.routing.*;
import org.apache.http.impl.conn.*;
import org.apache.http.conn.*;
import org.apache.http.util.*;

@Deprecated
@ThreadSafe
public class ThreadSafeClientConnManager implements ClientConnectionManager
{
    private final Log log;
    protected final SchemeRegistry schemeRegistry;
    protected final AbstractConnPool connectionPool;
    protected final ConnPoolByRoute pool;
    protected final ClientConnectionOperator connOperator;
    protected final ConnPerRouteBean connPerRoute;
    
    public ThreadSafeClientConnManager(final SchemeRegistry schemeRegistry) {
        this(schemeRegistry, -1L, TimeUnit.MILLISECONDS);
    }
    
    public ThreadSafeClientConnManager() {
        this(SchemeRegistryFactory.createDefault());
    }
    
    public ThreadSafeClientConnManager(final SchemeRegistry schemeRegistry, final long n, final TimeUnit timeUnit) {
        this(schemeRegistry, n, timeUnit, new ConnPerRouteBean());
    }
    
    public ThreadSafeClientConnManager(final SchemeRegistry schemeRegistry, final long n, final TimeUnit timeUnit, final ConnPerRouteBean connPerRoute) {
        Args.notNull(schemeRegistry, "Scheme registry");
        this.log = LogFactory.getLog(this.getClass());
        this.schemeRegistry = schemeRegistry;
        this.connPerRoute = connPerRoute;
        this.connOperator = this.createConnectionOperator(schemeRegistry);
        this.pool = this.createConnectionPool(n, timeUnit);
        this.connectionPool = this.pool;
    }
    
    @Deprecated
    public ThreadSafeClientConnManager(final HttpParams httpParams, final SchemeRegistry schemeRegistry) {
        Args.notNull(schemeRegistry, "Scheme registry");
        this.log = LogFactory.getLog(this.getClass());
        this.schemeRegistry = schemeRegistry;
        this.connPerRoute = new ConnPerRouteBean();
        this.connOperator = this.createConnectionOperator(schemeRegistry);
        this.pool = (ConnPoolByRoute)this.createConnectionPool(httpParams);
        this.connectionPool = this.pool;
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.shutdown();
        super.finalize();
    }
    
    @Deprecated
    protected AbstractConnPool createConnectionPool(final HttpParams httpParams) {
        return new ConnPoolByRoute(this.connOperator, httpParams);
    }
    
    protected ConnPoolByRoute createConnectionPool(final long n, final TimeUnit timeUnit) {
        return new ConnPoolByRoute(this.connOperator, this.connPerRoute, 20, n, timeUnit);
    }
    
    protected ClientConnectionOperator createConnectionOperator(final SchemeRegistry schemeRegistry) {
        return new DefaultClientConnectionOperator(schemeRegistry);
    }
    
    public SchemeRegistry getSchemeRegistry() {
        return this.schemeRegistry;
    }
    
    public ClientConnectionRequest requestConnection(final HttpRoute httpRoute, final Object o) {
        return new ClientConnectionRequest(this.pool.requestPoolEntry(httpRoute, o), httpRoute) {
            final PoolEntryRequest val$poolRequest;
            final HttpRoute val$route;
            final ThreadSafeClientConnManager this$0;
            
            public void abortRequest() {
                this.val$poolRequest.abortRequest();
            }
            
            public ManagedClientConnection getConnection(final long n, final TimeUnit timeUnit) throws InterruptedException, ConnectionPoolTimeoutException {
                Args.notNull(this.val$route, "Route");
                if (ThreadSafeClientConnManager.access$000(this.this$0).isDebugEnabled()) {
                    ThreadSafeClientConnManager.access$000(this.this$0).debug("Get connection: " + this.val$route + ", timeout = " + n);
                }
                return new BasicPooledConnAdapter(this.this$0, this.val$poolRequest.getPoolEntry(n, timeUnit));
            }
        };
    }
    
    public void releaseConnection(final ManagedClientConnection managedClientConnection, final long n, final TimeUnit timeUnit) {
        Args.check(managedClientConnection instanceof BasicPooledConnAdapter, "Connection class mismatch, connection not obtained from this manager");
        final BasicPooledConnAdapter basicPooledConnAdapter = (BasicPooledConnAdapter)managedClientConnection;
        if (basicPooledConnAdapter.getPoolEntry() != null) {
            Asserts.check(basicPooledConnAdapter.getManager() == this, "Connection not obtained from this manager");
        }
        // monitorenter(basicPooledConnAdapter2 = basicPooledConnAdapter)
        final BasicPoolEntry basicPoolEntry = (BasicPoolEntry)basicPooledConnAdapter.getPoolEntry();
        if (basicPoolEntry == null) {
            // monitorexit(basicPooledConnAdapter2)
            return;
        }
        if (basicPooledConnAdapter.isOpen() && !basicPooledConnAdapter.isMarkedReusable()) {
            basicPooledConnAdapter.shutdown();
        }
        final boolean markedReusable = basicPooledConnAdapter.isMarkedReusable();
        if (this.log.isDebugEnabled()) {
            if (markedReusable) {
                this.log.debug("Released connection is reusable.");
            }
            else {
                this.log.debug("Released connection is not reusable.");
            }
        }
        basicPooledConnAdapter.detach();
        this.pool.freeEntry(basicPoolEntry, markedReusable, n, timeUnit);
    }
    // monitorexit(basicPooledConnAdapter2)
    
    public void shutdown() {
        this.log.debug("Shutting down");
        this.pool.shutdown();
    }
    
    public int getConnectionsInPool(final HttpRoute httpRoute) {
        return this.pool.getConnectionsInPool(httpRoute);
    }
    
    public int getConnectionsInPool() {
        return this.pool.getConnectionsInPool();
    }
    
    public void closeIdleConnections(final long n, final TimeUnit timeUnit) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Closing connections idle longer than " + n + " " + timeUnit);
        }
        this.pool.closeIdleConnections(n, timeUnit);
    }
    
    public void closeExpiredConnections() {
        this.log.debug("Closing expired connections");
        this.pool.closeExpiredConnections();
    }
    
    public int getMaxTotal() {
        return this.pool.getMaxTotalConnections();
    }
    
    public void setMaxTotal(final int maxTotalConnections) {
        this.pool.setMaxTotalConnections(maxTotalConnections);
    }
    
    public int getDefaultMaxPerRoute() {
        return this.connPerRoute.getDefaultMaxPerRoute();
    }
    
    public void setDefaultMaxPerRoute(final int defaultMaxPerRoute) {
        this.connPerRoute.setDefaultMaxPerRoute(defaultMaxPerRoute);
    }
    
    public int getMaxForRoute(final HttpRoute httpRoute) {
        return this.connPerRoute.getMaxForRoute(httpRoute);
    }
    
    public void setMaxForRoute(final HttpRoute httpRoute, final int n) {
        this.connPerRoute.setMaxForRoute(httpRoute, n);
    }
    
    static Log access$000(final ThreadSafeClientConnManager threadSafeClientConnManager) {
        return threadSafeClientConnManager.log;
    }
}
