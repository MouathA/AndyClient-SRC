package com.google.common.cache;

import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.collect.*;
import java.util.*;
import java.util.concurrent.*;
import com.google.common.base.*;

@Beta
public abstract class ForwardingCache extends ForwardingObject implements Cache
{
    protected ForwardingCache() {
    }
    
    @Override
    protected abstract Cache delegate();
    
    @Nullable
    @Override
    public Object getIfPresent(final Object o) {
        return this.delegate().getIfPresent(o);
    }
    
    @Override
    public Object get(final Object o, final Callable callable) throws ExecutionException {
        return this.delegate().get(o, callable);
    }
    
    @Override
    public ImmutableMap getAllPresent(final Iterable iterable) {
        return this.delegate().getAllPresent(iterable);
    }
    
    @Override
    public void put(final Object o, final Object o2) {
        this.delegate().put(o, o2);
    }
    
    @Override
    public void putAll(final Map map) {
        this.delegate().putAll(map);
    }
    
    @Override
    public void invalidate(final Object o) {
        this.delegate().invalidate(o);
    }
    
    @Override
    public void invalidateAll(final Iterable iterable) {
        this.delegate().invalidateAll(iterable);
    }
    
    @Override
    public void invalidateAll() {
        this.delegate().invalidateAll();
    }
    
    @Override
    public long size() {
        return this.delegate().size();
    }
    
    @Override
    public CacheStats stats() {
        return this.delegate().stats();
    }
    
    @Override
    public ConcurrentMap asMap() {
        return this.delegate().asMap();
    }
    
    @Override
    public void cleanUp() {
        this.delegate().cleanUp();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
    
    @Beta
    public abstract static class SimpleForwardingCache extends ForwardingCache
    {
        private final Cache delegate;
        
        protected SimpleForwardingCache(final Cache cache) {
            this.delegate = (Cache)Preconditions.checkNotNull(cache);
        }
        
        @Override
        protected final Cache delegate() {
            return this.delegate;
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
}
