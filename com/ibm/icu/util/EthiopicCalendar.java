package com.ibm.icu.util;

import java.util.*;

public final class EthiopicCalendar extends CECalendar
{
    private static final long serialVersionUID = -2438495771339315608L;
    public static final int MESKEREM = 0;
    public static final int TEKEMT = 1;
    public static final int HEDAR = 2;
    public static final int TAHSAS = 3;
    public static final int TER = 4;
    public static final int YEKATIT = 5;
    public static final int MEGABIT = 6;
    public static final int MIAZIA = 7;
    public static final int GENBOT = 8;
    public static final int SENE = 9;
    public static final int HAMLE = 10;
    public static final int NEHASSE = 11;
    public static final int PAGUMEN = 12;
    private static final int JD_EPOCH_OFFSET_AMETE_MIHRET = 1723856;
    private static final int AMETE_MIHRET_DELTA = 5500;
    private static final int AMETE_ALEM = 0;
    private static final int AMETE_MIHRET = 1;
    private static final int AMETE_MIHRET_ERA = 0;
    private static final int AMETE_ALEM_ERA = 1;
    private int eraType;
    
    public EthiopicCalendar() {
        this.eraType = 0;
    }
    
    public EthiopicCalendar(final TimeZone timeZone) {
        super(timeZone);
        this.eraType = 0;
    }
    
    public EthiopicCalendar(final Locale locale) {
        super(locale);
        this.eraType = 0;
    }
    
    public EthiopicCalendar(final ULocale uLocale) {
        super(uLocale);
        this.eraType = 0;
    }
    
    public EthiopicCalendar(final TimeZone timeZone, final Locale locale) {
        super(timeZone, locale);
        this.eraType = 0;
    }
    
    public EthiopicCalendar(final TimeZone timeZone, final ULocale uLocale) {
        super(timeZone, uLocale);
        this.eraType = 0;
    }
    
    public EthiopicCalendar(final int n, final int n2, final int n3) {
        super(n, n2, n3);
        this.eraType = 0;
    }
    
    public EthiopicCalendar(final Date date) {
        super(date);
        this.eraType = 0;
    }
    
    public EthiopicCalendar(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        super(n, n2, n3, n4, n5, n6);
        this.eraType = 0;
    }
    
    @Override
    public String getType() {
        if (this.isAmeteAlemEra()) {
            return "ethiopic-amete-alem";
        }
        return "ethiopic";
    }
    
    public void setAmeteAlemEra(final boolean eraType) {
        this.eraType = (eraType ? 1 : 0);
    }
    
    public boolean isAmeteAlemEra() {
        return this.eraType == 1;
    }
    
    @Override
    @Deprecated
    protected int handleGetExtendedYear() {
        int n;
        if (this.newerField(19, 1) == 19) {
            n = this.internalGet(19, 1);
        }
        else if (this.isAmeteAlemEra()) {
            n = this.internalGet(1, 5501) - 5500;
        }
        else if (this.internalGet(0, 1) == 1) {
            n = this.internalGet(1, 1);
        }
        else {
            n = this.internalGet(1, 1) - 5500;
        }
        return n;
    }
    
    @Override
    @Deprecated
    protected void handleComputeFields(final int n) {
        final int[] array = new int[3];
        CECalendar.jdToCE(n, this.getJDEpochOffset(), array);
        int n2;
        if (this.isAmeteAlemEra()) {
            n2 = array[0] + 5500;
        }
        else if (array[0] > 0) {
            n2 = array[0];
        }
        else {
            n2 = array[0] + 5500;
        }
        this.internalSet(19, array[0]);
        this.internalSet(0, 0);
        this.internalSet(1, n2);
        this.internalSet(2, array[1]);
        this.internalSet(5, array[2]);
        this.internalSet(6, 30 * array[1] + array[2]);
    }
    
    @Override
    @Deprecated
    protected int handleGetLimit(final int n, final int n2) {
        if (this.isAmeteAlemEra() && n == 0) {
            return 0;
        }
        return super.handleGetLimit(n, n2);
    }
    
    @Override
    @Deprecated
    protected int getJDEpochOffset() {
        return 1723856;
    }
    
    public static int EthiopicToJD(final long n, final int n2, final int n3) {
        return CECalendar.ceToJD(n, n2, n3, 1723856);
    }
}
