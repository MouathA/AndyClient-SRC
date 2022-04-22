package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class ARBPointParameters
{
    public static final int GL_POINT_SIZE_MIN_ARB = 33062;
    public static final int GL_POINT_SIZE_MAX_ARB = 33063;
    public static final int GL_POINT_FADE_THRESHOLD_SIZE_ARB = 33064;
    public static final int GL_POINT_DISTANCE_ATTENUATION_ARB = 33065;
    
    private ARBPointParameters() {
    }
    
    public static void glPointParameterfARB(final int n, final float n2) {
        final long glPointParameterfARB = GLContext.getCapabilities().glPointParameterfARB;
        BufferChecks.checkFunctionAddress(glPointParameterfARB);
        nglPointParameterfARB(n, n2, glPointParameterfARB);
    }
    
    static native void nglPointParameterfARB(final int p0, final float p1, final long p2);
    
    public static void glPointParameterARB(final int n, final FloatBuffer floatBuffer) {
        final long glPointParameterfvARB = GLContext.getCapabilities().glPointParameterfvARB;
        BufferChecks.checkFunctionAddress(glPointParameterfvARB);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglPointParameterfvARB(n, MemoryUtil.getAddress(floatBuffer), glPointParameterfvARB);
    }
    
    static native void nglPointParameterfvARB(final int p0, final long p1, final long p2);
}
