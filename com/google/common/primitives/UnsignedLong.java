package com.google.common.primitives;

import java.io.*;
import com.google.common.annotations.*;
import com.google.common.base.*;
import java.math.*;
import javax.annotation.*;

@GwtCompatible(serializable = true)
public final class UnsignedLong extends Number implements Comparable, Serializable
{
    private static final long UNSIGNED_MASK = Long.MAX_VALUE;
    public static final UnsignedLong ZERO;
    public static final UnsignedLong ONE;
    public static final UnsignedLong MAX_VALUE;
    private final long value;
    
    private UnsignedLong(final long value) {
        this.value = value;
    }
    
    public static UnsignedLong fromLongBits(final long n) {
        return new UnsignedLong(n);
    }
    
    public static UnsignedLong valueOf(final long n) {
        Preconditions.checkArgument(n >= 0L, "value (%s) is outside the range for an unsigned long value", n);
        return fromLongBits(n);
    }
    
    public static UnsignedLong valueOf(final BigInteger bigInteger) {
        Preconditions.checkNotNull(bigInteger);
        Preconditions.checkArgument(bigInteger.signum() >= 0 && bigInteger.bitLength() <= 64, "value (%s) is outside the range for an unsigned long value", bigInteger);
        return fromLongBits(bigInteger.longValue());
    }
    
    public static UnsignedLong valueOf(final String s) {
        return valueOf(s, 10);
    }
    
    public static UnsignedLong valueOf(final String s, final int n) {
        return fromLongBits(UnsignedLongs.parseUnsignedLong(s, n));
    }
    
    public UnsignedLong plus(final UnsignedLong unsignedLong) {
        return fromLongBits(this.value + ((UnsignedLong)Preconditions.checkNotNull(unsignedLong)).value);
    }
    
    public UnsignedLong minus(final UnsignedLong unsignedLong) {
        return fromLongBits(this.value - ((UnsignedLong)Preconditions.checkNotNull(unsignedLong)).value);
    }
    
    @CheckReturnValue
    public UnsignedLong times(final UnsignedLong unsignedLong) {
        return fromLongBits(this.value * ((UnsignedLong)Preconditions.checkNotNull(unsignedLong)).value);
    }
    
    @CheckReturnValue
    public UnsignedLong dividedBy(final UnsignedLong unsignedLong) {
        return fromLongBits(UnsignedLongs.divide(this.value, ((UnsignedLong)Preconditions.checkNotNull(unsignedLong)).value));
    }
    
    @CheckReturnValue
    public UnsignedLong mod(final UnsignedLong unsignedLong) {
        return fromLongBits(UnsignedLongs.remainder(this.value, ((UnsignedLong)Preconditions.checkNotNull(unsignedLong)).value));
    }
    
    @Override
    public int intValue() {
        return (int)this.value;
    }
    
    @Override
    public long longValue() {
        return this.value;
    }
    
    @Override
    public float floatValue() {
        float n = (float)(this.value & Long.MAX_VALUE);
        if (this.value < 0L) {
            n += 9.223372E18f;
        }
        return n;
    }
    
    @Override
    public double doubleValue() {
        double n = (double)(this.value & Long.MAX_VALUE);
        if (this.value < 0L) {
            n += 9.223372036854776E18;
        }
        return n;
    }
    
    public BigInteger bigIntegerValue() {
        BigInteger bigInteger = BigInteger.valueOf(this.value & Long.MAX_VALUE);
        if (this.value < 0L) {
            bigInteger = bigInteger.setBit(63);
        }
        return bigInteger;
    }
    
    public int compareTo(final UnsignedLong unsignedLong) {
        Preconditions.checkNotNull(unsignedLong);
        return UnsignedLongs.compare(this.value, unsignedLong.value);
    }
    
    @Override
    public int hashCode() {
        return Longs.hashCode(this.value);
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o instanceof UnsignedLong && this.value == ((UnsignedLong)o).value;
    }
    
    @Override
    public String toString() {
        return UnsignedLongs.toString(this.value);
    }
    
    public String toString(final int n) {
        return UnsignedLongs.toString(this.value, n);
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((UnsignedLong)o);
    }
    
    static {
        ZERO = new UnsignedLong(0L);
        ONE = new UnsignedLong(1L);
        MAX_VALUE = new UnsignedLong(-1L);
    }
}
