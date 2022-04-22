package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class NVRegisterCombiners2
{
    public static final int GL_PER_STAGE_CONSTANTS_NV = 34101;
    
    private NVRegisterCombiners2() {
    }
    
    public static void glCombinerStageParameterNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glCombinerStageParameterfvNV = GLContext.getCapabilities().glCombinerStageParameterfvNV;
        BufferChecks.checkFunctionAddress(glCombinerStageParameterfvNV);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglCombinerStageParameterfvNV(n, n2, MemoryUtil.getAddress(floatBuffer), glCombinerStageParameterfvNV);
    }
    
    static native void nglCombinerStageParameterfvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetCombinerStageParameterNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetCombinerStageParameterfvNV = GLContext.getCapabilities().glGetCombinerStageParameterfvNV;
        BufferChecks.checkFunctionAddress(glGetCombinerStageParameterfvNV);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetCombinerStageParameterfvNV(n, n2, MemoryUtil.getAddress(floatBuffer), glGetCombinerStageParameterfvNV);
    }
    
    static native void nglGetCombinerStageParameterfvNV(final int p0, final int p1, final long p2, final long p3);
}
