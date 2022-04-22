package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ATIPnTriangles
{
    public static final int GL_PN_TRIANGLES_ATI = 34800;
    public static final int GL_MAX_PN_TRIANGLES_TESSELATION_LEVEL_ATI = 34801;
    public static final int GL_PN_TRIANGLES_POINT_MODE_ATI = 34802;
    public static final int GL_PN_TRIANGLES_NORMAL_MODE_ATI = 34803;
    public static final int GL_PN_TRIANGLES_TESSELATION_LEVEL_ATI = 34804;
    public static final int GL_PN_TRIANGLES_POINT_MODE_LINEAR_ATI = 34805;
    public static final int GL_PN_TRIANGLES_POINT_MODE_CUBIC_ATI = 34806;
    public static final int GL_PN_TRIANGLES_NORMAL_MODE_LINEAR_ATI = 34807;
    public static final int GL_PN_TRIANGLES_NORMAL_MODE_QUADRATIC_ATI = 34808;
    
    private ATIPnTriangles() {
    }
    
    public static void glPNTrianglesfATI(final int n, final float n2) {
        final long glPNTrianglesfATI = GLContext.getCapabilities().glPNTrianglesfATI;
        BufferChecks.checkFunctionAddress(glPNTrianglesfATI);
        nglPNTrianglesfATI(n, n2, glPNTrianglesfATI);
    }
    
    static native void nglPNTrianglesfATI(final int p0, final float p1, final long p2);
    
    public static void glPNTrianglesiATI(final int n, final int n2) {
        final long glPNTrianglesiATI = GLContext.getCapabilities().glPNTrianglesiATI;
        BufferChecks.checkFunctionAddress(glPNTrianglesiATI);
        nglPNTrianglesiATI(n, n2, glPNTrianglesiATI);
    }
    
    static native void nglPNTrianglesiATI(final int p0, final int p1, final long p2);
}
