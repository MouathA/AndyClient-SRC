package com.viaversion.viaversion.libs.javassist.scopedpool;

import com.viaversion.viaversion.libs.javassist.*;

public interface ScopedClassPoolFactory
{
    ScopedClassPool create(final ClassLoader p0, final ClassPool p1, final ScopedClassPoolRepository p2);
    
    ScopedClassPool create(final ClassPool p0, final ScopedClassPoolRepository p1);
}
