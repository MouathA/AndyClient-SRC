package io.netty.util.concurrent;

import java.util.concurrent.*;

class PromiseTask extends DefaultPromise implements RunnableFuture
{
    protected final Callable task;
    
    static Callable toCallable(final Runnable runnable, final Object o) {
        return new RunnableAdapter(runnable, o);
    }
    
    PromiseTask(final EventExecutor eventExecutor, final Runnable runnable, final Object o) {
        this(eventExecutor, toCallable(runnable, o));
    }
    
    PromiseTask(final EventExecutor eventExecutor, final Callable task) {
        super(eventExecutor);
        this.task = task;
    }
    
    @Override
    public final int hashCode() {
        return System.identityHashCode(this);
    }
    
    @Override
    public final boolean equals(final Object o) {
        return this == o;
    }
    
    @Override
    public void run() {
        if (this.setUncancellableInternal()) {
            this.setSuccessInternal(this.task.call());
        }
    }
    
    @Override
    public final Promise setFailure(final Throwable t) {
        throw new IllegalStateException();
    }
    
    protected final Promise setFailureInternal(final Throwable failure) {
        super.setFailure(failure);
        return this;
    }
    
    @Override
    public final boolean tryFailure(final Throwable t) {
        return false;
    }
    
    protected final boolean tryFailureInternal(final Throwable t) {
        return super.tryFailure(t);
    }
    
    @Override
    public final Promise setSuccess(final Object o) {
        throw new IllegalStateException();
    }
    
    protected final Promise setSuccessInternal(final Object success) {
        super.setSuccess(success);
        return this;
    }
    
    @Override
    public final boolean trySuccess(final Object o) {
        return false;
    }
    
    protected final boolean trySuccessInternal(final Object o) {
        return super.trySuccess(o);
    }
    
    @Override
    public final boolean setUncancellable() {
        throw new IllegalStateException();
    }
    
    protected final boolean setUncancellableInternal() {
        return super.setUncancellable();
    }
    
    @Override
    protected StringBuilder toStringBuilder() {
        final StringBuilder stringBuilder = super.toStringBuilder();
        stringBuilder.setCharAt(stringBuilder.length() - 1, ',');
        stringBuilder.append(" task: ");
        stringBuilder.append(this.task);
        stringBuilder.append(')');
        return stringBuilder;
    }
    
    private static final class RunnableAdapter implements Callable
    {
        final Runnable task;
        final Object result;
        
        RunnableAdapter(final Runnable task, final Object result) {
            this.task = task;
            this.result = result;
        }
        
        @Override
        public Object call() {
            this.task.run();
            return this.result;
        }
        
        @Override
        public String toString() {
            return "Callable(task: " + this.task + ", result: " + this.result + ')';
        }
    }
}
