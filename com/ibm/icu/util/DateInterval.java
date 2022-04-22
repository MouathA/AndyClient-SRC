package com.ibm.icu.util;

import java.io.*;

public final class DateInterval implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final long fromDate;
    private final long toDate;
    
    public DateInterval(final long fromDate, final long toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
    
    public long getFromDate() {
        return this.fromDate;
    }
    
    public long getToDate() {
        return this.toDate;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof DateInterval) {
            final DateInterval dateInterval = (DateInterval)o;
            return this.fromDate == dateInterval.fromDate && this.toDate == dateInterval.toDate;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return (int)(this.fromDate + this.toDate);
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.fromDate) + " " + String.valueOf(this.toDate);
    }
}
