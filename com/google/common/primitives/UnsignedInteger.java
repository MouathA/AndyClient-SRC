package com.google.common.primitives;

import com.google.common.base.*;
import java.math.*;
import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible(emulated = true)
public final class UnsignedInteger extends Number implements Comparable
{
    public static final UnsignedInteger ZERO;
    public static final UnsignedInteger ONE;
    public static final UnsignedInteger MAX_VALUE;
    private final int value;
    
    private UnsignedInteger(final int n) {
        this.value = (n & -1);
    }
    
    public static UnsignedInteger fromIntBits(final int n) {
        return new UnsignedInteger(n);
    }
    
    public static UnsignedInteger valueOf(final long n) {
        Preconditions.checkArgument((n & 0xFFFFFFFFL) == n, "value (%s) is outside the range for an unsigned integer value", n);
        return fromIntBits((int)n);
    }
    
    public static UnsignedInteger valueOf(final BigInteger bigInteger) {
        Preconditions.checkNotNull(bigInteger);
        Preconditions.checkArgument(bigInteger.signum() >= 0 && bigInteger.bitLength() <= 32, "value (%s) is outside the range for an unsigned integer value", bigInteger);
        return fromIntBits(bigInteger.intValue());
    }
    
    public static UnsignedInteger valueOf(final String s) {
        return valueOf(s, 10);
    }
    
    public static UnsignedInteger valueOf(final String s, final int n) {
        return fromIntBits(UnsignedInts.parseUnsignedInt(s, n));
    }
    
    @CheckReturnValue
    public UnsignedInteger plus(final UnsignedInteger unsignedInteger) {
        return fromIntBits(this.value + ((UnsignedInteger)Preconditions.checkNotNull(unsignedInteger)).value);
    }
    
    @CheckReturnValue
    public UnsignedInteger minus(final UnsignedInteger unsignedInteger) {
        return fromIntBits(this.value - ((UnsignedInteger)Preconditions.checkNotNull(unsignedInteger)).value);
    }
    
    @CheckReturnValue
    @GwtIncompatible("Does not truncate correctly")
    public UnsignedInteger times(final UnsignedInteger unsignedInteger) {
        return fromIntBits(this.value * ((UnsignedInteger)Preconditions.checkNotNull(unsignedInteger)).value);
    }
    
    @CheckReturnValue
    public UnsignedInteger dividedBy(final UnsignedInteger unsignedInteger) {
        return fromIntBits(UnsignedInts.divide(this.value, ((UnsignedInteger)Preconditions.checkNotNull(unsignedInteger)).value));
    }
    
    @CheckReturnValue
    public UnsignedInteger mod(final UnsignedInteger unsignedInteger) {
        return fromIntBits(UnsignedInts.remainder(this.value, ((UnsignedInteger)Preconditions.checkNotNull(unsignedInteger)).value));
    }
    
    @Override
    public int intValue() {
        return this.value;
    }
    
    @Override
    public long longValue() {
        return UnsignedInts.toLong(this.value);
    }
    
    @Override
    public float floatValue() {
        return (float)this.longValue();
    }
    
    @Override
    public double doubleValue() {
        return (double)this.longValue();
    }
    
    public BigInteger bigIntegerValue() {
        return BigInteger.valueOf(this.longValue());
    }
    
    public int compareTo(final UnsignedInteger unsignedInteger) {
        Preconditions.checkNotNull(unsignedInteger);
        return UnsignedInts.compare(this.value, unsignedInteger.value);
    }
    
    @Override
    public int hashCode() {
        return this.value;
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o instanceof UnsignedInteger && this.value == ((UnsignedInteger)o).value;
    }
    
    @Override
    public String toString() {
        return this.toString(10);
    }
    
    public String toString(final int n) {
        return UnsignedInts.toString(this.value, n);
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((UnsignedInteger)o);
    }
    
    static {
        ZERO = fromIntBits(0);
        ONE = fromIntBits(1);
        MAX_VALUE = fromIntBits(-1);
    }
}
