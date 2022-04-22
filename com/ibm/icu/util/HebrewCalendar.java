package com.ibm.icu.util;

import com.ibm.icu.impl.*;
import java.util.*;

public class HebrewCalendar extends Calendar
{
    private static final long serialVersionUID = -1952524560588825816L;
    public static final int TISHRI = 0;
    public static final int HESHVAN = 1;
    public static final int KISLEV = 2;
    public static final int TEVET = 3;
    public static final int SHEVAT = 4;
    public static final int ADAR_1 = 5;
    public static final int ADAR = 6;
    public static final int NISAN = 7;
    public static final int IYAR = 8;
    public static final int SIVAN = 9;
    public static final int TAMUZ = 10;
    public static final int AV = 11;
    public static final int ELUL = 12;
    private static final int[][] LIMITS;
    private static final int[][] MONTH_LENGTH;
    private static final int[][] MONTH_START;
    private static final int[][] LEAP_MONTH_START;
    private static CalendarCache cache;
    private static final long HOUR_PARTS = 1080L;
    private static final long DAY_PARTS = 25920L;
    private static final int MONTH_DAYS = 29;
    private static final long MONTH_FRACT = 13753L;
    private static final long MONTH_PARTS = 765433L;
    private static final long BAHARAD = 12084L;
    
    public HebrewCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public HebrewCalendar(final TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public HebrewCalendar(final Locale locale) {
        this(TimeZone.getDefault(), locale);
    }
    
    public HebrewCalendar(final ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }
    
    public HebrewCalendar(final TimeZone timeZone, final Locale locale) {
        super(timeZone, locale);
        this.setTimeInMillis(System.currentTimeMillis());
    }
    
    public HebrewCalendar(final TimeZone timeZone, final ULocale uLocale) {
        super(timeZone, uLocale);
        this.setTimeInMillis(System.currentTimeMillis());
    }
    
    public HebrewCalendar(final int n, final int n2, final int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }
    
    public HebrewCalendar(final Date time) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.setTime(time);
    }
    
    public HebrewCalendar(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }
    
    @Override
    public void add(final int n, final int n2) {
        switch (n) {
            case 2: {
                final int value = this.get(2);
                int value2 = this.get(1);
                int n3;
                if (n2 > 0) {
                    final boolean b = value < 5;
                    n3 = value + n2;
                    while (true) {
                        if (true && n3 >= 5 && !isLeapYear(value2)) {
                            ++n3;
                        }
                        if (n3 <= 12) {
                            break;
                        }
                        n3 -= 13;
                        ++value2;
                    }
                }
                else {
                    final boolean b2 = value > 5;
                    n3 = value + n2;
                    while (true) {
                        if (true && n3 <= 5 && !isLeapYear(value2)) {
                            --n3;
                        }
                        if (n3 >= 0) {
                            break;
                        }
                        n3 += 13;
                        --value2;
                    }
                }
                this.set(2, n3);
                this.set(1, value2);
                this.pinField(5);
                break;
            }
            default: {
                super.add(n, n2);
                break;
            }
        }
    }
    
    @Override
    public void roll(final int n, final int n2) {
        switch (n) {
            case 2: {
                final int value = this.get(2);
                final int value2 = this.get(1);
                final boolean leapYear = isLeapYear(value2);
                int n3 = value + n2 % monthsInYear(value2);
                if (!leapYear) {
                    if (n2 > 0 && value < 5 && n3 >= 5) {
                        ++n3;
                    }
                    else if (n2 < 0 && value > 5 && n3 <= 5) {
                        --n3;
                    }
                }
                this.set(2, (n3 + 13) % 13);
                this.pinField(5);
            }
            default: {
                super.roll(n, n2);
            }
        }
    }
    
    private static long startOfYear(final int n) {
        long value = HebrewCalendar.cache.get(n);
        if (value == CalendarCache.EMPTY) {
            final int n2 = (235 * n - 234) / 19;
            final long n3 = n2 * 13753L + 12084L;
            value = n2 * 29 + n3 / 25920L;
            final long n4 = n3 % 25920L;
            int n5 = (int)(value % 7L);
            if (n5 == 2 || n5 == 4 || n5 == 6) {
                ++value;
                n5 = (int)(value % 7L);
            }
            if (n5 == 1 && n4 > 16404L && !isLeapYear(n)) {
                value += 2L;
            }
            else if (n5 == 0 && n4 > 23269L && isLeapYear(n - 1)) {
                ++value;
            }
            HebrewCalendar.cache.put(n, value);
        }
        return value;
    }
    
    private final int yearType(final int n) {
        int handleGetYearLength = this.handleGetYearLength(n);
        if (handleGetYearLength > 380) {
            handleGetYearLength -= 30;
        }
        switch (handleGetYearLength) {
            case 353: {
                break;
            }
            case 354: {
                break;
            }
            case 355: {
                break;
            }
            default: {
                throw new IllegalArgumentException("Illegal year length " + handleGetYearLength + " in year " + n);
            }
        }
        return 2;
    }
    
    @Deprecated
    public static boolean isLeapYear(final int n) {
        final int n2 = (n * 12 + 17) % 19;
        return n2 >= ((n2 < 0) ? -7 : 12);
    }
    
    private static int monthsInYear(final int n) {
        return isLeapYear(n) ? 13 : 12;
    }
    
    @Override
    protected int handleGetLimit(final int n, final int n2) {
        return HebrewCalendar.LIMITS[n][n2];
    }
    
    @Override
    protected int handleGetMonthLength(int n, int i) {
        while (i < 0) {
            i += monthsInYear(--n);
        }
        while (i > 12) {
            i -= monthsInYear(n++);
        }
        switch (i) {
            case 1:
            case 2: {
                return HebrewCalendar.MONTH_LENGTH[i][this.yearType(n)];
            }
            default: {
                return HebrewCalendar.MONTH_LENGTH[i][0];
            }
        }
    }
    
    @Override
    protected int handleGetYearLength(final int n) {
        return (int)(startOfYear(n + 1) - startOfYear(n));
    }
    
    @Override
    protected void handleComputeFields(final int n) {
        long n2;
        int n3;
        int i;
        for (n2 = n - 347997, n3 = (int)((19L * (n2 * 25920L / 765433L) + 234L) / 235L) + 1, i = (int)(n2 - startOfYear(n3)); i < 1; i = (int)(n2 - startOfYear(--n3))) {}
        final int yearType = this.yearType(n3);
        final int[][] array = isLeapYear(n3) ? HebrewCalendar.LEAP_MONTH_START : HebrewCalendar.MONTH_START;
        int n4 = 0;
        while (i > array[0][yearType]) {
            ++n4;
        }
        --n4;
        final int n5 = i - array[0][yearType];
        this.internalSet(0, 0);
        this.internalSet(1, n3);
        this.internalSet(19, n3);
        this.internalSet(2, 0);
        this.internalSet(5, n5);
        this.internalSet(6, i);
    }
    
    @Override
    protected int handleGetExtendedYear() {
        int n;
        if (this.newerField(19, 1) == 19) {
            n = this.internalGet(19, 1);
        }
        else {
            n = this.internalGet(1, 1);
        }
        return n;
    }
    
    @Override
    protected int handleComputeMonthStart(int n, int i, final boolean b) {
        while (i < 0) {
            i += monthsInYear(--n);
        }
        while (i > 12) {
            i -= monthsInYear(n++);
        }
        long startOfYear = startOfYear(n);
        if (i != 0) {
            if (isLeapYear(n)) {
                startOfYear += HebrewCalendar.LEAP_MONTH_START[i][this.yearType(n)];
            }
            else {
                startOfYear += HebrewCalendar.MONTH_START[i][this.yearType(n)];
            }
        }
        return (int)(startOfYear + 347997L);
    }
    
    @Override
    public String getType() {
        return "hebrew";
    }
    
    static {
        LIMITS = new int[][] { { 0, 0, 0, 0 }, { -5000000, -5000000, 5000000, 5000000 }, { 0, 0, 12, 12 }, { 1, 1, 51, 56 }, new int[0], { 1, 1, 29, 30 }, { 1, 1, 353, 385 }, new int[0], { -1, -1, 5, 5 }, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], { -5000000, -5000000, 5000000, 5000000 }, new int[0], { -5000000, -5000000, 5000000, 5000000 }, new int[0], new int[0] };
        MONTH_LENGTH = new int[][] { { 30, 30, 30 }, { 29, 29, 30 }, { 29, 30, 30 }, { 29, 29, 29 }, { 30, 30, 30 }, { 30, 30, 30 }, { 29, 29, 29 }, { 30, 30, 30 }, { 29, 29, 29 }, { 30, 30, 30 }, { 29, 29, 29 }, { 30, 30, 30 }, { 29, 29, 29 } };
        MONTH_START = new int[][] { { 0, 0, 0 }, { 30, 30, 30 }, { 59, 59, 60 }, { 88, 89, 90 }, { 117, 118, 119 }, { 147, 148, 149 }, { 147, 148, 149 }, { 176, 177, 178 }, { 206, 207, 208 }, { 235, 236, 237 }, { 265, 266, 267 }, { 294, 295, 296 }, { 324, 325, 326 }, { 353, 354, 355 } };
        LEAP_MONTH_START = new int[][] { { 0, 0, 0 }, { 30, 30, 30 }, { 59, 59, 60 }, { 88, 89, 90 }, { 117, 118, 119 }, { 147, 148, 149 }, { 177, 178, 179 }, { 206, 207, 208 }, { 236, 237, 238 }, { 265, 266, 267 }, { 295, 296, 297 }, { 324, 325, 326 }, { 354, 355, 356 }, { 383, 384, 385 } };
        HebrewCalendar.cache = new CalendarCache();
    }
}
