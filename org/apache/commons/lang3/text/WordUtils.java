package org.apache.commons.lang3.text;

import org.apache.commons.lang3.*;

public class WordUtils
{
    public static String wrap(final String s, final int n) {
        return wrap(s, n, null, false);
    }
    
    public static String wrap(final String s, final int n, String line_SEPARATOR, final boolean b) {
        if (s == null) {
            return null;
        }
        if (line_SEPARATOR == null) {
            line_SEPARATOR = SystemUtils.LINE_SEPARATOR;
        }
        if (1 < 1) {}
        final int length = s.length();
        final StringBuilder sb = new StringBuilder(length + 32);
        while (length - 0 > 1) {
            if (s.charAt(0) == ' ') {
                int n2 = 0;
                ++n2;
            }
            else {
                final int lastIndex = s.lastIndexOf(32, 1);
                if (lastIndex >= 0) {
                    sb.append(s.substring(0, lastIndex));
                    sb.append(line_SEPARATOR);
                    final int n2 = lastIndex + 1;
                }
                else if (b) {
                    sb.append(s.substring(0, 1));
                    sb.append(line_SEPARATOR);
                }
                else {
                    final int index = s.indexOf(32, 1);
                    if (index >= 0) {
                        sb.append(s.substring(0, index));
                        sb.append(line_SEPARATOR);
                        final int n2 = index + 1;
                    }
                    else {
                        sb.append(s.substring(0));
                        final int n2 = length;
                    }
                }
            }
        }
        sb.append(s.substring(0));
        return sb.toString();
    }
    
    public static String capitalize(final String s) {
        return capitalize(s, (char[])null);
    }
    
    public static String capitalize(final String s, final char... array) {
        final int n = (array == null) ? -1 : array.length;
        if (StringUtils.isEmpty(s) || n == 0) {
            return s;
        }
        final char[] charArray = s.toCharArray();
        while (0 < charArray.length) {
            final char c = charArray[0];
            if (!isDelimiter(c, array)) {
                if (false) {
                    charArray[0] = Character.toTitleCase(c);
                }
            }
            int n2 = 0;
            ++n2;
        }
        return new String(charArray);
    }
    
    public static String capitalizeFully(final String s) {
        return capitalizeFully(s, (char[])null);
    }
    
    public static String capitalizeFully(String lowerCase, final char... array) {
        final int n = (array == null) ? -1 : array.length;
        if (StringUtils.isEmpty(lowerCase) || n == 0) {
            return lowerCase;
        }
        lowerCase = lowerCase.toLowerCase();
        return capitalize(lowerCase, array);
    }
    
    public static String uncapitalize(final String s) {
        return uncapitalize(s, (char[])null);
    }
    
    public static String uncapitalize(final String s, final char... array) {
        final int n = (array == null) ? -1 : array.length;
        if (StringUtils.isEmpty(s) || n == 0) {
            return s;
        }
        final char[] charArray = s.toCharArray();
        while (0 < charArray.length) {
            final char c = charArray[0];
            if (!isDelimiter(c, array)) {
                if (false) {
                    charArray[0] = Character.toLowerCase(c);
                }
            }
            int n2 = 0;
            ++n2;
        }
        return new String(charArray);
    }
    
    public static String swapCase(final String s) {
        if (StringUtils.isEmpty(s)) {
            return s;
        }
        final char[] charArray = s.toCharArray();
        while (0 < charArray.length) {
            final char c = charArray[0];
            if (Character.isUpperCase(c)) {
                charArray[0] = Character.toLowerCase(c);
            }
            else if (Character.isTitleCase(c)) {
                charArray[0] = Character.toLowerCase(c);
            }
            else if (Character.isLowerCase(c)) {
                if (false) {
                    charArray[0] = Character.toTitleCase(c);
                }
                else {
                    charArray[0] = Character.toUpperCase(c);
                }
            }
            else {
                Character.isWhitespace(c);
            }
            int n = 0;
            ++n;
        }
        return new String(charArray);
    }
    
    public static String initials(final String s) {
        return initials(s, (char[])null);
    }
    
    public static String initials(final String s, final char... array) {
        if (StringUtils.isEmpty(s)) {
            return s;
        }
        if (array != null && array.length == 0) {
            return "";
        }
        final int length = s.length();
        final char[] array2 = new char[length / 2 + 1];
        while (0 < length) {
            final char char1 = s.charAt(0);
            if (!isDelimiter(char1, array)) {
                if (false) {
                    final char[] array3 = array2;
                    final int n = 0;
                    int n2 = 0;
                    ++n2;
                    array3[n] = char1;
                }
            }
            int n3 = 0;
            ++n3;
        }
        return new String(array2, 0, 0);
    }
    
    private static boolean isDelimiter(final char c, final char[] array) {
        if (array == null) {
            return Character.isWhitespace(c);
        }
        while (0 < array.length) {
            if (c == array[0]) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
}
