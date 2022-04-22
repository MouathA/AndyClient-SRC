package com.ibm.icu.text;

import com.ibm.icu.util.*;
import java.util.*;
import java.io.*;
import com.ibm.icu.impl.*;

public class DateFormatSymbols implements Serializable, Cloneable
{
    public static final int FORMAT = 0;
    public static final int STANDALONE = 1;
    @Deprecated
    public static final int DT_CONTEXT_COUNT = 2;
    public static final int ABBREVIATED = 0;
    public static final int WIDE = 1;
    public static final int NARROW = 2;
    public static final int SHORT = 3;
    @Deprecated
    public static final int DT_WIDTH_COUNT = 4;
    static final int DT_LEAP_MONTH_PATTERN_FORMAT_WIDE = 0;
    static final int DT_LEAP_MONTH_PATTERN_FORMAT_ABBREV = 1;
    static final int DT_LEAP_MONTH_PATTERN_FORMAT_NARROW = 2;
    static final int DT_LEAP_MONTH_PATTERN_STANDALONE_WIDE = 3;
    static final int DT_LEAP_MONTH_PATTERN_STANDALONE_ABBREV = 4;
    static final int DT_LEAP_MONTH_PATTERN_STANDALONE_NARROW = 5;
    static final int DT_LEAP_MONTH_PATTERN_NUMERIC = 6;
    static final int DT_MONTH_PATTERN_COUNT = 7;
    String[] eras;
    String[] eraNames;
    String[] narrowEras;
    String[] months;
    String[] shortMonths;
    String[] narrowMonths;
    String[] standaloneMonths;
    String[] standaloneShortMonths;
    String[] standaloneNarrowMonths;
    String[] weekdays;
    String[] shortWeekdays;
    String[] shorterWeekdays;
    String[] narrowWeekdays;
    String[] standaloneWeekdays;
    String[] standaloneShortWeekdays;
    String[] standaloneShorterWeekdays;
    String[] standaloneNarrowWeekdays;
    String[] ampms;
    String[] shortQuarters;
    String[] quarters;
    String[] standaloneShortQuarters;
    String[] standaloneQuarters;
    String[] leapMonthPatterns;
    String[] shortYearNames;
    private String[][] zoneStrings;
    static final String patternChars = "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXx";
    String localPatternChars;
    private static final long serialVersionUID = -5987973545549424702L;
    private static final String[][] CALENDAR_CLASSES;
    private static final Map contextUsageTypeMap;
    Map capitalization;
    static final int millisPerHour = 3600000;
    private static ICUCache DFSCACHE;
    private ULocale requestedLocale;
    private ULocale validLocale;
    private ULocale actualLocale;
    
    public DateFormatSymbols() {
        this(ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public DateFormatSymbols(final Locale locale) {
        this(ULocale.forLocale(locale));
    }
    
    public DateFormatSymbols(final ULocale uLocale) {
        this.eras = null;
        this.eraNames = null;
        this.narrowEras = null;
        this.months = null;
        this.shortMonths = null;
        this.narrowMonths = null;
        this.standaloneMonths = null;
        this.standaloneShortMonths = null;
        this.standaloneNarrowMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.shorterWeekdays = null;
        this.narrowWeekdays = null;
        this.standaloneWeekdays = null;
        this.standaloneShortWeekdays = null;
        this.standaloneShorterWeekdays = null;
        this.standaloneNarrowWeekdays = null;
        this.ampms = null;
        this.shortQuarters = null;
        this.quarters = null;
        this.standaloneShortQuarters = null;
        this.standaloneQuarters = null;
        this.leapMonthPatterns = null;
        this.shortYearNames = null;
        this.zoneStrings = null;
        this.localPatternChars = null;
        this.capitalization = null;
        this.initializeData(uLocale, CalendarUtil.getCalendarType(uLocale));
    }
    
    public static DateFormatSymbols getInstance() {
        return new DateFormatSymbols();
    }
    
    public static DateFormatSymbols getInstance(final Locale locale) {
        return new DateFormatSymbols(locale);
    }
    
    public static DateFormatSymbols getInstance(final ULocale uLocale) {
        return new DateFormatSymbols(uLocale);
    }
    
    public static Locale[] getAvailableLocales() {
        return ICUResourceBundle.getAvailableLocales();
    }
    
    public static ULocale[] getAvailableULocales() {
        return ICUResourceBundle.getAvailableULocales();
    }
    
    public String[] getEras() {
        return this.duplicate(this.eras);
    }
    
    public void setEras(final String[] array) {
        this.eras = this.duplicate(array);
    }
    
    public String[] getEraNames() {
        return this.duplicate(this.eraNames);
    }
    
    public void setEraNames(final String[] array) {
        this.eraNames = this.duplicate(array);
    }
    
    public String[] getMonths() {
        return this.duplicate(this.months);
    }
    
    public String[] getMonths(final int n, final int n2) {
        String[] array = null;
        Label_0137: {
            switch (n) {
                case 0: {
                    switch (n2) {
                        case 1: {
                            array = this.months;
                            break;
                        }
                        case 0:
                        case 3: {
                            array = this.shortMonths;
                            break;
                        }
                        case 2: {
                            array = this.narrowMonths;
                            break;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (n2) {
                        case 1: {
                            array = this.standaloneMonths;
                            break Label_0137;
                        }
                        case 0:
                        case 3: {
                            array = this.standaloneShortMonths;
                            break Label_0137;
                        }
                        case 2: {
                            array = this.standaloneNarrowMonths;
                            break Label_0137;
                        }
                    }
                    break;
                }
            }
        }
        return this.duplicate(array);
    }
    
    public void setMonths(final String[] array) {
        this.months = this.duplicate(array);
    }
    
    public void setMonths(final String[] array, final int n, final int n2) {
        Label_0157: {
            switch (n) {
                case 0: {
                    switch (n2) {
                        case 1: {
                            this.months = this.duplicate(array);
                            break Label_0157;
                        }
                        case 0: {
                            this.shortMonths = this.duplicate(array);
                            break Label_0157;
                        }
                        case 2: {
                            this.narrowMonths = this.duplicate(array);
                            break Label_0157;
                        }
                        default: {
                            break Label_0157;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (n2) {
                        case 1: {
                            this.standaloneMonths = this.duplicate(array);
                            break Label_0157;
                        }
                        case 0: {
                            this.standaloneShortMonths = this.duplicate(array);
                            break Label_0157;
                        }
                        case 2: {
                            this.standaloneNarrowMonths = this.duplicate(array);
                            break Label_0157;
                        }
                    }
                    break;
                }
            }
        }
    }
    
    public String[] getShortMonths() {
        return this.duplicate(this.shortMonths);
    }
    
    public void setShortMonths(final String[] array) {
        this.shortMonths = this.duplicate(array);
    }
    
    public String[] getWeekdays() {
        return this.duplicate(this.weekdays);
    }
    
    public String[] getWeekdays(final int n, final int n2) {
        String[] array = null;
        Label_0179: {
            switch (n) {
                case 0: {
                    switch (n2) {
                        case 1: {
                            array = this.weekdays;
                            break;
                        }
                        case 0: {
                            array = this.shortWeekdays;
                            break;
                        }
                        case 3: {
                            array = ((this.shorterWeekdays != null) ? this.shorterWeekdays : this.shortWeekdays);
                            break;
                        }
                        case 2: {
                            array = this.narrowWeekdays;
                            break;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (n2) {
                        case 1: {
                            array = this.standaloneWeekdays;
                            break Label_0179;
                        }
                        case 0: {
                            array = this.standaloneShortWeekdays;
                            break Label_0179;
                        }
                        case 3: {
                            array = ((this.standaloneShorterWeekdays != null) ? this.standaloneShorterWeekdays : this.standaloneShortWeekdays);
                            break Label_0179;
                        }
                        case 2: {
                            array = this.standaloneNarrowWeekdays;
                            break Label_0179;
                        }
                    }
                    break;
                }
            }
        }
        return this.duplicate(array);
    }
    
    public void setWeekdays(final String[] array, final int n, final int n2) {
        Label_0185: {
            switch (n) {
                case 0: {
                    switch (n2) {
                        case 1: {
                            this.weekdays = this.duplicate(array);
                            break;
                        }
                        case 0: {
                            this.shortWeekdays = this.duplicate(array);
                            break;
                        }
                        case 3: {
                            this.shorterWeekdays = this.duplicate(array);
                            break;
                        }
                        case 2: {
                            this.narrowWeekdays = this.duplicate(array);
                            break;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (n2) {
                        case 1: {
                            this.standaloneWeekdays = this.duplicate(array);
                            break Label_0185;
                        }
                        case 0: {
                            this.standaloneShortWeekdays = this.duplicate(array);
                            break Label_0185;
                        }
                        case 3: {
                            this.standaloneShorterWeekdays = this.duplicate(array);
                            break Label_0185;
                        }
                        case 2: {
                            this.standaloneNarrowWeekdays = this.duplicate(array);
                            break Label_0185;
                        }
                    }
                    break;
                }
            }
        }
    }
    
    public void setWeekdays(final String[] array) {
        this.weekdays = this.duplicate(array);
    }
    
    public String[] getShortWeekdays() {
        return this.duplicate(this.shortWeekdays);
    }
    
    public void setShortWeekdays(final String[] array) {
        this.shortWeekdays = this.duplicate(array);
    }
    
    public String[] getQuarters(final int n, final int n2) {
        String[] array = null;
        Label_0130: {
            switch (n) {
                case 0: {
                    switch (n2) {
                        case 1: {
                            array = this.quarters;
                            break;
                        }
                        case 0:
                        case 3: {
                            array = this.shortQuarters;
                            break;
                        }
                        case 2: {
                            array = null;
                            break;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (n2) {
                        case 1: {
                            array = this.standaloneQuarters;
                            break Label_0130;
                        }
                        case 0:
                        case 3: {
                            array = this.standaloneShortQuarters;
                            break Label_0130;
                        }
                        case 2: {
                            array = null;
                            break Label_0130;
                        }
                    }
                    break;
                }
            }
        }
        return this.duplicate(array);
    }
    
    public void setQuarters(final String[] array, final int n, final int n2) {
        Label_0133: {
            switch (n) {
                case 0: {
                    switch (n2) {
                        case 1: {
                            this.quarters = this.duplicate(array);
                            break Label_0133;
                        }
                        case 0: {
                            this.shortQuarters = this.duplicate(array);
                            break Label_0133;
                        }
                        case 2: {
                            break Label_0133;
                        }
                        default: {
                            break Label_0133;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (n2) {
                        case 1: {
                            this.standaloneQuarters = this.duplicate(array);
                            break Label_0133;
                        }
                        case 0: {
                            this.standaloneShortQuarters = this.duplicate(array);
                            break Label_0133;
                        }
                    }
                    break;
                }
            }
        }
    }
    
    public String[] getAmPmStrings() {
        return this.duplicate(this.ampms);
    }
    
    public void setAmPmStrings(final String[] array) {
        this.ampms = this.duplicate(array);
    }
    
    public String[][] getZoneStrings() {
        if (this.zoneStrings != null) {
            return this.duplicate(this.zoneStrings);
        }
        final String[] availableIDs = TimeZone.getAvailableIDs();
        final TimeZoneNames instance = TimeZoneNames.getInstance(this.validLocale);
        final long currentTimeMillis = System.currentTimeMillis();
        final String[][] zoneStrings = new String[availableIDs.length][5];
        while (0 < availableIDs.length) {
            String canonicalID = TimeZone.getCanonicalID(availableIDs[0]);
            if (canonicalID == null) {
                canonicalID = availableIDs[0];
            }
            zoneStrings[0][0] = availableIDs[0];
            zoneStrings[0][1] = instance.getDisplayName(canonicalID, TimeZoneNames.NameType.LONG_STANDARD, currentTimeMillis);
            zoneStrings[0][2] = instance.getDisplayName(canonicalID, TimeZoneNames.NameType.SHORT_STANDARD, currentTimeMillis);
            zoneStrings[0][3] = instance.getDisplayName(canonicalID, TimeZoneNames.NameType.LONG_DAYLIGHT, currentTimeMillis);
            zoneStrings[0][4] = instance.getDisplayName(canonicalID, TimeZoneNames.NameType.SHORT_DAYLIGHT, currentTimeMillis);
            int n = 0;
            ++n;
        }
        return this.zoneStrings = zoneStrings;
    }
    
    public void setZoneStrings(final String[][] array) {
        this.zoneStrings = this.duplicate(array);
    }
    
    public String getLocalPatternChars() {
        return this.localPatternChars;
    }
    
    public void setLocalPatternChars(final String localPatternChars) {
        this.localPatternChars = localPatternChars;
    }
    
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public int hashCode() {
        return this.requestedLocale.toString().hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DateFormatSymbols dateFormatSymbols = (DateFormatSymbols)o;
        return Utility.arrayEquals(this.eras, dateFormatSymbols.eras) && Utility.arrayEquals(this.eraNames, dateFormatSymbols.eraNames) && Utility.arrayEquals(this.months, dateFormatSymbols.months) && Utility.arrayEquals(this.shortMonths, dateFormatSymbols.shortMonths) && Utility.arrayEquals(this.narrowMonths, dateFormatSymbols.narrowMonths) && Utility.arrayEquals(this.standaloneMonths, dateFormatSymbols.standaloneMonths) && Utility.arrayEquals(this.standaloneShortMonths, dateFormatSymbols.standaloneShortMonths) && Utility.arrayEquals(this.standaloneNarrowMonths, dateFormatSymbols.standaloneNarrowMonths) && Utility.arrayEquals(this.weekdays, dateFormatSymbols.weekdays) && Utility.arrayEquals(this.shortWeekdays, dateFormatSymbols.shortWeekdays) && Utility.arrayEquals(this.shorterWeekdays, dateFormatSymbols.shorterWeekdays) && Utility.arrayEquals(this.narrowWeekdays, dateFormatSymbols.narrowWeekdays) && Utility.arrayEquals(this.standaloneWeekdays, dateFormatSymbols.standaloneWeekdays) && Utility.arrayEquals(this.standaloneShortWeekdays, dateFormatSymbols.standaloneShortWeekdays) && Utility.arrayEquals(this.standaloneShorterWeekdays, dateFormatSymbols.standaloneShorterWeekdays) && Utility.arrayEquals(this.standaloneNarrowWeekdays, dateFormatSymbols.standaloneNarrowWeekdays) && Utility.arrayEquals(this.ampms, dateFormatSymbols.ampms) && arrayOfArrayEquals(this.zoneStrings, dateFormatSymbols.zoneStrings) && this.requestedLocale.getDisplayName().equals(dateFormatSymbols.requestedLocale.getDisplayName()) && Utility.arrayEquals((Object)this.localPatternChars, (Object)dateFormatSymbols.localPatternChars);
    }
    
    protected void initializeData(final ULocale uLocale, final String s) {
        final String string = uLocale.getBaseName() + "+" + s;
        final DateFormatSymbols dateFormatSymbols = (DateFormatSymbols)DateFormatSymbols.DFSCACHE.get(string);
        if (dateFormatSymbols == null) {
            this.initializeData(uLocale, new CalendarData(uLocale, s));
            if (this.getClass().getName().equals("com.ibm.icu.text.DateFormatSymbols")) {
                DateFormatSymbols.DFSCACHE.put(string, this.clone());
            }
        }
        else {
            this.initializeData(dateFormatSymbols);
        }
    }
    
    void initializeData(final DateFormatSymbols dateFormatSymbols) {
        this.eras = dateFormatSymbols.eras;
        this.eraNames = dateFormatSymbols.eraNames;
        this.narrowEras = dateFormatSymbols.narrowEras;
        this.months = dateFormatSymbols.months;
        this.shortMonths = dateFormatSymbols.shortMonths;
        this.narrowMonths = dateFormatSymbols.narrowMonths;
        this.standaloneMonths = dateFormatSymbols.standaloneMonths;
        this.standaloneShortMonths = dateFormatSymbols.standaloneShortMonths;
        this.standaloneNarrowMonths = dateFormatSymbols.standaloneNarrowMonths;
        this.weekdays = dateFormatSymbols.weekdays;
        this.shortWeekdays = dateFormatSymbols.shortWeekdays;
        this.shorterWeekdays = dateFormatSymbols.shorterWeekdays;
        this.narrowWeekdays = dateFormatSymbols.narrowWeekdays;
        this.standaloneWeekdays = dateFormatSymbols.standaloneWeekdays;
        this.standaloneShortWeekdays = dateFormatSymbols.standaloneShortWeekdays;
        this.standaloneShorterWeekdays = dateFormatSymbols.standaloneShorterWeekdays;
        this.standaloneNarrowWeekdays = dateFormatSymbols.standaloneNarrowWeekdays;
        this.ampms = dateFormatSymbols.ampms;
        this.shortQuarters = dateFormatSymbols.shortQuarters;
        this.quarters = dateFormatSymbols.quarters;
        this.standaloneShortQuarters = dateFormatSymbols.standaloneShortQuarters;
        this.standaloneQuarters = dateFormatSymbols.standaloneQuarters;
        this.leapMonthPatterns = dateFormatSymbols.leapMonthPatterns;
        this.shortYearNames = dateFormatSymbols.shortYearNames;
        this.zoneStrings = dateFormatSymbols.zoneStrings;
        this.localPatternChars = dateFormatSymbols.localPatternChars;
        this.capitalization = dateFormatSymbols.capitalization;
        this.actualLocale = dateFormatSymbols.actualLocale;
        this.validLocale = dateFormatSymbols.validLocale;
        this.requestedLocale = dateFormatSymbols.requestedLocale;
    }
    
    @Deprecated
    protected void initializeData(final ULocale requestedLocale, final CalendarData calendarData) {
        this.eras = calendarData.getEras("abbreviated");
        this.eraNames = calendarData.getEras("wide");
        this.narrowEras = calendarData.getEras("narrow");
        this.months = calendarData.getStringArray("monthNames", "wide");
        this.shortMonths = calendarData.getStringArray("monthNames", "abbreviated");
        this.narrowMonths = calendarData.getStringArray("monthNames", "narrow");
        this.standaloneMonths = calendarData.getStringArray("monthNames", "stand-alone", "wide");
        this.standaloneShortMonths = calendarData.getStringArray("monthNames", "stand-alone", "abbreviated");
        this.standaloneNarrowMonths = calendarData.getStringArray("monthNames", "stand-alone", "narrow");
        final String[] stringArray = calendarData.getStringArray("dayNames", "wide");
        (this.weekdays = new String[8])[0] = "";
        System.arraycopy(stringArray, 0, this.weekdays, 1, stringArray.length);
        final String[] stringArray2 = calendarData.getStringArray("dayNames", "abbreviated");
        (this.shortWeekdays = new String[8])[0] = "";
        System.arraycopy(stringArray2, 0, this.shortWeekdays, 1, stringArray2.length);
        final String[] stringArray3 = calendarData.getStringArray("dayNames", "short");
        (this.shorterWeekdays = new String[8])[0] = "";
        System.arraycopy(stringArray3, 0, this.shorterWeekdays, 1, stringArray3.length);
        final String[] stringArray4 = calendarData.getStringArray("dayNames", "narrow");
        (this.narrowWeekdays = new String[8])[0] = "";
        System.arraycopy(stringArray4, 0, this.narrowWeekdays, 1, stringArray4.length);
        final String[] stringArray5 = calendarData.getStringArray("dayNames", "stand-alone", "wide");
        (this.standaloneWeekdays = new String[8])[0] = "";
        System.arraycopy(stringArray5, 0, this.standaloneWeekdays, 1, stringArray5.length);
        final String[] stringArray6 = calendarData.getStringArray("dayNames", "stand-alone", "abbreviated");
        (this.standaloneShortWeekdays = new String[8])[0] = "";
        System.arraycopy(stringArray6, 0, this.standaloneShortWeekdays, 1, stringArray6.length);
        final String[] stringArray7 = calendarData.getStringArray("dayNames", "stand-alone", "short");
        (this.standaloneShorterWeekdays = new String[8])[0] = "";
        System.arraycopy(stringArray7, 0, this.standaloneShorterWeekdays, 1, stringArray7.length);
        final String[] stringArray8 = calendarData.getStringArray("dayNames", "stand-alone", "narrow");
        (this.standaloneNarrowWeekdays = new String[8])[0] = "";
        System.arraycopy(stringArray8, 0, this.standaloneNarrowWeekdays, 1, stringArray8.length);
        this.ampms = calendarData.getStringArray("AmPmMarkers");
        this.quarters = calendarData.getStringArray("quarters", "wide");
        this.shortQuarters = calendarData.getStringArray("quarters", "abbreviated");
        this.standaloneQuarters = calendarData.getStringArray("quarters", "stand-alone", "wide");
        this.standaloneShortQuarters = calendarData.getStringArray("quarters", "stand-alone", "abbreviated");
        if (calendarData.get("monthPatterns") != null) {
            (this.leapMonthPatterns = new String[7])[0] = calendarData.get("monthPatterns", "wide").get("leap").getString();
            this.leapMonthPatterns[1] = calendarData.get("monthPatterns", "abbreviated").get("leap").getString();
            this.leapMonthPatterns[2] = calendarData.get("monthPatterns", "narrow").get("leap").getString();
            this.leapMonthPatterns[3] = calendarData.get("monthPatterns", "stand-alone", "wide").get("leap").getString();
            this.leapMonthPatterns[4] = calendarData.get("monthPatterns", "stand-alone", "abbreviated").get("leap").getString();
            this.leapMonthPatterns[5] = calendarData.get("monthPatterns", "stand-alone", "narrow").get("leap").getString();
            this.leapMonthPatterns[6] = calendarData.get("monthPatterns", "numeric", "all").get("leap").getString();
        }
        if (calendarData.get("cyclicNameSets") != null) {
            this.shortYearNames = calendarData.get("cyclicNameSets", "years", "format", "abbreviated").getStringArray();
        }
        this.requestedLocale = requestedLocale;
        final ICUResourceBundle icuResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", requestedLocale);
        this.localPatternChars = "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXx";
        final ULocale uLocale = icuResourceBundle.getULocale();
        this.setLocale(uLocale, uLocale);
        this.capitalization = new HashMap();
        final boolean[] array = { false, false };
        final CapitalizationContextUsage[] values = CapitalizationContextUsage.values();
        while (0 < values.length) {
            this.capitalization.put(values[0], array);
            int n = 0;
            ++n;
        }
        final ICUResourceBundle withFallback = icuResourceBundle.getWithFallback("contextTransforms");
        if (withFallback != null) {
            final UResourceBundleIterator iterator = withFallback.getIterator();
            while (iterator.hasNext()) {
                final UResourceBundle next = iterator.next();
                final int[] intVector = next.getIntVector();
                if (intVector.length >= 2) {
                    final CapitalizationContextUsage capitalizationContextUsage = DateFormatSymbols.contextUsageTypeMap.get(next.getKey());
                    if (capitalizationContextUsage == null) {
                        continue;
                    }
                    this.capitalization.put(capitalizationContextUsage, new boolean[] { intVector[0] != 0, intVector[1] != 0 });
                }
            }
        }
    }
    
    private static final boolean arrayOfArrayEquals(final Object[][] array, final Object[][] array2) {
        if (array == array2) {
            return true;
        }
        if (array == null || array2 == null) {
            return false;
        }
        if (array.length != array2.length) {
            return false;
        }
        while (0 < array.length) {
            Utility.arrayEquals(array[0], array2[0]);
            if (!true) {
                break;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    private final String[] duplicate(final String[] array) {
        return array.clone();
    }
    
    private final String[][] duplicate(final String[][] array) {
        final String[][] array2 = new String[array.length][];
        while (0 < array.length) {
            array2[0] = this.duplicate(array[0]);
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public DateFormatSymbols(final Calendar calendar, final Locale locale) {
        this.eras = null;
        this.eraNames = null;
        this.narrowEras = null;
        this.months = null;
        this.shortMonths = null;
        this.narrowMonths = null;
        this.standaloneMonths = null;
        this.standaloneShortMonths = null;
        this.standaloneNarrowMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.shorterWeekdays = null;
        this.narrowWeekdays = null;
        this.standaloneWeekdays = null;
        this.standaloneShortWeekdays = null;
        this.standaloneShorterWeekdays = null;
        this.standaloneNarrowWeekdays = null;
        this.ampms = null;
        this.shortQuarters = null;
        this.quarters = null;
        this.standaloneShortQuarters = null;
        this.standaloneQuarters = null;
        this.leapMonthPatterns = null;
        this.shortYearNames = null;
        this.zoneStrings = null;
        this.localPatternChars = null;
        this.capitalization = null;
        this.initializeData(ULocale.forLocale(locale), calendar.getType());
    }
    
    public DateFormatSymbols(final Calendar calendar, final ULocale uLocale) {
        this.eras = null;
        this.eraNames = null;
        this.narrowEras = null;
        this.months = null;
        this.shortMonths = null;
        this.narrowMonths = null;
        this.standaloneMonths = null;
        this.standaloneShortMonths = null;
        this.standaloneNarrowMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.shorterWeekdays = null;
        this.narrowWeekdays = null;
        this.standaloneWeekdays = null;
        this.standaloneShortWeekdays = null;
        this.standaloneShorterWeekdays = null;
        this.standaloneNarrowWeekdays = null;
        this.ampms = null;
        this.shortQuarters = null;
        this.quarters = null;
        this.standaloneShortQuarters = null;
        this.standaloneQuarters = null;
        this.leapMonthPatterns = null;
        this.shortYearNames = null;
        this.zoneStrings = null;
        this.localPatternChars = null;
        this.capitalization = null;
        this.initializeData(uLocale, calendar.getType());
    }
    
    public DateFormatSymbols(final Class clazz, final Locale locale) {
        this(clazz, ULocale.forLocale(locale));
    }
    
    public DateFormatSymbols(final Class clazz, final ULocale uLocale) {
        this.eras = null;
        this.eraNames = null;
        this.narrowEras = null;
        this.months = null;
        this.shortMonths = null;
        this.narrowMonths = null;
        this.standaloneMonths = null;
        this.standaloneShortMonths = null;
        this.standaloneNarrowMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.shorterWeekdays = null;
        this.narrowWeekdays = null;
        this.standaloneWeekdays = null;
        this.standaloneShortWeekdays = null;
        this.standaloneShorterWeekdays = null;
        this.standaloneNarrowWeekdays = null;
        this.ampms = null;
        this.shortQuarters = null;
        this.quarters = null;
        this.standaloneShortQuarters = null;
        this.standaloneQuarters = null;
        this.leapMonthPatterns = null;
        this.shortYearNames = null;
        this.zoneStrings = null;
        this.localPatternChars = null;
        this.capitalization = null;
        final String name = clazz.getName();
        final String substring = name.substring(name.lastIndexOf(46) + 1);
        String lowerCase = null;
        final String[][] calendar_CLASSES = DateFormatSymbols.CALENDAR_CLASSES;
        while (0 < calendar_CLASSES.length) {
            final String[] array = calendar_CLASSES[0];
            if (array[0].equals(substring)) {
                lowerCase = array[1];
                break;
            }
            int n = 0;
            ++n;
        }
        if (lowerCase == null) {
            lowerCase = substring.replaceAll("Calendar", "").toLowerCase(Locale.ENGLISH);
        }
        this.initializeData(uLocale, lowerCase);
    }
    
    public DateFormatSymbols(final ResourceBundle resourceBundle, final Locale locale) {
        this(resourceBundle, ULocale.forLocale(locale));
    }
    
    public DateFormatSymbols(final ResourceBundle resourceBundle, final ULocale uLocale) {
        this.eras = null;
        this.eraNames = null;
        this.narrowEras = null;
        this.months = null;
        this.shortMonths = null;
        this.narrowMonths = null;
        this.standaloneMonths = null;
        this.standaloneShortMonths = null;
        this.standaloneNarrowMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.shorterWeekdays = null;
        this.narrowWeekdays = null;
        this.standaloneWeekdays = null;
        this.standaloneShortWeekdays = null;
        this.standaloneShorterWeekdays = null;
        this.standaloneNarrowWeekdays = null;
        this.ampms = null;
        this.shortQuarters = null;
        this.quarters = null;
        this.standaloneShortQuarters = null;
        this.standaloneQuarters = null;
        this.leapMonthPatterns = null;
        this.shortYearNames = null;
        this.zoneStrings = null;
        this.localPatternChars = null;
        this.capitalization = null;
        this.initializeData(uLocale, new CalendarData((ICUResourceBundle)resourceBundle, CalendarUtil.getCalendarType(uLocale)));
    }
    
    @Deprecated
    public static ResourceBundle getDateFormatBundle(final Class clazz, final Locale locale) throws MissingResourceException {
        return null;
    }
    
    @Deprecated
    public static ResourceBundle getDateFormatBundle(final Class clazz, final ULocale uLocale) throws MissingResourceException {
        return null;
    }
    
    @Deprecated
    public static ResourceBundle getDateFormatBundle(final Calendar calendar, final Locale locale) throws MissingResourceException {
        return null;
    }
    
    @Deprecated
    public static ResourceBundle getDateFormatBundle(final Calendar calendar, final ULocale uLocale) throws MissingResourceException {
        return null;
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
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
    }
    
    static {
        CALENDAR_CLASSES = new String[][] { { "GregorianCalendar", "gregorian" }, { "JapaneseCalendar", "japanese" }, { "BuddhistCalendar", "buddhist" }, { "TaiwanCalendar", "roc" }, { "PersianCalendar", "persian" }, { "IslamicCalendar", "islamic" }, { "HebrewCalendar", "hebrew" }, { "ChineseCalendar", "chinese" }, { "IndianCalendar", "indian" }, { "CopticCalendar", "coptic" }, { "EthiopicCalendar", "ethiopic" } };
        (contextUsageTypeMap = new HashMap()).put("month-format-except-narrow", CapitalizationContextUsage.MONTH_FORMAT);
        DateFormatSymbols.contextUsageTypeMap.put("month-standalone-except-narrow", CapitalizationContextUsage.MONTH_STANDALONE);
        DateFormatSymbols.contextUsageTypeMap.put("month-narrow", CapitalizationContextUsage.MONTH_NARROW);
        DateFormatSymbols.contextUsageTypeMap.put("day-format-except-narrow", CapitalizationContextUsage.DAY_FORMAT);
        DateFormatSymbols.contextUsageTypeMap.put("day-standalone-except-narrow", CapitalizationContextUsage.DAY_STANDALONE);
        DateFormatSymbols.contextUsageTypeMap.put("day-narrow", CapitalizationContextUsage.DAY_NARROW);
        DateFormatSymbols.contextUsageTypeMap.put("era-name", CapitalizationContextUsage.ERA_WIDE);
        DateFormatSymbols.contextUsageTypeMap.put("era-abbr", CapitalizationContextUsage.ERA_ABBREV);
        DateFormatSymbols.contextUsageTypeMap.put("era-narrow", CapitalizationContextUsage.ERA_NARROW);
        DateFormatSymbols.contextUsageTypeMap.put("zone-long", CapitalizationContextUsage.ZONE_LONG);
        DateFormatSymbols.contextUsageTypeMap.put("zone-short", CapitalizationContextUsage.ZONE_SHORT);
        DateFormatSymbols.contextUsageTypeMap.put("metazone-long", CapitalizationContextUsage.METAZONE_LONG);
        DateFormatSymbols.contextUsageTypeMap.put("metazone-short", CapitalizationContextUsage.METAZONE_SHORT);
        DateFormatSymbols.DFSCACHE = new SimpleCache();
    }
    
    enum CapitalizationContextUsage
    {
        OTHER("OTHER", 0), 
        MONTH_FORMAT("MONTH_FORMAT", 1), 
        MONTH_STANDALONE("MONTH_STANDALONE", 2), 
        MONTH_NARROW("MONTH_NARROW", 3), 
        DAY_FORMAT("DAY_FORMAT", 4), 
        DAY_STANDALONE("DAY_STANDALONE", 5), 
        DAY_NARROW("DAY_NARROW", 6), 
        ERA_WIDE("ERA_WIDE", 7), 
        ERA_ABBREV("ERA_ABBREV", 8), 
        ERA_NARROW("ERA_NARROW", 9), 
        ZONE_LONG("ZONE_LONG", 10), 
        ZONE_SHORT("ZONE_SHORT", 11), 
        METAZONE_LONG("METAZONE_LONG", 12), 
        METAZONE_SHORT("METAZONE_SHORT", 13);
        
        private static final CapitalizationContextUsage[] $VALUES;
        
        private CapitalizationContextUsage(final String s, final int n) {
        }
        
        static {
            $VALUES = new CapitalizationContextUsage[] { CapitalizationContextUsage.OTHER, CapitalizationContextUsage.MONTH_FORMAT, CapitalizationContextUsage.MONTH_STANDALONE, CapitalizationContextUsage.MONTH_NARROW, CapitalizationContextUsage.DAY_FORMAT, CapitalizationContextUsage.DAY_STANDALONE, CapitalizationContextUsage.DAY_NARROW, CapitalizationContextUsage.ERA_WIDE, CapitalizationContextUsage.ERA_ABBREV, CapitalizationContextUsage.ERA_NARROW, CapitalizationContextUsage.ZONE_LONG, CapitalizationContextUsage.ZONE_SHORT, CapitalizationContextUsage.METAZONE_LONG, CapitalizationContextUsage.METAZONE_SHORT };
        }
    }
}
