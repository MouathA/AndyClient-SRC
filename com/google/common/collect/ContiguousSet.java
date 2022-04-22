package com.google.common.collect;

import com.google.common.base.*;
import com.google.common.annotations.*;
import java.util.*;

@Beta
@GwtCompatible(emulated = true)
public abstract class ContiguousSet extends ImmutableSortedSet
{
    final DiscreteDomain domain;
    
    public static ContiguousSet create(final Range range, final DiscreteDomain discreteDomain) {
        Preconditions.checkNotNull(range);
        Preconditions.checkNotNull(discreteDomain);
        Range range2 = range;
        if (!range.hasLowerBound()) {
            range2 = range2.intersection(Range.atLeast(discreteDomain.minValue()));
        }
        if (!range.hasUpperBound()) {
            range2 = range2.intersection(Range.atMost(discreteDomain.maxValue()));
        }
        return (range2.isEmpty() || Range.compareOrThrow(range.lowerBound.leastValueAbove(discreteDomain), range.upperBound.greatestValueBelow(discreteDomain)) > 0) ? new EmptyContiguousSet(discreteDomain) : new RegularContiguousSet(range2, discreteDomain);
    }
    
    ContiguousSet(final DiscreteDomain domain) {
        super(Ordering.natural());
        this.domain = domain;
    }
    
    public ContiguousSet headSet(final Comparable comparable) {
        return this.headSetImpl((Comparable)Preconditions.checkNotNull(comparable), false);
    }
    
    @GwtIncompatible("NavigableSet")
    public ContiguousSet headSet(final Comparable comparable, final boolean b) {
        return this.headSetImpl((Comparable)Preconditions.checkNotNull(comparable), b);
    }
    
    public ContiguousSet subSet(final Comparable comparable, final Comparable comparable2) {
        Preconditions.checkNotNull(comparable);
        Preconditions.checkNotNull(comparable2);
        Preconditions.checkArgument(this.comparator().compare(comparable, comparable2) <= 0);
        return this.subSetImpl(comparable, true, comparable2, false);
    }
    
    @GwtIncompatible("NavigableSet")
    public ContiguousSet subSet(final Comparable comparable, final boolean b, final Comparable comparable2, final boolean b2) {
        Preconditions.checkNotNull(comparable);
        Preconditions.checkNotNull(comparable2);
        Preconditions.checkArgument(this.comparator().compare(comparable, comparable2) <= 0);
        return this.subSetImpl(comparable, b, comparable2, b2);
    }
    
    public ContiguousSet tailSet(final Comparable comparable) {
        return this.tailSetImpl((Comparable)Preconditions.checkNotNull(comparable), true);
    }
    
    @GwtIncompatible("NavigableSet")
    public ContiguousSet tailSet(final Comparable comparable, final boolean b) {
        return this.tailSetImpl((Comparable)Preconditions.checkNotNull(comparable), b);
    }
    
    abstract ContiguousSet headSetImpl(final Comparable p0, final boolean p1);
    
    abstract ContiguousSet subSetImpl(final Comparable p0, final boolean p1, final Comparable p2, final boolean p3);
    
    abstract ContiguousSet tailSetImpl(final Comparable p0, final boolean p1);
    
    public abstract ContiguousSet intersection(final ContiguousSet p0);
    
    public abstract Range range();
    
    public abstract Range range(final BoundType p0, final BoundType p1);
    
    @Override
    public String toString() {
        return this.range().toString();
    }
    
    @Deprecated
    public static Builder builder() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    ImmutableSortedSet tailSetImpl(final Object o, final boolean b) {
        return this.tailSetImpl((Comparable)o, b);
    }
    
    @Override
    ImmutableSortedSet subSetImpl(final Object o, final boolean b, final Object o2, final boolean b2) {
        return this.subSetImpl((Comparable)o, b, (Comparable)o2, b2);
    }
    
    @Override
    ImmutableSortedSet headSetImpl(final Object o, final boolean b) {
        return this.headSetImpl((Comparable)o, b);
    }
    
    @Override
    public ImmutableSortedSet tailSet(final Object o, final boolean b) {
        return this.tailSet((Comparable)o, b);
    }
    
    @Override
    public ImmutableSortedSet tailSet(final Object o) {
        return this.tailSet((Comparable)o);
    }
    
    @Override
    public ImmutableSortedSet subSet(final Object o, final boolean b, final Object o2, final boolean b2) {
        return this.subSet((Comparable)o, b, (Comparable)o2, b2);
    }
    
    @Override
    public ImmutableSortedSet subSet(final Object o, final Object o2) {
        return this.subSet((Comparable)o, (Comparable)o2);
    }
    
    @Override
    public ImmutableSortedSet headSet(final Object o, final boolean b) {
        return this.headSet((Comparable)o, b);
    }
    
    @Override
    public ImmutableSortedSet headSet(final Object o) {
        return this.headSet((Comparable)o);
    }
    
    @Override
    public SortedSet tailSet(final Object o) {
        return this.tailSet((Comparable)o);
    }
    
    @Override
    public SortedSet headSet(final Object o) {
        return this.headSet((Comparable)o);
    }
    
    @Override
    public SortedSet subSet(final Object o, final Object o2) {
        return this.subSet((Comparable)o, (Comparable)o2);
    }
    
    @Override
    public NavigableSet tailSet(final Object o, final boolean b) {
        return this.tailSet((Comparable)o, b);
    }
    
    @Override
    public NavigableSet headSet(final Object o, final boolean b) {
        return this.headSet((Comparable)o, b);
    }
    
    @Override
    public NavigableSet subSet(final Object o, final boolean b, final Object o2, final boolean b2) {
        return this.subSet((Comparable)o, b, (Comparable)o2, b2);
    }
}
