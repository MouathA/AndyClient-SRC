package com.viaversion.viaversion.unsupported;

import java.util.*;
import java.lang.reflect.*;

public final class UnsupportedMethods
{
    private final String className;
    private final Set methodNames;
    
    public UnsupportedMethods(final String className, final Set set) {
        this.className = className;
        this.methodNames = Collections.unmodifiableSet((Set<?>)set);
    }
    
    public String getClassName() {
        return this.className;
    }
    
    public final boolean findMatch() {
        final Method[] declaredMethods = Class.forName(this.className).getDeclaredMethods();
        while (0 < declaredMethods.length) {
            if (this.methodNames.contains(declaredMethods[0].getName())) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
}
