package org.apache.logging.log4j.core.pattern;

import java.util.*;
import java.text.*;

final class CachedDateFormat extends DateFormat
{
    public static final int NO_MILLISECONDS = -2;
    public static final int UNRECOGNIZED_MILLISECONDS = -1;
    private static final long serialVersionUID = -1253877934598423628L;
    private static final String DIGITS = "0123456789";
    private static final int MAGIC1 = 654;
    private static final String MAGICSTRING1 = "654";
    private static final int MAGIC2 = 987;
    private static final String MAGICSTRING2 = "987";
    private static final String ZERO_STRING = "000";
    private static final int BUF_SIZE = 50;
    private static final int DEFAULT_VALIDITY = 1000;
    private static final int THREE_DIGITS = 100;
    private static final int TWO_DIGITS = 10;
    private static final long SLOTS = 1000L;
    private final DateFormat formatter;
    private int millisecondStart;
    private long slotBegin;
    private final StringBuffer cache;
    private final int expiration;
    private long previousTime;
    private final Date tmpDate;
    
    public CachedDateFormat(final DateFormat formatter, final int expiration) {
        this.cache = new StringBuffer(50);
        this.tmpDate = new Date(0L);
        if (formatter == null) {
            throw new IllegalArgumentException("dateFormat cannot be null");
        }
        if (expiration < 0) {
            throw new IllegalArgumentException("expiration must be non-negative");
        }
        this.formatter = formatter;
        this.expiration = expiration;
        this.millisecondStart = 0;
        this.previousTime = Long.MIN_VALUE;
        this.slotBegin = Long.MIN_VALUE;
    }
    
    public static int findMillisecondStart(final long n, final String s, final DateFormat dateFormat) {
        long n2 = n / 1000L * 1000L;
        if (n2 > n) {
            n2 -= 1000L;
        }
        final int n3 = (int)(n - n2);
        String s2 = "654";
        if (n3 == 654) {
            s2 = "987";
        }
        final String format = dateFormat.format(new Date(n2 + 987));
        if (format.length() != s.length()) {
            return -1;
        }
        while (0 < s.length()) {
            if (s.charAt(0) != format.charAt(0)) {
                final StringBuffer sb = new StringBuffer("ABC");
                millisecondFormat(n3, sb, 0);
                final String format2 = dateFormat.format(new Date(n2));
                if (format2.length() == s.length() && s2.regionMatches(0, format, 0, s2.length()) && sb.toString().regionMatches(0, s, 0, s2.length()) && "000".regionMatches(0, format2, 0, 3)) {
                    return 0;
                }
                return -1;
            }
            else {
                int n4 = 0;
                ++n4;
            }
        }
        return -2;
    }
    
    @Override
    public StringBuffer format(final Date date, final StringBuffer sb, final FieldPosition fieldPosition) {
        this.format(date.getTime(), sb);
        return sb;
    }
    
    public StringBuffer format(final long previousTime, final StringBuffer sb) {
        if (previousTime == this.previousTime) {
            sb.append(this.cache);
            return sb;
        }
        if (this.millisecondStart != -1 && previousTime < this.slotBegin + this.expiration && previousTime >= this.slotBegin && previousTime < this.slotBegin + 1000L) {
            if (this.millisecondStart >= 0) {
                millisecondFormat((int)(previousTime - this.slotBegin), this.cache, this.millisecondStart);
            }
            this.previousTime = previousTime;
            sb.append(this.cache);
            return sb;
        }
        this.cache.setLength(0);
        this.tmpDate.setTime(previousTime);
        this.cache.append(this.formatter.format(this.tmpDate));
        sb.append(this.cache);
        this.previousTime = previousTime;
        this.slotBegin = this.previousTime / 1000L * 1000L;
        if (this.slotBegin > this.previousTime) {
            this.slotBegin -= 1000L;
        }
        if (this.millisecondStart >= 0) {
            this.millisecondStart = findMillisecondStart(previousTime, this.cache.toString(), this.formatter);
        }
        return sb;
    }
    
    private static void millisecondFormat(final int n, final StringBuffer sb, final int n2) {
        sb.setCharAt(n2, "0123456789".charAt(n / 100));
        sb.setCharAt(n2 + 1, "0123456789".charAt(n / 10 % 10));
        sb.setCharAt(n2 + 2, "0123456789".charAt(n % 10));
    }
    
    @Override
    public void setTimeZone(final TimeZone timeZone) {
        this.formatter.setTimeZone(timeZone);
        this.previousTime = Long.MIN_VALUE;
        this.slotBegin = Long.MIN_VALUE;
    }
    
    @Override
    public Date parse(final String s, final ParsePosition parsePosition) {
        return this.formatter.parse(s, parsePosition);
    }
    
    @Override
    public NumberFormat getNumberFormat() {
        return this.formatter.getNumberFormat();
    }
    
    public static int getMaximumCacheValidity(final String s) {
        final int index = s.indexOf(83);
        if (index >= 0 && index != s.lastIndexOf("SSS")) {
            return 1;
        }
        return 1000;
    }
}
