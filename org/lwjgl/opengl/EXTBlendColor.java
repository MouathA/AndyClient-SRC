package org.lwjgl.opengl;

import org.lwjgl.*;

public final class EXTBlendColor
{
    public static final int GL_CONSTANT_COLOR_EXT = 32769;
    public static final int GL_ONE_MINUS_CONSTANT_COLOR_EXT = 32770;
    public static final int GL_CONSTANT_ALPHA_EXT = 32771;
    public static final int GL_ONE_MINUS_CONSTANT_ALPHA_EXT = 32772;
    public static final int GL_BLEND_COLOR_EXT = 32773;
    
    private EXTBlendColor() {
    }
    
    public static void glBlendColorEXT(final float n, final float n2, final float n3, final float n4) {
        final long glBlendColorEXT = GLContext.getCapabilities().glBlendColorEXT;
        BufferChecks.checkFunctionAddress(glBlendColorEXT);
        nglBlendColorEXT(n, n2, n3, n4, glBlendColorEXT);
    }
    
    static native void nglBlendColorEXT(final float p0, final float p1, final float p2, final float p3, final long p4);
}
