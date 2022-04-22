package com.ibm.icu.text;

import com.ibm.icu.util.*;

public abstract class MeasureFormat extends UFormat
{
    static final long serialVersionUID = -7182021401701778240L;
    
    @Deprecated
    protected MeasureFormat() {
    }
    
    public static MeasureFormat getCurrencyFormat(final ULocale uLocale) {
        return new CurrencyFormat(uLocale);
    }
    
    public static MeasureFormat getCurrencyFormat() {
        return getCurrencyFormat(ULocale.getDefault(ULocale.Category.FORMAT));
    }
}
