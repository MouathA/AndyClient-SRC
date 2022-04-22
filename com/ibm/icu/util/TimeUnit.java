package com.ibm.icu.util;

public class TimeUnit extends MeasureUnit
{
    private String name;
    private static TimeUnit[] values;
    public static TimeUnit SECOND;
    public static TimeUnit MINUTE;
    public static TimeUnit HOUR;
    public static TimeUnit DAY;
    public static TimeUnit WEEK;
    public static TimeUnit MONTH;
    public static TimeUnit YEAR;
    
    private TimeUnit(final String name) {
        this.name = name;
        final TimeUnit[] values = TimeUnit.values;
        final int n = 0;
        TimeUnit.valueCount = 1;
        values[n] = this;
    }
    
    public static TimeUnit[] values() {
        return TimeUnit.values.clone();
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    static {
        TimeUnit.values = new TimeUnit[7];
        TimeUnit.SECOND = new TimeUnit("second");
        TimeUnit.MINUTE = new TimeUnit("minute");
        TimeUnit.HOUR = new TimeUnit("hour");
        TimeUnit.DAY = new TimeUnit("day");
        TimeUnit.WEEK = new TimeUnit("week");
        TimeUnit.MONTH = new TimeUnit("month");
        TimeUnit.YEAR = new TimeUnit("year");
    }
}
