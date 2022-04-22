package org.lwjgl.opengl;

import org.lwjgl.*;

public final class AMDDrawBuffersBlend
{
    private AMDDrawBuffersBlend() {
    }
    
    public static void glBlendFuncIndexedAMD(final int n, final int n2, final int n3) {
        final long glBlendFuncIndexedAMD = GLContext.getCapabilities().glBlendFuncIndexedAMD;
        BufferChecks.checkFunctionAddress(glBlendFuncIndexedAMD);
        nglBlendFuncIndexedAMD(n, n2, n3, glBlendFuncIndexedAMD);
    }
    
    static native void nglBlendFuncIndexedAMD(final int p0, final int p1, final int p2, final long p3);
    
    public static void glBlendFuncSeparateIndexedAMD(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glBlendFuncSeparateIndexedAMD = GLContext.getCapabilities().glBlendFuncSeparateIndexedAMD;
        BufferChecks.checkFunctionAddress(glBlendFuncSeparateIndexedAMD);
        nglBlendFuncSeparateIndexedAMD(n, n2, n3, n4, n5, glBlendFuncSeparateIndexedAMD);
    }
    
    static native void nglBlendFuncSeparateIndexedAMD(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glBlendEquationIndexedAMD(final int n, final int n2) {
        final long glBlendEquationIndexedAMD = GLContext.getCapabilities().glBlendEquationIndexedAMD;
        BufferChecks.checkFunctionAddress(glBlendEquationIndexedAMD);
        nglBlendEquationIndexedAMD(n, n2, glBlendEquationIndexedAMD);
    }
    
    static native void nglBlendEquationIndexedAMD(final int p0, final int p1, final long p2);
    
    public static void glBlendEquationSeparateIndexedAMD(final int n, final int n2, final int n3) {
        final long glBlendEquationSeparateIndexedAMD = GLContext.getCapabilities().glBlendEquationSeparateIndexedAMD;
        BufferChecks.checkFunctionAddress(glBlendEquationSeparateIndexedAMD);
        nglBlendEquationSeparateIndexedAMD(n, n2, n3, glBlendEquationSeparateIndexedAMD);
    }
    
    static native void nglBlendEquationSeparateIndexedAMD(final int p0, final int p1, final int p2, final long p3);
}
