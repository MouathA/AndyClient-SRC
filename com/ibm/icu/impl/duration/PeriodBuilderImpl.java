package com.ibm.icu.impl.duration;

import java.util.*;

abstract class PeriodBuilderImpl implements PeriodBuilder
{
    protected BasicPeriodBuilderFactory.Settings settings;
    
    public Period create(final long n) {
        return this.createWithReferenceDate(n, System.currentTimeMillis());
    }
    
    public long approximateDurationOf(final TimeUnit timeUnit) {
        return BasicPeriodBuilderFactory.approximateDurationOf(timeUnit);
    }
    
    public Period createWithReferenceDate(long n, final long n2) {
        final boolean b = n < 0L;
        if (b) {
            n = -n;
        }
        Period period = this.settings.createLimited(n, b);
        if (period == null) {
            period = this.handleCreate(n, n2, b);
            if (period == null) {
                period = Period.lessThan(1.0f, this.settings.effectiveMinUnit()).inPast(b);
            }
        }
        return period;
    }
    
    public PeriodBuilder withTimeZone(final TimeZone timeZone) {
        return this;
    }
    
    public PeriodBuilder withLocale(final String locale) {
        final BasicPeriodBuilderFactory.Settings setLocale = this.settings.setLocale(locale);
        if (setLocale != this.settings) {
            return this.withSettings(setLocale);
        }
        return this;
    }
    
    protected abstract PeriodBuilder withSettings(final BasicPeriodBuilderFactory.Settings p0);
    
    protected abstract Period handleCreate(final long p0, final long p1, final boolean p2);
    
    protected PeriodBuilderImpl(final BasicPeriodBuilderFactory.Settings settings) {
        this.settings = settings;
    }
}
