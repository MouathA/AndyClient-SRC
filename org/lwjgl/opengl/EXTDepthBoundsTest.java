package org.lwjgl.opengl;

import org.lwjgl.*;

public final class EXTDepthBoundsTest
{
    public static final int GL_DEPTH_BOUNDS_TEST_EXT = 34960;
    public static final int GL_DEPTH_BOUNDS_EXT = 34961;
    
    private EXTDepthBoundsTest() {
    }
    
    public static void glDepthBoundsEXT(final double n, final double n2) {
        final long glDepthBoundsEXT = GLContext.getCapabilities().glDepthBoundsEXT;
        BufferChecks.checkFunctionAddress(glDepthBoundsEXT);
        nglDepthBoundsEXT(n, n2, glDepthBoundsEXT);
    }
    
    static native void nglDepthBoundsEXT(final double p0, final double p1, final long p2);
}
