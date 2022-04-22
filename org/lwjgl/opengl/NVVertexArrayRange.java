package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class NVVertexArrayRange
{
    public static final int GL_VERTEX_ARRAY_RANGE_NV = 34077;
    public static final int GL_VERTEX_ARRAY_RANGE_LENGTH_NV = 34078;
    public static final int GL_VERTEX_ARRAY_RANGE_VALID_NV = 34079;
    public static final int GL_MAX_VERTEX_ARRAY_RANGE_ELEMENT_NV = 34080;
    public static final int GL_VERTEX_ARRAY_RANGE_POINTER_NV = 34081;
    
    private NVVertexArrayRange() {
    }
    
    public static void glVertexArrayRangeNV(final ByteBuffer byteBuffer) {
        final long glVertexArrayRangeNV = GLContext.getCapabilities().glVertexArrayRangeNV;
        BufferChecks.checkFunctionAddress(glVertexArrayRangeNV);
        BufferChecks.checkDirect(byteBuffer);
        nglVertexArrayRangeNV(byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glVertexArrayRangeNV);
    }
    
    public static void glVertexArrayRangeNV(final DoubleBuffer doubleBuffer) {
        final long glVertexArrayRangeNV = GLContext.getCapabilities().glVertexArrayRangeNV;
        BufferChecks.checkFunctionAddress(glVertexArrayRangeNV);
        BufferChecks.checkDirect(doubleBuffer);
        nglVertexArrayRangeNV(doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glVertexArrayRangeNV);
    }
    
    public static void glVertexArrayRangeNV(final FloatBuffer floatBuffer) {
        final long glVertexArrayRangeNV = GLContext.getCapabilities().glVertexArrayRangeNV;
        BufferChecks.checkFunctionAddress(glVertexArrayRangeNV);
        BufferChecks.checkDirect(floatBuffer);
        nglVertexArrayRangeNV(floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glVertexArrayRangeNV);
    }
    
    public static void glVertexArrayRangeNV(final IntBuffer intBuffer) {
        final long glVertexArrayRangeNV = GLContext.getCapabilities().glVertexArrayRangeNV;
        BufferChecks.checkFunctionAddress(glVertexArrayRangeNV);
        BufferChecks.checkDirect(intBuffer);
        nglVertexArrayRangeNV(intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glVertexArrayRangeNV);
    }
    
    public static void glVertexArrayRangeNV(final ShortBuffer shortBuffer) {
        final long glVertexArrayRangeNV = GLContext.getCapabilities().glVertexArrayRangeNV;
        BufferChecks.checkFunctionAddress(glVertexArrayRangeNV);
        BufferChecks.checkDirect(shortBuffer);
        nglVertexArrayRangeNV(shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glVertexArrayRangeNV);
    }
    
    static native void nglVertexArrayRangeNV(final int p0, final long p1, final long p2);
    
    public static void glFlushVertexArrayRangeNV() {
        final long glFlushVertexArrayRangeNV = GLContext.getCapabilities().glFlushVertexArrayRangeNV;
        BufferChecks.checkFunctionAddress(glFlushVertexArrayRangeNV);
        nglFlushVertexArrayRangeNV(glFlushVertexArrayRangeNV);
    }
    
    static native void nglFlushVertexArrayRangeNV(final long p0);
    
    public static ByteBuffer glAllocateMemoryNV(final int n, final float n2, final float n3, final float n4) {
        final long glAllocateMemoryNV = GLContext.getCapabilities().glAllocateMemoryNV;
        BufferChecks.checkFunctionAddress(glAllocateMemoryNV);
        final ByteBuffer nglAllocateMemoryNV = nglAllocateMemoryNV(n, n2, n3, n4, n, glAllocateMemoryNV);
        return (LWJGLUtil.CHECKS && nglAllocateMemoryNV == null) ? null : nglAllocateMemoryNV.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglAllocateMemoryNV(final int p0, final float p1, final float p2, final float p3, final long p4, final long p5);
    
    public static void glFreeMemoryNV(final ByteBuffer byteBuffer) {
        final long glFreeMemoryNV = GLContext.getCapabilities().glFreeMemoryNV;
        BufferChecks.checkFunctionAddress(glFreeMemoryNV);
        BufferChecks.checkDirect(byteBuffer);
        nglFreeMemoryNV(MemoryUtil.getAddress(byteBuffer), glFreeMemoryNV);
    }
    
    static native void nglFreeMemoryNV(final long p0, final long p1);
}
