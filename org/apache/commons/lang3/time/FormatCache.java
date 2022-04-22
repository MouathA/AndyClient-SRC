package org.apache.commons.lang3.time;

import java.util.concurrent.*;
import java.text.*;
import java.util.*;

abstract class FormatCache
{
    static final int NONE = -1;
    private final ConcurrentMap cInstanceCache;
    private static final ConcurrentMap cDateTimeInstanceCache;
    
    FormatCache() {
        this.cInstanceCache = new ConcurrentHashMap(7);
    }
    
    public Format getInstance() {
        return this.getDateTimeInstance(3, 3, TimeZone.getDefault(), Locale.getDefault());
    }
    
    public Format getInstance(final String s, TimeZone default1, Locale default2) {
        if (s == null) {
            throw new NullPointerException("pattern must not be null");
        }
        if (default1 == null) {
            default1 = TimeZone.getDefault();
        }
        if (default2 == null) {
            default2 = Locale.getDefault();
        }
        final MultipartKey multipartKey = new MultipartKey(new Object[] { s, default1, default2 });
        Format instance = (Format)this.cInstanceCache.get(multipartKey);
        if (instance == null) {
            instance = this.createInstance(s, default1, default2);
            final Format format = this.cInstanceCache.putIfAbsent(multipartKey, instance);
            if (format != null) {
                instance = format;
            }
        }
        return instance;
    }
    
    protected abstract Format createInstance(final String p0, final TimeZone p1, final Locale p2);
    
    private Format getDateTimeInstance(final Integer n, final Integer n2, final TimeZone timeZone, Locale default1) {
        if (default1 == null) {
            default1 = Locale.getDefault();
        }
        return this.getInstance(getPatternForStyle(n, n2, default1), timeZone, default1);
    }
    
    Format getDateTimeInstance(final int n, final int n2, final TimeZone timeZone, final Locale locale) {
        return this.getDateTimeInstance(Integer.valueOf(n), Integer.valueOf(n2), timeZone, locale);
    }
    
    Format getDateInstance(final int n, final TimeZone timeZone, final Locale locale) {
        return this.getDateTimeInstance(n, null, timeZone, locale);
    }
    
    Format getTimeInstance(final int n, final TimeZone timeZone, final Locale locale) {
        return this.getDateTimeInstance(null, n, timeZone, locale);
    }
    
    static String getPatternForStyle(final Integer n, final Integer n2, final Locale locale) {
        final MultipartKey multipartKey = new MultipartKey(new Object[] { n, n2, locale });
        String pattern = (String)FormatCache.cDateTimeInstanceCache.get(multipartKey);
        if (pattern == null) {
            DateFormat dateFormat;
            if (n == null) {
                dateFormat = DateFormat.getTimeInstance(n2, locale);
            }
            else if (n2 == null) {
                dateFormat = DateFormat.getDateInstance(n, locale);
            }
            else {
                dateFormat = DateFormat.getDateTimeInstance(n, n2, locale);
            }
            pattern = ((SimpleDateFormat)dateFormat).toPattern();
            final String s = FormatCache.cDateTimeInstanceCache.putIfAbsent(multipartKey, pattern);
            if (s != null) {
                pattern = s;
            }
        }
        return pattern;
    }
    
    static {
        cDateTimeInstanceCache = new ConcurrentHashMap(7);
    }
    
    private static class MultipartKey
    {
        private final Object[] keys;
        private int hashCode;
        
        public MultipartKey(final Object... keys) {
            this.keys = keys;
        }
        
        @Override
        public boolean equals(final Object o) {
            return Arrays.equals(this.keys, ((MultipartKey)o).keys);
        }
        
        @Override
        public int hashCode() {
            if (this.hashCode == 0) {
                final Object[] keys = this.keys;
                while (0 < keys.length) {
                    final Object o = keys[0];
                    if (o != null) {
                        final int n = 0 + o.hashCode();
                    }
                    int n2 = 0;
                    ++n2;
                }
                this.hashCode = 0;
            }
            return this.hashCode;
        }
    }
}
