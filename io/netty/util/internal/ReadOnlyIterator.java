package io.netty.util.internal;

import java.util.*;

public final class ReadOnlyIterator implements Iterator
{
    private final Iterator iterator;
    
    public ReadOnlyIterator(final Iterator iterator) {
        if (iterator == null) {
            throw new NullPointerException("iterator");
        }
        this.iterator = iterator;
    }
    
    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }
    
    @Override
    public Object next() {
        return this.iterator.next();
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("read-only");
    }
}
