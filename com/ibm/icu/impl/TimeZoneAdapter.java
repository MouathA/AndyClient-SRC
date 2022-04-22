package com.ibm.icu.impl;

import java.util.*;

public class TimeZoneAdapter extends TimeZone
{
    static final long serialVersionUID = -2040072218820018557L;
    private com.ibm.icu.util.TimeZone zone;
    
    public static TimeZone wrap(final com.ibm.icu.util.TimeZone timeZone) {
        return new TimeZoneAdapter(timeZone);
    }
    
    public com.ibm.icu.util.TimeZone unwrap() {
        return this.zone;
    }
    
    public TimeZoneAdapter(final com.ibm.icu.util.TimeZone zone) {
        this.zone = zone;
        super.setID(zone.getID());
    }
    
    @Override
    public void setID(final String s) {
        super.setID(s);
        this.zone.setID(s);
    }
    
    @Override
    public boolean hasSameRules(final TimeZone timeZone) {
        return timeZone instanceof TimeZoneAdapter && this.zone.hasSameRules(((TimeZoneAdapter)timeZone).zone);
    }
    
    @Override
    public int getOffset(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return this.zone.getOffset(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public int getRawOffset() {
        return this.zone.getRawOffset();
    }
    
    @Override
    public void setRawOffset(final int rawOffset) {
        this.zone.setRawOffset(rawOffset);
    }
    
    @Override
    public boolean useDaylightTime() {
        return this.zone.useDaylightTime();
    }
    
    @Override
    public boolean inDaylightTime(final Date date) {
        return this.zone.inDaylightTime(date);
    }
    
    @Override
    public Object clone() {
        return new TimeZoneAdapter((com.ibm.icu.util.TimeZone)this.zone.clone());
    }
    
    @Override
    public synchronized int hashCode() {
        return this.zone.hashCode();
    }
    
    @Override
    public boolean equals(Object zone) {
        if (zone instanceof TimeZoneAdapter) {
            zone = ((TimeZoneAdapter)zone).zone;
        }
        return this.zone.equals(zone);
    }
    
    @Override
    public String toString() {
        return "TimeZoneAdapter: " + this.zone.toString();
    }
}
