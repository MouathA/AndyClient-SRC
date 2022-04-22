package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;
import java.util.*;
import javax.annotation.*;

@GwtCompatible(serializable = true)
final class LexicographicalOrdering extends Ordering implements Serializable
{
    final Ordering elementOrder;
    private static final long serialVersionUID = 0L;
    
    LexicographicalOrdering(final Ordering elementOrder) {
        this.elementOrder = elementOrder;
    }
    
    public int compare(final Iterable iterable, final Iterable iterable2) {
        final Iterator<Object> iterator = iterable.iterator();
        final Iterator<Object> iterator2 = iterable2.iterator();
        while (iterator.hasNext()) {
            if (!iterator2.hasNext()) {
                return 1;
            }
            final int compare = this.elementOrder.compare(iterator.next(), iterator2.next());
            if (compare != 0) {
                return compare;
            }
        }
        if (iterator2.hasNext()) {
            return -1;
        }
        return 0;
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o == this || (o instanceof LexicographicalOrdering && this.elementOrder.equals(((LexicographicalOrdering)o).elementOrder));
    }
    
    @Override
    public int hashCode() {
        return this.elementOrder.hashCode() ^ 0x7BB78CF5;
    }
    
    @Override
    public String toString() {
        return this.elementOrder + ".lexicographical()";
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        return this.compare((Iterable)o, (Iterable)o2);
    }
}
