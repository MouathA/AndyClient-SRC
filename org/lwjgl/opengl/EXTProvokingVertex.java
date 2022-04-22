package org.lwjgl.opengl;

import org.lwjgl.*;

public final class EXTProvokingVertex
{
    public static final int GL_FIRST_VERTEX_CONVENTION_EXT = 36429;
    public static final int GL_LAST_VERTEX_CONVENTION_EXT = 36430;
    public static final int GL_PROVOKING_VERTEX_EXT = 36431;
    public static final int GL_QUADS_FOLLOW_PROVOKING_VERTEX_CONVENTION_EXT = 36428;
    
    private EXTProvokingVertex() {
    }
    
    public static void glProvokingVertexEXT(final int n) {
        final long glProvokingVertexEXT = GLContext.getCapabilities().glProvokingVertexEXT;
        BufferChecks.checkFunctionAddress(glProvokingVertexEXT);
        nglProvokingVertexEXT(n, glProvokingVertexEXT);
    }
    
    static native void nglProvokingVertexEXT(final int p0, final long p1);
}
