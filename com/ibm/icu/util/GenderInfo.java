package com.ibm.icu.util;

import java.util.*;
import com.ibm.icu.impl.*;

public class GenderInfo
{
    private final ListGenderStyle style;
    private static GenderInfo neutral;
    private static Cache genderInfoCache;
    
    public static GenderInfo getInstance(final ULocale uLocale) {
        return GenderInfo.genderInfoCache.get(uLocale);
    }
    
    public static GenderInfo getInstance(final Locale locale) {
        return getInstance(ULocale.forLocale(locale));
    }
    
    public Gender getListGender(final Gender... array) {
        return this.getListGender(Arrays.asList(array));
    }
    
    public Gender getListGender(final List list) {
        if (list.size() == 0) {
            return Gender.OTHER;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        switch (this.style) {
            case NEUTRAL: {
                return Gender.OTHER;
            }
            case MIXED_NEUTRAL: {
                final Iterator<Gender> iterator = list.iterator();
                while (iterator.hasNext()) {
                    switch (iterator.next()) {
                        case FEMALE: {
                            return Gender.OTHER;
                        }
                        case MALE: {
                            return Gender.OTHER;
                        }
                        case OTHER: {
                            return Gender.OTHER;
                        }
                        default: {
                            continue;
                        }
                    }
                }
                return Gender.MALE;
            }
            case MALE_TAINTS: {
                final Iterator<Gender> iterator2 = list.iterator();
                while (iterator2.hasNext()) {
                    if (iterator2.next() != Gender.FEMALE) {
                        return Gender.MALE;
                    }
                }
                return Gender.FEMALE;
            }
            default: {
                return Gender.OTHER;
            }
        }
    }
    
    public GenderInfo(final ListGenderStyle style) {
        this.style = style;
    }
    
    static GenderInfo access$000() {
        return GenderInfo.neutral;
    }
    
    static {
        GenderInfo.neutral = new GenderInfo(ListGenderStyle.NEUTRAL);
        GenderInfo.genderInfoCache = new Cache(null);
    }
    
    public enum ListGenderStyle
    {
        NEUTRAL("NEUTRAL", 0), 
        MIXED_NEUTRAL("MIXED_NEUTRAL", 1), 
        MALE_TAINTS("MALE_TAINTS", 2);
        
        private static Map fromNameMap;
        private static final ListGenderStyle[] $VALUES;
        
        private ListGenderStyle(final String s, final int n) {
        }
        
        public static ListGenderStyle fromName(final String s) {
            final ListGenderStyle listGenderStyle = ListGenderStyle.fromNameMap.get(s);
            if (listGenderStyle == null) {
                throw new IllegalArgumentException("Unknown gender style name: " + s);
            }
            return listGenderStyle;
        }
        
        static {
            $VALUES = new ListGenderStyle[] { ListGenderStyle.NEUTRAL, ListGenderStyle.MIXED_NEUTRAL, ListGenderStyle.MALE_TAINTS };
            (ListGenderStyle.fromNameMap = new HashMap(3)).put("neutral", ListGenderStyle.NEUTRAL);
            ListGenderStyle.fromNameMap.put("maleTaints", ListGenderStyle.MALE_TAINTS);
            ListGenderStyle.fromNameMap.put("mixedNeutral", ListGenderStyle.MIXED_NEUTRAL);
        }
    }
    
    public enum Gender
    {
        MALE("MALE", 0), 
        FEMALE("FEMALE", 1), 
        OTHER("OTHER", 2);
        
        private static final Gender[] $VALUES;
        
        private Gender(final String s, final int n) {
        }
        
        static {
            $VALUES = new Gender[] { Gender.MALE, Gender.FEMALE, Gender.OTHER };
        }
    }
    
    private static class Cache
    {
        private final ICUCache cache;
        
        private Cache() {
            this.cache = new SimpleCache();
        }
        
        public GenderInfo get(final ULocale uLocale) {
            GenderInfo load = (GenderInfo)this.cache.get(uLocale);
            if (load == null) {
                load = load(uLocale);
                if (load == null) {
                    final ULocale fallback = uLocale.getFallback();
                    load = ((fallback == null) ? GenderInfo.access$000() : this.get(fallback));
                }
                this.cache.put(uLocale, load);
            }
            return load;
        }
        
        private static GenderInfo load(final ULocale uLocale) {
            return new GenderInfo(ListGenderStyle.fromName(UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "genderList", ICUResourceBundle.ICU_DATA_CLASS_LOADER, true).get("genderList").getString(uLocale.toString())));
        }
        
        Cache(final GenderInfo$1 object) {
            this();
        }
    }
}
