package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public interface SetMultimap extends Multimap
{
    Set get(@Nullable final Object p0);
    
    Set removeAll(@Nullable final Object p0);
    
    Set replaceValues(final Object p0, final Iterable p1);
    
    Set entries();
    
    Map asMap();
    
    boolean equals(@Nullable final Object p0);
}
