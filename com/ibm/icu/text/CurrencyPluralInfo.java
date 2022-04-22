package com.ibm.icu.text;

import java.io.*;
import com.ibm.icu.util.*;
import java.util.*;
import com.ibm.icu.impl.*;

public class CurrencyPluralInfo implements Cloneable, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final char[] tripleCurrencySign;
    private static final String tripleCurrencyStr;
    private static final char[] defaultCurrencyPluralPatternChar;
    private static final String defaultCurrencyPluralPattern;
    private Map pluralCountToCurrencyUnitPattern;
    private PluralRules pluralRules;
    private ULocale ulocale;
    static final boolean $assertionsDisabled;
    
    public CurrencyPluralInfo() {
        this.pluralCountToCurrencyUnitPattern = null;
        this.pluralRules = null;
        this.ulocale = null;
        this.initialize(ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public CurrencyPluralInfo(final Locale locale) {
        this.pluralCountToCurrencyUnitPattern = null;
        this.pluralRules = null;
        this.ulocale = null;
        this.initialize(ULocale.forLocale(locale));
    }
    
    public CurrencyPluralInfo(final ULocale uLocale) {
        this.pluralCountToCurrencyUnitPattern = null;
        this.pluralRules = null;
        this.ulocale = null;
        this.initialize(uLocale);
    }
    
    public static CurrencyPluralInfo getInstance() {
        return new CurrencyPluralInfo();
    }
    
    public static CurrencyPluralInfo getInstance(final Locale locale) {
        return new CurrencyPluralInfo(locale);
    }
    
    public static CurrencyPluralInfo getInstance(final ULocale uLocale) {
        return new CurrencyPluralInfo(uLocale);
    }
    
    public PluralRules getPluralRules() {
        return this.pluralRules;
    }
    
    public String getCurrencyPluralPattern(final String s) {
        String defaultCurrencyPluralPattern = this.pluralCountToCurrencyUnitPattern.get(s);
        if (defaultCurrencyPluralPattern == null) {
            if (!s.equals("other")) {
                defaultCurrencyPluralPattern = this.pluralCountToCurrencyUnitPattern.get("other");
            }
            if (defaultCurrencyPluralPattern == null) {
                defaultCurrencyPluralPattern = CurrencyPluralInfo.defaultCurrencyPluralPattern;
            }
        }
        return defaultCurrencyPluralPattern;
    }
    
    public ULocale getLocale() {
        return this.ulocale;
    }
    
    public void setPluralRules(final String s) {
        this.pluralRules = PluralRules.createRules(s);
    }
    
    public void setCurrencyPluralPattern(final String s, final String s2) {
        this.pluralCountToCurrencyUnitPattern.put(s, s2);
    }
    
    public void setLocale(final ULocale ulocale) {
        this.initialize(this.ulocale = ulocale);
    }
    
    public Object clone() {
        final CurrencyPluralInfo currencyPluralInfo = (CurrencyPluralInfo)super.clone();
        currencyPluralInfo.ulocale = (ULocale)this.ulocale.clone();
        currencyPluralInfo.pluralCountToCurrencyUnitPattern = new HashMap();
        for (final String s : this.pluralCountToCurrencyUnitPattern.keySet()) {
            currencyPluralInfo.pluralCountToCurrencyUnitPattern.put(s, this.pluralCountToCurrencyUnitPattern.get(s));
        }
        return currencyPluralInfo;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof CurrencyPluralInfo) {
            final CurrencyPluralInfo currencyPluralInfo = (CurrencyPluralInfo)o;
            return this.pluralRules.equals(currencyPluralInfo.pluralRules) && this.pluralCountToCurrencyUnitPattern.equals(currencyPluralInfo.pluralCountToCurrencyUnitPattern);
        }
        return false;
    }
    
    @Override
    @Deprecated
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    String select(final double n) {
        return this.pluralRules.select(n);
    }
    
    Iterator pluralPatternIterator() {
        return this.pluralCountToCurrencyUnitPattern.keySet().iterator();
    }
    
    private void initialize(final ULocale ulocale) {
        this.ulocale = ulocale;
        this.pluralRules = PluralRules.forLocale(ulocale);
        this.setupCurrencyPluralPattern(ulocale);
    }
    
    private void setupCurrencyPluralPattern(final ULocale uLocale) {
        this.pluralCountToCurrencyUnitPattern = new HashMap();
        String s = NumberFormat.getPattern(uLocale, 0);
        final int index = s.indexOf(";");
        CharSequence substring = null;
        if (index != -1) {
            substring = s.substring(index + 1);
            s = s.substring(0, index);
        }
        for (final Map.Entry<String, V> entry : CurrencyData.provider.getInstance(uLocale, true).getUnitPatterns().entrySet()) {
            final String s2 = entry.getKey();
            final String s3 = (String)entry.getValue();
            String s4 = s3.replace("{0}", s).replace("{1}", CurrencyPluralInfo.tripleCurrencyStr);
            if (index != -1) {
                final String replace = s3.replace("{0}", substring).replace("{1}", CurrencyPluralInfo.tripleCurrencyStr);
                final StringBuilder sb = new StringBuilder(s4);
                sb.append(";");
                sb.append(replace);
                s4 = sb.toString();
            }
            this.pluralCountToCurrencyUnitPattern.put(s2, s4);
        }
    }
    
    static {
        $assertionsDisabled = !CurrencyPluralInfo.class.desiredAssertionStatus();
        tripleCurrencySign = new char[] { '¤', '¤', '¤' };
        tripleCurrencyStr = new String(CurrencyPluralInfo.tripleCurrencySign);
        defaultCurrencyPluralPatternChar = new char[] { '\0', '.', '#', '#', ' ', '¤', '¤', '¤' };
        defaultCurrencyPluralPattern = new String(CurrencyPluralInfo.defaultCurrencyPluralPatternChar);
    }
}
