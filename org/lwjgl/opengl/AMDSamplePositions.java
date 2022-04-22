package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class AMDSamplePositions
{
    public static final int GL_SUBSAMPLE_DISTANCE_AMD = 34879;
    
    private AMDSamplePositions() {
    }
    
    public static void glSetMultisampleAMD(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glSetMultisamplefvAMD = GLContext.getCapabilities().glSetMultisamplefvAMD;
        BufferChecks.checkFunctionAddress(glSetMultisamplefvAMD);
        BufferChecks.checkBuffer(floatBuffer, 2);
        nglSetMultisamplefvAMD(n, n2, MemoryUtil.getAddress(floatBuffer), glSetMultisamplefvAMD);
    }
    
    static native void nglSetMultisamplefvAMD(final int p0, final int p1, final long p2, final long p3);
}
