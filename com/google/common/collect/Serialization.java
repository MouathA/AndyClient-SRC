package com.google.common.collect;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;

final class Serialization
{
    private Serialization() {
    }
    
    static int readCount(final ObjectInputStream objectInputStream) throws IOException {
        return objectInputStream.readInt();
    }
    
    static void writeMap(final Map map, final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(map.size());
        for (final Map.Entry<Object, V> entry : map.entrySet()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }
    
    static void populateMap(final Map map, final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        populateMap(map, objectInputStream, objectInputStream.readInt());
    }
    
    static void populateMap(final Map map, final ObjectInputStream objectInputStream, final int n) throws IOException, ClassNotFoundException {
        while (0 < n) {
            map.put(objectInputStream.readObject(), objectInputStream.readObject());
            int n2 = 0;
            ++n2;
        }
    }
    
    static void writeMultiset(final Multiset multiset, final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(multiset.entrySet().size());
        for (final Multiset.Entry entry : multiset.entrySet()) {
            objectOutputStream.writeObject(entry.getElement());
            objectOutputStream.writeInt(entry.getCount());
        }
    }
    
    static void populateMultiset(final Multiset multiset, final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        populateMultiset(multiset, objectInputStream, objectInputStream.readInt());
    }
    
    static void populateMultiset(final Multiset multiset, final ObjectInputStream objectInputStream, final int n) throws IOException, ClassNotFoundException {
        while (0 < n) {
            multiset.add(objectInputStream.readObject(), objectInputStream.readInt());
            int n2 = 0;
            ++n2;
        }
    }
    
    static void writeMultimap(final Multimap multimap, final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(multimap.asMap().size());
        for (final Map.Entry<Object, V> entry : multimap.asMap().entrySet()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeInt(((Collection)entry.getValue()).size());
            final Iterator iterator2 = ((Collection)entry.getValue()).iterator();
            while (iterator2.hasNext()) {
                objectOutputStream.writeObject(iterator2.next());
            }
        }
    }
    
    static void populateMultimap(final Multimap multimap, final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        populateMultimap(multimap, objectInputStream, objectInputStream.readInt());
    }
    
    static void populateMultimap(final Multimap multimap, final ObjectInputStream objectInputStream, final int n) throws IOException, ClassNotFoundException {
        while (0 < n) {
            final Collection value = multimap.get(objectInputStream.readObject());
            while (0 < objectInputStream.readInt()) {
                value.add(objectInputStream.readObject());
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    static FieldSetter getFieldSetter(final Class clazz, final String s) {
        return new FieldSetter(clazz.getDeclaredField(s), null);
    }
    
    static final class FieldSetter
    {
        private final Field field;
        
        private FieldSetter(final Field field) {
            (this.field = field).setAccessible(true);
        }
        
        void set(final Object o, final Object o2) {
            this.field.set(o, o2);
        }
        
        void set(final Object o, final int n) {
            this.field.set(o, n);
        }
        
        FieldSetter(final Field field, final Serialization$1 object) {
            this(field);
        }
    }
}
