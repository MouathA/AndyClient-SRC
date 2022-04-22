package com.google.common.collect;

import javax.annotation.*;
import java.util.*;

final class DescendingImmutableSortedMultiset extends ImmutableSortedMultiset
{
    private final transient ImmutableSortedMultiset forward;
    
    DescendingImmutableSortedMultiset(final ImmutableSortedMultiset forward) {
        this.forward = forward;
    }
    
    @Override
    public int count(@Nullable final Object o) {
        return this.forward.count(o);
    }
    
    @Override
    public Multiset.Entry firstEntry() {
        return this.forward.lastEntry();
    }
    
    @Override
    public Multiset.Entry lastEntry() {
        return this.forward.firstEntry();
    }
    
    @Override
    public int size() {
        return this.forward.size();
    }
    
    @Override
    public ImmutableSortedSet elementSet() {
        return this.forward.elementSet().descendingSet();
    }
    
    @Override
    Multiset.Entry getEntry(final int n) {
        return this.forward.entrySet().asList().reverse().get(n);
    }
    
    @Override
    public ImmutableSortedMultiset descendingMultiset() {
        return this.forward;
    }
    
    @Override
    public ImmutableSortedMultiset headMultiset(final Object o, final BoundType boundType) {
        return this.forward.tailMultiset(o, boundType).descendingMultiset();
    }
    
    @Override
    public ImmutableSortedMultiset tailMultiset(final Object o, final BoundType boundType) {
        return this.forward.headMultiset(o, boundType).descendingMultiset();
    }
    
    @Override
    boolean isPartialView() {
        return this.forward.isPartialView();
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
    public SortedMultiset descendingMultiset() {
        return this.descendingMultiset();
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
