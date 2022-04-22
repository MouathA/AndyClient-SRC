package com.ibm.icu.util;

import java.util.*;

class EasterRule implements DateRule
{
    private static GregorianCalendar gregorian;
    private static GregorianCalendar orthodox;
    private int daysAfterEaster;
    private GregorianCalendar calendar;
    
    public EasterRule(final int daysAfterEaster, final boolean b) {
        this.calendar = EasterRule.gregorian;
        this.daysAfterEaster = daysAfterEaster;
        if (b) {
            EasterRule.orthodox.setGregorianChange(new Date(Long.MAX_VALUE));
            this.calendar = EasterRule.orthodox;
        }
    }
    
    public Date firstAfter(final Date date) {
        return this.doFirstBetween(date, null);
    }
    
    public Date firstBetween(final Date date, final Date date2) {
        return this.doFirstBetween(date, date2);
    }
    
    public boolean isOn(final Date time) {
        // monitorenter(calendar = this.calendar)
        this.calendar.setTime(time);
        final int value = this.calendar.get(6);
        this.calendar.setTime(this.computeInYear(this.calendar.getTime(), this.calendar));
        // monitorexit(calendar)
        return this.calendar.get(6) == value;
    }
    
    public boolean isBetween(final Date date, final Date date2) {
        return this.firstBetween(date, date2) != null;
    }
    
    private Date doFirstBetween(final Date time, final Date date) {
        // monitorenter(calendar = this.calendar)
        Date date2 = this.computeInYear(time, this.calendar);
        if (date2.before(time)) {
            this.calendar.setTime(time);
            this.calendar.get(1);
            this.calendar.add(1, 1);
            date2 = this.computeInYear(this.calendar.getTime(), this.calendar);
        }
        if (date != null && date2.after(date)) {
            // monitorexit(calendar)
            return null;
        }
        // monitorexit(calendar)
        return date2;
    }
    
    private Date computeInYear(final Date time, GregorianCalendar calendar) {
        if (calendar == null) {
            calendar = this.calendar;
        }
        // monitorenter(gregorianCalendar = calendar)
        calendar.setTime(time);
        final int value = calendar.get(1);
        if (calendar.getTime().after(calendar.getGregorianChange())) {}
        calendar.clear();
        calendar.set(0, 1);
        calendar.set(1, value);
        calendar.set(2, 2);
        calendar.set(5, 28);
        calendar.getTime();
        calendar.add(5, this.daysAfterEaster);
        // monitorexit(gregorianCalendar)
        return calendar.getTime();
    }
    
    static {
        EasterRule.gregorian = new GregorianCalendar();
        EasterRule.orthodox = new GregorianCalendar();
    }
}
