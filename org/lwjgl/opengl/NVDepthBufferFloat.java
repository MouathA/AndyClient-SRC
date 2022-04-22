package org.lwjgl.opengl;

import org.lwjgl.*;

public final class NVDepthBufferFloat
{
    public static final int GL_DEPTH_COMPONENT32F_NV = 36267;
    public static final int GL_DEPTH32F_STENCIL8_NV = 36268;
    public static final int GL_FLOAT_32_UNSIGNED_INT_24_8_REV_NV = 36269;
    public static final int GL_DEPTH_BUFFER_FLOAT_MODE_NV = 36271;
    
    private NVDepthBufferFloat() {
    }
    
    public static void glDepthRangedNV(final double n, final double n2) {
        final long glDepthRangedNV = GLContext.getCapabilities().glDepthRangedNV;
        BufferChecks.checkFunctionAddress(glDepthRangedNV);
        nglDepthRangedNV(n, n2, glDepthRangedNV);
    }
    
    static native void nglDepthRangedNV(final double p0, final double p1, final long p2);
    
    public static void glClearDepthdNV(final double n) {
        final long glClearDepthdNV = GLContext.getCapabilities().glClearDepthdNV;
        BufferChecks.checkFunctionAddress(glClearDepthdNV);
        nglClearDepthdNV(n, glClearDepthdNV);
    }
    
    static native void nglClearDepthdNV(final double p0, final long p1);
    
    public static void glDepthBoundsdNV(final double n, final double n2) {
        final long glDepthBoundsdNV = GLContext.getCapabilities().glDepthBoundsdNV;
        BufferChecks.checkFunctionAddress(glDepthBoundsdNV);
        nglDepthBoundsdNV(n, n2, glDepthBoundsdNV);
    }
    
    static native void nglDepthBoundsdNV(final double p0, final double p1, final long p2);
}
