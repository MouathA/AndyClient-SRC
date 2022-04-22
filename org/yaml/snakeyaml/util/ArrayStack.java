package org.yaml.snakeyaml.util;

import java.util.*;

public class ArrayStack
{
    private ArrayList stack;
    
    public ArrayStack(final int n) {
        this.stack = new ArrayList(n);
    }
    
    public void push(final Object o) {
        this.stack.add(o);
    }
    
    public Object pop() {
        return this.stack.remove(this.stack.size() - 1);
    }
    
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }
    
    public void clear() {
        this.stack.clear();
    }
}
