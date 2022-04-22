package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.*;
import java.io.*;
import java.util.*;

public interface IntSet extends IntCollection, Set
{
    IntIterator iterator();
    
    default IntSpliterator spliterator() {
        return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this), 321);
    }
    
    boolean remove(final int p0);
    
    @Deprecated
    default boolean remove(final Object o) {
        return super.remove(o);
    }
    
    @Deprecated
    default boolean add(final Integer n) {
        return super.add(n);
    }
    
    @Deprecated
    default boolean contains(final Object o) {
        return super.contains(o);
    }
    
    @Deprecated
    default boolean rem(final int n) {
        return this.remove(n);
    }
    
    default IntSet of() {
        return IntSets.UNMODIFIABLE_EMPTY_SET;
    }
    
    default IntSet of(final int n) {
        return IntSets.singleton(n);
    }
    
    default IntSet of(final int n, final int n2) {
        final IntArraySet set = new IntArraySet(2);
        set.add(n);
        if (!set.add(n2)) {
            throw new IllegalArgumentException("Duplicate element: " + n2);
        }
        return IntSets.unmodifiable(set);
    }
    
    default IntSet of(final int n, final int n2, final int n3) {
        final IntArraySet set = new IntArraySet(3);
        set.add(n);
        if (!set.add(n2)) {
            throw new IllegalArgumentException("Duplicate element: " + n2);
        }
        if (!set.add(n3)) {
            throw new IllegalArgumentException("Duplicate element: " + n3);
        }
        return IntSets.unmodifiable(set);
    }
    
    default IntSet of(final int... array) {
        switch (array.length) {
            case 0: {
                return of();
            }
            case 1: {
                return of(array[0]);
            }
            case 2: {
                return of(array[0], array[1]);
            }
            case 3: {
                return of(array[0], array[1], array[2]);
            }
            default: {
                final Serializable s = (array.length <= 4) ? new IntArraySet(array.length) : new IntOpenHashSet(array.length);
                while (0 < array.length) {
                    final int n = array[0];
                    if (!((IntCollection)s).add(n)) {
                        throw new IllegalArgumentException("Duplicate element: " + n);
                    }
                    int n2 = 0;
                    ++n2;
                }
                return IntSets.unmodifiable((IntSet)s);
            }
        }
    }
    
    default Spliterator spliterator() {
        return this.spliterator();
    }
    
    @Deprecated
    default boolean add(final Object o) {
        return this.add((Integer)o);
    }
    
    default Iterator iterator() {
        return this.iterator();
    }
}
