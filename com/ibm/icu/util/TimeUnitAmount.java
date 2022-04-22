package com.ibm.icu.util;

public class TimeUnitAmount extends Measure
{
    public TimeUnitAmount(final Number n, final TimeUnit timeUnit) {
        super(n, timeUnit);
    }
    
    public TimeUnitAmount(final double n, final TimeUnit timeUnit) {
        super(new Double(n), timeUnit);
    }
    
    public TimeUnit getTimeUnit() {
        return (TimeUnit)this.getUnit();
    }
}
