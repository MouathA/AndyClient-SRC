package io.netty.channel.group;

import java.util.*;

final class CombinedIterator implements Iterator
{
    private final Iterator i1;
    private final Iterator i2;
    private Iterator currentIterator;
    
    CombinedIterator(final Iterator iterator, final Iterator i2) {
        if (iterator == null) {
            throw new NullPointerException("i1");
        }
        if (i2 == null) {
            throw new NullPointerException("i2");
        }
        this.i1 = iterator;
        this.i2 = i2;
        this.currentIterator = iterator;
    }
    
    @Override
    public boolean hasNext() {
        while (!this.currentIterator.hasNext()) {
            if (this.currentIterator != this.i1) {
                return false;
            }
            this.currentIterator = this.i2;
        }
        return true;
    }
    
    @Override
    public Object next() {
        return this.currentIterator.next();
    }
    
    @Override
    public void remove() {
        this.currentIterator.remove();
    }
}
