package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.io.*;
import java.util.*;

@GwtCompatible(serializable = true, emulated = true)
public class TreeMultimap extends AbstractSortedKeySortedSetMultimap
{
    private transient Comparator keyComparator;
    private transient Comparator valueComparator;
    @GwtIncompatible("not needed in emulated source")
    private static final long serialVersionUID = 0L;
    
    public static TreeMultimap create() {
        return new TreeMultimap(Ordering.natural(), Ordering.natural());
    }
    
    public static TreeMultimap create(final Comparator comparator, final Comparator comparator2) {
        return new TreeMultimap((Comparator)Preconditions.checkNotNull(comparator), (Comparator)Preconditions.checkNotNull(comparator2));
    }
    
    public static TreeMultimap create(final Multimap multimap) {
        return new TreeMultimap(Ordering.natural(), Ordering.natural(), multimap);
    }
    
    TreeMultimap(final Comparator keyComparator, final Comparator valueComparator) {
        super(new TreeMap(keyComparator));
        this.keyComparator = keyComparator;
        this.valueComparator = valueComparator;
    }
    
    private TreeMultimap(final Comparator comparator, final Comparator comparator2, final Multimap multimap) {
        this(comparator, comparator2);
        this.putAll(multimap);
    }
    
    @Override
    SortedSet createCollection() {
        return new TreeSet(this.valueComparator);
    }
    
    @Override
    Collection createCollection(@Nullable final Object o) {
        if (o == null) {
            this.keyComparator().compare(o, o);
        }
        return super.createCollection(o);
    }
    
    public Comparator keyComparator() {
        return this.keyComparator;
    }
    
    @Override
    public Comparator valueComparator() {
        return this.valueComparator;
    }
    
    @GwtIncompatible("NavigableMap")
    @Override
    NavigableMap backingMap() {
        return (NavigableMap)super.backingMap();
    }
    
    @GwtIncompatible("NavigableSet")
    @Override
    public NavigableSet get(@Nullable final Object o) {
        return (NavigableSet)super.get(o);
    }
    
    @GwtIncompatible("NavigableSet")
    @Override
    Collection unmodifiableCollectionSubclass(final Collection collection) {
        return Sets.unmodifiableNavigableSet((NavigableSet)collection);
    }
    
    @GwtIncompatible("NavigableSet")
    @Override
    Collection wrapCollection(final Object o, final Collection collection) {
        return new WrappedNavigableSet(o, (NavigableSet)collection, null);
    }
    
    @GwtIncompatible("NavigableSet")
    @Override
    public NavigableSet keySet() {
        return (NavigableSet)super.keySet();
    }
    
    @GwtIncompatible("NavigableSet")
    @Override
    NavigableSet createKeySet() {
        return new NavigableKeySet(this.backingMap());
    }
    
    @GwtIncompatible("NavigableMap")
    @Override
    public NavigableMap asMap() {
        return (NavigableMap)super.asMap();
    }
    
    @GwtIncompatible("NavigableMap")
    @Override
    NavigableMap createAsMap() {
        return new NavigableAsMap(this.backingMap());
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.keyComparator());
        objectOutputStream.writeObject(this.valueComparator());
        Serialization.writeMultimap(this, objectOutputStream);
    }
    
    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.keyComparator = (Comparator)Preconditions.checkNotNull(objectInputStream.readObject());
        this.valueComparator = (Comparator)Preconditions.checkNotNull(objectInputStream.readObject());
        this.setMap(new TreeMap(this.keyComparator));
        Serialization.populateMultimap(this, objectInputStream);
    }
    
    @Override
    public SortedSet keySet() {
        return this.keySet();
    }
    
    @Override
    SortedMap backingMap() {
        return this.backingMap();
    }
    
    @Override
    public SortedMap asMap() {
        return this.asMap();
    }
    
    @Override
    public Collection values() {
        return super.values();
    }
    
    @Override
    public Map asMap() {
        return this.asMap();
    }
    
    @Override
    public SortedSet replaceValues(final Object o, final Iterable iterable) {
        return super.replaceValues(o, iterable);
    }
    
    @Override
    public SortedSet removeAll(final Object o) {
        return super.removeAll(o);
    }
    
    @Override
    public SortedSet get(final Object o) {
        return this.get(o);
    }
    
    @Override
    public Set get(final Object o) {
        return this.get(o);
    }
    
    @Override
    public Set keySet() {
        return this.keySet();
    }
    
    @Override
    public Collection get(final Object o) {
        return this.get(o);
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }
    
    @Override
    public boolean put(final Object o, final Object o2) {
        return super.put(o, o2);
    }
    
    @Override
    public Set entries() {
        return super.entries();
    }
    
    @Override
    Set createCollection() {
        return this.createCollection();
    }
    
    @Override
    Map createAsMap() {
        return this.createAsMap();
    }
    
    @Override
    Set createKeySet() {
        return this.createKeySet();
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
    Map backingMap() {
        return this.backingMap();
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
