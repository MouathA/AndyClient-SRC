package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@Beta
@GwtCompatible(emulated = true)
public interface SortedMultiset extends SortedMultisetBridge, SortedIterable
{
    Comparator comparator();
    
    Multiset.Entry firstEntry();
    
    Multiset.Entry lastEntry();
    
    Multiset.Entry pollFirstEntry();
    
    Multiset.Entry pollLastEntry();
    
    NavigableSet elementSet();
    
    Set entrySet();
    
    Iterator iterator();
    
    SortedMultiset descendingMultiset();
    
    SortedMultiset headMultiset(final Object p0, final BoundType p1);
    
    SortedMultiset subMultiset(final Object p0, final BoundType p1, final Object p2, final BoundType p3);
    
    SortedMultiset tailMultiset(final Object p0, final BoundType p1);
}
