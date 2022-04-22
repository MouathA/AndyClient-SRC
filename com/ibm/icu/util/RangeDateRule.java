package com.ibm.icu.util;

import java.util.*;

public class RangeDateRule implements DateRule
{
    List ranges;
    
    public RangeDateRule() {
        this.ranges = new ArrayList(2);
    }
    
    public void add(final DateRule dateRule) {
        this.add(new Date(Long.MIN_VALUE), dateRule);
    }
    
    public void add(final Date date, final DateRule dateRule) {
        this.ranges.add(new Range(date, dateRule));
    }
    
    public Date firstAfter(final Date date) {
        this.startIndex(date);
        if (0 == this.ranges.size()) {}
        Date date2 = null;
        final Range range = this.rangeAt(0);
        final Range range2 = this.rangeAt(1);
        if (range != null && range.rule != null) {
            if (range2 != null) {
                date2 = range.rule.firstBetween(date, range2.start);
            }
            else {
                date2 = range.rule.firstAfter(date);
            }
        }
        return date2;
    }
    
    public Date firstBetween(final Date date, final Date date2) {
        if (date2 == null) {
            return this.firstAfter(date);
        }
        final int startIndex = this.startIndex(date);
        Date firstBetween = null;
        Range range2;
        for (Range range = this.rangeAt(startIndex); firstBetween == null && range != null && !range.start.after(date2); firstBetween = range2.rule.firstBetween(date, (range != null && !range.start.after(date2)) ? range.start : date2)) {
            range2 = range;
            range = this.rangeAt(startIndex + 1);
            if (range2.rule != null) {}
        }
        return firstBetween;
    }
    
    public boolean isOn(final Date date) {
        final Range range = this.rangeAt(this.startIndex(date));
        return range != null && range.rule != null && range.rule.isOn(date);
    }
    
    public boolean isBetween(final Date date, final Date date2) {
        return this.firstBetween(date, date2) == null;
    }
    
    private int startIndex(final Date date) {
        this.ranges.size();
        while (0 < this.ranges.size() && !date.before(this.ranges.get(0).start)) {
            int n = 0;
            ++n;
        }
        return 0;
    }
    
    private Range rangeAt(final int n) {
        return (n < this.ranges.size()) ? this.ranges.get(n) : null;
    }
}
