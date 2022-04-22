package com.google.common.collect;

import javax.annotation.*;
import java.util.*;

abstract class AbstractNavigableMap extends AbstractMap implements NavigableMap
{
    @Nullable
    @Override
    public abstract Object get(@Nullable final Object p0);
    
    @Nullable
    @Override
    public Map.Entry firstEntry() {
        return (Map.Entry)Iterators.getNext(this.entryIterator(), null);
    }
    
    @Nullable
    @Override
    public Map.Entry lastEntry() {
        return (Map.Entry)Iterators.getNext(this.descendingEntryIterator(), null);
    }
    
    @Nullable
    @Override
    public Map.Entry pollFirstEntry() {
        return (Map.Entry)Iterators.pollNext(this.entryIterator());
    }
    
    @Nullable
    @Override
    public Map.Entry pollLastEntry() {
        return (Map.Entry)Iterators.pollNext(this.descendingEntryIterator());
    }
    
    @Override
    public Object firstKey() {
        final Map.Entry firstEntry = this.firstEntry();
        if (firstEntry == null) {
            throw new NoSuchElementException();
        }
        return firstEntry.getKey();
    }
    
    @Override
    public Object lastKey() {
        final Map.Entry lastEntry = this.lastEntry();
        if (lastEntry == null) {
            throw new NoSuchElementException();
        }
        return lastEntry.getKey();
    }
    
    @Nullable
    @Override
    public Map.Entry lowerEntry(final Object o) {
        return this.headMap(o, false).lastEntry();
    }
    
    @Nullable
    @Override
    public Map.Entry floorEntry(final Object o) {
        return this.headMap(o, true).lastEntry();
    }
    
    @Nullable
    @Override
    public Map.Entry ceilingEntry(final Object o) {
        return this.tailMap(o, true).firstEntry();
    }
    
    @Nullable
    @Override
    public Map.Entry higherEntry(final Object o) {
        return this.tailMap(o, false).firstEntry();
    }
    
    @Override
    public Object lowerKey(final Object o) {
        return Maps.keyOrNull(this.lowerEntry(o));
    }
    
    @Override
    public Object floorKey(final Object o) {
        return Maps.keyOrNull(this.floorEntry(o));
    }
    
    @Override
    public Object ceilingKey(final Object o) {
        return Maps.keyOrNull(this.ceilingEntry(o));
    }
    
    @Override
    public Object higherKey(final Object o) {
        return Maps.keyOrNull(this.higherEntry(o));
    }
    
    abstract Iterator entryIterator();
    
    abstract Iterator descendingEntryIterator();
    
    @Override
    public SortedMap subMap(final Object o, final Object o2) {
        return this.subMap(o, true, o2, false);
    }
    
    @Override
    public SortedMap headMap(final Object o) {
        return this.headMap(o, false);
    }
    
    @Override
    public SortedMap tailMap(final Object o) {
        return this.tailMap(o, true);
    }
    
    @Override
    public NavigableSet navigableKeySet() {
        return new Maps.NavigableKeySet(this);
    }
    
    @Override
    public Set keySet() {
        return this.navigableKeySet();
    }
    
    @Override
    public abstract int size();
    
    @Override
    public Set entrySet() {
        return new Maps.EntrySet() {
            final AbstractNavigableMap this$0;
            
            @Override
            Map map() {
                return this.this$0;
            }
            
            @Override
            public Iterator iterator() {
                return this.this$0.entryIterator();
            }
        };
    }
    
    @Override
    public NavigableSet descendingKeySet() {
        return this.descendingMap().navigableKeySet();
    }
    
    @Override
    public NavigableMap descendingMap() {
        return new DescendingMap(null);
    }
    
    private final class DescendingMap extends Maps.DescendingMap
    {
        final AbstractNavigableMap this$0;
        
        private DescendingMap(final AbstractNavigableMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        NavigableMap forward() {
            return this.this$0;
        }
        
        @Override
        Iterator entryIterator() {
            return this.this$0.descendingEntryIterator();
        }
        
        DescendingMap(final AbstractNavigableMap abstractNavigableMap, final AbstractNavigableMap$1 entrySet) {
            this(abstractNavigableMap);
        }
    }
}
