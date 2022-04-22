package org.apache.commons.lang3;

import java.io.*;
import java.util.*;

public final class Range implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final Comparator comparator;
    private final Object minimum;
    private final Object maximum;
    private transient int hashCode;
    private transient String toString;
    
    public static Range is(final Comparable comparable) {
        return between(comparable, comparable, null);
    }
    
    public static Range is(final Object o, final Comparator comparator) {
        return between(o, o, comparator);
    }
    
    public static Range between(final Comparable comparable, final Comparable comparable2) {
        return between(comparable, comparable2, null);
    }
    
    public static Range between(final Object o, final Object o2, final Comparator comparator) {
        return new Range(o, o2, comparator);
    }
    
    private Range(final Object o, final Object o2, final Comparator comparator) {
        if (o == null || o2 == null) {
            throw new IllegalArgumentException("Elements in a range must not be null: element1=" + o + ", element2=" + o2);
        }
        if (comparator == null) {
            this.comparator = ComparableComparator.INSTANCE;
        }
        else {
            this.comparator = comparator;
        }
        if (this.comparator.compare(o, o2) < 1) {
            this.minimum = o;
            this.maximum = o2;
        }
        else {
            this.minimum = o2;
            this.maximum = o;
        }
    }
    
    public Object getMinimum() {
        return this.minimum;
    }
    
    public Object getMaximum() {
        return this.maximum;
    }
    
    public Comparator getComparator() {
        return this.comparator;
    }
    
    public boolean isNaturalOrdering() {
        return this.comparator == ComparableComparator.INSTANCE;
    }
    
    public boolean contains(final Object o) {
        return o != null && this.comparator.compare(o, this.minimum) > -1 && this.comparator.compare(o, this.maximum) < 1;
    }
    
    public boolean isAfter(final Object o) {
        return o != null && this.comparator.compare(o, this.minimum) < 0;
    }
    
    public boolean isStartedBy(final Object o) {
        return o != null && this.comparator.compare(o, this.minimum) == 0;
    }
    
    public boolean isEndedBy(final Object o) {
        return o != null && this.comparator.compare(o, this.maximum) == 0;
    }
    
    public boolean isBefore(final Object o) {
        return o != null && this.comparator.compare(o, this.maximum) > 0;
    }
    
    public int elementCompareTo(final Object o) {
        if (o == null) {
            throw new NullPointerException("Element is null");
        }
        if (this.isAfter(o)) {
            return -1;
        }
        if (this.isBefore(o)) {
            return 1;
        }
        return 0;
    }
    
    public boolean containsRange(final Range range) {
        return range != null && this.contains(range.minimum) && this.contains(range.maximum);
    }
    
    public boolean isAfterRange(final Range range) {
        return range != null && this.isAfter(range.maximum);
    }
    
    public boolean isOverlappedBy(final Range range) {
        return range != null && (range.contains(this.minimum) || range.contains(this.maximum) || this.contains(range.minimum));
    }
    
    public boolean isBeforeRange(final Range range) {
        return range != null && this.isBefore(range.minimum);
    }
    
    public Range intersectionWith(final Range range) {
        if (!this.isOverlappedBy(range)) {
            throw new IllegalArgumentException(String.format("Cannot calculate intersection with non-overlapping range %s", range));
        }
        if (this.equals(range)) {
            return this;
        }
        return between((this.getComparator().compare(this.minimum, range.minimum) < 0) ? range.minimum : this.minimum, (this.getComparator().compare(this.maximum, range.maximum) < 0) ? this.maximum : range.maximum, this.getComparator());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        final Range range = (Range)o;
        return this.minimum.equals(range.minimum) && this.maximum.equals(range.maximum);
    }
    
    @Override
    public int hashCode() {
        final int hashCode = this.hashCode;
        if (this.hashCode == 0) {
            final int n = 629 + this.getClass().hashCode();
            final int n2 = 629 + this.minimum.hashCode();
            final int n3 = 629 + this.maximum.hashCode();
            this.hashCode = 17;
        }
        return 17;
    }
    
    @Override
    public String toString() {
        String toString = this.toString;
        if (toString == null) {
            final StringBuilder sb = new StringBuilder(32);
            sb.append('[');
            sb.append(this.minimum);
            sb.append("..");
            sb.append(this.maximum);
            sb.append(']');
            toString = sb.toString();
            this.toString = toString;
        }
        return toString;
    }
    
    public String toString(final String s) {
        return String.format(s, this.minimum, this.maximum, this.comparator);
    }
    
    private enum ComparableComparator implements Comparator
    {
        INSTANCE("INSTANCE", 0);
        
        private static final ComparableComparator[] $VALUES;
        
        private ComparableComparator(final String s, final int n) {
        }
        
        @Override
        public int compare(final Object o, final Object o2) {
            return ((Comparable)o).compareTo(o2);
        }
        
        static {
            $VALUES = new ComparableComparator[] { ComparableComparator.INSTANCE };
        }
    }
}
