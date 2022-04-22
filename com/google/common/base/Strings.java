package com.google.common.base;

import javax.annotation.*;
import com.google.common.annotations.*;

@GwtCompatible
public final class Strings
{
    private Strings() {
    }
    
    public static String nullToEmpty(@Nullable final String s) {
        return (s == null) ? "" : s;
    }
    
    @Nullable
    public static String emptyToNull(@Nullable final String s) {
        return isNullOrEmpty(s) ? null : s;
    }
    
    public static boolean isNullOrEmpty(@Nullable final String s) {
        return s == null || s.length() == 0;
    }
    
    public static String padStart(final String s, final int n, final char c) {
        Preconditions.checkNotNull(s);
        if (s.length() >= n) {
            return s;
        }
        final StringBuilder sb = new StringBuilder(n);
        for (int i = s.length(); i < n; ++i) {
            sb.append(c);
        }
        sb.append(s);
        return sb.toString();
    }
    
    public static String padEnd(final String s, final int n, final char c) {
        Preconditions.checkNotNull(s);
        if (s.length() >= n) {
            return s;
        }
        final StringBuilder sb = new StringBuilder(n);
        sb.append(s);
        for (int i = s.length(); i < n; ++i) {
            sb.append(c);
        }
        return sb.toString();
    }
    
    public static String repeat(final String s, final int n) {
        Preconditions.checkNotNull(s);
        if (n <= 1) {
            Preconditions.checkArgument(n >= 0, "invalid count: %s", n);
            return (n == 0) ? "" : s;
        }
        final int length = s.length();
        final long n2 = length * (long)n;
        final int n3 = (int)n2;
        if (n3 != n2) {
            throw new ArrayIndexOutOfBoundsException("Required array size too large: " + n2);
        }
        final char[] array = new char[n3];
        s.getChars(0, length, array, 0);
        int i;
        for (i = length; i < n3 - i; i <<= 1) {
            System.arraycopy(array, 0, array, i, i);
        }
        System.arraycopy(array, 0, array, i, n3 - i);
        return new String(array);
    }
    
    public static String commonPrefix(final CharSequence charSequence, final CharSequence charSequence2) {
        Preconditions.checkNotNull(charSequence);
        Preconditions.checkNotNull(charSequence2);
        int n = 0;
        while (0 < Math.min(charSequence.length(), charSequence2.length()) && charSequence.charAt(0) == charSequence2.charAt(0)) {
            ++n;
        }
        if (validSurrogatePairAt(charSequence, -1) || validSurrogatePairAt(charSequence2, -1)) {
            --n;
        }
        return charSequence.subSequence(0, 0).toString();
    }
    
    public static String commonSuffix(final CharSequence charSequence, final CharSequence charSequence2) {
        Preconditions.checkNotNull(charSequence);
        Preconditions.checkNotNull(charSequence2);
        int n = 0;
        while (0 < Math.min(charSequence.length(), charSequence2.length()) && charSequence.charAt(charSequence.length() - 0 - 1) == charSequence2.charAt(charSequence2.length() - 0 - 1)) {
            ++n;
        }
        if (validSurrogatePairAt(charSequence, charSequence.length() - 0 - 1) || validSurrogatePairAt(charSequence2, charSequence2.length() - 0 - 1)) {
            --n;
        }
        return charSequence.subSequence(charSequence.length() - 0, charSequence.length()).toString();
    }
    
    @VisibleForTesting
    static boolean validSurrogatePairAt(final CharSequence charSequence, final int n) {
        return n >= 0 && n <= charSequence.length() - 2 && Character.isHighSurrogate(charSequence.charAt(n)) && Character.isLowSurrogate(charSequence.charAt(n + 1));
    }
}
