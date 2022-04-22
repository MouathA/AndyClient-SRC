package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.*;
import java.io.*;
import java.util.*;

public interface ObjectSet extends ObjectCollection, Set
{
    ObjectIterator iterator();
    
    default ObjectSpliterator spliterator() {
        return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this), 65);
    }
    
    default ObjectSet of() {
        return ObjectSets.UNMODIFIABLE_EMPTY_SET;
    }
    
    default ObjectSet of(final Object o) {
        return ObjectSets.singleton(o);
    }
    
    default ObjectSet of(final Object o, final Object o2) {
        final ObjectArraySet set = new ObjectArraySet(2);
        set.add(o);
        if (!set.add(o2)) {
            throw new IllegalArgumentException("Duplicate element: " + o2);
        }
        return ObjectSets.unmodifiable(set);
    }
    
    default ObjectSet of(final Object o, final Object o2, final Object o3) {
        final ObjectArraySet set = new ObjectArraySet(3);
        set.add(o);
        if (!set.add(o2)) {
            throw new IllegalArgumentException("Duplicate element: " + o2);
        }
        if (!set.add(o3)) {
            throw new IllegalArgumentException("Duplicate element: " + o3);
        }
        return ObjectSets.unmodifiable(set);
    }
    
    @SafeVarargs
    default ObjectSet of(final Object... array) {
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
                final Serializable s = (array.length <= 4) ? new ObjectArraySet(array.length) : new ObjectOpenHashSet(array.length);
                while (0 < array.length) {
                    final Object o = array[0];
                    if (!((Collection<Object>)s).add(o)) {
                        throw new IllegalArgumentException("Duplicate element: " + o);
                    }
                    int n = 0;
                    ++n;
                }
                return ObjectSets.unmodifiable((ObjectSet)s);
            }
        }
    }
    
    default Spliterator spliterator() {
        return this.spliterator();
    }
    
    default Iterator iterator() {
        return this.iterator();
    }
}
