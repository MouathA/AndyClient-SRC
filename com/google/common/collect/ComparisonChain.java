package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;
import com.google.common.primitives.*;

@GwtCompatible
public abstract class ComparisonChain
{
    private static final ComparisonChain ACTIVE;
    private static final ComparisonChain LESS;
    private static final ComparisonChain GREATER;
    
    private ComparisonChain() {
    }
    
    public static ComparisonChain start() {
        return ComparisonChain.ACTIVE;
    }
    
    public abstract ComparisonChain compare(final Comparable p0, final Comparable p1);
    
    public abstract ComparisonChain compare(@Nullable final Object p0, @Nullable final Object p1, final Comparator p2);
    
    public abstract ComparisonChain compare(final int p0, final int p1);
    
    public abstract ComparisonChain compare(final long p0, final long p1);
    
    public abstract ComparisonChain compare(final float p0, final float p1);
    
    public abstract ComparisonChain compare(final double p0, final double p1);
    
    public abstract ComparisonChain compareTrueFirst(final boolean p0, final boolean p1);
    
    public abstract ComparisonChain compareFalseFirst(final boolean p0, final boolean p1);
    
    public abstract int result();
    
    ComparisonChain(final ComparisonChain$1 comparisonChain) {
        this();
    }
    
    static ComparisonChain access$100() {
        return ComparisonChain.LESS;
    }
    
    static ComparisonChain access$200() {
        return ComparisonChain.GREATER;
    }
    
    static ComparisonChain access$300() {
        return ComparisonChain.ACTIVE;
    }
    
    static {
        ACTIVE = new ComparisonChain() {
            @Override
            public ComparisonChain compare(final Comparable comparable, final Comparable comparable2) {
                return this.classify(comparable.compareTo(comparable2));
            }
            
            @Override
            public ComparisonChain compare(@Nullable final Object o, @Nullable final Object o2, final Comparator comparator) {
                return this.classify(comparator.compare(o, o2));
            }
            
            @Override
            public ComparisonChain compare(final int n, final int n2) {
                return this.classify(Ints.compare(n, n2));
            }
            
            @Override
            public ComparisonChain compare(final long n, final long n2) {
                return this.classify(Longs.compare(n, n2));
            }
            
            @Override
            public ComparisonChain compare(final float n, final float n2) {
                return this.classify(Float.compare(n, n2));
            }
            
            @Override
            public ComparisonChain compare(final double n, final double n2) {
                return this.classify(Double.compare(n, n2));
            }
            
            @Override
            public ComparisonChain compareTrueFirst(final boolean b, final boolean b2) {
                return this.classify(Booleans.compare(b2, b));
            }
            
            @Override
            public ComparisonChain compareFalseFirst(final boolean b, final boolean b2) {
                return this.classify(Booleans.compare(b, b2));
            }
            
            ComparisonChain classify(final int n) {
                return (n < 0) ? ComparisonChain.access$100() : ((n > 0) ? ComparisonChain.access$200() : ComparisonChain.access$300());
            }
            
            @Override
            public int result() {
                return 0;
            }
        };
        LESS = new InactiveComparisonChain(-1);
        GREATER = new InactiveComparisonChain(1);
    }
    
    private static final class InactiveComparisonChain extends ComparisonChain
    {
        final int result;
        
        InactiveComparisonChain(final int result) {
            super(null);
            this.result = result;
        }
        
        @Override
        public ComparisonChain compare(@Nullable final Comparable comparable, @Nullable final Comparable comparable2) {
            return this;
        }
        
        @Override
        public ComparisonChain compare(@Nullable final Object o, @Nullable final Object o2, @Nullable final Comparator comparator) {
            return this;
        }
        
        @Override
        public ComparisonChain compare(final int n, final int n2) {
            return this;
        }
        
        @Override
        public ComparisonChain compare(final long n, final long n2) {
            return this;
        }
        
        @Override
        public ComparisonChain compare(final float n, final float n2) {
            return this;
        }
        
        @Override
        public ComparisonChain compare(final double n, final double n2) {
            return this;
        }
        
        @Override
        public ComparisonChain compareTrueFirst(final boolean b, final boolean b2) {
            return this;
        }
        
        @Override
        public ComparisonChain compareFalseFirst(final boolean b, final boolean b2) {
            return this;
        }
        
        @Override
        public int result() {
            return this.result;
        }
    }
}
