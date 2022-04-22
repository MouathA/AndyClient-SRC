package io.netty.util.internal;

import sun.misc.*;
import java.util.concurrent.atomic.*;
import java.security.*;
import io.netty.util.internal.logging.*;
import java.nio.*;
import java.lang.reflect.*;

final class PlatformDependent0
{
    private static final InternalLogger logger;
    private static final Unsafe UNSAFE;
    private static final boolean BIG_ENDIAN;
    private static final long UNSAFE_COPY_THRESHOLD = 1048576L;
    private static final boolean UNALIGNED;
    
    static boolean hasUnsafe() {
        return PlatformDependent0.UNSAFE != null;
    }
    
    static void throwException(final Throwable t) {
        PlatformDependent0.UNSAFE.throwException(t);
    }
    
    static void freeDirectBuffer(final ByteBuffer byteBuffer) {
        Cleaner0.freeDirectBuffer(byteBuffer);
    }
    
    static long directBufferAddress(final ByteBuffer byteBuffer) {
        return getLong(byteBuffer, -1L);
    }
    
    static long arrayBaseOffset() {
        return PlatformDependent0.UNSAFE.arrayBaseOffset(byte[].class);
    }
    
    static Object getObject(final Object o, final long n) {
        return PlatformDependent0.UNSAFE.getObject(o, n);
    }
    
    static Object getObjectVolatile(final Object o, final long n) {
        return PlatformDependent0.UNSAFE.getObjectVolatile(o, n);
    }
    
    static int getInt(final Object o, final long n) {
        return PlatformDependent0.UNSAFE.getInt(o, n);
    }
    
    private static long getLong(final Object o, final long n) {
        return PlatformDependent0.UNSAFE.getLong(o, n);
    }
    
    static long objectFieldOffset(final Field field) {
        return PlatformDependent0.UNSAFE.objectFieldOffset(field);
    }
    
    static byte getByte(final long n) {
        return PlatformDependent0.UNSAFE.getByte(n);
    }
    
    static short getShort(final long n) {
        if (PlatformDependent0.UNALIGNED) {
            return PlatformDependent0.UNSAFE.getShort(n);
        }
        if (PlatformDependent0.BIG_ENDIAN) {
            return (short)(getByte(n) << 8 | (getByte(n + 1L) & 0xFF));
        }
        return (short)(getByte(n + 1L) << 8 | (getByte(n) & 0xFF));
    }
    
    static int getInt(final long n) {
        if (PlatformDependent0.UNALIGNED) {
            return PlatformDependent0.UNSAFE.getInt(n);
        }
        if (PlatformDependent0.BIG_ENDIAN) {
            return getByte(n) << 24 | (getByte(n + 1L) & 0xFF) << 16 | (getByte(n + 2L) & 0xFF) << 8 | (getByte(n + 3L) & 0xFF);
        }
        return getByte(n + 3L) << 24 | (getByte(n + 2L) & 0xFF) << 16 | (getByte(n + 1L) & 0xFF) << 8 | (getByte(n) & 0xFF);
    }
    
    static long getLong(final long n) {
        if (PlatformDependent0.UNALIGNED) {
            return PlatformDependent0.UNSAFE.getLong(n);
        }
        if (PlatformDependent0.BIG_ENDIAN) {
            return (long)getByte(n) << 56 | ((long)getByte(n + 1L) & 0xFFL) << 48 | ((long)getByte(n + 2L) & 0xFFL) << 40 | ((long)getByte(n + 3L) & 0xFFL) << 32 | ((long)getByte(n + 4L) & 0xFFL) << 24 | ((long)getByte(n + 5L) & 0xFFL) << 16 | ((long)getByte(n + 6L) & 0xFFL) << 8 | ((long)getByte(n + 7L) & 0xFFL);
        }
        return (long)getByte(n + 7L) << 56 | ((long)getByte(n + 6L) & 0xFFL) << 48 | ((long)getByte(n + 5L) & 0xFFL) << 40 | ((long)getByte(n + 4L) & 0xFFL) << 32 | ((long)getByte(n + 3L) & 0xFFL) << 24 | ((long)getByte(n + 2L) & 0xFFL) << 16 | ((long)getByte(n + 1L) & 0xFFL) << 8 | ((long)getByte(n) & 0xFFL);
    }
    
    static void putOrderedObject(final Object o, final long n, final Object o2) {
        PlatformDependent0.UNSAFE.putOrderedObject(o, n, o2);
    }
    
    static void putByte(final long n, final byte b) {
        PlatformDependent0.UNSAFE.putByte(n, b);
    }
    
    static void putShort(final long n, final short n2) {
        if (PlatformDependent0.UNALIGNED) {
            PlatformDependent0.UNSAFE.putShort(n, n2);
        }
        else if (PlatformDependent0.BIG_ENDIAN) {
            putByte(n, (byte)(n2 >>> 8));
            putByte(n + 1L, (byte)n2);
        }
        else {
            putByte(n + 1L, (byte)(n2 >>> 8));
            putByte(n, (byte)n2);
        }
    }
    
    static void putInt(final long n, final int n2) {
        if (PlatformDependent0.UNALIGNED) {
            PlatformDependent0.UNSAFE.putInt(n, n2);
        }
        else if (PlatformDependent0.BIG_ENDIAN) {
            putByte(n, (byte)(n2 >>> 24));
            putByte(n + 1L, (byte)(n2 >>> 16));
            putByte(n + 2L, (byte)(n2 >>> 8));
            putByte(n + 3L, (byte)n2);
        }
        else {
            putByte(n + 3L, (byte)(n2 >>> 24));
            putByte(n + 2L, (byte)(n2 >>> 16));
            putByte(n + 1L, (byte)(n2 >>> 8));
            putByte(n, (byte)n2);
        }
    }
    
    static void putLong(final long n, final long n2) {
        if (PlatformDependent0.UNALIGNED) {
            PlatformDependent0.UNSAFE.putLong(n, n2);
        }
        else if (PlatformDependent0.BIG_ENDIAN) {
            putByte(n, (byte)(n2 >>> 56));
            putByte(n + 1L, (byte)(n2 >>> 48));
            putByte(n + 2L, (byte)(n2 >>> 40));
            putByte(n + 3L, (byte)(n2 >>> 32));
            putByte(n + 4L, (byte)(n2 >>> 24));
            putByte(n + 5L, (byte)(n2 >>> 16));
            putByte(n + 6L, (byte)(n2 >>> 8));
            putByte(n + 7L, (byte)n2);
        }
        else {
            putByte(n + 7L, (byte)(n2 >>> 56));
            putByte(n + 6L, (byte)(n2 >>> 48));
            putByte(n + 5L, (byte)(n2 >>> 40));
            putByte(n + 4L, (byte)(n2 >>> 32));
            putByte(n + 3L, (byte)(n2 >>> 24));
            putByte(n + 2L, (byte)(n2 >>> 16));
            putByte(n + 1L, (byte)(n2 >>> 8));
            putByte(n, (byte)n2);
        }
    }
    
    static void copyMemory(long n, long n2, long n3) {
        while (n3 > 0L) {
            final long min = Math.min(n3, 1048576L);
            PlatformDependent0.UNSAFE.copyMemory(n, n2, min);
            n3 -= min;
            n += min;
            n2 += min;
        }
    }
    
    static void copyMemory(final Object o, long n, final Object o2, long n2, long n3) {
        while (n3 > 0L) {
            final long min = Math.min(n3, 1048576L);
            PlatformDependent0.UNSAFE.copyMemory(o, n, o2, n2, min);
            n3 -= min;
            n += min;
            n2 += min;
        }
    }
    
    static AtomicReferenceFieldUpdater newAtomicReferenceFieldUpdater(final Class clazz, final String s) throws Exception {
        return new UnsafeAtomicReferenceFieldUpdater(PlatformDependent0.UNSAFE, clazz, s);
    }
    
    static AtomicIntegerFieldUpdater newAtomicIntegerFieldUpdater(final Class clazz, final String s) throws Exception {
        return new UnsafeAtomicIntegerFieldUpdater(PlatformDependent0.UNSAFE, clazz, s);
    }
    
    static AtomicLongFieldUpdater newAtomicLongFieldUpdater(final Class clazz, final String s) throws Exception {
        return new UnsafeAtomicLongFieldUpdater(PlatformDependent0.UNSAFE, clazz, s);
    }
    
    static ClassLoader getClassLoader(final Class clazz) {
        if (System.getSecurityManager() == null) {
            return clazz.getClassLoader();
        }
        return AccessController.doPrivileged((PrivilegedAction<ClassLoader>)new PrivilegedAction(clazz) {
            final Class val$clazz;
            
            @Override
            public ClassLoader run() {
                return this.val$clazz.getClassLoader();
            }
            
            @Override
            public Object run() {
                return this.run();
            }
        });
    }
    
    static ClassLoader getContextClassLoader() {
        if (System.getSecurityManager() == null) {
            return Thread.currentThread().getContextClassLoader();
        }
        return AccessController.doPrivileged((PrivilegedAction<ClassLoader>)new PrivilegedAction() {
            @Override
            public ClassLoader run() {
                return Thread.currentThread().getContextClassLoader();
            }
            
            @Override
            public Object run() {
                return this.run();
            }
        });
    }
    
    static ClassLoader getSystemClassLoader() {
        if (System.getSecurityManager() == null) {
            return ClassLoader.getSystemClassLoader();
        }
        return AccessController.doPrivileged((PrivilegedAction<ClassLoader>)new PrivilegedAction() {
            @Override
            public ClassLoader run() {
                return ClassLoader.getSystemClassLoader();
            }
            
            @Override
            public Object run() {
                return this.run();
            }
        });
    }
    
    static int addressSize() {
        return PlatformDependent0.UNSAFE.addressSize();
    }
    
    static long allocateMemory(final long n) {
        return PlatformDependent0.UNSAFE.allocateMemory(n);
    }
    
    static void freeMemory(final long n) {
        PlatformDependent0.UNSAFE.freeMemory(n);
    }
    
    private PlatformDependent0() {
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(PlatformDependent0.class);
        BIG_ENDIAN = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
        final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(1);
        Field declaredField = Buffer.class.getDeclaredField("address");
        declaredField.setAccessible(true);
        if (declaredField.getLong(ByteBuffer.allocate(1)) != 0L) {
            declaredField = null;
        }
        else if (declaredField.getLong(allocateDirect) == 0L) {
            declaredField = null;
        }
        PlatformDependent0.logger.debug("java.nio.Buffer.address: {}", (declaredField != null) ? "available" : "unavailable");
        Unsafe unsafe;
        if (declaredField != null) {
            final Field declaredField2 = Unsafe.class.getDeclaredField("theUnsafe");
            declaredField2.setAccessible(true);
            unsafe = (Unsafe)declaredField2.get(null);
            PlatformDependent0.logger.debug("sun.misc.Unsafe.theUnsafe: {}", (unsafe != null) ? "available" : "unavailable");
            if (unsafe != null) {
                unsafe.getClass().getDeclaredMethod("copyMemory", Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE);
                PlatformDependent0.logger.debug("sun.misc.Unsafe.copyMemory: available");
            }
        }
        else {
            unsafe = null;
        }
        UNSAFE = unsafe;
        if (unsafe == null) {
            UNALIGNED = false;
        }
        else {
            PlatformDependent0.ADDRESS_FIELD_OFFSET = objectFieldOffset(declaredField);
            final Method declaredMethod = Class.forName("java.nio.Bits", false, ClassLoader.getSystemClassLoader()).getDeclaredMethod("unaligned", (Class<?>[])new Class[0]);
            declaredMethod.setAccessible(true);
            UNALIGNED = Boolean.TRUE.equals(declaredMethod.invoke(null, new Object[0]));
            PlatformDependent0.logger.debug("java.nio.Bits.unaligned: {}", (Object)PlatformDependent0.UNALIGNED);
        }
    }
}
