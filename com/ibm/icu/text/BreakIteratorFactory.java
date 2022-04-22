package com.ibm.icu.text;

import java.text.*;
import java.util.*;
import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;

final class BreakIteratorFactory extends BreakIterator.BreakIteratorServiceShim
{
    static final ICULocaleService service;
    
    @Override
    public Object registerInstance(final BreakIterator breakIterator, final ULocale uLocale, final int n) {
        breakIterator.setText(new StringCharacterIterator(""));
        return BreakIteratorFactory.service.registerObject(breakIterator, uLocale, n);
    }
    
    @Override
    public boolean unregister(final Object o) {
        return !BreakIteratorFactory.service.isDefault() && BreakIteratorFactory.service.unregisterFactory((ICUService.Factory)o);
    }
    
    @Override
    public Locale[] getAvailableLocales() {
        if (BreakIteratorFactory.service == null) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return BreakIteratorFactory.service.getAvailableLocales();
    }
    
    @Override
    public ULocale[] getAvailableULocales() {
        if (BreakIteratorFactory.service == null) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return BreakIteratorFactory.service.getAvailableULocales();
    }
    
    @Override
    public BreakIterator createBreakIterator(final ULocale uLocale, final int n) {
        if (BreakIteratorFactory.service.isDefault()) {
            return createBreakInstance(uLocale, n);
        }
        final ULocale[] array = { null };
        final BreakIterator breakIterator = (BreakIterator)BreakIteratorFactory.service.get(uLocale, n, array);
        breakIterator.setLocale(array[0], array[0]);
        return breakIterator;
    }
    
    private static BreakIterator createBreakInstance(final ULocale uLocale, final int breakType) {
        final ICUResourceBundle icuResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b/brkitr", uLocale);
        final RuleBasedBreakIterator instanceFromCompiledRules = RuleBasedBreakIterator.getInstanceFromCompiledRules(ICUData.getStream("data/icudt51b/brkitr/" + icuResourceBundle.getStringWithFallback("boundaries/" + BreakIteratorFactory.KIND_NAMES[breakType])));
        final ULocale forLocale = ULocale.forLocale(icuResourceBundle.getLocale());
        instanceFromCompiledRules.setLocale(forLocale, forLocale);
        instanceFromCompiledRules.setBreakType(breakType);
        return instanceFromCompiledRules;
    }
    
    static BreakIterator access$000(final ULocale uLocale, final int n) {
        return createBreakInstance(uLocale, n);
    }
    
    static {
        service = new BFService();
        BreakIteratorFactory.KIND_NAMES = new String[] { "grapheme", "word", "line", "sentence", "title" };
    }
    
    private static class BFService extends ICULocaleService
    {
        BFService() {
            super("BreakIterator");
            this.registerFactory(new RBBreakIteratorFactory());
            this.markDefault();
        }
        
        class RBBreakIteratorFactory extends ICUResourceBundleFactory
        {
            final BFService this$0;
            
            RBBreakIteratorFactory(final BFService this$0) {
                this.this$0 = this$0;
            }
            
            @Override
            protected Object handleCreate(final ULocale uLocale, final int n, final ICUService icuService) {
                return BreakIteratorFactory.access$000(uLocale, n);
            }
        }
    }
}
