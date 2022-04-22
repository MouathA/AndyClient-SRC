package org.apache.http.impl.conn.tsccm;

import org.apache.http.annotation.*;
import org.apache.http.impl.conn.*;
import org.apache.commons.logging.*;
import java.util.concurrent.locks.*;
import org.apache.http.conn.routing.*;
import java.util.concurrent.*;
import java.lang.ref.*;
import org.apache.http.util.*;
import java.util.*;
import org.apache.http.conn.*;

@Deprecated
public abstract class AbstractConnPool
{
    private final Log log;
    protected final Lock poolLock;
    @GuardedBy("poolLock")
    protected Set leasedConnections;
    @GuardedBy("poolLock")
    protected int numConnections;
    protected boolean isShutDown;
    protected Set issuedConnections;
    protected ReferenceQueue refQueue;
    protected IdleConnectionHandler idleConnHandler;
    
    protected AbstractConnPool() {
        this.log = LogFactory.getLog(this.getClass());
        this.leasedConnections = new HashSet();
        this.idleConnHandler = new IdleConnectionHandler();
        this.poolLock = new ReentrantLock();
    }
    
    public void enableConnectionGC() throws IllegalStateException {
    }
    
    public final BasicPoolEntry getEntry(final HttpRoute httpRoute, final Object o, final long n, final TimeUnit timeUnit) throws ConnectionPoolTimeoutException, InterruptedException {
        return this.requestPoolEntry(httpRoute, o).getPoolEntry(n, timeUnit);
    }
    
    public abstract PoolEntryRequest requestPoolEntry(final HttpRoute p0, final Object p1);
    
    public abstract void freeEntry(final BasicPoolEntry p0, final boolean p1, final long p2, final TimeUnit p3);
    
    public void handleReference(final Reference reference) {
    }
    
    protected abstract void handleLostEntry(final HttpRoute p0);
    
    public void closeIdleConnections(final long n, final TimeUnit timeUnit) {
        Args.notNull(timeUnit, "Time unit");
        this.poolLock.lock();
        this.idleConnHandler.closeIdleConnections(timeUnit.toMillis(n));
        this.poolLock.unlock();
    }
    
    public void closeExpiredConnections() {
        this.poolLock.lock();
        this.idleConnHandler.closeExpiredConnections();
        this.poolLock.unlock();
    }
    
    public abstract void deleteClosedConnections();
    
    public void shutdown() {
        this.poolLock.lock();
        if (this.isShutDown) {
            this.poolLock.unlock();
            return;
        }
        final Iterator<BasicPoolEntry> iterator = (Iterator<BasicPoolEntry>)this.leasedConnections.iterator();
        while (iterator.hasNext()) {
            final BasicPoolEntry basicPoolEntry = iterator.next();
            iterator.remove();
            this.closeConnection(basicPoolEntry.getConnection());
        }
        this.idleConnHandler.removeAll();
        this.isShutDown = true;
        this.poolLock.unlock();
    }
    
    protected void closeConnection(final OperatedClientConnection operatedClientConnection) {
        if (operatedClientConnection != null) {
            operatedClientConnection.close();
        }
    }
}
