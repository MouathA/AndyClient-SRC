package com.ibm.icu.text;

import java.util.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;

public final class ListFormatter
{
    private final String two;
    private final String start;
    private final String middle;
    private final String end;
    static Map localeToData;
    static Cache cache;
    
    public ListFormatter(final String two, final String start, final String middle, final String end) {
        this.two = two;
        this.start = start;
        this.middle = middle;
        this.end = end;
    }
    
    public static ListFormatter getInstance(final ULocale uLocale) {
        return ListFormatter.cache.get(uLocale);
    }
    
    public static ListFormatter getInstance(final Locale locale) {
        return getInstance(ULocale.forLocale(locale));
    }
    
    public static ListFormatter getInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public String format(final Object... array) {
        return this.format(Arrays.asList(array));
    }
    
    public String format(final Collection collection) {
        final Iterator<Object> iterator = collection.iterator();
        int i = collection.size();
        switch (i) {
            case 0: {
                return "";
            }
            case 1: {
                return iterator.next().toString();
            }
            case 2: {
                return this.format2(this.two, iterator.next(), iterator.next());
            }
            default: {
                String s = this.format2(this.start, iterator.next().toString(), iterator.next());
                for (i -= 3; i > 0; --i) {
                    s = this.format2(this.middle, s, iterator.next());
                }
                return this.format2(this.end, s, iterator.next());
            }
        }
    }
    
    private String format2(final String s, final Object o, final Object o2) {
        final int index = s.indexOf("{0}");
        final int index2 = s.indexOf("{1}");
        if (index < 0 || index2 < 0) {
            throw new IllegalArgumentException("Missing {0} or {1} in pattern " + s);
        }
        if (index < index2) {
            return s.substring(0, index) + o + s.substring(index + 3, index2) + o2 + s.substring(index2 + 3);
        }
        return s.substring(0, index2) + o2 + s.substring(index2 + 3, index) + o + s.substring(index + 3);
    }
    
    static void add(final String s, final String... array) {
        ListFormatter.localeToData.put(new ULocale(s), new ListFormatter(array[0], array[1], array[2], array[3]));
    }
    
    static {
        ListFormatter.localeToData = new HashMap();
        ListFormatter.cache = new Cache(null);
    }
    
    private static class Cache
    {
        private final ICUCache cache;
        
        private Cache() {
            this.cache = new SimpleCache();
        }
        
        public ListFormatter get(final ULocale uLocale) {
            ListFormatter load = (ListFormatter)this.cache.get(uLocale);
            if (load == null) {
                load = load(uLocale);
                this.cache.put(uLocale, load);
            }
            return load;
        }
        
        private static ListFormatter load(final ULocale uLocale) {
            final ICUResourceBundle withFallback = ((ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", uLocale)).getWithFallback("listPattern/standard");
            return new ListFormatter(withFallback.getWithFallback("2").getString(), withFallback.getWithFallback("start").getString(), withFallback.getWithFallback("middle").getString(), withFallback.getWithFallback("end").getString());
        }
        
        Cache(final ListFormatter$1 object) {
            this();
        }
    }
}
