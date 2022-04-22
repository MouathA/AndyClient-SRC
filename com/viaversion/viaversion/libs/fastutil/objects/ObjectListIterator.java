package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;

public interface ObjectListIterator extends ObjectBidirectionalIterator, ListIterator
{
    default void set(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    default void add(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    default void remove() {
        throw new UnsupportedOperationException();
    }
}
