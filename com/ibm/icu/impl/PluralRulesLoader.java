package com.ibm.icu.impl;

import com.ibm.icu.text.*;
import com.ibm.icu.util.*;
import java.util.*;

public class PluralRulesLoader
{
    private final Map rulesIdToRules;
    private Map localeIdToCardinalRulesId;
    private Map localeIdToOrdinalRulesId;
    private Map rulesIdToEquivalentULocale;
    public static final PluralRulesLoader loader;
    
    private PluralRulesLoader() {
        this.rulesIdToRules = new HashMap();
    }
    
    public ULocale[] getAvailableULocales() {
        final Set keySet = this.getLocaleIdToRulesIdMap(PluralRules.PluralType.CARDINAL).keySet();
        final ULocale[] array = new ULocale[keySet.size()];
        final Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            final ULocale[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = ULocale.createCanonical(iterator.next());
        }
        return array;
    }
    
    public ULocale getFunctionalEquivalent(final ULocale uLocale, final boolean[] array) {
        if (array != null && array.length > 0) {
            array[0] = this.getLocaleIdToRulesIdMap(PluralRules.PluralType.CARDINAL).containsKey(ULocale.canonicalize(uLocale.getBaseName()));
        }
        final String rulesIdForLocale = this.getRulesIdForLocale(uLocale, PluralRules.PluralType.CARDINAL);
        if (rulesIdForLocale == null || rulesIdForLocale.trim().length() == 0) {
            return ULocale.ROOT;
        }
        final ULocale uLocale2 = this.getRulesIdToEquivalentULocaleMap().get(rulesIdForLocale);
        if (uLocale2 == null) {
            return ULocale.ROOT;
        }
        return uLocale2;
    }
    
    private Map getLocaleIdToRulesIdMap(final PluralRules.PluralType pluralType) {
        this.checkBuildRulesIdMaps();
        return (pluralType == PluralRules.PluralType.CARDINAL) ? this.localeIdToCardinalRulesId : this.localeIdToOrdinalRulesId;
    }
    
    private Map getRulesIdToEquivalentULocaleMap() {
        this.checkBuildRulesIdMaps();
        return this.rulesIdToEquivalentULocale;
    }
    
    private void checkBuildRulesIdMaps() {
        // monitorenter(this)
        final boolean b = this.localeIdToCardinalRulesId != null;
        // monitorexit(this)
        if (!b) {
            final UResourceBundle pluralBundle = this.getPluralBundle();
            final UResourceBundle value = pluralBundle.get("locales");
            final TreeMap<String, String> localeIdToCardinalRulesId = new TreeMap<String, String>();
            final HashMap<String, ULocale> rulesIdToEquivalentULocale = new HashMap<String, ULocale>();
            int n = 0;
            while (0 < value.getSize()) {
                final UResourceBundle value2 = value.get(0);
                final String key = value2.getKey();
                final String intern = value2.getString().intern();
                localeIdToCardinalRulesId.put(key, intern);
                if (!rulesIdToEquivalentULocale.containsKey(intern)) {
                    rulesIdToEquivalentULocale.put(intern, new ULocale(key));
                }
                ++n;
            }
            final UResourceBundle value3 = pluralBundle.get("locales_ordinals");
            final TreeMap<String, String> localeIdToOrdinalRulesId = new TreeMap<String, String>();
            while (0 < value3.getSize()) {
                final UResourceBundle value4 = value3.get(0);
                localeIdToOrdinalRulesId.put(value4.getKey(), value4.getString().intern());
                ++n;
            }
            // monitorenter(this)
            if (this.localeIdToCardinalRulesId == null) {
                this.localeIdToCardinalRulesId = localeIdToCardinalRulesId;
                this.localeIdToOrdinalRulesId = localeIdToOrdinalRulesId;
                this.rulesIdToEquivalentULocale = rulesIdToEquivalentULocale;
            }
        }
        // monitorexit(this)
    }
    
    public String getRulesIdForLocale(final ULocale uLocale, final PluralRules.PluralType pluralType) {
        final Map localeIdToRulesIdMap = this.getLocaleIdToRulesIdMap(pluralType);
        String s2;
        int lastIndex;
        for (String s = ULocale.canonicalize(uLocale.getBaseName()); null == (s2 = localeIdToRulesIdMap.get(s)); s = s.substring(0, lastIndex)) {
            lastIndex = s.lastIndexOf("_");
            if (lastIndex == -1) {
                break;
            }
        }
        return s2;
    }
    
    public PluralRules getRulesForRulesId(final String s) {
        PluralRules description = null;
        // monitorenter(rulesIdToRules = this.rulesIdToRules)
        final boolean containsKey = this.rulesIdToRules.containsKey(s);
        if (containsKey) {
            description = this.rulesIdToRules.get(s);
        }
        // monitorexit(rulesIdToRules)
        if (!containsKey) {
            final UResourceBundle value = this.getPluralBundle().get("rules").get(s);
            final StringBuilder sb = new StringBuilder();
            while (0 < value.getSize()) {
                final UResourceBundle value2 = value.get(0);
                if (0 > 0) {
                    sb.append("; ");
                }
                sb.append(value2.getKey());
                sb.append(": ");
                sb.append(value2.getString());
                int n = 0;
                ++n;
            }
            description = PluralRules.parseDescription(sb.toString());
            // monitorenter(rulesIdToRules2 = this.rulesIdToRules)
            if (this.rulesIdToRules.containsKey(s)) {
                description = this.rulesIdToRules.get(s);
            }
            else {
                this.rulesIdToRules.put(s, description);
            }
        }
        // monitorexit(rulesIdToRules2)
        return description;
    }
    
    public UResourceBundle getPluralBundle() throws MissingResourceException {
        return ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "plurals", ICUResourceBundle.ICU_DATA_CLASS_LOADER, true);
    }
    
    public PluralRules forLocale(final ULocale uLocale, final PluralRules.PluralType pluralType) {
        final String rulesIdForLocale = this.getRulesIdForLocale(uLocale, pluralType);
        if (rulesIdForLocale == null || rulesIdForLocale.trim().length() == 0) {
            return PluralRules.DEFAULT;
        }
        PluralRules pluralRules = this.getRulesForRulesId(rulesIdForLocale);
        if (pluralRules == null) {
            pluralRules = PluralRules.DEFAULT;
        }
        return pluralRules;
    }
    
    static {
        loader = new PluralRulesLoader();
    }
}
