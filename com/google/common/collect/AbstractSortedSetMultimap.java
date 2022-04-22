package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
abstract class AbstractSortedSetMultimap extends AbstractSetMultimap implements SortedSetMultimap
{
    private static final long serialVersionUID = 430848587173315748L;
    
    protected AbstractSortedSetMultimap(final Map map) {
        super(map);
    }
    
    @Override
    abstract SortedSet createCollection();
    
    @Override
    SortedSet createUnmodifiableEmptyCollection() {
        if (this.valueComparator() == null) {
            return Collections.unmodifiableSortedSet((SortedSet<Object>)this.createCollection());
        }
        return ImmutableSortedSet.emptySet(this.valueComparator());
    }
    
    @Override
    public SortedSet get(@Nullable final Object o) {
        return (SortedSet)super.get(o);
    }
    
    @Override
    public SortedSet removeAll(@Nullable final Object o) {
        return (SortedSet)super.removeAll(o);
    }
    
    @Override
    public SortedSet replaceValues(@Nullable final Object o, final Iterable iterable) {
        return (SortedSet)super.replaceValues(o, iterable);
    }
    
    @Override
    public Map asMap() {
        return super.asMap();
    }
    
    @Override
    public Collection values() {
        return super.values();
    }
    
    @Override
    public Set replaceValues(final Object o, final Iterable iterable) {
        return this.replaceValues(o, iterable);
    }
    
    @Override
    public Set removeAll(final Object o) {
        return this.removeAll(o);
    }
    
    @Override
    public Set get(final Object o) {
        return this.get(o);
    }
    
    @Override
    Set createUnmodifiableEmptyCollection() {
        return this.createUnmodifiableEmptyCollection();
    }
    
    @Override
    Set createCollection() {
        return this.createCollection();
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
