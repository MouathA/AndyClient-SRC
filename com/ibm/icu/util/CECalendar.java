package com.ibm.icu.util;

import java.util.*;

abstract class CECalendar extends Calendar
{
    private static final long serialVersionUID = -999547623066414271L;
    private static final int[][] LIMITS;
    
    protected CECalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    protected CECalendar(final TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    protected CECalendar(final Locale locale) {
        this(TimeZone.getDefault(), locale);
    }
    
    protected CECalendar(final ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }
    
    protected CECalendar(final TimeZone timeZone, final Locale locale) {
        super(timeZone, locale);
        this.setTimeInMillis(System.currentTimeMillis());
    }
    
    protected CECalendar(final TimeZone timeZone, final ULocale uLocale) {
        super(timeZone, uLocale);
        this.setTimeInMillis(System.currentTimeMillis());
    }
    
    protected CECalendar(final int n, final int n2, final int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(n, n2, n3);
    }
    
    protected CECalendar(final Date time) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.setTime(time);
    }
    
    protected CECalendar(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(n, n2, n3, n4, n5, n6);
    }
    
    protected abstract int getJDEpochOffset();
    
    @Override
    protected int handleComputeMonthStart(final int n, final int n2, final boolean b) {
        return ceToJD(n, n2, 0, this.getJDEpochOffset());
    }
    
    @Override
    protected int handleGetLimit(final int n, final int n2) {
        return CECalendar.LIMITS[n][n2];
    }
    
    @Override
    protected int handleGetMonthLength(final int n, final int n2) {
        if ((n2 + 1) % 13 != 0) {
            return 30;
        }
        return n % 4 / 3 + 5;
    }
    
    public static int ceToJD(long n, int n2, final int n3, final int n4) {
        if (n2 >= 0) {
            n += n2 / 13;
            n2 %= 13;
        }
        else {
            ++n2;
            n += n2 / 13 - 1;
            n2 = n2 % 13 + 12;
        }
        return (int)(n4 + 365L * n + Calendar.floorDivide(n, 4L) + 30 * n2 + n3 - 1L);
    }
    
    public static void jdToCE(final int n, final int n2, final int[] array) {
        final int[] array2 = { 0 };
        array[0] = 4 * Calendar.floorDivide(n - n2, 1461, array2) + (array2[0] / 365 - array2[0] / 1460);
        final int n3 = (array2[0] == 1460) ? 365 : (array2[0] % 365);
        array[1] = n3 / 30;
        array[2] = n3 % 30 + 1;
    }
    
    static {
        LIMITS = new int[][] { { 0, 0, 1, 1 }, { 1, 1, 5000000, 5000000 }, { 0, 0, 12, 12 }, { 1, 1, 52, 53 }, new int[0], { 1, 1, 5, 30 }, { 1, 1, 365, 366 }, new int[0], { -1, -1, 1, 5 }, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], { -5000000, -5000000, 5000000, 5000000 }, new int[0], { -5000000, -5000000, 5000000, 5000000 }, new int[0], new int[0] };
    }
}
