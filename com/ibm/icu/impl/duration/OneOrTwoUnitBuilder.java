package com.ibm.icu.impl.duration;

class OneOrTwoUnitBuilder extends PeriodBuilderImpl
{
    OneOrTwoUnitBuilder(final BasicPeriodBuilderFactory.Settings settings) {
        super(settings);
    }
    
    public static OneOrTwoUnitBuilder get(final BasicPeriodBuilderFactory.Settings settings) {
        if (settings == null) {
            return null;
        }
        return new OneOrTwoUnitBuilder(settings);
    }
    
    @Override
    protected PeriodBuilder withSettings(final BasicPeriodBuilderFactory.Settings settings) {
        return get(settings);
    }
    
    @Override
    protected Period handleCreate(long n, final long n2, final boolean b) {
        Period period = null;
        final short effectiveSet = this.settings.effectiveSet();
        while (0 < TimeUnit.units.length) {
            if (0x0 != (effectiveSet & 0x1)) {
                final TimeUnit timeUnit = TimeUnit.units[0];
                final long approximateDuration = this.approximateDurationOf(timeUnit);
                if (n >= approximateDuration || period != null) {
                    final double n3 = n / (double)approximateDuration;
                    if (period == null) {
                        if (n3 >= 2.0) {
                            period = Period.at((float)n3, timeUnit);
                            break;
                        }
                        period = Period.at(1.0f, timeUnit).inPast(b);
                        n -= approximateDuration;
                    }
                    else {
                        if (n3 >= 1.0) {
                            period = period.and((float)n3, timeUnit);
                            break;
                        }
                        break;
                    }
                }
            }
            int n4 = 0;
            ++n4;
        }
        return period;
    }
}
