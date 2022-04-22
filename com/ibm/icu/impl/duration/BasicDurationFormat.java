package com.ibm.icu.impl.duration;

import com.ibm.icu.text.*;
import com.ibm.icu.util.*;
import java.text.*;
import java.util.*;
import javax.xml.datatype.*;

public class BasicDurationFormat extends DurationFormat
{
    private static final long serialVersionUID = -3146984141909457700L;
    transient DurationFormatter formatter;
    transient PeriodFormatter pformatter;
    transient PeriodFormatterService pfs;
    private static boolean checkXMLDuration;
    
    public static BasicDurationFormat getInstance(final ULocale uLocale) {
        return new BasicDurationFormat(uLocale);
    }
    
    @Override
    public StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        if (o instanceof Long) {
            return sb.append(this.formatDurationFromNow((long)o));
        }
        if (o instanceof Date) {
            return sb.append(this.formatDurationFromNowTo((Date)o));
        }
        if (BasicDurationFormat.checkXMLDuration && o instanceof Duration) {
            return sb.append(this.formatDuration(o));
        }
        throw new IllegalArgumentException("Cannot format given Object as a Duration");
    }
    
    public BasicDurationFormat() {
        this.pfs = null;
        this.pfs = BasicPeriodFormatterService.getInstance();
        this.formatter = this.pfs.newDurationFormatterFactory().getFormatter();
        this.pformatter = this.pfs.newPeriodFormatterFactory().setDisplayPastFuture(false).getFormatter();
    }
    
    public BasicDurationFormat(final ULocale uLocale) {
        super(uLocale);
        this.pfs = null;
        this.pfs = BasicPeriodFormatterService.getInstance();
        this.formatter = this.pfs.newDurationFormatterFactory().setLocale(uLocale.getName()).getFormatter();
        this.pformatter = this.pfs.newPeriodFormatterFactory().setDisplayPastFuture(false).setLocale(uLocale.getName()).getFormatter();
    }
    
    @Override
    public String formatDurationFrom(final long n, final long n2) {
        return this.formatter.formatDurationFrom(n, n2);
    }
    
    @Override
    public String formatDurationFromNow(final long n) {
        return this.formatter.formatDurationFromNow(n);
    }
    
    @Override
    public String formatDurationFromNowTo(final Date date) {
        return this.formatter.formatDurationFromNowTo(date);
    }
    
    public String formatDuration(final Object o) {
        final DatatypeConstants.Field[] array = { DatatypeConstants.YEARS, DatatypeConstants.MONTHS, DatatypeConstants.DAYS, DatatypeConstants.HOURS, DatatypeConstants.MINUTES, DatatypeConstants.SECONDS };
        final TimeUnit[] array2 = { TimeUnit.YEAR, TimeUnit.MONTH, TimeUnit.DAY, TimeUnit.HOUR, TimeUnit.MINUTE, TimeUnit.SECOND };
        final Duration duration = (Duration)o;
        Period period = null;
        Duration negate = duration;
        if (duration.getSign() < 0) {
            negate = duration.negate();
        }
        while (0 < array.length) {
            if (negate.isSet(array[0])) {
                final Number field = negate.getField(array[0]);
                if (field.intValue() == 0) {}
                float floatValue = field.floatValue();
                TimeUnit millisecond = null;
                float n = 0.0f;
                if (array2[0] == TimeUnit.SECOND) {
                    final double n2 = floatValue;
                    final double floor = Math.floor(floatValue);
                    final double n3 = (n2 - floor) * 1000.0;
                    if (n3 > 0.0) {
                        millisecond = TimeUnit.MILLISECOND;
                        n = (float)n3;
                        floatValue = (float)floor;
                    }
                }
                if (period == null) {
                    period = Period.at(floatValue, array2[0]);
                }
                else {
                    period = period.and(floatValue, array2[0]);
                }
                if (millisecond != null) {
                    period = period.and(n, millisecond);
                }
            }
            int n4 = 0;
            ++n4;
        }
        if (period == null) {
            return this.formatDurationFromNow(0L);
        }
        return this.pformatter.format(period.inPast());
    }
    
    static {
        BasicDurationFormat.checkXMLDuration = true;
    }
}
