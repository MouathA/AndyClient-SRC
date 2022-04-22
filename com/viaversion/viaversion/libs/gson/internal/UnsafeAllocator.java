package com.viaversion.viaversion.libs.gson.internal;

import java.lang.reflect.*;

public abstract class UnsafeAllocator
{
    public abstract Object newInstance(final Class p0) throws Exception;
    
    public static UnsafeAllocator create() {
        final Class<?> forName = Class.forName("sun.misc.Unsafe");
        final Field declaredField = forName.getDeclaredField("theUnsafe");
        declaredField.setAccessible(true);
        return new UnsafeAllocator(declaredField.get(null)) {
            final Method val$allocateInstance;
            final Object val$unsafe;
            
            @Override
            public Object newInstance(final Class clazz) throws Exception {
                UnsafeAllocator.assertInstantiable(clazz);
                return this.val$allocateInstance.invoke(this.val$unsafe, clazz);
            }
        };
    }
    
    static void assertInstantiable(final Class clazz) {
        final int modifiers = clazz.getModifiers();
        if (Modifier.isInterface(modifiers)) {
            throw new UnsupportedOperationException("Interface can't be instantiated! Interface name: " + clazz.getName());
        }
        if (Modifier.isAbstract(modifiers)) {
            throw new UnsupportedOperationException("Abstract class can't be instantiated! Class name: " + clazz.getName());
        }
    }
}
