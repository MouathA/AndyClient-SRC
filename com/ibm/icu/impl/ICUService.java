package com.ibm.icu.impl;

import java.lang.ref.*;
import java.io.*;
import com.ibm.icu.util.*;
import java.util.*;

public class ICUService extends ICUNotifier
{
    protected final String name;
    private static final boolean DEBUG;
    private final ICURWLock factoryLock;
    private final List factories;
    private int defaultSize;
    private SoftReference cacheref;
    private SoftReference idref;
    private LocaleRef dnref;
    
    public ICUService() {
        this.factoryLock = new ICURWLock();
        this.factories = new ArrayList();
        this.defaultSize = 0;
        this.name = "";
    }
    
    public ICUService(final String name) {
        this.factoryLock = new ICURWLock();
        this.factories = new ArrayList();
        this.defaultSize = 0;
        this.name = name;
    }
    
    public Object get(final String s) {
        return this.getKey(this.createKey(s), null);
    }
    
    public Object get(final String s, final String[] array) {
        if (s == null) {
            throw new NullPointerException("descriptor must not be null");
        }
        return this.getKey(this.createKey(s), array);
    }
    
    public Object getKey(final Key key) {
        return this.getKey(key, null);
    }
    
    public Object getKey(final Key key, final String[] array) {
        return this.getKey(key, array, null);
    }
    
    public Object getKey(final Key key, final String[] array, final Factory factory) {
        if (this.factories.size() == 0) {
            return this.handleDefault(key, array);
        }
        if (ICUService.DEBUG) {
            System.out.println("Service: " + this.name + " key: " + key.canonicalID());
        }
        if (key != null) {
            this.factoryLock.acquireRead();
            Object synchronizedMap = null;
            SoftReference cacheref = this.cacheref;
            if (cacheref != null) {
                if (ICUService.DEBUG) {
                    System.out.println("Service " + this.name + " ref exists");
                }
                synchronizedMap = cacheref.get();
            }
            if (synchronizedMap == null) {
                if (ICUService.DEBUG) {
                    System.out.println("Service " + this.name + " cache was empty");
                }
                synchronizedMap = Collections.synchronizedMap((Map<K, CacheEntry>)new HashMap<Object, CacheEntry>());
                cacheref = new SoftReference<Map<K, CacheEntry>>((Map<K, CacheEntry>)synchronizedMap);
            }
            ArrayList<String> list = null;
            final int size = this.factories.size();
            int n = 0;
            if (factory != null) {
                while (0 < size && factory != this.factories.get(0)) {
                    ++n;
                }
                if (!false) {
                    throw new IllegalStateException("Factory " + factory + "not registered with service: " + this);
                }
            }
            CacheEntry cacheEntry = null;
        Label_0663:
            do {
                final String currentDescriptor = key.currentDescriptor();
                if (ICUService.DEBUG) {
                    final PrintStream out = System.out;
                    final StringBuilder append = new StringBuilder().append(this.name).append("[");
                    final int n2 = 0;
                    int n3 = 0;
                    ++n3;
                    out.println(append.append(n2).append("] looking for: ").append(currentDescriptor).toString());
                }
                cacheEntry = ((Map<K, CacheEntry>)synchronizedMap).get(currentDescriptor);
                if (cacheEntry != null) {
                    if (ICUService.DEBUG) {
                        System.out.println(this.name + " found with descriptor: " + currentDescriptor);
                        break;
                    }
                    break;
                }
                else {
                    if (ICUService.DEBUG) {
                        System.out.println("did not find: " + currentDescriptor + " in cache");
                    }
                    while (0 < size) {
                        final List factories = this.factories;
                        final int n4 = 0;
                        ++n;
                        final Factory factory2 = factories.get(n4);
                        if (ICUService.DEBUG) {
                            System.out.println("trying factory[" + -1 + "] " + factory2.toString());
                        }
                        final Object create = factory2.create(key, this);
                        if (create != null) {
                            cacheEntry = new CacheEntry(currentDescriptor, create);
                            if (ICUService.DEBUG) {
                                System.out.println(this.name + " factory supported: " + currentDescriptor + ", caching");
                                break Label_0663;
                            }
                            break Label_0663;
                        }
                        else {
                            if (!ICUService.DEBUG) {
                                continue;
                            }
                            System.out.println("factory did not support: " + currentDescriptor);
                        }
                    }
                    if (list == null) {
                        list = new ArrayList<String>(5);
                    }
                    list.add(currentDescriptor);
                }
            } while (key.fallback());
            if (cacheEntry != null) {
                if (false) {
                    if (ICUService.DEBUG) {
                        System.out.println("caching '" + cacheEntry.actualDescriptor + "'");
                    }
                    ((Map<K, CacheEntry>)synchronizedMap).put((K)cacheEntry.actualDescriptor, cacheEntry);
                    if (list != null) {
                        for (final String s : list) {
                            if (ICUService.DEBUG) {
                                System.out.println(this.name + " adding descriptor: '" + s + "' for actual: '" + cacheEntry.actualDescriptor + "'");
                            }
                            ((Map<K, CacheEntry>)synchronizedMap).put((K)s, cacheEntry);
                        }
                    }
                    this.cacheref = cacheref;
                }
                if (array != null) {
                    if (cacheEntry.actualDescriptor.indexOf("/") == 0) {
                        array[0] = cacheEntry.actualDescriptor.substring(1);
                    }
                    else {
                        array[0] = cacheEntry.actualDescriptor;
                    }
                }
                if (ICUService.DEBUG) {
                    System.out.println("found in service: " + this.name);
                }
                final Object service = cacheEntry.service;
                this.factoryLock.releaseRead();
                return service;
            }
            this.factoryLock.releaseRead();
        }
        if (ICUService.DEBUG) {
            System.out.println("not found in service: " + this.name);
        }
        return this.handleDefault(key, array);
    }
    
    protected Object handleDefault(final Key key, final String[] array) {
        return null;
    }
    
    public Set getVisibleIDs() {
        return this.getVisibleIDs(null);
    }
    
    public Set getVisibleIDs(final String s) {
        Set<String> keySet = (Set<String>)this.getVisibleIDMap().keySet();
        final Key key = this.createKey(s);
        if (key != null) {
            final HashSet set = new HashSet<String>(keySet.size());
            for (final String s2 : keySet) {
                if (key.isFallbackOf(s2)) {
                    set.add(s2);
                }
            }
            keySet = (Set<String>)set;
        }
        return keySet;
    }
    
    private Map getVisibleIDMap() {
        Map<Object, Object> unmodifiableMap = null;
        SoftReference softReference = this.idref;
        if (softReference != null) {
            unmodifiableMap = softReference.get();
        }
        while (unmodifiableMap == null) {
            // monitorenter(this)
            if (softReference == this.idref || this.idref == null) {
                this.factoryLock.acquireRead();
                final HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                final ListIterator<Factory> listIterator = this.factories.listIterator(this.factories.size());
                while (listIterator.hasPrevious()) {
                    listIterator.previous().updateVisibleIDs(hashMap);
                }
                unmodifiableMap = Collections.unmodifiableMap((Map<?, ?>)hashMap);
                this.idref = new SoftReference(unmodifiableMap);
                this.factoryLock.releaseRead();
            }
            else {
                softReference = this.idref;
                unmodifiableMap = softReference.get();
            }
        }
        // monitorexit(this)
        return unmodifiableMap;
    }
    
    public String getDisplayName(final String s) {
        return this.getDisplayName(s, ULocale.getDefault(ULocale.Category.DISPLAY));
    }
    
    public String getDisplayName(final String s, final ULocale uLocale) {
        final Map visibleIDMap = this.getVisibleIDMap();
        final Factory factory = visibleIDMap.get(s);
        if (factory != null) {
            return factory.getDisplayName(s, uLocale);
        }
        final Key key = this.createKey(s);
        while (key.fallback()) {
            final Factory factory2 = visibleIDMap.get(key.currentID());
            if (factory2 != null) {
                return factory2.getDisplayName(s, uLocale);
            }
        }
        return null;
    }
    
    public SortedMap getDisplayNames() {
        return this.getDisplayNames(ULocale.getDefault(ULocale.Category.DISPLAY), null, null);
    }
    
    public SortedMap getDisplayNames(final ULocale uLocale) {
        return this.getDisplayNames(uLocale, null, null);
    }
    
    public SortedMap getDisplayNames(final ULocale uLocale, final Comparator comparator) {
        return this.getDisplayNames(uLocale, comparator, null);
    }
    
    public SortedMap getDisplayNames(final ULocale uLocale, final String s) {
        return this.getDisplayNames(uLocale, null, s);
    }
    
    public SortedMap getDisplayNames(final ULocale uLocale, final Comparator comparator, final String s) {
        Object o = null;
        LocaleRef localeRef = this.dnref;
        if (localeRef != null) {
            o = localeRef.get(uLocale, comparator);
        }
        while (o == null) {
            // monitorenter(this)
            if (localeRef == this.dnref || this.dnref == null) {
                final TreeMap<Object, String> treeMap = new TreeMap<Object, String>(comparator);
                for (final Map.Entry<String, V> entry : this.getVisibleIDMap().entrySet()) {
                    final String s2 = entry.getKey();
                    treeMap.put(((Factory)entry.getValue()).getDisplayName(s2, uLocale), s2);
                }
                o = Collections.unmodifiableSortedMap((SortedMap<K, ? extends String>)treeMap);
                this.dnref = new LocaleRef((SortedMap)o, uLocale, comparator);
            }
            else {
                localeRef = this.dnref;
                o = localeRef.get(uLocale, comparator);
            }
        }
        // monitorexit(this)
        final Key key = this.createKey(s);
        if (key == null) {
            return (SortedMap)o;
        }
        final TreeMap treeMap2 = new TreeMap<Object, String>((SortedMap<K, ? extends String>)o);
        final Iterator<Map.Entry<K, String>> iterator2 = treeMap2.entrySet().iterator();
        while (iterator2.hasNext()) {
            if (!key.isFallbackOf(iterator2.next().getValue())) {
                iterator2.remove();
            }
        }
        return treeMap2;
    }
    
    public final List factories() {
        this.factoryLock.acquireRead();
        final ArrayList list = new ArrayList(this.factories);
        this.factoryLock.releaseRead();
        return list;
    }
    
    public Factory registerObject(final Object o, final String s) {
        return this.registerObject(o, s, true);
    }
    
    public Factory registerObject(final Object o, final String s, final boolean b) {
        return this.registerFactory(new SimpleFactory(o, this.createKey(s).canonicalID(), b));
    }
    
    public final Factory registerFactory(final Factory factory) {
        if (factory == null) {
            throw new NullPointerException();
        }
        this.factoryLock.acquireWrite();
        this.factories.add(0, factory);
        this.clearCaches();
        this.factoryLock.releaseWrite();
        this.notifyChanged();
        return factory;
    }
    
    public final boolean unregisterFactory(final Factory factory) {
        if (factory == null) {
            throw new NullPointerException();
        }
        this.factoryLock.acquireWrite();
        if (this.factories.remove(factory)) {
            this.clearCaches();
        }
        this.factoryLock.releaseWrite();
        if (true) {
            this.notifyChanged();
        }
        return true;
    }
    
    public final void reset() {
        this.factoryLock.acquireWrite();
        this.reInitializeFactories();
        this.clearCaches();
        this.factoryLock.releaseWrite();
        this.notifyChanged();
    }
    
    protected void reInitializeFactories() {
        this.factories.clear();
    }
    
    public boolean isDefault() {
        return this.factories.size() == this.defaultSize;
    }
    
    protected void markDefault() {
        this.defaultSize = this.factories.size();
    }
    
    public Key createKey(final String s) {
        return (s == null) ? null : new Key(s);
    }
    
    protected void clearCaches() {
        this.cacheref = null;
        this.idref = null;
        this.dnref = null;
    }
    
    protected void clearServiceCache() {
        this.cacheref = null;
    }
    
    @Override
    protected boolean acceptsListener(final EventListener eventListener) {
        return eventListener instanceof ServiceListener;
    }
    
    @Override
    protected void notifyListener(final EventListener eventListener) {
        ((ServiceListener)eventListener).serviceChanged(this);
    }
    
    public String stats() {
        final ICURWLock.Stats resetStats = this.factoryLock.resetStats();
        if (resetStats != null) {
            return resetStats.toString();
        }
        return "no stats";
    }
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return super.toString() + "{" + this.name + "}";
    }
    
    static {
        DEBUG = ICUDebug.enabled("service");
    }
    
    public interface ServiceListener extends EventListener
    {
        void serviceChanged(final ICUService p0);
    }
    
    private static class LocaleRef
    {
        private final ULocale locale;
        private SoftReference ref;
        private Comparator com;
        
        LocaleRef(final SortedMap sortedMap, final ULocale locale, final Comparator com) {
            this.locale = locale;
            this.com = com;
            this.ref = new SoftReference((T)sortedMap);
        }
        
        SortedMap get(final ULocale uLocale, final Comparator comparator) {
            final SortedMap sortedMap = this.ref.get();
            if (sortedMap != null && this.locale.equals(uLocale) && (this.com == comparator || (this.com != null && this.com.equals(comparator)))) {
                return sortedMap;
            }
            return null;
        }
    }
    
    private static final class CacheEntry
    {
        final String actualDescriptor;
        final Object service;
        
        CacheEntry(final String actualDescriptor, final Object service) {
            this.actualDescriptor = actualDescriptor;
            this.service = service;
        }
    }
    
    public static class SimpleFactory implements Factory
    {
        protected Object instance;
        protected String id;
        protected boolean visible;
        
        public SimpleFactory(final Object o, final String s) {
            this(o, s, true);
        }
        
        public SimpleFactory(final Object instance, final String id, final boolean visible) {
            if (instance == null || id == null) {
                throw new IllegalArgumentException("Instance or id is null");
            }
            this.instance = instance;
            this.id = id;
            this.visible = visible;
        }
        
        public Object create(final Key key, final ICUService icuService) {
            if (this.id.equals(key.currentID())) {
                return this.instance;
            }
            return null;
        }
        
        public void updateVisibleIDs(final Map map) {
            if (this.visible) {
                map.put(this.id, this);
            }
            else {
                map.remove(this.id);
            }
        }
        
        public String getDisplayName(final String s, final ULocale uLocale) {
            return (this.visible && this.id.equals(s)) ? s : null;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(super.toString());
            sb.append(", id: ");
            sb.append(this.id);
            sb.append(", visible: ");
            sb.append(this.visible);
            return sb.toString();
        }
    }
    
    public static class Key
    {
        private final String id;
        
        public Key(final String id) {
            this.id = id;
        }
        
        public final String id() {
            return this.id;
        }
        
        public String canonicalID() {
            return this.id;
        }
        
        public String currentID() {
            return this.canonicalID();
        }
        
        public String currentDescriptor() {
            return "/" + this.currentID();
        }
        
        public boolean fallback() {
            return false;
        }
        
        public boolean isFallbackOf(final String s) {
            return this.canonicalID().equals(s);
        }
    }
    
    public interface Factory
    {
        Object create(final Key p0, final ICUService p1);
        
        void updateVisibleIDs(final Map p0);
        
        String getDisplayName(final String p0, final ULocale p1);
    }
}
