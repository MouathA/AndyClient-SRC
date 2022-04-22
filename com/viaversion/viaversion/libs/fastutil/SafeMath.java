package com.viaversion.viaversion.libs.fastutil;

public final class SafeMath
{
    private SafeMath() {
    }
    
    public static char safeIntToChar(final int n) {
        if (n < 0 || 65535 < n) {
            throw new IllegalArgumentException(n + " can't be represented as char");
        }
        return (char)n;
    }
    
    public static byte safeIntToByte(final int n) {
        if (n < -128 || 127 < n) {
            throw new IllegalArgumentException(n + " can't be represented as byte (out of range)");
        }
        return (byte)n;
    }
    
    public static short safeIntToShort(final int n) {
        if (n < -32768 || 32767 < n) {
            throw new IllegalArgumentException(n + " can't be represented as short (out of range)");
        }
        return (short)n;
    }
    
    public static char safeLongToChar(final long n) {
        if (n < 0L || 65535L < n) {
            throw new IllegalArgumentException(n + " can't be represented as int (out of range)");
        }
        return (char)n;
    }
    
    public static byte safeLongToByte(final long n) {
        if (n < -128L || 127L < n) {
            throw new IllegalArgumentException(n + " can't be represented as int (out of range)");
        }
        return (byte)n;
    }
    
    public static short safeLongToShort(final long n) {
        if (n < -32768L || 32767L < n) {
            throw new IllegalArgumentException(n + " can't be represented as int (out of range)");
        }
        return (short)n;
    }
    
    public static int safeLongToInt(final long n) {
        if (n < -2147483648L || 2147483647L < n) {
            throw new IllegalArgumentException(n + " can't be represented as int (out of range)");
        }
        return (int)n;
    }
    
    public static float safeDoubleToFloat(final double n) {
        if (Double.isNaN(n)) {
            return Float.NaN;
        }
        if (Double.isInfinite(n)) {
            return (n < 0.0) ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
        }
        if (n < -3.4028234663852886E38 || 3.4028234663852886E38 < n) {
            throw new IllegalArgumentException(n + " can't be represented as float (out of range)");
        }
        final float n2 = (float)n;
        if (n2 != n) {
            throw new IllegalArgumentException(n + " can't be represented as float (imprecise)");
        }
        return n2;
    }
}
