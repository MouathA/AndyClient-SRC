package io.netty.util.internal;

import java.util.concurrent.atomic.*;
import sun.misc.*;
import java.lang.reflect.*;

final class UnsafeAtomicIntegerFieldUpdater extends AtomicIntegerFieldUpdater
{
    private final long offset;
    private final Unsafe unsafe;
    
    UnsafeAtomicIntegerFieldUpdater(final Unsafe unsafe, final Class clazz, final String s) throws NoSuchFieldException {
        final Field declaredField = clazz.getDeclaredField(s);
        if (!Modifier.isVolatile(declaredField.getModifiers())) {
            throw new IllegalArgumentException("Must be volatile");
        }
        this.unsafe = unsafe;
        this.offset = unsafe.objectFieldOffset(declaredField);
    }
    
    @Override
    public boolean compareAndSet(final Object o, final int n, final int n2) {
        return this.unsafe.compareAndSwapInt(o, this.offset, n, n2);
    }
    
    @Override
    public boolean weakCompareAndSet(final Object o, final int n, final int n2) {
        return this.unsafe.compareAndSwapInt(o, this.offset, n, n2);
    }
    
    @Override
    public void set(final Object o, final int n) {
        this.unsafe.putIntVolatile(o, this.offset, n);
    }
    
    @Override
    public void lazySet(final Object o, final int n) {
        this.unsafe.putOrderedInt(o, this.offset, n);
    }
    
    @Override
    public int get(final Object o) {
        return this.unsafe.getIntVolatile(o, this.offset);
    }
}
