package com.viaversion.viaversion.util;

import java.util.*;
import java.util.function.*;

public final class SynchronizedListWrapper implements List
{
    private final List list;
    private final Consumer addHandler;
    
    public SynchronizedListWrapper(final List list, final Consumer addHandler) {
        this.list = list;
        this.addHandler = addHandler;
    }
    
    public List originalList() {
        return this.list;
    }
    
    private void handleAdd(final Object o) {
        this.addHandler.accept(o);
    }
    
    @Override
    public int size() {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.size();
    }
    
    @Override
    public boolean isEmpty() {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.isEmpty();
    }
    
    @Override
    public boolean contains(final Object o) {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.contains(o);
    }
    
    @Override
    public Iterator iterator() {
        return this.listIterator();
    }
    
    @Override
    public Object[] toArray() {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.toArray();
    }
    
    @Override
    public boolean add(final Object o) {
        // monitorenter(this)
        this.handleAdd(o);
        // monitorexit(this)
        return this.list.add(o);
    }
    
    @Override
    public boolean remove(final Object o) {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.remove(o);
    }
    
    @Override
    public boolean addAll(final Collection collection) {
        // monitorenter(this)
        final Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            this.handleAdd(iterator.next());
        }
        // monitorexit(this)
        return this.list.addAll(collection);
    }
    
    @Override
    public boolean addAll(final int n, final Collection collection) {
        // monitorenter(this)
        final Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            this.handleAdd(iterator.next());
        }
        // monitorexit(this)
        return this.list.addAll(n, collection);
    }
    
    @Override
    public void clear() {
        // monitorenter(this)
        this.list.clear();
    }
    // monitorexit(this)
    
    @Override
    public Object get(final int n) {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.get(n);
    }
    
    @Override
    public Object set(final int n, final Object o) {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.set(n, o);
    }
    
    @Override
    public void add(final int n, final Object o) {
        // monitorenter(this)
        this.list.add(n, o);
    }
    // monitorexit(this)
    
    @Override
    public Object remove(final int n) {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.remove(n);
    }
    
    @Override
    public int indexOf(final Object o) {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.indexOf(o);
    }
    
    @Override
    public int lastIndexOf(final Object o) {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.lastIndexOf(o);
    }
    
    @Override
    public ListIterator listIterator() {
        return this.list.listIterator();
    }
    
    @Override
    public ListIterator listIterator(final int n) {
        return this.list.listIterator(n);
    }
    
    @Override
    public List subList(final int n, final int n2) {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.subList(n, n2);
    }
    
    @Override
    public boolean retainAll(final Collection collection) {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.retainAll(collection);
    }
    
    @Override
    public boolean removeAll(final Collection collection) {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.removeAll(collection);
    }
    
    @Override
    public boolean containsAll(final Collection collection) {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.containsAll(collection);
    }
    
    @Override
    public Object[] toArray(final Object[] array) {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.toArray(array);
    }
    
    @Override
    public void sort(final Comparator comparator) {
        // monitorenter(this)
        this.list.sort(comparator);
    }
    // monitorexit(this)
    
    @Override
    public void forEach(final Consumer consumer) {
        // monitorenter(this)
        this.list.forEach(consumer);
    }
    // monitorexit(this)
    
    @Override
    public boolean removeIf(final Predicate predicate) {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.removeIf(predicate);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        // monitorenter(this)
        // monitorexit(this)
        return this.list.equals(o);
    }
    
    @Override
    public int hashCode() {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.hashCode();
    }
    
    @Override
    public String toString() {
        // monitorenter(this)
        // monitorexit(this)
        return this.list.toString();
    }
}
