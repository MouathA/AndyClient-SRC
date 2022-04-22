package com.ibm.icu.util;

import java.util.*;

public class SimpleDateRule implements DateRule
{
    private static GregorianCalendar gCalendar;
    private Calendar calendar;
    private int month;
    private int dayOfMonth;
    private int dayOfWeek;
    
    public SimpleDateRule(final int month, final int dayOfMonth) {
        this.calendar = SimpleDateRule.gCalendar;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = 0;
    }
    
    SimpleDateRule(final int month, final int dayOfMonth, final Calendar calendar) {
        this.calendar = SimpleDateRule.gCalendar;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = 0;
        this.calendar = calendar;
    }
    
    public SimpleDateRule(final int month, final int dayOfMonth, final int n, final boolean b) {
        this.calendar = SimpleDateRule.gCalendar;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = (b ? n : (-n));
    }
    
    public Date firstAfter(final Date date) {
        return this.doFirstBetween(date, null);
    }
    
    public Date firstBetween(final Date date, final Date date2) {
        return this.doFirstBetween(date, date2);
    }
    
    public boolean isOn(final Date time) {
        final Calendar calendar = this.calendar;
        // monitorenter(calendar2 = calendar)
        calendar.setTime(time);
        final int value = calendar.get(6);
        calendar.setTime(this.computeInYear(calendar.get(1), calendar));
        // monitorexit(calendar2)
        return calendar.get(6) == value;
    }
    
    public boolean isBetween(final Date date, final Date date2) {
        return this.firstBetween(date, date2) != null;
    }
    
    private Date doFirstBetween(final Date time, final Date date) {
        final Calendar calendar = this.calendar;
        // monitorenter(calendar2 = calendar)
        calendar.setTime(time);
        int value = calendar.get(1);
        final int value2 = calendar.get(2);
        if (value2 > this.month) {
            ++value;
        }
        Date date2 = this.computeInYear(value, calendar);
        if (value2 == this.month && date2.before(time)) {
            date2 = this.computeInYear(value + 1, calendar);
        }
        if (date != null && date2.after(date)) {
            // monitorexit(calendar2)
            return null;
        }
        // monitorexit(calendar2)
        return date2;
    }
    
    private Date computeInYear(final int n, final Calendar calendar) {
        // monitorenter(calendar)
        calendar.clear();
        calendar.set(0, calendar.getMaximum(0));
        calendar.set(1, n);
        calendar.set(2, this.month);
        calendar.set(5, this.dayOfMonth);
        if (this.dayOfWeek != 0) {
            calendar.setTime(calendar.getTime());
            final int value = calendar.get(7);
            if (this.dayOfWeek > 0) {
                final int n2 = (this.dayOfWeek - value + 7) % 7;
            }
            else {
                final int n3 = -((this.dayOfWeek + value + 7) % 7);
            }
            calendar.add(5, 0);
        }
        // monitorexit(calendar)
        return calendar.getTime();
    }
    
    static {
        SimpleDateRule.gCalendar = new GregorianCalendar();
    }
}
