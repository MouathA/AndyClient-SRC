package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible(serializable = true, emulated = true)
final class RegularImmutableSet extends ImmutableSet
{
    private final Object[] elements;
    @VisibleForTesting
    final transient Object[] table;
    private final transient int mask;
    private final transient int hashCode;
    
    RegularImmutableSet(final Object[] elements, final int hashCode, final Object[] table, final int mask) {
        this.elements = elements;
        this.table = table;
        this.mask = mask;
        this.hashCode = hashCode;
    }
    
    @Override
    public boolean contains(final Object o) {
        if (o == null) {
            return false;
        }
        int smear = Hashing.smear(o.hashCode());
        while (true) {
            final Object o2 = this.table[smear & this.mask];
            if (o2 == null) {
                return false;
            }
            if (o2.equals(o)) {
                return true;
            }
            ++smear;
        }
    }
    
    @Override
    public int size() {
        return this.elements.length;
    }
    
    @Override
    public UnmodifiableIterator iterator() {
        return Iterators.forArray(this.elements);
    }
    
    @Override
    int copyIntoArray(final Object[] array, final int n) {
        System.arraycopy(this.elements, 0, array, n, this.elements.length);
        return n + this.elements.length;
    }
    
    @Override
    ImmutableList createAsList() {
        return new RegularImmutableAsList(this, this.elements);
    }
    
    @Override
    boolean isPartialView() {
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.hashCode;
    }
    
    @Override
    boolean isHashCodeFast() {
        return true;
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}
