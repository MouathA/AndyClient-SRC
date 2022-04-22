package com.ibm.icu.util;

import com.ibm.icu.impl.*;
import java.util.*;

public class IslamicCalendar extends Calendar
{
    private static final long serialVersionUID = -6253365474073869325L;
    public static final int MUHARRAM = 0;
    public static final int SAFAR = 1;
    public static final int RABI_1 = 2;
    public static final int RABI_2 = 3;
    public static final int JUMADA_1 = 4;
    public static final int JUMADA_2 = 5;
    public static final int RAJAB = 6;
    public static final int SHABAN = 7;
    public static final int RAMADAN = 8;
    public static final int SHAWWAL = 9;
    public static final int DHU_AL_QIDAH = 10;
    public static final int DHU_AL_HIJJAH = 11;
    private static final long HIJRA_MILLIS = -42521587200000L;
    private static final int[][] LIMITS;
    private static CalendarAstronomer astro;
    private static CalendarCache cache;
    private boolean civil;
    
    public IslamicCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public IslamicCalendar(final TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public IslamicCalendar(final Locale locale) {
        this(TimeZone.getDefault(), locale);
    }
    
    public IslamicCalendar(final ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }
    
    public IslamicCalendar(final TimeZone timeZone, final Locale locale) {
        super(timeZone, locale);
        this.civil = true;
        this.setTimeInMillis(System.currentTimeMillis());
    }
    
    public IslamicCalendar(final TimeZone timeZone, final ULocale uLocale) {
        super(timeZone, uLocale);
        this.civil = true;
        this.setTimeInMillis(System.currentTimeMillis());
    }
    
    public IslamicCalendar(final Date time) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.civil = true;
        this.setTime(time);
    }
    
    public IslamicCalendar(final int n, final int n2, final int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.civil = true;
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }
    
    public IslamicCalendar(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.civil = true;
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }
    
    public void setCivil(final boolean civil) {
        if (this.civil != civil) {
            final long timeInMillis = this.getTimeInMillis();
            this.civil = civil;
            this.clear();
            this.setTimeInMillis(timeInMillis);
        }
    }
    
    public boolean isCivil() {
        return this.civil;
    }
    
    @Override
    protected int handleGetLimit(final int n, final int n2) {
        return IslamicCalendar.LIMITS[n][n2];
    }
    
    private static final boolean civilLeapYear(final int n) {
        return (14 + 11 * n) % 30 < 11;
    }
    
    private long yearStart(final int n) {
        if (this.civil) {
            return (n - 1) * 354 + (long)Math.floor((3 + 11 * n) / 30.0);
        }
        return trueMonthStart(12 * (n - 1));
    }
    
    private long monthStart(final int n, final int n2) {
        final int n3 = n + n2 / 12;
        final int n4 = n2 % 12;
        if (this.civil) {
            return (long)Math.ceil(29.5 * n4) + (n3 - 1) * 354 + (long)Math.floor((3 + 11 * n3) / 30.0);
        }
        return trueMonthStart(12 * (n3 - 1) + n4);
    }
    
    private static final long trueMonthStart(final long n) {
        long value = IslamicCalendar.cache.get(n);
        if (value == CalendarCache.EMPTY) {
            long n2 = -42521587200000L + (long)Math.floor(n * 29.530588853) * 86400000L;
            moonAge(n2);
            if (moonAge(n2) >= 0.0) {
                do {
                    n2 -= 86400000L;
                } while (moonAge(n2) >= 0.0);
            }
            else {
                do {
                    n2 += 86400000L;
                } while (moonAge(n2) < 0.0);
            }
            value = (n2 + 42521587200000L) / 86400000L + 1L;
            IslamicCalendar.cache.put(n, value);
        }
        return value;
    }
    
    static final double moonAge(final long time) {
        // monitorenter(astro = IslamicCalendar.astro)
        IslamicCalendar.astro.setTime(time);
        final double moonAge = IslamicCalendar.astro.getMoonAge();
        // monitorexit(astro)
        double n = moonAge * 180.0 / 3.141592653589793;
        if (n > 180.0) {
            n -= 360.0;
        }
        return n;
    }
    
    @Override
    protected int handleGetMonthLength(final int n, int n2) {
        if (this.civil) {
            int n3 = 29 + (n2 + 1) % 2;
            if (n2 == 11 && civilLeapYear(n)) {
                ++n3;
            }
        }
        else {
            n2 += 12 * (n - 1);
            final int n4 = (int)(trueMonthStart(n2 + 1) - trueMonthStart(n2));
        }
        return 0;
    }
    
    @Override
    protected int handleGetYearLength(final int n) {
        if (this.civil) {
            return 354 + (civilLeapYear(n) ? 1 : 0);
        }
        final int n2 = 12 * (n - 1);
        return (int)(trueMonthStart(n2 + 12) - trueMonthStart(n2));
    }
    
    @Override
    protected int handleComputeMonthStart(final int n, final int n2, final boolean b) {
        return (int)this.monthStart(n, n2) + 1948439;
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
    protected void handleComputeFields(final int n) {
        final long n2 = n - 1948440;
        int n3;
        int min;
        if (this.civil) {
            n3 = (int)Math.floor((30L * n2 + 10646L) / 10631.0);
            min = Math.min((int)Math.ceil((n2 - 29L - this.yearStart(n3)) / 29.5), 11);
        }
        else {
            int n4 = (int)Math.floor(n2 / 29.530588853);
            if (n2 - (long)Math.floor(n4 * 29.530588853 - 1.0) >= 25L && moonAge(this.internalGetTimeInMillis()) > 0.0) {
                ++n4;
            }
            while (trueMonthStart(n4) > n2) {
                --n4;
            }
            n3 = n4 / 12 + 1;
            min = n4 % 12;
        }
        final int n5 = (int)(n2 - this.monthStart(n3, min)) + 1;
        final int n6 = (int)(n2 - this.monthStart(n3, 0) + 1L);
        this.internalSet(0, 0);
        this.internalSet(1, n3);
        this.internalSet(19, n3);
        this.internalSet(2, min);
        this.internalSet(5, n5);
        this.internalSet(6, n6);
    }
    
    @Override
    public String getType() {
        if (this.civil) {
            return "islamic-civil";
        }
        return "islamic";
    }
    
    static {
        LIMITS = new int[][] { { 0, 0, 0, 0 }, { 1, 1, 5000000, 5000000 }, { 0, 0, 11, 11 }, { 1, 1, 50, 51 }, new int[0], { 1, 1, 29, 30 }, { 1, 1, 354, 355 }, new int[0], { -1, -1, 5, 5 }, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], { 1, 1, 5000000, 5000000 }, new int[0], { 1, 1, 5000000, 5000000 }, new int[0], new int[0] };
        IslamicCalendar.astro = new CalendarAstronomer();
        IslamicCalendar.cache = new CalendarCache();
    }
}
