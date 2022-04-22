package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;

@GwtCompatible
final class SortedIterables
{
    private SortedIterables() {
    }
    
    public static boolean hasSameComparator(final Comparator comparator, final Iterable iterable) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(iterable);
        Comparator comparator2;
        if (iterable instanceof SortedSet) {
            comparator2 = comparator((SortedSet)iterable);
        }
        else {
            if (!(iterable instanceof SortedIterable)) {
                return false;
            }
            comparator2 = ((SortedIterable)iterable).comparator();
        }
        return comparator.equals(comparator2);
    }
    
    public static Comparator comparator(final SortedSet set) {
        Comparator comparator = set.comparator();
        if (comparator == null) {
            comparator = Ordering.natural();
        }
        return comparator;
    }
}
