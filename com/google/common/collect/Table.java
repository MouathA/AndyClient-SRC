package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public interface Table
{
    boolean contains(@Nullable final Object p0, @Nullable final Object p1);
    
    boolean containsRow(@Nullable final Object p0);
    
    boolean containsColumn(@Nullable final Object p0);
    
    boolean containsValue(@Nullable final Object p0);
    
    Object get(@Nullable final Object p0, @Nullable final Object p1);
    
    boolean isEmpty();
    
    int size();
    
    boolean equals(@Nullable final Object p0);
    
    int hashCode();
    
    void clear();
    
    Object put(final Object p0, final Object p1, final Object p2);
    
    void putAll(final Table p0);
    
    Object remove(@Nullable final Object p0, @Nullable final Object p1);
    
    Map row(final Object p0);
    
    Map column(final Object p0);
    
    Set cellSet();
    
    Set rowKeySet();
    
    Set columnKeySet();
    
    Collection values();
    
    Map rowMap();
    
    Map columnMap();
    
    public interface Cell
    {
        Object getRowKey();
        
        Object getColumnKey();
        
        Object getValue();
        
        boolean equals(@Nullable final Object p0);
        
        int hashCode();
    }
}
