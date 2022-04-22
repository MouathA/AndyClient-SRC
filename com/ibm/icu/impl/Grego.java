package com.ibm.icu.impl;

public class Grego
{
    public static final long MIN_MILLIS = -184303902528000000L;
    public static final long MAX_MILLIS = 183882168921600000L;
    public static final int MILLIS_PER_SECOND = 1000;
    public static final int MILLIS_PER_MINUTE = 60000;
    public static final int MILLIS_PER_HOUR = 3600000;
    public static final int MILLIS_PER_DAY = 86400000;
    private static final int JULIAN_1_CE = 1721426;
    private static final int JULIAN_1970_CE = 2440588;
    private static final int[] MONTH_LENGTH;
    private static final int[] DAYS_BEFORE;
    
    public static final boolean isLeapYear(final int n) {
        return (n & 0x3) == 0x0 && (n % 100 != 0 || n % 400 == 0);
    }
    
    public static final int monthLength(final int n, final int n2) {
        return Grego.MONTH_LENGTH[n2 + (isLeapYear(n) ? 12 : 0)];
    }
    
    public static final int previousMonthLength(final int n, final int n2) {
        return (n2 > 0) ? monthLength(n, n2 - 1) : 31;
    }
    
    public static long fieldsToDay(final int n, final int n2, final int n3) {
        final int n4 = n - 1;
        return 365 * n4 + floorDivide(n4, 4L) + 1721423L + floorDivide(n4, 400L) - floorDivide(n4, 100L) + 2L + Grego.DAYS_BEFORE[n2 + (isLeapYear(n) ? 12 : 0)] + n3 - 2440588L;
    }
    
    public static int dayOfWeek(final long n) {
        final long[] array = { 0L };
        floorDivide(n + 5L, 7L, array);
        final int n2 = (int)array[0];
        return (n2 == 0) ? 7 : n2;
    }
    
    public static int[] dayToFields(long n, int[] array) {
        if (array == null || array.length < 5) {
            array = new int[5];
        }
        n += 719162L;
        final long[] array2 = { 0L };
        final long floorDivide = floorDivide(n, 146097L, array2);
        final long floorDivide2 = floorDivide(array2[0], 36524L, array2);
        final long floorDivide3 = floorDivide(array2[0], 1461L, array2);
        final long floorDivide4 = floorDivide(array2[0], 365L, array2);
        int n2 = (int)(400L * floorDivide + 100L * floorDivide2 + 4L * floorDivide3 + floorDivide4);
        int n3 = (int)array2[0];
        if (floorDivide2 == 4L || floorDivide4 == 4L) {
            n3 = 365;
        }
        else {
            ++n2;
        }
        final boolean leapYear = isLeapYear(n2);
        int n4 = 0;
        if (n3 >= (leapYear ? 60 : 59)) {
            n4 = (leapYear ? 1 : 2);
        }
        final int n5 = (12 * (n3 + n4) + 6) / 367;
        final int n6 = n3 - Grego.DAYS_BEFORE[leapYear ? (n5 + 12) : n5] + 1;
        int n7 = (int)((n + 2L) % 7L);
        if (n7 < 1) {
            n7 += 7;
        }
        ++n3;
        array[0] = n2;
        array[1] = n5;
        array[2] = n6;
        array[3] = n7;
        array[4] = n3;
        return array;
    }
    
    public static int[] timeToFields(final long n, int[] array) {
        if (array == null || array.length < 6) {
            array = new int[6];
        }
        final long[] array2 = { 0L };
        dayToFields(floorDivide(n, 86400000L, array2), array);
        array[5] = (int)array2[0];
        return array;
    }
    
    public static long floorDivide(final long n, final long n2) {
        return (n >= 0L) ? (n / n2) : ((n + 1L) / n2 - 1L);
    }
    
    private static long floorDivide(final long n, final long n2, final long[] array) {
        if (n >= 0L) {
            array[0] = n % n2;
            return n / n2;
        }
        final long n3 = (n + 1L) / n2 - 1L;
        array[0] = n - n3 * n2;
        return n3;
    }
    
    public static int getDayOfWeekInMonth(final int n, final int n2, final int n3) {
        int n4 = (n3 + 6) / 7;
        if (n4 == 4) {
            if (n3 + 7 > monthLength(n, n2)) {
                n4 = -1;
            }
        }
        else if (n4 == 5) {
            n4 = -1;
        }
        return n4;
    }
    
    static {
        MONTH_LENGTH = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        DAYS_BEFORE = new int[] { 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335 };
    }
}
