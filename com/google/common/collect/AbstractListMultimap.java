package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
abstract class AbstractListMultimap extends AbstractMapBasedMultimap implements ListMultimap
{
    private static final long serialVersionUID = 6588350623831699109L;
    
    protected AbstractListMultimap(final Map map) {
        super(map);
    }
    
    @Override
    abstract List createCollection();
    
    @Override
    List createUnmodifiableEmptyCollection() {
        return ImmutableList.of();
    }
    
    @Override
    public List get(@Nullable final Object o) {
        return (List)super.get(o);
    }
    
    @Override
    public List removeAll(@Nullable final Object o) {
        return (List)super.removeAll(o);
    }
    
    @Override
    public List replaceValues(@Nullable final Object o, final Iterable iterable) {
        return (List)super.replaceValues(o, iterable);
    }
    
    @Override
    public boolean put(@Nullable final Object o, @Nullable final Object o2) {
        return super.put(o, o2);
    }
    
    @Override
    public Map asMap() {
        return super.asMap();
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return super.equals(o);
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
