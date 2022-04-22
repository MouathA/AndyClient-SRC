package com.ibm.icu.lang;

public class CharSequences
{
    @Deprecated
    public static int matchAfter(final CharSequence charSequence, final CharSequence charSequence2, final int n, final int n2) {
        int n3 = n;
        int n4 = n2;
        for (int length = charSequence.length(), length2 = charSequence2.length(); n3 < length && n4 < length2 && charSequence.charAt(n3) == charSequence2.charAt(n4); ++n3, ++n4) {}
        int n5 = n3 - n;
        if (n5 != 0 && !onCharacterBoundary(charSequence, n3) && !onCharacterBoundary(charSequence2, n4)) {
            --n5;
        }
        return n5;
    }
    
    @Deprecated
    public int codePointLength(final CharSequence charSequence) {
        return Character.codePointCount(charSequence, 0, charSequence.length());
    }
    
    @Deprecated
    public static final boolean equals(final int n, final CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        switch (charSequence.length()) {
            case 1: {
                return n == charSequence.charAt(0);
            }
            case 2: {
                return n > 65535 && n == Character.codePointAt(charSequence, 0);
            }
            default: {
                return false;
            }
        }
    }
    
    @Deprecated
    public static final boolean equals(final CharSequence charSequence, final int n) {
        return equals(n, charSequence);
    }
    
    @Deprecated
    public static int compare(final CharSequence charSequence, final int n) {
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException();
        }
        final int length = charSequence.length();
        if (length == 0) {
            return -1;
        }
        final char char1 = charSequence.charAt(0);
        final int n2 = n - 65536;
        if (n2 < 0) {
            final int n3 = char1 - n;
            if (n3 != 0) {
                return n3;
            }
            return length - 1;
        }
        else {
            final int n4 = char1 - (char)((n2 >>> 10) + 55296);
            if (n4 != 0) {
                return n4;
            }
            if (length > 1) {
                final int n5 = charSequence.charAt(1) - (char)((n2 & 0x3FF) + 56320);
                if (n5 != 0) {
                    return n5;
                }
            }
            return length - 2;
        }
    }
    
    @Deprecated
    public static int compare(final int n, final CharSequence charSequence) {
        return -compare(charSequence, n);
    }
    
    @Deprecated
    public static int getSingleCodePoint(final CharSequence charSequence) {
        final int length = charSequence.length();
        if (length < 1 || length > 2) {
            return Integer.MAX_VALUE;
        }
        final int codePoint = Character.codePointAt(charSequence, 0);
        return (codePoint < 65536 == (length == 1)) ? codePoint : Integer.MAX_VALUE;
    }
    
    @Deprecated
    public static final boolean equals(final Object o, final Object o2) {
        return (o == null) ? (o2 == null) : (o2 != null && o.equals(o2));
    }
    
    @Deprecated
    public static int compare(final CharSequence charSequence, final CharSequence charSequence2) {
        final int length = charSequence.length();
        final int length2 = charSequence2.length();
        while (0 < ((length <= length2) ? length : length2)) {
            final int n = charSequence.charAt(0) - charSequence2.charAt(0);
            if (n != 0) {
                return n;
            }
            int n2 = 0;
            ++n2;
        }
        return length - length2;
    }
    
    @Deprecated
    public static boolean equalsChars(final CharSequence charSequence, final CharSequence charSequence2) {
        return charSequence.length() == charSequence2.length() && compare(charSequence, charSequence2) == 0;
    }
    
    @Deprecated
    public static boolean onCharacterBoundary(final CharSequence charSequence, final int n) {
        return n <= 0 || n >= charSequence.length() || !Character.isHighSurrogate(charSequence.charAt(n - 1)) || !Character.isLowSurrogate(charSequence.charAt(n));
    }
    
    @Deprecated
    public static int indexOf(final CharSequence charSequence, final int n) {
        while (0 < charSequence.length()) {
            final int codePoint = Character.codePointAt(charSequence, 0);
            if (codePoint == n) {
                return 0;
            }
            final int n2 = 0 + Character.charCount(codePoint);
        }
        return -1;
    }
    
    @Deprecated
    public static int[] codePoints(final CharSequence charSequence) {
        final int[] array = new int[charSequence.length()];
        while (0 < charSequence.length()) {
            final char char1 = charSequence.charAt(0);
            Label_0087: {
                if (char1 >= '\udc00' && char1 <= '\udfff' && false) {
                    final char c = (char)array[-1];
                    if (c >= '\ud800' && c <= '\udbff') {
                        array[-1] = Character.toCodePoint(c, char1);
                        break Label_0087;
                    }
                }
                final int[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = char1;
            }
            int n3 = 0;
            ++n3;
        }
        if (0 == array.length) {
            return array;
        }
        final int[] array3 = new int[0];
        System.arraycopy(array, 0, array3, 0, 0);
        return array3;
    }
    
    private CharSequences() {
    }
}
