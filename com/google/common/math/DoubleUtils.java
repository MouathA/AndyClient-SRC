package com.google.common.math;

import com.google.common.base.*;
import java.math.*;

final class DoubleUtils
{
    static final long SIGNIFICAND_MASK = 4503599627370495L;
    static final long EXPONENT_MASK = 9218868437227405312L;
    static final long SIGN_MASK = Long.MIN_VALUE;
    static final int SIGNIFICAND_BITS = 52;
    static final int EXPONENT_BIAS = 1023;
    static final long IMPLICIT_BIT = 4503599627370496L;
    private static final long ONE_BITS;
    
    private DoubleUtils() {
    }
    
    static double nextDown(final double n) {
        return -Math.nextUp(-n);
    }
    
    static long getSignificand(final double n) {
        Preconditions.checkArgument(isFinite(n), (Object)"not a normal value");
        final int exponent = Math.getExponent(n);
        final long n2 = Double.doubleToRawLongBits(n) & 0xFFFFFFFFFFFFFL;
        return (exponent == -1023) ? (n2 << 1) : (n2 | 0x10000000000000L);
    }
    
    static boolean isFinite(final double n) {
        return Math.getExponent(n) <= 1023;
    }
    
    static boolean isNormal(final double n) {
        return Math.getExponent(n) >= -1022;
    }
    
    static double scaleNormalize(final double n) {
        return Double.longBitsToDouble((Double.doubleToRawLongBits(n) & 0xFFFFFFFFFFFFFL) | DoubleUtils.ONE_BITS);
    }
    
    static double bigToDouble(final BigInteger bigInteger) {
        final BigInteger abs = bigInteger.abs();
        final int n = abs.bitLength() - 1;
        if (n < 63) {
            return (double)bigInteger.longValue();
        }
        if (n > 1023) {
            return bigInteger.signum() * Double.POSITIVE_INFINITY;
        }
        final int n2 = n - 52 - 1;
        final long longValue = abs.shiftRight(n2).longValue();
        final long n3 = longValue >> 1 & 0xFFFFFFFFFFFFFL;
        return Double.longBitsToDouble(((long)(n + 1023) << 52) + (((longValue & 0x1L) != 0x0L && ((n3 & 0x1L) != 0x0L || abs.getLowestSetBit() < n2)) ? (n3 + 1L) : n3) | ((long)bigInteger.signum() & Long.MIN_VALUE));
    }
    
    static double ensureNonNegative(final double n) {
        Preconditions.checkArgument(!Double.isNaN(n));
        if (n > 0.0) {
            return n;
        }
        return 0.0;
    }
    
    static {
        ONE_BITS = Double.doubleToRawLongBits(1.0);
    }
}
