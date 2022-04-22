package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.*;
import java.util.*;

public interface IntSortedSet extends IntSet, SortedSet, IntBidirectionalIterable
{
    IntBidirectionalIterator iterator(final int p0);
    
    IntBidirectionalIterator iterator();
    
    default IntSpliterator spliterator() {
        return IntSpliterators.asSpliteratorFromSorted(this.iterator(), Size64.sizeOf(this), 341, this.comparator());
    }
    
    IntSortedSet subSet(final int p0, final int p1);
    
    IntSortedSet headSet(final int p0);
    
    IntSortedSet tailSet(final int p0);
    
    IntComparator comparator();
    
    int firstInt();
    
    int lastInt();
    
    @Deprecated
    default IntSortedSet subSet(final Integer n, final Integer n2) {
        return this.subSet((int)n, (int)n2);
    }
    
    @Deprecated
    default IntSortedSet headSet(final Integer n) {
        return this.headSet((int)n);
    }
    
    @Deprecated
    default IntSortedSet tailSet(final Integer n) {
        return this.tailSet((int)n);
    }
    
    @Deprecated
    default Integer first() {
        return this.firstInt();
    }
    
    @Deprecated
    default Integer last() {
        return this.lastInt();
    }
    
    default IntIterator iterator() {
        return this.iterator();
    }
    
    default Spliterator spliterator() {
        return this.spliterator();
    }
    
    default Iterator iterator() {
        return this.iterator();
    }
    
    @Deprecated
    default Object last() {
        return this.last();
    }
    
    @Deprecated
    default Object first() {
        return this.first();
    }
    
    @Deprecated
    default SortedSet tailSet(final Object o) {
        return this.tailSet((Integer)o);
    }
    
    @Deprecated
    default SortedSet headSet(final Object o) {
        return this.headSet((Integer)o);
    }
    
    @Deprecated
    default SortedSet subSet(final Object o, final Object o2) {
        return this.subSet((Integer)o, (Integer)o2);
    }
    
    default Comparator comparator() {
        return this.comparator();
    }
}
