package org.apache.logging.log4j.spi;

import java.util.*;
import org.apache.logging.log4j.*;

public class MutableThreadContextStack implements ThreadContextStack
{
    private static final long serialVersionUID = 50505011L;
    private final List list;
    
    public MutableThreadContextStack(final List list) {
        this.list = new ArrayList(list);
    }
    
    private MutableThreadContextStack(final MutableThreadContextStack mutableThreadContextStack) {
        this.list = new ArrayList(mutableThreadContextStack.list);
    }
    
    @Override
    public String pop() {
        if (this.list.isEmpty()) {
            return null;
        }
        return this.list.remove(this.list.size() - 1);
    }
    
    @Override
    public String peek() {
        if (this.list.isEmpty()) {
            return null;
        }
        return this.list.get(this.list.size() - 1);
    }
    
    @Override
    public void push(final String s) {
        this.list.add(s);
    }
    
    @Override
    public int getDepth() {
        return this.list.size();
    }
    
    @Override
    public List asList() {
        return this.list;
    }
    
    @Override
    public void trim(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Maximum stack depth cannot be negative");
        }
        if (this.list == null) {
            return;
        }
        final ArrayList<Object> list = new ArrayList<Object>(this.list.size());
        while (0 < Math.min(n, this.list.size())) {
            list.add(this.list.get(0));
            int n2 = 0;
            ++n2;
        }
        this.list.clear();
        this.list.addAll(list);
    }
    
    @Override
    public ThreadContextStack copy() {
        return new MutableThreadContextStack(this);
    }
    
    @Override
    public void clear() {
        this.list.clear();
    }
    
    @Override
    public int size() {
        return this.list.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.list.contains(o);
    }
    
    @Override
    public Iterator iterator() {
        return this.list.iterator();
    }
    
    @Override
    public Object[] toArray() {
        return this.list.toArray();
    }
    
    @Override
    public Object[] toArray(final Object[] array) {
        return this.list.toArray(array);
    }
    
    public boolean add(final String s) {
        return this.list.add(s);
    }
    
    @Override
    public boolean remove(final Object o) {
        return this.list.remove(o);
    }
    
    @Override
    public boolean containsAll(final Collection collection) {
        return this.list.containsAll(collection);
    }
    
    @Override
    public boolean addAll(final Collection collection) {
        return this.list.addAll(collection);
    }
    
    @Override
    public boolean removeAll(final Collection collection) {
        return this.list.removeAll(collection);
    }
    
    @Override
    public boolean retainAll(final Collection collection) {
        return this.list.retainAll(collection);
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.list);
    }
    
    @Override
    public ThreadContext.ContextStack copy() {
        return this.copy();
    }
    
    @Override
    public boolean add(final Object o) {
        return this.add((String)o);
    }
}
