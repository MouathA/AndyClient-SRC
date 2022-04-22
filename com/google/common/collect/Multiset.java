package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public interface Multiset extends Collection
{
    int count(@Nullable final Object p0);
    
    int add(@Nullable final Object p0, final int p1);
    
    int remove(@Nullable final Object p0, final int p1);
    
    int setCount(final Object p0, final int p1);
    
    boolean setCount(final Object p0, final int p1, final int p2);
    
    Set elementSet();
    
    Set entrySet();
    
    boolean equals(@Nullable final Object p0);
    
    int hashCode();
    
    String toString();
    
    Iterator iterator();
    
    boolean contains(@Nullable final Object p0);
    
    boolean containsAll(final Collection p0);
    
    boolean add(final Object p0);
    
    boolean remove(@Nullable final Object p0);
    
    boolean removeAll(final Collection p0);
    
    boolean retainAll(final Collection p0);
    
    public interface Entry
    {
        Object getElement();
        
        int getCount();
        
        boolean equals(final Object p0);
        
        int hashCode();
        
        String toString();
    }
}
