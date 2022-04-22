package com.google.common.collect;

import javax.annotation.*;
import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;

@GwtCompatible
public abstract class ForwardingSortedMap extends ForwardingMap implements SortedMap
{
    protected ForwardingSortedMap() {
    }
    
    @Override
    protected abstract SortedMap delegate();
    
    @Override
    public Comparator comparator() {
        return this.delegate().comparator();
    }
    
    @Override
    public Object firstKey() {
        return this.delegate().firstKey();
    }
    
    @Override
    public SortedMap headMap(final Object o) {
        return this.delegate().headMap(o);
    }
    
    @Override
    public Object lastKey() {
        return this.delegate().lastKey();
    }
    
    @Override
    public SortedMap subMap(final Object o, final Object o2) {
        return this.delegate().subMap(o, o2);
    }
    
    @Override
    public SortedMap tailMap(final Object o) {
        return this.delegate().tailMap(o);
    }
    
    private int unsafeCompare(final Object o, final Object o2) {
        final Comparator comparator = this.comparator();
        if (comparator == null) {
            return ((Comparable)o).compareTo(o2);
        }
        return comparator.compare(o, o2);
    }
    
    @Beta
    @Override
    protected boolean standardContainsKey(@Nullable final Object o) {
        return this.unsafeCompare(this.tailMap(o).firstKey(), o) == 0;
    }
    
    @Beta
    protected SortedMap standardSubMap(final Object o, final Object o2) {
        Preconditions.checkArgument(this.unsafeCompare(o, o2) <= 0, (Object)"fromKey must be <= toKey");
        return this.tailMap(o).headMap(o2);
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
    protected class StandardKeySet extends Maps.SortedKeySet
    {
        final ForwardingSortedMap this$0;
        
        public StandardKeySet(final ForwardingSortedMap this$0) {
            super(this.this$0 = this$0);
        }
    }
}
