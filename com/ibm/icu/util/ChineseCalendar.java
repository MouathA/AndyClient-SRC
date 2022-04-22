package com.ibm.icu.util;

import com.ibm.icu.impl.*;
import java.util.*;
import com.ibm.icu.text.*;
import java.io.*;

public class ChineseCalendar extends Calendar
{
    private static final long serialVersionUID = 7312110751940929420L;
    private int epochYear;
    private TimeZone zoneAstro;
    private transient CalendarAstronomer astro;
    private transient CalendarCache winterSolsticeCache;
    private transient CalendarCache newYearCache;
    private transient boolean isLeapYear;
    private static final int[][] LIMITS;
    static final int[][][] CHINESE_DATE_PRECEDENCE;
    private static final int CHINESE_EPOCH_YEAR = -2636;
    private static final TimeZone CHINA_ZONE;
    private static final int SYNODIC_GAP = 25;
    
    public ChineseCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT), -2636, ChineseCalendar.CHINA_ZONE);
    }
    
    public ChineseCalendar(final Date time) {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT), -2636, ChineseCalendar.CHINA_ZONE);
        this.setTime(time);
    }
    
    public ChineseCalendar(final int n, final int n2, final int n3, final int n4) {
        this(n, n2, n3, n4, 0, 0, 0);
    }
    
    public ChineseCalendar(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT), -2636, ChineseCalendar.CHINA_ZONE);
        this.set(14, 0);
        this.set(1, n);
        this.set(2, n2);
        this.set(22, n3);
        this.set(5, n4);
        this.set(11, n5);
        this.set(12, n6);
        this.set(13, n7);
    }
    
    public ChineseCalendar(final int n, final int n2, final int n3, final int n4, final int n5) {
        this(n, n2, n3, n4, 0, 0, 0);
    }
    
    public ChineseCalendar(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT), -2636, ChineseCalendar.CHINA_ZONE);
        this.set(14, 0);
        this.set(0, n);
        this.set(1, n2);
        this.set(2, n3);
        this.set(22, n4);
        this.set(5, n5);
        this.set(11, n6);
        this.set(12, n7);
        this.set(13, n8);
    }
    
    public ChineseCalendar(final Locale locale) {
        this(TimeZone.getDefault(), ULocale.forLocale(locale), -2636, ChineseCalendar.CHINA_ZONE);
    }
    
    public ChineseCalendar(final TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT), -2636, ChineseCalendar.CHINA_ZONE);
    }
    
    public ChineseCalendar(final TimeZone timeZone, final Locale locale) {
        this(timeZone, ULocale.forLocale(locale), -2636, ChineseCalendar.CHINA_ZONE);
    }
    
    public ChineseCalendar(final ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale, -2636, ChineseCalendar.CHINA_ZONE);
    }
    
    public ChineseCalendar(final TimeZone timeZone, final ULocale uLocale) {
        this(timeZone, uLocale, -2636, ChineseCalendar.CHINA_ZONE);
    }
    
    @Deprecated
    protected ChineseCalendar(final TimeZone timeZone, final ULocale uLocale, final int epochYear, final TimeZone zoneAstro) {
        super(timeZone, uLocale);
        this.astro = new CalendarAstronomer();
        this.winterSolsticeCache = new CalendarCache();
        this.newYearCache = new CalendarCache();
        this.epochYear = epochYear;
        this.zoneAstro = zoneAstro;
        this.setTimeInMillis(System.currentTimeMillis());
    }
    
    @Override
    protected int handleGetLimit(final int n, final int n2) {
        return ChineseCalendar.LIMITS[n][n2];
    }
    
    @Override
    protected int handleGetExtendedYear() {
        int internalGet;
        if (this.newestStamp(0, 1, 0) <= this.getStamp(19)) {
            internalGet = this.internalGet(19, 1);
        }
        else {
            internalGet = (this.internalGet(0, 1) - 1) * 60 + this.internalGet(1, 1) - (this.epochYear + 2636);
        }
        return internalGet;
    }
    
    @Override
    protected int handleGetMonthLength(final int n, final int n2) {
        final int n3 = this.handleComputeMonthStart(n, n2, true) - 2440588 + 1;
        return this.newMoonNear(n3 + 25, true) - n3;
    }
    
    @Override
    protected DateFormat handleGetDateFormat(final String s, final String s2, final ULocale uLocale) {
        return super.handleGetDateFormat(s, s2, uLocale);
    }
    
    @Override
    protected int[][][] getFieldResolutionTable() {
        return ChineseCalendar.CHINESE_DATE_PRECEDENCE;
    }
    
    private void offsetMonth(int moonNear, final int n, final int n2) {
        moonNear += (int)(29.530588853 * (n2 - 0.5));
        moonNear = this.newMoonNear(moonNear, true);
        final int n3 = moonNear + 2440588 - 1 + n;
        if (n > 29) {
            this.set(20, n3 - 1);
            this.complete();
            if (this.getActualMaximum(5) >= n) {
                this.set(20, n3);
            }
        }
        else {
            this.set(20, n3);
        }
    }
    
    @Override
    public void add(final int n, final int n2) {
        switch (n) {
            case 2: {
                if (n2 != 0) {
                    final int value = this.get(5);
                    this.offsetMonth(this.get(20) - 2440588 - value + 1, value, n2);
                    break;
                }
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
                if (n2 != 0) {
                    final int value = this.get(5);
                    final int n3 = this.get(20) - 2440588 - value + 1;
                    int value2 = this.get(2);
                    if (this.isLeapYear) {
                        if (this.get(22) == 1) {
                            ++value2;
                        }
                        else if (this.isLeapMonthBetween(this.newMoonNear(n3 - (int)(29.530588853 * (value2 - 0.5)), true), n3)) {
                            ++value2;
                        }
                    }
                    final int n4 = this.isLeapYear ? 13 : 12;
                    int n5 = (value2 + n2) % n4;
                    if (n5 < 0) {
                        n5 += n4;
                    }
                    if (n5 != value2) {
                        this.offsetMonth(n3, value, n5 - value2);
                    }
                    break;
                }
                break;
            }
            default: {
                super.roll(n, n2);
                break;
            }
        }
    }
    
    private final long daysToMillis(final int n) {
        final long n2 = n * 86400000L;
        return n2 - this.zoneAstro.getOffset(n2);
    }
    
    private final int millisToDays(final long n) {
        return (int)Calendar.floorDivide(n + this.zoneAstro.getOffset(n), 86400000L);
    }
    
    private int winterSolstice(final int n) {
        long value = this.winterSolsticeCache.get(n);
        if (value == CalendarCache.EMPTY) {
            this.astro.setTime(this.daysToMillis(this.computeGregorianMonthStart(n, 11) + 1 - 2440588));
            value = this.millisToDays(this.astro.getSunTime(CalendarAstronomer.WINTER_SOLSTICE, true));
            this.winterSolsticeCache.put(n, value);
        }
        return (int)value;
    }
    
    private int newMoonNear(final int n, final boolean b) {
        this.astro.setTime(this.daysToMillis(n));
        return this.millisToDays(this.astro.getMoonTime(CalendarAstronomer.NEW_MOON, b));
    }
    
    private int synodicMonthsBetween(final int n, final int n2) {
        return (int)Math.round((n2 - n) / 29.530588853);
    }
    
    private int majorSolarTerm(final int n) {
        this.astro.setTime(this.daysToMillis(n));
        int n2 = ((int)Math.floor(6.0 * this.astro.getSunLongitude() / 3.141592653589793) + 2) % 12;
        if (n2 < 1) {
            n2 += 12;
        }
        return n2;
    }
    
    private boolean hasNoMajorSolarTerm(final int n) {
        return this.majorSolarTerm(n) == this.majorSolarTerm(this.newMoonNear(n + 25, true));
    }
    
    private boolean isLeapMonthBetween(final int n, final int n2) {
        if (this.synodicMonthsBetween(n, n2) >= 50) {
            throw new IllegalArgumentException("isLeapMonthBetween(" + n + ", " + n2 + "): Invalid parameters");
        }
        return n2 >= n && (this.isLeapMonthBetween(n, this.newMoonNear(n2 - 25, false)) || this.hasNoMajorSolarTerm(n2));
    }
    
    @Override
    protected void handleComputeFields(final int n) {
        this.computeChineseFields(n - 2440588, this.getGregorianYear(), this.getGregorianMonth(), true);
    }
    
    private void computeChineseFields(final int n, final int n2, final int n3, final boolean b) {
        int n4 = this.winterSolstice(n2);
        int winterSolstice;
        if (n < n4) {
            winterSolstice = this.winterSolstice(n2 - 1);
        }
        else {
            winterSolstice = n4;
            n4 = this.winterSolstice(n2 + 1);
        }
        final int moonNear = this.newMoonNear(winterSolstice + 1, true);
        final int moonNear2 = this.newMoonNear(n4 + 1, false);
        final int moonNear3 = this.newMoonNear(n + 1, false);
        this.isLeapYear = (this.synodicMonthsBetween(moonNear, moonNear2) == 12);
        int synodicMonthsBetween = this.synodicMonthsBetween(moonNear, moonNear3);
        if (this.isLeapYear && this.isLeapMonthBetween(moonNear, moonNear3)) {
            --synodicMonthsBetween;
        }
        if (synodicMonthsBetween < 1) {
            synodicMonthsBetween += 12;
        }
        final int n5 = (this.isLeapYear && this.hasNoMajorSolarTerm(moonNear3) && !this.isLeapMonthBetween(moonNear, this.newMoonNear(moonNear3 - 25, false))) ? 1 : 0;
        this.internalSet(2, synodicMonthsBetween - 1);
        this.internalSet(22, n5);
        if (b) {
            int n6 = n2 - this.epochYear;
            int n7 = n2 + 2636;
            if (synodicMonthsBetween < 11 || n3 >= 6) {
                ++n6;
                ++n7;
            }
            final int n8 = n - moonNear3 + 1;
            this.internalSet(19, n6);
            final int[] array = { 0 };
            this.internalSet(0, Calendar.floorDivide(n7 - 1, 60, array) + 1);
            this.internalSet(1, array[0] + 1);
            this.internalSet(5, n8);
            int n9 = this.newYear(n2);
            if (n < n9) {
                n9 = this.newYear(n2 - 1);
            }
            this.internalSet(6, n - n9 + 1);
        }
    }
    
    private int newYear(final int n) {
        long value = this.newYearCache.get(n);
        if (value == CalendarCache.EMPTY) {
            final int winterSolstice = this.winterSolstice(n - 1);
            final int winterSolstice2 = this.winterSolstice(n);
            final int moonNear = this.newMoonNear(winterSolstice + 1, true);
            final int moonNear2 = this.newMoonNear(moonNear + 25, true);
            if (this.synodicMonthsBetween(moonNear, this.newMoonNear(winterSolstice2 + 1, false)) == 12 && (this.hasNoMajorSolarTerm(moonNear) || this.hasNoMajorSolarTerm(moonNear2))) {
                value = this.newMoonNear(moonNear2 + 25, true);
            }
            else {
                value = moonNear2;
            }
            this.newYearCache.put(n, value);
        }
        return (int)value;
    }
    
    @Override
    protected int handleComputeMonthStart(int n, int n2, final boolean b) {
        if (n2 < 0 || n2 > 11) {
            final int[] array = { 0 };
            n += Calendar.floorDivide(n2, 12, array);
            n2 = array[0];
        }
        final int moonNear = this.newMoonNear(this.newYear(n + this.epochYear - 1) + n2 * 29, true);
        int n3 = moonNear + 2440588;
        final int internalGet = this.internalGet(2);
        final int internalGet2 = this.internalGet(22);
        final boolean b2 = (b ? internalGet2 : false) != 0;
        this.computeGregorianFields(n3);
        this.computeChineseFields(moonNear, this.getGregorianYear(), this.getGregorianMonth(), false);
        if (n2 != this.internalGet(2) || (b2 ? 1 : 0) != this.internalGet(22)) {
            n3 = this.newMoonNear(moonNear + 25, true) + 2440588;
        }
        this.internalSet(2, internalGet);
        this.internalSet(22, internalGet2);
        return n3 - 1;
    }
    
    @Override
    public String getType() {
        return "chinese";
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.epochYear = -2636;
        this.zoneAstro = ChineseCalendar.CHINA_ZONE;
        objectInputStream.defaultReadObject();
        this.astro = new CalendarAstronomer();
        this.winterSolsticeCache = new CalendarCache();
        this.newYearCache = new CalendarCache();
    }
    
    static {
        LIMITS = new int[][] { { 1, 1, 83333, 83333 }, { 1, 1, 60, 60 }, { 0, 0, 11, 11 }, { 1, 1, 50, 55 }, new int[0], { 1, 1, 29, 30 }, { 1, 1, 353, 385 }, new int[0], { -1, -1, 5, 5 }, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], { -5000000, -5000000, 5000000, 5000000 }, new int[0], { -5000000, -5000000, 5000000, 5000000 }, new int[0], new int[0], { 0, 0, 1, 1 } };
        CHINESE_DATE_PRECEDENCE = new int[][][] { { { 5 }, { 3, 7 }, { 4, 7 }, { 8, 7 }, { 3, 18 }, { 4, 18 }, { 8, 18 }, { 6 }, { 37, 22 } }, { { 3 }, { 4 }, { 8 }, { 40, 7 }, { 40, 18 } } };
        CHINA_ZONE = new SimpleTimeZone(28800000, "CHINA_ZONE").freeze();
    }
}
