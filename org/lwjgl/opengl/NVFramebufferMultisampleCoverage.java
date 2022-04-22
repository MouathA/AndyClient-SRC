package org.lwjgl.opengl;

import org.lwjgl.*;

public final class NVFramebufferMultisampleCoverage
{
    public static final int GL_RENDERBUFFER_COVERAGE_SAMPLES_NV = 36011;
    public static final int GL_RENDERBUFFER_COLOR_SAMPLES_NV = 36368;
    public static final int GL_MAX_MULTISAMPLE_COVERAGE_MODES_NV = 36369;
    public static final int GL_MULTISAMPLE_COVERAGE_MODES_NV = 36370;
    
    private NVFramebufferMultisampleCoverage() {
    }
    
    public static void glRenderbufferStorageMultisampleCoverageNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glRenderbufferStorageMultisampleCoverageNV = GLContext.getCapabilities().glRenderbufferStorageMultisampleCoverageNV;
        BufferChecks.checkFunctionAddress(glRenderbufferStorageMultisampleCoverageNV);
        nglRenderbufferStorageMultisampleCoverageNV(n, n2, n3, n4, n5, n6, glRenderbufferStorageMultisampleCoverageNV);
    }
    
    static native void nglRenderbufferStorageMultisampleCoverageNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
}
