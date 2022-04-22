package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.base.*;
import java.util.*;

@Beta
@GwtIncompatible("NavigableMap")
public final class TreeRangeMap implements RangeMap
{
    private final NavigableMap entriesByLowerBound;
    private static final RangeMap EMPTY_SUB_RANGE_MAP;
    
    public static TreeRangeMap create() {
        return new TreeRangeMap();
    }
    
    private TreeRangeMap() {
        this.entriesByLowerBound = Maps.newTreeMap();
    }
    
    @Nullable
    @Override
    public Object get(final Comparable comparable) {
        final Map.Entry entry = this.getEntry(comparable);
        return (entry == null) ? null : entry.getValue();
    }
    
    @Nullable
    @Override
    public Map.Entry getEntry(final Comparable comparable) {
        final Map.Entry<Cut, RangeMapEntry> floorEntry = this.entriesByLowerBound.floorEntry(Cut.belowValue(comparable));
        if (floorEntry != null && floorEntry.getValue().contains(comparable)) {
            return floorEntry.getValue();
        }
        return null;
    }
    
    @Override
    public void put(final Range range, final Object o) {
        if (!range.isEmpty()) {
            Preconditions.checkNotNull(o);
            this.remove(range);
            this.entriesByLowerBound.put(range.lowerBound, new RangeMapEntry(range, o));
        }
    }
    
    @Override
    public void putAll(final RangeMap rangeMap) {
        for (final Map.Entry<Range, V> entry : rangeMap.asMapOfRanges().entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public void clear() {
        this.entriesByLowerBound.clear();
    }
    
    @Override
    public Range span() {
        final Map.Entry<K, RangeMapEntry> firstEntry = this.entriesByLowerBound.firstEntry();
        final Map.Entry<K, RangeMapEntry> lastEntry = this.entriesByLowerBound.lastEntry();
        if (firstEntry == null) {
            throw new NoSuchElementException();
        }
        return Range.create(firstEntry.getValue().getKey().lowerBound, lastEntry.getValue().getKey().upperBound);
    }
    
    private void putRangeMapEntry(final Cut cut, final Cut cut2, final Object o) {
        this.entriesByLowerBound.put(cut, new RangeMapEntry(cut, cut2, o));
    }
    
    @Override
    public void remove(final Range range) {
        if (range.isEmpty()) {
            return;
        }
        final Map.Entry<Cut, RangeMapEntry> lowerEntry = this.entriesByLowerBound.lowerEntry(range.lowerBound);
        if (lowerEntry != null) {
            final RangeMapEntry rangeMapEntry = lowerEntry.getValue();
            if (rangeMapEntry.getUpperBound().compareTo(range.lowerBound) > 0) {
                if (rangeMapEntry.getUpperBound().compareTo(range.upperBound) > 0) {
                    this.putRangeMapEntry(range.upperBound, rangeMapEntry.getUpperBound(), lowerEntry.getValue().getValue());
                }
                this.putRangeMapEntry(rangeMapEntry.getLowerBound(), range.lowerBound, lowerEntry.getValue().getValue());
            }
        }
        final Map.Entry<Cut, RangeMapEntry> lowerEntry2 = this.entriesByLowerBound.lowerEntry(range.upperBound);
        if (lowerEntry2 != null) {
            final RangeMapEntry rangeMapEntry2 = lowerEntry2.getValue();
            if (rangeMapEntry2.getUpperBound().compareTo(range.upperBound) > 0) {
                this.putRangeMapEntry(range.upperBound, rangeMapEntry2.getUpperBound(), lowerEntry2.getValue().getValue());
                this.entriesByLowerBound.remove(range.lowerBound);
            }
        }
        this.entriesByLowerBound.subMap(range.lowerBound, range.upperBound).clear();
    }
    
    @Override
    public Map asMapOfRanges() {
        return new AsMapOfRanges(null);
    }
    
    @Override
    public RangeMap subRangeMap(final Range range) {
        if (range.equals(Range.all())) {
            return this;
        }
        return new SubRangeMap(range);
    }
    
    private RangeMap emptySubRangeMap() {
        return TreeRangeMap.EMPTY_SUB_RANGE_MAP;
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o instanceof RangeMap && this.asMapOfRanges().equals(((RangeMap)o).asMapOfRanges());
    }
    
    @Override
    public int hashCode() {
        return this.asMapOfRanges().hashCode();
    }
    
    @Override
    public String toString() {
        return this.entriesByLowerBound.values().toString();
    }
    
    static NavigableMap access$100(final TreeRangeMap treeRangeMap) {
        return treeRangeMap.entriesByLowerBound;
    }
    
    static RangeMap access$200(final TreeRangeMap treeRangeMap) {
        return treeRangeMap.emptySubRangeMap();
    }
    
    static {
        EMPTY_SUB_RANGE_MAP = new RangeMap() {
            @Nullable
            @Override
            public Object get(final Comparable comparable) {
                return null;
            }
            
            @Nullable
            @Override
            public Map.Entry getEntry(final Comparable comparable) {
                return null;
            }
            
            @Override
            public Range span() {
                throw new NoSuchElementException();
            }
            
            @Override
            public void put(final Range range, final Object o) {
                Preconditions.checkNotNull(range);
                throw new IllegalArgumentException("Cannot insert range " + range + " into an empty subRangeMap");
            }
            
            @Override
            public void putAll(final RangeMap rangeMap) {
                if (!rangeMap.asMapOfRanges().isEmpty()) {
                    throw new IllegalArgumentException("Cannot putAll(nonEmptyRangeMap) into an empty subRangeMap");
                }
            }
            
            @Override
            public void clear() {
            }
            
            @Override
            public void remove(final Range range) {
                Preconditions.checkNotNull(range);
            }
            
            @Override
            public Map asMapOfRanges() {
                return Collections.emptyMap();
            }
            
            @Override
            public RangeMap subRangeMap(final Range range) {
                Preconditions.checkNotNull(range);
                return this;
            }
        };
    }
    
    private class SubRangeMap implements RangeMap
    {
        private final Range subRange;
        final TreeRangeMap this$0;
        
        SubRangeMap(final TreeRangeMap this$0, final Range subRange) {
            this.this$0 = this$0;
            this.subRange = subRange;
        }
        
        @Nullable
        @Override
        public Object get(final Comparable comparable) {
            return this.subRange.contains(comparable) ? this.this$0.get(comparable) : null;
        }
        
        @Nullable
        @Override
        public Map.Entry getEntry(final Comparable comparable) {
            if (this.subRange.contains(comparable)) {
                final Map.Entry entry = this.this$0.getEntry(comparable);
                if (entry != null) {
                    return Maps.immutableEntry(entry.getKey().intersection(this.subRange), entry.getValue());
                }
            }
            return null;
        }
        
        @Override
        public Range span() {
            final Map.Entry<Cut, RangeMapEntry> floorEntry = TreeRangeMap.access$100(this.this$0).floorEntry(this.subRange.lowerBound);
            Cut lowerBound;
            if (floorEntry != null && floorEntry.getValue().getUpperBound().compareTo(this.subRange.lowerBound) > 0) {
                lowerBound = this.subRange.lowerBound;
            }
            else {
                lowerBound = TreeRangeMap.access$100(this.this$0).ceilingKey(this.subRange.lowerBound);
                if (lowerBound == null || lowerBound.compareTo(this.subRange.upperBound) >= 0) {
                    throw new NoSuchElementException();
                }
            }
            final Map.Entry<Cut, RangeMapEntry> lowerEntry = TreeRangeMap.access$100(this.this$0).lowerEntry(this.subRange.upperBound);
            if (lowerEntry == null) {
                throw new NoSuchElementException();
            }
            Cut cut;
            if (lowerEntry.getValue().getUpperBound().compareTo(this.subRange.upperBound) >= 0) {
                cut = this.subRange.upperBound;
            }
            else {
                cut = lowerEntry.getValue().getUpperBound();
            }
            return Range.create(lowerBound, cut);
        }
        
        @Override
        public void put(final Range range, final Object o) {
            Preconditions.checkArgument(this.subRange.encloses(range), "Cannot put range %s into a subRangeMap(%s)", range, this.subRange);
            this.this$0.put(range, o);
        }
        
        @Override
        public void putAll(final RangeMap rangeMap) {
            if (rangeMap.asMapOfRanges().isEmpty()) {
                return;
            }
            final Range span = rangeMap.span();
            Preconditions.checkArgument(this.subRange.encloses(span), "Cannot putAll rangeMap with span %s into a subRangeMap(%s)", span, this.subRange);
            this.this$0.putAll(rangeMap);
        }
        
        @Override
        public void clear() {
            this.this$0.remove(this.subRange);
        }
        
        @Override
        public void remove(final Range range) {
            if (range.isConnected(this.subRange)) {
                this.this$0.remove(range.intersection(this.subRange));
            }
        }
        
        @Override
        public RangeMap subRangeMap(final Range range) {
            if (!range.isConnected(this.subRange)) {
                return TreeRangeMap.access$200(this.this$0);
            }
            return this.this$0.subRangeMap(range.intersection(this.subRange));
        }
        
        @Override
        public Map asMapOfRanges() {
            return new SubRangeMapAsMap();
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof RangeMap && this.asMapOfRanges().equals(((RangeMap)o).asMapOfRanges());
        }
        
        @Override
        public int hashCode() {
            return this.asMapOfRanges().hashCode();
        }
        
        @Override
        public String toString() {
            return this.asMapOfRanges().toString();
        }
        
        static Range access$300(final SubRangeMap subRangeMap) {
            return subRangeMap.subRange;
        }
        
        class SubRangeMapAsMap extends AbstractMap
        {
            final SubRangeMap this$1;
            
            SubRangeMapAsMap(final SubRangeMap this$1) {
                this.this$1 = this$1;
            }
            
            @Override
            public boolean containsKey(final Object o) {
                return this.get(o) != null;
            }
            
            @Override
            public Object get(final Object o) {
                if (o instanceof Range) {
                    final Range range = (Range)o;
                    if (!SubRangeMap.access$300(this.this$1).encloses(range) || range.isEmpty()) {
                        return null;
                    }
                    RangeMapEntry rangeMapEntry = null;
                    if (range.lowerBound.compareTo(SubRangeMap.access$300(this.this$1).lowerBound) == 0) {
                        final Map.Entry<Cut, Object> floorEntry = TreeRangeMap.access$100(this.this$1.this$0).floorEntry(range.lowerBound);
                        if (floorEntry != null) {
                            rangeMapEntry = floorEntry.getValue();
                        }
                    }
                    else {
                        rangeMapEntry = (RangeMapEntry)TreeRangeMap.access$100(this.this$1.this$0).get(range.lowerBound);
                    }
                    if (rangeMapEntry != null && rangeMapEntry.getKey().isConnected(SubRangeMap.access$300(this.this$1)) && rangeMapEntry.getKey().intersection(SubRangeMap.access$300(this.this$1)).equals(range)) {
                        return rangeMapEntry.getValue();
                    }
                }
                return null;
            }
            
            @Override
            public Object remove(final Object o) {
                final Object value = this.get(o);
                if (value != null) {
                    this.this$1.this$0.remove((Range)o);
                    return value;
                }
                return null;
            }
            
            @Override
            public void clear() {
                this.this$1.clear();
            }
            
            private boolean removeEntryIf(final Predicate predicate) {
                final ArrayList arrayList = Lists.newArrayList();
                for (final Map.Entry<Object, V> entry : this.entrySet()) {
                    if (predicate.apply(entry)) {
                        arrayList.add(entry.getKey());
                    }
                }
                final Iterator<Range> iterator2 = arrayList.iterator();
                while (iterator2.hasNext()) {
                    this.this$1.this$0.remove(iterator2.next());
                }
                return !arrayList.isEmpty();
            }
            
            @Override
            public Set keySet() {
                return new Maps.KeySet((Map)this) {
                    final SubRangeMapAsMap this$2;
                    
                    @Override
                    public boolean remove(@Nullable final Object o) {
                        return this.this$2.remove(o) != null;
                    }
                    
                    @Override
                    public boolean retainAll(final Collection collection) {
                        return SubRangeMapAsMap.access$400(this.this$2, Predicates.compose(Predicates.not(Predicates.in(collection)), Maps.keyFunction()));
                    }
                };
            }
            
            @Override
            public Set entrySet() {
                return new Maps.EntrySet() {
                    final SubRangeMapAsMap this$2;
                    
                    @Override
                    Map map() {
                        return this.this$2;
                    }
                    
                    @Override
                    public Iterator iterator() {
                        if (SubRangeMap.access$300(this.this$2.this$1).isEmpty()) {
                            return Iterators.emptyIterator();
                        }
                        return new AbstractIterator((Iterator)TreeRangeMap.access$100(this.this$2.this$1.this$0).tailMap(Objects.firstNonNull(TreeRangeMap.access$100(this.this$2.this$1.this$0).floorKey(SubRangeMap.access$300(this.this$2.this$1).lowerBound), SubRangeMap.access$300(this.this$2.this$1).lowerBound), true).values().iterator()) {
                            final Iterator val$backingItr;
                            final TreeRangeMap$SubRangeMap$SubRangeMapAsMap$2 this$3;
                            
                            @Override
                            protected Map.Entry computeNext() {
                                while (this.val$backingItr.hasNext()) {
                                    final RangeMapEntry rangeMapEntry = this.val$backingItr.next();
                                    if (rangeMapEntry.getLowerBound().compareTo(SubRangeMap.access$300(this.this$3.this$2.this$1).upperBound) >= 0) {
                                        break;
                                    }
                                    if (rangeMapEntry.getUpperBound().compareTo(SubRangeMap.access$300(this.this$3.this$2.this$1).lowerBound) > 0) {
                                        return Maps.immutableEntry(rangeMapEntry.getKey().intersection(SubRangeMap.access$300(this.this$3.this$2.this$1)), rangeMapEntry.getValue());
                                    }
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
                    public boolean retainAll(final Collection collection) {
                        return SubRangeMapAsMap.access$400(this.this$2, Predicates.not(Predicates.in(collection)));
                    }
                    
                    @Override
                    public int size() {
                        return Iterators.size(this.iterator());
                    }
                    
                    @Override
                    public boolean isEmpty() {
                        return !this.iterator().hasNext();
                    }
                };
            }
            
            @Override
            public Collection values() {
                return new Maps.Values((Map)this) {
                    final SubRangeMapAsMap this$2;
                    
                    @Override
                    public boolean removeAll(final Collection collection) {
                        return SubRangeMapAsMap.access$400(this.this$2, Predicates.compose(Predicates.in(collection), Maps.valueFunction()));
                    }
                    
                    @Override
                    public boolean retainAll(final Collection collection) {
                        return SubRangeMapAsMap.access$400(this.this$2, Predicates.compose(Predicates.not(Predicates.in(collection)), Maps.valueFunction()));
                    }
                };
            }
            
            static boolean access$400(final SubRangeMapAsMap subRangeMapAsMap, final Predicate predicate) {
                return subRangeMapAsMap.removeEntryIf(predicate);
            }
        }
    }
    
    private static final class RangeMapEntry extends AbstractMapEntry
    {
        private final Range range;
        private final Object value;
        
        RangeMapEntry(final Cut cut, final Cut cut2, final Object o) {
            this(Range.create(cut, cut2), o);
        }
        
        RangeMapEntry(final Range range, final Object value) {
            this.range = range;
            this.value = value;
        }
        
        @Override
        public Range getKey() {
            return this.range;
        }
        
        @Override
        public Object getValue() {
            return this.value;
        }
        
        public boolean contains(final Comparable comparable) {
            return this.range.contains(comparable);
        }
        
        Cut getLowerBound() {
            return this.range.lowerBound;
        }
        
        Cut getUpperBound() {
            return this.range.upperBound;
        }
        
        @Override
        public Object getKey() {
            return this.getKey();
        }
    }
    
    private final class AsMapOfRanges extends AbstractMap
    {
        final TreeRangeMap this$0;
        
        private AsMapOfRanges(final TreeRangeMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public boolean containsKey(@Nullable final Object o) {
            return this.get(o) != null;
        }
        
        @Override
        public Object get(@Nullable final Object o) {
            if (o instanceof Range) {
                final Range range = (Range)o;
                final RangeMapEntry rangeMapEntry = (RangeMapEntry)TreeRangeMap.access$100(this.this$0).get(range.lowerBound);
                if (rangeMapEntry != null && rangeMapEntry.getKey().equals(range)) {
                    return rangeMapEntry.getValue();
                }
            }
            return null;
        }
        
        @Override
        public Set entrySet() {
            return new AbstractSet() {
                final AsMapOfRanges this$1;
                
                @Override
                public Iterator iterator() {
                    return TreeRangeMap.access$100(this.this$1.this$0).values().iterator();
                }
                
                @Override
                public int size() {
                    return TreeRangeMap.access$100(this.this$1.this$0).size();
                }
            };
        }
        
        AsMapOfRanges(final TreeRangeMap treeRangeMap, final TreeRangeMap$1 rangeMap) {
            this(treeRangeMap);
        }
    }
}
