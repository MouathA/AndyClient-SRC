package com.ibm.icu.util;

import java.util.*;

public class PersianCalendar extends Calendar
{
    private static final long serialVersionUID = -6727306982975111643L;
    private static final int[][] MONTH_COUNT;
    private static final int PERSIAN_EPOCH = 1948320;
    private static final int[][] LIMITS;
    
    @Deprecated
    public PersianCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    @Deprecated
    public PersianCalendar(final TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    @Deprecated
    public PersianCalendar(final Locale locale) {
        this(TimeZone.getDefault(), locale);
    }
    
    @Deprecated
    public PersianCalendar(final ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }
    
    @Deprecated
    public PersianCalendar(final TimeZone timeZone, final Locale locale) {
        super(timeZone, locale);
        this.setTimeInMillis(System.currentTimeMillis());
    }
    
    @Deprecated
    public PersianCalendar(final TimeZone timeZone, final ULocale uLocale) {
        super(timeZone, uLocale);
        this.setTimeInMillis(System.currentTimeMillis());
    }
    
    @Deprecated
    public PersianCalendar(final Date time) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.setTime(time);
    }
    
    @Deprecated
    public PersianCalendar(final int n, final int n2, final int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }
    
    @Deprecated
    public PersianCalendar(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }
    
    @Override
    @Deprecated
    protected int handleGetLimit(final int n, final int n2) {
        return PersianCalendar.LIMITS[n][n2];
    }
    
    private static final boolean isLeapYear(final int n) {
        final int[] array = { 0 };
        Calendar.floorDivide(25 * n + 11, 33, array);
        return array[0] < 8;
    }
    
    @Override
    @Deprecated
    protected int handleGetMonthLength(int n, int n2) {
        if (n2 < 0 || n2 > 11) {
            final int[] array = { 0 };
            n += Calendar.floorDivide(n2, 12, array);
            n2 = array[0];
        }
        return PersianCalendar.MONTH_COUNT[n2][isLeapYear(n)];
    }
    
    @Override
    @Deprecated
    protected int handleGetYearLength(final int n) {
        return isLeapYear(n) ? 366 : 365;
    }
    
    @Override
    @Deprecated
    protected int handleComputeMonthStart(int n, int n2, final boolean b) {
        if (n2 < 0 || n2 > 11) {
            final int[] array = { 0 };
            n += Calendar.floorDivide(n2, 12, array);
            n2 = array[0];
        }
        int n3 = 1948319 + 365 * (n - 1) + Calendar.floorDivide(8 * n + 21, 33);
        if (n2 != 0) {
            n3 += PersianCalendar.MONTH_COUNT[n2][2];
        }
        return n3;
    }
    
    @Override
    @Deprecated
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
    @Deprecated
    protected void handleComputeFields(final int n) {
        final long n2 = n - 1948320;
        final int n3 = 1 + (int)Calendar.floorDivide(33L * n2 + 3L, 12053L);
        int n4 = (int)(n2 - (365 * (n3 - 1) + Calendar.floorDivide(8 * n3 + 21, 33)));
        int n5;
        if (n4 < 216) {
            n5 = n4 / 31;
        }
        else {
            n5 = (n4 - 6) / 30;
        }
        final int n6 = n4 - PersianCalendar.MONTH_COUNT[n5][2] + 1;
        ++n4;
        this.internalSet(0, 0);
        this.internalSet(1, n3);
        this.internalSet(19, n3);
        this.internalSet(2, n5);
        this.internalSet(5, n6);
        this.internalSet(6, n4);
    }
    
    @Override
    @Deprecated
    public String getType() {
        return "persian";
    }
    
    static {
        MONTH_COUNT = new int[][] { { 31, 31, 0 }, { 31, 31, 31 }, { 31, 31, 62 }, { 31, 31, 93 }, { 31, 31, 124 }, { 31, 31, 155 }, { 30, 30, 186 }, { 30, 30, 216 }, { 30, 30, 246 }, { 30, 30, 276 }, { 30, 30, 306 }, { 29, 30, 336 } };
        LIMITS = new int[][] { { 0, 0, 0, 0 }, { -5000000, -5000000, 5000000, 5000000 }, { 0, 0, 11, 11 }, { 1, 1, 52, 53 }, new int[0], { 1, 1, 29, 31 }, { 1, 1, 365, 366 }, new int[0], { -1, -1, 5, 5 }, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], { -5000000, -5000000, 5000000, 5000000 }, new int[0], { -5000000, -5000000, 5000000, 5000000 }, new int[0], new int[0] };
    }
}
