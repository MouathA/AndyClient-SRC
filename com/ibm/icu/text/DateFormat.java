package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import java.text.*;
import java.io.*;
import com.ibm.icu.util.*;
import java.util.*;

public abstract class DateFormat extends UFormat
{
    protected Calendar calendar;
    protected NumberFormat numberFormat;
    public static final int ERA_FIELD = 0;
    public static final int YEAR_FIELD = 1;
    public static final int MONTH_FIELD = 2;
    public static final int DATE_FIELD = 3;
    public static final int HOUR_OF_DAY1_FIELD = 4;
    public static final int HOUR_OF_DAY0_FIELD = 5;
    public static final int MINUTE_FIELD = 6;
    public static final int SECOND_FIELD = 7;
    public static final int FRACTIONAL_SECOND_FIELD = 8;
    public static final int MILLISECOND_FIELD = 8;
    public static final int DAY_OF_WEEK_FIELD = 9;
    public static final int DAY_OF_YEAR_FIELD = 10;
    public static final int DAY_OF_WEEK_IN_MONTH_FIELD = 11;
    public static final int WEEK_OF_YEAR_FIELD = 12;
    public static final int WEEK_OF_MONTH_FIELD = 13;
    public static final int AM_PM_FIELD = 14;
    public static final int HOUR1_FIELD = 15;
    public static final int HOUR0_FIELD = 16;
    public static final int TIMEZONE_FIELD = 17;
    public static final int YEAR_WOY_FIELD = 18;
    public static final int DOW_LOCAL_FIELD = 19;
    public static final int EXTENDED_YEAR_FIELD = 20;
    public static final int JULIAN_DAY_FIELD = 21;
    public static final int MILLISECONDS_IN_DAY_FIELD = 22;
    public static final int TIMEZONE_RFC_FIELD = 23;
    public static final int TIMEZONE_GENERIC_FIELD = 24;
    public static final int STANDALONE_DAY_FIELD = 25;
    public static final int STANDALONE_MONTH_FIELD = 26;
    public static final int QUARTER_FIELD = 27;
    public static final int STANDALONE_QUARTER_FIELD = 28;
    public static final int TIMEZONE_SPECIAL_FIELD = 29;
    public static final int YEAR_NAME_FIELD = 30;
    public static final int TIMEZONE_LOCALIZED_GMT_OFFSET_FIELD = 31;
    public static final int TIMEZONE_ISO_FIELD = 32;
    public static final int TIMEZONE_ISO_LOCAL_FIELD = 33;
    public static final int FIELD_COUNT = 34;
    private static final long serialVersionUID = 7218322306649953788L;
    public static final int NONE = -1;
    public static final int FULL = 0;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    public static final int DEFAULT = 2;
    public static final int RELATIVE = 128;
    public static final int RELATIVE_FULL = 128;
    public static final int RELATIVE_LONG = 129;
    public static final int RELATIVE_MEDIUM = 130;
    public static final int RELATIVE_SHORT = 131;
    public static final int RELATIVE_DEFAULT = 130;
    public static final String YEAR = "y";
    public static final String QUARTER = "QQQQ";
    public static final String ABBR_QUARTER = "QQQ";
    public static final String YEAR_QUARTER = "yQQQQ";
    public static final String YEAR_ABBR_QUARTER = "yQQQ";
    public static final String MONTH = "MMMM";
    public static final String ABBR_MONTH = "MMM";
    public static final String NUM_MONTH = "M";
    public static final String YEAR_MONTH = "yMMMM";
    public static final String YEAR_ABBR_MONTH = "yMMM";
    public static final String YEAR_NUM_MONTH = "yM";
    public static final String DAY = "d";
    public static final String YEAR_MONTH_DAY = "yMMMMd";
    public static final String YEAR_ABBR_MONTH_DAY = "yMMMd";
    public static final String YEAR_NUM_MONTH_DAY = "yMd";
    public static final String WEEKDAY = "EEEE";
    public static final String ABBR_WEEKDAY = "E";
    public static final String YEAR_MONTH_WEEKDAY_DAY = "yMMMMEEEEd";
    public static final String YEAR_ABBR_MONTH_WEEKDAY_DAY = "yMMMEd";
    public static final String YEAR_NUM_MONTH_WEEKDAY_DAY = "yMEd";
    public static final String MONTH_DAY = "MMMMd";
    public static final String ABBR_MONTH_DAY = "MMMd";
    public static final String NUM_MONTH_DAY = "Md";
    public static final String MONTH_WEEKDAY_DAY = "MMMMEEEEd";
    public static final String ABBR_MONTH_WEEKDAY_DAY = "MMMEd";
    public static final String NUM_MONTH_WEEKDAY_DAY = "MEd";
    public static final String HOUR = "j";
    public static final String HOUR24 = "H";
    public static final String MINUTE = "m";
    public static final String HOUR_MINUTE = "jm";
    public static final String HOUR24_MINUTE = "Hm";
    public static final String SECOND = "s";
    public static final String HOUR_MINUTE_SECOND = "jms";
    public static final String HOUR24_MINUTE_SECOND = "Hms";
    public static final String MINUTE_SECOND = "ms";
    public static final String LOCATION_TZ = "VVVV";
    public static final String GENERIC_TZ = "vvvv";
    public static final String ABBR_GENERIC_TZ = "v";
    public static final String SPECIFIC_TZ = "zzzz";
    public static final String ABBR_SPECIFIC_TZ = "z";
    public static final String ABBR_UTC_TZ = "ZZZZ";
    @Deprecated
    public static final String STANDALONE_MONTH = "LLLL";
    @Deprecated
    public static final String ABBR_STANDALONE_MONTH = "LLL";
    @Deprecated
    public static final String HOUR_MINUTE_GENERIC_TZ = "jmv";
    @Deprecated
    public static final String HOUR_MINUTE_TZ = "jmz";
    @Deprecated
    public static final String HOUR_GENERIC_TZ = "jv";
    @Deprecated
    public static final String HOUR_TZ = "jz";
    
    @Override
    public final StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        if (o instanceof Calendar) {
            return this.format((Calendar)o, sb, fieldPosition);
        }
        if (o instanceof Date) {
            return this.format((Date)o, sb, fieldPosition);
        }
        if (o instanceof Number) {
            return this.format(new Date(((Number)o).longValue()), sb, fieldPosition);
        }
        throw new IllegalArgumentException("Cannot format given Object (" + o.getClass().getName() + ") as a Date");
    }
    
    public abstract StringBuffer format(final Calendar p0, final StringBuffer p1, final FieldPosition p2);
    
    public StringBuffer format(final Date time, final StringBuffer sb, final FieldPosition fieldPosition) {
        this.calendar.setTime(time);
        return this.format(this.calendar, sb, fieldPosition);
    }
    
    public final String format(final Date date) {
        return this.format(date, new StringBuffer(64), new FieldPosition(0)).toString();
    }
    
    public Date parse(final String s) throws ParseException {
        final ParsePosition parsePosition = new ParsePosition(0);
        final Date parse = this.parse(s, parsePosition);
        if (parsePosition.getIndex() == 0) {
            throw new ParseException("Unparseable date: \"" + s + "\"", parsePosition.getErrorIndex());
        }
        return parse;
    }
    
    public abstract void parse(final String p0, final Calendar p1, final ParsePosition p2);
    
    public Date parse(final String s, final ParsePosition parsePosition) {
        Date time = null;
        final int index = parsePosition.getIndex();
        final TimeZone timeZone = this.calendar.getTimeZone();
        this.calendar.clear();
        this.parse(s, this.calendar, parsePosition);
        if (parsePosition.getIndex() != index) {
            time = this.calendar.getTime();
        }
        this.calendar.setTimeZone(timeZone);
        return time;
    }
    
    @Override
    public Object parseObject(final String s, final ParsePosition parsePosition) {
        return this.parse(s, parsePosition);
    }
    
    public static final DateFormat getTimeInstance() {
        return get(-1, 2, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public static final DateFormat getTimeInstance(final int n) {
        return get(-1, n, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public static final DateFormat getTimeInstance(final int n, final Locale locale) {
        return get(-1, n, ULocale.forLocale(locale));
    }
    
    public static final DateFormat getTimeInstance(final int n, final ULocale uLocale) {
        return get(-1, n, uLocale);
    }
    
    public static final DateFormat getDateInstance() {
        return get(2, -1, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public static final DateFormat getDateInstance(final int n) {
        return get(n, -1, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public static final DateFormat getDateInstance(final int n, final Locale locale) {
        return get(n, -1, ULocale.forLocale(locale));
    }
    
    public static final DateFormat getDateInstance(final int n, final ULocale uLocale) {
        return get(n, -1, uLocale);
    }
    
    public static final DateFormat getDateTimeInstance() {
        return get(2, 2, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public static final DateFormat getDateTimeInstance(final int n, final int n2) {
        return get(n, n2, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public static final DateFormat getDateTimeInstance(final int n, final int n2, final Locale locale) {
        return get(n, n2, ULocale.forLocale(locale));
    }
    
    public static final DateFormat getDateTimeInstance(final int n, final int n2, final ULocale uLocale) {
        return get(n, n2, uLocale);
    }
    
    public static final DateFormat getInstance() {
        return getDateTimeInstance(3, 3);
    }
    
    public static Locale[] getAvailableLocales() {
        return ICUResourceBundle.getAvailableLocales();
    }
    
    public static ULocale[] getAvailableULocales() {
        return ICUResourceBundle.getAvailableULocales();
    }
    
    public void setCalendar(final Calendar calendar) {
        this.calendar = calendar;
    }
    
    public Calendar getCalendar() {
        return this.calendar;
    }
    
    public void setNumberFormat(final NumberFormat numberFormat) {
        (this.numberFormat = numberFormat).setParseIntegerOnly(true);
    }
    
    public NumberFormat getNumberFormat() {
        return this.numberFormat;
    }
    
    public void setTimeZone(final TimeZone timeZone) {
        this.calendar.setTimeZone(timeZone);
    }
    
    public TimeZone getTimeZone() {
        return this.calendar.getTimeZone();
    }
    
    public void setLenient(final boolean lenient) {
        this.calendar.setLenient(lenient);
    }
    
    public boolean isLenient() {
        return this.calendar.isLenient();
    }
    
    @Override
    public int hashCode() {
        return this.numberFormat.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DateFormat dateFormat = (DateFormat)o;
        return this.calendar.isEquivalentTo(dateFormat.calendar) && this.numberFormat.equals(dateFormat.numberFormat);
    }
    
    @Override
    public Object clone() {
        final DateFormat dateFormat = (DateFormat)super.clone();
        dateFormat.calendar = (Calendar)this.calendar.clone();
        dateFormat.numberFormat = (NumberFormat)this.numberFormat.clone();
        return dateFormat;
    }
    
    private static DateFormat get(final int n, final int n2, final ULocale uLocale) {
        if ((n2 != -1 && (n2 & 0x80) > 0) || (n != -1 && (n & 0x80) > 0)) {
            return new RelativeDateFormat(n2, n, uLocale);
        }
        if (n2 < -1 || n2 > 3) {
            throw new IllegalArgumentException("Illegal time style " + n2);
        }
        if (n < -1 || n > 3) {
            throw new IllegalArgumentException("Illegal date style " + n);
        }
        final Calendar instance = Calendar.getInstance(uLocale);
        final DateFormat dateTimeFormat = instance.getDateTimeFormat(n, n2, uLocale);
        dateTimeFormat.setLocale(instance.getLocale(ULocale.VALID_LOCALE), instance.getLocale(ULocale.ACTUAL_LOCALE));
        return dateTimeFormat;
    }
    
    protected DateFormat() {
    }
    
    public static final DateFormat getDateInstance(final Calendar calendar, final int n, final Locale locale) {
        return getDateTimeInstance(calendar, n, -1, ULocale.forLocale(locale));
    }
    
    public static final DateFormat getDateInstance(final Calendar calendar, final int n, final ULocale uLocale) {
        return getDateTimeInstance(calendar, n, -1, uLocale);
    }
    
    public static final DateFormat getTimeInstance(final Calendar calendar, final int n, final Locale locale) {
        return getDateTimeInstance(calendar, -1, n, ULocale.forLocale(locale));
    }
    
    public static final DateFormat getTimeInstance(final Calendar calendar, final int n, final ULocale uLocale) {
        return getDateTimeInstance(calendar, -1, n, uLocale);
    }
    
    public static final DateFormat getDateTimeInstance(final Calendar calendar, final int n, final int n2, final Locale locale) {
        return calendar.getDateTimeFormat(n, n2, ULocale.forLocale(locale));
    }
    
    public static final DateFormat getDateTimeInstance(final Calendar calendar, final int n, final int n2, final ULocale uLocale) {
        return calendar.getDateTimeFormat(n, n2, uLocale);
    }
    
    public static final DateFormat getInstance(final Calendar calendar, final Locale locale) {
        return getDateTimeInstance(calendar, 3, 3, ULocale.forLocale(locale));
    }
    
    public static final DateFormat getInstance(final Calendar calendar, final ULocale uLocale) {
        return getDateTimeInstance(calendar, 3, 3, uLocale);
    }
    
    public static final DateFormat getInstance(final Calendar calendar) {
        return getInstance(calendar, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public static final DateFormat getDateInstance(final Calendar calendar, final int n) {
        return getDateInstance(calendar, n, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public static final DateFormat getTimeInstance(final Calendar calendar, final int n) {
        return getTimeInstance(calendar, n, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public static final DateFormat getDateTimeInstance(final Calendar calendar, final int n, final int n2) {
        return getDateTimeInstance(calendar, n, n2, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public static final DateFormat getPatternInstance(final String s) {
        return getPatternInstance(s, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public static final DateFormat getPatternInstance(final String s, final Locale locale) {
        return getPatternInstance(s, ULocale.forLocale(locale));
    }
    
    public static final DateFormat getPatternInstance(final String s, final ULocale uLocale) {
        return new SimpleDateFormat(DateTimePatternGenerator.getInstance(uLocale).getBestPattern(s), uLocale);
    }
    
    public static final DateFormat getPatternInstance(final Calendar calendar, final String s, final Locale locale) {
        return getPatternInstance(calendar, s, ULocale.forLocale(locale));
    }
    
    public static final DateFormat getPatternInstance(final Calendar calendar, final String s, final ULocale uLocale) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateTimePatternGenerator.getInstance(uLocale).getBestPattern(s), uLocale);
        simpleDateFormat.setCalendar(calendar);
        return simpleDateFormat;
    }
    
    public static class Field extends Format.Field
    {
        private static final long serialVersionUID = -3627456821000730829L;
        private static final int CAL_FIELD_COUNT;
        private static final Field[] CAL_FIELDS;
        private static final Map FIELD_NAME_MAP;
        public static final Field AM_PM;
        public static final Field DAY_OF_MONTH;
        public static final Field DAY_OF_WEEK;
        public static final Field DAY_OF_WEEK_IN_MONTH;
        public static final Field DAY_OF_YEAR;
        public static final Field ERA;
        public static final Field HOUR_OF_DAY0;
        public static final Field HOUR_OF_DAY1;
        public static final Field HOUR0;
        public static final Field HOUR1;
        public static final Field MILLISECOND;
        public static final Field MINUTE;
        public static final Field MONTH;
        public static final Field SECOND;
        public static final Field TIME_ZONE;
        public static final Field WEEK_OF_MONTH;
        public static final Field WEEK_OF_YEAR;
        public static final Field YEAR;
        public static final Field DOW_LOCAL;
        public static final Field EXTENDED_YEAR;
        public static final Field JULIAN_DAY;
        public static final Field MILLISECONDS_IN_DAY;
        public static final Field YEAR_WOY;
        public static final Field QUARTER;
        private final int calendarField;
        
        protected Field(final String s, final int calendarField) {
            super(s);
            this.calendarField = calendarField;
            if (this.getClass() == Field.class) {
                Field.FIELD_NAME_MAP.put(s, this);
                if (calendarField >= 0 && calendarField < Field.CAL_FIELD_COUNT) {
                    Field.CAL_FIELDS[calendarField] = this;
                }
            }
        }
        
        public static Field ofCalendarField(final int n) {
            if (n < 0 || n >= Field.CAL_FIELD_COUNT) {
                throw new IllegalArgumentException("Calendar field number is out of range");
            }
            return Field.CAL_FIELDS[n];
        }
        
        public int getCalendarField() {
            return this.calendarField;
        }
        
        @Override
        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() != Field.class) {
                throw new InvalidObjectException("A subclass of DateFormat.Field must implement readResolve.");
            }
            final Object value = Field.FIELD_NAME_MAP.get(this.getName());
            if (value == null) {
                throw new InvalidObjectException("Unknown attribute name.");
            }
            return value;
        }
        
        static {
            CAL_FIELD_COUNT = new GregorianCalendar().getFieldCount();
            CAL_FIELDS = new Field[Field.CAL_FIELD_COUNT];
            FIELD_NAME_MAP = new HashMap(Field.CAL_FIELD_COUNT);
            AM_PM = new Field("am pm", 9);
            DAY_OF_MONTH = new Field("day of month", 5);
            DAY_OF_WEEK = new Field("day of week", 7);
            DAY_OF_WEEK_IN_MONTH = new Field("day of week in month", 8);
            DAY_OF_YEAR = new Field("day of year", 6);
            ERA = new Field("era", 0);
            HOUR_OF_DAY0 = new Field("hour of day", 11);
            HOUR_OF_DAY1 = new Field("hour of day 1", -1);
            HOUR0 = new Field("hour", 10);
            HOUR1 = new Field("hour 1", -1);
            MILLISECOND = new Field("millisecond", 14);
            MINUTE = new Field("minute", 12);
            MONTH = new Field("month", 2);
            SECOND = new Field("second", 13);
            TIME_ZONE = new Field("time zone", -1);
            WEEK_OF_MONTH = new Field("week of month", 4);
            WEEK_OF_YEAR = new Field("week of year", 3);
            YEAR = new Field("year", 1);
            DOW_LOCAL = new Field("local day of week", 18);
            EXTENDED_YEAR = new Field("extended year", 19);
            JULIAN_DAY = new Field("Julian day", 20);
            MILLISECONDS_IN_DAY = new Field("milliseconds in day", 21);
            YEAR_WOY = new Field("year for week of year", 17);
            QUARTER = new Field("quarter", -1);
        }
    }
}
