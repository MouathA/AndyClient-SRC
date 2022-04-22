package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class EXTFogCoord
{
    public static final int GL_FOG_COORDINATE_SOURCE_EXT = 33872;
    public static final int GL_FOG_COORDINATE_EXT = 33873;
    public static final int GL_FRAGMENT_DEPTH_EXT = 33874;
    public static final int GL_CURRENT_FOG_COORDINATE_EXT = 33875;
    public static final int GL_FOG_COORDINATE_ARRAY_TYPE_EXT = 33876;
    public static final int GL_FOG_COORDINATE_ARRAY_STRIDE_EXT = 33877;
    public static final int GL_FOG_COORDINATE_ARRAY_POINTER_EXT = 33878;
    public static final int GL_FOG_COORDINATE_ARRAY_EXT = 33879;
    
    private EXTFogCoord() {
    }
    
    public static void glFogCoordfEXT(final float n) {
        final long glFogCoordfEXT = GLContext.getCapabilities().glFogCoordfEXT;
        BufferChecks.checkFunctionAddress(glFogCoordfEXT);
        nglFogCoordfEXT(n, glFogCoordfEXT);
    }
    
    static native void nglFogCoordfEXT(final float p0, final long p1);
    
    public static void glFogCoorddEXT(final double n) {
        final long glFogCoorddEXT = GLContext.getCapabilities().glFogCoorddEXT;
        BufferChecks.checkFunctionAddress(glFogCoorddEXT);
        nglFogCoorddEXT(n, glFogCoorddEXT);
    }
    
    static native void nglFogCoorddEXT(final double p0, final long p1);
    
    public static void glFogCoordPointerEXT(final int n, final DoubleBuffer ext_fog_coord_glFogCoordPointerEXT_data) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glFogCoordPointerEXT = capabilities.glFogCoordPointerEXT;
        BufferChecks.checkFunctionAddress(glFogCoordPointerEXT);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(ext_fog_coord_glFogCoordPointerEXT_data);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).EXT_fog_coord_glFogCoordPointerEXT_data = ext_fog_coord_glFogCoordPointerEXT_data;
        }
        nglFogCoordPointerEXT(5130, n, MemoryUtil.getAddress(ext_fog_coord_glFogCoordPointerEXT_data), glFogCoordPointerEXT);
    }
    
    public static void glFogCoordPointerEXT(final int n, final FloatBuffer ext_fog_coord_glFogCoordPointerEXT_data) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glFogCoordPointerEXT = capabilities.glFogCoordPointerEXT;
        BufferChecks.checkFunctionAddress(glFogCoordPointerEXT);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(ext_fog_coord_glFogCoordPointerEXT_data);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).EXT_fog_coord_glFogCoordPointerEXT_data = ext_fog_coord_glFogCoordPointerEXT_data;
        }
        nglFogCoordPointerEXT(5126, n, MemoryUtil.getAddress(ext_fog_coord_glFogCoordPointerEXT_data), glFogCoordPointerEXT);
    }
    
    static native void nglFogCoordPointerEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glFogCoordPointerEXT(final int n, final int n2, final long n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glFogCoordPointerEXT = capabilities.glFogCoordPointerEXT;
        BufferChecks.checkFunctionAddress(glFogCoordPointerEXT);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglFogCoordPointerEXTBO(n, n2, n3, glFogCoordPointerEXT);
    }
    
    static native void nglFogCoordPointerEXTBO(final int p0, final int p1, final long p2, final long p3);
}
