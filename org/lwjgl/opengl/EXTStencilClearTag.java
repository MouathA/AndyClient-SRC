package org.lwjgl.opengl;

import org.lwjgl.*;

public final class EXTStencilClearTag
{
    public static final int GL_STENCIL_TAG_BITS_EXT = 35058;
    public static final int GL_STENCIL_CLEAR_TAG_VALUE_EXT = 35059;
    
    private EXTStencilClearTag() {
    }
    
    public static void glStencilClearTagEXT(final int n, final int n2) {
        final long glStencilClearTagEXT = GLContext.getCapabilities().glStencilClearTagEXT;
        BufferChecks.checkFunctionAddress(glStencilClearTagEXT);
        nglStencilClearTagEXT(n, n2, glStencilClearTagEXT);
    }
    
    static native void nglStencilClearTagEXT(final int p0, final int p1, final long p2);
}
