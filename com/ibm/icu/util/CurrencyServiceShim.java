package com.ibm.icu.util;

import java.util.*;
import com.ibm.icu.impl.*;

final class CurrencyServiceShim extends Currency.ServiceShim
{
    static final ICULocaleService service;
    
    @Override
    Locale[] getAvailableLocales() {
        if (CurrencyServiceShim.service.isDefault()) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return CurrencyServiceShim.service.getAvailableLocales();
    }
    
    @Override
    ULocale[] getAvailableULocales() {
        if (CurrencyServiceShim.service.isDefault()) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return CurrencyServiceShim.service.getAvailableULocales();
    }
    
    @Override
    Currency createInstance(final ULocale uLocale) {
        if (CurrencyServiceShim.service.isDefault()) {
            return Currency.createCurrency(uLocale);
        }
        return (Currency)CurrencyServiceShim.service.get(uLocale);
    }
    
    @Override
    Object registerInstance(final Currency currency, final ULocale uLocale) {
        return CurrencyServiceShim.service.registerObject(currency, uLocale);
    }
    
    @Override
    boolean unregister(final Object o) {
        return CurrencyServiceShim.service.unregisterFactory((ICUService.Factory)o);
    }
    
    static {
        service = new CFService();
    }
    
    private static class CFService extends ICULocaleService
    {
        CFService() {
            super("Currency");
            this.registerFactory(new CurrencyFactory());
            this.markDefault();
        }
        
        class CurrencyFactory extends ICUResourceBundleFactory
        {
            final CFService this$0;
            
            CurrencyFactory(final CFService this$0) {
                this.this$0 = this$0;
            }
            
            @Override
            protected Object handleCreate(final ULocale uLocale, final int n, final ICUService icuService) {
                return Currency.createCurrency(uLocale);
            }
        }
    }
}
