package org.apache.commons.lang3.time;

import java.util.*;
import java.text.*;

public class FastDateFormat extends Format implements DateParser, DatePrinter
{
    private static final long serialVersionUID = 2L;
    public static final int FULL = 0;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    private static final FormatCache cache;
    private final FastDatePrinter printer;
    private final FastDateParser parser;
    
    public static FastDateFormat getInstance() {
        return (FastDateFormat)FastDateFormat.cache.getInstance();
    }
    
    public static FastDateFormat getInstance(final String s) {
        return (FastDateFormat)FastDateFormat.cache.getInstance(s, null, null);
    }
    
    public static FastDateFormat getInstance(final String s, final TimeZone timeZone) {
        return (FastDateFormat)FastDateFormat.cache.getInstance(s, timeZone, null);
    }
    
    public static FastDateFormat getInstance(final String s, final Locale locale) {
        return (FastDateFormat)FastDateFormat.cache.getInstance(s, null, locale);
    }
    
    public static FastDateFormat getInstance(final String s, final TimeZone timeZone, final Locale locale) {
        return (FastDateFormat)FastDateFormat.cache.getInstance(s, timeZone, locale);
    }
    
    public static FastDateFormat getDateInstance(final int n) {
        return (FastDateFormat)FastDateFormat.cache.getDateInstance(n, null, null);
    }
    
    public static FastDateFormat getDateInstance(final int n, final Locale locale) {
        return (FastDateFormat)FastDateFormat.cache.getDateInstance(n, null, locale);
    }
    
    public static FastDateFormat getDateInstance(final int n, final TimeZone timeZone) {
        return (FastDateFormat)FastDateFormat.cache.getDateInstance(n, timeZone, null);
    }
    
    public static FastDateFormat getDateInstance(final int n, final TimeZone timeZone, final Locale locale) {
        return (FastDateFormat)FastDateFormat.cache.getDateInstance(n, timeZone, locale);
    }
    
    public static FastDateFormat getTimeInstance(final int n) {
        return (FastDateFormat)FastDateFormat.cache.getTimeInstance(n, null, null);
    }
    
    public static FastDateFormat getTimeInstance(final int n, final Locale locale) {
        return (FastDateFormat)FastDateFormat.cache.getTimeInstance(n, null, locale);
    }
    
    public static FastDateFormat getTimeInstance(final int n, final TimeZone timeZone) {
        return (FastDateFormat)FastDateFormat.cache.getTimeInstance(n, timeZone, null);
    }
    
    public static FastDateFormat getTimeInstance(final int n, final TimeZone timeZone, final Locale locale) {
        return (FastDateFormat)FastDateFormat.cache.getTimeInstance(n, timeZone, locale);
    }
    
    public static FastDateFormat getDateTimeInstance(final int n, final int n2) {
        return (FastDateFormat)FastDateFormat.cache.getDateTimeInstance(n, n2, null, null);
    }
    
    public static FastDateFormat getDateTimeInstance(final int n, final int n2, final Locale locale) {
        return (FastDateFormat)FastDateFormat.cache.getDateTimeInstance(n, n2, null, locale);
    }
    
    public static FastDateFormat getDateTimeInstance(final int n, final int n2, final TimeZone timeZone) {
        return getDateTimeInstance(n, n2, timeZone, null);
    }
    
    public static FastDateFormat getDateTimeInstance(final int n, final int n2, final TimeZone timeZone, final Locale locale) {
        return (FastDateFormat)FastDateFormat.cache.getDateTimeInstance(n, n2, timeZone, locale);
    }
    
    protected FastDateFormat(final String s, final TimeZone timeZone, final Locale locale) {
        this(s, timeZone, locale, null);
    }
    
    protected FastDateFormat(final String s, final TimeZone timeZone, final Locale locale, final Date date) {
        this.printer = new FastDatePrinter(s, timeZone, locale);
        this.parser = new FastDateParser(s, timeZone, locale, date);
    }
    
    @Override
    public StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        return this.printer.format(o, sb, fieldPosition);
    }
    
    @Override
    public String format(final long n) {
        return this.printer.format(n);
    }
    
    @Override
    public String format(final Date date) {
        return this.printer.format(date);
    }
    
    @Override
    public String format(final Calendar calendar) {
        return this.printer.format(calendar);
    }
    
    @Override
    public StringBuffer format(final long n, final StringBuffer sb) {
        return this.printer.format(n, sb);
    }
    
    @Override
    public StringBuffer format(final Date date, final StringBuffer sb) {
        return this.printer.format(date, sb);
    }
    
    @Override
    public StringBuffer format(final Calendar calendar, final StringBuffer sb) {
        return this.printer.format(calendar, sb);
    }
    
    @Override
    public Date parse(final String s) throws ParseException {
        return this.parser.parse(s);
    }
    
    @Override
    public Date parse(final String s, final ParsePosition parsePosition) {
        return this.parser.parse(s, parsePosition);
    }
    
    @Override
    public Object parseObject(final String s, final ParsePosition parsePosition) {
        return this.parser.parseObject(s, parsePosition);
    }
    
    @Override
    public String getPattern() {
        return this.printer.getPattern();
    }
    
    @Override
    public TimeZone getTimeZone() {
        return this.printer.getTimeZone();
    }
    
    @Override
    public Locale getLocale() {
        return this.printer.getLocale();
    }
    
    public int getMaxLengthEstimate() {
        return this.printer.getMaxLengthEstimate();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof FastDateFormat && this.printer.equals(((FastDateFormat)o).printer);
    }
    
    @Override
    public int hashCode() {
        return this.printer.hashCode();
    }
    
    @Override
    public String toString() {
        return "FastDateFormat[" + this.printer.getPattern() + "," + this.printer.getLocale() + "," + this.printer.getTimeZone().getID() + "]";
    }
    
    protected StringBuffer applyRules(final Calendar calendar, final StringBuffer sb) {
        return this.printer.applyRules(calendar, sb);
    }
    
    static {
        cache = new FormatCache() {
            @Override
            protected FastDateFormat createInstance(final String s, final TimeZone timeZone, final Locale locale) {
                return new FastDateFormat(s, timeZone, locale);
            }
            
            @Override
            protected Format createInstance(final String s, final TimeZone timeZone, final Locale locale) {
                return this.createInstance(s, timeZone, locale);
            }
        };
    }
}
