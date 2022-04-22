package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible(serializable = true, emulated = true)
final class SingletonImmutableSet extends ImmutableSet
{
    final transient Object element;
    private transient int cachedHashCode;
    
    SingletonImmutableSet(final Object o) {
        this.element = Preconditions.checkNotNull(o);
    }
    
    SingletonImmutableSet(final Object element, final int cachedHashCode) {
        this.element = element;
        this.cachedHashCode = cachedHashCode;
    }
    
    @Override
    public int size() {
        return 1;
    }
    
    @Override
    public boolean isEmpty() {
        return false;
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.element.equals(o);
    }
    
    @Override
    public UnmodifiableIterator iterator() {
        return Iterators.singletonIterator(this.element);
    }
    
    @Override
    boolean isPartialView() {
        return false;
    }
    
    @Override
    int copyIntoArray(final Object[] array, final int n) {
        array[n] = this.element;
        return n + 1;
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Set) {
            final Set set = (Set)o;
            return set.size() == 1 && this.element.equals(set.iterator().next());
        }
        return false;
    }
    
    @Override
    public final int hashCode() {
        int cachedHashCode = this.cachedHashCode;
        if (cachedHashCode == 0) {
            cachedHashCode = (this.cachedHashCode = this.element.hashCode());
        }
        return cachedHashCode;
    }
    
    @Override
    boolean isHashCodeFast() {
        return this.cachedHashCode != 0;
    }
    
    @Override
    public String toString() {
        final String string = this.element.toString();
        return new StringBuilder(string.length() + 2).append('[').append(string).append(']').toString();
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}
