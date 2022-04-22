package com.google.common.math;

import com.google.common.annotations.*;
import java.math.*;
import com.google.common.base.*;
import com.google.common.primitives.*;
import java.util.*;

@GwtCompatible(emulated = true)
public final class DoubleMath
{
    private static final double MIN_INT_AS_DOUBLE = -2.147483648E9;
    private static final double MAX_INT_AS_DOUBLE = 2.147483647E9;
    private static final double MIN_LONG_AS_DOUBLE = -9.223372036854776E18;
    private static final double MAX_LONG_AS_DOUBLE_PLUS_ONE = 9.223372036854776E18;
    private static final double LN_2;
    @VisibleForTesting
    static final int MAX_FACTORIAL = 170;
    @VisibleForTesting
    static final double[] everySixteenthFactorial;
    
    @GwtIncompatible("#isMathematicalInteger, com.google.common.math.DoubleUtils")
    static double roundIntermediate(final double n, final RoundingMode roundingMode) {
        if (!DoubleUtils.isFinite(n)) {
            throw new ArithmeticException("input is infinite or NaN");
        }
        switch (roundingMode) {
            case UNNECESSARY: {
                MathPreconditions.checkRoundingUnnecessary(isMathematicalInteger(n));
                return n;
            }
            case FLOOR: {
                if (n >= 0.0 || isMathematicalInteger(n)) {
                    return n;
                }
                return n - 1.0;
            }
            case CEILING: {
                if (n <= 0.0 || isMathematicalInteger(n)) {
                    return n;
                }
                return n + 1.0;
            }
            case DOWN: {
                return n;
            }
            case UP: {
                if (isMathematicalInteger(n)) {
                    return n;
                }
                return n + Math.copySign(1.0, n);
            }
            case HALF_EVEN: {
                return Math.rint(n);
            }
            case HALF_UP: {
                final double rint = Math.rint(n);
                if (Math.abs(n - rint) == 0.5) {
                    return n + Math.copySign(0.5, n);
                }
                return rint;
            }
            case HALF_DOWN: {
                final double rint2 = Math.rint(n);
                if (Math.abs(n - rint2) == 0.5) {
                    return n;
                }
                return rint2;
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    @GwtIncompatible("#roundIntermediate")
    public static int roundToInt(final double n, final RoundingMode roundingMode) {
        final double roundIntermediate = roundIntermediate(n, roundingMode);
        MathPreconditions.checkInRange(roundIntermediate > -2.147483649E9 & roundIntermediate < 2.147483648E9);
        return (int)roundIntermediate;
    }
    
    @GwtIncompatible("#roundIntermediate")
    public static long roundToLong(final double n, final RoundingMode roundingMode) {
        final double roundIntermediate = roundIntermediate(n, roundingMode);
        MathPreconditions.checkInRange(-9.223372036854776E18 - roundIntermediate < 1.0 & roundIntermediate < 9.223372036854776E18);
        return (long)roundIntermediate;
    }
    
    @GwtIncompatible("#roundIntermediate, java.lang.Math.getExponent, com.google.common.math.DoubleUtils")
    public static BigInteger roundToBigInteger(double roundIntermediate, final RoundingMode roundingMode) {
        roundIntermediate = roundIntermediate(roundIntermediate, roundingMode);
        if (-9.223372036854776E18 - roundIntermediate < 1.0 & roundIntermediate < 9.223372036854776E18) {
            return BigInteger.valueOf((long)roundIntermediate);
        }
        final BigInteger shiftLeft = BigInteger.valueOf(DoubleUtils.getSignificand(roundIntermediate)).shiftLeft(Math.getExponent(roundIntermediate) - 52);
        return (roundIntermediate < 0.0) ? shiftLeft.negate() : shiftLeft;
    }
    
    @GwtIncompatible("com.google.common.math.DoubleUtils")
    public static boolean isPowerOfTwo(final double n) {
        return n > 0.0 && DoubleUtils.isFinite(n) && LongMath.isPowerOfTwo(DoubleUtils.getSignificand(n));
    }
    
    public static double log2(final double n) {
        return Math.log(n) / DoubleMath.LN_2;
    }
    
    @GwtIncompatible("java.lang.Math.getExponent, com.google.common.math.DoubleUtils")
    public static int log2(final double n, final RoundingMode roundingMode) {
        Preconditions.checkArgument(n > 0.0 && DoubleUtils.isFinite(n), (Object)"x must be positive and finite");
        final int exponent = Math.getExponent(n);
        if (!DoubleUtils.isNormal(n)) {
            return log2(n * 4.503599627370496E15, roundingMode) - 52;
        }
        switch (roundingMode) {
            case UNNECESSARY: {
                MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(n));
            }
            case FLOOR: {
                break;
            }
            case CEILING: {
                final boolean b = !isPowerOfTwo(n);
                break;
            }
            case DOWN: {
                final boolean b2 = exponent < 0 & !isPowerOfTwo(n);
                break;
            }
            case UP: {
                final boolean b3 = exponent >= 0 & !isPowerOfTwo(n);
                break;
            }
            case HALF_EVEN:
            case HALF_UP:
            case HALF_DOWN: {
                final double scaleNormalize = DoubleUtils.scaleNormalize(n);
                final boolean b4 = scaleNormalize * scaleNormalize > 2.0;
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
        return false ? (exponent + 1) : exponent;
    }
    
    @GwtIncompatible("java.lang.Math.getExponent, com.google.common.math.DoubleUtils")
    public static boolean isMathematicalInteger(final double n) {
        return DoubleUtils.isFinite(n) && (n == 0.0 || 52 - Long.numberOfTrailingZeros(DoubleUtils.getSignificand(n)) <= Math.getExponent(n));
    }
    
    public static double factorial(final int n) {
        MathPreconditions.checkNonNegative("n", n);
        if (n > 170) {
            return Double.POSITIVE_INFINITY;
        }
        double n2 = 1.0;
        for (int i = 1 + (n & 0xFFFFFFF0); i <= n; ++i) {
            n2 *= i;
        }
        return n2 * DoubleMath.everySixteenthFactorial[n >> 4];
    }
    
    public static boolean fuzzyEquals(final double n, final double n2, final double n3) {
        MathPreconditions.checkNonNegative("tolerance", n3);
        return Math.copySign(n - n2, 1.0) <= n3 || n == n2 || (Double.isNaN(n) && Double.isNaN(n2));
    }
    
    public static int fuzzyCompare(final double n, final double n2, final double n3) {
        if (fuzzyEquals(n, n2, n3)) {
            return 0;
        }
        if (n < n2) {
            return -1;
        }
        if (n > n2) {
            return 1;
        }
        return Booleans.compare(Double.isNaN(n), Double.isNaN(n2));
    }
    
    @GwtIncompatible("MeanAccumulator")
    public static double mean(final double... array) {
        final MeanAccumulator meanAccumulator = new MeanAccumulator(null);
        while (0 < array.length) {
            meanAccumulator.add(array[0]);
            int n = 0;
            ++n;
        }
        return meanAccumulator.mean();
    }
    
    @GwtIncompatible("MeanAccumulator")
    public static double mean(final int... array) {
        final MeanAccumulator meanAccumulator = new MeanAccumulator(null);
        while (0 < array.length) {
            meanAccumulator.add(array[0]);
            int n = 0;
            ++n;
        }
        return meanAccumulator.mean();
    }
    
    @GwtIncompatible("MeanAccumulator")
    public static double mean(final long... array) {
        final MeanAccumulator meanAccumulator = new MeanAccumulator(null);
        while (0 < array.length) {
            meanAccumulator.add((double)array[0]);
            int n = 0;
            ++n;
        }
        return meanAccumulator.mean();
    }
    
    @GwtIncompatible("MeanAccumulator")
    public static double mean(final Iterable iterable) {
        final MeanAccumulator meanAccumulator = new MeanAccumulator(null);
        final Iterator<Number> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            meanAccumulator.add(iterator.next().doubleValue());
        }
        return meanAccumulator.mean();
    }
    
    @GwtIncompatible("MeanAccumulator")
    public static double mean(final Iterator iterator) {
        final MeanAccumulator meanAccumulator = new MeanAccumulator(null);
        while (iterator.hasNext()) {
            meanAccumulator.add(iterator.next().doubleValue());
        }
        return meanAccumulator.mean();
    }
    
    private DoubleMath() {
    }
    
    static {
        LN_2 = Math.log(2.0);
        everySixteenthFactorial = new double[] { 1.0, 2.0922789888E13, 2.631308369336935E35, 1.2413915592536073E61, 1.2688693218588417E89, 7.156945704626381E118, 9.916779348709496E149, 1.974506857221074E182, 3.856204823625804E215, 5.5502938327393044E249, 4.7147236359920616E284 };
    }
    
    @GwtIncompatible("com.google.common.math.DoubleUtils")
    private static final class MeanAccumulator
    {
        private long count;
        private double mean;
        
        private MeanAccumulator() {
            this.count = 0L;
            this.mean = 0.0;
        }
        
        void add(final double n) {
            Preconditions.checkArgument(DoubleUtils.isFinite(n));
            ++this.count;
            this.mean += (n - this.mean) / this.count;
        }
        
        double mean() {
            Preconditions.checkArgument(this.count > 0L, (Object)"Cannot take mean of 0 values");
            return this.mean;
        }
        
        MeanAccumulator(final DoubleMath$1 object) {
            this();
        }
    }
}
