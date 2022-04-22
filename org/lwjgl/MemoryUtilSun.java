package org.lwjgl;

import sun.reflect.*;
import java.nio.*;
import sun.misc.*;
import java.lang.reflect.*;

final class MemoryUtilSun
{
    private MemoryUtilSun() {
    }
    
    private static class AccessorReflectFast implements MemoryUtil.Accessor
    {
        private final FieldAccessor addressAccessor;
        
        AccessorReflectFast() {
            final Field addressField = MemoryUtil.getAddressField();
            addressField.setAccessible(true);
            final Method declaredMethod = Field.class.getDeclaredMethod("acquireFieldAccessor", Boolean.TYPE);
            declaredMethod.setAccessible(true);
            this.addressAccessor = (FieldAccessor)declaredMethod.invoke(addressField, true);
        }
        
        public long getAddress(final Buffer buffer) {
            return this.addressAccessor.getLong(buffer);
        }
    }
    
    private static class AccessorUnsafe implements MemoryUtil.Accessor
    {
        private final Unsafe unsafe;
        private final long address;
        
        AccessorUnsafe() {
            this.unsafe = getUnsafeInstance();
            this.address = this.unsafe.objectFieldOffset(MemoryUtil.getAddressField());
        }
        
        public long getAddress(final Buffer buffer) {
            return this.unsafe.getLong(buffer, this.address);
        }
        
        private static Unsafe getUnsafeInstance() {
            final Field[] declaredFields = Unsafe.class.getDeclaredFields();
            while (0 < declaredFields.length) {
                final Field field = declaredFields[0];
                if (field.getType().equals(Unsafe.class)) {
                    final int modifiers = field.getModifiers();
                    if (Modifier.isStatic(modifiers)) {
                        if (Modifier.isFinal(modifiers)) {
                            field.setAccessible(true);
                            return (Unsafe)field.get(null);
                        }
                    }
                }
                int n = 0;
                ++n;
            }
            throw new UnsupportedOperationException();
        }
    }
}
