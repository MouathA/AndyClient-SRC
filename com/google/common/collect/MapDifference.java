package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;
import javax.annotation.*;

@GwtCompatible
public interface MapDifference
{
    boolean areEqual();
    
    Map entriesOnlyOnLeft();
    
    Map entriesOnlyOnRight();
    
    Map entriesInCommon();
    
    Map entriesDiffering();
    
    boolean equals(@Nullable final Object p0);
    
    int hashCode();
    
    public interface ValueDifference
    {
        Object leftValue();
        
        Object rightValue();
        
        boolean equals(@Nullable final Object p0);
        
        int hashCode();
    }
}
