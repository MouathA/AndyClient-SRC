package com.ibm.icu.impl.locale;

import java.util.concurrent.*;
import java.lang.ref.*;

public abstract class LocaleObjectCache
{
    private ConcurrentHashMap _map;
    private ReferenceQueue _queue;
    
    public LocaleObjectCache() {
        this(16, 0.75f, 16);
    }
    
    public LocaleObjectCache(final int n, final float n2, final int n3) {
        this._queue = new ReferenceQueue();
        this._map = new ConcurrentHashMap(n, n2, n3);
    }
    
    public Object get(Object normalizeKey) {
        Object o = null;
        this.cleanStaleEntries();
        final CacheEntry cacheEntry = this._map.get(normalizeKey);
        if (cacheEntry != null) {
            o = cacheEntry.get();
        }
        if (o == null) {
            normalizeKey = this.normalizeKey(normalizeKey);
            final Object object = this.createObject(normalizeKey);
            if (normalizeKey == null || object == null) {
                return null;
            }
            final CacheEntry cacheEntry2 = new CacheEntry(normalizeKey, object, this._queue);
            while (o == null) {
                this.cleanStaleEntries();
                final CacheEntry cacheEntry3 = this._map.putIfAbsent(normalizeKey, cacheEntry2);
                if (cacheEntry3 == null) {
                    o = object;
                    break;
                }
                o = cacheEntry3.get();
            }
        }
        return o;
    }
    
    private void cleanStaleEntries() {
        CacheEntry cacheEntry;
        while ((cacheEntry = (CacheEntry)this._queue.poll()) != null) {
            this._map.remove(cacheEntry.getKey());
        }
    }
    
    protected abstract Object createObject(final Object p0);
    
    protected Object normalizeKey(final Object o) {
        return o;
    }
    
    private static class CacheEntry extends SoftReference
    {
        private Object _key;
        
        CacheEntry(final Object key, final Object o, final ReferenceQueue referenceQueue) {
            super(o, referenceQueue);
            this._key = key;
        }
        
        Object getKey() {
            return this._key;
        }
    }
}
