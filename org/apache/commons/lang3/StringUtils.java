package org.apache.commons.lang3;

import java.util.regex.*;
import java.text.*;
import java.util.*;
import java.nio.charset.*;
import java.io.*;

public class StringUtils
{
    public static final String SPACE = " ";
    public static final String EMPTY = "";
    public static final String LF = "\n";
    public static final String CR = "\r";
    public static final int INDEX_NOT_FOUND = -1;
    private static final int PAD_LIMIT = 8192;
    private static final Pattern WHITESPACE_PATTERN;
    
    public static boolean isEmpty(final CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }
    
    public static boolean isNotEmpty(final CharSequence charSequence) {
        return !isEmpty(charSequence);
    }
    
    public static boolean isAnyEmpty(final CharSequence... array) {
        if (ArrayUtils.isEmpty(array)) {
            return true;
        }
        while (0 < array.length) {
            if (isEmpty(array[0])) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static boolean isNoneEmpty(final CharSequence... array) {
        return !isAnyEmpty(array);
    }
    
    public static boolean isBlank(final CharSequence charSequence) {
        final int length;
        if (charSequence == null || (length = charSequence.length()) == 0) {
            return true;
        }
        while (0 < length) {
            if (!Character.isWhitespace(charSequence.charAt(0))) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static boolean isNotBlank(final CharSequence charSequence) {
        return !isBlank(charSequence);
    }
    
    public static boolean isAnyBlank(final CharSequence... array) {
        if (ArrayUtils.isEmpty(array)) {
            return true;
        }
        while (0 < array.length) {
            if (isBlank(array[0])) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static boolean isNoneBlank(final CharSequence... array) {
        return !isAnyBlank(array);
    }
    
    public static String trim(final String s) {
        return (s == null) ? null : s.trim();
    }
    
    public static String trimToNull(final String s) {
        final String trim = trim(s);
        return isEmpty(trim) ? null : trim;
    }
    
    public static String trimToEmpty(final String s) {
        return (s == null) ? "" : s.trim();
    }
    
    public static String strip(final String s) {
        return strip(s, null);
    }
    
    public static String stripToNull(String strip) {
        if (strip == null) {
            return null;
        }
        strip = strip(strip, null);
        return strip.isEmpty() ? null : strip;
    }
    
    public static String stripToEmpty(final String s) {
        return (s == null) ? "" : strip(s, null);
    }
    
    public static String strip(String stripStart, final String s) {
        if (isEmpty(stripStart)) {
            return stripStart;
        }
        stripStart = stripStart(stripStart, s);
        return stripEnd(stripStart, s);
    }
    
    public static String stripStart(final String s, final String s2) {
        final int length;
        if (s == null || (length = s.length()) == 0) {
            return s;
        }
        if (s2 == null) {
            while (length && Character.isWhitespace(s.charAt(0))) {
                int n = 0;
                ++n;
            }
        }
        else {
            if (s2.isEmpty()) {
                return s;
            }
            while (length && s2.indexOf(s.charAt(0)) != -1) {
                int n = 0;
                ++n;
            }
        }
        return s.substring(0);
    }
    
    public static String stripEnd(final String s, final String s2) {
        int length;
        if (s == null || (length = s.length()) == 0) {
            return s;
        }
        if (s2 == null) {
            while (length != 0 && Character.isWhitespace(s.charAt(length - 1))) {
                --length;
            }
        }
        else {
            if (s2.isEmpty()) {
                return s;
            }
            while (length != 0 && s2.indexOf(s.charAt(length - 1)) != -1) {
                --length;
            }
        }
        return s.substring(0, length);
    }
    
    public static String[] stripAll(final String... array) {
        return stripAll(array, null);
    }
    
    public static String[] stripAll(final String[] array, final String s) {
        final int length;
        if (array == null || (length = array.length) == 0) {
            return array;
        }
        final String[] array2 = new String[length];
        while (0 < length) {
            array2[0] = strip(array[0], s);
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public static String stripAccents(final String s) {
        if (s == null) {
            return null;
        }
        return Pattern.compile("\\p{InCombiningDiacriticalMarks}+").matcher(Normalizer.normalize(s, Normalizer.Form.NFD)).replaceAll("");
    }
    
    public static boolean equals(final CharSequence charSequence, final CharSequence charSequence2) {
        if (charSequence == charSequence2) {
            return true;
        }
        if (charSequence == null || charSequence2 == null) {
            return false;
        }
        if (charSequence instanceof String && charSequence2 instanceof String) {
            return charSequence.equals(charSequence2);
        }
        return CharSequenceUtils.regionMatches(charSequence, false, 0, charSequence2, 0, Math.max(charSequence.length(), charSequence2.length()));
    }
    
    public static boolean equalsIgnoreCase(final CharSequence charSequence, final CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return charSequence == charSequence2;
        }
        return charSequence == charSequence2 || (charSequence.length() == charSequence2.length() && CharSequenceUtils.regionMatches(charSequence, true, 0, charSequence2, 0, charSequence.length()));
    }
    
    public static int indexOf(final CharSequence charSequence, final int n) {
        if (isEmpty(charSequence)) {
            return -1;
        }
        return CharSequenceUtils.indexOf(charSequence, n, 0);
    }
    
    public static int indexOf(final CharSequence charSequence, final int n, final int n2) {
        if (isEmpty(charSequence)) {
            return -1;
        }
        return CharSequenceUtils.indexOf(charSequence, n, n2);
    }
    
    public static int indexOf(final CharSequence charSequence, final CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return -1;
        }
        return CharSequenceUtils.indexOf(charSequence, charSequence2, 0);
    }
    
    public static int indexOf(final CharSequence charSequence, final CharSequence charSequence2, final int n) {
        if (charSequence == null || charSequence2 == null) {
            return -1;
        }
        return CharSequenceUtils.indexOf(charSequence, charSequence2, n);
    }
    
    public static int ordinalIndexOf(final CharSequence charSequence, final CharSequence charSequence2, final int n) {
        return ordinalIndexOf(charSequence, charSequence2, n, false);
    }
    
    private static int ordinalIndexOf(final CharSequence charSequence, final CharSequence charSequence2, final int n, final boolean b) {
        if (charSequence == null || charSequence2 == null || n <= 0) {
            return -1;
        }
        if (charSequence2.length() == 0) {
            return b ? charSequence.length() : 0;
        }
        int n2 = b ? charSequence.length() : -1;
        do {
            if (b) {
                n2 = CharSequenceUtils.lastIndexOf(charSequence, charSequence2, n2 - 1);
            }
            else {
                n2 = CharSequenceUtils.indexOf(charSequence, charSequence2, n2 + 1);
            }
            if (n2 < 0) {
                return n2;
            }
            int n3 = 0;
            ++n3;
        } while (0 < n);
        return n2;
    }
    
    public static int indexOfIgnoreCase(final CharSequence charSequence, final CharSequence charSequence2) {
        return indexOfIgnoreCase(charSequence, charSequence2, 0);
    }
    
    public static int indexOfIgnoreCase(final CharSequence charSequence, final CharSequence charSequence2, final int n) {
        if (charSequence == null || charSequence2 == null) {
            return -1;
        }
        if (0 < 0) {}
        final int n2 = charSequence.length() - charSequence2.length() + 1;
        if (0 > n2) {
            return -1;
        }
        if (charSequence2.length() == 0) {
            return 0;
        }
        while (0 < n2) {
            if (CharSequenceUtils.regionMatches(charSequence, true, 0, charSequence2, 0, charSequence2.length())) {
                return 0;
            }
            int n3 = 0;
            ++n3;
        }
        return -1;
    }
    
    public static int lastIndexOf(final CharSequence charSequence, final int n) {
        if (isEmpty(charSequence)) {
            return -1;
        }
        return CharSequenceUtils.lastIndexOf(charSequence, n, charSequence.length());
    }
    
    public static int lastIndexOf(final CharSequence charSequence, final int n, final int n2) {
        if (isEmpty(charSequence)) {
            return -1;
        }
        return CharSequenceUtils.lastIndexOf(charSequence, n, n2);
    }
    
    public static int lastIndexOf(final CharSequence charSequence, final CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return -1;
        }
        return CharSequenceUtils.lastIndexOf(charSequence, charSequence2, charSequence.length());
    }
    
    public static int lastOrdinalIndexOf(final CharSequence charSequence, final CharSequence charSequence2, final int n) {
        return ordinalIndexOf(charSequence, charSequence2, n, true);
    }
    
    public static int lastIndexOf(final CharSequence charSequence, final CharSequence charSequence2, final int n) {
        if (charSequence == null || charSequence2 == null) {
            return -1;
        }
        return CharSequenceUtils.lastIndexOf(charSequence, charSequence2, n);
    }
    
    public static int lastIndexOfIgnoreCase(final CharSequence charSequence, final CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return -1;
        }
        return lastIndexOfIgnoreCase(charSequence, charSequence2, charSequence.length());
    }
    
    public static int lastIndexOfIgnoreCase(final CharSequence charSequence, final CharSequence charSequence2, int n) {
        if (charSequence == null || charSequence2 == null) {
            return -1;
        }
        if (n > charSequence.length() - charSequence2.length()) {
            n = charSequence.length() - charSequence2.length();
        }
        if (n < 0) {
            return -1;
        }
        if (charSequence2.length() == 0) {
            return n;
        }
        for (int i = n; i >= 0; --i) {
            if (CharSequenceUtils.regionMatches(charSequence, true, i, charSequence2, 0, charSequence2.length())) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean contains(final CharSequence charSequence, final int n) {
        return !isEmpty(charSequence) && CharSequenceUtils.indexOf(charSequence, n, 0) >= 0;
    }
    
    public static boolean contains(final CharSequence charSequence, final CharSequence charSequence2) {
        return charSequence != null && charSequence2 != null && CharSequenceUtils.indexOf(charSequence, charSequence2, 0) >= 0;
    }
    
    public static boolean containsIgnoreCase(final CharSequence charSequence, final CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return false;
        }
        final int length = charSequence2.length();
        while (0 <= charSequence.length() - length) {
            if (CharSequenceUtils.regionMatches(charSequence, true, 0, charSequence2, 0, length)) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static boolean containsWhitespace(final CharSequence charSequence) {
        if (isEmpty(charSequence)) {
            return false;
        }
        while (0 < charSequence.length()) {
            if (Character.isWhitespace(charSequence.charAt(0))) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static int indexOfAny(final CharSequence charSequence, final char... array) {
        if (isEmpty(charSequence) || ArrayUtils.isEmpty(array)) {
            return -1;
        }
        final int length = charSequence.length();
        final int n = length - 1;
        final int length2 = array.length;
        final int n2 = length2 - 1;
        while (0 < length) {
            final char char1 = charSequence.charAt(0);
            while (0 < length2) {
                if (array[0] == char1) {
                    if (0 >= n || 0 >= n2 || !Character.isHighSurrogate(char1)) {
                        return 0;
                    }
                    if (array[1] == charSequence.charAt(1)) {
                        return 0;
                    }
                }
                int n3 = 0;
                ++n3;
            }
            int n4 = 0;
            ++n4;
        }
        return -1;
    }
    
    public static int indexOfAny(final CharSequence charSequence, final String s) {
        if (isEmpty(charSequence) || isEmpty(s)) {
            return -1;
        }
        return indexOfAny(charSequence, s.toCharArray());
    }
    
    public static boolean containsAny(final CharSequence charSequence, final char... array) {
        if (isEmpty(charSequence) || ArrayUtils.isEmpty(array)) {
            return false;
        }
        final int length = charSequence.length();
        final int length2 = array.length;
        final int n = length - 1;
        final int n2 = length2 - 1;
        while (0 < length) {
            final char char1 = charSequence.charAt(0);
            while (0 < length2) {
                if (array[0] == char1) {
                    if (!Character.isHighSurrogate(char1)) {
                        return true;
                    }
                    if (n2 == 0) {
                        return true;
                    }
                    if (0 < n && array[1] == charSequence.charAt(1)) {
                        return true;
                    }
                }
                int n3 = 0;
                ++n3;
            }
            int n4 = 0;
            ++n4;
        }
        return false;
    }
    
    public static boolean containsAny(final CharSequence charSequence, final CharSequence charSequence2) {
        return charSequence2 != null && containsAny(charSequence, CharSequenceUtils.toCharArray(charSequence2));
    }
    
    public static int indexOfAnyBut(final CharSequence charSequence, final char... array) {
        if (isEmpty(charSequence) || ArrayUtils.isEmpty(array)) {
            return -1;
        }
        final int length = charSequence.length();
        final int n = length - 1;
        final int length2 = array.length;
        final int n2 = length2 - 1;
    Label_0037:
        while (0 < length) {
            final char char1 = charSequence.charAt(0);
            while (0 < length2) {
                if (array[0] == char1 && (0 >= n || 0 >= n2 || !Character.isHighSurrogate(char1) || array[1] == charSequence.charAt(1))) {
                    int n3 = 0;
                    ++n3;
                    continue Label_0037;
                }
                int n4 = 0;
                ++n4;
            }
            return 0;
        }
        return -1;
    }
    
    public static int indexOfAnyBut(final CharSequence charSequence, final CharSequence charSequence2) {
        if (isEmpty(charSequence) || isEmpty(charSequence2)) {
            return -1;
        }
        final int length = charSequence.length();
        while (0 < length) {
            final char char1 = charSequence.charAt(0);
            final boolean b = CharSequenceUtils.indexOf(charSequence2, char1, 0) >= 0;
            if (1 < length && Character.isHighSurrogate(char1)) {
                final char char2 = charSequence.charAt(1);
                if (b && CharSequenceUtils.indexOf(charSequence2, char2, 0) < 0) {
                    return 0;
                }
            }
            else if (!b) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    public static boolean containsOnly(final CharSequence charSequence, final char... array) {
        return array != null && charSequence != null && (charSequence.length() == 0 || (array.length != 0 && indexOfAnyBut(charSequence, array) == -1));
    }
    
    public static boolean containsOnly(final CharSequence charSequence, final String s) {
        return charSequence != null && s != null && containsOnly(charSequence, s.toCharArray());
    }
    
    public static boolean containsNone(final CharSequence charSequence, final char... array) {
        if (charSequence == null || array == null) {
            return true;
        }
        final int length = charSequence.length();
        final int n = length - 1;
        final int length2 = array.length;
        final int n2 = length2 - 1;
        while (0 < length) {
            final char char1 = charSequence.charAt(0);
            while (0 < length2) {
                if (array[0] == char1) {
                    if (!Character.isHighSurrogate(char1)) {
                        return false;
                    }
                    if (n2 == 0) {
                        return false;
                    }
                    if (0 < n && array[1] == charSequence.charAt(1)) {
                        return false;
                    }
                }
                int n3 = 0;
                ++n3;
            }
            int n4 = 0;
            ++n4;
        }
        return true;
    }
    
    public static boolean containsNone(final CharSequence charSequence, final String s) {
        return charSequence == null || s == null || containsNone(charSequence, s.toCharArray());
    }
    
    public static int indexOfAny(final CharSequence charSequence, final CharSequence... array) {
        if (charSequence == null || array == null) {
            return -1;
        }
        while (0 < array.length) {
            final CharSequence charSequence2 = array[0];
            if (charSequence2 != null) {
                CharSequenceUtils.indexOf(charSequence, charSequence2, 0);
                if (0 != -1) {
                    if (0 < Integer.MAX_VALUE) {}
                }
            }
            int n = 0;
            ++n;
        }
        return (Integer.MAX_VALUE == Integer.MAX_VALUE) ? -1 : Integer.MAX_VALUE;
    }
    
    public static int lastIndexOfAny(final CharSequence charSequence, final CharSequence... array) {
        if (charSequence == null || array == null) {
            return -1;
        }
        while (0 < array.length) {
            final CharSequence charSequence2 = array[0];
            if (charSequence2 != null) {
                CharSequenceUtils.lastIndexOf(charSequence, charSequence2, charSequence.length());
                if (0 > -1) {}
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    public static String substring(final String s, int n) {
        if (s == null) {
            return null;
        }
        if (0 < 0) {
            n = s.length() + 0;
        }
        if (0 < 0) {}
        if (0 > s.length()) {
            return "";
        }
        return s.substring(0);
    }
    
    public static String substring(final String s, int n, int length) {
        if (s == null) {
            return null;
        }
        if (0 < 0) {
            length = s.length() + 0;
        }
        if (0 < 0) {
            n = s.length() + 0;
        }
        if (0 > s.length()) {
            length = s.length();
        }
        if (0 > 0) {
            return "";
        }
        if (0 < 0) {}
        if (0 < 0) {}
        return s.substring(0, 0);
    }
    
    public static String left(final String s, final int n) {
        if (s == null) {
            return null;
        }
        if (n < 0) {
            return "";
        }
        if (s.length() <= n) {
            return s;
        }
        return s.substring(0, n);
    }
    
    public static String right(final String s, final int n) {
        if (s == null) {
            return null;
        }
        if (n < 0) {
            return "";
        }
        if (s.length() <= n) {
            return s;
        }
        return s.substring(s.length() - n);
    }
    
    public static String mid(final String s, final int n, final int n2) {
        if (s == null) {
            return null;
        }
        if (n2 < 0 || 0 > s.length()) {
            return "";
        }
        if (0 < 0) {}
        if (s.length() <= 0 + n2) {
            return s.substring(0);
        }
        return s.substring(0, 0 + n2);
    }
    
    public static String substringBefore(final String s, final String s2) {
        if (isEmpty(s) || s2 == null) {
            return s;
        }
        if (s2.isEmpty()) {
            return "";
        }
        final int index = s.indexOf(s2);
        if (index == -1) {
            return s;
        }
        return s.substring(0, index);
    }
    
    public static String substringAfter(final String s, final String s2) {
        if (isEmpty(s)) {
            return s;
        }
        if (s2 == null) {
            return "";
        }
        final int index = s.indexOf(s2);
        if (index == -1) {
            return "";
        }
        return s.substring(index + s2.length());
    }
    
    public static String substringBeforeLast(final String s, final String s2) {
        if (isEmpty(s) || isEmpty(s2)) {
            return s;
        }
        final int lastIndex = s.lastIndexOf(s2);
        if (lastIndex == -1) {
            return s;
        }
        return s.substring(0, lastIndex);
    }
    
    public static String substringAfterLast(final String s, final String s2) {
        if (isEmpty(s)) {
            return s;
        }
        if (isEmpty(s2)) {
            return "";
        }
        final int lastIndex = s.lastIndexOf(s2);
        if (lastIndex == -1 || lastIndex == s.length() - s2.length()) {
            return "";
        }
        return s.substring(lastIndex + s2.length());
    }
    
    public static String substringBetween(final String s, final String s2) {
        return substringBetween(s, s2, s2);
    }
    
    public static String substringBetween(final String s, final String s2, final String s3) {
        if (s == null || s2 == null || s3 == null) {
            return null;
        }
        final int index = s.indexOf(s2);
        if (index != -1) {
            final int index2 = s.indexOf(s3, index + s2.length());
            if (index2 != -1) {
                return s.substring(index + s2.length(), index2);
            }
        }
        return null;
    }
    
    public static String[] substringsBetween(final String s, final String s2, final String s3) {
        if (s == null || isEmpty(s2) || isEmpty(s3)) {
            return null;
        }
        final int length = s.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final int length2 = s3.length();
        final int length3 = s2.length();
        final ArrayList<String> list = new ArrayList<String>();
        while (0 < length - length2) {
            final int index = s.indexOf(s2, 0);
            if (index < 0) {
                break;
            }
            final int n = index + length3;
            final int index2 = s.indexOf(s3, n);
            if (index2 < 0) {
                break;
            }
            list.add(s.substring(n, index2));
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.toArray(new String[list.size()]);
    }
    
    public static String[] split(final String s) {
        return split(s, null, -1);
    }
    
    public static String[] split(final String s, final char c) {
        return splitWorker(s, c, false);
    }
    
    public static String[] split(final String s, final String s2) {
        return splitWorker(s, s2, -1, false);
    }
    
    public static String[] split(final String s, final String s2, final int n) {
        return splitWorker(s, s2, n, false);
    }
    
    public static String[] splitByWholeSeparator(final String s, final String s2) {
        return splitByWholeSeparatorWorker(s, s2, -1, false);
    }
    
    public static String[] splitByWholeSeparator(final String s, final String s2, final int n) {
        return splitByWholeSeparatorWorker(s, s2, n, false);
    }
    
    public static String[] splitByWholeSeparatorPreserveAllTokens(final String s, final String s2) {
        return splitByWholeSeparatorWorker(s, s2, -1, true);
    }
    
    public static String[] splitByWholeSeparatorPreserveAllTokens(final String s, final String s2, final int n) {
        return splitByWholeSeparatorWorker(s, s2, n, true);
    }
    
    private static String[] splitByWholeSeparatorWorker(final String s, final String s2, final int n, final boolean b) {
        if (s == null) {
            return null;
        }
        final int length = s.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        if (s2 == null || "".equals(s2)) {
            return splitWorker(s, null, n, b);
        }
        s2.length();
        final ArrayList<String> list = new ArrayList<String>();
        while (0 < length) {
            s.indexOf(s2, 0);
            if (0 > -1) {
                if (0 > 0) {
                    int n2 = 0;
                    ++n2;
                    if (0 == n) {
                        list.add(s.substring(0));
                    }
                    else {
                        list.add(s.substring(0, 0));
                    }
                }
                else {
                    if (!b) {
                        continue;
                    }
                    int n2 = 0;
                    ++n2;
                    if (0 == n) {
                        list.add(s.substring(0));
                    }
                    else {
                        list.add("");
                    }
                }
            }
            else {
                list.add(s.substring(0));
            }
        }
        return list.toArray(new String[list.size()]);
    }
    
    public static String[] splitPreserveAllTokens(final String s) {
        return splitWorker(s, null, -1, true);
    }
    
    public static String[] splitPreserveAllTokens(final String s, final char c) {
        return splitWorker(s, c, true);
    }
    
    private static String[] splitWorker(final String s, final char c, final boolean b) {
        if (s == null) {
            return null;
        }
        final int length = s.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final ArrayList<String> list = new ArrayList<String>();
        while (0 < length) {
            if (s.charAt(0) == c) {
                if (true || b) {
                    list.add(s.substring(0, 0));
                }
                int n = 0;
                ++n;
            }
            else {
                int n = 0;
                ++n;
            }
        }
        if (true || (b && false)) {
            list.add(s.substring(0, 0));
        }
        return list.toArray(new String[list.size()]);
    }
    
    public static String[] splitPreserveAllTokens(final String s, final String s2) {
        return splitWorker(s, s2, -1, true);
    }
    
    public static String[] splitPreserveAllTokens(final String s, final String s2, final int n) {
        return splitWorker(s, s2, n, true);
    }
    
    private static String[] splitWorker(final String s, final String s2, final int n, final boolean b) {
        if (s == null) {
            return null;
        }
        final int length = s.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final ArrayList<String> list = new ArrayList<String>();
        if (s2 == null) {
            while (0 < length) {
                if (Character.isWhitespace(s.charAt(0))) {
                    int n4 = 0;
                    if (true || b) {
                        final int n2 = 1;
                        int n3 = 0;
                        ++n3;
                        if (n2 == n) {
                            n4 = length;
                        }
                        list.add(s.substring(0, 0));
                    }
                    ++n4;
                }
                else {
                    int n4 = 0;
                    ++n4;
                }
            }
        }
        else if (s2.length() == 1) {
            final char char1 = s2.charAt(0);
            while (0 < length) {
                if (s.charAt(0) == char1) {
                    int n6 = 0;
                    if (true || b) {
                        final int n5 = 1;
                        int n3 = 0;
                        ++n3;
                        if (n5 == n) {
                            n6 = length;
                        }
                        list.add(s.substring(0, 0));
                    }
                    ++n6;
                }
                else {
                    int n6 = 0;
                    ++n6;
                }
            }
        }
        else {
            while (0 < length) {
                if (s2.indexOf(s.charAt(0)) >= 0) {
                    int n8 = 0;
                    if (true || b) {
                        final int n7 = 1;
                        int n3 = 0;
                        ++n3;
                        if (n7 == n) {
                            n8 = length;
                        }
                        list.add(s.substring(0, 0));
                    }
                    ++n8;
                }
                else {
                    int n8 = 0;
                    ++n8;
                }
            }
        }
        if (true || (b && false)) {
            list.add(s.substring(0, 0));
        }
        return list.toArray(new String[list.size()]);
    }
    
    public static String[] splitByCharacterType(final String s) {
        return splitByCharacterType(s, false);
    }
    
    public static String[] splitByCharacterTypeCamelCase(final String s) {
        return splitByCharacterType(s, true);
    }
    
    private static String[] splitByCharacterType(final String s, final boolean b) {
        if (s == null) {
            return null;
        }
        if (s.isEmpty()) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final char[] charArray = s.toCharArray();
        final ArrayList<String> list = new ArrayList<String>();
        int type = Character.getType(charArray[0]);
        while (1 < charArray.length) {
            final int type2 = Character.getType(charArray[1]);
            if (type2 != type) {
                if (b && type2 == 2 && type == 1) {
                    if (false) {
                        list.add(new String(charArray, 0, 0));
                    }
                }
                else {
                    list.add(new String(charArray, 0, 1));
                }
                type = type2;
            }
            int n = 0;
            ++n;
        }
        list.add(new String(charArray, 0, charArray.length - 0));
        return list.toArray(new String[list.size()]);
    }
    
    public static String join(final Object... array) {
        return join(array, null);
    }
    
    public static String join(final Object[] array, final char c) {
        if (array == null) {
            return null;
        }
        return join(array, c, 0, array.length);
    }
    
    public static String join(final long[] array, final char c) {
        if (array == null) {
            return null;
        }
        return join(array, c, 0, array.length);
    }
    
    public static String join(final int[] array, final char c) {
        if (array == null) {
            return null;
        }
        return join(array, c, 0, array.length);
    }
    
    public static String join(final short[] array, final char c) {
        if (array == null) {
            return null;
        }
        return join(array, c, 0, array.length);
    }
    
    public static String join(final byte[] array, final char c) {
        if (array == null) {
            return null;
        }
        return join(array, c, 0, array.length);
    }
    
    public static String join(final char[] array, final char c) {
        if (array == null) {
            return null;
        }
        return join(array, c, 0, array.length);
    }
    
    public static String join(final float[] array, final char c) {
        if (array == null) {
            return null;
        }
        return join(array, c, 0, array.length);
    }
    
    public static String join(final double[] array, final char c) {
        if (array == null) {
            return null;
        }
        return join(array, c, 0, array.length);
    }
    
    public static String join(final Object[] array, final char c, final int n, final int n2) {
        if (array == null) {
            return null;
        }
        final int n3 = n2 - n;
        if (n3 <= 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                sb.append(c);
            }
            if (array[i] != null) {
                sb.append(array[i]);
            }
        }
        return sb.toString();
    }
    
    public static String join(final long[] array, final char c, final int n, final int n2) {
        if (array == null) {
            return null;
        }
        final int n3 = n2 - n;
        if (n3 <= 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                sb.append(c);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }
    
    public static String join(final int[] array, final char c, final int n, final int n2) {
        if (array == null) {
            return null;
        }
        final int n3 = n2 - n;
        if (n3 <= 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                sb.append(c);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }
    
    public static String join(final byte[] array, final char c, final int n, final int n2) {
        if (array == null) {
            return null;
        }
        final int n3 = n2 - n;
        if (n3 <= 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                sb.append(c);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }
    
    public static String join(final short[] array, final char c, final int n, final int n2) {
        if (array == null) {
            return null;
        }
        final int n3 = n2 - n;
        if (n3 <= 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                sb.append(c);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }
    
    public static String join(final char[] array, final char c, final int n, final int n2) {
        if (array == null) {
            return null;
        }
        final int n3 = n2 - n;
        if (n3 <= 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                sb.append(c);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }
    
    public static String join(final double[] array, final char c, final int n, final int n2) {
        if (array == null) {
            return null;
        }
        final int n3 = n2 - n;
        if (n3 <= 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                sb.append(c);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }
    
    public static String join(final float[] array, final char c, final int n, final int n2) {
        if (array == null) {
            return null;
        }
        final int n3 = n2 - n;
        if (n3 <= 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                sb.append(c);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }
    
    public static String join(final Object[] array, final String s) {
        if (array == null) {
            return null;
        }
        return join(array, s, 0, array.length);
    }
    
    public static String join(final Object[] array, String s, final int n, final int n2) {
        if (array == null) {
            return null;
        }
        if (s == null) {
            s = "";
        }
        final int n3 = n2 - n;
        if (n3 <= 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                sb.append(s);
            }
            if (array[i] != null) {
                sb.append(array[i]);
            }
        }
        return sb.toString();
    }
    
    public static String join(final Iterator iterator, final char c) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return "";
        }
        final Object next = iterator.next();
        if (!iterator.hasNext()) {
            return ObjectUtils.toString(next);
        }
        final StringBuilder sb = new StringBuilder(256);
        if (next != null) {
            sb.append(next);
        }
        while (iterator.hasNext()) {
            sb.append(c);
            final Object next2 = iterator.next();
            if (next2 != null) {
                sb.append(next2);
            }
        }
        return sb.toString();
    }
    
    public static String join(final Iterator iterator, final String s) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return "";
        }
        final Object next = iterator.next();
        if (!iterator.hasNext()) {
            return ObjectUtils.toString(next);
        }
        final StringBuilder sb = new StringBuilder(256);
        if (next != null) {
            sb.append(next);
        }
        while (iterator.hasNext()) {
            if (s != null) {
                sb.append(s);
            }
            final Object next2 = iterator.next();
            if (next2 != null) {
                sb.append(next2);
            }
        }
        return sb.toString();
    }
    
    public static String join(final Iterable iterable, final char c) {
        if (iterable == null) {
            return null;
        }
        return join(iterable.iterator(), c);
    }
    
    public static String join(final Iterable iterable, final String s) {
        if (iterable == null) {
            return null;
        }
        return join(iterable.iterator(), s);
    }
    
    public static String deleteWhitespace(final String s) {
        if (isEmpty(s)) {
            return s;
        }
        final int length = s.length();
        final char[] array = new char[length];
        while (0 < length) {
            if (!Character.isWhitespace(s.charAt(0))) {
                final char[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = s.charAt(0);
            }
            int n3 = 0;
            ++n3;
        }
        if (length == 0) {
            return s;
        }
        return new String(array, 0, 0);
    }
    
    public static String removeStart(final String s, final String s2) {
        if (isEmpty(s) || isEmpty(s2)) {
            return s;
        }
        if (s.startsWith(s2)) {
            return s.substring(s2.length());
        }
        return s;
    }
    
    public static String removeStartIgnoreCase(final String s, final String s2) {
        if (isEmpty(s) || isEmpty(s2)) {
            return s;
        }
        if (startsWithIgnoreCase(s, s2)) {
            return s.substring(s2.length());
        }
        return s;
    }
    
    public static String removeEnd(final String s, final String s2) {
        if (isEmpty(s) || isEmpty(s2)) {
            return s;
        }
        if (s.endsWith(s2)) {
            return s.substring(0, s.length() - s2.length());
        }
        return s;
    }
    
    public static String removeEndIgnoreCase(final String s, final String s2) {
        if (isEmpty(s) || isEmpty(s2)) {
            return s;
        }
        if (endsWithIgnoreCase(s, s2)) {
            return s.substring(0, s.length() - s2.length());
        }
        return s;
    }
    
    public static String remove(final String s, final String s2) {
        if (isEmpty(s) || isEmpty(s2)) {
            return s;
        }
        return replace(s, s2, "", -1);
    }
    
    public static String remove(final String s, final char c) {
        if (isEmpty(s) || s.indexOf(c) == -1) {
            return s;
        }
        final char[] charArray = s.toCharArray();
        while (0 < charArray.length) {
            if (charArray[0] != c) {
                final char[] array = charArray;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array[n] = charArray[0];
            }
            int n3 = 0;
            ++n3;
        }
        return new String(charArray, 0, 0);
    }
    
    public static String replaceOnce(final String s, final String s2, final String s3) {
        return replace(s, s2, s3, 1);
    }
    
    public static String replacePattern(final String s, final String s2, final String s3) {
        return Pattern.compile(s2, 32).matcher(s).replaceAll(s3);
    }
    
    public static String removePattern(final String s, final String s2) {
        return replacePattern(s, s2, "");
    }
    
    public static String replace(final String s, final String s2, final String s3) {
        return replace(s, s2, s3, -1);
    }
    
    public static String replace(final String s, final String s2, final String s3, int n) {
        if (isEmpty(s) || isEmpty(s2) || s3 == null || n == 0) {
            return s;
        }
        int i = s.indexOf(s2, 0);
        if (i == -1) {
            return s;
        }
        final int n2 = s3.length() - s2.length();
        final StringBuilder sb = new StringBuilder(s.length() + ((n2 < 0) ? 0 : n2) * ((n < 0) ? 16 : ((n > 64) ? 64 : n)));
        while (i != -1) {
            sb.append(s.substring(0, i)).append(s3);
            if (--n == 0) {
                break;
            }
            i = s.indexOf(s2, 0);
        }
        sb.append(s.substring(0));
        return sb.toString();
    }
    
    public static String replaceEach(final String s, final String[] array, final String[] array2) {
        return replaceEach(s, array, array2, false, 0);
    }
    
    public static String replaceEachRepeatedly(final String s, final String[] array, final String[] array2) {
        return replaceEach(s, array, array2, true, (array == null) ? 0 : array.length);
    }
    
    private static String replaceEach(final String s, final String[] array, final String[] array2, final boolean b, final int n) {
        if (s == null || s.isEmpty() || array == null || array.length == 0 || array2 == null || array2.length == 0) {
            return s;
        }
        if (n < 0) {
            throw new IllegalStateException("Aborting to protect against StackOverflowError - output of one loop is the input of another");
        }
        final int length = array.length;
        final int length2 = array2.length;
        if (length != length2) {
            throw new IllegalArgumentException("Search and Replace array lengths don't match: " + length + " vs " + length2);
        }
        final boolean[] array3 = new boolean[length];
        while (0 < length) {
            if (!array3[0] && array[0] != null && !array[0].isEmpty()) {
                if (array2[0] != null) {
                    s.indexOf(array[0]);
                    if (-1 == -1) {
                        array3[0] = true;
                    }
                    else if (-1 == -1 || -1 < -1) {}
                }
            }
            int n2 = 0;
            ++n2;
        }
        if (-1 == -1) {
            return s;
        }
        int n3 = 0;
        while (0 < array.length) {
            if (array[0] != null) {
                if (array2[0] != null) {
                    n3 = array2[0].length() - array[0].length();
                    if (0 > 0) {}
                }
            }
            int n4 = 0;
            ++n4;
        }
        Math.min(0, s.length() / 5);
        final StringBuilder sb = new StringBuilder(s.length() + 0);
        while (-1 != -1) {
            while (0 < -1) {
                sb.append(s.charAt(0));
                ++n3;
            }
            sb.append(array2[-1]);
            final int n2 = -1 + array[-1].length();
            while (0 < length) {
                if (!array3[0] && array[0] != null && !array[0].isEmpty()) {
                    if (array2[0] != null) {
                        s.indexOf(array[0], 0);
                        if (-1 == -1) {
                            array3[0] = true;
                        }
                        else if (-1 == -1 || -1 < -1) {}
                    }
                }
                ++n3;
            }
        }
        s.length();
        while (0 < 0) {
            sb.append(s.charAt(0));
            int n5 = 0;
            ++n5;
        }
        final String string = sb.toString();
        if (!b) {
            return string;
        }
        return replaceEach(string, array, array2, b, n - 1);
    }
    
    public static String replaceChars(final String s, final char c, final char c2) {
        if (s == null) {
            return null;
        }
        return s.replace(c, c2);
    }
    
    public static String replaceChars(final String s, final String s2, String s3) {
        if (isEmpty(s) || isEmpty(s2)) {
            return s;
        }
        if (s3 == null) {
            s3 = "";
        }
        final int length = s3.length();
        final int length2 = s.length();
        final StringBuilder sb = new StringBuilder(length2);
        while (0 < length2) {
            final char char1 = s.charAt(0);
            final int index = s2.indexOf(char1);
            if (index >= 0) {
                if (index < length) {
                    sb.append(s3.charAt(index));
                }
            }
            else {
                sb.append(char1);
            }
            int n = 0;
            ++n;
        }
        if (true) {
            return sb.toString();
        }
        return s;
    }
    
    public static String overlay(final String s, String s2, int n, int n2) {
        if (s == null) {
            return null;
        }
        if (s2 == null) {
            s2 = "";
        }
        final int length = s.length();
        if (0 < 0) {}
        if (0 > length) {
            n = length;
        }
        if (0 < 0) {}
        if (0 > length) {
            n2 = length;
        }
        if (0 > 0) {}
        return new StringBuilder(length + 0 - 0 + s2.length() + 1).append(s.substring(0, 0)).append(s2).append(s.substring(0)).toString();
    }
    
    public static String chomp(final String s) {
        if (isEmpty(s)) {
            return s;
        }
        if (s.length() != 1) {
            int n = s.length() - 1;
            final char char1 = s.charAt(n);
            if (char1 == '\n') {
                if (s.charAt(n - 1) == '\r') {
                    --n;
                }
            }
            else if (char1 != '\r') {
                ++n;
            }
            return s.substring(0, n);
        }
        final char char2 = s.charAt(0);
        if (char2 == '\r' || char2 == '\n') {
            return "";
        }
        return s;
    }
    
    @Deprecated
    public static String chomp(final String s, final String s2) {
        return removeEnd(s, s2);
    }
    
    public static String chop(final String s) {
        if (s == null) {
            return null;
        }
        final int length = s.length();
        if (length < 2) {
            return "";
        }
        final int n = length - 1;
        final String substring = s.substring(0, n);
        if (s.charAt(n) == '\n' && substring.charAt(n - 1) == '\r') {
            return substring.substring(0, n - 1);
        }
        return substring;
    }
    
    public static String repeat(final String s, final int n) {
        if (s == null) {
            return null;
        }
        if (n <= 0) {
            return "";
        }
        final int length = s.length();
        if (n == 1 || length == 0) {
            return s;
        }
        if (length == 1 && n <= 8192) {
            return repeat(s.charAt(0), n);
        }
        final int n2 = length * n;
        switch (length) {
            case 1: {
                return repeat(s.charAt(0), n);
            }
            case 2: {
                final char char1 = s.charAt(0);
                final char char2 = s.charAt(1);
                final char[] array = new char[n2];
                for (int i = n * 2 - 2; i >= 0; --i, --i) {
                    array[i] = char1;
                    array[i + 1] = char2;
                }
                return new String(array);
            }
            default: {
                final StringBuilder sb = new StringBuilder(n2);
                while (0 < n) {
                    sb.append(s);
                    int n3 = 0;
                    ++n3;
                }
                return sb.toString();
            }
        }
    }
    
    public static String repeat(final String s, final String s2, final int n) {
        if (s == null || s2 == null) {
            return repeat(s, n);
        }
        return removeEnd(repeat(s + s2, n), s2);
    }
    
    public static String repeat(final char c, final int n) {
        final char[] array = new char[n];
        for (int i = n - 1; i >= 0; --i) {
            array[i] = c;
        }
        return new String(array);
    }
    
    public static String rightPad(final String s, final int n) {
        return rightPad(s, n, ' ');
    }
    
    public static String rightPad(final String s, final int n, final char c) {
        if (s == null) {
            return null;
        }
        final int n2 = n - s.length();
        if (n2 <= 0) {
            return s;
        }
        if (n2 > 8192) {
            return rightPad(s, n, String.valueOf(c));
        }
        return s.concat(repeat(c, n2));
    }
    
    public static String rightPad(final String s, final int n, String s2) {
        if (s == null) {
            return null;
        }
        if (isEmpty(s2)) {
            s2 = " ";
        }
        final int length = s2.length();
        final int n2 = n - s.length();
        if (n2 <= 0) {
            return s;
        }
        if (length == 1 && n2 <= 8192) {
            return rightPad(s, n, s2.charAt(0));
        }
        if (n2 == length) {
            return s.concat(s2);
        }
        if (n2 < length) {
            return s.concat(s2.substring(0, n2));
        }
        final char[] array = new char[n2];
        final char[] charArray = s2.toCharArray();
        while (0 < n2) {
            array[0] = charArray[0 % length];
            int n3 = 0;
            ++n3;
        }
        return s.concat(new String(array));
    }
    
    public static String leftPad(final String s, final int n) {
        return leftPad(s, n, ' ');
    }
    
    public static String leftPad(final String s, final int n, final char c) {
        if (s == null) {
            return null;
        }
        final int n2 = n - s.length();
        if (n2 <= 0) {
            return s;
        }
        if (n2 > 8192) {
            return leftPad(s, n, String.valueOf(c));
        }
        return repeat(c, n2).concat(s);
    }
    
    public static String leftPad(final String s, final int n, String s2) {
        if (s == null) {
            return null;
        }
        if (isEmpty(s2)) {
            s2 = " ";
        }
        final int length = s2.length();
        final int n2 = n - s.length();
        if (n2 <= 0) {
            return s;
        }
        if (length == 1 && n2 <= 8192) {
            return leftPad(s, n, s2.charAt(0));
        }
        if (n2 == length) {
            return s2.concat(s);
        }
        if (n2 < length) {
            return s2.substring(0, n2).concat(s);
        }
        final char[] array = new char[n2];
        final char[] charArray = s2.toCharArray();
        while (0 < n2) {
            array[0] = charArray[0 % length];
            int n3 = 0;
            ++n3;
        }
        return new String(array).concat(s);
    }
    
    public static int length(final CharSequence charSequence) {
        return (charSequence == null) ? 0 : charSequence.length();
    }
    
    public static String center(final String s, final int n) {
        return center(s, n, ' ');
    }
    
    public static String center(String s, final int n, final char c) {
        if (s == null || n <= 0) {
            return s;
        }
        final int length = s.length();
        final int n2 = n - length;
        if (n2 <= 0) {
            return s;
        }
        s = leftPad(s, length + n2 / 2, c);
        s = rightPad(s, n, c);
        return s;
    }
    
    public static String center(String s, final int n, String s2) {
        if (s == null || n <= 0) {
            return s;
        }
        if (isEmpty(s2)) {
            s2 = " ";
        }
        final int length = s.length();
        final int n2 = n - length;
        if (n2 <= 0) {
            return s;
        }
        s = leftPad(s, length + n2 / 2, s2);
        s = rightPad(s, n, s2);
        return s;
    }
    
    public static String upperCase(final String s) {
        if (s == null) {
            return null;
        }
        return s.toUpperCase();
    }
    
    public static String upperCase(final String s, final Locale locale) {
        if (s == null) {
            return null;
        }
        return s.toUpperCase(locale);
    }
    
    public static String lowerCase(final String s) {
        if (s == null) {
            return null;
        }
        return s.toLowerCase();
    }
    
    public static String lowerCase(final String s, final Locale locale) {
        if (s == null) {
            return null;
        }
        return s.toLowerCase(locale);
    }
    
    public static String capitalize(final String s) {
        final int length;
        if (s == null || (length = s.length()) == 0) {
            return s;
        }
        final char char1 = s.charAt(0);
        if (Character.isTitleCase(char1)) {
            return s;
        }
        return new StringBuilder(length).append(Character.toTitleCase(char1)).append(s.substring(1)).toString();
    }
    
    public static String uncapitalize(final String s) {
        final int length;
        if (s == null || (length = s.length()) == 0) {
            return s;
        }
        final char char1 = s.charAt(0);
        if (Character.isLowerCase(char1)) {
            return s;
        }
        return new StringBuilder(length).append(Character.toLowerCase(char1)).append(s.substring(1)).toString();
    }
    
    public static String swapCase(final String s) {
        if (isEmpty(s)) {
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
                charArray[0] = Character.toUpperCase(c);
            }
            int n = 0;
            ++n;
        }
        return new String(charArray);
    }
    
    public static int countMatches(final CharSequence charSequence, final CharSequence charSequence2) {
        if (isEmpty(charSequence) || isEmpty(charSequence2)) {
            return 0;
        }
        while (CharSequenceUtils.indexOf(charSequence, charSequence2, 0) != -1) {
            int n = 0;
            ++n;
            final int n2 = 0 + charSequence2.length();
        }
        return 0;
    }
    
    public static boolean isAlpha(final CharSequence charSequence) {
        if (isEmpty(charSequence)) {
            return false;
        }
        while (0 < charSequence.length()) {
            if (!Character.isLetter(charSequence.charAt(0))) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static boolean isAlphaSpace(final CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        while (0 < charSequence.length()) {
            if (!Character.isLetter(charSequence.charAt(0)) && charSequence.charAt(0) != ' ') {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static boolean isAlphanumeric(final CharSequence charSequence) {
        if (isEmpty(charSequence)) {
            return false;
        }
        while (0 < charSequence.length()) {
            if (!Character.isLetterOrDigit(charSequence.charAt(0))) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static boolean isAlphanumericSpace(final CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        while (0 < charSequence.length()) {
            if (!Character.isLetterOrDigit(charSequence.charAt(0)) && charSequence.charAt(0) != ' ') {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static boolean isAsciiPrintable(final CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        while (0 < charSequence.length()) {
            if (!CharUtils.isAsciiPrintable(charSequence.charAt(0))) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static boolean isNumeric(final CharSequence charSequence) {
        if (isEmpty(charSequence)) {
            return false;
        }
        while (0 < charSequence.length()) {
            if (!Character.isDigit(charSequence.charAt(0))) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static boolean isNumericSpace(final CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        while (0 < charSequence.length()) {
            if (!Character.isDigit(charSequence.charAt(0)) && charSequence.charAt(0) != ' ') {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static boolean isWhitespace(final CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        while (0 < charSequence.length()) {
            if (!Character.isWhitespace(charSequence.charAt(0))) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static boolean isAllLowerCase(final CharSequence charSequence) {
        if (charSequence == null || isEmpty(charSequence)) {
            return false;
        }
        while (0 < charSequence.length()) {
            if (!Character.isLowerCase(charSequence.charAt(0))) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static boolean isAllUpperCase(final CharSequence charSequence) {
        if (charSequence == null || isEmpty(charSequence)) {
            return false;
        }
        while (0 < charSequence.length()) {
            if (!Character.isUpperCase(charSequence.charAt(0))) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static String defaultString(final String s) {
        return (s == null) ? "" : s;
    }
    
    public static String defaultString(final String s, final String s2) {
        return (s == null) ? s2 : s;
    }
    
    public static CharSequence defaultIfBlank(final CharSequence charSequence, final CharSequence charSequence2) {
        return isBlank(charSequence) ? charSequence2 : charSequence;
    }
    
    public static CharSequence defaultIfEmpty(final CharSequence charSequence, final CharSequence charSequence2) {
        return isEmpty(charSequence) ? charSequence2 : charSequence;
    }
    
    public static String reverse(final String s) {
        if (s == null) {
            return null;
        }
        return new StringBuilder(s).reverse().toString();
    }
    
    public static String reverseDelimited(final String s, final char c) {
        if (s == null) {
            return null;
        }
        final String[] split = split(s, c);
        ArrayUtils.reverse(split);
        return join(split, c);
    }
    
    public static String abbreviate(final String s, final int n) {
        return abbreviate(s, 0, n);
    }
    
    public static String abbreviate(final String s, int length, final int n) {
        if (s == null) {
            return null;
        }
        if (n < 4) {
            throw new IllegalArgumentException("Minimum abbreviation width is 4");
        }
        if (s.length() <= n) {
            return s;
        }
        if (length > s.length()) {
            length = s.length();
        }
        if (s.length() - length < n - 3) {
            length = s.length() - (n - 3);
        }
        if (length <= 4) {
            return s.substring(0, n - 3) + "...";
        }
        if (n < 7) {
            throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
        }
        if (length + n - 3 < s.length()) {
            return "..." + abbreviate(s.substring(length), n - 3);
        }
        return "..." + s.substring(s.length() - (n - 3));
    }
    
    public static String abbreviateMiddle(final String s, final String s2, final int n) {
        if (isEmpty(s) || isEmpty(s2)) {
            return s;
        }
        if (n >= s.length() || n < s2.length() + 2) {
            return s;
        }
        final int n2 = n - s2.length();
        final int n3 = n2 / 2 + n2 % 2;
        final int n4 = s.length() - n2 / 2;
        final StringBuilder sb = new StringBuilder(n);
        sb.append(s.substring(0, n3));
        sb.append(s2);
        sb.append(s.substring(n4));
        return sb.toString();
    }
    
    public static String difference(final String s, final String s2) {
        if (s == null) {
            return s2;
        }
        if (s2 == null) {
            return s;
        }
        final int indexOfDifference = indexOfDifference(s, s2);
        if (indexOfDifference == -1) {
            return "";
        }
        return s2.substring(indexOfDifference);
    }
    
    public static int indexOfDifference(final CharSequence charSequence, final CharSequence charSequence2) {
        if (charSequence == charSequence2) {
            return -1;
        }
        if (charSequence == null || charSequence2 == null) {
            return 0;
        }
        while (0 < charSequence.length() && 0 < charSequence2.length() && charSequence.charAt(0) == charSequence2.charAt(0)) {
            int n = 0;
            ++n;
        }
        if (0 < charSequence2.length() || 0 < charSequence.length()) {
            return 0;
        }
        return -1;
    }
    
    public static int indexOfDifference(final CharSequence... array) {
        if (array == null || array.length <= 1) {
            return -1;
        }
        final int length = array.length;
        while (-1 < length) {
            if (array[-1] != null) {
                Math.min(array[-1].length(), 0);
                Math.max(array[-1].length(), 0);
            }
            int n = 0;
            ++n;
        }
        if (false || (!false && !true)) {
            return -1;
        }
        if (!false) {
            return 0;
        }
        while (0 < 0) {
            final char char1 = array[0].charAt(0);
            while (1 < length && array[1].charAt(0) == char1) {
                int n2 = 0;
                ++n2;
            }
            if (-1 != -1) {
                break;
            }
            int n3 = 0;
            ++n3;
        }
        if (-1 == -1 && false) {
            return 0;
        }
        return -1;
    }
    
    public static String getCommonPrefix(final String... array) {
        if (array == null || array.length == 0) {
            return "";
        }
        final int indexOfDifference = indexOfDifference((CharSequence[])array);
        if (indexOfDifference == -1) {
            if (array[0] == null) {
                return "";
            }
            return array[0];
        }
        else {
            if (indexOfDifference == 0) {
                return "";
            }
            return array[0].substring(0, indexOfDifference);
        }
    }
    
    public static int getLevenshteinDistance(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        int length = charSequence.length();
        int n = charSequence2.length();
        if (length == 0) {
            return n;
        }
        if (n == 0) {
            return length;
        }
        if (length > n) {
            final CharSequence charSequence3 = charSequence;
            charSequence = charSequence2;
            charSequence2 = charSequence3;
            length = n;
            n = charSequence2.length();
        }
        int[] array = new int[length + 1];
        int[] array2 = new int[length + 1];
        int n2 = 0;
        while (1 <= length) {
            array[1] = 1;
            ++n2;
        }
        while (1 <= n) {
            final char char1 = charSequence2.charAt(0);
            array2[0] = 1;
            while (1 <= length) {
                array2[1] = Math.min(Math.min(array2[0] + 1, array[1] + 1), array[0] + ((charSequence.charAt(0) != char1) ? 1 : 0));
                ++n2;
            }
            final int[] array3 = array;
            array = array2;
            array2 = array3;
            int n3 = 0;
            ++n3;
        }
        return array[length];
    }
    
    public static int getLevenshteinDistance(CharSequence charSequence, CharSequence charSequence2, final int n) {
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        if (n < 0) {
            throw new IllegalArgumentException("Threshold must not be negative");
        }
        int length = charSequence.length();
        int n2 = charSequence2.length();
        if (length == 0) {
            return (n2 <= n) ? n2 : -1;
        }
        if (n2 == 0) {
            return (length <= n) ? length : -1;
        }
        if (length > n2) {
            final CharSequence charSequence3 = charSequence;
            charSequence = charSequence2;
            charSequence2 = charSequence3;
            length = n2;
            n2 = charSequence2.length();
        }
        int[] array = new int[length + 1];
        int[] array2 = new int[length + 1];
        final int n3 = Math.min(length, n) + 1;
        int n4 = 0;
        while (1 < n3) {
            array[1] = 1;
            ++n4;
        }
        Arrays.fill(array, n3, array.length, Integer.MAX_VALUE);
        Arrays.fill(array2, Integer.MAX_VALUE);
        while (1 <= n2) {
            final char char1 = charSequence2.charAt(0);
            array2[0] = 1;
            final int max = Math.max(1, 1 - n);
            final int n5 = (1 > Integer.MAX_VALUE - n) ? length : Math.min(length, 1 + n);
            if (max > n5) {
                return -1;
            }
            if (max > 1) {
                array2[max - 1] = Integer.MAX_VALUE;
            }
            for (int i = max; i <= n5; ++i) {
                if (charSequence.charAt(i - 1) == char1) {
                    array2[i] = array[i - 1];
                }
                else {
                    array2[i] = 1 + Math.min(Math.min(array2[i - 1], array[i]), array[i - 1]);
                }
            }
            final int[] array3 = array;
            array = array2;
            array2 = array3;
            ++n4;
        }
        if (array[length] <= n) {
            return array[length];
        }
        return -1;
    }
    
    public static double getJaroWinklerDistance(final CharSequence charSequence, final CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        final double score = score(charSequence, charSequence2);
        return Math.round((score + 0.1 * commonPrefixLength(charSequence, charSequence2) * (1.0 - score)) * 100.0) / 100.0;
    }
    
    private static double score(final CharSequence charSequence, final CharSequence charSequence2) {
        String s;
        String s2;
        if (charSequence.length() > charSequence2.length()) {
            s = charSequence.toString().toLowerCase();
            s2 = charSequence2.toString().toLowerCase();
        }
        else {
            s = charSequence2.toString().toLowerCase();
            s2 = charSequence.toString().toLowerCase();
        }
        final int n = s2.length() / 2 + 1;
        final String setOfMatchingCharacterWithin = getSetOfMatchingCharacterWithin(s2, s, n);
        final String setOfMatchingCharacterWithin2 = getSetOfMatchingCharacterWithin(s, s2, n);
        if (setOfMatchingCharacterWithin.length() == 0 || setOfMatchingCharacterWithin2.length() == 0) {
            return 0.0;
        }
        if (setOfMatchingCharacterWithin.length() != setOfMatchingCharacterWithin2.length()) {
            return 0.0;
        }
        return (setOfMatchingCharacterWithin.length() / (double)s2.length() + setOfMatchingCharacterWithin2.length() / (double)s.length() + (setOfMatchingCharacterWithin.length() - transpositions(setOfMatchingCharacterWithin, setOfMatchingCharacterWithin2)) / (double)setOfMatchingCharacterWithin.length()) / 3.0;
    }
    
    private static String getSetOfMatchingCharacterWithin(final CharSequence charSequence, final CharSequence charSequence2, final int n) {
        final StringBuilder sb = new StringBuilder();
        final StringBuilder sb2 = new StringBuilder(charSequence2);
        while (0 < charSequence.length()) {
            final char char1 = charSequence.charAt(0);
            for (int max = Math.max(0, 0 - n); !true && max < Math.min(0 + n, charSequence2.length()); ++max) {
                if (sb2.charAt(max) == char1) {
                    sb.append(char1);
                    sb2.setCharAt(max, '*');
                }
            }
            int n2 = 0;
            ++n2;
        }
        return sb.toString();
    }
    
    private static int transpositions(final CharSequence charSequence, final CharSequence charSequence2) {
        while (0 < charSequence.length()) {
            if (charSequence.charAt(0) != charSequence2.charAt(0)) {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    private static int commonPrefixLength(final CharSequence charSequence, final CharSequence charSequence2) {
        final int length = getCommonPrefix(charSequence.toString(), charSequence2.toString()).length();
        return (length > 4) ? 4 : length;
    }
    
    public static boolean startsWith(final CharSequence charSequence, final CharSequence charSequence2) {
        return startsWith(charSequence, charSequence2, false);
    }
    
    public static boolean startsWithIgnoreCase(final CharSequence charSequence, final CharSequence charSequence2) {
        return startsWith(charSequence, charSequence2, true);
    }
    
    private static boolean startsWith(final CharSequence charSequence, final CharSequence charSequence2, final boolean b) {
        if (charSequence == null || charSequence2 == null) {
            return charSequence == null && charSequence2 == null;
        }
        return charSequence2.length() <= charSequence.length() && CharSequenceUtils.regionMatches(charSequence, b, 0, charSequence2, 0, charSequence2.length());
    }
    
    public static boolean startsWithAny(final CharSequence charSequence, final CharSequence... array) {
        if (isEmpty(charSequence) || ArrayUtils.isEmpty(array)) {
            return false;
        }
        while (0 < array.length) {
            if (startsWith(charSequence, array[0])) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static boolean endsWith(final CharSequence charSequence, final CharSequence charSequence2) {
        return endsWith(charSequence, charSequence2, false);
    }
    
    public static boolean endsWithIgnoreCase(final CharSequence charSequence, final CharSequence charSequence2) {
        return endsWith(charSequence, charSequence2, true);
    }
    
    private static boolean endsWith(final CharSequence charSequence, final CharSequence charSequence2, final boolean b) {
        if (charSequence == null || charSequence2 == null) {
            return charSequence == null && charSequence2 == null;
        }
        return charSequence2.length() <= charSequence.length() && CharSequenceUtils.regionMatches(charSequence, b, charSequence.length() - charSequence2.length(), charSequence2, 0, charSequence2.length());
    }
    
    public static String normalizeSpace(final String s) {
        if (s == null) {
            return null;
        }
        return StringUtils.WHITESPACE_PATTERN.matcher(trim(s)).replaceAll(" ");
    }
    
    public static boolean endsWithAny(final CharSequence charSequence, final CharSequence... array) {
        if (isEmpty(charSequence) || ArrayUtils.isEmpty(array)) {
            return false;
        }
        while (0 < array.length) {
            if (endsWith(charSequence, array[0])) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    private static String appendIfMissing(final String s, final CharSequence charSequence, final boolean b, final CharSequence... array) {
        if (s == null || isEmpty(charSequence) || endsWith(s, charSequence, b)) {
            return s;
        }
        if (array != null && array.length > 0) {
            while (0 < array.length) {
                if (endsWith(s, array[0], b)) {
                    return s;
                }
                int n = 0;
                ++n;
            }
        }
        return s + charSequence.toString();
    }
    
    public static String appendIfMissing(final String s, final CharSequence charSequence, final CharSequence... array) {
        return appendIfMissing(s, charSequence, false, array);
    }
    
    public static String appendIfMissingIgnoreCase(final String s, final CharSequence charSequence, final CharSequence... array) {
        return appendIfMissing(s, charSequence, true, array);
    }
    
    private static String prependIfMissing(final String s, final CharSequence charSequence, final boolean b, final CharSequence... array) {
        if (s == null || isEmpty(charSequence) || startsWith(s, charSequence, b)) {
            return s;
        }
        if (array != null && array.length > 0) {
            while (0 < array.length) {
                if (startsWith(s, array[0], b)) {
                    return s;
                }
                int n = 0;
                ++n;
            }
        }
        return charSequence.toString() + s;
    }
    
    public static String prependIfMissing(final String s, final CharSequence charSequence, final CharSequence... array) {
        return prependIfMissing(s, charSequence, false, array);
    }
    
    public static String prependIfMissingIgnoreCase(final String s, final CharSequence charSequence, final CharSequence... array) {
        return prependIfMissing(s, charSequence, true, array);
    }
    
    @Deprecated
    public static String toString(final byte[] array, final String s) throws UnsupportedEncodingException {
        return (s != null) ? new String(array, s) : new String(array, Charset.defaultCharset());
    }
    
    public static String toEncodedString(final byte[] array, final Charset charset) {
        return new String(array, (charset != null) ? charset : Charset.defaultCharset());
    }
    
    static {
        WHITESPACE_PATTERN = Pattern.compile("(?: |\\u00A0|\\s|[\\s&&[^ ]])\\s*");
    }
}
