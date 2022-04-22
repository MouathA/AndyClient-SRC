package com.ibm.icu.util;

import java.util.*;

public class TimeArrayTimeZoneRule extends TimeZoneRule
{
    private static final long serialVersionUID = -1117109130077415245L;
    private final long[] startTimes;
    private final int timeType;
    
    public TimeArrayTimeZoneRule(final String s, final int n, final int n2, final long[] array, final int timeType) {
        super(s, n, n2);
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("No start times are specified.");
        }
        Arrays.sort(this.startTimes = array.clone());
        this.timeType = timeType;
    }
    
    public long[] getStartTimes() {
        return this.startTimes.clone();
    }
    
    public int getTimeType() {
        return this.timeType;
    }
    
    @Override
    public Date getFirstStart(final int n, final int n2) {
        return new Date(this.getUTC(this.startTimes[0], n, n2));
    }
    
    @Override
    public Date getFinalStart(final int n, final int n2) {
        return new Date(this.getUTC(this.startTimes[this.startTimes.length - 1], n, n2));
    }
    
    @Override
    public Date getNextStart(final long n, final int n2, final int n3, final boolean b) {
        int i;
        for (i = this.startTimes.length - 1; i >= 0; --i) {
            final long utc = this.getUTC(this.startTimes[i], n2, n3);
            if (utc < n) {
                break;
            }
            if (!b && utc == n) {
                break;
            }
        }
        if (i == this.startTimes.length - 1) {
            return null;
        }
        return new Date(this.getUTC(this.startTimes[i + 1], n2, n3));
    }
    
    @Override
    public Date getPreviousStart(final long n, final int n2, final int n3, final boolean b) {
        for (int i = this.startTimes.length - 1; i >= 0; --i) {
            final long utc = this.getUTC(this.startTimes[i], n2, n3);
            if (utc < n || (b && utc == n)) {
                return new Date(utc);
            }
        }
        return null;
    }
    
    @Override
    public boolean isEquivalentTo(final TimeZoneRule timeZoneRule) {
        return timeZoneRule instanceof TimeArrayTimeZoneRule && (this.timeType == ((TimeArrayTimeZoneRule)timeZoneRule).timeType && Arrays.equals(this.startTimes, ((TimeArrayTimeZoneRule)timeZoneRule).startTimes)) && super.isEquivalentTo(timeZoneRule);
    }
    
    @Override
    public boolean isTransitionRule() {
        return true;
    }
    
    private long getUTC(long n, final int n2, final int n3) {
        if (this.timeType != 2) {
            n -= n2;
        }
        if (this.timeType == 0) {
            n -= n3;
        }
        return n;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", timeType=");
        sb.append(this.timeType);
        sb.append(", startTimes=[");
        while (0 < this.startTimes.length) {
            if (false) {
                sb.append(", ");
            }
            sb.append(Long.toString(this.startTimes[0]));
            int n = 0;
            ++n;
        }
        sb.append("]");
        return sb.toString();
    }
}
