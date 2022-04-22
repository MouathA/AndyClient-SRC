package com.ibm.icu.impl.duration.impl;

import com.ibm.icu.impl.duration.*;
import java.text.*;
import java.util.*;

public class YMDDateFormatter implements DateFormatter
{
    private String requestedFields;
    private String localeName;
    private TimeZone timeZone;
    private SimpleDateFormat df;
    
    public YMDDateFormatter(final String s) {
        this(s, Locale.getDefault().toString(), TimeZone.getDefault());
    }
    
    public YMDDateFormatter(final String requestedFields, final String localeName, final TimeZone timeZone) {
        this.requestedFields = requestedFields;
        this.localeName = localeName;
        this.timeZone = timeZone;
        (this.df = new SimpleDateFormat("yyyy/mm/dd", Utils.localeFromString(localeName))).setTimeZone(timeZone);
    }
    
    public String format(final long n) {
        return this.format(new Date(n));
    }
    
    public String format(final Date date) {
        return this.df.format(date);
    }
    
    public DateFormatter withLocale(final String s) {
        if (!s.equals(this.localeName)) {
            return new YMDDateFormatter(this.requestedFields, s, this.timeZone);
        }
        return this;
    }
    
    public DateFormatter withTimeZone(final TimeZone timeZone) {
        if (!timeZone.equals(this.timeZone)) {
            return new YMDDateFormatter(this.requestedFields, this.localeName, timeZone);
        }
        return this;
    }
}
