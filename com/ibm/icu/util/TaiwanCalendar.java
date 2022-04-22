package com.ibm.icu.util;

import java.util.*;

public class TaiwanCalendar extends GregorianCalendar
{
    private static final long serialVersionUID = 2583005278132380631L;
    public static final int BEFORE_MINGUO = 0;
    public static final int MINGUO = 1;
    private static final int Taiwan_ERA_START = 1911;
    private static final int GREGORIAN_EPOCH = 1970;
    
    public TaiwanCalendar() {
    }
    
    public TaiwanCalendar(final TimeZone timeZone) {
        super(timeZone);
    }
    
    public TaiwanCalendar(final Locale locale) {
        super(locale);
    }
    
    public TaiwanCalendar(final ULocale uLocale) {
        super(uLocale);
    }
    
    public TaiwanCalendar(final TimeZone timeZone, final Locale locale) {
        super(timeZone, locale);
    }
    
    public TaiwanCalendar(final TimeZone timeZone, final ULocale uLocale) {
        super(timeZone, uLocale);
    }
    
    public TaiwanCalendar(final Date time) {
        this();
        this.setTime(time);
    }
    
    public TaiwanCalendar(final int n, final int n2, final int n3) {
        super(n, n2, n3);
    }
    
    public TaiwanCalendar(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        super(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    protected int handleGetExtendedYear() {
        if (this.newerField(19, 1) == 19 && this.newerField(19, 0) == 19) {
            this.internalGet(19, 1970);
        }
        else if (this.internalGet(0, 1) == 1) {
            final int n = this.internalGet(1, 1) + 1911;
        }
        else {
            final int n2 = 1 - this.internalGet(1, 1) + 1911;
        }
        return 1970;
    }
    
    @Override
    protected void handleComputeFields(final int n) {
        super.handleComputeFields(n);
        final int n2 = this.internalGet(19) - 1911;
        if (n2 > 0) {
            this.internalSet(0, 1);
            this.internalSet(1, n2);
        }
        else {
            this.internalSet(0, 0);
            this.internalSet(1, 1 - n2);
        }
    }
    
    @Override
    protected int handleGetLimit(final int n, final int n2) {
        if (n != 0) {
            return super.handleGetLimit(n, n2);
        }
        if (n2 == 0 || n2 == 1) {
            return 0;
        }
        return 1;
    }
    
    @Override
    public String getType() {
        return "roc";
    }
}
