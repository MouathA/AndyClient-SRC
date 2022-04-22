package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;

@GwtCompatible
abstract class AbstractIndexedListIterator extends UnmodifiableListIterator
{
    private final int size;
    private int position;
    
    protected abstract Object get(final int p0);
    
    protected AbstractIndexedListIterator(final int n) {
        this(n, 0);
    }
    
    protected AbstractIndexedListIterator(final int size, final int position) {
        Preconditions.checkPositionIndex(position, size);
        this.size = size;
        this.position = position;
    }
    
    @Override
    public final boolean hasNext() {
        return this.position < this.size;
    }
    
    @Override
    public final Object next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return this.get(this.position++);
    }
    
    @Override
    public final int nextIndex() {
        return this.position;
    }
    
    @Override
    public final boolean hasPrevious() {
        return this.position > 0;
    }
    
    @Override
    public final Object previous() {
        if (!this.hasPrevious()) {
            throw new NoSuchElementException();
        }
        final int position = this.position - 1;
        this.position = position;
        return this.get(position);
    }
    
    @Override
    public final int previousIndex() {
        return this.position - 1;
    }
}
