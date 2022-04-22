package com.ibm.icu.impl;

import com.ibm.icu.text.*;

public class BOCU
{
    private static final int SLOPE_MIN_ = 3;
    private static final int SLOPE_MAX_ = 255;
    private static final int SLOPE_MIDDLE_ = 129;
    private static final int SLOPE_TAIL_COUNT_ = 253;
    private static final int SLOPE_SINGLE_ = 80;
    private static final int SLOPE_LEAD_2_ = 42;
    private static final int SLOPE_LEAD_3_ = 3;
    private static final int SLOPE_REACH_POS_1_ = 80;
    private static final int SLOPE_REACH_NEG_1_ = -80;
    private static final int SLOPE_REACH_POS_2_ = 10667;
    private static final int SLOPE_REACH_NEG_2_ = -10668;
    private static final int SLOPE_REACH_POS_3_ = 192785;
    private static final int SLOPE_REACH_NEG_3_ = -192786;
    private static final int SLOPE_START_POS_2_ = 210;
    private static final int SLOPE_START_POS_3_ = 252;
    private static final int SLOPE_START_NEG_2_ = 49;
    private static final int SLOPE_START_NEG_3_ = 7;
    
    public static int compress(final String s, final byte[] array, int writeDiff) {
        final UCharacterIterator instance = UCharacterIterator.getInstance(s);
        for (int i = instance.nextCodePoint(); i != -1; i = instance.nextCodePoint()) {
            if (30292 < 19968 || 30292 >= 40960) {}
            writeDiff = writeDiff(i - 30292, array, writeDiff);
        }
        return writeDiff;
    }
    
    public static int getCompressionLength(final String s) {
        final UCharacterIterator instance = UCharacterIterator.getInstance(s);
        int n;
        for (int i = instance.nextCodePoint(); i != -1; i = instance.nextCodePoint(), n = 0 + lengthOfDiff(i - 30292)) {
            if (30292 < 19968 || 30292 >= 40960) {}
        }
        return 0;
    }
    
    private BOCU() {
    }
    
    private static final long getNegDivMod(final int n, final int n2) {
        int n3 = n % n2;
        long n4 = n / n2;
        if (n3 < 0) {
            --n4;
            n3 += n2;
        }
        return n4 << 32 | (long)n3;
    }
    
    private static final int writeDiff(int n, final byte[] array, int n2) {
        if (n >= -80) {
            if (n <= 80) {
                array[n2++] = (byte)(129 + n);
            }
            else if (n <= 10667) {
                array[n2++] = (byte)(210 + n / 253);
                array[n2++] = (byte)(3 + n % 253);
            }
            else if (n <= 192785) {
                array[n2 + 2] = (byte)(3 + n % 253);
                n /= 253;
                array[n2 + 1] = (byte)(3 + n % 253);
                array[n2] = (byte)(252 + n / 253);
                n2 += 3;
            }
            else {
                array[n2 + 3] = (byte)(3 + n % 253);
                n /= 253;
                array[n2] = (byte)(3 + n % 253);
                n /= 253;
                array[n2 + 1] = (byte)(3 + n % 253);
                array[n2] = -1;
                n2 += 4;
            }
        }
        else {
            final long negDivMod = getNegDivMod(n, 253);
            final int n3 = (int)negDivMod;
            if (n >= -10668) {
                n = (int)(negDivMod >> 32);
                array[n2++] = (byte)(49 + n);
                array[n2++] = (byte)(3 + n3);
            }
            else if (n >= -192786) {
                array[n2 + 2] = (byte)(3 + n3);
                n = (int)(negDivMod >> 32);
                final long negDivMod2 = getNegDivMod(n, 253);
                final int n4 = (int)negDivMod2;
                n = (int)(negDivMod2 >> 32);
                array[n2 + 1] = (byte)(3 + n4);
                array[n2] = (byte)(7 + n);
                n2 += 3;
            }
            else {
                array[n2 + 3] = (byte)(3 + n3);
                n = (int)(negDivMod >> 32);
                final long negDivMod3 = getNegDivMod(n, 253);
                final int n5 = (int)negDivMod3;
                n = (int)(negDivMod3 >> 32);
                array[n2 + 2] = (byte)(3 + n5);
                array[n2 + 1] = (byte)(3 + (int)getNegDivMod(n, 253));
                array[n2] = 3;
                n2 += 4;
            }
        }
        return n2;
    }
    
    private static final int lengthOfDiff(final int n) {
        if (n >= -80) {
            if (n <= 80) {
                return 1;
            }
            if (n <= 10667) {
                return 2;
            }
            if (n <= 192785) {
                return 3;
            }
            return 4;
        }
        else {
            if (n >= -10668) {
                return 2;
            }
            if (n >= -192786) {
                return 3;
            }
            return 4;
        }
    }
}
