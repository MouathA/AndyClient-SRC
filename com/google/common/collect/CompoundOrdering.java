package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible(serializable = true)
final class CompoundOrdering extends Ordering implements Serializable
{
    final ImmutableList comparators;
    private static final long serialVersionUID = 0L;
    
    CompoundOrdering(final Comparator comparator, final Comparator comparator2) {
        this.comparators = ImmutableList.of(comparator, comparator2);
    }
    
    CompoundOrdering(final Iterable iterable) {
        this.comparators = ImmutableList.copyOf(iterable);
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        while (0 < this.comparators.size()) {
            final int compare = this.comparators.get(0).compare(o, o2);
            if (compare != 0) {
                return compare;
            }
            int n = 0;
            ++n;
        }
        return 0;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof CompoundOrdering && this.comparators.equals(((CompoundOrdering)o).comparators));
    }
    
    @Override
    public int hashCode() {
        return this.comparators.hashCode();
    }
    
    @Override
    public String toString() {
        return "Ordering.compound(" + this.comparators + ")";
    }
}
