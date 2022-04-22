package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ARBMultisample
{
    public static final int GL_MULTISAMPLE_ARB = 32925;
    public static final int GL_SAMPLE_ALPHA_TO_COVERAGE_ARB = 32926;
    public static final int GL_SAMPLE_ALPHA_TO_ONE_ARB = 32927;
    public static final int GL_SAMPLE_COVERAGE_ARB = 32928;
    public static final int GL_SAMPLE_BUFFERS_ARB = 32936;
    public static final int GL_SAMPLES_ARB = 32937;
    public static final int GL_SAMPLE_COVERAGE_VALUE_ARB = 32938;
    public static final int GL_SAMPLE_COVERAGE_INVERT_ARB = 32939;
    public static final int GL_MULTISAMPLE_BIT_ARB = 536870912;
    
    private ARBMultisample() {
    }
    
    public static void glSampleCoverageARB(final float n, final boolean b) {
        final long glSampleCoverageARB = GLContext.getCapabilities().glSampleCoverageARB;
        BufferChecks.checkFunctionAddress(glSampleCoverageARB);
        nglSampleCoverageARB(n, b, glSampleCoverageARB);
    }
    
    static native void nglSampleCoverageARB(final float p0, final boolean p1, final long p2);
}
