package org.apache.http.impl.conn.tsccm;

import java.util.concurrent.*;
import org.apache.commons.logging.*;
import org.apache.http.params.*;
import org.apache.http.conn.params.*;
import org.apache.http.conn.routing.*;
import java.util.concurrent.locks.*;
import org.apache.http.conn.*;
import org.apache.http.util.*;
import java.util.*;

@Deprecated
public class ConnPoolByRoute extends AbstractConnPool
{
    private final Log log;
    private final Lock poolLock;
    protected final ClientConnectionOperator operator;
    protected final ConnPerRoute connPerRoute;
    protected final Set leasedConnections;
    protected final Queue freeConnections;
    protected final Queue waitingThreads;
    protected final Map routeToPool;
    private final long connTTL;
    private final TimeUnit connTTLTimeUnit;
    protected boolean shutdown;
    protected int maxTotalConnections;
    protected int numConnections;
    
    public ConnPoolByRoute(final ClientConnectionOperator clientConnectionOperator, final ConnPerRoute connPerRoute, final int n) {
        this(clientConnectionOperator, connPerRoute, n, -1L, TimeUnit.MILLISECONDS);
    }
    
    public ConnPoolByRoute(final ClientConnectionOperator operator, final ConnPerRoute connPerRoute, final int maxTotalConnections, final long connTTL, final TimeUnit connTTLTimeUnit) {
        this.log = LogFactory.getLog(this.getClass());
        Args.notNull(operator, "Connection operator");
        Args.notNull(connPerRoute, "Connections per route");
        this.poolLock = super.poolLock;
        this.leasedConnections = super.leasedConnections;
        this.operator = operator;
        this.connPerRoute = connPerRoute;
        this.maxTotalConnections = maxTotalConnections;
        this.freeConnections = this.createFreeConnQueue();
        this.waitingThreads = this.createWaitingThreadQueue();
        this.routeToPool = this.createRouteToPoolMap();
        this.connTTL = connTTL;
        this.connTTLTimeUnit = connTTLTimeUnit;
    }
    
    protected Lock getLock() {
        return this.poolLock;
    }
    
    @Deprecated
    public ConnPoolByRoute(final ClientConnectionOperator clientConnectionOperator, final HttpParams httpParams) {
        this(clientConnectionOperator, ConnManagerParams.getMaxConnectionsPerRoute(httpParams), ConnManagerParams.getMaxTotalConnections(httpParams));
    }
    
    protected Queue createFreeConnQueue() {
        return new LinkedList();
    }
    
    protected Queue createWaitingThreadQueue() {
        return new LinkedList();
    }
    
    protected Map createRouteToPoolMap() {
        return new HashMap();
    }
    
    protected RouteSpecificPool newRouteSpecificPool(final HttpRoute httpRoute) {
        return new RouteSpecificPool(httpRoute, this.connPerRoute);
    }
    
    protected WaitingThread newWaitingThread(final Condition condition, final RouteSpecificPool routeSpecificPool) {
        return new WaitingThread(condition, routeSpecificPool);
    }
    
    private void closeConnection(final BasicPoolEntry basicPoolEntry) {
        final OperatedClientConnection connection = basicPoolEntry.getConnection();
        if (connection != null) {
            connection.close();
        }
    }
    
    protected RouteSpecificPool getRoutePool(final HttpRoute httpRoute, final boolean b) {
        this.poolLock.lock();
        RouteSpecificPool routeSpecificPool = this.routeToPool.get(httpRoute);
        if (routeSpecificPool == null && b) {
            routeSpecificPool = this.newRouteSpecificPool(httpRoute);
            this.routeToPool.put(httpRoute, routeSpecificPool);
        }
        this.poolLock.unlock();
        return routeSpecificPool;
    }
    
    public int getConnectionsInPool(final HttpRoute httpRoute) {
        this.poolLock.lock();
        final RouteSpecificPool routePool = this.getRoutePool(httpRoute, false);
        final int n = (routePool != null) ? routePool.getEntryCount() : 0;
        this.poolLock.unlock();
        return n;
    }
    
    public int getConnectionsInPool() {
        this.poolLock.lock();
        final int numConnections = this.numConnections;
        this.poolLock.unlock();
        return numConnections;
    }
    
    @Override
    public PoolEntryRequest requestPoolEntry(final HttpRoute httpRoute, final Object o) {
        return new PoolEntryRequest(new WaitingThreadAborter(), httpRoute, o) {
            final WaitingThreadAborter val$aborter;
            final HttpRoute val$route;
            final Object val$state;
            final ConnPoolByRoute this$0;
            
            public void abortRequest() {
                ConnPoolByRoute.access$000(this.this$0).lock();
                this.val$aborter.abort();
                ConnPoolByRoute.access$000(this.this$0).unlock();
            }
            
            public BasicPoolEntry getPoolEntry(final long n, final TimeUnit timeUnit) throws InterruptedException, ConnectionPoolTimeoutException {
                return this.this$0.getEntryBlocking(this.val$route, this.val$state, n, timeUnit, this.val$aborter);
            }
        };
    }
    
    protected BasicPoolEntry getEntryBlocking(final HttpRoute httpRoute, final Object o, final long n, final TimeUnit timeUnit, final WaitingThreadAborter waitingThreadAborter) throws ConnectionPoolTimeoutException, InterruptedException {
        Date date = null;
        if (n > 0L) {
            date = new Date(System.currentTimeMillis() + timeUnit.toMillis(n));
        }
        BasicPoolEntry basicPoolEntry = null;
        this.poolLock.lock();
        RouteSpecificPool routeSpecificPool = this.getRoutePool(httpRoute, true);
        WaitingThread waitingThread = null;
        while (basicPoolEntry == null) {
            Asserts.check(!this.shutdown, "Connection pool shut down");
            if (this.log.isDebugEnabled()) {
                this.log.debug("[" + httpRoute + "] total kept alive: " + this.freeConnections.size() + ", total issued: " + this.leasedConnections.size() + ", total allocated: " + this.numConnections + " out of " + this.maxTotalConnections);
            }
            basicPoolEntry = this.getFreeEntry(routeSpecificPool, o);
            if (basicPoolEntry != null) {
                break;
            }
            final boolean b = routeSpecificPool.getCapacity() > 0;
            if (this.log.isDebugEnabled()) {
                this.log.debug("Available capacity: " + routeSpecificPool.getCapacity() + " out of " + routeSpecificPool.getMaxEntries() + " [" + httpRoute + "][" + o + "]");
            }
            if (b && this.numConnections < this.maxTotalConnections) {
                basicPoolEntry = this.createEntry(routeSpecificPool, this.operator);
            }
            else if (b && !this.freeConnections.isEmpty()) {
                this.deleteLeastUsedEntry();
                routeSpecificPool = this.getRoutePool(httpRoute, true);
                basicPoolEntry = this.createEntry(routeSpecificPool, this.operator);
            }
            else {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Need to wait for connection [" + httpRoute + "][" + o + "]");
                }
                if (waitingThread == null) {
                    waitingThread = this.newWaitingThread(this.poolLock.newCondition(), routeSpecificPool);
                    waitingThreadAborter.setWaitingThread(waitingThread);
                }
                routeSpecificPool.queueThread(waitingThread);
                this.waitingThreads.add(waitingThread);
                waitingThread.await(date);
                routeSpecificPool.removeThread(waitingThread);
                this.waitingThreads.remove(waitingThread);
                if (!false && date != null && date.getTime() <= System.currentTimeMillis()) {
                    throw new ConnectionPoolTimeoutException("Timeout waiting for connection from pool");
                }
                continue;
            }
        }
        this.poolLock.unlock();
        return basicPoolEntry;
    }
    
    @Override
    public void freeEntry(final BasicPoolEntry basicPoolEntry, final boolean b, final long n, final TimeUnit timeUnit) {
        final HttpRoute plannedRoute = basicPoolEntry.getPlannedRoute();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Releasing connection [" + plannedRoute + "][" + basicPoolEntry.getState() + "]");
        }
        this.poolLock.lock();
        if (this.shutdown) {
            this.closeConnection(basicPoolEntry);
            this.poolLock.unlock();
            return;
        }
        this.leasedConnections.remove(basicPoolEntry);
        final RouteSpecificPool routePool = this.getRoutePool(plannedRoute, true);
        if (b && routePool.getCapacity() >= 0) {
            if (this.log.isDebugEnabled()) {
                String string;
                if (n > 0L) {
                    string = "for " + n + " " + timeUnit;
                }
                else {
                    string = "indefinitely";
                }
                this.log.debug("Pooling connection [" + plannedRoute + "][" + basicPoolEntry.getState() + "]; keep alive " + string);
            }
            routePool.freeEntry(basicPoolEntry);
            basicPoolEntry.updateExpiry(n, timeUnit);
            this.freeConnections.add(basicPoolEntry);
        }
        else {
            this.closeConnection(basicPoolEntry);
            routePool.dropEntry();
            --this.numConnections;
        }
        this.notifyWaitingThread(routePool);
        this.poolLock.unlock();
    }
    
    protected BasicPoolEntry getFreeEntry(final RouteSpecificPool routeSpecificPool, final Object o) {
        BasicPoolEntry allocEntry = null;
        this.poolLock.lock();
        while (!true) {
            allocEntry = routeSpecificPool.allocEntry(o);
            if (allocEntry != null) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Getting free connection [" + routeSpecificPool.getRoute() + "][" + o + "]");
                }
                this.freeConnections.remove(allocEntry);
                if (allocEntry.isExpired(System.currentTimeMillis())) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Closing expired free connection [" + routeSpecificPool.getRoute() + "][" + o + "]");
                    }
                    this.closeConnection(allocEntry);
                    routeSpecificPool.dropEntry();
                    --this.numConnections;
                }
                else {
                    this.leasedConnections.add(allocEntry);
                }
            }
            else {
                if (!this.log.isDebugEnabled()) {
                    continue;
                }
                this.log.debug("No free connections [" + routeSpecificPool.getRoute() + "][" + o + "]");
            }
        }
        this.poolLock.unlock();
        return allocEntry;
    }
    
    protected BasicPoolEntry createEntry(final RouteSpecificPool routeSpecificPool, final ClientConnectionOperator clientConnectionOperator) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Creating new connection [" + routeSpecificPool.getRoute() + "]");
        }
        final BasicPoolEntry basicPoolEntry = new BasicPoolEntry(clientConnectionOperator, routeSpecificPool.getRoute(), this.connTTL, this.connTTLTimeUnit);
        this.poolLock.lock();
        routeSpecificPool.createdEntry(basicPoolEntry);
        ++this.numConnections;
        this.leasedConnections.add(basicPoolEntry);
        this.poolLock.unlock();
        return basicPoolEntry;
    }
    
    protected void deleteEntry(final BasicPoolEntry basicPoolEntry) {
        final HttpRoute plannedRoute = basicPoolEntry.getPlannedRoute();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Deleting connection [" + plannedRoute + "][" + basicPoolEntry.getState() + "]");
        }
        this.poolLock.lock();
        this.closeConnection(basicPoolEntry);
        final RouteSpecificPool routePool = this.getRoutePool(plannedRoute, true);
        routePool.deleteEntry(basicPoolEntry);
        --this.numConnections;
        if (routePool.isUnused()) {
            this.routeToPool.remove(plannedRoute);
        }
        this.poolLock.unlock();
    }
    
    protected void deleteLeastUsedEntry() {
        this.poolLock.lock();
        final BasicPoolEntry basicPoolEntry = this.freeConnections.remove();
        if (basicPoolEntry != null) {
            this.deleteEntry(basicPoolEntry);
        }
        else if (this.log.isDebugEnabled()) {
            this.log.debug("No free connection to delete");
        }
        this.poolLock.unlock();
    }
    
    @Override
    protected void handleLostEntry(final HttpRoute httpRoute) {
        this.poolLock.lock();
        final RouteSpecificPool routePool = this.getRoutePool(httpRoute, true);
        routePool.dropEntry();
        if (routePool.isUnused()) {
            this.routeToPool.remove(httpRoute);
        }
        --this.numConnections;
        this.notifyWaitingThread(routePool);
        this.poolLock.unlock();
    }
    
    protected void notifyWaitingThread(final RouteSpecificPool routeSpecificPool) {
        WaitingThread nextThread = null;
        this.poolLock.lock();
        if (routeSpecificPool != null && routeSpecificPool.hasThread()) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Notifying thread waiting on pool [" + routeSpecificPool.getRoute() + "]");
            }
            nextThread = routeSpecificPool.nextThread();
        }
        else if (!this.waitingThreads.isEmpty()) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Notifying thread waiting on any pool");
            }
            nextThread = this.waitingThreads.remove();
        }
        else if (this.log.isDebugEnabled()) {
            this.log.debug("Notifying no-one, there are no waiting threads");
        }
        if (nextThread != null) {
            nextThread.wakeup();
        }
        this.poolLock.unlock();
    }
    
    @Override
    public void deleteClosedConnections() {
        this.poolLock.lock();
        final Iterator iterator = this.freeConnections.iterator();
        while (iterator.hasNext()) {
            final BasicPoolEntry basicPoolEntry = iterator.next();
            if (!basicPoolEntry.getConnection().isOpen()) {
                iterator.remove();
                this.deleteEntry(basicPoolEntry);
            }
        }
        this.poolLock.unlock();
    }
    
    @Override
    public void closeIdleConnections(final long n, final TimeUnit timeUnit) {
        Args.notNull(timeUnit, "Time unit");
        final long n2 = (n > 0L) ? n : 0L;
        if (this.log.isDebugEnabled()) {
            this.log.debug("Closing connections idle longer than " + n2 + " " + timeUnit);
        }
        final long n3 = System.currentTimeMillis() - timeUnit.toMillis(n2);
        this.poolLock.lock();
        final Iterator iterator = this.freeConnections.iterator();
        while (iterator.hasNext()) {
            final BasicPoolEntry basicPoolEntry = iterator.next();
            if (basicPoolEntry.getUpdated() <= n3) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Closing connection last used @ " + new Date(basicPoolEntry.getUpdated()));
                }
                iterator.remove();
                this.deleteEntry(basicPoolEntry);
            }
        }
        this.poolLock.unlock();
    }
    
    @Override
    public void closeExpiredConnections() {
        this.log.debug("Closing expired connections");
        final long currentTimeMillis = System.currentTimeMillis();
        this.poolLock.lock();
        final Iterator iterator = this.freeConnections.iterator();
        while (iterator.hasNext()) {
            final BasicPoolEntry basicPoolEntry = iterator.next();
            if (basicPoolEntry.isExpired(currentTimeMillis)) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Closing connection expired @ " + new Date(basicPoolEntry.getExpiry()));
                }
                iterator.remove();
                this.deleteEntry(basicPoolEntry);
            }
        }
        this.poolLock.unlock();
    }
    
    @Override
    public void shutdown() {
        this.poolLock.lock();
        if (this.shutdown) {
            this.poolLock.unlock();
            return;
        }
        this.shutdown = true;
        final Iterator iterator = this.leasedConnections.iterator();
        while (iterator.hasNext()) {
            final BasicPoolEntry basicPoolEntry = iterator.next();
            iterator.remove();
            this.closeConnection(basicPoolEntry);
        }
        final Iterator iterator2 = this.freeConnections.iterator();
        while (iterator2.hasNext()) {
            final BasicPoolEntry basicPoolEntry2 = iterator2.next();
            iterator2.remove();
            if (this.log.isDebugEnabled()) {
                this.log.debug("Closing connection [" + basicPoolEntry2.getPlannedRoute() + "][" + basicPoolEntry2.getState() + "]");
            }
            this.closeConnection(basicPoolEntry2);
        }
        final Iterator iterator3 = this.waitingThreads.iterator();
        while (iterator3.hasNext()) {
            final WaitingThread waitingThread = iterator3.next();
            iterator3.remove();
            waitingThread.wakeup();
        }
        this.routeToPool.clear();
        this.poolLock.unlock();
    }
    
    public void setMaxTotalConnections(final int maxTotalConnections) {
        this.poolLock.lock();
        this.maxTotalConnections = maxTotalConnections;
        this.poolLock.unlock();
    }
    
    public int getMaxTotalConnections() {
        return this.maxTotalConnections;
    }
    
    static Lock access$000(final ConnPoolByRoute connPoolByRoute) {
        return connPoolByRoute.poolLock;
    }
}
