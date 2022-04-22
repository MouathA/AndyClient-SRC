package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;
import java.util.*;

class NumberFormatServiceShim extends NumberFormat.NumberFormatShim
{
    private static ICULocaleService service;
    
    @Override
    Locale[] getAvailableLocales() {
        if (NumberFormatServiceShim.service.isDefault()) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return NumberFormatServiceShim.service.getAvailableLocales();
    }
    
    @Override
    ULocale[] getAvailableULocales() {
        if (NumberFormatServiceShim.service.isDefault()) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return NumberFormatServiceShim.service.getAvailableULocales();
    }
    
    @Override
    Object registerFactory(final NumberFormat.NumberFormatFactory numberFormatFactory) {
        return NumberFormatServiceShim.service.registerFactory(new NFFactory(numberFormatFactory));
    }
    
    @Override
    boolean unregister(final Object o) {
        return NumberFormatServiceShim.service.unregisterFactory((ICUService.Factory)o);
    }
    
    @Override
    NumberFormat createInstance(final ULocale uLocale, final int n) {
        final ULocale[] array = { null };
        final NumberFormat numberFormat = (NumberFormat)NumberFormatServiceShim.service.get(uLocale, n, array);
        if (numberFormat == null) {
            throw new MissingResourceException("Unable to construct NumberFormat", "", "");
        }
        final NumberFormat numberFormat2 = (NumberFormat)numberFormat.clone();
        if (n == 1 || n == 5 || n == 6) {
            numberFormat2.setCurrency(Currency.getInstance(uLocale));
        }
        final ULocale uLocale2 = array[0];
        numberFormat2.setLocale(uLocale2, uLocale2);
        return numberFormat2;
    }
    
    static {
        NumberFormatServiceShim.service = new NFService();
    }
    
    private static class NFService extends ICULocaleService
    {
        NFService() {
            super("NumberFormat");
            this.registerFactory(new RBNumberFormatFactory());
            this.markDefault();
        }
        
        class RBNumberFormatFactory extends ICUResourceBundleFactory
        {
            final NFService this$0;
            
            RBNumberFormatFactory(final NFService this$0) {
                this.this$0 = this$0;
            }
            
            @Override
            protected Object handleCreate(final ULocale uLocale, final int n, final ICUService icuService) {
                return NumberFormat.createInstance(uLocale, n);
            }
        }
    }
    
    private static final class NFFactory extends ICULocaleService.LocaleKeyFactory
    {
        private NumberFormat.NumberFormatFactory delegate;
        
        NFFactory(final NumberFormat.NumberFormatFactory delegate) {
            super(delegate.visible());
            this.delegate = delegate;
        }
        
        @Override
        public Object create(final ICUService.Key key, final ICUService icuService) {
            if (!this.handlesKey(key) || !(key instanceof ICULocaleService.LocaleKey)) {
                return null;
            }
            final ICULocaleService.LocaleKey localeKey = (ICULocaleService.LocaleKey)key;
            Object o = this.delegate.createFormat(localeKey.canonicalLocale(), localeKey.kind());
            if (o == null) {
                o = icuService.getKey(key, null, this);
            }
            return o;
        }
        
        @Override
        protected Set getSupportedIDs() {
            return this.delegate.getSupportedLocaleNames();
        }
    }
}
