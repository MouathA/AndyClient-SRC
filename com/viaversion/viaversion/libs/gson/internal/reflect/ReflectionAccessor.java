package com.viaversion.viaversion.libs.gson.internal.reflect;

import java.lang.reflect.*;
import com.viaversion.viaversion.libs.gson.internal.*;

public abstract class ReflectionAccessor
{
    private static final ReflectionAccessor instance;
    
    public abstract void makeAccessible(final AccessibleObject p0);
    
    public static ReflectionAccessor getInstance() {
        return ReflectionAccessor.instance;
    }
    
    static {
        instance = ((JavaVersion.getMajorJavaVersion() < 9) ? new PreJava9ReflectionAccessor() : new UnsafeReflectionAccessor());
    }
}
