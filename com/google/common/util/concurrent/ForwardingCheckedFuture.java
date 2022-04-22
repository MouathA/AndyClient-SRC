package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import java.util.concurrent.*;
import com.google.common.base.*;

@Beta
public abstract class ForwardingCheckedFuture extends ForwardingListenableFuture implements CheckedFuture
{
    @Override
    public Object checkedGet() throws Exception {
        return this.delegate().checkedGet();
    }
    
    @Override
    public Object checkedGet(final long n, final TimeUnit timeUnit) throws TimeoutException, Exception {
        return this.delegate().checkedGet(n, timeUnit);
    }
    
    @Override
    protected abstract CheckedFuture delegate();
    
    @Override
    protected ListenableFuture delegate() {
        return this.delegate();
    }
    
    @Override
    protected Future delegate() {
        return this.delegate();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
    
    @Beta
    public abstract static class SimpleForwardingCheckedFuture extends ForwardingCheckedFuture
    {
        private final CheckedFuture delegate;
        
        protected SimpleForwardingCheckedFuture(final CheckedFuture checkedFuture) {
            this.delegate = (CheckedFuture)Preconditions.checkNotNull(checkedFuture);
        }
        
        @Override
        protected final CheckedFuture delegate() {
            return this.delegate;
        }
        
        @Override
        protected ListenableFuture delegate() {
            return this.delegate();
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
