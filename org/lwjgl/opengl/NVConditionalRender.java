package org.lwjgl.opengl;

import org.lwjgl.*;

public final class NVConditionalRender
{
    public static final int GL_QUERY_WAIT_NV = 36371;
    public static final int GL_QUERY_NO_WAIT_NV = 36372;
    public static final int GL_QUERY_BY_REGION_WAIT_NV = 36373;
    public static final int GL_QUERY_BY_REGION_NO_WAIT_NV = 36374;
    
    private NVConditionalRender() {
    }
    
    public static void glBeginConditionalRenderNV(final int n, final int n2) {
        final long glBeginConditionalRenderNV = GLContext.getCapabilities().glBeginConditionalRenderNV;
        BufferChecks.checkFunctionAddress(glBeginConditionalRenderNV);
        nglBeginConditionalRenderNV(n, n2, glBeginConditionalRenderNV);
    }
    
    static native void nglBeginConditionalRenderNV(final int p0, final int p1, final long p2);
    
    public static void glEndConditionalRenderNV() {
        final long glEndConditionalRenderNV = GLContext.getCapabilities().glEndConditionalRenderNV;
        BufferChecks.checkFunctionAddress(glEndConditionalRenderNV);
        nglEndConditionalRenderNV(glEndConditionalRenderNV);
    }
    
    static native void nglEndConditionalRenderNV(final long p0);
}
