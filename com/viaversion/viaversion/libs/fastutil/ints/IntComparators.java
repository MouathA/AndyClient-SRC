package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.*;
import java.io.*;

public final class IntComparators
{
    public static final IntComparator NATURAL_COMPARATOR;
    public static final IntComparator OPPOSITE_COMPARATOR;
    
    private IntComparators() {
    }
    
    public static IntComparator oppositeComparator(final IntComparator intComparator) {
        if (intComparator instanceof OppositeComparator) {
            return ((OppositeComparator)intComparator).comparator;
        }
        return new OppositeComparator(intComparator);
    }
    
    public static IntComparator asIntComparator(final Comparator comparator) {
        if (comparator == null || comparator instanceof IntComparator) {
            return (IntComparator)comparator;
        }
        return new IntComparator() {
            final Comparator val$c;
            
            @Override
            public int compare(final int n, final int n2) {
                return this.val$c.compare(n, n2);
            }
            
            @Override
            public int compare(final Integer n, final Integer n2) {
                return this.val$c.compare(n, n2);
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((Integer)o, (Integer)o2);
            }
        };
    }
    
    static {
        NATURAL_COMPARATOR = new NaturalImplicitComparator();
        OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
    }
    
    protected static class OppositeComparator implements IntComparator, Serializable
    {
        private static final long serialVersionUID = 1L;
        final IntComparator comparator;
        
        protected OppositeComparator(final IntComparator comparator) {
            this.comparator = comparator;
        }
        
        @Override
        public final int compare(final int n, final int n2) {
            return this.comparator.compare(n2, n);
        }
        
        @Override
        public final IntComparator reversed() {
            return this.comparator;
        }
        
        @Override
        public Comparator reversed() {
            return this.reversed();
        }
    }
    
    protected static class NaturalImplicitComparator implements IntComparator, Serializable
    {
        private static final long serialVersionUID = 1L;
        
        @Override
        public final int compare(final int n, final int n2) {
            return Integer.compare(n, n2);
        }
        
        @Override
        public IntComparator reversed() {
            return IntComparators.OPPOSITE_COMPARATOR;
        }
        
        private Object readResolve() {
            return IntComparators.NATURAL_COMPARATOR;
        }
        
        @Override
        public Comparator reversed() {
            return this.reversed();
        }
    }
    
    protected static class OppositeImplicitComparator implements IntComparator, Serializable
    {
        private static final long serialVersionUID = 1L;
        
        @Override
        public final int compare(final int n, final int n2) {
            return -Integer.compare(n, n2);
        }
        
        @Override
        public IntComparator reversed() {
            return IntComparators.NATURAL_COMPARATOR;
        }
        
        private Object readResolve() {
            return IntComparators.OPPOSITE_COMPARATOR;
        }
        
        @Override
        public Comparator reversed() {
            return this.reversed();
        }
    }
}
