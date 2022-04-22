package com.ibm.icu.impl;

import java.util.*;
import com.ibm.icu.util.*;

public class CalendarData
{
    private ICUResourceBundle fBundle;
    private String fMainType;
    private String fFallbackType;
    
    public CalendarData(final ULocale uLocale, final String s) {
        this((ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", uLocale), s);
    }
    
    public CalendarData(final ICUResourceBundle fBundle, final String fMainType) {
        this.fBundle = fBundle;
        if (fMainType == null || fMainType.equals("") || fMainType.equals("gregorian")) {
            this.fMainType = "gregorian";
            this.fFallbackType = null;
        }
        else {
            this.fMainType = fMainType;
            this.fFallbackType = "gregorian";
        }
    }
    
    public ICUResourceBundle get(final String s) {
        return this.fBundle.getWithFallback("calendar/" + this.fMainType + "/" + s);
    }
    
    public ICUResourceBundle get(final String s, final String s2) {
        return this.fBundle.getWithFallback("calendar/" + this.fMainType + "/" + s + "/format/" + s2);
    }
    
    public ICUResourceBundle get(final String s, final String s2, final String s3) {
        return this.fBundle.getWithFallback("calendar/" + this.fMainType + "/" + s + "/" + s2 + "/" + s3);
    }
    
    public ICUResourceBundle get(final String s, final String s2, final String s3, final String s4) {
        return this.fBundle.getWithFallback("calendar/" + this.fMainType + "/" + s + "/" + s2 + "/" + s3 + "/" + s4);
    }
    
    public String[] getStringArray(final String s) {
        return this.get(s).getStringArray();
    }
    
    public String[] getStringArray(final String s, final String s2) {
        return this.get(s, s2).getStringArray();
    }
    
    public String[] getStringArray(final String s, final String s2, final String s3) {
        return this.get(s, s2, s3).getStringArray();
    }
    
    public String[] getEras(final String s) {
        return this.get("eras/" + s).getStringArray();
    }
    
    public String[] getDateTimePatterns() {
        final ICUResourceBundle value = this.get("DateTimePatterns");
        final ArrayList<String> list = new ArrayList<String>();
        final UResourceBundleIterator iterator = value.getIterator();
        while (iterator.hasNext()) {
            final UResourceBundle next = iterator.next();
            switch (next.getType()) {
                case 0: {
                    list.add(next.getString());
                    continue;
                }
                case 8: {
                    list.add(next.getStringArray()[0]);
                    continue;
                }
            }
        }
        return list.toArray(new String[list.size()]);
    }
    
    public String[] getOverrides() {
        final ICUResourceBundle value = this.get("DateTimePatterns");
        final ArrayList<String> list = new ArrayList<String>();
        final UResourceBundleIterator iterator = value.getIterator();
        while (iterator.hasNext()) {
            final UResourceBundle next = iterator.next();
            switch (next.getType()) {
                case 0: {
                    list.add(null);
                    continue;
                }
                case 8: {
                    list.add(next.getStringArray()[1]);
                    continue;
                }
            }
        }
        return list.toArray(new String[list.size()]);
    }
    
    public ULocale getULocale() {
        return this.fBundle.getULocale();
    }
}
