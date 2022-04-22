package com.ibm.icu.text;

import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;
import java.util.*;

public abstract class CurrencyDisplayNames
{
    public static CurrencyDisplayNames getInstance(final ULocale uLocale) {
        return CurrencyData.provider.getInstance(uLocale, true);
    }
    
    public static CurrencyDisplayNames getInstance(final ULocale uLocale, final boolean b) {
        return CurrencyData.provider.getInstance(uLocale, !b);
    }
    
    @Deprecated
    public static boolean hasData() {
        return CurrencyData.provider.hasData();
    }
    
    public abstract ULocale getULocale();
    
    public abstract String getSymbol(final String p0);
    
    public abstract String getName(final String p0);
    
    public abstract String getPluralName(final String p0, final String p1);
    
    public abstract Map symbolMap();
    
    public abstract Map nameMap();
    
    @Deprecated
    protected CurrencyDisplayNames() {
    }
}
