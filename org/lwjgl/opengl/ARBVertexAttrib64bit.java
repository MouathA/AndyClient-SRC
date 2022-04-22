package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class ARBVertexAttrib64bit
{
    public static final int GL_DOUBLE_VEC2 = 36860;
    public static final int GL_DOUBLE_VEC3 = 36861;
    public static final int GL_DOUBLE_VEC4 = 36862;
    public static final int GL_DOUBLE_MAT2 = 36678;
    public static final int GL_DOUBLE_MAT3 = 36679;
    public static final int GL_DOUBLE_MAT4 = 36680;
    public static final int GL_DOUBLE_MAT2x3 = 36681;
    public static final int GL_DOUBLE_MAT2x4 = 36682;
    public static final int GL_DOUBLE_MAT3x2 = 36683;
    public static final int GL_DOUBLE_MAT3x4 = 36684;
    public static final int GL_DOUBLE_MAT4x2 = 36685;
    public static final int GL_DOUBLE_MAT4x3 = 36686;
    
    private ARBVertexAttrib64bit() {
    }
    
    public static void glVertexAttribL1d(final int n, final double n2) {
        GL41.glVertexAttribL1d(n, n2);
    }
    
    public static void glVertexAttribL2d(final int n, final double n2, final double n3) {
        GL41.glVertexAttribL2d(n, n2, n3);
    }
    
    public static void glVertexAttribL3d(final int n, final double n2, final double n3, final double n4) {
        GL41.glVertexAttribL3d(n, n2, n3, n4);
    }
    
    public static void glVertexAttribL4d(final int n, final double n2, final double n3, final double n4, final double n5) {
        GL41.glVertexAttribL4d(n, n2, n3, n4, n5);
    }
    
    public static void glVertexAttribL1(final int n, final DoubleBuffer doubleBuffer) {
        GL41.glVertexAttribL1(n, doubleBuffer);
    }
    
    public static void glVertexAttribL2(final int n, final DoubleBuffer doubleBuffer) {
        GL41.glVertexAttribL2(n, doubleBuffer);
    }
    
    public static void glVertexAttribL3(final int n, final DoubleBuffer doubleBuffer) {
        GL41.glVertexAttribL3(n, doubleBuffer);
    }
    
    public static void glVertexAttribL4(final int n, final DoubleBuffer doubleBuffer) {
        GL41.glVertexAttribL4(n, doubleBuffer);
    }
    
    public static void glVertexAttribLPointer(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        GL41.glVertexAttribLPointer(n, n2, n3, doubleBuffer);
    }
    
    public static void glVertexAttribLPointer(final int n, final int n2, final int n3, final long n4) {
        GL41.glVertexAttribLPointer(n, n2, n3, n4);
    }
    
    public static void glGetVertexAttribL(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        GL41.glGetVertexAttribL(n, n2, doubleBuffer);
    }
    
    public static void glVertexArrayVertexAttribLOffsetEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        final long glVertexArrayVertexAttribLOffsetEXT = GLContext.getCapabilities().glVertexArrayVertexAttribLOffsetEXT;
        BufferChecks.checkFunctionAddress(glVertexArrayVertexAttribLOffsetEXT);
        nglVertexArrayVertexAttribLOffsetEXT(n, n2, n3, n4, n5, n6, n7, glVertexArrayVertexAttribLOffsetEXT);
    }
    
    static native void nglVertexArrayVertexAttribLOffsetEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
}
