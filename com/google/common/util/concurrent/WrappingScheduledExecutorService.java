package com.google.common.util.concurrent;

import java.util.concurrent.*;

abstract class WrappingScheduledExecutorService extends WrappingExecutorService implements ScheduledExecutorService
{
    final ScheduledExecutorService delegate;
    
    protected WrappingScheduledExecutorService(final ScheduledExecutorService delegate) {
        super(delegate);
        this.delegate = delegate;
    }
    
    @Override
    public final ScheduledFuture schedule(final Runnable runnable, final long n, final TimeUnit timeUnit) {
        return this.delegate.schedule(this.wrapTask(runnable), n, timeUnit);
    }
    
    @Override
    public final ScheduledFuture schedule(final Callable callable, final long n, final TimeUnit timeUnit) {
        return this.delegate.schedule((Callable<Object>)this.wrapTask(callable), n, timeUnit);
    }
    
    @Override
    public final ScheduledFuture scheduleAtFixedRate(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        return this.delegate.scheduleAtFixedRate(this.wrapTask(runnable), n, n2, timeUnit);
    }
    
    @Override
    public final ScheduledFuture scheduleWithFixedDelay(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        return this.delegate.scheduleWithFixedDelay(this.wrapTask(runnable), n, n2, timeUnit);
    }
}
