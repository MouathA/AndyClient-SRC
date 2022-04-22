package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.*;

public interface IntStack extends Stack
{
    void push(final int p0);
    
    int popInt();
    
    int topInt();
    
    int peekInt(final int p0);
    
    @Deprecated
    default void push(final Integer n) {
        this.push((int)n);
    }
    
    @Deprecated
    default Integer pop() {
        return this.popInt();
    }
    
    @Deprecated
    default Integer top() {
        return this.topInt();
    }
    
    @Deprecated
    default Integer peek(final int n) {
        return this.peekInt(n);
    }
    
    @Deprecated
    default Object peek(final int n) {
        return this.peek(n);
    }
    
    @Deprecated
    default Object top() {
        return this.top();
    }
    
    @Deprecated
    default Object pop() {
        return this.pop();
    }
    
    @Deprecated
    default void push(final Object o) {
        this.push((Integer)o);
    }
}
