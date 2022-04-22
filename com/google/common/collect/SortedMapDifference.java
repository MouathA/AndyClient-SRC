package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
public interface SortedMapDifference extends MapDifference
{
    SortedMap entriesOnlyOnLeft();
    
    SortedMap entriesOnlyOnRight();
    
    SortedMap entriesInCommon();
    
    SortedMap entriesDiffering();
}
