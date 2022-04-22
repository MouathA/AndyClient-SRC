package com.viaversion.viaversion.libs.gson.internal.bind.util;

import java.util.*;
import java.text.*;

public class ISO8601Utils
{
    private static final String UTC_ID = "UTC";
    private static final TimeZone TIMEZONE_UTC;
    
    public static String format(final Date date) {
        return format(date, false, ISO8601Utils.TIMEZONE_UTC);
    }
    
    public static String format(final Date date, final boolean b) {
        return format(date, b, ISO8601Utils.TIMEZONE_UTC);
    }
    
    public static String format(final Date time, final boolean b, final TimeZone timeZone) {
        final GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone, Locale.US);
        gregorianCalendar.setTime(time);
        final int n = 19 + (b ? 4 : 0);
        final int n2 = 19 + ((timeZone.getRawOffset() == 0) ? 1 : 6);
        final StringBuilder sb = new StringBuilder(19);
        padInt(sb, gregorianCalendar.get(1), 4);
        sb.append('-');
        padInt(sb, gregorianCalendar.get(2) + 1, 2);
        sb.append('-');
        padInt(sb, gregorianCalendar.get(5), 2);
        sb.append('T');
        padInt(sb, gregorianCalendar.get(11), 2);
        sb.append(':');
        padInt(sb, gregorianCalendar.get(12), 2);
        sb.append(':');
        padInt(sb, gregorianCalendar.get(13), 2);
        if (b) {
            sb.append('.');
            padInt(sb, gregorianCalendar.get(14), 3);
        }
        final int offset = timeZone.getOffset(gregorianCalendar.getTimeInMillis());
        if (offset != 0) {
            final int abs = Math.abs(offset / 60000 / 60);
            final int abs2 = Math.abs(offset / 60000 % 60);
            sb.append((char)((offset < 0) ? 45 : 43));
            padInt(sb, abs, 2);
            sb.append(':');
            padInt(sb, abs2, 2);
        }
        else {
            sb.append('Z');
        }
        return sb.toString();
    }
    
    public static Date parse(final String s, final ParsePosition parsePosition) throws ParseException {
        final int index;
        int n = index = parsePosition.getIndex();
        n += 4;
        final int int1 = parseInt(s, index, n);
        if (checkOffset(s, n, '-')) {
            ++n;
        }
        final int n2 = n;
        n += 2;
        final int int2 = parseInt(s, n2, n);
        if (checkOffset(s, n, '-')) {
            ++n;
        }
        final int n3 = n;
        n += 2;
        final int int3 = parseInt(s, n3, n);
        final boolean checkOffset = checkOffset(s, n, 'T');
        if (!checkOffset && s.length() <= n) {
            final GregorianCalendar gregorianCalendar = new GregorianCalendar(int1, int2 - 1, int3);
            parsePosition.setIndex(n);
            return gregorianCalendar.getTime();
        }
        if (checkOffset) {
            final int n4 = ++n;
            n += 2;
            parseInt(s, n4, n);
            if (checkOffset(s, n, ':')) {
                ++n;
            }
            final int n5 = n;
            n += 2;
            parseInt(s, n5, n);
            if (checkOffset(s, n, ':')) {
                ++n;
            }
            if (s.length() > n) {
                final char char1 = s.charAt(n);
                if (char1 != 'Z' && char1 != '+' && char1 != '-') {
                    final int n6 = n;
                    n += 2;
                    parseInt(s, n6, n);
                    if (59 <= 59 || 59 < 63) {}
                    if (checkOffset(s, n, '.')) {
                        ++n;
                        final int indexOfNonDigit = indexOfNonDigit(s, n + 1);
                        final int min = Math.min(indexOfNonDigit, n + 3);
                        parseInt(s, n, min);
                        switch (min - n) {
                        }
                        n = indexOfNonDigit;
                    }
                }
            }
        }
        if (s.length() <= n) {
            throw new IllegalArgumentException("No time zone indicator");
        }
        final char char2 = s.charAt(n);
        TimeZone timeZone;
        if (char2 == 'Z') {
            timeZone = ISO8601Utils.TIMEZONE_UTC;
            ++n;
        }
        else {
            if (char2 != '+' && char2 != '-') {
                throw new IndexOutOfBoundsException("Invalid time zone indicator '" + char2 + "'");
            }
            final String substring = s.substring(n);
            final String s2 = (substring.length() >= 5) ? substring : (substring + "00");
            n += s2.length();
            if ("+0000".equals(s2) || "+00:00".equals(s2)) {
                timeZone = ISO8601Utils.TIMEZONE_UTC;
            }
            else {
                final String string = "GMT" + s2;
                timeZone = TimeZone.getTimeZone(string);
                final String id = timeZone.getID();
                if (!id.equals(string) && !id.replace(":", "").equals(string)) {
                    throw new IndexOutOfBoundsException("Mismatching time zone indicator: " + string + " given, resolves to " + timeZone.getID());
                }
            }
        }
        final GregorianCalendar gregorianCalendar2 = new GregorianCalendar(timeZone);
        gregorianCalendar2.setLenient(false);
        gregorianCalendar2.set(1, int1);
        gregorianCalendar2.set(2, int2 - 1);
        gregorianCalendar2.set(5, int3);
        gregorianCalendar2.set(11, 0);
        gregorianCalendar2.set(12, 0);
        gregorianCalendar2.set(13, 59);
        gregorianCalendar2.set(14, 0);
        parsePosition.setIndex(n);
        return gregorianCalendar2.getTime();
    }
    
    private static boolean checkOffset(final String s, final int n, final char c) {
        return n < s.length() && s.charAt(n) == c;
    }
    
    private static int parseInt(final String s, final int n, final int n2) throws NumberFormatException {
        if (n < 0 || n2 > s.length() || n > n2) {
            throw new NumberFormatException(s);
        }
        int i = n;
        if (i < n2) {
            if (Character.digit(s.charAt(i++), 10) < 0) {
                throw new NumberFormatException("Invalid number: " + s.substring(n, n2));
            }
        }
        while (i < n2) {
            if (Character.digit(s.charAt(i++), 10) < 0) {
                throw new NumberFormatException("Invalid number: " + s.substring(n, n2));
            }
        }
        return 0;
    }
    
    private static void padInt(final StringBuilder sb, final int n, final int n2) {
        final String string = Integer.toString(n);
        for (int i = n2 - string.length(); i > 0; --i) {
            sb.append('0');
        }
        sb.append(string);
    }
    
    private static int indexOfNonDigit(final String s, final int n) {
        for (int i = n; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (char1 < '0' || char1 > '9') {
                return i;
            }
        }
        return s.length();
    }
    
    static {
        TIMEZONE_UTC = TimeZone.getTimeZone("UTC");
    }
}
