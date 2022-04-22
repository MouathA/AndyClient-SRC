package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;
import javax.annotation.*;

@GwtCompatible(serializable = true)
final class ReverseOrdering extends Ordering implements Serializable
{
    final Ordering forwardOrder;
    private static final long serialVersionUID = 0L;
    
    ReverseOrdering(final Ordering ordering) {
        this.forwardOrder = (Ordering)Preconditions.checkNotNull(ordering);
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        return this.forwardOrder.compare(o2, o);
    }
    
    @Override
    public Ordering reverse() {
        return this.forwardOrder;
    }
    
    @Override
    public Object min(final Object o, final Object o2) {
        return this.forwardOrder.max(o, o2);
    }
    
    @Override
    public Object min(final Object o, final Object o2, final Object o3, final Object... array) {
        return this.forwardOrder.max(o, o2, o3, array);
    }
    
    @Override
    public Object min(final Iterator iterator) {
        return this.forwardOrder.max(iterator);
    }
    
    @Override
    public Object min(final Iterable iterable) {
        return this.forwardOrder.max(iterable);
    }
    
    @Override
    public Object max(final Object o, final Object o2) {
        return this.forwardOrder.min(o, o2);
    }
    
    @Override
    public Object max(final Object o, final Object o2, final Object o3, final Object... array) {
        return this.forwardOrder.min(o, o2, o3, array);
    }
    
    @Override
    public Object max(final Iterator iterator) {
        return this.forwardOrder.min(iterator);
    }
    
    @Override
    public Object max(final Iterable iterable) {
        return this.forwardOrder.min(iterable);
    }
    
    @Override
    public int hashCode() {
        return -this.forwardOrder.hashCode();
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o == this || (o instanceof ReverseOrdering && this.forwardOrder.equals(((ReverseOrdering)o).forwardOrder));
    }
    
    @Override
    public String toString() {
        return this.forwardOrder + ".reverse()";
    }
}
