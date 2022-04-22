package com.ibm.icu.text;

import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;
import java.util.*;

public abstract class LocaleDisplayNames
{
    public static LocaleDisplayNames getInstance(final ULocale uLocale) {
        return getInstance(uLocale, DialectHandling.STANDARD_NAMES);
    }
    
    public static LocaleDisplayNames getInstance(final ULocale uLocale, final DialectHandling dialectHandling) {
        return LocaleDisplayNamesImpl.getInstance(uLocale, dialectHandling);
    }
    
    public static LocaleDisplayNames getInstance(final ULocale uLocale, final DisplayContext... array) {
        return LocaleDisplayNamesImpl.getInstance(uLocale, array);
    }
    
    public abstract ULocale getLocale();
    
    public abstract DialectHandling getDialectHandling();
    
    public abstract DisplayContext getContext(final DisplayContext.Type p0);
    
    public abstract String localeDisplayName(final ULocale p0);
    
    public abstract String localeDisplayName(final Locale p0);
    
    public abstract String localeDisplayName(final String p0);
    
    public abstract String languageDisplayName(final String p0);
    
    public abstract String scriptDisplayName(final String p0);
    
    @Deprecated
    public String scriptDisplayNameInContext(final String s) {
        return this.scriptDisplayName(s);
    }
    
    public abstract String scriptDisplayName(final int p0);
    
    public abstract String regionDisplayName(final String p0);
    
    public abstract String variantDisplayName(final String p0);
    
    public abstract String keyDisplayName(final String p0);
    
    public abstract String keyValueDisplayName(final String p0, final String p1);
    
    @Deprecated
    protected LocaleDisplayNames() {
    }
    
    public enum DialectHandling
    {
        STANDARD_NAMES("STANDARD_NAMES", 0), 
        DIALECT_NAMES("DIALECT_NAMES", 1);
        
        private static final DialectHandling[] $VALUES;
        
        private DialectHandling(final String s, final int n) {
        }
        
        static {
            $VALUES = new DialectHandling[] { DialectHandling.STANDARD_NAMES, DialectHandling.DIALECT_NAMES };
        }
    }
}
