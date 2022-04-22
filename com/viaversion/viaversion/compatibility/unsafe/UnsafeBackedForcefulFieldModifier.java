package com.viaversion.viaversion.compatibility.unsafe;

import com.viaversion.viaversion.compatibility.*;
import sun.misc.*;
import java.lang.reflect.*;
import java.util.*;

@Deprecated
public final class UnsafeBackedForcefulFieldModifier implements ForcefulFieldModifier
{
    private final Unsafe unsafe;
    
    public UnsafeBackedForcefulFieldModifier() throws ReflectiveOperationException {
        final Field declaredField = Unsafe.class.getDeclaredField("theUnsafe");
        declaredField.setAccessible(true);
        this.unsafe = (Unsafe)declaredField.get(null);
    }
    
    @Override
    public void setField(final Field field, final Object o, final Object o2) {
        Objects.requireNonNull(field, "field must not be null");
        this.unsafe.putObject((o != null) ? o : this.unsafe.staticFieldBase(field), (o != null) ? this.unsafe.objectFieldOffset(field) : this.unsafe.staticFieldOffset(field), o2);
    }
}
