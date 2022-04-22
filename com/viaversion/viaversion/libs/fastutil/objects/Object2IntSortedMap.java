package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.*;

public interface Object2IntSortedMap extends Object2IntMap, SortedMap
{
    Object2IntSortedMap subMap(final Object p0, final Object p1);
    
    Object2IntSortedMap headMap(final Object p0);
    
    Object2IntSortedMap tailMap(final Object p0);
    
    @Deprecated
    default ObjectSortedSet entrySet() {
        return this.object2IntEntrySet();
    }
    
    ObjectSortedSet object2IntEntrySet();
    
    ObjectSortedSet keySet();
    
    IntCollection values();
    
    Comparator comparator();
    
    default ObjectSet keySet() {
        return this.keySet();
    }
    
    @Deprecated
    default ObjectSet entrySet() {
        return this.entrySet();
    }
    
    default ObjectSet object2IntEntrySet() {
        return this.object2IntEntrySet();
    }
    
    @Deprecated
    default Set entrySet() {
        return this.entrySet();
    }
    
    default Collection values() {
        return this.values();
    }
    
    default Set keySet() {
        return this.keySet();
    }
    
    default SortedMap tailMap(final Object o) {
        return this.tailMap(o);
    }
    
    default SortedMap headMap(final Object o) {
        return this.headMap(o);
    }
    
    default SortedMap subMap(final Object o, final Object o2) {
        return this.subMap(o, o2);
    }
    
    public interface FastSortedEntrySet extends ObjectSortedSet, FastEntrySet
    {
        ObjectBidirectionalIterator fastIterator();
        
        ObjectBidirectionalIterator fastIterator(final Object2IntMap.Entry p0);
        
        default ObjectIterator fastIterator() {
            return this.fastIterator();
        }
    }
}
