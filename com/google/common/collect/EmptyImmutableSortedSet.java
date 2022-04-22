package com.google.common.collect;

import javax.annotation.*;
import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible(serializable = true, emulated = true)
class EmptyImmutableSortedSet extends ImmutableSortedSet
{
    EmptyImmutableSortedSet(final Comparator comparator) {
        super(comparator);
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
    public boolean contains(@Nullable final Object o) {
        return false;
    }
    
    @Override
    public boolean containsAll(final Collection collection) {
        return collection.isEmpty();
    }
    
    @Override
    public UnmodifiableIterator iterator() {
        return Iterators.emptyIterator();
    }
    
    @GwtIncompatible("NavigableSet")
    @Override
    public UnmodifiableIterator descendingIterator() {
        return Iterators.emptyIterator();
    }
    
    @Override
    boolean isPartialView() {
        return false;
    }
    
    @Override
    public ImmutableList asList() {
        return ImmutableList.of();
    }
    
    @Override
    int copyIntoArray(final Object[] array, final int n) {
        return n;
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o instanceof Set && ((Set)o).isEmpty();
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    @Override
    public String toString() {
        return "[]";
    }
    
    @Override
    public Object first() {
        throw new NoSuchElementException();
    }
    
    @Override
    public Object last() {
        throw new NoSuchElementException();
    }
    
    @Override
    ImmutableSortedSet headSetImpl(final Object o, final boolean b) {
        return this;
    }
    
    @Override
    ImmutableSortedSet subSetImpl(final Object o, final boolean b, final Object o2, final boolean b2) {
        return this;
    }
    
    @Override
    ImmutableSortedSet tailSetImpl(final Object o, final boolean b) {
        return this;
    }
    
    @Override
    int indexOf(@Nullable final Object o) {
        return -1;
    }
    
    @Override
    ImmutableSortedSet createDescendingSet() {
        return new EmptyImmutableSortedSet(Ordering.from(this.comparator).reverse());
    }
    
    @Override
    public Iterator descendingIterator() {
        return this.descendingIterator();
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}
