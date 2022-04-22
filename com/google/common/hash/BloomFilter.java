package com.google.common.hash;

import java.io.*;
import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.base.*;

@Beta
public final class BloomFilter implements Predicate, Serializable
{
    private final BloomFilterStrategies.BitArray bits;
    private final int numHashFunctions;
    private final Funnel funnel;
    private final Strategy strategy;
    private static final Strategy DEFAULT_STRATEGY;
    @VisibleForTesting
    static final String USE_MITZ32_PROPERTY = "com.google.common.hash.BloomFilter.useMitz32";
    
    private BloomFilter(final BloomFilterStrategies.BitArray bitArray, final int numHashFunctions, final Funnel funnel, final Strategy strategy) {
        Preconditions.checkArgument(numHashFunctions > 0, "numHashFunctions (%s) must be > 0", numHashFunctions);
        Preconditions.checkArgument(numHashFunctions <= 255, "numHashFunctions (%s) must be <= 255", numHashFunctions);
        this.bits = (BloomFilterStrategies.BitArray)Preconditions.checkNotNull(bitArray);
        this.numHashFunctions = numHashFunctions;
        this.funnel = (Funnel)Preconditions.checkNotNull(funnel);
        this.strategy = (Strategy)Preconditions.checkNotNull(strategy);
    }
    
    public BloomFilter copy() {
        return new BloomFilter(this.bits.copy(), this.numHashFunctions, this.funnel, this.strategy);
    }
    
    public boolean mightContain(final Object o) {
        return this.strategy.mightContain(o, this.funnel, this.numHashFunctions, this.bits);
    }
    
    @Deprecated
    @Override
    public boolean apply(final Object o) {
        return this.mightContain(o);
    }
    
    public boolean put(final Object o) {
        return this.strategy.put(o, this.funnel, this.numHashFunctions, this.bits);
    }
    
    public double expectedFpp() {
        return Math.pow(this.bits.bitCount() / (double)this.bitSize(), this.numHashFunctions);
    }
    
    @VisibleForTesting
    long bitSize() {
        return this.bits.bitSize();
    }
    
    public boolean isCompatible(final BloomFilter bloomFilter) {
        Preconditions.checkNotNull(bloomFilter);
        return this != bloomFilter && this.numHashFunctions == bloomFilter.numHashFunctions && this.bitSize() == bloomFilter.bitSize() && this.strategy.equals(bloomFilter.strategy) && this.funnel.equals(bloomFilter.funnel);
    }
    
    public void putAll(final BloomFilter bloomFilter) {
        Preconditions.checkNotNull(bloomFilter);
        Preconditions.checkArgument(this != bloomFilter, (Object)"Cannot combine a BloomFilter with itself.");
        Preconditions.checkArgument(this.numHashFunctions == bloomFilter.numHashFunctions, "BloomFilters must have the same number of hash functions (%s != %s)", this.numHashFunctions, bloomFilter.numHashFunctions);
        Preconditions.checkArgument(this.bitSize() == bloomFilter.bitSize(), "BloomFilters must have the same size underlying bit arrays (%s != %s)", this.bitSize(), bloomFilter.bitSize());
        Preconditions.checkArgument(this.strategy.equals(bloomFilter.strategy), "BloomFilters must have equal strategies (%s != %s)", this.strategy, bloomFilter.strategy);
        Preconditions.checkArgument(this.funnel.equals(bloomFilter.funnel), "BloomFilters must have equal funnels (%s != %s)", this.funnel, bloomFilter.funnel);
        this.bits.putAll(bloomFilter.bits);
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof BloomFilter) {
            final BloomFilter bloomFilter = (BloomFilter)o;
            return this.numHashFunctions == bloomFilter.numHashFunctions && this.funnel.equals(bloomFilter.funnel) && this.bits.equals(bloomFilter.bits) && this.strategy.equals(bloomFilter.strategy);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.numHashFunctions, this.funnel, this.strategy, this.bits);
    }
    
    @VisibleForTesting
    static Strategy getDefaultStrategyFromSystemProperty() {
        return Boolean.parseBoolean(System.getProperty("com.google.common.hash.BloomFilter.useMitz32")) ? BloomFilterStrategies.MURMUR128_MITZ_32 : BloomFilterStrategies.MURMUR128_MITZ_64;
    }
    
    public static BloomFilter create(final Funnel funnel, final int n, final double n2) {
        return create(funnel, n, n2, BloomFilter.DEFAULT_STRATEGY);
    }
    
    @VisibleForTesting
    static BloomFilter create(final Funnel funnel, final int n, final double n2, final Strategy strategy) {
        Preconditions.checkNotNull(funnel);
        Preconditions.checkArgument(1 >= 0, "Expected insertions (%s) must be >= 0", 1);
        Preconditions.checkArgument(n2 > 0.0, "False positive probability (%s) must be > 0.0", n2);
        Preconditions.checkArgument(n2 < 1.0, "False positive probability (%s) must be < 1.0", n2);
        Preconditions.checkNotNull(strategy);
        if (!true) {}
        final long optimalNumOfBits = optimalNumOfBits(1, n2);
        return new BloomFilter(new BloomFilterStrategies.BitArray(optimalNumOfBits), optimalNumOfHashFunctions(1, optimalNumOfBits), funnel, strategy);
    }
    
    public static BloomFilter create(final Funnel funnel, final int n) {
        return create(funnel, n, 0.03);
    }
    
    @VisibleForTesting
    static int optimalNumOfHashFunctions(final long n, final long n2) {
        return Math.max(1, (int)Math.round(n2 / n * Math.log(2.0)));
    }
    
    @VisibleForTesting
    static long optimalNumOfBits(final long n, double n2) {
        if (n2 == 0.0) {
            n2 = Double.MIN_VALUE;
        }
        return (long)(-n * Math.log(n2) / (Math.log(2.0) * Math.log(2.0)));
    }
    
    private Object writeReplace() {
        return new SerialForm(this);
    }
    
    static BloomFilterStrategies.BitArray access$000(final BloomFilter bloomFilter) {
        return bloomFilter.bits;
    }
    
    static int access$100(final BloomFilter bloomFilter) {
        return bloomFilter.numHashFunctions;
    }
    
    static Funnel access$200(final BloomFilter bloomFilter) {
        return bloomFilter.funnel;
    }
    
    static Strategy access$300(final BloomFilter bloomFilter) {
        return bloomFilter.strategy;
    }
    
    BloomFilter(final BloomFilterStrategies.BitArray bitArray, final int n, final Funnel funnel, final Strategy strategy, final BloomFilter$1 object) {
        this(bitArray, n, funnel, strategy);
    }
    
    static {
        DEFAULT_STRATEGY = getDefaultStrategyFromSystemProperty();
    }
    
    private static class SerialForm implements Serializable
    {
        final long[] data;
        final int numHashFunctions;
        final Funnel funnel;
        final Strategy strategy;
        private static final long serialVersionUID = 1L;
        
        SerialForm(final BloomFilter bloomFilter) {
            this.data = BloomFilter.access$000(bloomFilter).data;
            this.numHashFunctions = BloomFilter.access$100(bloomFilter);
            this.funnel = BloomFilter.access$200(bloomFilter);
            this.strategy = BloomFilter.access$300(bloomFilter);
        }
        
        Object readResolve() {
            return new BloomFilter(new BloomFilterStrategies.BitArray(this.data), this.numHashFunctions, this.funnel, this.strategy, null);
        }
    }
    
    interface Strategy extends Serializable
    {
        boolean put(final Object p0, final Funnel p1, final int p2, final BloomFilterStrategies.BitArray p3);
        
        boolean mightContain(final Object p0, final Funnel p1, final int p2, final BloomFilterStrategies.BitArray p3);
        
        int ordinal();
    }
}
