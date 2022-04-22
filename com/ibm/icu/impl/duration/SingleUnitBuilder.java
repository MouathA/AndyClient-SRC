package com.ibm.icu.impl.duration;

class SingleUnitBuilder extends PeriodBuilderImpl
{
    SingleUnitBuilder(final BasicPeriodBuilderFactory.Settings settings) {
        super(settings);
    }
    
    public static SingleUnitBuilder get(final BasicPeriodBuilderFactory.Settings settings) {
        if (settings == null) {
            return null;
        }
        return new SingleUnitBuilder(settings);
    }
    
    @Override
    protected PeriodBuilder withSettings(final BasicPeriodBuilderFactory.Settings settings) {
        return get(settings);
    }
    
    @Override
    protected Period handleCreate(final long n, final long n2, final boolean b) {
        final short effectiveSet = this.settings.effectiveSet();
        while (0 < TimeUnit.units.length) {
            if (0x0 != (effectiveSet & 0x1)) {
                final TimeUnit timeUnit = TimeUnit.units[0];
                final long approximateDuration = this.approximateDurationOf(timeUnit);
                if (n >= approximateDuration) {
                    return Period.at((float)(n / (double)approximateDuration), timeUnit).inPast(b);
                }
            }
            int n3 = 0;
            ++n3;
        }
        return null;
    }
}
