package org.lwjgl.opengl;

import org.lwjgl.*;

public final class AMDStencilOperationExtended
{
    public static final int GL_SET_AMD = 34634;
    public static final int GL_REPLACE_VALUE_AMD = 34635;
    public static final int GL_STENCIL_OP_VALUE_AMD = 34636;
    public static final int GL_STENCIL_BACK_OP_VALUE_AMD = 34637;
    
    private AMDStencilOperationExtended() {
    }
    
    public static void glStencilOpValueAMD(final int n, final int n2) {
        final long glStencilOpValueAMD = GLContext.getCapabilities().glStencilOpValueAMD;
        BufferChecks.checkFunctionAddress(glStencilOpValueAMD);
        nglStencilOpValueAMD(n, n2, glStencilOpValueAMD);
    }
    
    static native void nglStencilOpValueAMD(final int p0, final int p1, final long p2);
}
