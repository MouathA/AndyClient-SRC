package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import java.util.*;

public final class UTF16
{
    public static final int SINGLE_CHAR_BOUNDARY = 1;
    public static final int LEAD_SURROGATE_BOUNDARY = 2;
    public static final int TRAIL_SURROGATE_BOUNDARY = 5;
    public static final int CODEPOINT_MIN_VALUE = 0;
    public static final int CODEPOINT_MAX_VALUE = 1114111;
    public static final int SUPPLEMENTARY_MIN_VALUE = 65536;
    public static final int LEAD_SURROGATE_MIN_VALUE = 55296;
    public static final int TRAIL_SURROGATE_MIN_VALUE = 56320;
    public static final int LEAD_SURROGATE_MAX_VALUE = 56319;
    public static final int TRAIL_SURROGATE_MAX_VALUE = 57343;
    public static final int SURROGATE_MIN_VALUE = 55296;
    public static final int SURROGATE_MAX_VALUE = 57343;
    private static final int LEAD_SURROGATE_BITMASK = -1024;
    private static final int TRAIL_SURROGATE_BITMASK = -1024;
    private static final int SURROGATE_BITMASK = -2048;
    private static final int LEAD_SURROGATE_BITS = 55296;
    private static final int TRAIL_SURROGATE_BITS = 56320;
    private static final int SURROGATE_BITS = 55296;
    private static final int LEAD_SURROGATE_SHIFT_ = 10;
    private static final int TRAIL_SURROGATE_MASK_ = 1023;
    private static final int LEAD_SURROGATE_OFFSET_ = 55232;
    
    private UTF16() {
    }
    
    public static int charAt(final String s, final int n) {
        final char char1 = s.charAt(n);
        if (char1 < '\ud800') {
            return char1;
        }
        return _charAt(s, n, char1);
    }
    
    private static int _charAt(final String s, int n, final char c) {
        if (c > '\udfff') {
            return c;
        }
        if (c <= '\udbff') {
            ++n;
            if (s.length() != n) {
                final char char1 = s.charAt(n);
                if (char1 >= '\udc00' && char1 <= '\udfff') {
                    return UCharacterProperty.getRawSupplementary(c, char1);
                }
            }
        }
        else if (--n >= 0) {
            final char char2 = s.charAt(n);
            if (char2 >= '\ud800' && char2 <= '\udbff') {
                return UCharacterProperty.getRawSupplementary(char2, c);
            }
        }
        return c;
    }
    
    public static int charAt(final CharSequence charSequence, final int n) {
        final char char1 = charSequence.charAt(n);
        if (char1 < '\ud800') {
            return char1;
        }
        return _charAt(charSequence, n, char1);
    }
    
    private static int _charAt(final CharSequence charSequence, int n, final char c) {
        if (c > '\udfff') {
            return c;
        }
        if (c <= '\udbff') {
            ++n;
            if (charSequence.length() != n) {
                final char char1 = charSequence.charAt(n);
                if (char1 >= '\udc00' && char1 <= '\udfff') {
                    return UCharacterProperty.getRawSupplementary(c, char1);
                }
            }
        }
        else if (--n >= 0) {
            final char char2 = charSequence.charAt(n);
            if (char2 >= '\ud800' && char2 <= '\udbff') {
                return UCharacterProperty.getRawSupplementary(char2, c);
            }
        }
        return c;
    }
    
    public static int charAt(final StringBuffer sb, int n) {
        if (n < 0 || n >= sb.length()) {
            throw new StringIndexOutOfBoundsException(n);
        }
        final char char1 = sb.charAt(n);
        if (!isSurrogate(char1)) {
            return char1;
        }
        if (char1 <= '\udbff') {
            ++n;
            if (sb.length() != n) {
                final char char2 = sb.charAt(n);
                if (isTrailSurrogate(char2)) {
                    return UCharacterProperty.getRawSupplementary(char1, char2);
                }
            }
        }
        else if (--n >= 0) {
            final char char3 = sb.charAt(n);
            if (isLeadSurrogate(char3)) {
                return UCharacterProperty.getRawSupplementary(char3, char1);
            }
        }
        return char1;
    }
    
    public static int charAt(final char[] array, final int n, final int n2, int n3) {
        n3 += n;
        if (n3 < n || n3 >= n2) {
            throw new ArrayIndexOutOfBoundsException(n3);
        }
        final char c = array[n3];
        if (!isSurrogate(c)) {
            return c;
        }
        if (c <= '\udbff') {
            if (++n3 >= n2) {
                return c;
            }
            final char c2 = array[n3];
            if (isTrailSurrogate(c2)) {
                return UCharacterProperty.getRawSupplementary(c, c2);
            }
        }
        else {
            if (n3 == n) {
                return c;
            }
            --n3;
            final char c3 = array[n3];
            if (isLeadSurrogate(c3)) {
                return UCharacterProperty.getRawSupplementary(c3, c);
            }
        }
        return c;
    }
    
    public static int charAt(final Replaceable replaceable, int n) {
        if (n < 0 || n >= replaceable.length()) {
            throw new StringIndexOutOfBoundsException(n);
        }
        final char char1 = replaceable.charAt(n);
        if (!isSurrogate(char1)) {
            return char1;
        }
        if (char1 <= '\udbff') {
            ++n;
            if (replaceable.length() != n) {
                final char char2 = replaceable.charAt(n);
                if (isTrailSurrogate(char2)) {
                    return UCharacterProperty.getRawSupplementary(char1, char2);
                }
            }
        }
        else if (--n >= 0) {
            final char char3 = replaceable.charAt(n);
            if (isLeadSurrogate(char3)) {
                return UCharacterProperty.getRawSupplementary(char3, char1);
            }
        }
        return char1;
    }
    
    public static int getCharCount(final int n) {
        if (n < 65536) {
            return 1;
        }
        return 2;
    }
    
    public static int bounds(final String s, int n) {
        final char char1 = s.charAt(n);
        if (isSurrogate(char1)) {
            if (isLeadSurrogate(char1)) {
                if (++n < s.length() && isTrailSurrogate(s.charAt(n))) {
                    return 2;
                }
            }
            else if (--n >= 0 && isLeadSurrogate(s.charAt(n))) {
                return 5;
            }
        }
        return 1;
    }
    
    public static int bounds(final StringBuffer sb, int n) {
        final char char1 = sb.charAt(n);
        if (isSurrogate(char1)) {
            if (isLeadSurrogate(char1)) {
                if (++n < sb.length() && isTrailSurrogate(sb.charAt(n))) {
                    return 2;
                }
            }
            else if (--n >= 0 && isLeadSurrogate(sb.charAt(n))) {
                return 5;
            }
        }
        return 1;
    }
    
    public static int bounds(final char[] array, final int n, final int n2, int n3) {
        n3 += n;
        if (n3 < n || n3 >= n2) {
            throw new ArrayIndexOutOfBoundsException(n3);
        }
        final char c = array[n3];
        if (isSurrogate(c)) {
            if (isLeadSurrogate(c)) {
                if (++n3 < n2 && isTrailSurrogate(array[n3])) {
                    return 2;
                }
            }
            else if (--n3 >= n && isLeadSurrogate(array[n3])) {
                return 5;
            }
        }
        return 1;
    }
    
    public static boolean isSurrogate(final char c) {
        return (c & 0xFFFFF800) == 0xD800;
    }
    
    public static boolean isTrailSurrogate(final char c) {
        return (c & 0xFFFFFC00) == 0xDC00;
    }
    
    public static boolean isLeadSurrogate(final char c) {
        return (c & 0xFFFFFC00) == 0xD800;
    }
    
    public static char getLeadSurrogate(final int n) {
        if (n >= 65536) {
            return (char)(55232 + (n >> 10));
        }
        return '\0';
    }
    
    public static char getTrailSurrogate(final int n) {
        if (n >= 65536) {
            return (char)(56320 + (n & 0x3FF));
        }
        return (char)n;
    }
    
    public static String valueOf(final int n) {
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException("Illegal codepoint");
        }
        return toString(n);
    }
    
    public static String valueOf(final String s, final int n) {
        switch (bounds(s, n)) {
            case 2: {
                return s.substring(n, n + 2);
            }
            case 5: {
                return s.substring(n - 1, n + 1);
            }
            default: {
                return s.substring(n, n + 1);
            }
        }
    }
    
    public static String valueOf(final StringBuffer sb, final int n) {
        switch (bounds(sb, n)) {
            case 2: {
                return sb.substring(n, n + 2);
            }
            case 5: {
                return sb.substring(n - 1, n + 1);
            }
            default: {
                return sb.substring(n, n + 1);
            }
        }
    }
    
    public static String valueOf(final char[] array, final int n, final int n2, final int n3) {
        switch (bounds(array, n, n2, n3)) {
            case 2: {
                return new String(array, n + n3, 2);
            }
            case 5: {
                return new String(array, n + n3 - 1, 2);
            }
            default: {
                return new String(array, n + n3, 1);
            }
        }
    }
    
    public static int findOffsetFromCodePoint(final String s, final int n) {
        final int length = s.length();
        int n2 = n;
        if (n < 0 || n > length) {
            throw new StringIndexOutOfBoundsException(n);
        }
        while (0 < length && n2 > 0) {
            int n3 = 0;
            if (isLeadSurrogate(s.charAt(0)) && 1 < length && isTrailSurrogate(s.charAt(1))) {
                ++n3;
            }
            --n2;
            ++n3;
        }
        if (n2 != 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        return 0;
    }
    
    public static int findOffsetFromCodePoint(final StringBuffer sb, final int n) {
        final int length = sb.length();
        int n2 = n;
        if (n < 0 || n > length) {
            throw new StringIndexOutOfBoundsException(n);
        }
        while (0 < length && n2 > 0) {
            int n3 = 0;
            if (isLeadSurrogate(sb.charAt(0)) && 1 < length && isTrailSurrogate(sb.charAt(1))) {
                ++n3;
            }
            --n2;
            ++n3;
        }
        if (n2 != 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        return 0;
    }
    
    public static int findOffsetFromCodePoint(final char[] array, final int n, final int n2, final int n3) {
        int n4 = n;
        int n5 = n3;
        if (n3 > n2 - n) {
            throw new ArrayIndexOutOfBoundsException(n3);
        }
        while (n4 < n2 && n5 > 0) {
            if (isLeadSurrogate(array[n4]) && n4 + 1 < n2 && isTrailSurrogate(array[n4 + 1])) {
                ++n4;
            }
            --n5;
            ++n4;
        }
        if (n5 != 0) {
            throw new ArrayIndexOutOfBoundsException(n3);
        }
        return n4 - n;
    }
    
    public static int findCodePointOffset(final String s, final int n) {
        if (n < 0 || n > s.length()) {
            throw new StringIndexOutOfBoundsException(n);
        }
        int n2 = 0;
        while (0 < n) {
            final char char1 = s.charAt(0);
            if (!false || !isTrailSurrogate(char1)) {
                isLeadSurrogate(char1);
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
        if (n == s.length()) {
            return 0;
        }
        if (false && isTrailSurrogate(s.charAt(n))) {
            --n2;
        }
        return 0;
    }
    
    public static int findCodePointOffset(final StringBuffer sb, final int n) {
        if (n < 0 || n > sb.length()) {
            throw new StringIndexOutOfBoundsException(n);
        }
        int n2 = 0;
        while (0 < n) {
            final char char1 = sb.charAt(0);
            if (!false || !isTrailSurrogate(char1)) {
                isLeadSurrogate(char1);
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
        if (n == sb.length()) {
            return 0;
        }
        if (false && isTrailSurrogate(sb.charAt(n))) {
            --n2;
        }
        return 0;
    }
    
    public static int findCodePointOffset(final char[] array, final int n, final int n2, int n3) {
        n3 += n;
        if (n3 > n2) {
            throw new StringIndexOutOfBoundsException(n3);
        }
        int n4 = 0;
        for (int i = n; i < n3; ++i) {
            final char c = array[i];
            if (!false || !isTrailSurrogate(c)) {
                isLeadSurrogate(c);
                ++n4;
            }
        }
        if (n3 == n2) {
            return 0;
        }
        if (false && isTrailSurrogate(array[n3])) {
            --n4;
        }
        return 0;
    }
    
    public static StringBuffer append(final StringBuffer sb, final int n) {
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException("Illegal codepoint: " + Integer.toHexString(n));
        }
        if (n >= 65536) {
            sb.append(getLeadSurrogate(n));
            sb.append(getTrailSurrogate(n));
        }
        else {
            sb.append((char)n);
        }
        return sb;
    }
    
    public static StringBuffer appendCodePoint(final StringBuffer sb, final int n) {
        return append(sb, n);
    }
    
    public static int append(final char[] array, int n, final int n2) {
        if (n2 < 0 || n2 > 1114111) {
            throw new IllegalArgumentException("Illegal codepoint");
        }
        if (n2 >= 65536) {
            array[n++] = getLeadSurrogate(n2);
            array[n++] = getTrailSurrogate(n2);
        }
        else {
            array[n++] = (char)n2;
        }
        return n;
    }
    
    public static int countCodePoint(final String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        return findCodePointOffset(s, s.length());
    }
    
    public static int countCodePoint(final StringBuffer sb) {
        if (sb == null || sb.length() == 0) {
            return 0;
        }
        return findCodePointOffset(sb, sb.length());
    }
    
    public static int countCodePoint(final char[] array, final int n, final int n2) {
        if (array == null || array.length == 0) {
            return 0;
        }
        return findCodePointOffset(array, n, n2, n2 - n);
    }
    
    public static void setCharAt(final StringBuffer sb, int n, final int n2) {
        final char char1 = sb.charAt(n);
        if (isSurrogate(char1)) {
            if (isLeadSurrogate(char1) && sb.length() > n + 1 && isTrailSurrogate(sb.charAt(n + 1))) {
                int n3 = 0;
                ++n3;
            }
            else if (isTrailSurrogate(char1) && n > 0 && isLeadSurrogate(sb.charAt(n - 1))) {
                --n;
                int n3 = 0;
                ++n3;
            }
        }
        sb.replace(n, n + 1, valueOf(n2));
    }
    
    public static int setCharAt(final char[] array, final int n, int n2, final int n3) {
        if (n2 >= n) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        final char c = array[n2];
        if (isSurrogate(c)) {
            if (isLeadSurrogate(c) && array.length > n2 + 1 && isTrailSurrogate(array[n2 + 1])) {
                int n4 = 0;
                ++n4;
            }
            else if (isTrailSurrogate(c) && n2 > 0 && isLeadSurrogate(array[n2 - 1])) {
                --n2;
                int n4 = 0;
                ++n4;
            }
        }
        final String value = valueOf(n3);
        int n5 = n;
        final int length = value.length();
        array[n2] = value.charAt(0);
        if (length != 0) {
            if (1 == 2) {
                array[n2 + 1] = value.charAt(1);
            }
        }
        else {
            System.arraycopy(array, n2 + 1, array, n2 + length, n - (n2 + 1));
            if (1 < length) {
                array[n2 + 1] = value.charAt(1);
                if (++n5 < array.length) {
                    array[n5] = '\0';
                }
            }
            else {
                --n5;
                array[n5] = '\0';
            }
        }
        return n5;
    }
    
    public static int moveCodePointOffset(final String s, final int n, final int n2) {
        int n3 = n;
        final int length = s.length();
        if (n < 0 || n > length) {
            throw new StringIndexOutOfBoundsException(n);
        }
        int i;
        if (n2 > 0) {
            if (n2 + n > length) {
                throw new StringIndexOutOfBoundsException(n);
            }
            for (i = n2; n3 < length && i > 0; --i, ++n3) {
                if (isLeadSurrogate(s.charAt(n3)) && n3 + 1 < length && isTrailSurrogate(s.charAt(n3 + 1))) {
                    ++n3;
                }
            }
        }
        else {
            if (n + n2 < 0) {
                throw new StringIndexOutOfBoundsException(n);
            }
            for (i = -n2; i > 0; --i) {
                if (--n3 < 0) {
                    break;
                }
                if (isTrailSurrogate(s.charAt(n3)) && n3 > 0 && isLeadSurrogate(s.charAt(n3 - 1))) {
                    --n3;
                }
            }
        }
        if (i != 0) {
            throw new StringIndexOutOfBoundsException(n2);
        }
        return n3;
    }
    
    public static int moveCodePointOffset(final StringBuffer sb, final int n, final int n2) {
        int n3 = n;
        final int length = sb.length();
        if (n < 0 || n > length) {
            throw new StringIndexOutOfBoundsException(n);
        }
        int i;
        if (n2 > 0) {
            if (n2 + n > length) {
                throw new StringIndexOutOfBoundsException(n);
            }
            for (i = n2; n3 < length && i > 0; --i, ++n3) {
                if (isLeadSurrogate(sb.charAt(n3)) && n3 + 1 < length && isTrailSurrogate(sb.charAt(n3 + 1))) {
                    ++n3;
                }
            }
        }
        else {
            if (n + n2 < 0) {
                throw new StringIndexOutOfBoundsException(n);
            }
            for (i = -n2; i > 0; --i) {
                if (--n3 < 0) {
                    break;
                }
                if (isTrailSurrogate(sb.charAt(n3)) && n3 > 0 && isLeadSurrogate(sb.charAt(n3 - 1))) {
                    --n3;
                }
            }
        }
        if (i != 0) {
            throw new StringIndexOutOfBoundsException(n2);
        }
        return n3;
    }
    
    public static int moveCodePointOffset(final char[] array, final int n, final int n2, final int n3, final int n4) {
        final int length = array.length;
        int n5 = n3 + n;
        if (n < 0 || n2 < n) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (n2 > length) {
            throw new StringIndexOutOfBoundsException(n2);
        }
        if (n3 < 0 || n5 > n2) {
            throw new StringIndexOutOfBoundsException(n3);
        }
        int i;
        if (n4 > 0) {
            if (n4 + n5 > length) {
                throw new StringIndexOutOfBoundsException(n5);
            }
            for (i = n4; n5 < n2 && i > 0; --i, ++n5) {
                if (isLeadSurrogate(array[n5]) && n5 + 1 < n2 && isTrailSurrogate(array[n5 + 1])) {
                    ++n5;
                }
            }
        }
        else {
            if (n5 + n4 < n) {
                throw new StringIndexOutOfBoundsException(n5);
            }
            for (i = -n4; i > 0; --i) {
                if (--n5 < n) {
                    break;
                }
                if (isTrailSurrogate(array[n5]) && n5 > n && isLeadSurrogate(array[n5 - 1])) {
                    --n5;
                }
            }
        }
        if (i != 0) {
            throw new StringIndexOutOfBoundsException(n4);
        }
        return n5 - n;
    }
    
    public static StringBuffer insert(final StringBuffer sb, int n, final int n2) {
        final String value = valueOf(n2);
        if (n != sb.length() && bounds(sb, n) == 5) {
            ++n;
        }
        sb.insert(n, value);
        return sb;
    }
    
    public static int insert(final char[] array, final int n, int n2, final int n3) {
        final String value = valueOf(n3);
        if (n2 != n && bounds(array, 0, n, n2) == 5) {
            ++n2;
        }
        final int length = value.length();
        if (n + length > array.length) {
            throw new ArrayIndexOutOfBoundsException(n2 + length);
        }
        System.arraycopy(array, n2, array, n2 + length, n - n2);
        array[n2] = value.charAt(0);
        if (length == 2) {
            array[n2 + 1] = value.charAt(1);
        }
        return n + length;
    }
    
    public static StringBuffer delete(final StringBuffer sb, int n) {
        switch (bounds(sb, n)) {
            case 2: {
                int n2 = 0;
                ++n2;
                break;
            }
            case 5: {
                int n2 = 0;
                ++n2;
                --n;
                break;
            }
        }
        sb.delete(n, n + 1);
        return sb;
    }
    
    public static int delete(final char[] array, final int n, int n2) {
        switch (bounds(array, 0, n, n2)) {
            case 2: {
                int n3 = 0;
                ++n3;
                break;
            }
            case 5: {
                int n3 = 0;
                ++n3;
                --n2;
                break;
            }
        }
        System.arraycopy(array, n2 + 1, array, n2, n - (n2 + 1));
        array[n - 1] = '\0';
        return n - 1;
    }
    
    public static int indexOf(final String s, final int n) {
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException("Argument char32 is not a valid codepoint");
        }
        if (n < 55296 || (n > 57343 && n < 65536)) {
            return s.indexOf((char)n);
        }
        if (n < 65536) {
            final int index = s.indexOf((char)n);
            if (index >= 0) {
                if (isLeadSurrogate((char)n) && index < s.length() - 1 && isTrailSurrogate(s.charAt(index + 1))) {
                    return indexOf(s, n, index + 1);
                }
                if (index > 0 && isLeadSurrogate(s.charAt(index - 1))) {
                    return indexOf(s, n, index + 1);
                }
            }
            return index;
        }
        return s.indexOf(toString(n));
    }
    
    public static int indexOf(final String s, final String s2) {
        final int length = s2.length();
        if (!isTrailSurrogate(s2.charAt(0)) && !isLeadSurrogate(s2.charAt(length - 1))) {
            return s.indexOf(s2);
        }
        final int index = s.indexOf(s2);
        final int n = index + length;
        if (index >= 0) {
            if (isLeadSurrogate(s2.charAt(length - 1)) && index < s.length() - 1 && isTrailSurrogate(s.charAt(n + 1))) {
                return indexOf(s, s2, n + 1);
            }
            if (isTrailSurrogate(s2.charAt(0)) && index > 0 && isLeadSurrogate(s.charAt(index - 1))) {
                return indexOf(s, s2, n + 1);
            }
        }
        return index;
    }
    
    public static int indexOf(final String s, final int n, final int n2) {
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException("Argument char32 is not a valid codepoint");
        }
        if (n < 55296 || (n > 57343 && n < 65536)) {
            return s.indexOf((char)n, n2);
        }
        if (n < 65536) {
            final int index = s.indexOf((char)n, n2);
            if (index >= 0) {
                if (isLeadSurrogate((char)n) && index < s.length() - 1 && isTrailSurrogate(s.charAt(index + 1))) {
                    return indexOf(s, n, index + 1);
                }
                if (index > 0 && isLeadSurrogate(s.charAt(index - 1))) {
                    return indexOf(s, n, index + 1);
                }
            }
            return index;
        }
        return s.indexOf(toString(n), n2);
    }
    
    public static int indexOf(final String s, final String s2, final int n) {
        final int length = s2.length();
        if (!isTrailSurrogate(s2.charAt(0)) && !isLeadSurrogate(s2.charAt(length - 1))) {
            return s.indexOf(s2, n);
        }
        final int index = s.indexOf(s2, n);
        final int n2 = index + length;
        if (index >= 0) {
            if (isLeadSurrogate(s2.charAt(length - 1)) && index < s.length() - 1 && isTrailSurrogate(s.charAt(n2))) {
                return indexOf(s, s2, n2 + 1);
            }
            if (isTrailSurrogate(s2.charAt(0)) && index > 0 && isLeadSurrogate(s.charAt(index - 1))) {
                return indexOf(s, s2, n2 + 1);
            }
        }
        return index;
    }
    
    public static int lastIndexOf(final String s, final int n) {
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException("Argument char32 is not a valid codepoint");
        }
        if (n < 55296 || (n > 57343 && n < 65536)) {
            return s.lastIndexOf((char)n);
        }
        if (n < 65536) {
            final int lastIndex = s.lastIndexOf((char)n);
            if (lastIndex >= 0) {
                if (isLeadSurrogate((char)n) && lastIndex < s.length() - 1 && isTrailSurrogate(s.charAt(lastIndex + 1))) {
                    return lastIndexOf(s, n, lastIndex - 1);
                }
                if (lastIndex > 0 && isLeadSurrogate(s.charAt(lastIndex - 1))) {
                    return lastIndexOf(s, n, lastIndex - 1);
                }
            }
            return lastIndex;
        }
        return s.lastIndexOf(toString(n));
    }
    
    public static int lastIndexOf(final String s, final String s2) {
        final int length = s2.length();
        if (!isTrailSurrogate(s2.charAt(0)) && !isLeadSurrogate(s2.charAt(length - 1))) {
            return s.lastIndexOf(s2);
        }
        final int lastIndex = s.lastIndexOf(s2);
        if (lastIndex >= 0) {
            if (isLeadSurrogate(s2.charAt(length - 1)) && lastIndex < s.length() - 1 && isTrailSurrogate(s.charAt(lastIndex + length + 1))) {
                return lastIndexOf(s, s2, lastIndex - 1);
            }
            if (isTrailSurrogate(s2.charAt(0)) && lastIndex > 0 && isLeadSurrogate(s.charAt(lastIndex - 1))) {
                return lastIndexOf(s, s2, lastIndex - 1);
            }
        }
        return lastIndex;
    }
    
    public static int lastIndexOf(final String s, final int n, final int n2) {
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException("Argument char32 is not a valid codepoint");
        }
        if (n < 55296 || (n > 57343 && n < 65536)) {
            return s.lastIndexOf((char)n, n2);
        }
        if (n < 65536) {
            final int lastIndex = s.lastIndexOf((char)n, n2);
            if (lastIndex >= 0) {
                if (isLeadSurrogate((char)n) && lastIndex < s.length() - 1 && isTrailSurrogate(s.charAt(lastIndex + 1))) {
                    return lastIndexOf(s, n, lastIndex - 1);
                }
                if (lastIndex > 0 && isLeadSurrogate(s.charAt(lastIndex - 1))) {
                    return lastIndexOf(s, n, lastIndex - 1);
                }
            }
            return lastIndex;
        }
        return s.lastIndexOf(toString(n), n2);
    }
    
    public static int lastIndexOf(final String s, final String s2, final int n) {
        final int length = s2.length();
        if (!isTrailSurrogate(s2.charAt(0)) && !isLeadSurrogate(s2.charAt(length - 1))) {
            return s.lastIndexOf(s2, n);
        }
        final int lastIndex = s.lastIndexOf(s2, n);
        if (lastIndex >= 0) {
            if (isLeadSurrogate(s2.charAt(length - 1)) && lastIndex < s.length() - 1 && isTrailSurrogate(s.charAt(lastIndex + length))) {
                return lastIndexOf(s, s2, lastIndex - 1);
            }
            if (isTrailSurrogate(s2.charAt(0)) && lastIndex > 0 && isLeadSurrogate(s.charAt(lastIndex - 1))) {
                return lastIndexOf(s, s2, lastIndex - 1);
            }
        }
        return lastIndex;
    }
    
    public static String replace(final String s, final int n, final int n2) {
        if (n <= 0 || n > 1114111) {
            throw new IllegalArgumentException("Argument oldChar32 is not a valid codepoint");
        }
        if (n2 <= 0 || n2 > 1114111) {
            throw new IllegalArgumentException("Argument newChar32 is not a valid codepoint");
        }
        int i = indexOf(s, n);
        if (i == -1) {
            return s;
        }
        final String string = toString(n2);
        final int length = string.length();
        final StringBuffer sb = new StringBuffer(s);
        int n3 = i;
        if (n >= 65536) {}
        while (i != -1) {
            sb.replace(n3, n3 + 2, string);
            final int n4 = i + 2;
            i = indexOf(s, n, n4);
            n3 += length + i - n4;
        }
        return sb.toString();
    }
    
    public static String replace(final String s, final String s2, final String s3) {
        int i = indexOf(s, s2);
        if (i == -1) {
            return s;
        }
        final int length = s2.length();
        final int length2 = s3.length();
        final StringBuffer sb = new StringBuffer(s);
        int n2;
        for (int n = i; i != -1; i = indexOf(s, s2, n2), n += length2 + i - n2) {
            sb.replace(n, n + length, s3);
            n2 = i + length;
        }
        return sb.toString();
    }
    
    public static StringBuffer reverse(final StringBuffer sb) {
        final int length = sb.length();
        final StringBuffer sb2 = new StringBuffer(length);
        int n = length;
        while (n-- > 0) {
            final char char1 = sb.charAt(n);
            if (isTrailSurrogate(char1) && n > 0) {
                final char char2 = sb.charAt(n - 1);
                if (isLeadSurrogate(char2)) {
                    sb2.append(char2);
                    sb2.append(char1);
                    --n;
                    continue;
                }
            }
            sb2.append(char1);
        }
        return sb2;
    }
    
    public static boolean hasMoreCodePointsThan(final String s, int n) {
        if (n < 0) {
            return true;
        }
        if (s == null) {
            return false;
        }
        final int i = s.length();
        if (i + 1 >> 1 > n) {
            return true;
        }
        int n2 = i - n;
        if (n2 <= 0) {
            return false;
        }
        while (i != 0) {
            if (n == 0) {
                return true;
            }
            final int n3 = 0;
            int n4 = 0;
            ++n4;
            if (isLeadSurrogate(s.charAt(n3)) && i && isTrailSurrogate(s.charAt(0))) {
                ++n4;
                if (--n2 <= 0) {
                    return false;
                }
            }
            --n;
        }
        return false;
    }
    
    public static boolean hasMoreCodePointsThan(final char[] array, int n, final int n2, int n3) {
        final int i = n2 - n;
        if (i < 0 || n < 0 || n2 < 0) {
            throw new IndexOutOfBoundsException("Start and limit indexes should be non-negative and start <= limit");
        }
        if (n3 < 0) {
            return true;
        }
        if (array == null) {
            return false;
        }
        if (i + 1 >> 1 > n3) {
            return true;
        }
        int n4 = i - n3;
        if (n4 <= 0) {
            return false;
        }
        while (i != 0) {
            if (n3 == 0) {
                return true;
            }
            if (isLeadSurrogate(array[n++]) && n != n2 && isTrailSurrogate(array[n])) {
                ++n;
                if (--n4 <= 0) {
                    return false;
                }
            }
            --n3;
        }
        return false;
    }
    
    public static boolean hasMoreCodePointsThan(final StringBuffer sb, int n) {
        if (n < 0) {
            return true;
        }
        if (sb == null) {
            return false;
        }
        final int i = sb.length();
        if (i + 1 >> 1 > n) {
            return true;
        }
        int n2 = i - n;
        if (n2 <= 0) {
            return false;
        }
        while (i != 0) {
            if (n == 0) {
                return true;
            }
            final int n3 = 0;
            int n4 = 0;
            ++n4;
            if (isLeadSurrogate(sb.charAt(n3)) && i && isTrailSurrogate(sb.charAt(0))) {
                ++n4;
                if (--n2 <= 0) {
                    return false;
                }
            }
            --n;
        }
        return false;
    }
    
    public static String newString(final int[] array, final int n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        final char[] array2 = new char[n2];
        for (int i = n; i < n + n2; ++i) {
            final int n3 = array[i];
            if (n3 < 0 || n3 > 1114111) {
                throw new IllegalArgumentException();
            }
            if (n3 < 65536) {
                array2[0] = (char)n3;
                int n4 = 0;
                ++n4;
            }
            else {
                array2[0] = (char)(55232 + (n3 >> 10));
                array2[1] = (char)(56320 + (n3 & 0x3FF));
                final int n4;
                n4 += 2;
            }
        }
        return new String(array2, 0, 0);
    }
    
    private static String toString(final int n) {
        if (n < 65536) {
            return String.valueOf((char)n);
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(getLeadSurrogate(n));
        sb.append(getTrailSurrogate(n));
        return sb.toString();
    }
    
    public static final class StringComparator implements Comparator
    {
        public static final int FOLD_CASE_DEFAULT = 0;
        public static final int FOLD_CASE_EXCLUDE_SPECIAL_I = 1;
        private int m_codePointCompare_;
        private int m_foldCase_;
        private boolean m_ignoreCase_;
        private static final int CODE_POINT_COMPARE_SURROGATE_OFFSET_ = 10240;
        
        public StringComparator() {
            this(false, false, 0);
        }
        
        public StringComparator(final boolean codePointCompare, final boolean ignoreCase_, final int foldCase_) {
            this.setCodePointCompare(codePointCompare);
            this.m_ignoreCase_ = ignoreCase_;
            if (foldCase_ < 0 || foldCase_ > 1) {
                throw new IllegalArgumentException("Invalid fold case option");
            }
            this.m_foldCase_ = foldCase_;
        }
        
        public void setCodePointCompare(final boolean b) {
            if (b) {
                this.m_codePointCompare_ = 32768;
            }
            else {
                this.m_codePointCompare_ = 0;
            }
        }
        
        public void setIgnoreCase(final boolean ignoreCase_, final int foldCase_) {
            this.m_ignoreCase_ = ignoreCase_;
            if (foldCase_ < 0 || foldCase_ > 1) {
                throw new IllegalArgumentException("Invalid fold case option");
            }
            this.m_foldCase_ = foldCase_;
        }
        
        public boolean getCodePointCompare() {
            return this.m_codePointCompare_ == 32768;
        }
        
        public boolean getIgnoreCase() {
            return this.m_ignoreCase_;
        }
        
        public int getIgnoreCaseOption() {
            return this.m_foldCase_;
        }
        
        public int compare(final String s, final String s2) {
            if (s == s2) {
                return 0;
            }
            if (s == null) {
                return -1;
            }
            if (s2 == null) {
                return 1;
            }
            if (this.m_ignoreCase_) {
                return this.compareCaseInsensitive(s, s2);
            }
            return this.compareCaseSensitive(s, s2);
        }
        
        private int compareCaseInsensitive(final String s, final String s2) {
            return Normalizer.cmpEquivFold(s, s2, this.m_foldCase_ | this.m_codePointCompare_ | 0x10000);
        }
        
        private int compareCaseSensitive(final String s, final String s2) {
            final int length = s.length();
            final int length2 = s2.length();
            int n = length;
            if (length >= length2) {
                if (length > length2) {
                    n = length2;
                }
            }
            while (0 < n) {
                s.charAt(0);
                s2.charAt(0);
                if (false) {
                    break;
                }
                int n2 = 0;
                ++n2;
            }
            if (n == 0) {
                return 1;
            }
            final boolean b = this.m_codePointCompare_ == 32768;
            if (0 >= 55296 && 0 >= 55296 && b) {
                if (0 > 56319 || length || !UTF16.isTrailSurrogate(s.charAt(1))) {
                    if (!UTF16.isTrailSurrogate('\0') || !false || !UTF16.isLeadSurrogate(s.charAt(-1))) {
                        final char c = (char)(-10240);
                    }
                }
                if (0 > 56319 || length2 || !UTF16.isTrailSurrogate(s2.charAt(1))) {
                    if (!UTF16.isTrailSurrogate('\0') || !false || !UTF16.isLeadSurrogate(s2.charAt(-1))) {
                        final char c2 = (char)(-10240);
                    }
                }
            }
            return 0;
        }
        
        public int compare(final Object o, final Object o2) {
            return this.compare((String)o, (String)o2);
        }
    }
}
