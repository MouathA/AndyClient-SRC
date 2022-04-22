package org.apache.http.impl.conn;

import org.apache.http.pool.*;
import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.conn.routing.*;
import org.apache.http.conn.*;
import java.util.concurrent.*;
import org.apache.http.*;
import java.io.*;
import java.util.*;

@ThreadSafe
class CPoolEntry extends PoolEntry
{
    private final Log log;
    private boolean routeComplete;
    
    public CPoolEntry(final Log log, final String s, final HttpRoute httpRoute, final ManagedHttpClientConnection managedHttpClientConnection, final long n, final TimeUnit timeUnit) {
        super(s, httpRoute, managedHttpClientConnection, n, timeUnit);
        this.log = log;
    }
    
    public void markRouteComplete() {
        this.routeComplete = true;
    }
    
    public boolean isRouteComplete() {
        return this.routeComplete;
    }
    
    public void closeConnection() throws IOException {
        ((HttpClientConnection)this.getConnection()).close();
    }
    
    public void shutdownConnection() throws IOException {
        ((HttpClientConnection)this.getConnection()).shutdown();
    }
    
    @Override
    public boolean isExpired(final long n) {
        final boolean expired = super.isExpired(n);
        if (expired && this.log.isDebugEnabled()) {
            this.log.debug("Connection " + this + " expired @ " + new Date(this.getExpiry()));
        }
        return expired;
    }
    
    @Override
    public boolean isClosed() {
        return !((HttpClientConnection)this.getConnection()).isOpen();
    }
    
    @Override
    public void close() {
        this.closeConnection();
    }
}
