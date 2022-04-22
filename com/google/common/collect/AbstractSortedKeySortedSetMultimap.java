package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
abstract class AbstractSortedKeySortedSetMultimap extends AbstractSortedSetMultimap
{
    AbstractSortedKeySortedSetMultimap(final SortedMap sortedMap) {
        super(sortedMap);
    }
    
    @Override
    public SortedMap asMap() {
        return (SortedMap)super.asMap();
    }
    
    @Override
    SortedMap backingMap() {
        return (SortedMap)super.backingMap();
    }
    
    @Override
    public SortedSet keySet() {
        return (SortedSet)super.keySet();
    }
    
    @Override
    public Map asMap() {
        return this.asMap();
    }
    
    @Override
    public Set keySet() {
        return this.keySet();
    }
    
    @Override
    Map backingMap() {
        return this.backingMap();
    }
}
