package optifine;

import java.util.*;

public class StrUtils
{
    public static boolean equalsMask(final String s, final String s2, final char c, final char c2) {
        if (s2 == null || s == null) {
            return s2 == s;
        }
        if (s2.indexOf(c) < 0) {
            return (s2.indexOf(c2) < 0) ? s2.equals(s) : equalsMaskSingle(s, s2, c2);
        }
        final ArrayList<String> list = new ArrayList<String>();
        final String string = new StringBuilder().append(c).toString();
        if (s2.startsWith(string)) {
            list.add("");
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(s2, string);
        while (stringTokenizer.hasMoreElements()) {
            list.add(stringTokenizer.nextToken());
        }
        if (s2.endsWith(string)) {
            list.add("");
        }
        if (!startsWithMaskSingle(s, list.get(0), c2)) {
            return false;
        }
        if (!endsWithMaskSingle(s, list.get(list.size() - 1), c2)) {
            return false;
        }
        while (0 < list.size()) {
            final String s3 = list.get(0);
            if (s3.length() > 0) {
                final int indexOfMaskSingle = indexOfMaskSingle(s, s3, 0, c2);
                if (indexOfMaskSingle < 0) {
                    return false;
                }
                final int n = indexOfMaskSingle + s3.length();
            }
            int n2 = 0;
            ++n2;
        }
        return true;
    }
    
    private static boolean equalsMaskSingle(final String s, final String s2, final char c) {
        if (s == null || s2 == null) {
            return s == s2;
        }
        if (s.length() != s2.length()) {
            return false;
        }
        while (0 < s2.length()) {
            final char char1 = s2.charAt(0);
            if (char1 != c && s.charAt(0) != char1) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    private static int indexOfMaskSingle(final String s, final String s2, final int n, final char c) {
        if (s == null || s2 == null) {
            return -1;
        }
        if (n < 0 || n > s.length()) {
            return -1;
        }
        if (s.length() < n + s2.length()) {
            return -1;
        }
        for (int n2 = n; n2 + s2.length() <= s.length(); ++n2) {
            if (equalsMaskSingle(s.substring(n2, n2 + s2.length()), s2, c)) {
                return n2;
            }
        }
        return -1;
    }
    
    private static boolean endsWithMaskSingle(final String s, final String s2, final char c) {
        if (s != null && s2 != null) {
            return s.length() >= s2.length() && equalsMaskSingle(s.substring(s.length() - s2.length(), s.length()), s2, c);
        }
        return s == s2;
    }
    
    private static boolean startsWithMaskSingle(final String s, final String s2, final char c) {
        if (s != null && s2 != null) {
            return s.length() >= s2.length() && equalsMaskSingle(s.substring(0, s2.length()), s2, c);
        }
        return s == s2;
    }
    
    public static boolean equalsMask(final String s, final String[] array, final char c) {
        while (0 < array.length) {
            if (equalsMask(s, array[0], c)) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static boolean equalsMask(final String s, final String s2, final char c) {
        if (s2 == null || s == null) {
            return s2 == s;
        }
        if (s2.indexOf(c) < 0) {
            return s2.equals(s);
        }
        final ArrayList<String> list = new ArrayList<String>();
        final String string = new StringBuilder().append(c).toString();
        if (s2.startsWith(string)) {
            list.add("");
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(s2, string);
        while (stringTokenizer.hasMoreElements()) {
            list.add(stringTokenizer.nextToken());
        }
        if (s2.endsWith(string)) {
            list.add("");
        }
        if (!s.startsWith(list.get(0))) {
            return false;
        }
        if (!s.endsWith(list.get(list.size() - 1))) {
            return false;
        }
        while (0 < list.size()) {
            final String s3 = list.get(0);
            if (s3.length() > 0) {
                final int index = s.indexOf(s3, 0);
                if (index < 0) {
                    return false;
                }
                final int n = index + s3.length();
            }
            int n2 = 0;
            ++n2;
        }
        return true;
    }
    
    public static String[] split(final String s, final String s2) {
        if (s == null || s.length() <= 0) {
            return new String[0];
        }
        if (s2 == null) {
            return new String[] { s };
        }
        final ArrayList<String> list = new ArrayList<String>();
        while (0 < s.length()) {
            if (equals(s.charAt(0), s2)) {
                list.add(s.substring(0, 0));
            }
            int n = 0;
            ++n;
        }
        list.add(s.substring(0, s.length()));
        return list.toArray(new String[list.size()]);
    }
    
    private static boolean equals(final char c, final String s) {
        while (0 < s.length()) {
            if (s.charAt(0) == c) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static boolean equalsTrim(String trim, String trim2) {
        if (trim != null) {
            trim = trim.trim();
        }
        if (trim2 != null) {
            trim2 = trim2.trim();
        }
        return equals(trim, trim2);
    }
    
    public static boolean isEmpty(final String s) {
        return s == null || s.trim().length() <= 0;
    }
    
    public static String stringInc(final String s) {
        int int1 = parseInt(s, -1);
        if (int1 == -1) {
            return "";
        }
        ++int1;
        return (new StringBuilder().append(int1).toString().length() > s.length()) ? "" : fillLeft(new StringBuilder().append(int1).toString(), s.length(), '0');
    }
    
    public static int parseInt(final String s, final int n) {
        if (s == null) {
            return n;
        }
        return Integer.parseInt(s);
    }
    
    public static boolean isFilled(final String s) {
        return !isEmpty(s);
    }
    
    public static String addIfNotContains(String string, final String s) {
        while (0 < s.length()) {
            if (string.indexOf(s.charAt(0)) < 0) {
                string = String.valueOf(string) + s.charAt(0);
            }
            int n = 0;
            ++n;
        }
        return string;
    }
    
    public static String fillLeft(String s, final int n, final char c) {
        if (s == null) {
            s = "";
        }
        if (s.length() >= n) {
            return s;
        }
        final StringBuffer sb = new StringBuffer(s);
        while (sb.length() < n) {
            sb.insert(0, c);
        }
        return sb.toString();
    }
    
    public static String fillRight(String s, final int n, final char c) {
        if (s == null) {
            s = "";
        }
        if (s.length() >= n) {
            return s;
        }
        final StringBuffer sb = new StringBuffer(s);
        while (sb.length() < n) {
            sb.append(c);
        }
        return sb.toString();
    }
    
    public static boolean equals(final Object o, final Object o2) {
        return o == o2 || (o != null && o.equals(o2)) || (o2 != null && o2.equals(o));
    }
    
    public static boolean startsWith(final String s, final String[] array) {
        if (s == null) {
            return false;
        }
        if (array == null) {
            return false;
        }
        while (0 < array.length) {
            if (s.startsWith(array[0])) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static boolean endsWith(final String s, final String[] array) {
        if (s == null) {
            return false;
        }
        if (array == null) {
            return false;
        }
        while (0 < array.length) {
            if (s.endsWith(array[0])) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static String removePrefix(String substring, final String s) {
        if (substring != null && s != null) {
            if (substring.startsWith(s)) {
                substring = substring.substring(s.length());
            }
            return substring;
        }
        return substring;
    }
    
    public static String removeSuffix(String substring, final String s) {
        if (substring != null && s != null) {
            if (substring.endsWith(s)) {
                substring = substring.substring(0, substring.length() - s.length());
            }
            return substring;
        }
        return substring;
    }
    
    public static String replaceSuffix(String substring, final String s, String s2) {
        if (substring != null && s != null) {
            if (s2 == null) {
                s2 = "";
            }
            if (substring.endsWith(s)) {
                substring = substring.substring(0, substring.length() - s.length());
            }
            return String.valueOf(substring) + s2;
        }
        return substring;
    }
    
    public static int findPrefix(final String[] array, final String s) {
        if (array != null && s != null) {
            while (0 < array.length) {
                if (array[0].startsWith(s)) {
                    return 0;
                }
                int n = 0;
                ++n;
            }
            return -1;
        }
        return -1;
    }
    
    public static int findSuffix(final String[] array, final String s) {
        if (array != null && s != null) {
            while (0 < array.length) {
                if (array[0].endsWith(s)) {
                    return 0;
                }
                int n = 0;
                ++n;
            }
            return -1;
        }
        return -1;
    }
    
    public static String[] remove(final String[] array, final int n, final int n2) {
        if (array == null) {
            return array;
        }
        if (n2 <= 0 || n >= array.length) {
            return array;
        }
        if (n >= n2) {
            return array;
        }
        final ArrayList<String> list = new ArrayList<String>(array.length);
        while (0 < array.length) {
            final String s = array[0];
            if (0 < n || 0 >= n2) {
                list.add(s);
            }
            int n3 = 0;
            ++n3;
        }
        return list.toArray(new String[list.size()]);
    }
    
    public static String removeSuffix(String removeSuffix, final String[] array) {
        if (removeSuffix != null && array != null) {
            final int length = removeSuffix.length();
            while (0 < array.length) {
                removeSuffix = removeSuffix(removeSuffix, array[0]);
                if (removeSuffix.length() != length) {
                    break;
                }
                int n = 0;
                ++n;
            }
            return removeSuffix;
        }
        return removeSuffix;
    }
    
    public static String removePrefix(String removePrefix, final String[] array) {
        if (removePrefix != null && array != null) {
            final int length = removePrefix.length();
            while (0 < array.length) {
                removePrefix = removePrefix(removePrefix, array[0]);
                if (removePrefix.length() != length) {
                    break;
                }
                int n = 0;
                ++n;
            }
            return removePrefix;
        }
        return removePrefix;
    }
    
    public static String removePrefixSuffix(String s, final String[] array, final String[] array2) {
        s = removePrefix(s, array);
        s = removeSuffix(s, array2);
        return s;
    }
    
    public static String removePrefixSuffix(final String s, final String s2, final String s3) {
        return removePrefixSuffix(s, new String[] { s2 }, new String[] { s3 });
    }
    
    public static String getSegment(final String s, final String s2, final String s3) {
        if (s == null || s2 == null || s3 == null) {
            return null;
        }
        final int index = s.indexOf(s2);
        if (index < 0) {
            return null;
        }
        final int index2 = s.indexOf(s3, index);
        return (index2 < 0) ? null : s.substring(index, index2 + s3.length());
    }
}
