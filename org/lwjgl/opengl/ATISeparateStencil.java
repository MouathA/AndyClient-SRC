package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ATISeparateStencil
{
    public static final int GL_STENCIL_BACK_FUNC_ATI = 34816;
    public static final int GL_STENCIL_BACK_FAIL_ATI = 34817;
    public static final int GL_STENCIL_BACK_PASS_DEPTH_FAIL_ATI = 34818;
    public static final int GL_STENCIL_BACK_PASS_DEPTH_PASS_ATI = 34819;
    
    private ATISeparateStencil() {
    }
    
    public static void glStencilOpSeparateATI(final int n, final int n2, final int n3, final int n4) {
        final long glStencilOpSeparateATI = GLContext.getCapabilities().glStencilOpSeparateATI;
        BufferChecks.checkFunctionAddress(glStencilOpSeparateATI);
        nglStencilOpSeparateATI(n, n2, n3, n4, glStencilOpSeparateATI);
    }
    
    static native void nglStencilOpSeparateATI(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glStencilFuncSeparateATI(final int n, final int n2, final int n3, final int n4) {
        final long glStencilFuncSeparateATI = GLContext.getCapabilities().glStencilFuncSeparateATI;
        BufferChecks.checkFunctionAddress(glStencilFuncSeparateATI);
        nglStencilFuncSeparateATI(n, n2, n3, n4, glStencilFuncSeparateATI);
    }
    
    static native void nglStencilFuncSeparateATI(final int p0, final int p1, final int p2, final int p3, final long p4);
}
