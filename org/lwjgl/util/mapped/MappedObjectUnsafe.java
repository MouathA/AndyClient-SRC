package org.lwjgl.util.mapped;

import sun.misc.*;
import java.nio.*;
import java.lang.reflect.*;

final class MappedObjectUnsafe
{
    static final Unsafe INSTANCE;
    private static final long BUFFER_ADDRESS_OFFSET;
    private static final long BUFFER_CAPACITY_OFFSET;
    private static final ByteBuffer global;
    
    static ByteBuffer newBuffer(final long n, final int n2) {
        if (n <= 0L || n2 < 0) {
            throw new IllegalStateException("you almost crashed the jvm");
        }
        final ByteBuffer order = MappedObjectUnsafe.global.duplicate().order(ByteOrder.nativeOrder());
        MappedObjectUnsafe.INSTANCE.putLong(order, MappedObjectUnsafe.BUFFER_ADDRESS_OFFSET, n);
        MappedObjectUnsafe.INSTANCE.putInt(order, MappedObjectUnsafe.BUFFER_CAPACITY_OFFSET, n2);
        order.position(0);
        order.limit(n2);
        return order;
    }
    
    private static long getObjectFieldOffset(final Class clazz, final String s) {
        if (clazz != null) {
            return MappedObjectUnsafe.INSTANCE.objectFieldOffset(clazz.getDeclaredField(s));
        }
        throw new UnsupportedOperationException();
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
    
    static {
        INSTANCE = getUnsafeInstance();
        BUFFER_ADDRESS_OFFSET = getObjectFieldOffset(ByteBuffer.class, "address");
        BUFFER_CAPACITY_OFFSET = getObjectFieldOffset(ByteBuffer.class, "capacity");
        global = ByteBuffer.allocateDirect(4096);
    }
}
