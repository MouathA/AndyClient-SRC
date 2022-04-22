package com.google.common.math;

import com.google.common.base.*;
import com.google.common.annotations.*;
import java.math.*;
import java.util.*;

@GwtCompatible(emulated = true)
public final class BigIntegerMath
{
    @VisibleForTesting
    static final int SQRT2_PRECOMPUTE_THRESHOLD = 256;
    @VisibleForTesting
    static final BigInteger SQRT2_PRECOMPUTED_BITS;
    private static final double LN_10;
    private static final double LN_2;
    
    public static boolean isPowerOfTwo(final BigInteger bigInteger) {
        Preconditions.checkNotNull(bigInteger);
        return bigInteger.signum() > 0 && bigInteger.getLowestSetBit() == bigInteger.bitLength() - 1;
    }
    
    public static int log2(final BigInteger bigInteger, final RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", (BigInteger)Preconditions.checkNotNull(bigInteger));
        final int n = bigInteger.bitLength() - 1;
        switch (roundingMode) {
            case UNNECESSARY: {
                MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(bigInteger));
            }
            case DOWN:
            case FLOOR: {
                return n;
            }
            case UP:
            case CEILING: {
                return isPowerOfTwo(bigInteger) ? n : (n + 1);
            }
            case HALF_DOWN:
            case HALF_UP:
            case HALF_EVEN: {
                if (n >= 256) {
                    return (bigInteger.pow(2).bitLength() - 1 < 2 * n + 1) ? n : (n + 1);
                }
                if (bigInteger.compareTo(BigIntegerMath.SQRT2_PRECOMPUTED_BITS.shiftRight(256 - n)) <= 0) {
                    return n;
                }
                return n + 1;
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    @GwtIncompatible("TODO")
    public static int log10(final BigInteger bigInteger, final RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", bigInteger);
        if (fitsInLong(bigInteger)) {
            return LongMath.log10(bigInteger.longValue(), roundingMode);
        }
        int n = (int)(log2(bigInteger, RoundingMode.FLOOR) * BigIntegerMath.LN_2 / BigIntegerMath.LN_10);
        BigInteger bigInteger2 = BigInteger.TEN.pow(n);
        int i = bigInteger2.compareTo(bigInteger);
        if (i > 0) {
            do {
                --n;
                bigInteger2 = bigInteger2.divide(BigInteger.TEN);
                i = bigInteger2.compareTo(bigInteger);
            } while (i > 0);
        }
        else {
            BigInteger bigInteger3 = BigInteger.TEN.multiply(bigInteger2);
            for (int j = bigInteger3.compareTo(bigInteger); j <= 0; j = bigInteger3.compareTo(bigInteger)) {
                ++n;
                bigInteger2 = bigInteger3;
                i = j;
                bigInteger3 = BigInteger.TEN.multiply(bigInteger2);
            }
        }
        final int n2 = n;
        final BigInteger bigInteger4 = bigInteger2;
        final int n3 = i;
        switch (roundingMode) {
            case UNNECESSARY: {
                MathPreconditions.checkRoundingUnnecessary(n3 == 0);
            }
            case DOWN:
            case FLOOR: {
                return n2;
            }
            case UP:
            case CEILING: {
                return bigInteger4.equals(bigInteger) ? n2 : (n2 + 1);
            }
            case HALF_DOWN:
            case HALF_UP:
            case HALF_EVEN: {
                return (bigInteger.pow(2).compareTo(bigInteger4.pow(2).multiply(BigInteger.TEN)) <= 0) ? n2 : (n2 + 1);
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    @GwtIncompatible("TODO")
    public static BigInteger sqrt(final BigInteger bigInteger, final RoundingMode roundingMode) {
        MathPreconditions.checkNonNegative("x", bigInteger);
        if (fitsInLong(bigInteger)) {
            return BigInteger.valueOf(LongMath.sqrt(bigInteger.longValue(), roundingMode));
        }
        final BigInteger sqrtFloor = sqrtFloor(bigInteger);
        switch (roundingMode) {
            case UNNECESSARY: {
                MathPreconditions.checkRoundingUnnecessary(sqrtFloor.pow(2).equals(bigInteger));
            }
            case DOWN:
            case FLOOR: {
                return sqrtFloor;
            }
            case UP:
            case CEILING: {
                final int intValue = sqrtFloor.intValue();
                return (intValue * intValue == bigInteger.intValue() && sqrtFloor.pow(2).equals(bigInteger)) ? sqrtFloor : sqrtFloor.add(BigInteger.ONE);
            }
            case HALF_DOWN:
            case HALF_UP:
            case HALF_EVEN: {
                return (sqrtFloor.pow(2).add(sqrtFloor).compareTo(bigInteger) >= 0) ? sqrtFloor : sqrtFloor.add(BigInteger.ONE);
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    @GwtIncompatible("TODO")
    private static BigInteger sqrtFloor(final BigInteger bigInteger) {
        final int log2 = log2(bigInteger, RoundingMode.FLOOR);
        BigInteger bigInteger2;
        if (log2 < 1023) {
            bigInteger2 = sqrtApproxWithDoubles(bigInteger);
        }
        else {
            final int n = log2 - 52 & 0xFFFFFFFE;
            bigInteger2 = sqrtApproxWithDoubles(bigInteger.shiftRight(n)).shiftLeft(n >> 1);
        }
        BigInteger bigInteger3 = bigInteger2.add(bigInteger.divide(bigInteger2)).shiftRight(1);
        if (bigInteger2.equals(bigInteger3)) {
            return bigInteger2;
        }
        BigInteger bigInteger4;
        do {
            bigInteger4 = bigInteger3;
            bigInteger3 = bigInteger4.add(bigInteger.divide(bigInteger4)).shiftRight(1);
        } while (bigInteger3.compareTo(bigInteger4) < 0);
        return bigInteger4;
    }
    
    @GwtIncompatible("TODO")
    private static BigInteger sqrtApproxWithDoubles(final BigInteger bigInteger) {
        return DoubleMath.roundToBigInteger(Math.sqrt(DoubleUtils.bigToDouble(bigInteger)), RoundingMode.HALF_EVEN);
    }
    
    @GwtIncompatible("TODO")
    public static BigInteger divide(final BigInteger bigInteger, final BigInteger bigInteger2, final RoundingMode roundingMode) {
        return new BigDecimal(bigInteger).divide(new BigDecimal(bigInteger2), 0, roundingMode).toBigIntegerExact();
    }
    
    public static BigInteger factorial(final int n) {
        MathPreconditions.checkNonNegative("n", n);
        if (n < LongMath.factorials.length) {
            return BigInteger.valueOf(LongMath.factorials[n]);
        }
        final ArrayList<BigInteger> list = new ArrayList<BigInteger>(IntMath.divide(n * IntMath.log2(n, RoundingMode.CEILING), 64, RoundingMode.CEILING));
        final int length = LongMath.factorials.length;
        final long n2 = LongMath.factorials[length - 1];
        int numberOfTrailingZeros = Long.numberOfTrailingZeros(n2);
        long n3 = n2 >> numberOfTrailingZeros;
        final int n4 = LongMath.log2(n3, RoundingMode.FLOOR) + 1;
        int n5 = LongMath.log2(length, RoundingMode.FLOOR) + 1;
        int n6 = 1 << n5 - 1;
        for (long n7 = length; n7 <= n; ++n7) {
            if ((n7 & (long)n6) != 0x0L) {
                n6 <<= 1;
                ++n5;
            }
            final int numberOfTrailingZeros2 = Long.numberOfTrailingZeros(n7);
            final long n8 = n7 >> numberOfTrailingZeros2;
            numberOfTrailingZeros += numberOfTrailingZeros2;
            if (n5 - numberOfTrailingZeros2 + 0 >= 64) {
                list.add(BigInteger.valueOf(n3));
                n3 = 1L;
            }
            n3 *= n8;
            final int n9 = LongMath.log2(n3, RoundingMode.FLOOR) + 1;
        }
        if (n3 > 1L) {
            list.add(BigInteger.valueOf(n3));
        }
        return listProduct(list).shiftLeft(numberOfTrailingZeros);
    }
    
    static BigInteger listProduct(final List list) {
        return listProduct(list, 0, list.size());
    }
    
    static BigInteger listProduct(final List list, final int n, final int n2) {
        switch (n2 - n) {
            case 0: {
                return BigInteger.ONE;
            }
            case 1: {
                return list.get(n);
            }
            case 2: {
                return list.get(n).multiply(list.get(n + 1));
            }
            case 3: {
                return list.get(n).multiply(list.get(n + 1)).multiply(list.get(n + 2));
            }
            default: {
                final int n3 = n2 + n >>> 1;
                return listProduct(list, n, n3).multiply(listProduct(list, n3, n2));
            }
        }
    }
    
    public static BigInteger binomial(final int n, int n2) {
        MathPreconditions.checkNonNegative("n", n);
        MathPreconditions.checkNonNegative("k", n2);
        Preconditions.checkArgument(n2 <= n, "k (%s) > n (%s)", n2, n);
        if (n2 > n >> 1) {
            n2 = n - n2;
        }
        if (n2 < LongMath.biggestBinomials.length && n <= LongMath.biggestBinomials[n2]) {
            return BigInteger.valueOf(LongMath.binomial(n, n2));
        }
        BigInteger bigInteger = BigInteger.ONE;
        long n3 = n;
        long n4 = 1L;
        int log2;
        final int n5 = log2 = LongMath.log2(n, RoundingMode.CEILING);
        while (1 < n2) {
            final int n6 = n - 1;
            if (log2 + n5 >= 63) {
                bigInteger = bigInteger.multiply(BigInteger.valueOf(n3)).divide(BigInteger.valueOf(n4));
                n3 = n6;
                n4 = 2;
                log2 = n5;
            }
            else {
                n3 *= n6;
                n4 *= 2;
                log2 += n5;
            }
            int n7 = 0;
            ++n7;
        }
        return bigInteger.multiply(BigInteger.valueOf(n3)).divide(BigInteger.valueOf(n4));
    }
    
    @GwtIncompatible("TODO")
    static boolean fitsInLong(final BigInteger bigInteger) {
        return bigInteger.bitLength() <= 63;
    }
    
    private BigIntegerMath() {
    }
    
    static {
        SQRT2_PRECOMPUTED_BITS = new BigInteger("16a09e667f3bcc908b2fb1366ea957d3e3adec17512775099da2f590b0667322a", 16);
        LN_10 = Math.log(10.0);
        LN_2 = Math.log(2.0);
    }
}
