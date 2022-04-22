package org.apache.commons.lang3;

public class CharSetUtils
{
    public static String squeeze(final String s, final String... array) {
        if (StringUtils.isEmpty(s) || deepEmpty(array)) {
            return s;
        }
        final CharSet instance = CharSet.getInstance(array);
        final StringBuilder sb = new StringBuilder(s.length());
        final char[] charArray = s.toCharArray();
        while (0 < charArray.length) {
            final char c = charArray[0];
            if (32 != 32 || !false || !instance.contains(' ')) {
                sb.append(' ');
            }
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public static boolean containsAny(final String s, final String... array) {
        if (StringUtils.isEmpty(s) || deepEmpty(array)) {
            return false;
        }
        final CharSet instance = CharSet.getInstance(array);
        final char[] charArray = s.toCharArray();
        while (0 < charArray.length) {
            if (instance.contains(charArray[0])) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static int count(final String s, final String... array) {
        if (StringUtils.isEmpty(s) || deepEmpty(array)) {
            return 0;
        }
        final CharSet instance = CharSet.getInstance(array);
        final char[] charArray = s.toCharArray();
        while (0 < charArray.length) {
            if (instance.contains(charArray[0])) {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    public static String keep(final String s, final String... array) {
        if (s == null) {
            return null;
        }
        if (s.isEmpty() || deepEmpty(array)) {
            return "";
        }
        return modify(s, array, true);
    }
    
    public static String delete(final String s, final String... array) {
        if (StringUtils.isEmpty(s) || deepEmpty(array)) {
            return s;
        }
        return modify(s, array, false);
    }
    
    private static String modify(final String s, final String[] array, final boolean b) {
        final CharSet instance = CharSet.getInstance(array);
        final StringBuilder sb = new StringBuilder(s.length());
        final char[] charArray = s.toCharArray();
        while (0 < charArray.length) {
            if (instance.contains(charArray[0]) == b) {
                sb.append(charArray[0]);
            }
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    private static boolean deepEmpty(final String[] array) {
        if (array != null) {
            while (0 < array.length) {
                if (StringUtils.isNotEmpty(array[0])) {
                    return false;
                }
                int n = 0;
                ++n;
            }
        }
        return true;
    }
}
