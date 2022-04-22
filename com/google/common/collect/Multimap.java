package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public interface Multimap
{
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(@Nullable final Object p0);
    
    boolean containsValue(@Nullable final Object p0);
    
    boolean containsEntry(@Nullable final Object p0, @Nullable final Object p1);
    
    boolean put(@Nullable final Object p0, @Nullable final Object p1);
    
    boolean remove(@Nullable final Object p0, @Nullable final Object p1);
    
    boolean putAll(@Nullable final Object p0, final Iterable p1);
    
    boolean putAll(final Multimap p0);
    
    Collection replaceValues(@Nullable final Object p0, final Iterable p1);
    
    Collection removeAll(@Nullable final Object p0);
    
    void clear();
    
    Collection get(@Nullable final Object p0);
    
    Set keySet();
    
    Multiset keys();
    
    Collection values();
    
    Collection entries();
    
    Map asMap();
    
    boolean equals(@Nullable final Object p0);
    
    int hashCode();
}
