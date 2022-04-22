package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import java.util.*;

@Deprecated
@Immutable
public final class DateUtils
{
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String PATTERN_RFC1036 = "EEE, dd-MMM-yy HH:mm:ss zzz";
    public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
    public static final TimeZone GMT;
    
    public static Date parseDate(final String s) throws DateParseException {
        return parseDate(s, null, null);
    }
    
    public static Date parseDate(final String s, final String[] array) throws DateParseException {
        return parseDate(s, array, null);
    }
    
    public static Date parseDate(final String s, final String[] array, final Date date) throws DateParseException {
        final Date date2 = org.apache.http.client.utils.DateUtils.parseDate(s, array, date);
        if (date2 == null) {
            throw new DateParseException("Unable to parse the date " + s);
        }
        return date2;
    }
    
    public static String formatDate(final Date date) {
        return org.apache.http.client.utils.DateUtils.formatDate(date);
    }
    
    public static String formatDate(final Date date, final String s) {
        return org.apache.http.client.utils.DateUtils.formatDate(date, s);
    }
    
    private DateUtils() {
    }
    
    static {
        GMT = TimeZone.getTimeZone("GMT");
    }
}
