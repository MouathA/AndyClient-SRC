package com.ibm.icu.text;

import com.ibm.icu.util.*;
import java.text.*;

class CurrencyFormat extends MeasureFormat
{
    static final long serialVersionUID = -931679363692504634L;
    private NumberFormat fmt;
    
    public CurrencyFormat(final ULocale uLocale) {
        this.fmt = NumberFormat.getCurrencyInstance(uLocale.toLocale());
    }
    
    @Override
    public StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        final CurrencyAmount currencyAmount = (CurrencyAmount)o;
        this.fmt.setCurrency(currencyAmount.getCurrency());
        return this.fmt.format(currencyAmount.getNumber(), sb, fieldPosition);
    }
    
    @Override
    public Object parseObject(final String s, final ParsePosition parsePosition) {
        return this.fmt.parseCurrency(s, parsePosition);
    }
}
