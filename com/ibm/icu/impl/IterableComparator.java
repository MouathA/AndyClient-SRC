package com.ibm.icu.impl;

import java.util.*;

public class IterableComparator implements Comparator
{
    private final Comparator comparator;
    private final int shorterFirst;
    private static final IterableComparator NOCOMPARATOR;
    
    public IterableComparator() {
        this(null, true);
    }
    
    public IterableComparator(final Comparator comparator) {
        this(comparator, true);
    }
    
    public IterableComparator(final Comparator comparator, final boolean b) {
        this.comparator = comparator;
        this.shorterFirst = (b ? 1 : -1);
    }
    
    public int compare(final Iterable iterable, final Iterable iterable2) {
        if (iterable == null) {
            return (iterable2 == null) ? 0 : (-this.shorterFirst);
        }
        if (iterable2 == null) {
            return this.shorterFirst;
        }
        final Iterator<Comparable<Object>> iterator = iterable.iterator();
        final Iterator<Object> iterator2 = iterable2.iterator();
        while (iterator.hasNext()) {
            if (!iterator2.hasNext()) {
                return this.shorterFirst;
            }
            final Comparable<Object> next = iterator.next();
            final Object next2 = iterator2.next();
            final int n = (this.comparator != null) ? this.comparator.compare(next, next2) : next.compareTo(next2);
            if (n != 0) {
                return n;
            }
        }
        return iterator2.hasNext() ? (-this.shorterFirst) : 0;
    }
    
    public static int compareIterables(final Iterable iterable, final Iterable iterable2) {
        return IterableComparator.NOCOMPARATOR.compare(iterable, iterable2);
    }
    
    public int compare(final Object o, final Object o2) {
        return this.compare((Iterable)o, (Iterable)o2);
    }
    
    static {
        NOCOMPARATOR = new IterableComparator();
    }
}
