package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
abstract class TransformedListIterator extends TransformedIterator implements ListIterator
{
    TransformedListIterator(final ListIterator listIterator) {
        super(listIterator);
    }
    
    private ListIterator backingIterator() {
        return Iterators.cast(this.backingIterator);
    }
    
    @Override
    public final boolean hasPrevious() {
        return this.backingIterator().hasPrevious();
    }
    
    @Override
    public final Object previous() {
        return this.transform(this.backingIterator().previous());
    }
    
    @Override
    public final int nextIndex() {
        return this.backingIterator().nextIndex();
    }
    
    @Override
    public final int previousIndex() {
        return this.backingIterator().previousIndex();
    }
    
    @Override
    public void set(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void add(final Object o) {
        throw new UnsupportedOperationException();
    }
}
