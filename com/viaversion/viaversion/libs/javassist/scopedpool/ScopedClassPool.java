package com.viaversion.viaversion.libs.javassist.scopedpool;

import java.lang.ref.*;
import java.util.*;
import java.security.*;
import com.viaversion.viaversion.libs.javassist.*;

public class ScopedClassPool extends ClassPool
{
    protected ScopedClassPoolRepository repository;
    protected Reference classLoader;
    protected LoaderClassPath classPath;
    protected Map softcache;
    boolean isBootstrapCl;
    
    protected ScopedClassPool(final ClassLoader classLoader, final ClassPool classPool, final ScopedClassPoolRepository scopedClassPoolRepository) {
        this(classLoader, classPool, scopedClassPoolRepository, false);
    }
    
    protected ScopedClassPool(final ClassLoader classLoader, final ClassPool classPool, final ScopedClassPoolRepository repository, final boolean b) {
        super(classPool);
        this.softcache = new SoftValueHashMap();
        this.isBootstrapCl = true;
        this.repository = repository;
        this.classLoader = new WeakReference(classLoader);
        if (classLoader != null) {
            this.insertClassPath(this.classPath = new LoaderClassPath(classLoader));
        }
        this.childFirstLookup = true;
        if (!b && classLoader == null) {
            this.isBootstrapCl = true;
        }
    }
    
    @Override
    public ClassLoader getClassLoader() {
        final ClassLoader classLoader0 = this.getClassLoader0();
        if (classLoader0 == null && !this.isBootstrapCl) {
            throw new IllegalStateException("ClassLoader has been garbage collected");
        }
        return classLoader0;
    }
    
    protected ClassLoader getClassLoader0() {
        return this.classLoader.get();
    }
    
    public void close() {
        this.removeClassPath(this.classPath);
        this.classes.clear();
        this.softcache.clear();
    }
    
    public synchronized void flushClass(final String s) {
        this.classes.remove(s);
        this.softcache.remove(s);
    }
    
    public synchronized void soften(final CtClass ctClass) {
        if (this.repository.isPrune()) {
            ctClass.prune();
        }
        this.classes.remove(ctClass.getName());
        this.softcache.put(ctClass.getName(), ctClass);
    }
    
    public boolean isUnloadedClassLoader() {
        return false;
    }
    
    @Override
    protected CtClass getCached(final String s) {
        CtClass ctClass = this.getCachedLocally(s);
        if (ctClass == null) {
            final ClassLoader classLoader0 = this.getClassLoader0();
            if (classLoader0 != null) {
                final int lastIndex = s.lastIndexOf(36);
                String s2;
                if (lastIndex < 0) {
                    s2 = s.replaceAll("[\\.]", "/") + ".class";
                }
                else {
                    s2 = s.substring(0, lastIndex).replaceAll("[\\.]", "/") + s.substring(lastIndex) + ".class";
                }
                final boolean b = classLoader0.getResource(s2) != null;
            }
            if (!false) {
                final Map registeredCLs = this.repository.getRegisteredCLs();
                // monitorenter(map = registeredCLs)
                for (final ScopedClassPool scopedClassPool : registeredCLs.values()) {
                    if (scopedClassPool.isUnloadedClassLoader()) {
                        this.repository.unregisterClassLoader(scopedClassPool.getClassLoader());
                    }
                    else {
                        ctClass = scopedClassPool.getCachedLocally(s);
                        if (ctClass != null) {
                            // monitorexit(map)
                            return ctClass;
                        }
                        continue;
                    }
                }
            }
            // monitorexit(map)
        }
        return ctClass;
    }
    
    @Override
    protected void cacheCtClass(final String s, final CtClass ctClass, final boolean b) {
        if (b) {
            super.cacheCtClass(s, ctClass, b);
        }
        else {
            if (this.repository.isPrune()) {
                ctClass.prune();
            }
            this.softcache.put(s, ctClass);
        }
    }
    
    public void lockInCache(final CtClass ctClass) {
        super.cacheCtClass(ctClass.getName(), ctClass, false);
    }
    
    protected CtClass getCachedLocally(final String s) {
        final CtClass ctClass = this.classes.get(s);
        if (ctClass != null) {
            return ctClass;
        }
        // monitorenter(softcache = this.softcache)
        // monitorexit(softcache)
        return this.softcache.get(s);
    }
    
    public synchronized CtClass getLocally(final String s) throws NotFoundException {
        this.softcache.remove(s);
        CtClass ctClass = this.classes.get(s);
        if (ctClass == null) {
            ctClass = this.createCtClass(s, true);
            if (ctClass == null) {
                throw new NotFoundException(s);
            }
            super.cacheCtClass(s, ctClass, false);
        }
        return ctClass;
    }
    
    @Override
    public Class toClass(final CtClass ctClass, final ClassLoader classLoader, final ProtectionDomain protectionDomain) throws CannotCompileException {
        this.lockInCache(ctClass);
        return super.toClass(ctClass, this.getClassLoader0(), protectionDomain);
    }
    
    static {
        ClassPool.doPruning = false;
        ClassPool.releaseUnmodifiedClassFile = false;
    }
}
