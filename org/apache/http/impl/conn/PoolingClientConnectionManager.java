package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import org.apache.http.conn.scheme.*;
import org.apache.commons.logging.*;
import org.apache.http.conn.routing.*;
import java.util.concurrent.*;
import org.apache.http.conn.*;
import org.apache.http.util.*;
import org.apache.http.pool.*;

@Deprecated
@ThreadSafe
public class PoolingClientConnectionManager implements ClientConnectionManager, ConnPoolControl
{
    private final Log log;
    private final SchemeRegistry schemeRegistry;
    private final HttpConnPool pool;
    private final ClientConnectionOperator operator;
    private final DnsResolver dnsResolver;
    
    public PoolingClientConnectionManager(final SchemeRegistry schemeRegistry) {
        this(schemeRegistry, -1L, TimeUnit.MILLISECONDS);
    }
    
    public PoolingClientConnectionManager(final SchemeRegistry schemeRegistry, final DnsResolver dnsResolver) {
        this(schemeRegistry, -1L, TimeUnit.MILLISECONDS, dnsResolver);
    }
    
    public PoolingClientConnectionManager() {
        this(SchemeRegistryFactory.createDefault());
    }
    
    public PoolingClientConnectionManager(final SchemeRegistry schemeRegistry, final long n, final TimeUnit timeUnit) {
        this(schemeRegistry, n, timeUnit, new SystemDefaultDnsResolver());
    }
    
    public PoolingClientConnectionManager(final SchemeRegistry schemeRegistry, final long n, final TimeUnit timeUnit, final DnsResolver dnsResolver) {
        this.log = LogFactory.getLog(this.getClass());
        Args.notNull(schemeRegistry, "Scheme registry");
        Args.notNull(dnsResolver, "DNS resolver");
        this.schemeRegistry = schemeRegistry;
        this.dnsResolver = dnsResolver;
        this.operator = this.createConnectionOperator(schemeRegistry);
        this.pool = new HttpConnPool(this.log, this.operator, 2, 20, n, timeUnit);
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.shutdown();
        super.finalize();
    }
    
    protected ClientConnectionOperator createConnectionOperator(final SchemeRegistry schemeRegistry) {
        return new DefaultClientConnectionOperator(schemeRegistry, this.dnsResolver);
    }
    
    public SchemeRegistry getSchemeRegistry() {
        return this.schemeRegistry;
    }
    
    private String format(final HttpRoute httpRoute, final Object o) {
        final StringBuilder sb = new StringBuilder();
        sb.append("[route: ").append(httpRoute).append("]");
        if (o != null) {
            sb.append("[state: ").append(o).append("]");
        }
        return sb.toString();
    }
    
    private String formatStats(final HttpRoute httpRoute) {
        final StringBuilder sb = new StringBuilder();
        final PoolStats totalStats = this.pool.getTotalStats();
        final PoolStats stats = this.pool.getStats(httpRoute);
        sb.append("[total kept alive: ").append(totalStats.getAvailable()).append("; ");
        sb.append("route allocated: ").append(stats.getLeased() + stats.getAvailable());
        sb.append(" of ").append(stats.getMax()).append("; ");
        sb.append("total allocated: ").append(totalStats.getLeased() + totalStats.getAvailable());
        sb.append(" of ").append(totalStats.getMax()).append("]");
        return sb.toString();
    }
    
    private String format(final HttpPoolEntry httpPoolEntry) {
        final StringBuilder sb = new StringBuilder();
        sb.append("[id: ").append(httpPoolEntry.getId()).append("]");
        sb.append("[route: ").append(httpPoolEntry.getRoute()).append("]");
        final Object state = httpPoolEntry.getState();
        if (state != null) {
            sb.append("[state: ").append(state).append("]");
        }
        return sb.toString();
    }
    
    public ClientConnectionRequest requestConnection(final HttpRoute httpRoute, final Object o) {
        Args.notNull(httpRoute, "HTTP route");
        if (this.log.isDebugEnabled()) {
            this.log.debug("Connection request: " + this.format(httpRoute, o) + this.formatStats(httpRoute));
        }
        return new ClientConnectionRequest(this.pool.lease(httpRoute, o)) {
            final Future val$future;
            final PoolingClientConnectionManager this$0;
            
            public void abortRequest() {
                this.val$future.cancel(true);
            }
            
            public ManagedClientConnection getConnection(final long n, final TimeUnit timeUnit) throws InterruptedException, ConnectionPoolTimeoutException {
                return this.this$0.leaseConnection(this.val$future, n, timeUnit);
            }
        };
    }
    
    ManagedClientConnection leaseConnection(final Future future, final long n, final TimeUnit timeUnit) throws InterruptedException, ConnectionPoolTimeoutException {
        final HttpPoolEntry httpPoolEntry = future.get(n, timeUnit);
        if (httpPoolEntry == null || future.isCancelled()) {
            throw new InterruptedException();
        }
        Asserts.check(httpPoolEntry.getConnection() != null, "Pool entry with no connection");
        if (this.log.isDebugEnabled()) {
            this.log.debug("Connection leased: " + this.format(httpPoolEntry) + this.formatStats((HttpRoute)httpPoolEntry.getRoute()));
        }
        return new ManagedClientConnectionImpl(this, this.operator, httpPoolEntry);
    }
    
    public void releaseConnection(final ManagedClientConnection managedClientConnection, final long n, final TimeUnit timeUnit) {
        Args.check(managedClientConnection instanceof ManagedClientConnectionImpl, "Connection class mismatch, connection not obtained from this manager");
        final ManagedClientConnectionImpl managedClientConnectionImpl = (ManagedClientConnectionImpl)managedClientConnection;
        Asserts.check(managedClientConnectionImpl.getManager() == this, "Connection not obtained from this manager");
        // monitorenter(managedClientConnectionImpl2 = managedClientConnectionImpl)
        final HttpPoolEntry detach = managedClientConnectionImpl.detach();
        if (detach == null) {
            // monitorexit(managedClientConnectionImpl2)
            return;
        }
        if (managedClientConnectionImpl.isOpen() && !managedClientConnectionImpl.isMarkedReusable()) {
            managedClientConnectionImpl.shutdown();
        }
        if (managedClientConnectionImpl.isMarkedReusable()) {
            detach.updateExpiry(n, (timeUnit != null) ? timeUnit : TimeUnit.MILLISECONDS);
            if (this.log.isDebugEnabled()) {
                String string;
                if (n > 0L) {
                    string = "for " + n + " " + timeUnit;
                }
                else {
                    string = "indefinitely";
                }
                this.log.debug("Connection " + this.format(detach) + " can be kept alive " + string);
            }
        }
        this.pool.release(detach, managedClientConnectionImpl.isMarkedReusable());
        if (this.log.isDebugEnabled()) {
            this.log.debug("Connection released: " + this.format(detach) + this.formatStats((HttpRoute)detach.getRoute()));
        }
    }
    // monitorexit(managedClientConnectionImpl2)
    
    public void shutdown() {
        this.log.debug("Connection manager is shutting down");
        this.pool.shutdown();
        this.log.debug("Connection manager shut down");
    }
    
    public void closeIdleConnections(final long n, final TimeUnit timeUnit) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Closing connections idle longer than " + n + " " + timeUnit);
        }
        this.pool.closeIdle(n, timeUnit);
    }
    
    public void closeExpiredConnections() {
        this.log.debug("Closing expired connections");
        this.pool.closeExpired();
    }
    
    public int getMaxTotal() {
        return this.pool.getMaxTotal();
    }
    
    public void setMaxTotal(final int maxTotal) {
        this.pool.setMaxTotal(maxTotal);
    }
    
    public int getDefaultMaxPerRoute() {
        return this.pool.getDefaultMaxPerRoute();
    }
    
    public void setDefaultMaxPerRoute(final int defaultMaxPerRoute) {
        this.pool.setDefaultMaxPerRoute(defaultMaxPerRoute);
    }
    
    public int getMaxPerRoute(final HttpRoute httpRoute) {
        return this.pool.getMaxPerRoute(httpRoute);
    }
    
    public void setMaxPerRoute(final HttpRoute httpRoute, final int n) {
        this.pool.setMaxPerRoute(httpRoute, n);
    }
    
    public PoolStats getTotalStats() {
        return this.pool.getTotalStats();
    }
    
    public PoolStats getStats(final HttpRoute httpRoute) {
        return this.pool.getStats(httpRoute);
    }
    
    public PoolStats getStats(final Object o) {
        return this.getStats((HttpRoute)o);
    }
    
    public int getMaxPerRoute(final Object o) {
        return this.getMaxPerRoute((HttpRoute)o);
    }
    
    public void setMaxPerRoute(final Object o, final int n) {
        this.setMaxPerRoute((HttpRoute)o, n);
    }
}
