package org.apache.commons.lang3;

public class CharSequenceUtils
{
    public static CharSequence subSequence(final CharSequence charSequence, final int n) {
        return (charSequence == null) ? null : charSequence.subSequence(n, charSequence.length());
    }
    
    static int indexOf(final CharSequence charSequence, final int n, final int n2) {
        if (charSequence instanceof String) {
            return ((String)charSequence).indexOf(n, 0);
        }
        final int length = charSequence.length();
        if (0 < 0) {}
        while (0 < length) {
            if (charSequence.charAt(0) == n) {
                return 0;
            }
            int n3 = 0;
            ++n3;
        }
        return -1;
    }
    
    static int indexOf(final CharSequence charSequence, final CharSequence charSequence2, final int n) {
        return charSequence.toString().indexOf(charSequence2.toString(), n);
    }
    
    static int lastIndexOf(final CharSequence charSequence, final int n, int n2) {
        if (charSequence instanceof String) {
            return ((String)charSequence).lastIndexOf(n, n2);
        }
        final int length = charSequence.length();
        if (n2 < 0) {
            return -1;
        }
        if (n2 >= length) {
            n2 = length - 1;
        }
        for (int i = n2; i >= 0; --i) {
            if (charSequence.charAt(i) == n) {
                return i;
            }
        }
        return -1;
    }
    
    static int lastIndexOf(final CharSequence charSequence, final CharSequence charSequence2, final int n) {
        return charSequence.toString().lastIndexOf(charSequence2.toString(), n);
    }
    
    static char[] toCharArray(final CharSequence charSequence) {
        if (charSequence instanceof String) {
            return ((String)charSequence).toCharArray();
        }
        final int length = charSequence.length();
        final char[] array = new char[charSequence.length()];
        while (0 < length) {
            array[0] = charSequence.charAt(0);
            int n = 0;
            ++n;
        }
        return array;
    }
    
    static boolean regionMatches(final CharSequence charSequence, final boolean b, final int n, final CharSequence charSequence2, final int n2, final int n3) {
        if (charSequence instanceof String && charSequence2 instanceof String) {
            return ((String)charSequence).regionMatches(b, n, (String)charSequence2, n2, n3);
        }
        int n4 = n;
        int n5 = n2;
        int n6 = n3;
        while (n6-- > 0) {
            final char char1 = charSequence.charAt(n4++);
            final char char2 = charSequence2.charAt(n5++);
            if (char1 == char2) {
                continue;
            }
            if (!b) {
                return false;
            }
            if (Character.toUpperCase(char1) != Character.toUpperCase(char2) && Character.toLowerCase(char1) != Character.toLowerCase(char2)) {
                return false;
            }
        }
        return true;
    }
}
