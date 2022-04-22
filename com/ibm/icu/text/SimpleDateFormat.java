package com.ibm.icu.text;

import com.ibm.icu.lang.*;
import java.util.*;
import com.ibm.icu.util.*;
import java.io.*;
import java.text.*;
import com.ibm.icu.impl.*;

public class SimpleDateFormat extends DateFormat
{
    private static final long serialVersionUID = 4774881970558875024L;
    static final int currentSerialVersion = 2;
    static boolean DelayedHebrewMonthCheck;
    private static final int[] CALENDAR_FIELD_TO_LEVEL;
    private static final int[] PATTERN_CHAR_TO_LEVEL;
    private static final int HEBREW_CAL_CUR_MILLENIUM_START_YEAR = 5000;
    private static final int HEBREW_CAL_CUR_MILLENIUM_END_YEAR = 6000;
    private int serialVersionOnStream;
    private String pattern;
    private String override;
    private HashMap numberFormatters;
    private HashMap overrideMap;
    private DateFormatSymbols formatData;
    private transient ULocale locale;
    private Date defaultCenturyStart;
    private transient int defaultCenturyStartYear;
    private transient long defaultCenturyBase;
    private transient TimeZoneFormat.TimeType tztype;
    private static final int millisPerHour = 3600000;
    private static final int ISOSpecialEra = -32000;
    private static final String SUPPRESS_NEGATIVE_PREFIX = "\uab00";
    private transient boolean useFastFormat;
    private TimeZoneFormat tzFormat;
    private transient DisplayContext capitalizationSetting;
    private static ULocale cachedDefaultLocale;
    private static String cachedDefaultPattern;
    private static final String FALLBACKPATTERN = "yy/MM/dd HH:mm";
    private static final int PATTERN_CHAR_BASE = 64;
    private static final int[] PATTERN_CHAR_TO_INDEX;
    private static final int[] PATTERN_INDEX_TO_CALENDAR_FIELD;
    private static final int[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD;
    private static final Field[] PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE;
    private static ICUCache PARSED_PATTERN_CACHE;
    private transient Object[] patternItems;
    private transient boolean useLocalZeroPaddingNumberFormat;
    private transient char[] decDigits;
    private transient char[] decimalBuf;
    private static final String NUMERIC_FORMAT_CHARS = "MYyudehHmsSDFwWkK";
    static final UnicodeSet DATE_PATTERN_TYPE;
    
    public SimpleDateFormat() {
        this(getDefaultPattern(), null, null, null, null, true, null);
    }
    
    public SimpleDateFormat(final String s) {
        this(s, null, null, null, null, true, null);
    }
    
    public SimpleDateFormat(final String s, final Locale locale) {
        this(s, null, null, null, ULocale.forLocale(locale), true, null);
    }
    
    public SimpleDateFormat(final String s, final ULocale uLocale) {
        this(s, null, null, null, uLocale, true, null);
    }
    
    public SimpleDateFormat(final String s, final String s2, final ULocale uLocale) {
        this(s, null, null, null, uLocale, false, s2);
    }
    
    public SimpleDateFormat(final String s, final DateFormatSymbols dateFormatSymbols) {
        this(s, (DateFormatSymbols)dateFormatSymbols.clone(), null, null, null, true, null);
    }
    
    @Deprecated
    public SimpleDateFormat(final String s, final DateFormatSymbols dateFormatSymbols, final ULocale uLocale) {
        this(s, (DateFormatSymbols)dateFormatSymbols.clone(), null, null, uLocale, true, null);
    }
    
    SimpleDateFormat(final String s, final DateFormatSymbols dateFormatSymbols, final Calendar calendar, final ULocale uLocale, final boolean b, final String s2) {
        this(s, (DateFormatSymbols)dateFormatSymbols.clone(), (Calendar)calendar.clone(), null, uLocale, b, s2);
    }
    
    private SimpleDateFormat(final String pattern, final DateFormatSymbols formatData, final Calendar calendar, final NumberFormat numberFormat, final ULocale locale, final boolean useFastFormat, final String override) {
        this.serialVersionOnStream = 2;
        this.tztype = TimeZoneFormat.TimeType.UNKNOWN;
        this.pattern = pattern;
        this.formatData = formatData;
        this.calendar = calendar;
        this.numberFormat = numberFormat;
        this.locale = locale;
        this.useFastFormat = useFastFormat;
        this.override = override;
        this.initialize();
    }
    
    @Deprecated
    public static SimpleDateFormat getInstance(final Calendar.FormatConfiguration formatConfiguration) {
        final String overrideString = formatConfiguration.getOverrideString();
        return new SimpleDateFormat(formatConfiguration.getPatternString(), formatConfiguration.getDateFormatSymbols(), formatConfiguration.getCalendar(), null, formatConfiguration.getLocale(), overrideString != null && overrideString.length() > 0, formatConfiguration.getOverrideString());
    }
    
    private void initialize() {
        if (this.locale == null) {
            this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        if (this.formatData == null) {
            this.formatData = new DateFormatSymbols(this.locale);
        }
        if (this.calendar == null) {
            this.calendar = Calendar.getInstance(this.locale);
        }
        if (this.numberFormat == null) {
            final NumberingSystem instance = NumberingSystem.getInstance(this.locale);
            if (instance.isAlgorithmic()) {
                this.numberFormat = NumberFormat.getInstance(this.locale);
            }
            else {
                this.numberFormat = new DateNumberFormat(this.locale, instance.getDescription(), instance.getName());
            }
        }
        this.defaultCenturyBase = System.currentTimeMillis();
        this.setLocale(this.calendar.getLocale(ULocale.VALID_LOCALE), this.calendar.getLocale(ULocale.ACTUAL_LOCALE));
        this.initLocalZeroPaddingNumberFormat();
        if (this.override != null) {
            this.initNumberFormatters(this.locale);
        }
        this.capitalizationSetting = DisplayContext.CAPITALIZATION_NONE;
    }
    
    private synchronized void initializeTimeZoneFormat(final boolean b) {
        if (b || this.tzFormat == null) {
            this.tzFormat = TimeZoneFormat.getInstance(this.locale);
            String gmtOffsetDigits = null;
            if (this.numberFormat instanceof DecimalFormat) {
                gmtOffsetDigits = new String(((DecimalFormat)this.numberFormat).getDecimalFormatSymbols().getDigits());
            }
            else if (this.numberFormat instanceof DateNumberFormat) {
                gmtOffsetDigits = new String(((DateNumberFormat)this.numberFormat).getDigits());
            }
            if (gmtOffsetDigits != null && !this.tzFormat.getGMTOffsetDigits().equals(gmtOffsetDigits)) {
                if (this.tzFormat.isFrozen()) {
                    this.tzFormat = this.tzFormat.cloneAsThawed();
                }
                this.tzFormat.setGMTOffsetDigits(gmtOffsetDigits);
            }
        }
    }
    
    private TimeZoneFormat tzFormat() {
        if (this.tzFormat == null) {
            this.initializeTimeZoneFormat(false);
        }
        return this.tzFormat;
    }
    
    private static synchronized String getDefaultPattern() {
        final ULocale default1 = ULocale.getDefault(ULocale.Category.FORMAT);
        if (!default1.equals(SimpleDateFormat.cachedDefaultLocale)) {
            SimpleDateFormat.cachedDefaultLocale = default1;
            final Calendar instance = Calendar.getInstance(SimpleDateFormat.cachedDefaultLocale);
            try {
                final String[] dateTimePatterns = new CalendarData(SimpleDateFormat.cachedDefaultLocale, instance.getType()).getDateTimePatterns();
                int n = 8;
                if (dateTimePatterns.length >= 13) {
                    n += 4;
                }
                SimpleDateFormat.cachedDefaultPattern = MessageFormat.format(dateTimePatterns[n], dateTimePatterns[3], dateTimePatterns[7]);
            }
            catch (MissingResourceException ex) {
                SimpleDateFormat.cachedDefaultPattern = "yy/MM/dd HH:mm";
            }
        }
        return SimpleDateFormat.cachedDefaultPattern;
    }
    
    private void parseAmbiguousDatesAsAfter(final Date date) {
        this.defaultCenturyStart = date;
        this.calendar.setTime(date);
        this.defaultCenturyStartYear = this.calendar.get(1);
    }
    
    private void initializeDefaultCenturyStart(final long n) {
        this.defaultCenturyBase = n;
        final Calendar calendar = (Calendar)this.calendar.clone();
        calendar.setTimeInMillis(n);
        calendar.add(1, -80);
        this.defaultCenturyStart = calendar.getTime();
        this.defaultCenturyStartYear = calendar.get(1);
    }
    
    private Date getDefaultCenturyStart() {
        if (this.defaultCenturyStart == null) {
            this.initializeDefaultCenturyStart(this.defaultCenturyBase);
        }
        return this.defaultCenturyStart;
    }
    
    private int getDefaultCenturyStartYear() {
        if (this.defaultCenturyStart == null) {
            this.initializeDefaultCenturyStart(this.defaultCenturyBase);
        }
        return this.defaultCenturyStartYear;
    }
    
    public void set2DigitYearStart(final Date date) {
        this.parseAmbiguousDatesAsAfter(date);
    }
    
    public Date get2DigitYearStart() {
        return this.getDefaultCenturyStart();
    }
    
    @Override
    public StringBuffer format(Calendar calendar, final StringBuffer sb, final FieldPosition fieldPosition) {
        TimeZone timeZone = null;
        if (calendar != this.calendar && !calendar.getType().equals(this.calendar.getType())) {
            this.calendar.setTimeInMillis(calendar.getTimeInMillis());
            timeZone = this.calendar.getTimeZone();
            this.calendar.setTimeZone(calendar.getTimeZone());
            calendar = this.calendar;
        }
        final StringBuffer format = this.format(calendar, this.capitalizationSetting, sb, fieldPosition, null);
        if (timeZone != null) {
            this.calendar.setTimeZone(timeZone);
        }
        return format;
    }
    
    private StringBuffer format(final Calendar calendar, final DisplayContext displayContext, final StringBuffer sb, final FieldPosition fieldPosition, final List list) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        final Object[] patternItems = this.getPatternItems();
        for (int i = 0; i < patternItems.length; ++i) {
            if (patternItems[i] instanceof String) {
                sb.append((String)patternItems[i]);
            }
            else {
                final PatternItem patternItem = (PatternItem)patternItems[i];
                int length = 0;
                if (list != null) {
                    length = sb.length();
                }
                if (this.useFastFormat) {
                    this.subFormat(sb, patternItem.type, patternItem.length, sb.length(), i, displayContext, fieldPosition, calendar);
                }
                else {
                    sb.append(this.subFormat(patternItem.type, patternItem.length, sb.length(), i, displayContext, fieldPosition, calendar));
                }
                if (list != null) {
                    final int length2 = sb.length();
                    if (length2 - length > 0) {
                        final FieldPosition fieldPosition2 = new FieldPosition(this.patternCharToDateFormatField(patternItem.type));
                        fieldPosition2.setBeginIndex(length);
                        fieldPosition2.setEndIndex(length2);
                        list.add(fieldPosition2);
                    }
                }
            }
        }
        return sb;
    }
    
    protected Field patternCharToDateFormatField(final char c) {
        int n = -1;
        if ('A' <= c && c <= 'z') {
            n = SimpleDateFormat.PATTERN_CHAR_TO_INDEX[c - '@'];
        }
        if (n != -1) {
            return SimpleDateFormat.PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE[n];
        }
        return null;
    }
    
    protected String subFormat(final char c, final int n, final int n2, final FieldPosition fieldPosition, final DateFormatSymbols dateFormatSymbols, final Calendar calendar) throws IllegalArgumentException {
        return this.subFormat(c, n, n2, 0, DisplayContext.CAPITALIZATION_NONE, fieldPosition, calendar);
    }
    
    @Deprecated
    protected String subFormat(final char c, final int n, final int n2, final int n3, final DisplayContext displayContext, final FieldPosition fieldPosition, final Calendar calendar) {
        final StringBuffer sb = new StringBuffer();
        this.subFormat(sb, c, n, n2, n3, displayContext, fieldPosition, calendar);
        return sb.toString();
    }
    
    @Deprecated
    protected void subFormat(final StringBuffer sb, final char c, final int n, final int n2, final int n3, final DisplayContext displayContext, final FieldPosition fieldPosition, final Calendar calendar) {
        final int length = sb.length();
        final TimeZone timeZone = calendar.getTimeZone();
        final long timeInMillis = calendar.getTimeInMillis();
        String s = null;
        int n4 = -1;
        if ('A' <= c && c <= 'z') {
            n4 = SimpleDateFormat.PATTERN_CHAR_TO_INDEX[c - '@'];
        }
        if (n4 != -1) {
            int n5 = calendar.get(SimpleDateFormat.PATTERN_INDEX_TO_CALENDAR_FIELD[n4]);
            final NumberFormat numberFormat = this.getNumberFormat(c);
            DateFormatSymbols.CapitalizationContextUsage capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.OTHER;
            switch (n4) {
                case 0: {
                    if (calendar.getType().equals("chinese")) {
                        this.zeroPaddingNumber(numberFormat, sb, n5, 1, 9);
                        break;
                    }
                    if (n == 5) {
                        safeAppend(this.formatData.narrowEras, n5, sb);
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.ERA_NARROW;
                        break;
                    }
                    if (n == 4) {
                        safeAppend(this.formatData.eraNames, n5, sb);
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.ERA_WIDE;
                        break;
                    }
                    safeAppend(this.formatData.eras, n5, sb);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.ERA_ABBREV;
                    break;
                }
                case 30: {
                    if (this.formatData.shortYearNames != null && n5 <= this.formatData.shortYearNames.length) {
                        safeAppend(this.formatData.shortYearNames, n5 - 1, sb);
                        break;
                    }
                }
                case 1:
                case 18: {
                    if (this.override != null && (this.override.compareTo("hebr") == 0 || this.override.indexOf("y=hebr") >= 0) && n5 > 5000 && n5 < 6000) {
                        n5 -= 5000;
                    }
                    if (n == 2) {
                        this.zeroPaddingNumber(numberFormat, sb, n5, 2, 2);
                        break;
                    }
                    this.zeroPaddingNumber(numberFormat, sb, n5, n, Integer.MAX_VALUE);
                    break;
                }
                case 2:
                case 26: {
                    if (calendar.getType().equals("hebrew")) {
                        final boolean leapYear = HebrewCalendar.isLeapYear(calendar.get(1));
                        if (leapYear && n5 == 6 && n >= 3) {
                            n5 = 13;
                        }
                        if (!leapYear && n5 >= 6 && n < 3) {
                            --n5;
                        }
                    }
                    final int n6 = (this.formatData.leapMonthPatterns != null && this.formatData.leapMonthPatterns.length >= 7) ? calendar.get(22) : 0;
                    if (n == 5) {
                        if (n4 == 2) {
                            safeAppendWithMonthPattern(this.formatData.narrowMonths, n5, sb, (n6 != 0) ? this.formatData.leapMonthPatterns[2] : null);
                        }
                        else {
                            safeAppendWithMonthPattern(this.formatData.standaloneNarrowMonths, n5, sb, (n6 != 0) ? this.formatData.leapMonthPatterns[5] : null);
                        }
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.MONTH_NARROW;
                        break;
                    }
                    if (n == 4) {
                        if (n4 == 2) {
                            safeAppendWithMonthPattern(this.formatData.months, n5, sb, (n6 != 0) ? this.formatData.leapMonthPatterns[0] : null);
                            capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.MONTH_FORMAT;
                            break;
                        }
                        safeAppendWithMonthPattern(this.formatData.standaloneMonths, n5, sb, (n6 != 0) ? this.formatData.leapMonthPatterns[3] : null);
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.MONTH_STANDALONE;
                        break;
                    }
                    else {
                        if (n != 3) {
                            final StringBuffer sb2 = new StringBuffer();
                            this.zeroPaddingNumber(numberFormat, sb2, n5 + 1, n, Integer.MAX_VALUE);
                            safeAppendWithMonthPattern(new String[] { sb2.toString() }, 0, sb, (n6 != 0) ? this.formatData.leapMonthPatterns[6] : null);
                            break;
                        }
                        if (n4 == 2) {
                            safeAppendWithMonthPattern(this.formatData.shortMonths, n5, sb, (n6 != 0) ? this.formatData.leapMonthPatterns[1] : null);
                            capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.MONTH_FORMAT;
                            break;
                        }
                        safeAppendWithMonthPattern(this.formatData.standaloneShortMonths, n5, sb, (n6 != 0) ? this.formatData.leapMonthPatterns[4] : null);
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.MONTH_STANDALONE;
                        break;
                    }
                    break;
                }
                case 4: {
                    if (n5 == 0) {
                        this.zeroPaddingNumber(numberFormat, sb, calendar.getMaximum(11) + 1, n, Integer.MAX_VALUE);
                        break;
                    }
                    this.zeroPaddingNumber(numberFormat, sb, n5, n, Integer.MAX_VALUE);
                    break;
                }
                case 8: {
                    this.numberFormat.setMinimumIntegerDigits(Math.min(3, n));
                    this.numberFormat.setMaximumIntegerDigits(Integer.MAX_VALUE);
                    if (n == 1) {
                        n5 /= 100;
                    }
                    else if (n == 2) {
                        n5 /= 10;
                    }
                    final FieldPosition fieldPosition2 = new FieldPosition(-1);
                    this.numberFormat.format(n5, sb, fieldPosition2);
                    if (n > 3) {
                        this.numberFormat.setMinimumIntegerDigits(n - 3);
                        this.numberFormat.format(0L, sb, fieldPosition2);
                    }
                    break;
                }
                case 19: {
                    if (n < 3) {
                        this.zeroPaddingNumber(numberFormat, sb, n5, n, Integer.MAX_VALUE);
                        break;
                    }
                    n5 = calendar.get(7);
                }
                case 9: {
                    if (n == 5) {
                        safeAppend(this.formatData.narrowWeekdays, n5, sb);
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.DAY_NARROW;
                        break;
                    }
                    if (n == 4) {
                        safeAppend(this.formatData.weekdays, n5, sb);
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
                        break;
                    }
                    if (n == 6 && this.formatData.shorterWeekdays != null) {
                        safeAppend(this.formatData.shorterWeekdays, n5, sb);
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
                        break;
                    }
                    safeAppend(this.formatData.shortWeekdays, n5, sb);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
                    break;
                }
                case 14: {
                    safeAppend(this.formatData.ampms, n5, sb);
                    break;
                }
                case 15: {
                    if (n5 == 0) {
                        this.zeroPaddingNumber(numberFormat, sb, calendar.getLeastMaximum(10) + 1, n, Integer.MAX_VALUE);
                        break;
                    }
                    this.zeroPaddingNumber(numberFormat, sb, n5, n, Integer.MAX_VALUE);
                    break;
                }
                case 17: {
                    String s2;
                    if (n < 4) {
                        s2 = this.tzFormat().format(TimeZoneFormat.Style.SPECIFIC_SHORT, timeZone, timeInMillis);
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.METAZONE_SHORT;
                    }
                    else {
                        s2 = this.tzFormat().format(TimeZoneFormat.Style.SPECIFIC_LONG, timeZone, timeInMillis);
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.METAZONE_LONG;
                    }
                    sb.append(s2);
                    break;
                }
                case 23: {
                    String s3;
                    if (n < 4) {
                        s3 = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL, timeZone, timeInMillis);
                    }
                    else if (n == 5) {
                        s3 = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_FULL, timeZone, timeInMillis);
                    }
                    else {
                        s3 = this.tzFormat().format(TimeZoneFormat.Style.LOCALIZED_GMT, timeZone, timeInMillis);
                    }
                    sb.append(s3);
                    break;
                }
                case 24: {
                    if (n == 1) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.GENERIC_SHORT, timeZone, timeInMillis);
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.METAZONE_SHORT;
                    }
                    else if (n == 4) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.GENERIC_LONG, timeZone, timeInMillis);
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.METAZONE_LONG;
                    }
                    sb.append(s);
                    break;
                }
                case 29: {
                    if (n == 1) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.ZONE_ID_SHORT, timeZone, timeInMillis);
                    }
                    else if (n == 2) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.ZONE_ID, timeZone, timeInMillis);
                    }
                    else if (n == 3) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.EXEMPLAR_LOCATION, timeZone, timeInMillis);
                    }
                    else if (n == 4) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.GENERIC_LOCATION, timeZone, timeInMillis);
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.ZONE_LONG;
                    }
                    sb.append(s);
                    break;
                }
                case 31: {
                    if (n == 1) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.LOCALIZED_GMT_SHORT, timeZone, timeInMillis);
                    }
                    else if (n == 4) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.LOCALIZED_GMT, timeZone, timeInMillis);
                    }
                    sb.append(s);
                    break;
                }
                case 32: {
                    if (n == 1) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_SHORT, timeZone, timeInMillis);
                    }
                    else if (n == 2) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_FIXED, timeZone, timeInMillis);
                    }
                    else if (n == 3) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_FIXED, timeZone, timeInMillis);
                    }
                    else if (n == 4) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_FULL, timeZone, timeInMillis);
                    }
                    else if (n == 5) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_FULL, timeZone, timeInMillis);
                    }
                    sb.append(s);
                    break;
                }
                case 33: {
                    if (n == 1) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_SHORT, timeZone, timeInMillis);
                    }
                    else if (n == 2) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_FIXED, timeZone, timeInMillis);
                    }
                    else if (n == 3) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FIXED, timeZone, timeInMillis);
                    }
                    else if (n == 4) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL, timeZone, timeInMillis);
                    }
                    else if (n == 5) {
                        s = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FULL, timeZone, timeInMillis);
                    }
                    sb.append(s);
                    break;
                }
                case 25: {
                    if (n < 3) {
                        this.zeroPaddingNumber(numberFormat, sb, n5, 1, Integer.MAX_VALUE);
                        break;
                    }
                    final int value = calendar.get(7);
                    if (n == 5) {
                        safeAppend(this.formatData.standaloneNarrowWeekdays, value, sb);
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.DAY_NARROW;
                        break;
                    }
                    if (n == 4) {
                        safeAppend(this.formatData.standaloneWeekdays, value, sb);
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE;
                        break;
                    }
                    if (n == 6 && this.formatData.standaloneShorterWeekdays != null) {
                        safeAppend(this.formatData.standaloneShorterWeekdays, value, sb);
                        capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE;
                        break;
                    }
                    safeAppend(this.formatData.standaloneShortWeekdays, value, sb);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE;
                    break;
                }
                case 27: {
                    if (n >= 4) {
                        safeAppend(this.formatData.quarters, n5 / 3, sb);
                        break;
                    }
                    if (n == 3) {
                        safeAppend(this.formatData.shortQuarters, n5 / 3, sb);
                        break;
                    }
                    this.zeroPaddingNumber(numberFormat, sb, n5 / 3 + 1, n, Integer.MAX_VALUE);
                    break;
                }
                case 28: {
                    if (n >= 4) {
                        safeAppend(this.formatData.standaloneQuarters, n5 / 3, sb);
                        break;
                    }
                    if (n == 3) {
                        safeAppend(this.formatData.standaloneShortQuarters, n5 / 3, sb);
                        break;
                    }
                    this.zeroPaddingNumber(numberFormat, sb, n5 / 3 + 1, n, Integer.MAX_VALUE);
                    break;
                }
                default: {
                    this.zeroPaddingNumber(numberFormat, sb, n5, n, Integer.MAX_VALUE);
                    break;
                }
            }
            if (n3 == 0) {
                int n7 = 0;
                if (displayContext != null) {
                    switch (displayContext) {
                        case CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE: {
                            n7 = 1;
                            break;
                        }
                        case CAPITALIZATION_FOR_UI_LIST_OR_MENU:
                        case CAPITALIZATION_FOR_STANDALONE: {
                            if (this.formatData.capitalization != null) {
                                final boolean[] array = this.formatData.capitalization.get(capitalizationContextUsage);
                                n7 = (((displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU) ? array[0] : array[1]) ? 1 : 0);
                                break;
                            }
                            break;
                        }
                    }
                }
                if (n7 != 0) {
                    sb.replace(length, sb.length(), UCharacter.toTitleCase(this.locale, sb.substring(length), null, 768));
                }
            }
            if (fieldPosition.getBeginIndex() == fieldPosition.getEndIndex()) {
                if (fieldPosition.getField() == SimpleDateFormat.PATTERN_INDEX_TO_DATE_FORMAT_FIELD[n4]) {
                    fieldPosition.setBeginIndex(n2);
                    fieldPosition.setEndIndex(n2 + sb.length() - length);
                }
                else if (fieldPosition.getFieldAttribute() == SimpleDateFormat.PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE[n4]) {
                    fieldPosition.setBeginIndex(n2);
                    fieldPosition.setEndIndex(n2 + sb.length() - length);
                }
            }
            return;
        }
        if (c == 'l') {
            return;
        }
        throw new IllegalArgumentException("Illegal pattern character '" + c + "' in \"" + this.pattern + '\"');
    }
    
    private static void safeAppend(final String[] array, final int n, final StringBuffer sb) {
        if (array != null && n >= 0 && n < array.length) {
            sb.append(array[n]);
        }
    }
    
    private static void safeAppendWithMonthPattern(final String[] array, final int n, final StringBuffer sb, final String s) {
        if (array != null && n >= 0 && n < array.length) {
            if (s == null) {
                sb.append(array[n]);
            }
            else {
                sb.append(MessageFormat.format(s, array[n]));
            }
        }
    }
    
    private Object[] getPatternItems() {
        if (this.patternItems != null) {
            return this.patternItems;
        }
        this.patternItems = (Object[])SimpleDateFormat.PARSED_PATTERN_CACHE.get(this.pattern);
        if (this.patternItems != null) {
            return this.patternItems;
        }
        int n = 0;
        boolean b = false;
        final StringBuilder sb = new StringBuilder();
        char c = '\0';
        int n2 = 1;
        final ArrayList<PatternItem> list = new ArrayList<PatternItem>();
        for (int i = 0; i < this.pattern.length(); ++i) {
            final char char1 = this.pattern.charAt(i);
            if (char1 == '\'') {
                if (n != 0) {
                    sb.append('\'');
                    n = 0;
                }
                else {
                    n = 1;
                    if (c != '\0') {
                        list.add(new PatternItem(c, n2));
                        c = '\0';
                    }
                }
                b = !b;
            }
            else {
                n = 0;
                if (b) {
                    sb.append(char1);
                }
                else if ((char1 >= 'a' && char1 <= 'z') || (char1 >= 'A' && char1 <= 'Z')) {
                    if (char1 == c) {
                        ++n2;
                    }
                    else {
                        if (c == '\0') {
                            if (sb.length() > 0) {
                                list.add(sb.toString());
                                sb.setLength(0);
                            }
                        }
                        else {
                            list.add((String)new PatternItem(c, n2));
                        }
                        c = char1;
                        n2 = 1;
                    }
                }
                else {
                    if (c != '\0') {
                        list.add((String)new PatternItem(c, n2));
                        c = '\0';
                    }
                    sb.append(char1);
                }
            }
        }
        if (c == '\0') {
            if (sb.length() > 0) {
                list.add(sb.toString());
                sb.setLength(0);
            }
        }
        else {
            list.add((String)new PatternItem(c, n2));
        }
        this.patternItems = list.toArray(new Object[list.size()]);
        SimpleDateFormat.PARSED_PATTERN_CACHE.put(this.pattern, this.patternItems);
        return this.patternItems;
    }
    
    @Deprecated
    protected void zeroPaddingNumber(final NumberFormat numberFormat, final StringBuffer sb, final int n, final int minimumIntegerDigits, final int maximumIntegerDigits) {
        if (this.useLocalZeroPaddingNumberFormat && n >= 0) {
            this.fastZeroPaddingNumber(sb, n, minimumIntegerDigits, maximumIntegerDigits);
        }
        else {
            numberFormat.setMinimumIntegerDigits(minimumIntegerDigits);
            numberFormat.setMaximumIntegerDigits(maximumIntegerDigits);
            numberFormat.format(n, sb, new FieldPosition(-1));
        }
    }
    
    @Override
    public void setNumberFormat(final NumberFormat numberFormat) {
        super.setNumberFormat(numberFormat);
        this.initLocalZeroPaddingNumberFormat();
        this.initializeTimeZoneFormat(true);
    }
    
    private void initLocalZeroPaddingNumberFormat() {
        if (this.numberFormat instanceof DecimalFormat) {
            this.decDigits = ((DecimalFormat)this.numberFormat).getDecimalFormatSymbols().getDigits();
            this.useLocalZeroPaddingNumberFormat = true;
        }
        else if (this.numberFormat instanceof DateNumberFormat) {
            this.decDigits = ((DateNumberFormat)this.numberFormat).getDigits();
            this.useLocalZeroPaddingNumberFormat = true;
        }
        else {
            this.useLocalZeroPaddingNumberFormat = false;
        }
        if (this.useLocalZeroPaddingNumberFormat) {
            this.decimalBuf = new char[10];
        }
    }
    
    private void fastZeroPaddingNumber(final StringBuffer sb, int n, final int n2, final int n3) {
        final int n4 = (this.decimalBuf.length < n3) ? this.decimalBuf.length : n3;
        int n5 = n4 - 1;
        while (true) {
            this.decimalBuf[n5] = this.decDigits[n % 10];
            n /= 10;
            if (n5 == 0 || n == 0) {
                break;
            }
            --n5;
        }
        int i;
        for (i = n2 - (n4 - n5); i > 0 && n5 > 0; this.decimalBuf[--n5] = this.decDigits[0], --i) {}
        while (i > 0) {
            sb.append(this.decDigits[0]);
            --i;
        }
        sb.append(this.decimalBuf, n5, n4 - n5);
    }
    
    protected String zeroPaddingNumber(final long n, final int minimumIntegerDigits, final int maximumIntegerDigits) {
        this.numberFormat.setMinimumIntegerDigits(minimumIntegerDigits);
        this.numberFormat.setMaximumIntegerDigits(maximumIntegerDigits);
        return this.numberFormat.format(n);
    }
    
    private static final boolean isNumeric(final char c, final int n) {
        final int index = "MYyudehHmsSDFwWkK".indexOf(c);
        return index > 0 || (index == 0 && n < 3);
    }
    
    @Override
    public void parse(final String s, Calendar calendar, final ParsePosition parsePosition) {
        TimeZone timeZone = null;
        Calendar calendar2 = null;
        if (calendar != this.calendar && !calendar.getType().equals(this.calendar.getType())) {
            this.calendar.setTimeInMillis(calendar.getTimeInMillis());
            timeZone = this.calendar.getTimeZone();
            this.calendar.setTimeZone(calendar.getTimeZone());
            calendar2 = calendar;
            calendar = this.calendar;
        }
        final int index;
        int n = index = parsePosition.getIndex();
        this.tztype = TimeZoneFormat.TimeType.UNKNOWN;
        final boolean[] array = { false };
        int n2 = -1;
        int length = 0;
        int n3 = 0;
        MessageFormat messageFormat = null;
        if (this.formatData.leapMonthPatterns != null && this.formatData.leapMonthPatterns.length >= 7) {
            messageFormat = new MessageFormat(this.formatData.leapMonthPatterns[6], this.locale);
        }
        final Object[] patternItems = this.getPatternItems();
        int i = 0;
        while (i < patternItems.length) {
            if (patternItems[i] instanceof PatternItem) {
                final PatternItem patternItem = (PatternItem)patternItems[i];
                if (patternItem.isNumeric && n2 == -1 && i + 1 < patternItems.length && patternItems[i + 1] instanceof PatternItem && ((PatternItem)patternItems[i + 1]).isNumeric) {
                    n2 = i;
                    length = patternItem.length;
                    n3 = n;
                }
                if (n2 != -1) {
                    int length2 = patternItem.length;
                    if (n2 == i) {
                        length2 = length;
                    }
                    n = this.subParse(s, n, patternItem.type, length2, true, false, array, calendar, messageFormat);
                    if (n < 0) {
                        if (--length == 0) {
                            parsePosition.setIndex(index);
                            parsePosition.setErrorIndex(n);
                            if (timeZone != null) {
                                this.calendar.setTimeZone(timeZone);
                            }
                            return;
                        }
                        i = n2;
                        n = n3;
                        continue;
                    }
                }
                else if (patternItem.type != 'l') {
                    n2 = -1;
                    final int n4 = n;
                    n = this.subParse(s, n, patternItem.type, patternItem.length, false, true, array, calendar, messageFormat);
                    if (n < 0) {
                        if (n != -32000) {
                            parsePosition.setIndex(index);
                            parsePosition.setErrorIndex(n4);
                            if (timeZone != null) {
                                this.calendar.setTimeZone(timeZone);
                            }
                            return;
                        }
                        n = n4;
                        if (i + 1 < patternItems.length) {
                            String s2;
                            try {
                                s2 = (String)patternItems[i + 1];
                            }
                            catch (ClassCastException ex) {
                                parsePosition.setIndex(index);
                                parsePosition.setErrorIndex(n4);
                                if (timeZone != null) {
                                    this.calendar.setTimeZone(timeZone);
                                }
                                return;
                            }
                            if (s2 == null) {
                                s2 = (String)patternItems[i + 1];
                            }
                            int length3;
                            int n5;
                            for (length3 = s2.length(), n5 = 0; n5 < length3 && PatternProps.isWhiteSpace(s2.charAt(n5)); ++n5) {}
                            if (n5 == length3) {
                                ++i;
                            }
                        }
                    }
                }
            }
            else {
                n2 = -1;
                final boolean[] array2 = { false };
                n = this.matchLiteral(s, n, patternItems, i, array2);
                if (!array2[0]) {
                    parsePosition.setIndex(index);
                    parsePosition.setErrorIndex(n);
                    if (timeZone != null) {
                        this.calendar.setTimeZone(timeZone);
                    }
                    return;
                }
            }
            ++i;
        }
        if (n < s.length() && s.charAt(n) == '.' && this.isLenient() && patternItems.length != 0) {
            final Object o = patternItems[patternItems.length - 1];
            if (o instanceof PatternItem && !((PatternItem)o).isNumeric) {
                ++n;
            }
        }
        parsePosition.setIndex(n);
        try {
            if (array[0] || this.tztype != TimeZoneFormat.TimeType.UNKNOWN) {
                if (array[0] && ((Calendar)calendar.clone()).getTime().before(this.getDefaultCenturyStart())) {
                    calendar.set(1, this.getDefaultCenturyStartYear() + 100);
                }
                if (this.tztype != TimeZoneFormat.TimeType.UNKNOWN) {
                    final Calendar calendar3 = (Calendar)calendar.clone();
                    final TimeZone timeZone2 = calendar3.getTimeZone();
                    BasicTimeZone basicTimeZone = null;
                    if (timeZone2 instanceof BasicTimeZone) {
                        basicTimeZone = (BasicTimeZone)timeZone2;
                    }
                    calendar3.set(15, 0);
                    calendar3.set(16, 0);
                    final long timeInMillis = calendar3.getTimeInMillis();
                    final int[] array3 = new int[2];
                    if (basicTimeZone != null) {
                        if (this.tztype == TimeZoneFormat.TimeType.STANDARD) {
                            basicTimeZone.getOffsetFromLocal(timeInMillis, 1, 1, array3);
                        }
                        else {
                            basicTimeZone.getOffsetFromLocal(timeInMillis, 3, 3, array3);
                        }
                    }
                    else {
                        timeZone2.getOffset(timeInMillis, true, array3);
                        if ((this.tztype == TimeZoneFormat.TimeType.STANDARD && array3[1] != 0) || (this.tztype == TimeZoneFormat.TimeType.DAYLIGHT && array3[1] == 0)) {
                            timeZone2.getOffset(timeInMillis - 86400000L, true, array3);
                        }
                    }
                    int n6 = array3[1];
                    if (this.tztype == TimeZoneFormat.TimeType.STANDARD) {
                        if (array3[1] != 0) {
                            n6 = 0;
                        }
                    }
                    else if (array3[1] == 0) {
                        if (basicTimeZone != null) {
                            long n8;
                            long time;
                            final long n7 = time = (n8 = timeInMillis + array3[0]);
                            int j = 0;
                            int k = 0;
                            TimeZoneTransition previousTransition;
                            do {
                                previousTransition = basicTimeZone.getPreviousTransition(n8, true);
                                if (previousTransition == null) {
                                    break;
                                }
                                n8 = previousTransition.getTime() - 1L;
                                j = previousTransition.getFrom().getDSTSavings();
                            } while (j == 0);
                            TimeZoneTransition nextTransition;
                            do {
                                nextTransition = basicTimeZone.getNextTransition(time, false);
                                if (nextTransition == null) {
                                    break;
                                }
                                time = nextTransition.getTime();
                                k = nextTransition.getTo().getDSTSavings();
                            } while (k == 0);
                            if (previousTransition != null && nextTransition != null) {
                                if (n7 - n8 > time - n7) {
                                    n6 = k;
                                }
                                else {
                                    n6 = j;
                                }
                            }
                            else if (previousTransition != null && j != 0) {
                                n6 = j;
                            }
                            else if (nextTransition != null && k != 0) {
                                n6 = k;
                            }
                            else {
                                n6 = basicTimeZone.getDSTSavings();
                            }
                        }
                        else {
                            n6 = timeZone2.getDSTSavings();
                        }
                        if (n6 == 0) {
                            n6 = 3600000;
                        }
                    }
                    calendar.set(15, array3[0]);
                    calendar.set(16, n6);
                }
            }
        }
        catch (IllegalArgumentException ex2) {
            parsePosition.setErrorIndex(n);
            parsePosition.setIndex(index);
            if (timeZone != null) {
                this.calendar.setTimeZone(timeZone);
            }
            return;
        }
        if (calendar2 != null) {
            calendar2.setTimeZone(calendar.getTimeZone());
            calendar2.setTimeInMillis(calendar.getTimeInMillis());
        }
        if (timeZone != null) {
            this.calendar.setTimeZone(timeZone);
        }
    }
    
    private int matchLiteral(final String s, int n, final Object[] array, final int n2, final boolean[] array2) {
        final int n3 = n;
        final String s2 = (String)array[n2];
        final int length = s2.length();
        final int length2 = s.length();
        int n4 = 0;
        while (n4 < length && n < length2) {
            final char char1 = s2.charAt(n4);
            final char char2 = s.charAt(n);
            if (PatternProps.isWhiteSpace(char1) && PatternProps.isWhiteSpace(char2)) {
                while (n4 + 1 < length && PatternProps.isWhiteSpace(s2.charAt(n4 + 1))) {
                    ++n4;
                }
                while (n + 1 < length2 && PatternProps.isWhiteSpace(s.charAt(n + 1))) {
                    ++n;
                }
            }
            else if (char1 != char2) {
                if (char2 != '.' || n != n3 || 0 >= n2 || !this.isLenient()) {
                    break;
                }
                final Object o = array[n2 - 1];
                if (o instanceof PatternItem && !((PatternItem)o).isNumeric) {
                    ++n;
                    continue;
                }
                break;
            }
            ++n4;
            ++n;
        }
        array2[0] = (n4 == length);
        if (!array2[0] && this.isLenient() && 0 < n2 && n2 < array.length - 1 && n3 < length2) {
            final Object o2 = array[n2 - 1];
            final Object o3 = array[n2 + 1];
            if (o2 instanceof PatternItem && o3 instanceof PatternItem && SimpleDateFormat.DATE_PATTERN_TYPE.contains(((PatternItem)o2).type) != SimpleDateFormat.DATE_PATTERN_TYPE.contains(((PatternItem)o3).type)) {
                int n5;
                for (n5 = n3; PatternProps.isWhiteSpace(s.charAt(n5)); ++n5) {}
                array2[0] = (n5 > n3);
                n = n5;
            }
        }
        return n;
    }
    
    protected int matchString(final String s, final int n, final int n2, final String[] array, final Calendar calendar) {
        return this.matchString(s, n, n2, array, null, calendar);
    }
    
    protected int matchString(final String s, final int n, final int n2, final String[] array, final String s2, final Calendar calendar) {
        int i = 0;
        final int length = array.length;
        if (n2 == 7) {
            i = 1;
        }
        int n3 = 0;
        int n4 = -1;
        int n5 = 0;
        while (i < length) {
            final int length2 = array[i].length();
            final int regionMatchesWithOptionalDot;
            if (length2 > n3 && (regionMatchesWithOptionalDot = this.regionMatchesWithOptionalDot(s, n, array[i], length2)) >= 0) {
                n4 = i;
                n3 = regionMatchesWithOptionalDot;
                n5 = 0;
            }
            if (s2 != null) {
                final String format = MessageFormat.format(s2, array[i]);
                final int length3 = format.length();
                final int regionMatchesWithOptionalDot2;
                if (length3 > n3 && (regionMatchesWithOptionalDot2 = this.regionMatchesWithOptionalDot(s, n, format, length3)) >= 0) {
                    n4 = i;
                    n3 = regionMatchesWithOptionalDot2;
                    n5 = 1;
                }
            }
            ++i;
        }
        if (n4 >= 0) {
            if (n2 == 1) {
                ++n4;
            }
            calendar.set(n2, n4);
            if (s2 != null) {
                calendar.set(22, n5);
            }
            return n + n3;
        }
        return -n;
    }
    
    private int regionMatchesWithOptionalDot(final String s, final int n, final String s2, final int n2) {
        if (s.regionMatches(true, n, s2, 0, n2)) {
            return n2;
        }
        if (s2.length() > 0 && s2.charAt(s2.length() - 1) == '.' && s.regionMatches(true, n, s2, 0, n2 - 1)) {
            return n2 - 1;
        }
        return -1;
    }
    
    protected int matchQuarterString(final String s, final int n, final int n2, final String[] array, final Calendar calendar) {
        int i = 0;
        final int length = array.length;
        int n3 = 0;
        int n4 = -1;
        while (i < length) {
            final int length2 = array[i].length();
            final int regionMatchesWithOptionalDot;
            if (length2 > n3 && (regionMatchesWithOptionalDot = this.regionMatchesWithOptionalDot(s, n, array[i], length2)) >= 0) {
                n4 = i;
                n3 = regionMatchesWithOptionalDot;
            }
            ++i;
        }
        if (n4 >= 0) {
            calendar.set(n2, n4 * 3);
            return n + n3;
        }
        return -n;
    }
    
    protected int subParse(final String s, final int n, final char c, final int n2, final boolean b, final boolean b2, final boolean[] array, final Calendar calendar) {
        return this.subParse(s, n, c, n2, b, b2, array, calendar, null);
    }
    
    protected int subParse(final String s, int i, final char c, final int n, final boolean b, final boolean b2, final boolean[] array, final Calendar calendar, final MessageFormat messageFormat) {
        Number n2 = null;
        int intValue = 0;
        final ParsePosition parsePosition = new ParsePosition(0);
        final boolean lenient = this.isLenient();
        int n3 = -1;
        if ('A' <= c && c <= 'z') {
            n3 = SimpleDateFormat.PATTERN_CHAR_TO_INDEX[c - '@'];
        }
        if (n3 == -1) {
            return -i;
        }
        final NumberFormat numberFormat = this.getNumberFormat(c);
        final int n4 = SimpleDateFormat.PATTERN_INDEX_TO_CALENDAR_FIELD[n3];
        if (messageFormat != null) {
            messageFormat.setFormatByArgumentIndex(0, numberFormat);
        }
        while (i < s.length()) {
            final int char1 = UTF16.charAt(s, i);
            if (UCharacter.isUWhiteSpace(char1) && PatternProps.isWhiteSpace(char1)) {
                i += UTF16.getCharCount(char1);
            }
            else {
                parsePosition.setIndex(i);
                if (n3 == 4 || n3 == 15 || (n3 == 2 && n <= 2) || (n3 == 26 && n <= 2) || n3 == 1 || n3 == 18 || n3 == 30 || (n3 == 0 && calendar.getType().equals("chinese")) || n3 == 8) {
                    boolean b3 = false;
                    if (messageFormat != null && (n3 == 2 || n3 == 26)) {
                        final Object[] parse = messageFormat.parse(s, parsePosition);
                        if (parse != null && parsePosition.getIndex() > i && parse[0] instanceof Number) {
                            b3 = true;
                            n2 = (Number)parse[0];
                            calendar.set(22, 1);
                        }
                        else {
                            parsePosition.setIndex(i);
                            calendar.set(22, 0);
                        }
                    }
                    if (!b3) {
                        if (b) {
                            if (i + n > s.length()) {
                                return -i;
                            }
                            n2 = this.parseInt(s, n, parsePosition, b2, numberFormat);
                        }
                        else {
                            n2 = this.parseInt(s, parsePosition, b2, numberFormat);
                        }
                        if (n2 == null && n3 != 30) {
                            return -i;
                        }
                    }
                    if (n2 != null) {
                        intValue = n2.intValue();
                    }
                }
                switch (n3) {
                    case 0: {
                        if (calendar.getType().equals("chinese")) {
                            calendar.set(0, intValue);
                            return parsePosition.getIndex();
                        }
                        int n5;
                        if (n == 5) {
                            n5 = this.matchString(s, i, 0, this.formatData.narrowEras, null, calendar);
                        }
                        else if (n == 4) {
                            n5 = this.matchString(s, i, 0, this.formatData.eraNames, null, calendar);
                        }
                        else {
                            n5 = this.matchString(s, i, 0, this.formatData.eras, null, calendar);
                        }
                        if (n5 == -i) {
                            n5 = -32000;
                        }
                        return n5;
                    }
                    case 1:
                    case 18: {
                        if (this.override != null && (this.override.compareTo("hebr") == 0 || this.override.indexOf("y=hebr") >= 0) && intValue < 1000) {
                            intValue += 5000;
                        }
                        else if (n == 2 && parsePosition.getIndex() - i == 2 && !calendar.getType().equals("chinese") && UCharacter.isDigit(s.charAt(i)) && UCharacter.isDigit(s.charAt(i + 1))) {
                            final int n6 = this.getDefaultCenturyStartYear() % 100;
                            array[0] = (intValue == n6);
                            intValue += this.getDefaultCenturyStartYear() / 100 * 100 + ((intValue < n6) ? 100 : 0);
                        }
                        calendar.set(n4, intValue);
                        if (SimpleDateFormat.DelayedHebrewMonthCheck) {
                            if (!HebrewCalendar.isLeapYear(intValue)) {
                                calendar.add(2, 1);
                            }
                            SimpleDateFormat.DelayedHebrewMonthCheck = false;
                        }
                        return parsePosition.getIndex();
                    }
                    case 30: {
                        if (this.formatData.shortYearNames != null) {
                            final int matchString = this.matchString(s, i, 1, this.formatData.shortYearNames, null, calendar);
                            if (matchString > 0) {
                                return matchString;
                            }
                        }
                        if (n2 != null && (lenient || this.formatData.shortYearNames == null || intValue > this.formatData.shortYearNames.length)) {
                            calendar.set(1, intValue);
                            return parsePosition.getIndex();
                        }
                        return -i;
                    }
                    case 2:
                    case 26: {
                        if (n <= 2) {
                            calendar.set(2, intValue - 1);
                            if (calendar.getType().equals("hebrew") && intValue >= 6) {
                                if (calendar.isSet(1)) {
                                    if (!HebrewCalendar.isLeapYear(calendar.get(1))) {
                                        calendar.set(2, intValue);
                                    }
                                }
                                else {
                                    SimpleDateFormat.DelayedHebrewMonthCheck = true;
                                }
                            }
                            return parsePosition.getIndex();
                        }
                        final boolean b4 = this.formatData.leapMonthPatterns != null && this.formatData.leapMonthPatterns.length >= 7;
                        final int n7 = (n3 == 2) ? this.matchString(s, i, 2, this.formatData.months, b4 ? this.formatData.leapMonthPatterns[0] : null, calendar) : this.matchString(s, i, 2, this.formatData.standaloneMonths, b4 ? this.formatData.leapMonthPatterns[3] : null, calendar);
                        if (n7 > 0) {
                            return n7;
                        }
                        return (n3 == 2) ? this.matchString(s, i, 2, this.formatData.shortMonths, b4 ? this.formatData.leapMonthPatterns[1] : null, calendar) : this.matchString(s, i, 2, this.formatData.standaloneShortMonths, b4 ? this.formatData.leapMonthPatterns[4] : null, calendar);
                    }
                    case 4: {
                        if (intValue == calendar.getMaximum(11) + 1) {
                            intValue = 0;
                        }
                        calendar.set(11, intValue);
                        return parsePosition.getIndex();
                    }
                    case 8: {
                        int j = parsePosition.getIndex() - i;
                        if (j < 3) {
                            while (j < 3) {
                                intValue *= 10;
                                ++j;
                            }
                        }
                        else {
                            int n8 = 1;
                            while (j > 3) {
                                n8 *= 10;
                                --j;
                            }
                            intValue /= n8;
                        }
                        calendar.set(14, intValue);
                        return parsePosition.getIndex();
                    }
                    case 9: {
                        final int matchString2 = this.matchString(s, i, 7, this.formatData.weekdays, null, calendar);
                        if (matchString2 > 0) {
                            return matchString2;
                        }
                        final int matchString3;
                        if ((matchString3 = this.matchString(s, i, 7, this.formatData.shortWeekdays, null, calendar)) > 0) {
                            return matchString3;
                        }
                        if (this.formatData.shorterWeekdays != null) {
                            return this.matchString(s, i, 7, this.formatData.shorterWeekdays, null, calendar);
                        }
                        return matchString3;
                    }
                    case 25: {
                        final int matchString4 = this.matchString(s, i, 7, this.formatData.standaloneWeekdays, null, calendar);
                        if (matchString4 > 0) {
                            return matchString4;
                        }
                        final int matchString5;
                        if ((matchString5 = this.matchString(s, i, 7, this.formatData.standaloneShortWeekdays, null, calendar)) > 0) {
                            return matchString5;
                        }
                        if (this.formatData.standaloneShorterWeekdays != null) {
                            return this.matchString(s, i, 7, this.formatData.standaloneShorterWeekdays, null, calendar);
                        }
                        return matchString5;
                    }
                    case 14: {
                        return this.matchString(s, i, 9, this.formatData.ampms, null, calendar);
                    }
                    case 15: {
                        if (intValue == calendar.getLeastMaximum(10) + 1) {
                            intValue = 0;
                        }
                        calendar.set(10, intValue);
                        return parsePosition.getIndex();
                    }
                    case 17: {
                        final Output output = new Output();
                        final TimeZone parse2 = this.tzFormat().parse((n < 4) ? TimeZoneFormat.Style.SPECIFIC_SHORT : TimeZoneFormat.Style.SPECIFIC_LONG, s, parsePosition, output);
                        if (parse2 != null) {
                            this.tztype = (TimeZoneFormat.TimeType)output.value;
                            calendar.setTimeZone(parse2);
                            return parsePosition.getIndex();
                        }
                        return -i;
                    }
                    case 23: {
                        final Output output2 = new Output();
                        final TimeZone parse3 = this.tzFormat().parse((n < 4) ? TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL : ((n == 5) ? TimeZoneFormat.Style.ISO_EXTENDED_FULL : TimeZoneFormat.Style.LOCALIZED_GMT), s, parsePosition, output2);
                        if (parse3 != null) {
                            this.tztype = (TimeZoneFormat.TimeType)output2.value;
                            calendar.setTimeZone(parse3);
                            return parsePosition.getIndex();
                        }
                        return -i;
                    }
                    case 24: {
                        final Output output3 = new Output();
                        final TimeZone parse4 = this.tzFormat().parse((n < 4) ? TimeZoneFormat.Style.GENERIC_SHORT : TimeZoneFormat.Style.GENERIC_LONG, s, parsePosition, output3);
                        if (parse4 != null) {
                            this.tztype = (TimeZoneFormat.TimeType)output3.value;
                            calendar.setTimeZone(parse4);
                            return parsePosition.getIndex();
                        }
                        return -i;
                    }
                    case 29: {
                        final Output output4 = new Output();
                        TimeZoneFormat.Style style = null;
                        switch (n) {
                            case 1: {
                                style = TimeZoneFormat.Style.ZONE_ID_SHORT;
                                break;
                            }
                            case 2: {
                                style = TimeZoneFormat.Style.ZONE_ID;
                                break;
                            }
                            case 3: {
                                style = TimeZoneFormat.Style.EXEMPLAR_LOCATION;
                                break;
                            }
                            default: {
                                style = TimeZoneFormat.Style.GENERIC_LOCATION;
                                break;
                            }
                        }
                        final TimeZone parse5 = this.tzFormat().parse(style, s, parsePosition, output4);
                        if (parse5 != null) {
                            this.tztype = (TimeZoneFormat.TimeType)output4.value;
                            calendar.setTimeZone(parse5);
                            return parsePosition.getIndex();
                        }
                        return -i;
                    }
                    case 31: {
                        final Output output5 = new Output();
                        final TimeZone parse6 = this.tzFormat().parse((n < 4) ? TimeZoneFormat.Style.LOCALIZED_GMT_SHORT : TimeZoneFormat.Style.LOCALIZED_GMT, s, parsePosition, output5);
                        if (parse6 != null) {
                            this.tztype = (TimeZoneFormat.TimeType)output5.value;
                            calendar.setTimeZone(parse6);
                            return parsePosition.getIndex();
                        }
                        return -i;
                    }
                    case 32: {
                        final Output output6 = new Output();
                        TimeZoneFormat.Style style2 = null;
                        switch (n) {
                            case 1: {
                                style2 = TimeZoneFormat.Style.ISO_BASIC_SHORT;
                                break;
                            }
                            case 2: {
                                style2 = TimeZoneFormat.Style.ISO_BASIC_FIXED;
                                break;
                            }
                            case 3: {
                                style2 = TimeZoneFormat.Style.ISO_EXTENDED_FIXED;
                                break;
                            }
                            case 4: {
                                style2 = TimeZoneFormat.Style.ISO_BASIC_FULL;
                                break;
                            }
                            default: {
                                style2 = TimeZoneFormat.Style.ISO_EXTENDED_FULL;
                                break;
                            }
                        }
                        final TimeZone parse7 = this.tzFormat().parse(style2, s, parsePosition, output6);
                        if (parse7 != null) {
                            this.tztype = (TimeZoneFormat.TimeType)output6.value;
                            calendar.setTimeZone(parse7);
                            return parsePosition.getIndex();
                        }
                        return -i;
                    }
                    case 33: {
                        final Output output7 = new Output();
                        TimeZoneFormat.Style style3 = null;
                        switch (n) {
                            case 1: {
                                style3 = TimeZoneFormat.Style.ISO_BASIC_LOCAL_SHORT;
                                break;
                            }
                            case 2: {
                                style3 = TimeZoneFormat.Style.ISO_BASIC_LOCAL_FIXED;
                                break;
                            }
                            case 3: {
                                style3 = TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FIXED;
                                break;
                            }
                            case 4: {
                                style3 = TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL;
                                break;
                            }
                            default: {
                                style3 = TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FULL;
                                break;
                            }
                        }
                        final TimeZone parse8 = this.tzFormat().parse(style3, s, parsePosition, output7);
                        if (parse8 != null) {
                            this.tztype = (TimeZoneFormat.TimeType)output7.value;
                            calendar.setTimeZone(parse8);
                            return parsePosition.getIndex();
                        }
                        return -i;
                    }
                    case 27: {
                        if (n <= 2) {
                            calendar.set(2, (intValue - 1) * 3);
                            return parsePosition.getIndex();
                        }
                        final int matchQuarterString = this.matchQuarterString(s, i, 2, this.formatData.quarters, calendar);
                        if (matchQuarterString > 0) {
                            return matchQuarterString;
                        }
                        return this.matchQuarterString(s, i, 2, this.formatData.shortQuarters, calendar);
                    }
                    case 28: {
                        if (n <= 2) {
                            calendar.set(2, (intValue - 1) * 3);
                            return parsePosition.getIndex();
                        }
                        final int matchQuarterString2 = this.matchQuarterString(s, i, 2, this.formatData.standaloneQuarters, calendar);
                        if (matchQuarterString2 > 0) {
                            return matchQuarterString2;
                        }
                        return this.matchQuarterString(s, i, 2, this.formatData.standaloneShortQuarters, calendar);
                    }
                    default: {
                        Number n9;
                        if (b) {
                            if (i + n > s.length()) {
                                return -i;
                            }
                            n9 = this.parseInt(s, n, parsePosition, b2, numberFormat);
                        }
                        else {
                            n9 = this.parseInt(s, parsePosition, b2, numberFormat);
                        }
                        if (n9 != null) {
                            calendar.set(n4, n9.intValue());
                            return parsePosition.getIndex();
                        }
                        return -i;
                    }
                }
            }
        }
        return -i;
    }
    
    private Number parseInt(final String s, final ParsePosition parsePosition, final boolean b, final NumberFormat numberFormat) {
        return this.parseInt(s, -1, parsePosition, b, numberFormat);
    }
    
    private Number parseInt(final String s, final int n, final ParsePosition parsePosition, final boolean b, final NumberFormat numberFormat) {
        final int index = parsePosition.getIndex();
        Number n2;
        if (b) {
            n2 = numberFormat.parse(s, parsePosition);
        }
        else if (numberFormat instanceof DecimalFormat) {
            final String negativePrefix = ((DecimalFormat)numberFormat).getNegativePrefix();
            ((DecimalFormat)numberFormat).setNegativePrefix("\uab00");
            n2 = numberFormat.parse(s, parsePosition);
            ((DecimalFormat)numberFormat).setNegativePrefix(negativePrefix);
        }
        else {
            final boolean b2 = numberFormat instanceof DateNumberFormat;
            if (b2) {
                ((DateNumberFormat)numberFormat).setParsePositiveOnly(true);
            }
            n2 = numberFormat.parse(s, parsePosition);
            if (b2) {
                ((DateNumberFormat)numberFormat).setParsePositiveOnly(false);
            }
        }
        if (n > 0) {
            final int n3 = parsePosition.getIndex() - index;
            if (n3 > n) {
                double doubleValue = n2.doubleValue();
                for (int i = n3 - n; i > 0; --i) {
                    doubleValue /= 10.0;
                }
                parsePosition.setIndex(index + n);
                n2 = (int)doubleValue;
            }
        }
        return n2;
    }
    
    private String translatePattern(final String s, final String s2, final String s3) {
        final StringBuilder sb = new StringBuilder();
        int n = 0;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (n != 0) {
                if (c == '\'') {
                    n = 0;
                }
            }
            else if (c == '\'') {
                n = 1;
            }
            else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                final int index = s2.indexOf(c);
                if (index != -1) {
                    c = s3.charAt(index);
                }
            }
            sb.append(c);
        }
        if (n != 0) {
            throw new IllegalArgumentException("Unfinished quote in pattern");
        }
        return sb.toString();
    }
    
    public String toPattern() {
        return this.pattern;
    }
    
    public String toLocalizedPattern() {
        return this.translatePattern(this.pattern, "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXx", this.formatData.localPatternChars);
    }
    
    public void applyPattern(final String pattern) {
        this.pattern = pattern;
        this.setLocale(null, null);
        this.patternItems = null;
    }
    
    public void applyLocalizedPattern(final String s) {
        this.pattern = this.translatePattern(s, this.formatData.localPatternChars, "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXx");
        this.setLocale(null, null);
    }
    
    public DateFormatSymbols getDateFormatSymbols() {
        return (DateFormatSymbols)this.formatData.clone();
    }
    
    public void setDateFormatSymbols(final DateFormatSymbols dateFormatSymbols) {
        this.formatData = (DateFormatSymbols)dateFormatSymbols.clone();
    }
    
    protected DateFormatSymbols getSymbols() {
        return this.formatData;
    }
    
    public TimeZoneFormat getTimeZoneFormat() {
        return this.tzFormat().freeze();
    }
    
    public void setTimeZoneFormat(final TimeZoneFormat tzFormat) {
        if (tzFormat.isFrozen()) {
            this.tzFormat = tzFormat;
        }
        else {
            this.tzFormat = tzFormat.cloneAsThawed().freeze();
        }
    }
    
    public void setContext(final DisplayContext capitalizationSetting) {
        if (capitalizationSetting.type() == DisplayContext.Type.CAPITALIZATION) {
            this.capitalizationSetting = capitalizationSetting;
        }
    }
    
    public DisplayContext getContext(final DisplayContext.Type type) {
        return (type == DisplayContext.Type.CAPITALIZATION && this.capitalizationSetting != null) ? this.capitalizationSetting : DisplayContext.CAPITALIZATION_NONE;
    }
    
    @Override
    public Object clone() {
        final SimpleDateFormat simpleDateFormat = (SimpleDateFormat)super.clone();
        simpleDateFormat.formatData = (DateFormatSymbols)this.formatData.clone();
        return simpleDateFormat;
    }
    
    @Override
    public int hashCode() {
        return this.pattern.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!super.equals(o)) {
            return false;
        }
        final SimpleDateFormat simpleDateFormat = (SimpleDateFormat)o;
        return this.pattern.equals(simpleDateFormat.pattern) && this.formatData.equals(simpleDateFormat.formatData);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        if (this.defaultCenturyStart == null) {
            this.initializeDefaultCenturyStart(this.defaultCenturyBase);
        }
        this.initializeTimeZoneFormat(false);
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.capitalizationSetting.value());
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        final int n = (this.serialVersionOnStream > 1) ? objectInputStream.readInt() : -1;
        if (this.serialVersionOnStream < 1) {
            this.defaultCenturyBase = System.currentTimeMillis();
        }
        else {
            this.parseAmbiguousDatesAsAfter(this.defaultCenturyStart);
        }
        this.serialVersionOnStream = 2;
        this.locale = this.getLocale(ULocale.VALID_LOCALE);
        if (this.locale == null) {
            this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        this.initLocalZeroPaddingNumberFormat();
        this.capitalizationSetting = DisplayContext.CAPITALIZATION_NONE;
        if (n >= 0) {
            for (final DisplayContext capitalizationSetting : DisplayContext.values()) {
                if (capitalizationSetting.value() == n) {
                    this.capitalizationSetting = capitalizationSetting;
                    break;
                }
            }
        }
    }
    
    @Override
    public AttributedCharacterIterator formatToCharacterIterator(final Object o) {
        Calendar calendar = this.calendar;
        if (o instanceof Calendar) {
            calendar = (Calendar)o;
        }
        else if (o instanceof Date) {
            this.calendar.setTime((Date)o);
        }
        else {
            if (!(o instanceof Number)) {
                throw new IllegalArgumentException("Cannot format given Object as a Date");
            }
            this.calendar.setTimeInMillis(((Number)o).longValue());
        }
        final StringBuffer sb = new StringBuffer();
        final FieldPosition fieldPosition = new FieldPosition(0);
        final ArrayList<FieldPosition> list = new ArrayList<FieldPosition>();
        this.format(calendar, this.capitalizationSetting, sb, fieldPosition, list);
        final AttributedString attributedString = new AttributedString(sb.toString());
        for (int i = 0; i < list.size(); ++i) {
            final FieldPosition fieldPosition2 = list.get(i);
            final Format.Field fieldAttribute = fieldPosition2.getFieldAttribute();
            attributedString.addAttribute(fieldAttribute, fieldAttribute, fieldPosition2.getBeginIndex(), fieldPosition2.getEndIndex());
        }
        return attributedString.getIterator();
    }
    
    ULocale getLocale() {
        return this.locale;
    }
    
    boolean isFieldUnitIgnored(final int n) {
        return isFieldUnitIgnored(this.pattern, n);
    }
    
    static boolean isFieldUnitIgnored(final String s, final int n) {
        final int n2 = SimpleDateFormat.CALENDAR_FIELD_TO_LEVEL[n];
        boolean b = false;
        int n3 = '\0';
        int n4 = 0;
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (char1 != n3 && n4 > 0) {
                if (n2 <= SimpleDateFormat.PATTERN_CHAR_TO_LEVEL[n3 - '@']) {
                    return false;
                }
                n4 = 0;
            }
            if (char1 == '\'') {
                if (i + 1 < s.length() && s.charAt(i + 1) == '\'') {
                    ++i;
                }
                else {
                    b = !b;
                }
            }
            else if (!b && ((char1 >= 'a' && char1 <= 'z') || (char1 >= 'A' && char1 <= 'Z'))) {
                n3 = char1;
                ++n4;
            }
        }
        return n4 <= 0 || n2 > SimpleDateFormat.PATTERN_CHAR_TO_LEVEL[n3 - '@'];
    }
    
    @Deprecated
    public final StringBuffer intervalFormatByAlgorithm(final Calendar calendar, final Calendar calendar2, final StringBuffer sb, final FieldPosition fieldPosition) throws IllegalArgumentException {
        if (!calendar.isEquivalentTo(calendar2)) {
            throw new IllegalArgumentException("can not format on two different calendars");
        }
        final Object[] patternItems = this.getPatternItems();
        int n = -1;
        int n2 = -1;
        try {
            for (int i = 0; i < patternItems.length; ++i) {
                if (this.diffCalFieldValue(calendar, calendar2, patternItems, i)) {
                    n = i;
                    break;
                }
            }
            if (n == -1) {
                return this.format(calendar, sb, fieldPosition);
            }
            for (int j = patternItems.length - 1; j >= n; --j) {
                if (this.diffCalFieldValue(calendar, calendar2, patternItems, j)) {
                    n2 = j;
                    break;
                }
            }
        }
        catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.toString());
        }
        if (n == 0 && n2 == patternItems.length - 1) {
            this.format(calendar, sb, fieldPosition);
            sb.append(" \u2013 ");
            this.format(calendar2, sb, fieldPosition);
            return sb;
        }
        int n3 = 1000;
        for (int k = n; k <= n2; ++k) {
            if (!(patternItems[k] instanceof String)) {
                final char type = ((PatternItem)patternItems[k]).type;
                int n4 = -1;
                if ('A' <= type && type <= 'z') {
                    n4 = SimpleDateFormat.PATTERN_CHAR_TO_LEVEL[type - '@'];
                }
                if (n4 == -1) {
                    throw new IllegalArgumentException("Illegal pattern character '" + type + "' in \"" + this.pattern + '\"');
                }
                if (n4 < n3) {
                    n3 = n4;
                }
            }
        }
        try {
            for (int l = 0; l < n; ++l) {
                if (this.lowerLevel(patternItems, l, n3)) {
                    n = l;
                    break;
                }
            }
            for (int n5 = patternItems.length - 1; n5 > n2; --n5) {
                if (this.lowerLevel(patternItems, n5, n3)) {
                    n2 = n5;
                    break;
                }
            }
        }
        catch (IllegalArgumentException ex2) {
            throw new IllegalArgumentException(ex2.toString());
        }
        if (n == 0 && n2 == patternItems.length - 1) {
            this.format(calendar, sb, fieldPosition);
            sb.append(" \u2013 ");
            this.format(calendar2, sb, fieldPosition);
            return sb;
        }
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        for (int n6 = 0; n6 <= n2; ++n6) {
            if (patternItems[n6] instanceof String) {
                sb.append((String)patternItems[n6]);
            }
            else {
                final PatternItem patternItem = (PatternItem)patternItems[n6];
                if (this.useFastFormat) {
                    this.subFormat(sb, patternItem.type, patternItem.length, sb.length(), n6, this.capitalizationSetting, fieldPosition, calendar);
                }
                else {
                    sb.append(this.subFormat(patternItem.type, patternItem.length, sb.length(), n6, this.capitalizationSetting, fieldPosition, calendar));
                }
            }
        }
        sb.append(" \u2013 ");
        for (int n7 = n; n7 < patternItems.length; ++n7) {
            if (patternItems[n7] instanceof String) {
                sb.append((String)patternItems[n7]);
            }
            else {
                final PatternItem patternItem2 = (PatternItem)patternItems[n7];
                if (this.useFastFormat) {
                    this.subFormat(sb, patternItem2.type, patternItem2.length, sb.length(), n7, this.capitalizationSetting, fieldPosition, calendar2);
                }
                else {
                    sb.append(this.subFormat(patternItem2.type, patternItem2.length, sb.length(), n7, this.capitalizationSetting, fieldPosition, calendar2));
                }
            }
        }
        return sb;
    }
    
    private boolean diffCalFieldValue(final Calendar calendar, final Calendar calendar2, final Object[] array, final int n) throws IllegalArgumentException {
        if (array[n] instanceof String) {
            return false;
        }
        final char type = ((PatternItem)array[n]).type;
        int n2 = -1;
        if ('A' <= type && type <= 'z') {
            n2 = SimpleDateFormat.PATTERN_CHAR_TO_INDEX[type - '@'];
        }
        if (n2 == -1) {
            throw new IllegalArgumentException("Illegal pattern character '" + type + "' in \"" + this.pattern + '\"');
        }
        final int n3 = SimpleDateFormat.PATTERN_INDEX_TO_CALENDAR_FIELD[n2];
        return calendar.get(n3) != calendar2.get(n3);
    }
    
    private boolean lowerLevel(final Object[] array, final int n, final int n2) throws IllegalArgumentException {
        if (array[n] instanceof String) {
            return false;
        }
        final char type = ((PatternItem)array[n]).type;
        int n3 = -1;
        if ('A' <= type && type <= 'z') {
            n3 = SimpleDateFormat.PATTERN_CHAR_TO_LEVEL[type - '@'];
        }
        if (n3 == -1) {
            throw new IllegalArgumentException("Illegal pattern character '" + type + "' in \"" + this.pattern + '\"');
        }
        return n3 >= n2;
    }
    
    @Deprecated
    protected NumberFormat getNumberFormat(final char c) {
        final Character value = c;
        if (this.overrideMap != null && this.overrideMap.containsKey(value)) {
            return (NumberFormat)this.numberFormatters.get(((String)this.overrideMap.get(value)).toString());
        }
        return this.numberFormat;
    }
    
    private void initNumberFormatters(final ULocale uLocale) {
        this.numberFormatters = new HashMap();
        this.overrideMap = new HashMap();
        this.processOverrideString(uLocale, this.override);
    }
    
    private void processOverrideString(final ULocale uLocale, final String s) {
        if (s == null || s.length() == 0) {
            return;
        }
        int n = 0;
        int i = 1;
        while (i != 0) {
            final int index = s.indexOf(";", n);
            int length;
            if (index == -1) {
                i = 0;
                length = s.length();
            }
            else {
                length = index;
            }
            final String substring = s.substring(n, length);
            final int index2 = substring.indexOf("=");
            String substring2;
            boolean b;
            if (index2 == -1) {
                substring2 = substring;
                b = true;
            }
            else {
                substring2 = substring.substring(index2 + 1);
                this.overrideMap.put(substring.charAt(0), substring2);
                b = false;
            }
            final NumberFormat instance = NumberFormat.createInstance(new ULocale(uLocale.getBaseName() + "@numbers=" + substring2), 0);
            instance.setGroupingUsed(false);
            if (b) {
                this.setNumberFormat(instance);
            }
            else {
                this.useLocalZeroPaddingNumberFormat = false;
            }
            if (!this.numberFormatters.containsKey(substring2)) {
                this.numberFormatters.put(substring2, instance);
            }
            n = index + 1;
        }
    }
    
    static boolean access$000(final char c, final int n) {
        return isNumeric(c, n);
    }
    
    static {
        SimpleDateFormat.DelayedHebrewMonthCheck = false;
        CALENDAR_FIELD_TO_LEVEL = new int[] { 0, 10, 20, 20, 30, 30, 20, 30, 30, 40, 50, 50, 60, 70, 80, 0, 0, 10, 30, 10, 0, 40 };
        PATTERN_CHAR_TO_LEVEL = new int[] { -1, 40, -1, -1, 20, 30, 30, 0, 50, -1, -1, 50, 20, 20, -1, 0, -1, 20, -1, 80, -1, 10, 0, 30, 0, 10, 0, -1, -1, -1, -1, -1, -1, 40, -1, 30, 30, 30, -1, 0, 50, -1, -1, 50, -1, 60, -1, -1, -1, 20, -1, 70, -1, 10, 0, 20, 0, 10, 0, -1, -1, -1, -1, -1 };
        SimpleDateFormat.cachedDefaultLocale = null;
        SimpleDateFormat.cachedDefaultPattern = null;
        PATTERN_CHAR_TO_INDEX = new int[] { -1, 22, -1, -1, 10, 9, 11, 0, 5, -1, -1, 16, 26, 2, -1, 31, -1, 27, -1, 8, -1, 30, 29, 13, 32, 18, 23, -1, -1, -1, -1, -1, -1, 14, -1, 25, 3, 19, -1, 21, 15, -1, -1, 4, -1, 6, -1, -1, -1, 28, -1, 7, -1, 20, 24, 12, 33, 1, 17, -1, -1, -1, -1, -1 };
        PATTERN_INDEX_TO_CALENDAR_FIELD = new int[] { 0, 1, 2, 5, 11, 11, 12, 13, 14, 7, 6, 8, 3, 4, 9, 10, 10, 15, 17, 18, 19, 20, 21, 15, 15, 18, 2, 2, 2, 15, 1, 15, 15, 15 };
        PATTERN_INDEX_TO_DATE_FORMAT_FIELD = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33 };
        PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE = new Field[] { Field.ERA, Field.YEAR, Field.MONTH, Field.DAY_OF_MONTH, Field.HOUR_OF_DAY1, Field.HOUR_OF_DAY0, Field.MINUTE, Field.SECOND, Field.MILLISECOND, Field.DAY_OF_WEEK, Field.DAY_OF_YEAR, Field.DAY_OF_WEEK_IN_MONTH, Field.WEEK_OF_YEAR, Field.WEEK_OF_MONTH, Field.AM_PM, Field.HOUR1, Field.HOUR0, Field.TIME_ZONE, Field.YEAR_WOY, Field.DOW_LOCAL, Field.EXTENDED_YEAR, Field.JULIAN_DAY, Field.MILLISECONDS_IN_DAY, Field.TIME_ZONE, Field.TIME_ZONE, Field.DAY_OF_WEEK, Field.MONTH, Field.QUARTER, Field.QUARTER, Field.TIME_ZONE, Field.YEAR, Field.TIME_ZONE, Field.TIME_ZONE, Field.TIME_ZONE };
        SimpleDateFormat.PARSED_PATTERN_CACHE = new SimpleCache();
        DATE_PATTERN_TYPE = new UnicodeSet("[GyYuUQqMLlwWd]").freeze();
    }
    
    private static class PatternItem
    {
        final char type;
        final int length;
        final boolean isNumeric;
        
        PatternItem(final char type, final int length) {
            this.type = type;
            this.length = length;
            this.isNumeric = SimpleDateFormat.access$000(type, length);
        }
    }
    
    private enum ContextValue
    {
        UNKNOWN("UNKNOWN", 0), 
        CAPITALIZATION_FOR_MIDDLE_OF_SENTENCE("CAPITALIZATION_FOR_MIDDLE_OF_SENTENCE", 1), 
        CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE("CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE", 2), 
        CAPITALIZATION_FOR_UI_LIST_OR_MENU("CAPITALIZATION_FOR_UI_LIST_OR_MENU", 3), 
        CAPITALIZATION_FOR_STANDALONE("CAPITALIZATION_FOR_STANDALONE", 4);
        
        private static final ContextValue[] $VALUES;
        
        private ContextValue(final String s, final int n) {
        }
        
        static {
            $VALUES = new ContextValue[] { ContextValue.UNKNOWN, ContextValue.CAPITALIZATION_FOR_MIDDLE_OF_SENTENCE, ContextValue.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE, ContextValue.CAPITALIZATION_FOR_UI_LIST_OR_MENU, ContextValue.CAPITALIZATION_FOR_STANDALONE };
        }
    }
}
