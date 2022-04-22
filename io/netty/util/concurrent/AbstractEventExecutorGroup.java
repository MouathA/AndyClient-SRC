package io.netty.util.concurrent;

import java.util.*;
import java.util.concurrent.*;

public abstract class AbstractEventExecutorGroup implements EventExecutorGroup
{
    @Override
    public Future submit(final Runnable runnable) {
        return this.next().submit(runnable);
    }
    
    @Override
    public Future submit(final Runnable runnable, final Object o) {
        return this.next().submit(runnable, o);
    }
    
    @Override
    public Future submit(final Callable callable) {
        return this.next().submit(callable);
    }
    
    @Override
    public ScheduledFuture schedule(final Runnable runnable, final long n, final TimeUnit timeUnit) {
        return this.next().schedule(runnable, n, timeUnit);
    }
    
    @Override
    public ScheduledFuture schedule(final Callable callable, final long n, final TimeUnit timeUnit) {
        return this.next().schedule(callable, n, timeUnit);
    }
    
    @Override
    public ScheduledFuture scheduleAtFixedRate(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        return this.next().scheduleAtFixedRate(runnable, n, n2, timeUnit);
    }
    
    @Override
    public ScheduledFuture scheduleWithFixedDelay(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        return this.next().scheduleWithFixedDelay(runnable, n, n2, timeUnit);
    }
    
    @Override
    public Future shutdownGracefully() {
        return this.shutdownGracefully(2L, 15L, TimeUnit.SECONDS);
    }
    
    @Deprecated
    @Override
    public abstract void shutdown();
    
    @Deprecated
    @Override
    public List shutdownNow() {
        this.shutdown();
        return Collections.emptyList();
    }
    
    @Override
    public List invokeAll(final Collection collection) throws InterruptedException {
        return this.next().invokeAll((Collection<? extends Callable<Object>>)collection);
    }
    
    @Override
    public List invokeAll(final Collection collection, final long n, final TimeUnit timeUnit) throws InterruptedException {
        return this.next().invokeAll((Collection<? extends Callable<Object>>)collection, n, timeUnit);
    }
    
    @Override
    public Object invokeAny(final Collection collection) throws InterruptedException, ExecutionException {
        return this.next().invokeAny((Collection<? extends Callable<Object>>)collection);
    }
    
    @Override
    public Object invokeAny(final Collection collection, final long n, final TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.next().invokeAny((Collection<? extends Callable<Object>>)collection, n, timeUnit);
    }
    
    @Override
    public void execute(final Runnable runnable) {
        this.next().execute(runnable);
    }
    
    @Override
    public java.util.concurrent.ScheduledFuture scheduleWithFixedDelay(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        return this.scheduleWithFixedDelay(runnable, n, n2, timeUnit);
    }
    
    @Override
    public java.util.concurrent.ScheduledFuture scheduleAtFixedRate(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        return this.scheduleAtFixedRate(runnable, n, n2, timeUnit);
    }
    
    @Override
    public java.util.concurrent.ScheduledFuture schedule(final Callable callable, final long n, final TimeUnit timeUnit) {
        return this.schedule(callable, n, timeUnit);
    }
    
    @Override
    public java.util.concurrent.ScheduledFuture schedule(final Runnable runnable, final long n, final TimeUnit timeUnit) {
        return this.schedule(runnable, n, timeUnit);
    }
    
    @Override
    public java.util.concurrent.Future submit(final Runnable runnable) {
        return this.submit(runnable);
    }
    
    @Override
    public java.util.concurrent.Future submit(final Runnable runnable, final Object o) {
        return this.submit(runnable, o);
    }
    
    @Override
    public java.util.concurrent.Future submit(final Callable callable) {
        return this.submit(callable);
    }
}
