package com.ibm.icu.impl.duration;

class FixedUnitBuilder extends PeriodBuilderImpl
{
    private TimeUnit unit;
    
    public static FixedUnitBuilder get(final TimeUnit timeUnit, final BasicPeriodBuilderFactory.Settings settings) {
        if (settings != null && (settings.effectiveSet() & 1 << timeUnit.ordinal) != 0x0) {
            return new FixedUnitBuilder(timeUnit, settings);
        }
        return null;
    }
    
    FixedUnitBuilder(final TimeUnit unit, final BasicPeriodBuilderFactory.Settings settings) {
        super(settings);
        this.unit = unit;
    }
    
    @Override
    protected PeriodBuilder withSettings(final BasicPeriodBuilderFactory.Settings settings) {
        return get(this.unit, settings);
    }
    
    @Override
    protected Period handleCreate(final long n, final long n2, final boolean b) {
        if (this.unit == null) {
            return null;
        }
        return Period.at((float)(n / (double)this.approximateDurationOf(this.unit)), this.unit).inPast(b);
    }
}
