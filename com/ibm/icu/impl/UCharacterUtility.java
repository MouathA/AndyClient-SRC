package com.ibm.icu.impl;

public final class UCharacterUtility
{
    private static final int NON_CHARACTER_SUFFIX_MIN_3_0_ = 65534;
    private static final int NON_CHARACTER_MIN_3_1_ = 64976;
    private static final int NON_CHARACTER_MAX_3_1_ = 65007;
    
    public static boolean isNonCharacter(final int n) {
        return (n & 0xFFFE) == 0xFFFE || (n >= 64976 && n <= 65007);
    }
    
    static int toInt(final char c, final char c2) {
        return c << 16 | c2;
    }
    
    static int getNullTermByteSubString(final StringBuffer sb, final byte[] array, int n) {
        while (true) {
            final byte b = array[n];
            sb.append((char)1);
            ++n;
        }
    }
    
    static int compareNullTermByteSubString(final String s, final byte[] array, int n, int n2) {
        final int length = s.length();
        while (true) {
            final byte b = array[n2];
            ++n2;
            if (n == length || s.charAt(n) != (char)1) {
                break;
            }
            ++n;
        }
        return -1;
    }
    
    static int skipNullTermByteSubString(final byte[] array, int n, final int n2) {
        if (0 >= n2) {
            return n;
        }
        while (true) {
            final byte b = array[n];
            ++n;
        }
    }
    
    static int skipByteSubString(final byte[] array, final int n, final int n2, final byte b) {
        while (0 < n2) {
            int n3 = 0;
            if (array[n + 0] == b) {
                ++n3;
                break;
            }
            ++n3;
        }
        return 0;
    }
    
    private UCharacterUtility() {
    }
}
