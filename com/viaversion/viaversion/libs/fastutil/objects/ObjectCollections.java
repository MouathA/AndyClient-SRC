package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.*;
import java.util.concurrent.atomic.*;
import java.util.*;
import java.util.function.*;

public final class ObjectCollections
{
    private ObjectCollections() {
    }
    
    public static ObjectCollection synchronize(final ObjectCollection collection) {
        return (ObjectCollection)new ObjectCollections.SynchronizedCollection(collection);
    }
    
    public static ObjectCollection synchronize(final ObjectCollection collection, final Object o) {
        return (ObjectCollection)new ObjectCollections.SynchronizedCollection(collection, o);
    }
    
    public static ObjectCollection unmodifiable(final ObjectCollection collection) {
        return (ObjectCollection)new ObjectCollections.UnmodifiableCollection(collection);
    }
    
    public static ObjectCollection asCollection(final ObjectIterable objectIterable) {
        if (objectIterable instanceof ObjectCollection) {
            return (ObjectCollection)objectIterable;
        }
        return new IterableCollection(objectIterable);
    }
    
    public static class IterableCollection extends AbstractObjectCollection implements Serializable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ObjectIterable iterable;
        
        protected IterableCollection(final ObjectIterable objectIterable) {
            this.iterable = Objects.requireNonNull(objectIterable);
        }
        
        @Override
        public int size() {
            final long exactSizeIfKnown = this.iterable.spliterator().getExactSizeIfKnown();
            if (exactSizeIfKnown >= 0L) {
                return (int)Math.min(2147483647L, exactSizeIfKnown);
            }
            final ObjectIterator iterator = this.iterator();
            while (iterator.hasNext()) {
                iterator.next();
                int n = 0;
                ++n;
            }
            return 0;
        }
        
        @Override
        public boolean isEmpty() {
            return !this.iterable.iterator().hasNext();
        }
        
        @Override
        public ObjectIterator iterator() {
            return this.iterable.iterator();
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return this.iterable.spliterator();
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
    
    static class SizeDecreasingSupplier implements Supplier
    {
        static final int RECOMMENDED_MIN_SIZE = 8;
        final AtomicInteger suppliedCount;
        final int expectedFinalSize;
        final IntFunction builder;
        
        SizeDecreasingSupplier(final int expectedFinalSize, final IntFunction builder) {
            this.suppliedCount = new AtomicInteger(0);
            this.expectedFinalSize = expectedFinalSize;
            this.builder = builder;
        }
        
        @Override
        public ObjectCollection get() {
            final int n = 1 + (this.expectedFinalSize - 1) / this.suppliedCount.incrementAndGet();
            if (8 < 0) {}
            return this.builder.apply(8);
        }
        
        @Override
        public Object get() {
            return this.get();
        }
    }
    
    public abstract static class EmptyCollection extends AbstractObjectCollection
    {
        protected EmptyCollection() {
        }
        
        @Override
        public boolean contains(final Object o) {
            return false;
        }
        
        @Override
        public Object[] toArray() {
            return ObjectArrays.EMPTY_ARRAY;
        }
        
        @Override
        public Object[] toArray(final Object[] array) {
            if (array.length > 0) {
                array[0] = null;
            }
            return array;
        }
        
        @Override
        public ObjectBidirectionalIterator iterator() {
            return ObjectIterators.EMPTY_ITERATOR;
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return ObjectSpliterators.EMPTY_SPLITERATOR;
        }
        
        @Override
        public int size() {
            return 0;
        }
        
        @Override
        public void clear() {
        }
        
        @Override
        public int hashCode() {
            return 0;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o == this || (o instanceof Collection && ((Collection)o).isEmpty());
        }
        
        @Override
        public void forEach(final Consumer consumer) {
        }
        
        @Override
        public boolean containsAll(final Collection collection) {
            return collection.isEmpty();
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
            Objects.requireNonNull(predicate);
            return false;
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
