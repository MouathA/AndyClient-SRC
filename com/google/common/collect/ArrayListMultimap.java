package com.google.common.collect;

import com.google.common.annotations.*;
import java.io.*;
import java.util.*;

@GwtCompatible(serializable = true, emulated = true)
public final class ArrayListMultimap extends AbstractListMultimap
{
    private static final int DEFAULT_VALUES_PER_KEY = 3;
    @VisibleForTesting
    transient int expectedValuesPerKey;
    @GwtIncompatible("Not needed in emulated source.")
    private static final long serialVersionUID = 0L;
    
    public static ArrayListMultimap create() {
        return new ArrayListMultimap();
    }
    
    public static ArrayListMultimap create(final int n, final int n2) {
        return new ArrayListMultimap(n, n2);
    }
    
    public static ArrayListMultimap create(final Multimap multimap) {
        return new ArrayListMultimap(multimap);
    }
    
    private ArrayListMultimap() {
        super(new HashMap());
        this.expectedValuesPerKey = 3;
    }
    
    private ArrayListMultimap(final int n, final int expectedValuesPerKey) {
        super(Maps.newHashMapWithExpectedSize(n));
        CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
        this.expectedValuesPerKey = expectedValuesPerKey;
    }
    
    private ArrayListMultimap(final Multimap multimap) {
        this(multimap.keySet().size(), (multimap instanceof ArrayListMultimap) ? ((ArrayListMultimap)multimap).expectedValuesPerKey : 3);
        this.putAll(multimap);
    }
    
    @Override
    List createCollection() {
        return new ArrayList(this.expectedValuesPerKey);
    }
    
    public void trimToSize() {
        final Iterator<Collection> iterator = this.backingMap().values().iterator();
        while (iterator.hasNext()) {
            ((ArrayList)iterator.next()).trimToSize();
        }
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.expectedValuesPerKey);
        Serialization.writeMultimap(this, objectOutputStream);
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.expectedValuesPerKey = objectInputStream.readInt();
        final int count = Serialization.readCount(objectInputStream);
        this.setMap(Maps.newHashMapWithExpectedSize(count));
        Serialization.populateMultimap(this, objectInputStream, count);
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }
    
    @Override
    public Map asMap() {
        return super.asMap();
    }
    
    @Override
    public boolean put(final Object o, final Object o2) {
        return super.put(o, o2);
    }
    
    @Override
    public List replaceValues(final Object o, final Iterable iterable) {
        return super.replaceValues(o, iterable);
    }
    
    @Override
    public List removeAll(final Object o) {
        return super.removeAll(o);
    }
    
    @Override
    public List get(final Object o) {
        return super.get(o);
    }
    
    @Override
    public Collection entries() {
        return super.entries();
    }
    
    @Override
    public Collection values() {
        return super.values();
    }
    
    @Override
    public void clear() {
        super.clear();
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return super.containsKey(o);
    }
    
    @Override
    public int size() {
        return super.size();
    }
    
    @Override
    Collection createCollection() {
        return this.createCollection();
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
    public Multiset keys() {
        return super.keys();
    }
    
    @Override
    public Set keySet() {
        return super.keySet();
    }
    
    @Override
    public boolean putAll(final Multimap multimap) {
        return super.putAll(multimap);
    }
    
    @Override
    public boolean putAll(final Object o, final Iterable iterable) {
        return super.putAll(o, iterable);
    }
    
    @Override
    public boolean remove(final Object o, final Object o2) {
        return super.remove(o, o2);
    }
    
    @Override
    public boolean containsEntry(final Object o, final Object o2) {
        return super.containsEntry(o, o2);
    }
    
    @Override
    public boolean containsValue(final Object o) {
        return super.containsValue(o);
    }
    
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
}
