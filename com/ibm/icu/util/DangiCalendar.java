package com.ibm.icu.util;

import java.util.*;

public class DangiCalendar extends ChineseCalendar
{
    private static final long serialVersionUID = 8156297445349501985L;
    private static final int DANGI_EPOCH_YEAR = -2332;
    private static final TimeZone KOREA_ZONE;
    
    @Deprecated
    public DangiCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    @Deprecated
    public DangiCalendar(final Date time) {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.setTime(time);
    }
    
    @Deprecated
    public DangiCalendar(final TimeZone timeZone, final ULocale uLocale) {
        super(timeZone, uLocale, -2332, DangiCalendar.KOREA_ZONE);
    }
    
    @Override
    @Deprecated
    public String getType() {
        return "dangi";
    }
    
    static {
        final InitialTimeZoneRule initialTimeZoneRule = new InitialTimeZoneRule("GMT+8", 28800000, 0);
        final long[] array = { -2302128000000L };
        final long[] array2 = { -2270592000000L };
        final long[] array3 = { -1829088000000L };
        final TimeArrayTimeZoneRule timeArrayTimeZoneRule = new TimeArrayTimeZoneRule("Korean 1897", 25200000, 0, array, 1);
        final TimeArrayTimeZoneRule timeArrayTimeZoneRule2 = new TimeArrayTimeZoneRule("Korean 1898-1911", 28800000, 0, array2, 1);
        final TimeArrayTimeZoneRule timeArrayTimeZoneRule3 = new TimeArrayTimeZoneRule("Korean 1912-", 32400000, 0, array3, 1);
        final RuleBasedTimeZone korea_ZONE = new RuleBasedTimeZone("KOREA_ZONE", initialTimeZoneRule);
        korea_ZONE.addTransitionRule(timeArrayTimeZoneRule);
        korea_ZONE.addTransitionRule(timeArrayTimeZoneRule2);
        korea_ZONE.addTransitionRule(timeArrayTimeZoneRule3);
        korea_ZONE.freeze();
        KOREA_ZONE = korea_ZONE;
    }
}
