package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

class DescendingImmutableSortedSet extends ImmutableSortedSet
{
    private final ImmutableSortedSet forward;
    
    DescendingImmutableSortedSet(final ImmutableSortedSet forward) {
        super(Ordering.from(forward.comparator()).reverse());
        this.forward = forward;
    }
    
    @Override
    public int size() {
        return this.forward.size();
    }
    
    @Override
    public UnmodifiableIterator iterator() {
        return this.forward.descendingIterator();
    }
    
    @Override
    ImmutableSortedSet headSetImpl(final Object o, final boolean b) {
        return this.forward.tailSet(o, b).descendingSet();
    }
    
    @Override
    ImmutableSortedSet subSetImpl(final Object o, final boolean b, final Object o2, final boolean b2) {
        return this.forward.subSet(o2, b2, o, b).descendingSet();
    }
    
    @Override
    ImmutableSortedSet tailSetImpl(final Object o, final boolean b) {
        return this.forward.headSet(o, b).descendingSet();
    }
    
    @GwtIncompatible("NavigableSet")
    @Override
    public ImmutableSortedSet descendingSet() {
        return this.forward;
    }
    
    @GwtIncompatible("NavigableSet")
    @Override
    public UnmodifiableIterator descendingIterator() {
        return this.forward.iterator();
    }
    
    @GwtIncompatible("NavigableSet")
    @Override
    ImmutableSortedSet createDescendingSet() {
        throw new AssertionError((Object)"should never be called");
    }
    
    @Override
    public Object lower(final Object o) {
        return this.forward.higher(o);
    }
    
    @Override
    public Object floor(final Object o) {
        return this.forward.ceiling(o);
    }
    
    @Override
    public Object ceiling(final Object o) {
        return this.forward.floor(o);
    }
    
    @Override
    public Object higher(final Object o) {
        return this.forward.lower(o);
    }
    
    @Override
    int indexOf(@Nullable final Object o) {
        final int index = this.forward.indexOf(o);
        if (index == -1) {
            return index;
        }
        return this.size() - 1 - index;
    }
    
    @Override
    boolean isPartialView() {
        return this.forward.isPartialView();
    }
    
    @Override
    public Iterator descendingIterator() {
        return this.descendingIterator();
    }
    
    @Override
    public NavigableSet descendingSet() {
        return this.descendingSet();
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}
