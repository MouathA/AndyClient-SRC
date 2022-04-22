package com.viaversion.viaversion.libs.javassist.scopedpool;

import com.viaversion.viaversion.libs.javassist.*;
import java.util.*;

public class ScopedClassPoolRepositoryImpl implements ScopedClassPoolRepository
{
    private static final ScopedClassPoolRepositoryImpl instance;
    private boolean prune;
    boolean pruneWhenCached;
    protected Map registeredCLs;
    protected ClassPool classpool;
    protected ScopedClassPoolFactory factory;
    
    public static ScopedClassPoolRepository getInstance() {
        return ScopedClassPoolRepositoryImpl.instance;
    }
    
    private ScopedClassPoolRepositoryImpl() {
        this.prune = true;
        this.registeredCLs = Collections.synchronizedMap(new WeakHashMap<Object, Object>());
        this.factory = new ScopedClassPoolFactoryImpl();
        (this.classpool = ClassPool.getDefault()).insertClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
    }
    
    @Override
    public boolean isPrune() {
        return this.prune;
    }
    
    @Override
    public void setPrune(final boolean prune) {
        this.prune = prune;
    }
    
    @Override
    public ScopedClassPool createScopedClassPool(final ClassLoader classLoader, final ClassPool classPool) {
        return this.factory.create(classLoader, classPool, this);
    }
    
    @Override
    public ClassPool findClassPool(final ClassLoader classLoader) {
        if (classLoader == null) {
            return this.registerClassLoader(ClassLoader.getSystemClassLoader());
        }
        return this.registerClassLoader(classLoader);
    }
    
    @Override
    public ClassPool registerClassLoader(final ClassLoader classLoader) {
        // monitorenter(registeredCLs = this.registeredCLs)
        if (this.registeredCLs.containsKey(classLoader)) {
            // monitorexit(registeredCLs)
            return this.registeredCLs.get(classLoader);
        }
        final ScopedClassPool scopedClassPool = this.createScopedClassPool(classLoader, this.classpool);
        this.registeredCLs.put(classLoader, scopedClassPool);
        // monitorexit(registeredCLs)
        return scopedClassPool;
    }
    
    @Override
    public Map getRegisteredCLs() {
        this.clearUnregisteredClassLoaders();
        return this.registeredCLs;
    }
    
    @Override
    public void clearUnregisteredClassLoaders() {
        List<ClassLoader> list = null;
        // monitorenter(registeredCLs = this.registeredCLs)
        for (final Map.Entry<K, ScopedClassPool> entry : this.registeredCLs.entrySet()) {
            if (entry.getValue().isUnloadedClassLoader()) {
                final ClassLoader classLoader = entry.getValue().getClassLoader();
                if (classLoader != null) {
                    if (list == null) {
                        list = new ArrayList<ClassLoader>();
                    }
                    list.add(classLoader);
                }
                this.registeredCLs.remove(entry.getKey());
            }
        }
        if (list != null) {
            final Iterator<ClassLoader> iterator2 = list.iterator();
            while (iterator2.hasNext()) {
                this.unregisterClassLoader(iterator2.next());
            }
        }
    }
    // monitorexit(registeredCLs)
    
    @Override
    public void unregisterClassLoader(final ClassLoader classLoader) {
        // monitorenter(registeredCLs = this.registeredCLs)
        final ScopedClassPool scopedClassPool = this.registeredCLs.remove(classLoader);
        if (scopedClassPool != null) {
            scopedClassPool.close();
        }
    }
    // monitorexit(registeredCLs)
    
    public void insertDelegate(final ScopedClassPoolRepository scopedClassPoolRepository) {
    }
    
    @Override
    public void setClassPoolFactory(final ScopedClassPoolFactory factory) {
        this.factory = factory;
    }
    
    @Override
    public ScopedClassPoolFactory getClassPoolFactory() {
        return this.factory;
    }
    
    static {
        instance = new ScopedClassPoolRepositoryImpl();
    }
}
