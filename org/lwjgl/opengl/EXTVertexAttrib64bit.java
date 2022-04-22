package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class EXTVertexAttrib64bit
{
    public static final int GL_DOUBLE_VEC2_EXT = 36860;
    public static final int GL_DOUBLE_VEC3_EXT = 36861;
    public static final int GL_DOUBLE_VEC4_EXT = 36862;
    public static final int GL_DOUBLE_MAT2_EXT = 36678;
    public static final int GL_DOUBLE_MAT3_EXT = 36679;
    public static final int GL_DOUBLE_MAT4_EXT = 36680;
    public static final int GL_DOUBLE_MAT2x3_EXT = 36681;
    public static final int GL_DOUBLE_MAT2x4_EXT = 36682;
    public static final int GL_DOUBLE_MAT3x2_EXT = 36683;
    public static final int GL_DOUBLE_MAT3x4_EXT = 36684;
    public static final int GL_DOUBLE_MAT4x2_EXT = 36685;
    public static final int GL_DOUBLE_MAT4x3_EXT = 36686;
    
    private EXTVertexAttrib64bit() {
    }
    
    public static void glVertexAttribL1dEXT(final int n, final double n2) {
        final long glVertexAttribL1dEXT = GLContext.getCapabilities().glVertexAttribL1dEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribL1dEXT);
        nglVertexAttribL1dEXT(n, n2, glVertexAttribL1dEXT);
    }
    
    static native void nglVertexAttribL1dEXT(final int p0, final double p1, final long p2);
    
    public static void glVertexAttribL2dEXT(final int n, final double n2, final double n3) {
        final long glVertexAttribL2dEXT = GLContext.getCapabilities().glVertexAttribL2dEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribL2dEXT);
        nglVertexAttribL2dEXT(n, n2, n3, glVertexAttribL2dEXT);
    }
    
    static native void nglVertexAttribL2dEXT(final int p0, final double p1, final double p2, final long p3);
    
    public static void glVertexAttribL3dEXT(final int n, final double n2, final double n3, final double n4) {
        final long glVertexAttribL3dEXT = GLContext.getCapabilities().glVertexAttribL3dEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribL3dEXT);
        nglVertexAttribL3dEXT(n, n2, n3, n4, glVertexAttribL3dEXT);
    }
    
    static native void nglVertexAttribL3dEXT(final int p0, final double p1, final double p2, final double p3, final long p4);
    
    public static void glVertexAttribL4dEXT(final int n, final double n2, final double n3, final double n4, final double n5) {
        final long glVertexAttribL4dEXT = GLContext.getCapabilities().glVertexAttribL4dEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribL4dEXT);
        nglVertexAttribL4dEXT(n, n2, n3, n4, n5, glVertexAttribL4dEXT);
    }
    
    static native void nglVertexAttribL4dEXT(final int p0, final double p1, final double p2, final double p3, final double p4, final long p5);
    
    public static void glVertexAttribL1EXT(final int n, final DoubleBuffer doubleBuffer) {
        final long glVertexAttribL1dvEXT = GLContext.getCapabilities().glVertexAttribL1dvEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribL1dvEXT);
        BufferChecks.checkBuffer(doubleBuffer, 1);
        nglVertexAttribL1dvEXT(n, MemoryUtil.getAddress(doubleBuffer), glVertexAttribL1dvEXT);
    }
    
    static native void nglVertexAttribL1dvEXT(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribL2EXT(final int n, final DoubleBuffer doubleBuffer) {
        final long glVertexAttribL2dvEXT = GLContext.getCapabilities().glVertexAttribL2dvEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribL2dvEXT);
        BufferChecks.checkBuffer(doubleBuffer, 2);
        nglVertexAttribL2dvEXT(n, MemoryUtil.getAddress(doubleBuffer), glVertexAttribL2dvEXT);
    }
    
    static native void nglVertexAttribL2dvEXT(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribL3EXT(final int n, final DoubleBuffer doubleBuffer) {
        final long glVertexAttribL3dvEXT = GLContext.getCapabilities().glVertexAttribL3dvEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribL3dvEXT);
        BufferChecks.checkBuffer(doubleBuffer, 3);
        nglVertexAttribL3dvEXT(n, MemoryUtil.getAddress(doubleBuffer), glVertexAttribL3dvEXT);
    }
    
    static native void nglVertexAttribL3dvEXT(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribL4EXT(final int n, final DoubleBuffer doubleBuffer) {
        final long glVertexAttribL4dvEXT = GLContext.getCapabilities().glVertexAttribL4dvEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribL4dvEXT);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglVertexAttribL4dvEXT(n, MemoryUtil.getAddress(doubleBuffer), glVertexAttribL4dvEXT);
    }
    
    static native void nglVertexAttribL4dvEXT(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribLPointerEXT(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribLPointerEXT = capabilities.glVertexAttribLPointerEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribLPointerEXT);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = doubleBuffer;
        }
        nglVertexAttribLPointerEXT(n, n2, 5130, n3, MemoryUtil.getAddress(doubleBuffer), glVertexAttribLPointerEXT);
    }
    
    static native void nglVertexAttribLPointerEXT(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glVertexAttribLPointerEXT(final int n, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribLPointerEXT = capabilities.glVertexAttribLPointerEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribLPointerEXT);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglVertexAttribLPointerEXTBO(n, n2, 5130, n3, n4, glVertexAttribLPointerEXT);
    }
    
    static native void nglVertexAttribLPointerEXTBO(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glGetVertexAttribLEXT(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glGetVertexAttribLdvEXT = GLContext.getCapabilities().glGetVertexAttribLdvEXT;
        BufferChecks.checkFunctionAddress(glGetVertexAttribLdvEXT);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglGetVertexAttribLdvEXT(n, n2, MemoryUtil.getAddress(doubleBuffer), glGetVertexAttribLdvEXT);
    }
    
    static native void nglGetVertexAttribLdvEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVertexArrayVertexAttribLOffsetEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        ARBVertexAttrib64bit.glVertexArrayVertexAttribLOffsetEXT(n, n2, n3, n4, n5, n6, n7);
    }
}
