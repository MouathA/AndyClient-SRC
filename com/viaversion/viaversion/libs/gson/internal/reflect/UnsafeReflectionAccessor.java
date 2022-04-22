package com.viaversion.viaversion.libs.gson.internal.reflect;

import java.lang.reflect.*;

final class UnsafeReflectionAccessor extends ReflectionAccessor
{
    private static Class unsafeClass;
    private final Object theUnsafe;
    private final Field overrideField;
    
    UnsafeReflectionAccessor() {
        this.theUnsafe = getUnsafeInstance();
        this.overrideField = getOverrideField();
    }
    
    @Override
    public void makeAccessible(final AccessibleObject accessibleObject) {
        if (!this.makeAccessibleWithUnsafe(accessibleObject)) {
            accessibleObject.setAccessible(true);
        }
    }
    
    boolean makeAccessibleWithUnsafe(final AccessibleObject accessibleObject) {
        if (this.theUnsafe != null && this.overrideField != null) {
            UnsafeReflectionAccessor.unsafeClass.getMethod("putBoolean", Object.class, Long.TYPE, Boolean.TYPE).invoke(this.theUnsafe, accessibleObject, (long)UnsafeReflectionAccessor.unsafeClass.getMethod("objectFieldOffset", Field.class).invoke(this.theUnsafe, this.overrideField), true);
            return true;
        }
        return false;
    }
    
    private static Object getUnsafeInstance() {
        UnsafeReflectionAccessor.unsafeClass = Class.forName("sun.misc.Unsafe");
        final Field declaredField = UnsafeReflectionAccessor.unsafeClass.getDeclaredField("theUnsafe");
        declaredField.setAccessible(true);
        return declaredField.get(null);
    }
    
    private static Field getOverrideField() {
        return AccessibleObject.class.getDeclaredField("override");
    }
}
