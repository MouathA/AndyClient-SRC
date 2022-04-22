package org.apache.commons.lang3;

import java.util.*;

public class RandomUtils
{
    private static final Random RANDOM;
    
    public static byte[] nextBytes(final int n) {
        Validate.isTrue(n >= 0, "Count cannot be negative.", new Object[0]);
        final byte[] array = new byte[n];
        RandomUtils.RANDOM.nextBytes(array);
        return array;
    }
    
    public static int nextInt(final int n, final int n2) {
        Validate.isTrue(n2 >= n, "Start value must be smaller or equal to end value.", new Object[0]);
        Validate.isTrue(n >= 0, "Both range values must be non-negative.", new Object[0]);
        if (n == n2) {
            return n;
        }
        return n + RandomUtils.RANDOM.nextInt(n2 - n);
    }
    
    public static long nextLong(final long n, final long n2) {
        Validate.isTrue(n2 >= n, "Start value must be smaller or equal to end value.", new Object[0]);
        Validate.isTrue(n >= 0L, "Both range values must be non-negative.", new Object[0]);
        if (n == n2) {
            return n;
        }
        return (long)nextDouble((double)n, (double)n2);
    }
    
    public static double nextDouble(final double n, final double n2) {
        Validate.isTrue(n2 >= n, "Start value must be smaller or equal to end value.", new Object[0]);
        Validate.isTrue(n >= 0.0, "Both range values must be non-negative.", new Object[0]);
        if (n == n2) {
            return n;
        }
        return n + (n2 - n) * RandomUtils.RANDOM.nextDouble();
    }
    
    public static float nextFloat(final float n, final float n2) {
        Validate.isTrue(n2 >= n, "Start value must be smaller or equal to end value.", new Object[0]);
        Validate.isTrue(n >= 0.0f, "Both range values must be non-negative.", new Object[0]);
        if (n == n2) {
            return n;
        }
        return n + (n2 - n) * RandomUtils.RANDOM.nextFloat();
    }
    
    static {
        RANDOM = new Random();
    }
}
