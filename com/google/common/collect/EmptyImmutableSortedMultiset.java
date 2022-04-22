package com.google.common.collect;

import javax.annotation.*;
import com.google.common.base.*;
import java.util.*;

final class EmptyImmutableSortedMultiset extends ImmutableSortedMultiset
{
    private final ImmutableSortedSet elementSet;
    
    EmptyImmutableSortedMultiset(final Comparator comparator) {
        this.elementSet = ImmutableSortedSet.emptySet(comparator);
    }
    
    @Override
    public Multiset.Entry firstEntry() {
        return null;
    }
    
    @Override
    public Multiset.Entry lastEntry() {
        return null;
    }
    
    @Override
    public int count(@Nullable final Object o) {
        return 0;
    }
    
    @Override
    public boolean containsAll(final Collection collection) {
        return collection.isEmpty();
    }
    
    @Override
    public int size() {
        return 0;
    }
    
    @Override
    public ImmutableSortedSet elementSet() {
        return this.elementSet;
    }
    
    @Override
    Multiset.Entry getEntry(final int n) {
        throw new AssertionError((Object)"should never be called");
    }
    
    @Override
    public ImmutableSortedMultiset headMultiset(final Object o, final BoundType boundType) {
        Preconditions.checkNotNull(o);
        Preconditions.checkNotNull(boundType);
        return this;
    }
    
    @Override
    public ImmutableSortedMultiset tailMultiset(final Object o, final BoundType boundType) {
        Preconditions.checkNotNull(o);
        Preconditions.checkNotNull(boundType);
        return this;
    }
    
    @Override
    public UnmodifiableIterator iterator() {
        return Iterators.emptyIterator();
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o instanceof Multiset && ((Multiset)o).isEmpty();
    }
    
    @Override
    boolean isPartialView() {
        return false;
    }
    
    @Override
    int copyIntoArray(final Object[] array, final int n) {
        return n;
    }
    
    @Override
    public ImmutableList asList() {
        return ImmutableList.of();
    }
    
    @Override
    public SortedMultiset tailMultiset(final Object o, final BoundType boundType) {
        return this.tailMultiset(o, boundType);
    }
    
    @Override
    public SortedMultiset headMultiset(final Object o, final BoundType boundType) {
        return this.headMultiset(o, boundType);
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
    
    @Override
    public NavigableSet elementSet() {
        return this.elementSet();
    }
    
    @Override
    public SortedSet elementSet() {
        return this.elementSet();
    }
    
    @Override
    public Set elementSet() {
        return this.elementSet();
    }
}
