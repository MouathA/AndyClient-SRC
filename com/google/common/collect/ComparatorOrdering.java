package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;
import java.util.*;
import com.google.common.base.*;
import javax.annotation.*;

@GwtCompatible(serializable = true)
final class ComparatorOrdering extends Ordering implements Serializable
{
    final Comparator comparator;
    private static final long serialVersionUID = 0L;
    
    ComparatorOrdering(final Comparator comparator) {
        this.comparator = (Comparator)Preconditions.checkNotNull(comparator);
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        return this.comparator.compare(o, o2);
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o == this || (o instanceof ComparatorOrdering && this.comparator.equals(((ComparatorOrdering)o).comparator));
    }
    
    @Override
    public int hashCode() {
        return this.comparator.hashCode();
    }
    
    @Override
    public String toString() {
        return this.comparator.toString();
    }
}
