package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ARBInstancedArrays
{
    public static final int GL_VERTEX_ATTRIB_ARRAY_DIVISOR_ARB = 35070;
    
    private ARBInstancedArrays() {
    }
    
    public static void glVertexAttribDivisorARB(final int n, final int n2) {
        final long glVertexAttribDivisorARB = GLContext.getCapabilities().glVertexAttribDivisorARB;
        BufferChecks.checkFunctionAddress(glVertexAttribDivisorARB);
        nglVertexAttribDivisorARB(n, n2, glVertexAttribDivisorARB);
    }
    
    static native void nglVertexAttribDivisorARB(final int p0, final int p1, final long p2);
}
