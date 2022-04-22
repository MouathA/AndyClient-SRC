package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import java.util.*;

public final class IntSortedSets
{
    public static final EmptySet EMPTY_SET;
    
    private IntSortedSets() {
    }
    
    public static IntSortedSet singleton(final int n) {
        return new Singleton(n);
    }
    
    public static IntSortedSet singleton(final int n, final IntComparator intComparator) {
        return new Singleton(n, intComparator);
    }
    
    public static IntSortedSet singleton(final Object o) {
        return new Singleton((int)o);
    }
    
    public static IntSortedSet singleton(final Object o, final IntComparator intComparator) {
        return new Singleton((int)o, intComparator);
    }
    
    public static IntSortedSet synchronize(final IntSortedSet set) {
        return (IntSortedSet)new IntSortedSets.SynchronizedSortedSet(set);
    }
    
    public static IntSortedSet synchronize(final IntSortedSet set, final Object o) {
        return (IntSortedSet)new IntSortedSets.SynchronizedSortedSet(set, o);
    }
    
    public static IntSortedSet unmodifiable(final IntSortedSet set) {
        return (IntSortedSet)new IntSortedSets.UnmodifiableSortedSet(set);
    }
    
    static {
        EMPTY_SET = new EmptySet();
    }
    
    public static class Singleton extends IntSets.Singleton implements IntSortedSet, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        final IntComparator comparator;
        
        protected Singleton(final int n, final IntComparator comparator) {
            super(n);
            this.comparator = comparator;
        }
        
        Singleton(final int n) {
            this(n, null);
        }
        
        final int compare(final int n, final int n2) {
            return (this.comparator == null) ? Integer.compare(n, n2) : this.comparator.compare(n, n2);
        }
        
        @Override
        public IntBidirectionalIterator iterator(final int n) {
            final IntListIterator iterator = this.iterator();
            if (this.compare(this.element, n) <= 0) {
                iterator.nextInt();
            }
            return iterator;
        }
        
        @Override
        public IntComparator comparator() {
            return this.comparator;
        }
        
        @Override
        public IntSpliterator spliterator() {
            return IntSpliterators.singleton(this.element, this.comparator);
        }
        
        @Override
        public IntSortedSet subSet(final int n, final int n2) {
            if (this.compare(n, this.element) <= 0 && this.compare(this.element, n2) < 0) {
                return this;
            }
            return IntSortedSets.EMPTY_SET;
        }
        
        @Override
        public IntSortedSet headSet(final int n) {
            if (this.compare(this.element, n) < 0) {
                return this;
            }
            return IntSortedSets.EMPTY_SET;
        }
        
        @Override
        public IntSortedSet tailSet(final int n) {
            if (this.compare(n, this.element) <= 0) {
                return this;
            }
            return IntSortedSets.EMPTY_SET;
        }
        
        @Override
        public int firstInt() {
            return this.element;
        }
        
        @Override
        public int lastInt() {
            return this.element;
        }
        
        @Deprecated
        @Override
        public IntSortedSet subSet(final Integer n, final Integer n2) {
            return this.subSet((int)n, (int)n2);
        }
        
        @Deprecated
        @Override
        public IntSortedSet headSet(final Integer n) {
            return this.headSet((int)n);
        }
        
        @Deprecated
        @Override
        public IntSortedSet tailSet(final Integer n) {
            return this.tailSet((int)n);
        }
        
        @Deprecated
        @Override
        public Integer first() {
            return this.element;
        }
        
        @Deprecated
        @Override
        public Integer last() {
            return this.element;
        }
        
        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }
        
        @Override
        public IntBidirectionalIterator iterator() {
            return super.iterator();
        }
        
        @Deprecated
        @Override
        public Object last() {
            return this.last();
        }
        
        @Deprecated
        @Override
        public Object first() {
            return this.first();
        }
        
        @Deprecated
        @Override
        public SortedSet tailSet(final Object o) {
            return this.tailSet((Integer)o);
        }
        
        @Deprecated
        @Override
        public SortedSet headSet(final Object o) {
            return this.headSet((Integer)o);
        }
        
        @Deprecated
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            return this.subSet((Integer)o, (Integer)o2);
        }
        
        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
    
    public static class EmptySet extends IntSets.EmptySet implements IntSortedSet, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptySet() {
        }
        
        @Override
        public IntBidirectionalIterator iterator(final int n) {
            return IntIterators.EMPTY_ITERATOR;
        }
        
        @Override
        public IntSortedSet subSet(final int n, final int n2) {
            return IntSortedSets.EMPTY_SET;
        }
        
        @Override
        public IntSortedSet headSet(final int n) {
            return IntSortedSets.EMPTY_SET;
        }
        
        @Override
        public IntSortedSet tailSet(final int n) {
            return IntSortedSets.EMPTY_SET;
        }
        
        @Override
        public int firstInt() {
            throw new NoSuchElementException();
        }
        
        @Override
        public int lastInt() {
            throw new NoSuchElementException();
        }
        
        @Override
        public IntComparator comparator() {
            return null;
        }
        
        @Deprecated
        @Override
        public IntSortedSet subSet(final Integer n, final Integer n2) {
            return IntSortedSets.EMPTY_SET;
        }
        
        @Deprecated
        @Override
        public IntSortedSet headSet(final Integer n) {
            return IntSortedSets.EMPTY_SET;
        }
        
        @Deprecated
        @Override
        public IntSortedSet tailSet(final Integer n) {
            return IntSortedSets.EMPTY_SET;
        }
        
        @Deprecated
        @Override
        public Integer first() {
            throw new NoSuchElementException();
        }
        
        @Deprecated
        @Override
        public Integer last() {
            throw new NoSuchElementException();
        }
        
        @Override
        public Object clone() {
            return IntSortedSets.EMPTY_SET;
        }
        
        private Object readResolve() {
            return IntSortedSets.EMPTY_SET;
        }
        
        @Deprecated
        @Override
        public Object last() {
            return this.last();
        }
        
        @Deprecated
        @Override
        public Object first() {
            return this.first();
        }
        
        @Deprecated
        @Override
        public SortedSet tailSet(final Object o) {
            return this.tailSet((Integer)o);
        }
        
        @Deprecated
        @Override
        public SortedSet headSet(final Object o) {
            return this.headSet((Integer)o);
        }
        
        @Deprecated
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            return this.subSet((Integer)o, (Integer)o2);
        }
        
        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}
