package com.google.common.math;

import java.math.*;
import com.google.common.annotations.*;
import com.google.common.base.*;

@GwtCompatible(emulated = true)
public final class IntMath
{
    static final int MAX_POWER_OF_SQRT2_UNSIGNED = -1257966797;
    static final byte[] maxLog10ForLeadingZeros;
    static final int[] powersOf10;
    static final int[] halfPowersOf10;
    static final int FLOOR_SQRT_MAX_INT = 46340;
    private static final int[] factorials;
    static int[] biggestBinomials;
    
    public static boolean isPowerOfTwo(final int n) {
        return n > 0 & (n & n - 1) == 0x0;
    }
    
    @VisibleForTesting
    static int lessThanBranchFree(final int n, final int n2) {
        return ~(~(n - n2)) >>> 31;
    }
    
    public static int log2(final int n, final RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", n);
        switch (roundingMode) {
            case UNNECESSARY: {
                MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(n));
            }
            case DOWN:
            case FLOOR: {
                return 31 - Integer.numberOfLeadingZeros(n);
            }
            case UP:
            case CEILING: {
                return 32 - Integer.numberOfLeadingZeros(n - 1);
            }
            case HALF_DOWN:
            case HALF_UP:
            case HALF_EVEN: {
                final int numberOfLeadingZeros = Integer.numberOfLeadingZeros(n);
                return 31 - numberOfLeadingZeros + lessThanBranchFree(-1257966797 >>> numberOfLeadingZeros, n);
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    @GwtIncompatible("need BigIntegerMath to adequately test")
    public static int log10(final int n, final RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", n);
        final int log10Floor = log10Floor(n);
        final int n2 = IntMath.powersOf10[log10Floor];
        switch (roundingMode) {
            case UNNECESSARY: {
                MathPreconditions.checkRoundingUnnecessary(n == n2);
            }
            case DOWN:
            case FLOOR: {
                return log10Floor;
            }
            case UP:
            case CEILING: {
                return log10Floor + lessThanBranchFree(n2, n);
            }
            case HALF_DOWN:
            case HALF_UP:
            case HALF_EVEN: {
                return log10Floor + lessThanBranchFree(IntMath.halfPowersOf10[log10Floor], n);
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    private static int log10Floor(final int n) {
        final byte b = IntMath.maxLog10ForLeadingZeros[Integer.numberOfLeadingZeros(n)];
        return b - lessThanBranchFree(n, IntMath.powersOf10[b]);
    }
    
    @GwtIncompatible("failing tests")
    public static int pow(int n, int n2) {
        MathPreconditions.checkNonNegative("exponent", n2);
        switch (n) {
            case 0: {
                return (n2 == 0) ? 1 : 0;
            }
            case 1: {
                return 1;
            }
            case -1: {
                return ((n2 & 0x1) == 0x0) ? 1 : -1;
            }
            case 2: {
                return (n2 < 32) ? (1 << n2) : 0;
            }
            case -2: {
                if (n2 < 32) {
                    return ((n2 & 0x1) == 0x0) ? (1 << n2) : (-(1 << n2));
                }
                return 0;
            }
            default: {
                int n3 = 1;
                while (true) {
                    switch (n2) {
                        case 0: {
                            return n3;
                        }
                        case 1: {
                            return n * n3;
                        }
                        default: {
                            n3 *= (((n2 & 0x1) == 0x0) ? 1 : n);
                            n *= n;
                            n2 >>= 1;
                            continue;
                        }
                    }
                }
                break;
            }
        }
    }
    
    @GwtIncompatible("need BigIntegerMath to adequately test")
    public static int sqrt(final int n, final RoundingMode roundingMode) {
        MathPreconditions.checkNonNegative("x", n);
        final int sqrtFloor = sqrtFloor(n);
        switch (roundingMode) {
            case UNNECESSARY: {
                MathPreconditions.checkRoundingUnnecessary(sqrtFloor * sqrtFloor == n);
            }
            case DOWN:
            case FLOOR: {
                return sqrtFloor;
            }
            case UP:
            case CEILING: {
                return sqrtFloor + lessThanBranchFree(sqrtFloor * sqrtFloor, n);
            }
            case HALF_DOWN:
            case HALF_UP:
            case HALF_EVEN: {
                return sqrtFloor + lessThanBranchFree(sqrtFloor * sqrtFloor + sqrtFloor, n);
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    private static int sqrtFloor(final int n) {
        return (int)Math.sqrt(n);
    }
    
    public static int divide(final int n, final int n2, final RoundingMode roundingMode) {
        Preconditions.checkNotNull(roundingMode);
        if (n2 == 0) {
            throw new ArithmeticException("/ by zero");
        }
        final int n3 = n / n2;
        final int n4 = n - n2 * n3;
        if (n4 == 0) {
            return n3;
        }
        final int n5 = 0x1 | (n ^ n2) >> 31;
        boolean b = false;
        switch (roundingMode) {
            case UNNECESSARY: {
                MathPreconditions.checkRoundingUnnecessary(n4 == 0);
            }
            case DOWN: {
                b = false;
                break;
            }
            case UP: {
                b = true;
                break;
            }
            case CEILING: {
                b = (n5 > 0);
                break;
            }
            case FLOOR: {
                b = (n5 < 0);
                break;
            }
            case HALF_DOWN:
            case HALF_UP:
            case HALF_EVEN: {
                final int abs = Math.abs(n4);
                final int n6 = abs - (Math.abs(n2) - abs);
                if (n6 == 0) {
                    b = (roundingMode == RoundingMode.HALF_UP || (roundingMode == RoundingMode.HALF_EVEN & (n3 & 0x1) != 0x0));
                    break;
                }
                b = (n6 > 0);
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
        return b ? (n3 + n5) : n3;
    }
    
    public static int mod(final int n, final int n2) {
        if (n2 <= 0) {
            throw new ArithmeticException("Modulus " + n2 + " must be > 0");
        }
        final int n3 = n % n2;
        return (n3 >= 0) ? n3 : (n3 + n2);
    }
    
    public static int gcd(int i, int n) {
        MathPreconditions.checkNonNegative("a", i);
        MathPreconditions.checkNonNegative("b", n);
        if (i == 0) {
            return n;
        }
        if (n == 0) {
            return i;
        }
        final int numberOfTrailingZeros = Integer.numberOfTrailingZeros(i);
        i >>= numberOfTrailingZeros;
        final int numberOfTrailingZeros2 = Integer.numberOfTrailingZeros(n);
        int n2;
        int n3;
        for (n >>= numberOfTrailingZeros2; i != n; i = n2 - n3 - n3, n += n3, i >>= Integer.numberOfTrailingZeros(i)) {
            n2 = i - n;
            n3 = (n2 & n2 >> 31);
        }
        return i << Math.min(numberOfTrailingZeros, numberOfTrailingZeros2);
    }
    
    public static int checkedAdd(final int n, final int n2) {
        final long n3 = n + (long)n2;
        MathPreconditions.checkNoOverflow(n3 == (int)n3);
        return (int)n3;
    }
    
    public static int checkedSubtract(final int n, final int n2) {
        final long n3 = n - (long)n2;
        MathPreconditions.checkNoOverflow(n3 == (int)n3);
        return (int)n3;
    }
    
    public static int checkedMultiply(final int n, final int n2) {
        final long n3 = n * (long)n2;
        MathPreconditions.checkNoOverflow(n3 == (int)n3);
        return (int)n3;
    }
    
    public static int checkedPow(int n, int n2) {
        MathPreconditions.checkNonNegative("exponent", n2);
        switch (n) {
            case 0: {
                return (n2 == 0) ? 1 : 0;
            }
            case 1: {
                return 1;
            }
            case -1: {
                return ((n2 & 0x1) == 0x0) ? 1 : -1;
            }
            case 2: {
                MathPreconditions.checkNoOverflow(n2 < 31);
                return 1 << n2;
            }
            case -2: {
                MathPreconditions.checkNoOverflow(n2 < 32);
                return ((n2 & 0x1) == 0x0) ? (1 << n2) : (-1 << n2);
            }
            default: {
                int checkedMultiply = 1;
                while (true) {
                    switch (n2) {
                        case 0: {
                            return checkedMultiply;
                        }
                        case 1: {
                            return checkedMultiply(checkedMultiply, n);
                        }
                        default: {
                            if ((n2 & 0x1) != 0x0) {
                                checkedMultiply = checkedMultiply(checkedMultiply, n);
                            }
                            n2 >>= 1;
                            if (n2 > 0) {
                                MathPreconditions.checkNoOverflow(-46340 <= n & n <= 46340);
                                n *= n;
                                continue;
                            }
                            continue;
                        }
                    }
                }
                break;
            }
        }
    }
    
    public static int factorial(final int n) {
        MathPreconditions.checkNonNegative("n", n);
        return (n < IntMath.factorials.length) ? IntMath.factorials[n] : Integer.MAX_VALUE;
    }
    
    @GwtIncompatible("need BigIntegerMath to adequately test")
    public static int binomial(final int n, int n2) {
        MathPreconditions.checkNonNegative("n", n);
        MathPreconditions.checkNonNegative("k", n2);
        Preconditions.checkArgument(n2 <= n, "k (%s) > n (%s)", n2, n);
        if (n2 > n >> 1) {
            n2 = n - n2;
        }
        if (n2 >= IntMath.biggestBinomials.length || n > IntMath.biggestBinomials[n2]) {
            return Integer.MAX_VALUE;
        }
        switch (n2) {
            case 0: {
                return 1;
            }
            case 1: {
                return n;
            }
            default: {
                long n3 = 1L;
                for (int i = 0; i < n2; ++i) {
                    n3 = n3 * (n - i) / (i + 1);
                }
                return (int)n3;
            }
        }
    }
    
    public static int mean(final int n, final int n2) {
        return (n & n2) + ((n ^ n2) >> 1);
    }
    
    private IntMath() {
    }
    
    static {
        maxLog10ForLeadingZeros = new byte[] { 9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0, 0 };
        powersOf10 = new int[] { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000 };
        halfPowersOf10 = new int[] { 3, 31, 316, 3162, 31622, 316227, 3162277, 31622776, 316227766, Integer.MAX_VALUE };
        factorials = new int[] { 1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600 };
        IntMath.biggestBinomials = new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE, 65536, 2345, 477, 193, 110, 75, 58, 49, 43, 39, 37, 35, 34, 34, 33 };
    }
}
