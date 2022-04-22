package com.ibm.icu.util;

import java.util.*;

public class GregorianCalendar extends Calendar
{
    private static final long serialVersionUID = 9199388694351062137L;
    public static final int BC = 0;
    public static final int AD = 1;
    private static final int EPOCH_YEAR = 1970;
    private static final int[][] MONTH_COUNT;
    private static final int[][] LIMITS;
    private long gregorianCutover;
    private transient int cutoverJulianDay;
    private transient int gregorianCutoverYear;
    protected transient boolean isGregorian;
    protected transient boolean invertGregorian;
    
    @Override
    protected int handleGetLimit(final int n, final int n2) {
        return GregorianCalendar.LIMITS[n][n2];
    }
    
    public GregorianCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public GregorianCalendar(final TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public GregorianCalendar(final Locale locale) {
        this(TimeZone.getDefault(), locale);
    }
    
    public GregorianCalendar(final ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }
    
    public GregorianCalendar(final TimeZone timeZone, final Locale locale) {
        super(timeZone, locale);
        this.gregorianCutover = -12219292800000L;
        this.cutoverJulianDay = 2299161;
        this.gregorianCutoverYear = 1582;
        this.setTimeInMillis(System.currentTimeMillis());
    }
    
    public GregorianCalendar(final TimeZone timeZone, final ULocale uLocale) {
        super(timeZone, uLocale);
        this.gregorianCutover = -12219292800000L;
        this.cutoverJulianDay = 2299161;
        this.gregorianCutoverYear = 1582;
        this.setTimeInMillis(System.currentTimeMillis());
    }
    
    public GregorianCalendar(final int n, final int n2, final int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.gregorianCutover = -12219292800000L;
        this.cutoverJulianDay = 2299161;
        this.gregorianCutoverYear = 1582;
        this.set(0, 1);
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }
    
    public GregorianCalendar(final int n, final int n2, final int n3, final int n4, final int n5) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.gregorianCutover = -12219292800000L;
        this.cutoverJulianDay = 2299161;
        this.gregorianCutoverYear = 1582;
        this.set(0, 1);
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
    }
    
    public GregorianCalendar(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.gregorianCutover = -12219292800000L;
        this.cutoverJulianDay = 2299161;
        this.gregorianCutoverYear = 1582;
        this.set(0, 1);
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }
    
    public void setGregorianChange(final Date time) {
        this.gregorianCutover = time.getTime();
        if (this.gregorianCutover <= -184303902528000000L) {
            final int n = Integer.MIN_VALUE;
            this.cutoverJulianDay = n;
            this.gregorianCutoverYear = n;
        }
        else if (this.gregorianCutover >= 183882168921600000L) {
            final int n2 = Integer.MAX_VALUE;
            this.cutoverJulianDay = n2;
            this.gregorianCutoverYear = n2;
        }
        else {
            this.cutoverJulianDay = (int)Calendar.floorDivide(this.gregorianCutover, 86400000L);
            final GregorianCalendar gregorianCalendar = new GregorianCalendar(this.getTimeZone());
            gregorianCalendar.setTime(time);
            this.gregorianCutoverYear = gregorianCalendar.get(19);
        }
    }
    
    public final Date getGregorianChange() {
        return new Date(this.gregorianCutover);
    }
    
    public boolean isLeapYear(final int n) {
        return (n >= this.gregorianCutoverYear) ? (n % 4 == 0 && (n % 100 != 0 || n % 400 == 0)) : (n % 4 == 0);
    }
    
    @Override
    public boolean isEquivalentTo(final Calendar calendar) {
        return super.isEquivalentTo(calendar) && this.gregorianCutover == ((GregorianCalendar)calendar).gregorianCutover;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ (int)this.gregorianCutover;
    }
    
    @Override
    public void roll(final int n, final int n2) {
        switch (n) {
            case 3: {
                final int value = this.get(3);
                final int value2 = this.get(17);
                int internalGet = this.internalGet(6);
                if (this.internalGet(2) == 0) {
                    if (value >= 52) {
                        internalGet += this.handleGetYearLength(value2);
                    }
                }
                else if (value == 1) {
                    internalGet -= this.handleGetYearLength(value2 - 1);
                }
                int n3 = value + n2;
                if (n3 < 1 || n3 > 52) {
                    int handleGetYearLength = this.handleGetYearLength(value2);
                    int n4 = (handleGetYearLength - internalGet + this.internalGet(7) - this.getFirstDayOfWeek()) % 7;
                    if (n4 < 0) {
                        n4 += 7;
                    }
                    if (6 - n4 >= this.getMinimalDaysInFirstWeek()) {
                        handleGetYearLength -= 7;
                    }
                    final int weekNumber = this.weekNumber(handleGetYearLength, n4 + 1);
                    n3 = (n3 + weekNumber - 1) % weekNumber + 1;
                }
                this.set(3, n3);
                this.set(1, value2);
            }
            default: {
                super.roll(n, n2);
            }
        }
    }
    
    @Override
    public int getActualMinimum(final int n) {
        return this.getMinimum(n);
    }
    
    @Override
    public int getActualMaximum(final int n) {
        switch (n) {
            case 1: {
                final Calendar calendar = (Calendar)this.clone();
                calendar.setLenient(true);
                final int value = calendar.get(0);
                final Date time = calendar.getTime();
                int n2 = GregorianCalendar.LIMITS[1][1];
                int n3 = GregorianCalendar.LIMITS[1][2] + 1;
                while (n2 + 1 < n3) {
                    final int n4 = (n2 + n3) / 2;
                    calendar.set(1, n4);
                    if (calendar.get(1) == n4 && calendar.get(0) == value) {
                        n2 = n4;
                    }
                    else {
                        n3 = n4;
                        calendar.setTime(time);
                    }
                }
                return n2;
            }
            default: {
                return super.getActualMaximum(n);
            }
        }
    }
    
    boolean inDaylightTime() {
        if (!this.getTimeZone().useDaylightTime()) {
            return false;
        }
        this.complete();
        return this.internalGet(16) != 0;
    }
    
    @Override
    protected int handleGetMonthLength(int n, int n2) {
        if (n2 < 0 || n2 > 11) {
            final int[] array = { 0 };
            n += Calendar.floorDivide(n2, 12, array);
            n2 = array[0];
        }
        return GregorianCalendar.MONTH_COUNT[n2][this.isLeapYear(n)];
    }
    
    @Override
    protected int handleGetYearLength(final int n) {
        return this.isLeapYear(n) ? 366 : 365;
    }
    
    @Override
    protected void handleComputeFields(final int n) {
        int gregorianMonth;
        int gregorianDayOfMonth;
        int gregorianDayOfYear;
        int gregorianYear;
        if (n >= this.cutoverJulianDay) {
            gregorianMonth = this.getGregorianMonth();
            gregorianDayOfMonth = this.getGregorianDayOfMonth();
            gregorianDayOfYear = this.getGregorianDayOfYear();
            gregorianYear = this.getGregorianYear();
        }
        else {
            final long n2 = n - 1721424;
            gregorianYear = (int)Calendar.floorDivide(4L * n2 + 1464L, 1461L);
            gregorianDayOfYear = (int)(n2 - (365 * (gregorianYear - 1) + Calendar.floorDivide(gregorianYear - 1, 4)));
            final boolean b = (gregorianYear & 0x3) == 0x0;
            if (gregorianDayOfYear >= (b ? 60 : 59)) {
                final int n3 = b ? 1 : 2;
            }
            gregorianMonth = (12 * (gregorianDayOfYear + 0) + 6) / 367;
            gregorianDayOfMonth = gregorianDayOfYear - GregorianCalendar.MONTH_COUNT[gregorianMonth][b ? 3 : 2] + 1;
            ++gregorianDayOfYear;
        }
        this.internalSet(2, gregorianMonth);
        this.internalSet(5, gregorianDayOfMonth);
        this.internalSet(6, gregorianDayOfYear);
        this.internalSet(19, gregorianYear);
        if (gregorianYear < 1) {
            gregorianYear = 1 - gregorianYear;
        }
        this.internalSet(0, 0);
        this.internalSet(1, gregorianYear);
    }
    
    @Override
    protected int handleGetExtendedYear() {
        int n;
        if (this.newerField(19, 1) == 19) {
            n = this.internalGet(19, 1970);
        }
        else if (this.internalGet(0, 1) == 0) {
            n = 1 - this.internalGet(1, 1);
        }
        else {
            n = this.internalGet(1, 1970);
        }
        return n;
    }
    
    @Override
    protected int handleComputeJulianDay(final int n) {
        this.invertGregorian = false;
        int n2 = super.handleComputeJulianDay(n);
        if (this.isGregorian != n2 >= this.cutoverJulianDay) {
            this.invertGregorian = true;
            n2 = super.handleComputeJulianDay(n);
        }
        return n2;
    }
    
    @Override
    protected int handleComputeMonthStart(int n, int n2, final boolean b) {
        if (n2 < 0 || n2 > 11) {
            final int[] array = { 0 };
            n += Calendar.floorDivide(n2, 12, array);
            n2 = array[0];
        }
        boolean b2 = n % 4 == 0;
        final int n3 = n - 1;
        int n4 = 365 * n3 + Calendar.floorDivide(n3, 4) + 1721423;
        this.isGregorian = (n >= this.gregorianCutoverYear);
        if (this.invertGregorian) {
            this.isGregorian = !this.isGregorian;
        }
        if (this.isGregorian) {
            b2 = (b2 && (n % 100 != 0 || n % 400 == 0));
            n4 += Calendar.floorDivide(n3, 400) - Calendar.floorDivide(n3, 100) + 2;
        }
        if (n2 != 0) {
            n4 += GregorianCalendar.MONTH_COUNT[n2][b2 ? 3 : 2];
        }
        return n4;
    }
    
    @Override
    public String getType() {
        return "gregorian";
    }
    
    static {
        MONTH_COUNT = new int[][] { { 31, 31, 0, 0 }, { 28, 29, 31, 31 }, { 31, 31, 59, 60 }, { 30, 30, 90, 91 }, { 31, 31, 120, 121 }, { 30, 30, 151, 152 }, { 31, 31, 181, 182 }, { 31, 31, 212, 213 }, { 30, 30, 243, 244 }, { 31, 31, 273, 274 }, { 30, 30, 304, 305 }, { 31, 31, 334, 335 } };
        LIMITS = new int[][] { { 0, 0, 1, 1 }, { 1, 1, 5828963, 5838270 }, { 0, 0, 11, 11 }, { 1, 1, 52, 53 }, new int[0], { 1, 1, 28, 31 }, { 1, 1, 365, 366 }, new int[0], { -1, -1, 4, 5 }, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], { -5838270, -5838270, 5828964, 5838271 }, new int[0], { -5838269, -5838269, 5828963, 5838270 }, new int[0], new int[0] };
    }
}
