package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class NVPathRendering
{
    public static final int GL_CLOSE_PATH_NV = 0;
    public static final int GL_MOVE_TO_NV = 2;
    public static final int GL_RELATIVE_MOVE_TO_NV = 3;
    public static final int GL_LINE_TO_NV = 4;
    public static final int GL_RELATIVE_LINE_TO_NV = 5;
    public static final int GL_HORIZONTAL_LINE_TO_NV = 6;
    public static final int GL_RELATIVE_HORIZONTAL_LINE_TO_NV = 7;
    public static final int GL_VERTICAL_LINE_TO_NV = 8;
    public static final int GL_RELATIVE_VERTICAL_LINE_TO_NV = 9;
    public static final int GL_QUADRATIC_CURVE_TO_NV = 10;
    public static final int GL_RELATIVE_QUADRATIC_CURVE_TO_NV = 11;
    public static final int GL_CUBIC_CURVE_TO_NV = 12;
    public static final int GL_RELATIVE_CUBIC_CURVE_TO_NV = 13;
    public static final int GL_SMOOTH_QUADRATIC_CURVE_TO_NV = 14;
    public static final int GL_RELATIVE_SMOOTH_QUADRATIC_CURVE_TO_NV = 15;
    public static final int GL_SMOOTH_CUBIC_CURVE_TO_NV = 16;
    public static final int GL_RELATIVE_SMOOTH_CUBIC_CURVE_TO_NV = 17;
    public static final int GL_SMALL_CCW_ARC_TO_NV = 18;
    public static final int GL_RELATIVE_SMALL_CCW_ARC_TO_NV = 19;
    public static final int GL_SMALL_CW_ARC_TO_NV = 20;
    public static final int GL_RELATIVE_SMALL_CW_ARC_TO_NV = 21;
    public static final int GL_LARGE_CCW_ARC_TO_NV = 22;
    public static final int GL_RELATIVE_LARGE_CCW_ARC_TO_NV = 23;
    public static final int GL_LARGE_CW_ARC_TO_NV = 24;
    public static final int GL_RELATIVE_LARGE_CW_ARC_TO_NV = 25;
    public static final int GL_CIRCULAR_CCW_ARC_TO_NV = 248;
    public static final int GL_CIRCULAR_CW_ARC_TO_NV = 250;
    public static final int GL_CIRCULAR_TANGENT_ARC_TO_NV = 252;
    public static final int GL_ARC_TO_NV = 254;
    public static final int GL_RELATIVE_ARC_TO_NV = 255;
    public static final int GL_PATH_FORMAT_SVG_NV = 36976;
    public static final int GL_PATH_FORMAT_PS_NV = 36977;
    public static final int GL_STANDARD_FONT_NAME_NV = 36978;
    public static final int GL_SYSTEM_FONT_NAME_NV = 36979;
    public static final int GL_FILE_NAME_NV = 36980;
    public static final int GL_SKIP_MISSING_GLYPH_NV = 37033;
    public static final int GL_USE_MISSING_GLYPH_NV = 37034;
    public static final int GL_PATH_STROKE_WIDTH_NV = 36981;
    public static final int GL_PATH_INITIAL_END_CAP_NV = 36983;
    public static final int GL_PATH_TERMINAL_END_CAP_NV = 36984;
    public static final int GL_PATH_JOIN_STYLE_NV = 36985;
    public static final int GL_PATH_MITER_LIMIT_NV = 36986;
    public static final int GL_PATH_INITIAL_DASH_CAP_NV = 36988;
    public static final int GL_PATH_TERMINAL_DASH_CAP_NV = 36989;
    public static final int GL_PATH_DASH_OFFSET_NV = 36990;
    public static final int GL_PATH_CLIENT_LENGTH_NV = 36991;
    public static final int GL_PATH_DASH_OFFSET_RESET_NV = 37044;
    public static final int GL_PATH_FILL_MODE_NV = 36992;
    public static final int GL_PATH_FILL_MASK_NV = 36993;
    public static final int GL_PATH_FILL_COVER_MODE_NV = 36994;
    public static final int GL_PATH_STROKE_COVER_MODE_NV = 36995;
    public static final int GL_PATH_STROKE_MASK_NV = 36996;
    public static final int GL_PATH_END_CAPS_NV = 36982;
    public static final int GL_PATH_DASH_CAPS_NV = 36987;
    public static final int GL_COUNT_UP_NV = 37000;
    public static final int GL_COUNT_DOWN_NV = 37001;
    public static final int GL_PRIMARY_COLOR = 34167;
    public static final int GL_PRIMARY_COLOR_NV = 34092;
    public static final int GL_SECONDARY_COLOR_NV = 34093;
    public static final int GL_PATH_OBJECT_BOUNDING_BOX_NV = 37002;
    public static final int GL_CONVEX_HULL_NV = 37003;
    public static final int GL_BOUNDING_BOX_NV = 37005;
    public static final int GL_TRANSLATE_X_NV = 37006;
    public static final int GL_TRANSLATE_Y_NV = 37007;
    public static final int GL_TRANSLATE_2D_NV = 37008;
    public static final int GL_TRANSLATE_3D_NV = 37009;
    public static final int GL_AFFINE_2D_NV = 37010;
    public static final int GL_AFFINE_3D_NV = 37012;
    public static final int GL_TRANSPOSE_AFFINE_2D_NV = 37014;
    public static final int GL_TRANSPOSE_AFFINE_3D_NV = 37016;
    public static final int GL_UTF8_NV = 37018;
    public static final int GL_UTF16_NV = 37019;
    public static final int GL_BOUNDING_BOX_OF_BOUNDING_BOXES_NV = 37020;
    public static final int GL_PATH_COMMAND_COUNT_NV = 37021;
    public static final int GL_PATH_COORD_COUNT_NV = 37022;
    public static final int GL_PATH_DASH_ARRAY_COUNT_NV = 37023;
    public static final int GL_PATH_COMPUTED_LENGTH_NV = 37024;
    public static final int GL_PATH_FILL_BOUNDING_BOX_NV = 37025;
    public static final int GL_PATH_STROKE_BOUNDING_BOX_NV = 37026;
    public static final int GL_SQUARE_NV = 37027;
    public static final int GL_ROUND_NV = 37028;
    public static final int GL_TRIANGULAR_NV = 37029;
    public static final int GL_BEVEL_NV = 37030;
    public static final int GL_MITER_REVERT_NV = 37031;
    public static final int GL_MITER_TRUNCATE_NV = 37032;
    public static final int GL_MOVE_TO_RESETS_NV = 37045;
    public static final int GL_MOVE_TO_CONTINUES_NV = 37046;
    public static final int GL_BOLD_BIT_NV = 1;
    public static final int GL_ITALIC_BIT_NV = 2;
    public static final int GL_PATH_ERROR_POSITION_NV = 37035;
    public static final int GL_PATH_FOG_GEN_MODE_NV = 37036;
    public static final int GL_PATH_STENCIL_FUNC_NV = 37047;
    public static final int GL_PATH_STENCIL_REF_NV = 37048;
    public static final int GL_PATH_STENCIL_VALUE_MASK_NV = 37049;
    public static final int GL_PATH_STENCIL_DEPTH_OFFSET_FACTOR_NV = 37053;
    public static final int GL_PATH_STENCIL_DEPTH_OFFSET_UNITS_NV = 37054;
    public static final int GL_PATH_COVER_DEPTH_FUNC_NV = 37055;
    public static final int GL_GLYPH_WIDTH_BIT_NV = 1;
    public static final int GL_GLYPH_HEIGHT_BIT_NV = 2;
    public static final int GL_GLYPH_HORIZONTAL_BEARING_X_BIT_NV = 4;
    public static final int GL_GLYPH_HORIZONTAL_BEARING_Y_BIT_NV = 8;
    public static final int GL_GLYPH_HORIZONTAL_BEARING_ADVANCE_BIT_NV = 16;
    public static final int GL_GLYPH_VERTICAL_BEARING_X_BIT_NV = 32;
    public static final int GL_GLYPH_VERTICAL_BEARING_Y_BIT_NV = 64;
    public static final int GL_GLYPH_VERTICAL_BEARING_ADVANCE_BIT_NV = 128;
    public static final int GL_GLYPH_HAS_KERNING_NV = 256;
    public static final int GL_FONT_X_MIN_BOUNDS_NV = 65536;
    public static final int GL_FONT_Y_MIN_BOUNDS_NV = 131072;
    public static final int GL_FONT_X_MAX_BOUNDS_NV = 262144;
    public static final int GL_FONT_Y_MAX_BOUNDS_NV = 524288;
    public static final int GL_FONT_UNITS_PER_EM_NV = 1048576;
    public static final int GL_FONT_ASCENDER_NV = 2097152;
    public static final int GL_FONT_DESCENDER_NV = 4194304;
    public static final int GL_FONT_HEIGHT_NV = 8388608;
    public static final int GL_FONT_MAX_ADVANCE_WIDTH_NV = 16777216;
    public static final int GL_FONT_MAX_ADVANCE_HEIGHT_NV = 33554432;
    public static final int GL_FONT_UNDERLINE_POSITION_NV = 67108864;
    public static final int GL_FONT_UNDERLINE_THICKNESS_NV = 134217728;
    public static final int GL_FONT_HAS_KERNING_NV = 268435456;
    public static final int GL_ACCUM_ADJACENT_PAIRS_NV = 37037;
    public static final int GL_ADJACENT_PAIRS_NV = 37038;
    public static final int GL_FIRST_TO_REST_NV = 37039;
    public static final int GL_PATH_GEN_MODE_NV = 37040;
    public static final int GL_PATH_GEN_COEFF_NV = 37041;
    public static final int GL_PATH_GEN_COLOR_FORMAT_NV = 37042;
    public static final int GL_PATH_GEN_COMPONENTS_NV = 37043;
    
    private NVPathRendering() {
    }
    
    public static void glPathCommandsNV(final int n, final ByteBuffer byteBuffer, final int n2, final ByteBuffer byteBuffer2) {
        final long glPathCommandsNV = GLContext.getCapabilities().glPathCommandsNV;
        BufferChecks.checkFunctionAddress(glPathCommandsNV);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglPathCommandsNV(n, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), byteBuffer2.remaining(), n2, MemoryUtil.getAddress(byteBuffer2), glPathCommandsNV);
    }
    
    static native void nglPathCommandsNV(final int p0, final int p1, final long p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glPathCoordsNV(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glPathCoordsNV = GLContext.getCapabilities().glPathCoordsNV;
        BufferChecks.checkFunctionAddress(glPathCoordsNV);
        BufferChecks.checkDirect(byteBuffer);
        nglPathCoordsNV(n, byteBuffer.remaining(), n2, MemoryUtil.getAddress(byteBuffer), glPathCoordsNV);
    }
    
    static native void nglPathCoordsNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glPathSubCommandsNV(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final int n4, final ByteBuffer byteBuffer2) {
        final long glPathSubCommandsNV = GLContext.getCapabilities().glPathSubCommandsNV;
        BufferChecks.checkFunctionAddress(glPathSubCommandsNV);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglPathSubCommandsNV(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), byteBuffer2.remaining(), n4, MemoryUtil.getAddress(byteBuffer2), glPathSubCommandsNV);
    }
    
    static native void nglPathSubCommandsNV(final int p0, final int p1, final int p2, final int p3, final long p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glPathSubCoordsNV(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final long glPathSubCoordsNV = GLContext.getCapabilities().glPathSubCoordsNV;
        BufferChecks.checkFunctionAddress(glPathSubCoordsNV);
        BufferChecks.checkDirect(byteBuffer);
        nglPathSubCoordsNV(n, n2, byteBuffer.remaining(), n3, MemoryUtil.getAddress(byteBuffer), glPathSubCoordsNV);
    }
    
    static native void nglPathSubCoordsNV(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glPathStringNV(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glPathStringNV = GLContext.getCapabilities().glPathStringNV;
        BufferChecks.checkFunctionAddress(glPathStringNV);
        BufferChecks.checkDirect(byteBuffer);
        nglPathStringNV(n, n2, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glPathStringNV);
    }
    
    static native void nglPathStringNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glPathGlyphsNV(final int n, final int n2, final ByteBuffer byteBuffer, final int n3, final int n4, final ByteBuffer byteBuffer2, final int n5, final int n6, final float n7) {
        final long glPathGlyphsNV = GLContext.getCapabilities().glPathGlyphsNV;
        BufferChecks.checkFunctionAddress(glPathGlyphsNV);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglPathGlyphsNV(n, n2, MemoryUtil.getAddress(byteBuffer), n3, byteBuffer2.remaining() / GLChecks.calculateBytesPerCharCode(n4), n4, MemoryUtil.getAddress(byteBuffer2), n5, n6, n7, glPathGlyphsNV);
    }
    
    static native void nglPathGlyphsNV(final int p0, final int p1, final long p2, final int p3, final int p4, final int p5, final long p6, final int p7, final int p8, final float p9, final long p10);
    
    public static void glPathGlyphRangeNV(final int n, final int n2, final ByteBuffer byteBuffer, final int n3, final int n4, final int n5, final int n6, final int n7, final float n8) {
        final long glPathGlyphRangeNV = GLContext.getCapabilities().glPathGlyphRangeNV;
        BufferChecks.checkFunctionAddress(glPathGlyphRangeNV);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        nglPathGlyphRangeNV(n, n2, MemoryUtil.getAddress(byteBuffer), n3, n4, n5, n6, n7, n8, glPathGlyphRangeNV);
    }
    
    static native void nglPathGlyphRangeNV(final int p0, final int p1, final long p2, final int p3, final int p4, final int p5, final int p6, final int p7, final float p8, final long p9);
    
    public static void glWeightPathsNV(final int n, final IntBuffer intBuffer, final FloatBuffer floatBuffer) {
        final long glWeightPathsNV = GLContext.getCapabilities().glWeightPathsNV;
        BufferChecks.checkFunctionAddress(glWeightPathsNV);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkBuffer(floatBuffer, intBuffer.remaining());
        nglWeightPathsNV(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(floatBuffer), glWeightPathsNV);
    }
    
    static native void nglWeightPathsNV(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static void glCopyPathNV(final int n, final int n2) {
        final long glCopyPathNV = GLContext.getCapabilities().glCopyPathNV;
        BufferChecks.checkFunctionAddress(glCopyPathNV);
        nglCopyPathNV(n, n2, glCopyPathNV);
    }
    
    static native void nglCopyPathNV(final int p0, final int p1, final long p2);
    
    public static void glInterpolatePathsNV(final int n, final int n2, final int n3, final float n4) {
        final long glInterpolatePathsNV = GLContext.getCapabilities().glInterpolatePathsNV;
        BufferChecks.checkFunctionAddress(glInterpolatePathsNV);
        nglInterpolatePathsNV(n, n2, n3, n4, glInterpolatePathsNV);
    }
    
    static native void nglInterpolatePathsNV(final int p0, final int p1, final int p2, final float p3, final long p4);
    
    public static void glTransformPathNV(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glTransformPathNV = GLContext.getCapabilities().glTransformPathNV;
        BufferChecks.checkFunctionAddress(glTransformPathNV);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateTransformPathValues(n3));
        }
        nglTransformPathNV(n, n2, n3, MemoryUtil.getAddressSafe(floatBuffer), glTransformPathNV);
    }
    
    static native void nglTransformPathNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glPathParameterNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glPathParameterivNV = GLContext.getCapabilities().glPathParameterivNV;
        BufferChecks.checkFunctionAddress(glPathParameterivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglPathParameterivNV(n, n2, MemoryUtil.getAddress(intBuffer), glPathParameterivNV);
    }
    
    static native void nglPathParameterivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glPathParameteriNV(final int n, final int n2, final int n3) {
        final long glPathParameteriNV = GLContext.getCapabilities().glPathParameteriNV;
        BufferChecks.checkFunctionAddress(glPathParameteriNV);
        nglPathParameteriNV(n, n2, n3, glPathParameteriNV);
    }
    
    static native void nglPathParameteriNV(final int p0, final int p1, final int p2, final long p3);
    
    public static void glPathParameterNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glPathParameterfvNV = GLContext.getCapabilities().glPathParameterfvNV;
        BufferChecks.checkFunctionAddress(glPathParameterfvNV);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglPathParameterfvNV(n, n2, MemoryUtil.getAddress(floatBuffer), glPathParameterfvNV);
    }
    
    static native void nglPathParameterfvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glPathParameterfNV(final int n, final int n2, final float n3) {
        final long glPathParameterfNV = GLContext.getCapabilities().glPathParameterfNV;
        BufferChecks.checkFunctionAddress(glPathParameterfNV);
        nglPathParameterfNV(n, n2, n3, glPathParameterfNV);
    }
    
    static native void nglPathParameterfNV(final int p0, final int p1, final float p2, final long p3);
    
    public static void glPathDashArrayNV(final int n, final FloatBuffer floatBuffer) {
        final long glPathDashArrayNV = GLContext.getCapabilities().glPathDashArrayNV;
        BufferChecks.checkFunctionAddress(glPathDashArrayNV);
        BufferChecks.checkDirect(floatBuffer);
        nglPathDashArrayNV(n, floatBuffer.remaining(), MemoryUtil.getAddress(floatBuffer), glPathDashArrayNV);
    }
    
    static native void nglPathDashArrayNV(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGenPathsNV(final int n) {
        final long glGenPathsNV = GLContext.getCapabilities().glGenPathsNV;
        BufferChecks.checkFunctionAddress(glGenPathsNV);
        return nglGenPathsNV(n, glGenPathsNV);
    }
    
    static native int nglGenPathsNV(final int p0, final long p1);
    
    public static void glDeletePathsNV(final int n, final int n2) {
        final long glDeletePathsNV = GLContext.getCapabilities().glDeletePathsNV;
        BufferChecks.checkFunctionAddress(glDeletePathsNV);
        nglDeletePathsNV(n, n2, glDeletePathsNV);
    }
    
    static native void nglDeletePathsNV(final int p0, final int p1, final long p2);
    
    public static boolean glIsPathNV(final int n) {
        final long glIsPathNV = GLContext.getCapabilities().glIsPathNV;
        BufferChecks.checkFunctionAddress(glIsPathNV);
        return nglIsPathNV(n, glIsPathNV);
    }
    
    static native boolean nglIsPathNV(final int p0, final long p1);
    
    public static void glPathStencilFuncNV(final int n, final int n2, final int n3) {
        final long glPathStencilFuncNV = GLContext.getCapabilities().glPathStencilFuncNV;
        BufferChecks.checkFunctionAddress(glPathStencilFuncNV);
        nglPathStencilFuncNV(n, n2, n3, glPathStencilFuncNV);
    }
    
    static native void nglPathStencilFuncNV(final int p0, final int p1, final int p2, final long p3);
    
    public static void glPathStencilDepthOffsetNV(final float n, final int n2) {
        final long glPathStencilDepthOffsetNV = GLContext.getCapabilities().glPathStencilDepthOffsetNV;
        BufferChecks.checkFunctionAddress(glPathStencilDepthOffsetNV);
        nglPathStencilDepthOffsetNV(n, n2, glPathStencilDepthOffsetNV);
    }
    
    static native void nglPathStencilDepthOffsetNV(final float p0, final int p1, final long p2);
    
    public static void glStencilFillPathNV(final int n, final int n2, final int n3) {
        final long glStencilFillPathNV = GLContext.getCapabilities().glStencilFillPathNV;
        BufferChecks.checkFunctionAddress(glStencilFillPathNV);
        nglStencilFillPathNV(n, n2, n3, glStencilFillPathNV);
    }
    
    static native void nglStencilFillPathNV(final int p0, final int p1, final int p2, final long p3);
    
    public static void glStencilStrokePathNV(final int n, final int n2, final int n3) {
        final long glStencilStrokePathNV = GLContext.getCapabilities().glStencilStrokePathNV;
        BufferChecks.checkFunctionAddress(glStencilStrokePathNV);
        nglStencilStrokePathNV(n, n2, n3, glStencilStrokePathNV);
    }
    
    static native void nglStencilStrokePathNV(final int p0, final int p1, final int p2, final long p3);
    
    public static void glStencilFillPathInstancedNV(final int n, final ByteBuffer byteBuffer, final int n2, final int n3, final int n4, final int n5, final FloatBuffer floatBuffer) {
        final long glStencilFillPathInstancedNV = GLContext.getCapabilities().glStencilFillPathInstancedNV;
        BufferChecks.checkFunctionAddress(glStencilFillPathInstancedNV);
        BufferChecks.checkDirect(byteBuffer);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateTransformPathValues(n5));
        }
        nglStencilFillPathInstancedNV(byteBuffer.remaining() / GLChecks.calculateBytesPerPathName(n), n, MemoryUtil.getAddress(byteBuffer), n2, n3, n4, n5, MemoryUtil.getAddressSafe(floatBuffer), glStencilFillPathInstancedNV);
    }
    
    static native void nglStencilFillPathInstancedNV(final int p0, final int p1, final long p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glStencilStrokePathInstancedNV(final int n, final ByteBuffer byteBuffer, final int n2, final int n3, final int n4, final int n5, final FloatBuffer floatBuffer) {
        final long glStencilStrokePathInstancedNV = GLContext.getCapabilities().glStencilStrokePathInstancedNV;
        BufferChecks.checkFunctionAddress(glStencilStrokePathInstancedNV);
        BufferChecks.checkDirect(byteBuffer);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateTransformPathValues(n5));
        }
        nglStencilStrokePathInstancedNV(byteBuffer.remaining() / GLChecks.calculateBytesPerPathName(n), n, MemoryUtil.getAddress(byteBuffer), n2, n3, n4, n5, MemoryUtil.getAddressSafe(floatBuffer), glStencilStrokePathInstancedNV);
    }
    
    static native void nglStencilStrokePathInstancedNV(final int p0, final int p1, final long p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glPathCoverDepthFuncNV(final int n) {
        final long glPathCoverDepthFuncNV = GLContext.getCapabilities().glPathCoverDepthFuncNV;
        BufferChecks.checkFunctionAddress(glPathCoverDepthFuncNV);
        nglPathCoverDepthFuncNV(n, glPathCoverDepthFuncNV);
    }
    
    static native void nglPathCoverDepthFuncNV(final int p0, final long p1);
    
    public static void glPathColorGenNV(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glPathColorGenNV = GLContext.getCapabilities().glPathColorGenNV;
        BufferChecks.checkFunctionAddress(glPathColorGenNV);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, GLChecks.calculatePathColorGenCoeffsCount(n2, n3));
        }
        nglPathColorGenNV(n, n2, n3, MemoryUtil.getAddressSafe(floatBuffer), glPathColorGenNV);
    }
    
    static native void nglPathColorGenNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glPathTexGenNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glPathTexGenNV = GLContext.getCapabilities().glPathTexGenNV;
        BufferChecks.checkFunctionAddress(glPathTexGenNV);
        if (floatBuffer != null) {
            BufferChecks.checkDirect(floatBuffer);
        }
        nglPathTexGenNV(n, n2, GLChecks.calculatePathTextGenCoeffsPerComponent(floatBuffer, n2), MemoryUtil.getAddressSafe(floatBuffer), glPathTexGenNV);
    }
    
    static native void nglPathTexGenNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glPathFogGenNV(final int n) {
        final long glPathFogGenNV = GLContext.getCapabilities().glPathFogGenNV;
        BufferChecks.checkFunctionAddress(glPathFogGenNV);
        nglPathFogGenNV(n, glPathFogGenNV);
    }
    
    static native void nglPathFogGenNV(final int p0, final long p1);
    
    public static void glCoverFillPathNV(final int n, final int n2) {
        final long glCoverFillPathNV = GLContext.getCapabilities().glCoverFillPathNV;
        BufferChecks.checkFunctionAddress(glCoverFillPathNV);
        nglCoverFillPathNV(n, n2, glCoverFillPathNV);
    }
    
    static native void nglCoverFillPathNV(final int p0, final int p1, final long p2);
    
    public static void glCoverStrokePathNV(final int n, final int n2) {
        final long glCoverStrokePathNV = GLContext.getCapabilities().glCoverStrokePathNV;
        BufferChecks.checkFunctionAddress(glCoverStrokePathNV);
        nglCoverStrokePathNV(n, n2, glCoverStrokePathNV);
    }
    
    static native void nglCoverStrokePathNV(final int p0, final int p1, final long p2);
    
    public static void glCoverFillPathInstancedNV(final int n, final ByteBuffer byteBuffer, final int n2, final int n3, final int n4, final FloatBuffer floatBuffer) {
        final long glCoverFillPathInstancedNV = GLContext.getCapabilities().glCoverFillPathInstancedNV;
        BufferChecks.checkFunctionAddress(glCoverFillPathInstancedNV);
        BufferChecks.checkDirect(byteBuffer);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateTransformPathValues(n4));
        }
        nglCoverFillPathInstancedNV(byteBuffer.remaining() / GLChecks.calculateBytesPerPathName(n), n, MemoryUtil.getAddress(byteBuffer), n2, n3, n4, MemoryUtil.getAddressSafe(floatBuffer), glCoverFillPathInstancedNV);
    }
    
    static native void nglCoverFillPathInstancedNV(final int p0, final int p1, final long p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glCoverStrokePathInstancedNV(final int n, final ByteBuffer byteBuffer, final int n2, final int n3, final int n4, final FloatBuffer floatBuffer) {
        final long glCoverStrokePathInstancedNV = GLContext.getCapabilities().glCoverStrokePathInstancedNV;
        BufferChecks.checkFunctionAddress(glCoverStrokePathInstancedNV);
        BufferChecks.checkDirect(byteBuffer);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateTransformPathValues(n4));
        }
        nglCoverStrokePathInstancedNV(byteBuffer.remaining() / GLChecks.calculateBytesPerPathName(n), n, MemoryUtil.getAddress(byteBuffer), n2, n3, n4, MemoryUtil.getAddressSafe(floatBuffer), glCoverStrokePathInstancedNV);
    }
    
    static native void nglCoverStrokePathInstancedNV(final int p0, final int p1, final long p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glGetPathParameterNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetPathParameterivNV = GLContext.getCapabilities().glGetPathParameterivNV;
        BufferChecks.checkFunctionAddress(glGetPathParameterivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetPathParameterivNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetPathParameterivNV);
    }
    
    static native void nglGetPathParameterivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetPathParameteriNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetPathParameterivNV = capabilities.glGetPathParameterivNV;
        BufferChecks.checkFunctionAddress(glGetPathParameterivNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetPathParameterivNV(n, n2, MemoryUtil.getAddress(bufferInt), glGetPathParameterivNV);
        return bufferInt.get(0);
    }
    
    public static void glGetPathParameterfvNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetPathParameterfvNV = GLContext.getCapabilities().glGetPathParameterfvNV;
        BufferChecks.checkFunctionAddress(glGetPathParameterfvNV);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetPathParameterfvNV(n, n2, MemoryUtil.getAddress(floatBuffer), glGetPathParameterfvNV);
    }
    
    static native void nglGetPathParameterfvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static float glGetPathParameterfNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetPathParameterfvNV = capabilities.glGetPathParameterfvNV;
        BufferChecks.checkFunctionAddress(glGetPathParameterfvNV);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetPathParameterfvNV(n, n2, MemoryUtil.getAddress(bufferFloat), glGetPathParameterfvNV);
        return bufferFloat.get(0);
    }
    
    public static void glGetPathCommandsNV(final int n, final ByteBuffer byteBuffer) {
        final long glGetPathCommandsNV = GLContext.getCapabilities().glGetPathCommandsNV;
        BufferChecks.checkFunctionAddress(glGetPathCommandsNV);
        BufferChecks.checkDirect(byteBuffer);
        nglGetPathCommandsNV(n, MemoryUtil.getAddress(byteBuffer), glGetPathCommandsNV);
    }
    
    static native void nglGetPathCommandsNV(final int p0, final long p1, final long p2);
    
    public static void glGetPathCoordsNV(final int n, final FloatBuffer floatBuffer) {
        final long glGetPathCoordsNV = GLContext.getCapabilities().glGetPathCoordsNV;
        BufferChecks.checkFunctionAddress(glGetPathCoordsNV);
        BufferChecks.checkDirect(floatBuffer);
        nglGetPathCoordsNV(n, MemoryUtil.getAddress(floatBuffer), glGetPathCoordsNV);
    }
    
    static native void nglGetPathCoordsNV(final int p0, final long p1, final long p2);
    
    public static void glGetPathDashArrayNV(final int n, final FloatBuffer floatBuffer) {
        final long glGetPathDashArrayNV = GLContext.getCapabilities().glGetPathDashArrayNV;
        BufferChecks.checkFunctionAddress(glGetPathDashArrayNV);
        BufferChecks.checkDirect(floatBuffer);
        nglGetPathDashArrayNV(n, MemoryUtil.getAddress(floatBuffer), glGetPathDashArrayNV);
    }
    
    static native void nglGetPathDashArrayNV(final int p0, final long p1, final long p2);
    
    public static void glGetPathMetricsNV(final int n, final int n2, final ByteBuffer byteBuffer, final int n3, final int n4, final FloatBuffer floatBuffer) {
        final long glGetPathMetricsNV = GLContext.getCapabilities().glGetPathMetricsNV;
        BufferChecks.checkFunctionAddress(glGetPathMetricsNV);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateMetricsSize(n, n4));
        nglGetPathMetricsNV(n, byteBuffer.remaining() / GLChecks.calculateBytesPerPathName(n2), n2, MemoryUtil.getAddress(byteBuffer), n3, n4, MemoryUtil.getAddress(floatBuffer), glGetPathMetricsNV);
    }
    
    static native void nglGetPathMetricsNV(final int p0, final int p1, final int p2, final long p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glGetPathMetricRangeNV(final int n, final int n2, final int n3, final int n4, final FloatBuffer floatBuffer) {
        final long glGetPathMetricRangeNV = GLContext.getCapabilities().glGetPathMetricRangeNV;
        BufferChecks.checkFunctionAddress(glGetPathMetricRangeNV);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateMetricsSize(n, n4));
        nglGetPathMetricRangeNV(n, n2, n3, n4, MemoryUtil.getAddress(floatBuffer), glGetPathMetricRangeNV);
    }
    
    static native void nglGetPathMetricRangeNV(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glGetPathSpacingNV(final int n, final int n2, final ByteBuffer byteBuffer, final int n3, final float n4, final float n5, final int n6, final FloatBuffer floatBuffer) {
        final long glGetPathSpacingNV = GLContext.getCapabilities().glGetPathSpacingNV;
        BufferChecks.checkFunctionAddress(glGetPathSpacingNV);
        final int n7 = byteBuffer.remaining() / GLChecks.calculateBytesPerPathName(n2);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkBuffer(floatBuffer, n7 - 1);
        nglGetPathSpacingNV(n, n7, n2, MemoryUtil.getAddress(byteBuffer), n3, n4, n5, n6, MemoryUtil.getAddress(floatBuffer), glGetPathSpacingNV);
    }
    
    static native void nglGetPathSpacingNV(final int p0, final int p1, final int p2, final long p3, final int p4, final float p5, final float p6, final int p7, final long p8, final long p9);
    
    public static void glGetPathColorGenNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetPathColorGenivNV = GLContext.getCapabilities().glGetPathColorGenivNV;
        BufferChecks.checkFunctionAddress(glGetPathColorGenivNV);
        BufferChecks.checkBuffer(intBuffer, 16);
        nglGetPathColorGenivNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetPathColorGenivNV);
    }
    
    static native void nglGetPathColorGenivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetPathColorGeniNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetPathColorGenivNV = capabilities.glGetPathColorGenivNV;
        BufferChecks.checkFunctionAddress(glGetPathColorGenivNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetPathColorGenivNV(n, n2, MemoryUtil.getAddress(bufferInt), glGetPathColorGenivNV);
        return bufferInt.get(0);
    }
    
    public static void glGetPathColorGenNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetPathColorGenfvNV = GLContext.getCapabilities().glGetPathColorGenfvNV;
        BufferChecks.checkFunctionAddress(glGetPathColorGenfvNV);
        BufferChecks.checkBuffer(floatBuffer, 16);
        nglGetPathColorGenfvNV(n, n2, MemoryUtil.getAddress(floatBuffer), glGetPathColorGenfvNV);
    }
    
    static native void nglGetPathColorGenfvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static float glGetPathColorGenfNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetPathColorGenfvNV = capabilities.glGetPathColorGenfvNV;
        BufferChecks.checkFunctionAddress(glGetPathColorGenfvNV);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetPathColorGenfvNV(n, n2, MemoryUtil.getAddress(bufferFloat), glGetPathColorGenfvNV);
        return bufferFloat.get(0);
    }
    
    public static void glGetPathTexGenNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetPathTexGenivNV = GLContext.getCapabilities().glGetPathTexGenivNV;
        BufferChecks.checkFunctionAddress(glGetPathTexGenivNV);
        BufferChecks.checkBuffer(intBuffer, 16);
        nglGetPathTexGenivNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetPathTexGenivNV);
    }
    
    static native void nglGetPathTexGenivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetPathTexGeniNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetPathTexGenivNV = capabilities.glGetPathTexGenivNV;
        BufferChecks.checkFunctionAddress(glGetPathTexGenivNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetPathTexGenivNV(n, n2, MemoryUtil.getAddress(bufferInt), glGetPathTexGenivNV);
        return bufferInt.get(0);
    }
    
    public static void glGetPathTexGenNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetPathTexGenfvNV = GLContext.getCapabilities().glGetPathTexGenfvNV;
        BufferChecks.checkFunctionAddress(glGetPathTexGenfvNV);
        BufferChecks.checkBuffer(floatBuffer, 16);
        nglGetPathTexGenfvNV(n, n2, MemoryUtil.getAddress(floatBuffer), glGetPathTexGenfvNV);
    }
    
    static native void nglGetPathTexGenfvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static float glGetPathTexGenfNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetPathTexGenfvNV = capabilities.glGetPathTexGenfvNV;
        BufferChecks.checkFunctionAddress(glGetPathTexGenfvNV);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetPathTexGenfvNV(n, n2, MemoryUtil.getAddress(bufferFloat), glGetPathTexGenfvNV);
        return bufferFloat.get(0);
    }
    
    public static boolean glIsPointInFillPathNV(final int n, final int n2, final float n3, final float n4) {
        final long glIsPointInFillPathNV = GLContext.getCapabilities().glIsPointInFillPathNV;
        BufferChecks.checkFunctionAddress(glIsPointInFillPathNV);
        return nglIsPointInFillPathNV(n, n2, n3, n4, glIsPointInFillPathNV);
    }
    
    static native boolean nglIsPointInFillPathNV(final int p0, final int p1, final float p2, final float p3, final long p4);
    
    public static boolean glIsPointInStrokePathNV(final int n, final float n2, final float n3) {
        final long glIsPointInStrokePathNV = GLContext.getCapabilities().glIsPointInStrokePathNV;
        BufferChecks.checkFunctionAddress(glIsPointInStrokePathNV);
        return nglIsPointInStrokePathNV(n, n2, n3, glIsPointInStrokePathNV);
    }
    
    static native boolean nglIsPointInStrokePathNV(final int p0, final float p1, final float p2, final long p3);
    
    public static float glGetPathLengthNV(final int n, final int n2, final int n3) {
        final long glGetPathLengthNV = GLContext.getCapabilities().glGetPathLengthNV;
        BufferChecks.checkFunctionAddress(glGetPathLengthNV);
        return nglGetPathLengthNV(n, n2, n3, glGetPathLengthNV);
    }
    
    static native float nglGetPathLengthNV(final int p0, final int p1, final int p2, final long p3);
    
    public static boolean glPointAlongPathNV(final int n, final int n2, final int n3, final float n4, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final FloatBuffer floatBuffer3, final FloatBuffer floatBuffer4) {
        final long glPointAlongPathNV = GLContext.getCapabilities().glPointAlongPathNV;
        BufferChecks.checkFunctionAddress(glPointAlongPathNV);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, 1);
        }
        if (floatBuffer2 != null) {
            BufferChecks.checkBuffer(floatBuffer2, 1);
        }
        if (floatBuffer3 != null) {
            BufferChecks.checkBuffer(floatBuffer3, 1);
        }
        if (floatBuffer4 != null) {
            BufferChecks.checkBuffer(floatBuffer4, 1);
        }
        return nglPointAlongPathNV(n, n2, n3, n4, MemoryUtil.getAddressSafe(floatBuffer), MemoryUtil.getAddressSafe(floatBuffer2), MemoryUtil.getAddressSafe(floatBuffer3), MemoryUtil.getAddressSafe(floatBuffer4), glPointAlongPathNV);
    }
    
    static native boolean nglPointAlongPathNV(final int p0, final int p1, final int p2, final float p3, final long p4, final long p5, final long p6, final long p7, final long p8);
}
