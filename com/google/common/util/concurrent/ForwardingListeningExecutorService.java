package com.google.common.util.concurrent;

import java.util.concurrent.*;

public abstract class ForwardingListeningExecutorService extends ForwardingExecutorService implements ListeningExecutorService
{
    protected ForwardingListeningExecutorService() {
    }
    
    @Override
    protected abstract ListeningExecutorService delegate();
    
    @Override
    public ListenableFuture submit(final Callable callable) {
        return this.delegate().submit(callable);
    }
    
    @Override
    public ListenableFuture submit(final Runnable runnable) {
        return this.delegate().submit(runnable);
    }
    
    @Override
    public ListenableFuture submit(final Runnable runnable, final Object o) {
        return this.delegate().submit(runnable, o);
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
    public Future submit(final Callable callable) {
        return this.submit(callable);
    }
    
    @Override
    protected ExecutorService delegate() {
        return this.delegate();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
}
