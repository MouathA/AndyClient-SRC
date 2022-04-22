package com.google.common.cache;

import com.google.common.annotations.*;
import java.util.concurrent.*;
import com.google.common.collect.*;
import com.google.common.base.*;

@Beta
public abstract class ForwardingLoadingCache extends ForwardingCache implements LoadingCache
{
    protected ForwardingLoadingCache() {
    }
    
    @Override
    protected abstract LoadingCache delegate();
    
    @Override
    public Object get(final Object o) throws ExecutionException {
        return this.delegate().get(o);
    }
    
    @Override
    public Object getUnchecked(final Object o) {
        return this.delegate().getUnchecked(o);
    }
    
    @Override
    public ImmutableMap getAll(final Iterable iterable) throws ExecutionException {
        return this.delegate().getAll(iterable);
    }
    
    @Override
    public Object apply(final Object o) {
        return this.delegate().apply(o);
    }
    
    @Override
    public void refresh(final Object o) {
        this.delegate().refresh(o);
    }
    
    @Override
    protected Cache delegate() {
        return this.delegate();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
    
    @Beta
    public abstract static class SimpleForwardingLoadingCache extends ForwardingLoadingCache
    {
        private final LoadingCache delegate;
        
        protected SimpleForwardingLoadingCache(final LoadingCache loadingCache) {
            this.delegate = (LoadingCache)Preconditions.checkNotNull(loadingCache);
        }
        
        @Override
        protected final LoadingCache delegate() {
            return this.delegate;
        }
        
        @Override
        protected Cache delegate() {
            return this.delegate();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
}
