package com.viaversion.viaversion.libs.fastutil;

public class HashCommon
{
    private static final int INT_PHI = -1640531527;
    private static final int INV_INT_PHI = 340573321;
    private static final long LONG_PHI = -7046029254386353131L;
    private static final long INV_LONG_PHI = -1018231460777725123L;
    
    protected HashCommon() {
    }
    
    public static int murmurHash3(int n) {
        n ^= n >>> 16;
        n *= -2048144789;
        n ^= n >>> 13;
        n *= -1028477387;
        n ^= n >>> 16;
        return n;
    }
    
    public static long murmurHash3(long n) {
        n ^= n >>> 33;
        n *= -49064778989728563L;
        n ^= n >>> 33;
        n *= -4265267296055464877L;
        n ^= n >>> 33;
        return n;
    }
    
    public static int mix(final int n) {
        final int n2 = n * -1640531527;
        return n2 ^ n2 >>> 16;
    }
    
    public static int invMix(final int n) {
        return (n ^ n >>> 16) * 340573321;
    }
    
    public static long mix(final long n) {
        final long n2 = n * -7046029254386353131L;
        final long n3 = n2 ^ n2 >>> 32;
        return n3 ^ n3 >>> 16;
    }
    
    public static long invMix(long n) {
        n ^= n >>> 32;
        n ^= n >>> 16;
        return (n ^ n >>> 32) * -1018231460777725123L;
    }
    
    public static int float2int(final float n) {
        return Float.floatToRawIntBits(n);
    }
    
    public static int double2int(final double n) {
        final long doubleToRawLongBits = Double.doubleToRawLongBits(n);
        return (int)(doubleToRawLongBits ^ doubleToRawLongBits >>> 32);
    }
    
    public static int long2int(final long n) {
        return (int)(n ^ n >>> 32);
    }
    
    public static int nextPowerOfTwo(int n) {
        if (n == 0) {
            return 1;
        }
        n = (--n | n >> 1);
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        return (n | n >> 16) + 1;
    }
    
    public static long nextPowerOfTwo(long n) {
        if (n == 0L) {
            return 1L;
        }
        --n;
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        return (n | n >> 32) + 1L;
    }
    
    public static int maxFill(final int n, final float n2) {
        return Math.min((int)Math.ceil(n * n2), n - 1);
    }
    
    public static long maxFill(final long n, final float n2) {
        return Math.min((long)Math.ceil(n * n2), n - 1L);
    }
    
    public static int arraySize(final int n, final float n2) {
        final long max = Math.max(2L, nextPowerOfTwo((long)Math.ceil(n / n2)));
        if (max > 1073741824L) {
            throw new IllegalArgumentException("Too large (" + n + " expected elements with load factor " + n2 + ")");
        }
        return (int)max;
    }
    
    public static long bigArraySize(final long n, final float n2) {
        return nextPowerOfTwo((long)Math.ceil(n / n2));
    }
}
