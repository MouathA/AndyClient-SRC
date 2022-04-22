package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.*;

public interface IntListIterator extends IntBidirectionalIterator, ListIterator
{
    default void set(final int n) {
        throw new UnsupportedOperationException();
    }
    
    default void add(final int n) {
        throw new UnsupportedOperationException();
    }
    
    default void remove() {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    default void set(final Integer n) {
        this.set((int)n);
    }
    
    @Deprecated
    default void add(final Integer n) {
        this.add((int)n);
    }
    
    @Deprecated
    default Integer next() {
        return super.next();
    }
    
    @Deprecated
    default Integer previous() {
        return super.previous();
    }
    
    @Deprecated
    default Object next() {
        return this.next();
    }
    
    @Deprecated
    default Object previous() {
        return this.previous();
    }
    
    @Deprecated
    default void add(final Object o) {
        this.add((Integer)o);
    }
    
    @Deprecated
    default void set(final Object o) {
        this.set((Integer)o);
    }
}
