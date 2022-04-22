package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class ATIVertexArrayObject
{
    public static final int GL_STATIC_ATI = 34656;
    public static final int GL_DYNAMIC_ATI = 34657;
    public static final int GL_PRESERVE_ATI = 34658;
    public static final int GL_DISCARD_ATI = 34659;
    public static final int GL_OBJECT_BUFFER_SIZE_ATI = 34660;
    public static final int GL_OBJECT_BUFFER_USAGE_ATI = 34661;
    public static final int GL_ARRAY_OBJECT_BUFFER_ATI = 34662;
    public static final int GL_ARRAY_OBJECT_OFFSET_ATI = 34663;
    
    private ATIVertexArrayObject() {
    }
    
    public static int glNewObjectBufferATI(final int n, final int n2) {
        final long glNewObjectBufferATI = GLContext.getCapabilities().glNewObjectBufferATI;
        BufferChecks.checkFunctionAddress(glNewObjectBufferATI);
        return nglNewObjectBufferATI(n, 0L, n2, glNewObjectBufferATI);
    }
    
    public static int glNewObjectBufferATI(final ByteBuffer byteBuffer, final int n) {
        final long glNewObjectBufferATI = GLContext.getCapabilities().glNewObjectBufferATI;
        BufferChecks.checkFunctionAddress(glNewObjectBufferATI);
        BufferChecks.checkDirect(byteBuffer);
        return nglNewObjectBufferATI(byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), n, glNewObjectBufferATI);
    }
    
    public static int glNewObjectBufferATI(final DoubleBuffer doubleBuffer, final int n) {
        final long glNewObjectBufferATI = GLContext.getCapabilities().glNewObjectBufferATI;
        BufferChecks.checkFunctionAddress(glNewObjectBufferATI);
        BufferChecks.checkDirect(doubleBuffer);
        return nglNewObjectBufferATI(doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), n, glNewObjectBufferATI);
    }
    
    public static int glNewObjectBufferATI(final FloatBuffer floatBuffer, final int n) {
        final long glNewObjectBufferATI = GLContext.getCapabilities().glNewObjectBufferATI;
        BufferChecks.checkFunctionAddress(glNewObjectBufferATI);
        BufferChecks.checkDirect(floatBuffer);
        return nglNewObjectBufferATI(floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), n, glNewObjectBufferATI);
    }
    
    public static int glNewObjectBufferATI(final IntBuffer intBuffer, final int n) {
        final long glNewObjectBufferATI = GLContext.getCapabilities().glNewObjectBufferATI;
        BufferChecks.checkFunctionAddress(glNewObjectBufferATI);
        BufferChecks.checkDirect(intBuffer);
        return nglNewObjectBufferATI(intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), n, glNewObjectBufferATI);
    }
    
    public static int glNewObjectBufferATI(final ShortBuffer shortBuffer, final int n) {
        final long glNewObjectBufferATI = GLContext.getCapabilities().glNewObjectBufferATI;
        BufferChecks.checkFunctionAddress(glNewObjectBufferATI);
        BufferChecks.checkDirect(shortBuffer);
        return nglNewObjectBufferATI(shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), n, glNewObjectBufferATI);
    }
    
    static native int nglNewObjectBufferATI(final int p0, final long p1, final int p2, final long p3);
    
    public static boolean glIsObjectBufferATI(final int n) {
        final long glIsObjectBufferATI = GLContext.getCapabilities().glIsObjectBufferATI;
        BufferChecks.checkFunctionAddress(glIsObjectBufferATI);
        return nglIsObjectBufferATI(n, glIsObjectBufferATI);
    }
    
    static native boolean nglIsObjectBufferATI(final int p0, final long p1);
    
    public static void glUpdateObjectBufferATI(final int n, final int n2, final ByteBuffer byteBuffer, final int n3) {
        final long glUpdateObjectBufferATI = GLContext.getCapabilities().glUpdateObjectBufferATI;
        BufferChecks.checkFunctionAddress(glUpdateObjectBufferATI);
        BufferChecks.checkDirect(byteBuffer);
        nglUpdateObjectBufferATI(n, n2, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), n3, glUpdateObjectBufferATI);
    }
    
    public static void glUpdateObjectBufferATI(final int n, final int n2, final DoubleBuffer doubleBuffer, final int n3) {
        final long glUpdateObjectBufferATI = GLContext.getCapabilities().glUpdateObjectBufferATI;
        BufferChecks.checkFunctionAddress(glUpdateObjectBufferATI);
        BufferChecks.checkDirect(doubleBuffer);
        nglUpdateObjectBufferATI(n, n2, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), n3, glUpdateObjectBufferATI);
    }
    
    public static void glUpdateObjectBufferATI(final int n, final int n2, final FloatBuffer floatBuffer, final int n3) {
        final long glUpdateObjectBufferATI = GLContext.getCapabilities().glUpdateObjectBufferATI;
        BufferChecks.checkFunctionAddress(glUpdateObjectBufferATI);
        BufferChecks.checkDirect(floatBuffer);
        nglUpdateObjectBufferATI(n, n2, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), n3, glUpdateObjectBufferATI);
    }
    
    public static void glUpdateObjectBufferATI(final int n, final int n2, final IntBuffer intBuffer, final int n3) {
        final long glUpdateObjectBufferATI = GLContext.getCapabilities().glUpdateObjectBufferATI;
        BufferChecks.checkFunctionAddress(glUpdateObjectBufferATI);
        BufferChecks.checkDirect(intBuffer);
        nglUpdateObjectBufferATI(n, n2, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), n3, glUpdateObjectBufferATI);
    }
    
    public static void glUpdateObjectBufferATI(final int n, final int n2, final ShortBuffer shortBuffer, final int n3) {
        final long glUpdateObjectBufferATI = GLContext.getCapabilities().glUpdateObjectBufferATI;
        BufferChecks.checkFunctionAddress(glUpdateObjectBufferATI);
        BufferChecks.checkDirect(shortBuffer);
        nglUpdateObjectBufferATI(n, n2, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), n3, glUpdateObjectBufferATI);
    }
    
    static native void nglUpdateObjectBufferATI(final int p0, final int p1, final int p2, final long p3, final int p4, final long p5);
    
    public static void glGetObjectBufferATI(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetObjectBufferfvATI = GLContext.getCapabilities().glGetObjectBufferfvATI;
        BufferChecks.checkFunctionAddress(glGetObjectBufferfvATI);
        BufferChecks.checkDirect(floatBuffer);
        nglGetObjectBufferfvATI(n, n2, MemoryUtil.getAddress(floatBuffer), glGetObjectBufferfvATI);
    }
    
    static native void nglGetObjectBufferfvATI(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetObjectBufferATI(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetObjectBufferivATI = GLContext.getCapabilities().glGetObjectBufferivATI;
        BufferChecks.checkFunctionAddress(glGetObjectBufferivATI);
        BufferChecks.checkDirect(intBuffer);
        nglGetObjectBufferivATI(n, n2, MemoryUtil.getAddress(intBuffer), glGetObjectBufferivATI);
    }
    
    static native void nglGetObjectBufferivATI(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetObjectBufferiATI(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetObjectBufferivATI = capabilities.glGetObjectBufferivATI;
        BufferChecks.checkFunctionAddress(glGetObjectBufferivATI);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetObjectBufferivATI(n, n2, MemoryUtil.getAddress(bufferInt), glGetObjectBufferivATI);
        return bufferInt.get(0);
    }
    
    public static void glFreeObjectBufferATI(final int n) {
        final long glFreeObjectBufferATI = GLContext.getCapabilities().glFreeObjectBufferATI;
        BufferChecks.checkFunctionAddress(glFreeObjectBufferATI);
        nglFreeObjectBufferATI(n, glFreeObjectBufferATI);
    }
    
    static native void nglFreeObjectBufferATI(final int p0, final long p1);
    
    public static void glArrayObjectATI(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glArrayObjectATI = GLContext.getCapabilities().glArrayObjectATI;
        BufferChecks.checkFunctionAddress(glArrayObjectATI);
        nglArrayObjectATI(n, n2, n3, n4, n5, n6, glArrayObjectATI);
    }
    
    static native void nglArrayObjectATI(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glGetArrayObjectATI(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetArrayObjectfvATI = GLContext.getCapabilities().glGetArrayObjectfvATI;
        BufferChecks.checkFunctionAddress(glGetArrayObjectfvATI);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetArrayObjectfvATI(n, n2, MemoryUtil.getAddress(floatBuffer), glGetArrayObjectfvATI);
    }
    
    static native void nglGetArrayObjectfvATI(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetArrayObjectATI(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetArrayObjectivATI = GLContext.getCapabilities().glGetArrayObjectivATI;
        BufferChecks.checkFunctionAddress(glGetArrayObjectivATI);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetArrayObjectivATI(n, n2, MemoryUtil.getAddress(intBuffer), glGetArrayObjectivATI);
    }
    
    static native void nglGetArrayObjectivATI(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVariantArrayObjectATI(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glVariantArrayObjectATI = GLContext.getCapabilities().glVariantArrayObjectATI;
        BufferChecks.checkFunctionAddress(glVariantArrayObjectATI);
        nglVariantArrayObjectATI(n, n2, n3, n4, n5, glVariantArrayObjectATI);
    }
    
    static native void nglVariantArrayObjectATI(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glGetVariantArrayObjectATI(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetVariantArrayObjectfvATI = GLContext.getCapabilities().glGetVariantArrayObjectfvATI;
        BufferChecks.checkFunctionAddress(glGetVariantArrayObjectfvATI);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetVariantArrayObjectfvATI(n, n2, MemoryUtil.getAddress(floatBuffer), glGetVariantArrayObjectfvATI);
    }
    
    static native void nglGetVariantArrayObjectfvATI(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetVariantArrayObjectATI(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetVariantArrayObjectivATI = GLContext.getCapabilities().glGetVariantArrayObjectivATI;
        BufferChecks.checkFunctionAddress(glGetVariantArrayObjectivATI);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetVariantArrayObjectivATI(n, n2, MemoryUtil.getAddress(intBuffer), glGetVariantArrayObjectivATI);
    }
    
    static native void nglGetVariantArrayObjectivATI(final int p0, final int p1, final long p2, final long p3);
}
