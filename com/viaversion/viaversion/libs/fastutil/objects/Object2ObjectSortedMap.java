package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;

public interface Object2ObjectSortedMap extends Object2ObjectMap, SortedMap
{
    Object2ObjectSortedMap subMap(final Object p0, final Object p1);
    
    Object2ObjectSortedMap headMap(final Object p0);
    
    Object2ObjectSortedMap tailMap(final Object p0);
    
    default ObjectSortedSet entrySet() {
        return this.object2ObjectEntrySet();
    }
    
    ObjectSortedSet object2ObjectEntrySet();
    
    ObjectSortedSet keySet();
    
    ObjectCollection values();
    
    Comparator comparator();
    
    default ObjectSet keySet() {
        return this.keySet();
    }
    
    default ObjectSet entrySet() {
        return this.entrySet();
    }
    
    default ObjectSet object2ObjectEntrySet() {
        return this.object2ObjectEntrySet();
    }
    
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
        
        ObjectBidirectionalIterator fastIterator(final Object2ObjectMap.Entry p0);
        
        default ObjectIterator fastIterator() {
            return this.fastIterator();
        }
    }
}
