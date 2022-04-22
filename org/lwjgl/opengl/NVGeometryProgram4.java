package org.lwjgl.opengl;

import org.lwjgl.*;

public final class NVGeometryProgram4
{
    public static final int GL_GEOMETRY_PROGRAM_NV = 35878;
    public static final int GL_MAX_PROGRAM_OUTPUT_VERTICES_NV = 35879;
    public static final int GL_MAX_PROGRAM_TOTAL_OUTPUT_COMPONENTS_NV = 35880;
    
    private NVGeometryProgram4() {
    }
    
    public static void glProgramVertexLimitNV(final int n, final int n2) {
        final long glProgramVertexLimitNV = GLContext.getCapabilities().glProgramVertexLimitNV;
        BufferChecks.checkFunctionAddress(glProgramVertexLimitNV);
        nglProgramVertexLimitNV(n, n2, glProgramVertexLimitNV);
    }
    
    static native void nglProgramVertexLimitNV(final int p0, final int p1, final long p2);
    
    public static void glFramebufferTextureEXT(final int n, final int n2, final int n3, final int n4) {
        EXTGeometryShader4.glFramebufferTextureEXT(n, n2, n3, n4);
    }
    
    public static void glFramebufferTextureLayerEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        EXTGeometryShader4.glFramebufferTextureLayerEXT(n, n2, n3, n4, n5);
    }
    
    public static void glFramebufferTextureFaceEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        EXTGeometryShader4.glFramebufferTextureFaceEXT(n, n2, n3, n4, n5);
    }
}
