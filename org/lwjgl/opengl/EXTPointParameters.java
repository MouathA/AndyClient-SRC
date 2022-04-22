package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class EXTPointParameters
{
    public static final int GL_POINT_SIZE_MIN_EXT = 33062;
    public static final int GL_POINT_SIZE_MAX_EXT = 33063;
    public static final int GL_POINT_FADE_THRESHOLD_SIZE_EXT = 33064;
    public static final int GL_DISTANCE_ATTENUATION_EXT = 33065;
    
    private EXTPointParameters() {
    }
    
    public static void glPointParameterfEXT(final int n, final float n2) {
        final long glPointParameterfEXT = GLContext.getCapabilities().glPointParameterfEXT;
        BufferChecks.checkFunctionAddress(glPointParameterfEXT);
        nglPointParameterfEXT(n, n2, glPointParameterfEXT);
    }
    
    static native void nglPointParameterfEXT(final int p0, final float p1, final long p2);
    
    public static void glPointParameterEXT(final int n, final FloatBuffer floatBuffer) {
        final long glPointParameterfvEXT = GLContext.getCapabilities().glPointParameterfvEXT;
        BufferChecks.checkFunctionAddress(glPointParameterfvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglPointParameterfvEXT(n, MemoryUtil.getAddress(floatBuffer), glPointParameterfvEXT);
    }
    
    static native void nglPointParameterfvEXT(final int p0, final long p1, final long p2);
}
