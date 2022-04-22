package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;

public interface Int2IntSortedMap extends Int2IntMap, SortedMap
{
    Int2IntSortedMap subMap(final int p0, final int p1);
    
    Int2IntSortedMap headMap(final int p0);
    
    Int2IntSortedMap tailMap(final int p0);
    
    int firstIntKey();
    
    int lastIntKey();
    
    @Deprecated
    default Int2IntSortedMap subMap(final Integer n, final Integer n2) {
        return this.subMap((int)n, (int)n2);
    }
    
    @Deprecated
    default Int2IntSortedMap headMap(final Integer n) {
        return this.headMap((int)n);
    }
    
    @Deprecated
    default Int2IntSortedMap tailMap(final Integer n) {
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
        return this.int2IntEntrySet();
    }
    
    ObjectSortedSet int2IntEntrySet();
    
    IntSortedSet keySet();
    
    IntCollection values();
    
    IntComparator comparator();
    
    default IntSet keySet() {
        return this.keySet();
    }
    
    @Deprecated
    default ObjectSet entrySet() {
        return this.entrySet();
    }
    
    default ObjectSet int2IntEntrySet() {
        return this.int2IntEntrySet();
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
        
        ObjectBidirectionalIterator fastIterator(final Int2IntMap.Entry p0);
        
        default ObjectIterator fastIterator() {
            return this.fastIterator();
        }
    }
}
