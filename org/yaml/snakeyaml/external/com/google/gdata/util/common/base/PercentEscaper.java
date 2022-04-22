package org.yaml.snakeyaml.external.com.google.gdata.util.common.base;

public class PercentEscaper extends UnicodeEscaper
{
    public static final String SAFECHARS_URLENCODER = "-_.*";
    public static final String SAFEPATHCHARS_URLENCODER = "-_.!~*'()@:$&,;=";
    public static final String SAFEQUERYSTRINGCHARS_URLENCODER = "-_.!~*'()@:$,;/?:";
    private static final char[] URI_ESCAPED_SPACE;
    private static final char[] UPPER_HEX_DIGITS;
    private final boolean plusForSpace;
    private final boolean[] safeOctets;
    
    public PercentEscaper(final String s, final boolean plusForSpace) {
        if (s.matches(".*[0-9A-Za-z].*")) {
            throw new IllegalArgumentException("Alphanumeric characters are always 'safe' and should not be explicitly specified");
        }
        if (plusForSpace && s.contains(" ")) {
            throw new IllegalArgumentException("plusForSpace cannot be specified when space is a 'safe' character");
        }
        if (s.contains("%")) {
            throw new IllegalArgumentException("The '%' character cannot be specified as 'safe'");
        }
        this.plusForSpace = plusForSpace;
        this.safeOctets = createSafeOctets(s);
    }
    
    private static boolean[] createSafeOctets(final String s) {
        final char[] charArray = s.toCharArray();
        final int length = charArray.length;
        while (true) {
            final char c = charArray[0];
            Math.max(0, 122);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    protected int nextEscapeIndex(final CharSequence charSequence, int i, final int n) {
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
            return PercentEscaper.URI_ESCAPED_SPACE;
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
        URI_ESCAPED_SPACE = new char[] { '+' };
        UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    }
}
