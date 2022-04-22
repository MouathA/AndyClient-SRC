package com.google.common.math;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.math.*;

@GwtCompatible
final class MathPreconditions
{
    static int checkPositive(@Nullable final String s, final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(s + " (" + n + ") must be > 0");
        }
        return n;
    }
    
    static long checkPositive(@Nullable final String s, final long n) {
        if (n <= 0L) {
            throw new IllegalArgumentException(s + " (" + n + ") must be > 0");
        }
        return n;
    }
    
    static BigInteger checkPositive(@Nullable final String s, final BigInteger bigInteger) {
        if (bigInteger.signum() <= 0) {
            throw new IllegalArgumentException(s + " (" + bigInteger + ") must be > 0");
        }
        return bigInteger;
    }
    
    static int checkNonNegative(@Nullable final String s, final int n) {
        if (n < 0) {
            throw new IllegalArgumentException(s + " (" + n + ") must be >= 0");
        }
        return n;
    }
    
    static long checkNonNegative(@Nullable final String s, final long n) {
        if (n < 0L) {
            throw new IllegalArgumentException(s + " (" + n + ") must be >= 0");
        }
        return n;
    }
    
    static BigInteger checkNonNegative(@Nullable final String s, final BigInteger bigInteger) {
        if (bigInteger.signum() < 0) {
            throw new IllegalArgumentException(s + " (" + bigInteger + ") must be >= 0");
        }
        return bigInteger;
    }
    
    static double checkNonNegative(@Nullable final String s, final double n) {
        if (n < 0.0) {
            throw new IllegalArgumentException(s + " (" + n + ") must be >= 0");
        }
        return n;
    }
    
    static void checkRoundingUnnecessary(final boolean b) {
        if (!b) {
            throw new ArithmeticException("mode was UNNECESSARY, but rounding was necessary");
        }
    }
    
    static void checkInRange(final boolean b) {
        if (!b) {
            throw new ArithmeticException("not in range");
        }
    }
    
    static void checkNoOverflow(final boolean b) {
        if (!b) {
            throw new ArithmeticException("overflow");
        }
    }
    
    private MathPreconditions() {
    }
}
