package com.ibm.icu.impl;

import com.ibm.icu.util.*;
import java.net.*;
import java.io.*;
import java.security.*;
import java.util.*;

public class ICUResourceBundle extends UResourceBundle
{
    protected static final String ICU_DATA_PATH = "com/ibm/icu/impl/";
    public static final String ICU_BUNDLE = "data/icudt51b";
    public static final String ICU_BASE_NAME = "com/ibm/icu/impl/data/icudt51b";
    public static final String ICU_COLLATION_BASE_NAME = "com/ibm/icu/impl/data/icudt51b/coll";
    public static final String ICU_BRKITR_NAME = "/brkitr";
    public static final String ICU_BRKITR_BASE_NAME = "com/ibm/icu/impl/data/icudt51b/brkitr";
    public static final String ICU_RBNF_BASE_NAME = "com/ibm/icu/impl/data/icudt51b/rbnf";
    public static final String ICU_TRANSLIT_BASE_NAME = "com/ibm/icu/impl/data/icudt51b/translit";
    public static final String ICU_LANG_BASE_NAME = "com/ibm/icu/impl/data/icudt51b/lang";
    public static final String ICU_CURR_BASE_NAME = "com/ibm/icu/impl/data/icudt51b/curr";
    public static final String ICU_REGION_BASE_NAME = "com/ibm/icu/impl/data/icudt51b/region";
    public static final String ICU_ZONE_BASE_NAME = "com/ibm/icu/impl/data/icudt51b/zone";
    private static final String NO_INHERITANCE_MARKER = "\u2205\u2205\u2205";
    protected String resPath;
    public static final ClassLoader ICU_DATA_CLASS_LOADER;
    protected static final String INSTALLED_LOCALES = "InstalledLocales";
    public static final int FROM_FALLBACK = 1;
    public static final int FROM_ROOT = 2;
    public static final int FROM_DEFAULT = 3;
    public static final int FROM_LOCALE = 4;
    private int loadingStatus;
    private static final String ICU_RESOURCE_INDEX = "res_index";
    private static final String DEFAULT_TAG = "default";
    private static final String FULL_LOCALE_NAMES_LIST = "fullLocaleNames.lst";
    private static final boolean DEBUG;
    private static CacheBase GET_AVAILABLE_CACHE;
    protected String localeID;
    protected String baseName;
    protected ULocale ulocale;
    protected ClassLoader loader;
    protected ICUResourceBundleReader reader;
    protected String key;
    protected int resource;
    public static final int RES_BOGUS = -1;
    public static final int ALIAS = 3;
    public static final int TABLE32 = 4;
    public static final int TABLE16 = 5;
    public static final int STRING_V2 = 6;
    public static final int ARRAY16 = 9;
    private static final int[] gPublicTypes;
    private static final char RES_PATH_SEP_CHAR = '/';
    private static final String RES_PATH_SEP_STR = "/";
    private static final String ICUDATA = "ICUDATA";
    private static final char HYPHEN = '-';
    private static final String LOCALE = "LOCALE";
    protected ICUCache lookup;
    private static final int MAX_INITIAL_LOOKUP_SIZE = 64;
    static final boolean $assertionsDisabled;
    
    public void setLoadingStatus(final int loadingStatus) {
        this.loadingStatus = loadingStatus;
    }
    
    public int getLoadingStatus() {
        return this.loadingStatus;
    }
    
    public void setLoadingStatus(final String s) {
        final String localeID = this.getLocaleID();
        if (localeID.equals("root")) {
            this.setLoadingStatus(2);
        }
        else if (localeID.equals(s)) {
            this.setLoadingStatus(4);
        }
        else {
            this.setLoadingStatus(1);
        }
    }
    
    public String getResPath() {
        return this.resPath;
    }
    
    public static final ULocale getFunctionalEquivalent(final String s, final ClassLoader classLoader, final String s2, final String s3, final ULocale uLocale, final boolean[] array, final boolean b) {
        String keywordValue = uLocale.getKeywordValue(s3);
        final String baseName = uLocale.getBaseName();
        String s4 = null;
        final ULocale uLocale2 = new ULocale(baseName);
        ULocale uLocale3 = null;
        int n = 0;
        ULocale uLocale4 = null;
        int n2 = 0;
        int n3 = 0;
        if (keywordValue == null || keywordValue.length() == 0 || keywordValue.equals("default")) {
            keywordValue = "";
            n = 1;
        }
        ICUResourceBundle icuResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(s, uLocale2);
        if (array != null) {
            array[0] = false;
            final ULocale[] uLocaleList = getAvailEntry(s, classLoader).getULocaleList();
            for (int i = 0; i < uLocaleList.length; ++i) {
                if (uLocale2.equals(uLocaleList[i])) {
                    array[0] = true;
                    break;
                }
            }
        }
        do {
            try {
                s4 = ((ICUResourceBundle)icuResourceBundle.get(s2)).getString("default");
                if (n == 1) {
                    keywordValue = s4;
                    n = 0;
                }
                uLocale3 = icuResourceBundle.getULocale();
            }
            catch (MissingResourceException ex) {}
            if (uLocale3 == null) {
                icuResourceBundle = (ICUResourceBundle)icuResourceBundle.getParent();
                ++n2;
            }
        } while (icuResourceBundle != null && uLocale3 == null);
        ICUResourceBundle icuResourceBundle2 = (ICUResourceBundle)UResourceBundle.getBundleInstance(s, new ULocale(baseName));
        do {
            try {
                final ICUResourceBundle icuResourceBundle3 = (ICUResourceBundle)icuResourceBundle2.get(s2);
                icuResourceBundle3.get(keywordValue);
                uLocale4 = icuResourceBundle3.getULocale();
                if (uLocale4 != null && n3 > n2) {
                    s4 = icuResourceBundle3.getString("default");
                    icuResourceBundle2.getULocale();
                    n2 = n3;
                }
            }
            catch (MissingResourceException ex2) {}
            if (uLocale4 == null) {
                icuResourceBundle2 = (ICUResourceBundle)icuResourceBundle2.getParent();
                ++n3;
            }
        } while (icuResourceBundle2 != null && uLocale4 == null);
        if (uLocale4 == null && s4 != null && !s4.equals(keywordValue)) {
            keywordValue = s4;
            ICUResourceBundle icuResourceBundle4 = (ICUResourceBundle)UResourceBundle.getBundleInstance(s, new ULocale(baseName));
            n3 = 0;
            do {
                try {
                    final ICUResourceBundle icuResourceBundle5 = (ICUResourceBundle)icuResourceBundle4.get(s2);
                    final UResourceBundle value = icuResourceBundle5.get(keywordValue);
                    uLocale4 = icuResourceBundle4.getULocale();
                    if (!uLocale4.toString().equals(value.getLocale().toString())) {
                        uLocale4 = null;
                    }
                    if (uLocale4 != null && n3 > n2) {
                        s4 = icuResourceBundle5.getString("default");
                        icuResourceBundle4.getULocale();
                        n2 = n3;
                    }
                }
                catch (MissingResourceException ex3) {}
                if (uLocale4 == null) {
                    icuResourceBundle4 = (ICUResourceBundle)icuResourceBundle4.getParent();
                    ++n3;
                }
            } while (icuResourceBundle4 != null && uLocale4 == null);
        }
        if (uLocale4 == null) {
            throw new MissingResourceException("Could not find locale containing requested or default keyword.", s, s3 + "=" + keywordValue);
        }
        if (b && s4.equals(keywordValue) && n3 <= n2) {
            return uLocale4;
        }
        return new ULocale(uLocale4.toString() + "@" + s3 + "=" + keywordValue);
    }
    
    public static final String[] getKeywordValues(final String s, final String s2) {
        final HashSet<String> set = new HashSet<String>();
        final ULocale[] uLocaleList = createULocaleList(s, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        for (int i = 0; i < uLocaleList.length; ++i) {
            try {
                final Enumeration keys = ((ICUResourceBundle)UResourceBundle.getBundleInstance(s, uLocaleList[i]).getObject(s2)).getKeys();
                while (keys.hasMoreElements()) {
                    final String s3 = keys.nextElement();
                    if (!"default".equals(s3)) {
                        set.add(s3);
                    }
                }
            }
            catch (Throwable t) {}
        }
        return set.toArray(new String[0]);
    }
    
    public ICUResourceBundle getWithFallback(final String s) throws MissingResourceException {
        final ICUResourceBundle resourceWithFallback = findResourceWithFallback(s, this, null);
        if (resourceWithFallback == null) {
            throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + this.getType(), s, this.getKey());
        }
        if (resourceWithFallback.getType() == 0 && resourceWithFallback.getString().equals("\u2205\u2205\u2205")) {
            throw new MissingResourceException("Encountered NO_INHERITANCE_MARKER", s, this.getKey());
        }
        return resourceWithFallback;
    }
    
    public ICUResourceBundle at(final int n) {
        return (ICUResourceBundle)this.handleGet(n, null, this);
    }
    
    public ICUResourceBundle at(final String s) {
        if (this instanceof ICUResourceBundleImpl.ResourceTable) {
            return (ICUResourceBundle)this.handleGet(s, null, this);
        }
        return null;
    }
    
    public ICUResourceBundle findTopLevel(final int n) {
        return (ICUResourceBundle)super.findTopLevel(n);
    }
    
    public ICUResourceBundle findTopLevel(final String s) {
        return (ICUResourceBundle)super.findTopLevel(s);
    }
    
    public ICUResourceBundle findWithFallback(final String s) {
        return findResourceWithFallback(s, this, null);
    }
    
    public String getStringWithFallback(final String s) throws MissingResourceException {
        return this.getWithFallback(s).getString();
    }
    
    public static Set getAvailableLocaleNameSet(final String s, final ClassLoader classLoader) {
        return getAvailEntry(s, classLoader).getLocaleNameSet();
    }
    
    public static Set getFullLocaleNameSet() {
        return getFullLocaleNameSet("com/ibm/icu/impl/data/icudt51b", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
    }
    
    public static Set getFullLocaleNameSet(final String s, final ClassLoader classLoader) {
        return getAvailEntry(s, classLoader).getFullLocaleNameSet();
    }
    
    public static Set getAvailableLocaleNameSet() {
        return getAvailableLocaleNameSet("com/ibm/icu/impl/data/icudt51b", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
    }
    
    public static final ULocale[] getAvailableULocales(final String s, final ClassLoader classLoader) {
        return getAvailEntry(s, classLoader).getULocaleList();
    }
    
    public static final ULocale[] getAvailableULocales() {
        return getAvailableULocales("com/ibm/icu/impl/data/icudt51b", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
    }
    
    public static final Locale[] getAvailableLocales(final String s, final ClassLoader classLoader) {
        return getAvailEntry(s, classLoader).getLocaleList();
    }
    
    public static final Locale[] getAvailableLocales() {
        return getAvailEntry("com/ibm/icu/impl/data/icudt51b", ICUResourceBundle.ICU_DATA_CLASS_LOADER).getLocaleList();
    }
    
    public static final Locale[] getLocaleList(final ULocale[] array) {
        final ArrayList<Locale> list = new ArrayList<Locale>(array.length);
        final HashSet<Locale> set = new HashSet<Locale>();
        for (int i = 0; i < array.length; ++i) {
            final Locale locale = array[i].toLocale();
            if (!set.contains(locale)) {
                list.add(locale);
                set.add(locale);
            }
        }
        return list.toArray(new Locale[list.size()]);
    }
    
    @Override
    public Locale getLocale() {
        return this.getULocale().toLocale();
    }
    
    private static final ULocale[] createULocaleList(final String s, final ClassLoader classLoader) {
        final ICUResourceBundle icuResourceBundle = (ICUResourceBundle)((ICUResourceBundle)UResourceBundle.instantiateBundle(s, "res_index", classLoader, true)).get("InstalledLocales");
        final int size = icuResourceBundle.getSize();
        int n = 0;
        final ULocale[] array = new ULocale[size];
        final UResourceBundleIterator iterator = icuResourceBundle.getIterator();
        iterator.reset();
        while (iterator.hasNext()) {
            final String key = iterator.next().getKey();
            if (key.equals("root")) {
                array[n++] = ULocale.ROOT;
            }
            else {
                array[n++] = new ULocale(key);
            }
        }
        return array;
    }
    
    private static final Locale[] createLocaleList(final String s, final ClassLoader classLoader) {
        return getLocaleList(getAvailEntry(s, classLoader).getULocaleList());
    }
    
    private static final String[] createLocaleNameArray(final String s, final ClassLoader classLoader) {
        final ICUResourceBundle icuResourceBundle = (ICUResourceBundle)((ICUResourceBundle)UResourceBundle.instantiateBundle(s, "res_index", classLoader, true)).get("InstalledLocales");
        final int size = icuResourceBundle.getSize();
        int n = 0;
        final String[] array = new String[size];
        final UResourceBundleIterator iterator = icuResourceBundle.getIterator();
        iterator.reset();
        while (iterator.hasNext()) {
            final String key = iterator.next().getKey();
            if (key.equals("root")) {
                array[n++] = ULocale.ROOT.toString();
            }
            else {
                array[n++] = key;
            }
        }
        return array;
    }
    
    private static final List createFullLocaleNameArray(final String s, final ClassLoader classLoader) {
        return AccessController.doPrivileged((PrivilegedAction<List>)new PrivilegedAction(s, classLoader) {
            final String val$baseName;
            final ClassLoader val$root;
            
            public List run() {
                final String s = this.val$baseName.endsWith("/") ? this.val$baseName : (this.val$baseName + "/");
                List<String> list = null;
                if (!ICUConfig.get("com.ibm.icu.impl.ICUResourceBundle.skipRuntimeLocaleResourceScan", "false").equalsIgnoreCase("true")) {
                    final Enumeration<URL> resources = this.val$root.getResources(s);
                    while (resources.hasMoreElements()) {
                        final URL url = resources.nextElement();
                        final URLHandler value = URLHandler.get(url);
                        if (value != null) {
                            final ArrayList<String> list2 = new ArrayList<String>();
                            value.guide(new URLHandler.URLVisitor((List)list2) {
                                final List val$lst;
                                final ICUResourceBundle$1 this$0;
                                
                                public void visit(final String s) {
                                    if (s.endsWith(".res")) {
                                        final String substring = s.substring(0, s.length() - 4);
                                        if (substring.contains("_") && !substring.equals("res_index")) {
                                            this.val$lst.add(substring);
                                        }
                                        else if (substring.length() == 2 || substring.length() == 3) {
                                            this.val$lst.add(substring);
                                        }
                                        else if (substring.equalsIgnoreCase("root")) {
                                            this.val$lst.add(ULocale.ROOT.toString());
                                        }
                                    }
                                }
                            }, false);
                            if (list == null) {
                                list = new ArrayList<String>(list2);
                            }
                            else {
                                list.addAll(list2);
                            }
                        }
                        else {
                            if (!ICUResourceBundle.access$000()) {
                                continue;
                            }
                            System.out.println("handler for " + url + " is null");
                        }
                    }
                }
                if (list == null) {
                    final InputStream resourceAsStream = this.val$root.getResourceAsStream(s + "fullLocaleNames.lst");
                    if (resourceAsStream != null) {
                        list = new ArrayList<String>();
                        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream, "ASCII"));
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            if (line.length() != 0 && !line.startsWith("#")) {
                                if (line.equalsIgnoreCase("root")) {
                                    list.add(ULocale.ROOT.toString());
                                }
                                else {
                                    list.add(line);
                                }
                            }
                        }
                        bufferedReader.close();
                    }
                }
                return list;
            }
            
            public Object run() {
                return this.run();
            }
        });
    }
    
    private static Set createFullLocaleNameSet(final String s, final ClassLoader classLoader) {
        final List fullLocaleNameArray = createFullLocaleNameArray(s, classLoader);
        if (fullLocaleNameArray == null) {
            if (ICUResourceBundle.DEBUG) {
                System.out.println("createFullLocaleNameArray returned null");
            }
            Set<? extends E> set = (Set<? extends E>)createLocaleNameSet(s, classLoader);
            final String string = ULocale.ROOT.toString();
            if (!set.contains(string)) {
                final HashSet<String> set2 = new HashSet<String>((Collection<? extends String>)set);
                set2.add(string);
                set = (Set<? extends E>)Collections.unmodifiableSet((Set<?>)set2);
            }
            return set;
        }
        final HashSet<Object> set3 = new HashSet<Object>();
        set3.addAll(fullLocaleNameArray);
        return Collections.unmodifiableSet((Set<?>)set3);
    }
    
    private static Set createLocaleNameSet(final String s, final ClassLoader classLoader) {
        try {
            final String[] localeNameArray = createLocaleNameArray(s, classLoader);
            final HashSet<Object> set = new HashSet<Object>();
            set.addAll(Arrays.asList(localeNameArray));
            return Collections.unmodifiableSet((Set<?>)set);
        }
        catch (MissingResourceException ex) {
            if (ICUResourceBundle.DEBUG) {
                System.out.println("couldn't find index for bundleName: " + s);
                Thread.dumpStack();
            }
            return Collections.emptySet();
        }
    }
    
    private static AvailEntry getAvailEntry(final String s, final ClassLoader classLoader) {
        return (AvailEntry)ICUResourceBundle.GET_AVAILABLE_CACHE.getInstance(s, classLoader);
    }
    
    protected static final ICUResourceBundle findResourceWithFallback(String s, final UResourceBundle uResourceBundle, UResourceBundle uResourceBundle2) {
        ICUResourceBundle resourceWithFallback = null;
        if (uResourceBundle2 == null) {
            uResourceBundle2 = uResourceBundle;
        }
        ICUResourceBundle icuResourceBundle = (ICUResourceBundle)uResourceBundle;
        for (String s2 = (((ICUResourceBundle)uResourceBundle).resPath.length() > 0) ? ((ICUResourceBundle)uResourceBundle).resPath : ""; icuResourceBundle != null; icuResourceBundle = (ICUResourceBundle)icuResourceBundle.getParent(), s = ((s2.length() > 0) ? (s2 + "/" + s) : s), s2 = "") {
            if (s.indexOf(47) == -1) {
                resourceWithFallback = (ICUResourceBundle)icuResourceBundle.handleGet(s, null, uResourceBundle2);
                if (resourceWithFallback != null) {
                    break;
                }
            }
            else {
                ICUResourceBundle icuResourceBundle2 = icuResourceBundle;
                final StringTokenizer stringTokenizer = new StringTokenizer(s, "/");
                while (stringTokenizer.hasMoreTokens()) {
                    resourceWithFallback = findResourceWithFallback(stringTokenizer.nextToken(), icuResourceBundle2, uResourceBundle2);
                    if (resourceWithFallback == null) {
                        break;
                    }
                    icuResourceBundle2 = resourceWithFallback;
                }
                if (resourceWithFallback != null) {
                    break;
                }
            }
        }
        if (resourceWithFallback != null) {
            resourceWithFallback.setLoadingStatus(((ICUResourceBundle)uResourceBundle2).getLocaleID());
        }
        return resourceWithFallback;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof ICUResourceBundle) {
            final ICUResourceBundle icuResourceBundle = (ICUResourceBundle)o;
            if (this.getBaseName().equals(icuResourceBundle.getBaseName()) && this.getLocaleID().equals(icuResourceBundle.getLocaleID())) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    public static UResourceBundle getBundleInstance(final String s, final String s2, final ClassLoader classLoader, final boolean b) {
        final UResourceBundle instantiateBundle = instantiateBundle(s, s2, classLoader, b);
        if (instantiateBundle == null) {
            throw new MissingResourceException("Could not find the bundle " + s + "/" + s2 + ".res", "", "");
        }
        return instantiateBundle;
    }
    
    protected static synchronized UResourceBundle instantiateBundle(final String s, final String s2, final ClassLoader classLoader, final boolean b) {
        final ULocale default1 = ULocale.getDefault();
        String baseName = s2;
        if (baseName.indexOf(64) >= 0) {
            baseName = ULocale.getBaseName(s2);
        }
        final String fullName = ICUResourceBundleReader.getFullName(s, baseName);
        ICUResourceBundle icuResourceBundle = (ICUResourceBundle)UResourceBundle.loadFromCache(classLoader, fullName, default1);
        final String s3 = (s.indexOf(46) == -1) ? "root" : "";
        final String baseName2 = default1.getBaseName();
        if (baseName.equals("")) {
            baseName = s3;
        }
        if (ICUResourceBundle.DEBUG) {
            System.out.println("Creating " + fullName + " currently b is " + icuResourceBundle);
        }
        if (icuResourceBundle == null) {
            icuResourceBundle = createBundle(s, baseName, classLoader);
            if (ICUResourceBundle.DEBUG) {
                System.out.println("The bundle created is: " + icuResourceBundle + " and disableFallback=" + b + " and bundle.getNoFallback=" + (icuResourceBundle != null && icuResourceBundle.getNoFallback()));
            }
            if (b || (icuResourceBundle != null && icuResourceBundle.getNoFallback())) {
                return UResourceBundle.addToCache(classLoader, fullName, default1, icuResourceBundle);
            }
            if (icuResourceBundle == null) {
                final int lastIndex = baseName.lastIndexOf(95);
                if (lastIndex != -1) {
                    final String substring = baseName.substring(0, lastIndex);
                    icuResourceBundle = (ICUResourceBundle)instantiateBundle(s, substring, classLoader, b);
                    if (icuResourceBundle != null && icuResourceBundle.getULocale().getName().equals(substring)) {
                        icuResourceBundle.setLoadingStatus(1);
                    }
                }
                else if (baseName2.indexOf(baseName) == -1) {
                    icuResourceBundle = (ICUResourceBundle)instantiateBundle(s, baseName2, classLoader, b);
                    if (icuResourceBundle != null) {
                        icuResourceBundle.setLoadingStatus(3);
                    }
                }
                else if (s3.length() != 0) {
                    icuResourceBundle = createBundle(s, s3, classLoader);
                    if (icuResourceBundle != null) {
                        icuResourceBundle.setLoadingStatus(2);
                    }
                }
            }
            else {
                ResourceBundle parent = null;
                final String localeID = icuResourceBundle.getLocaleID();
                final int lastIndex2 = localeID.lastIndexOf(95);
                icuResourceBundle = (ICUResourceBundle)UResourceBundle.addToCache(classLoader, fullName, default1, icuResourceBundle);
                if (icuResourceBundle.getTableResource("%%Parent") != -1) {
                    parent = instantiateBundle(s, icuResourceBundle.getString("%%Parent"), classLoader, b);
                }
                else if (lastIndex2 != -1) {
                    parent = instantiateBundle(s, localeID.substring(0, lastIndex2), classLoader, b);
                }
                else if (!localeID.equals(s3)) {
                    parent = instantiateBundle(s, s3, classLoader, true);
                }
                if (!icuResourceBundle.equals(parent)) {
                    icuResourceBundle.setParent(parent);
                }
            }
        }
        return icuResourceBundle;
    }
    
    UResourceBundle get(final String s, final HashMap hashMap, final UResourceBundle uResourceBundle) {
        ICUResourceBundle icuResourceBundle = (ICUResourceBundle)this.handleGet(s, hashMap, uResourceBundle);
        if (icuResourceBundle == null) {
            icuResourceBundle = (ICUResourceBundle)this.getParent();
            if (icuResourceBundle != null) {
                icuResourceBundle = (ICUResourceBundle)icuResourceBundle.get(s, hashMap, uResourceBundle);
            }
            if (icuResourceBundle == null) {
                throw new MissingResourceException("Can't find resource for bundle " + ICUResourceBundleReader.getFullName(this.getBaseName(), this.getLocaleID()) + ", key " + s, this.getClass().getName(), s);
            }
        }
        icuResourceBundle.setLoadingStatus(((ICUResourceBundle)uResourceBundle).getLocaleID());
        return icuResourceBundle;
    }
    
    public static ICUResourceBundle createBundle(final String s, final String s2, final ClassLoader classLoader) {
        final ICUResourceBundleReader reader = ICUResourceBundleReader.getReader(s, s2, classLoader);
        if (reader == null) {
            return null;
        }
        return getBundle(reader, s, s2, classLoader);
    }
    
    @Override
    protected String getLocaleID() {
        return this.localeID;
    }
    
    @Override
    protected String getBaseName() {
        return this.baseName;
    }
    
    @Override
    public ULocale getULocale() {
        return this.ulocale;
    }
    
    public UResourceBundle getParent() {
        return (UResourceBundle)this.parent;
    }
    
    @Override
    protected void setParent(final ResourceBundle parent) {
        this.parent = parent;
    }
    
    @Override
    public String getKey() {
        return this.key;
    }
    
    @Override
    public int getType() {
        return ICUResourceBundle.gPublicTypes[ICUResourceBundleReader.RES_GET_TYPE(this.resource)];
    }
    
    private boolean getNoFallback() {
        return this.reader.getNoFallback();
    }
    
    private static ICUResourceBundle getBundle(final ICUResourceBundleReader icuResourceBundleReader, final String baseName, final String localeID, final ClassLoader loader) {
        final int rootResource = icuResourceBundleReader.getRootResource();
        if (ICUResourceBundle.gPublicTypes[ICUResourceBundleReader.RES_GET_TYPE(rootResource)] != 2) {
            throw new IllegalStateException("Invalid format error");
        }
        final ICUResourceBundleImpl.ResourceTable resourceTable = new ICUResourceBundleImpl.ResourceTable(icuResourceBundleReader, null, "", rootResource, null);
        resourceTable.baseName = baseName;
        resourceTable.localeID = localeID;
        resourceTable.ulocale = new ULocale(localeID);
        resourceTable.loader = loader;
        final UResourceBundle handleGetImpl = resourceTable.handleGetImpl("%%ALIAS", null, resourceTable, null, null);
        if (handleGetImpl != null) {
            return (ICUResourceBundle)UResourceBundle.getBundleInstance(baseName, handleGetImpl.getString());
        }
        return resourceTable;
    }
    
    protected ICUResourceBundle(final ICUResourceBundleReader reader, final String key, final String resPath, final int resource, final ICUResourceBundle icuResourceBundle) {
        this.loadingStatus = -1;
        this.reader = reader;
        this.key = key;
        this.resPath = resPath;
        this.resource = resource;
        if (icuResourceBundle != null) {
            this.baseName = icuResourceBundle.baseName;
            this.localeID = icuResourceBundle.localeID;
            this.ulocale = icuResourceBundle.ulocale;
            this.loader = icuResourceBundle.loader;
            this.parent = icuResourceBundle.parent;
        }
    }
    
    private String getAliasValue(final int n) {
        final String alias = this.reader.getAlias(n);
        return (alias != null) ? alias : "";
    }
    
    protected ICUResourceBundle findResource(final String s, final String s2, final int n, HashMap hashMap, final UResourceBundle uResourceBundle) {
        ClassLoader classLoader = this.loader;
        final String aliasValue = this.getAliasValue(n);
        if (hashMap == null) {
            hashMap = new HashMap<String, String>();
        }
        if (hashMap.get(aliasValue) != null) {
            throw new IllegalArgumentException("Circular references in the resource bundles");
        }
        hashMap.put(aliasValue, "");
        String s3;
        String s4;
        String s5;
        if (aliasValue.indexOf(47) == 0) {
            final int index = aliasValue.indexOf(47, 1);
            final int index2 = aliasValue.indexOf(47, index + 1);
            s3 = aliasValue.substring(1, index);
            if (index2 < 0) {
                s4 = aliasValue.substring(index + 1);
                s5 = s2;
            }
            else {
                s4 = aliasValue.substring(index + 1, index2);
                s5 = aliasValue.substring(index2 + 1, aliasValue.length());
            }
            if (s3.equals("ICUDATA")) {
                s3 = "com/ibm/icu/impl/data/icudt51b";
                classLoader = ICUResourceBundle.ICU_DATA_CLASS_LOADER;
            }
            else if (s3.indexOf("ICUDATA") > -1) {
                final int index3 = s3.indexOf(45);
                if (index3 > -1) {
                    s3 = "com/ibm/icu/impl/data/icudt51b/" + s3.substring(index3 + 1, s3.length());
                    classLoader = ICUResourceBundle.ICU_DATA_CLASS_LOADER;
                }
            }
        }
        else {
            final int index4 = aliasValue.indexOf(47);
            if (index4 != -1) {
                s4 = aliasValue.substring(0, index4);
                s5 = aliasValue.substring(index4 + 1);
            }
            else {
                s4 = aliasValue;
                s5 = s2;
            }
            s3 = this.baseName;
        }
        ICUResourceBundle resourceWithFallback = null;
        if (s3.equals("LOCALE")) {
            final String baseName = this.baseName;
            final String substring = aliasValue.substring(8, aliasValue.length());
            final ICUResourceBundle icuResourceBundle = (ICUResourceBundle)getBundleInstance(baseName, ((ICUResourceBundle)uResourceBundle).getLocaleID(), classLoader, false);
            if (icuResourceBundle != null) {
                resourceWithFallback = findResourceWithFallback(substring, icuResourceBundle, null);
            }
        }
        else {
            ICUResourceBundle icuResourceBundle2;
            if (s4 == null) {
                icuResourceBundle2 = (ICUResourceBundle)getBundleInstance(s3, "", classLoader, false);
            }
            else {
                icuResourceBundle2 = (ICUResourceBundle)getBundleInstance(s3, s4, classLoader, false);
            }
            final StringTokenizer stringTokenizer = new StringTokenizer(s5, "/");
            ICUResourceBundle icuResourceBundle3 = icuResourceBundle2;
            while (stringTokenizer.hasMoreTokens()) {
                resourceWithFallback = (ICUResourceBundle)icuResourceBundle3.get(stringTokenizer.nextToken(), hashMap, uResourceBundle);
                if (resourceWithFallback == null) {
                    break;
                }
                icuResourceBundle3 = resourceWithFallback;
            }
        }
        if (resourceWithFallback == null) {
            throw new MissingResourceException(this.localeID, this.baseName, s);
        }
        return resourceWithFallback;
    }
    
    protected void createLookupCache() {
        this.lookup = new SimpleCache(1, Math.max(this.getSize() * 2, 64));
    }
    
    @Override
    protected UResourceBundle handleGet(final String s, final HashMap hashMap, final UResourceBundle uResourceBundle) {
        UResourceBundle handleGetImpl = null;
        if (this.lookup != null) {
            handleGetImpl = (UResourceBundle)this.lookup.get(s);
        }
        if (handleGetImpl == null) {
            final int[] array = { 0 };
            final boolean[] array2 = { false };
            handleGetImpl = this.handleGetImpl(s, hashMap, uResourceBundle, array, array2);
            if (handleGetImpl != null && this.lookup != null && !array2[0]) {
                this.lookup.put(s, handleGetImpl);
                this.lookup.put(array[0], handleGetImpl);
            }
        }
        return handleGetImpl;
    }
    
    @Override
    protected UResourceBundle handleGet(final int n, final HashMap hashMap, final UResourceBundle uResourceBundle) {
        UResourceBundle handleGetImpl = null;
        Object value = null;
        if (this.lookup != null) {
            value = n;
            handleGetImpl = (UResourceBundle)this.lookup.get(value);
        }
        if (handleGetImpl == null) {
            final boolean[] array = { false };
            handleGetImpl = this.handleGetImpl(n, hashMap, uResourceBundle, array);
            if (handleGetImpl != null && this.lookup != null && !array[0]) {
                this.lookup.put(handleGetImpl.getKey(), handleGetImpl);
                this.lookup.put(value, handleGetImpl);
            }
        }
        return handleGetImpl;
    }
    
    protected UResourceBundle handleGetImpl(final String s, final HashMap hashMap, final UResourceBundle uResourceBundle, final int[] array, final boolean[] array2) {
        return null;
    }
    
    protected UResourceBundle handleGetImpl(final int n, final HashMap hashMap, final UResourceBundle uResourceBundle, final boolean[] array) {
        return null;
    }
    
    @Deprecated
    protected int getTableResource(final String s) {
        return -1;
    }
    
    protected int getTableResource(final int n) {
        return -1;
    }
    
    @Deprecated
    public boolean isAlias(final int n) {
        return ICUResourceBundleReader.RES_GET_TYPE(this.getTableResource(n)) == 3;
    }
    
    @Deprecated
    public boolean isAlias() {
        return ICUResourceBundleReader.RES_GET_TYPE(this.resource) == 3;
    }
    
    @Deprecated
    public boolean isAlias(final String s) {
        return ICUResourceBundleReader.RES_GET_TYPE(this.getTableResource(s)) == 3;
    }
    
    @Deprecated
    public String getAliasPath(final int n) {
        return this.getAliasValue(this.getTableResource(n));
    }
    
    @Deprecated
    public String getAliasPath() {
        return this.getAliasValue(this.resource);
    }
    
    @Deprecated
    public String getAliasPath(final String s) {
        return this.getAliasValue(this.getTableResource(s));
    }
    
    protected String getKey(final int n) {
        return null;
    }
    
    @Deprecated
    public Enumeration getKeysSafe() {
        if (!ICUResourceBundleReader.URES_IS_TABLE(this.resource)) {
            return this.getKeys();
        }
        final ArrayList<Object> list = new ArrayList<Object>();
        for (int size = this.getSize(), i = 0; i < size; ++i) {
            list.add(this.getKey(i));
        }
        return Collections.enumeration(list);
    }
    
    @Override
    protected Enumeration handleGetKeys() {
        return Collections.enumeration((Collection<Object>)this.handleKeySet());
    }
    
    @Override
    protected boolean isTopLevelResource() {
        return this.resPath.length() == 0;
    }
    
    public UResourceBundle findTopLevel(final int n) {
        return this.findTopLevel(n);
    }
    
    public UResourceBundle findTopLevel(final String s) {
        return this.findTopLevel(s);
    }
    
    static boolean access$000() {
        return ICUResourceBundle.DEBUG;
    }
    
    static ULocale[] access$100(final String s, final ClassLoader classLoader) {
        return createULocaleList(s, classLoader);
    }
    
    static Locale[] access$200(final String s, final ClassLoader classLoader) {
        return createLocaleList(s, classLoader);
    }
    
    static Set access$300(final String s, final ClassLoader classLoader) {
        return createLocaleNameSet(s, classLoader);
    }
    
    static Set access$400(final String s, final ClassLoader classLoader) {
        return createFullLocaleNameSet(s, classLoader);
    }
    
    static {
        $assertionsDisabled = !ICUResourceBundle.class.desiredAssertionStatus();
        ClassLoader icu_DATA_CLASS_LOADER = ICUData.class.getClassLoader();
        if (icu_DATA_CLASS_LOADER == null) {
            icu_DATA_CLASS_LOADER = Utility.getFallbackClassLoader();
        }
        ICU_DATA_CLASS_LOADER = icu_DATA_CLASS_LOADER;
        DEBUG = ICUDebug.enabled("localedata");
        ICUResourceBundle.GET_AVAILABLE_CACHE = new SoftCache() {
            protected AvailEntry createInstance(final String s, final ClassLoader classLoader) {
                return new AvailEntry(s, classLoader);
            }
            
            @Override
            protected Object createInstance(final Object o, final Object o2) {
                return this.createInstance((String)o, (ClassLoader)o2);
            }
        };
        gPublicTypes = new int[] { 0, 1, 2, 3, 2, 2, 0, 7, 8, 8, -1, -1, -1, -1, 14, -1 };
    }
    
    private static final class AvailEntry
    {
        private String prefix;
        private ClassLoader loader;
        private ULocale[] ulocales;
        private Locale[] locales;
        private Set nameSet;
        private Set fullNameSet;
        
        AvailEntry(final String prefix, final ClassLoader loader) {
            this.prefix = prefix;
            this.loader = loader;
        }
        
        ULocale[] getULocaleList() {
            if (this.ulocales == null) {
                // monitorenter(this)
                if (this.ulocales == null) {
                    this.ulocales = ICUResourceBundle.access$100(this.prefix, this.loader);
                }
            }
            // monitorexit(this)
            return this.ulocales;
        }
        
        Locale[] getLocaleList() {
            if (this.locales == null) {
                // monitorenter(this)
                if (this.locales == null) {
                    this.locales = ICUResourceBundle.access$200(this.prefix, this.loader);
                }
            }
            // monitorexit(this)
            return this.locales;
        }
        
        Set getLocaleNameSet() {
            if (this.nameSet == null) {
                // monitorenter(this)
                if (this.nameSet == null) {
                    this.nameSet = ICUResourceBundle.access$300(this.prefix, this.loader);
                }
            }
            // monitorexit(this)
            return this.nameSet;
        }
        
        Set getFullLocaleNameSet() {
            if (this.fullNameSet == null) {
                // monitorenter(this)
                if (this.fullNameSet == null) {
                    this.fullNameSet = ICUResourceBundle.access$400(this.prefix, this.loader);
                }
            }
            // monitorexit(this)
            return this.fullNameSet;
        }
    }
}
