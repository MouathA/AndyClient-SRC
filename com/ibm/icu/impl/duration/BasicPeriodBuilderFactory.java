package com.ibm.icu.impl.duration;

import java.util.*;
import com.ibm.icu.impl.duration.impl.*;

class BasicPeriodBuilderFactory implements PeriodBuilderFactory
{
    private PeriodFormatterDataService ds;
    private Settings settings;
    private static final short allBits = 255;
    
    BasicPeriodBuilderFactory(final PeriodFormatterDataService ds) {
        this.ds = ds;
        this.settings = new Settings();
    }
    
    static long approximateDurationOf(final TimeUnit timeUnit) {
        return TimeUnit.approxDurations[timeUnit.ordinal];
    }
    
    public PeriodBuilderFactory setAvailableUnitRange(final TimeUnit timeUnit, final TimeUnit timeUnit2) {
        for (byte ordinal = timeUnit2.ordinal; ordinal <= timeUnit.ordinal; ++ordinal) {}
        if (!false) {
            throw new IllegalArgumentException("range " + timeUnit + " to " + timeUnit2 + " is empty");
        }
        this.settings = this.settings.setUnits(0);
        return this;
    }
    
    public PeriodBuilderFactory setUnitIsAvailable(final TimeUnit timeUnit, final boolean b) {
        final short uset = this.settings.uset;
        int units;
        if (b) {
            units = (uset | 1 << timeUnit.ordinal);
        }
        else {
            units = (uset & ~(1 << timeUnit.ordinal));
        }
        this.settings = this.settings.setUnits(units);
        return this;
    }
    
    public PeriodBuilderFactory setMaxLimit(final float maxLimit) {
        this.settings = this.settings.setMaxLimit(maxLimit);
        return this;
    }
    
    public PeriodBuilderFactory setMinLimit(final float minLimit) {
        this.settings = this.settings.setMinLimit(minLimit);
        return this;
    }
    
    public PeriodBuilderFactory setAllowZero(final boolean allowZero) {
        this.settings = this.settings.setAllowZero(allowZero);
        return this;
    }
    
    public PeriodBuilderFactory setWeeksAloneOnly(final boolean weeksAloneOnly) {
        this.settings = this.settings.setWeeksAloneOnly(weeksAloneOnly);
        return this;
    }
    
    public PeriodBuilderFactory setAllowMilliseconds(final boolean allowMilliseconds) {
        this.settings = this.settings.setAllowMilliseconds(allowMilliseconds);
        return this;
    }
    
    public PeriodBuilderFactory setLocale(final String locale) {
        this.settings = this.settings.setLocale(locale);
        return this;
    }
    
    public PeriodBuilderFactory setTimeZone(final TimeZone timeZone) {
        return this;
    }
    
    private Settings getSettings() {
        if (this.settings.effectiveSet() == 0) {
            return null;
        }
        return this.settings.setInUse();
    }
    
    public PeriodBuilder getFixedUnitBuilder(final TimeUnit timeUnit) {
        return FixedUnitBuilder.get(timeUnit, this.getSettings());
    }
    
    public PeriodBuilder getSingleUnitBuilder() {
        return SingleUnitBuilder.get(this.getSettings());
    }
    
    public PeriodBuilder getOneOrTwoUnitBuilder() {
        return OneOrTwoUnitBuilder.get(this.getSettings());
    }
    
    public PeriodBuilder getMultiUnitBuilder(final int n) {
        return MultiUnitBuilder.get(n, this.getSettings());
    }
    
    static PeriodFormatterDataService access$000(final BasicPeriodBuilderFactory basicPeriodBuilderFactory) {
        return basicPeriodBuilderFactory.ds;
    }
    
    class Settings
    {
        boolean inUse;
        short uset;
        TimeUnit maxUnit;
        TimeUnit minUnit;
        int maxLimit;
        int minLimit;
        boolean allowZero;
        boolean weeksAloneOnly;
        boolean allowMillis;
        final BasicPeriodBuilderFactory this$0;
        
        Settings(final BasicPeriodBuilderFactory this$0) {
            this.this$0 = this$0;
            this.uset = 255;
            this.maxUnit = TimeUnit.YEAR;
            this.minUnit = TimeUnit.MILLISECOND;
            this.allowZero = true;
            this.allowMillis = true;
        }
        
        Settings setUnits(final int n) {
            if (this.uset == n) {
                return this;
            }
            final Settings settings = this.inUse ? this.copy() : this;
            settings.uset = (short)n;
            if ((n & 0xFF) == 0xFF) {
                settings.uset = 255;
                settings.maxUnit = TimeUnit.YEAR;
                settings.minUnit = TimeUnit.MILLISECOND;
            }
            else {
                while (0 < TimeUnit.units.length) {
                    if (0x0 != (n & 0x1)) {
                        settings.maxUnit = TimeUnit.units[0];
                    }
                    int n2 = 0;
                    ++n2;
                }
                final Settings settings2 = settings;
                final Settings settings3 = settings;
                final TimeUnit timeUnit = null;
                settings3.maxUnit = timeUnit;
                settings2.minUnit = timeUnit;
            }
            return settings;
        }
        
        short effectiveSet() {
            if (this.allowMillis) {
                return this.uset;
            }
            return (short)(this.uset & ~(1 << TimeUnit.MILLISECOND.ordinal));
        }
        
        TimeUnit effectiveMinUnit() {
            if (this.allowMillis || this.minUnit != TimeUnit.MILLISECOND) {
                return this.minUnit;
            }
            int n = TimeUnit.units.length - 1;
            while (--n >= 0) {
                if (0x0 != (this.uset & 1 << n)) {
                    return TimeUnit.units[n];
                }
            }
            return TimeUnit.SECOND;
        }
        
        Settings setMaxLimit(final float n) {
            final int maxLimit = (n <= 0.0f) ? 0 : ((int)(n * 1000.0f));
            if (n == maxLimit) {
                return this;
            }
            final Settings settings = this.inUse ? this.copy() : this;
            settings.maxLimit = maxLimit;
            return settings;
        }
        
        Settings setMinLimit(final float n) {
            final int minLimit = (n <= 0.0f) ? 0 : ((int)(n * 1000.0f));
            if (n == minLimit) {
                return this;
            }
            final Settings settings = this.inUse ? this.copy() : this;
            settings.minLimit = minLimit;
            return settings;
        }
        
        Settings setAllowZero(final boolean allowZero) {
            if (this.allowZero == allowZero) {
                return this;
            }
            final Settings settings = this.inUse ? this.copy() : this;
            settings.allowZero = allowZero;
            return settings;
        }
        
        Settings setWeeksAloneOnly(final boolean weeksAloneOnly) {
            if (this.weeksAloneOnly == weeksAloneOnly) {
                return this;
            }
            final Settings settings = this.inUse ? this.copy() : this;
            settings.weeksAloneOnly = weeksAloneOnly;
            return settings;
        }
        
        Settings setAllowMilliseconds(final boolean allowMillis) {
            if (this.allowMillis == allowMillis) {
                return this;
            }
            final Settings settings = this.inUse ? this.copy() : this;
            settings.allowMillis = allowMillis;
            return settings;
        }
        
        Settings setLocale(final String s) {
            final PeriodFormatterData value = BasicPeriodBuilderFactory.access$000(this.this$0).get(s);
            return this.setAllowZero(value.allowZero()).setWeeksAloneOnly(value.weeksAloneOnly()).setAllowMilliseconds(value.useMilliseconds() != 1);
        }
        
        Settings setInUse() {
            this.inUse = true;
            return this;
        }
        
        Period createLimited(final long n, final boolean b) {
            if (this.maxLimit > 0 && n * 1000L > this.maxLimit * BasicPeriodBuilderFactory.approximateDurationOf(this.maxUnit)) {
                return Period.moreThan(this.maxLimit / 1000.0f, this.maxUnit).inPast(b);
            }
            if (this.minLimit > 0) {
                final TimeUnit effectiveMinUnit = this.effectiveMinUnit();
                final long approximateDuration = BasicPeriodBuilderFactory.approximateDurationOf(effectiveMinUnit);
                final long n2 = (effectiveMinUnit == this.minUnit) ? this.minLimit : Math.max(1000L, BasicPeriodBuilderFactory.approximateDurationOf(this.minUnit) * this.minLimit / approximateDuration);
                if (n * 1000L < n2 * approximateDuration) {
                    return Period.lessThan(n2 / 1000.0f, effectiveMinUnit).inPast(b);
                }
            }
            return null;
        }
        
        public Settings copy() {
            final Settings settings = this.this$0.new Settings();
            settings.inUse = this.inUse;
            settings.uset = this.uset;
            settings.maxUnit = this.maxUnit;
            settings.minUnit = this.minUnit;
            settings.maxLimit = this.maxLimit;
            settings.minLimit = this.minLimit;
            settings.allowZero = this.allowZero;
            settings.weeksAloneOnly = this.weeksAloneOnly;
            settings.allowMillis = this.allowMillis;
            return settings;
        }
    }
}
