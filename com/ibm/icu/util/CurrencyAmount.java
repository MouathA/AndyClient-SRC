package com.ibm.icu.util;

public class CurrencyAmount extends Measure
{
    public CurrencyAmount(final Number n, final Currency currency) {
        super(n, currency);
    }
    
    public CurrencyAmount(final double n, final Currency currency) {
        super(new Double(n), currency);
    }
    
    public Currency getCurrency() {
        return (Currency)this.getUnit();
    }
}
