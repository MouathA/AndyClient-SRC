package org.apache.http.impl.conn;

import org.apache.http.conn.routing.*;
import org.apache.http.protocol.*;
import org.apache.http.params.*;
import java.io.*;
import org.apache.http.*;
import org.apache.http.conn.*;

@Deprecated
public abstract class AbstractPooledConnAdapter extends AbstractClientConnAdapter
{
    protected AbstractPoolEntry poolEntry;
    
    protected AbstractPooledConnAdapter(final ClientConnectionManager clientConnectionManager, final AbstractPoolEntry poolEntry) {
        super(clientConnectionManager, poolEntry.connection);
        this.poolEntry = poolEntry;
    }
    
    public String getId() {
        return null;
    }
    
    @Deprecated
    protected AbstractPoolEntry getPoolEntry() {
        return this.poolEntry;
    }
    
    protected void assertValid(final AbstractPoolEntry abstractPoolEntry) {
        if (this.isReleased() || abstractPoolEntry == null) {
            throw new ConnectionShutdownException();
        }
    }
    
    @Deprecated
    protected final void assertAttached() {
        if (this.poolEntry == null) {
            throw new ConnectionShutdownException();
        }
    }
    
    @Override
    protected synchronized void detach() {
        this.poolEntry = null;
        super.detach();
    }
    
    public HttpRoute getRoute() {
        final AbstractPoolEntry poolEntry = this.getPoolEntry();
        this.assertValid(poolEntry);
        return (poolEntry.tracker == null) ? null : poolEntry.tracker.toRoute();
    }
    
    public void open(final HttpRoute httpRoute, final HttpContext httpContext, final HttpParams httpParams) throws IOException {
        final AbstractPoolEntry poolEntry = this.getPoolEntry();
        this.assertValid(poolEntry);
        poolEntry.open(httpRoute, httpContext, httpParams);
    }
    
    public void tunnelTarget(final boolean b, final HttpParams httpParams) throws IOException {
        final AbstractPoolEntry poolEntry = this.getPoolEntry();
        this.assertValid(poolEntry);
        poolEntry.tunnelTarget(b, httpParams);
    }
    
    public void tunnelProxy(final HttpHost httpHost, final boolean b, final HttpParams httpParams) throws IOException {
        final AbstractPoolEntry poolEntry = this.getPoolEntry();
        this.assertValid(poolEntry);
        poolEntry.tunnelProxy(httpHost, b, httpParams);
    }
    
    public void layerProtocol(final HttpContext httpContext, final HttpParams httpParams) throws IOException {
        final AbstractPoolEntry poolEntry = this.getPoolEntry();
        this.assertValid(poolEntry);
        poolEntry.layerProtocol(httpContext, httpParams);
    }
    
    public void close() throws IOException {
        final AbstractPoolEntry poolEntry = this.getPoolEntry();
        if (poolEntry != null) {
            poolEntry.shutdownEntry();
        }
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        if (wrappedConnection != null) {
            wrappedConnection.close();
        }
    }
    
    public void shutdown() throws IOException {
        final AbstractPoolEntry poolEntry = this.getPoolEntry();
        if (poolEntry != null) {
            poolEntry.shutdownEntry();
        }
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        if (wrappedConnection != null) {
            wrappedConnection.shutdown();
        }
    }
    
    public Object getState() {
        final AbstractPoolEntry poolEntry = this.getPoolEntry();
        this.assertValid(poolEntry);
        return poolEntry.getState();
    }
    
    public void setState(final Object state) {
        final AbstractPoolEntry poolEntry = this.getPoolEntry();
        this.assertValid(poolEntry);
        poolEntry.setState(state);
    }
}
