package com.google.gson.internal;

import java.lang.reflect.*;

public abstract class UnsafeAllocator
{
    public abstract Object newInstance(final Class p0) throws Exception;
    
    public static UnsafeAllocator create() {
        final Class<?> forName = Class.forName("sun.misc.Unsafe");
        final Field declaredField = forName.getDeclaredField("theUnsafe");
        declaredField.setAccessible(true);
        return new UnsafeAllocator(forName.getMethod("allocateInstance", Class.class), declaredField.get(null)) {
            final Method val$allocateInstance;
            final Object val$unsafe;
            
            @Override
            public Object newInstance(final Class clazz) throws Exception {
                return this.val$allocateInstance.invoke(this.val$unsafe, clazz);
            }
        };
    }
}
