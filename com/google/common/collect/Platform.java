package com.google.common.collect;

import com.google.common.annotations.*;
import java.lang.reflect.*;
import java.util.*;
import com.google.common.base.*;

@GwtCompatible(emulated = true)
final class Platform
{
    static Object[] newArray(final Object[] array, final int n) {
        return (Object[])Array.newInstance(array.getClass().getComponentType(), n);
    }
    
    static Set newSetFromMap(final Map map) {
        return Collections.newSetFromMap((Map<Object, Boolean>)map);
    }
    
    static MapMaker tryWeakKeys(final MapMaker mapMaker) {
        return mapMaker.weakKeys();
    }
    
    static SortedMap mapsTransformEntriesSortedMap(final SortedMap sortedMap, final Maps.EntryTransformer entryTransformer) {
        return (sortedMap instanceof NavigableMap) ? Maps.transformEntries((NavigableMap)sortedMap, entryTransformer) : Maps.transformEntriesIgnoreNavigable(sortedMap, entryTransformer);
    }
    
    static SortedMap mapsAsMapSortedSet(final SortedSet set, final Function function) {
        return (set instanceof NavigableSet) ? Maps.asMap((NavigableSet)set, function) : Maps.asMapSortedIgnoreNavigable(set, function);
    }
    
    static SortedSet setsFilterSortedSet(final SortedSet set, final Predicate predicate) {
        return (set instanceof NavigableSet) ? Sets.filter((NavigableSet)set, predicate) : Sets.filterSortedIgnoreNavigable(set, predicate);
    }
    
    static SortedMap mapsFilterSortedMap(final SortedMap sortedMap, final Predicate predicate) {
        return (sortedMap instanceof NavigableMap) ? Maps.filterEntries((NavigableMap)sortedMap, predicate) : Maps.filterSortedIgnoreNavigable(sortedMap, predicate);
    }
    
    private Platform() {
    }
}
