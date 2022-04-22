package com.google.common.cache;

import com.google.common.annotations.*;
import com.google.common.collect.*;
import java.util.*;
import java.util.concurrent.*;

@Beta
@GwtCompatible
public abstract class AbstractCache implements Cache
{
    protected AbstractCache() {
    }
    
    @Override
    public Object get(final Object o, final Callable callable) throws ExecutionException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ImmutableMap getAllPresent(final Iterable iterable) {
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        for (final Object next : iterable) {
            if (!linkedHashMap.containsKey(next)) {
                linkedHashMap.put(next, this.getIfPresent(next));
            }
        }
        return ImmutableMap.copyOf(linkedHashMap);
    }
    
    @Override
    public void put(final Object o, final Object o2) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void putAll(final Map map) {
        for (final Map.Entry<Object, V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public void cleanUp() {
    }
    
    @Override
    public long size() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void invalidate(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void invalidateAll(final Iterable iterable) {
        final Iterator<Object> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            this.invalidate(iterator.next());
        }
    }
    
    @Override
    public void invalidateAll() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public CacheStats stats() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ConcurrentMap asMap() {
        throw new UnsupportedOperationException();
    }
    
    @Beta
    public static final class SimpleStatsCounter implements StatsCounter
    {
        private final LongAddable hitCount;
        private final LongAddable missCount;
        private final LongAddable loadSuccessCount;
        private final LongAddable loadExceptionCount;
        private final LongAddable totalLoadTime;
        private final LongAddable evictionCount;
        
        public SimpleStatsCounter() {
            this.hitCount = LongAddables.create();
            this.missCount = LongAddables.create();
            this.loadSuccessCount = LongAddables.create();
            this.loadExceptionCount = LongAddables.create();
            this.totalLoadTime = LongAddables.create();
            this.evictionCount = LongAddables.create();
        }
        
        @Override
        public void recordHits(final int n) {
            this.hitCount.add(n);
        }
        
        @Override
        public void recordMisses(final int n) {
            this.missCount.add(n);
        }
        
        @Override
        public void recordLoadSuccess(final long n) {
            this.loadSuccessCount.increment();
            this.totalLoadTime.add(n);
        }
        
        @Override
        public void recordLoadException(final long n) {
            this.loadExceptionCount.increment();
            this.totalLoadTime.add(n);
        }
        
        @Override
        public void recordEviction() {
            this.evictionCount.increment();
        }
        
        @Override
        public CacheStats snapshot() {
            return new CacheStats(this.hitCount.sum(), this.missCount.sum(), this.loadSuccessCount.sum(), this.loadExceptionCount.sum(), this.totalLoadTime.sum(), this.evictionCount.sum());
        }
        
        public void incrementBy(final StatsCounter statsCounter) {
            final CacheStats snapshot = statsCounter.snapshot();
            this.hitCount.add(snapshot.hitCount());
            this.missCount.add(snapshot.missCount());
            this.loadSuccessCount.add(snapshot.loadSuccessCount());
            this.loadExceptionCount.add(snapshot.loadExceptionCount());
            this.totalLoadTime.add(snapshot.totalLoadTime());
            this.evictionCount.add(snapshot.evictionCount());
        }
    }
    
    @Beta
    public interface StatsCounter
    {
        void recordHits(final int p0);
        
        void recordMisses(final int p0);
        
        void recordLoadSuccess(final long p0);
        
        void recordLoadException(final long p0);
        
        void recordEviction();
        
        CacheStats snapshot();
    }
}
