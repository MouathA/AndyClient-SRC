package org.lwjgl;

import java.nio.*;

public final class BufferUtils
{
    public static ByteBuffer createByteBuffer(final int n) {
        return ByteBuffer.allocateDirect(n).order(ByteOrder.nativeOrder());
    }
    
    public static ShortBuffer createShortBuffer(final int n) {
        return createByteBuffer(n << 1).asShortBuffer();
    }
    
    public static CharBuffer createCharBuffer(final int n) {
        return createByteBuffer(n << 1).asCharBuffer();
    }
    
    public static IntBuffer createIntBuffer(final int n) {
        return createByteBuffer(n << 2).asIntBuffer();
    }
    
    public static LongBuffer createLongBuffer(final int n) {
        return createByteBuffer(n << 3).asLongBuffer();
    }
    
    public static FloatBuffer createFloatBuffer(final int n) {
        return createByteBuffer(n << 2).asFloatBuffer();
    }
    
    public static DoubleBuffer createDoubleBuffer(final int n) {
        return createByteBuffer(n << 3).asDoubleBuffer();
    }
    
    public static PointerBuffer createPointerBuffer(final int n) {
        return PointerBuffer.allocateDirect(n);
    }
    
    public static int getElementSizeExponent(final Buffer buffer) {
        if (buffer instanceof ByteBuffer) {
            return 0;
        }
        if (buffer instanceof ShortBuffer || buffer instanceof CharBuffer) {
            return 1;
        }
        if (buffer instanceof FloatBuffer || buffer instanceof IntBuffer) {
            return 2;
        }
        if (buffer instanceof LongBuffer || buffer instanceof DoubleBuffer) {
            return 3;
        }
        throw new IllegalStateException("Unsupported buffer type: " + buffer);
    }
    
    public static int getOffset(final Buffer buffer) {
        return buffer.position() << getElementSizeExponent(buffer);
    }
    
    public static void zeroBuffer(final ByteBuffer byteBuffer) {
        zeroBuffer0(byteBuffer, byteBuffer.position(), byteBuffer.remaining());
    }
    
    public static void zeroBuffer(final ShortBuffer shortBuffer) {
        zeroBuffer0(shortBuffer, shortBuffer.position() * 2L, shortBuffer.remaining() * 2L);
    }
    
    public static void zeroBuffer(final CharBuffer charBuffer) {
        zeroBuffer0(charBuffer, charBuffer.position() * 2L, charBuffer.remaining() * 2L);
    }
    
    public static void zeroBuffer(final IntBuffer intBuffer) {
        zeroBuffer0(intBuffer, intBuffer.position() * 4L, intBuffer.remaining() * 4L);
    }
    
    public static void zeroBuffer(final FloatBuffer floatBuffer) {
        zeroBuffer0(floatBuffer, floatBuffer.position() * 4L, floatBuffer.remaining() * 4L);
    }
    
    public static void zeroBuffer(final LongBuffer longBuffer) {
        zeroBuffer0(longBuffer, longBuffer.position() * 8L, longBuffer.remaining() * 8L);
    }
    
    public static void zeroBuffer(final DoubleBuffer doubleBuffer) {
        zeroBuffer0(doubleBuffer, doubleBuffer.position() * 8L, doubleBuffer.remaining() * 8L);
    }
    
    private static native void zeroBuffer0(final Buffer p0, final long p1, final long p2);
    
    static native long getBufferAddress(final Buffer p0);
}
