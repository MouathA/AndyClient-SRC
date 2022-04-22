package com.viaversion.viaversion.libs.javassist.scopedpool;

import com.viaversion.viaversion.libs.javassist.*;

public class ScopedClassPoolFactoryImpl implements ScopedClassPoolFactory
{
    @Override
    public ScopedClassPool create(final ClassLoader classLoader, final ClassPool classPool, final ScopedClassPoolRepository scopedClassPoolRepository) {
        return new ScopedClassPool(classLoader, classPool, scopedClassPoolRepository, false);
    }
    
    @Override
    public ScopedClassPool create(final ClassPool classPool, final ScopedClassPoolRepository scopedClassPoolRepository) {
        return new ScopedClassPool(null, classPool, scopedClassPoolRepository, true);
    }
}
