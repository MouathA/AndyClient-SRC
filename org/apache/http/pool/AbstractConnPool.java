package org.apache.http.pool;

import org.apache.http.annotation.*;
import java.util.concurrent.locks.*;
import java.io.*;
import org.apache.http.concurrent.*;
import org.apache.http.util.*;
import java.util.concurrent.*;
import java.util.*;

@ThreadSafe
public abstract class AbstractConnPool implements ConnPool, ConnPoolControl
{
    private final Lock lock;
    private final ConnFactory connFactory;
    private final Map routeToPool;
    private final Set leased;
    private final LinkedList available;
    private final LinkedList pending;
    private final Map maxPerRoute;
    private boolean isShutDown;
    private int defaultMaxPerRoute;
    private int maxTotal;
    
    public AbstractConnPool(final ConnFactory connFactory, final int n, final int n2) {
        this.connFactory = (ConnFactory)Args.notNull(connFactory, "Connection factory");
        this.defaultMaxPerRoute = Args.notNegative(n, "Max per route value");
        this.maxTotal = Args.notNegative(n2, "Max total value");
        this.lock = new ReentrantLock();
        this.routeToPool = new HashMap();
        this.leased = new HashSet();
        this.available = new LinkedList();
        this.pending = new LinkedList();
        this.maxPerRoute = new HashMap();
    }
    
    protected abstract PoolEntry createEntry(final Object p0, final Object p1);
    
    protected void onLease(final PoolEntry poolEntry) {
    }
    
    protected void onRelease(final PoolEntry poolEntry) {
    }
    
    public boolean isShutdown() {
        return this.isShutDown;
    }
    
    public void shutdown() throws IOException {
        if (this.isShutDown) {
            return;
        }
        this.isShutDown = true;
        this.lock.lock();
        final Iterator iterator = this.available.iterator();
        while (iterator.hasNext()) {
            iterator.next().close();
        }
        final Iterator<PoolEntry> iterator2 = this.leased.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().close();
        }
        final Iterator<RouteSpecificPool> iterator3 = this.routeToPool.values().iterator();
        while (iterator3.hasNext()) {
            iterator3.next().shutdown();
        }
        this.routeToPool.clear();
        this.leased.clear();
        this.available.clear();
        this.lock.unlock();
    }
    
    private RouteSpecificPool getPool(final Object o) {
        RouteSpecificPool routeSpecificPool = this.routeToPool.get(o);
        if (routeSpecificPool == null) {
            routeSpecificPool = new RouteSpecificPool(o, o) {
                final Object val$route;
                final AbstractConnPool this$0;
                
                @Override
                protected PoolEntry createEntry(final Object o) {
                    return this.this$0.createEntry(this.val$route, o);
                }
            };
            this.routeToPool.put(o, routeSpecificPool);
        }
        return routeSpecificPool;
    }
    
    public Future lease(final Object o, final Object o2, final FutureCallback futureCallback) {
        Args.notNull(o, "Route");
        Asserts.check(!this.isShutDown, "Connection pool shut down");
        return new PoolEntryFuture(this.lock, futureCallback, o, o2) {
            final Object val$route;
            final Object val$state;
            final AbstractConnPool this$0;
            
            public PoolEntry getPoolEntry(final long n, final TimeUnit timeUnit) throws InterruptedException, TimeoutException, IOException {
                final PoolEntry access$000 = AbstractConnPool.access$000(this.this$0, this.val$route, this.val$state, n, timeUnit, this);
                this.this$0.onLease(access$000);
                return access$000;
            }
            
            public Object getPoolEntry(final long n, final TimeUnit timeUnit) throws IOException, InterruptedException, TimeoutException {
                return this.getPoolEntry(n, timeUnit);
            }
        };
    }
    
    public Future lease(final Object o, final Object o2) {
        return this.lease(o, o2, null);
    }
    
    private PoolEntry getPoolEntryBlocking(final Object o, final Object o2, final long n, final TimeUnit timeUnit, final PoolEntryFuture poolEntryFuture) throws IOException, InterruptedException, TimeoutException {
        Date date = null;
        if (n > 0L) {
            date = new Date(System.currentTimeMillis() + timeUnit.toMillis(n));
        }
        this.lock.lock();
        final RouteSpecificPool pool = this.getPool(o);
        PoolEntry free = null;
        while (free == null) {
            Asserts.check(!this.isShutDown, "Connection pool shut down");
            while (true) {
                free = pool.getFree(o2);
                if (free == null) {
                    break;
                }
                if (!free.isClosed() && !free.isExpired(System.currentTimeMillis())) {
                    break;
                }
                free.close();
                this.available.remove(free);
                pool.free(free, false);
            }
            if (free != null) {
                this.available.remove(free);
                this.leased.add(free);
                final PoolEntry poolEntry = free;
                this.lock.unlock();
                return poolEntry;
            }
            final int max = this.getMax(o);
            final int max2 = Math.max(0, pool.getAllocatedCount() + 1 - max);
            if (max2 > 0) {
                while (0 < max2) {
                    final PoolEntry lastUsed = pool.getLastUsed();
                    if (lastUsed == null) {
                        break;
                    }
                    lastUsed.close();
                    this.available.remove(lastUsed);
                    pool.remove(lastUsed);
                    int await = 0;
                    ++await;
                }
            }
            if (pool.getAllocatedCount() < max) {
                this.leased.size();
                final int max3 = Math.max(this.maxTotal - 0, 0);
                if (max3 > 0) {
                    if (this.available.size() > max3 - 1 && !this.available.isEmpty()) {
                        final PoolEntry poolEntry2 = this.available.removeLast();
                        poolEntry2.close();
                        this.getPool(poolEntry2.getRoute()).remove(poolEntry2);
                    }
                    final PoolEntry add = pool.add(this.connFactory.create(o));
                    this.leased.add(add);
                    final PoolEntry poolEntry3 = add;
                    this.lock.unlock();
                    return poolEntry3;
                }
            }
            pool.queue(poolEntryFuture);
            this.pending.add(poolEntryFuture);
            int await = poolEntryFuture.await(date) ? 1 : 0;
            pool.unqueue(poolEntryFuture);
            this.pending.remove(poolEntryFuture);
            if (!false && date != null && date.getTime() <= System.currentTimeMillis()) {
                break;
            }
        }
        throw new TimeoutException("Timeout waiting for connection");
    }
    
    public void release(final PoolEntry poolEntry, final boolean b) {
        this.lock.lock();
        if (this.leased.remove(poolEntry)) {
            final RouteSpecificPool pool = this.getPool(poolEntry.getRoute());
            pool.free(poolEntry, b);
            if (b && !this.isShutDown) {
                this.available.addFirst(poolEntry);
                this.onRelease(poolEntry);
            }
            else {
                poolEntry.close();
            }
            PoolEntryFuture nextPending = pool.nextPending();
            if (nextPending != null) {
                this.pending.remove(nextPending);
            }
            else {
                nextPending = this.pending.poll();
            }
            if (nextPending != null) {
                nextPending.wakeup();
            }
        }
        this.lock.unlock();
    }
    
    private int getMax(final Object o) {
        final Integer n = this.maxPerRoute.get(o);
        if (n != null) {
            return n;
        }
        return this.defaultMaxPerRoute;
    }
    
    public void setMaxTotal(final int maxTotal) {
        Args.notNegative(maxTotal, "Max value");
        this.lock.lock();
        this.maxTotal = maxTotal;
        this.lock.unlock();
    }
    
    public int getMaxTotal() {
        this.lock.lock();
        final int maxTotal = this.maxTotal;
        this.lock.unlock();
        return maxTotal;
    }
    
    public void setDefaultMaxPerRoute(final int defaultMaxPerRoute) {
        Args.notNegative(defaultMaxPerRoute, "Max per route value");
        this.lock.lock();
        this.defaultMaxPerRoute = defaultMaxPerRoute;
        this.lock.unlock();
    }
    
    public int getDefaultMaxPerRoute() {
        this.lock.lock();
        final int defaultMaxPerRoute = this.defaultMaxPerRoute;
        this.lock.unlock();
        return defaultMaxPerRoute;
    }
    
    public void setMaxPerRoute(final Object o, final int n) {
        Args.notNull(o, "Route");
        Args.notNegative(n, "Max per route value");
        this.lock.lock();
        this.maxPerRoute.put(o, n);
        this.lock.unlock();
    }
    
    public int getMaxPerRoute(final Object o) {
        Args.notNull(o, "Route");
        this.lock.lock();
        final int max = this.getMax(o);
        this.lock.unlock();
        return max;
    }
    
    public PoolStats getTotalStats() {
        this.lock.lock();
        final PoolStats poolStats = new PoolStats(this.leased.size(), this.pending.size(), this.available.size(), this.maxTotal);
        this.lock.unlock();
        return poolStats;
    }
    
    public PoolStats getStats(final Object o) {
        Args.notNull(o, "Route");
        this.lock.lock();
        final RouteSpecificPool pool = this.getPool(o);
        final PoolStats poolStats = new PoolStats(pool.getLeasedCount(), pool.getPendingCount(), pool.getAvailableCount(), this.getMax(o));
        this.lock.unlock();
        return poolStats;
    }
    
    protected void enumAvailable(final PoolEntryCallback poolEntryCallback) {
        this.lock.lock();
        final Iterator iterator = this.available.iterator();
        while (iterator.hasNext()) {
            final PoolEntry poolEntry = iterator.next();
            poolEntryCallback.process(poolEntry);
            if (poolEntry.isClosed()) {
                this.getPool(poolEntry.getRoute()).remove(poolEntry);
                iterator.remove();
            }
        }
        this.purgePoolMap();
        this.lock.unlock();
    }
    
    protected void enumLeased(final PoolEntryCallback poolEntryCallback) {
        this.lock.lock();
        final Iterator<PoolEntry> iterator = this.leased.iterator();
        while (iterator.hasNext()) {
            poolEntryCallback.process(iterator.next());
        }
        this.lock.unlock();
    }
    
    private void purgePoolMap() {
        final Iterator<Map.Entry<K, RouteSpecificPool>> iterator = this.routeToPool.entrySet().iterator();
        while (iterator.hasNext()) {
            final RouteSpecificPool routeSpecificPool = iterator.next().getValue();
            if (routeSpecificPool.getPendingCount() + routeSpecificPool.getAllocatedCount() == 0) {
                iterator.remove();
            }
        }
    }
    
    public void closeIdle(final long n, final TimeUnit timeUnit) {
        Args.notNull(timeUnit, "Time unit");
        long millis = timeUnit.toMillis(n);
        if (millis < 0L) {
            millis = 0L;
        }
        this.enumAvailable(new PoolEntryCallback(System.currentTimeMillis() - millis) {
            final long val$deadline;
            final AbstractConnPool this$0;
            
            public void process(final PoolEntry poolEntry) {
                if (poolEntry.getUpdated() <= this.val$deadline) {
                    poolEntry.close();
                }
            }
        });
    }
    
    public void closeExpired() {
        this.enumAvailable(new PoolEntryCallback(System.currentTimeMillis()) {
            final long val$now;
            final AbstractConnPool this$0;
            
            public void process(final PoolEntry poolEntry) {
                if (poolEntry.isExpired(this.val$now)) {
                    poolEntry.close();
                }
            }
        });
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[leased: ");
        sb.append(this.leased);
        sb.append("][available: ");
        sb.append(this.available);
        sb.append("][pending: ");
        sb.append(this.pending);
        sb.append("]");
        return sb.toString();
    }
    
    public void release(final Object o, final boolean b) {
        this.release((PoolEntry)o, b);
    }
    
    static PoolEntry access$000(final AbstractConnPool abstractConnPool, final Object o, final Object o2, final long n, final TimeUnit timeUnit, final PoolEntryFuture poolEntryFuture) throws IOException, InterruptedException, TimeoutException {
        return abstractConnPool.getPoolEntryBlocking(o, o2, n, timeUnit, poolEntryFuture);
    }
}
