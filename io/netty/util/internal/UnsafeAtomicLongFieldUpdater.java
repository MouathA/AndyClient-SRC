package io.netty.util.internal;

import java.util.concurrent.atomic.*;
import sun.misc.*;
import java.lang.reflect.*;

final class UnsafeAtomicLongFieldUpdater extends AtomicLongFieldUpdater
{
    private final long offset;
    private final Unsafe unsafe;
    
    UnsafeAtomicLongFieldUpdater(final Unsafe unsafe, final Class clazz, final String s) throws NoSuchFieldException {
        final Field declaredField = clazz.getDeclaredField(s);
        if (!Modifier.isVolatile(declaredField.getModifiers())) {
            throw new IllegalArgumentException("Must be volatile");
        }
        this.unsafe = unsafe;
        this.offset = unsafe.objectFieldOffset(declaredField);
    }
    
    @Override
    public boolean compareAndSet(final Object o, final long n, final long n2) {
        return this.unsafe.compareAndSwapLong(o, this.offset, n, n2);
    }
    
    @Override
    public boolean weakCompareAndSet(final Object o, final long n, final long n2) {
        return this.unsafe.compareAndSwapLong(o, this.offset, n, n2);
    }
    
    @Override
    public void set(final Object o, final long n) {
        this.unsafe.putLongVolatile(o, this.offset, n);
    }
    
    @Override
    public void lazySet(final Object o, final long n) {
        this.unsafe.putOrderedLong(o, this.offset, n);
    }
    
    @Override
    public long get(final Object o) {
        return this.unsafe.getLongVolatile(o, this.offset);
    }
}
