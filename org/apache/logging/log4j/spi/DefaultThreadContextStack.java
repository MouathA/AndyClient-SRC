package org.apache.logging.log4j.spi;

import java.util.*;
import org.apache.logging.log4j.*;

public class DefaultThreadContextStack implements ThreadContextStack
{
    private static final long serialVersionUID = 5050501L;
    private static ThreadLocal stack;
    private final boolean useStack;
    
    public DefaultThreadContextStack(final boolean useStack) {
        this.useStack = useStack;
    }
    
    @Override
    public String pop() {
        if (!this.useStack) {
            return "";
        }
        final List<? extends String> list = DefaultThreadContextStack.stack.get();
        if (list == null || list.size() == 0) {
            throw new NoSuchElementException("The ThreadContext stack is empty");
        }
        final ArrayList list2 = new ArrayList<String>(list);
        final String s = list2.remove(list2.size() - 1);
        DefaultThreadContextStack.stack.set(Collections.unmodifiableList((List<?>)list2));
        return s;
    }
    
    @Override
    public String peek() {
        final List<String> list = DefaultThreadContextStack.stack.get();
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(list.size() - 1);
    }
    
    @Override
    public void push(final String s) {
        if (!this.useStack) {
            return;
        }
        this.add(s);
    }
    
    @Override
    public int getDepth() {
        final List list = DefaultThreadContextStack.stack.get();
        return (list == null) ? 0 : list.size();
    }
    
    @Override
    public List asList() {
        final List list = DefaultThreadContextStack.stack.get();
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }
    
    @Override
    public void trim(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Maximum stack depth cannot be negative");
        }
        final List<Object> list = DefaultThreadContextStack.stack.get();
        if (list == null) {
            return;
        }
        final ArrayList<Object> list2 = new ArrayList<Object>();
        while (0 < Math.min(n, list.size())) {
            list2.add(list.get(0));
            int n2 = 0;
            ++n2;
        }
        DefaultThreadContextStack.stack.set(list2);
    }
    
    @Override
    public ThreadContextStack copy() {
        final List list;
        if (!this.useStack || (list = DefaultThreadContextStack.stack.get()) == null) {
            return new MutableThreadContextStack(new ArrayList());
        }
        return new MutableThreadContextStack(list);
    }
    
    @Override
    public void clear() {
        DefaultThreadContextStack.stack.remove();
    }
    
    @Override
    public int size() {
        final List list = DefaultThreadContextStack.stack.get();
        return (list == null) ? 0 : list.size();
    }
    
    @Override
    public boolean isEmpty() {
        final List list = DefaultThreadContextStack.stack.get();
        return list == null || list.isEmpty();
    }
    
    @Override
    public boolean contains(final Object o) {
        final List list = DefaultThreadContextStack.stack.get();
        return list != null && list.contains(o);
    }
    
    @Override
    public Iterator iterator() {
        final List list = DefaultThreadContextStack.stack.get();
        if (list == null) {
            return Collections.emptyList().iterator();
        }
        return list.iterator();
    }
    
    @Override
    public Object[] toArray() {
        final List list = DefaultThreadContextStack.stack.get();
        if (list == null) {
            return new String[0];
        }
        return list.toArray(new Object[list.size()]);
    }
    
    @Override
    public Object[] toArray(final Object[] array) {
        final List list = DefaultThreadContextStack.stack.get();
        if (list == null) {
            if (array.length > 0) {
                array[0] = null;
            }
            return array;
        }
        return list.toArray(array);
    }
    
    public boolean add(final String s) {
        if (!this.useStack) {
            return false;
        }
        final List<? extends T> list = DefaultThreadContextStack.stack.get();
        final ArrayList list2 = (list == null) ? new ArrayList<String>() : new ArrayList<String>(list);
        list2.add(s);
        DefaultThreadContextStack.stack.set(Collections.unmodifiableList((List<?>)list2));
        return true;
    }
    
    @Override
    public boolean remove(final Object o) {
        if (!this.useStack) {
            return false;
        }
        final List<? extends T> list = DefaultThreadContextStack.stack.get();
        if (list == null || list.size() == 0) {
            return false;
        }
        final ArrayList list2 = new ArrayList<Object>(list);
        final boolean remove = list2.remove(o);
        DefaultThreadContextStack.stack.set(Collections.unmodifiableList((List<?>)list2));
        return remove;
    }
    
    @Override
    public boolean containsAll(final Collection collection) {
        if (collection.isEmpty()) {
            return true;
        }
        final List list = DefaultThreadContextStack.stack.get();
        return list != null && list.containsAll(collection);
    }
    
    @Override
    public boolean addAll(final Collection collection) {
        if (!this.useStack || collection.isEmpty()) {
            return false;
        }
        final List<?> list = DefaultThreadContextStack.stack.get();
        final ArrayList list2 = (list == null) ? new ArrayList<Object>() : new ArrayList<Object>((Collection<? extends T>)list);
        list2.addAll(collection);
        DefaultThreadContextStack.stack.set(Collections.unmodifiableList((List<?>)list2));
        return true;
    }
    
    @Override
    public boolean removeAll(final Collection collection) {
        if (!this.useStack || collection.isEmpty()) {
            return false;
        }
        final List<? extends T> list = DefaultThreadContextStack.stack.get();
        if (list == null || list.isEmpty()) {
            return false;
        }
        final ArrayList list2 = new ArrayList<Object>(list);
        final boolean removeAll = list2.removeAll(collection);
        DefaultThreadContextStack.stack.set(Collections.unmodifiableList((List<?>)list2));
        return removeAll;
    }
    
    @Override
    public boolean retainAll(final Collection collection) {
        if (!this.useStack || collection.isEmpty()) {
            return false;
        }
        final List<? extends T> list = DefaultThreadContextStack.stack.get();
        if (list == null || list.isEmpty()) {
            return false;
        }
        final ArrayList list2 = new ArrayList<Object>(list);
        final boolean retainAll = list2.retainAll(collection);
        DefaultThreadContextStack.stack.set(Collections.unmodifiableList((List<?>)list2));
        return retainAll;
    }
    
    @Override
    public String toString() {
        final List list = DefaultThreadContextStack.stack.get();
        return (list == null) ? "[]" : list.toString();
    }
    
    @Override
    public ThreadContext.ContextStack copy() {
        return this.copy();
    }
    
    @Override
    public boolean add(final Object o) {
        return this.add((String)o);
    }
    
    static {
        DefaultThreadContextStack.stack = new ThreadLocal();
    }
}
