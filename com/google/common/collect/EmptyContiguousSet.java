package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;
import java.io.*;

@GwtCompatible(emulated = true)
final class EmptyContiguousSet extends ContiguousSet
{
    EmptyContiguousSet(final DiscreteDomain discreteDomain) {
        super(discreteDomain);
    }
    
    @Override
    public Comparable first() {
        throw new NoSuchElementException();
    }
    
    @Override
    public Comparable last() {
        throw new NoSuchElementException();
    }
    
    @Override
    public int size() {
        return 0;
    }
    
    @Override
    public ContiguousSet intersection(final ContiguousSet set) {
        return this;
    }
    
    @Override
    public Range range() {
        throw new NoSuchElementException();
    }
    
    @Override
    public Range range(final BoundType boundType, final BoundType boundType2) {
        throw new NoSuchElementException();
    }
    
    @Override
    ContiguousSet headSetImpl(final Comparable comparable, final boolean b) {
        return this;
    }
    
    @Override
    ContiguousSet subSetImpl(final Comparable comparable, final boolean b, final Comparable comparable2, final boolean b2) {
        return this;
    }
    
    @Override
    ContiguousSet tailSetImpl(final Comparable comparable, final boolean b) {
        return this;
    }
    
    @GwtIncompatible("not used by GWT emulation")
    @Override
    int indexOf(final Object o) {
        return -1;
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
    public boolean isEmpty() {
        return true;
    }
    
    @Override
    public ImmutableList asList() {
        return ImmutableList.of();
    }
    
    @Override
    public String toString() {
        return "[]";
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o instanceof Set && ((Set)o).isEmpty();
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    @GwtIncompatible("serialization")
    @Override
    Object writeReplace() {
        return new SerializedForm(this.domain, null);
    }
    
    @GwtIncompatible("NavigableSet")
    @Override
    ImmutableSortedSet createDescendingSet() {
        return new EmptyImmutableSortedSet(Ordering.natural().reverse());
    }
    
    @Override
    public Object last() {
        return this.last();
    }
    
    @Override
    public Object first() {
        return this.first();
    }
    
    @Override
    ImmutableSortedSet tailSetImpl(final Object o, final boolean b) {
        return this.tailSetImpl((Comparable)o, b);
    }
    
    @Override
    ImmutableSortedSet subSetImpl(final Object o, final boolean b, final Object o2, final boolean b2) {
        return this.subSetImpl((Comparable)o, b, (Comparable)o2, b2);
    }
    
    @Override
    ImmutableSortedSet headSetImpl(final Object o, final boolean b) {
        return this.headSetImpl((Comparable)o, b);
    }
    
    @Override
    public Iterator descendingIterator() {
        return this.descendingIterator();
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
    
    @GwtIncompatible("serialization")
    private static final class SerializedForm implements Serializable
    {
        private final DiscreteDomain domain;
        private static final long serialVersionUID = 0L;
        
        private SerializedForm(final DiscreteDomain domain) {
            this.domain = domain;
        }
        
        private Object readResolve() {
            return new EmptyContiguousSet(this.domain);
        }
        
        SerializedForm(final DiscreteDomain discreteDomain, final EmptyContiguousSet$1 object) {
            this(discreteDomain);
        }
    }
}
