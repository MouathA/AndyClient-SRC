package com.google.common.reflect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.lang.reflect.*;

@Beta
public final class Reflection
{
    public static String getPackageName(final Class clazz) {
        return getPackageName(clazz.getName());
    }
    
    public static String getPackageName(final String s) {
        final int lastIndex = s.lastIndexOf(46);
        return (lastIndex < 0) ? "" : s.substring(0, lastIndex);
    }
    
    public static void initialize(final Class... array) {
        while (0 < array.length) {
            final Class clazz = array[0];
            Class.forName(clazz.getName(), true, clazz.getClassLoader());
            int n = 0;
            ++n;
        }
    }
    
    public static Object newProxy(final Class clazz, final InvocationHandler invocationHandler) {
        Preconditions.checkNotNull(invocationHandler);
        Preconditions.checkArgument(clazz.isInterface(), "%s is not an interface", clazz);
        return clazz.cast(Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, invocationHandler));
    }
    
    private Reflection() {
    }
}
