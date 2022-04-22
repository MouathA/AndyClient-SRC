package io.netty.util.concurrent;

import java.util.concurrent.*;

public abstract class CompleteFuture extends AbstractFuture
{
    private final EventExecutor executor;
    
    protected CompleteFuture(final EventExecutor executor) {
        this.executor = executor;
    }
    
    protected EventExecutor executor() {
        return this.executor;
    }
    
    @Override
    public Future addListener(final GenericFutureListener genericFutureListener) {
        if (genericFutureListener == null) {
            throw new NullPointerException("listener");
        }
        DefaultPromise.notifyListener(this.executor(), this, genericFutureListener);
        return this;
    }
    
    @Override
    public Future addListeners(final GenericFutureListener... array) {
        if (array == null) {
            throw new NullPointerException("listeners");
        }
        while (0 < array.length) {
            final GenericFutureListener genericFutureListener = array[0];
            if (genericFutureListener == null) {
                break;
            }
            DefaultPromise.notifyListener(this.executor(), this, genericFutureListener);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    @Override
    public Future removeListener(final GenericFutureListener genericFutureListener) {
        return this;
    }
    
    @Override
    public Future removeListeners(final GenericFutureListener... array) {
        return this;
    }
    
    @Override
    public Future await() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return this;
    }
    
    @Override
    public boolean await(final long n, final TimeUnit timeUnit) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return true;
    }
    
    @Override
    public Future sync() throws InterruptedException {
        return this;
    }
    
    @Override
    public Future syncUninterruptibly() {
        return this;
    }
    
    @Override
    public boolean await(final long n) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return true;
    }
    
    @Override
    public Future awaitUninterruptibly() {
        return this;
    }
    
    @Override
    public boolean awaitUninterruptibly(final long n, final TimeUnit timeUnit) {
        return true;
    }
    
    @Override
    public boolean awaitUninterruptibly(final long n) {
        return true;
    }
    
    @Override
    public boolean isDone() {
        return true;
    }
    
    @Override
    public boolean isCancellable() {
        return false;
    }
    
    @Override
    public boolean isCancelled() {
        return false;
    }
    
    @Override
    public boolean cancel(final boolean b) {
        return false;
    }
}
