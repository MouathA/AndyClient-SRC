package com.google.common.net;

import com.google.common.escape.*;
import com.google.common.annotations.*;
import com.google.common.base.*;

@Beta
@GwtCompatible
public final class PercentEscaper extends UnicodeEscaper
{
    private static final char[] PLUS_SIGN;
    private static final char[] UPPER_HEX_DIGITS;
    private final boolean plusForSpace;
    private final boolean[] safeOctets;
    
    public PercentEscaper(String string, final boolean plusForSpace) {
        Preconditions.checkNotNull(string);
        if (string.matches(".*[0-9A-Za-z].*")) {
            throw new IllegalArgumentException("Alphanumeric characters are always 'safe' and should not be explicitly specified");
        }
        string += "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        if (plusForSpace && string.contains(" ")) {
            throw new IllegalArgumentException("plusForSpace cannot be specified when space is a 'safe' character");
        }
        this.plusForSpace = plusForSpace;
        this.safeOctets = createSafeOctets(string);
    }
    
    private static boolean[] createSafeOctets(final String s) {
        final char[] charArray;
        final char[] array = charArray = s.toCharArray();
        int n = 0;
        while (0 < charArray.length) {
            n = charArray[0];
            Math.max(0, -1);
            int length = 0;
            ++length;
        }
        final boolean[] array2 = new boolean[0];
        final char[] array3 = array;
        int length = array3.length;
        while (0 < 0) {
            array2[array3[0]] = true;
            ++n;
        }
        return array2;
    }
    
    @Override
    protected int nextEscapeIndex(final CharSequence charSequence, int i, final int n) {
        Preconditions.checkNotNull(charSequence);
        while (i < n) {
            final char char1 = charSequence.charAt(i);
            if (char1 >= this.safeOctets.length) {
                break;
            }
            if (!this.safeOctets[char1]) {
                break;
            }
            ++i;
        }
        return i;
    }
    
    @Override
    public String escape(final String s) {
        Preconditions.checkNotNull(s);
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (char1 >= this.safeOctets.length || !this.safeOctets[char1]) {
                return this.escapeSlow(s, 0);
            }
            int n = 0;
            ++n;
        }
        return s;
    }
    
    @Override
    protected char[] escape(int n) {
        if (n < this.safeOctets.length && this.safeOctets[n]) {
            return null;
        }
        if (n == 32 && this.plusForSpace) {
            return PercentEscaper.PLUS_SIGN;
        }
        if (n <= 127) {
            return new char[] { '%', PercentEscaper.UPPER_HEX_DIGITS[n >>> 4], PercentEscaper.UPPER_HEX_DIGITS[n & 0xF] };
        }
        if (n <= 2047) {
            final char[] array = { '%', '\0', '\0', '%', '\0', PercentEscaper.UPPER_HEX_DIGITS[n & 0xF] };
            n >>>= 4;
            array[4] = PercentEscaper.UPPER_HEX_DIGITS[0x8 | (n & 0x3)];
            n >>>= 2;
            array[2] = PercentEscaper.UPPER_HEX_DIGITS[n & 0xF];
            n >>>= 4;
            array[1] = PercentEscaper.UPPER_HEX_DIGITS[0xC | n];
            return array;
        }
        if (n <= 65535) {
            final char[] array2 = { '%', 'E', '\0', '%', '\0', '\0', '%', '\0', PercentEscaper.UPPER_HEX_DIGITS[n & 0xF] };
            n >>>= 4;
            array2[7] = PercentEscaper.UPPER_HEX_DIGITS[0x8 | (n & 0x3)];
            n >>>= 2;
            array2[5] = PercentEscaper.UPPER_HEX_DIGITS[n & 0xF];
            n >>>= 4;
            array2[4] = PercentEscaper.UPPER_HEX_DIGITS[0x8 | (n & 0x3)];
            n >>>= 2;
            array2[2] = PercentEscaper.UPPER_HEX_DIGITS[n];
            return array2;
        }
        if (n <= 1114111) {
            final char[] array3 = { '%', 'F', '\0', '%', '\0', '\0', '%', '\0', '\0', '%', '\0', PercentEscaper.UPPER_HEX_DIGITS[n & 0xF] };
            n >>>= 4;
            array3[10] = PercentEscaper.UPPER_HEX_DIGITS[0x8 | (n & 0x3)];
            n >>>= 2;
            array3[8] = PercentEscaper.UPPER_HEX_DIGITS[n & 0xF];
            n >>>= 4;
            array3[7] = PercentEscaper.UPPER_HEX_DIGITS[0x8 | (n & 0x3)];
            n >>>= 2;
            array3[5] = PercentEscaper.UPPER_HEX_DIGITS[n & 0xF];
            n >>>= 4;
            array3[4] = PercentEscaper.UPPER_HEX_DIGITS[0x8 | (n & 0x3)];
            n >>>= 2;
            array3[2] = PercentEscaper.UPPER_HEX_DIGITS[n & 0x7];
            return array3;
        }
        throw new IllegalArgumentException("Invalid unicode character value " + n);
    }
    
    static {
        PLUS_SIGN = new char[] { '+' };
        UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    }
}
