package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
@Beta
public interface RowSortedTable extends Table
{
    SortedSet rowKeySet();
    
    SortedMap rowMap();
}
