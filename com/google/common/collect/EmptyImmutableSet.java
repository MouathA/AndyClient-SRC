package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible(serializable = true, emulated = true)
final class EmptyImmutableSet extends ImmutableSet
{
    static final EmptyImmutableSet INSTANCE;
    private static final long serialVersionUID = 0L;
    
    private EmptyImmutableSet() {
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
    public boolean equals(@Nullable final Object o) {
        return o instanceof Set && ((Set)o).isEmpty();
    }
    
    @Override
    public final int hashCode() {
        return 0;
    }
    
    @Override
    boolean isHashCodeFast() {
        return true;
    }
    
    @Override
    public String toString() {
        return "[]";
    }
    
    Object readResolve() {
        return EmptyImmutableSet.INSTANCE;
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
    
    static {
        INSTANCE = new EmptyImmutableSet();
    }
}
