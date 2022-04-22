package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public interface SortedSetMultimap extends SetMultimap
{
    SortedSet get(@Nullable final Object p0);
    
    SortedSet removeAll(@Nullable final Object p0);
    
    SortedSet replaceValues(final Object p0, final Iterable p1);
    
    Map asMap();
    
    Comparator valueComparator();
}
