package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible(serializable = true)
final class NullsLastOrdering extends Ordering implements Serializable
{
    final Ordering ordering;
    private static final long serialVersionUID = 0L;
    
    NullsLastOrdering(final Ordering ordering) {
        this.ordering = ordering;
    }
    
    @Override
    public int compare(@Nullable final Object o, @Nullable final Object o2) {
        if (o == o2) {
            return 0;
        }
        if (o == null) {
            return 1;
        }
        if (o2 == null) {
            return -1;
        }
        return this.ordering.compare(o, o2);
    }
    
    @Override
    public Ordering reverse() {
        return this.ordering.reverse().nullsFirst();
    }
    
    @Override
    public Ordering nullsFirst() {
        return this.ordering.nullsFirst();
    }
    
    @Override
    public Ordering nullsLast() {
        return this;
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o == this || (o instanceof NullsLastOrdering && this.ordering.equals(((NullsLastOrdering)o).ordering));
    }
    
    @Override
    public int hashCode() {
        return this.ordering.hashCode() ^ 0xC9177248;
    }
    
    @Override
    public String toString() {
        return this.ordering + ".nullsLast()";
    }
}
