package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import java.util.function.*;
import java.util.*;

public final class IntSets
{
    static final int ARRAY_SET_CUTOFF = 4;
    public static final EmptySet EMPTY_SET;
    static final IntSet UNMODIFIABLE_EMPTY_SET;
    
    private IntSets() {
    }
    
    public static IntSet emptySet() {
        return IntSets.EMPTY_SET;
    }
    
    public static IntSet singleton(final int n) {
        return new Singleton(n);
    }
    
    public static IntSet singleton(final Integer n) {
        return new Singleton(n);
    }
    
    public static IntSet synchronize(final IntSet set) {
        return (IntSet)new IntSets.SynchronizedSet(set);
    }
    
    public static IntSet synchronize(final IntSet set, final Object o) {
        return (IntSet)new IntSets.SynchronizedSet(set, o);
    }
    
    public static IntSet unmodifiable(final IntSet set) {
        return (IntSet)new IntSets.UnmodifiableSet(set);
    }
    
    public static IntSet fromTo(final int n, final int n2) {
        return new AbstractIntSet(n2) {
            final int val$from;
            final int val$to;
            
            @Override
            public boolean contains(final int n) {
                return n >= this.val$from && n < this.val$to;
            }
            
            @Override
            public IntIterator iterator() {
                return IntIterators.fromTo(this.val$from, this.val$to);
            }
            
            @Override
            public int size() {
                final long n = this.val$to - (long)this.val$from;
                return (n >= 0L && n <= 2147483647L) ? ((int)n) : Integer.MAX_VALUE;
            }
            
            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }
    
    public static IntSet from(final int n) {
        return new AbstractIntSet() {
            final int val$from;
            
            @Override
            public boolean contains(final int n) {
                return n >= this.val$from;
            }
            
            @Override
            public IntIterator iterator() {
                return IntIterators.concat(IntIterators.fromTo(this.val$from, Integer.MAX_VALUE), IntSets.singleton(Integer.MAX_VALUE).iterator());
            }
            
            @Override
            public int size() {
                final long n = 2147483647L - this.val$from + 1L;
                return (n >= 0L && n <= 2147483647L) ? ((int)n) : Integer.MAX_VALUE;
            }
            
            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }
    
    public static IntSet to(final int n) {
        return new AbstractIntSet() {
            final int val$to;
            
            @Override
            public boolean contains(final int n) {
                return n < this.val$to;
            }
            
            @Override
            public IntIterator iterator() {
                return IntIterators.fromTo(Integer.MIN_VALUE, this.val$to);
            }
            
            @Override
            public int size() {
                final long n = this.val$to + 2147483648L;
                return (n >= 0L && n <= 2147483647L) ? ((int)n) : Integer.MAX_VALUE;
            }
            
            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }
    
    static {
        EMPTY_SET = new EmptySet();
        UNMODIFIABLE_EMPTY_SET = unmodifiable(new IntArraySet(IntArrays.EMPTY_ARRAY));
    }
    
    public static class EmptySet extends IntCollections.EmptyCollection implements IntSet, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptySet() {
        }
        
        @Override
        public boolean remove(final int n) {
            throw new UnsupportedOperationException();
        }
        
        public Object clone() {
            return IntSets.EMPTY_SET;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof Set && ((Set)o).isEmpty();
        }
        
        @Deprecated
        @Override
        public boolean rem(final int n) {
            return super.rem(n);
        }
        
        private Object readResolve() {
            return IntSets.EMPTY_SET;
        }
    }
    
    public static class Singleton extends AbstractIntSet implements Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int element;
        
        protected Singleton(final int element) {
            this.element = element;
        }
        
        @Override
        public boolean contains(final int n) {
            return n == this.element;
        }
        
        @Override
        public boolean remove(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public IntListIterator iterator() {
            return IntIterators.singleton(this.element);
        }
        
        @Override
        public IntSpliterator spliterator() {
            return IntSpliterators.singleton(this.element);
        }
        
        @Override
        public int size() {
            return 1;
        }
        
        @Override
        public int[] toIntArray() {
            return new int[] { this.element };
        }
        
        @Deprecated
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
        
        @Deprecated
        @Override
        public boolean removeIf(final Predicate predicate) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void forEach(final IntConsumer intConsumer) {
            intConsumer.accept(this.element);
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
            throw new UnsupportedOperationException();
        }
        
        @Deprecated
        @Override
        public Object[] toArray() {
            return new Object[] { this.element };
        }
        
        public Object clone() {
            return this;
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
