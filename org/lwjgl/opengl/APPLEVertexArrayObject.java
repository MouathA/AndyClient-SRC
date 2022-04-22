package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class APPLEVertexArrayObject
{
    public static final int GL_VERTEX_ARRAY_BINDING_APPLE = 34229;
    
    private APPLEVertexArrayObject() {
    }
    
    public static void glBindVertexArrayAPPLE(final int n) {
        final long glBindVertexArrayAPPLE = GLContext.getCapabilities().glBindVertexArrayAPPLE;
        BufferChecks.checkFunctionAddress(glBindVertexArrayAPPLE);
        nglBindVertexArrayAPPLE(n, glBindVertexArrayAPPLE);
    }
    
    static native void nglBindVertexArrayAPPLE(final int p0, final long p1);
    
    public static void glDeleteVertexArraysAPPLE(final IntBuffer intBuffer) {
        final long glDeleteVertexArraysAPPLE = GLContext.getCapabilities().glDeleteVertexArraysAPPLE;
        BufferChecks.checkFunctionAddress(glDeleteVertexArraysAPPLE);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteVertexArraysAPPLE(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteVertexArraysAPPLE);
    }
    
    static native void nglDeleteVertexArraysAPPLE(final int p0, final long p1, final long p2);
    
    public static void glDeleteVertexArraysAPPLE(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteVertexArraysAPPLE = capabilities.glDeleteVertexArraysAPPLE;
        BufferChecks.checkFunctionAddress(glDeleteVertexArraysAPPLE);
        nglDeleteVertexArraysAPPLE(1, APIUtil.getInt(capabilities, n), glDeleteVertexArraysAPPLE);
    }
    
    public static void glGenVertexArraysAPPLE(final IntBuffer intBuffer) {
        final long glGenVertexArraysAPPLE = GLContext.getCapabilities().glGenVertexArraysAPPLE;
        BufferChecks.checkFunctionAddress(glGenVertexArraysAPPLE);
        BufferChecks.checkDirect(intBuffer);
        nglGenVertexArraysAPPLE(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenVertexArraysAPPLE);
    }
    
    static native void nglGenVertexArraysAPPLE(final int p0, final long p1, final long p2);
    
    public static int glGenVertexArraysAPPLE() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenVertexArraysAPPLE = capabilities.glGenVertexArraysAPPLE;
        BufferChecks.checkFunctionAddress(glGenVertexArraysAPPLE);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenVertexArraysAPPLE(1, MemoryUtil.getAddress(bufferInt), glGenVertexArraysAPPLE);
        return bufferInt.get(0);
    }
    
    public static boolean glIsVertexArrayAPPLE(final int n) {
        final long glIsVertexArrayAPPLE = GLContext.getCapabilities().glIsVertexArrayAPPLE;
        BufferChecks.checkFunctionAddress(glIsVertexArrayAPPLE);
        return nglIsVertexArrayAPPLE(n, glIsVertexArrayAPPLE);
    }
    
    static native boolean nglIsVertexArrayAPPLE(final int p0, final long p1);
}
