package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;
import java.io.*;

@Beta
@GwtIncompatible("hasn't been tested yet")
public abstract class ImmutableSortedMultiset extends ImmutableSortedMultisetFauxverideShim implements SortedMultiset
{
    private static final Comparator NATURAL_ORDER;
    private static final ImmutableSortedMultiset NATURAL_EMPTY_MULTISET;
    transient ImmutableSortedMultiset descendingMultiset;
    
    public static ImmutableSortedMultiset of() {
        return ImmutableSortedMultiset.NATURAL_EMPTY_MULTISET;
    }
    
    public static ImmutableSortedMultiset of(final Comparable comparable) {
        return new RegularImmutableSortedMultiset((RegularImmutableSortedSet)ImmutableSortedSet.of(comparable), new int[] { 1 }, new long[] { 0L, 1L }, 0, 1);
    }
    
    public static ImmutableSortedMultiset of(final Comparable comparable, final Comparable comparable2) {
        return copyOf(Ordering.natural(), Arrays.asList(comparable, comparable2));
    }
    
    public static ImmutableSortedMultiset of(final Comparable comparable, final Comparable comparable2, final Comparable comparable3) {
        return copyOf(Ordering.natural(), Arrays.asList(comparable, comparable2, comparable3));
    }
    
    public static ImmutableSortedMultiset of(final Comparable comparable, final Comparable comparable2, final Comparable comparable3, final Comparable comparable4) {
        return copyOf(Ordering.natural(), Arrays.asList(comparable, comparable2, comparable3, comparable4));
    }
    
    public static ImmutableSortedMultiset of(final Comparable comparable, final Comparable comparable2, final Comparable comparable3, final Comparable comparable4, final Comparable comparable5) {
        return copyOf(Ordering.natural(), Arrays.asList(comparable, comparable2, comparable3, comparable4, comparable5));
    }
    
    public static ImmutableSortedMultiset of(final Comparable comparable, final Comparable comparable2, final Comparable comparable3, final Comparable comparable4, final Comparable comparable5, final Comparable comparable6, final Comparable... array) {
        final ArrayList arrayListWithCapacity = Lists.newArrayListWithCapacity(array.length + 6);
        Collections.addAll(arrayListWithCapacity, comparable, comparable2, comparable3, comparable4, comparable5, comparable6);
        Collections.addAll(arrayListWithCapacity, (Comparable[])array);
        return copyOf(Ordering.natural(), arrayListWithCapacity);
    }
    
    public static ImmutableSortedMultiset copyOf(final Comparable[] array) {
        return copyOf(Ordering.natural(), Arrays.asList((Comparable[])array));
    }
    
    public static ImmutableSortedMultiset copyOf(final Iterable iterable) {
        return copyOf(Ordering.natural(), iterable);
    }
    
    public static ImmutableSortedMultiset copyOf(final Iterator iterator) {
        return copyOf(Ordering.natural(), iterator);
    }
    
    public static ImmutableSortedMultiset copyOf(final Comparator comparator, final Iterator iterator) {
        Preconditions.checkNotNull(comparator);
        return new Builder(comparator).addAll(iterator).build();
    }
    
    public static ImmutableSortedMultiset copyOf(final Comparator comparator, final Iterable iterable) {
        if (iterable instanceof ImmutableSortedMultiset) {
            final ImmutableSortedMultiset immutableSortedMultiset = (ImmutableSortedMultiset)iterable;
            if (comparator.equals(immutableSortedMultiset.comparator())) {
                if (immutableSortedMultiset.isPartialView()) {
                    return copyOfSortedEntries(comparator, immutableSortedMultiset.entrySet().asList());
                }
                return immutableSortedMultiset;
            }
        }
        final ArrayList arrayList = Lists.newArrayList(iterable);
        final TreeMultiset create = TreeMultiset.create((Comparator)Preconditions.checkNotNull(comparator));
        Iterables.addAll(create, arrayList);
        return copyOfSortedEntries(comparator, create.entrySet());
    }
    
    public static ImmutableSortedMultiset copyOfSorted(final SortedMultiset sortedMultiset) {
        return copyOfSortedEntries(sortedMultiset.comparator(), Lists.newArrayList(sortedMultiset.entrySet()));
    }
    
    private static ImmutableSortedMultiset copyOfSortedEntries(final Comparator comparator, final Collection collection) {
        if (collection.isEmpty()) {
            return emptyMultiset(comparator);
        }
        final ImmutableList.Builder builder = new ImmutableList.Builder(collection.size());
        final int[] array = new int[collection.size()];
        final long[] array2 = new long[collection.size() + 1];
        for (final Multiset.Entry entry : collection) {
            builder.add(entry.getElement());
            array[0] = entry.getCount();
            array2[1] = array2[0] + array[0];
            int n = 0;
            ++n;
        }
        return new RegularImmutableSortedMultiset(new RegularImmutableSortedSet(builder.build(), comparator), array, array2, 0, collection.size());
    }
    
    static ImmutableSortedMultiset emptyMultiset(final Comparator comparator) {
        if (ImmutableSortedMultiset.NATURAL_ORDER.equals(comparator)) {
            return ImmutableSortedMultiset.NATURAL_EMPTY_MULTISET;
        }
        return new EmptyImmutableSortedMultiset(comparator);
    }
    
    ImmutableSortedMultiset() {
    }
    
    @Override
    public final Comparator comparator() {
        return this.elementSet().comparator();
    }
    
    @Override
    public abstract ImmutableSortedSet elementSet();
    
    @Override
    public ImmutableSortedMultiset descendingMultiset() {
        final ImmutableSortedMultiset descendingMultiset = this.descendingMultiset;
        if (descendingMultiset == null) {
            return this.descendingMultiset = new DescendingImmutableSortedMultiset(this);
        }
        return descendingMultiset;
    }
    
    @Deprecated
    @Override
    public final Multiset.Entry pollFirstEntry() {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final Multiset.Entry pollLastEntry() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public abstract ImmutableSortedMultiset headMultiset(final Object p0, final BoundType p1);
    
    @Override
    public ImmutableSortedMultiset subMultiset(final Object o, final BoundType boundType, final Object o2, final BoundType boundType2) {
        Preconditions.checkArgument(this.comparator().compare(o, o2) <= 0, "Expected lowerBound <= upperBound but %s > %s", o, o2);
        return this.tailMultiset(o, boundType).headMultiset(o2, boundType2);
    }
    
    @Override
    public abstract ImmutableSortedMultiset tailMultiset(final Object p0, final BoundType p1);
    
    public static Builder orderedBy(final Comparator comparator) {
        return new Builder(comparator);
    }
    
    public static Builder reverseOrder() {
        return new Builder(Ordering.natural().reverse());
    }
    
    public static Builder naturalOrder() {
        return new Builder(Ordering.natural());
    }
    
    @Override
    Object writeReplace() {
        return new SerializedForm(this);
    }
    
    @Override
    public Set elementSet() {
        return this.elementSet();
    }
    
    @Override
    public SortedMultiset tailMultiset(final Object o, final BoundType boundType) {
        return this.tailMultiset(o, boundType);
    }
    
    @Override
    public SortedMultiset subMultiset(final Object o, final BoundType boundType, final Object o2, final BoundType boundType2) {
        return this.subMultiset(o, boundType, o2, boundType2);
    }
    
    @Override
    public SortedMultiset headMultiset(final Object o, final BoundType boundType) {
        return this.headMultiset(o, boundType);
    }
    
    @Override
    public SortedMultiset descendingMultiset() {
        return this.descendingMultiset();
    }
    
    @Override
    public NavigableSet elementSet() {
        return this.elementSet();
    }
    
    @Override
    public SortedSet elementSet() {
        return this.elementSet();
    }
    
    static {
        NATURAL_ORDER = Ordering.natural();
        NATURAL_EMPTY_MULTISET = new EmptyImmutableSortedMultiset(ImmutableSortedMultiset.NATURAL_ORDER);
    }
    
    private static final class SerializedForm implements Serializable
    {
        Comparator comparator;
        Object[] elements;
        int[] counts;
        
        SerializedForm(final SortedMultiset sortedMultiset) {
            this.comparator = sortedMultiset.comparator();
            final int size = sortedMultiset.entrySet().size();
            this.elements = new Object[size];
            this.counts = new int[size];
            for (final Multiset.Entry entry : sortedMultiset.entrySet()) {
                this.elements[0] = entry.getElement();
                this.counts[0] = entry.getCount();
                int n = 0;
                ++n;
            }
        }
        
        Object readResolve() {
            final int length = this.elements.length;
            final Builder builder = new Builder(this.comparator);
            while (0 < length) {
                builder.addCopies(this.elements[0], this.counts[0]);
                int n = 0;
                ++n;
            }
            return builder.build();
        }
    }
    
    public static class Builder extends ImmutableMultiset.Builder
    {
        public Builder(final Comparator comparator) {
            super(TreeMultiset.create((Comparator)Preconditions.checkNotNull(comparator)));
        }
        
        @Override
        public Builder add(final Object o) {
            super.add(o);
            return this;
        }
        
        @Override
        public Builder addCopies(final Object o, final int n) {
            super.addCopies(o, n);
            return this;
        }
        
        @Override
        public Builder setCount(final Object o, final int n) {
            super.setCount(o, n);
            return this;
        }
        
        @Override
        public Builder add(final Object... array) {
            super.add(array);
            return this;
        }
        
        @Override
        public Builder addAll(final Iterable iterable) {
            super.addAll(iterable);
            return this;
        }
        
        @Override
        public Builder addAll(final Iterator iterator) {
            super.addAll(iterator);
            return this;
        }
        
        @Override
        public ImmutableSortedMultiset build() {
            return ImmutableSortedMultiset.copyOfSorted((SortedMultiset)this.contents);
        }
        
        @Override
        public ImmutableMultiset build() {
            return this.build();
        }
        
        @Override
        public ImmutableMultiset.Builder addAll(final Iterator iterator) {
            return this.addAll(iterator);
        }
        
        @Override
        public ImmutableMultiset.Builder addAll(final Iterable iterable) {
            return this.addAll(iterable);
        }
        
        @Override
        public ImmutableMultiset.Builder add(final Object[] array) {
            return this.add(array);
        }
        
        @Override
        public ImmutableMultiset.Builder setCount(final Object o, final int n) {
            return this.setCount(o, n);
        }
        
        @Override
        public ImmutableMultiset.Builder addCopies(final Object o, final int n) {
            return this.addCopies(o, n);
        }
        
        @Override
        public ImmutableMultiset.Builder add(final Object o) {
            return this.add(o);
        }
        
        @Override
        public ImmutableCollection build() {
            return this.build();
        }
        
        @Override
        public ImmutableCollection.Builder addAll(final Iterator iterator) {
            return this.addAll(iterator);
        }
        
        @Override
        public ImmutableCollection.Builder addAll(final Iterable iterable) {
            return this.addAll(iterable);
        }
        
        @Override
        public ImmutableCollection.Builder add(final Object[] array) {
            return this.add(array);
        }
        
        @Override
        public ImmutableCollection.Builder add(final Object o) {
            return this.add(o);
        }
    }
}
