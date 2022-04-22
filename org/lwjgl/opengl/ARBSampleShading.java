package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ARBSampleShading
{
    public static final int GL_SAMPLE_SHADING_ARB = 35894;
    public static final int GL_MIN_SAMPLE_SHADING_VALUE_ARB = 35895;
    
    private ARBSampleShading() {
    }
    
    public static void glMinSampleShadingARB(final float n) {
        final long glMinSampleShadingARB = GLContext.getCapabilities().glMinSampleShadingARB;
        BufferChecks.checkFunctionAddress(glMinSampleShadingARB);
        nglMinSampleShadingARB(n, glMinSampleShadingARB);
    }
    
    static native void nglMinSampleShadingARB(final float p0, final long p1);
}
