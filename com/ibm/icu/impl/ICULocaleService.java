package com.ibm.icu.impl;

import com.ibm.icu.util.*;
import java.util.*;

public class ICULocaleService extends ICUService
{
    private ULocale fallbackLocale;
    private String fallbackLocaleName;
    
    public ICULocaleService() {
    }
    
    public ICULocaleService(final String s) {
        super(s);
    }
    
    public Object get(final ULocale uLocale) {
        return this.get(uLocale, -1, null);
    }
    
    public Object get(final ULocale uLocale, final int n) {
        return this.get(uLocale, n, null);
    }
    
    public Object get(final ULocale uLocale, final ULocale[] array) {
        return this.get(uLocale, -1, array);
    }
    
    public Object get(final ULocale uLocale, final int n, final ULocale[] array) {
        final Key key = this.createKey(uLocale, n);
        if (array == null) {
            return this.getKey(key);
        }
        final String[] array2 = { null };
        final Object key2 = this.getKey(key, array2);
        if (key2 != null) {
            final int index = array2[0].indexOf("/");
            if (index >= 0) {
                array2[0] = array2[0].substring(index + 1);
            }
            array[0] = new ULocale(array2[0]);
        }
        return key2;
    }
    
    public Factory registerObject(final Object o, final ULocale uLocale) {
        return this.registerObject(o, uLocale, -1, true);
    }
    
    public Factory registerObject(final Object o, final ULocale uLocale, final boolean b) {
        return this.registerObject(o, uLocale, -1, b);
    }
    
    public Factory registerObject(final Object o, final ULocale uLocale, final int n) {
        return this.registerObject(o, uLocale, n, true);
    }
    
    public Factory registerObject(final Object o, final ULocale uLocale, final int n, final boolean b) {
        return this.registerFactory(new SimpleLocaleKeyFactory(o, uLocale, n, b));
    }
    
    public Locale[] getAvailableLocales() {
        final Set visibleIDs = this.getVisibleIDs();
        final Locale[] array = new Locale[visibleIDs.size()];
        final Iterator<String> iterator = visibleIDs.iterator();
        while (iterator.hasNext()) {
            final Locale localeFromName = LocaleUtility.getLocaleFromName(iterator.next());
            final Locale[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = localeFromName;
        }
        return array;
    }
    
    public ULocale[] getAvailableULocales() {
        final Set visibleIDs = this.getVisibleIDs();
        final ULocale[] array = new ULocale[visibleIDs.size()];
        for (final String s : visibleIDs) {
            final ULocale[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = new ULocale(s);
        }
        return array;
    }
    
    public String validateFallbackLocale() {
        final ULocale default1 = ULocale.getDefault();
        if (default1 != this.fallbackLocale) {
            // monitorenter(this)
            if (default1 != this.fallbackLocale) {
                this.fallbackLocale = default1;
                this.fallbackLocaleName = default1.getBaseName();
                this.clearServiceCache();
            }
        }
        // monitorexit(this)
        return this.fallbackLocaleName;
    }
    
    @Override
    public Key createKey(final String s) {
        return LocaleKey.createWithCanonicalFallback(s, this.validateFallbackLocale());
    }
    
    public Key createKey(final String s, final int n) {
        return LocaleKey.createWithCanonicalFallback(s, this.validateFallbackLocale(), n);
    }
    
    public Key createKey(final ULocale uLocale, final int n) {
        return LocaleKey.createWithCanonical(uLocale, this.validateFallbackLocale(), n);
    }
    
    public static class ICUResourceBundleFactory extends LocaleKeyFactory
    {
        protected final String bundleName;
        
        public ICUResourceBundleFactory() {
            this("com/ibm/icu/impl/data/icudt51b");
        }
        
        public ICUResourceBundleFactory(final String bundleName) {
            super(true);
            this.bundleName = bundleName;
        }
        
        @Override
        protected Set getSupportedIDs() {
            return ICUResourceBundle.getFullLocaleNameSet(this.bundleName, this.loader());
        }
        
        @Override
        public void updateVisibleIDs(final Map map) {
            final Iterator<String> iterator = ICUResourceBundle.getAvailableLocaleNameSet(this.bundleName, this.loader()).iterator();
            while (iterator.hasNext()) {
                map.put(iterator.next(), this);
            }
        }
        
        @Override
        protected Object handleCreate(final ULocale uLocale, final int n, final ICUService icuService) {
            return UResourceBundle.getBundleInstance(this.bundleName, uLocale, this.loader());
        }
        
        protected ClassLoader loader() {
            ClassLoader classLoader = this.getClass().getClassLoader();
            if (classLoader == null) {
                classLoader = Utility.getFallbackClassLoader();
            }
            return classLoader;
        }
        
        @Override
        public String toString() {
            return super.toString() + ", bundle: " + this.bundleName;
        }
    }
    
    public abstract static class LocaleKeyFactory implements Factory
    {
        protected final String name;
        protected final boolean visible;
        public static final boolean VISIBLE = true;
        public static final boolean INVISIBLE = false;
        
        protected LocaleKeyFactory(final boolean visible) {
            this.visible = visible;
            this.name = null;
        }
        
        protected LocaleKeyFactory(final boolean visible, final String name) {
            this.visible = visible;
            this.name = name;
        }
        
        public Object create(final Key key, final ICUService icuService) {
            if (this.handlesKey(key)) {
                final LocaleKey localeKey = (LocaleKey)key;
                return this.handleCreate(localeKey.currentLocale(), localeKey.kind(), icuService);
            }
            return null;
        }
        
        protected boolean handlesKey(final Key key) {
            return key != null && this.getSupportedIDs().contains(key.currentID());
        }
        
        public void updateVisibleIDs(final Map map) {
            for (final String s : this.getSupportedIDs()) {
                if (this.visible) {
                    map.put(s, this);
                }
                else {
                    map.remove(s);
                }
            }
        }
        
        public String getDisplayName(final String s, final ULocale uLocale) {
            if (uLocale == null) {
                return s;
            }
            return new ULocale(s).getDisplayName(uLocale);
        }
        
        protected Object handleCreate(final ULocale uLocale, final int n, final ICUService icuService) {
            return null;
        }
        
        protected boolean isSupportedID(final String s) {
            return this.getSupportedIDs().contains(s);
        }
        
        protected Set getSupportedIDs() {
            return Collections.emptySet();
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(super.toString());
            if (this.name != null) {
                sb.append(", name: ");
                sb.append(this.name);
            }
            sb.append(", visible: ");
            sb.append(this.visible);
            return sb.toString();
        }
    }
    
    public static class LocaleKey extends Key
    {
        private int kind;
        private int varstart;
        private String primaryID;
        private String fallbackID;
        private String currentID;
        public static final int KIND_ANY = -1;
        
        public static LocaleKey createWithCanonicalFallback(final String s, final String s2) {
            return createWithCanonicalFallback(s, s2, -1);
        }
        
        public static LocaleKey createWithCanonicalFallback(final String s, final String s2, final int n) {
            if (s == null) {
                return null;
            }
            return new LocaleKey(s, ULocale.getName(s), s2, n);
        }
        
        public static LocaleKey createWithCanonical(final ULocale uLocale, final String s, final int n) {
            if (uLocale == null) {
                return null;
            }
            final String name = uLocale.getName();
            return new LocaleKey(name, name, s, n);
        }
        
        protected LocaleKey(final String s, final String primaryID, final String fallbackID, final int kind) {
            super(s);
            this.kind = kind;
            if (primaryID == null || primaryID.equalsIgnoreCase("root")) {
                this.primaryID = "";
                this.fallbackID = null;
            }
            else {
                final int index = primaryID.indexOf(64);
                if (index == 4 && primaryID.regionMatches(true, 0, "root", 0, 4)) {
                    this.primaryID = primaryID.substring(4);
                    this.varstart = 0;
                    this.fallbackID = null;
                }
                else {
                    this.primaryID = primaryID;
                    this.varstart = index;
                    if (fallbackID == null || this.primaryID.equals(fallbackID)) {
                        this.fallbackID = "";
                    }
                    else {
                        this.fallbackID = fallbackID;
                    }
                }
            }
            this.currentID = ((this.varstart == -1) ? this.primaryID : this.primaryID.substring(0, this.varstart));
        }
        
        public String prefix() {
            return (this.kind == -1) ? null : Integer.toString(this.kind());
        }
        
        public int kind() {
            return this.kind;
        }
        
        @Override
        public String canonicalID() {
            return this.primaryID;
        }
        
        @Override
        public String currentID() {
            return this.currentID;
        }
        
        @Override
        public String currentDescriptor() {
            String s = this.currentID();
            if (s != null) {
                final StringBuilder sb = new StringBuilder();
                if (this.kind != -1) {
                    sb.append(this.prefix());
                }
                sb.append('/');
                sb.append(s);
                if (this.varstart != -1) {
                    sb.append(this.primaryID.substring(this.varstart, this.primaryID.length()));
                }
                s = sb.toString();
            }
            return s;
        }
        
        public ULocale canonicalLocale() {
            return new ULocale(this.primaryID);
        }
        
        public ULocale currentLocale() {
            if (this.varstart == -1) {
                return new ULocale(this.currentID);
            }
            return new ULocale(this.currentID + this.primaryID.substring(this.varstart));
        }
        
        @Override
        public boolean fallback() {
            int lastIndex = this.currentID.lastIndexOf(95);
            if (lastIndex != -1) {
                while (--lastIndex >= 0 && this.currentID.charAt(lastIndex) == '_') {}
                this.currentID = this.currentID.substring(0, lastIndex + 1);
                return true;
            }
            if (this.fallbackID != null) {
                this.currentID = this.fallbackID;
                if (this.fallbackID.length() == 0) {
                    this.fallbackID = null;
                }
                else {
                    this.fallbackID = "";
                }
                return true;
            }
            this.currentID = null;
            return false;
        }
        
        @Override
        public boolean isFallbackOf(final String s) {
            return LocaleUtility.isFallbackOf(this.canonicalID(), s);
        }
    }
    
    public static class SimpleLocaleKeyFactory extends LocaleKeyFactory
    {
        private final Object obj;
        private final String id;
        private final int kind;
        
        public SimpleLocaleKeyFactory(final Object o, final ULocale uLocale, final int n, final boolean b) {
            this(o, uLocale, n, b, null);
        }
        
        public SimpleLocaleKeyFactory(final Object obj, final ULocale uLocale, final int kind, final boolean b, final String s) {
            super(b, s);
            this.obj = obj;
            this.id = uLocale.getBaseName();
            this.kind = kind;
        }
        
        @Override
        public Object create(final Key key, final ICUService icuService) {
            if (!(key instanceof LocaleKey)) {
                return null;
            }
            final LocaleKey localeKey = (LocaleKey)key;
            if (this.kind != -1 && this.kind != localeKey.kind()) {
                return null;
            }
            if (!this.id.equals(localeKey.currentID())) {
                return null;
            }
            return this.obj;
        }
        
        @Override
        protected boolean isSupportedID(final String s) {
            return this.id.equals(s);
        }
        
        @Override
        public void updateVisibleIDs(final Map map) {
            if (this.visible) {
                map.put(this.id, this);
            }
            else {
                map.remove(this.id);
            }
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(super.toString());
            sb.append(", id: ");
            sb.append(this.id);
            sb.append(", kind: ");
            sb.append(this.kind);
            return sb.toString();
        }
    }
}
