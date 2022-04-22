package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;
import javax.annotation.*;

@GwtCompatible
public final class Range implements Predicate, Serializable
{
    private static final Function LOWER_BOUND_FN;
    private static final Function UPPER_BOUND_FN;
    static final Ordering RANGE_LEX_ORDERING;
    private static final Range ALL;
    final Cut lowerBound;
    final Cut upperBound;
    private static final long serialVersionUID = 0L;
    
    static Function lowerBoundFn() {
        return Range.LOWER_BOUND_FN;
    }
    
    static Function upperBoundFn() {
        return Range.UPPER_BOUND_FN;
    }
    
    static Range create(final Cut cut, final Cut cut2) {
        return new Range(cut, cut2);
    }
    
    public static Range open(final Comparable comparable, final Comparable comparable2) {
        return create(Cut.aboveValue(comparable), Cut.belowValue(comparable2));
    }
    
    public static Range closed(final Comparable comparable, final Comparable comparable2) {
        return create(Cut.belowValue(comparable), Cut.aboveValue(comparable2));
    }
    
    public static Range closedOpen(final Comparable comparable, final Comparable comparable2) {
        return create(Cut.belowValue(comparable), Cut.belowValue(comparable2));
    }
    
    public static Range openClosed(final Comparable comparable, final Comparable comparable2) {
        return create(Cut.aboveValue(comparable), Cut.aboveValue(comparable2));
    }
    
    public static Range range(final Comparable comparable, final BoundType boundType, final Comparable comparable2, final BoundType boundType2) {
        Preconditions.checkNotNull(boundType);
        Preconditions.checkNotNull(boundType2);
        return create((boundType == BoundType.OPEN) ? Cut.aboveValue(comparable) : Cut.belowValue(comparable), (boundType2 == BoundType.OPEN) ? Cut.belowValue(comparable2) : Cut.aboveValue(comparable2));
    }
    
    public static Range lessThan(final Comparable comparable) {
        return create(Cut.belowAll(), Cut.belowValue(comparable));
    }
    
    public static Range atMost(final Comparable comparable) {
        return create(Cut.belowAll(), Cut.aboveValue(comparable));
    }
    
    public static Range upTo(final Comparable comparable, final BoundType boundType) {
        switch (boundType) {
            case OPEN: {
                return lessThan(comparable);
            }
            case CLOSED: {
                return atMost(comparable);
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    public static Range greaterThan(final Comparable comparable) {
        return create(Cut.aboveValue(comparable), Cut.aboveAll());
    }
    
    public static Range atLeast(final Comparable comparable) {
        return create(Cut.belowValue(comparable), Cut.aboveAll());
    }
    
    public static Range downTo(final Comparable comparable, final BoundType boundType) {
        switch (boundType) {
            case OPEN: {
                return greaterThan(comparable);
            }
            case CLOSED: {
                return atLeast(comparable);
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    public static Range all() {
        return Range.ALL;
    }
    
    public static Range singleton(final Comparable comparable) {
        return closed(comparable, comparable);
    }
    
    public static Range encloseAll(final Iterable iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof ContiguousSet) {
            return ((ContiguousSet)iterable).range();
        }
        final Iterator<Object> iterator = iterable.iterator();
        Comparable comparable2;
        Comparable comparable = comparable2 = (Comparable)Preconditions.checkNotNull(iterator.next());
        while (iterator.hasNext()) {
            final Comparable comparable3 = (Comparable)Preconditions.checkNotNull(iterator.next());
            comparable = (Comparable)Ordering.natural().min(comparable, comparable3);
            comparable2 = (Comparable)Ordering.natural().max(comparable2, comparable3);
        }
        return closed(comparable, comparable2);
    }
    
    private Range(final Cut cut, final Cut cut2) {
        if (cut.compareTo(cut2) > 0 || cut == Cut.aboveAll() || cut2 == Cut.belowAll()) {
            throw new IllegalArgumentException("Invalid range: " + toString(cut, cut2));
        }
        this.lowerBound = (Cut)Preconditions.checkNotNull(cut);
        this.upperBound = (Cut)Preconditions.checkNotNull(cut2);
    }
    
    public boolean hasLowerBound() {
        return this.lowerBound != Cut.belowAll();
    }
    
    public Comparable lowerEndpoint() {
        return this.lowerBound.endpoint();
    }
    
    public BoundType lowerBoundType() {
        return this.lowerBound.typeAsLowerBound();
    }
    
    public boolean hasUpperBound() {
        return this.upperBound != Cut.aboveAll();
    }
    
    public Comparable upperEndpoint() {
        return this.upperBound.endpoint();
    }
    
    public BoundType upperBoundType() {
        return this.upperBound.typeAsUpperBound();
    }
    
    public boolean isEmpty() {
        return this.lowerBound.equals(this.upperBound);
    }
    
    public boolean contains(final Comparable comparable) {
        Preconditions.checkNotNull(comparable);
        return this.lowerBound.isLessThan(comparable) && !this.upperBound.isLessThan(comparable);
    }
    
    @Deprecated
    public boolean apply(final Comparable comparable) {
        return this.contains(comparable);
    }
    
    public boolean containsAll(final Iterable iterable) {
        if (Iterables.isEmpty(iterable)) {
            return true;
        }
        if (iterable instanceof SortedSet) {
            final SortedSet cast = cast(iterable);
            final Comparator<? super Comparable> comparator = cast.comparator();
            if (Ordering.natural().equals(comparator) || comparator == null) {
                return this.contains(cast.first()) && this.contains(cast.last());
            }
        }
        final Iterator<Comparable> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            if (!this.contains(iterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    public boolean encloses(final Range range) {
        return this.lowerBound.compareTo(range.lowerBound) <= 0 && this.upperBound.compareTo(range.upperBound) >= 0;
    }
    
    public boolean isConnected(final Range range) {
        return this.lowerBound.compareTo(range.upperBound) <= 0 && range.lowerBound.compareTo(this.upperBound) <= 0;
    }
    
    public Range intersection(final Range range) {
        final int compareTo = this.lowerBound.compareTo(range.lowerBound);
        final int compareTo2 = this.upperBound.compareTo(range.upperBound);
        if (compareTo >= 0 && compareTo2 <= 0) {
            return this;
        }
        if (compareTo <= 0 && compareTo2 >= 0) {
            return range;
        }
        return create((compareTo >= 0) ? this.lowerBound : range.lowerBound, (compareTo2 <= 0) ? this.upperBound : range.upperBound);
    }
    
    public Range span(final Range range) {
        final int compareTo = this.lowerBound.compareTo(range.lowerBound);
        final int compareTo2 = this.upperBound.compareTo(range.upperBound);
        if (compareTo <= 0 && compareTo2 >= 0) {
            return this;
        }
        if (compareTo >= 0 && compareTo2 <= 0) {
            return range;
        }
        return create((compareTo <= 0) ? this.lowerBound : range.lowerBound, (compareTo2 >= 0) ? this.upperBound : range.upperBound);
    }
    
    public Range canonical(final DiscreteDomain discreteDomain) {
        Preconditions.checkNotNull(discreteDomain);
        final Cut canonical = this.lowerBound.canonical(discreteDomain);
        final Cut canonical2 = this.upperBound.canonical(discreteDomain);
        return (canonical == this.lowerBound && canonical2 == this.upperBound) ? this : create(canonical, canonical2);
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        if (o instanceof Range) {
            final Range range = (Range)o;
            return this.lowerBound.equals(range.lowerBound) && this.upperBound.equals(range.upperBound);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.lowerBound.hashCode() * 31 + this.upperBound.hashCode();
    }
    
    @Override
    public String toString() {
        return toString(this.lowerBound, this.upperBound);
    }
    
    private static String toString(final Cut cut, final Cut cut2) {
        final StringBuilder sb = new StringBuilder(16);
        cut.describeAsLowerBound(sb);
        sb.append('\u2025');
        cut2.describeAsUpperBound(sb);
        return sb.toString();
    }
    
    private static SortedSet cast(final Iterable iterable) {
        return (SortedSet)iterable;
    }
    
    Object readResolve() {
        if (this.equals(Range.ALL)) {
            return all();
        }
        return this;
    }
    
    static int compareOrThrow(final Comparable comparable, final Comparable comparable2) {
        return comparable.compareTo(comparable2);
    }
    
    @Override
    public boolean apply(final Object o) {
        return this.apply((Comparable)o);
    }
    
    static {
        LOWER_BOUND_FN = new Function() {
            public Cut apply(final Range range) {
                return range.lowerBound;
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((Range)o);
            }
        };
        UPPER_BOUND_FN = new Function() {
            public Cut apply(final Range range) {
                return range.upperBound;
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((Range)o);
            }
        };
        RANGE_LEX_ORDERING = new Ordering() {
            public int compare(final Range range, final Range range2) {
                return ComparisonChain.start().compare(range.lowerBound, range2.lowerBound).compare(range.upperBound, range2.upperBound).result();
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((Range)o, (Range)o2);
            }
        };
        ALL = new Range(Cut.belowAll(), Cut.aboveAll());
    }
}
