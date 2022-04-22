package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.base.*;
import java.util.*;

@GwtCompatible(emulated = true)
final class EmptyImmutableSortedMap extends ImmutableSortedMap
{
    private final transient ImmutableSortedSet keySet;
    
    EmptyImmutableSortedMap(final Comparator comparator) {
        this.keySet = ImmutableSortedSet.emptySet(comparator);
    }
    
    EmptyImmutableSortedMap(final Comparator comparator, final ImmutableSortedMap immutableSortedMap) {
        super(immutableSortedMap);
        this.keySet = ImmutableSortedSet.emptySet(comparator);
    }
    
    @Override
    public Object get(@Nullable final Object o) {
        return null;
    }
    
    @Override
    public ImmutableSortedSet keySet() {
        return this.keySet;
    }
    
    @Override
    public int size() {
        return 0;
    }
    
    @Override
    public boolean isEmpty() {
        return true;
    }
    
    @Override
    public ImmutableCollection values() {
        return ImmutableList.of();
    }
    
    @Override
    public String toString() {
        return "{}";
    }
    
    @Override
    boolean isPartialView() {
        return false;
    }
    
    @Override
    public ImmutableSet entrySet() {
        return ImmutableSet.of();
    }
    
    @Override
    ImmutableSet createEntrySet() {
        throw new AssertionError((Object)"should never be called");
    }
    
    @Override
    public ImmutableSetMultimap asMultimap() {
        return ImmutableSetMultimap.of();
    }
    
    @Override
    public ImmutableSortedMap headMap(final Object o, final boolean b) {
        Preconditions.checkNotNull(o);
        return this;
    }
    
    @Override
    public ImmutableSortedMap tailMap(final Object o, final boolean b) {
        Preconditions.checkNotNull(o);
        return this;
    }
    
    @Override
    ImmutableSortedMap createDescendingMap() {
        return new EmptyImmutableSortedMap(Ordering.from(this.comparator()).reverse(), this);
    }
    
    @Override
    public NavigableMap tailMap(final Object o, final boolean b) {
        return this.tailMap(o, b);
    }
    
    @Override
    public NavigableMap headMap(final Object o, final boolean b) {
        return this.headMap(o, b);
    }
    
    @Override
    public Set entrySet() {
        return this.entrySet();
    }
    
    @Override
    public Collection values() {
        return this.values();
    }
    
    @Override
    public Set keySet() {
        return this.keySet();
    }
    
    @Override
    public ImmutableSet keySet() {
        return this.keySet();
    }
}
