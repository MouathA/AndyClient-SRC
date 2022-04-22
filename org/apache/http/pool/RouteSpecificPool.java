package org.apache.http.pool;

import org.apache.http.annotation.*;
import java.util.*;
import org.apache.http.util.*;

@NotThreadSafe
abstract class RouteSpecificPool
{
    private final Object route;
    private final Set leased;
    private final LinkedList available;
    private final LinkedList pending;
    
    RouteSpecificPool(final Object route) {
        this.route = route;
        this.leased = new HashSet();
        this.available = new LinkedList();
        this.pending = new LinkedList();
    }
    
    protected abstract PoolEntry createEntry(final Object p0);
    
    public final Object getRoute() {
        return this.route;
    }
    
    public int getLeasedCount() {
        return this.leased.size();
    }
    
    public int getPendingCount() {
        return this.pending.size();
    }
    
    public int getAvailableCount() {
        return this.available.size();
    }
    
    public int getAllocatedCount() {
        return this.available.size() + this.leased.size();
    }
    
    public PoolEntry getFree(final Object o) {
        if (!this.available.isEmpty()) {
            if (o != null) {
                final Iterator iterator = this.available.iterator();
                while (iterator.hasNext()) {
                    final PoolEntry poolEntry = iterator.next();
                    if (o.equals(poolEntry.getState())) {
                        iterator.remove();
                        this.leased.add(poolEntry);
                        return poolEntry;
                    }
                }
            }
            final Iterator iterator2 = this.available.iterator();
            while (iterator2.hasNext()) {
                final PoolEntry poolEntry2 = iterator2.next();
                if (poolEntry2.getState() == null) {
                    iterator2.remove();
                    this.leased.add(poolEntry2);
                    return poolEntry2;
                }
            }
        }
        return null;
    }
    
    public PoolEntry getLastUsed() {
        if (!this.available.isEmpty()) {
            return this.available.getLast();
        }
        return null;
    }
    
    public boolean remove(final PoolEntry poolEntry) {
        Args.notNull(poolEntry, "Pool entry");
        return this.available.remove(poolEntry) || this.leased.remove(poolEntry);
    }
    
    public void free(final PoolEntry poolEntry, final boolean b) {
        Args.notNull(poolEntry, "Pool entry");
        Asserts.check(this.leased.remove(poolEntry), "Entry %s has not been leased from this pool", poolEntry);
        if (b) {
            this.available.addFirst(poolEntry);
        }
    }
    
    public PoolEntry add(final Object o) {
        final PoolEntry entry = this.createEntry(o);
        this.leased.add(entry);
        return entry;
    }
    
    public void queue(final PoolEntryFuture poolEntryFuture) {
        if (poolEntryFuture == null) {
            return;
        }
        this.pending.add(poolEntryFuture);
    }
    
    public PoolEntryFuture nextPending() {
        return this.pending.poll();
    }
    
    public void unqueue(final PoolEntryFuture poolEntryFuture) {
        if (poolEntryFuture == null) {
            return;
        }
        this.pending.remove(poolEntryFuture);
    }
    
    public void shutdown() {
        final Iterator iterator = this.pending.iterator();
        while (iterator.hasNext()) {
            iterator.next().cancel(true);
        }
        this.pending.clear();
        final Iterator iterator2 = this.available.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().close();
        }
        this.available.clear();
        final Iterator<PoolEntry> iterator3 = this.leased.iterator();
        while (iterator3.hasNext()) {
            iterator3.next().close();
        }
        this.leased.clear();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[route: ");
        sb.append(this.route);
        sb.append("][leased: ");
        sb.append(this.leased.size());
        sb.append("][available: ");
        sb.append(this.available.size());
        sb.append("][pending: ");
        sb.append(this.pending.size());
        sb.append("]");
        return sb.toString();
    }
}
