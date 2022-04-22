package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ARBColorBufferFloat
{
    public static final int GL_RGBA_FLOAT_MODE_ARB = 34848;
    public static final int GL_CLAMP_VERTEX_COLOR_ARB = 35098;
    public static final int GL_CLAMP_FRAGMENT_COLOR_ARB = 35099;
    public static final int GL_CLAMP_READ_COLOR_ARB = 35100;
    public static final int GL_FIXED_ONLY_ARB = 35101;
    public static final int WGL_TYPE_RGBA_FLOAT_ARB = 8608;
    public static final int GLX_RGBA_FLOAT_TYPE = 8377;
    public static final int GLX_RGBA_FLOAT_BIT = 4;
    
    private ARBColorBufferFloat() {
    }
    
    public static void glClampColorARB(final int n, final int n2) {
        final long glClampColorARB = GLContext.getCapabilities().glClampColorARB;
        BufferChecks.checkFunctionAddress(glClampColorARB);
        nglClampColorARB(n, n2, glClampColorARB);
    }
    
    static native void nglClampColorARB(final int p0, final int p1, final long p2);
}
