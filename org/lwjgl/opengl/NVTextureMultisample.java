package org.lwjgl.opengl;

import org.lwjgl.*;

public final class NVTextureMultisample
{
    public static final int GL_TEXTURE_COVERAGE_SAMPLES_NV = 36933;
    public static final int GL_TEXTURE_COLOR_SAMPLES_NV = 36934;
    
    private NVTextureMultisample() {
    }
    
    public static void glTexImage2DMultisampleCoverageNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        final long glTexImage2DMultisampleCoverageNV = GLContext.getCapabilities().glTexImage2DMultisampleCoverageNV;
        BufferChecks.checkFunctionAddress(glTexImage2DMultisampleCoverageNV);
        nglTexImage2DMultisampleCoverageNV(n, n2, n3, n4, n5, n6, b, glTexImage2DMultisampleCoverageNV);
    }
    
    static native void nglTexImage2DMultisampleCoverageNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final boolean p6, final long p7);
    
    public static void glTexImage3DMultisampleCoverageNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        final long glTexImage3DMultisampleCoverageNV = GLContext.getCapabilities().glTexImage3DMultisampleCoverageNV;
        BufferChecks.checkFunctionAddress(glTexImage3DMultisampleCoverageNV);
        nglTexImage3DMultisampleCoverageNV(n, n2, n3, n4, n5, n6, n7, b, glTexImage3DMultisampleCoverageNV);
    }
    
    static native void nglTexImage3DMultisampleCoverageNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final boolean p7, final long p8);
    
    public static void glTextureImage2DMultisampleNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        final long glTextureImage2DMultisampleNV = GLContext.getCapabilities().glTextureImage2DMultisampleNV;
        BufferChecks.checkFunctionAddress(glTextureImage2DMultisampleNV);
        nglTextureImage2DMultisampleNV(n, n2, n3, n4, n5, n6, b, glTextureImage2DMultisampleNV);
    }
    
    static native void nglTextureImage2DMultisampleNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final boolean p6, final long p7);
    
    public static void glTextureImage3DMultisampleNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        final long glTextureImage3DMultisampleNV = GLContext.getCapabilities().glTextureImage3DMultisampleNV;
        BufferChecks.checkFunctionAddress(glTextureImage3DMultisampleNV);
        nglTextureImage3DMultisampleNV(n, n2, n3, n4, n5, n6, n7, b, glTextureImage3DMultisampleNV);
    }
    
    static native void nglTextureImage3DMultisampleNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final boolean p7, final long p8);
    
    public static void glTextureImage2DMultisampleCoverageNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        final long glTextureImage2DMultisampleCoverageNV = GLContext.getCapabilities().glTextureImage2DMultisampleCoverageNV;
        BufferChecks.checkFunctionAddress(glTextureImage2DMultisampleCoverageNV);
        nglTextureImage2DMultisampleCoverageNV(n, n2, n3, n4, n5, n6, n7, b, glTextureImage2DMultisampleCoverageNV);
    }
    
    static native void nglTextureImage2DMultisampleCoverageNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final boolean p7, final long p8);
    
    public static void glTextureImage3DMultisampleCoverageNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final boolean b) {
        final long glTextureImage3DMultisampleCoverageNV = GLContext.getCapabilities().glTextureImage3DMultisampleCoverageNV;
        BufferChecks.checkFunctionAddress(glTextureImage3DMultisampleCoverageNV);
        nglTextureImage3DMultisampleCoverageNV(n, n2, n3, n4, n5, n6, n7, n8, b, glTextureImage3DMultisampleCoverageNV);
    }
    
    static native void nglTextureImage3DMultisampleCoverageNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final boolean p8, final long p9);
}
