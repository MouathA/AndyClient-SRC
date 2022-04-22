package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
abstract class AbstractSetMultimap extends AbstractMapBasedMultimap implements SetMultimap
{
    private static final long serialVersionUID = 7431625294878419160L;
    
    protected AbstractSetMultimap(final Map map) {
        super(map);
    }
    
    @Override
    abstract Set createCollection();
    
    @Override
    Set createUnmodifiableEmptyCollection() {
        return ImmutableSet.of();
    }
    
    @Override
    public Set get(@Nullable final Object o) {
        return (Set)super.get(o);
    }
    
    @Override
    public Set entries() {
        return (Set)super.entries();
    }
    
    @Override
    public Set removeAll(@Nullable final Object o) {
        return (Set)super.removeAll(o);
    }
    
    @Override
    public Set replaceValues(@Nullable final Object o, final Iterable iterable) {
        return (Set)super.replaceValues(o, iterable);
    }
    
    @Override
    public Map asMap() {
        return super.asMap();
    }
    
    @Override
    public boolean put(@Nullable final Object o, @Nullable final Object o2) {
        return super.put(o, o2);
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return super.equals(o);
    }
    
    @Override
    public Collection entries() {
        return this.entries();
    }
    
    @Override
    public Collection get(final Object o) {
        return this.get(o);
    }
    
    @Override
    public Collection removeAll(final Object o) {
        return this.removeAll(o);
    }
    
    @Override
    public Collection replaceValues(final Object o, final Iterable iterable) {
        return this.replaceValues(o, iterable);
    }
    
    @Override
    Collection createCollection() {
        return this.createCollection();
    }
    
    @Override
    Collection createUnmodifiableEmptyCollection() {
        return this.createUnmodifiableEmptyCollection();
    }
}
