package com.ibm.icu.util;

import java.io.*;
import java.util.logging.*;
import com.ibm.icu.text.*;
import java.util.*;
import com.ibm.icu.impl.*;

public abstract class TimeZone implements Serializable, Cloneable, Freezable
{
    private static final Logger LOGGER;
    private static final long serialVersionUID = -744942128318337471L;
    public static final int TIMEZONE_ICU = 0;
    public static final int TIMEZONE_JDK = 1;
    public static final int SHORT = 0;
    public static final int LONG = 1;
    public static final int SHORT_GENERIC = 2;
    public static final int LONG_GENERIC = 3;
    public static final int SHORT_GMT = 4;
    public static final int LONG_GMT = 5;
    public static final int SHORT_COMMONLY_USED = 6;
    public static final int GENERIC_LOCATION = 7;
    public static final String UNKNOWN_ZONE_ID = "Etc/Unknown";
    static final String GMT_ZONE_ID = "Etc/GMT";
    public static final TimeZone UNKNOWN_ZONE;
    public static final TimeZone GMT_ZONE;
    private String ID;
    private static TimeZone defaultZone;
    private static String TZDATA_VERSION;
    private static final String TZIMPL_CONFIG_KEY = "com.ibm.icu.util.TimeZone.DefaultTimeZoneType";
    private static final String TZIMPL_CONFIG_ICU = "ICU";
    private static final String TZIMPL_CONFIG_JDK = "JDK";
    static final boolean $assertionsDisabled;
    
    public TimeZone() {
    }
    
    @Deprecated
    protected TimeZone(final String id) {
        if (id == null) {
            throw new NullPointerException();
        }
        this.ID = id;
    }
    
    public abstract int getOffset(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    public int getOffset(final long n) {
        final int[] array = new int[2];
        this.getOffset(n, false, array);
        return array[0] + array[1];
    }
    
    public void getOffset(long n, final boolean b, final int[] array) {
        array[0] = this.getRawOffset();
        if (!b) {
            n += array[0];
        }
        final int[] array2 = new int[6];
        while (true) {
            Grego.timeToFields(n, array2);
            array[1] = this.getOffset(1, array2[0], array2[1], array2[2], array2[3], array2[5]) - array[0];
            if (false || !b || array[1] == 0) {
                break;
            }
            n -= array[1];
            int n2 = 0;
            ++n2;
        }
    }
    
    public abstract void setRawOffset(final int p0);
    
    public abstract int getRawOffset();
    
    public String getID() {
        return this.ID;
    }
    
    public void setID(final String id) {
        if (id == null) {
            throw new NullPointerException();
        }
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen TimeZone instance.");
        }
        this.ID = id;
    }
    
    public final String getDisplayName() {
        return this._getDisplayName(3, false, ULocale.getDefault(ULocale.Category.DISPLAY));
    }
    
    public final String getDisplayName(final Locale locale) {
        return this._getDisplayName(3, false, ULocale.forLocale(locale));
    }
    
    public final String getDisplayName(final ULocale uLocale) {
        return this._getDisplayName(3, false, uLocale);
    }
    
    public final String getDisplayName(final boolean b, final int n) {
        return this.getDisplayName(b, n, ULocale.getDefault(ULocale.Category.DISPLAY));
    }
    
    public String getDisplayName(final boolean b, final int n, final Locale locale) {
        return this.getDisplayName(b, n, ULocale.forLocale(locale));
    }
    
    public String getDisplayName(final boolean b, final int n, final ULocale uLocale) {
        if (n < 0 || n > 7) {
            throw new IllegalArgumentException("Illegal style: " + n);
        }
        return this._getDisplayName(n, b, uLocale);
    }
    
    private String _getDisplayName(final int n, final boolean b, final ULocale uLocale) {
        if (uLocale == null) {
            throw new NullPointerException("locale is null");
        }
        String s = null;
        if (n == 7 || n == 3 || n == 2) {
            final TimeZoneFormat instance = TimeZoneFormat.getInstance(uLocale);
            final long currentTimeMillis = System.currentTimeMillis();
            final Output output = new Output(TimeZoneFormat.TimeType.UNKNOWN);
            switch (n) {
                case 7: {
                    s = instance.format(TimeZoneFormat.Style.GENERIC_LOCATION, this, currentTimeMillis, output);
                    break;
                }
                case 3: {
                    s = instance.format(TimeZoneFormat.Style.GENERIC_LONG, this, currentTimeMillis, output);
                    break;
                }
                case 2: {
                    s = instance.format(TimeZoneFormat.Style.GENERIC_SHORT, this, currentTimeMillis, output);
                    break;
                }
            }
            if ((b && output.value == TimeZoneFormat.TimeType.STANDARD) || (!b && output.value == TimeZoneFormat.TimeType.DAYLIGHT)) {
                final int n2 = b ? (this.getRawOffset() + this.getDSTSavings()) : this.getRawOffset();
                s = ((n == 2) ? instance.formatOffsetShortLocalizedGMT(n2) : instance.formatOffsetLocalizedGMT(n2));
            }
        }
        else if (n == 5 || n == 4) {
            final TimeZoneFormat instance2 = TimeZoneFormat.getInstance(uLocale);
            final int n3 = (b && this.useDaylightTime()) ? (this.getRawOffset() + this.getDSTSavings()) : this.getRawOffset();
            switch (n) {
                case 5: {
                    s = instance2.formatOffsetLocalizedGMT(n3);
                    break;
                }
                case 4: {
                    s = instance2.formatOffsetISO8601Basic(n3, false, false, false);
                    break;
                }
            }
        }
        else {
            assert n == 6;
            final long currentTimeMillis2 = System.currentTimeMillis();
            final TimeZoneNames instance3 = TimeZoneNames.getInstance(uLocale);
            TimeZoneNames.NameType nameType = null;
            switch (n) {
                case 1: {
                    nameType = (b ? TimeZoneNames.NameType.LONG_DAYLIGHT : TimeZoneNames.NameType.LONG_STANDARD);
                    break;
                }
                case 0:
                case 6: {
                    nameType = (b ? TimeZoneNames.NameType.SHORT_DAYLIGHT : TimeZoneNames.NameType.SHORT_STANDARD);
                    break;
                }
            }
            s = instance3.getDisplayName(ZoneMeta.getCanonicalCLDRID(this), nameType, currentTimeMillis2);
            if (s == null) {
                final TimeZoneFormat instance4 = TimeZoneFormat.getInstance(uLocale);
                final int n4 = (b && this.useDaylightTime()) ? (this.getRawOffset() + this.getDSTSavings()) : this.getRawOffset();
                s = ((n == 1) ? instance4.formatOffsetLocalizedGMT(n4) : instance4.formatOffsetShortLocalizedGMT(n4));
            }
        }
        assert s != null;
        return s;
    }
    
    public int getDSTSavings() {
        if (this.useDaylightTime()) {
            return 3600000;
        }
        return 0;
    }
    
    public abstract boolean useDaylightTime();
    
    public boolean observesDaylightTime() {
        return this.useDaylightTime() || this.inDaylightTime(new Date());
    }
    
    public abstract boolean inDaylightTime(final Date p0);
    
    public static TimeZone getTimeZone(final String s) {
        return getTimeZone(s, 1, false);
    }
    
    public static TimeZone getFrozenTimeZone(final String s) {
        return getTimeZone(s, 1, true);
    }
    
    public static TimeZone getTimeZone(final String s, final int n) {
        return getTimeZone(s, n, false);
    }
    
    private static synchronized TimeZone getTimeZone(final String s, final int n, final boolean b) {
        TimeZone timeZone;
        if (n == 1) {
            timeZone = JavaTimeZone.createTimeZone(s);
            if (timeZone != null) {
                return b ? timeZone.freeze() : timeZone;
            }
        }
        else {
            if (s == null) {
                throw new NullPointerException();
            }
            timeZone = ZoneMeta.getSystemTimeZone(s);
        }
        if (timeZone == null) {
            timeZone = ZoneMeta.getCustomTimeZone(s);
        }
        if (timeZone == null) {
            TimeZone.LOGGER.fine("\"" + s + "\" is a bogus id so timezone is falling back to Etc/Unknown(GMT).");
            timeZone = TimeZone.UNKNOWN_ZONE;
        }
        return b ? timeZone : timeZone.cloneAsThawed();
    }
    
    public static synchronized void setDefaultTimeZoneType(final int tz_IMPL) {
        if (tz_IMPL != 0 && tz_IMPL != 1) {
            throw new IllegalArgumentException("Invalid timezone type");
        }
        TimeZone.TZ_IMPL = tz_IMPL;
    }
    
    public static int getDefaultTimeZoneType() {
        return 1;
    }
    
    public static Set getAvailableIDs(final SystemTimeZoneType systemTimeZoneType, final String s, final Integer n) {
        return ZoneMeta.getAvailableIDs(systemTimeZoneType, s, n);
    }
    
    public static String[] getAvailableIDs(final int n) {
        return getAvailableIDs(SystemTimeZoneType.ANY, null, n).toArray(new String[0]);
    }
    
    public static String[] getAvailableIDs(final String s) {
        return getAvailableIDs(SystemTimeZoneType.ANY, s, null).toArray(new String[0]);
    }
    
    public static String[] getAvailableIDs() {
        return getAvailableIDs(SystemTimeZoneType.ANY, null, null).toArray(new String[0]);
    }
    
    public static int countEquivalentIDs(final String s) {
        return ZoneMeta.countEquivalentIDs(s);
    }
    
    public static String getEquivalentID(final String s, final int n) {
        return ZoneMeta.getEquivalentID(s, n);
    }
    
    public static synchronized TimeZone getDefault() {
        if (TimeZone.defaultZone == null) {
            if (true == true) {
                TimeZone.defaultZone = new JavaTimeZone();
            }
            else {
                TimeZone.defaultZone = getFrozenTimeZone(java.util.TimeZone.getDefault().getID());
            }
        }
        return TimeZone.defaultZone.cloneAsThawed();
    }
    
    public static synchronized void setDefault(final TimeZone defaultZone) {
        TimeZone.defaultZone = defaultZone;
        java.util.TimeZone default1 = null;
        if (TimeZone.defaultZone instanceof JavaTimeZone) {
            default1 = ((JavaTimeZone)TimeZone.defaultZone).unwrap();
        }
        else if (defaultZone != null) {
            if (defaultZone instanceof OlsonTimeZone) {
                final String id = defaultZone.getID();
                default1 = java.util.TimeZone.getTimeZone(id);
                if (!id.equals(default1.getID())) {
                    default1 = null;
                }
            }
            if (default1 == null) {
                default1 = TimeZoneAdapter.wrap(defaultZone);
            }
        }
        java.util.TimeZone.setDefault(default1);
    }
    
    public boolean hasSameRules(final TimeZone timeZone) {
        return timeZone != null && this.getRawOffset() == timeZone.getRawOffset() && this.useDaylightTime() == timeZone.useDaylightTime();
    }
    
    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && this.ID.equals(((TimeZone)o).ID));
    }
    
    @Override
    public int hashCode() {
        return this.ID.hashCode();
    }
    
    public static synchronized String getTZDataVersion() {
        if (TimeZone.TZDATA_VERSION == null) {
            TimeZone.TZDATA_VERSION = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "zoneinfo64").getString("TZVersion");
        }
        return TimeZone.TZDATA_VERSION;
    }
    
    public static String getCanonicalID(final String s) {
        return getCanonicalID(s, null);
    }
    
    public static String getCanonicalID(final String s, final boolean[] array) {
        String s2 = null;
        if (s != null && s.length() != 0) {
            if (s.equals("Etc/Unknown")) {
                s2 = "Etc/Unknown";
            }
            else {
                s2 = ZoneMeta.getCanonicalCLDRID(s);
                if (s2 == null) {
                    s2 = ZoneMeta.getCustomID(s);
                }
            }
        }
        if (array != null) {
            array[0] = true;
        }
        return s2;
    }
    
    public static String getRegion(final String s) {
        String region = null;
        if (!s.equals("Etc/Unknown")) {
            region = ZoneMeta.getRegion(s);
        }
        if (region == null) {
            throw new IllegalArgumentException("Unknown system zone id: " + s);
        }
        return region;
    }
    
    public boolean isFrozen() {
        return false;
    }
    
    public TimeZone freeze() {
        throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
    }
    
    public TimeZone cloneAsThawed() {
        return (TimeZone)super.clone();
    }
    
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }
    
    public Object freeze() {
        return this.freeze();
    }
    
    static {
        $assertionsDisabled = !TimeZone.class.desiredAssertionStatus();
        LOGGER = Logger.getLogger("com.ibm.icu.util.TimeZone");
        UNKNOWN_ZONE = new SimpleTimeZone(0, "Etc/Unknown").freeze();
        GMT_ZONE = new SimpleTimeZone(0, "Etc/GMT").freeze();
        TimeZone.defaultZone = null;
        TimeZone.TZDATA_VERSION = null;
        ICUConfig.get("com.ibm.icu.util.TimeZone.DefaultTimeZoneType", "ICU").equalsIgnoreCase("JDK");
    }
    
    public enum SystemTimeZoneType
    {
        ANY("ANY", 0), 
        CANONICAL("CANONICAL", 1), 
        CANONICAL_LOCATION("CANONICAL_LOCATION", 2);
        
        private static final SystemTimeZoneType[] $VALUES;
        
        private SystemTimeZoneType(final String s, final int n) {
        }
        
        static {
            $VALUES = new SystemTimeZoneType[] { SystemTimeZoneType.ANY, SystemTimeZoneType.CANONICAL, SystemTimeZoneType.CANONICAL_LOCATION };
        }
    }
}
