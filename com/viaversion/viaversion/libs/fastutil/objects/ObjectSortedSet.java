package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.*;
import java.util.*;

public interface ObjectSortedSet extends ObjectSet, SortedSet, ObjectBidirectionalIterable
{
    ObjectBidirectionalIterator iterator(final Object p0);
    
    ObjectBidirectionalIterator iterator();
    
    default ObjectSpliterator spliterator() {
        return ObjectSpliterators.asSpliteratorFromSorted(this.iterator(), Size64.sizeOf(this), 85, this.comparator());
    }
    
    ObjectSortedSet subSet(final Object p0, final Object p1);
    
    ObjectSortedSet headSet(final Object p0);
    
    ObjectSortedSet tailSet(final Object p0);
    
    default ObjectIterator iterator() {
        return this.iterator();
    }
    
    default Spliterator spliterator() {
        return this.spliterator();
    }
    
    default Iterator iterator() {
        return this.iterator();
    }
    
    default SortedSet tailSet(final Object o) {
        return this.tailSet(o);
    }
    
    default SortedSet headSet(final Object o) {
        return this.headSet(o);
    }
    
    default SortedSet subSet(final Object o, final Object o2) {
        return this.subSet(o, o2);
    }
}
