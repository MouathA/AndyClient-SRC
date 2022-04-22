package org.apache.http.pool;

import org.apache.http.annotation.*;
import org.apache.http.concurrent.*;
import java.util.concurrent.locks.*;
import org.apache.http.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.util.*;

@ThreadSafe
abstract class PoolEntryFuture implements Future
{
    private final Lock lock;
    private final FutureCallback callback;
    private final Condition condition;
    private boolean cancelled;
    private boolean completed;
    private Object result;
    
    PoolEntryFuture(final Lock lock, final FutureCallback callback) {
        this.lock = lock;
        this.condition = lock.newCondition();
        this.callback = callback;
    }
    
    public boolean cancel(final boolean b) {
        this.lock.lock();
        if (this.completed) {
            this.lock.unlock();
            return true;
        }
        this.completed = true;
        this.cancelled = true;
        if (this.callback != null) {
            this.callback.cancelled();
        }
        this.condition.signalAll();
        this.lock.unlock();
        return true;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public boolean isDone() {
        return this.completed;
    }
    
    public Object get() throws InterruptedException, ExecutionException {
        return this.get(0L, TimeUnit.MILLISECONDS);
    }
    
    public Object get(final long n, final TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        Args.notNull(timeUnit, "Time unit");
        this.lock.lock();
        if (this.completed) {
            final Object result = this.result;
            this.lock.unlock();
            return result;
        }
        this.result = this.getPoolEntry(n, timeUnit);
        this.completed = true;
        if (this.callback != null) {
            this.callback.completed(this.result);
        }
        final Object result2 = this.result;
        this.lock.unlock();
        return result2;
    }
    
    protected abstract Object getPoolEntry(final long p0, final TimeUnit p1) throws IOException, InterruptedException, TimeoutException;
    
    public boolean await(final Date date) throws InterruptedException {
        this.lock.lock();
        if (this.cancelled) {
            throw new InterruptedException("Operation interrupted");
        }
        if (date != null) {
            this.condition.awaitUntil(date);
        }
        else {
            this.condition.await();
        }
        if (this.cancelled) {
            throw new InterruptedException("Operation interrupted");
        }
        this.lock.unlock();
        return true;
    }
    
    public void wakeup() {
        this.lock.lock();
        this.condition.signalAll();
        this.lock.unlock();
    }
}
