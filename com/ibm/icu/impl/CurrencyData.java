package com.ibm.icu.impl;

import java.util.*;
import com.ibm.icu.util.*;
import com.ibm.icu.text.*;

public class CurrencyData
{
    public static final CurrencyDisplayInfoProvider provider;
    
    static {
        provider = (CurrencyDisplayInfoProvider)Class.forName("com.ibm.icu.impl.ICUCurrencyDisplayInfoProvider").newInstance();
    }
    
    public static class DefaultInfo extends CurrencyDisplayInfo
    {
        private final boolean fallback;
        private static final CurrencyDisplayInfo FALLBACK_INSTANCE;
        private static final CurrencyDisplayInfo NO_FALLBACK_INSTANCE;
        
        private DefaultInfo(final boolean fallback) {
            this.fallback = fallback;
        }
        
        public static final CurrencyDisplayInfo getWithFallback(final boolean b) {
            return b ? DefaultInfo.FALLBACK_INSTANCE : DefaultInfo.NO_FALLBACK_INSTANCE;
        }
        
        @Override
        public String getName(final String s) {
            return this.fallback ? s : null;
        }
        
        @Override
        public String getPluralName(final String s, final String s2) {
            return this.fallback ? s : null;
        }
        
        @Override
        public String getSymbol(final String s) {
            return this.fallback ? s : null;
        }
        
        @Override
        public Map symbolMap() {
            return Collections.emptyMap();
        }
        
        @Override
        public Map nameMap() {
            return Collections.emptyMap();
        }
        
        @Override
        public ULocale getULocale() {
            return ULocale.ROOT;
        }
        
        @Override
        public Map getUnitPatterns() {
            if (this.fallback) {
                return Collections.emptyMap();
            }
            return null;
        }
        
        @Override
        public CurrencyFormatInfo getFormatInfo(final String s) {
            return null;
        }
        
        @Override
        public CurrencySpacingInfo getSpacingInfo() {
            return this.fallback ? CurrencySpacingInfo.DEFAULT : null;
        }
        
        static {
            FALLBACK_INSTANCE = new DefaultInfo(true);
            NO_FALLBACK_INSTANCE = new DefaultInfo(false);
        }
    }
    
    public abstract static class CurrencyDisplayInfo extends CurrencyDisplayNames
    {
        public abstract Map getUnitPatterns();
        
        public abstract CurrencyFormatInfo getFormatInfo(final String p0);
        
        public abstract CurrencySpacingInfo getSpacingInfo();
    }
    
    public static final class CurrencyFormatInfo
    {
        public final String currencyPattern;
        public final char monetarySeparator;
        public final char monetaryGroupingSeparator;
        
        public CurrencyFormatInfo(final String currencyPattern, final char monetarySeparator, final char monetaryGroupingSeparator) {
            this.currencyPattern = currencyPattern;
            this.monetarySeparator = monetarySeparator;
            this.monetaryGroupingSeparator = monetaryGroupingSeparator;
        }
    }
    
    public static final class CurrencySpacingInfo
    {
        public final String beforeCurrencyMatch;
        public final String beforeContextMatch;
        public final String beforeInsert;
        public final String afterCurrencyMatch;
        public final String afterContextMatch;
        public final String afterInsert;
        private static final String DEFAULT_CUR_MATCH = "[:letter:]";
        private static final String DEFAULT_CTX_MATCH = "[:digit:]";
        private static final String DEFAULT_INSERT = " ";
        public static final CurrencySpacingInfo DEFAULT;
        
        public CurrencySpacingInfo(final String beforeCurrencyMatch, final String beforeContextMatch, final String beforeInsert, final String afterCurrencyMatch, final String afterContextMatch, final String afterInsert) {
            this.beforeCurrencyMatch = beforeCurrencyMatch;
            this.beforeContextMatch = beforeContextMatch;
            this.beforeInsert = beforeInsert;
            this.afterCurrencyMatch = afterCurrencyMatch;
            this.afterContextMatch = afterContextMatch;
            this.afterInsert = afterInsert;
        }
        
        static {
            DEFAULT = new CurrencySpacingInfo("[:letter:]", "[:digit:]", " ", "[:letter:]", "[:digit:]", " ");
        }
    }
    
    public interface CurrencyDisplayInfoProvider
    {
        CurrencyDisplayInfo getInstance(final ULocale p0, final boolean p1);
        
        boolean hasData();
    }
}
