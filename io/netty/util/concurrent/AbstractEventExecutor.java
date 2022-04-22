package io.netty.util.concurrent;

import java.util.concurrent.*;
import java.util.*;

public abstract class AbstractEventExecutor extends AbstractExecutorService implements EventExecutor
{
    @Override
    public EventExecutor next() {
        return this;
    }
    
    @Override
    public boolean inEventLoop() {
        return this.inEventLoop(Thread.currentThread());
    }
    
    @Override
    public Iterator iterator() {
        return new EventExecutorIterator(null);
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
    public Promise newPromise() {
        return new DefaultPromise(this);
    }
    
    @Override
    public ProgressivePromise newProgressivePromise() {
        return new DefaultProgressivePromise(this);
    }
    
    @Override
    public Future newSucceededFuture(final Object o) {
        return new SucceededFuture(this, o);
    }
    
    @Override
    public Future newFailedFuture(final Throwable t) {
        return new FailedFuture(this, t);
    }
    
    @Override
    public Future submit(final Runnable runnable) {
        return (Future)super.submit(runnable);
    }
    
    @Override
    public Future submit(final Runnable runnable, final Object o) {
        return (Future)super.submit(runnable, o);
    }
    
    @Override
    public Future submit(final Callable callable) {
        return (Future)super.submit((Callable<Object>)callable);
    }
    
    @Override
    protected final RunnableFuture newTaskFor(final Runnable runnable, final Object o) {
        return new PromiseTask(this, runnable, o);
    }
    
    @Override
    protected final RunnableFuture newTaskFor(final Callable callable) {
        return new PromiseTask(this, callable);
    }
    
    @Override
    public ScheduledFuture schedule(final Runnable runnable, final long n, final TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ScheduledFuture schedule(final Callable callable, final long n, final TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ScheduledFuture scheduleAtFixedRate(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ScheduledFuture scheduleWithFixedDelay(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public java.util.concurrent.Future submit(final Callable callable) {
        return this.submit(callable);
    }
    
    @Override
    public java.util.concurrent.Future submit(final Runnable runnable, final Object o) {
        return this.submit(runnable, o);
    }
    
    @Override
    public java.util.concurrent.Future submit(final Runnable runnable) {
        return this.submit(runnable);
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
    
    private final class EventExecutorIterator implements Iterator
    {
        private boolean nextCalled;
        final AbstractEventExecutor this$0;
        
        private EventExecutorIterator(final AbstractEventExecutor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public boolean hasNext() {
            return !this.nextCalled;
        }
        
        @Override
        public EventExecutor next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.nextCalled = true;
            return this.this$0;
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException("read-only");
        }
        
        @Override
        public Object next() {
            return this.next();
        }
        
        EventExecutorIterator(final AbstractEventExecutor abstractEventExecutor, final AbstractEventExecutor$1 object) {
            this(abstractEventExecutor);
        }
    }
}
