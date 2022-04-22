package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;
import javax.annotation.*;

@Beta
public interface RangeSet
{
    boolean contains(final Comparable p0);
    
    Range rangeContaining(final Comparable p0);
    
    boolean encloses(final Range p0);
    
    boolean enclosesAll(final RangeSet p0);
    
    boolean isEmpty();
    
    Range span();
    
    Set asRanges();
    
    RangeSet complement();
    
    RangeSet subRangeSet(final Range p0);
    
    void add(final Range p0);
    
    void remove(final Range p0);
    
    void clear();
    
    void addAll(final RangeSet p0);
    
    void removeAll(final RangeSet p0);
    
    boolean equals(@Nullable final Object p0);
    
    int hashCode();
    
    String toString();
}
