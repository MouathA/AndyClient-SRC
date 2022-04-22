package com.ibm.icu.util;

import java.util.*;

public final class CopticCalendar extends CECalendar
{
    private static final long serialVersionUID = 5903818751846742911L;
    public static final int TOUT = 0;
    public static final int BABA = 1;
    public static final int HATOR = 2;
    public static final int KIAHK = 3;
    public static final int TOBA = 4;
    public static final int AMSHIR = 5;
    public static final int BARAMHAT = 6;
    public static final int BARAMOUDA = 7;
    public static final int BASHANS = 8;
    public static final int PAONA = 9;
    public static final int EPEP = 10;
    public static final int MESRA = 11;
    public static final int NASIE = 12;
    private static final int JD_EPOCH_OFFSET = 1824665;
    private static final int BCE = 0;
    private static final int CE = 1;
    
    public CopticCalendar() {
    }
    
    public CopticCalendar(final TimeZone timeZone) {
        super(timeZone);
    }
    
    public CopticCalendar(final Locale locale) {
        super(locale);
    }
    
    public CopticCalendar(final ULocale uLocale) {
        super(uLocale);
    }
    
    public CopticCalendar(final TimeZone timeZone, final Locale locale) {
        super(timeZone, locale);
    }
    
    public CopticCalendar(final TimeZone timeZone, final ULocale uLocale) {
        super(timeZone, uLocale);
    }
    
    public CopticCalendar(final int n, final int n2, final int n3) {
        super(n, n2, n3);
    }
    
    public CopticCalendar(final Date date) {
        super(date);
    }
    
    public CopticCalendar(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        super(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public String getType() {
        return "coptic";
    }
    
    @Override
    @Deprecated
    protected int handleGetExtendedYear() {
        int n;
        if (this.newerField(19, 1) == 19) {
            n = this.internalGet(19, 1);
        }
        else if (this.internalGet(0, 1) == 0) {
            n = 1 - this.internalGet(1, 1);
        }
        else {
            n = this.internalGet(1, 1);
        }
        return n;
    }
    
    @Override
    @Deprecated
    protected void handleComputeFields(final int n) {
        final int[] array = new int[3];
        CECalendar.jdToCE(n, this.getJDEpochOffset(), array);
        int n2;
        if (array[0] <= 0) {
            n2 = 1 - array[0];
        }
        else {
            n2 = array[0];
        }
        this.internalSet(19, array[0]);
        this.internalSet(0, 1);
        this.internalSet(1, n2);
        this.internalSet(2, array[1]);
        this.internalSet(5, array[2]);
        this.internalSet(6, 30 * array[1] + array[2]);
    }
    
    @Override
    @Deprecated
    protected int getJDEpochOffset() {
        return 1824665;
    }
    
    public static int copticToJD(final long n, final int n2, final int n3) {
        return CECalendar.ceToJD(n, n2, n3, 1824665);
    }
}
