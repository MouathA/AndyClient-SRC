package org.lwjgl.opengl;

import org.lwjgl.*;

public final class EXTBlendMinmax
{
    public static final int GL_FUNC_ADD_EXT = 32774;
    public static final int GL_MIN_EXT = 32775;
    public static final int GL_MAX_EXT = 32776;
    public static final int GL_BLEND_EQUATION_EXT = 32777;
    
    private EXTBlendMinmax() {
    }
    
    public static void glBlendEquationEXT(final int n) {
        final long glBlendEquationEXT = GLContext.getCapabilities().glBlendEquationEXT;
        BufferChecks.checkFunctionAddress(glBlendEquationEXT);
        nglBlendEquationEXT(n, glBlendEquationEXT);
    }
    
    static native void nglBlendEquationEXT(final int p0, final long p1);
}
