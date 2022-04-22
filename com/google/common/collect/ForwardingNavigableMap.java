package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

public abstract class ForwardingNavigableMap extends ForwardingSortedMap implements NavigableMap
{
    protected ForwardingNavigableMap() {
    }
    
    @Override
    protected abstract NavigableMap delegate();
    
    @Override
    public Map.Entry lowerEntry(final Object o) {
        return this.delegate().lowerEntry(o);
    }
    
    protected Map.Entry standardLowerEntry(final Object o) {
        return this.headMap(o, false).lastEntry();
    }
    
    @Override
    public Object lowerKey(final Object o) {
        return this.delegate().lowerKey(o);
    }
    
    protected Object standardLowerKey(final Object o) {
        return Maps.keyOrNull(this.lowerEntry(o));
    }
    
    @Override
    public Map.Entry floorEntry(final Object o) {
        return this.delegate().floorEntry(o);
    }
    
    protected Map.Entry standardFloorEntry(final Object o) {
        return this.headMap(o, true).lastEntry();
    }
    
    @Override
    public Object floorKey(final Object o) {
        return this.delegate().floorKey(o);
    }
    
    protected Object standardFloorKey(final Object o) {
        return Maps.keyOrNull(this.floorEntry(o));
    }
    
    @Override
    public Map.Entry ceilingEntry(final Object o) {
        return this.delegate().ceilingEntry(o);
    }
    
    protected Map.Entry standardCeilingEntry(final Object o) {
        return this.tailMap(o, true).firstEntry();
    }
    
    @Override
    public Object ceilingKey(final Object o) {
        return this.delegate().ceilingKey(o);
    }
    
    protected Object standardCeilingKey(final Object o) {
        return Maps.keyOrNull(this.ceilingEntry(o));
    }
    
    @Override
    public Map.Entry higherEntry(final Object o) {
        return this.delegate().higherEntry(o);
    }
    
    protected Map.Entry standardHigherEntry(final Object o) {
        return this.tailMap(o, false).firstEntry();
    }
    
    @Override
    public Object higherKey(final Object o) {
        return this.delegate().higherKey(o);
    }
    
    protected Object standardHigherKey(final Object o) {
        return Maps.keyOrNull(this.higherEntry(o));
    }
    
    @Override
    public Map.Entry firstEntry() {
        return this.delegate().firstEntry();
    }
    
    protected Map.Entry standardFirstEntry() {
        return (Map.Entry)Iterables.getFirst(this.entrySet(), null);
    }
    
    protected Object standardFirstKey() {
        final Map.Entry firstEntry = this.firstEntry();
        if (firstEntry == null) {
            throw new NoSuchElementException();
        }
        return firstEntry.getKey();
    }
    
    @Override
    public Map.Entry lastEntry() {
        return this.delegate().lastEntry();
    }
    
    protected Map.Entry standardLastEntry() {
        return (Map.Entry)Iterables.getFirst(this.descendingMap().entrySet(), null);
    }
    
    protected Object standardLastKey() {
        final Map.Entry lastEntry = this.lastEntry();
        if (lastEntry == null) {
            throw new NoSuchElementException();
        }
        return lastEntry.getKey();
    }
    
    @Override
    public Map.Entry pollFirstEntry() {
        return this.delegate().pollFirstEntry();
    }
    
    protected Map.Entry standardPollFirstEntry() {
        return (Map.Entry)Iterators.pollNext(this.entrySet().iterator());
    }
    
    @Override
    public Map.Entry pollLastEntry() {
        return this.delegate().pollLastEntry();
    }
    
    protected Map.Entry standardPollLastEntry() {
        return (Map.Entry)Iterators.pollNext(this.descendingMap().entrySet().iterator());
    }
    
    @Override
    public NavigableMap descendingMap() {
        return this.delegate().descendingMap();
    }
    
    @Override
    public NavigableSet navigableKeySet() {
        return this.delegate().navigableKeySet();
    }
    
    @Override
    public NavigableSet descendingKeySet() {
        return this.delegate().descendingKeySet();
    }
    
    @Beta
    protected NavigableSet standardDescendingKeySet() {
        return this.descendingMap().navigableKeySet();
    }
    
    @Override
    protected SortedMap standardSubMap(final Object o, final Object o2) {
        return this.subMap(o, true, o2, false);
    }
    
    @Override
    public NavigableMap subMap(final Object o, final boolean b, final Object o2, final boolean b2) {
        return this.delegate().subMap(o, b, o2, b2);
    }
    
    @Override
    public NavigableMap headMap(final Object o, final boolean b) {
        return this.delegate().headMap(o, b);
    }
    
    @Override
    public NavigableMap tailMap(final Object o, final boolean b) {
        return this.delegate().tailMap(o, b);
    }
    
    protected SortedMap standardHeadMap(final Object o) {
        return this.headMap(o, false);
    }
    
    protected SortedMap standardTailMap(final Object o) {
        return this.tailMap(o, true);
    }
    
    @Override
    protected SortedMap delegate() {
        return this.delegate();
    }
    
    @Override
    protected Map delegate() {
        return this.delegate();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
    
    @Beta
    protected class StandardNavigableKeySet extends Maps.NavigableKeySet
    {
        final ForwardingNavigableMap this$0;
        
        public StandardNavigableKeySet(final ForwardingNavigableMap this$0) {
            super(this.this$0 = this$0);
        }
    }
    
    @Beta
    protected class StandardDescendingMap extends Maps.DescendingMap
    {
        final ForwardingNavigableMap this$0;
        
        public StandardDescendingMap(final ForwardingNavigableMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        NavigableMap forward() {
            return this.this$0;
        }
        
        protected Iterator entryIterator() {
            return new Iterator() {
                private Map.Entry toRemove = null;
                private Map.Entry nextOrNull = this.this$1.forward().lastEntry();
                final StandardDescendingMap this$1;
                
                @Override
                public boolean hasNext() {
                    return this.nextOrNull != null;
                }
                
                @Override
                public Map.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    final Map.Entry nextOrNull = this.nextOrNull;
                    this.toRemove = this.nextOrNull;
                    this.nextOrNull = this.this$1.forward().lowerEntry(this.nextOrNull.getKey());
                    return nextOrNull;
                }
                
                @Override
                public void remove() {
                    CollectPreconditions.checkRemove(this.toRemove != null);
                    this.this$1.forward().remove(this.toRemove.getKey());
                    this.toRemove = null;
                }
                
                @Override
                public Object next() {
                    return this.next();
                }
            };
        }
    }
}
