package com.ibm.icu.impl;

public final class PatternProps
{
    private static final byte[] latin1;
    private static final byte[] index2000;
    private static final int[] syntax2000;
    private static final int[] syntaxOrWhiteSpace2000;
    
    public static boolean isSyntax(final int n) {
        if (n < 0) {
            return false;
        }
        if (n <= 255) {
            return PatternProps.latin1[n] == 3;
        }
        if (n < 8208) {
            return false;
        }
        if (n <= 12336) {
            return (PatternProps.syntax2000[PatternProps.index2000[n - 8192 >> 5]] >> (n & 0x1F) & 0x1) != 0x0;
        }
        return 64830 <= n && n <= 65094 && (n <= 64831 || 65093 <= n);
    }
    
    public static boolean isSyntaxOrWhiteSpace(final int n) {
        if (n < 0) {
            return false;
        }
        if (n <= 255) {
            return PatternProps.latin1[n] != 0;
        }
        if (n < 8206) {
            return false;
        }
        if (n <= 12336) {
            return (PatternProps.syntaxOrWhiteSpace2000[PatternProps.index2000[n - 8192 >> 5]] >> (n & 0x1F) & 0x1) != 0x0;
        }
        return 64830 <= n && n <= 65094 && (n <= 64831 || 65093 <= n);
    }
    
    public static boolean isWhiteSpace(final int n) {
        if (n < 0) {
            return false;
        }
        if (n <= 255) {
            return PatternProps.latin1[n] == 5;
        }
        return 8206 <= n && n <= 8233 && (n <= 8207 || 8232 <= n);
    }
    
    public static int skipWhiteSpace(final CharSequence charSequence, int n) {
        while (n < charSequence.length() && isWhiteSpace(charSequence.charAt(n))) {
            ++n;
        }
        return n;
    }
    
    public static String trimWhiteSpace(final String s) {
        if (s.length() == 0 || (!isWhiteSpace(s.charAt(0)) && !isWhiteSpace(s.charAt(s.length() - 1)))) {
            return s;
        }
        int n;
        int length;
        for (n = 0, length = s.length(); n < length && isWhiteSpace(s.charAt(n)); ++n) {}
        if (n < length) {
            while (isWhiteSpace(s.charAt(length - 1))) {
                --length;
            }
        }
        return s.substring(n, length);
    }
    
    public static boolean isIdentifier(final CharSequence charSequence) {
        final int length = charSequence.length();
        if (length == 0) {
            return false;
        }
        int n = 0;
        while (!isSyntaxOrWhiteSpace(charSequence.charAt(n++))) {
            if (n >= length) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isIdentifier(final CharSequence charSequence, int n, final int n2) {
        if (n >= n2) {
            return false;
        }
        while (!isSyntaxOrWhiteSpace(charSequence.charAt(n++))) {
            if (n >= n2) {
                return true;
            }
        }
        return false;
    }
    
    public static int skipIdentifier(final CharSequence charSequence, int n) {
        while (n < charSequence.length() && !isSyntaxOrWhiteSpace(charSequence.charAt(n))) {
            ++n;
        }
        return n;
    }
    
    static {
        latin1 = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 5, 5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 0, 3, 0, 3, 3, 0, 3, 0, 3, 3, 0, 0, 0, 0, 3, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0 };
        index2000 = new byte[] { 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 7, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 9 };
        syntax2000 = new int[] { 0, -1, -65536, 2147418367, 2146435070, -65536, 4194303, -1048576, -242, 65537 };
        syntaxOrWhiteSpace2000 = new int[] { 0, -1, -16384, 2147419135, 2146435070, -65536, 4194303, -1048576, -242, 65537 };
    }
}
