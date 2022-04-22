package com.ibm.icu.impl;

import com.ibm.icu.text.*;
import com.ibm.icu.lang.*;

public final class Punycode
{
    private static final int BASE = 36;
    private static final int TMIN = 1;
    private static final int TMAX = 26;
    private static final int SKEW = 38;
    private static final int DAMP = 700;
    private static final int INITIAL_BIAS = 72;
    private static final int INITIAL_N = 128;
    private static final int HYPHEN = 45;
    private static final int DELIMITER = 45;
    private static final int ZERO = 48;
    private static final int SMALL_A = 97;
    private static final int SMALL_Z = 122;
    private static final int CAPITAL_A = 65;
    private static final int CAPITAL_Z = 90;
    private static final int MAX_CP_COUNT = 200;
    static final int[] basicToDigit;
    
    private static int adaptBias(int i, final int n, final boolean b) {
        if (b) {
            i /= 700;
        }
        else {
            i /= 2;
        }
        int n2;
        for (i += i / n, n2 = 0; i > 455; i /= 35, n2 += 36) {}
        return n2 + 36 * i / (i + 38);
    }
    
    private static char asciiCaseMap(char c, final boolean b) {
        if (b) {
            if ('a' <= c && c <= 'z') {
                c -= ' ';
            }
        }
        else if ('A' <= c && c <= 'Z') {
            c += ' ';
        }
        return c;
    }
    
    private static char digitToBasic(final int n, final boolean b) {
        if (n >= 26) {
            return (char)(22 + n);
        }
        if (b) {
            return (char)(65 + n);
        }
        return (char)(97 + n);
    }
    
    public static StringBuilder encode(final CharSequence charSequence, final boolean[] array) throws StringPrepParseException {
        final int[] array2 = new int[200];
        final int length = charSequence.length();
        final int n = 200;
        final char[] array3 = new char[n];
        final StringBuilder sb = new StringBuilder();
        int n3;
        int n2 = n3 = 0;
        for (int i = 0; i < length; ++i) {
            if (n3 == 200) {
                throw new IndexOutOfBoundsException();
            }
            final char char1 = charSequence.charAt(i);
            if (isBasic(char1)) {
                if (n2 < n) {
                    array2[n3++] = 0;
                    array3[n2] = ((array != null) ? asciiCaseMap(char1, array[i]) : char1);
                }
                ++n2;
            }
            else {
                final boolean b = ((array != null && array[i]) ? 1 : 0) << 31 != 0;
                int n4;
                if (!UTF16.isSurrogate(char1)) {
                    n4 = ((b ? 1 : 0) | char1);
                }
                else {
                    final char char2;
                    if (!UTF16.isLeadSurrogate(char1) || i + 1 >= length || !UTF16.isTrailSurrogate(char2 = charSequence.charAt(i + 1))) {
                        throw new StringPrepParseException("Illegal char found", 1);
                    }
                    ++i;
                    n4 = ((b ? 1 : 0) | UCharacter.getCodePoint(char1, char2));
                }
                array2[n3++] = n4;
            }
        }
        final int n5 = n2;
        if (n5 > 0) {
            if (n2 < n) {
                array3[n2] = '-';
            }
            ++n2;
        }
        int n6 = 128;
        int n7 = 0;
        int adaptBias = 72;
        int j = n5;
        while (j < n3) {
            int n8 = Integer.MAX_VALUE;
            for (int k = 0; k < n3; ++k) {
                final int n9 = array2[k] & Integer.MAX_VALUE;
                if (n6 <= n9 && n9 < n8) {
                    n8 = n9;
                }
            }
            if (n8 - n6 > (2147483447 - n7) / (j + 1)) {
                throw new IllegalStateException("Internal program error");
            }
            n7 += (n8 - n6) * (j + 1);
            n6 = n8;
            for (int l = 0; l < n3; ++l) {
                final int n10 = array2[l] & Integer.MAX_VALUE;
                if (n10 < n6) {
                    ++n7;
                }
                else if (n10 == n6) {
                    int n11 = n7;
                    int n12 = 36;
                    while (true) {
                        int n13 = n12 - adaptBias;
                        if (n13 < 1) {
                            n13 = 1;
                        }
                        else if (n12 >= adaptBias + 26) {
                            n13 = 26;
                        }
                        if (n11 < n13) {
                            break;
                        }
                        if (n2 < n) {
                            array3[n2++] = digitToBasic(n13 + (n11 - n13) % (36 - n13), false);
                        }
                        n11 = (n11 - n13) / (36 - n13);
                        n12 += 36;
                    }
                    if (n2 < n) {
                        array3[n2++] = digitToBasic(n11, array2[l] < 0);
                    }
                    adaptBias = adaptBias(n7, j + 1, j == n5);
                    n7 = 0;
                    ++j;
                }
            }
            ++n7;
            ++n6;
        }
        return sb.append(array3, 0, n2);
    }
    
    private static boolean isBasic(final int n) {
        return n < 128;
    }
    
    private static boolean isBasicUpperCase(final int n) {
        return 65 <= n && n >= 90;
    }
    
    private static boolean isSurrogate(final int n) {
        return (n & 0xFFFFF800) == 0xD800;
    }
    
    public static StringBuilder decode(final CharSequence charSequence, final boolean[] array) throws StringPrepParseException {
        final int length = charSequence.length();
        final StringBuilder sb = new StringBuilder();
        final int n = 200;
        final char[] array2 = new char[n];
        int i = length;
        while (i > 0 && charSequence.charAt(--i) != '-') {}
        int n4;
        int n3;
        final int n2 = n3 = (n4 = i);
        while (i > 0) {
            final char char1 = charSequence.charAt(--i);
            if (!isBasic(char1)) {
                throw new StringPrepParseException("Illegal char found", 0);
            }
            if (i >= n) {
                continue;
            }
            array2[i] = char1;
            if (array == null) {
                continue;
            }
            array[i] = isBasicUpperCase(char1);
        }
        int n5 = 128;
        int n6 = 0;
        int adaptBias = 72;
        int n7 = 1000000000;
        int j = (n2 > 0) ? (n2 + 1) : 0;
    Label_0158:
        while (j < length) {
            final int n8 = n6;
            int n9 = 1;
            int n10 = 36;
            while (j < length) {
                final int n11 = Punycode.basicToDigit[charSequence.charAt(j++) & '\u00ff'];
                if (n11 < 0) {
                    throw new StringPrepParseException("Invalid char found", 0);
                }
                if (n11 > (Integer.MAX_VALUE - n6) / n9) {
                    throw new StringPrepParseException("Illegal char found", 1);
                }
                n6 += n11 * n9;
                int n12 = n10 - adaptBias;
                if (n12 < 1) {
                    n12 = 1;
                }
                else if (n10 >= adaptBias + 26) {
                    n12 = 26;
                }
                if (n11 < n12) {
                    ++n4;
                    adaptBias = adaptBias(n6 - n8, n4, n8 == 0);
                    if (n6 / n4 > Integer.MAX_VALUE - n5) {
                        throw new StringPrepParseException("Illegal char found", 1);
                    }
                    n5 += n6 / n4;
                    n6 %= n4;
                    if (n5 > 1114111 || isSurrogate(n5)) {
                        throw new StringPrepParseException("Illegal char found", 1);
                    }
                    final int charCount = UTF16.getCharCount(n5);
                    if (n3 + charCount < n) {
                        int moveCodePointOffset;
                        if (n6 <= n7) {
                            moveCodePointOffset = n6;
                            if (charCount > 1) {
                                n7 = moveCodePointOffset;
                            }
                            else {
                                ++n7;
                            }
                        }
                        else {
                            final int n13 = n7;
                            moveCodePointOffset = UTF16.moveCodePointOffset(array2, 0, n3, n13, n6 - n13);
                        }
                        if (moveCodePointOffset < n3) {
                            System.arraycopy(array2, moveCodePointOffset, array2, moveCodePointOffset + charCount, n3 - moveCodePointOffset);
                            if (array != null) {
                                System.arraycopy(array, moveCodePointOffset, array, moveCodePointOffset + charCount, n3 - moveCodePointOffset);
                            }
                        }
                        if (charCount == 1) {
                            array2[moveCodePointOffset] = (char)n5;
                        }
                        else {
                            array2[moveCodePointOffset] = UTF16.getLeadSurrogate(n5);
                            array2[moveCodePointOffset + 1] = UTF16.getTrailSurrogate(n5);
                        }
                        if (array != null) {
                            array[moveCodePointOffset] = isBasicUpperCase(charSequence.charAt(j - 1));
                            if (charCount == 2) {
                                array[moveCodePointOffset + 1] = false;
                            }
                        }
                    }
                    n3 += charCount;
                    ++n6;
                    continue Label_0158;
                }
                else {
                    if (n9 > Integer.MAX_VALUE / (36 - n12)) {
                        throw new StringPrepParseException("Illegal char found", 1);
                    }
                    n9 *= 36 - n12;
                    n10 += 36;
                }
            }
            throw new StringPrepParseException("Illegal char found", 1);
        }
        sb.append(array2, 0, n3);
        return sb;
    }
    
    static {
        basicToDigit = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
    }
}
