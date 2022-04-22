package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ARBGeometryShader4
{
    public static final int GL_GEOMETRY_SHADER_ARB = 36313;
    public static final int GL_GEOMETRY_VERTICES_OUT_ARB = 36314;
    public static final int GL_GEOMETRY_INPUT_TYPE_ARB = 36315;
    public static final int GL_GEOMETRY_OUTPUT_TYPE_ARB = 36316;
    public static final int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS_ARB = 35881;
    public static final int GL_MAX_GEOMETRY_VARYING_COMPONENTS_ARB = 36317;
    public static final int GL_MAX_VERTEX_VARYING_COMPONENTS_ARB = 36318;
    public static final int GL_MAX_VARYING_COMPONENTS_ARB = 35659;
    public static final int GL_MAX_GEOMETRY_UNIFORM_COMPONENTS_ARB = 36319;
    public static final int GL_MAX_GEOMETRY_OUTPUT_VERTICES_ARB = 36320;
    public static final int GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS_ARB = 36321;
    public static final int GL_LINES_ADJACENCY_ARB = 10;
    public static final int GL_LINE_STRIP_ADJACENCY_ARB = 11;
    public static final int GL_TRIANGLES_ADJACENCY_ARB = 12;
    public static final int GL_TRIANGLE_STRIP_ADJACENCY_ARB = 13;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS_ARB = 36264;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_COUNT_ARB = 36265;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_LAYERED_ARB = 36263;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER_ARB = 36052;
    public static final int GL_PROGRAM_POINT_SIZE_ARB = 34370;
    
    private ARBGeometryShader4() {
    }
    
    public static void glProgramParameteriARB(final int n, final int n2, final int n3) {
        final long glProgramParameteriARB = GLContext.getCapabilities().glProgramParameteriARB;
        BufferChecks.checkFunctionAddress(glProgramParameteriARB);
        nglProgramParameteriARB(n, n2, n3, glProgramParameteriARB);
    }
    
    static native void nglProgramParameteriARB(final int p0, final int p1, final int p2, final long p3);
    
    public static void glFramebufferTextureARB(final int n, final int n2, final int n3, final int n4) {
        final long glFramebufferTextureARB = GLContext.getCapabilities().glFramebufferTextureARB;
        BufferChecks.checkFunctionAddress(glFramebufferTextureARB);
        nglFramebufferTextureARB(n, n2, n3, n4, glFramebufferTextureARB);
    }
    
    static native void nglFramebufferTextureARB(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glFramebufferTextureLayerARB(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glFramebufferTextureLayerARB = GLContext.getCapabilities().glFramebufferTextureLayerARB;
        BufferChecks.checkFunctionAddress(glFramebufferTextureLayerARB);
        nglFramebufferTextureLayerARB(n, n2, n3, n4, n5, glFramebufferTextureLayerARB);
    }
    
    static native void nglFramebufferTextureLayerARB(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glFramebufferTextureFaceARB(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glFramebufferTextureFaceARB = GLContext.getCapabilities().glFramebufferTextureFaceARB;
        BufferChecks.checkFunctionAddress(glFramebufferTextureFaceARB);
        nglFramebufferTextureFaceARB(n, n2, n3, n4, n5, glFramebufferTextureFaceARB);
    }
    
    static native void nglFramebufferTextureFaceARB(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
}
