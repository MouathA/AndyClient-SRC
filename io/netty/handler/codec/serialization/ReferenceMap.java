package io.netty.handler.codec.serialization;

import java.lang.ref.*;
import java.util.*;

abstract class ReferenceMap implements Map
{
    private final Map delegate;
    
    protected ReferenceMap(final Map delegate) {
        this.delegate = delegate;
    }
    
    abstract Reference fold(final Object p0);
    
    private Object unfold(final Reference reference) {
        if (reference == null) {
            return null;
        }
        return reference.get();
    }
    
    @Override
    public int size() {
        return this.delegate.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return this.delegate.containsKey(o);
    }
    
    @Override
    public boolean containsValue(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Object get(final Object o) {
        return this.unfold(this.delegate.get(o));
    }
    
    @Override
    public Object put(final Object o, final Object o2) {
        return this.unfold(this.delegate.put(o, this.fold(o2)));
    }
    
    @Override
    public Object remove(final Object o) {
        return this.unfold(this.delegate.remove(o));
    }
    
    @Override
    public void putAll(final Map map) {
        for (final Entry<Object, V> entry : map.entrySet()) {
            this.delegate.put(entry.getKey(), this.fold(entry.getValue()));
        }
    }
    
    @Override
    public void clear() {
        this.delegate.clear();
    }
    
    @Override
    public Set keySet() {
        return this.delegate.keySet();
    }
    
    @Override
    public Collection values() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Set entrySet() {
        throw new UnsupportedOperationException();
    }
}
