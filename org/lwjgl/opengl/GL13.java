package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class GL13
{
    public static final int GL_TEXTURE0 = 33984;
    public static final int GL_TEXTURE1 = 33985;
    public static final int GL_TEXTURE2 = 33986;
    public static final int GL_TEXTURE3 = 33987;
    public static final int GL_TEXTURE4 = 33988;
    public static final int GL_TEXTURE5 = 33989;
    public static final int GL_TEXTURE6 = 33990;
    public static final int GL_TEXTURE7 = 33991;
    public static final int GL_TEXTURE8 = 33992;
    public static final int GL_TEXTURE9 = 33993;
    public static final int GL_TEXTURE10 = 33994;
    public static final int GL_TEXTURE11 = 33995;
    public static final int GL_TEXTURE12 = 33996;
    public static final int GL_TEXTURE13 = 33997;
    public static final int GL_TEXTURE14 = 33998;
    public static final int GL_TEXTURE15 = 33999;
    public static final int GL_TEXTURE16 = 34000;
    public static final int GL_TEXTURE17 = 34001;
    public static final int GL_TEXTURE18 = 34002;
    public static final int GL_TEXTURE19 = 34003;
    public static final int GL_TEXTURE20 = 34004;
    public static final int GL_TEXTURE21 = 34005;
    public static final int GL_TEXTURE22 = 34006;
    public static final int GL_TEXTURE23 = 34007;
    public static final int GL_TEXTURE24 = 34008;
    public static final int GL_TEXTURE25 = 34009;
    public static final int GL_TEXTURE26 = 34010;
    public static final int GL_TEXTURE27 = 34011;
    public static final int GL_TEXTURE28 = 34012;
    public static final int GL_TEXTURE29 = 34013;
    public static final int GL_TEXTURE30 = 34014;
    public static final int GL_TEXTURE31 = 34015;
    public static final int GL_ACTIVE_TEXTURE = 34016;
    public static final int GL_CLIENT_ACTIVE_TEXTURE = 34017;
    public static final int GL_MAX_TEXTURE_UNITS = 34018;
    public static final int GL_NORMAL_MAP = 34065;
    public static final int GL_REFLECTION_MAP = 34066;
    public static final int GL_TEXTURE_CUBE_MAP = 34067;
    public static final int GL_TEXTURE_BINDING_CUBE_MAP = 34068;
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_X = 34069;
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_X = 34070;
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Y = 34071;
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y = 34072;
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Z = 34073;
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z = 34074;
    public static final int GL_PROXY_TEXTURE_CUBE_MAP = 34075;
    public static final int GL_MAX_CUBE_MAP_TEXTURE_SIZE = 34076;
    public static final int GL_COMPRESSED_ALPHA = 34025;
    public static final int GL_COMPRESSED_LUMINANCE = 34026;
    public static final int GL_COMPRESSED_LUMINANCE_ALPHA = 34027;
    public static final int GL_COMPRESSED_INTENSITY = 34028;
    public static final int GL_COMPRESSED_RGB = 34029;
    public static final int GL_COMPRESSED_RGBA = 34030;
    public static final int GL_TEXTURE_COMPRESSION_HINT = 34031;
    public static final int GL_TEXTURE_COMPRESSED_IMAGE_SIZE = 34464;
    public static final int GL_TEXTURE_COMPRESSED = 34465;
    public static final int GL_NUM_COMPRESSED_TEXTURE_FORMATS = 34466;
    public static final int GL_COMPRESSED_TEXTURE_FORMATS = 34467;
    public static final int GL_MULTISAMPLE = 32925;
    public static final int GL_SAMPLE_ALPHA_TO_COVERAGE = 32926;
    public static final int GL_SAMPLE_ALPHA_TO_ONE = 32927;
    public static final int GL_SAMPLE_COVERAGE = 32928;
    public static final int GL_SAMPLE_BUFFERS = 32936;
    public static final int GL_SAMPLES = 32937;
    public static final int GL_SAMPLE_COVERAGE_VALUE = 32938;
    public static final int GL_SAMPLE_COVERAGE_INVERT = 32939;
    public static final int GL_MULTISAMPLE_BIT = 536870912;
    public static final int GL_TRANSPOSE_MODELVIEW_MATRIX = 34019;
    public static final int GL_TRANSPOSE_PROJECTION_MATRIX = 34020;
    public static final int GL_TRANSPOSE_TEXTURE_MATRIX = 34021;
    public static final int GL_TRANSPOSE_COLOR_MATRIX = 34022;
    public static final int GL_COMBINE = 34160;
    public static final int GL_COMBINE_RGB = 34161;
    public static final int GL_COMBINE_ALPHA = 34162;
    public static final int GL_SOURCE0_RGB = 34176;
    public static final int GL_SOURCE1_RGB = 34177;
    public static final int GL_SOURCE2_RGB = 34178;
    public static final int GL_SOURCE0_ALPHA = 34184;
    public static final int GL_SOURCE1_ALPHA = 34185;
    public static final int GL_SOURCE2_ALPHA = 34186;
    public static final int GL_OPERAND0_RGB = 34192;
    public static final int GL_OPERAND1_RGB = 34193;
    public static final int GL_OPERAND2_RGB = 34194;
    public static final int GL_OPERAND0_ALPHA = 34200;
    public static final int GL_OPERAND1_ALPHA = 34201;
    public static final int GL_OPERAND2_ALPHA = 34202;
    public static final int GL_RGB_SCALE = 34163;
    public static final int GL_ADD_SIGNED = 34164;
    public static final int GL_INTERPOLATE = 34165;
    public static final int GL_SUBTRACT = 34023;
    public static final int GL_CONSTANT = 34166;
    public static final int GL_PRIMARY_COLOR = 34167;
    public static final int GL_PREVIOUS = 34168;
    public static final int GL_DOT3_RGB = 34478;
    public static final int GL_DOT3_RGBA = 34479;
    public static final int GL_CLAMP_TO_BORDER = 33069;
    
    private GL13() {
    }
    
    public static void glActiveTexture(final int n) {
        final long glActiveTexture = GLContext.getCapabilities().glActiveTexture;
        BufferChecks.checkFunctionAddress(glActiveTexture);
        nglActiveTexture(n, glActiveTexture);
    }
    
    static native void nglActiveTexture(final int p0, final long p1);
    
    public static void glClientActiveTexture(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glClientActiveTexture = capabilities.glClientActiveTexture;
        BufferChecks.checkFunctionAddress(glClientActiveTexture);
        StateTracker.getReferences(capabilities).glClientActiveTexture = n - 33984;
        nglClientActiveTexture(n, glClientActiveTexture);
    }
    
    static native void nglClientActiveTexture(final int p0, final long p1);
    
    public static void glCompressedTexImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexImage1D = capabilities.glCompressedTexImage1D;
        BufferChecks.checkFunctionAddress(glCompressedTexImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTexImage1D(n, n2, n3, n4, n5, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTexImage1D);
    }
    
    static native void nglCompressedTexImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glCompressedTexImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexImage1D = capabilities.glCompressedTexImage1D;
        BufferChecks.checkFunctionAddress(glCompressedTexImage1D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTexImage1DBO(n, n2, n3, n4, n5, n6, n7, glCompressedTexImage1D);
    }
    
    static native void nglCompressedTexImage1DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glCompressedTexImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexImage1D = capabilities.glCompressedTexImage1D;
        BufferChecks.checkFunctionAddress(glCompressedTexImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        nglCompressedTexImage1D(n, n2, n3, n4, n5, n6, 0L, glCompressedTexImage1D);
    }
    
    public static void glCompressedTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexImage2D = capabilities.glCompressedTexImage2D;
        BufferChecks.checkFunctionAddress(glCompressedTexImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTexImage2D(n, n2, n3, n4, n5, n6, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTexImage2D);
    }
    
    static native void nglCompressedTexImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glCompressedTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexImage2D = capabilities.glCompressedTexImage2D;
        BufferChecks.checkFunctionAddress(glCompressedTexImage2D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTexImage2DBO(n, n2, n3, n4, n5, n6, n7, n8, glCompressedTexImage2D);
    }
    
    static native void nglCompressedTexImage2DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glCompressedTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexImage2D = capabilities.glCompressedTexImage2D;
        BufferChecks.checkFunctionAddress(glCompressedTexImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        nglCompressedTexImage2D(n, n2, n3, n4, n5, n6, n7, 0L, glCompressedTexImage2D);
    }
    
    public static void glCompressedTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexImage3D = capabilities.glCompressedTexImage3D;
        BufferChecks.checkFunctionAddress(glCompressedTexImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTexImage3D(n, n2, n3, n4, n5, n6, n7, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTexImage3D);
    }
    
    static native void nglCompressedTexImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glCompressedTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexImage3D = capabilities.glCompressedTexImage3D;
        BufferChecks.checkFunctionAddress(glCompressedTexImage3D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTexImage3DBO(n, n2, n3, n4, n5, n6, n7, n8, n9, glCompressedTexImage3D);
    }
    
    static native void nglCompressedTexImage3DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glCompressedTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexImage3D = capabilities.glCompressedTexImage3D;
        BufferChecks.checkFunctionAddress(glCompressedTexImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        nglCompressedTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, 0L, glCompressedTexImage3D);
    }
    
    public static void glCompressedTexSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexSubImage1D = capabilities.glCompressedTexSubImage1D;
        BufferChecks.checkFunctionAddress(glCompressedTexSubImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTexSubImage1D(n, n2, n3, n4, n5, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTexSubImage1D);
    }
    
    static native void nglCompressedTexSubImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glCompressedTexSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexSubImage1D = capabilities.glCompressedTexSubImage1D;
        BufferChecks.checkFunctionAddress(glCompressedTexSubImage1D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTexSubImage1DBO(n, n2, n3, n4, n5, n6, n7, glCompressedTexSubImage1D);
    }
    
    static native void nglCompressedTexSubImage1DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glCompressedTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexSubImage2D = capabilities.glCompressedTexSubImage2D;
        BufferChecks.checkFunctionAddress(glCompressedTexSubImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTexSubImage2D(n, n2, n3, n4, n5, n6, n7, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTexSubImage2D);
    }
    
    static native void nglCompressedTexSubImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glCompressedTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexSubImage2D = capabilities.glCompressedTexSubImage2D;
        BufferChecks.checkFunctionAddress(glCompressedTexSubImage2D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTexSubImage2DBO(n, n2, n3, n4, n5, n6, n7, n8, n9, glCompressedTexSubImage2D);
    }
    
    static native void nglCompressedTexSubImage2DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glCompressedTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexSubImage3D = capabilities.glCompressedTexSubImage3D;
        BufferChecks.checkFunctionAddress(glCompressedTexSubImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTexSubImage3D);
    }
    
    static native void nglCompressedTexSubImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10, final long p11);
    
    public static void glCompressedTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final long n11) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexSubImage3D = capabilities.glCompressedTexSubImage3D;
        BufferChecks.checkFunctionAddress(glCompressedTexSubImage3D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTexSubImage3DBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, glCompressedTexSubImage3D);
    }
    
    static native void nglCompressedTexSubImage3DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10, final long p11);
    
    public static void glGetCompressedTexImage(final int n, final int n2, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTexImage = capabilities.glGetCompressedTexImage;
        BufferChecks.checkFunctionAddress(glGetCompressedTexImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglGetCompressedTexImage(n, n2, MemoryUtil.getAddress(byteBuffer), glGetCompressedTexImage);
    }
    
    public static void glGetCompressedTexImage(final int n, final int n2, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTexImage = capabilities.glGetCompressedTexImage;
        BufferChecks.checkFunctionAddress(glGetCompressedTexImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglGetCompressedTexImage(n, n2, MemoryUtil.getAddress(intBuffer), glGetCompressedTexImage);
    }
    
    public static void glGetCompressedTexImage(final int n, final int n2, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTexImage = capabilities.glGetCompressedTexImage;
        BufferChecks.checkFunctionAddress(glGetCompressedTexImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglGetCompressedTexImage(n, n2, MemoryUtil.getAddress(shortBuffer), glGetCompressedTexImage);
    }
    
    static native void nglGetCompressedTexImage(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetCompressedTexImage(final int n, final int n2, final long n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTexImage = capabilities.glGetCompressedTexImage;
        BufferChecks.checkFunctionAddress(glGetCompressedTexImage);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetCompressedTexImageBO(n, n2, n3, glGetCompressedTexImage);
    }
    
    static native void nglGetCompressedTexImageBO(final int p0, final int p1, final long p2, final long p3);
    
    public static void glMultiTexCoord1f(final int n, final float n2) {
        final long glMultiTexCoord1f = GLContext.getCapabilities().glMultiTexCoord1f;
        BufferChecks.checkFunctionAddress(glMultiTexCoord1f);
        nglMultiTexCoord1f(n, n2, glMultiTexCoord1f);
    }
    
    static native void nglMultiTexCoord1f(final int p0, final float p1, final long p2);
    
    public static void glMultiTexCoord1d(final int n, final double n2) {
        final long glMultiTexCoord1d = GLContext.getCapabilities().glMultiTexCoord1d;
        BufferChecks.checkFunctionAddress(glMultiTexCoord1d);
        nglMultiTexCoord1d(n, n2, glMultiTexCoord1d);
    }
    
    static native void nglMultiTexCoord1d(final int p0, final double p1, final long p2);
    
    public static void glMultiTexCoord2f(final int n, final float n2, final float n3) {
        final long glMultiTexCoord2f = GLContext.getCapabilities().glMultiTexCoord2f;
        BufferChecks.checkFunctionAddress(glMultiTexCoord2f);
        nglMultiTexCoord2f(n, n2, n3, glMultiTexCoord2f);
    }
    
    static native void nglMultiTexCoord2f(final int p0, final float p1, final float p2, final long p3);
    
    public static void glMultiTexCoord2d(final int n, final double n2, final double n3) {
        final long glMultiTexCoord2d = GLContext.getCapabilities().glMultiTexCoord2d;
        BufferChecks.checkFunctionAddress(glMultiTexCoord2d);
        nglMultiTexCoord2d(n, n2, n3, glMultiTexCoord2d);
    }
    
    static native void nglMultiTexCoord2d(final int p0, final double p1, final double p2, final long p3);
    
    public static void glMultiTexCoord3f(final int n, final float n2, final float n3, final float n4) {
        final long glMultiTexCoord3f = GLContext.getCapabilities().glMultiTexCoord3f;
        BufferChecks.checkFunctionAddress(glMultiTexCoord3f);
        nglMultiTexCoord3f(n, n2, n3, n4, glMultiTexCoord3f);
    }
    
    static native void nglMultiTexCoord3f(final int p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glMultiTexCoord3d(final int n, final double n2, final double n3, final double n4) {
        final long glMultiTexCoord3d = GLContext.getCapabilities().glMultiTexCoord3d;
        BufferChecks.checkFunctionAddress(glMultiTexCoord3d);
        nglMultiTexCoord3d(n, n2, n3, n4, glMultiTexCoord3d);
    }
    
    static native void nglMultiTexCoord3d(final int p0, final double p1, final double p2, final double p3, final long p4);
    
    public static void glMultiTexCoord4f(final int n, final float n2, final float n3, final float n4, final float n5) {
        final long glMultiTexCoord4f = GLContext.getCapabilities().glMultiTexCoord4f;
        BufferChecks.checkFunctionAddress(glMultiTexCoord4f);
        nglMultiTexCoord4f(n, n2, n3, n4, n5, glMultiTexCoord4f);
    }
    
    static native void nglMultiTexCoord4f(final int p0, final float p1, final float p2, final float p3, final float p4, final long p5);
    
    public static void glMultiTexCoord4d(final int n, final double n2, final double n3, final double n4, final double n5) {
        final long glMultiTexCoord4d = GLContext.getCapabilities().glMultiTexCoord4d;
        BufferChecks.checkFunctionAddress(glMultiTexCoord4d);
        nglMultiTexCoord4d(n, n2, n3, n4, n5, glMultiTexCoord4d);
    }
    
    static native void nglMultiTexCoord4d(final int p0, final double p1, final double p2, final double p3, final double p4, final long p5);
    
    public static void glLoadTransposeMatrix(final FloatBuffer floatBuffer) {
        final long glLoadTransposeMatrixf = GLContext.getCapabilities().glLoadTransposeMatrixf;
        BufferChecks.checkFunctionAddress(glLoadTransposeMatrixf);
        BufferChecks.checkBuffer(floatBuffer, 16);
        nglLoadTransposeMatrixf(MemoryUtil.getAddress(floatBuffer), glLoadTransposeMatrixf);
    }
    
    static native void nglLoadTransposeMatrixf(final long p0, final long p1);
    
    public static void glLoadTransposeMatrix(final DoubleBuffer doubleBuffer) {
        final long glLoadTransposeMatrixd = GLContext.getCapabilities().glLoadTransposeMatrixd;
        BufferChecks.checkFunctionAddress(glLoadTransposeMatrixd);
        BufferChecks.checkBuffer(doubleBuffer, 16);
        nglLoadTransposeMatrixd(MemoryUtil.getAddress(doubleBuffer), glLoadTransposeMatrixd);
    }
    
    static native void nglLoadTransposeMatrixd(final long p0, final long p1);
    
    public static void glMultTransposeMatrix(final FloatBuffer floatBuffer) {
        final long glMultTransposeMatrixf = GLContext.getCapabilities().glMultTransposeMatrixf;
        BufferChecks.checkFunctionAddress(glMultTransposeMatrixf);
        BufferChecks.checkBuffer(floatBuffer, 16);
        nglMultTransposeMatrixf(MemoryUtil.getAddress(floatBuffer), glMultTransposeMatrixf);
    }
    
    static native void nglMultTransposeMatrixf(final long p0, final long p1);
    
    public static void glMultTransposeMatrix(final DoubleBuffer doubleBuffer) {
        final long glMultTransposeMatrixd = GLContext.getCapabilities().glMultTransposeMatrixd;
        BufferChecks.checkFunctionAddress(glMultTransposeMatrixd);
        BufferChecks.checkBuffer(doubleBuffer, 16);
        nglMultTransposeMatrixd(MemoryUtil.getAddress(doubleBuffer), glMultTransposeMatrixd);
    }
    
    static native void nglMultTransposeMatrixd(final long p0, final long p1);
    
    public static void glSampleCoverage(final float n, final boolean b) {
        final long glSampleCoverage = GLContext.getCapabilities().glSampleCoverage;
        BufferChecks.checkFunctionAddress(glSampleCoverage);
        nglSampleCoverage(n, b, glSampleCoverage);
    }
    
    static native void nglSampleCoverage(final float p0, final boolean p1, final long p2);
}
