package com.ibm.icu.util;

import java.util.*;

public class BuddhistCalendar extends GregorianCalendar
{
    private static final long serialVersionUID = 2583005278132380631L;
    public static final int BE = 0;
    private static final int BUDDHIST_ERA_START = -543;
    private static final int GREGORIAN_EPOCH = 1970;
    
    public BuddhistCalendar() {
    }
    
    public BuddhistCalendar(final TimeZone timeZone) {
        super(timeZone);
    }
    
    public BuddhistCalendar(final Locale locale) {
        super(locale);
    }
    
    public BuddhistCalendar(final ULocale uLocale) {
        super(uLocale);
    }
    
    public BuddhistCalendar(final TimeZone timeZone, final Locale locale) {
        super(timeZone, locale);
    }
    
    public BuddhistCalendar(final TimeZone timeZone, final ULocale uLocale) {
        super(timeZone, uLocale);
    }
    
    public BuddhistCalendar(final Date time) {
        this();
        this.setTime(time);
    }
    
    public BuddhistCalendar(final int n, final int n2, final int n3) {
        super(n, n2, n3);
    }
    
    public BuddhistCalendar(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        super(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    protected int handleGetExtendedYear() {
        int internalGet;
        if (this.newerField(19, 1) == 19) {
            internalGet = this.internalGet(19, 1970);
        }
        else {
            internalGet = this.internalGet(1, 2513) - 543;
        }
        return internalGet;
    }
    
    @Override
    protected int handleComputeMonthStart(final int n, final int n2, final boolean b) {
        return super.handleComputeMonthStart(n, n2, b);
    }
    
    @Override
    protected void handleComputeFields(final int n) {
        super.handleComputeFields(n);
        final int n2 = this.internalGet(19) + 543;
        this.internalSet(0, 0);
        this.internalSet(1, n2);
    }
    
    @Override
    protected int handleGetLimit(final int n, final int n2) {
        if (n == 0) {
            return 0;
        }
        return super.handleGetLimit(n, n2);
    }
    
    @Override
    public String getType() {
        return "buddhist";
    }
}
