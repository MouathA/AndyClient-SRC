package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible(serializable = true, emulated = true)
final class SingletonImmutableList extends ImmutableList
{
    final transient Object element;
    
    SingletonImmutableList(final Object o) {
        this.element = Preconditions.checkNotNull(o);
    }
    
    @Override
    public Object get(final int n) {
        Preconditions.checkElementIndex(n, 1);
        return this.element;
    }
    
    @Override
    public int indexOf(@Nullable final Object o) {
        return this.element.equals(o) ? 0 : -1;
    }
    
    @Override
    public UnmodifiableIterator iterator() {
        return Iterators.singletonIterator(this.element);
    }
    
    @Override
    public int lastIndexOf(@Nullable final Object o) {
        return this.indexOf(o);
    }
    
    @Override
    public int size() {
        return 1;
    }
    
    @Override
    public ImmutableList subList(final int n, final int n2) {
        Preconditions.checkPositionIndexes(n, n2, 1);
        return (n == n2) ? ImmutableList.of() : this;
    }
    
    @Override
    public ImmutableList reverse() {
        return this;
    }
    
    @Override
    public boolean contains(@Nullable final Object o) {
        return this.element.equals(o);
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof List) {
            final List list = (List)o;
            return list.size() == 1 && this.element.equals(list.get(0));
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return 31 + this.element.hashCode();
    }
    
    @Override
    public String toString() {
        final String string = this.element.toString();
        return new StringBuilder(string.length() + 2).append('[').append(string).append(']').toString();
    }
    
    @Override
    public boolean isEmpty() {
        return false;
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
    public List subList(final int n, final int n2) {
        return this.subList(n, n2);
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}
