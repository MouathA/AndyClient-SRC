package org.lwjgl.opengl;

import org.lwjgl.*;

public final class EXTBlendFuncSeparate
{
    public static final int GL_BLEND_DST_RGB_EXT = 32968;
    public static final int GL_BLEND_SRC_RGB_EXT = 32969;
    public static final int GL_BLEND_DST_ALPHA_EXT = 32970;
    public static final int GL_BLEND_SRC_ALPHA_EXT = 32971;
    
    private EXTBlendFuncSeparate() {
    }
    
    public static void glBlendFuncSeparateEXT(final int n, final int n2, final int n3, final int n4) {
        final long glBlendFuncSeparateEXT = GLContext.getCapabilities().glBlendFuncSeparateEXT;
        BufferChecks.checkFunctionAddress(glBlendFuncSeparateEXT);
        nglBlendFuncSeparateEXT(n, n2, n3, n4, glBlendFuncSeparateEXT);
    }
    
    static native void nglBlendFuncSeparateEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
}
