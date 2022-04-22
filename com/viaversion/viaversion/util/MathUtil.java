package com.viaversion.viaversion.util;

public final class MathUtil
{
    public static int ceilLog2(final int n) {
        return (n > 0) ? (32 - Integer.numberOfLeadingZeros(n - 1)) : 0;
    }
    
    public static int clamp(final int n, final int n2, final int n3) {
        if (n < n2) {
            return n2;
        }
        return (n > n3) ? n3 : n;
    }
}
