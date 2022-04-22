package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.base.*;
import java.util.*;

@GwtCompatible(emulated = true)
final class RegularImmutableSortedMap extends ImmutableSortedMap
{
    private final transient RegularImmutableSortedSet keySet;
    private final transient ImmutableList valueList;
    
    RegularImmutableSortedMap(final RegularImmutableSortedSet keySet, final ImmutableList valueList) {
        this.keySet = keySet;
        this.valueList = valueList;
    }
    
    RegularImmutableSortedMap(final RegularImmutableSortedSet keySet, final ImmutableList valueList, final ImmutableSortedMap immutableSortedMap) {
        super(immutableSortedMap);
        this.keySet = keySet;
        this.valueList = valueList;
    }
    
    @Override
    ImmutableSet createEntrySet() {
        return new EntrySet(null);
    }
    
    @Override
    public ImmutableSortedSet keySet() {
        return this.keySet;
    }
    
    @Override
    public ImmutableCollection values() {
        return this.valueList;
    }
    
    @Override
    public Object get(@Nullable final Object o) {
        final int index = this.keySet.indexOf(o);
        return (index == -1) ? null : this.valueList.get(index);
    }
    
    private ImmutableSortedMap getSubMap(final int n, final int n2) {
        if (n == 0 && n2 == this.size()) {
            return this;
        }
        if (n == n2) {
            return ImmutableSortedMap.emptyMap(this.comparator());
        }
        return ImmutableSortedMap.from(this.keySet.getSubSet(n, n2), this.valueList.subList(n, n2));
    }
    
    @Override
    public ImmutableSortedMap headMap(final Object o, final boolean b) {
        return this.getSubMap(0, this.keySet.headIndex(Preconditions.checkNotNull(o), b));
    }
    
    @Override
    public ImmutableSortedMap tailMap(final Object o, final boolean b) {
        return this.getSubMap(this.keySet.tailIndex(Preconditions.checkNotNull(o), b), this.size());
    }
    
    @Override
    ImmutableSortedMap createDescendingMap() {
        return new RegularImmutableSortedMap((RegularImmutableSortedSet)this.keySet.descendingSet(), this.valueList.reverse(), this);
    }
    
    @Override
    public NavigableMap tailMap(final Object o, final boolean b) {
        return this.tailMap(o, b);
    }
    
    @Override
    public NavigableMap headMap(final Object o, final boolean b) {
        return this.headMap(o, b);
    }
    
    @Override
    public Collection values() {
        return this.values();
    }
    
    @Override
    public Set keySet() {
        return this.keySet();
    }
    
    @Override
    public ImmutableSet keySet() {
        return this.keySet();
    }
    
    static ImmutableList access$100(final RegularImmutableSortedMap regularImmutableSortedMap) {
        return regularImmutableSortedMap.valueList;
    }
    
    private class EntrySet extends ImmutableMapEntrySet
    {
        final RegularImmutableSortedMap this$0;
        
        private EntrySet(final RegularImmutableSortedMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public UnmodifiableIterator iterator() {
            return this.asList().iterator();
        }
        
        @Override
        ImmutableList createAsList() {
            return new ImmutableAsList() {
                private final ImmutableList keyList = this.this$1.this$0.keySet().asList();
                final EntrySet this$1;
                
                @Override
                public Map.Entry get(final int n) {
                    return Maps.immutableEntry(this.keyList.get(n), RegularImmutableSortedMap.access$100(this.this$1.this$0).get(n));
                }
                
                @Override
                ImmutableCollection delegateCollection() {
                    return this.this$1;
                }
                
                @Override
                public Object get(final int n) {
                    return this.get(n);
                }
            };
        }
        
        @Override
        ImmutableMap map() {
            return this.this$0;
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
        
        EntrySet(final RegularImmutableSortedMap regularImmutableSortedMap, final RegularImmutableSortedMap$1 object) {
            this(regularImmutableSortedMap);
        }
    }
}
