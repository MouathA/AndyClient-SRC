package com.google.common.util.concurrent;

import com.google.common.collect.*;
import java.util.concurrent.*;
import com.google.common.base.*;

public abstract class ForwardingFuture extends ForwardingObject implements Future
{
    protected ForwardingFuture() {
    }
    
    @Override
    protected abstract Future delegate();
    
    @Override
    public boolean cancel(final boolean b) {
        return this.delegate().cancel(b);
    }
    
    @Override
    public boolean isCancelled() {
        return this.delegate().isCancelled();
    }
    
    @Override
    public boolean isDone() {
        return this.delegate().isDone();
    }
    
    @Override
    public Object get() throws InterruptedException, ExecutionException {
        return this.delegate().get();
    }
    
    @Override
    public Object get(final long n, final TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.delegate().get(n, timeUnit);
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
    
    public abstract static class SimpleForwardingFuture extends ForwardingFuture
    {
        private final Future delegate;
        
        protected SimpleForwardingFuture(final Future future) {
            this.delegate = (Future)Preconditions.checkNotNull(future);
        }
        
        @Override
        protected final Future delegate() {
            return this.delegate;
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
}
