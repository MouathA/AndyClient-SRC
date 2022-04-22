package com.ibm.icu.impl.duration;

import java.util.*;

class BasicDurationFormatter implements DurationFormatter
{
    private PeriodFormatter formatter;
    private PeriodBuilder builder;
    private DateFormatter fallback;
    private long fallbackLimit;
    private String localeName;
    private TimeZone timeZone;
    
    public BasicDurationFormatter(final PeriodFormatter formatter, final PeriodBuilder builder, final DateFormatter fallback, final long n) {
        this.formatter = formatter;
        this.builder = builder;
        this.fallback = fallback;
        this.fallbackLimit = ((n < 0L) ? 0L : n);
    }
    
    protected BasicDurationFormatter(final PeriodFormatter formatter, final PeriodBuilder builder, final DateFormatter fallback, final long fallbackLimit, final String localeName, final TimeZone timeZone) {
        this.formatter = formatter;
        this.builder = builder;
        this.fallback = fallback;
        this.fallbackLimit = fallbackLimit;
        this.localeName = localeName;
        this.timeZone = timeZone;
    }
    
    public String formatDurationFromNowTo(final Date date) {
        final long currentTimeMillis = System.currentTimeMillis();
        return this.formatDurationFrom(currentTimeMillis - date.getTime(), currentTimeMillis);
    }
    
    public String formatDurationFromNow(final long n) {
        return this.formatDurationFrom(n, System.currentTimeMillis());
    }
    
    public String formatDurationFrom(final long n, final long n2) {
        String s = this.doFallback(n, n2);
        if (s == null) {
            s = this.doFormat(this.doBuild(n, n2));
        }
        return s;
    }
    
    public DurationFormatter withLocale(final String s) {
        if (!s.equals(this.localeName)) {
            return new BasicDurationFormatter(this.formatter.withLocale(s), this.builder.withLocale(s), (this.fallback == null) ? null : this.fallback.withLocale(s), this.fallbackLimit, s, this.timeZone);
        }
        return this;
    }
    
    public DurationFormatter withTimeZone(final TimeZone timeZone) {
        if (!timeZone.equals(this.timeZone)) {
            return new BasicDurationFormatter(this.formatter, this.builder.withTimeZone(timeZone), (this.fallback == null) ? null : this.fallback.withTimeZone(timeZone), this.fallbackLimit, this.localeName, timeZone);
        }
        return this;
    }
    
    protected String doFallback(final long n, final long n2) {
        if (this.fallback != null && this.fallbackLimit > 0L && Math.abs(n) >= this.fallbackLimit) {
            return this.fallback.format(n2 + n);
        }
        return null;
    }
    
    protected Period doBuild(final long n, final long n2) {
        return this.builder.createWithReferenceDate(n, n2);
    }
    
    protected String doFormat(final Period period) {
        if (!period.isSet()) {
            throw new IllegalArgumentException("period is not set");
        }
        return this.formatter.format(period);
    }
}
