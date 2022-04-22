package com.google.common.collect;

import java.io.*;
import com.google.common.base.*;
import java.util.*;
import com.google.common.primitives.*;
import com.google.common.annotations.*;

@Beta
public final class ImmutableRangeSet extends AbstractRangeSet implements Serializable
{
    private static final ImmutableRangeSet EMPTY;
    private static final ImmutableRangeSet ALL;
    private final transient ImmutableList ranges;
    private transient ImmutableRangeSet complement;
    
    public static ImmutableRangeSet of() {
        return ImmutableRangeSet.EMPTY;
    }
    
    static ImmutableRangeSet all() {
        return ImmutableRangeSet.ALL;
    }
    
    public static ImmutableRangeSet of(final Range range) {
        Preconditions.checkNotNull(range);
        if (range.isEmpty()) {
            return of();
        }
        if (range.equals(Range.all())) {
            return all();
        }
        return new ImmutableRangeSet(ImmutableList.of(range));
    }
    
    public static ImmutableRangeSet copyOf(final RangeSet set) {
        Preconditions.checkNotNull(set);
        if (set.isEmpty()) {
            return of();
        }
        if (set.encloses(Range.all())) {
            return all();
        }
        if (set instanceof ImmutableRangeSet) {
            final ImmutableRangeSet set2 = (ImmutableRangeSet)set;
            if (!set2.isPartialView()) {
                return set2;
            }
        }
        return new ImmutableRangeSet(ImmutableList.copyOf(set.asRanges()));
    }
    
    ImmutableRangeSet(final ImmutableList ranges) {
        this.ranges = ranges;
    }
    
    private ImmutableRangeSet(final ImmutableList ranges, final ImmutableRangeSet complement) {
        this.ranges = ranges;
        this.complement = complement;
    }
    
    @Override
    public boolean encloses(final Range range) {
        final int binarySearch = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), range.lowerBound, Ordering.natural(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
        return binarySearch != -1 && ((Range)this.ranges.get(binarySearch)).encloses(range);
    }
    
    @Override
    public Range rangeContaining(final Comparable comparable) {
        final int binarySearch = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(comparable), Ordering.natural(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
        if (binarySearch != -1) {
            final Range range = this.ranges.get(binarySearch);
            return range.contains(comparable) ? range : null;
        }
        return null;
    }
    
    @Override
    public Range span() {
        if (this.ranges.isEmpty()) {
            throw new NoSuchElementException();
        }
        return Range.create(this.ranges.get(0).lowerBound, this.ranges.get(this.ranges.size() - 1).upperBound);
    }
    
    @Override
    public boolean isEmpty() {
        return this.ranges.isEmpty();
    }
    
    @Override
    public void add(final Range range) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void addAll(final RangeSet set) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void remove(final Range range) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void removeAll(final RangeSet set) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ImmutableSet asRanges() {
        if (this.ranges.isEmpty()) {
            return ImmutableSet.of();
        }
        return new RegularImmutableSortedSet(this.ranges, Range.RANGE_LEX_ORDERING);
    }
    
    @Override
    public ImmutableRangeSet complement() {
        final ImmutableRangeSet complement = this.complement;
        if (complement != null) {
            return complement;
        }
        if (this.ranges.isEmpty()) {
            return this.complement = all();
        }
        if (this.ranges.size() == 1 && this.ranges.get(0).equals(Range.all())) {
            return this.complement = of();
        }
        return this.complement = new ImmutableRangeSet(new ComplementRanges(), this);
    }
    
    private ImmutableList intersectRanges(final Range range) {
        if (this.ranges.isEmpty() || range.isEmpty()) {
            return ImmutableList.of();
        }
        if (range.encloses(this.span())) {
            return this.ranges;
        }
        if (range.hasLowerBound()) {
            SortedLists.binarySearch(this.ranges, Range.upperBoundFn(), range.lowerBound, SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        }
        int n;
        if (range.hasUpperBound()) {
            n = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), range.upperBound, SortedLists.KeyPresentBehavior.FIRST_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        }
        else {
            n = this.ranges.size();
        }
        final int n2 = n - 0;
        if (n2 == 0) {
            return ImmutableList.of();
        }
        return new ImmutableList(n2, 0, range) {
            final int val$length;
            final int val$fromIndex;
            final Range val$range;
            final ImmutableRangeSet this$0;
            
            @Override
            public int size() {
                return this.val$length;
            }
            
            @Override
            public Range get(final int n) {
                Preconditions.checkElementIndex(n, this.val$length);
                if (n == 0 || n == this.val$length - 1) {
                    return ImmutableRangeSet.access$000(this.this$0).get(n + this.val$fromIndex).intersection(this.val$range);
                }
                return ImmutableRangeSet.access$000(this.this$0).get(n + this.val$fromIndex);
            }
            
            @Override
            boolean isPartialView() {
                return true;
            }
            
            @Override
            public Object get(final int n) {
                return this.get(n);
            }
        };
    }
    
    @Override
    public ImmutableRangeSet subRangeSet(final Range range) {
        if (!this.isEmpty()) {
            final Range span = this.span();
            if (range.encloses(span)) {
                return this;
            }
            if (range.isConnected(span)) {
                return new ImmutableRangeSet(this.intersectRanges(range));
            }
        }
        return of();
    }
    
    public ImmutableSortedSet asSet(final DiscreteDomain discreteDomain) {
        Preconditions.checkNotNull(discreteDomain);
        if (this.isEmpty()) {
            return ImmutableSortedSet.of();
        }
        final Range canonical = this.span().canonical(discreteDomain);
        if (!canonical.hasLowerBound()) {
            throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded below");
        }
        if (!canonical.hasUpperBound()) {
            discreteDomain.maxValue();
        }
        return new AsSet(discreteDomain);
    }
    
    boolean isPartialView() {
        return this.ranges.isPartialView();
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    Object writeReplace() {
        return new SerializedForm(this.ranges);
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }
    
    @Override
    public boolean enclosesAll(final RangeSet set) {
        return super.enclosesAll(set);
    }
    
    @Override
    public void clear() {
        super.clear();
    }
    
    @Override
    public boolean contains(final Comparable comparable) {
        return super.contains(comparable);
    }
    
    @Override
    public RangeSet subRangeSet(final Range range) {
        return this.subRangeSet(range);
    }
    
    @Override
    public RangeSet complement() {
        return this.complement();
    }
    
    @Override
    public Set asRanges() {
        return this.asRanges();
    }
    
    static ImmutableList access$000(final ImmutableRangeSet set) {
        return set.ranges;
    }
    
    static {
        EMPTY = new ImmutableRangeSet(ImmutableList.of());
        ALL = new ImmutableRangeSet(ImmutableList.of(Range.all()));
    }
    
    private static final class SerializedForm implements Serializable
    {
        private final ImmutableList ranges;
        
        SerializedForm(final ImmutableList ranges) {
            this.ranges = ranges;
        }
        
        Object readResolve() {
            if (this.ranges.isEmpty()) {
                return ImmutableRangeSet.of();
            }
            if (this.ranges.equals(ImmutableList.of(Range.all()))) {
                return ImmutableRangeSet.all();
            }
            return new ImmutableRangeSet(this.ranges);
        }
    }
    
    public static class Builder
    {
        private final RangeSet rangeSet;
        
        public Builder() {
            this.rangeSet = TreeRangeSet.create();
        }
        
        public Builder add(final Range range) {
            if (range.isEmpty()) {
                throw new IllegalArgumentException("range must not be empty, but was " + range);
            }
            if (!this.rangeSet.complement().encloses(range)) {
                for (final Range range2 : this.rangeSet.asRanges()) {
                    Preconditions.checkArgument(!range2.isConnected(range) || range2.intersection(range).isEmpty(), "Ranges may not overlap, but received %s and %s", range2, range);
                }
                throw new AssertionError((Object)"should have thrown an IAE above");
            }
            this.rangeSet.add(range);
            return this;
        }
        
        public Builder addAll(final RangeSet set) {
            final Iterator<Range> iterator = set.asRanges().iterator();
            while (iterator.hasNext()) {
                this.add(iterator.next());
            }
            return this;
        }
        
        public ImmutableRangeSet build() {
            return ImmutableRangeSet.copyOf(this.rangeSet);
        }
    }
    
    private static class AsSetSerializedForm implements Serializable
    {
        private final ImmutableList ranges;
        private final DiscreteDomain domain;
        
        AsSetSerializedForm(final ImmutableList ranges, final DiscreteDomain domain) {
            this.ranges = ranges;
            this.domain = domain;
        }
        
        Object readResolve() {
            return new ImmutableRangeSet(this.ranges).asSet(this.domain);
        }
    }
    
    private final class AsSet extends ImmutableSortedSet
    {
        private final DiscreteDomain domain;
        private transient Integer size;
        final ImmutableRangeSet this$0;
        
        AsSet(final ImmutableRangeSet this$0, final DiscreteDomain domain) {
            this.this$0 = this$0;
            super(Ordering.natural());
            this.domain = domain;
        }
        
        @Override
        public int size() {
            Integer size = this.size;
            if (size == null) {
                long n = 0L;
                final Iterator iterator = ImmutableRangeSet.access$000(this.this$0).iterator();
                while (iterator.hasNext()) {
                    n += ContiguousSet.create(iterator.next(), this.domain).size();
                    if (n >= 2147483647L) {
                        break;
                    }
                }
                final Integer value = Ints.saturatedCast(n);
                this.size = value;
                size = value;
            }
            return size;
        }
        
        @Override
        public UnmodifiableIterator iterator() {
            return new AbstractIterator() {
                final Iterator rangeItr = ImmutableRangeSet.access$000(this.this$1.this$0).iterator();
                Iterator elemItr = Iterators.emptyIterator();
                final AsSet this$1;
                
                @Override
                protected Comparable computeNext() {
                    while (!this.elemItr.hasNext()) {
                        if (!this.rangeItr.hasNext()) {
                            return (Comparable)this.endOfData();
                        }
                        this.elemItr = ContiguousSet.create(this.rangeItr.next(), AsSet.access$100(this.this$1)).iterator();
                    }
                    return this.elemItr.next();
                }
                
                @Override
                protected Object computeNext() {
                    return this.computeNext();
                }
            };
        }
        
        @GwtIncompatible("NavigableSet")
        @Override
        public UnmodifiableIterator descendingIterator() {
            return new AbstractIterator() {
                final Iterator rangeItr = ImmutableRangeSet.access$000(this.this$1.this$0).reverse().iterator();
                Iterator elemItr = Iterators.emptyIterator();
                final AsSet this$1;
                
                @Override
                protected Comparable computeNext() {
                    while (!this.elemItr.hasNext()) {
                        if (!this.rangeItr.hasNext()) {
                            return (Comparable)this.endOfData();
                        }
                        this.elemItr = ContiguousSet.create(this.rangeItr.next(), AsSet.access$100(this.this$1)).descendingIterator();
                    }
                    return this.elemItr.next();
                }
                
                @Override
                protected Object computeNext() {
                    return this.computeNext();
                }
            };
        }
        
        ImmutableSortedSet subSet(final Range range) {
            return this.this$0.subRangeSet(range).asSet(this.domain);
        }
        
        ImmutableSortedSet headSetImpl(final Comparable comparable, final boolean b) {
            return this.subSet(Range.upTo(comparable, BoundType.forBoolean(b)));
        }
        
        ImmutableSortedSet subSetImpl(final Comparable comparable, final boolean b, final Comparable comparable2, final boolean b2) {
            if (!b && !b2 && Range.compareOrThrow(comparable, comparable2) == 0) {
                return ImmutableSortedSet.of();
            }
            return this.subSet(Range.range(comparable, BoundType.forBoolean(b), comparable2, BoundType.forBoolean(b2)));
        }
        
        ImmutableSortedSet tailSetImpl(final Comparable comparable, final boolean b) {
            return this.subSet(Range.downTo(comparable, BoundType.forBoolean(b)));
        }
        
        @Override
        int indexOf(final Object o) {
            if (o == null) {
                final Comparable comparable = (Comparable)o;
                long n = 0L;
                for (final Range range : ImmutableRangeSet.access$000(this.this$0)) {
                    if (range.contains(comparable)) {
                        return Ints.saturatedCast(n + ContiguousSet.create(range, this.domain).indexOf(comparable));
                    }
                    n += ContiguousSet.create(range, this.domain).size();
                }
                throw new AssertionError((Object)"impossible");
            }
            return -1;
        }
        
        @Override
        boolean isPartialView() {
            return ImmutableRangeSet.access$000(this.this$0).isPartialView();
        }
        
        @Override
        public String toString() {
            return ImmutableRangeSet.access$000(this.this$0).toString();
        }
        
        @Override
        Object writeReplace() {
            return new AsSetSerializedForm(ImmutableRangeSet.access$000(this.this$0), this.domain);
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
        public Iterator descendingIterator() {
            return this.descendingIterator();
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
        
        static DiscreteDomain access$100(final AsSet set) {
            return set.domain;
        }
    }
    
    private final class ComplementRanges extends ImmutableList
    {
        private final boolean positiveBoundedBelow;
        private final boolean positiveBoundedAbove;
        private final int size;
        final ImmutableRangeSet this$0;
        
        ComplementRanges(final ImmutableRangeSet this$0) {
            this.this$0 = this$0;
            this.positiveBoundedBelow = ImmutableRangeSet.access$000(this$0).get(0).hasLowerBound();
            this.positiveBoundedAbove = ((Range)Iterables.getLast(ImmutableRangeSet.access$000(this$0))).hasUpperBound();
            int size = ImmutableRangeSet.access$000(this$0).size() - 1;
            if (this.positiveBoundedBelow) {
                ++size;
            }
            if (this.positiveBoundedAbove) {
                ++size;
            }
            this.size = size;
        }
        
        @Override
        public int size() {
            return this.size;
        }
        
        @Override
        public Range get(final int n) {
            Preconditions.checkElementIndex(n, this.size);
            Cut upperBound;
            if (this.positiveBoundedBelow) {
                upperBound = ((n == 0) ? Cut.belowAll() : ImmutableRangeSet.access$000(this.this$0).get(n - 1).upperBound);
            }
            else {
                upperBound = ImmutableRangeSet.access$000(this.this$0).get(n).upperBound;
            }
            Cut cut;
            if (this.positiveBoundedAbove && n == this.size - 1) {
                cut = Cut.aboveAll();
            }
            else {
                cut = ImmutableRangeSet.access$000(this.this$0).get(n + (this.positiveBoundedBelow ? 0 : 1)).lowerBound;
            }
            return Range.create(upperBound, cut);
        }
        
        @Override
        boolean isPartialView() {
            return true;
        }
        
        @Override
        public Object get(final int n) {
            return this.get(n);
        }
    }
}
