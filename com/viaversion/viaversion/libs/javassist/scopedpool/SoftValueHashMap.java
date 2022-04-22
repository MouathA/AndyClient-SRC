package com.viaversion.viaversion.libs.javassist.scopedpool;

import java.util.concurrent.*;
import java.util.*;
import java.lang.ref.*;

public class SoftValueHashMap implements Map
{
    private Map hash;
    private ReferenceQueue queue;
    
    @Override
    public Set entrySet() {
        this.processQueue();
        final HashSet<AbstractMap.SimpleImmutableEntry<Object, Object>> set = new HashSet<AbstractMap.SimpleImmutableEntry<Object, Object>>();
        for (final Entry<Object, V> entry : this.hash.entrySet()) {
            set.add(new AbstractMap.SimpleImmutableEntry<Object, Object>(entry.getKey(), ((SoftValueRef)entry.getValue()).get()));
        }
        return set;
    }
    
    private void processQueue() {
        if (!this.hash.isEmpty()) {
            Reference poll;
            while ((poll = this.queue.poll()) != null) {
                if (poll instanceof SoftValueRef) {
                    final SoftValueRef softValueRef = (SoftValueRef)poll;
                    if (poll != this.hash.get(softValueRef.key)) {
                        continue;
                    }
                    this.hash.remove(softValueRef.key);
                }
            }
        }
    }
    
    public SoftValueHashMap(final int n, final float n2) {
        this.queue = new ReferenceQueue();
        this.hash = new ConcurrentHashMap(n, n2);
    }
    
    public SoftValueHashMap(final int n) {
        this.queue = new ReferenceQueue();
        this.hash = new ConcurrentHashMap(n);
    }
    
    public SoftValueHashMap() {
        this.queue = new ReferenceQueue();
        this.hash = new ConcurrentHashMap();
    }
    
    public SoftValueHashMap(final Map map) {
        this(Math.max(2 * map.size(), 11), 0.75f);
        this.putAll(map);
    }
    
    @Override
    public int size() {
        this.processQueue();
        return this.hash.size();
    }
    
    @Override
    public boolean isEmpty() {
        this.processQueue();
        return this.hash.isEmpty();
    }
    
    @Override
    public boolean containsKey(final Object o) {
        this.processQueue();
        return this.hash.containsKey(o);
    }
    
    @Override
    public Object get(final Object o) {
        this.processQueue();
        return this.valueOrNull(this.hash.get(o));
    }
    
    @Override
    public Object put(final Object o, final Object o2) {
        this.processQueue();
        return this.valueOrNull(this.hash.put(o, SoftValueRef.access$000(o, o2, this.queue)));
    }
    
    @Override
    public Object remove(final Object o) {
        this.processQueue();
        return this.valueOrNull(this.hash.remove(o));
    }
    
    @Override
    public void clear() {
        this.processQueue();
        this.hash.clear();
    }
    
    @Override
    public boolean containsValue(final Object o) {
        this.processQueue();
        if (null == o) {
            return false;
        }
        for (final SoftValueRef softValueRef : this.hash.values()) {
            if (null != softValueRef && o.equals(softValueRef.get())) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Set keySet() {
        this.processQueue();
        return this.hash.keySet();
    }
    
    @Override
    public void putAll(final Map map) {
        this.processQueue();
        for (final Object next : map.keySet()) {
            this.put(next, map.get(next));
        }
    }
    
    @Override
    public Collection values() {
        this.processQueue();
        final ArrayList<Object> list = new ArrayList<Object>();
        final Iterator<SoftValueRef> iterator = this.hash.values().iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().get());
        }
        return list;
    }
    
    private Object valueOrNull(final SoftValueRef softValueRef) {
        if (null == softValueRef) {
            return null;
        }
        return softValueRef.get();
    }
    
    private static class SoftValueRef extends SoftReference
    {
        public Object key;
        
        private SoftValueRef(final Object key, final Object o, final ReferenceQueue referenceQueue) {
            super(o, referenceQueue);
            this.key = key;
        }
        
        private static SoftValueRef create(final Object o, final Object o2, final ReferenceQueue referenceQueue) {
            if (o2 == null) {
                return null;
            }
            return new SoftValueRef(o, o2, referenceQueue);
        }
        
        static SoftValueRef access$000(final Object o, final Object o2, final ReferenceQueue referenceQueue) {
            return create(o, o2, referenceQueue);
        }
    }
}
