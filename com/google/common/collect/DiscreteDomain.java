package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;
import java.io.*;
import java.math.*;

@GwtCompatible
@Beta
public abstract class DiscreteDomain
{
    public static DiscreteDomain integers() {
        return IntegerDomain.access$000();
    }
    
    public static DiscreteDomain longs() {
        return LongDomain.access$100();
    }
    
    public static DiscreteDomain bigIntegers() {
        return BigIntegerDomain.access$200();
    }
    
    protected DiscreteDomain() {
    }
    
    public abstract Comparable next(final Comparable p0);
    
    public abstract Comparable previous(final Comparable p0);
    
    public abstract long distance(final Comparable p0, final Comparable p1);
    
    public Comparable minValue() {
        throw new NoSuchElementException();
    }
    
    public Comparable maxValue() {
        throw new NoSuchElementException();
    }
    
    private static final class BigIntegerDomain extends DiscreteDomain implements Serializable
    {
        private static final BigIntegerDomain INSTANCE;
        private static final BigInteger MIN_LONG;
        private static final BigInteger MAX_LONG;
        private static final long serialVersionUID = 0L;
        
        public BigInteger next(final BigInteger bigInteger) {
            return bigInteger.add(BigInteger.ONE);
        }
        
        public BigInteger previous(final BigInteger bigInteger) {
            return bigInteger.subtract(BigInteger.ONE);
        }
        
        public long distance(final BigInteger bigInteger, final BigInteger bigInteger2) {
            return bigInteger2.subtract(bigInteger).max(BigIntegerDomain.MIN_LONG).min(BigIntegerDomain.MAX_LONG).longValue();
        }
        
        private Object readResolve() {
            return BigIntegerDomain.INSTANCE;
        }
        
        @Override
        public String toString() {
            return "DiscreteDomain.bigIntegers()";
        }
        
        @Override
        public long distance(final Comparable comparable, final Comparable comparable2) {
            return this.distance((BigInteger)comparable, (BigInteger)comparable2);
        }
        
        @Override
        public Comparable previous(final Comparable comparable) {
            return this.previous((BigInteger)comparable);
        }
        
        @Override
        public Comparable next(final Comparable comparable) {
            return this.next((BigInteger)comparable);
        }
        
        static BigIntegerDomain access$200() {
            return BigIntegerDomain.INSTANCE;
        }
        
        static {
            INSTANCE = new BigIntegerDomain();
            MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
            MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
        }
    }
    
    private static final class LongDomain extends DiscreteDomain implements Serializable
    {
        private static final LongDomain INSTANCE;
        private static final long serialVersionUID = 0L;
        
        public Long next(final Long n) {
            final long longValue = n;
            return (longValue == Long.MAX_VALUE) ? null : Long.valueOf(longValue + 1L);
        }
        
        public Long previous(final Long n) {
            final long longValue = n;
            return (longValue == Long.MIN_VALUE) ? null : Long.valueOf(longValue - 1L);
        }
        
        public long distance(final Long n, final Long n2) {
            final long n3 = n2 - n;
            if (n2 > n && n3 < 0L) {
                return Long.MAX_VALUE;
            }
            if (n2 < n && n3 > 0L) {
                return Long.MIN_VALUE;
            }
            return n3;
        }
        
        @Override
        public Long minValue() {
            return Long.MIN_VALUE;
        }
        
        @Override
        public Long maxValue() {
            return Long.MAX_VALUE;
        }
        
        private Object readResolve() {
            return LongDomain.INSTANCE;
        }
        
        @Override
        public String toString() {
            return "DiscreteDomain.longs()";
        }
        
        @Override
        public Comparable maxValue() {
            return this.maxValue();
        }
        
        @Override
        public Comparable minValue() {
            return this.minValue();
        }
        
        @Override
        public long distance(final Comparable comparable, final Comparable comparable2) {
            return this.distance((Long)comparable, (Long)comparable2);
        }
        
        @Override
        public Comparable previous(final Comparable comparable) {
            return this.previous((Long)comparable);
        }
        
        @Override
        public Comparable next(final Comparable comparable) {
            return this.next((Long)comparable);
        }
        
        static LongDomain access$100() {
            return LongDomain.INSTANCE;
        }
        
        static {
            INSTANCE = new LongDomain();
        }
    }
    
    private static final class IntegerDomain extends DiscreteDomain implements Serializable
    {
        private static final IntegerDomain INSTANCE;
        private static final long serialVersionUID = 0L;
        
        public Integer next(final Integer n) {
            final int intValue = n;
            return (intValue == Integer.MAX_VALUE) ? null : Integer.valueOf(intValue + 1);
        }
        
        public Integer previous(final Integer n) {
            final int intValue = n;
            return (intValue == Integer.MIN_VALUE) ? null : Integer.valueOf(intValue - 1);
        }
        
        public long distance(final Integer n, final Integer n2) {
            return n2 - (long)n;
        }
        
        @Override
        public Integer minValue() {
            return Integer.MIN_VALUE;
        }
        
        @Override
        public Integer maxValue() {
            return Integer.MAX_VALUE;
        }
        
        private Object readResolve() {
            return IntegerDomain.INSTANCE;
        }
        
        @Override
        public String toString() {
            return "DiscreteDomain.integers()";
        }
        
        @Override
        public Comparable maxValue() {
            return this.maxValue();
        }
        
        @Override
        public Comparable minValue() {
            return this.minValue();
        }
        
        @Override
        public long distance(final Comparable comparable, final Comparable comparable2) {
            return this.distance((Integer)comparable, (Integer)comparable2);
        }
        
        @Override
        public Comparable previous(final Comparable comparable) {
            return this.previous((Integer)comparable);
        }
        
        @Override
        public Comparable next(final Comparable comparable) {
            return this.next((Integer)comparable);
        }
        
        static IntegerDomain access$000() {
            return IntegerDomain.INSTANCE;
        }
        
        static {
            INSTANCE = new IntegerDomain();
        }
    }
}
