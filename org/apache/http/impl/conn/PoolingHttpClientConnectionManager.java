package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import org.apache.http.conn.socket.*;
import org.apache.http.conn.ssl.*;
import org.apache.commons.logging.*;
import org.apache.http.conn.routing.*;
import org.apache.http.concurrent.*;
import org.apache.http.util.*;
import org.apache.http.conn.*;
import org.apache.http.pool.*;
import org.apache.http.protocol.*;
import org.apache.http.*;
import java.net.*;
import java.io.*;
import org.apache.http.config.*;
import java.util.*;
import java.util.concurrent.*;

@ThreadSafe
public class PoolingHttpClientConnectionManager implements HttpClientConnectionManager, ConnPoolControl, Closeable
{
    private final Log log;
    private final ConfigData configData;
    private final CPool pool;
    private final HttpClientConnectionOperator connectionOperator;
    
    private static Registry getDefaultRegistry() {
        return RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
    }
    
    public PoolingHttpClientConnectionManager() {
        this(getDefaultRegistry());
    }
    
    public PoolingHttpClientConnectionManager(final long n, final TimeUnit timeUnit) {
        this(getDefaultRegistry(), null, null, null, n, timeUnit);
    }
    
    public PoolingHttpClientConnectionManager(final Registry registry) {
        this(registry, null, null);
    }
    
    public PoolingHttpClientConnectionManager(final Registry registry, final DnsResolver dnsResolver) {
        this(registry, null, dnsResolver);
    }
    
    public PoolingHttpClientConnectionManager(final Registry registry, final HttpConnectionFactory httpConnectionFactory) {
        this(registry, httpConnectionFactory, null);
    }
    
    public PoolingHttpClientConnectionManager(final HttpConnectionFactory httpConnectionFactory) {
        this(getDefaultRegistry(), httpConnectionFactory, null);
    }
    
    public PoolingHttpClientConnectionManager(final Registry registry, final HttpConnectionFactory httpConnectionFactory, final DnsResolver dnsResolver) {
        this(registry, httpConnectionFactory, null, dnsResolver, -1L, TimeUnit.MILLISECONDS);
    }
    
    public PoolingHttpClientConnectionManager(final Registry registry, final HttpConnectionFactory httpConnectionFactory, final SchemePortResolver schemePortResolver, final DnsResolver dnsResolver, final long n, final TimeUnit timeUnit) {
        this.log = LogFactory.getLog(this.getClass());
        this.configData = new ConfigData();
        this.pool = new CPool(new InternalConnectionFactory(this.configData, httpConnectionFactory), 2, 20, n, timeUnit);
        this.connectionOperator = new HttpClientConnectionOperator(registry, schemePortResolver, dnsResolver);
    }
    
    PoolingHttpClientConnectionManager(final CPool pool, final Lookup lookup, final SchemePortResolver schemePortResolver, final DnsResolver dnsResolver) {
        this.log = LogFactory.getLog(this.getClass());
        this.configData = new ConfigData();
        this.pool = pool;
        this.connectionOperator = new HttpClientConnectionOperator(lookup, schemePortResolver, dnsResolver);
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.shutdown();
        super.finalize();
    }
    
    public void close() {
        this.shutdown();
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
    
    private String format(final CPoolEntry cPoolEntry) {
        final StringBuilder sb = new StringBuilder();
        sb.append("[id: ").append(cPoolEntry.getId()).append("]");
        sb.append("[route: ").append(cPoolEntry.getRoute()).append("]");
        final Object state = cPoolEntry.getState();
        if (state != null) {
            sb.append("[state: ").append(state).append("]");
        }
        return sb.toString();
    }
    
    public ConnectionRequest requestConnection(final HttpRoute httpRoute, final Object o) {
        Args.notNull(httpRoute, "HTTP route");
        if (this.log.isDebugEnabled()) {
            this.log.debug("Connection request: " + this.format(httpRoute, o) + this.formatStats(httpRoute));
        }
        return new ConnectionRequest(this.pool.lease(httpRoute, o, null)) {
            final Future val$future;
            final PoolingHttpClientConnectionManager this$0;
            
            public boolean cancel() {
                return this.val$future.cancel(true);
            }
            
            public HttpClientConnection get(final long n, final TimeUnit timeUnit) throws InterruptedException, ExecutionException, ConnectionPoolTimeoutException {
                return this.this$0.leaseConnection(this.val$future, n, timeUnit);
            }
        };
    }
    
    protected HttpClientConnection leaseConnection(final Future future, final long n, final TimeUnit timeUnit) throws InterruptedException, ExecutionException, ConnectionPoolTimeoutException {
        final CPoolEntry cPoolEntry = future.get(n, timeUnit);
        if (cPoolEntry == null || future.isCancelled()) {
            throw new InterruptedException();
        }
        Asserts.check(cPoolEntry.getConnection() != null, "Pool entry with no connection");
        if (this.log.isDebugEnabled()) {
            this.log.debug("Connection leased: " + this.format(cPoolEntry) + this.formatStats((HttpRoute)cPoolEntry.getRoute()));
        }
        return CPoolProxy.newProxy(cPoolEntry);
    }
    
    public void releaseConnection(final HttpClientConnection httpClientConnection, final Object state, final long n, final TimeUnit timeUnit) {
        Args.notNull(httpClientConnection, "Managed connection");
        // monitorenter(httpClientConnection)
        final CPoolEntry detach = CPoolProxy.detach(httpClientConnection);
        if (detach == null) {
            // monitorexit(httpClientConnection)
            return;
        }
        final ManagedHttpClientConnection managedHttpClientConnection = (ManagedHttpClientConnection)detach.getConnection();
        if (managedHttpClientConnection.isOpen()) {
            detach.setState(state);
            detach.updateExpiry(n, (timeUnit != null) ? timeUnit : TimeUnit.MILLISECONDS);
            if (this.log.isDebugEnabled()) {
                String string;
                if (n > 0L) {
                    string = "for " + n / 1000.0 + " seconds";
                }
                else {
                    string = "indefinitely";
                }
                this.log.debug("Connection " + this.format(detach) + " can be kept alive " + string);
            }
        }
        this.pool.release(detach, managedHttpClientConnection.isOpen() && detach.isRouteComplete());
        if (this.log.isDebugEnabled()) {
            this.log.debug("Connection released: " + this.format(detach) + this.formatStats((HttpRoute)detach.getRoute()));
        }
    }
    // monitorexit(httpClientConnection)
    
    public void connect(final HttpClientConnection httpClientConnection, final HttpRoute httpRoute, final int n, final HttpContext httpContext) throws IOException {
        Args.notNull(httpClientConnection, "Managed Connection");
        Args.notNull(httpRoute, "HTTP route");
        // monitorenter(httpClientConnection)
        final ManagedHttpClientConnection managedHttpClientConnection = (ManagedHttpClientConnection)CPoolProxy.getPoolEntry(httpClientConnection).getConnection();
        // monitorexit(httpClientConnection)
        HttpHost httpHost;
        if (httpRoute.getProxyHost() != null) {
            httpHost = httpRoute.getProxyHost();
        }
        else {
            httpHost = httpRoute.getTargetHost();
        }
        final InetSocketAddress localSocketAddress = httpRoute.getLocalSocketAddress();
        SocketConfig socketConfig = this.configData.getSocketConfig(httpHost);
        if (socketConfig == null) {
            socketConfig = this.configData.getDefaultSocketConfig();
        }
        if (socketConfig == null) {
            socketConfig = SocketConfig.DEFAULT;
        }
        this.connectionOperator.connect(managedHttpClientConnection, httpHost, localSocketAddress, n, socketConfig, httpContext);
    }
    
    public void upgrade(final HttpClientConnection httpClientConnection, final HttpRoute httpRoute, final HttpContext httpContext) throws IOException {
        Args.notNull(httpClientConnection, "Managed Connection");
        Args.notNull(httpRoute, "HTTP route");
        // monitorenter(httpClientConnection)
        final ManagedHttpClientConnection managedHttpClientConnection = (ManagedHttpClientConnection)CPoolProxy.getPoolEntry(httpClientConnection).getConnection();
        // monitorexit(httpClientConnection)
        this.connectionOperator.upgrade(managedHttpClientConnection, httpRoute.getTargetHost(), httpContext);
    }
    
    public void routeComplete(final HttpClientConnection httpClientConnection, final HttpRoute httpRoute, final HttpContext httpContext) throws IOException {
        Args.notNull(httpClientConnection, "Managed Connection");
        Args.notNull(httpRoute, "HTTP route");
        // monitorenter(httpClientConnection)
        CPoolProxy.getPoolEntry(httpClientConnection).markRouteComplete();
    }
    // monitorexit(httpClientConnection)
    
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
    
    public SocketConfig getDefaultSocketConfig() {
        return this.configData.getDefaultSocketConfig();
    }
    
    public void setDefaultSocketConfig(final SocketConfig defaultSocketConfig) {
        this.configData.setDefaultSocketConfig(defaultSocketConfig);
    }
    
    public ConnectionConfig getDefaultConnectionConfig() {
        return this.configData.getDefaultConnectionConfig();
    }
    
    public void setDefaultConnectionConfig(final ConnectionConfig defaultConnectionConfig) {
        this.configData.setDefaultConnectionConfig(defaultConnectionConfig);
    }
    
    public SocketConfig getSocketConfig(final HttpHost httpHost) {
        return this.configData.getSocketConfig(httpHost);
    }
    
    public void setSocketConfig(final HttpHost httpHost, final SocketConfig socketConfig) {
        this.configData.setSocketConfig(httpHost, socketConfig);
    }
    
    public ConnectionConfig getConnectionConfig(final HttpHost httpHost) {
        return this.configData.getConnectionConfig(httpHost);
    }
    
    public void setConnectionConfig(final HttpHost httpHost, final ConnectionConfig connectionConfig) {
        this.configData.setConnectionConfig(httpHost, connectionConfig);
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
    
    static class InternalConnectionFactory implements ConnFactory
    {
        private final ConfigData configData;
        private final HttpConnectionFactory connFactory;
        
        InternalConnectionFactory(final ConfigData configData, final HttpConnectionFactory httpConnectionFactory) {
            this.configData = ((configData != null) ? configData : new ConfigData());
            this.connFactory = ((httpConnectionFactory != null) ? httpConnectionFactory : ManagedHttpClientConnectionFactory.INSTANCE);
        }
        
        public ManagedHttpClientConnection create(final HttpRoute httpRoute) throws IOException {
            ConnectionConfig connectionConfig = null;
            if (httpRoute.getProxyHost() != null) {
                connectionConfig = this.configData.getConnectionConfig(httpRoute.getProxyHost());
            }
            if (connectionConfig == null) {
                connectionConfig = this.configData.getConnectionConfig(httpRoute.getTargetHost());
            }
            if (connectionConfig == null) {
                connectionConfig = this.configData.getDefaultConnectionConfig();
            }
            if (connectionConfig == null) {
                connectionConfig = ConnectionConfig.DEFAULT;
            }
            return (ManagedHttpClientConnection)this.connFactory.create(httpRoute, connectionConfig);
        }
        
        public Object create(final Object o) throws IOException {
            return this.create((HttpRoute)o);
        }
    }
    
    static class ConfigData
    {
        private final Map socketConfigMap;
        private final Map connectionConfigMap;
        private SocketConfig defaultSocketConfig;
        private ConnectionConfig defaultConnectionConfig;
        
        ConfigData() {
            this.socketConfigMap = new ConcurrentHashMap();
            this.connectionConfigMap = new ConcurrentHashMap();
        }
        
        public SocketConfig getDefaultSocketConfig() {
            return this.defaultSocketConfig;
        }
        
        public void setDefaultSocketConfig(final SocketConfig defaultSocketConfig) {
            this.defaultSocketConfig = defaultSocketConfig;
        }
        
        public ConnectionConfig getDefaultConnectionConfig() {
            return this.defaultConnectionConfig;
        }
        
        public void setDefaultConnectionConfig(final ConnectionConfig defaultConnectionConfig) {
            this.defaultConnectionConfig = defaultConnectionConfig;
        }
        
        public SocketConfig getSocketConfig(final HttpHost httpHost) {
            return this.socketConfigMap.get(httpHost);
        }
        
        public void setSocketConfig(final HttpHost httpHost, final SocketConfig socketConfig) {
            this.socketConfigMap.put(httpHost, socketConfig);
        }
        
        public ConnectionConfig getConnectionConfig(final HttpHost httpHost) {
            return this.connectionConfigMap.get(httpHost);
        }
        
        public void setConnectionConfig(final HttpHost httpHost, final ConnectionConfig connectionConfig) {
            this.connectionConfigMap.put(httpHost, connectionConfig);
        }
    }
}
