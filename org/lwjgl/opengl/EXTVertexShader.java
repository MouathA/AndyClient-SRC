package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class EXTVertexShader
{
    public static final int GL_VERTEX_SHADER_EXT = 34688;
    public static final int GL_VERTEX_SHADER_BINDING_EXT = 34689;
    public static final int GL_OP_INDEX_EXT = 34690;
    public static final int GL_OP_NEGATE_EXT = 34691;
    public static final int GL_OP_DOT3_EXT = 34692;
    public static final int GL_OP_DOT4_EXT = 34693;
    public static final int GL_OP_MUL_EXT = 34694;
    public static final int GL_OP_ADD_EXT = 34695;
    public static final int GL_OP_MADD_EXT = 34696;
    public static final int GL_OP_FRAC_EXT = 34697;
    public static final int GL_OP_MAX_EXT = 34698;
    public static final int GL_OP_MIN_EXT = 34699;
    public static final int GL_OP_SET_GE_EXT = 34700;
    public static final int GL_OP_SET_LT_EXT = 34701;
    public static final int GL_OP_CLAMP_EXT = 34702;
    public static final int GL_OP_FLOOR_EXT = 34703;
    public static final int GL_OP_ROUND_EXT = 34704;
    public static final int GL_OP_EXP_BASE_2_EXT = 34705;
    public static final int GL_OP_LOG_BASE_2_EXT = 34706;
    public static final int GL_OP_POWER_EXT = 34707;
    public static final int GL_OP_RECIP_EXT = 34708;
    public static final int GL_OP_RECIP_SQRT_EXT = 34709;
    public static final int GL_OP_SUB_EXT = 34710;
    public static final int GL_OP_CROSS_PRODUCT_EXT = 34711;
    public static final int GL_OP_MULTIPLY_MATRIX_EXT = 34712;
    public static final int GL_OP_MOV_EXT = 34713;
    public static final int GL_OUTPUT_VERTEX_EXT = 34714;
    public static final int GL_OUTPUT_COLOR0_EXT = 34715;
    public static final int GL_OUTPUT_COLOR1_EXT = 34716;
    public static final int GL_OUTPUT_TEXTURE_COORD0_EXT = 34717;
    public static final int GL_OUTPUT_TEXTURE_COORD1_EXT = 34718;
    public static final int GL_OUTPUT_TEXTURE_COORD2_EXT = 34719;
    public static final int GL_OUTPUT_TEXTURE_COORD3_EXT = 34720;
    public static final int GL_OUTPUT_TEXTURE_COORD4_EXT = 34721;
    public static final int GL_OUTPUT_TEXTURE_COORD5_EXT = 34722;
    public static final int GL_OUTPUT_TEXTURE_COORD6_EXT = 34723;
    public static final int GL_OUTPUT_TEXTURE_COORD7_EXT = 34724;
    public static final int GL_OUTPUT_TEXTURE_COORD8_EXT = 34725;
    public static final int GL_OUTPUT_TEXTURE_COORD9_EXT = 34726;
    public static final int GL_OUTPUT_TEXTURE_COORD10_EXT = 34727;
    public static final int GL_OUTPUT_TEXTURE_COORD11_EXT = 34728;
    public static final int GL_OUTPUT_TEXTURE_COORD12_EXT = 34729;
    public static final int GL_OUTPUT_TEXTURE_COORD13_EXT = 34730;
    public static final int GL_OUTPUT_TEXTURE_COORD14_EXT = 34731;
    public static final int GL_OUTPUT_TEXTURE_COORD15_EXT = 34732;
    public static final int GL_OUTPUT_TEXTURE_COORD16_EXT = 34733;
    public static final int GL_OUTPUT_TEXTURE_COORD17_EXT = 34734;
    public static final int GL_OUTPUT_TEXTURE_COORD18_EXT = 34735;
    public static final int GL_OUTPUT_TEXTURE_COORD19_EXT = 34736;
    public static final int GL_OUTPUT_TEXTURE_COORD20_EXT = 34737;
    public static final int GL_OUTPUT_TEXTURE_COORD21_EXT = 34738;
    public static final int GL_OUTPUT_TEXTURE_COORD22_EXT = 34739;
    public static final int GL_OUTPUT_TEXTURE_COORD23_EXT = 34740;
    public static final int GL_OUTPUT_TEXTURE_COORD24_EXT = 34741;
    public static final int GL_OUTPUT_TEXTURE_COORD25_EXT = 34742;
    public static final int GL_OUTPUT_TEXTURE_COORD26_EXT = 34743;
    public static final int GL_OUTPUT_TEXTURE_COORD27_EXT = 34744;
    public static final int GL_OUTPUT_TEXTURE_COORD28_EXT = 34745;
    public static final int GL_OUTPUT_TEXTURE_COORD29_EXT = 34746;
    public static final int GL_OUTPUT_TEXTURE_COORD30_EXT = 34747;
    public static final int GL_OUTPUT_TEXTURE_COORD31_EXT = 34748;
    public static final int GL_OUTPUT_FOG_EXT = 34749;
    public static final int GL_SCALAR_EXT = 34750;
    public static final int GL_VECTOR_EXT = 34751;
    public static final int GL_MATRIX_EXT = 34752;
    public static final int GL_VARIANT_EXT = 34753;
    public static final int GL_INVARIANT_EXT = 34754;
    public static final int GL_LOCAL_CONSTANT_EXT = 34755;
    public static final int GL_LOCAL_EXT = 34756;
    public static final int GL_MAX_VERTEX_SHADER_INSTRUCTIONS_EXT = 34757;
    public static final int GL_MAX_VERTEX_SHADER_VARIANTS_EXT = 34758;
    public static final int GL_MAX_VERTEX_SHADER_INVARIANTS_EXT = 34759;
    public static final int GL_MAX_VERTEX_SHADER_LOCAL_CONSTANTS_EXT = 34760;
    public static final int GL_MAX_VERTEX_SHADER_LOCALS_EXT = 34761;
    public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_INSTRUCTIONS_EXT = 34762;
    public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_VARIANTS_EXT = 34763;
    public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_INVARIANTS_EXT = 34764;
    public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_LOCAL_CONSTANTS_EXT = 34765;
    public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_LOCALS_EXT = 34766;
    public static final int GL_VERTEX_SHADER_INSTRUCTIONS_EXT = 34767;
    public static final int GL_VERTEX_SHADER_VARIANTS_EXT = 34768;
    public static final int GL_VERTEX_SHADER_INVARIANTS_EXT = 34769;
    public static final int GL_VERTEX_SHADER_LOCAL_CONSTANTS_EXT = 34770;
    public static final int GL_VERTEX_SHADER_LOCALS_EXT = 34771;
    public static final int GL_VERTEX_SHADER_OPTIMIZED_EXT = 34772;
    public static final int GL_X_EXT = 34773;
    public static final int GL_Y_EXT = 34774;
    public static final int GL_Z_EXT = 34775;
    public static final int GL_W_EXT = 34776;
    public static final int GL_NEGATIVE_X_EXT = 34777;
    public static final int GL_NEGATIVE_Y_EXT = 34778;
    public static final int GL_NEGATIVE_Z_EXT = 34779;
    public static final int GL_NEGATIVE_W_EXT = 34780;
    public static final int GL_ZERO_EXT = 34781;
    public static final int GL_ONE_EXT = 34782;
    public static final int GL_NEGATIVE_ONE_EXT = 34783;
    public static final int GL_NORMALIZED_RANGE_EXT = 34784;
    public static final int GL_FULL_RANGE_EXT = 34785;
    public static final int GL_CURRENT_VERTEX_EXT = 34786;
    public static final int GL_MVP_MATRIX_EXT = 34787;
    public static final int GL_VARIANT_VALUE_EXT = 34788;
    public static final int GL_VARIANT_DATATYPE_EXT = 34789;
    public static final int GL_VARIANT_ARRAY_STRIDE_EXT = 34790;
    public static final int GL_VARIANT_ARRAY_TYPE_EXT = 34791;
    public static final int GL_VARIANT_ARRAY_EXT = 34792;
    public static final int GL_VARIANT_ARRAY_POINTER_EXT = 34793;
    public static final int GL_INVARIANT_VALUE_EXT = 34794;
    public static final int GL_INVARIANT_DATATYPE_EXT = 34795;
    public static final int GL_LOCAL_CONSTANT_VALUE_EXT = 34796;
    public static final int GL_LOCAL_CONSTANT_DATATYPE_EXT = 34797;
    
    private EXTVertexShader() {
    }
    
    public static void glBeginVertexShaderEXT() {
        final long glBeginVertexShaderEXT = GLContext.getCapabilities().glBeginVertexShaderEXT;
        BufferChecks.checkFunctionAddress(glBeginVertexShaderEXT);
        nglBeginVertexShaderEXT(glBeginVertexShaderEXT);
    }
    
    static native void nglBeginVertexShaderEXT(final long p0);
    
    public static void glEndVertexShaderEXT() {
        final long glEndVertexShaderEXT = GLContext.getCapabilities().glEndVertexShaderEXT;
        BufferChecks.checkFunctionAddress(glEndVertexShaderEXT);
        nglEndVertexShaderEXT(glEndVertexShaderEXT);
    }
    
    static native void nglEndVertexShaderEXT(final long p0);
    
    public static void glBindVertexShaderEXT(final int n) {
        final long glBindVertexShaderEXT = GLContext.getCapabilities().glBindVertexShaderEXT;
        BufferChecks.checkFunctionAddress(glBindVertexShaderEXT);
        nglBindVertexShaderEXT(n, glBindVertexShaderEXT);
    }
    
    static native void nglBindVertexShaderEXT(final int p0, final long p1);
    
    public static int glGenVertexShadersEXT(final int n) {
        final long glGenVertexShadersEXT = GLContext.getCapabilities().glGenVertexShadersEXT;
        BufferChecks.checkFunctionAddress(glGenVertexShadersEXT);
        return nglGenVertexShadersEXT(n, glGenVertexShadersEXT);
    }
    
    static native int nglGenVertexShadersEXT(final int p0, final long p1);
    
    public static void glDeleteVertexShaderEXT(final int n) {
        final long glDeleteVertexShaderEXT = GLContext.getCapabilities().glDeleteVertexShaderEXT;
        BufferChecks.checkFunctionAddress(glDeleteVertexShaderEXT);
        nglDeleteVertexShaderEXT(n, glDeleteVertexShaderEXT);
    }
    
    static native void nglDeleteVertexShaderEXT(final int p0, final long p1);
    
    public static void glShaderOp1EXT(final int n, final int n2, final int n3) {
        final long glShaderOp1EXT = GLContext.getCapabilities().glShaderOp1EXT;
        BufferChecks.checkFunctionAddress(glShaderOp1EXT);
        nglShaderOp1EXT(n, n2, n3, glShaderOp1EXT);
    }
    
    static native void nglShaderOp1EXT(final int p0, final int p1, final int p2, final long p3);
    
    public static void glShaderOp2EXT(final int n, final int n2, final int n3, final int n4) {
        final long glShaderOp2EXT = GLContext.getCapabilities().glShaderOp2EXT;
        BufferChecks.checkFunctionAddress(glShaderOp2EXT);
        nglShaderOp2EXT(n, n2, n3, n4, glShaderOp2EXT);
    }
    
    static native void nglShaderOp2EXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glShaderOp3EXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glShaderOp3EXT = GLContext.getCapabilities().glShaderOp3EXT;
        BufferChecks.checkFunctionAddress(glShaderOp3EXT);
        nglShaderOp3EXT(n, n2, n3, n4, n5, glShaderOp3EXT);
    }
    
    static native void nglShaderOp3EXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glSwizzleEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glSwizzleEXT = GLContext.getCapabilities().glSwizzleEXT;
        BufferChecks.checkFunctionAddress(glSwizzleEXT);
        nglSwizzleEXT(n, n2, n3, n4, n5, n6, glSwizzleEXT);
    }
    
    static native void nglSwizzleEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glWriteMaskEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glWriteMaskEXT = GLContext.getCapabilities().glWriteMaskEXT;
        BufferChecks.checkFunctionAddress(glWriteMaskEXT);
        nglWriteMaskEXT(n, n2, n3, n4, n5, n6, glWriteMaskEXT);
    }
    
    static native void nglWriteMaskEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glInsertComponentEXT(final int n, final int n2, final int n3) {
        final long glInsertComponentEXT = GLContext.getCapabilities().glInsertComponentEXT;
        BufferChecks.checkFunctionAddress(glInsertComponentEXT);
        nglInsertComponentEXT(n, n2, n3, glInsertComponentEXT);
    }
    
    static native void nglInsertComponentEXT(final int p0, final int p1, final int p2, final long p3);
    
    public static void glExtractComponentEXT(final int n, final int n2, final int n3) {
        final long glExtractComponentEXT = GLContext.getCapabilities().glExtractComponentEXT;
        BufferChecks.checkFunctionAddress(glExtractComponentEXT);
        nglExtractComponentEXT(n, n2, n3, glExtractComponentEXT);
    }
    
    static native void nglExtractComponentEXT(final int p0, final int p1, final int p2, final long p3);
    
    public static int glGenSymbolsEXT(final int n, final int n2, final int n3, final int n4) {
        final long glGenSymbolsEXT = GLContext.getCapabilities().glGenSymbolsEXT;
        BufferChecks.checkFunctionAddress(glGenSymbolsEXT);
        return nglGenSymbolsEXT(n, n2, n3, n4, glGenSymbolsEXT);
    }
    
    static native int nglGenSymbolsEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glSetInvariantEXT(final int n, final DoubleBuffer doubleBuffer) {
        final long glSetInvariantEXT = GLContext.getCapabilities().glSetInvariantEXT;
        BufferChecks.checkFunctionAddress(glSetInvariantEXT);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglSetInvariantEXT(n, 5130, MemoryUtil.getAddress(doubleBuffer), glSetInvariantEXT);
    }
    
    public static void glSetInvariantEXT(final int n, final FloatBuffer floatBuffer) {
        final long glSetInvariantEXT = GLContext.getCapabilities().glSetInvariantEXT;
        BufferChecks.checkFunctionAddress(glSetInvariantEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglSetInvariantEXT(n, 5126, MemoryUtil.getAddress(floatBuffer), glSetInvariantEXT);
    }
    
    public static void glSetInvariantEXT(final int n, final boolean b, final ByteBuffer byteBuffer) {
        final long glSetInvariantEXT = GLContext.getCapabilities().glSetInvariantEXT;
        BufferChecks.checkFunctionAddress(glSetInvariantEXT);
        BufferChecks.checkBuffer(byteBuffer, 4);
        nglSetInvariantEXT(n, b ? 5121 : 5120, MemoryUtil.getAddress(byteBuffer), glSetInvariantEXT);
    }
    
    public static void glSetInvariantEXT(final int n, final boolean b, final IntBuffer intBuffer) {
        final long glSetInvariantEXT = GLContext.getCapabilities().glSetInvariantEXT;
        BufferChecks.checkFunctionAddress(glSetInvariantEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglSetInvariantEXT(n, b ? 5125 : 5124, MemoryUtil.getAddress(intBuffer), glSetInvariantEXT);
    }
    
    public static void glSetInvariantEXT(final int n, final boolean b, final ShortBuffer shortBuffer) {
        final long glSetInvariantEXT = GLContext.getCapabilities().glSetInvariantEXT;
        BufferChecks.checkFunctionAddress(glSetInvariantEXT);
        BufferChecks.checkBuffer(shortBuffer, 4);
        nglSetInvariantEXT(n, b ? 5123 : 5122, MemoryUtil.getAddress(shortBuffer), glSetInvariantEXT);
    }
    
    static native void nglSetInvariantEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glSetLocalConstantEXT(final int n, final DoubleBuffer doubleBuffer) {
        final long glSetLocalConstantEXT = GLContext.getCapabilities().glSetLocalConstantEXT;
        BufferChecks.checkFunctionAddress(glSetLocalConstantEXT);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglSetLocalConstantEXT(n, 5130, MemoryUtil.getAddress(doubleBuffer), glSetLocalConstantEXT);
    }
    
    public static void glSetLocalConstantEXT(final int n, final FloatBuffer floatBuffer) {
        final long glSetLocalConstantEXT = GLContext.getCapabilities().glSetLocalConstantEXT;
        BufferChecks.checkFunctionAddress(glSetLocalConstantEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglSetLocalConstantEXT(n, 5126, MemoryUtil.getAddress(floatBuffer), glSetLocalConstantEXT);
    }
    
    public static void glSetLocalConstantEXT(final int n, final boolean b, final ByteBuffer byteBuffer) {
        final long glSetLocalConstantEXT = GLContext.getCapabilities().glSetLocalConstantEXT;
        BufferChecks.checkFunctionAddress(glSetLocalConstantEXT);
        BufferChecks.checkBuffer(byteBuffer, 4);
        nglSetLocalConstantEXT(n, b ? 5121 : 5120, MemoryUtil.getAddress(byteBuffer), glSetLocalConstantEXT);
    }
    
    public static void glSetLocalConstantEXT(final int n, final boolean b, final IntBuffer intBuffer) {
        final long glSetLocalConstantEXT = GLContext.getCapabilities().glSetLocalConstantEXT;
        BufferChecks.checkFunctionAddress(glSetLocalConstantEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglSetLocalConstantEXT(n, b ? 5125 : 5124, MemoryUtil.getAddress(intBuffer), glSetLocalConstantEXT);
    }
    
    public static void glSetLocalConstantEXT(final int n, final boolean b, final ShortBuffer shortBuffer) {
        final long glSetLocalConstantEXT = GLContext.getCapabilities().glSetLocalConstantEXT;
        BufferChecks.checkFunctionAddress(glSetLocalConstantEXT);
        BufferChecks.checkBuffer(shortBuffer, 4);
        nglSetLocalConstantEXT(n, b ? 5123 : 5122, MemoryUtil.getAddress(shortBuffer), glSetLocalConstantEXT);
    }
    
    static native void nglSetLocalConstantEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVariantEXT(final int n, final ByteBuffer byteBuffer) {
        final long glVariantbvEXT = GLContext.getCapabilities().glVariantbvEXT;
        BufferChecks.checkFunctionAddress(glVariantbvEXT);
        BufferChecks.checkBuffer(byteBuffer, 4);
        nglVariantbvEXT(n, MemoryUtil.getAddress(byteBuffer), glVariantbvEXT);
    }
    
    static native void nglVariantbvEXT(final int p0, final long p1, final long p2);
    
    public static void glVariantEXT(final int n, final ShortBuffer shortBuffer) {
        final long glVariantsvEXT = GLContext.getCapabilities().glVariantsvEXT;
        BufferChecks.checkFunctionAddress(glVariantsvEXT);
        BufferChecks.checkBuffer(shortBuffer, 4);
        nglVariantsvEXT(n, MemoryUtil.getAddress(shortBuffer), glVariantsvEXT);
    }
    
    static native void nglVariantsvEXT(final int p0, final long p1, final long p2);
    
    public static void glVariantEXT(final int n, final IntBuffer intBuffer) {
        final long glVariantivEXT = GLContext.getCapabilities().glVariantivEXT;
        BufferChecks.checkFunctionAddress(glVariantivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglVariantivEXT(n, MemoryUtil.getAddress(intBuffer), glVariantivEXT);
    }
    
    static native void nglVariantivEXT(final int p0, final long p1, final long p2);
    
    public static void glVariantEXT(final int n, final FloatBuffer floatBuffer) {
        final long glVariantfvEXT = GLContext.getCapabilities().glVariantfvEXT;
        BufferChecks.checkFunctionAddress(glVariantfvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglVariantfvEXT(n, MemoryUtil.getAddress(floatBuffer), glVariantfvEXT);
    }
    
    static native void nglVariantfvEXT(final int p0, final long p1, final long p2);
    
    public static void glVariantEXT(final int n, final DoubleBuffer doubleBuffer) {
        final long glVariantdvEXT = GLContext.getCapabilities().glVariantdvEXT;
        BufferChecks.checkFunctionAddress(glVariantdvEXT);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglVariantdvEXT(n, MemoryUtil.getAddress(doubleBuffer), glVariantdvEXT);
    }
    
    static native void nglVariantdvEXT(final int p0, final long p1, final long p2);
    
    public static void glVariantuEXT(final int n, final ByteBuffer byteBuffer) {
        final long glVariantubvEXT = GLContext.getCapabilities().glVariantubvEXT;
        BufferChecks.checkFunctionAddress(glVariantubvEXT);
        BufferChecks.checkBuffer(byteBuffer, 4);
        nglVariantubvEXT(n, MemoryUtil.getAddress(byteBuffer), glVariantubvEXT);
    }
    
    static native void nglVariantubvEXT(final int p0, final long p1, final long p2);
    
    public static void glVariantuEXT(final int n, final ShortBuffer shortBuffer) {
        final long glVariantusvEXT = GLContext.getCapabilities().glVariantusvEXT;
        BufferChecks.checkFunctionAddress(glVariantusvEXT);
        BufferChecks.checkBuffer(shortBuffer, 4);
        nglVariantusvEXT(n, MemoryUtil.getAddress(shortBuffer), glVariantusvEXT);
    }
    
    static native void nglVariantusvEXT(final int p0, final long p1, final long p2);
    
    public static void glVariantuEXT(final int n, final IntBuffer intBuffer) {
        final long glVariantuivEXT = GLContext.getCapabilities().glVariantuivEXT;
        BufferChecks.checkFunctionAddress(glVariantuivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglVariantuivEXT(n, MemoryUtil.getAddress(intBuffer), glVariantuivEXT);
    }
    
    static native void nglVariantuivEXT(final int p0, final long p1, final long p2);
    
    public static void glVariantPointerEXT(final int n, final int n2, final DoubleBuffer ext_vertex_shader_glVariantPointerEXT_pAddr) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVariantPointerEXT = capabilities.glVariantPointerEXT;
        BufferChecks.checkFunctionAddress(glVariantPointerEXT);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(ext_vertex_shader_glVariantPointerEXT_pAddr);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).EXT_vertex_shader_glVariantPointerEXT_pAddr = ext_vertex_shader_glVariantPointerEXT_pAddr;
        }
        nglVariantPointerEXT(n, 5130, n2, MemoryUtil.getAddress(ext_vertex_shader_glVariantPointerEXT_pAddr), glVariantPointerEXT);
    }
    
    public static void glVariantPointerEXT(final int n, final int n2, final FloatBuffer ext_vertex_shader_glVariantPointerEXT_pAddr) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVariantPointerEXT = capabilities.glVariantPointerEXT;
        BufferChecks.checkFunctionAddress(glVariantPointerEXT);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(ext_vertex_shader_glVariantPointerEXT_pAddr);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).EXT_vertex_shader_glVariantPointerEXT_pAddr = ext_vertex_shader_glVariantPointerEXT_pAddr;
        }
        nglVariantPointerEXT(n, 5126, n2, MemoryUtil.getAddress(ext_vertex_shader_glVariantPointerEXT_pAddr), glVariantPointerEXT);
    }
    
    public static void glVariantPointerEXT(final int n, final boolean b, final int n2, final ByteBuffer ext_vertex_shader_glVariantPointerEXT_pAddr) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVariantPointerEXT = capabilities.glVariantPointerEXT;
        BufferChecks.checkFunctionAddress(glVariantPointerEXT);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(ext_vertex_shader_glVariantPointerEXT_pAddr);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).EXT_vertex_shader_glVariantPointerEXT_pAddr = ext_vertex_shader_glVariantPointerEXT_pAddr;
        }
        nglVariantPointerEXT(n, b ? 5121 : 5120, n2, MemoryUtil.getAddress(ext_vertex_shader_glVariantPointerEXT_pAddr), glVariantPointerEXT);
    }
    
    public static void glVariantPointerEXT(final int n, final boolean b, final int n2, final IntBuffer ext_vertex_shader_glVariantPointerEXT_pAddr) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVariantPointerEXT = capabilities.glVariantPointerEXT;
        BufferChecks.checkFunctionAddress(glVariantPointerEXT);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(ext_vertex_shader_glVariantPointerEXT_pAddr);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).EXT_vertex_shader_glVariantPointerEXT_pAddr = ext_vertex_shader_glVariantPointerEXT_pAddr;
        }
        nglVariantPointerEXT(n, b ? 5125 : 5124, n2, MemoryUtil.getAddress(ext_vertex_shader_glVariantPointerEXT_pAddr), glVariantPointerEXT);
    }
    
    public static void glVariantPointerEXT(final int n, final boolean b, final int n2, final ShortBuffer ext_vertex_shader_glVariantPointerEXT_pAddr) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVariantPointerEXT = capabilities.glVariantPointerEXT;
        BufferChecks.checkFunctionAddress(glVariantPointerEXT);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(ext_vertex_shader_glVariantPointerEXT_pAddr);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).EXT_vertex_shader_glVariantPointerEXT_pAddr = ext_vertex_shader_glVariantPointerEXT_pAddr;
        }
        nglVariantPointerEXT(n, b ? 5123 : 5122, n2, MemoryUtil.getAddress(ext_vertex_shader_glVariantPointerEXT_pAddr), glVariantPointerEXT);
    }
    
    static native void nglVariantPointerEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glVariantPointerEXT(final int n, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVariantPointerEXT = capabilities.glVariantPointerEXT;
        BufferChecks.checkFunctionAddress(glVariantPointerEXT);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglVariantPointerEXTBO(n, n2, n3, n4, glVariantPointerEXT);
    }
    
    static native void nglVariantPointerEXTBO(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glEnableVariantClientStateEXT(final int n) {
        final long glEnableVariantClientStateEXT = GLContext.getCapabilities().glEnableVariantClientStateEXT;
        BufferChecks.checkFunctionAddress(glEnableVariantClientStateEXT);
        nglEnableVariantClientStateEXT(n, glEnableVariantClientStateEXT);
    }
    
    static native void nglEnableVariantClientStateEXT(final int p0, final long p1);
    
    public static void glDisableVariantClientStateEXT(final int n) {
        final long glDisableVariantClientStateEXT = GLContext.getCapabilities().glDisableVariantClientStateEXT;
        BufferChecks.checkFunctionAddress(glDisableVariantClientStateEXT);
        nglDisableVariantClientStateEXT(n, glDisableVariantClientStateEXT);
    }
    
    static native void nglDisableVariantClientStateEXT(final int p0, final long p1);
    
    public static int glBindLightParameterEXT(final int n, final int n2) {
        final long glBindLightParameterEXT = GLContext.getCapabilities().glBindLightParameterEXT;
        BufferChecks.checkFunctionAddress(glBindLightParameterEXT);
        return nglBindLightParameterEXT(n, n2, glBindLightParameterEXT);
    }
    
    static native int nglBindLightParameterEXT(final int p0, final int p1, final long p2);
    
    public static int glBindMaterialParameterEXT(final int n, final int n2) {
        final long glBindMaterialParameterEXT = GLContext.getCapabilities().glBindMaterialParameterEXT;
        BufferChecks.checkFunctionAddress(glBindMaterialParameterEXT);
        return nglBindMaterialParameterEXT(n, n2, glBindMaterialParameterEXT);
    }
    
    static native int nglBindMaterialParameterEXT(final int p0, final int p1, final long p2);
    
    public static int glBindTexGenParameterEXT(final int n, final int n2, final int n3) {
        final long glBindTexGenParameterEXT = GLContext.getCapabilities().glBindTexGenParameterEXT;
        BufferChecks.checkFunctionAddress(glBindTexGenParameterEXT);
        return nglBindTexGenParameterEXT(n, n2, n3, glBindTexGenParameterEXT);
    }
    
    static native int nglBindTexGenParameterEXT(final int p0, final int p1, final int p2, final long p3);
    
    public static int glBindTextureUnitParameterEXT(final int n, final int n2) {
        final long glBindTextureUnitParameterEXT = GLContext.getCapabilities().glBindTextureUnitParameterEXT;
        BufferChecks.checkFunctionAddress(glBindTextureUnitParameterEXT);
        return nglBindTextureUnitParameterEXT(n, n2, glBindTextureUnitParameterEXT);
    }
    
    static native int nglBindTextureUnitParameterEXT(final int p0, final int p1, final long p2);
    
    public static int glBindParameterEXT(final int n) {
        final long glBindParameterEXT = GLContext.getCapabilities().glBindParameterEXT;
        BufferChecks.checkFunctionAddress(glBindParameterEXT);
        return nglBindParameterEXT(n, glBindParameterEXT);
    }
    
    static native int nglBindParameterEXT(final int p0, final long p1);
    
    public static boolean glIsVariantEnabledEXT(final int n, final int n2) {
        final long glIsVariantEnabledEXT = GLContext.getCapabilities().glIsVariantEnabledEXT;
        BufferChecks.checkFunctionAddress(glIsVariantEnabledEXT);
        return nglIsVariantEnabledEXT(n, n2, glIsVariantEnabledEXT);
    }
    
    static native boolean nglIsVariantEnabledEXT(final int p0, final int p1, final long p2);
    
    public static void glGetVariantBooleanEXT(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glGetVariantBooleanvEXT = GLContext.getCapabilities().glGetVariantBooleanvEXT;
        BufferChecks.checkFunctionAddress(glGetVariantBooleanvEXT);
        BufferChecks.checkBuffer(byteBuffer, 4);
        nglGetVariantBooleanvEXT(n, n2, MemoryUtil.getAddress(byteBuffer), glGetVariantBooleanvEXT);
    }
    
    static native void nglGetVariantBooleanvEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetVariantIntegerEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetVariantIntegervEXT = GLContext.getCapabilities().glGetVariantIntegervEXT;
        BufferChecks.checkFunctionAddress(glGetVariantIntegervEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetVariantIntegervEXT(n, n2, MemoryUtil.getAddress(intBuffer), glGetVariantIntegervEXT);
    }
    
    static native void nglGetVariantIntegervEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetVariantFloatEXT(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetVariantFloatvEXT = GLContext.getCapabilities().glGetVariantFloatvEXT;
        BufferChecks.checkFunctionAddress(glGetVariantFloatvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetVariantFloatvEXT(n, n2, MemoryUtil.getAddress(floatBuffer), glGetVariantFloatvEXT);
    }
    
    static native void nglGetVariantFloatvEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static ByteBuffer glGetVariantPointerEXT(final int n, final int n2, final long n3) {
        final long glGetVariantPointervEXT = GLContext.getCapabilities().glGetVariantPointervEXT;
        BufferChecks.checkFunctionAddress(glGetVariantPointervEXT);
        final ByteBuffer nglGetVariantPointervEXT = nglGetVariantPointervEXT(n, n2, n3, glGetVariantPointervEXT);
        return (LWJGLUtil.CHECKS && nglGetVariantPointervEXT == null) ? null : nglGetVariantPointervEXT.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglGetVariantPointervEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetInvariantBooleanEXT(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glGetInvariantBooleanvEXT = GLContext.getCapabilities().glGetInvariantBooleanvEXT;
        BufferChecks.checkFunctionAddress(glGetInvariantBooleanvEXT);
        BufferChecks.checkBuffer(byteBuffer, 4);
        nglGetInvariantBooleanvEXT(n, n2, MemoryUtil.getAddress(byteBuffer), glGetInvariantBooleanvEXT);
    }
    
    static native void nglGetInvariantBooleanvEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetInvariantIntegerEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetInvariantIntegervEXT = GLContext.getCapabilities().glGetInvariantIntegervEXT;
        BufferChecks.checkFunctionAddress(glGetInvariantIntegervEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetInvariantIntegervEXT(n, n2, MemoryUtil.getAddress(intBuffer), glGetInvariantIntegervEXT);
    }
    
    static native void nglGetInvariantIntegervEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetInvariantFloatEXT(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetInvariantFloatvEXT = GLContext.getCapabilities().glGetInvariantFloatvEXT;
        BufferChecks.checkFunctionAddress(glGetInvariantFloatvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetInvariantFloatvEXT(n, n2, MemoryUtil.getAddress(floatBuffer), glGetInvariantFloatvEXT);
    }
    
    static native void nglGetInvariantFloatvEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetLocalConstantBooleanEXT(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glGetLocalConstantBooleanvEXT = GLContext.getCapabilities().glGetLocalConstantBooleanvEXT;
        BufferChecks.checkFunctionAddress(glGetLocalConstantBooleanvEXT);
        BufferChecks.checkBuffer(byteBuffer, 4);
        nglGetLocalConstantBooleanvEXT(n, n2, MemoryUtil.getAddress(byteBuffer), glGetLocalConstantBooleanvEXT);
    }
    
    static native void nglGetLocalConstantBooleanvEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetLocalConstantIntegerEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetLocalConstantIntegervEXT = GLContext.getCapabilities().glGetLocalConstantIntegervEXT;
        BufferChecks.checkFunctionAddress(glGetLocalConstantIntegervEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetLocalConstantIntegervEXT(n, n2, MemoryUtil.getAddress(intBuffer), glGetLocalConstantIntegervEXT);
    }
    
    static native void nglGetLocalConstantIntegervEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetLocalConstantFloatEXT(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetLocalConstantFloatvEXT = GLContext.getCapabilities().glGetLocalConstantFloatvEXT;
        BufferChecks.checkFunctionAddress(glGetLocalConstantFloatvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetLocalConstantFloatvEXT(n, n2, MemoryUtil.getAddress(floatBuffer), glGetLocalConstantFloatvEXT);
    }
    
    static native void nglGetLocalConstantFloatvEXT(final int p0, final int p1, final long p2, final long p3);
}
