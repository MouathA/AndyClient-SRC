package org.lwjgl.opengl;

import org.lwjgl.*;

public final class AMDVertexShaderTessellator
{
    public static final int GL_SAMPLER_BUFFER_AMD = 36865;
    public static final int GL_INT_SAMPLER_BUFFER_AMD = 36866;
    public static final int GL_UNSIGNED_INT_SAMPLER_BUFFER_AMD = 36867;
    public static final int GL_DISCRETE_AMD = 36870;
    public static final int GL_CONTINUOUS_AMD = 36871;
    public static final int GL_TESSELLATION_MODE_AMD = 36868;
    public static final int GL_TESSELLATION_FACTOR_AMD = 36869;
    
    private AMDVertexShaderTessellator() {
    }
    
    public static void glTessellationFactorAMD(final float n) {
        final long glTessellationFactorAMD = GLContext.getCapabilities().glTessellationFactorAMD;
        BufferChecks.checkFunctionAddress(glTessellationFactorAMD);
        nglTessellationFactorAMD(n, glTessellationFactorAMD);
    }
    
    static native void nglTessellationFactorAMD(final float p0, final long p1);
    
    public static void glTessellationModeAMD(final int n) {
        final long glTessellationModeAMD = GLContext.getCapabilities().glTessellationModeAMD;
        BufferChecks.checkFunctionAddress(glTessellationModeAMD);
        nglTessellationModeAMD(n, glTessellationModeAMD);
    }
    
    static native void nglTessellationModeAMD(final int p0, final long p1);
}
