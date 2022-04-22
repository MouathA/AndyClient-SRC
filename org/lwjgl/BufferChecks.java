package org.lwjgl;

import java.nio.*;

public class BufferChecks
{
    private BufferChecks() {
    }
    
    public static void checkFunctionAddress(final long n) {
        if (LWJGLUtil.CHECKS && n == 0L) {
            throw new IllegalStateException("Function is not supported");
        }
    }
    
    public static void checkNullTerminated(final ByteBuffer byteBuffer) {
        if (LWJGLUtil.CHECKS && byteBuffer.get(byteBuffer.limit() - 1) != 0) {
            throw new IllegalArgumentException("Missing null termination");
        }
    }
    
    public static void checkNullTerminated(final ByteBuffer byteBuffer, final int n) {
        if (LWJGLUtil.CHECKS) {
            for (int i = byteBuffer.position(); i < byteBuffer.limit(); ++i) {
                if (byteBuffer.get(i) == 0) {
                    int n2 = 0;
                    ++n2;
                }
            }
            if (0 < n) {
                throw new IllegalArgumentException("Missing null termination");
            }
        }
    }
    
    public static void checkNullTerminated(final IntBuffer intBuffer) {
        if (LWJGLUtil.CHECKS && intBuffer.get(intBuffer.limit() - 1) != 0) {
            throw new IllegalArgumentException("Missing null termination");
        }
    }
    
    public static void checkNullTerminated(final LongBuffer longBuffer) {
        if (LWJGLUtil.CHECKS && longBuffer.get(longBuffer.limit() - 1) != 0L) {
            throw new IllegalArgumentException("Missing null termination");
        }
    }
    
    public static void checkNullTerminated(final PointerBuffer pointerBuffer) {
        if (LWJGLUtil.CHECKS && pointerBuffer.get(pointerBuffer.limit() - 1) != 0L) {
            throw new IllegalArgumentException("Missing null termination");
        }
    }
    
    public static void checkNotNull(final Object o) {
        if (LWJGLUtil.CHECKS && o == null) {
            throw new IllegalArgumentException("Null argument");
        }
    }
    
    public static void checkDirect(final ByteBuffer byteBuffer) {
        if (LWJGLUtil.CHECKS && !byteBuffer.isDirect()) {
            throw new IllegalArgumentException("ByteBuffer is not direct");
        }
    }
    
    public static void checkDirect(final ShortBuffer shortBuffer) {
        if (LWJGLUtil.CHECKS && !shortBuffer.isDirect()) {
            throw new IllegalArgumentException("ShortBuffer is not direct");
        }
    }
    
    public static void checkDirect(final IntBuffer intBuffer) {
        if (LWJGLUtil.CHECKS && !intBuffer.isDirect()) {
            throw new IllegalArgumentException("IntBuffer is not direct");
        }
    }
    
    public static void checkDirect(final LongBuffer longBuffer) {
        if (LWJGLUtil.CHECKS && !longBuffer.isDirect()) {
            throw new IllegalArgumentException("LongBuffer is not direct");
        }
    }
    
    public static void checkDirect(final FloatBuffer floatBuffer) {
        if (LWJGLUtil.CHECKS && !floatBuffer.isDirect()) {
            throw new IllegalArgumentException("FloatBuffer is not direct");
        }
    }
    
    public static void checkDirect(final DoubleBuffer doubleBuffer) {
        if (LWJGLUtil.CHECKS && !doubleBuffer.isDirect()) {
            throw new IllegalArgumentException("DoubleBuffer is not direct");
        }
    }
    
    public static void checkDirect(final PointerBuffer pointerBuffer) {
    }
    
    public static void checkArray(final Object[] array) {
        if (LWJGLUtil.CHECKS && (array == null || array.length == 0)) {
            throw new IllegalArgumentException("Invalid array");
        }
    }
    
    private static void throwBufferSizeException(final Buffer buffer, final int n) {
        throw new IllegalArgumentException("Number of remaining buffer elements is " + buffer.remaining() + ", must be at least " + n + ". Because at most " + n + " elements can be returned, a buffer with at least " + n + " elements is required, regardless of actual returned element count");
    }
    
    private static void throwBufferSizeException(final PointerBuffer pointerBuffer, final int n) {
        throw new IllegalArgumentException("Number of remaining pointer buffer elements is " + pointerBuffer.remaining() + ", must be at least " + n);
    }
    
    private static void throwArraySizeException(final Object[] array, final int n) {
        throw new IllegalArgumentException("Number of array elements is " + array.length + ", must be at least " + n);
    }
    
    private static void throwArraySizeException(final long[] array, final int n) {
        throw new IllegalArgumentException("Number of array elements is " + array.length + ", must be at least " + n);
    }
    
    public static void checkBufferSize(final Buffer buffer, final int n) {
        if (LWJGLUtil.CHECKS && buffer.remaining() < n) {
            throwBufferSizeException(buffer, n);
        }
    }
    
    public static int checkBuffer(final Buffer buffer, final int n) {
        if (buffer instanceof ByteBuffer) {
            checkBuffer((ByteBuffer)buffer, n);
        }
        else if (buffer instanceof ShortBuffer) {
            checkBuffer((ShortBuffer)buffer, n);
        }
        else if (buffer instanceof IntBuffer) {
            checkBuffer((IntBuffer)buffer, n);
        }
        else if (buffer instanceof LongBuffer) {
            checkBuffer((LongBuffer)buffer, n);
        }
        else if (buffer instanceof FloatBuffer) {
            checkBuffer((FloatBuffer)buffer, n);
        }
        else {
            if (!(buffer instanceof DoubleBuffer)) {
                throw new IllegalArgumentException("Unsupported Buffer type specified: " + buffer.getClass());
            }
            checkBuffer((DoubleBuffer)buffer, n);
        }
        return buffer.position() << 4;
    }
    
    public static void checkBuffer(final ByteBuffer byteBuffer, final int n) {
        if (LWJGLUtil.CHECKS) {
            checkBufferSize(byteBuffer, n);
            checkDirect(byteBuffer);
        }
    }
    
    public static void checkBuffer(final ShortBuffer shortBuffer, final int n) {
        if (LWJGLUtil.CHECKS) {
            checkBufferSize(shortBuffer, n);
            checkDirect(shortBuffer);
        }
    }
    
    public static void checkBuffer(final IntBuffer intBuffer, final int n) {
        if (LWJGLUtil.CHECKS) {
            checkBufferSize(intBuffer, n);
            checkDirect(intBuffer);
        }
    }
    
    public static void checkBuffer(final LongBuffer longBuffer, final int n) {
        if (LWJGLUtil.CHECKS) {
            checkBufferSize(longBuffer, n);
            checkDirect(longBuffer);
        }
    }
    
    public static void checkBuffer(final FloatBuffer floatBuffer, final int n) {
        if (LWJGLUtil.CHECKS) {
            checkBufferSize(floatBuffer, n);
            checkDirect(floatBuffer);
        }
    }
    
    public static void checkBuffer(final DoubleBuffer doubleBuffer, final int n) {
        if (LWJGLUtil.CHECKS) {
            checkBufferSize(doubleBuffer, n);
            checkDirect(doubleBuffer);
        }
    }
    
    public static void checkBuffer(final PointerBuffer pointerBuffer, final int n) {
        if (LWJGLUtil.CHECKS && pointerBuffer.remaining() < n) {
            throwBufferSizeException(pointerBuffer, n);
        }
    }
    
    public static void checkArray(final Object[] array, final int n) {
        if (LWJGLUtil.CHECKS && array.length < n) {
            throwArraySizeException(array, n);
        }
    }
    
    public static void checkArray(final long[] array, final int n) {
        if (LWJGLUtil.CHECKS && array.length < n) {
            throwArraySizeException(array, n);
        }
    }
}
