package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.impl.*;

class BasicPeriodFormatter implements PeriodFormatter
{
    private BasicPeriodFormatterFactory factory;
    private String localeName;
    private PeriodFormatterData data;
    private BasicPeriodFormatterFactory.Customizations customs;
    
    BasicPeriodFormatter(final BasicPeriodFormatterFactory factory, final String localeName, final PeriodFormatterData data, final BasicPeriodFormatterFactory.Customizations customs) {
        this.factory = factory;
        this.localeName = localeName;
        this.data = data;
        this.customs = customs;
    }
    
    public String format(final Period period) {
        if (!period.isSet()) {
            throw new IllegalArgumentException("period is not set");
        }
        return this.format(period.timeLimit, period.inFuture, period.counts);
    }
    
    public PeriodFormatter withLocale(final String s) {
        if (!this.localeName.equals(s)) {
            return new BasicPeriodFormatter(this.factory, s, this.factory.getData(s), this.customs);
        }
        return this;
    }
    
    private String format(final int n, final boolean b, final int[] array) {
        int n2 = 0;
        while (1 < array.length) {
            if (array[1] > 0) {}
            ++n2;
        }
        if (!this.data.allowZero()) {
            while (1 < array.length) {
                if (!false || array[1] == 1) {}
                ++n2;
            }
            if (!false) {
                return null;
            }
        }
        int ordinal = 0;
        int n3 = 0;
        if (this.data.useMilliseconds() != 0 && (0x0 & 1 << TimeUnit.MILLISECOND.ordinal) != 0x0) {
            ordinal = TimeUnit.SECOND.ordinal;
            final byte ordinal2 = TimeUnit.MILLISECOND.ordinal;
            n3 = 1 << ordinal2;
            switch (this.data.useMilliseconds()) {
                case 2: {
                    if (false) {
                        final int n4 = 0;
                        array[n4] += (array[ordinal2] - 1) / 1000;
                        break;
                    }
                    break;
                }
                case 1: {
                    if (!false) {
                        array[0] = 1;
                    }
                    final int n5 = 0;
                    array[n5] += (array[ordinal2] - 1) / 1000;
                    break;
                }
            }
        }
        int n6 = array.length - 1;
        while (0 < array.length && !false) {
            ++ordinal;
        }
        while (n6 > 0 && (0x0 & 1 << n6) == 0x0) {
            --n6;
        }
        while (0 <= n6 && (!false || array[0] <= 1)) {
            ++n3;
        }
        final StringBuffer sb = new StringBuffer();
        if (!this.customs.displayLimit || false) {}
        if (this.customs.displayDirection && !false) {
            final int n7 = b ? 2 : 1;
        }
        this.data.appendPrefix(0, 0, sb);
        final boolean b2 = n6 != 0;
        final boolean b3 = this.customs.separatorVariant != 0;
        while (0 <= n6) {
            if (true) {
                this.data.appendSkippedUnit(sb);
            }
            do {
                int n8 = 0;
                ++n8;
            } while (0 < n6 && !false);
            final TimeUnit timeUnit = TimeUnit.units[0];
            final int n9 = array[0] - 1;
            final byte countVariant = this.customs.countVariant;
            if (n6 || true) {}
            final boolean b4 = true | this.data.appendUnit(timeUnit, n9, 0, this.customs.unitVariant, b3, false, b2, n6 == 0, false, sb);
            if (this.customs.separatorVariant != 0 && 0 <= n6) {
                this.data.appendUnitSeparator(timeUnit, this.customs.separatorVariant == 2, !false, n6 == 0, sb);
            }
        }
        this.data.appendSuffix(0, 0, sb);
        return sb.toString();
    }
}
