package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;
import com.google.common.base.*;

@Beta
@GwtIncompatible("uses NavigableMap")
public class TreeRangeSet extends AbstractRangeSet
{
    @VisibleForTesting
    final NavigableMap rangesByLowerBound;
    private transient Set asRanges;
    private transient RangeSet complement;
    
    public static TreeRangeSet create() {
        return new TreeRangeSet(new TreeMap());
    }
    
    public static TreeRangeSet create(final RangeSet set) {
        final TreeRangeSet create = create();
        create.addAll(set);
        return create;
    }
    
    private TreeRangeSet(final NavigableMap rangesByLowerBound) {
        this.rangesByLowerBound = rangesByLowerBound;
    }
    
    @Override
    public Set asRanges() {
        final Set asRanges = this.asRanges;
        return (asRanges == null) ? (this.asRanges = new AsRanges()) : asRanges;
    }
    
    @Nullable
    @Override
    public Range rangeContaining(final Comparable comparable) {
        Preconditions.checkNotNull(comparable);
        final Map.Entry<Cut, Range> floorEntry = this.rangesByLowerBound.floorEntry(Cut.belowValue(comparable));
        if (floorEntry != null && floorEntry.getValue().contains(comparable)) {
            return floorEntry.getValue();
        }
        return null;
    }
    
    @Override
    public boolean encloses(final Range range) {
        Preconditions.checkNotNull(range);
        final Map.Entry<Cut, Range> floorEntry = this.rangesByLowerBound.floorEntry(range.lowerBound);
        return floorEntry != null && floorEntry.getValue().encloses(range);
    }
    
    @Nullable
    private Range rangeEnclosing(final Range range) {
        Preconditions.checkNotNull(range);
        final Map.Entry<Cut, Range> floorEntry = this.rangesByLowerBound.floorEntry(range.lowerBound);
        return (floorEntry != null && floorEntry.getValue().encloses(range)) ? floorEntry.getValue() : null;
    }
    
    @Override
    public Range span() {
        final Map.Entry<K, Range> firstEntry = this.rangesByLowerBound.firstEntry();
        final Map.Entry<K, Range> lastEntry = this.rangesByLowerBound.lastEntry();
        if (firstEntry == null) {
            throw new NoSuchElementException();
        }
        return Range.create(firstEntry.getValue().lowerBound, lastEntry.getValue().upperBound);
    }
    
    @Override
    public void add(final Range range) {
        Preconditions.checkNotNull(range);
        if (range.isEmpty()) {
            return;
        }
        Cut cut = range.lowerBound;
        Cut cut2 = range.upperBound;
        final Map.Entry<Cut, Object> lowerEntry = this.rangesByLowerBound.lowerEntry(cut);
        if (lowerEntry != null) {
            final Range range2 = lowerEntry.getValue();
            if (range2.upperBound.compareTo(cut) >= 0) {
                if (range2.upperBound.compareTo(cut2) >= 0) {
                    cut2 = range2.upperBound;
                }
                cut = range2.lowerBound;
            }
        }
        final Map.Entry<Cut, Object> floorEntry = this.rangesByLowerBound.floorEntry(cut2);
        if (floorEntry != null) {
            final Range range3 = floorEntry.getValue();
            if (range3.upperBound.compareTo(cut2) >= 0) {
                cut2 = range3.upperBound;
            }
        }
        this.rangesByLowerBound.subMap(cut, cut2).clear();
        this.replaceRangeWithSameLowerBound(Range.create(cut, cut2));
    }
    
    @Override
    public void remove(final Range range) {
        Preconditions.checkNotNull(range);
        if (range.isEmpty()) {
            return;
        }
        final Map.Entry<Cut, Range> lowerEntry = this.rangesByLowerBound.lowerEntry(range.lowerBound);
        if (lowerEntry != null) {
            final Range range2 = lowerEntry.getValue();
            if (range2.upperBound.compareTo(range.lowerBound) >= 0) {
                if (range.hasUpperBound() && range2.upperBound.compareTo(range.upperBound) >= 0) {
                    this.replaceRangeWithSameLowerBound(Range.create(range.upperBound, range2.upperBound));
                }
                this.replaceRangeWithSameLowerBound(Range.create(range2.lowerBound, range.lowerBound));
            }
        }
        final Map.Entry<Cut, Range> floorEntry = this.rangesByLowerBound.floorEntry(range.upperBound);
        if (floorEntry != null) {
            final Range range3 = floorEntry.getValue();
            if (range.hasUpperBound() && range3.upperBound.compareTo(range.upperBound) >= 0) {
                this.replaceRangeWithSameLowerBound(Range.create(range.upperBound, range3.upperBound));
            }
        }
        this.rangesByLowerBound.subMap(range.lowerBound, range.upperBound).clear();
    }
    
    private void replaceRangeWithSameLowerBound(final Range range) {
        if (range.isEmpty()) {
            this.rangesByLowerBound.remove(range.lowerBound);
        }
        else {
            this.rangesByLowerBound.put(range.lowerBound, range);
        }
    }
    
    @Override
    public RangeSet complement() {
        final RangeSet complement = this.complement;
        return (complement == null) ? (this.complement = new Complement()) : complement;
    }
    
    @Override
    public RangeSet subRangeSet(final Range range) {
        return range.equals(Range.all()) ? this : new SubRangeSet(range);
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }
    
    @Override
    public void removeAll(final RangeSet set) {
        super.removeAll(set);
    }
    
    @Override
    public void addAll(final RangeSet set) {
        super.addAll(set);
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
    public boolean isEmpty() {
        return super.isEmpty();
    }
    
    @Override
    public boolean contains(final Comparable comparable) {
        return super.contains(comparable);
    }
    
    TreeRangeSet(final NavigableMap navigableMap, final TreeRangeSet$1 object) {
        this(navigableMap);
    }
    
    static Range access$600(final TreeRangeSet set, final Range range) {
        return set.rangeEnclosing(range);
    }
    
    private final class SubRangeSet extends TreeRangeSet
    {
        private final Range restriction;
        final TreeRangeSet this$0;
        
        SubRangeSet(final TreeRangeSet this$0, final Range restriction) {
            this.this$0 = this$0;
            super(new SubRangeSetRangesByLowerBound(Range.all(), restriction, this$0.rangesByLowerBound, null), null);
            this.restriction = restriction;
        }
        
        @Override
        public boolean encloses(final Range range) {
            if (!this.restriction.isEmpty() && this.restriction.encloses(range)) {
                final Range access$600 = TreeRangeSet.access$600(this.this$0, range);
                return access$600 != null && !access$600.intersection(this.restriction).isEmpty();
            }
            return false;
        }
        
        @Nullable
        @Override
        public Range rangeContaining(final Comparable comparable) {
            if (!this.restriction.contains(comparable)) {
                return null;
            }
            final Range rangeContaining = this.this$0.rangeContaining(comparable);
            return (rangeContaining == null) ? null : rangeContaining.intersection(this.restriction);
        }
        
        @Override
        public void add(final Range range) {
            Preconditions.checkArgument(this.restriction.encloses(range), "Cannot add range %s to subRangeSet(%s)", range, this.restriction);
            super.add(range);
        }
        
        @Override
        public void remove(final Range range) {
            if (range.isConnected(this.restriction)) {
                this.this$0.remove(range.intersection(this.restriction));
            }
        }
        
        @Override
        public boolean contains(final Comparable comparable) {
            return this.restriction.contains(comparable) && this.this$0.contains(comparable);
        }
        
        @Override
        public void clear() {
            this.this$0.remove(this.restriction);
        }
        
        @Override
        public RangeSet subRangeSet(final Range range) {
            if (range.encloses(this.restriction)) {
                return this;
            }
            if (range.isConnected(this.restriction)) {
                return new SubRangeSet(this.restriction.intersection(range));
            }
            return ImmutableRangeSet.of();
        }
    }
    
    private static final class SubRangeSetRangesByLowerBound extends AbstractNavigableMap
    {
        private final Range lowerBoundWindow;
        private final Range restriction;
        private final NavigableMap rangesByLowerBound;
        private final NavigableMap rangesByUpperBound;
        
        private SubRangeSetRangesByLowerBound(final Range range, final Range range2, final NavigableMap navigableMap) {
            this.lowerBoundWindow = (Range)Preconditions.checkNotNull(range);
            this.restriction = (Range)Preconditions.checkNotNull(range2);
            this.rangesByLowerBound = (NavigableMap)Preconditions.checkNotNull(navigableMap);
            this.rangesByUpperBound = new RangesByUpperBound(navigableMap);
        }
        
        private NavigableMap subMap(final Range range) {
            if (!range.isConnected(this.lowerBoundWindow)) {
                return ImmutableSortedMap.of();
            }
            return new SubRangeSetRangesByLowerBound(this.lowerBoundWindow.intersection(range), this.restriction, this.rangesByLowerBound);
        }
        
        public NavigableMap subMap(final Cut cut, final boolean b, final Cut cut2, final boolean b2) {
            return this.subMap(Range.range(cut, BoundType.forBoolean(b), cut2, BoundType.forBoolean(b2)));
        }
        
        public NavigableMap headMap(final Cut cut, final boolean b) {
            return this.subMap(Range.upTo(cut, BoundType.forBoolean(b)));
        }
        
        public NavigableMap tailMap(final Cut cut, final boolean b) {
            return this.subMap(Range.downTo(cut, BoundType.forBoolean(b)));
        }
        
        @Override
        public Comparator comparator() {
            return Ordering.natural();
        }
        
        @Override
        public boolean containsKey(@Nullable final Object o) {
            return this.get(o) != null;
        }
        
        @Nullable
        @Override
        public Range get(@Nullable final Object o) {
            if (o instanceof Cut) {
                final Cut cut = (Cut)o;
                if (!this.lowerBoundWindow.contains(cut) || cut.compareTo(this.restriction.lowerBound) < 0 || cut.compareTo(this.restriction.upperBound) >= 0) {
                    return null;
                }
                if (cut.equals(this.restriction.lowerBound)) {
                    final Range range = (Range)Maps.valueOrNull(this.rangesByLowerBound.floorEntry(cut));
                    if (range != null && range.upperBound.compareTo(this.restriction.lowerBound) > 0) {
                        return range.intersection(this.restriction);
                    }
                }
                else {
                    final Range range2 = (Range)this.rangesByLowerBound.get(cut);
                    if (range2 != null) {
                        return range2.intersection(this.restriction);
                    }
                }
            }
            return null;
        }
        
        @Override
        Iterator entryIterator() {
            if (this.restriction.isEmpty()) {
                return Iterators.emptyIterator();
            }
            if (this.lowerBoundWindow.upperBound.isLessThan(this.restriction.lowerBound)) {
                return Iterators.emptyIterator();
            }
            Iterator<Object> iterator;
            if (this.lowerBoundWindow.lowerBound.isLessThan(this.restriction.lowerBound)) {
                iterator = this.rangesByUpperBound.tailMap(this.restriction.lowerBound, false).values().iterator();
            }
            else {
                iterator = this.rangesByLowerBound.tailMap(this.lowerBoundWindow.lowerBound.endpoint(), this.lowerBoundWindow.lowerBoundType() == BoundType.CLOSED).values().iterator();
            }
            return new AbstractIterator((Iterator)iterator, (Cut)Ordering.natural().min(this.lowerBoundWindow.upperBound, Cut.belowValue(this.restriction.upperBound))) {
                final Iterator val$completeRangeItr;
                final Cut val$upperBoundOnLowerBounds;
                final SubRangeSetRangesByLowerBound this$0;
                
                @Override
                protected Map.Entry computeNext() {
                    if (!this.val$completeRangeItr.hasNext()) {
                        return (Map.Entry)this.endOfData();
                    }
                    final Range range = this.val$completeRangeItr.next();
                    if (this.val$upperBoundOnLowerBounds.isLessThan(range.lowerBound)) {
                        return (Map.Entry)this.endOfData();
                    }
                    final Range intersection = range.intersection(SubRangeSetRangesByLowerBound.access$300(this.this$0));
                    return Maps.immutableEntry(intersection.lowerBound, intersection);
                }
                
                @Override
                protected Object computeNext() {
                    return this.computeNext();
                }
            };
        }
        
        @Override
        Iterator descendingEntryIterator() {
            if (this.restriction.isEmpty()) {
                return Iterators.emptyIterator();
            }
            final Cut cut = (Cut)Ordering.natural().min(this.lowerBoundWindow.upperBound, Cut.belowValue(this.restriction.upperBound));
            return new AbstractIterator((Iterator)this.rangesByLowerBound.headMap(cut.endpoint(), cut.typeAsUpperBound() == BoundType.CLOSED).descendingMap().values().iterator()) {
                final Iterator val$completeRangeItr;
                final SubRangeSetRangesByLowerBound this$0;
                
                @Override
                protected Map.Entry computeNext() {
                    if (!this.val$completeRangeItr.hasNext()) {
                        return (Map.Entry)this.endOfData();
                    }
                    final Range range = this.val$completeRangeItr.next();
                    if (SubRangeSetRangesByLowerBound.access$300(this.this$0).lowerBound.compareTo(range.upperBound) >= 0) {
                        return (Map.Entry)this.endOfData();
                    }
                    final Range intersection = range.intersection(SubRangeSetRangesByLowerBound.access$300(this.this$0));
                    if (SubRangeSetRangesByLowerBound.access$400(this.this$0).contains(intersection.lowerBound)) {
                        return Maps.immutableEntry(intersection.lowerBound, intersection);
                    }
                    return (Map.Entry)this.endOfData();
                }
                
                @Override
                protected Object computeNext() {
                    return this.computeNext();
                }
            };
        }
        
        @Override
        public int size() {
            return Iterators.size(this.entryIterator());
        }
        
        @Override
        public Object get(final Object o) {
            return this.get(o);
        }
        
        @Override
        public NavigableMap tailMap(final Object o, final boolean b) {
            return this.tailMap((Cut)o, b);
        }
        
        @Override
        public NavigableMap headMap(final Object o, final boolean b) {
            return this.headMap((Cut)o, b);
        }
        
        @Override
        public NavigableMap subMap(final Object o, final boolean b, final Object o2, final boolean b2) {
            return this.subMap((Cut)o, b, (Cut)o2, b2);
        }
        
        static Range access$300(final SubRangeSetRangesByLowerBound subRangeSetRangesByLowerBound) {
            return subRangeSetRangesByLowerBound.restriction;
        }
        
        static Range access$400(final SubRangeSetRangesByLowerBound subRangeSetRangesByLowerBound) {
            return subRangeSetRangesByLowerBound.lowerBoundWindow;
        }
        
        SubRangeSetRangesByLowerBound(final Range range, final Range range2, final NavigableMap navigableMap, final TreeRangeSet$1 object) {
            this(range, range2, navigableMap);
        }
    }
    
    @VisibleForTesting
    static final class RangesByUpperBound extends AbstractNavigableMap
    {
        private final NavigableMap rangesByLowerBound;
        private final Range upperBoundWindow;
        
        RangesByUpperBound(final NavigableMap rangesByLowerBound) {
            this.rangesByLowerBound = rangesByLowerBound;
            this.upperBoundWindow = Range.all();
        }
        
        private RangesByUpperBound(final NavigableMap rangesByLowerBound, final Range upperBoundWindow) {
            this.rangesByLowerBound = rangesByLowerBound;
            this.upperBoundWindow = upperBoundWindow;
        }
        
        private NavigableMap subMap(final Range range) {
            if (range.isConnected(this.upperBoundWindow)) {
                return new RangesByUpperBound(this.rangesByLowerBound, range.intersection(this.upperBoundWindow));
            }
            return ImmutableSortedMap.of();
        }
        
        public NavigableMap subMap(final Cut cut, final boolean b, final Cut cut2, final boolean b2) {
            return this.subMap(Range.range(cut, BoundType.forBoolean(b), cut2, BoundType.forBoolean(b2)));
        }
        
        public NavigableMap headMap(final Cut cut, final boolean b) {
            return this.subMap(Range.upTo(cut, BoundType.forBoolean(b)));
        }
        
        public NavigableMap tailMap(final Cut cut, final boolean b) {
            return this.subMap(Range.downTo(cut, BoundType.forBoolean(b)));
        }
        
        @Override
        public Comparator comparator() {
            return Ordering.natural();
        }
        
        @Override
        public boolean containsKey(@Nullable final Object o) {
            return this.get(o) != null;
        }
        
        @Override
        public Range get(@Nullable final Object o) {
            if (o instanceof Cut) {
                final Cut cut = (Cut)o;
                if (!this.upperBoundWindow.contains(cut)) {
                    return null;
                }
                final Map.Entry<Cut, Object> lowerEntry = this.rangesByLowerBound.lowerEntry(cut);
                if (lowerEntry != null && lowerEntry.getValue().upperBound.equals(cut)) {
                    return lowerEntry.getValue();
                }
            }
            return null;
        }
        
        @Override
        Iterator entryIterator() {
            Iterator<Object> iterator;
            if (!this.upperBoundWindow.hasLowerBound()) {
                iterator = (Iterator<Object>)this.rangesByLowerBound.values().iterator();
            }
            else {
                final Map.Entry<Comparable, Range> lowerEntry = this.rangesByLowerBound.lowerEntry(this.upperBoundWindow.lowerEndpoint());
                if (lowerEntry == null) {
                    iterator = (Iterator<Object>)this.rangesByLowerBound.values().iterator();
                }
                else if (this.upperBoundWindow.lowerBound.isLessThan(lowerEntry.getValue().upperBound)) {
                    iterator = this.rangesByLowerBound.tailMap(lowerEntry.getKey(), true).values().iterator();
                }
                else {
                    iterator = this.rangesByLowerBound.tailMap(this.upperBoundWindow.lowerEndpoint(), true).values().iterator();
                }
            }
            return new AbstractIterator((Iterator)iterator) {
                final Iterator val$backingItr;
                final RangesByUpperBound this$0;
                
                @Override
                protected Map.Entry computeNext() {
                    if (!this.val$backingItr.hasNext()) {
                        return (Map.Entry)this.endOfData();
                    }
                    final Range range = this.val$backingItr.next();
                    if (RangesByUpperBound.access$000(this.this$0).upperBound.isLessThan(range.upperBound)) {
                        return (Map.Entry)this.endOfData();
                    }
                    return Maps.immutableEntry(range.upperBound, range);
                }
                
                @Override
                protected Object computeNext() {
                    return this.computeNext();
                }
            };
        }
        
        @Override
        Iterator descendingEntryIterator() {
            Collection<Object> collection;
            if (this.upperBoundWindow.hasUpperBound()) {
                collection = this.rangesByLowerBound.headMap(this.upperBoundWindow.upperEndpoint(), false).descendingMap().values();
            }
            else {
                collection = (Collection<Object>)this.rangesByLowerBound.descendingMap().values();
            }
            final PeekingIterator peekingIterator = Iterators.peekingIterator(collection.iterator());
            if (peekingIterator.hasNext() && this.upperBoundWindow.upperBound.isLessThan(((Range)peekingIterator.peek()).upperBound)) {
                peekingIterator.next();
            }
            return new AbstractIterator(peekingIterator) {
                final PeekingIterator val$backingItr;
                final RangesByUpperBound this$0;
                
                @Override
                protected Map.Entry computeNext() {
                    if (!this.val$backingItr.hasNext()) {
                        return (Map.Entry)this.endOfData();
                    }
                    final Range range = (Range)this.val$backingItr.next();
                    return (Map.Entry)(RangesByUpperBound.access$000(this.this$0).lowerBound.isLessThan(range.upperBound) ? Maps.immutableEntry(range.upperBound, range) : this.endOfData());
                }
                
                @Override
                protected Object computeNext() {
                    return this.computeNext();
                }
            };
        }
        
        @Override
        public int size() {
            if (this.upperBoundWindow.equals(Range.all())) {
                return this.rangesByLowerBound.size();
            }
            return Iterators.size(this.entryIterator());
        }
        
        @Override
        public boolean isEmpty() {
            return this.upperBoundWindow.equals(Range.all()) ? this.rangesByLowerBound.isEmpty() : (!this.entryIterator().hasNext());
        }
        
        @Override
        public Object get(final Object o) {
            return this.get(o);
        }
        
        @Override
        public NavigableMap tailMap(final Object o, final boolean b) {
            return this.tailMap((Cut)o, b);
        }
        
        @Override
        public NavigableMap headMap(final Object o, final boolean b) {
            return this.headMap((Cut)o, b);
        }
        
        @Override
        public NavigableMap subMap(final Object o, final boolean b, final Object o2, final boolean b2) {
            return this.subMap((Cut)o, b, (Cut)o2, b2);
        }
        
        static Range access$000(final RangesByUpperBound rangesByUpperBound) {
            return rangesByUpperBound.upperBoundWindow;
        }
    }
    
    private final class Complement extends TreeRangeSet
    {
        final TreeRangeSet this$0;
        
        Complement(final TreeRangeSet this$0) {
            this.this$0 = this$0;
            super(new ComplementRangesByLowerBound(this$0.rangesByLowerBound), null);
        }
        
        @Override
        public void add(final Range range) {
            this.this$0.remove(range);
        }
        
        @Override
        public void remove(final Range range) {
            this.this$0.add(range);
        }
        
        @Override
        public boolean contains(final Comparable comparable) {
            return !this.this$0.contains(comparable);
        }
        
        @Override
        public RangeSet complement() {
            return this.this$0;
        }
    }
    
    private static final class ComplementRangesByLowerBound extends AbstractNavigableMap
    {
        private final NavigableMap positiveRangesByLowerBound;
        private final NavigableMap positiveRangesByUpperBound;
        private final Range complementLowerBoundWindow;
        
        ComplementRangesByLowerBound(final NavigableMap navigableMap) {
            this(navigableMap, Range.all());
        }
        
        private ComplementRangesByLowerBound(final NavigableMap positiveRangesByLowerBound, final Range complementLowerBoundWindow) {
            this.positiveRangesByLowerBound = positiveRangesByLowerBound;
            this.positiveRangesByUpperBound = new RangesByUpperBound(positiveRangesByLowerBound);
            this.complementLowerBoundWindow = complementLowerBoundWindow;
        }
        
        private NavigableMap subMap(Range intersection) {
            if (!this.complementLowerBoundWindow.isConnected(intersection)) {
                return ImmutableSortedMap.of();
            }
            intersection = intersection.intersection(this.complementLowerBoundWindow);
            return new ComplementRangesByLowerBound(this.positiveRangesByLowerBound, intersection);
        }
        
        public NavigableMap subMap(final Cut cut, final boolean b, final Cut cut2, final boolean b2) {
            return this.subMap(Range.range(cut, BoundType.forBoolean(b), cut2, BoundType.forBoolean(b2)));
        }
        
        public NavigableMap headMap(final Cut cut, final boolean b) {
            return this.subMap(Range.upTo(cut, BoundType.forBoolean(b)));
        }
        
        public NavigableMap tailMap(final Cut cut, final boolean b) {
            return this.subMap(Range.downTo(cut, BoundType.forBoolean(b)));
        }
        
        @Override
        public Comparator comparator() {
            return Ordering.natural();
        }
        
        @Override
        Iterator entryIterator() {
            Collection<Object> collection;
            if (this.complementLowerBoundWindow.hasLowerBound()) {
                collection = this.positiveRangesByUpperBound.tailMap(this.complementLowerBoundWindow.lowerEndpoint(), this.complementLowerBoundWindow.lowerBoundType() == BoundType.CLOSED).values();
            }
            else {
                collection = (Collection<Object>)this.positiveRangesByUpperBound.values();
            }
            final PeekingIterator peekingIterator = Iterators.peekingIterator(collection.iterator());
            Cut cut;
            if (this.complementLowerBoundWindow.contains(Cut.belowAll()) && (!peekingIterator.hasNext() || ((Range)peekingIterator.peek()).lowerBound != Cut.belowAll())) {
                cut = Cut.belowAll();
            }
            else {
                if (!peekingIterator.hasNext()) {
                    return Iterators.emptyIterator();
                }
                cut = ((Range)peekingIterator.next()).upperBound;
            }
            return new AbstractIterator(cut, peekingIterator) {
                Cut nextComplementRangeLowerBound = this.val$firstComplementRangeLowerBound;
                final Cut val$firstComplementRangeLowerBound;
                final PeekingIterator val$positiveItr;
                final ComplementRangesByLowerBound this$0;
                
                @Override
                protected Map.Entry computeNext() {
                    if (ComplementRangesByLowerBound.access$100(this.this$0).upperBound.isLessThan(this.nextComplementRangeLowerBound) || this.nextComplementRangeLowerBound == Cut.aboveAll()) {
                        return (Map.Entry)this.endOfData();
                    }
                    Range range2;
                    if (this.val$positiveItr.hasNext()) {
                        final Range range = (Range)this.val$positiveItr.next();
                        range2 = Range.create(this.nextComplementRangeLowerBound, range.lowerBound);
                        this.nextComplementRangeLowerBound = range.upperBound;
                    }
                    else {
                        range2 = Range.create(this.nextComplementRangeLowerBound, Cut.aboveAll());
                        this.nextComplementRangeLowerBound = Cut.aboveAll();
                    }
                    return Maps.immutableEntry(range2.lowerBound, range2);
                }
                
                @Override
                protected Object computeNext() {
                    return this.computeNext();
                }
            };
        }
        
        @Override
        Iterator descendingEntryIterator() {
            final PeekingIterator peekingIterator = Iterators.peekingIterator(this.positiveRangesByUpperBound.headMap(this.complementLowerBoundWindow.hasUpperBound() ? ((Cut)this.complementLowerBoundWindow.upperEndpoint()) : Cut.aboveAll(), this.complementLowerBoundWindow.hasUpperBound() && this.complementLowerBoundWindow.upperBoundType() == BoundType.CLOSED).descendingMap().values().iterator());
            Cut cut;
            if (peekingIterator.hasNext()) {
                cut = ((((Range)peekingIterator.peek()).upperBound == Cut.aboveAll()) ? ((Range)peekingIterator.next()).lowerBound : this.positiveRangesByLowerBound.higherKey(((Range)peekingIterator.peek()).upperBound));
            }
            else {
                if (!this.complementLowerBoundWindow.contains(Cut.belowAll()) || this.positiveRangesByLowerBound.containsKey(Cut.belowAll())) {
                    return Iterators.emptyIterator();
                }
                cut = this.positiveRangesByLowerBound.higherKey(Cut.belowAll());
            }
            return new AbstractIterator((Cut)Objects.firstNonNull(cut, Cut.aboveAll()), peekingIterator) {
                Cut nextComplementRangeUpperBound = this.val$firstComplementRangeUpperBound;
                final Cut val$firstComplementRangeUpperBound;
                final PeekingIterator val$positiveItr;
                final ComplementRangesByLowerBound this$0;
                
                @Override
                protected Map.Entry computeNext() {
                    if (this.nextComplementRangeUpperBound == Cut.belowAll()) {
                        return (Map.Entry)this.endOfData();
                    }
                    if (this.val$positiveItr.hasNext()) {
                        final Range range = (Range)this.val$positiveItr.next();
                        final Range create = Range.create(range.upperBound, this.nextComplementRangeUpperBound);
                        this.nextComplementRangeUpperBound = range.lowerBound;
                        if (ComplementRangesByLowerBound.access$100(this.this$0).lowerBound.isLessThan(create.lowerBound)) {
                            return Maps.immutableEntry(create.lowerBound, create);
                        }
                    }
                    else if (ComplementRangesByLowerBound.access$100(this.this$0).lowerBound.isLessThan(Cut.belowAll())) {
                        final Range create2 = Range.create(Cut.belowAll(), this.nextComplementRangeUpperBound);
                        this.nextComplementRangeUpperBound = Cut.belowAll();
                        return Maps.immutableEntry(Cut.belowAll(), create2);
                    }
                    return (Map.Entry)this.endOfData();
                }
                
                @Override
                protected Object computeNext() {
                    return this.computeNext();
                }
            };
        }
        
        @Override
        public int size() {
            return Iterators.size(this.entryIterator());
        }
        
        @Nullable
        @Override
        public Range get(final Object o) {
            if (o instanceof Cut) {
                final Cut cut = (Cut)o;
                final Map.Entry firstEntry = this.tailMap(cut, true).firstEntry();
                if (firstEntry != null && firstEntry.getKey().equals(cut)) {
                    return firstEntry.getValue();
                }
            }
            return null;
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return this.get(o) != null;
        }
        
        @Override
        public Object get(final Object o) {
            return this.get(o);
        }
        
        @Override
        public NavigableMap tailMap(final Object o, final boolean b) {
            return this.tailMap((Cut)o, b);
        }
        
        @Override
        public NavigableMap headMap(final Object o, final boolean b) {
            return this.headMap((Cut)o, b);
        }
        
        @Override
        public NavigableMap subMap(final Object o, final boolean b, final Object o2, final boolean b2) {
            return this.subMap((Cut)o, b, (Cut)o2, b2);
        }
        
        static Range access$100(final ComplementRangesByLowerBound complementRangesByLowerBound) {
            return complementRangesByLowerBound.complementLowerBoundWindow;
        }
    }
    
    final class AsRanges extends ForwardingCollection implements Set
    {
        final TreeRangeSet this$0;
        
        AsRanges(final TreeRangeSet this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        protected Collection delegate() {
            return this.this$0.rangesByLowerBound.values();
        }
        
        @Override
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return Sets.equalsImpl(this, o);
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
}
