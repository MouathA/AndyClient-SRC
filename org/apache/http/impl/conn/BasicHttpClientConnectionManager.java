package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import org.apache.http.conn.routing.*;
import org.apache.http.conn.socket.*;
import org.apache.http.conn.ssl.*;
import org.apache.http.config.*;
import org.apache.commons.logging.*;
import org.apache.http.conn.*;
import java.util.concurrent.*;
import java.util.*;
import org.apache.http.util.*;
import org.apache.http.protocol.*;
import org.apache.http.*;
import java.io.*;

@ThreadSafe
public class BasicHttpClientConnectionManager implements HttpClientConnectionManager, Closeable
{
    private final Log log;
    private final HttpClientConnectionOperator connectionOperator;
    private final HttpConnectionFactory connFactory;
    @GuardedBy("this")
    private ManagedHttpClientConnection conn;
    @GuardedBy("this")
    private HttpRoute route;
    @GuardedBy("this")
    private Object state;
    @GuardedBy("this")
    private long updated;
    @GuardedBy("this")
    private long expiry;
    @GuardedBy("this")
    private boolean leased;
    @GuardedBy("this")
    private SocketConfig socketConfig;
    @GuardedBy("this")
    private ConnectionConfig connConfig;
    @GuardedBy("this")
    private boolean shutdown;
    
    private static Registry getDefaultRegistry() {
        return RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
    }
    
    public BasicHttpClientConnectionManager(final Lookup lookup, final HttpConnectionFactory httpConnectionFactory, final SchemePortResolver schemePortResolver, final DnsResolver dnsResolver) {
        this.log = LogFactory.getLog(this.getClass());
        this.connectionOperator = new HttpClientConnectionOperator(lookup, schemePortResolver, dnsResolver);
        this.connFactory = ((httpConnectionFactory != null) ? httpConnectionFactory : ManagedHttpClientConnectionFactory.INSTANCE);
        this.expiry = Long.MAX_VALUE;
        this.socketConfig = SocketConfig.DEFAULT;
        this.connConfig = ConnectionConfig.DEFAULT;
    }
    
    public BasicHttpClientConnectionManager(final Lookup lookup, final HttpConnectionFactory httpConnectionFactory) {
        this(lookup, httpConnectionFactory, null, null);
    }
    
    public BasicHttpClientConnectionManager(final Lookup lookup) {
        this(lookup, null, null, null);
    }
    
    public BasicHttpClientConnectionManager() {
        this(getDefaultRegistry(), null, null, null);
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.shutdown();
        super.finalize();
    }
    
    public void close() {
        this.shutdown();
    }
    
    HttpRoute getRoute() {
        return this.route;
    }
    
    Object getState() {
        return this.state;
    }
    
    public synchronized SocketConfig getSocketConfig() {
        return this.socketConfig;
    }
    
    public synchronized void setSocketConfig(final SocketConfig socketConfig) {
        this.socketConfig = ((socketConfig != null) ? socketConfig : SocketConfig.DEFAULT);
    }
    
    public synchronized ConnectionConfig getConnectionConfig() {
        return this.connConfig;
    }
    
    public synchronized void setConnectionConfig(final ConnectionConfig connectionConfig) {
        this.connConfig = ((connectionConfig != null) ? connectionConfig : ConnectionConfig.DEFAULT);
    }
    
    public final ConnectionRequest requestConnection(final HttpRoute httpRoute, final Object o) {
        Args.notNull(httpRoute, "Route");
        return new ConnectionRequest(httpRoute, o) {
            final HttpRoute val$route;
            final Object val$state;
            final BasicHttpClientConnectionManager this$0;
            
            public boolean cancel() {
                return false;
            }
            
            public HttpClientConnection get(final long n, final TimeUnit timeUnit) {
                return this.this$0.getConnection(this.val$route, this.val$state);
            }
        };
    }
    
    private void closeConnection() {
        if (this.conn != null) {
            this.log.debug("Closing connection");
            this.conn.close();
            this.conn = null;
        }
    }
    
    private void shutdownConnection() {
        if (this.conn != null) {
            this.log.debug("Shutting down connection");
            this.conn.shutdown();
            this.conn = null;
        }
    }
    
    private void checkExpiry() {
        if (this.conn != null && System.currentTimeMillis() >= this.expiry) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Connection expired @ " + new Date(this.expiry));
            }
            this.closeConnection();
        }
    }
    
    synchronized HttpClientConnection getConnection(final HttpRoute route, final Object state) {
        Asserts.check(!this.shutdown, "Connection manager has been shut down");
        if (this.log.isDebugEnabled()) {
            this.log.debug("Get connection for route " + route);
        }
        Asserts.check(!this.leased, "Connection is still allocated");
        if (!LangUtils.equals(this.route, route) || !LangUtils.equals(this.state, state)) {
            this.closeConnection();
        }
        this.route = route;
        this.state = state;
        this.checkExpiry();
        if (this.conn == null) {
            this.conn = (ManagedHttpClientConnection)this.connFactory.create(route, this.connConfig);
        }
        this.leased = true;
        return this.conn;
    }
    
    public synchronized void releaseConnection(final HttpClientConnection httpClientConnection, final Object state, final long n, final TimeUnit timeUnit) {
        Args.notNull(httpClientConnection, "Connection");
        Asserts.check(httpClientConnection == this.conn, "Connection not obtained from this manager");
        if (this.log.isDebugEnabled()) {
            this.log.debug("Releasing connection " + httpClientConnection);
        }
        if (this.shutdown) {
            this.shutdownConnection();
            return;
        }
        this.updated = System.currentTimeMillis();
        if (!this.conn.isOpen()) {
            this.conn = null;
            this.route = null;
            this.conn = null;
            this.expiry = Long.MAX_VALUE;
        }
        else {
            this.state = state;
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
            if (n > 0L) {
                this.expiry = this.updated + timeUnit.toMillis(n);
            }
            else {
                this.expiry = Long.MAX_VALUE;
            }
        }
        this.leased = false;
    }
    
    public void connect(final HttpClientConnection httpClientConnection, final HttpRoute httpRoute, final int n, final HttpContext httpContext) throws IOException {
        Args.notNull(httpClientConnection, "Connection");
        Args.notNull(httpRoute, "HTTP route");
        Asserts.check(httpClientConnection == this.conn, "Connection not obtained from this manager");
        HttpHost httpHost;
        if (httpRoute.getProxyHost() != null) {
            httpHost = httpRoute.getProxyHost();
        }
        else {
            httpHost = httpRoute.getTargetHost();
        }
        this.connectionOperator.connect(this.conn, httpHost, httpRoute.getLocalSocketAddress(), n, this.socketConfig, httpContext);
    }
    
    public void upgrade(final HttpClientConnection httpClientConnection, final HttpRoute httpRoute, final HttpContext httpContext) throws IOException {
        Args.notNull(httpClientConnection, "Connection");
        Args.notNull(httpRoute, "HTTP route");
        Asserts.check(httpClientConnection == this.conn, "Connection not obtained from this manager");
        this.connectionOperator.upgrade(this.conn, httpRoute.getTargetHost(), httpContext);
    }
    
    public void routeComplete(final HttpClientConnection httpClientConnection, final HttpRoute httpRoute, final HttpContext httpContext) throws IOException {
    }
    
    public synchronized void closeExpiredConnections() {
        if (this.shutdown) {
            return;
        }
        if (!this.leased) {
            this.checkExpiry();
        }
    }
    
    public synchronized void closeIdleConnections(final long n, final TimeUnit timeUnit) {
        Args.notNull(timeUnit, "Time unit");
        if (this.shutdown) {
            return;
        }
        if (!this.leased) {
            long millis = timeUnit.toMillis(n);
            if (millis < 0L) {
                millis = 0L;
            }
            if (this.updated <= System.currentTimeMillis() - millis) {
                this.closeConnection();
            }
        }
    }
    
    public synchronized void shutdown() {
        if (this.shutdown) {
            return;
        }
        this.shutdown = true;
        this.shutdownConnection();
    }
}
