package com.viaversion.viaversion.libs.fastutil;

public interface BigListIterator extends BidirectionalIterator
{
    long nextIndex();
    
    long previousIndex();
    
    default void set(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    default void add(final Object o) {
        throw new UnsupportedOperationException();
    }
}
