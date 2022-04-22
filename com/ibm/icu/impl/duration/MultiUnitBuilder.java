package com.ibm.icu.impl.duration;

class MultiUnitBuilder extends PeriodBuilderImpl
{
    private int nPeriods;
    
    MultiUnitBuilder(final int nPeriods, final BasicPeriodBuilderFactory.Settings settings) {
        super(settings);
        this.nPeriods = nPeriods;
    }
    
    public static MultiUnitBuilder get(final int n, final BasicPeriodBuilderFactory.Settings settings) {
        if (n > 0 && settings != null) {
            return new MultiUnitBuilder(n, settings);
        }
        return null;
    }
    
    @Override
    protected PeriodBuilder withSettings(final BasicPeriodBuilderFactory.Settings settings) {
        return get(this.nPeriods, settings);
    }
    
    @Override
    protected Period handleCreate(long n, final long n2, final boolean b) {
        Period period = null;
        final short effectiveSet = this.settings.effectiveSet();
        while (0 < TimeUnit.units.length) {
            if (0x0 != (effectiveSet & 0x1)) {
                final TimeUnit timeUnit = TimeUnit.units[0];
                if (0 == this.nPeriods) {
                    break;
                }
                final long approximateDuration = this.approximateDurationOf(timeUnit);
                if (n >= approximateDuration || 0 > 0) {
                    int n3 = 0;
                    ++n3;
                    double floor = n / (double)approximateDuration;
                    if (0 < this.nPeriods) {
                        floor = Math.floor(floor);
                        n -= (long)(floor * approximateDuration);
                    }
                    if (period == null) {
                        period = Period.at((float)floor, timeUnit).inPast(b);
                    }
                    else {
                        period = period.and((float)floor, timeUnit);
                    }
                }
            }
            int n4 = 0;
            ++n4;
        }
        return period;
    }
}
