package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;
import java.io.*;

public final class ObjectComparators
{
    public static final Comparator NATURAL_COMPARATOR;
    public static final Comparator OPPOSITE_COMPARATOR;
    
    private ObjectComparators() {
    }
    
    public static Comparator oppositeComparator(final Comparator comparator) {
        if (comparator instanceof OppositeComparator) {
            return ((OppositeComparator)comparator).comparator;
        }
        return new OppositeComparator(comparator);
    }
    
    public static Comparator asObjectComparator(final Comparator comparator) {
        return comparator;
    }
    
    static {
        NATURAL_COMPARATOR = new NaturalImplicitComparator();
        OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
    }
    
    protected static class OppositeComparator implements Comparator, Serializable
    {
        private static final long serialVersionUID = 1L;
        final Comparator comparator;
        
        protected OppositeComparator(final Comparator comparator) {
            this.comparator = comparator;
        }
        
        @Override
        public final int compare(final Object o, final Object o2) {
            return this.comparator.compare(o2, o);
        }
        
        @Override
        public final Comparator reversed() {
            return this.comparator;
        }
    }
    
    protected static class NaturalImplicitComparator implements Comparator, Serializable
    {
        private static final long serialVersionUID = 1L;
        
        @Override
        public final int compare(final Object o, final Object o2) {
            return ((Comparable)o).compareTo(o2);
        }
        
        @Override
        public Comparator reversed() {
            return ObjectComparators.OPPOSITE_COMPARATOR;
        }
        
        private Object readResolve() {
            return ObjectComparators.NATURAL_COMPARATOR;
        }
    }
    
    protected static class OppositeImplicitComparator implements Comparator, Serializable
    {
        private static final long serialVersionUID = 1L;
        
        @Override
        public final int compare(final Object o, final Object o2) {
            return ((Comparable)o2).compareTo(o);
        }
        
        @Override
        public Comparator reversed() {
            return ObjectComparators.NATURAL_COMPARATOR;
        }
        
        private Object readResolve() {
            return ObjectComparators.OPPOSITE_COMPARATOR;
        }
    }
}
