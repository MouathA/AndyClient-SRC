package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class NVEvaluators
{
    public static final int GL_EVAL_2D_NV = 34496;
    public static final int GL_EVAL_TRIANGULAR_2D_NV = 34497;
    public static final int GL_MAP_TESSELLATION_NV = 34498;
    public static final int GL_MAP_ATTRIB_U_ORDER_NV = 34499;
    public static final int GL_MAP_ATTRIB_V_ORDER_NV = 34500;
    public static final int GL_EVAL_FRACTIONAL_TESSELLATION_NV = 34501;
    public static final int GL_EVAL_VERTEX_ATTRIB0_NV = 34502;
    public static final int GL_EVAL_VERTEX_ATTRIB1_NV = 34503;
    public static final int GL_EVAL_VERTEX_ATTRIB2_NV = 34504;
    public static final int GL_EVAL_VERTEX_ATTRIB3_NV = 34505;
    public static final int GL_EVAL_VERTEX_ATTRIB4_NV = 34506;
    public static final int GL_EVAL_VERTEX_ATTRIB5_NV = 34507;
    public static final int GL_EVAL_VERTEX_ATTRIB6_NV = 34508;
    public static final int GL_EVAL_VERTEX_ATTRIB7_NV = 34509;
    public static final int GL_EVAL_VERTEX_ATTRIB8_NV = 34510;
    public static final int GL_EVAL_VERTEX_ATTRIB9_NV = 34511;
    public static final int GL_EVAL_VERTEX_ATTRIB10_NV = 34512;
    public static final int GL_EVAL_VERTEX_ATTRIB11_NV = 34513;
    public static final int GL_EVAL_VERTEX_ATTRIB12_NV = 34514;
    public static final int GL_EVAL_VERTEX_ATTRIB13_NV = 34515;
    public static final int GL_EVAL_VERTEX_ATTRIB14_NV = 34516;
    public static final int GL_EVAL_VERTEX_ATTRIB15_NV = 34517;
    public static final int GL_MAX_MAP_TESSELLATION_NV = 34518;
    public static final int GL_MAX_RATIONAL_EVAL_ORDER_NV = 34519;
    
    private NVEvaluators() {
    }
    
    public static void glGetMapControlPointsNV(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b, final FloatBuffer floatBuffer) {
        final long glGetMapControlPointsNV = GLContext.getCapabilities().glGetMapControlPointsNV;
        BufferChecks.checkFunctionAddress(glGetMapControlPointsNV);
        BufferChecks.checkDirect(floatBuffer);
        nglGetMapControlPointsNV(n, n2, n3, n4, n5, b, MemoryUtil.getAddress(floatBuffer), glGetMapControlPointsNV);
    }
    
    static native void nglGetMapControlPointsNV(final int p0, final int p1, final int p2, final int p3, final int p4, final boolean p5, final long p6, final long p7);
    
    public static void glMapControlPointsNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b, final FloatBuffer floatBuffer) {
        final long glMapControlPointsNV = GLContext.getCapabilities().glMapControlPointsNV;
        BufferChecks.checkFunctionAddress(glMapControlPointsNV);
        BufferChecks.checkDirect(floatBuffer);
        nglMapControlPointsNV(n, n2, n3, n4, n5, n6, n7, b, MemoryUtil.getAddress(floatBuffer), glMapControlPointsNV);
    }
    
    static native void nglMapControlPointsNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final boolean p7, final long p8, final long p9);
    
    public static void glMapParameterNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glMapParameterfvNV = GLContext.getCapabilities().glMapParameterfvNV;
        BufferChecks.checkFunctionAddress(glMapParameterfvNV);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglMapParameterfvNV(n, n2, MemoryUtil.getAddress(floatBuffer), glMapParameterfvNV);
    }
    
    static native void nglMapParameterfvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glMapParameterNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glMapParameterivNV = GLContext.getCapabilities().glMapParameterivNV;
        BufferChecks.checkFunctionAddress(glMapParameterivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglMapParameterivNV(n, n2, MemoryUtil.getAddress(intBuffer), glMapParameterivNV);
    }
    
    static native void nglMapParameterivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetMapParameterNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetMapParameterfvNV = GLContext.getCapabilities().glGetMapParameterfvNV;
        BufferChecks.checkFunctionAddress(glGetMapParameterfvNV);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetMapParameterfvNV(n, n2, MemoryUtil.getAddress(floatBuffer), glGetMapParameterfvNV);
    }
    
    static native void nglGetMapParameterfvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetMapParameterNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetMapParameterivNV = GLContext.getCapabilities().glGetMapParameterivNV;
        BufferChecks.checkFunctionAddress(glGetMapParameterivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetMapParameterivNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetMapParameterivNV);
    }
    
    static native void nglGetMapParameterivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetMapAttribParameterNV(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glGetMapAttribParameterfvNV = GLContext.getCapabilities().glGetMapAttribParameterfvNV;
        BufferChecks.checkFunctionAddress(glGetMapAttribParameterfvNV);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetMapAttribParameterfvNV(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glGetMapAttribParameterfvNV);
    }
    
    static native void nglGetMapAttribParameterfvNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetMapAttribParameterNV(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetMapAttribParameterivNV = GLContext.getCapabilities().glGetMapAttribParameterivNV;
        BufferChecks.checkFunctionAddress(glGetMapAttribParameterivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetMapAttribParameterivNV(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetMapAttribParameterivNV);
    }
    
    static native void nglGetMapAttribParameterivNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glEvalMapsNV(final int n, final int n2) {
        final long glEvalMapsNV = GLContext.getCapabilities().glEvalMapsNV;
        BufferChecks.checkFunctionAddress(glEvalMapsNV);
        nglEvalMapsNV(n, n2, glEvalMapsNV);
    }
    
    static native void nglEvalMapsNV(final int p0, final int p1, final long p2);
}
