package com.ibm.icu.impl.locale;

public final class AsciiUtil
{
    public static boolean caseIgnoreMatch(final String s, final String s2) {
        if (s == s2) {
            return true;
        }
        final int length = s.length();
        if (length != s2.length()) {
            return false;
        }
        while (0 < length) {
            final char char1 = s.charAt(0);
            final char char2 = s2.charAt(0);
            if (char1 != char2 && toLower(char1) != toLower(char2)) {
                break;
            }
            int n = 0;
            ++n;
        }
        return length == 0;
    }
    
    public static int caseIgnoreCompare(final String s, final String s2) {
        if (s == s2) {
            return 0;
        }
        return toLowerString(s).compareTo(toLowerString(s2));
    }
    
    public static char toUpper(char c) {
        if (c >= 'a' && c <= 'z') {
            c -= ' ';
        }
        return c;
    }
    
    public static char toLower(char c) {
        if (c >= 'A' && c <= 'Z') {
            c += ' ';
        }
        return c;
    }
    
    public static String toLowerString(final String s) {
        int n = 0;
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (char1 >= 'A' && char1 <= 'Z') {
                break;
            }
            ++n;
        }
        if (0 == s.length()) {
            return s;
        }
        final StringBuilder sb = new StringBuilder(s.substring(0, 0));
        while (0 < s.length()) {
            sb.append(toLower(s.charAt(0)));
            ++n;
        }
        return sb.toString();
    }
    
    public static String toUpperString(final String s) {
        int n = 0;
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (char1 >= 'a' && char1 <= 'z') {
                break;
            }
            ++n;
        }
        if (0 == s.length()) {
            return s;
        }
        final StringBuilder sb = new StringBuilder(s.substring(0, 0));
        while (0 < s.length()) {
            sb.append(toUpper(s.charAt(0)));
            ++n;
        }
        return sb.toString();
    }
    
    public static String toTitleString(final String s) {
        if (s.length() == 0) {
            return s;
        }
        final char char1 = s.charAt(1);
        int n = 0;
        if (char1 < 'a' || char1 > 'z') {
            while (1 < s.length()) {
                if (char1 >= 'A' && char1 <= 'Z') {
                    break;
                }
                ++n;
            }
        }
        if (1 == s.length()) {
            return s;
        }
        final StringBuilder sb = new StringBuilder(s.substring(0, 1));
        if (!true) {
            sb.append(toUpper(s.charAt(1)));
            ++n;
        }
        while (1 < s.length()) {
            sb.append(toLower(s.charAt(1)));
            ++n;
        }
        return sb.toString();
    }
    
    public static boolean isAlpha(final char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }
    
    public static boolean isAlphaString(final String s) {
        while (0 < s.length() && isAlpha(s.charAt(0))) {
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static boolean isNumeric(final char c) {
        return c >= '0' && c <= '9';
    }
    
    public static boolean isNumericString(final String s) {
        while (0 < s.length() && isNumeric(s.charAt(0))) {
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static boolean isAlphaNumeric(final char c) {
        return isAlpha(c) || isNumeric(c);
    }
    
    public static boolean isAlphaNumericString(final String s) {
        while (0 < s.length() && isAlphaNumeric(s.charAt(0))) {
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static class CaseInsensitiveKey
    {
        private String _key;
        private int _hash;
        
        public CaseInsensitiveKey(final String key) {
            this._key = key;
            this._hash = AsciiUtil.toLowerString(key).hashCode();
        }
        
        @Override
        public boolean equals(final Object o) {
            return this == o || (o instanceof CaseInsensitiveKey && AsciiUtil.caseIgnoreMatch(this._key, ((CaseInsensitiveKey)o)._key));
        }
        
        @Override
        public int hashCode() {
            return this._hash;
        }
    }
}
