package org.lwjgl.opengl;

import org.lwjgl.*;

public final class EXTStencilTwoSide
{
    public static final int GL_STENCIL_TEST_TWO_SIDE_EXT = 35088;
    public static final int GL_ACTIVE_STENCIL_FACE_EXT = 35089;
    
    private EXTStencilTwoSide() {
    }
    
    public static void glActiveStencilFaceEXT(final int n) {
        final long glActiveStencilFaceEXT = GLContext.getCapabilities().glActiveStencilFaceEXT;
        BufferChecks.checkFunctionAddress(glActiveStencilFaceEXT);
        nglActiveStencilFaceEXT(n, glActiveStencilFaceEXT);
    }
    
    static native void nglActiveStencilFaceEXT(final int p0, final long p1);
}
