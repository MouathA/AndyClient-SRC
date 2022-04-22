package com.ibm.icu.util;

import java.lang.ref.*;
import java.util.concurrent.*;
import com.ibm.icu.impl.*;
import java.nio.*;
import java.util.*;

public abstract class UResourceBundle extends ResourceBundle
{
    private static ICUCache BUNDLE_CACHE;
    private static final ResourceCacheKey cacheKey;
    private static final int ROOT_MISSING = 0;
    private static final int ROOT_ICU = 1;
    private static final int ROOT_JAVA = 2;
    private static SoftReference ROOT_CACHE;
    private Set keys;
    public static final int NONE = -1;
    public static final int STRING = 0;
    public static final int BINARY = 1;
    public static final int TABLE = 2;
    public static final int INT = 7;
    public static final int ARRAY = 8;
    public static final int INT_VECTOR = 14;
    
    public static UResourceBundle getBundleInstance(final String s, final String s2) {
        return getBundleInstance(s, s2, ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }
    
    public static UResourceBundle getBundleInstance(final String s, final String s2, final ClassLoader classLoader) {
        return getBundleInstance(s, s2, classLoader, false);
    }
    
    protected static UResourceBundle getBundleInstance(final String s, final String s2, final ClassLoader classLoader, final boolean b) {
        return instantiateBundle(s, s2, classLoader, b);
    }
    
    public UResourceBundle() {
        this.keys = null;
    }
    
    public static UResourceBundle getBundleInstance(ULocale default1) {
        if (default1 == null) {
            default1 = ULocale.getDefault();
        }
        return getBundleInstance("com/ibm/icu/impl/data/icudt51b", default1.toString(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }
    
    public static UResourceBundle getBundleInstance(String s) {
        if (s == null) {
            s = "com/ibm/icu/impl/data/icudt51b";
        }
        return getBundleInstance(s, ULocale.getDefault().toString(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }
    
    public static UResourceBundle getBundleInstance(String s, final Locale locale) {
        if (s == null) {
            s = "com/ibm/icu/impl/data/icudt51b";
        }
        return getBundleInstance(s, ((locale == null) ? ULocale.getDefault() : ULocale.forLocale(locale)).toString(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }
    
    public static UResourceBundle getBundleInstance(String s, ULocale default1) {
        if (s == null) {
            s = "com/ibm/icu/impl/data/icudt51b";
        }
        if (default1 == null) {
            default1 = ULocale.getDefault();
        }
        return getBundleInstance(s, default1.toString(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }
    
    public static UResourceBundle getBundleInstance(String s, final Locale locale, final ClassLoader classLoader) {
        if (s == null) {
            s = "com/ibm/icu/impl/data/icudt51b";
        }
        return getBundleInstance(s, ((locale == null) ? ULocale.getDefault() : ULocale.forLocale(locale)).toString(), classLoader, false);
    }
    
    public static UResourceBundle getBundleInstance(String s, ULocale default1, final ClassLoader classLoader) {
        if (s == null) {
            s = "com/ibm/icu/impl/data/icudt51b";
        }
        if (default1 == null) {
            default1 = ULocale.getDefault();
        }
        return getBundleInstance(s, default1.toString(), classLoader, false);
    }
    
    public abstract ULocale getULocale();
    
    protected abstract String getLocaleID();
    
    protected abstract String getBaseName();
    
    protected abstract UResourceBundle getParent();
    
    @Override
    public Locale getLocale() {
        return this.getULocale().toLocale();
    }
    
    @Deprecated
    public static void resetBundleCache() {
        UResourceBundle.BUNDLE_CACHE = new SimpleCache();
    }
    
    @Deprecated
    protected static UResourceBundle addToCache(final ClassLoader classLoader, final String s, final ULocale uLocale, final UResourceBundle uResourceBundle) {
        // monitorenter(cacheKey = UResourceBundle.cacheKey)
        ResourceCacheKey.access$000(UResourceBundle.cacheKey, classLoader, s, uLocale);
        final UResourceBundle uResourceBundle2 = (UResourceBundle)UResourceBundle.BUNDLE_CACHE.get(UResourceBundle.cacheKey);
        if (uResourceBundle2 != null) {
            // monitorexit(cacheKey)
            return uResourceBundle2;
        }
        UResourceBundle.BUNDLE_CACHE.put(UResourceBundle.cacheKey.clone(), uResourceBundle);
        // monitorexit(cacheKey)
        return uResourceBundle;
    }
    
    @Deprecated
    protected static UResourceBundle loadFromCache(final ClassLoader classLoader, final String s, final ULocale uLocale) {
        // monitorenter(cacheKey = UResourceBundle.cacheKey)
        ResourceCacheKey.access$000(UResourceBundle.cacheKey, classLoader, s, uLocale);
        // monitorexit(cacheKey)
        return (UResourceBundle)UResourceBundle.BUNDLE_CACHE.get(UResourceBundle.cacheKey);
    }
    
    private static int getRootType(final String s, final ClassLoader classLoader) {
        ConcurrentHashMap<?, Integer> concurrentHashMap = UResourceBundle.ROOT_CACHE.get();
        if (concurrentHashMap == null) {
            final Class<UResourceBundle> clazz = UResourceBundle.class;
            final Class<UResourceBundle> clazz2 = UResourceBundle.class;
            // monitorenter(clazz)
            concurrentHashMap = UResourceBundle.ROOT_CACHE.get();
            if (concurrentHashMap == null) {
                concurrentHashMap = new ConcurrentHashMap<Object, Integer>();
                UResourceBundle.ROOT_CACHE = new SoftReference(concurrentHashMap);
            }
        }
        // monitorexit(clazz2)
        Integer value = concurrentHashMap.get(s);
        if (value == null) {
            ICUResourceBundle.getBundleInstance(s, (s.indexOf(46) == -1) ? "root" : "", classLoader, true);
            value = 2;
            concurrentHashMap.putIfAbsent(s, value);
        }
        return value;
    }
    
    private static void setRootType(final String s, final int n) {
        final Integer value = n;
        ConcurrentHashMap<String, Integer> concurrentHashMap = UResourceBundle.ROOT_CACHE.get();
        if (concurrentHashMap == null) {
            final Class<UResourceBundle> clazz = UResourceBundle.class;
            final Class<UResourceBundle> clazz2 = UResourceBundle.class;
            // monitorenter(clazz)
            concurrentHashMap = UResourceBundle.ROOT_CACHE.get();
            if (concurrentHashMap == null) {
                concurrentHashMap = new ConcurrentHashMap<String, Integer>();
                UResourceBundle.ROOT_CACHE = new SoftReference(concurrentHashMap);
            }
        }
        // monitorexit(clazz2)
        concurrentHashMap.put(s, value);
    }
    
    protected static UResourceBundle instantiateBundle(final String s, final String s2, final ClassLoader classLoader, final boolean b) {
        final int rootType = getRootType(s, classLoader);
        final ULocale default1 = ULocale.getDefault();
        switch (rootType) {
            case 1: {
                UResourceBundle uResourceBundle;
                if (b) {
                    uResourceBundle = loadFromCache(classLoader, ICUResourceBundleReader.getFullName(s, s2), default1);
                    if (uResourceBundle == null) {
                        uResourceBundle = ICUResourceBundle.getBundleInstance(s, s2, classLoader, b);
                    }
                }
                else {
                    uResourceBundle = ICUResourceBundle.getBundleInstance(s, s2, classLoader, b);
                }
                return uResourceBundle;
            }
            case 2: {
                return ResourceBundleWrapper.getBundleInstance(s, s2, classLoader, b);
            }
            default: {
                final UResourceBundle bundleInstance = ICUResourceBundle.getBundleInstance(s, s2, classLoader, b);
                setRootType(s, 1);
                return bundleInstance;
            }
        }
    }
    
    public ByteBuffer getBinary() {
        throw new UResourceTypeMismatchException("");
    }
    
    public String getString() {
        throw new UResourceTypeMismatchException("");
    }
    
    public String[] getStringArray() {
        throw new UResourceTypeMismatchException("");
    }
    
    public byte[] getBinary(final byte[] array) {
        throw new UResourceTypeMismatchException("");
    }
    
    public int[] getIntVector() {
        throw new UResourceTypeMismatchException("");
    }
    
    public int getInt() {
        throw new UResourceTypeMismatchException("");
    }
    
    public int getUInt() {
        throw new UResourceTypeMismatchException("");
    }
    
    public UResourceBundle get(final String s) {
        final UResourceBundle topLevel = this.findTopLevel(s);
        if (topLevel == null) {
            throw new MissingResourceException("Can't find resource for bundle " + ICUResourceBundleReader.getFullName(this.getBaseName(), this.getLocaleID()) + ", key " + s, this.getClass().getName(), s);
        }
        return topLevel;
    }
    
    @Deprecated
    protected UResourceBundle findTopLevel(final String s) {
        for (UResourceBundle parent = this; parent != null; parent = parent.getParent()) {
            final UResourceBundle handleGet = parent.handleGet(s, null, this);
            if (handleGet != null) {
                ((ICUResourceBundle)handleGet).setLoadingStatus(this.getLocaleID());
                return handleGet;
            }
        }
        return null;
    }
    
    public String getString(final int n) {
        final ICUResourceBundle icuResourceBundle = (ICUResourceBundle)this.get(n);
        if (icuResourceBundle.getType() == 0) {
            return icuResourceBundle.getString();
        }
        throw new UResourceTypeMismatchException("");
    }
    
    public UResourceBundle get(final int n) {
        UResourceBundle uResourceBundle = this.handleGet(n, null, this);
        if (uResourceBundle == null) {
            uResourceBundle = this.getParent();
            if (uResourceBundle != null) {
                uResourceBundle = uResourceBundle.get(n);
            }
            if (uResourceBundle == null) {
                throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + this.getKey(), this.getClass().getName(), this.getKey());
            }
        }
        ((ICUResourceBundle)uResourceBundle).setLoadingStatus(this.getLocaleID());
        return uResourceBundle;
    }
    
    @Deprecated
    protected UResourceBundle findTopLevel(final int n) {
        for (UResourceBundle parent = this; parent != null; parent = parent.getParent()) {
            final UResourceBundle handleGet = parent.handleGet(n, null, this);
            if (handleGet != null) {
                ((ICUResourceBundle)handleGet).setLoadingStatus(this.getLocaleID());
                return handleGet;
            }
        }
        return null;
    }
    
    @Override
    public Enumeration getKeys() {
        return Collections.enumeration((Collection<Object>)this.keySet());
    }
    
    @Override
    @Deprecated
    public Set keySet() {
        if (this.keys == null) {
            if (!this.isTopLevelResource()) {
                return this.handleKeySet();
            }
            TreeSet<Object> set;
            if (this.parent == null) {
                set = new TreeSet<Object>();
            }
            else if (this.parent instanceof UResourceBundle) {
                set = new TreeSet<Object>(((UResourceBundle)this.parent).keySet());
            }
            else {
                set = new TreeSet<Object>();
                final Enumeration<String> keys = this.parent.getKeys();
                while (keys.hasMoreElements()) {
                    set.add(keys.nextElement());
                }
            }
            set.addAll(this.handleKeySet());
            this.keys = Collections.unmodifiableSet((Set<?>)set);
        }
        return this.keys;
    }
    
    @Override
    @Deprecated
    protected Set handleKeySet() {
        return Collections.emptySet();
    }
    
    public int getSize() {
        return 1;
    }
    
    public int getType() {
        return -1;
    }
    
    public VersionInfo getVersion() {
        return null;
    }
    
    public UResourceBundleIterator getIterator() {
        return new UResourceBundleIterator(this);
    }
    
    public String getKey() {
        return null;
    }
    
    protected UResourceBundle handleGet(final String s, final HashMap hashMap, final UResourceBundle uResourceBundle) {
        return null;
    }
    
    protected UResourceBundle handleGet(final int n, final HashMap hashMap, final UResourceBundle uResourceBundle) {
        return null;
    }
    
    protected String[] handleGetStringArray() {
        return null;
    }
    
    protected Enumeration handleGetKeys() {
        return null;
    }
    
    @Override
    protected Object handleGetObject(final String s) {
        return this.handleGetObjectImpl(s, this);
    }
    
    private Object handleGetObjectImpl(final String s, final UResourceBundle uResourceBundle) {
        Object o = this.resolveObject(s, uResourceBundle);
        if (o == null) {
            final UResourceBundle parent = this.getParent();
            if (parent != null) {
                o = parent.handleGetObjectImpl(s, uResourceBundle);
            }
            if (o == null) {
                throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + s, this.getClass().getName(), s);
            }
        }
        return o;
    }
    
    private Object resolveObject(final String s, final UResourceBundle uResourceBundle) {
        if (this.getType() == 0) {
            return this.getString();
        }
        final UResourceBundle handleGet = this.handleGet(s, null, uResourceBundle);
        if (handleGet != null) {
            if (handleGet.getType() == 0) {
                return handleGet.getString();
            }
            if (handleGet.getType() == 8) {
                return handleGet.handleGetStringArray();
            }
        }
        return handleGet;
    }
    
    @Deprecated
    protected abstract void setLoadingStatus(final int p0);
    
    @Deprecated
    protected boolean isTopLevelResource() {
        return true;
    }
    
    static {
        UResourceBundle.BUNDLE_CACHE = new SimpleCache();
        cacheKey = new ResourceCacheKey(null);
        UResourceBundle.ROOT_CACHE = new SoftReference((T)new ConcurrentHashMap());
    }
    
    private static final class ResourceCacheKey implements Cloneable
    {
        private SoftReference loaderRef;
        private String searchName;
        private ULocale defaultLocale;
        private int hashCodeCache;
        
        private ResourceCacheKey() {
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == null) {
                return false;
            }
            if (this == o) {
                return true;
            }
            final ResourceCacheKey resourceCacheKey = (ResourceCacheKey)o;
            if (this.hashCodeCache != resourceCacheKey.hashCodeCache) {
                return false;
            }
            if (!this.searchName.equals(resourceCacheKey.searchName)) {
                return false;
            }
            if (this.defaultLocale == null) {
                if (resourceCacheKey.defaultLocale != null) {
                    return false;
                }
            }
            else if (!this.defaultLocale.equals(resourceCacheKey.defaultLocale)) {
                return false;
            }
            if (this.loaderRef == null) {
                return resourceCacheKey.loaderRef == null;
            }
            return resourceCacheKey.loaderRef != null && this.loaderRef.get() == resourceCacheKey.loaderRef.get();
        }
        
        @Override
        public int hashCode() {
            return this.hashCodeCache;
        }
        
        public Object clone() {
            return super.clone();
        }
        
        private synchronized void setKeyValues(final ClassLoader classLoader, final String searchName, final ULocale defaultLocale) {
            this.searchName = searchName;
            this.hashCodeCache = searchName.hashCode();
            this.defaultLocale = defaultLocale;
            if (defaultLocale != null) {
                this.hashCodeCache ^= defaultLocale.hashCode();
            }
            if (classLoader == null) {
                this.loaderRef = null;
            }
            else {
                this.loaderRef = new SoftReference((T)classLoader);
                this.hashCodeCache ^= classLoader.hashCode();
            }
        }
        
        static void access$000(final ResourceCacheKey resourceCacheKey, final ClassLoader classLoader, final String s, final ULocale uLocale) {
            resourceCacheKey.setKeyValues(classLoader, s, uLocale);
        }
        
        ResourceCacheKey(final UResourceBundle$1 object) {
            this();
        }
    }
}
