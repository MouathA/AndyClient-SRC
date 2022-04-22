package com.google.common.util.concurrent;

import java.util.concurrent.*;
import com.google.common.base.*;

public abstract class ForwardingListenableFuture extends ForwardingFuture implements ListenableFuture
{
    protected ForwardingListenableFuture() {
    }
    
    @Override
    protected abstract ListenableFuture delegate();
    
    @Override
    public void addListener(final Runnable runnable, final Executor executor) {
        this.delegate().addListener(runnable, executor);
    }
    
    @Override
    protected Future delegate() {
        return this.delegate();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
    
    public abstract static class SimpleForwardingListenableFuture extends ForwardingListenableFuture
    {
        private final ListenableFuture delegate;
        
        protected SimpleForwardingListenableFuture(final ListenableFuture listenableFuture) {
            this.delegate = (ListenableFuture)Preconditions.checkNotNull(listenableFuture);
        }
        
        @Override
        protected final ListenableFuture delegate() {
            return this.delegate;
        }
        
        @Override
        protected Future delegate() {
            return this.delegate();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
}
