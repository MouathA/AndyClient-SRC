package org.apache.http.impl.conn;

import org.apache.http.conn.*;
import org.apache.http.conn.routing.*;
import org.apache.http.protocol.*;
import org.apache.http.params.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.io.*;
import java.net.*;

@Deprecated
public abstract class AbstractPoolEntry
{
    protected final ClientConnectionOperator connOperator;
    protected final OperatedClientConnection connection;
    protected HttpRoute route;
    protected Object state;
    protected RouteTracker tracker;
    
    protected AbstractPoolEntry(final ClientConnectionOperator connOperator, final HttpRoute route) {
        Args.notNull(connOperator, "Connection operator");
        this.connOperator = connOperator;
        this.connection = connOperator.createConnection();
        this.route = route;
        this.tracker = null;
    }
    
    public Object getState() {
        return this.state;
    }
    
    public void setState(final Object state) {
        this.state = state;
    }
    
    public void open(final HttpRoute httpRoute, final HttpContext httpContext, final HttpParams httpParams) throws IOException {
        Args.notNull(httpRoute, "Route");
        Args.notNull(httpParams, "HTTP parameters");
        if (this.tracker != null) {
            Asserts.check(!this.tracker.isConnected(), "Connection already open");
        }
        this.tracker = new RouteTracker(httpRoute);
        final HttpHost proxyHost = httpRoute.getProxyHost();
        this.connOperator.openConnection(this.connection, (proxyHost != null) ? proxyHost : httpRoute.getTargetHost(), httpRoute.getLocalAddress(), httpContext, httpParams);
        final RouteTracker tracker = this.tracker;
        if (tracker == null) {
            throw new InterruptedIOException("Request aborted");
        }
        if (proxyHost == null) {
            tracker.connectTarget(this.connection.isSecure());
        }
        else {
            tracker.connectProxy(proxyHost, this.connection.isSecure());
        }
    }
    
    public void tunnelTarget(final boolean b, final HttpParams httpParams) throws IOException {
        Args.notNull(httpParams, "HTTP parameters");
        Asserts.notNull(this.tracker, "Route tracker");
        Asserts.check(this.tracker.isConnected(), "Connection not open");
        Asserts.check(!this.tracker.isTunnelled(), "Connection is already tunnelled");
        this.connection.update(null, this.tracker.getTargetHost(), b, httpParams);
        this.tracker.tunnelTarget(b);
    }
    
    public void tunnelProxy(final HttpHost httpHost, final boolean b, final HttpParams httpParams) throws IOException {
        Args.notNull(httpHost, "Next proxy");
        Args.notNull(httpParams, "Parameters");
        Asserts.notNull(this.tracker, "Route tracker");
        Asserts.check(this.tracker.isConnected(), "Connection not open");
        this.connection.update(null, httpHost, b, httpParams);
        this.tracker.tunnelProxy(httpHost, b);
    }
    
    public void layerProtocol(final HttpContext httpContext, final HttpParams httpParams) throws IOException {
        Args.notNull(httpParams, "HTTP parameters");
        Asserts.notNull(this.tracker, "Route tracker");
        Asserts.check(this.tracker.isConnected(), "Connection not open");
        Asserts.check(this.tracker.isTunnelled(), "Protocol layering without a tunnel not supported");
        Asserts.check(!this.tracker.isLayered(), "Multiple protocol layering not supported");
        this.connOperator.updateSecureConnection(this.connection, this.tracker.getTargetHost(), httpContext, httpParams);
        this.tracker.layerProtocol(this.connection.isSecure());
    }
    
    protected void shutdownEntry() {
        this.tracker = null;
        this.state = null;
    }
}
