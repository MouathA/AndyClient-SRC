package org.lwjgl.opengl;

import org.lwjgl.*;

public final class AMDInterleavedElements
{
    public static final int GL_VERTEX_ELEMENT_SWIZZLE_AMD = 37284;
    public static final int GL_VERTEX_ID_SWIZZLE_AMD = 37285;
    
    private AMDInterleavedElements() {
    }
    
    public static void glVertexAttribParameteriAMD(final int n, final int n2, final int n3) {
        final long glVertexAttribParameteriAMD = GLContext.getCapabilities().glVertexAttribParameteriAMD;
        BufferChecks.checkFunctionAddress(glVertexAttribParameteriAMD);
        nglVertexAttribParameteriAMD(n, n2, n3, glVertexAttribParameteriAMD);
    }
    
    static native void nglVertexAttribParameteriAMD(final int p0, final int p1, final int p2, final long p3);
}
