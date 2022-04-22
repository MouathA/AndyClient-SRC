package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class APPLEFence
{
    public static final int GL_DRAW_PIXELS_APPLE = 35338;
    public static final int GL_FENCE_APPLE = 35339;
    
    private APPLEFence() {
    }
    
    public static void glGenFencesAPPLE(final IntBuffer intBuffer) {
        final long glGenFencesAPPLE = GLContext.getCapabilities().glGenFencesAPPLE;
        BufferChecks.checkFunctionAddress(glGenFencesAPPLE);
        BufferChecks.checkDirect(intBuffer);
        nglGenFencesAPPLE(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenFencesAPPLE);
    }
    
    static native void nglGenFencesAPPLE(final int p0, final long p1, final long p2);
    
    public static int glGenFencesAPPLE() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenFencesAPPLE = capabilities.glGenFencesAPPLE;
        BufferChecks.checkFunctionAddress(glGenFencesAPPLE);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenFencesAPPLE(1, MemoryUtil.getAddress(bufferInt), glGenFencesAPPLE);
        return bufferInt.get(0);
    }
    
    public static void glDeleteFencesAPPLE(final IntBuffer intBuffer) {
        final long glDeleteFencesAPPLE = GLContext.getCapabilities().glDeleteFencesAPPLE;
        BufferChecks.checkFunctionAddress(glDeleteFencesAPPLE);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteFencesAPPLE(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteFencesAPPLE);
    }
    
    static native void nglDeleteFencesAPPLE(final int p0, final long p1, final long p2);
    
    public static void glDeleteFencesAPPLE(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteFencesAPPLE = capabilities.glDeleteFencesAPPLE;
        BufferChecks.checkFunctionAddress(glDeleteFencesAPPLE);
        nglDeleteFencesAPPLE(1, APIUtil.getInt(capabilities, n), glDeleteFencesAPPLE);
    }
    
    public static void glSetFenceAPPLE(final int n) {
        final long glSetFenceAPPLE = GLContext.getCapabilities().glSetFenceAPPLE;
        BufferChecks.checkFunctionAddress(glSetFenceAPPLE);
        nglSetFenceAPPLE(n, glSetFenceAPPLE);
    }
    
    static native void nglSetFenceAPPLE(final int p0, final long p1);
    
    public static boolean glIsFenceAPPLE(final int n) {
        final long glIsFenceAPPLE = GLContext.getCapabilities().glIsFenceAPPLE;
        BufferChecks.checkFunctionAddress(glIsFenceAPPLE);
        return nglIsFenceAPPLE(n, glIsFenceAPPLE);
    }
    
    static native boolean nglIsFenceAPPLE(final int p0, final long p1);
    
    public static boolean glTestFenceAPPLE(final int n) {
        final long glTestFenceAPPLE = GLContext.getCapabilities().glTestFenceAPPLE;
        BufferChecks.checkFunctionAddress(glTestFenceAPPLE);
        return nglTestFenceAPPLE(n, glTestFenceAPPLE);
    }
    
    static native boolean nglTestFenceAPPLE(final int p0, final long p1);
    
    public static void glFinishFenceAPPLE(final int n) {
        final long glFinishFenceAPPLE = GLContext.getCapabilities().glFinishFenceAPPLE;
        BufferChecks.checkFunctionAddress(glFinishFenceAPPLE);
        nglFinishFenceAPPLE(n, glFinishFenceAPPLE);
    }
    
    static native void nglFinishFenceAPPLE(final int p0, final long p1);
    
    public static boolean glTestObjectAPPLE(final int n, final int n2) {
        final long glTestObjectAPPLE = GLContext.getCapabilities().glTestObjectAPPLE;
        BufferChecks.checkFunctionAddress(glTestObjectAPPLE);
        return nglTestObjectAPPLE(n, n2, glTestObjectAPPLE);
    }
    
    static native boolean nglTestObjectAPPLE(final int p0, final int p1, final long p2);
    
    public static void glFinishObjectAPPLE(final int n, final int n2) {
        final long glFinishObjectAPPLE = GLContext.getCapabilities().glFinishObjectAPPLE;
        BufferChecks.checkFunctionAddress(glFinishObjectAPPLE);
        nglFinishObjectAPPLE(n, n2, glFinishObjectAPPLE);
    }
    
    static native void nglFinishObjectAPPLE(final int p0, final int p1, final long p2);
}
