package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public interface ListMultimap extends Multimap
{
    List get(@Nullable final Object p0);
    
    List removeAll(@Nullable final Object p0);
    
    List replaceValues(final Object p0, final Iterable p1);
    
    Map asMap();
    
    boolean equals(@Nullable final Object p0);
}
