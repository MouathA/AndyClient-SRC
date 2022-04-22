package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.io.*;
import java.util.*;

@GwtCompatible(emulated = true)
public final class EnumMultiset extends AbstractMapBasedMultiset
{
    private transient Class type;
    @GwtIncompatible("Not needed in emulated source")
    private static final long serialVersionUID = 0L;
    
    public static EnumMultiset create(final Class clazz) {
        return new EnumMultiset(clazz);
    }
    
    public static EnumMultiset create(final Iterable iterable) {
        final Iterator<Enum> iterator = iterable.iterator();
        Preconditions.checkArgument(iterator.hasNext(), (Object)"EnumMultiset constructor passed empty Iterable");
        final EnumMultiset enumMultiset = new EnumMultiset(iterator.next().getDeclaringClass());
        Iterables.addAll(enumMultiset, iterable);
        return enumMultiset;
    }
    
    public static EnumMultiset create(final Iterable iterable, final Class clazz) {
        final EnumMultiset create = create(clazz);
        Iterables.addAll(create, iterable);
        return create;
    }
    
    private EnumMultiset(final Class type) {
        super(WellBehavedMap.wrap(new EnumMap(type)));
        this.type = type;
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.type);
        Serialization.writeMultiset(this, objectOutputStream);
    }
    
    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.type = (Class)objectInputStream.readObject();
        this.setBackingMap(WellBehavedMap.wrap(new EnumMap(this.type)));
        Serialization.populateMultiset(this, objectInputStream);
    }
    
    @Override
    public int remove(final Object o, final int n) {
        return super.remove(o, n);
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
    public boolean remove(final Object o) {
        return super.remove(o);
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
