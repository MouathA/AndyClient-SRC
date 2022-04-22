package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.concurrent.*;

@Beta
public abstract class AbstractListeningExecutorService extends AbstractExecutorService implements ListeningExecutorService
{
    @Override
    protected final ListenableFutureTask newTaskFor(final Runnable runnable, final Object o) {
        return ListenableFutureTask.create(runnable, o);
    }
    
    @Override
    protected final ListenableFutureTask newTaskFor(final Callable callable) {
        return ListenableFutureTask.create(callable);
    }
    
    @Override
    public ListenableFuture submit(final Runnable runnable) {
        return (ListenableFuture)super.submit(runnable);
    }
    
    @Override
    public ListenableFuture submit(final Runnable runnable, @Nullable final Object o) {
        return (ListenableFuture)super.submit(runnable, o);
    }
    
    @Override
    public ListenableFuture submit(final Callable callable) {
        return (ListenableFuture)super.submit((Callable<Object>)callable);
    }
    
    @Override
    public Future submit(final Callable callable) {
        return this.submit(callable);
    }
    
    @Override
    public Future submit(final Runnable runnable, final Object o) {
        return this.submit(runnable, o);
    }
    
    @Override
    public Future submit(final Runnable runnable) {
        return this.submit(runnable);
    }
    
    @Override
    protected RunnableFuture newTaskFor(final Callable callable) {
        return this.newTaskFor(callable);
    }
    
    @Override
    protected RunnableFuture newTaskFor(final Runnable runnable, final Object o) {
        return this.newTaskFor(runnable, o);
    }
}
