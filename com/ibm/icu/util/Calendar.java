package com.ibm.icu.util;

import com.ibm.icu.text.*;
import java.text.*;
import java.io.*;
import com.ibm.icu.impl.*;
import java.util.*;

public abstract class Calendar implements Serializable, Cloneable, Comparable<Calendar>
{
    public static final int ERA = 0;
    public static final int YEAR = 1;
    public static final int MONTH = 2;
    public static final int WEEK_OF_YEAR = 3;
    public static final int WEEK_OF_MONTH = 4;
    public static final int DATE = 5;
    public static final int DAY_OF_MONTH = 5;
    public static final int DAY_OF_YEAR = 6;
    public static final int DAY_OF_WEEK = 7;
    public static final int DAY_OF_WEEK_IN_MONTH = 8;
    public static final int AM_PM = 9;
    public static final int HOUR = 10;
    public static final int HOUR_OF_DAY = 11;
    public static final int MINUTE = 12;
    public static final int SECOND = 13;
    public static final int MILLISECOND = 14;
    public static final int ZONE_OFFSET = 15;
    public static final int DST_OFFSET = 16;
    public static final int YEAR_WOY = 17;
    public static final int DOW_LOCAL = 18;
    public static final int EXTENDED_YEAR = 19;
    public static final int JULIAN_DAY = 20;
    public static final int MILLISECONDS_IN_DAY = 21;
    public static final int IS_LEAP_MONTH = 22;
    protected static final int BASE_FIELD_COUNT = 23;
    protected static final int MAX_FIELD_COUNT = 32;
    public static final int SUNDAY = 1;
    public static final int MONDAY = 2;
    public static final int TUESDAY = 3;
    public static final int WEDNESDAY = 4;
    public static final int THURSDAY = 5;
    public static final int FRIDAY = 6;
    public static final int SATURDAY = 7;
    public static final int JANUARY = 0;
    public static final int FEBRUARY = 1;
    public static final int MARCH = 2;
    public static final int APRIL = 3;
    public static final int MAY = 4;
    public static final int JUNE = 5;
    public static final int JULY = 6;
    public static final int AUGUST = 7;
    public static final int SEPTEMBER = 8;
    public static final int OCTOBER = 9;
    public static final int NOVEMBER = 10;
    public static final int DECEMBER = 11;
    public static final int UNDECIMBER = 12;
    public static final int AM = 0;
    public static final int PM = 1;
    public static final int WEEKDAY = 0;
    public static final int WEEKEND = 1;
    public static final int WEEKEND_ONSET = 2;
    public static final int WEEKEND_CEASE = 3;
    public static final int WALLTIME_LAST = 0;
    public static final int WALLTIME_FIRST = 1;
    public static final int WALLTIME_NEXT_VALID = 2;
    protected static final int ONE_SECOND = 1000;
    protected static final int ONE_MINUTE = 60000;
    protected static final int ONE_HOUR = 3600000;
    protected static final long ONE_DAY = 86400000L;
    protected static final long ONE_WEEK = 604800000L;
    protected static final int JAN_1_1_JULIAN_DAY = 1721426;
    protected static final int EPOCH_JULIAN_DAY = 2440588;
    protected static final int MIN_JULIAN = -2130706432;
    protected static final long MIN_MILLIS = -184303902528000000L;
    protected static final Date MIN_DATE;
    protected static final int MAX_JULIAN = 2130706432;
    protected static final long MAX_MILLIS = 183882168921600000L;
    protected static final Date MAX_DATE;
    private transient int[] fields;
    private transient int[] stamp;
    private long time;
    private transient boolean isTimeSet;
    private transient boolean areFieldsSet;
    private transient boolean areAllFieldsSet;
    private transient boolean areFieldsVirtuallySet;
    private boolean lenient;
    private TimeZone zone;
    private int firstDayOfWeek;
    private int minimalDaysInFirstWeek;
    private int weekendOnset;
    private int weekendOnsetMillis;
    private int weekendCease;
    private int weekendCeaseMillis;
    private int repeatedWallTime;
    private int skippedWallTime;
    private static ICUCache cachedLocaleData;
    protected static final int UNSET = 0;
    protected static final int INTERNALLY_SET = 1;
    protected static final int MINIMUM_USER_STAMP = 2;
    private transient int nextStamp;
    private static final long serialVersionUID = 6222646104888790989L;
    private transient int internalSetMask;
    private transient int gregorianYear;
    private transient int gregorianMonth;
    private transient int gregorianDayOfYear;
    private transient int gregorianDayOfMonth;
    private static int STAMP_MAX;
    private static final String[] calTypes;
    private static final int CALTYPE_GREGORIAN = 0;
    private static final int CALTYPE_JAPANESE = 1;
    private static final int CALTYPE_BUDDHIST = 2;
    private static final int CALTYPE_ROC = 3;
    private static final int CALTYPE_PERSIAN = 4;
    private static final int CALTYPE_ISLAMIC_CIVIL = 5;
    private static final int CALTYPE_ISLAMIC = 6;
    private static final int CALTYPE_HEBREW = 7;
    private static final int CALTYPE_CHINESE = 8;
    private static final int CALTYPE_INDIAN = 9;
    private static final int CALTYPE_COPTIC = 10;
    private static final int CALTYPE_ETHIOPIC = 11;
    private static final int CALTYPE_ETHIOPIC_AMETE_ALEM = 12;
    private static final int CALTYPE_ISO8601 = 13;
    private static final int CALTYPE_DANGI = 14;
    private static final int CALTYPE_UNKNOWN = -1;
    private static CalendarShim shim;
    private static final ICUCache PATTERN_CACHE;
    private static final String[] DEFAULT_PATTERNS;
    private static final char QUOTE = '\'';
    private static final int FIELD_DIFF_MAX_INT = Integer.MAX_VALUE;
    private static final int[][] LIMITS;
    protected static final int MINIMUM = 0;
    protected static final int GREATEST_MINIMUM = 1;
    protected static final int LEAST_MAXIMUM = 2;
    protected static final int MAXIMUM = 3;
    protected static final int RESOLVE_REMAP = 32;
    static final int[][][] DATE_PRECEDENCE;
    static final int[][][] DOW_PRECEDENCE;
    private static final int[] FIND_ZONE_TRANSITION_TIME_UNITS;
    private static final int[][] GREGORIAN_MONTH_COUNT;
    private static final String[] FIELD_NAME;
    private ULocale validLocale;
    private ULocale actualLocale;
    static final boolean $assertionsDisabled;
    
    protected Calendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    protected Calendar(final TimeZone timeZone, final Locale locale) {
        this(timeZone, ULocale.forLocale(locale));
    }
    
    protected Calendar(final TimeZone zone, final ULocale weekData) {
        this.lenient = true;
        this.repeatedWallTime = 0;
        this.skippedWallTime = 0;
        this.nextStamp = 2;
        this.zone = zone;
        this.setWeekData(weekData);
        this.initInternal();
    }
    
    private void recalculateStamp() {
        this.nextStamp = 1;
        for (int i = 0; i < this.stamp.length; ++i) {
            int stamp_MAX = Calendar.STAMP_MAX;
            int n = -1;
            for (int j = 0; j < this.stamp.length; ++j) {
                if (this.stamp[j] > this.nextStamp && this.stamp[j] < stamp_MAX) {
                    stamp_MAX = this.stamp[j];
                    n = j;
                }
            }
            if (n < 0) {
                break;
            }
            this.stamp[n] = ++this.nextStamp;
        }
        ++this.nextStamp;
    }
    
    private void initInternal() {
        this.fields = this.handleCreateFields();
        if (this.fields == null || this.fields.length < 23 || this.fields.length > 32) {
            throw new IllegalStateException("Invalid fields[]");
        }
        this.stamp = new int[this.fields.length];
        int internalSetMask = 4718695;
        for (int i = 23; i < this.fields.length; ++i) {
            internalSetMask |= 1 << i;
        }
        this.internalSetMask = internalSetMask;
    }
    
    public static synchronized Calendar getInstance() {
        return getInstanceInternal(null, null);
    }
    
    public static synchronized Calendar getInstance(final TimeZone timeZone) {
        return getInstanceInternal(timeZone, null);
    }
    
    public static synchronized Calendar getInstance(final Locale locale) {
        return getInstanceInternal(null, ULocale.forLocale(locale));
    }
    
    public static synchronized Calendar getInstance(final ULocale uLocale) {
        return getInstanceInternal(null, uLocale);
    }
    
    public static synchronized Calendar getInstance(final TimeZone timeZone, final Locale locale) {
        return getInstanceInternal(timeZone, ULocale.forLocale(locale));
    }
    
    public static synchronized Calendar getInstance(final TimeZone timeZone, final ULocale uLocale) {
        return getInstanceInternal(timeZone, uLocale);
    }
    
    private static Calendar getInstanceInternal(TimeZone default1, ULocale default2) {
        if (default2 == null) {
            default2 = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        if (default1 == null) {
            default1 = TimeZone.getDefault();
        }
        final Calendar instance = getShim().createInstance(default2);
        instance.setTimeZone(default1);
        instance.setTimeInMillis(System.currentTimeMillis());
        return instance;
    }
    
    private static int getCalendarTypeForLocale(final ULocale uLocale) {
        final String calendarType = CalendarUtil.getCalendarType(uLocale);
        if (calendarType != null) {
            final String lowerCase = calendarType.toLowerCase(Locale.ENGLISH);
            for (int i = 0; i < Calendar.calTypes.length; ++i) {
                if (lowerCase.equals(Calendar.calTypes[i])) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static Locale[] getAvailableLocales() {
        if (Calendar.shim == null) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return getShim().getAvailableLocales();
    }
    
    public static ULocale[] getAvailableULocales() {
        if (Calendar.shim == null) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return getShim().getAvailableULocales();
    }
    
    private static CalendarShim getShim() {
        if (Calendar.shim == null) {
            try {
                Calendar.shim = (CalendarShim)Class.forName("com.ibm.icu.util.CalendarServiceShim").newInstance();
            }
            catch (MissingResourceException ex) {
                throw ex;
            }
            catch (Exception ex2) {
                throw new RuntimeException(ex2.getMessage());
            }
        }
        return Calendar.shim;
    }
    
    static Calendar createInstance(final ULocale uLocale) {
        final TimeZone default1 = TimeZone.getDefault();
        int calendarTypeForLocale = getCalendarTypeForLocale(uLocale);
        if (calendarTypeForLocale == -1) {
            calendarTypeForLocale = 0;
        }
        Calendar calendar = null;
        switch (calendarTypeForLocale) {
            case 0: {
                calendar = new GregorianCalendar(default1, uLocale);
                break;
            }
            case 1: {
                calendar = new JapaneseCalendar(default1, uLocale);
                break;
            }
            case 2: {
                calendar = new BuddhistCalendar(default1, uLocale);
                break;
            }
            case 3: {
                calendar = new TaiwanCalendar(default1, uLocale);
                break;
            }
            case 4: {
                calendar = new PersianCalendar(default1, uLocale);
                break;
            }
            case 5: {
                calendar = new IslamicCalendar(default1, uLocale);
                break;
            }
            case 6: {
                calendar = new IslamicCalendar(default1, uLocale);
                ((IslamicCalendar)calendar).setCivil(false);
                break;
            }
            case 7: {
                calendar = new HebrewCalendar(default1, uLocale);
                break;
            }
            case 8: {
                calendar = new ChineseCalendar(default1, uLocale);
                break;
            }
            case 9: {
                calendar = new IndianCalendar(default1, uLocale);
                break;
            }
            case 10: {
                calendar = new CopticCalendar(default1, uLocale);
                break;
            }
            case 11: {
                calendar = new EthiopicCalendar(default1, uLocale);
                break;
            }
            case 12: {
                calendar = new EthiopicCalendar(default1, uLocale);
                ((EthiopicCalendar)calendar).setAmeteAlemEra(true);
                break;
            }
            case 14: {
                calendar = new DangiCalendar(default1, uLocale);
                break;
            }
            case 13: {
                calendar = new GregorianCalendar(default1, uLocale);
                calendar.setFirstDayOfWeek(2);
                calendar.setMinimalDaysInFirstWeek(4);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown calendar type");
            }
        }
        return calendar;
    }
    
    static Object registerFactory(final CalendarFactory calendarFactory) {
        if (calendarFactory == null) {
            throw new IllegalArgumentException("factory must not be null");
        }
        return getShim().registerFactory(calendarFactory);
    }
    
    static boolean unregister(final Object o) {
        if (o == null) {
            throw new IllegalArgumentException("registryKey must not be null");
        }
        return Calendar.shim != null && Calendar.shim.unregister(o);
    }
    
    public static final String[] getKeywordValuesForLocale(final String s, final ULocale uLocale, final boolean b) {
        String s2 = uLocale.getCountry();
        if (s2.length() == 0) {
            s2 = ULocale.addLikelySubtags(uLocale).getCountry();
        }
        final ArrayList<String> list = new ArrayList<String>();
        final UResourceBundle value = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("calendarPreferenceData");
        UResourceBundle uResourceBundle;
        try {
            uResourceBundle = value.get(s2);
        }
        catch (MissingResourceException ex) {
            uResourceBundle = value.get("001");
        }
        final String[] stringArray = uResourceBundle.getStringArray();
        if (b) {
            return stringArray;
        }
        for (int i = 0; i < stringArray.length; ++i) {
            list.add(stringArray[i]);
        }
        for (int j = 0; j < Calendar.calTypes.length; ++j) {
            if (!list.contains(Calendar.calTypes[j])) {
                list.add(Calendar.calTypes[j]);
            }
        }
        return list.toArray(new String[list.size()]);
    }
    
    public final Date getTime() {
        return new Date(this.getTimeInMillis());
    }
    
    public final void setTime(final Date date) {
        this.setTimeInMillis(date.getTime());
    }
    
    public long getTimeInMillis() {
        if (!this.isTimeSet) {
            this.updateTime();
        }
        return this.time;
    }
    
    public void setTimeInMillis(long time) {
        if (time > 183882168921600000L) {
            if (!this.isLenient()) {
                throw new IllegalArgumentException("millis value greater than upper bounds for a Calendar : " + time);
            }
            time = 183882168921600000L;
        }
        else if (time < -184303902528000000L) {
            if (!this.isLenient()) {
                throw new IllegalArgumentException("millis value less than lower bounds for a Calendar : " + time);
            }
            time = -184303902528000000L;
        }
        this.time = time;
        final boolean b = false;
        this.areAllFieldsSet = b;
        this.areFieldsSet = b;
        final boolean b2 = true;
        this.areFieldsVirtuallySet = b2;
        this.isTimeSet = b2;
        for (int i = 0; i < this.fields.length; ++i) {
            this.fields[i] = (this.stamp[i] = 0);
        }
    }
    
    public final int get(final int n) {
        this.complete();
        return this.fields[n];
    }
    
    protected final int internalGet(final int n) {
        return this.fields[n];
    }
    
    protected final int internalGet(final int n, final int n2) {
        return (this.stamp[n] > 0) ? this.fields[n] : n2;
    }
    
    public final void set(final int n, final int n2) {
        if (this.areFieldsVirtuallySet) {
            this.computeFields();
        }
        this.fields[n] = n2;
        if (this.nextStamp == Calendar.STAMP_MAX) {
            this.recalculateStamp();
        }
        this.stamp[n] = this.nextStamp++;
        final boolean isTimeSet = false;
        this.areFieldsVirtuallySet = isTimeSet;
        this.areFieldsSet = isTimeSet;
        this.isTimeSet = isTimeSet;
    }
    
    public final void set(final int n, final int n2, final int n3) {
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }
    
    public final void set(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
    }
    
    public final void set(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }
    
    public final void clear() {
        for (int i = 0; i < this.fields.length; ++i) {
            this.fields[i] = (this.stamp[i] = 0);
        }
        final boolean b = false;
        this.areFieldsVirtuallySet = b;
        this.areAllFieldsSet = b;
        this.areFieldsSet = b;
        this.isTimeSet = b;
    }
    
    public final void clear(final int n) {
        if (this.areFieldsVirtuallySet) {
            this.computeFields();
        }
        this.fields[n] = 0;
        this.stamp[n] = 0;
        final boolean b = false;
        this.areFieldsVirtuallySet = b;
        this.areAllFieldsSet = b;
        this.areFieldsSet = b;
        this.isTimeSet = b;
    }
    
    public final boolean isSet(final int n) {
        return this.areFieldsVirtuallySet || this.stamp[n] != 0;
    }
    
    protected void complete() {
        if (!this.isTimeSet) {
            this.updateTime();
        }
        if (!this.areFieldsSet) {
            this.computeFields();
            this.areFieldsSet = true;
            this.areAllFieldsSet = true;
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final Calendar calendar = (Calendar)o;
        return this.isEquivalentTo(calendar) && this.getTimeInMillis() == calendar.getTime().getTime();
    }
    
    public boolean isEquivalentTo(final Calendar calendar) {
        return this.getClass() == calendar.getClass() && this.isLenient() == calendar.isLenient() && this.getFirstDayOfWeek() == calendar.getFirstDayOfWeek() && this.getMinimalDaysInFirstWeek() == calendar.getMinimalDaysInFirstWeek() && this.getTimeZone().equals(calendar.getTimeZone()) && this.getRepeatedWallTimeOption() == calendar.getRepeatedWallTimeOption() && this.getSkippedWallTimeOption() == calendar.getSkippedWallTimeOption();
    }
    
    @Override
    public int hashCode() {
        return (this.lenient ? 1 : 0) | this.firstDayOfWeek << 1 | this.minimalDaysInFirstWeek << 4 | this.repeatedWallTime << 7 | this.skippedWallTime << 9 | this.zone.hashCode() << 11;
    }
    
    private long compare(final Object o) {
        long n;
        if (o instanceof Calendar) {
            n = ((Calendar)o).getTimeInMillis();
        }
        else {
            if (!(o instanceof Date)) {
                throw new IllegalArgumentException(o + "is not a Calendar or Date");
            }
            n = ((Date)o).getTime();
        }
        return this.getTimeInMillis() - n;
    }
    
    public boolean before(final Object o) {
        return this.compare(o) < 0L;
    }
    
    public boolean after(final Object o) {
        return this.compare(o) > 0L;
    }
    
    public int getActualMaximum(final int n) {
        int n2 = 0;
        switch (n) {
            case 5: {
                final Calendar calendar = (Calendar)this.clone();
                calendar.setLenient(true);
                calendar.prepareGetActual(n, false);
                n2 = this.handleGetMonthLength(calendar.get(19), calendar.get(2));
                break;
            }
            case 6: {
                final Calendar calendar2 = (Calendar)this.clone();
                calendar2.setLenient(true);
                calendar2.prepareGetActual(n, false);
                n2 = this.handleGetYearLength(calendar2.get(19));
                break;
            }
            case 0:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 20:
            case 21: {
                n2 = this.getMaximum(n);
                break;
            }
            default: {
                n2 = this.getActualHelper(n, this.getLeastMaximum(n), this.getMaximum(n));
                break;
            }
        }
        return n2;
    }
    
    public int getActualMinimum(final int n) {
        int n2 = 0;
        switch (n) {
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 20:
            case 21: {
                n2 = this.getMinimum(n);
                break;
            }
            default: {
                n2 = this.getActualHelper(n, this.getGreatestMinimum(n), this.getMinimum(n));
                break;
            }
        }
        return n2;
    }
    
    protected void prepareGetActual(final int n, final boolean b) {
        this.set(21, 0);
        switch (n) {
            case 1:
            case 19: {
                this.set(6, this.getGreatestMinimum(6));
                break;
            }
            case 17: {
                this.set(3, this.getGreatestMinimum(3));
                break;
            }
            case 2: {
                this.set(5, this.getGreatestMinimum(5));
                break;
            }
            case 8: {
                this.set(5, 1);
                this.set(7, this.get(7));
                break;
            }
            case 3:
            case 4: {
                int firstDayOfWeek = this.firstDayOfWeek;
                if (b) {
                    firstDayOfWeek = (firstDayOfWeek + 6) % 7;
                    if (firstDayOfWeek < 1) {
                        firstDayOfWeek += 7;
                    }
                }
                this.set(7, firstDayOfWeek);
                break;
            }
        }
        this.set(n, this.getGreatestMinimum(n));
    }
    
    private int getActualHelper(final int n, int n2, final int n3) {
        if (n2 == n3) {
            return n2;
        }
        final int n4 = (n3 > n2) ? 1 : -1;
        final Calendar calendar = (Calendar)this.clone();
        calendar.complete();
        calendar.setLenient(true);
        calendar.prepareGetActual(n, n4 < 0);
        calendar.set(n, n2);
        if (calendar.get(n) != n2 && n != 4 && n4 > 0) {
            return n2;
        }
        int n5 = n2;
        do {
            n2 += n4;
            calendar.add(n, n4);
            if (calendar.get(n) != n2) {
                break;
            }
        } while ((n5 = n2) != n3);
        return n5;
    }
    
    public final void roll(final int n, final boolean b) {
        this.roll(n, b ? 1 : -1);
    }
    
    public void roll(final int n, int n2) {
        if (n2 == 0) {
            return;
        }
        this.complete();
        switch (n) {
            case 0:
            case 5:
            case 9:
            case 12:
            case 13:
            case 14:
            case 21: {
                final int actualMinimum = this.getActualMinimum(n);
                final int n3 = this.getActualMaximum(n) - actualMinimum + 1;
                int n4 = (this.internalGet(n) + n2 - actualMinimum) % n3;
                if (n4 < 0) {
                    n4 += n3;
                }
                this.set(n, n4 + actualMinimum);
            }
            case 10:
            case 11: {
                final long timeInMillis = this.getTimeInMillis();
                final int internalGet = this.internalGet(n);
                final int maximum = this.getMaximum(n);
                int n5 = (internalGet + n2) % (maximum + 1);
                if (n5 < 0) {
                    n5 += maximum + 1;
                }
                this.setTimeInMillis(timeInMillis + 3600000L * (n5 - (long)internalGet));
            }
            case 2: {
                final int actualMaximum = this.getActualMaximum(2);
                int n6 = (this.internalGet(2) + n2) % (actualMaximum + 1);
                if (n6 < 0) {
                    n6 += actualMaximum + 1;
                }
                this.set(2, n6);
                this.pinField(5);
            }
            case 1:
            case 17: {
                boolean b = false;
                final int value = this.get(0);
                if (value == 0) {
                    final String type = this.getType();
                    if (type.equals("gregorian") || type.equals("roc") || type.equals("coptic")) {
                        n2 = -n2;
                        b = true;
                    }
                }
                int n7 = this.internalGet(n) + n2;
                if (value > 0 || n7 >= 1) {
                    final int actualMaximum2 = this.getActualMaximum(n);
                    if (actualMaximum2 < 32768) {
                        if (n7 < 1) {
                            n7 = actualMaximum2 - -n7 % actualMaximum2;
                        }
                        else if (n7 > actualMaximum2) {
                            n7 = (n7 - 1) % actualMaximum2 + 1;
                        }
                    }
                    else if (n7 < 1) {
                        n7 = 1;
                    }
                }
                else if (b) {
                    n7 = 1;
                }
                this.set(n, n7);
                this.pinField(2);
                this.pinField(5);
            }
            case 19: {
                this.set(n, this.internalGet(n) + n2);
                this.pinField(2);
                this.pinField(5);
            }
            case 4: {
                int n8 = this.internalGet(7) - this.getFirstDayOfWeek();
                if (n8 < 0) {
                    n8 += 7;
                }
                int n9 = (n8 - this.internalGet(5) + 1) % 7;
                if (n9 < 0) {
                    n9 += 7;
                }
                int n10;
                if (7 - n9 < this.getMinimalDaysInFirstWeek()) {
                    n10 = 8 - n9;
                }
                else {
                    n10 = 1 - n9;
                }
                final int actualMaximum3 = this.getActualMaximum(5);
                final int n11 = actualMaximum3 + 7 - (actualMaximum3 - this.internalGet(5) + n8) % 7 - n10;
                int n12 = (this.internalGet(5) + n2 * 7 - n10) % n11;
                if (n12 < 0) {
                    n12 += n11;
                }
                int n13 = n12 + n10;
                if (n13 < 1) {
                    n13 = 1;
                }
                if (n13 > actualMaximum3) {
                    n13 = actualMaximum3;
                }
                this.set(5, n13);
            }
            case 3: {
                int n14 = this.internalGet(7) - this.getFirstDayOfWeek();
                if (n14 < 0) {
                    n14 += 7;
                }
                int n15 = (n14 - this.internalGet(6) + 1) % 7;
                if (n15 < 0) {
                    n15 += 7;
                }
                int n16;
                if (7 - n15 < this.getMinimalDaysInFirstWeek()) {
                    n16 = 8 - n15;
                }
                else {
                    n16 = 1 - n15;
                }
                final int actualMaximum4 = this.getActualMaximum(6);
                final int n17 = actualMaximum4 + 7 - (actualMaximum4 - this.internalGet(6) + n14) % 7 - n16;
                int n18 = (this.internalGet(6) + n2 * 7 - n16) % n17;
                if (n18 < 0) {
                    n18 += n17;
                }
                int n19 = n18 + n16;
                if (n19 < 1) {
                    n19 = 1;
                }
                if (n19 > actualMaximum4) {
                    n19 = actualMaximum4;
                }
                this.set(6, n19);
                this.clear(2);
            }
            case 6: {
                final long n20 = n2 * 86400000L;
                final long n21 = this.time - (this.internalGet(6) - 1) * 86400000L;
                final int actualMaximum5 = this.getActualMaximum(6);
                this.time = (this.time + n20 - n21) % (actualMaximum5 * 86400000L);
                if (this.time < 0L) {
                    this.time += actualMaximum5 * 86400000L;
                }
                this.setTimeInMillis(this.time + n21);
            }
            case 7:
            case 18: {
                final long n22 = n2 * 86400000L;
                int n23 = this.internalGet(n) - ((n == 7) ? this.getFirstDayOfWeek() : 1);
                if (n23 < 0) {
                    n23 += 7;
                }
                final long n24 = this.time - n23 * 86400000L;
                this.time = (this.time + n22 - n24) % 604800000L;
                if (this.time < 0L) {
                    this.time += 604800000L;
                }
                this.setTimeInMillis(this.time + n24);
            }
            case 8: {
                final long n25 = n2 * 604800000L;
                final int n26 = (this.internalGet(5) - 1) / 7;
                final int n27 = (this.getActualMaximum(5) - this.internalGet(5)) / 7;
                final long n28 = this.time - n26 * 604800000L;
                final long n29 = 604800000L * (n26 + n27 + 1);
                this.time = (this.time + n25 - n28) % n29;
                if (this.time < 0L) {
                    this.time += n29;
                }
                this.setTimeInMillis(this.time + n28);
            }
            case 20: {
                this.set(n, this.internalGet(n) + n2);
            }
            default: {
                throw new IllegalArgumentException("Calendar.roll(" + this.fieldName(n) + ") not supported");
            }
        }
    }
    
    public void add(final int n, int n2) {
        if (n2 == 0) {
            return;
        }
        long n3 = n2;
        boolean b = true;
        Label_0188: {
            switch (n) {
                case 0: {
                    this.set(n, this.get(n) + n2);
                    this.pinField(0);
                    return;
                }
                case 1:
                case 17: {
                    if (this.get(0) != 0) {
                        break Label_0188;
                    }
                    final String type = this.getType();
                    if (type.equals("gregorian") || type.equals("roc") || type.equals("coptic")) {
                        n2 = -n2;
                    }
                    break Label_0188;
                }
                case 2:
                case 19: {
                    final boolean lenient = this.isLenient();
                    this.setLenient(true);
                    this.set(n, this.get(n) + n2);
                    this.pinField(5);
                    if (!lenient) {
                        this.complete();
                        this.setLenient(lenient);
                    }
                    return;
                }
                case 3:
                case 4:
                case 8: {
                    n3 *= 604800000L;
                    break;
                }
                case 9: {
                    n3 *= 43200000L;
                    break;
                }
                case 5:
                case 6:
                case 7:
                case 18:
                case 20: {
                    n3 *= 86400000L;
                    break;
                }
                case 10:
                case 11: {
                    n3 *= 3600000L;
                    b = false;
                    break;
                }
                case 12: {
                    n3 *= 60000L;
                    b = false;
                    break;
                }
                case 13: {
                    n3 *= 1000L;
                    b = false;
                    break;
                }
                case 14:
                case 21: {
                    b = false;
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Calendar.add(" + this.fieldName(n) + ") not supported");
                }
            }
        }
        int n4 = 0;
        int internalGet = 0;
        if (b) {
            n4 = this.get(16) + this.get(15);
            internalGet = this.internalGet(11);
        }
        this.setTimeInMillis(this.getTimeInMillis() + n3);
        if (b) {
            final int n5 = this.get(16) + this.get(15);
            if (n5 != n4) {
                final long n6 = (n4 - n5) % 86400000L;
                if (n6 != 0L) {
                    final long time = this.time;
                    this.setTimeInMillis(this.time + n6);
                    if (this.get(11) != internalGet) {
                        this.setTimeInMillis(time);
                    }
                }
            }
        }
    }
    
    public String getDisplayName(final Locale locale) {
        return this.getClass().getName();
    }
    
    public String getDisplayName(final ULocale uLocale) {
        return this.getClass().getName();
    }
    
    public int compareTo(final Calendar calendar) {
        final long n = this.getTimeInMillis() - calendar.getTimeInMillis();
        return (n < 0L) ? -1 : (n > 0L);
    }
    
    public DateFormat getDateTimeFormat(final int n, final int n2, final Locale locale) {
        return formatHelper(this, ULocale.forLocale(locale), n, n2);
    }
    
    public DateFormat getDateTimeFormat(final int n, final int n2, final ULocale uLocale) {
        return formatHelper(this, uLocale, n, n2);
    }
    
    protected DateFormat handleGetDateFormat(final String s, final Locale locale) {
        return this.handleGetDateFormat(s, null, ULocale.forLocale(locale));
    }
    
    protected DateFormat handleGetDateFormat(final String s, final String s2, final Locale locale) {
        return this.handleGetDateFormat(s, s2, ULocale.forLocale(locale));
    }
    
    protected DateFormat handleGetDateFormat(final String s, final ULocale uLocale) {
        return this.handleGetDateFormat(s, null, uLocale);
    }
    
    protected DateFormat handleGetDateFormat(final String s, final String s2, final ULocale uLocale) {
        final FormatConfiguration formatConfiguration = new FormatConfiguration(null);
        FormatConfiguration.access$102(formatConfiguration, s);
        FormatConfiguration.access$202(formatConfiguration, s2);
        FormatConfiguration.access$302(formatConfiguration, new DateFormatSymbols(this, uLocale));
        FormatConfiguration.access$402(formatConfiguration, uLocale);
        FormatConfiguration.access$502(formatConfiguration, this);
        return SimpleDateFormat.getInstance(formatConfiguration);
    }
    
    private static DateFormat formatHelper(final Calendar calendar, final ULocale uLocale, final int n, final int n2) {
        final PatternData access$600 = PatternData.access$600(calendar, uLocale);
        String mergeOverrideStrings = null;
        String format;
        if (n2 >= 0 && n >= 0) {
            format = MessageFormat.format(PatternData.access$700(access$600, n), PatternData.access$800(access$600)[n2], PatternData.access$800(access$600)[n + 4]);
            if (PatternData.access$900(access$600) != null) {
                mergeOverrideStrings = mergeOverrideStrings(PatternData.access$800(access$600)[n + 4], PatternData.access$800(access$600)[n2], PatternData.access$900(access$600)[n + 4], PatternData.access$900(access$600)[n2]);
            }
        }
        else if (n2 >= 0) {
            format = PatternData.access$800(access$600)[n2];
            if (PatternData.access$900(access$600) != null) {
                mergeOverrideStrings = PatternData.access$900(access$600)[n2];
            }
        }
        else {
            if (n < 0) {
                throw new IllegalArgumentException("No date or time style specified");
            }
            format = PatternData.access$800(access$600)[n + 4];
            if (PatternData.access$900(access$600) != null) {
                mergeOverrideStrings = PatternData.access$900(access$600)[n + 4];
            }
        }
        final DateFormat handleGetDateFormat = calendar.handleGetDateFormat(format, mergeOverrideStrings, uLocale);
        handleGetDateFormat.setCalendar(calendar);
        return handleGetDateFormat;
    }
    
    @Deprecated
    public static String getDateTimePattern(final Calendar calendar, final ULocale uLocale, final int n) {
        return PatternData.access$700(PatternData.access$600(calendar, uLocale), n);
    }
    
    private static String mergeOverrideStrings(final String s, final String s2, final String s3, final String s4) {
        if (s3 == null && s4 == null) {
            return null;
        }
        if (s3 == null) {
            return expandOverride(s2, s4);
        }
        if (s4 == null) {
            return expandOverride(s, s3);
        }
        if (s3.equals(s4)) {
            return s3;
        }
        return expandOverride(s, s3) + ";" + expandOverride(s2, s4);
    }
    
    private static String expandOverride(final String s, final String s2) {
        if (s2.indexOf(61) >= 0) {
            return s2;
        }
        boolean b = false;
        char c = ' ';
        final StringBuilder sb = new StringBuilder();
        final StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(s);
        for (char c2 = stringCharacterIterator.first(); c2 != '\uffff'; c2 = stringCharacterIterator.next()) {
            if (c2 == '\'') {
                b = !b;
                c = c2;
            }
            else {
                if (!b && c2 != c) {
                    if (sb.length() > 0) {
                        sb.append(";");
                    }
                    sb.append(c2);
                    sb.append("=");
                    sb.append(s2);
                }
                c = c2;
            }
        }
        return sb.toString();
    }
    
    protected void pinField(final int n) {
        final int actualMaximum = this.getActualMaximum(n);
        final int actualMinimum = this.getActualMinimum(n);
        if (this.fields[n] > actualMaximum) {
            this.set(n, actualMaximum);
        }
        else if (this.fields[n] < actualMinimum) {
            this.set(n, actualMinimum);
        }
    }
    
    protected int weekNumber(final int n, final int n2, final int n3) {
        int n4 = (n3 - this.getFirstDayOfWeek() - n2 + 1) % 7;
        if (n4 < 0) {
            n4 += 7;
        }
        int n5 = (n + n4 - 1) / 7;
        if (7 - n4 >= this.getMinimalDaysInFirstWeek()) {
            ++n5;
        }
        return n5;
    }
    
    protected final int weekNumber(final int n, final int n2) {
        return this.weekNumber(n, n, n2);
    }
    
    public int fieldDifference(final Date date, final int n) {
        int n2 = 0;
        final long timeInMillis = this.getTimeInMillis();
        final long time = date.getTime();
        if (timeInMillis < time) {
            int n3 = 1;
            while (true) {
                this.setTimeInMillis(timeInMillis);
                this.add(n, n3);
                final long timeInMillis2 = this.getTimeInMillis();
                if (timeInMillis2 == time) {
                    return n3;
                }
                if (timeInMillis2 > time) {
                    while (n3 - n2 > 1) {
                        final int n4 = n2 + (n3 - n2) / 2;
                        this.setTimeInMillis(timeInMillis);
                        this.add(n, n4);
                        final long timeInMillis3 = this.getTimeInMillis();
                        if (timeInMillis3 == time) {
                            return n4;
                        }
                        if (timeInMillis3 > time) {
                            n3 = n4;
                        }
                        else {
                            n2 = n4;
                        }
                    }
                    break;
                }
                if (n3 >= Integer.MAX_VALUE) {
                    throw new RuntimeException();
                }
                n2 = n3;
                n3 <<= 1;
                if (n3 >= 0) {
                    continue;
                }
                n3 = Integer.MAX_VALUE;
            }
        }
        else if (timeInMillis > time) {
            int n5 = -1;
            while (true) {
                this.setTimeInMillis(timeInMillis);
                this.add(n, n5);
                final long timeInMillis4 = this.getTimeInMillis();
                if (timeInMillis4 == time) {
                    return n5;
                }
                if (timeInMillis4 < time) {
                    while (n2 - n5 > 1) {
                        final int n6 = n2 + (n5 - n2) / 2;
                        this.setTimeInMillis(timeInMillis);
                        this.add(n, n6);
                        final long timeInMillis5 = this.getTimeInMillis();
                        if (timeInMillis5 == time) {
                            return n6;
                        }
                        if (timeInMillis5 < time) {
                            n5 = n6;
                        }
                        else {
                            n2 = n6;
                        }
                    }
                    break;
                }
                n2 = n5;
                n5 <<= 1;
                if (n5 == 0) {
                    throw new RuntimeException();
                }
            }
        }
        this.setTimeInMillis(timeInMillis);
        this.add(n, n2);
        return n2;
    }
    
    public void setTimeZone(final TimeZone zone) {
        this.zone = zone;
        this.areFieldsSet = false;
    }
    
    public TimeZone getTimeZone() {
        return this.zone;
    }
    
    public void setLenient(final boolean lenient) {
        this.lenient = lenient;
    }
    
    public boolean isLenient() {
        return this.lenient;
    }
    
    public void setRepeatedWallTimeOption(final int repeatedWallTime) {
        if (repeatedWallTime != 0 && repeatedWallTime != 1) {
            throw new IllegalArgumentException("Illegal repeated wall time option - " + repeatedWallTime);
        }
        this.repeatedWallTime = repeatedWallTime;
    }
    
    public int getRepeatedWallTimeOption() {
        return this.repeatedWallTime;
    }
    
    public void setSkippedWallTimeOption(final int skippedWallTime) {
        if (skippedWallTime != 0 && skippedWallTime != 1 && skippedWallTime != 2) {
            throw new IllegalArgumentException("Illegal skipped wall time option - " + skippedWallTime);
        }
        this.skippedWallTime = skippedWallTime;
    }
    
    public int getSkippedWallTimeOption() {
        return this.skippedWallTime;
    }
    
    public void setFirstDayOfWeek(final int firstDayOfWeek) {
        if (this.firstDayOfWeek != firstDayOfWeek) {
            if (firstDayOfWeek < 1 || firstDayOfWeek > 7) {
                throw new IllegalArgumentException("Invalid day of week");
            }
            this.firstDayOfWeek = firstDayOfWeek;
            this.areFieldsSet = false;
        }
    }
    
    public int getFirstDayOfWeek() {
        return this.firstDayOfWeek;
    }
    
    public void setMinimalDaysInFirstWeek(int minimalDaysInFirstWeek) {
        if (minimalDaysInFirstWeek < 1) {
            minimalDaysInFirstWeek = 1;
        }
        else if (minimalDaysInFirstWeek > 7) {
            minimalDaysInFirstWeek = 7;
        }
        if (this.minimalDaysInFirstWeek != minimalDaysInFirstWeek) {
            this.minimalDaysInFirstWeek = minimalDaysInFirstWeek;
            this.areFieldsSet = false;
        }
    }
    
    public int getMinimalDaysInFirstWeek() {
        return this.minimalDaysInFirstWeek;
    }
    
    protected abstract int handleGetLimit(final int p0, final int p1);
    
    protected int getLimit(final int n, final int n2) {
        switch (n) {
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 20:
            case 21:
            case 22: {
                return Calendar.LIMITS[n][n2];
            }
            case 4: {
                int n3;
                if (n2 == 0) {
                    n3 = ((this.getMinimalDaysInFirstWeek() == 1) ? 1 : 0);
                }
                else if (n2 == 1) {
                    n3 = 1;
                }
                else {
                    final int minimalDaysInFirstWeek = this.getMinimalDaysInFirstWeek();
                    final int handleGetLimit = this.handleGetLimit(5, n2);
                    if (n2 == 2) {
                        n3 = (handleGetLimit + (7 - minimalDaysInFirstWeek)) / 7;
                    }
                    else {
                        n3 = (handleGetLimit + 6 + (7 - minimalDaysInFirstWeek)) / 7;
                    }
                }
                return n3;
            }
            default: {
                return this.handleGetLimit(n, n2);
            }
        }
    }
    
    public final int getMinimum(final int n) {
        return this.getLimit(n, 0);
    }
    
    public final int getMaximum(final int n) {
        return this.getLimit(n, 3);
    }
    
    public final int getGreatestMinimum(final int n) {
        return this.getLimit(n, 1);
    }
    
    public final int getLeastMaximum(final int n) {
        return this.getLimit(n, 2);
    }
    
    public int getDayOfWeekType(final int n) {
        if (n < 1 || n > 7) {
            throw new IllegalArgumentException("Invalid day of week");
        }
        if (this.weekendOnset < this.weekendCease) {
            if (n < this.weekendOnset || n > this.weekendCease) {
                return 0;
            }
        }
        else if (n > this.weekendCease && n < this.weekendOnset) {
            return 0;
        }
        if (n == this.weekendOnset) {
            return (this.weekendOnsetMillis == 0) ? 1 : 2;
        }
        if (n == this.weekendCease) {
            return (this.weekendCeaseMillis == 0) ? 0 : 3;
        }
        return 1;
    }
    
    public int getWeekendTransition(final int n) {
        if (n == this.weekendOnset) {
            return this.weekendOnsetMillis;
        }
        if (n == this.weekendCease) {
            return this.weekendCeaseMillis;
        }
        throw new IllegalArgumentException("Not weekend transition day");
    }
    
    public boolean isWeekend(final Date time) {
        this.setTime(time);
        return this.isWeekend();
    }
    
    public boolean isWeekend() {
        final int value = this.get(7);
        final int dayOfWeekType = this.getDayOfWeekType(value);
        switch (dayOfWeekType) {
            case 0: {
                return false;
            }
            case 1: {
                return true;
            }
            default: {
                final int n = this.internalGet(14) + 1000 * (this.internalGet(13) + 60 * (this.internalGet(12) + 60 * this.internalGet(11)));
                final int weekendTransition = this.getWeekendTransition(value);
                return (dayOfWeekType == 2) ? (n >= weekendTransition) : (n < weekendTransition);
            }
        }
    }
    
    public Object clone() {
        try {
            final Calendar calendar = (Calendar)super.clone();
            calendar.fields = new int[this.fields.length];
            calendar.stamp = new int[this.fields.length];
            System.arraycopy(this.fields, 0, calendar.fields, 0, this.fields.length);
            System.arraycopy(this.stamp, 0, calendar.stamp, 0, this.fields.length);
            calendar.zone = (TimeZone)this.zone.clone();
            return calendar;
        }
        catch (CloneNotSupportedException ex) {
            throw new IllegalStateException();
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName());
        sb.append("[time=");
        sb.append(this.isTimeSet ? String.valueOf(this.time) : "?");
        sb.append(",areFieldsSet=");
        sb.append(this.areFieldsSet);
        sb.append(",areAllFieldsSet=");
        sb.append(this.areAllFieldsSet);
        sb.append(",lenient=");
        sb.append(this.lenient);
        sb.append(",zone=");
        sb.append(this.zone);
        sb.append(",firstDayOfWeek=");
        sb.append(this.firstDayOfWeek);
        sb.append(",minimalDaysInFirstWeek=");
        sb.append(this.minimalDaysInFirstWeek);
        sb.append(",repeatedWallTime=");
        sb.append(this.repeatedWallTime);
        sb.append(",skippedWallTime=");
        sb.append(this.skippedWallTime);
        for (int i = 0; i < this.fields.length; ++i) {
            sb.append(',').append(this.fieldName(i)).append('=');
            sb.append(this.isSet(i) ? String.valueOf(this.fields[i]) : "?");
        }
        sb.append(']');
        return sb.toString();
    }
    
    private void setWeekData(final ULocale uLocale) {
        WeekData weekData = (WeekData)Calendar.cachedLocaleData.get(uLocale);
        if (weekData == null) {
            final CalendarData calendarData = new CalendarData(uLocale, this.getType());
            final ULocale minimizeSubtags = ULocale.minimizeSubtags(calendarData.getULocale());
            ULocale uLocale2;
            if (minimizeSubtags.getCountry().length() > 0) {
                uLocale2 = minimizeSubtags;
            }
            else {
                final ULocale addLikelySubtags = ULocale.addLikelySubtags(minimizeSubtags);
                final StringBuilder sb = new StringBuilder();
                sb.append(minimizeSubtags.getLanguage());
                if (minimizeSubtags.getScript().length() > 0) {
                    sb.append("_" + minimizeSubtags.getScript());
                }
                if (addLikelySubtags.getCountry().length() > 0) {
                    sb.append("_" + addLikelySubtags.getCountry());
                }
                if (minimizeSubtags.getVariant().length() > 0) {
                    sb.append("_" + minimizeSubtags.getVariant());
                }
                uLocale2 = new ULocale(sb.toString());
            }
            final UResourceBundle value = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("weekData");
            UResourceBundle uResourceBundle;
            try {
                uResourceBundle = value.get(uLocale2.getCountry());
            }
            catch (MissingResourceException ex) {
                uResourceBundle = value.get("001");
            }
            final int[] intVector = uResourceBundle.getIntVector();
            weekData = new WeekData(intVector[0], intVector[1], intVector[2], intVector[3], intVector[4], intVector[5], calendarData.getULocale());
            Calendar.cachedLocaleData.put(uLocale, weekData);
        }
        this.setFirstDayOfWeek(weekData.firstDayOfWeek);
        this.setMinimalDaysInFirstWeek(weekData.minimalDaysInFirstWeek);
        this.weekendOnset = weekData.weekendOnset;
        this.weekendOnsetMillis = weekData.weekendOnsetMillis;
        this.weekendCease = weekData.weekendCease;
        this.weekendCeaseMillis = weekData.weekendCeaseMillis;
        final ULocale actualLocale = weekData.actualLocale;
        this.setLocale(actualLocale, actualLocale);
    }
    
    private void updateTime() {
        this.computeTime();
        if (this.isLenient() || !this.areAllFieldsSet) {
            this.areFieldsSet = false;
        }
        this.isTimeSet = true;
        this.areFieldsVirtuallySet = false;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        if (!this.isTimeSet) {
            try {
                this.updateTime();
            }
            catch (IllegalArgumentException ex) {}
        }
        objectOutputStream.defaultWriteObject();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.initInternal();
        this.isTimeSet = true;
        final boolean b = false;
        this.areAllFieldsSet = b;
        this.areFieldsSet = b;
        this.areFieldsVirtuallySet = true;
        this.nextStamp = 2;
    }
    
    protected void computeFields() {
        final int[] array = new int[2];
        this.getTimeZone().getOffset(this.time, false, array);
        final long n = this.time + array[0] + array[1];
        int internalSetMask = this.internalSetMask;
        for (int i = 0; i < this.fields.length; ++i) {
            if ((internalSetMask & 0x1) == 0x0) {
                this.stamp[i] = 1;
            }
            else {
                this.stamp[i] = 0;
            }
            internalSetMask >>= 1;
        }
        final long floorDivide = floorDivide(n, 86400000L);
        this.computeGregorianAndDOWFields(this.fields[20] = (int)floorDivide + 2440588);
        this.handleComputeFields(this.fields[20]);
        this.computeWeekFields();
        final int n2 = (int)(n - floorDivide * 86400000L);
        this.fields[21] = n2;
        this.fields[14] = n2 % 1000;
        final int n3 = n2 / 1000;
        this.fields[13] = n3 % 60;
        final int n4 = n3 / 60;
        this.fields[12] = n4 % 60;
        final int n5 = n4 / 60;
        this.fields[11] = n5;
        this.fields[9] = n5 / 12;
        this.fields[10] = n5 % 12;
        this.fields[15] = array[0];
        this.fields[16] = array[1];
    }
    
    private final void computeGregorianAndDOWFields(final int n) {
        this.computeGregorianFields(n);
        final int[] fields = this.fields;
        final int n2 = 7;
        final int julianDayToDayOfWeek = julianDayToDayOfWeek(n);
        fields[n2] = julianDayToDayOfWeek;
        int n3 = julianDayToDayOfWeek - this.getFirstDayOfWeek() + 1;
        if (n3 < 1) {
            n3 += 7;
        }
        this.fields[18] = n3;
    }
    
    protected final void computeGregorianFields(final int n) {
        final long n2 = n - 1721426;
        final int[] array = { 0 };
        final int floorDivide = floorDivide(n2, 146097, array);
        final int floorDivide2 = floorDivide(array[0], 36524, array);
        final int floorDivide3 = floorDivide(array[0], 1461, array);
        final int floorDivide4 = floorDivide(array[0], 365, array);
        int gregorianYear = 400 * floorDivide + 100 * floorDivide2 + 4 * floorDivide3 + floorDivide4;
        int n3 = array[0];
        if (floorDivide2 == 4 || floorDivide4 == 4) {
            n3 = 365;
        }
        else {
            ++gregorianYear;
        }
        final boolean b = (gregorianYear & 0x3) == 0x0 && (gregorianYear % 100 != 0 || gregorianYear % 400 == 0);
        int n4 = 0;
        if (n3 >= (b ? 60 : 59)) {
            n4 = (b ? 1 : 2);
        }
        final int gregorianMonth = (12 * (n3 + n4) + 6) / 367;
        final int gregorianDayOfMonth = n3 - Calendar.GREGORIAN_MONTH_COUNT[gregorianMonth][b ? 3 : 2] + 1;
        this.gregorianYear = gregorianYear;
        this.gregorianMonth = gregorianMonth;
        this.gregorianDayOfMonth = gregorianDayOfMonth;
        this.gregorianDayOfYear = n3 + 1;
    }
    
    private final void computeWeekFields() {
        final int n = this.fields[19];
        final int n2 = this.fields[7];
        final int n3 = this.fields[6];
        int n4 = n;
        final int n5 = (n2 + 7 - this.getFirstDayOfWeek()) % 7;
        final int n6 = (n2 - n3 + 7001 - this.getFirstDayOfWeek()) % 7;
        int weekNumber = (n3 - 1 + n6) / 7;
        if (7 - n6 >= this.getMinimalDaysInFirstWeek()) {
            ++weekNumber;
        }
        if (weekNumber == 0) {
            weekNumber = this.weekNumber(n3 + this.handleGetYearLength(n - 1), n2);
            --n4;
        }
        else {
            final int handleGetYearLength = this.handleGetYearLength(n);
            if (n3 >= handleGetYearLength - 5) {
                int n7 = (n5 + handleGetYearLength - n3) % 7;
                if (n7 < 0) {
                    n7 += 7;
                }
                if (6 - n7 >= this.getMinimalDaysInFirstWeek() && n3 + 7 - n5 > handleGetYearLength) {
                    weekNumber = 1;
                    ++n4;
                }
            }
        }
        this.fields[3] = weekNumber;
        this.fields[17] = n4;
        final int n8 = this.fields[5];
        this.fields[4] = this.weekNumber(n8, n2);
        this.fields[8] = (n8 - 1) / 7 + 1;
    }
    
    protected int resolveFields(final int[][][] array) {
        int n = -1;
        for (int n2 = 0; n2 < array.length && n < 0; ++n2) {
            final int[][] array2 = array[n2];
            int n3 = 0;
            int i = 0;
        Label_0028:
            while (i < array2.length) {
                final int[] array3 = array2[i];
                int max = 0;
                while (true) {
                    for (int j = (array3[0] >= 32) ? 1 : 0; j < array3.length; ++j) {
                        final int n4 = this.stamp[array3[j]];
                        if (n4 == 0) {
                            ++i;
                            continue Label_0028;
                        }
                        max = Math.max(max, n4);
                    }
                    if (max <= n3) {
                        continue;
                    }
                    int n5 = array3[0];
                    if (n5 >= 32) {
                        n5 &= 0x1F;
                        if (n5 != 5 || this.stamp[4] < this.stamp[n5]) {
                            n = n5;
                        }
                    }
                    else {
                        n = n5;
                    }
                    if (n == n5) {
                        n3 = max;
                    }
                    continue;
                }
            }
        }
        return (n >= 32) ? (n & 0x1F) : n;
    }
    
    protected int newestStamp(final int n, final int n2, final int n3) {
        int n4 = n3;
        for (int i = n; i <= n2; ++i) {
            if (this.stamp[i] > n4) {
                n4 = this.stamp[i];
            }
        }
        return n4;
    }
    
    protected final int getStamp(final int n) {
        return this.stamp[n];
    }
    
    protected int newerField(final int n, final int n2) {
        if (this.stamp[n2] > this.stamp[n]) {
            return n2;
        }
        return n;
    }
    
    protected void validateFields() {
        for (int i = 0; i < this.fields.length; ++i) {
            if (this.stamp[i] >= 2) {
                this.validateField(i);
            }
        }
    }
    
    protected void validateField(final int n) {
        switch (n) {
            case 5: {
                this.validateField(n, 1, this.handleGetMonthLength(this.handleGetExtendedYear(), this.internalGet(2)));
                break;
            }
            case 6: {
                this.validateField(n, 1, this.handleGetYearLength(this.handleGetExtendedYear()));
                break;
            }
            case 8: {
                if (this.internalGet(n) == 0) {
                    throw new IllegalArgumentException("DAY_OF_WEEK_IN_MONTH cannot be zero");
                }
                this.validateField(n, this.getMinimum(n), this.getMaximum(n));
                break;
            }
            default: {
                this.validateField(n, this.getMinimum(n), this.getMaximum(n));
                break;
            }
        }
    }
    
    protected final void validateField(final int n, final int n2, final int n3) {
        final int n4 = this.fields[n];
        if (n4 < n2 || n4 > n3) {
            throw new IllegalArgumentException(this.fieldName(n) + '=' + n4 + ", valid range=" + n2 + ".." + n3);
        }
    }
    
    protected void computeTime() {
        if (!this.isLenient()) {
            this.validateFields();
        }
        final long julianDayToMillis = julianDayToMillis(this.computeJulianDay());
        int n;
        if (this.stamp[21] >= 2 && this.newestStamp(9, 14, 0) <= this.stamp[21]) {
            n = this.internalGet(21);
        }
        else {
            n = this.computeMillisInDay();
        }
        if (this.stamp[15] >= 2 || this.stamp[16] >= 2) {
            this.time = julianDayToMillis + n - (this.internalGet(15) + this.internalGet(16));
        }
        else if (!this.lenient || this.skippedWallTime == 2) {
            final int computeZoneOffset = this.computeZoneOffset(julianDayToMillis, n);
            final long time = julianDayToMillis + n - computeZoneOffset;
            if (computeZoneOffset != this.zone.getOffset(time)) {
                if (!this.lenient) {
                    throw new IllegalArgumentException("The specified wall time does not exist due to time zone offset transition.");
                }
                assert this.skippedWallTime == 2 : this.skippedWallTime;
                if (this.zone instanceof BasicTimeZone) {
                    final TimeZoneTransition previousTransition = ((BasicTimeZone)this.zone).getPreviousTransition(time, true);
                    if (previousTransition == null) {
                        throw new RuntimeException("Could not locate previous zone transition");
                    }
                    this.time = previousTransition.getTime();
                }
                else {
                    Long n2 = this.getPreviousZoneTransitionTime(this.zone, time, 7200000L);
                    if (n2 == null) {
                        n2 = this.getPreviousZoneTransitionTime(this.zone, time, 108000000L);
                        if (n2 == null) {
                            throw new RuntimeException("Could not locate previous zone transition within 30 hours from " + time);
                        }
                    }
                    this.time = n2;
                }
            }
            else {
                this.time = time;
            }
        }
        else {
            this.time = julianDayToMillis + n - this.computeZoneOffset(julianDayToMillis, n);
        }
    }
    
    private Long getPreviousZoneTransitionTime(final TimeZone timeZone, final long n, final long n2) {
        assert n2 > 0L;
        final long n3 = n - n2 - 1L;
        final int offset = timeZone.getOffset(n);
        if (offset == timeZone.getOffset(n3)) {
            return null;
        }
        return this.findPreviousZoneTransitionTime(timeZone, offset, n, n3);
    }
    
    private Long findPreviousZoneTransitionTime(final TimeZone timeZone, final int n, long n2, final long n3) {
        boolean b = false;
        long n4 = 0L;
        for (final int n5 : Calendar.FIND_ZONE_TRANSITION_TIME_UNITS) {
            final long n6 = n3 / n5;
            final long n7 = n2 / n5;
            if (n7 > n6) {
                n4 = (n6 + n7 + 1L >>> 1) * n5;
                b = true;
                break;
            }
        }
        if (!b) {
            n4 = n2 + n3 >>> 1;
        }
        long n8;
        if (b) {
            if (n4 != n2) {
                if (timeZone.getOffset(n4) != n) {
                    return this.findPreviousZoneTransitionTime(timeZone, n, n2, n4);
                }
                n2 = n4;
            }
            n8 = n4 - 1L;
        }
        else {
            n8 = n2 + n3 >>> 1;
        }
        if (n8 == n3) {
            return n2;
        }
        if (timeZone.getOffset(n8) == n) {
            return this.findPreviousZoneTransitionTime(timeZone, n, n8, n3);
        }
        if (b) {
            return n2;
        }
        return this.findPreviousZoneTransitionTime(timeZone, n, n2, n8);
    }
    
    protected int computeMillisInDay() {
        int n = 0;
        final int n2 = this.stamp[11];
        final int max = Math.max(this.stamp[10], this.stamp[9]);
        final int n3 = (max > n2) ? max : n2;
        if (n3 != 0) {
            if (n3 == n2) {
                n += this.internalGet(11);
            }
            else {
                n = n + this.internalGet(10) + 12 * this.internalGet(9);
            }
        }
        return ((n * 60 + this.internalGet(12)) * 60 + this.internalGet(13)) * 1000 + this.internalGet(14);
    }
    
    protected int computeZoneOffset(final long n, final int n2) {
        final int[] array = new int[2];
        final long n3 = n + n2;
        if (this.zone instanceof BasicTimeZone) {
            ((BasicTimeZone)this.zone).getOffsetFromLocal(n3, (this.skippedWallTime == 1) ? 12 : 4, (this.repeatedWallTime == 1) ? 4 : 12, array);
        }
        else {
            this.zone.getOffset(n3, true, array);
            boolean b = false;
            if (this.repeatedWallTime == 1) {
                final int n4 = array[0] + array[1] - this.zone.getOffset(n3 - (array[0] + array[1]) - 21600000L);
                assert n4 < -21600000 : n4;
                if (n4 < 0) {
                    b = true;
                    this.zone.getOffset(n3 + n4, true, array);
                }
            }
            if (!b && this.skippedWallTime == 1) {
                this.zone.getOffset(n3 - (array[0] + array[1]), false, array);
            }
        }
        return array[0] + array[1];
    }
    
    protected int computeJulianDay() {
        if (this.stamp[20] >= 2 && this.newestStamp(17, 19, this.newestStamp(0, 8, 0)) <= this.stamp[20]) {
            return this.internalGet(20);
        }
        int resolveFields = this.resolveFields(this.getFieldResolutionTable());
        if (resolveFields < 0) {
            resolveFields = 5;
        }
        return this.handleComputeJulianDay(resolveFields);
    }
    
    protected int[][][] getFieldResolutionTable() {
        return Calendar.DATE_PRECEDENCE;
    }
    
    protected abstract int handleComputeMonthStart(final int p0, final int p1, final boolean p2);
    
    protected abstract int handleGetExtendedYear();
    
    protected int handleGetMonthLength(final int n, final int n2) {
        return this.handleComputeMonthStart(n, n2 + 1, true) - this.handleComputeMonthStart(n, n2, true);
    }
    
    protected int handleGetYearLength(final int n) {
        return this.handleComputeMonthStart(n + 1, 0, false) - this.handleComputeMonthStart(n, 0, false);
    }
    
    protected int[] handleCreateFields() {
        return new int[23];
    }
    
    protected int getDefaultMonthInYear(final int n) {
        return 0;
    }
    
    protected int getDefaultDayInMonth(final int n, final int n2) {
        return 1;
    }
    
    protected int handleComputeJulianDay(final int n) {
        final boolean b = n == 5 || n == 4 || n == 8;
        int n2;
        if (n == 3) {
            n2 = this.internalGet(17, this.handleGetExtendedYear());
        }
        else {
            n2 = this.handleGetExtendedYear();
        }
        this.internalSet(19, n2);
        final int n3 = b ? this.internalGet(2, this.getDefaultMonthInYear(n2)) : 0;
        final int handleComputeMonthStart = this.handleComputeMonthStart(n2, n3, b);
        if (n == 5) {
            if (this.isSet(5)) {
                return handleComputeMonthStart + this.internalGet(5, this.getDefaultDayInMonth(n2, n3));
            }
            return handleComputeMonthStart + this.getDefaultDayInMonth(n2, n3);
        }
        else {
            if (n == 6) {
                return handleComputeMonthStart + this.internalGet(6);
            }
            final int firstDayOfWeek = this.getFirstDayOfWeek();
            int n4 = julianDayToDayOfWeek(handleComputeMonthStart + 1) - firstDayOfWeek;
            if (n4 < 0) {
                n4 += 7;
            }
            int n5 = 0;
            switch (this.resolveFields(Calendar.DOW_PRECEDENCE)) {
                case 7: {
                    n5 = this.internalGet(7) - firstDayOfWeek;
                    break;
                }
                case 18: {
                    n5 = this.internalGet(18) - 1;
                    break;
                }
            }
            int n6 = n5 % 7;
            if (n6 < 0) {
                n6 += 7;
            }
            int n7 = 1 - n4 + n6;
            int n8;
            if (n == 8) {
                if (n7 < 1) {
                    n7 += 7;
                }
                final int internalGet = this.internalGet(8, 1);
                if (internalGet >= 0) {
                    n8 = n7 + 7 * (internalGet - 1);
                }
                else {
                    n8 = n7 + ((this.handleGetMonthLength(n2, this.internalGet(2, 0)) - n7) / 7 + internalGet + 1) * 7;
                }
            }
            else {
                if (7 - n4 < this.getMinimalDaysInFirstWeek()) {
                    n7 += 7;
                }
                n8 = n7 + 7 * (this.internalGet(n) - 1);
            }
            return handleComputeMonthStart + n8;
        }
    }
    
    protected int computeGregorianMonthStart(int n, int n2) {
        if (n2 < 0 || n2 > 11) {
            final int[] array = { 0 };
            n += floorDivide(n2, 12, array);
            n2 = array[0];
        }
        final boolean b = n % 4 == 0 && (n % 100 != 0 || n % 400 == 0);
        final int n3 = n - 1;
        int n4 = 365 * n3 + floorDivide(n3, 4) - floorDivide(n3, 100) + floorDivide(n3, 400) + 1721426 - 1;
        if (n2 != 0) {
            n4 += Calendar.GREGORIAN_MONTH_COUNT[n2][b ? 3 : 2];
        }
        return n4;
    }
    
    protected void handleComputeFields(final int n) {
        this.internalSet(2, this.getGregorianMonth());
        this.internalSet(5, this.getGregorianDayOfMonth());
        this.internalSet(6, this.getGregorianDayOfYear());
        int gregorianYear = this.getGregorianYear();
        this.internalSet(19, gregorianYear);
        int n2 = 1;
        if (gregorianYear < 1) {
            n2 = 0;
            gregorianYear = 1 - gregorianYear;
        }
        this.internalSet(0, n2);
        this.internalSet(1, gregorianYear);
    }
    
    protected final int getGregorianYear() {
        return this.gregorianYear;
    }
    
    protected final int getGregorianMonth() {
        return this.gregorianMonth;
    }
    
    protected final int getGregorianDayOfYear() {
        return this.gregorianDayOfYear;
    }
    
    protected final int getGregorianDayOfMonth() {
        return this.gregorianDayOfMonth;
    }
    
    public final int getFieldCount() {
        return this.fields.length;
    }
    
    protected final void internalSet(final int n, final int n2) {
        if ((1 << n & this.internalSetMask) == 0x0) {
            throw new IllegalStateException("Subclass cannot set " + this.fieldName(n));
        }
        this.fields[n] = n2;
        this.stamp[n] = 1;
    }
    
    protected static final boolean isGregorianLeapYear(final int n) {
        return n % 4 == 0 && (n % 100 != 0 || n % 400 == 0);
    }
    
    protected static final int gregorianMonthLength(final int n, final int n2) {
        return Calendar.GREGORIAN_MONTH_COUNT[n2][isGregorianLeapYear(n)];
    }
    
    protected static final int gregorianPreviousMonthLength(final int n, final int n2) {
        return (n2 > 0) ? gregorianMonthLength(n, n2 - 1) : 31;
    }
    
    protected static final long floorDivide(final long n, final long n2) {
        return (n >= 0L) ? (n / n2) : ((n + 1L) / n2 - 1L);
    }
    
    protected static final int floorDivide(final int n, final int n2) {
        return (n >= 0) ? (n / n2) : ((n + 1) / n2 - 1);
    }
    
    protected static final int floorDivide(final int n, final int n2, final int[] array) {
        if (n >= 0) {
            array[0] = n % n2;
            return n / n2;
        }
        final int n3 = (n + 1) / n2 - 1;
        array[0] = n - n3 * n2;
        return n3;
    }
    
    protected static final int floorDivide(final long n, final int n2, final int[] array) {
        if (n >= 0L) {
            array[0] = (int)(n % n2);
            return (int)(n / n2);
        }
        final int n3 = (int)((n + 1L) / n2 - 1L);
        array[0] = (int)(n - n3 * (long)n2);
        return n3;
    }
    
    protected String fieldName(final int n) {
        try {
            return Calendar.FIELD_NAME[n];
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            return "Field " + n;
        }
    }
    
    protected static final int millisToJulianDay(final long n) {
        return (int)(2440588L + floorDivide(n, 86400000L));
    }
    
    protected static final long julianDayToMillis(final int n) {
        return (n - 2440588) * 86400000L;
    }
    
    protected static final int julianDayToDayOfWeek(final int n) {
        int n2 = (n + 2) % 7;
        if (n2 < 1) {
            n2 += 7;
        }
        return n2;
    }
    
    protected final long internalGetTimeInMillis() {
        return this.time;
    }
    
    public String getType() {
        return "unknown";
    }
    
    public final ULocale getLocale(final ULocale.Type type) {
        return (type == ULocale.ACTUAL_LOCALE) ? this.actualLocale : this.validLocale;
    }
    
    final void setLocale(final ULocale validLocale, final ULocale actualLocale) {
        if (validLocale == null != (actualLocale == null)) {
            throw new IllegalArgumentException();
        }
        this.validLocale = validLocale;
        this.actualLocale = actualLocale;
    }
    
    public int compareTo(final Object o) {
        return this.compareTo((Calendar)o);
    }
    
    static ICUCache access$1000() {
        return Calendar.PATTERN_CACHE;
    }
    
    static String[] access$1100() {
        return Calendar.DEFAULT_PATTERNS;
    }
    
    static {
        $assertionsDisabled = !Calendar.class.desiredAssertionStatus();
        MIN_DATE = new Date(-184303902528000000L);
        MAX_DATE = new Date(183882168921600000L);
        Calendar.cachedLocaleData = new SimpleCache();
        Calendar.STAMP_MAX = 10000;
        calTypes = new String[] { "gregorian", "japanese", "buddhist", "roc", "persian", "islamic-civil", "islamic", "hebrew", "chinese", "indian", "coptic", "ethiopic", "ethiopic-amete-alem", "iso8601", "dangi" };
        PATTERN_CACHE = new SimpleCache();
        DEFAULT_PATTERNS = new String[] { "HH:mm:ss z", "HH:mm:ss z", "HH:mm:ss", "HH:mm", "EEEE, yyyy MMMM dd", "yyyy MMMM d", "yyyy MMM d", "yy/MM/dd", "{1} {0}", "{1} {0}", "{1} {0}", "{1} {0}", "{1} {0}" };
        LIMITS = new int[][] { new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], { 1, 1, 7, 7 }, new int[0], { 0, 0, 1, 1 }, { 0, 0, 11, 11 }, { 0, 0, 23, 23 }, { 0, 0, 59, 59 }, { 0, 0, 59, 59 }, { 0, 0, 999, 999 }, { -43200000, -43200000, 43200000, 43200000 }, { 0, 0, 3600000, 3600000 }, new int[0], { 1, 1, 7, 7 }, new int[0], { -2130706432, -2130706432, 2130706432, 2130706432 }, { 0, 0, 86399999, 86399999 }, { 0, 0, 1, 1 } };
        DATE_PRECEDENCE = new int[][][] { { { 5 }, { 3, 7 }, { 4, 7 }, { 8, 7 }, { 3, 18 }, { 4, 18 }, { 8, 18 }, { 6 }, { 37, 1 }, { 35, 17 } }, { { 3 }, { 4 }, { 8 }, { 40, 7 }, { 40, 18 } } };
        DOW_PRECEDENCE = new int[][][] { { { 7 }, { 18 } } };
        FIND_ZONE_TRANSITION_TIME_UNITS = new int[] { 3600000, 1800000, 60000, 1000 };
        GREGORIAN_MONTH_COUNT = new int[][] { { 31, 31, 0, 0 }, { 28, 29, 31, 31 }, { 31, 31, 59, 60 }, { 30, 30, 90, 91 }, { 31, 31, 120, 121 }, { 30, 30, 151, 152 }, { 31, 31, 181, 182 }, { 31, 31, 212, 213 }, { 30, 30, 243, 244 }, { 31, 31, 273, 274 }, { 30, 30, 304, 305 }, { 31, 31, 334, 335 } };
        FIELD_NAME = new String[] { "ERA", "YEAR", "MONTH", "WEEK_OF_YEAR", "WEEK_OF_MONTH", "DAY_OF_MONTH", "DAY_OF_YEAR", "DAY_OF_WEEK", "DAY_OF_WEEK_IN_MONTH", "AM_PM", "HOUR", "HOUR_OF_DAY", "MINUTE", "SECOND", "MILLISECOND", "ZONE_OFFSET", "DST_OFFSET", "YEAR_WOY", "DOW_LOCAL", "EXTENDED_YEAR", "JULIAN_DAY", "MILLISECONDS_IN_DAY" };
    }
    
    private static class WeekData
    {
        public int firstDayOfWeek;
        public int minimalDaysInFirstWeek;
        public int weekendOnset;
        public int weekendOnsetMillis;
        public int weekendCease;
        public int weekendCeaseMillis;
        public ULocale actualLocale;
        
        public WeekData(final int firstDayOfWeek, final int minimalDaysInFirstWeek, final int weekendOnset, final int weekendOnsetMillis, final int weekendCease, final int weekendCeaseMillis, final ULocale actualLocale) {
            this.firstDayOfWeek = firstDayOfWeek;
            this.minimalDaysInFirstWeek = minimalDaysInFirstWeek;
            this.actualLocale = actualLocale;
            this.weekendOnset = weekendOnset;
            this.weekendOnsetMillis = weekendOnsetMillis;
            this.weekendCease = weekendCease;
            this.weekendCeaseMillis = weekendCeaseMillis;
        }
    }
    
    public static class FormatConfiguration
    {
        private String pattern;
        private String override;
        private DateFormatSymbols formatData;
        private Calendar cal;
        private ULocale loc;
        
        private FormatConfiguration() {
        }
        
        @Deprecated
        public String getPatternString() {
            return this.pattern;
        }
        
        @Deprecated
        public String getOverrideString() {
            return this.override;
        }
        
        @Deprecated
        public Calendar getCalendar() {
            return this.cal;
        }
        
        @Deprecated
        public ULocale getLocale() {
            return this.loc;
        }
        
        @Deprecated
        public DateFormatSymbols getDateFormatSymbols() {
            return this.formatData;
        }
        
        FormatConfiguration(final Calendar$1 object) {
            this();
        }
        
        static String access$102(final FormatConfiguration formatConfiguration, final String pattern) {
            return formatConfiguration.pattern = pattern;
        }
        
        static String access$202(final FormatConfiguration formatConfiguration, final String override) {
            return formatConfiguration.override = override;
        }
        
        static DateFormatSymbols access$302(final FormatConfiguration formatConfiguration, final DateFormatSymbols formatData) {
            return formatConfiguration.formatData = formatData;
        }
        
        static ULocale access$402(final FormatConfiguration formatConfiguration, final ULocale loc) {
            return formatConfiguration.loc = loc;
        }
        
        static Calendar access$502(final FormatConfiguration formatConfiguration, final Calendar cal) {
            return formatConfiguration.cal = cal;
        }
    }
    
    static class PatternData
    {
        private String[] patterns;
        private String[] overrides;
        
        public PatternData(final String[] patterns, final String[] overrides) {
            this.patterns = patterns;
            this.overrides = overrides;
        }
        
        private String getDateTimePattern(final int n) {
            if (this.patterns.length >= 13) {}
            return this.patterns[8];
        }
        
        private static PatternData make(final Calendar calendar, final ULocale uLocale) {
            final String type = calendar.getType();
            final String string = uLocale.getBaseName() + "+" + type;
            PatternData patternData = (PatternData)Calendar.access$1000().get(string);
            if (patternData == null) {
                final CalendarData calendarData = new CalendarData(uLocale, type);
                patternData = new PatternData(calendarData.getDateTimePatterns(), calendarData.getOverrides());
                Calendar.access$1000().put(string, patternData);
            }
            return patternData;
        }
        
        static PatternData access$600(final Calendar calendar, final ULocale uLocale) {
            return make(calendar, uLocale);
        }
        
        static String access$700(final PatternData patternData, final int n) {
            return patternData.getDateTimePattern(n);
        }
        
        static String[] access$800(final PatternData patternData) {
            return patternData.patterns;
        }
        
        static String[] access$900(final PatternData patternData) {
            return patternData.overrides;
        }
    }
    
    abstract static class CalendarShim
    {
        abstract Locale[] getAvailableLocales();
        
        abstract ULocale[] getAvailableULocales();
        
        abstract Object registerFactory(final CalendarFactory p0);
        
        abstract boolean unregister(final Object p0);
        
        abstract Calendar createInstance(final ULocale p0);
    }
    
    abstract static class CalendarFactory
    {
        public boolean visible() {
            return true;
        }
        
        public abstract Set getSupportedLocaleNames();
        
        public Calendar createCalendar(final ULocale uLocale) {
            return null;
        }
        
        protected CalendarFactory() {
        }
    }
}
