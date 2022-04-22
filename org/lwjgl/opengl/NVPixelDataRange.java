package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class NVPixelDataRange
{
    public static final int GL_WRITE_PIXEL_DATA_RANGE_NV = 34936;
    public static final int GL_READ_PIXEL_DATA_RANGE_NV = 34937;
    public static final int GL_WRITE_PIXEL_DATA_RANGE_LENGTH_NV = 34938;
    public static final int GL_READ_PIXEL_DATA_RANGE_LENGTH_NV = 34939;
    public static final int GL_WRITE_PIXEL_DATA_RANGE_POINTER_NV = 34940;
    public static final int GL_READ_PIXEL_DATA_RANGE_POINTER_NV = 34941;
    
    private NVPixelDataRange() {
    }
    
    public static void glPixelDataRangeNV(final int n, final ByteBuffer byteBuffer) {
        final long glPixelDataRangeNV = GLContext.getCapabilities().glPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(glPixelDataRangeNV);
        BufferChecks.checkDirect(byteBuffer);
        nglPixelDataRangeNV(n, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glPixelDataRangeNV);
    }
    
    public static void glPixelDataRangeNV(final int n, final DoubleBuffer doubleBuffer) {
        final long glPixelDataRangeNV = GLContext.getCapabilities().glPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(glPixelDataRangeNV);
        BufferChecks.checkDirect(doubleBuffer);
        nglPixelDataRangeNV(n, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glPixelDataRangeNV);
    }
    
    public static void glPixelDataRangeNV(final int n, final FloatBuffer floatBuffer) {
        final long glPixelDataRangeNV = GLContext.getCapabilities().glPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(glPixelDataRangeNV);
        BufferChecks.checkDirect(floatBuffer);
        nglPixelDataRangeNV(n, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glPixelDataRangeNV);
    }
    
    public static void glPixelDataRangeNV(final int n, final IntBuffer intBuffer) {
        final long glPixelDataRangeNV = GLContext.getCapabilities().glPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(glPixelDataRangeNV);
        BufferChecks.checkDirect(intBuffer);
        nglPixelDataRangeNV(n, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glPixelDataRangeNV);
    }
    
    public static void glPixelDataRangeNV(final int n, final ShortBuffer shortBuffer) {
        final long glPixelDataRangeNV = GLContext.getCapabilities().glPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(glPixelDataRangeNV);
        BufferChecks.checkDirect(shortBuffer);
        nglPixelDataRangeNV(n, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glPixelDataRangeNV);
    }
    
    static native void nglPixelDataRangeNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glFlushPixelDataRangeNV(final int n) {
        final long glFlushPixelDataRangeNV = GLContext.getCapabilities().glFlushPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(glFlushPixelDataRangeNV);
        nglFlushPixelDataRangeNV(n, glFlushPixelDataRangeNV);
    }
    
    static native void nglFlushPixelDataRangeNV(final int p0, final long p1);
}
