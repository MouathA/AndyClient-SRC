package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.*;
import java.util.*;

public final class ObjectSortedSets
{
    public static final EmptySet EMPTY_SET;
    
    private ObjectSortedSets() {
    }
    
    public static ObjectSet emptySet() {
        return ObjectSortedSets.EMPTY_SET;
    }
    
    public static ObjectSortedSet singleton(final Object o) {
        return new Singleton(o);
    }
    
    public static ObjectSortedSet singleton(final Object o, final Comparator comparator) {
        return new Singleton(o, comparator);
    }
    
    public static ObjectSortedSet synchronize(final ObjectSortedSet set) {
        return (ObjectSortedSet)new ObjectSortedSets.SynchronizedSortedSet(set);
    }
    
    public static ObjectSortedSet synchronize(final ObjectSortedSet set, final Object o) {
        return (ObjectSortedSet)new ObjectSortedSets.SynchronizedSortedSet(set, o);
    }
    
    public static ObjectSortedSet unmodifiable(final ObjectSortedSet set) {
        return (ObjectSortedSet)new ObjectSortedSets.UnmodifiableSortedSet(set);
    }
    
    static {
        EMPTY_SET = new EmptySet();
    }
    
    public static class EmptySet extends ObjectSets.EmptySet implements ObjectSortedSet, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptySet() {
        }
        
        @Override
        public ObjectBidirectionalIterator iterator(final Object o) {
            return ObjectIterators.EMPTY_ITERATOR;
        }
        
        @Override
        public ObjectSortedSet subSet(final Object o, final Object o2) {
            return ObjectSortedSets.EMPTY_SET;
        }
        
        @Override
        public ObjectSortedSet headSet(final Object o) {
            return ObjectSortedSets.EMPTY_SET;
        }
        
        @Override
        public ObjectSortedSet tailSet(final Object o) {
            return ObjectSortedSets.EMPTY_SET;
        }
        
        @Override
        public Object first() {
            throw new NoSuchElementException();
        }
        
        @Override
        public Object last() {
            throw new NoSuchElementException();
        }
        
        @Override
        public Comparator comparator() {
            return null;
        }
        
        @Override
        public Object clone() {
            return ObjectSortedSets.EMPTY_SET;
        }
        
        private Object readResolve() {
            return ObjectSortedSets.EMPTY_SET;
        }
        
        @Override
        public SortedSet tailSet(final Object o) {
            return this.tailSet(o);
        }
        
        @Override
        public SortedSet headSet(final Object o) {
            return this.headSet(o);
        }
        
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            return this.subSet(o, o2);
        }
    }
    
    public static class Singleton extends ObjectSets.Singleton implements ObjectSortedSet, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        final Comparator comparator;
        
        protected Singleton(final Object o, final Comparator comparator) {
            super(o);
            this.comparator = comparator;
        }
        
        Singleton(final Object o) {
            this(o, null);
        }
        
        final int compare(final Object o, final Object o2) {
            return (this.comparator == null) ? ((Comparable)o).compareTo(o2) : this.comparator.compare(o, o2);
        }
        
        @Override
        public ObjectBidirectionalIterator iterator(final Object o) {
            final ObjectListIterator iterator = this.iterator();
            if (this.compare(this.element, o) <= 0) {
                iterator.next();
            }
            return iterator;
        }
        
        @Override
        public Comparator comparator() {
            return this.comparator;
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return ObjectSpliterators.singleton(this.element, this.comparator);
        }
        
        @Override
        public ObjectSortedSet subSet(final Object o, final Object o2) {
            if (this.compare(o, this.element) <= 0 && this.compare(this.element, o2) < 0) {
                return this;
            }
            return ObjectSortedSets.EMPTY_SET;
        }
        
        @Override
        public ObjectSortedSet headSet(final Object o) {
            if (this.compare(this.element, o) < 0) {
                return this;
            }
            return ObjectSortedSets.EMPTY_SET;
        }
        
        @Override
        public ObjectSortedSet tailSet(final Object o) {
            if (this.compare(o, this.element) <= 0) {
                return this;
            }
            return ObjectSortedSets.EMPTY_SET;
        }
        
        @Override
        public Object first() {
            return this.element;
        }
        
        @Override
        public Object last() {
            return this.element;
        }
        
        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }
        
        @Override
        public ObjectBidirectionalIterator iterator() {
            return super.iterator();
        }
        
        @Override
        public SortedSet tailSet(final Object o) {
            return this.tailSet(o);
        }
        
        @Override
        public SortedSet headSet(final Object o) {
            return this.headSet(o);
        }
        
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            return this.subSet(o, o2);
        }
    }
}
