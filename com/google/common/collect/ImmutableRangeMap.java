package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;
import com.google.common.base.*;

@Beta
@GwtIncompatible("NavigableMap")
public class ImmutableRangeMap implements RangeMap
{
    private static final ImmutableRangeMap EMPTY;
    private final ImmutableList ranges;
    private final ImmutableList values;
    
    public static ImmutableRangeMap of() {
        return ImmutableRangeMap.EMPTY;
    }
    
    public static ImmutableRangeMap of(final Range range, final Object o) {
        return new ImmutableRangeMap(ImmutableList.of(range), ImmutableList.of(o));
    }
    
    public static ImmutableRangeMap copyOf(final RangeMap rangeMap) {
        if (rangeMap instanceof ImmutableRangeMap) {
            return (ImmutableRangeMap)rangeMap;
        }
        final Map mapOfRanges = rangeMap.asMapOfRanges();
        final ImmutableList.Builder builder = new ImmutableList.Builder(mapOfRanges.size());
        final ImmutableList.Builder builder2 = new ImmutableList.Builder(mapOfRanges.size());
        for (final Map.Entry<Object, V> entry : mapOfRanges.entrySet()) {
            builder.add(entry.getKey());
            builder2.add((Object)entry.getValue());
        }
        return new ImmutableRangeMap(builder.build(), builder2.build());
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    ImmutableRangeMap(final ImmutableList ranges, final ImmutableList values) {
        this.ranges = ranges;
        this.values = values;
    }
    
    @Nullable
    @Override
    public Object get(final Comparable comparable) {
        final int binarySearch = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(comparable), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
        if (binarySearch == -1) {
            return null;
        }
        return ((Range)this.ranges.get(binarySearch)).contains(comparable) ? this.values.get(binarySearch) : null;
    }
    
    @Nullable
    @Override
    public Map.Entry getEntry(final Comparable comparable) {
        final int binarySearch = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(comparable), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
        if (binarySearch == -1) {
            return null;
        }
        final Range range = this.ranges.get(binarySearch);
        return range.contains(comparable) ? Maps.immutableEntry(range, this.values.get(binarySearch)) : null;
    }
    
    @Override
    public Range span() {
        if (this.ranges.isEmpty()) {
            throw new NoSuchElementException();
        }
        return Range.create(this.ranges.get(0).lowerBound, this.ranges.get(this.ranges.size() - 1).upperBound);
    }
    
    @Override
    public void put(final Range range, final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void putAll(final RangeMap rangeMap) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void remove(final Range range) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ImmutableMap asMapOfRanges() {
        if (this.ranges.isEmpty()) {
            return ImmutableMap.of();
        }
        return new RegularImmutableSortedMap(new RegularImmutableSortedSet(this.ranges, Range.RANGE_LEX_ORDERING), this.values);
    }
    
    @Override
    public ImmutableRangeMap subRangeMap(final Range range) {
        if (((Range)Preconditions.checkNotNull(range)).isEmpty()) {
            return of();
        }
        if (this.ranges.isEmpty() || range.encloses(this.span())) {
            return this;
        }
        final int binarySearch = SortedLists.binarySearch(this.ranges, Range.upperBoundFn(), range.lowerBound, SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        final int binarySearch2 = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), range.upperBound, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        if (binarySearch >= binarySearch2) {
            return of();
        }
        return new ImmutableRangeMap((ImmutableList)new ImmutableList(binarySearch2 - binarySearch, binarySearch, range) {
            final int val$len;
            final int val$off;
            final Range val$range;
            final ImmutableRangeMap this$0;
            
            @Override
            public int size() {
                return this.val$len;
            }
            
            @Override
            public Range get(final int n) {
                Preconditions.checkElementIndex(n, this.val$len);
                if (n == 0 || n == this.val$len - 1) {
                    return ImmutableRangeMap.access$000(this.this$0).get(n + this.val$off).intersection(this.val$range);
                }
                return ImmutableRangeMap.access$000(this.this$0).get(n + this.val$off);
            }
            
            @Override
            boolean isPartialView() {
                return true;
            }
            
            @Override
            public Object get(final int n) {
                return this.get(n);
            }
        }, this.values.subList(binarySearch, binarySearch2), range, this) {
            final Range val$range;
            final ImmutableRangeMap val$outer;
            final ImmutableRangeMap this$0;
            
            @Override
            public ImmutableRangeMap subRangeMap(final Range range) {
                if (this.val$range.isConnected(range)) {
                    return this.val$outer.subRangeMap(range.intersection(this.val$range));
                }
                return ImmutableRangeMap.of();
            }
            
            @Override
            public RangeMap subRangeMap(final Range range) {
                return this.subRangeMap(range);
            }
            
            @Override
            public Map asMapOfRanges() {
                return super.asMapOfRanges();
            }
        };
    }
    
    @Override
    public int hashCode() {
        return this.asMapOfRanges().hashCode();
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o instanceof RangeMap && this.asMapOfRanges().equals(((RangeMap)o).asMapOfRanges());
    }
    
    @Override
    public String toString() {
        return this.asMapOfRanges().toString();
    }
    
    @Override
    public RangeMap subRangeMap(final Range range) {
        return this.subRangeMap(range);
    }
    
    @Override
    public Map asMapOfRanges() {
        return this.asMapOfRanges();
    }
    
    static ImmutableList access$000(final ImmutableRangeMap immutableRangeMap) {
        return immutableRangeMap.ranges;
    }
    
    static {
        EMPTY = new ImmutableRangeMap(ImmutableList.of(), ImmutableList.of());
    }
    
    public static final class Builder
    {
        private final RangeSet keyRanges;
        private final RangeMap rangeMap;
        
        public Builder() {
            this.keyRanges = TreeRangeSet.create();
            this.rangeMap = TreeRangeMap.create();
        }
        
        public Builder put(final Range range, final Object o) {
            Preconditions.checkNotNull(range);
            Preconditions.checkNotNull(o);
            Preconditions.checkArgument(!range.isEmpty(), "Range must not be empty, but was %s", range);
            if (!this.keyRanges.complement().encloses(range)) {
                for (final Map.Entry<Range, V> entry : this.rangeMap.asMapOfRanges().entrySet()) {
                    final Range range2 = entry.getKey();
                    if (range2.isConnected(range) && !range2.intersection(range).isEmpty()) {
                        throw new IllegalArgumentException("Overlapping ranges: range " + range + " overlaps with entry " + entry);
                    }
                }
            }
            this.keyRanges.add(range);
            this.rangeMap.put(range, o);
            return this;
        }
        
        public Builder putAll(final RangeMap rangeMap) {
            for (final Map.Entry<Range, V> entry : rangeMap.asMapOfRanges().entrySet()) {
                this.put(entry.getKey(), entry.getValue());
            }
            return this;
        }
        
        public ImmutableRangeMap build() {
            final Map mapOfRanges = this.rangeMap.asMapOfRanges();
            final ImmutableList.Builder builder = new ImmutableList.Builder(mapOfRanges.size());
            final ImmutableList.Builder builder2 = new ImmutableList.Builder(mapOfRanges.size());
            for (final Map.Entry<Object, V> entry : mapOfRanges.entrySet()) {
                builder.add(entry.getKey());
                builder2.add((Object)entry.getValue());
            }
            return new ImmutableRangeMap(builder.build(), builder2.build());
        }
    }
}
