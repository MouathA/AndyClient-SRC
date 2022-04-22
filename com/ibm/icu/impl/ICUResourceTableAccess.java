package com.ibm.icu.impl;

import com.ibm.icu.util.*;

public class ICUResourceTableAccess
{
    public static String getTableString(final String s, final ULocale uLocale, final String s2, final String s3) {
        return getTableString((ICUResourceBundle)UResourceBundle.getBundleInstance(s, uLocale.getBaseName()), s2, null, s3);
    }
    
    public static String getTableString(ICUResourceBundle icuResourceBundle, final String s, final String s2, final String s3) {
        while (!"currency".equals(s2)) {
            final ICUResourceBundle lookup = lookup(icuResourceBundle, s);
            if (lookup == null) {
                return s3;
            }
            ICUResourceBundle lookup2 = lookup;
            if (s2 != null) {
                lookup2 = lookup(lookup, s2);
            }
            if (lookup2 != null) {
                final ICUResourceBundle lookup3 = lookup(lookup2, s3);
                if (lookup3 != null) {
                    final String s4 = lookup3.getString();
                    return (s4 != null && s4.length() > 0) ? s4 : s3;
                }
            }
            if (s2 == null) {
                String s5 = null;
                if (s.equals("Countries")) {
                    s5 = LocaleIDs.getCurrentCountryID(s3);
                }
                else if (s.equals("Languages")) {
                    s5 = LocaleIDs.getCurrentLanguageID(s3);
                }
                final ICUResourceBundle lookup4 = lookup(lookup, s5);
                if (lookup4 != null) {
                    final String s4 = lookup4.getString();
                    return (s4 != null && s4.length() > 0) ? s4 : s3;
                }
            }
            final ICUResourceBundle lookup5 = lookup(lookup, "Fallback");
            if (lookup5 == null) {
                return s3;
            }
            String string = lookup5.getString();
            if (string.length() == 0) {
                string = "root";
            }
            if (string.equals(lookup.getULocale().getName())) {
                return s3;
            }
            icuResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(icuResourceBundle.getBaseName(), string);
        }
        return icuResourceBundle.getWithFallback("Currencies").getWithFallback(s3).getString(1);
    }
    
    private static ICUResourceBundle lookup(final ICUResourceBundle icuResourceBundle, final String s) {
        return ICUResourceBundle.findResourceWithFallback(s, icuResourceBundle, null);
    }
}
