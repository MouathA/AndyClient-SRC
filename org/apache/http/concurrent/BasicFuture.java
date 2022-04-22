package org.apache.http.concurrent;

import org.apache.http.util.*;
import java.util.concurrent.*;

public class BasicFuture implements Future, Cancellable
{
    private final FutureCallback callback;
    private boolean completed;
    private boolean cancelled;
    private Object result;
    private Exception ex;
    
    public BasicFuture(final FutureCallback callback) {
        this.callback = callback;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public boolean isDone() {
        return this.completed;
    }
    
    private Object getResult() throws ExecutionException {
        if (this.ex != null) {
            throw new ExecutionException(this.ex);
        }
        return this.result;
    }
    
    public synchronized Object get() throws InterruptedException, ExecutionException {
        while (!this.completed) {
            this.wait();
        }
        return this.getResult();
    }
    
    public synchronized Object get(final long n, final TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        Args.notNull(timeUnit, "Time unit");
        final long millis = timeUnit.toMillis(n);
        final long n2 = (millis <= 0L) ? 0L : System.currentTimeMillis();
        long n3 = millis;
        if (this.completed) {
            return this.getResult();
        }
        if (n3 <= 0L) {
            throw new TimeoutException();
        }
        do {
            this.wait(n3);
            if (this.completed) {
                return this.getResult();
            }
            n3 = millis - (System.currentTimeMillis() - n2);
        } while (n3 > 0L);
        throw new TimeoutException();
    }
    
    public boolean completed(final Object result) {
        // monitorenter(this)
        if (this.completed) {
            // monitorexit(this)
            return false;
        }
        this.completed = true;
        this.result = result;
        this.notifyAll();
        // monitorexit(this)
        if (this.callback != null) {
            this.callback.completed(result);
        }
        return true;
    }
    
    public boolean failed(final Exception ex) {
        // monitorenter(this)
        if (this.completed) {
            // monitorexit(this)
            return false;
        }
        this.completed = true;
        this.ex = ex;
        this.notifyAll();
        // monitorexit(this)
        if (this.callback != null) {
            this.callback.failed(ex);
        }
        return true;
    }
    
    public boolean cancel(final boolean b) {
        // monitorenter(this)
        if (this.completed) {
            // monitorexit(this)
            return false;
        }
        this.completed = true;
        this.cancelled = true;
        this.notifyAll();
        // monitorexit(this)
        if (this.callback != null) {
            this.callback.cancelled();
        }
        return true;
    }
    
    public boolean cancel() {
        return this.cancel(true);
    }
}
