package com.ibm.icu.util;

import java.util.*;

public class IndianCalendar extends Calendar
{
    private static final long serialVersionUID = 3617859668165014834L;
    public static final int CHAITRA = 0;
    public static final int VAISAKHA = 1;
    public static final int JYAISTHA = 2;
    public static final int ASADHA = 3;
    public static final int SRAVANA = 4;
    public static final int BHADRA = 5;
    public static final int ASVINA = 6;
    public static final int KARTIKA = 7;
    public static final int AGRAHAYANA = 8;
    public static final int PAUSA = 9;
    public static final int MAGHA = 10;
    public static final int PHALGUNA = 11;
    public static final int IE = 0;
    private static final int INDIAN_ERA_START = 78;
    private static final int INDIAN_YEAR_START = 80;
    private static final int[][] LIMITS;
    
    public IndianCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public IndianCalendar(final TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public IndianCalendar(final Locale locale) {
        this(TimeZone.getDefault(), locale);
    }
    
    public IndianCalendar(final ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }
    
    public IndianCalendar(final TimeZone timeZone, final Locale locale) {
        super(timeZone, locale);
        this.setTimeInMillis(System.currentTimeMillis());
    }
    
    public IndianCalendar(final TimeZone timeZone, final ULocale uLocale) {
        super(timeZone, uLocale);
        this.setTimeInMillis(System.currentTimeMillis());
    }
    
    public IndianCalendar(final Date time) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.setTime(time);
    }
    
    public IndianCalendar(final int n, final int n2, final int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }
    
    public IndianCalendar(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
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
    protected int handleGetYearLength(final int n) {
        return super.handleGetYearLength(n);
    }
    
    @Override
    protected int handleGetMonthLength(int n, int n2) {
        if (n2 < 0 || n2 > 11) {
            final int[] array = { 0 };
            n += Calendar.floorDivide(n2, 12, array);
            n2 = array[0];
        }
        if (n + 78 == 0 && n2 == 0) {
            return 31;
        }
        if (n2 >= 1 && n2 <= 5) {
            return 31;
        }
        return 30;
    }
    
    @Override
    protected void handleComputeFields(final int n) {
        final int[] jdToGregorian = jdToGregorian(n);
        int n2 = jdToGregorian[0] - 78;
        int n3 = (int)(n - gregorianToJD(jdToGregorian[0], 1, 1));
        int n4;
        if (n3 < 80) {
            --n2;
            n4 = ((jdToGregorian[0] - 1 == 0) ? 31 : 30);
            n3 += n4 + 155 + 90 + 10;
        }
        else {
            n4 = ((jdToGregorian[0] == 0) ? 31 : 30);
            n3 -= 80;
        }
        int n5;
        if (n3 < n4) {
            n5 = n3 + 1;
        }
        else {
            int n6 = n3 - n4;
            if (n6 < 155) {
                n5 = n6 % 31 + 1;
            }
            else {
                n6 -= 155;
                n5 = n6 % 30 + 1;
            }
        }
        this.internalSet(0, 0);
        this.internalSet(19, n2);
        this.internalSet(1, n2);
        this.internalSet(2, 0);
        this.internalSet(5, n5);
        this.internalSet(6, n3 + 1);
    }
    
    @Override
    protected int handleGetLimit(final int n, final int n2) {
        return IndianCalendar.LIMITS[n][n2];
    }
    
    @Override
    protected int handleComputeMonthStart(int n, int n2, final boolean b) {
        if (n2 < 0 || n2 > 11) {
            n += n2 / 12;
            n2 %= 12;
        }
        if (n2 == 12) {}
        return (int)IndianToJD(n, 1, 1);
    }
    
    private static double IndianToJD(final int n, final int n2, final int n3) {
        final int n4 = n + 78;
        double n5;
        if (n4 == 0) {
            n5 = gregorianToJD(n4, 3, 21);
        }
        else {
            n5 = gregorianToJD(n4, 3, 22);
        }
        double n6;
        if (n2 == 1) {
            n6 = n5 + (n3 - 1);
        }
        else {
            double n7 = n5 + 30 + Math.min(n2 - 2, 5) * 31;
            if (n2 >= 8) {
                n7 += (n2 - 7) * 30;
            }
            n6 = n7 + (n3 - 1);
        }
        return n6;
    }
    
    private static double gregorianToJD(final int n, final int n2, final int n3) {
        final double n4 = 1721425.5;
        final int n5 = n - 1;
        return 365 * n5 + n5 / 4 - n5 / 100 + n5 / 400 + (367 * n2 - 362) / 12 + ((n2 <= 2) ? 0 : ((n == 0) ? -1 : -2)) + n3 - 1 + n4;
    }
    
    private static int[] jdToGregorian(final double n) {
        final double n2 = 1721425.5;
        final double n3 = Math.floor(n - 0.5) + 0.5;
        final double n4 = n3 - n2;
        final double floor = Math.floor(n4 / 146097.0);
        final double n5 = n4 % 146097.0;
        final double floor2 = Math.floor(n5 / 36524.0);
        final double n6 = n5 % 36524.0;
        final double floor3 = Math.floor(n6 / 1461.0);
        final double floor4 = Math.floor(n6 % 1461.0 / 365.0);
        int n7 = (int)(floor * 400.0 + floor2 * 100.0 + floor3 * 4.0 + floor4);
        if (floor2 != 4.0 && floor4 != 4.0) {
            ++n7;
        }
        final int n8 = (int)Math.floor(((n3 - gregorianToJD(n7, 1, 1) + ((n3 < gregorianToJD(n7, 3, 1)) ? 0 : ((n7 == 0) ? 1 : 2))) * 12.0 + 373.0) / 367.0);
        return new int[] { n7, n8, (int)(n3 - gregorianToJD(n7, n8, 1)) + 1 };
    }
    
    @Override
    public String getType() {
        return "indian";
    }
    
    static {
        LIMITS = new int[][] { { 0, 0, 0, 0 }, { -5000000, -5000000, 5000000, 5000000 }, { 0, 0, 11, 11 }, { 1, 1, 52, 53 }, new int[0], { 1, 1, 30, 31 }, { 1, 1, 365, 366 }, new int[0], { -1, -1, 5, 5 }, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], { -5000000, -5000000, 5000000, 5000000 }, new int[0], { -5000000, -5000000, 5000000, 5000000 }, new int[0], new int[0] };
    }
}
