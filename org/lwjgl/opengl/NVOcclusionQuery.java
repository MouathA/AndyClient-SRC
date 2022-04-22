package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class NVOcclusionQuery
{
    public static final int GL_OCCLUSION_TEST_HP = 33125;
    public static final int GL_OCCLUSION_TEST_RESULT_HP = 33126;
    public static final int GL_PIXEL_COUNTER_BITS_NV = 34916;
    public static final int GL_CURRENT_OCCLUSION_QUERY_ID_NV = 34917;
    public static final int GL_PIXEL_COUNT_NV = 34918;
    public static final int GL_PIXEL_COUNT_AVAILABLE_NV = 34919;
    
    private NVOcclusionQuery() {
    }
    
    public static void glGenOcclusionQueriesNV(final IntBuffer intBuffer) {
        final long glGenOcclusionQueriesNV = GLContext.getCapabilities().glGenOcclusionQueriesNV;
        BufferChecks.checkFunctionAddress(glGenOcclusionQueriesNV);
        BufferChecks.checkDirect(intBuffer);
        nglGenOcclusionQueriesNV(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenOcclusionQueriesNV);
    }
    
    static native void nglGenOcclusionQueriesNV(final int p0, final long p1, final long p2);
    
    public static int glGenOcclusionQueriesNV() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenOcclusionQueriesNV = capabilities.glGenOcclusionQueriesNV;
        BufferChecks.checkFunctionAddress(glGenOcclusionQueriesNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenOcclusionQueriesNV(1, MemoryUtil.getAddress(bufferInt), glGenOcclusionQueriesNV);
        return bufferInt.get(0);
    }
    
    public static void glDeleteOcclusionQueriesNV(final IntBuffer intBuffer) {
        final long glDeleteOcclusionQueriesNV = GLContext.getCapabilities().glDeleteOcclusionQueriesNV;
        BufferChecks.checkFunctionAddress(glDeleteOcclusionQueriesNV);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteOcclusionQueriesNV(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteOcclusionQueriesNV);
    }
    
    static native void nglDeleteOcclusionQueriesNV(final int p0, final long p1, final long p2);
    
    public static void glDeleteOcclusionQueriesNV(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteOcclusionQueriesNV = capabilities.glDeleteOcclusionQueriesNV;
        BufferChecks.checkFunctionAddress(glDeleteOcclusionQueriesNV);
        nglDeleteOcclusionQueriesNV(1, APIUtil.getInt(capabilities, n), glDeleteOcclusionQueriesNV);
    }
    
    public static boolean glIsOcclusionQueryNV(final int n) {
        final long glIsOcclusionQueryNV = GLContext.getCapabilities().glIsOcclusionQueryNV;
        BufferChecks.checkFunctionAddress(glIsOcclusionQueryNV);
        return nglIsOcclusionQueryNV(n, glIsOcclusionQueryNV);
    }
    
    static native boolean nglIsOcclusionQueryNV(final int p0, final long p1);
    
    public static void glBeginOcclusionQueryNV(final int n) {
        final long glBeginOcclusionQueryNV = GLContext.getCapabilities().glBeginOcclusionQueryNV;
        BufferChecks.checkFunctionAddress(glBeginOcclusionQueryNV);
        nglBeginOcclusionQueryNV(n, glBeginOcclusionQueryNV);
    }
    
    static native void nglBeginOcclusionQueryNV(final int p0, final long p1);
    
    public static void glEndOcclusionQueryNV() {
        final long glEndOcclusionQueryNV = GLContext.getCapabilities().glEndOcclusionQueryNV;
        BufferChecks.checkFunctionAddress(glEndOcclusionQueryNV);
        nglEndOcclusionQueryNV(glEndOcclusionQueryNV);
    }
    
    static native void nglEndOcclusionQueryNV(final long p0);
    
    public static void glGetOcclusionQueryuNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetOcclusionQueryuivNV = GLContext.getCapabilities().glGetOcclusionQueryuivNV;
        BufferChecks.checkFunctionAddress(glGetOcclusionQueryuivNV);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetOcclusionQueryuivNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetOcclusionQueryuivNV);
    }
    
    static native void nglGetOcclusionQueryuivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetOcclusionQueryuiNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetOcclusionQueryuivNV = capabilities.glGetOcclusionQueryuivNV;
        BufferChecks.checkFunctionAddress(glGetOcclusionQueryuivNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetOcclusionQueryuivNV(n, n2, MemoryUtil.getAddress(bufferInt), glGetOcclusionQueryuivNV);
        return bufferInt.get(0);
    }
    
    public static void glGetOcclusionQueryNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetOcclusionQueryivNV = GLContext.getCapabilities().glGetOcclusionQueryivNV;
        BufferChecks.checkFunctionAddress(glGetOcclusionQueryivNV);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetOcclusionQueryivNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetOcclusionQueryivNV);
    }
    
    static native void nglGetOcclusionQueryivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetOcclusionQueryiNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetOcclusionQueryivNV = capabilities.glGetOcclusionQueryivNV;
        BufferChecks.checkFunctionAddress(glGetOcclusionQueryivNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetOcclusionQueryivNV(n, n2, MemoryUtil.getAddress(bufferInt), glGetOcclusionQueryivNV);
        return bufferInt.get(0);
    }
}
