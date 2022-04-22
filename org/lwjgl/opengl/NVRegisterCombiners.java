package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class NVRegisterCombiners
{
    public static final int GL_REGISTER_COMBINERS_NV = 34082;
    public static final int GL_COMBINER0_NV = 34128;
    public static final int GL_COMBINER1_NV = 34129;
    public static final int GL_COMBINER2_NV = 34130;
    public static final int GL_COMBINER3_NV = 34131;
    public static final int GL_COMBINER4_NV = 34132;
    public static final int GL_COMBINER5_NV = 34133;
    public static final int GL_COMBINER6_NV = 34134;
    public static final int GL_COMBINER7_NV = 34135;
    public static final int GL_VARIABLE_A_NV = 34083;
    public static final int GL_VARIABLE_B_NV = 34084;
    public static final int GL_VARIABLE_C_NV = 34085;
    public static final int GL_VARIABLE_D_NV = 34086;
    public static final int GL_VARIABLE_E_NV = 34087;
    public static final int GL_VARIABLE_F_NV = 34088;
    public static final int GL_VARIABLE_G_NV = 34089;
    public static final int GL_CONSTANT_COLOR0_NV = 34090;
    public static final int GL_CONSTANT_COLOR1_NV = 34091;
    public static final int GL_PRIMARY_COLOR_NV = 34092;
    public static final int GL_SECONDARY_COLOR_NV = 34093;
    public static final int GL_SPARE0_NV = 34094;
    public static final int GL_SPARE1_NV = 34095;
    public static final int GL_UNSIGNED_IDENTITY_NV = 34102;
    public static final int GL_UNSIGNED_INVERT_NV = 34103;
    public static final int GL_EXPAND_NORMAL_NV = 34104;
    public static final int GL_EXPAND_NEGATE_NV = 34105;
    public static final int GL_HALF_BIAS_NORMAL_NV = 34106;
    public static final int GL_HALF_BIAS_NEGATE_NV = 34107;
    public static final int GL_SIGNED_IDENTITY_NV = 34108;
    public static final int GL_SIGNED_NEGATE_NV = 34109;
    public static final int GL_E_TIMES_F_NV = 34097;
    public static final int GL_SPARE0_PLUS_SECONDARY_COLOR_NV = 34098;
    public static final int GL_SCALE_BY_TWO_NV = 34110;
    public static final int GL_SCALE_BY_FOUR_NV = 34111;
    public static final int GL_SCALE_BY_ONE_HALF_NV = 34112;
    public static final int GL_BIAS_BY_NEGATIVE_ONE_HALF_NV = 34113;
    public static final int GL_DISCARD_NV = 34096;
    public static final int GL_COMBINER_INPUT_NV = 34114;
    public static final int GL_COMBINER_MAPPING_NV = 34115;
    public static final int GL_COMBINER_COMPONENT_USAGE_NV = 34116;
    public static final int GL_COMBINER_AB_DOT_PRODUCT_NV = 34117;
    public static final int GL_COMBINER_CD_DOT_PRODUCT_NV = 34118;
    public static final int GL_COMBINER_MUX_SUM_NV = 34119;
    public static final int GL_COMBINER_SCALE_NV = 34120;
    public static final int GL_COMBINER_BIAS_NV = 34121;
    public static final int GL_COMBINER_AB_OUTPUT_NV = 34122;
    public static final int GL_COMBINER_CD_OUTPUT_NV = 34123;
    public static final int GL_COMBINER_SUM_OUTPUT_NV = 34124;
    public static final int GL_NUM_GENERAL_COMBINERS_NV = 34126;
    public static final int GL_COLOR_SUM_CLAMP_NV = 34127;
    public static final int GL_MAX_GENERAL_COMBINERS_NV = 34125;
    
    private NVRegisterCombiners() {
    }
    
    public static void glCombinerParameterfNV(final int n, final float n2) {
        final long glCombinerParameterfNV = GLContext.getCapabilities().glCombinerParameterfNV;
        BufferChecks.checkFunctionAddress(glCombinerParameterfNV);
        nglCombinerParameterfNV(n, n2, glCombinerParameterfNV);
    }
    
    static native void nglCombinerParameterfNV(final int p0, final float p1, final long p2);
    
    public static void glCombinerParameterNV(final int n, final FloatBuffer floatBuffer) {
        final long glCombinerParameterfvNV = GLContext.getCapabilities().glCombinerParameterfvNV;
        BufferChecks.checkFunctionAddress(glCombinerParameterfvNV);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglCombinerParameterfvNV(n, MemoryUtil.getAddress(floatBuffer), glCombinerParameterfvNV);
    }
    
    static native void nglCombinerParameterfvNV(final int p0, final long p1, final long p2);
    
    public static void glCombinerParameteriNV(final int n, final int n2) {
        final long glCombinerParameteriNV = GLContext.getCapabilities().glCombinerParameteriNV;
        BufferChecks.checkFunctionAddress(glCombinerParameteriNV);
        nglCombinerParameteriNV(n, n2, glCombinerParameteriNV);
    }
    
    static native void nglCombinerParameteriNV(final int p0, final int p1, final long p2);
    
    public static void glCombinerParameterNV(final int n, final IntBuffer intBuffer) {
        final long glCombinerParameterivNV = GLContext.getCapabilities().glCombinerParameterivNV;
        BufferChecks.checkFunctionAddress(glCombinerParameterivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglCombinerParameterivNV(n, MemoryUtil.getAddress(intBuffer), glCombinerParameterivNV);
    }
    
    static native void nglCombinerParameterivNV(final int p0, final long p1, final long p2);
    
    public static void glCombinerInputNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glCombinerInputNV = GLContext.getCapabilities().glCombinerInputNV;
        BufferChecks.checkFunctionAddress(glCombinerInputNV);
        nglCombinerInputNV(n, n2, n3, n4, n5, n6, glCombinerInputNV);
    }
    
    static native void nglCombinerInputNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glCombinerOutputNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b, final boolean b2, final boolean b3) {
        final long glCombinerOutputNV = GLContext.getCapabilities().glCombinerOutputNV;
        BufferChecks.checkFunctionAddress(glCombinerOutputNV);
        nglCombinerOutputNV(n, n2, n3, n4, n5, n6, n7, b, b2, b3, glCombinerOutputNV);
    }
    
    static native void nglCombinerOutputNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final boolean p7, final boolean p8, final boolean p9, final long p10);
    
    public static void glFinalCombinerInputNV(final int n, final int n2, final int n3, final int n4) {
        final long glFinalCombinerInputNV = GLContext.getCapabilities().glFinalCombinerInputNV;
        BufferChecks.checkFunctionAddress(glFinalCombinerInputNV);
        nglFinalCombinerInputNV(n, n2, n3, n4, glFinalCombinerInputNV);
    }
    
    static native void nglFinalCombinerInputNV(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glGetCombinerInputParameterNV(final int n, final int n2, final int n3, final int n4, final FloatBuffer floatBuffer) {
        final long glGetCombinerInputParameterfvNV = GLContext.getCapabilities().glGetCombinerInputParameterfvNV;
        BufferChecks.checkFunctionAddress(glGetCombinerInputParameterfvNV);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetCombinerInputParameterfvNV(n, n2, n3, n4, MemoryUtil.getAddress(floatBuffer), glGetCombinerInputParameterfvNV);
    }
    
    static native void nglGetCombinerInputParameterfvNV(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static float glGetCombinerInputParameterfNV(final int n, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCombinerInputParameterfvNV = capabilities.glGetCombinerInputParameterfvNV;
        BufferChecks.checkFunctionAddress(glGetCombinerInputParameterfvNV);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetCombinerInputParameterfvNV(n, n2, n3, n4, MemoryUtil.getAddress(bufferFloat), glGetCombinerInputParameterfvNV);
        return bufferFloat.get(0);
    }
    
    public static void glGetCombinerInputParameterNV(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final long glGetCombinerInputParameterivNV = GLContext.getCapabilities().glGetCombinerInputParameterivNV;
        BufferChecks.checkFunctionAddress(glGetCombinerInputParameterivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetCombinerInputParameterivNV(n, n2, n3, n4, MemoryUtil.getAddress(intBuffer), glGetCombinerInputParameterivNV);
    }
    
    static native void nglGetCombinerInputParameterivNV(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static int glGetCombinerInputParameteriNV(final int n, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCombinerInputParameterivNV = capabilities.glGetCombinerInputParameterivNV;
        BufferChecks.checkFunctionAddress(glGetCombinerInputParameterivNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetCombinerInputParameterivNV(n, n2, n3, n4, MemoryUtil.getAddress(bufferInt), glGetCombinerInputParameterivNV);
        return bufferInt.get(0);
    }
    
    public static void glGetCombinerOutputParameterNV(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glGetCombinerOutputParameterfvNV = GLContext.getCapabilities().glGetCombinerOutputParameterfvNV;
        BufferChecks.checkFunctionAddress(glGetCombinerOutputParameterfvNV);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetCombinerOutputParameterfvNV(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glGetCombinerOutputParameterfvNV);
    }
    
    static native void nglGetCombinerOutputParameterfvNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static float glGetCombinerOutputParameterfNV(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCombinerOutputParameterfvNV = capabilities.glGetCombinerOutputParameterfvNV;
        BufferChecks.checkFunctionAddress(glGetCombinerOutputParameterfvNV);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetCombinerOutputParameterfvNV(n, n2, n3, MemoryUtil.getAddress(bufferFloat), glGetCombinerOutputParameterfvNV);
        return bufferFloat.get(0);
    }
    
    public static void glGetCombinerOutputParameterNV(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetCombinerOutputParameterivNV = GLContext.getCapabilities().glGetCombinerOutputParameterivNV;
        BufferChecks.checkFunctionAddress(glGetCombinerOutputParameterivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetCombinerOutputParameterivNV(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetCombinerOutputParameterivNV);
    }
    
    static native void nglGetCombinerOutputParameterivNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetCombinerOutputParameteriNV(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCombinerOutputParameterivNV = capabilities.glGetCombinerOutputParameterivNV;
        BufferChecks.checkFunctionAddress(glGetCombinerOutputParameterivNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetCombinerOutputParameterivNV(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetCombinerOutputParameterivNV);
        return bufferInt.get(0);
    }
    
    public static void glGetFinalCombinerInputParameterNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetFinalCombinerInputParameterfvNV = GLContext.getCapabilities().glGetFinalCombinerInputParameterfvNV;
        BufferChecks.checkFunctionAddress(glGetFinalCombinerInputParameterfvNV);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetFinalCombinerInputParameterfvNV(n, n2, MemoryUtil.getAddress(floatBuffer), glGetFinalCombinerInputParameterfvNV);
    }
    
    static native void nglGetFinalCombinerInputParameterfvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static float glGetFinalCombinerInputParameterfNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetFinalCombinerInputParameterfvNV = capabilities.glGetFinalCombinerInputParameterfvNV;
        BufferChecks.checkFunctionAddress(glGetFinalCombinerInputParameterfvNV);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetFinalCombinerInputParameterfvNV(n, n2, MemoryUtil.getAddress(bufferFloat), glGetFinalCombinerInputParameterfvNV);
        return bufferFloat.get(0);
    }
    
    public static void glGetFinalCombinerInputParameterNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetFinalCombinerInputParameterivNV = GLContext.getCapabilities().glGetFinalCombinerInputParameterivNV;
        BufferChecks.checkFunctionAddress(glGetFinalCombinerInputParameterivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetFinalCombinerInputParameterivNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetFinalCombinerInputParameterivNV);
    }
    
    static native void nglGetFinalCombinerInputParameterivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetFinalCombinerInputParameteriNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetFinalCombinerInputParameterivNV = capabilities.glGetFinalCombinerInputParameterivNV;
        BufferChecks.checkFunctionAddress(glGetFinalCombinerInputParameterivNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetFinalCombinerInputParameterivNV(n, n2, MemoryUtil.getAddress(bufferInt), glGetFinalCombinerInputParameterivNV);
        return bufferInt.get(0);
    }
}
