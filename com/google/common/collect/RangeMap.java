package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@Beta
public interface RangeMap
{
    @Nullable
    Object get(final Comparable p0);
    
    @Nullable
    Map.Entry getEntry(final Comparable p0);
    
    Range span();
    
    void put(final Range p0, final Object p1);
    
    void putAll(final RangeMap p0);
    
    void clear();
    
    void remove(final Range p0);
    
    Map asMapOfRanges();
    
    RangeMap subRangeMap(final Range p0);
    
    boolean equals(@Nullable final Object p0);
    
    int hashCode();
    
    String toString();
}
