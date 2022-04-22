package com.ibm.icu.text;

import java.lang.ref.*;
import com.ibm.icu.util.*;
import java.text.*;
import java.util.*;
import com.ibm.icu.impl.*;

public abstract class BreakIterator implements Cloneable
{
    private static final boolean DEBUG;
    public static final int DONE = -1;
    public static final int KIND_CHARACTER = 0;
    public static final int KIND_WORD = 1;
    public static final int KIND_LINE = 2;
    public static final int KIND_SENTENCE = 3;
    public static final int KIND_TITLE = 4;
    private static final int KIND_COUNT = 5;
    private static final SoftReference[] iterCache;
    private static BreakIteratorServiceShim shim;
    private ULocale validLocale;
    private ULocale actualLocale;
    
    protected BreakIterator() {
    }
    
    public Object clone() {
        return super.clone();
    }
    
    public abstract int first();
    
    public abstract int last();
    
    public abstract int next(final int p0);
    
    public abstract int next();
    
    public abstract int previous();
    
    public abstract int following(final int p0);
    
    public int preceding(final int n) {
        int n2;
        for (n2 = this.following(n); n2 >= n && n2 != -1; n2 = this.previous()) {}
        return n2;
    }
    
    public boolean isBoundary(final int n) {
        return n == 0 || this.following(n - 1) == n;
    }
    
    public abstract int current();
    
    public abstract CharacterIterator getText();
    
    public void setText(final String s) {
        this.setText(new StringCharacterIterator(s));
    }
    
    public abstract void setText(final CharacterIterator p0);
    
    public static BreakIterator getWordInstance() {
        return getWordInstance(ULocale.getDefault());
    }
    
    public static BreakIterator getWordInstance(final Locale locale) {
        return getBreakInstance(ULocale.forLocale(locale), 1);
    }
    
    public static BreakIterator getWordInstance(final ULocale uLocale) {
        return getBreakInstance(uLocale, 1);
    }
    
    public static BreakIterator getLineInstance() {
        return getLineInstance(ULocale.getDefault());
    }
    
    public static BreakIterator getLineInstance(final Locale locale) {
        return getBreakInstance(ULocale.forLocale(locale), 2);
    }
    
    public static BreakIterator getLineInstance(final ULocale uLocale) {
        return getBreakInstance(uLocale, 2);
    }
    
    public static BreakIterator getCharacterInstance() {
        return getCharacterInstance(ULocale.getDefault());
    }
    
    public static BreakIterator getCharacterInstance(final Locale locale) {
        return getBreakInstance(ULocale.forLocale(locale), 0);
    }
    
    public static BreakIterator getCharacterInstance(final ULocale uLocale) {
        return getBreakInstance(uLocale, 0);
    }
    
    public static BreakIterator getSentenceInstance() {
        return getSentenceInstance(ULocale.getDefault());
    }
    
    public static BreakIterator getSentenceInstance(final Locale locale) {
        return getBreakInstance(ULocale.forLocale(locale), 3);
    }
    
    public static BreakIterator getSentenceInstance(final ULocale uLocale) {
        return getBreakInstance(uLocale, 3);
    }
    
    public static BreakIterator getTitleInstance() {
        return getTitleInstance(ULocale.getDefault());
    }
    
    public static BreakIterator getTitleInstance(final Locale locale) {
        return getBreakInstance(ULocale.forLocale(locale), 4);
    }
    
    public static BreakIterator getTitleInstance(final ULocale uLocale) {
        return getBreakInstance(uLocale, 4);
    }
    
    public static Object registerInstance(final BreakIterator breakIterator, final Locale locale, final int n) {
        return registerInstance(breakIterator, ULocale.forLocale(locale), n);
    }
    
    public static Object registerInstance(final BreakIterator breakIterator, final ULocale uLocale, final int n) {
        if (BreakIterator.iterCache[n] != null) {
            final BreakIteratorCache breakIteratorCache = BreakIterator.iterCache[n].get();
            if (breakIteratorCache != null && breakIteratorCache.getLocale().equals(uLocale)) {
                BreakIterator.iterCache[n] = null;
            }
        }
        return getShim().registerInstance(breakIterator, uLocale, n);
    }
    
    public static boolean unregister(final Object o) {
        if (o == null) {
            throw new IllegalArgumentException("registry key must not be null");
        }
        if (BreakIterator.shim == null) {
            return false;
        }
        while (true) {
            BreakIterator.iterCache[0] = null;
            int n = 0;
            ++n;
        }
    }
    
    @Deprecated
    public static BreakIterator getBreakInstance(final ULocale uLocale, final int breakType) {
        if (BreakIterator.iterCache[breakType] != null) {
            final BreakIteratorCache breakIteratorCache = BreakIterator.iterCache[breakType].get();
            if (breakIteratorCache != null && breakIteratorCache.getLocale().equals(uLocale)) {
                return breakIteratorCache.createBreakInstance();
            }
        }
        final BreakIterator breakIterator = getShim().createBreakIterator(uLocale, breakType);
        BreakIterator.iterCache[breakType] = new SoftReference(new BreakIteratorCache(uLocale, breakIterator));
        if (breakIterator instanceof RuleBasedBreakIterator) {
            ((RuleBasedBreakIterator)breakIterator).setBreakType(breakType);
        }
        return breakIterator;
    }
    
    public static synchronized Locale[] getAvailableLocales() {
        return getShim().getAvailableLocales();
    }
    
    public static synchronized ULocale[] getAvailableULocales() {
        return getShim().getAvailableULocales();
    }
    
    private static BreakIteratorServiceShim getShim() {
        if (BreakIterator.shim == null) {
            BreakIterator.shim = (BreakIteratorServiceShim)Class.forName("com.ibm.icu.text.BreakIteratorFactory").newInstance();
        }
        return BreakIterator.shim;
    }
    
    public final ULocale getLocale(final ULocale.Type type) {
        return (type == ULocale.ACTUAL_LOCALE) ? this.actualLocale : this.validLocale;
    }
    
    final void setLocale(final ULocale validLocale, final ULocale actualLocale) {
        if (validLocale == null != (actualLocale == null)) {
            throw new IllegalArgumentException();
        }
        this.validLocale = validLocale;
        this.actualLocale = actualLocale;
    }
    
    static {
        DEBUG = ICUDebug.enabled("breakiterator");
        iterCache = new SoftReference[5];
    }
    
    abstract static class BreakIteratorServiceShim
    {
        public abstract Object registerInstance(final BreakIterator p0, final ULocale p1, final int p2);
        
        public abstract boolean unregister(final Object p0);
        
        public abstract Locale[] getAvailableLocales();
        
        public abstract ULocale[] getAvailableULocales();
        
        public abstract BreakIterator createBreakIterator(final ULocale p0, final int p1);
    }
    
    private static final class BreakIteratorCache
    {
        private BreakIterator iter;
        private ULocale where;
        
        BreakIteratorCache(final ULocale where, final BreakIterator breakIterator) {
            this.where = where;
            this.iter = (BreakIterator)breakIterator.clone();
        }
        
        ULocale getLocale() {
            return this.where;
        }
        
        BreakIterator createBreakInstance() {
            return (BreakIterator)this.iter.clone();
        }
    }
}
