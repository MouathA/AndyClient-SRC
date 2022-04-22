package org.apache.http.impl.conn.tsccm;

import org.apache.http.conn.routing.*;
import org.apache.http.conn.params.*;
import org.apache.commons.logging.*;
import java.util.*;
import org.apache.http.util.*;

@Deprecated
public class RouteSpecificPool
{
    private final Log log;
    protected final HttpRoute route;
    protected final int maxEntries;
    protected final ConnPerRoute connPerRoute;
    protected final LinkedList freeEntries;
    protected final Queue waitingThreads;
    protected int numEntries;
    
    @Deprecated
    public RouteSpecificPool(final HttpRoute route, final int maxEntries) {
        this.log = LogFactory.getLog(this.getClass());
        this.route = route;
        this.maxEntries = maxEntries;
        this.connPerRoute = new ConnPerRoute() {
            final RouteSpecificPool this$0;
            
            public int getMaxForRoute(final HttpRoute httpRoute) {
                return this.this$0.maxEntries;
            }
        };
        this.freeEntries = new LinkedList();
        this.waitingThreads = new LinkedList();
        this.numEntries = 0;
    }
    
    public RouteSpecificPool(final HttpRoute route, final ConnPerRoute connPerRoute) {
        this.log = LogFactory.getLog(this.getClass());
        this.route = route;
        this.connPerRoute = connPerRoute;
        this.maxEntries = connPerRoute.getMaxForRoute(route);
        this.freeEntries = new LinkedList();
        this.waitingThreads = new LinkedList();
        this.numEntries = 0;
    }
    
    public final HttpRoute getRoute() {
        return this.route;
    }
    
    public final int getMaxEntries() {
        return this.maxEntries;
    }
    
    public boolean isUnused() {
        return this.numEntries < 1 && this.waitingThreads.isEmpty();
    }
    
    public int getCapacity() {
        return this.connPerRoute.getMaxForRoute(this.route) - this.numEntries;
    }
    
    public final int getEntryCount() {
        return this.numEntries;
    }
    
    public BasicPoolEntry allocEntry(final Object o) {
        if (!this.freeEntries.isEmpty()) {
            final ListIterator<BasicPoolEntry> listIterator = (ListIterator<BasicPoolEntry>)this.freeEntries.listIterator(this.freeEntries.size());
            while (listIterator.hasPrevious()) {
                final BasicPoolEntry basicPoolEntry = listIterator.previous();
                if (basicPoolEntry.getState() == null || LangUtils.equals(o, basicPoolEntry.getState())) {
                    listIterator.remove();
                    return basicPoolEntry;
                }
            }
        }
        if (this.getCapacity() == 0 && !this.freeEntries.isEmpty()) {
            final BasicPoolEntry basicPoolEntry2 = this.freeEntries.remove();
            basicPoolEntry2.shutdownEntry();
            basicPoolEntry2.getConnection().close();
            return basicPoolEntry2;
        }
        return null;
    }
    
    public void freeEntry(final BasicPoolEntry basicPoolEntry) {
        if (this.numEntries < 1) {
            throw new IllegalStateException("No entry created for this pool. " + this.route);
        }
        if (this.numEntries <= this.freeEntries.size()) {
            throw new IllegalStateException("No entry allocated from this pool. " + this.route);
        }
        this.freeEntries.add(basicPoolEntry);
    }
    
    public void createdEntry(final BasicPoolEntry basicPoolEntry) {
        Args.check(this.route.equals(basicPoolEntry.getPlannedRoute()), "Entry not planned for this pool");
        ++this.numEntries;
    }
    
    public boolean deleteEntry(final BasicPoolEntry basicPoolEntry) {
        final boolean remove = this.freeEntries.remove(basicPoolEntry);
        if (remove) {
            --this.numEntries;
        }
        return remove;
    }
    
    public void dropEntry() {
        Asserts.check(this.numEntries > 0, "There is no entry that could be dropped");
        --this.numEntries;
    }
    
    public void queueThread(final WaitingThread waitingThread) {
        Args.notNull(waitingThread, "Waiting thread");
        this.waitingThreads.add(waitingThread);
    }
    
    public boolean hasThread() {
        return !this.waitingThreads.isEmpty();
    }
    
    public WaitingThread nextThread() {
        return this.waitingThreads.peek();
    }
    
    public void removeThread(final WaitingThread waitingThread) {
        if (waitingThread == null) {
            return;
        }
        this.waitingThreads.remove(waitingThread);
    }
}
