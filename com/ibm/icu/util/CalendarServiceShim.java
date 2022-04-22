package com.ibm.icu.util;

import com.ibm.icu.impl.*;
import java.util.*;

class CalendarServiceShim extends Calendar.CalendarShim
{
    private static ICULocaleService service;
    
    @Override
    Locale[] getAvailableLocales() {
        if (CalendarServiceShim.service.isDefault()) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return CalendarServiceShim.service.getAvailableLocales();
    }
    
    @Override
    ULocale[] getAvailableULocales() {
        if (CalendarServiceShim.service.isDefault()) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return CalendarServiceShim.service.getAvailableULocales();
    }
    
    @Override
    Calendar createInstance(ULocale root) {
        final ULocale[] array = { null };
        if (root.equals(ULocale.ROOT)) {
            root = ULocale.ROOT;
        }
        ULocale setKeywordValue;
        if (root.getKeywordValue("calendar") == null) {
            setKeywordValue = root.setKeywordValue("calendar", CalendarUtil.getCalendarType(root));
        }
        else {
            setKeywordValue = root;
        }
        final Calendar calendar = (Calendar)CalendarServiceShim.service.get(setKeywordValue, array);
        if (calendar == null) {
            throw new MissingResourceException("Unable to construct Calendar", "", "");
        }
        return (Calendar)calendar.clone();
    }
    
    @Override
    Object registerFactory(final Calendar.CalendarFactory calendarFactory) {
        return CalendarServiceShim.service.registerFactory(new CalFactory(calendarFactory));
    }
    
    @Override
    boolean unregister(final Object o) {
        return CalendarServiceShim.service.unregisterFactory((ICUService.Factory)o);
    }
    
    static {
        CalendarServiceShim.service = new CalService();
    }
    
    private static class CalService extends ICULocaleService
    {
        CalService() {
            super("Calendar");
            this.registerFactory(new RBCalendarFactory());
            this.markDefault();
        }
        
        class RBCalendarFactory extends ICUResourceBundleFactory
        {
            final CalService this$0;
            
            RBCalendarFactory(final CalService this$0) {
                this.this$0 = this$0;
            }
            
            @Override
            protected Object handleCreate(final ULocale uLocale, final int n, final ICUService icuService) {
                return Calendar.createInstance(uLocale);
            }
        }
    }
    
    private static final class CalFactory extends ICULocaleService.LocaleKeyFactory
    {
        private Calendar.CalendarFactory delegate;
        
        CalFactory(final Calendar.CalendarFactory delegate) {
            super(delegate.visible());
            this.delegate = delegate;
        }
        
        @Override
        public Object create(final ICUService.Key key, final ICUService icuService) {
            if (!this.handlesKey(key) || !(key instanceof ICULocaleService.LocaleKey)) {
                return null;
            }
            Object o = this.delegate.createCalendar(((ICULocaleService.LocaleKey)key).canonicalLocale());
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
