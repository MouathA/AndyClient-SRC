package com.ibm.icu.impl;

import java.lang.ref.*;
import java.util.*;
import com.ibm.icu.util.*;

public final class ZoneMeta
{
    private static final boolean ASSERT = false;
    private static final String ZONEINFORESNAME = "zoneinfo64";
    private static final String kREGIONS = "Regions";
    private static final String kZONES = "Zones";
    private static final String kNAMES = "Names";
    private static final String kGMT_ID = "GMT";
    private static final String kCUSTOM_TZ_PREFIX = "GMT";
    private static final String kWorld = "001";
    private static SoftReference REF_SYSTEM_ZONES;
    private static SoftReference REF_CANONICAL_SYSTEM_ZONES;
    private static SoftReference REF_CANONICAL_SYSTEM_LOCATION_ZONES;
    private static ICUCache CANONICAL_ID_CACHE;
    private static ICUCache REGION_CACHE;
    private static ICUCache SINGLE_COUNTRY_CACHE;
    private static final SystemTimeZoneCache SYSTEM_ZONE_CACHE;
    private static final int kMAX_CUSTOM_HOUR = 23;
    private static final int kMAX_CUSTOM_MIN = 59;
    private static final int kMAX_CUSTOM_SEC = 59;
    private static final CustomTimeZoneCache CUSTOM_ZONE_CACHE;
    static final boolean $assertionsDisabled;
    
    private static synchronized Set getSystemZIDs() {
        Set<Object> unmodifiableSet = null;
        if (ZoneMeta.REF_SYSTEM_ZONES != null) {
            unmodifiableSet = ZoneMeta.REF_SYSTEM_ZONES.get();
        }
        if (unmodifiableSet == null) {
            final TreeSet<String> set = new TreeSet<String>();
            final String[] zoneIDs = getZoneIDs();
            while (0 < zoneIDs.length) {
                final String s = zoneIDs[0];
                if (!s.equals("Etc/Unknown")) {
                    set.add(s);
                }
                int n = 0;
                ++n;
            }
            unmodifiableSet = Collections.unmodifiableSet((Set<?>)set);
            ZoneMeta.REF_SYSTEM_ZONES = new SoftReference(unmodifiableSet);
        }
        return unmodifiableSet;
    }
    
    private static synchronized Set getCanonicalSystemZIDs() {
        Set<Object> unmodifiableSet = null;
        if (ZoneMeta.REF_CANONICAL_SYSTEM_ZONES != null) {
            unmodifiableSet = ZoneMeta.REF_CANONICAL_SYSTEM_ZONES.get();
        }
        if (unmodifiableSet == null) {
            final TreeSet<String> set = new TreeSet<String>();
            final String[] zoneIDs = getZoneIDs();
            while (0 < zoneIDs.length) {
                final String s = zoneIDs[0];
                if (!s.equals("Etc/Unknown")) {
                    if (s.equals(getCanonicalCLDRID(s))) {
                        set.add(s);
                    }
                }
                int n = 0;
                ++n;
            }
            unmodifiableSet = Collections.unmodifiableSet((Set<?>)set);
            ZoneMeta.REF_CANONICAL_SYSTEM_ZONES = new SoftReference(unmodifiableSet);
        }
        return unmodifiableSet;
    }
    
    private static synchronized Set getCanonicalSystemLocationZIDs() {
        Set<Object> unmodifiableSet = null;
        if (ZoneMeta.REF_CANONICAL_SYSTEM_LOCATION_ZONES != null) {
            unmodifiableSet = ZoneMeta.REF_CANONICAL_SYSTEM_LOCATION_ZONES.get();
        }
        if (unmodifiableSet == null) {
            final TreeSet<String> set = new TreeSet<String>();
            final String[] zoneIDs = getZoneIDs();
            while (0 < zoneIDs.length) {
                final String s = zoneIDs[0];
                if (!s.equals("Etc/Unknown")) {
                    if (s.equals(getCanonicalCLDRID(s))) {
                        final String region = getRegion(s);
                        if (region != null && !region.equals("001")) {
                            set.add(s);
                        }
                    }
                }
                int n = 0;
                ++n;
            }
            unmodifiableSet = Collections.unmodifiableSet((Set<?>)set);
            ZoneMeta.REF_CANONICAL_SYSTEM_LOCATION_ZONES = new SoftReference(unmodifiableSet);
        }
        return unmodifiableSet;
    }
    
    public static Set getAvailableIDs(final TimeZone.SystemTimeZoneType systemTimeZoneType, String upperCase, final Integer n) {
        Set set = null;
        switch (systemTimeZoneType) {
            case ANY: {
                set = getSystemZIDs();
                break;
            }
            case CANONICAL: {
                set = getCanonicalSystemZIDs();
                break;
            }
            case CANONICAL_LOCATION: {
                set = getCanonicalSystemLocationZIDs();
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown SystemTimeZoneType");
            }
        }
        if (upperCase == null && n == null) {
            return set;
        }
        if (upperCase != null) {
            upperCase = upperCase.toUpperCase(Locale.ENGLISH);
        }
        final TreeSet<String> set2 = new TreeSet<String>();
        for (final String s : set) {
            if (upperCase != null && !upperCase.equals(getRegion(s))) {
                continue;
            }
            if (n != null) {
                final TimeZone systemTimeZone = getSystemTimeZone(s);
                if (systemTimeZone == null) {
                    continue;
                }
                if (!n.equals(systemTimeZone.getRawOffset())) {
                    continue;
                }
            }
            set2.add(s);
        }
        if (set2.isEmpty()) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet((Set<?>)set2);
    }
    
    public static synchronized int countEquivalentIDs(final String s) {
        final UResourceBundle openOlsonResource = openOlsonResource(null, s);
        if (openOlsonResource != null) {
            final int length = openOlsonResource.get("links").getIntVector().length;
        }
        return 0;
    }
    
    public static synchronized String getEquivalentID(final String s, final int n) {
        final String s2 = "";
        if (n >= 0) {
            final UResourceBundle openOlsonResource = openOlsonResource(null, s);
            if (openOlsonResource != null) {
                final int[] intVector = openOlsonResource.get("links").getIntVector();
                if (n < intVector.length) {
                    final int n2 = intVector[n];
                }
            }
        }
        return s2;
    }
    
    private static synchronized String[] getZoneIDs() {
        if (ZoneMeta.ZONEIDS == null) {
            ZoneMeta.ZONEIDS = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "zoneinfo64", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("Names").getStringArray();
        }
        if (ZoneMeta.ZONEIDS == null) {
            ZoneMeta.ZONEIDS = new String[0];
        }
        return ZoneMeta.ZONEIDS;
    }
    
    private static String getZoneID(final int n) {
        if (n >= 0) {
            final String[] zoneIDs = getZoneIDs();
            if (n < zoneIDs.length) {
                return zoneIDs[n];
            }
        }
        return null;
    }
    
    private static int getZoneIndex(final String s) {
        final String[] zoneIDs = getZoneIDs();
        if (zoneIDs.length > 0) {
            int length = zoneIDs.length;
            while (true) {
                final int n = (0 + length) / 2;
                if (Integer.MAX_VALUE == n) {
                    break;
                }
                final int compareTo = s.compareTo(zoneIDs[n]);
                if (compareTo == 0) {
                    break;
                }
                if (compareTo >= 0) {
                    continue;
                }
                length = n;
            }
        }
        return -1;
    }
    
    public static String getCanonicalCLDRID(final TimeZone timeZone) {
        if (timeZone instanceof OlsonTimeZone) {
            return ((OlsonTimeZone)timeZone).getCanonicalID();
        }
        return getCanonicalCLDRID(timeZone.getID());
    }
    
    public static String getCanonicalCLDRID(String zoneID) {
        String s = (String)ZoneMeta.CANONICAL_ID_CACHE.get(zoneID);
        if (s == null) {
            s = findCLDRCanonicalID(zoneID);
            if (s == null) {
                final int zoneIndex = getZoneIndex(zoneID);
                if (zoneIndex >= 0) {
                    final UResourceBundle value = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "zoneinfo64", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("Zones").get(zoneIndex);
                    if (value.getType() == 7) {
                        zoneID = getZoneID(value.getInt());
                        s = findCLDRCanonicalID(zoneID);
                    }
                    if (s == null) {
                        s = zoneID;
                    }
                }
            }
            if (s != null) {
                ZoneMeta.CANONICAL_ID_CACHE.put(zoneID, s);
            }
        }
        return s;
    }
    
    private static String findCLDRCanonicalID(final String s) {
        final String replace = s.replace('/', ':');
        final UResourceBundle bundleInstance = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "keyTypeData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        bundleInstance.get("typeMap").get("timezone").get(replace);
        String string = s;
        if (string == null) {
            string = bundleInstance.get("typeAlias").get("timezone").getString(replace);
        }
        return string;
    }
    
    public static String getRegion(final String s) {
        String string = (String)ZoneMeta.REGION_CACHE.get(s);
        if (string == null) {
            final int zoneIndex = getZoneIndex(s);
            if (zoneIndex >= 0) {
                final UResourceBundle value = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "zoneinfo64", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("Regions");
                if (zoneIndex < value.getSize()) {
                    string = value.getString(zoneIndex);
                }
                if (string != null) {
                    ZoneMeta.REGION_CACHE.put(s, string);
                }
            }
        }
        return string;
    }
    
    public static String getCanonicalCountry(final String s) {
        String region = getRegion(s);
        if (region != null && region.equals("001")) {
            region = null;
        }
        return region;
    }
    
    public static String getCanonicalCountry(final String s, final Output output) {
        output.value = Boolean.FALSE;
        final String region = getRegion(s);
        if (region != null && region.equals("001")) {
            return null;
        }
        Boolean value = (Boolean)ZoneMeta.SINGLE_COUNTRY_CACHE.get(s);
        if (value == null) {
            final Set availableIDs = TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL_LOCATION, region, null);
            assert availableIDs.size() >= 1;
            value = (availableIDs.size() <= 1);
            ZoneMeta.SINGLE_COUNTRY_CACHE.put(s, value);
        }
        if (value) {
            output.value = Boolean.TRUE;
        }
        else {
            final String string = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "metaZones").get("primaryZones").getString(region);
            if (s.equals(string)) {
                output.value = Boolean.TRUE;
            }
            else {
                final String canonicalCLDRID = getCanonicalCLDRID(s);
                if (canonicalCLDRID != null && canonicalCLDRID.equals(string)) {
                    output.value = Boolean.TRUE;
                }
            }
        }
        return region;
    }
    
    public static UResourceBundle openOlsonResource(UResourceBundle bundleInstance, final String s) {
        UResourceBundle uResourceBundle = null;
        final int zoneIndex = getZoneIndex(s);
        if (zoneIndex >= 0) {
            if (bundleInstance == null) {
                bundleInstance = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "zoneinfo64", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            }
            final UResourceBundle value = bundleInstance.get("Zones");
            UResourceBundle uResourceBundle2 = value.get(zoneIndex);
            if (uResourceBundle2.getType() == 7) {
                uResourceBundle2 = value.get(uResourceBundle2.getInt());
            }
            uResourceBundle = uResourceBundle2;
        }
        return uResourceBundle;
    }
    
    public static TimeZone getSystemTimeZone(final String s) {
        return (TimeZone)ZoneMeta.SYSTEM_ZONE_CACHE.getInstance(s, s);
    }
    
    public static TimeZone getCustomTimeZone(final String s) {
        final int[] array = new int[4];
        if (array != null) {
            return (TimeZone)ZoneMeta.CUSTOM_ZONE_CACHE.getInstance(array[0] * (array[1] | array[2] << 5 | array[3] << 11), array);
        }
        return null;
    }
    
    public static String getCustomID(final String s) {
        final int[] array = new int[4];
        if (array != null) {
            return formatCustomID(array[1], array[2], array[3], array[0] < 0);
        }
        return null;
    }
    
    public static TimeZone getCustomTimeZone(final int n) {
        int n2 = n;
        if (n < 0) {
            n2 = -n;
        }
        final int n3 = n2 / 1000;
        final int n4 = n3 % 60;
        final int n5 = n3 / 60;
        return new SimpleTimeZone(n, formatCustomID(n5 / 60, n5 % 60, n4, true));
    }
    
    static String formatCustomID(final int n, final int n2, final int n3, final boolean b) {
        final StringBuilder sb = new StringBuilder("GMT");
        if (n != 0 || n2 != 0) {
            if (b) {
                sb.append('-');
            }
            else {
                sb.append('+');
            }
            if (n < 10) {
                sb.append('0');
            }
            sb.append(n);
            sb.append(':');
            if (n2 < 10) {
                sb.append('0');
            }
            sb.append(n2);
            if (n3 != 0) {
                sb.append(':');
                if (n3 < 10) {
                    sb.append('0');
                }
                sb.append(n3);
            }
        }
        return sb.toString();
    }
    
    public static String getShortID(final TimeZone timeZone) {
        if (timeZone instanceof OlsonTimeZone) {
            ((OlsonTimeZone)timeZone).getCanonicalID();
        }
        final String canonicalCLDRID = getCanonicalCLDRID(timeZone.getID());
        if (canonicalCLDRID == null) {
            return null;
        }
        return getShortIDFromCanonical(canonicalCLDRID);
    }
    
    public static String getShortID(final String s) {
        final String canonicalCLDRID = getCanonicalCLDRID(s);
        if (canonicalCLDRID == null) {
            return null;
        }
        return getShortIDFromCanonical(canonicalCLDRID);
    }
    
    private static String getShortIDFromCanonical(final String s) {
        return UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "keyTypeData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("typeMap").get("timezone").getString(s.replace('/', ':'));
    }
    
    static {
        $assertionsDisabled = !ZoneMeta.class.desiredAssertionStatus();
        ZoneMeta.ZONEIDS = null;
        ZoneMeta.CANONICAL_ID_CACHE = new SimpleCache();
        ZoneMeta.REGION_CACHE = new SimpleCache();
        ZoneMeta.SINGLE_COUNTRY_CACHE = new SimpleCache();
        SYSTEM_ZONE_CACHE = new SystemTimeZoneCache(null);
        CUSTOM_ZONE_CACHE = new CustomTimeZoneCache(null);
    }
    
    private static class CustomTimeZoneCache extends SoftCache
    {
        static final boolean $assertionsDisabled;
        
        private CustomTimeZoneCache() {
        }
        
        protected SimpleTimeZone createInstance(final Integer n, final int[] array) {
            assert array.length == 4;
            assert array[0] == -1;
            assert array[1] >= 0 && array[1] <= 23;
            assert array[2] >= 0 && array[2] <= 59;
            assert array[3] >= 0 && array[3] <= 59;
            final SimpleTimeZone simpleTimeZone = new SimpleTimeZone(array[0] * ((array[1] * 60 + array[2]) * 60 + array[3]) * 1000, ZoneMeta.formatCustomID(array[1], array[2], array[3], array[0] < 0));
            simpleTimeZone.freeze();
            return simpleTimeZone;
        }
        
        @Override
        protected Object createInstance(final Object o, final Object o2) {
            return this.createInstance((Integer)o, (int[])o2);
        }
        
        CustomTimeZoneCache(final ZoneMeta$1 object) {
            this();
        }
        
        static {
            $assertionsDisabled = !ZoneMeta.class.desiredAssertionStatus();
        }
    }
    
    private static class SystemTimeZoneCache extends SoftCache
    {
        private SystemTimeZoneCache() {
        }
        
        protected OlsonTimeZone createInstance(final String s, final String s2) {
            OlsonTimeZone olsonTimeZone = null;
            final UResourceBundle bundleInstance = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "zoneinfo64", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            final UResourceBundle openOlsonResource = ZoneMeta.openOlsonResource(bundleInstance, s2);
            if (openOlsonResource != null) {
                olsonTimeZone = new OlsonTimeZone(bundleInstance, openOlsonResource, s2);
                olsonTimeZone.freeze();
            }
            return olsonTimeZone;
        }
        
        @Override
        protected Object createInstance(final Object o, final Object o2) {
            return this.createInstance((String)o, (String)o2);
        }
        
        SystemTimeZoneCache(final ZoneMeta$1 object) {
            this();
        }
    }
}
