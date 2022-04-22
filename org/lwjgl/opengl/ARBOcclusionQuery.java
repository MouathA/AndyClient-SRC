package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class ARBOcclusionQuery
{
    public static final int GL_SAMPLES_PASSED_ARB = 35092;
    public static final int GL_QUERY_COUNTER_BITS_ARB = 34916;
    public static final int GL_CURRENT_QUERY_ARB = 34917;
    public static final int GL_QUERY_RESULT_ARB = 34918;
    public static final int GL_QUERY_RESULT_AVAILABLE_ARB = 34919;
    
    private ARBOcclusionQuery() {
    }
    
    public static void glGenQueriesARB(final IntBuffer intBuffer) {
        final long glGenQueriesARB = GLContext.getCapabilities().glGenQueriesARB;
        BufferChecks.checkFunctionAddress(glGenQueriesARB);
        BufferChecks.checkDirect(intBuffer);
        nglGenQueriesARB(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenQueriesARB);
    }
    
    static native void nglGenQueriesARB(final int p0, final long p1, final long p2);
    
    public static int glGenQueriesARB() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenQueriesARB = capabilities.glGenQueriesARB;
        BufferChecks.checkFunctionAddress(glGenQueriesARB);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenQueriesARB(1, MemoryUtil.getAddress(bufferInt), glGenQueriesARB);
        return bufferInt.get(0);
    }
    
    public static void glDeleteQueriesARB(final IntBuffer intBuffer) {
        final long glDeleteQueriesARB = GLContext.getCapabilities().glDeleteQueriesARB;
        BufferChecks.checkFunctionAddress(glDeleteQueriesARB);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteQueriesARB(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteQueriesARB);
    }
    
    static native void nglDeleteQueriesARB(final int p0, final long p1, final long p2);
    
    public static void glDeleteQueriesARB(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteQueriesARB = capabilities.glDeleteQueriesARB;
        BufferChecks.checkFunctionAddress(glDeleteQueriesARB);
        nglDeleteQueriesARB(1, APIUtil.getInt(capabilities, n), glDeleteQueriesARB);
    }
    
    public static boolean glIsQueryARB(final int n) {
        final long glIsQueryARB = GLContext.getCapabilities().glIsQueryARB;
        BufferChecks.checkFunctionAddress(glIsQueryARB);
        return nglIsQueryARB(n, glIsQueryARB);
    }
    
    static native boolean nglIsQueryARB(final int p0, final long p1);
    
    public static void glBeginQueryARB(final int n, final int n2) {
        final long glBeginQueryARB = GLContext.getCapabilities().glBeginQueryARB;
        BufferChecks.checkFunctionAddress(glBeginQueryARB);
        nglBeginQueryARB(n, n2, glBeginQueryARB);
    }
    
    static native void nglBeginQueryARB(final int p0, final int p1, final long p2);
    
    public static void glEndQueryARB(final int n) {
        final long glEndQueryARB = GLContext.getCapabilities().glEndQueryARB;
        BufferChecks.checkFunctionAddress(glEndQueryARB);
        nglEndQueryARB(n, glEndQueryARB);
    }
    
    static native void nglEndQueryARB(final int p0, final long p1);
    
    public static void glGetQueryARB(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetQueryivARB = GLContext.getCapabilities().glGetQueryivARB;
        BufferChecks.checkFunctionAddress(glGetQueryivARB);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetQueryivARB(n, n2, MemoryUtil.getAddress(intBuffer), glGetQueryivARB);
    }
    
    static native void nglGetQueryivARB(final int p0, final int p1, final long p2, final long p3);
    
    @Deprecated
    public static int glGetQueryARB(final int n, final int n2) {
        return glGetQueryiARB(n, n2);
    }
    
    public static int glGetQueryiARB(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetQueryivARB = capabilities.glGetQueryivARB;
        BufferChecks.checkFunctionAddress(glGetQueryivARB);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetQueryivARB(n, n2, MemoryUtil.getAddress(bufferInt), glGetQueryivARB);
        return bufferInt.get(0);
    }
    
    public static void glGetQueryObjectARB(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetQueryObjectivARB = GLContext.getCapabilities().glGetQueryObjectivARB;
        BufferChecks.checkFunctionAddress(glGetQueryObjectivARB);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetQueryObjectivARB(n, n2, MemoryUtil.getAddress(intBuffer), glGetQueryObjectivARB);
    }
    
    static native void nglGetQueryObjectivARB(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetQueryObjectiARB(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetQueryObjectivARB = capabilities.glGetQueryObjectivARB;
        BufferChecks.checkFunctionAddress(glGetQueryObjectivARB);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetQueryObjectivARB(n, n2, MemoryUtil.getAddress(bufferInt), glGetQueryObjectivARB);
        return bufferInt.get(0);
    }
    
    public static void glGetQueryObjectuARB(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetQueryObjectuivARB = GLContext.getCapabilities().glGetQueryObjectuivARB;
        BufferChecks.checkFunctionAddress(glGetQueryObjectuivARB);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetQueryObjectuivARB(n, n2, MemoryUtil.getAddress(intBuffer), glGetQueryObjectuivARB);
    }
    
    static native void nglGetQueryObjectuivARB(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetQueryObjectuiARB(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetQueryObjectuivARB = capabilities.glGetQueryObjectuivARB;
        BufferChecks.checkFunctionAddress(glGetQueryObjectuivARB);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetQueryObjectuivARB(n, n2, MemoryUtil.getAddress(bufferInt), glGetQueryObjectuivARB);
        return bufferInt.get(0);
    }
}
