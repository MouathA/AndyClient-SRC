package org.lwjgl.util.mapped;

import java.nio.*;
import org.lwjgl.*;

public class MappedHelper
{
    public static void setup(final MappedObject mappedObject, final ByteBuffer preventGC, final int n, final int n2) {
        if (LWJGLUtil.CHECKS && mappedObject.baseAddress != 0L) {
            throw new IllegalStateException("this method should not be called by user-code");
        }
        if (LWJGLUtil.CHECKS && !preventGC.isDirect()) {
            throw new IllegalArgumentException("bytebuffer must be direct");
        }
        mappedObject.preventGC = preventGC;
        if (LWJGLUtil.CHECKS && n <= 0) {
            throw new IllegalArgumentException("invalid alignment");
        }
        if (LWJGLUtil.CHECKS && (n2 <= 0 || n2 % n != 0)) {
            throw new IllegalStateException("sizeof not a multiple of alignment");
        }
        final long address = MemoryUtil.getAddress(preventGC);
        if (LWJGLUtil.CHECKS && address % n != 0L) {
            throw new IllegalStateException("buffer address not aligned on " + n + " bytes");
        }
        final long n3 = address;
        mappedObject.viewAddress = n3;
        mappedObject.baseAddress = n3;
    }
    
    public static void checkAddress(final long n, final MappedObject mappedObject) {
        mappedObject.checkAddress(n);
    }
    
    public static void put_views(final MappedSet2 mappedSet2, final int n) {
        mappedSet2.view(n);
    }
    
    public static void put_views(final MappedSet3 mappedSet3, final int n) {
        mappedSet3.view(n);
    }
    
    public static void put_views(final MappedSet4 mappedSet4, final int n) {
        mappedSet4.view(n);
    }
    
    public static void put_view(final MappedObject mappedObject, final int n, final int n2) {
        mappedObject.setViewAddress(mappedObject.baseAddress + n * n2);
    }
    
    public static int get_view(final MappedObject mappedObject, final int n) {
        return (int)(mappedObject.viewAddress - mappedObject.baseAddress) / n;
    }
    
    public static void put_view_shift(final MappedObject mappedObject, final int n, final int n2) {
        mappedObject.setViewAddress(mappedObject.baseAddress + (n << n2));
    }
    
    public static int get_view_shift(final MappedObject mappedObject, final int n) {
        return (int)(mappedObject.viewAddress - mappedObject.baseAddress) >> n;
    }
    
    public static void put_view_next(final MappedObject mappedObject, final int n) {
        mappedObject.setViewAddress(mappedObject.viewAddress + n);
    }
    
    public static MappedObject dup(final MappedObject mappedObject, final MappedObject mappedObject2) {
        mappedObject2.baseAddress = mappedObject.baseAddress;
        mappedObject2.viewAddress = mappedObject.viewAddress;
        mappedObject2.preventGC = mappedObject.preventGC;
        return mappedObject2;
    }
    
    public static MappedObject slice(final MappedObject mappedObject, final MappedObject mappedObject2) {
        mappedObject2.baseAddress = mappedObject.viewAddress;
        mappedObject2.viewAddress = mappedObject.viewAddress;
        mappedObject2.preventGC = mappedObject.preventGC;
        return mappedObject2;
    }
    
    public static void copy(final MappedObject mappedObject, final MappedObject mappedObject2, final int n) {
        if (MappedObject.CHECKS) {
            mappedObject.checkRange(n);
            mappedObject2.checkRange(n);
        }
        MappedObjectUnsafe.INSTANCE.copyMemory(mappedObject.viewAddress, mappedObject2.viewAddress, n);
    }
    
    public static ByteBuffer newBuffer(final long n, final int n2) {
        return MappedObjectUnsafe.newBuffer(n, n2);
    }
    
    public static void bput(final byte b, final long n) {
        MappedObjectUnsafe.INSTANCE.putByte(n, b);
    }
    
    public static void bput(final MappedObject mappedObject, final byte b, final int n) {
        MappedObjectUnsafe.INSTANCE.putByte(mappedObject.viewAddress + n, b);
    }
    
    public static byte bget(final long n) {
        return MappedObjectUnsafe.INSTANCE.getByte(n);
    }
    
    public static byte bget(final MappedObject mappedObject, final int n) {
        return MappedObjectUnsafe.INSTANCE.getByte(mappedObject.viewAddress + n);
    }
    
    public static void bvput(final byte b, final long n) {
        MappedObjectUnsafe.INSTANCE.putByteVolatile(null, n, b);
    }
    
    public static void bvput(final MappedObject mappedObject, final byte b, final int n) {
        MappedObjectUnsafe.INSTANCE.putByteVolatile(null, mappedObject.viewAddress + n, b);
    }
    
    public static byte bvget(final long n) {
        return MappedObjectUnsafe.INSTANCE.getByteVolatile(null, n);
    }
    
    public static byte bvget(final MappedObject mappedObject, final int n) {
        return MappedObjectUnsafe.INSTANCE.getByteVolatile(null, mappedObject.viewAddress + n);
    }
    
    public static void sput(final short n, final long n2) {
        MappedObjectUnsafe.INSTANCE.putShort(n2, n);
    }
    
    public static void sput(final MappedObject mappedObject, final short n, final int n2) {
        MappedObjectUnsafe.INSTANCE.putShort(mappedObject.viewAddress + n2, n);
    }
    
    public static short sget(final long n) {
        return MappedObjectUnsafe.INSTANCE.getShort(n);
    }
    
    public static short sget(final MappedObject mappedObject, final int n) {
        return MappedObjectUnsafe.INSTANCE.getShort(mappedObject.viewAddress + n);
    }
    
    public static void svput(final short n, final long n2) {
        MappedObjectUnsafe.INSTANCE.putShortVolatile(null, n2, n);
    }
    
    public static void svput(final MappedObject mappedObject, final short n, final int n2) {
        MappedObjectUnsafe.INSTANCE.putShortVolatile(null, mappedObject.viewAddress + n2, n);
    }
    
    public static short svget(final long n) {
        return MappedObjectUnsafe.INSTANCE.getShortVolatile(null, n);
    }
    
    public static short svget(final MappedObject mappedObject, final int n) {
        return MappedObjectUnsafe.INSTANCE.getShortVolatile(null, mappedObject.viewAddress + n);
    }
    
    public static void cput(final char c, final long n) {
        MappedObjectUnsafe.INSTANCE.putChar(n, c);
    }
    
    public static void cput(final MappedObject mappedObject, final char c, final int n) {
        MappedObjectUnsafe.INSTANCE.putChar(mappedObject.viewAddress + n, c);
    }
    
    public static char cget(final long n) {
        return MappedObjectUnsafe.INSTANCE.getChar(n);
    }
    
    public static char cget(final MappedObject mappedObject, final int n) {
        return MappedObjectUnsafe.INSTANCE.getChar(mappedObject.viewAddress + n);
    }
    
    public static void cvput(final char c, final long n) {
        MappedObjectUnsafe.INSTANCE.putCharVolatile(null, n, c);
    }
    
    public static void cvput(final MappedObject mappedObject, final char c, final int n) {
        MappedObjectUnsafe.INSTANCE.putCharVolatile(null, mappedObject.viewAddress + n, c);
    }
    
    public static char cvget(final long n) {
        return MappedObjectUnsafe.INSTANCE.getCharVolatile(null, n);
    }
    
    public static char cvget(final MappedObject mappedObject, final int n) {
        return MappedObjectUnsafe.INSTANCE.getCharVolatile(null, mappedObject.viewAddress + n);
    }
    
    public static void iput(final int n, final long n2) {
        MappedObjectUnsafe.INSTANCE.putInt(n2, n);
    }
    
    public static void iput(final MappedObject mappedObject, final int n, final int n2) {
        MappedObjectUnsafe.INSTANCE.putInt(mappedObject.viewAddress + n2, n);
    }
    
    public static int iget(final long n) {
        return MappedObjectUnsafe.INSTANCE.getInt(n);
    }
    
    public static int iget(final MappedObject mappedObject, final int n) {
        return MappedObjectUnsafe.INSTANCE.getInt(mappedObject.viewAddress + n);
    }
    
    public static void ivput(final int n, final long n2) {
        MappedObjectUnsafe.INSTANCE.putIntVolatile(null, n2, n);
    }
    
    public static void ivput(final MappedObject mappedObject, final int n, final int n2) {
        MappedObjectUnsafe.INSTANCE.putIntVolatile(null, mappedObject.viewAddress + n2, n);
    }
    
    public static int ivget(final long n) {
        return MappedObjectUnsafe.INSTANCE.getIntVolatile(null, n);
    }
    
    public static int ivget(final MappedObject mappedObject, final int n) {
        return MappedObjectUnsafe.INSTANCE.getIntVolatile(null, mappedObject.viewAddress + n);
    }
    
    public static void fput(final float n, final long n2) {
        MappedObjectUnsafe.INSTANCE.putFloat(n2, n);
    }
    
    public static void fput(final MappedObject mappedObject, final float n, final int n2) {
        MappedObjectUnsafe.INSTANCE.putFloat(mappedObject.viewAddress + n2, n);
    }
    
    public static float fget(final long n) {
        return MappedObjectUnsafe.INSTANCE.getFloat(n);
    }
    
    public static float fget(final MappedObject mappedObject, final int n) {
        return MappedObjectUnsafe.INSTANCE.getFloat(mappedObject.viewAddress + n);
    }
    
    public static void fvput(final float n, final long n2) {
        MappedObjectUnsafe.INSTANCE.putFloatVolatile(null, n2, n);
    }
    
    public static void fvput(final MappedObject mappedObject, final float n, final int n2) {
        MappedObjectUnsafe.INSTANCE.putFloatVolatile(null, mappedObject.viewAddress + n2, n);
    }
    
    public static float fvget(final long n) {
        return MappedObjectUnsafe.INSTANCE.getFloatVolatile(null, n);
    }
    
    public static float fvget(final MappedObject mappedObject, final int n) {
        return MappedObjectUnsafe.INSTANCE.getFloatVolatile(null, mappedObject.viewAddress + n);
    }
    
    public static void jput(final long n, final long n2) {
        MappedObjectUnsafe.INSTANCE.putLong(n2, n);
    }
    
    public static void jput(final MappedObject mappedObject, final long n, final int n2) {
        MappedObjectUnsafe.INSTANCE.putLong(mappedObject.viewAddress + n2, n);
    }
    
    public static long jget(final long n) {
        return MappedObjectUnsafe.INSTANCE.getLong(n);
    }
    
    public static long jget(final MappedObject mappedObject, final int n) {
        return MappedObjectUnsafe.INSTANCE.getLong(mappedObject.viewAddress + n);
    }
    
    public static void jvput(final long n, final long n2) {
        MappedObjectUnsafe.INSTANCE.putLongVolatile(null, n2, n);
    }
    
    public static void jvput(final MappedObject mappedObject, final long n, final int n2) {
        MappedObjectUnsafe.INSTANCE.putLongVolatile(null, mappedObject.viewAddress + n2, n);
    }
    
    public static long jvget(final long n) {
        return MappedObjectUnsafe.INSTANCE.getLongVolatile(null, n);
    }
    
    public static long jvget(final MappedObject mappedObject, final int n) {
        return MappedObjectUnsafe.INSTANCE.getLongVolatile(null, mappedObject.viewAddress + n);
    }
    
    public static void aput(final long n, final long n2) {
        MappedObjectUnsafe.INSTANCE.putAddress(n2, n);
    }
    
    public static void aput(final MappedObject mappedObject, final long n, final int n2) {
        MappedObjectUnsafe.INSTANCE.putAddress(mappedObject.viewAddress + n2, n);
    }
    
    public static long aget(final long n) {
        return MappedObjectUnsafe.INSTANCE.getAddress(n);
    }
    
    public static long aget(final MappedObject mappedObject, final int n) {
        return MappedObjectUnsafe.INSTANCE.getAddress(mappedObject.viewAddress + n);
    }
    
    public static void dput(final double n, final long n2) {
        MappedObjectUnsafe.INSTANCE.putDouble(n2, n);
    }
    
    public static void dput(final MappedObject mappedObject, final double n, final int n2) {
        MappedObjectUnsafe.INSTANCE.putDouble(mappedObject.viewAddress + n2, n);
    }
    
    public static double dget(final long n) {
        return MappedObjectUnsafe.INSTANCE.getDouble(n);
    }
    
    public static double dget(final MappedObject mappedObject, final int n) {
        return MappedObjectUnsafe.INSTANCE.getDouble(mappedObject.viewAddress + n);
    }
    
    public static void dvput(final double n, final long n2) {
        MappedObjectUnsafe.INSTANCE.putDoubleVolatile(null, n2, n);
    }
    
    public static void dvput(final MappedObject mappedObject, final double n, final int n2) {
        MappedObjectUnsafe.INSTANCE.putDoubleVolatile(null, mappedObject.viewAddress + n2, n);
    }
    
    public static double dvget(final long n) {
        return MappedObjectUnsafe.INSTANCE.getDoubleVolatile(null, n);
    }
    
    public static double dvget(final MappedObject mappedObject, final int n) {
        return MappedObjectUnsafe.INSTANCE.getDoubleVolatile(null, mappedObject.viewAddress + n);
    }
}
