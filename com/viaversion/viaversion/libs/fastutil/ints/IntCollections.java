package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import java.util.concurrent.atomic.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import java.util.*;
import java.util.function.*;

public final class IntCollections
{
    private IntCollections() {
    }
    
    public static IntCollection synchronize(final IntCollection collection) {
        return (IntCollection)new IntCollections.SynchronizedCollection(collection);
    }
    
    public static IntCollection synchronize(final IntCollection collection, final Object o) {
        return (IntCollection)new IntCollections.SynchronizedCollection(collection, o);
    }
    
    public static IntCollection unmodifiable(final IntCollection collection) {
        return (IntCollection)new IntCollections.UnmodifiableCollection(collection);
    }
    
    public static IntCollection asCollection(final IntIterable intIterable) {
        if (intIterable instanceof IntCollection) {
            return (IntCollection)intIterable;
        }
        return new IterableCollection(intIterable);
    }
    
    public static class IterableCollection extends AbstractIntCollection implements Serializable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntIterable iterable;
        
        protected IterableCollection(final IntIterable intIterable) {
            this.iterable = Objects.requireNonNull(intIterable);
        }
        
        @Override
        public int size() {
            final long exactSizeIfKnown = this.iterable.spliterator().getExactSizeIfKnown();
            if (exactSizeIfKnown >= 0L) {
                return (int)Math.min(2147483647L, exactSizeIfKnown);
            }
            final IntIterator iterator = this.iterator();
            while (iterator.hasNext()) {
                iterator.nextInt();
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
        public IntIterator iterator() {
            return this.iterable.iterator();
        }
        
        @Override
        public IntSpliterator spliterator() {
            return this.iterable.spliterator();
        }
        
        @Override
        public IntIterator intIterator() {
            return this.iterable.intIterator();
        }
        
        @Override
        public IntSpliterator intSpliterator() {
            return this.iterable.intSpliterator();
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
        public IntCollection get() {
            final int n = 1 + (this.expectedFinalSize - 1) / this.suppliedCount.incrementAndGet();
            if (8 < 0) {}
            return this.builder.apply(8);
        }
        
        @Override
        public Object get() {
            return this.get();
        }
    }
    
    public abstract static class EmptyCollection extends AbstractIntCollection
    {
        protected EmptyCollection() {
        }
        
        @Override
        public boolean contains(final int n) {
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
        public IntBidirectionalIterator iterator() {
            return IntIterators.EMPTY_ITERATOR;
        }
        
        @Override
        public IntSpliterator spliterator() {
            return IntSpliterators.EMPTY_SPLITERATOR;
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
        
        @Deprecated
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
        
        @Deprecated
        @Override
        public boolean removeIf(final Predicate predicate) {
            Objects.requireNonNull(predicate);
            return false;
        }
        
        @Override
        public int[] toIntArray() {
            return IntArrays.EMPTY_ARRAY;
        }
        
        @Deprecated
        @Override
        public int[] toIntArray(final int[] array) {
            return array;
        }
        
        @Override
        public void forEach(final IntConsumer intConsumer) {
        }
        
        @Override
        public boolean containsAll(final IntCollection collection) {
            return collection.isEmpty();
        }
        
        @Override
        public boolean addAll(final IntCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean removeAll(final IntCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean retainAll(final IntCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean removeIf(final IntPredicate intPredicate) {
            Objects.requireNonNull(intPredicate);
            return false;
        }
        
        @Override
        public IntIterator iterator() {
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
