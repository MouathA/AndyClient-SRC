package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.*;
import java.util.function.*;
import java.util.*;

public final class ObjectSets
{
    static final int ARRAY_SET_CUTOFF = 4;
    public static final EmptySet EMPTY_SET;
    static final ObjectSet UNMODIFIABLE_EMPTY_SET;
    
    private ObjectSets() {
    }
    
    public static ObjectSet emptySet() {
        return ObjectSets.EMPTY_SET;
    }
    
    public static ObjectSet singleton(final Object o) {
        return new Singleton(o);
    }
    
    public static ObjectSet synchronize(final ObjectSet set) {
        return (ObjectSet)new ObjectSets.SynchronizedSet(set);
    }
    
    public static ObjectSet synchronize(final ObjectSet set, final Object o) {
        return (ObjectSet)new ObjectSets.SynchronizedSet(set, o);
    }
    
    public static ObjectSet unmodifiable(final ObjectSet set) {
        return (ObjectSet)new ObjectSets.UnmodifiableSet(set);
    }
    
    static {
        EMPTY_SET = new EmptySet();
        UNMODIFIABLE_EMPTY_SET = unmodifiable(new ObjectArraySet(ObjectArrays.EMPTY_ARRAY));
    }
    
    public static class EmptySet extends ObjectCollections.EmptyCollection implements ObjectSet, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptySet() {
        }
        
        @Override
        public boolean remove(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        public Object clone() {
            return ObjectSets.EMPTY_SET;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof Set && ((Set)o).isEmpty();
        }
        
        private Object readResolve() {
            return ObjectSets.EMPTY_SET;
        }
    }
    
    public static class Singleton extends AbstractObjectSet implements Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Object element;
        
        protected Singleton(final Object element) {
            this.element = element;
        }
        
        @Override
        public boolean contains(final Object o) {
            return Objects.equals(o, this.element);
        }
        
        @Override
        public boolean remove(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ObjectListIterator iterator() {
            return ObjectIterators.singleton(this.element);
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return ObjectSpliterators.singleton(this.element);
        }
        
        @Override
        public int size() {
            return 1;
        }
        
        @Override
        public Object[] toArray() {
            return new Object[] { this.element };
        }
        
        @Override
        public void forEach(final Consumer consumer) {
            consumer.accept(this.element);
        }
        
        @Override
        public boolean addAll(final Collection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean removeIf(final Predicate predicate) {
            throw new UnsupportedOperationException();
        }
        
        public Object clone() {
            return this;
        }
        
        @Override
        public ObjectIterator iterator() {
            return this.iterator();
        }
        
        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }
}
