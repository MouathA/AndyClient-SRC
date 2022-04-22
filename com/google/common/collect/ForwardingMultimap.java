package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public abstract class ForwardingMultimap extends ForwardingObject implements Multimap
{
    protected ForwardingMultimap() {
    }
    
    @Override
    protected abstract Multimap delegate();
    
    @Override
    public Map asMap() {
        return this.delegate().asMap();
    }
    
    @Override
    public void clear() {
        this.delegate().clear();
    }
    
    @Override
    public boolean containsEntry(@Nullable final Object o, @Nullable final Object o2) {
        return this.delegate().containsEntry(o, o2);
    }
    
    @Override
    public boolean containsKey(@Nullable final Object o) {
        return this.delegate().containsKey(o);
    }
    
    @Override
    public boolean containsValue(@Nullable final Object o) {
        return this.delegate().containsValue(o);
    }
    
    @Override
    public Collection entries() {
        return this.delegate().entries();
    }
    
    @Override
    public Collection get(@Nullable final Object o) {
        return this.delegate().get(o);
    }
    
    @Override
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }
    
    @Override
    public Multiset keys() {
        return this.delegate().keys();
    }
    
    @Override
    public Set keySet() {
        return this.delegate().keySet();
    }
    
    @Override
    public boolean put(final Object o, final Object o2) {
        return this.delegate().put(o, o2);
    }
    
    @Override
    public boolean putAll(final Object o, final Iterable iterable) {
        return this.delegate().putAll(o, iterable);
    }
    
    @Override
    public boolean putAll(final Multimap multimap) {
        return this.delegate().putAll(multimap);
    }
    
    @Override
    public boolean remove(@Nullable final Object o, @Nullable final Object o2) {
        return this.delegate().remove(o, o2);
    }
    
    @Override
    public Collection removeAll(@Nullable final Object o) {
        return this.delegate().removeAll(o);
    }
    
    @Override
    public Collection replaceValues(final Object o, final Iterable iterable) {
        return this.delegate().replaceValues(o, iterable);
    }
    
    @Override
    public int size() {
        return this.delegate().size();
    }
    
    @Override
    public Collection values() {
        return this.delegate().values();
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o == this || this.delegate().equals(o);
    }
    
    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
}
