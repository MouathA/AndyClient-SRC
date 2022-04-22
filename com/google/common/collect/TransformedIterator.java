package com.google.common.collect;

import java.util.*;
import com.google.common.annotations.*;
import com.google.common.base.*;

@GwtCompatible
abstract class TransformedIterator implements Iterator
{
    final Iterator backingIterator;
    
    TransformedIterator(final Iterator iterator) {
        this.backingIterator = (Iterator)Preconditions.checkNotNull(iterator);
    }
    
    abstract Object transform(final Object p0);
    
    @Override
    public final boolean hasNext() {
        return this.backingIterator.hasNext();
    }
    
    @Override
    public final Object next() {
        return this.transform(this.backingIterator.next());
    }
    
    @Override
    public final void remove() {
        this.backingIterator.remove();
    }
}
