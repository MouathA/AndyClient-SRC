package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;

public interface Int2ObjectSortedMap extends Int2ObjectMap, SortedMap
{
    Int2ObjectSortedMap subMap(final int p0, final int p1);
    
    Int2ObjectSortedMap headMap(final int p0);
    
    Int2ObjectSortedMap tailMap(final int p0);
    
    int firstIntKey();
    
    int lastIntKey();
    
    @Deprecated
    default Int2ObjectSortedMap subMap(final Integer n, final Integer n2) {
        return this.subMap((int)n, (int)n2);
    }
    
    @Deprecated
    default Int2ObjectSortedMap headMap(final Integer n) {
        return this.headMap((int)n);
    }
    
    @Deprecated
    default Int2ObjectSortedMap tailMap(final Integer n) {
        return this.tailMap((int)n);
    }
    
    @Deprecated
    default Integer firstKey() {
        return this.firstIntKey();
    }
    
    @Deprecated
    default Integer lastKey() {
        return this.lastIntKey();
    }
    
    @Deprecated
    default ObjectSortedSet entrySet() {
        return this.int2ObjectEntrySet();
    }
    
    ObjectSortedSet int2ObjectEntrySet();
    
    IntSortedSet keySet();
    
    ObjectCollection values();
    
    IntComparator comparator();
    
    default IntSet keySet() {
        return this.keySet();
    }
    
    @Deprecated
    default ObjectSet entrySet() {
        return this.entrySet();
    }
    
    default ObjectSet int2ObjectEntrySet() {
        return this.int2ObjectEntrySet();
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
    
    @Deprecated
    default Object lastKey() {
        return this.lastKey();
    }
    
    @Deprecated
    default Object firstKey() {
        return this.firstKey();
    }
    
    @Deprecated
    default SortedMap tailMap(final Object o) {
        return this.tailMap((Integer)o);
    }
    
    @Deprecated
    default SortedMap headMap(final Object o) {
        return this.headMap((Integer)o);
    }
    
    @Deprecated
    default SortedMap subMap(final Object o, final Object o2) {
        return this.subMap((Integer)o, (Integer)o2);
    }
    
    default Comparator comparator() {
        return this.comparator();
    }
    
    public interface FastSortedEntrySet extends ObjectSortedSet, FastEntrySet
    {
        ObjectBidirectionalIterator fastIterator();
        
        ObjectBidirectionalIterator fastIterator(final Int2ObjectMap.Entry p0);
        
        default ObjectIterator fastIterator() {
            return this.fastIterator();
        }
    }
}
