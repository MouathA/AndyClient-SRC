package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
interface SortedIterable extends Iterable
{
    Comparator comparator();
    
    Iterator iterator();
}
