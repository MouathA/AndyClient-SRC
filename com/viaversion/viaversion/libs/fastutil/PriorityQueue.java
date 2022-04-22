package com.viaversion.viaversion.libs.fastutil;

import java.util.*;

public interface PriorityQueue
{
    void enqueue(final Object p0);
    
    Object dequeue();
    
    default boolean isEmpty() {
        return this.size() == 0;
    }
    
    int size();
    
    void clear();
    
    Object first();
    
    default Object last() {
        throw new UnsupportedOperationException();
    }
    
    default void changed() {
        throw new UnsupportedOperationException();
    }
    
    Comparator comparator();
}
