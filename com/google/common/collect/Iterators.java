package com.google.common.collect;

import javax.annotation.*;
import com.google.common.base.*;
import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible(emulated = true)
public final class Iterators
{
    static final UnmodifiableListIterator EMPTY_LIST_ITERATOR;
    private static final Iterator EMPTY_MODIFIABLE_ITERATOR;
    
    private Iterators() {
    }
    
    public static UnmodifiableIterator emptyIterator() {
        return emptyListIterator();
    }
    
    static UnmodifiableListIterator emptyListIterator() {
        return Iterators.EMPTY_LIST_ITERATOR;
    }
    
    static Iterator emptyModifiableIterator() {
        return Iterators.EMPTY_MODIFIABLE_ITERATOR;
    }
    
    public static UnmodifiableIterator unmodifiableIterator(final Iterator iterator) {
        Preconditions.checkNotNull(iterator);
        if (iterator instanceof UnmodifiableIterator) {
            return (UnmodifiableIterator)iterator;
        }
        return new UnmodifiableIterator(iterator) {
            final Iterator val$iterator;
            
            @Override
            public boolean hasNext() {
                return this.val$iterator.hasNext();
            }
            
            @Override
            public Object next() {
                return this.val$iterator.next();
            }
        };
    }
    
    @Deprecated
    public static UnmodifiableIterator unmodifiableIterator(final UnmodifiableIterator unmodifiableIterator) {
        return (UnmodifiableIterator)Preconditions.checkNotNull(unmodifiableIterator);
    }
    
    public static int size(final Iterator iterator) {
        while (iterator.hasNext()) {
            iterator.next();
            int n = 0;
            ++n;
        }
        return 0;
    }
    
    public static boolean contains(final Iterator iterator, @Nullable final Object o) {
        return any(iterator, Predicates.equalTo(o));
    }
    
    public static boolean removeAll(final Iterator iterator, final Collection collection) {
        return removeIf(iterator, Predicates.in(collection));
    }
    
    public static boolean removeIf(final Iterator iterator, final Predicate predicate) {
        Preconditions.checkNotNull(predicate);
        while (iterator.hasNext()) {
            if (predicate.apply(iterator.next())) {
                iterator.remove();
            }
        }
        return true;
    }
    
    public static boolean retainAll(final Iterator iterator, final Collection collection) {
        return removeIf(iterator, Predicates.not(Predicates.in(collection)));
    }
    
    public static boolean elementsEqual(final Iterator iterator, final Iterator iterator2) {
        while (iterator.hasNext()) {
            if (!iterator2.hasNext()) {
                return false;
            }
            if (!Objects.equal(iterator.next(), iterator2.next())) {
                return false;
            }
        }
        return !iterator2.hasNext();
    }
    
    public static String toString(final Iterator iterator) {
        return Collections2.STANDARD_JOINER.appendTo(new StringBuilder().append('['), iterator).append(']').toString();
    }
    
    public static Object getOnlyElement(final Iterator iterator) {
        final Object next = iterator.next();
        if (!iterator.hasNext()) {
            return next;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("expected one element but was: <" + next);
        while (0 < 4 && iterator.hasNext()) {
            sb.append(", " + iterator.next());
            int n = 0;
            ++n;
        }
        if (iterator.hasNext()) {
            sb.append(", ...");
        }
        sb.append('>');
        throw new IllegalArgumentException(sb.toString());
    }
    
    @Nullable
    public static Object getOnlyElement(final Iterator iterator, @Nullable final Object o) {
        return iterator.hasNext() ? getOnlyElement(iterator) : o;
    }
    
    @GwtIncompatible("Array.newInstance(Class, int)")
    public static Object[] toArray(final Iterator iterator, final Class clazz) {
        return Iterables.toArray(Lists.newArrayList(iterator), clazz);
    }
    
    public static boolean addAll(final Collection collection, final Iterator iterator) {
        Preconditions.checkNotNull(collection);
        Preconditions.checkNotNull(iterator);
        while (iterator.hasNext()) {
            final boolean b = false | collection.add(iterator.next());
        }
        return false;
    }
    
    public static int frequency(final Iterator iterator, @Nullable final Object o) {
        return size(filter(iterator, Predicates.equalTo(o)));
    }
    
    public static Iterator cycle(final Iterable iterable) {
        Preconditions.checkNotNull(iterable);
        return new Iterator(iterable) {
            Iterator iterator = Iterators.emptyIterator();
            Iterator removeFrom;
            final Iterable val$iterable;
            
            @Override
            public boolean hasNext() {
                if (!this.iterator.hasNext()) {
                    this.iterator = this.val$iterable.iterator();
                }
                return this.iterator.hasNext();
            }
            
            @Override
            public Object next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.removeFrom = this.iterator;
                return this.iterator.next();
            }
            
            @Override
            public void remove() {
                CollectPreconditions.checkRemove(this.removeFrom != null);
                this.removeFrom.remove();
                this.removeFrom = null;
            }
        };
    }
    
    public static Iterator cycle(final Object... array) {
        return cycle(Lists.newArrayList(array));
    }
    
    public static Iterator concat(final Iterator iterator, final Iterator iterator2) {
        return concat(ImmutableList.of(iterator, iterator2).iterator());
    }
    
    public static Iterator concat(final Iterator iterator, final Iterator iterator2, final Iterator iterator3) {
        return concat(ImmutableList.of(iterator, iterator2, iterator3).iterator());
    }
    
    public static Iterator concat(final Iterator iterator, final Iterator iterator2, final Iterator iterator3, final Iterator iterator4) {
        return concat(ImmutableList.of(iterator, iterator2, iterator3, iterator4).iterator());
    }
    
    public static Iterator concat(final Iterator... array) {
        return concat(ImmutableList.copyOf((Object[])array).iterator());
    }
    
    public static Iterator concat(final Iterator iterator) {
        Preconditions.checkNotNull(iterator);
        return new Iterator(iterator) {
            Iterator current = Iterators.emptyIterator();
            Iterator removeFrom;
            final Iterator val$inputs;
            
            @Override
            public boolean hasNext() {
                boolean hasNext;
                while (!(hasNext = ((Iterator)Preconditions.checkNotNull(this.current)).hasNext()) && this.val$inputs.hasNext()) {
                    this.current = this.val$inputs.next();
                }
                return hasNext;
            }
            
            @Override
            public Object next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.removeFrom = this.current;
                return this.current.next();
            }
            
            @Override
            public void remove() {
                CollectPreconditions.checkRemove(this.removeFrom != null);
                this.removeFrom.remove();
                this.removeFrom = null;
            }
        };
    }
    
    public static UnmodifiableIterator partition(final Iterator iterator, final int n) {
        return partitionImpl(iterator, n, false);
    }
    
    public static UnmodifiableIterator paddedPartition(final Iterator iterator, final int n) {
        return partitionImpl(iterator, n, true);
    }
    
    private static UnmodifiableIterator partitionImpl(final Iterator iterator, final int n, final boolean b) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkArgument(n > 0);
        return new UnmodifiableIterator(iterator, n, b) {
            final Iterator val$iterator;
            final int val$size;
            final boolean val$pad;
            
            @Override
            public boolean hasNext() {
                return this.val$iterator.hasNext();
            }
            
            @Override
            public List next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                final Object[] array = new Object[this.val$size];
                while (0 < this.val$size && this.val$iterator.hasNext()) {
                    array[0] = this.val$iterator.next();
                    int n = 0;
                    ++n;
                }
                while (0 < this.val$size) {
                    array[0] = null;
                    int n2 = 0;
                    ++n2;
                }
                final List<Object> unmodifiableList = Collections.unmodifiableList((List<?>)Arrays.asList((T[])array));
                return (this.val$pad || 0 == this.val$size) ? unmodifiableList : unmodifiableList.subList(0, 0);
            }
            
            @Override
            public Object next() {
                return this.next();
            }
        };
    }
    
    public static UnmodifiableIterator filter(final Iterator iterator, final Predicate predicate) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkNotNull(predicate);
        return new AbstractIterator(iterator, predicate) {
            final Iterator val$unfiltered;
            final Predicate val$predicate;
            
            @Override
            protected Object computeNext() {
                while (this.val$unfiltered.hasNext()) {
                    final Object next = this.val$unfiltered.next();
                    if (this.val$predicate.apply(next)) {
                        return next;
                    }
                }
                return this.endOfData();
            }
        };
    }
    
    @GwtIncompatible("Class.isInstance")
    public static UnmodifiableIterator filter(final Iterator iterator, final Class clazz) {
        return filter(iterator, Predicates.instanceOf(clazz));
    }
    
    public static boolean any(final Iterator iterator, final Predicate predicate) {
        return indexOf(iterator, predicate) != -1;
    }
    
    public static boolean all(final Iterator iterator, final Predicate predicate) {
        Preconditions.checkNotNull(predicate);
        while (iterator.hasNext()) {
            if (!predicate.apply(iterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    public static Object find(final Iterator iterator, final Predicate predicate) {
        return filter(iterator, predicate).next();
    }
    
    @Nullable
    public static Object find(final Iterator iterator, final Predicate predicate, @Nullable final Object o) {
        return getNext(filter(iterator, predicate), o);
    }
    
    public static Optional tryFind(final Iterator iterator, final Predicate predicate) {
        final UnmodifiableIterator filter = filter(iterator, predicate);
        return filter.hasNext() ? Optional.of(filter.next()) : Optional.absent();
    }
    
    public static int indexOf(final Iterator iterator, final Predicate predicate) {
        Preconditions.checkNotNull(predicate, (Object)"predicate");
        while (iterator.hasNext()) {
            if (predicate.apply(iterator.next())) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    public static Iterator transform(final Iterator iterator, final Function function) {
        Preconditions.checkNotNull(function);
        return new TransformedIterator(iterator, function) {
            final Function val$function;
            
            @Override
            Object transform(final Object o) {
                return this.val$function.apply(o);
            }
        };
    }
    
    public static Object get(final Iterator iterator, final int n) {
        checkNonnegative(n);
        final int advance = advance(iterator, n);
        if (!iterator.hasNext()) {
            throw new IndexOutOfBoundsException("position (" + n + ") must be less than the number of elements that remained (" + advance + ")");
        }
        return iterator.next();
    }
    
    static void checkNonnegative(final int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("position (" + n + ") must not be negative");
        }
    }
    
    @Nullable
    public static Object get(final Iterator iterator, final int n, @Nullable final Object o) {
        checkNonnegative(n);
        advance(iterator, n);
        return getNext(iterator, o);
    }
    
    @Nullable
    public static Object getNext(final Iterator iterator, @Nullable final Object o) {
        return iterator.hasNext() ? iterator.next() : o;
    }
    
    public static Object getLast(final Iterator iterator) {
        Object next;
        do {
            next = iterator.next();
        } while (iterator.hasNext());
        return next;
    }
    
    @Nullable
    public static Object getLast(final Iterator iterator, @Nullable final Object o) {
        return iterator.hasNext() ? getLast(iterator) : o;
    }
    
    public static int advance(final Iterator iterator, final int n) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkArgument(n >= 0, (Object)"numberToAdvance must be nonnegative");
        while (0 < n && iterator.hasNext()) {
            iterator.next();
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    public static Iterator limit(final Iterator iterator, final int n) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkArgument(n >= 0, (Object)"limit is negative");
        return new Iterator(n, iterator) {
            private int count;
            final int val$limitSize;
            final Iterator val$iterator;
            
            @Override
            public boolean hasNext() {
                return this.count < this.val$limitSize && this.val$iterator.hasNext();
            }
            
            @Override
            public Object next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                ++this.count;
                return this.val$iterator.next();
            }
            
            @Override
            public void remove() {
                this.val$iterator.remove();
            }
        };
    }
    
    public static Iterator consumingIterator(final Iterator iterator) {
        Preconditions.checkNotNull(iterator);
        return new UnmodifiableIterator(iterator) {
            final Iterator val$iterator;
            
            @Override
            public boolean hasNext() {
                return this.val$iterator.hasNext();
            }
            
            @Override
            public Object next() {
                final Object next = this.val$iterator.next();
                this.val$iterator.remove();
                return next;
            }
            
            @Override
            public String toString() {
                return "Iterators.consumingIterator(...)";
            }
        };
    }
    
    @Nullable
    static Object pollNext(final Iterator iterator) {
        if (iterator.hasNext()) {
            final Object next = iterator.next();
            iterator.remove();
            return next;
        }
        return null;
    }
    
    static void clear(final Iterator iterator) {
        Preconditions.checkNotNull(iterator);
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }
    
    public static UnmodifiableIterator forArray(final Object... array) {
        return forArray(array, 0, array.length, 0);
    }
    
    static UnmodifiableListIterator forArray(final Object[] array, final int n, final int n2, final int n3) {
        Preconditions.checkArgument(n2 >= 0);
        Preconditions.checkPositionIndexes(n, n + n2, array.length);
        Preconditions.checkPositionIndex(n3, n2);
        if (n2 == 0) {
            return emptyListIterator();
        }
        return new AbstractIndexedListIterator(n2, n3, array, n) {
            final Object[] val$array;
            final int val$offset;
            
            @Override
            protected Object get(final int n) {
                return this.val$array[this.val$offset + n];
            }
        };
    }
    
    public static UnmodifiableIterator singletonIterator(@Nullable final Object o) {
        return new UnmodifiableIterator(o) {
            boolean done;
            final Object val$value;
            
            @Override
            public boolean hasNext() {
                return !this.done;
            }
            
            @Override
            public Object next() {
                if (this.done) {
                    throw new NoSuchElementException();
                }
                this.done = true;
                return this.val$value;
            }
        };
    }
    
    public static UnmodifiableIterator forEnumeration(final Enumeration enumeration) {
        Preconditions.checkNotNull(enumeration);
        return new UnmodifiableIterator(enumeration) {
            final Enumeration val$enumeration;
            
            @Override
            public boolean hasNext() {
                return this.val$enumeration.hasMoreElements();
            }
            
            @Override
            public Object next() {
                return this.val$enumeration.nextElement();
            }
        };
    }
    
    public static Enumeration asEnumeration(final Iterator iterator) {
        Preconditions.checkNotNull(iterator);
        return new Enumeration(iterator) {
            final Iterator val$iterator;
            
            @Override
            public boolean hasMoreElements() {
                return this.val$iterator.hasNext();
            }
            
            @Override
            public Object nextElement() {
                return this.val$iterator.next();
            }
        };
    }
    
    public static PeekingIterator peekingIterator(final Iterator iterator) {
        if (iterator instanceof PeekingImpl) {
            return (PeekingImpl)iterator;
        }
        return new PeekingImpl(iterator);
    }
    
    @Deprecated
    public static PeekingIterator peekingIterator(final PeekingIterator peekingIterator) {
        return (PeekingIterator)Preconditions.checkNotNull(peekingIterator);
    }
    
    @Beta
    public static UnmodifiableIterator mergeSorted(final Iterable iterable, final Comparator comparator) {
        Preconditions.checkNotNull(iterable, (Object)"iterators");
        Preconditions.checkNotNull(comparator, (Object)"comparator");
        return new MergingIterator(iterable, comparator);
    }
    
    static ListIterator cast(final Iterator iterator) {
        return (ListIterator)iterator;
    }
    
    static {
        EMPTY_LIST_ITERATOR = new UnmodifiableListIterator() {
            @Override
            public boolean hasNext() {
                return false;
            }
            
            @Override
            public Object next() {
                throw new NoSuchElementException();
            }
            
            @Override
            public boolean hasPrevious() {
                return false;
            }
            
            @Override
            public Object previous() {
                throw new NoSuchElementException();
            }
            
            @Override
            public int nextIndex() {
                return 0;
            }
            
            @Override
            public int previousIndex() {
                return -1;
            }
        };
        EMPTY_MODIFIABLE_ITERATOR = new Iterator() {
            @Override
            public boolean hasNext() {
                return false;
            }
            
            @Override
            public Object next() {
                throw new NoSuchElementException();
            }
            
            @Override
            public void remove() {
                CollectPreconditions.checkRemove(false);
            }
        };
    }
    
    private static class MergingIterator extends UnmodifiableIterator
    {
        final Queue queue;
        
        public MergingIterator(final Iterable iterable, final Comparator comparator) {
            this.queue = new PriorityQueue(2, new Comparator(comparator) {
                final Comparator val$itemComparator;
                final MergingIterator this$0;
                
                public int compare(final PeekingIterator peekingIterator, final PeekingIterator peekingIterator2) {
                    return this.val$itemComparator.compare(peekingIterator.peek(), peekingIterator2.peek());
                }
                
                @Override
                public int compare(final Object o, final Object o2) {
                    return this.compare((PeekingIterator)o, (PeekingIterator)o2);
                }
            });
            for (final Iterator iterator2 : iterable) {
                if (iterator2.hasNext()) {
                    this.queue.add(Iterators.peekingIterator(iterator2));
                }
            }
        }
        
        @Override
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }
        
        @Override
        public Object next() {
            final PeekingIterator peekingIterator = this.queue.remove();
            final Object next = peekingIterator.next();
            if (peekingIterator.hasNext()) {
                this.queue.add(peekingIterator);
            }
            return next;
        }
    }
    
    private static class PeekingImpl implements PeekingIterator
    {
        private final Iterator iterator;
        private boolean hasPeeked;
        private Object peekedElement;
        
        public PeekingImpl(final Iterator iterator) {
            this.iterator = (Iterator)Preconditions.checkNotNull(iterator);
        }
        
        @Override
        public boolean hasNext() {
            return this.hasPeeked || this.iterator.hasNext();
        }
        
        @Override
        public Object next() {
            if (!this.hasPeeked) {
                return this.iterator.next();
            }
            final Object peekedElement = this.peekedElement;
            this.hasPeeked = false;
            this.peekedElement = null;
            return peekedElement;
        }
        
        @Override
        public void remove() {
            Preconditions.checkState(!this.hasPeeked, (Object)"Can't remove after you've peeked at next");
            this.iterator.remove();
        }
        
        @Override
        public Object peek() {
            if (!this.hasPeeked) {
                this.peekedElement = this.iterator.next();
                this.hasPeeked = true;
            }
            return this.peekedElement;
        }
    }
}
