package com.viaversion.viaversion.libs.fastutil;

public interface Stack
{
    void push(final Object p0);
    
    Object pop();
    
    boolean isEmpty();
    
    default Object top() {
        return this.peek(0);
    }
    
    default Object peek(final int n) {
        throw new UnsupportedOperationException();
    }
}
