package org.apache.http.client.utils;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.text.*;
import java.lang.ref.*;
import java.util.*;

@Immutable
public final class DateUtils
{
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String PATTERN_RFC1036 = "EEE, dd-MMM-yy HH:mm:ss zzz";
    public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
    private static final Date DEFAULT_TWO_DIGIT_YEAR_START;
    public static final TimeZone GMT;
    
    public static Date parseDate(final String s) {
        return parseDate(s, null, null);
    }
    
    public static Date parseDate(final String s, final String[] array) {
        return parseDate(s, array, null);
    }
    
    public static Date parseDate(final String s, final String[] array, final Date date) {
        Args.notNull(s, "Date value");
        final String[] array2 = (array != null) ? array : DateUtils.DEFAULT_PATTERNS;
        final Date date2 = (date != null) ? date : DateUtils.DEFAULT_TWO_DIGIT_YEAR_START;
        String substring = s;
        if (substring.length() > 1 && substring.startsWith("'") && substring.endsWith("'")) {
            substring = substring.substring(1, substring.length() - 1);
        }
        final String[] array3 = array2;
        while (0 < array3.length) {
            final SimpleDateFormat format = DateFormatHolder.formatFor(array3[0]);
            format.set2DigitYearStart(date2);
            final ParsePosition parsePosition = new ParsePosition(0);
            final Date parse = format.parse(substring, parsePosition);
            if (parsePosition.getIndex() != 0) {
                return parse;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public static String formatDate(final Date date) {
        return formatDate(date, "EEE, dd MMM yyyy HH:mm:ss zzz");
    }
    
    public static String formatDate(final Date date, final String s) {
        Args.notNull(date, "Date");
        Args.notNull(s, "Pattern");
        return DateFormatHolder.formatFor(s).format(date);
    }
    
    public static void clearThreadLocal() {
    }
    
    private DateUtils() {
    }
    
    static {
        DateUtils.DEFAULT_PATTERNS = new String[] { "EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy" };
        GMT = TimeZone.getTimeZone("GMT");
        final Calendar instance = Calendar.getInstance();
        instance.setTimeZone(DateUtils.GMT);
        instance.set(2000, 0, 1, 0, 0, 0);
        instance.set(14, 0);
        DEFAULT_TWO_DIGIT_YEAR_START = instance.getTime();
    }
    
    static final class DateFormatHolder
    {
        private static final ThreadLocal THREADLOCAL_FORMATS;
        
        public static SimpleDateFormat formatFor(final String s) {
            Object o = DateFormatHolder.THREADLOCAL_FORMATS.get().get();
            if (o == null) {
                o = new HashMap<Object, SimpleDateFormat>();
                DateFormatHolder.THREADLOCAL_FORMATS.set(new SoftReference<Map>((Map)o));
            }
            SimpleDateFormat simpleDateFormat = ((Map<K, SimpleDateFormat>)o).get(s);
            if (simpleDateFormat == null) {
                simpleDateFormat = new SimpleDateFormat(s, Locale.US);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                ((Map<K, SimpleDateFormat>)o).put((K)s, simpleDateFormat);
            }
            return simpleDateFormat;
        }
        
        public static void clearThreadLocal() {
            DateFormatHolder.THREADLOCAL_FORMATS.remove();
        }
        
        static {
            THREADLOCAL_FORMATS = new ThreadLocal() {
                @Override
                protected SoftReference initialValue() {
                    return new SoftReference((T)new HashMap());
                }
                
                @Override
                protected Object initialValue() {
                    return this.initialValue();
                }
            };
        }
    }
}
