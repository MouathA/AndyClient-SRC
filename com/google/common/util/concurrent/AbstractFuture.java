package com.google.common.util.concurrent;

import javax.annotation.*;
import com.google.common.base.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public abstract class AbstractFuture implements ListenableFuture
{
    private final Sync sync;
    private final ExecutionList executionList;
    
    protected AbstractFuture() {
        this.sync = new Sync();
        this.executionList = new ExecutionList();
    }
    
    @Override
    public Object get(final long n, final TimeUnit timeUnit) throws InterruptedException, TimeoutException, ExecutionException {
        return this.sync.get(timeUnit.toNanos(n));
    }
    
    @Override
    public Object get() throws InterruptedException, ExecutionException {
        return this.sync.get();
    }
    
    @Override
    public boolean isDone() {
        return this.sync.isDone();
    }
    
    @Override
    public boolean isCancelled() {
        return this.sync.isCancelled();
    }
    
    @Override
    public boolean cancel(final boolean b) {
        if (!this.sync.cancel(b)) {
            return false;
        }
        this.executionList.execute();
        if (b) {
            this.interruptTask();
        }
        return true;
    }
    
    protected void interruptTask() {
    }
    
    protected final boolean wasInterrupted() {
        return this.sync.wasInterrupted();
    }
    
    @Override
    public void addListener(final Runnable runnable, final Executor executor) {
        this.executionList.add(runnable, executor);
    }
    
    protected boolean set(@Nullable final Object o) {
        final boolean set = this.sync.set(o);
        if (set) {
            this.executionList.execute();
        }
        return set;
    }
    
    protected boolean setException(final Throwable t) {
        final boolean setException = this.sync.setException((Throwable)Preconditions.checkNotNull(t));
        if (setException) {
            this.executionList.execute();
        }
        return setException;
    }
    
    static final CancellationException cancellationExceptionWithCause(@Nullable final String s, @Nullable final Throwable t) {
        final CancellationException ex = new CancellationException(s);
        ex.initCause(t);
        return ex;
    }
    
    static final class Sync extends AbstractQueuedSynchronizer
    {
        private static final long serialVersionUID = 0L;
        static final int RUNNING = 0;
        static final int COMPLETING = 1;
        static final int COMPLETED = 2;
        static final int CANCELLED = 4;
        static final int INTERRUPTED = 8;
        private Object value;
        private Throwable exception;
        
        @Override
        protected int tryAcquireShared(final int n) {
            if (this.isDone()) {
                return 1;
            }
            return -1;
        }
        
        @Override
        protected boolean tryReleaseShared(final int state) {
            this.setState(state);
            return true;
        }
        
        Object get(final long n) throws TimeoutException, CancellationException, ExecutionException, InterruptedException {
            if (!this.tryAcquireSharedNanos(-1, n)) {
                throw new TimeoutException("Timeout waiting for task.");
            }
            return this.getValue();
        }
        
        Object get() throws CancellationException, ExecutionException, InterruptedException {
            this.acquireSharedInterruptibly(-1);
            return this.getValue();
        }
        
        private Object getValue() throws CancellationException, ExecutionException {
            final int state = this.getState();
            switch (state) {
                case 2: {
                    if (this.exception != null) {
                        throw new ExecutionException(this.exception);
                    }
                    return this.value;
                }
                case 4:
                case 8: {
                    throw AbstractFuture.cancellationExceptionWithCause("Task was cancelled.", this.exception);
                }
                default: {
                    throw new IllegalStateException("Error, synchronizer in invalid state: " + state);
                }
            }
        }
        
        boolean isDone() {
            return (this.getState() & 0xE) != 0x0;
        }
        
        boolean isCancelled() {
            return (this.getState() & 0xC) != 0x0;
        }
        
        boolean wasInterrupted() {
            return this.getState() == 8;
        }
        
        boolean set(@Nullable final Object o) {
            return this.complete(o, null, 2);
        }
        
        boolean setException(final Throwable t) {
            return this.complete(null, t, 2);
        }
        
        boolean cancel(final boolean b) {
            return this.complete(null, null, b ? 8 : 4);
        }
        
        private boolean complete(@Nullable final Object value, @Nullable final Throwable t, final int n) {
            final boolean compareAndSetState = this.compareAndSetState(0, 1);
            if (compareAndSetState) {
                this.value = value;
                this.exception = (((n & 0xC) != 0x0) ? new CancellationException("Future.cancel() was called.") : t);
                this.releaseShared(n);
            }
            else if (this.getState() == 1) {
                this.acquireShared(-1);
            }
            return compareAndSetState;
        }
    }
}
