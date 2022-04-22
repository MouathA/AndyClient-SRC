package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible(serializable = true)
class RegularImmutableMultiset extends ImmutableMultiset
{
    private final transient ImmutableMap map;
    private final transient int size;
    
    RegularImmutableMultiset(final ImmutableMap map, final int size) {
        this.map = map;
        this.size = size;
    }
    
    @Override
    boolean isPartialView() {
        return this.map.isPartialView();
    }
    
    @Override
    public int count(@Nullable final Object o) {
        final Integer n = (Integer)this.map.get(o);
        return (n == null) ? 0 : n;
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public boolean contains(@Nullable final Object o) {
        return this.map.containsKey(o);
    }
    
    @Override
    public ImmutableSet elementSet() {
        return this.map.keySet();
    }
    
    @Override
    Multiset.Entry getEntry(final int n) {
        final Map.Entry<Object, V> entry = this.map.entrySet().asList().get(n);
        return Multisets.immutableEntry(entry.getKey(), (int)entry.getValue());
    }
    
    @Override
    public int hashCode() {
        return this.map.hashCode();
    }
    
    @Override
    public Set elementSet() {
        return this.elementSet();
    }
}
