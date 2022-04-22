package org.apache.http.impl.execchain;

import org.apache.http.concurrent.*;
import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.conn.*;
import org.apache.http.*;
import java.util.concurrent.*;
import java.io.*;

@ThreadSafe
class ConnectionHolder implements ConnectionReleaseTrigger, Cancellable, Closeable
{
    private final Log log;
    private final HttpClientConnectionManager manager;
    private final HttpClientConnection managedConn;
    private boolean reusable;
    private Object state;
    private long validDuration;
    private TimeUnit tunit;
    private boolean released;
    
    public ConnectionHolder(final Log log, final HttpClientConnectionManager manager, final HttpClientConnection managedConn) {
        this.log = log;
        this.manager = manager;
        this.managedConn = managedConn;
    }
    
    public boolean isReusable() {
        return this.reusable;
    }
    
    public void markReusable() {
        this.reusable = true;
    }
    
    public void markNonReusable() {
        this.reusable = false;
    }
    
    public void setState(final Object state) {
        this.state = state;
    }
    
    public void setValidFor(final long validDuration, final TimeUnit tunit) {
        // monitorenter(managedConn = this.managedConn)
        this.validDuration = validDuration;
        this.tunit = tunit;
    }
    // monitorexit(managedConn)
    
    public void releaseConnection() {
        // monitorenter(managedConn = this.managedConn)
        if (this.released) {
            // monitorexit(managedConn)
            return;
        }
        this.released = true;
        if (this.reusable) {
            this.manager.releaseConnection(this.managedConn, this.state, this.validDuration, this.tunit);
        }
        else {
            this.managedConn.close();
            this.log.debug("Connection discarded");
            this.manager.releaseConnection(this.managedConn, null, 0L, TimeUnit.MILLISECONDS);
        }
    }
    // monitorexit(managedConn)
    
    public void abortConnection() {
        // monitorenter(managedConn = this.managedConn)
        if (this.released) {
            // monitorexit(managedConn)
            return;
        }
        this.released = true;
        this.managedConn.shutdown();
        this.log.debug("Connection discarded");
        this.manager.releaseConnection(this.managedConn, null, 0L, TimeUnit.MILLISECONDS);
    }
    // monitorexit(managedConn)
    
    public boolean cancel() {
        final boolean released = this.released;
        this.log.debug("Cancelling request execution");
        this.abortConnection();
        return !released;
    }
    
    public boolean isReleased() {
        return this.released;
    }
    
    public void close() throws IOException {
        this.abortConnection();
    }
}
