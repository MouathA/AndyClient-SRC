package io.netty.util.internal;

import java.util.concurrent.atomic.*;
import sun.misc.*;
import java.lang.reflect.*;

final class UnsafeAtomicReferenceFieldUpdater extends AtomicReferenceFieldUpdater
{
    private final long offset;
    private final Unsafe unsafe;
    
    UnsafeAtomicReferenceFieldUpdater(final Unsafe unsafe, final Class clazz, final String s) throws NoSuchFieldException {
        final Field declaredField = clazz.getDeclaredField(s);
        if (!Modifier.isVolatile(declaredField.getModifiers())) {
            throw new IllegalArgumentException("Must be volatile");
        }
        this.unsafe = unsafe;
        this.offset = unsafe.objectFieldOffset(declaredField);
    }
    
    @Override
    public boolean compareAndSet(final Object o, final Object o2, final Object o3) {
        return this.unsafe.compareAndSwapObject(o, this.offset, o2, o3);
    }
    
    @Override
    public boolean weakCompareAndSet(final Object o, final Object o2, final Object o3) {
        return this.unsafe.compareAndSwapObject(o, this.offset, o2, o3);
    }
    
    @Override
    public void set(final Object o, final Object o2) {
        this.unsafe.putObjectVolatile(o, this.offset, o2);
    }
    
    @Override
    public void lazySet(final Object o, final Object o2) {
        this.unsafe.putOrderedObject(o, this.offset, o2);
    }
    
    @Override
    public Object get(final Object o) {
        return this.unsafe.getObjectVolatile(o, this.offset);
    }
}
