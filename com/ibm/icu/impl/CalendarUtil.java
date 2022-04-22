package com.ibm.icu.impl;

import com.ibm.icu.util.*;

public class CalendarUtil
{
    private static ICUCache CALTYPE_CACHE;
    private static final String CALKEY = "calendar";
    private static final String DEFCAL = "gregorian";
    
    public static String getCalendarType(final ULocale uLocale) {
        final String keywordValue = uLocale.getKeywordValue("calendar");
        if (keywordValue != null) {
            return keywordValue;
        }
        final String baseName = uLocale.getBaseName();
        final String s = (String)CalendarUtil.CALTYPE_CACHE.get(baseName);
        if (s != null) {
            return s;
        }
        final ULocale canonical = ULocale.createCanonical(uLocale.toString());
        String s2 = canonical.getKeywordValue("calendar");
        if (s2 == null) {
            String s3 = canonical.getCountry();
            if (s3.length() == 0) {
                s3 = ULocale.addLikelySubtags(canonical).getCountry();
            }
            s2 = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("calendarPreferenceData").get(s3).getString(0);
            if (s2 == null) {
                s2 = "gregorian";
            }
        }
        CalendarUtil.CALTYPE_CACHE.put(baseName, s2);
        return s2;
    }
    
    static {
        CalendarUtil.CALTYPE_CACHE = new SimpleCache();
    }
}
