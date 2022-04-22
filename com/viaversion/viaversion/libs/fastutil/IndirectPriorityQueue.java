package com.viaversion.viaversion.libs.fastutil;

import java.util.*;

public interface IndirectPriorityQueue
{
    void enqueue(final int p0);
    
    int dequeue();
    
    default boolean isEmpty() {
        return this.size() == 0;
    }
    
    int size();
    
    void clear();
    
    int first();
    
    default int last() {
        throw new UnsupportedOperationException();
    }
    
    default void changed() {
        this.changed(this.first());
    }
    
    Comparator comparator();
    
    default void changed(final int n) {
        throw new UnsupportedOperationException();
    }
    
    default void allChanged() {
        throw new UnsupportedOperationException();
    }
    
    default boolean contains(final int n) {
        throw new UnsupportedOperationException();
    }
    
    default boolean remove(final int n) {
        throw new UnsupportedOperationException();
    }
    
    default int front(final int[] array) {
        throw new UnsupportedOperationException();
    }
}
