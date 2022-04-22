package com.google.common.collect;

import com.google.common.annotations.*;
import java.io.*;
import java.util.*;

@GwtCompatible(serializable = true, emulated = true)
public final class HashMultiset extends AbstractMapBasedMultiset
{
    @GwtIncompatible("Not needed in emulated source.")
    private static final long serialVersionUID = 0L;
    
    public static HashMultiset create() {
        return new HashMultiset();
    }
    
    public static HashMultiset create(final int n) {
        return new HashMultiset(n);
    }
    
    public static HashMultiset create(final Iterable iterable) {
        final HashMultiset create = create(Multisets.inferDistinctElements(iterable));
        Iterables.addAll(create, iterable);
        return create;
    }
    
    private HashMultiset() {
        super(new HashMap());
    }
    
    private HashMultiset(final int n) {
        super(Maps.newHashMapWithExpectedSize(n));
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMultiset(this, objectOutputStream);
    }
    
    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        final int count = Serialization.readCount(objectInputStream);
        this.setBackingMap(Maps.newHashMapWithExpectedSize(count));
        Serialization.populateMultiset(this, objectInputStream, count);
    }
    
    @Override
    public int setCount(final Object o, final int n) {
        return super.setCount(o, n);
    }
    
    @Override
    public int remove(final Object o, final int n) {
        return super.remove(o, n);
    }
    
    @Override
    public int add(final Object o, final int n) {
        return super.add(o, n);
    }
    
    @Override
    public int count(final Object o) {
        return super.count(o);
    }
    
    @Override
    public Iterator iterator() {
        return super.iterator();
    }
    
    @Override
    public int size() {
        return super.size();
    }
    
    @Override
    public void clear() {
        super.clear();
    }
    
    @Override
    public Set entrySet() {
        return super.entrySet();
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }
    
    @Override
    public Set elementSet() {
        return super.elementSet();
    }
    
    @Override
    public boolean retainAll(final Collection collection) {
        return super.retainAll(collection);
    }
    
    @Override
    public boolean removeAll(final Collection collection) {
        return super.removeAll(collection);
    }
    
    @Override
    public boolean addAll(final Collection collection) {
        return super.addAll(collection);
    }
    
    @Override
    public boolean setCount(final Object o, final int n, final int n2) {
        return super.setCount(o, n, n2);
    }
    
    @Override
    public boolean remove(final Object o) {
        return super.remove(o);
    }
    
    @Override
    public boolean add(final Object o) {
        return super.add(o);
    }
    
    @Override
    public boolean contains(final Object o) {
        return super.contains(o);
    }
    
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
}
