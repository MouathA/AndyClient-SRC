package com.google.common.collect;

import javax.annotation.*;
import com.google.common.base.*;
import com.google.common.annotations.*;
import java.util.*;
import com.google.common.math.*;

@GwtCompatible
public final class Collections2
{
    static final Joiner STANDARD_JOINER;
    
    private Collections2() {
    }
    
    public static Collection filter(final Collection collection, final Predicate predicate) {
        if (collection instanceof FilteredCollection) {
            return ((FilteredCollection)collection).createCombined(predicate);
        }
        return new FilteredCollection((Collection)Preconditions.checkNotNull(collection), (Predicate)Preconditions.checkNotNull(predicate));
    }
    
    static boolean safeContains(final Collection collection, @Nullable final Object o) {
        Preconditions.checkNotNull(collection);
        return collection.contains(o);
    }
    
    static boolean safeRemove(final Collection collection, @Nullable final Object o) {
        Preconditions.checkNotNull(collection);
        return collection.remove(o);
    }
    
    public static Collection transform(final Collection collection, final Function function) {
        return new TransformedCollection(collection, function);
    }
    
    static boolean containsAllImpl(final Collection collection, final Collection collection2) {
        return Iterables.all(collection2, Predicates.in(collection));
    }
    
    static String toStringImpl(final Collection collection) {
        final StringBuilder append = newStringBuilderForCollection(collection.size()).append('[');
        Collections2.STANDARD_JOINER.appendTo(append, Iterables.transform(collection, new Function(collection) {
            final Collection val$collection;
            
            @Override
            public Object apply(final Object o) {
                return (o == this.val$collection) ? "(this Collection)" : o;
            }
        }));
        return append.append(']').toString();
    }
    
    static StringBuilder newStringBuilderForCollection(final int n) {
        CollectPreconditions.checkNonnegative(n, "size");
        return new StringBuilder((int)Math.min(n * 8L, 1073741824L));
    }
    
    static Collection cast(final Iterable iterable) {
        return (Collection)iterable;
    }
    
    @Beta
    public static Collection orderedPermutations(final Iterable iterable) {
        return orderedPermutations(iterable, Ordering.natural());
    }
    
    @Beta
    public static Collection orderedPermutations(final Iterable iterable, final Comparator comparator) {
        return new OrderedPermutationCollection(iterable, comparator);
    }
    
    @Beta
    public static Collection permutations(final Collection collection) {
        return new PermutationCollection(ImmutableList.copyOf(collection));
    }
    
    private static boolean isPermutation(final List list, final List list2) {
        return list.size() == list2.size() && HashMultiset.create(list).equals(HashMultiset.create(list2));
    }
    
    private static boolean isPositiveInt(final long n) {
        return n >= 0L && n <= 2147483647L;
    }
    
    static boolean access$000(final long n) {
        return isPositiveInt(n);
    }
    
    static boolean access$100(final List list, final List list2) {
        return isPermutation(list, list2);
    }
    
    static {
        STANDARD_JOINER = Joiner.on(", ").useForNull("null");
    }
    
    private static class PermutationIterator extends AbstractIterator
    {
        final List list;
        final int[] c;
        final int[] o;
        int j;
        
        PermutationIterator(final List list) {
            this.list = new ArrayList(list);
            final int size = list.size();
            this.c = new int[size];
            this.o = new int[size];
            Arrays.fill(this.c, 0);
            Arrays.fill(this.o, 1);
            this.j = Integer.MAX_VALUE;
        }
        
        @Override
        protected List computeNext() {
            if (this.j <= 0) {
                return (List)this.endOfData();
            }
            final ImmutableList copy = ImmutableList.copyOf(this.list);
            this.calculateNextPermutation();
            return copy;
        }
        
        void calculateNextPermutation() {
            this.j = this.list.size() - 1;
            if (this.j == -1) {
                return;
            }
            while (true) {
                final int n = this.c[this.j] + this.o[this.j];
                if (n < 0) {
                    this.switchDirection();
                }
                else {
                    if (n != this.j + 1) {
                        Collections.swap(this.list, this.j - this.c[this.j] + 0, this.j - n + 0);
                        this.c[this.j] = n;
                        break;
                    }
                    if (this.j == 0) {
                        break;
                    }
                    int n2 = 0;
                    ++n2;
                    this.switchDirection();
                }
            }
        }
        
        void switchDirection() {
            this.o[this.j] = -this.o[this.j];
            --this.j;
        }
        
        @Override
        protected Object computeNext() {
            return this.computeNext();
        }
    }
    
    private static final class PermutationCollection extends AbstractCollection
    {
        final ImmutableList inputList;
        
        PermutationCollection(final ImmutableList inputList) {
            this.inputList = inputList;
        }
        
        @Override
        public int size() {
            return IntMath.factorial(this.inputList.size());
        }
        
        @Override
        public boolean isEmpty() {
            return false;
        }
        
        @Override
        public Iterator iterator() {
            return new PermutationIterator(this.inputList);
        }
        
        @Override
        public boolean contains(@Nullable final Object o) {
            return o instanceof List && Collections2.access$100(this.inputList, (List)o);
        }
        
        @Override
        public String toString() {
            return "permutations(" + this.inputList + ")";
        }
    }
    
    private static final class OrderedPermutationIterator extends AbstractIterator
    {
        List nextPermutation;
        final Comparator comparator;
        
        OrderedPermutationIterator(final List list, final Comparator comparator) {
            this.nextPermutation = Lists.newArrayList(list);
            this.comparator = comparator;
        }
        
        @Override
        protected List computeNext() {
            if (this.nextPermutation == null) {
                return (List)this.endOfData();
            }
            final ImmutableList copy = ImmutableList.copyOf(this.nextPermutation);
            this.calculateNextPermutation();
            return copy;
        }
        
        void calculateNextPermutation() {
            final int nextJ = this.findNextJ();
            if (nextJ == -1) {
                this.nextPermutation = null;
                return;
            }
            Collections.swap(this.nextPermutation, nextJ, this.findNextL(nextJ));
            Collections.reverse(this.nextPermutation.subList(nextJ + 1, this.nextPermutation.size()));
        }
        
        int findNextJ() {
            for (int i = this.nextPermutation.size() - 2; i >= 0; --i) {
                if (this.comparator.compare(this.nextPermutation.get(i), this.nextPermutation.get(i + 1)) < 0) {
                    return i;
                }
            }
            return -1;
        }
        
        int findNextL(final int n) {
            final Object value = this.nextPermutation.get(n);
            for (int i = this.nextPermutation.size() - 1; i > n; --i) {
                if (this.comparator.compare(value, this.nextPermutation.get(i)) < 0) {
                    return i;
                }
            }
            throw new AssertionError((Object)"this statement should be unreachable");
        }
        
        @Override
        protected Object computeNext() {
            return this.computeNext();
        }
    }
    
    private static final class OrderedPermutationCollection extends AbstractCollection
    {
        final ImmutableList inputList;
        final Comparator comparator;
        final int size;
        
        OrderedPermutationCollection(final Iterable iterable, final Comparator comparator) {
            this.inputList = Ordering.from(comparator).immutableSortedCopy(iterable);
            this.comparator = comparator;
            this.size = calculateSize(this.inputList, comparator);
        }
        
        private static int calculateSize(final List list, final Comparator comparator) {
            long n = 1L;
            while (1 < list.size()) {
                if (comparator.compare(list.get(0), list.get(1)) < 0) {
                    n *= LongMath.binomial(1, 0);
                    if (!Collections2.access$000(n)) {
                        return Integer.MAX_VALUE;
                    }
                }
                int n2 = 0;
                ++n2;
                int n3 = 0;
                ++n3;
            }
            final long n4 = n * LongMath.binomial(1, 0);
            if (!Collections2.access$000(n4)) {
                return Integer.MAX_VALUE;
            }
            return (int)n4;
        }
        
        @Override
        public int size() {
            return this.size;
        }
        
        @Override
        public boolean isEmpty() {
            return false;
        }
        
        @Override
        public Iterator iterator() {
            return new OrderedPermutationIterator(this.inputList, this.comparator);
        }
        
        @Override
        public boolean contains(@Nullable final Object o) {
            return o instanceof List && Collections2.access$100(this.inputList, (List)o);
        }
        
        @Override
        public String toString() {
            return "orderedPermutationCollection(" + this.inputList + ")";
        }
    }
    
    static class TransformedCollection extends AbstractCollection
    {
        final Collection fromCollection;
        final Function function;
        
        TransformedCollection(final Collection collection, final Function function) {
            this.fromCollection = (Collection)Preconditions.checkNotNull(collection);
            this.function = (Function)Preconditions.checkNotNull(function);
        }
        
        @Override
        public void clear() {
            this.fromCollection.clear();
        }
        
        @Override
        public boolean isEmpty() {
            return this.fromCollection.isEmpty();
        }
        
        @Override
        public Iterator iterator() {
            return Iterators.transform(this.fromCollection.iterator(), this.function);
        }
        
        @Override
        public int size() {
            return this.fromCollection.size();
        }
    }
    
    static class FilteredCollection extends AbstractCollection
    {
        final Collection unfiltered;
        final Predicate predicate;
        
        FilteredCollection(final Collection unfiltered, final Predicate predicate) {
            this.unfiltered = unfiltered;
            this.predicate = predicate;
        }
        
        FilteredCollection createCombined(final Predicate predicate) {
            return new FilteredCollection(this.unfiltered, Predicates.and(this.predicate, predicate));
        }
        
        @Override
        public boolean add(final Object o) {
            Preconditions.checkArgument(this.predicate.apply(o));
            return this.unfiltered.add(o);
        }
        
        @Override
        public boolean addAll(final Collection collection) {
            final Iterator<Object> iterator = collection.iterator();
            while (iterator.hasNext()) {
                Preconditions.checkArgument(this.predicate.apply(iterator.next()));
            }
            return this.unfiltered.addAll(collection);
        }
        
        @Override
        public void clear() {
            Iterables.removeIf(this.unfiltered, this.predicate);
        }
        
        @Override
        public boolean contains(@Nullable final Object o) {
            return Collections2.safeContains(this.unfiltered, o) && this.predicate.apply(o);
        }
        
        @Override
        public boolean containsAll(final Collection collection) {
            return Collections2.containsAllImpl(this, collection);
        }
        
        @Override
        public boolean isEmpty() {
            return !Iterables.any(this.unfiltered, this.predicate);
        }
        
        @Override
        public Iterator iterator() {
            return Iterators.filter(this.unfiltered.iterator(), this.predicate);
        }
        
        @Override
        public boolean remove(final Object o) {
            return this.contains(o) && this.unfiltered.remove(o);
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            return Iterables.removeIf(this.unfiltered, Predicates.and(this.predicate, Predicates.in(collection)));
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            return Iterables.removeIf(this.unfiltered, Predicates.and(this.predicate, Predicates.not(Predicates.in(collection))));
        }
        
        @Override
        public int size() {
            return Iterators.size(this.iterator());
        }
        
        @Override
        public Object[] toArray() {
            return Lists.newArrayList(this.iterator()).toArray();
        }
        
        @Override
        public Object[] toArray(final Object[] array) {
            return Lists.newArrayList(this.iterator()).toArray(array);
        }
    }
}
