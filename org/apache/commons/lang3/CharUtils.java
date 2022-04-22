package org.apache.commons.lang3;

public class CharUtils
{
    public static final char LF = '\n';
    public static final char CR = '\r';
    
    @Deprecated
    public static Character toCharacterObject(final char c) {
        return c;
    }
    
    public static Character toCharacterObject(final String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        return s.charAt(0);
    }
    
    public static char toChar(final Character c) {
        if (c == null) {
            throw new IllegalArgumentException("The Character must not be null");
        }
        return c;
    }
    
    public static char toChar(final Character c, final char c2) {
        if (c == null) {
            return c2;
        }
        return c;
    }
    
    public static char toChar(final String s) {
        if (StringUtils.isEmpty(s)) {
            throw new IllegalArgumentException("The String must not be empty");
        }
        return s.charAt(0);
    }
    
    public static char toChar(final String s, final char c) {
        if (StringUtils.isEmpty(s)) {
            return c;
        }
        return s.charAt(0);
    }
    
    public static int toIntValue(final char c) {
        if (!isAsciiNumeric(c)) {
            throw new IllegalArgumentException("The character " + c + " is not in the range '0' - '9'");
        }
        return c - '0';
    }
    
    public static int toIntValue(final char c, final int n) {
        if (!isAsciiNumeric(c)) {
            return n;
        }
        return c - '0';
    }
    
    public static int toIntValue(final Character c) {
        if (c == null) {
            throw new IllegalArgumentException("The character must not be null");
        }
        return toIntValue((char)c);
    }
    
    public static int toIntValue(final Character c, final int n) {
        if (c == null) {
            return n;
        }
        return toIntValue((char)c, n);
    }
    
    public static String toString(final char c) {
        if (c < '\u0080') {
            return CharUtils.CHAR_STRING_ARRAY[c];
        }
        return new String(new char[] { c });
    }
    
    public static String toString(final Character c) {
        if (c == null) {
            return null;
        }
        return toString((char)c);
    }
    
    public static String unicodeEscaped(final char c) {
        if (c < '\u0010') {
            return "\\u000" + Integer.toHexString(c);
        }
        if (c < '\u0100') {
            return "\\u00" + Integer.toHexString(c);
        }
        if (c < '\u1000') {
            return "\\u0" + Integer.toHexString(c);
        }
        return "\\u" + Integer.toHexString(c);
    }
    
    public static String unicodeEscaped(final Character c) {
        if (c == null) {
            return null;
        }
        return unicodeEscaped((char)c);
    }
    
    public static boolean isAscii(final char c) {
        return c < '\u0080';
    }
    
    public static boolean isAsciiPrintable(final char c) {
        return c >= ' ' && c < '\u007f';
    }
    
    public static boolean isAsciiControl(final char c) {
        return c < ' ' || c == '\u007f';
    }
    
    public static boolean isAsciiAlpha(final char c) {
        return isAsciiAlphaUpper(c) || isAsciiAlphaLower(c);
    }
    
    public static boolean isAsciiAlphaUpper(final char c) {
        return c >= 'A' && c <= 'Z';
    }
    
    public static boolean isAsciiAlphaLower(final char c) {
        return c >= 'a' && c <= 'z';
    }
    
    public static boolean isAsciiNumeric(final char c) {
        return c >= '0' && c <= '9';
    }
    
    public static boolean isAsciiAlphanumeric(final char c) {
        return isAsciiAlpha(c) || isAsciiNumeric(c);
    }
    
    static {
        CharUtils.CHAR_STRING_ARRAY = new String[128];
        while (0 < CharUtils.CHAR_STRING_ARRAY.length) {
            CharUtils.CHAR_STRING_ARRAY[0] = String.valueOf('\0');
            final char c = 1;
        }
    }
}
