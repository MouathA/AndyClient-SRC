package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class ARBTransposeMatrix
{
    public static final int GL_TRANSPOSE_MODELVIEW_MATRIX_ARB = 34019;
    public static final int GL_TRANSPOSE_PROJECTION_MATRIX_ARB = 34020;
    public static final int GL_TRANSPOSE_TEXTURE_MATRIX_ARB = 34021;
    public static final int GL_TRANSPOSE_COLOR_MATRIX_ARB = 34022;
    
    private ARBTransposeMatrix() {
    }
    
    public static void glLoadTransposeMatrixARB(final FloatBuffer floatBuffer) {
        final long glLoadTransposeMatrixfARB = GLContext.getCapabilities().glLoadTransposeMatrixfARB;
        BufferChecks.checkFunctionAddress(glLoadTransposeMatrixfARB);
        BufferChecks.checkBuffer(floatBuffer, 16);
        nglLoadTransposeMatrixfARB(MemoryUtil.getAddress(floatBuffer), glLoadTransposeMatrixfARB);
    }
    
    static native void nglLoadTransposeMatrixfARB(final long p0, final long p1);
    
    public static void glMultTransposeMatrixARB(final FloatBuffer floatBuffer) {
        final long glMultTransposeMatrixfARB = GLContext.getCapabilities().glMultTransposeMatrixfARB;
        BufferChecks.checkFunctionAddress(glMultTransposeMatrixfARB);
        BufferChecks.checkBuffer(floatBuffer, 16);
        nglMultTransposeMatrixfARB(MemoryUtil.getAddress(floatBuffer), glMultTransposeMatrixfARB);
    }
    
    static native void nglMultTransposeMatrixfARB(final long p0, final long p1);
}
