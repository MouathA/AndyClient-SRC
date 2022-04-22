package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible(serializable = true, emulated = true)
class RegularImmutableList extends ImmutableList
{
    private final transient int offset;
    private final transient int size;
    private final transient Object[] array;
    
    RegularImmutableList(final Object[] array, final int offset, final int size) {
        this.offset = offset;
        this.size = size;
        this.array = array;
    }
    
    RegularImmutableList(final Object[] array) {
        this(array, 0, array.length);
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    boolean isPartialView() {
        return this.size != this.array.length;
    }
    
    @Override
    int copyIntoArray(final Object[] array, final int n) {
        System.arraycopy(this.array, this.offset, array, n, this.size);
        return n + this.size;
    }
    
    @Override
    public Object get(final int n) {
        Preconditions.checkElementIndex(n, this.size);
        return this.array[n + this.offset];
    }
    
    @Override
    public int indexOf(@Nullable final Object o) {
        if (o == null) {
            return -1;
        }
        while (0 < this.size) {
            if (this.array[this.offset + 0].equals(o)) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    @Override
    public int lastIndexOf(@Nullable final Object o) {
        if (o == null) {
            return -1;
        }
        for (int i = this.size - 1; i >= 0; --i) {
            if (this.array[this.offset + i].equals(o)) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    ImmutableList subListUnchecked(final int n, final int n2) {
        return new RegularImmutableList(this.array, this.offset + n, n2 - n);
    }
    
    @Override
    public UnmodifiableListIterator listIterator(final int n) {
        return Iterators.forArray(this.array, this.offset, this.size, n);
    }
    
    @Override
    public ListIterator listIterator(final int n) {
        return this.listIterator(n);
    }
}
