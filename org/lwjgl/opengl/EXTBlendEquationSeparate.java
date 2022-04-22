package org.lwjgl.opengl;

import org.lwjgl.*;

public final class EXTBlendEquationSeparate
{
    public static final int GL_BLEND_EQUATION_RGB_EXT = 32777;
    public static final int GL_BLEND_EQUATION_ALPHA_EXT = 34877;
    
    private EXTBlendEquationSeparate() {
    }
    
    public static void glBlendEquationSeparateEXT(final int n, final int n2) {
        final long glBlendEquationSeparateEXT = GLContext.getCapabilities().glBlendEquationSeparateEXT;
        BufferChecks.checkFunctionAddress(glBlendEquationSeparateEXT);
        nglBlendEquationSeparateEXT(n, n2, glBlendEquationSeparateEXT);
    }
    
    static native void nglBlendEquationSeparateEXT(final int p0, final int p1, final long p2);
}
