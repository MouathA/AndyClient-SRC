package org.apache.commons.lang3.time;

import java.util.*;

public class DateFormatUtils
{
    private static final TimeZone UTC_TIME_ZONE;
    public static final FastDateFormat ISO_DATETIME_FORMAT;
    public static final FastDateFormat ISO_DATETIME_TIME_ZONE_FORMAT;
    public static final FastDateFormat ISO_DATE_FORMAT;
    public static final FastDateFormat ISO_DATE_TIME_ZONE_FORMAT;
    public static final FastDateFormat ISO_TIME_FORMAT;
    public static final FastDateFormat ISO_TIME_TIME_ZONE_FORMAT;
    public static final FastDateFormat ISO_TIME_NO_T_FORMAT;
    public static final FastDateFormat ISO_TIME_NO_T_TIME_ZONE_FORMAT;
    public static final FastDateFormat SMTP_DATETIME_FORMAT;
    
    public static String formatUTC(final long n, final String s) {
        return format(new Date(n), s, DateFormatUtils.UTC_TIME_ZONE, null);
    }
    
    public static String formatUTC(final Date date, final String s) {
        return format(date, s, DateFormatUtils.UTC_TIME_ZONE, null);
    }
    
    public static String formatUTC(final long n, final String s, final Locale locale) {
        return format(new Date(n), s, DateFormatUtils.UTC_TIME_ZONE, locale);
    }
    
    public static String formatUTC(final Date date, final String s, final Locale locale) {
        return format(date, s, DateFormatUtils.UTC_TIME_ZONE, locale);
    }
    
    public static String format(final long n, final String s) {
        return format(new Date(n), s, null, null);
    }
    
    public static String format(final Date date, final String s) {
        return format(date, s, null, null);
    }
    
    public static String format(final Calendar calendar, final String s) {
        return format(calendar, s, null, null);
    }
    
    public static String format(final long n, final String s, final TimeZone timeZone) {
        return format(new Date(n), s, timeZone, null);
    }
    
    public static String format(final Date date, final String s, final TimeZone timeZone) {
        return format(date, s, timeZone, null);
    }
    
    public static String format(final Calendar calendar, final String s, final TimeZone timeZone) {
        return format(calendar, s, timeZone, null);
    }
    
    public static String format(final long n, final String s, final Locale locale) {
        return format(new Date(n), s, null, locale);
    }
    
    public static String format(final Date date, final String s, final Locale locale) {
        return format(date, s, null, locale);
    }
    
    public static String format(final Calendar calendar, final String s, final Locale locale) {
        return format(calendar, s, null, locale);
    }
    
    public static String format(final long n, final String s, final TimeZone timeZone, final Locale locale) {
        return format(new Date(n), s, timeZone, locale);
    }
    
    public static String format(final Date date, final String s, final TimeZone timeZone, final Locale locale) {
        return FastDateFormat.getInstance(s, timeZone, locale).format(date);
    }
    
    public static String format(final Calendar calendar, final String s, final TimeZone timeZone, final Locale locale) {
        return FastDateFormat.getInstance(s, timeZone, locale).format(calendar);
    }
    
    static {
        UTC_TIME_ZONE = TimeZone.getTimeZone("GMT");
        ISO_DATETIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss");
        ISO_DATETIME_TIME_ZONE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ssZZ");
        ISO_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");
        ISO_DATE_TIME_ZONE_FORMAT = FastDateFormat.getInstance("yyyy-MM-ddZZ");
        ISO_TIME_FORMAT = FastDateFormat.getInstance("'T'HH:mm:ss");
        ISO_TIME_TIME_ZONE_FORMAT = FastDateFormat.getInstance("'T'HH:mm:ssZZ");
        ISO_TIME_NO_T_FORMAT = FastDateFormat.getInstance("HH:mm:ss");
        ISO_TIME_NO_T_TIME_ZONE_FORMAT = FastDateFormat.getInstance("HH:mm:ssZZ");
        SMTP_DATETIME_FORMAT = FastDateFormat.getInstance("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
    }
}
