package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import org.apache.http.conn.*;
import java.net.*;
import javax.net.ssl.*;
import org.apache.http.protocol.*;
import org.apache.http.params.*;
import org.apache.http.util.*;
import java.io.*;
import org.apache.http.conn.routing.*;
import org.apache.http.*;
import java.util.concurrent.*;

@Deprecated
@NotThreadSafe
class ManagedClientConnectionImpl implements ManagedClientConnection
{
    private final ClientConnectionManager manager;
    private final ClientConnectionOperator operator;
    private HttpPoolEntry poolEntry;
    private boolean reusable;
    private long duration;
    
    ManagedClientConnectionImpl(final ClientConnectionManager manager, final ClientConnectionOperator operator, final HttpPoolEntry poolEntry) {
        Args.notNull(manager, "Connection manager");
        Args.notNull(operator, "Connection operator");
        Args.notNull(poolEntry, "HTTP pool entry");
        this.manager = manager;
        this.operator = operator;
        this.poolEntry = poolEntry;
        this.reusable = false;
        this.duration = Long.MAX_VALUE;
    }
    
    public String getId() {
        return null;
    }
    
    HttpPoolEntry getPoolEntry() {
        return this.poolEntry;
    }
    
    HttpPoolEntry detach() {
        final HttpPoolEntry poolEntry = this.poolEntry;
        this.poolEntry = null;
        return poolEntry;
    }
    
    public ClientConnectionManager getManager() {
        return this.manager;
    }
    
    private OperatedClientConnection getConnection() {
        final HttpPoolEntry poolEntry = this.poolEntry;
        if (poolEntry == null) {
            return null;
        }
        return (OperatedClientConnection)poolEntry.getConnection();
    }
    
    private OperatedClientConnection ensureConnection() {
        final HttpPoolEntry poolEntry = this.poolEntry;
        if (poolEntry == null) {
            throw new ConnectionShutdownException();
        }
        return (OperatedClientConnection)poolEntry.getConnection();
    }
    
    private HttpPoolEntry ensurePoolEntry() {
        final HttpPoolEntry poolEntry = this.poolEntry;
        if (poolEntry == null) {
            throw new ConnectionShutdownException();
        }
        return poolEntry;
    }
    
    public void close() throws IOException {
        final HttpPoolEntry poolEntry = this.poolEntry;
        if (poolEntry != null) {
            final OperatedClientConnection operatedClientConnection = (OperatedClientConnection)poolEntry.getConnection();
            poolEntry.getTracker().reset();
            operatedClientConnection.close();
        }
    }
    
    public void shutdown() throws IOException {
        final HttpPoolEntry poolEntry = this.poolEntry;
        if (poolEntry != null) {
            final OperatedClientConnection operatedClientConnection = (OperatedClientConnection)poolEntry.getConnection();
            poolEntry.getTracker().reset();
            operatedClientConnection.shutdown();
        }
    }
    
    public boolean isOpen() {
        final OperatedClientConnection connection = this.getConnection();
        return connection != null && connection.isOpen();
    }
    
    public boolean isStale() {
        final OperatedClientConnection connection = this.getConnection();
        return connection == null || connection.isStale();
    }
    
    public void setSocketTimeout(final int socketTimeout) {
        this.ensureConnection().setSocketTimeout(socketTimeout);
    }
    
    public int getSocketTimeout() {
        return this.ensureConnection().getSocketTimeout();
    }
    
    public HttpConnectionMetrics getMetrics() {
        return this.ensureConnection().getMetrics();
    }
    
    public void flush() throws IOException {
        this.ensureConnection().flush();
    }
    
    public boolean isResponseAvailable(final int n) throws IOException {
        return this.ensureConnection().isResponseAvailable(n);
    }
    
    public void receiveResponseEntity(final HttpResponse httpResponse) throws HttpException, IOException {
        this.ensureConnection().receiveResponseEntity(httpResponse);
    }
    
    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
        return this.ensureConnection().receiveResponseHeader();
    }
    
    public void sendRequestEntity(final HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws HttpException, IOException {
        this.ensureConnection().sendRequestEntity(httpEntityEnclosingRequest);
    }
    
    public void sendRequestHeader(final HttpRequest httpRequest) throws HttpException, IOException {
        this.ensureConnection().sendRequestHeader(httpRequest);
    }
    
    public InetAddress getLocalAddress() {
        return this.ensureConnection().getLocalAddress();
    }
    
    public int getLocalPort() {
        return this.ensureConnection().getLocalPort();
    }
    
    public InetAddress getRemoteAddress() {
        return this.ensureConnection().getRemoteAddress();
    }
    
    public int getRemotePort() {
        return this.ensureConnection().getRemotePort();
    }
    
    public boolean isSecure() {
        return this.ensureConnection().isSecure();
    }
    
    public void bind(final Socket socket) throws IOException {
        throw new UnsupportedOperationException();
    }
    
    public Socket getSocket() {
        return this.ensureConnection().getSocket();
    }
    
    public SSLSession getSSLSession() {
        final OperatedClientConnection ensureConnection = this.ensureConnection();
        SSLSession session = null;
        final Socket socket = ensureConnection.getSocket();
        if (socket instanceof SSLSocket) {
            session = ((SSLSocket)socket).getSession();
        }
        return session;
    }
    
    public Object getAttribute(final String s) {
        final OperatedClientConnection ensureConnection = this.ensureConnection();
        if (ensureConnection instanceof HttpContext) {
            return ((HttpContext)ensureConnection).getAttribute(s);
        }
        return null;
    }
    
    public Object removeAttribute(final String s) {
        final OperatedClientConnection ensureConnection = this.ensureConnection();
        if (ensureConnection instanceof HttpContext) {
            return ((HttpContext)ensureConnection).removeAttribute(s);
        }
        return null;
    }
    
    public void setAttribute(final String s, final Object o) {
        final OperatedClientConnection ensureConnection = this.ensureConnection();
        if (ensureConnection instanceof HttpContext) {
            ((HttpContext)ensureConnection).setAttribute(s, o);
        }
    }
    
    public HttpRoute getRoute() {
        return this.ensurePoolEntry().getEffectiveRoute();
    }
    
    public void open(final HttpRoute httpRoute, final HttpContext httpContext, final HttpParams httpParams) throws IOException {
        Args.notNull(httpRoute, "Route");
        Args.notNull(httpParams, "HTTP parameters");
        // monitorenter(this)
        if (this.poolEntry == null) {
            throw new ConnectionShutdownException();
        }
        final RouteTracker tracker = this.poolEntry.getTracker();
        Asserts.notNull(tracker, "Route tracker");
        Asserts.check(!tracker.isConnected(), "Connection already open");
        final OperatedClientConnection operatedClientConnection = (OperatedClientConnection)this.poolEntry.getConnection();
        // monitorexit(this)
        final HttpHost proxyHost = httpRoute.getProxyHost();
        this.operator.openConnection(operatedClientConnection, (proxyHost != null) ? proxyHost : httpRoute.getTargetHost(), httpRoute.getLocalAddress(), httpContext, httpParams);
        // monitorenter(this)
        if (this.poolEntry == null) {
            throw new InterruptedIOException();
        }
        final RouteTracker tracker2 = this.poolEntry.getTracker();
        if (proxyHost == null) {
            tracker2.connectTarget(operatedClientConnection.isSecure());
        }
        else {
            tracker2.connectProxy(proxyHost, operatedClientConnection.isSecure());
        }
    }
    // monitorexit(this)
    
    public void tunnelTarget(final boolean b, final HttpParams httpParams) throws IOException {
        Args.notNull(httpParams, "HTTP parameters");
        // monitorenter(this)
        if (this.poolEntry == null) {
            throw new ConnectionShutdownException();
        }
        final RouteTracker tracker = this.poolEntry.getTracker();
        Asserts.notNull(tracker, "Route tracker");
        Asserts.check(tracker.isConnected(), "Connection not open");
        Asserts.check(!tracker.isTunnelled(), "Connection is already tunnelled");
        final HttpHost targetHost = tracker.getTargetHost();
        final OperatedClientConnection operatedClientConnection = (OperatedClientConnection)this.poolEntry.getConnection();
        // monitorexit(this)
        operatedClientConnection.update(null, targetHost, b, httpParams);
        // monitorenter(this)
        if (this.poolEntry == null) {
            throw new InterruptedIOException();
        }
        this.poolEntry.getTracker().tunnelTarget(b);
    }
    // monitorexit(this)
    
    public void tunnelProxy(final HttpHost httpHost, final boolean b, final HttpParams httpParams) throws IOException {
        Args.notNull(httpHost, "Next proxy");
        Args.notNull(httpParams, "HTTP parameters");
        // monitorenter(this)
        if (this.poolEntry == null) {
            throw new ConnectionShutdownException();
        }
        final RouteTracker tracker = this.poolEntry.getTracker();
        Asserts.notNull(tracker, "Route tracker");
        Asserts.check(tracker.isConnected(), "Connection not open");
        final OperatedClientConnection operatedClientConnection = (OperatedClientConnection)this.poolEntry.getConnection();
        // monitorexit(this)
        operatedClientConnection.update(null, httpHost, b, httpParams);
        // monitorenter(this)
        if (this.poolEntry == null) {
            throw new InterruptedIOException();
        }
        this.poolEntry.getTracker().tunnelProxy(httpHost, b);
    }
    // monitorexit(this)
    
    public void layerProtocol(final HttpContext httpContext, final HttpParams httpParams) throws IOException {
        Args.notNull(httpParams, "HTTP parameters");
        // monitorenter(this)
        if (this.poolEntry == null) {
            throw new ConnectionShutdownException();
        }
        final RouteTracker tracker = this.poolEntry.getTracker();
        Asserts.notNull(tracker, "Route tracker");
        Asserts.check(tracker.isConnected(), "Connection not open");
        Asserts.check(tracker.isTunnelled(), "Protocol layering without a tunnel not supported");
        Asserts.check(!tracker.isLayered(), "Multiple protocol layering not supported");
        final HttpHost targetHost = tracker.getTargetHost();
        final OperatedClientConnection operatedClientConnection = (OperatedClientConnection)this.poolEntry.getConnection();
        // monitorexit(this)
        this.operator.updateSecureConnection(operatedClientConnection, targetHost, httpContext, httpParams);
        // monitorenter(this)
        if (this.poolEntry == null) {
            throw new InterruptedIOException();
        }
        this.poolEntry.getTracker().layerProtocol(operatedClientConnection.isSecure());
    }
    // monitorexit(this)
    
    public Object getState() {
        return this.ensurePoolEntry().getState();
    }
    
    public void setState(final Object state) {
        this.ensurePoolEntry().setState(state);
    }
    
    public void markReusable() {
        this.reusable = true;
    }
    
    public void unmarkReusable() {
        this.reusable = false;
    }
    
    public boolean isMarkedReusable() {
        return this.reusable;
    }
    
    public void setIdleDuration(final long n, final TimeUnit timeUnit) {
        if (n > 0L) {
            this.duration = timeUnit.toMillis(n);
        }
        else {
            this.duration = -1L;
        }
    }
    
    public void releaseConnection() {
        // monitorenter(this)
        if (this.poolEntry == null) {
            // monitorexit(this)
            return;
        }
        this.manager.releaseConnection(this, this.duration, TimeUnit.MILLISECONDS);
        this.poolEntry = null;
    }
    // monitorexit(this)
    
    public void abortConnection() {
        // monitorenter(this)
        if (this.poolEntry == null) {
            // monitorexit(this)
            return;
        }
        this.reusable = false;
        ((OperatedClientConnection)this.poolEntry.getConnection()).shutdown();
        this.manager.releaseConnection(this, this.duration, TimeUnit.MILLISECONDS);
        this.poolEntry = null;
    }
    // monitorexit(this)
}
