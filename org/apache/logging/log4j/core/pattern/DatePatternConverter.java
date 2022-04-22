package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import java.text.*;
import org.apache.logging.log4j.core.*;
import java.util.*;

@Plugin(name = "DatePatternConverter", category = "Converter")
@ConverterKeys({ "d", "date" })
public final class DatePatternConverter extends LogEventPatternConverter implements ArrayPatternConverter
{
    private static final String ABSOLUTE_FORMAT = "ABSOLUTE";
    private static final String COMPACT_FORMAT = "COMPACT";
    private static final String ABSOLUTE_TIME_PATTERN = "HH:mm:ss,SSS";
    private static final String DATE_AND_TIME_FORMAT = "DATE";
    private static final String DATE_AND_TIME_PATTERN = "dd MMM yyyy HH:mm:ss,SSS";
    private static final String ISO8601_FORMAT = "ISO8601";
    private static final String ISO8601_BASIC_FORMAT = "ISO8601_BASIC";
    private static final String ISO8601_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";
    private static final String ISO8601_BASIC_PATTERN = "yyyyMMdd HHmmss,SSS";
    private static final String COMPACT_PATTERN = "yyyyMMddHHmmssSSS";
    private String cachedDate;
    private long lastTimestamp;
    private final SimpleDateFormat simpleFormat;
    
    private DatePatternConverter(final String[] array) {
        super("Date", "date");
        String s;
        if (array == null || array.length == 0) {
            s = null;
        }
        else {
            s = array[0];
        }
        String s2;
        if (s == null || s.equalsIgnoreCase("ISO8601")) {
            s2 = "yyyy-MM-dd HH:mm:ss,SSS";
        }
        else if (s.equalsIgnoreCase("ISO8601_BASIC")) {
            s2 = "yyyyMMdd HHmmss,SSS";
        }
        else if (s.equalsIgnoreCase("ABSOLUTE")) {
            s2 = "HH:mm:ss,SSS";
        }
        else if (s.equalsIgnoreCase("DATE")) {
            s2 = "dd MMM yyyy HH:mm:ss,SSS";
        }
        else if (s.equalsIgnoreCase("COMPACT")) {
            s2 = "yyyyMMddHHmmssSSS";
        }
        else {
            s2 = s;
        }
        final SimpleDateFormat simpleFormat = new SimpleDateFormat(s2);
        if (array != null && array.length > 1) {
            simpleFormat.setTimeZone(TimeZone.getTimeZone(array[1]));
        }
        this.simpleFormat = simpleFormat;
    }
    
    public static DatePatternConverter newInstance(final String[] array) {
        return new DatePatternConverter(array);
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        final long millis = logEvent.getMillis();
        // monitorenter(this)
        if (millis != this.lastTimestamp) {
            this.lastTimestamp = millis;
            this.cachedDate = this.simpleFormat.format(millis);
        }
        // monitorexit(this)
        sb.append(this.cachedDate);
    }
    
    @Override
    public void format(final StringBuilder sb, final Object... array) {
        while (0 < array.length) {
            final Object o = array[0];
            if (o instanceof Date) {
                this.format(o, sb);
                break;
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void format(final Object o, final StringBuilder sb) {
        if (o instanceof Date) {
            this.format((Date)o, sb);
        }
        super.format(o, sb);
    }
    
    public void format(final Date date, final StringBuilder sb) {
        // monitorenter(this)
        sb.append(this.simpleFormat.format(date.getTime()));
    }
    // monitorexit(this)
    
    public String getPattern() {
        return this.simpleFormat.toPattern();
    }
}
