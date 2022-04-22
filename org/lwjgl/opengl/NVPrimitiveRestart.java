package org.lwjgl.opengl;

import org.lwjgl.*;

public final class NVPrimitiveRestart
{
    public static final int GL_PRIMITIVE_RESTART_NV = 34136;
    public static final int GL_PRIMITIVE_RESTART_INDEX_NV = 34137;
    
    private NVPrimitiveRestart() {
    }
    
    public static void glPrimitiveRestartNV() {
        final long glPrimitiveRestartNV = GLContext.getCapabilities().glPrimitiveRestartNV;
        BufferChecks.checkFunctionAddress(glPrimitiveRestartNV);
        nglPrimitiveRestartNV(glPrimitiveRestartNV);
    }
    
    static native void nglPrimitiveRestartNV(final long p0);
    
    public static void glPrimitiveRestartIndexNV(final int n) {
        final long glPrimitiveRestartIndexNV = GLContext.getCapabilities().glPrimitiveRestartIndexNV;
        BufferChecks.checkFunctionAddress(glPrimitiveRestartIndexNV);
        nglPrimitiveRestartIndexNV(n, glPrimitiveRestartIndexNV);
    }
    
    static native void nglPrimitiveRestartIndexNV(final int p0, final long p1);
}
