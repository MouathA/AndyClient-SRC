package com.ibm.icu.impl;

import java.util.*;
import java.lang.ref.*;

public class SimpleCache implements ICUCache
{
    private static final int DEFAULT_CAPACITY = 16;
    private Reference cacheRef;
    private int type;
    private int capacity;
    
    public SimpleCache() {
        this.cacheRef = null;
        this.type = 0;
        this.capacity = 16;
    }
    
    public SimpleCache(final int n) {
        this(n, 16);
    }
    
    public SimpleCache(final int type, final int capacity) {
        this.cacheRef = null;
        this.type = 0;
        this.capacity = 16;
        if (type == 1) {
            this.type = type;
        }
        if (capacity > 0) {
            this.capacity = capacity;
        }
    }
    
    public Object get(final Object o) {
        final Reference cacheRef = this.cacheRef;
        if (cacheRef != null) {
            final Map<K, Object> map = cacheRef.get();
            if (map != null) {
                return map.get(o);
            }
        }
        return null;
    }
    
    public void put(final Object o, final Object o2) {
        final Reference cacheRef = this.cacheRef;
        Map<Object, Object> synchronizedMap = null;
        if (cacheRef != null) {
            synchronizedMap = cacheRef.get();
        }
        if (synchronizedMap == null) {
            synchronizedMap = Collections.synchronizedMap(new HashMap<Object, Object>(this.capacity));
            Object cacheRef2;
            if (this.type == 1) {
                cacheRef2 = new WeakReference(synchronizedMap);
            }
            else {
                cacheRef2 = new SoftReference(synchronizedMap);
            }
            this.cacheRef = (Reference)cacheRef2;
        }
        synchronizedMap.put(o, o2);
    }
    
    public void clear() {
        this.cacheRef = null;
    }
}
