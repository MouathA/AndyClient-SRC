package com.google.common.collect;

import javax.annotation.*;
import com.google.common.primitives.*;
import com.google.common.base.*;
import java.util.*;

final class RegularImmutableSortedMultiset extends ImmutableSortedMultiset
{
    private final transient RegularImmutableSortedSet elementSet;
    private final transient int[] counts;
    private final transient long[] cumulativeCounts;
    private final transient int offset;
    private final transient int length;
    
    RegularImmutableSortedMultiset(final RegularImmutableSortedSet elementSet, final int[] counts, final long[] cumulativeCounts, final int offset, final int length) {
        this.elementSet = elementSet;
        this.counts = counts;
        this.cumulativeCounts = cumulativeCounts;
        this.offset = offset;
        this.length = length;
    }
    
    @Override
    Multiset.Entry getEntry(final int n) {
        return Multisets.immutableEntry(this.elementSet.asList().get(n), this.counts[this.offset + n]);
    }
    
    @Override
    public Multiset.Entry firstEntry() {
        return this.getEntry(0);
    }
    
    @Override
    public Multiset.Entry lastEntry() {
        return this.getEntry(this.length - 1);
    }
    
    @Override
    public int count(@Nullable final Object o) {
        final int index = this.elementSet.indexOf(o);
        return (index == -1) ? 0 : this.counts[index + this.offset];
    }
    
    @Override
    public int size() {
        return Ints.saturatedCast(this.cumulativeCounts[this.offset + this.length] - this.cumulativeCounts[this.offset]);
    }
    
    @Override
    public ImmutableSortedSet elementSet() {
        return this.elementSet;
    }
    
    @Override
    public ImmutableSortedMultiset headMultiset(final Object o, final BoundType boundType) {
        return this.getSubMultiset(0, this.elementSet.headIndex(o, Preconditions.checkNotNull(boundType) == BoundType.CLOSED));
    }
    
    @Override
    public ImmutableSortedMultiset tailMultiset(final Object o, final BoundType boundType) {
        return this.getSubMultiset(this.elementSet.tailIndex(o, Preconditions.checkNotNull(boundType) == BoundType.CLOSED), this.length);
    }
    
    ImmutableSortedMultiset getSubMultiset(final int n, final int n2) {
        Preconditions.checkPositionIndexes(n, n2, this.length);
        if (n == n2) {
            return ImmutableSortedMultiset.emptyMultiset(this.comparator());
        }
        if (n == 0 && n2 == this.length) {
            return this;
        }
        return new RegularImmutableSortedMultiset((RegularImmutableSortedSet)this.elementSet.getSubSet(n, n2), this.counts, this.cumulativeCounts, this.offset + n, n2 - n);
    }
    
    @Override
    boolean isPartialView() {
        return this.offset > 0 || this.length < this.counts.length;
    }
    
    @Override
    public SortedMultiset tailMultiset(final Object o, final BoundType boundType) {
        return this.tailMultiset(o, boundType);
    }
    
    @Override
    public SortedMultiset headMultiset(final Object o, final BoundType boundType) {
        return this.headMultiset(o, boundType);
    }
    
    @Override
    public NavigableSet elementSet() {
        return this.elementSet();
    }
    
    @Override
    public SortedSet elementSet() {
        return this.elementSet();
    }
    
    @Override
    public Set elementSet() {
        return this.elementSet();
    }
}
