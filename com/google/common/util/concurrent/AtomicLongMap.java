package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;

@GwtCompatible
public final class AtomicLongMap
{
    private final ConcurrentHashMap map;
    private transient Map asMap;
    
    private AtomicLongMap(final ConcurrentHashMap concurrentHashMap) {
        this.map = (ConcurrentHashMap)Preconditions.checkNotNull(concurrentHashMap);
    }
    
    public static AtomicLongMap create() {
        return new AtomicLongMap(new ConcurrentHashMap());
    }
    
    public static AtomicLongMap create(final Map map) {
        final AtomicLongMap create = create();
        create.putAll(map);
        return create;
    }
    
    public long get(final Object o) {
        final AtomicLong atomicLong = this.map.get(o);
        return (atomicLong == null) ? 0L : atomicLong.get();
    }
    
    public long incrementAndGet(final Object o) {
        return this.addAndGet(o, 1L);
    }
    
    public long decrementAndGet(final Object o) {
        return this.addAndGet(o, -1L);
    }
    
    public long addAndGet(final Object o, final long n) {
        while (true) {
            AtomicLong atomicLong = this.map.get(o);
            if (atomicLong == null) {
                atomicLong = this.map.putIfAbsent(o, new AtomicLong(n));
                if (atomicLong == null) {
                    return n;
                }
            }
            while (true) {
                final long value = atomicLong.get();
                if (value == 0L) {
                    if (this.map.replace(o, atomicLong, new AtomicLong(n))) {
                        return n;
                    }
                    break;
                }
                else {
                    final long n2 = value + n;
                    if (atomicLong.compareAndSet(value, n2)) {
                        return n2;
                    }
                    continue;
                }
            }
        }
    }
    
    public long getAndIncrement(final Object o) {
        return this.getAndAdd(o, 1L);
    }
    
    public long getAndDecrement(final Object o) {
        return this.getAndAdd(o, -1L);
    }
    
    public long getAndAdd(final Object o, final long n) {
        while (true) {
            AtomicLong atomicLong = this.map.get(o);
            if (atomicLong == null) {
                atomicLong = this.map.putIfAbsent(o, new AtomicLong(n));
                if (atomicLong == null) {
                    return 0L;
                }
            }
            while (true) {
                final long value = atomicLong.get();
                if (value == 0L) {
                    if (this.map.replace(o, atomicLong, new AtomicLong(n))) {
                        return 0L;
                    }
                    break;
                }
                else {
                    if (atomicLong.compareAndSet(value, value + n)) {
                        return value;
                    }
                    continue;
                }
            }
        }
    }
    
    public long put(final Object o, final long n) {
        while (true) {
            AtomicLong atomicLong = this.map.get(o);
            if (atomicLong == null) {
                atomicLong = this.map.putIfAbsent(o, new AtomicLong(n));
                if (atomicLong == null) {
                    return 0L;
                }
            }
            while (true) {
                final long value = atomicLong.get();
                if (value == 0L) {
                    if (this.map.replace(o, atomicLong, new AtomicLong(n))) {
                        return 0L;
                    }
                    break;
                }
                else {
                    if (atomicLong.compareAndSet(value, n)) {
                        return value;
                    }
                    continue;
                }
            }
        }
    }
    
    public void putAll(final Map map) {
        for (final Map.Entry<Object, V> entry : map.entrySet()) {
            this.put(entry.getKey(), (long)entry.getValue());
        }
    }
    
    public long remove(final Object o) {
        final AtomicLong atomicLong = this.map.get(o);
        if (atomicLong == null) {
            return 0L;
        }
        long value;
        do {
            value = atomicLong.get();
        } while (value != 0L && !atomicLong.compareAndSet(value, 0L));
        this.map.remove(o, atomicLong);
        return value;
    }
    
    public void removeAllZeros() {
        for (final Object next : this.map.keySet()) {
            final AtomicLong atomicLong = this.map.get(next);
            if (atomicLong != null && atomicLong.get() == 0L) {
                this.map.remove(next, atomicLong);
            }
        }
    }
    
    public long sum() {
        long n = 0L;
        final Iterator<AtomicLong> iterator = this.map.values().iterator();
        while (iterator.hasNext()) {
            n += iterator.next().get();
        }
        return n;
    }
    
    public Map asMap() {
        final Map asMap = this.asMap;
        return (asMap == null) ? (this.asMap = this.createAsMap()) : asMap;
    }
    
    private Map createAsMap() {
        return Collections.unmodifiableMap((Map<?, ?>)Maps.transformValues(this.map, new Function() {
            final AtomicLongMap this$0;
            
            public Long apply(final AtomicLong atomicLong) {
                return atomicLong.get();
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((AtomicLong)o);
            }
        }));
    }
    
    public boolean containsKey(final Object o) {
        return this.map.containsKey(o);
    }
    
    public int size() {
        return this.map.size();
    }
    
    public boolean isEmpty() {
        return this.map.isEmpty();
    }
    
    public void clear() {
        this.map.clear();
    }
    
    @Override
    public String toString() {
        return this.map.toString();
    }
    
    long putIfAbsent(final Object o, final long n) {
        AtomicLong atomicLong;
        do {
            atomicLong = this.map.get(o);
            if (atomicLong == null) {
                atomicLong = this.map.putIfAbsent(o, new AtomicLong(n));
                if (atomicLong == null) {
                    return 0L;
                }
            }
            final long value = atomicLong.get();
            if (value == 0L) {
                continue;
            }
            return value;
        } while (!this.map.replace(o, atomicLong, new AtomicLong(n)));
        return 0L;
    }
    
    boolean replace(final Object o, final long n, final long n2) {
        if (n == 0L) {
            return this.putIfAbsent(o, n2) == 0L;
        }
        final AtomicLong atomicLong = this.map.get(o);
        return atomicLong != null && atomicLong.compareAndSet(n, n2);
    }
    
    boolean remove(final Object o, final long n) {
        final AtomicLong atomicLong = this.map.get(o);
        if (atomicLong == null) {
            return false;
        }
        final long value = atomicLong.get();
        if (value != n) {
            return false;
        }
        if (value == 0L || atomicLong.compareAndSet(value, 0L)) {
            this.map.remove(o, atomicLong);
            return true;
        }
        return false;
    }
}
