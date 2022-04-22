package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class NVFence
{
    public static final int GL_ALL_COMPLETED_NV = 34034;
    public static final int GL_FENCE_STATUS_NV = 34035;
    public static final int GL_FENCE_CONDITION_NV = 34036;
    
    private NVFence() {
    }
    
    public static void glGenFencesNV(final IntBuffer intBuffer) {
        final long glGenFencesNV = GLContext.getCapabilities().glGenFencesNV;
        BufferChecks.checkFunctionAddress(glGenFencesNV);
        BufferChecks.checkDirect(intBuffer);
        nglGenFencesNV(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenFencesNV);
    }
    
    static native void nglGenFencesNV(final int p0, final long p1, final long p2);
    
    public static int glGenFencesNV() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenFencesNV = capabilities.glGenFencesNV;
        BufferChecks.checkFunctionAddress(glGenFencesNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenFencesNV(1, MemoryUtil.getAddress(bufferInt), glGenFencesNV);
        return bufferInt.get(0);
    }
    
    public static void glDeleteFencesNV(final IntBuffer intBuffer) {
        final long glDeleteFencesNV = GLContext.getCapabilities().glDeleteFencesNV;
        BufferChecks.checkFunctionAddress(glDeleteFencesNV);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteFencesNV(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteFencesNV);
    }
    
    static native void nglDeleteFencesNV(final int p0, final long p1, final long p2);
    
    public static void glDeleteFencesNV(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteFencesNV = capabilities.glDeleteFencesNV;
        BufferChecks.checkFunctionAddress(glDeleteFencesNV);
        nglDeleteFencesNV(1, APIUtil.getInt(capabilities, n), glDeleteFencesNV);
    }
    
    public static void glSetFenceNV(final int n, final int n2) {
        final long glSetFenceNV = GLContext.getCapabilities().glSetFenceNV;
        BufferChecks.checkFunctionAddress(glSetFenceNV);
        nglSetFenceNV(n, n2, glSetFenceNV);
    }
    
    static native void nglSetFenceNV(final int p0, final int p1, final long p2);
    
    public static boolean glTestFenceNV(final int n) {
        final long glTestFenceNV = GLContext.getCapabilities().glTestFenceNV;
        BufferChecks.checkFunctionAddress(glTestFenceNV);
        return nglTestFenceNV(n, glTestFenceNV);
    }
    
    static native boolean nglTestFenceNV(final int p0, final long p1);
    
    public static void glFinishFenceNV(final int n) {
        final long glFinishFenceNV = GLContext.getCapabilities().glFinishFenceNV;
        BufferChecks.checkFunctionAddress(glFinishFenceNV);
        nglFinishFenceNV(n, glFinishFenceNV);
    }
    
    static native void nglFinishFenceNV(final int p0, final long p1);
    
    public static boolean glIsFenceNV(final int n) {
        final long glIsFenceNV = GLContext.getCapabilities().glIsFenceNV;
        BufferChecks.checkFunctionAddress(glIsFenceNV);
        return nglIsFenceNV(n, glIsFenceNV);
    }
    
    static native boolean nglIsFenceNV(final int p0, final long p1);
    
    public static void glGetFenceivNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetFenceivNV = GLContext.getCapabilities().glGetFenceivNV;
        BufferChecks.checkFunctionAddress(glGetFenceivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetFenceivNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetFenceivNV);
    }
    
    static native void nglGetFenceivNV(final int p0, final int p1, final long p2, final long p3);
}
