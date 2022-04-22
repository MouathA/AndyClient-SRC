package DTool.util;

import java.util.*;
import java.math.*;

public class MathUtils
{
    private static final Random rng;
    
    static {
        rng = new Random();
    }
    
    public static int getMiddle(final int n, final int n2) {
        return (n + n2) / 2;
    }
    
    public static double getMiddleint(final double n, final double n2) {
        return (n + n2) / 2.0;
    }
    
    public static float getAngleDifference(final float n, final float n2) {
        final float n3 = Math.abs(n2 - n) % 360.0f;
        return (n3 > 180.0f) ? (360.0f - n3) : n3;
    }
    
    public static int getRandomInRange(final int n, final int n2) {
        return new Random().nextInt(n2 - n + 1) + n;
    }
    
    public static double getRandomInRange(final double n, final double n2) {
        return new Random().nextDouble() * (n2 - n) + n;
    }
    
    public static float getRandomInRange(final float n, final float n2) {
        return new Random().nextFloat() * (n2 - n) + n;
    }
    
    public static double round(final double n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(n).setScale(n2, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static int getRandom(final int n, final int n2) {
        return n + MathUtils.rng.nextInt(n2 - n + 1);
    }
    
    public static boolean isInteger(final String s) {
        Integer.parseInt(s);
        return true;
    }
    
    public static double roundToDecimalPlace(final double n, final double n2) {
        final double n3 = n2 / 2.0;
        final double n4 = Math.floor(n / n2) * n2;
        if (n >= n4 + n3) {
            return new BigDecimal(Math.ceil(n / n2) * n2, MathContext.DECIMAL64).stripTrailingZeros().doubleValue();
        }
        return new BigDecimal(n4, MathContext.DECIMAL64).stripTrailingZeros().doubleValue();
    }
    
    public static double getIncremental(final double n, final double n2) {
        final double n3 = 1.0 / n2;
        return Math.round(n * n3) / n3;
    }
}
