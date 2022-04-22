package org.apache.http.impl.conn.tsccm;

import java.util.concurrent.locks.*;
import org.apache.http.util.*;
import java.util.*;

@Deprecated
public class WaitingThread
{
    private final Condition cond;
    private final RouteSpecificPool pool;
    private Thread waiter;
    private boolean aborted;
    
    public WaitingThread(final Condition cond, final RouteSpecificPool pool) {
        Args.notNull(cond, "Condition");
        this.cond = cond;
        this.pool = pool;
    }
    
    public final Condition getCondition() {
        return this.cond;
    }
    
    public final RouteSpecificPool getPool() {
        return this.pool;
    }
    
    public final Thread getThread() {
        return this.waiter;
    }
    
    public boolean await(final Date date) throws InterruptedException {
        if (this.waiter != null) {
            throw new IllegalStateException("A thread is already waiting on this object.\ncaller: " + Thread.currentThread() + "\nwaiter: " + this.waiter);
        }
        if (this.aborted) {
            throw new InterruptedException("Operation interrupted");
        }
        this.waiter = Thread.currentThread();
        if (date != null) {
            this.cond.awaitUntil(date);
        }
        else {
            this.cond.await();
        }
        if (this.aborted) {
            throw new InterruptedException("Operation interrupted");
        }
        this.waiter = null;
        return true;
    }
    
    public void wakeup() {
        if (this.waiter == null) {
            throw new IllegalStateException("Nobody waiting on this object.");
        }
        this.cond.signalAll();
    }
    
    public void interrupt() {
        this.aborted = true;
        this.cond.signalAll();
    }
}
