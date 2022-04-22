package com.ibm.icu.text;

import com.ibm.icu.util.*;
import java.util.*;
import java.lang.reflect.*;

public class CurrencyMetaInfo
{
    private static final CurrencyMetaInfo impl;
    private static final boolean hasData;
    @Deprecated
    protected static final CurrencyDigits defaultDigits;
    
    public static CurrencyMetaInfo getInstance() {
        return CurrencyMetaInfo.impl;
    }
    
    public static CurrencyMetaInfo getInstance(final boolean b) {
        return CurrencyMetaInfo.hasData ? CurrencyMetaInfo.impl : null;
    }
    
    @Deprecated
    public static boolean hasData() {
        return CurrencyMetaInfo.hasData;
    }
    
    @Deprecated
    protected CurrencyMetaInfo() {
    }
    
    public List currencyInfo(final CurrencyFilter currencyFilter) {
        return Collections.emptyList();
    }
    
    public List currencies(final CurrencyFilter currencyFilter) {
        return Collections.emptyList();
    }
    
    public List regions(final CurrencyFilter currencyFilter) {
        return Collections.emptyList();
    }
    
    public CurrencyDigits currencyDigits(final String s) {
        return CurrencyMetaInfo.defaultDigits;
    }
    
    private static String dateString(final long timeInMillis) {
        if (timeInMillis == Long.MAX_VALUE || timeInMillis == Long.MIN_VALUE) {
            return null;
        }
        final GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        gregorianCalendar.setTimeInMillis(timeInMillis);
        return "" + gregorianCalendar.get(1) + '-' + (gregorianCalendar.get(2) + 1) + '-' + gregorianCalendar.get(5);
    }
    
    private static String debugString(final Object o) {
        final StringBuilder sb = new StringBuilder();
        final Field[] fields = o.getClass().getFields();
        while (0 < fields.length) {
            final Field field = fields[0];
            final Object value = field.get(o);
            if (value != null) {
                String s;
                if (value instanceof Date) {
                    s = dateString(((Date)value).getTime());
                }
                else if (value instanceof Long) {
                    s = dateString((long)value);
                }
                else {
                    s = String.valueOf(value);
                }
                if (s != null) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    sb.append(field.getName()).append("='").append(s).append("'");
                }
            }
            int n = 0;
            ++n;
        }
        sb.insert(0, o.getClass().getSimpleName() + "(");
        sb.append(")");
        return sb.toString();
    }
    
    static String access$000(final Object o) {
        return debugString(o);
    }
    
    static {
        defaultDigits = new CurrencyDigits(2, 0);
        impl = (CurrencyMetaInfo)Class.forName("com.ibm.icu.impl.ICUCurrencyMetaInfo").newInstance();
        hasData = true;
    }
    
    public static final class CurrencyInfo
    {
        public final String region;
        public final String code;
        public final long from;
        public final long to;
        public final int priority;
        private final boolean tender;
        
        @Deprecated
        public CurrencyInfo(final String s, final String s2, final long n, final long n2, final int n3) {
            this(s, s2, n, n2, n3, true);
        }
        
        @Deprecated
        public CurrencyInfo(final String region, final String code, final long from, final long to, final int priority, final boolean tender) {
            this.region = region;
            this.code = code;
            this.from = from;
            this.to = to;
            this.priority = priority;
            this.tender = tender;
        }
        
        @Override
        public String toString() {
            return CurrencyMetaInfo.access$000(this);
        }
        
        public boolean isTender() {
            return this.tender;
        }
    }
    
    public static final class CurrencyDigits
    {
        public final int fractionDigits;
        public final int roundingIncrement;
        
        public CurrencyDigits(final int fractionDigits, final int roundingIncrement) {
            this.fractionDigits = fractionDigits;
            this.roundingIncrement = roundingIncrement;
        }
        
        @Override
        public String toString() {
            return CurrencyMetaInfo.access$000(this);
        }
    }
    
    public static final class CurrencyFilter
    {
        public final String region;
        public final String currency;
        public final long from;
        public final long to;
        @Deprecated
        public final boolean tenderOnly;
        private static final CurrencyFilter ALL;
        
        private CurrencyFilter(final String region, final String currency, final long from, final long to, final boolean tenderOnly) {
            this.region = region;
            this.currency = currency;
            this.from = from;
            this.to = to;
            this.tenderOnly = tenderOnly;
        }
        
        public static CurrencyFilter all() {
            return CurrencyFilter.ALL;
        }
        
        public static CurrencyFilter now() {
            return CurrencyFilter.ALL.withDate(new Date());
        }
        
        public static CurrencyFilter onRegion(final String s) {
            return CurrencyFilter.ALL.withRegion(s);
        }
        
        public static CurrencyFilter onCurrency(final String s) {
            return CurrencyFilter.ALL.withCurrency(s);
        }
        
        public static CurrencyFilter onDate(final Date date) {
            return CurrencyFilter.ALL.withDate(date);
        }
        
        public static CurrencyFilter onDateRange(final Date date, final Date date2) {
            return CurrencyFilter.ALL.withDateRange(date, date2);
        }
        
        public static CurrencyFilter onDate(final long n) {
            return CurrencyFilter.ALL.withDate(n);
        }
        
        public static CurrencyFilter onDateRange(final long n, final long n2) {
            return CurrencyFilter.ALL.withDateRange(n, n2);
        }
        
        public static CurrencyFilter onTender() {
            return CurrencyFilter.ALL.withTender();
        }
        
        public CurrencyFilter withRegion(final String s) {
            return new CurrencyFilter(s, this.currency, this.from, this.to, this.tenderOnly);
        }
        
        public CurrencyFilter withCurrency(final String s) {
            return new CurrencyFilter(this.region, s, this.from, this.to, this.tenderOnly);
        }
        
        public CurrencyFilter withDate(final Date date) {
            return new CurrencyFilter(this.region, this.currency, date.getTime(), date.getTime(), this.tenderOnly);
        }
        
        public CurrencyFilter withDateRange(final Date date, final Date date2) {
            return new CurrencyFilter(this.region, this.currency, (date == null) ? Long.MIN_VALUE : date.getTime(), (date2 == null) ? Long.MAX_VALUE : date2.getTime(), this.tenderOnly);
        }
        
        public CurrencyFilter withDate(final long n) {
            return new CurrencyFilter(this.region, this.currency, n, n, this.tenderOnly);
        }
        
        public CurrencyFilter withDateRange(final long n, final long n2) {
            return new CurrencyFilter(this.region, this.currency, n, n2, this.tenderOnly);
        }
        
        public CurrencyFilter withTender() {
            return new CurrencyFilter(this.region, this.currency, this.from, this.to, true);
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof CurrencyFilter && this.equals((CurrencyFilter)o);
        }
        
        public boolean equals(final CurrencyFilter currencyFilter) {
            return this == currencyFilter || (currencyFilter != null && equals(this.region, currencyFilter.region) && equals(this.currency, currencyFilter.currency) && this.from == currencyFilter.from && this.to == currencyFilter.to && this.tenderOnly == currencyFilter.tenderOnly);
        }
        
        @Override
        public int hashCode() {
            if (this.region != null) {
                this.region.hashCode();
            }
            if (this.currency != null) {
                final int n = 0 + this.currency.hashCode();
            }
            final int n2 = 0 + (int)this.from;
            final int n3 = 0 + (int)(this.from >>> 32);
            final int n4 = 0 + (int)this.to;
            final int n5 = 0 + (int)(this.to >>> 32);
            final int n6 = 0 + (this.tenderOnly ? 1 : 0);
            return 0;
        }
        
        @Override
        public String toString() {
            return CurrencyMetaInfo.access$000(this);
        }
        
        private static boolean equals(final String s, final String s2) {
            return s == s2 || (s != null && s.equals(s2));
        }
        
        static {
            ALL = new CurrencyFilter(null, null, Long.MIN_VALUE, Long.MAX_VALUE, false);
        }
    }
}
