package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class GL14
{
    public static final int GL_GENERATE_MIPMAP = 33169;
    public static final int GL_GENERATE_MIPMAP_HINT = 33170;
    public static final int GL_DEPTH_COMPONENT16 = 33189;
    public static final int GL_DEPTH_COMPONENT24 = 33190;
    public static final int GL_DEPTH_COMPONENT32 = 33191;
    public static final int GL_TEXTURE_DEPTH_SIZE = 34890;
    public static final int GL_DEPTH_TEXTURE_MODE = 34891;
    public static final int GL_TEXTURE_COMPARE_MODE = 34892;
    public static final int GL_TEXTURE_COMPARE_FUNC = 34893;
    public static final int GL_COMPARE_R_TO_TEXTURE = 34894;
    public static final int GL_FOG_COORDINATE_SOURCE = 33872;
    public static final int GL_FOG_COORDINATE = 33873;
    public static final int GL_FRAGMENT_DEPTH = 33874;
    public static final int GL_CURRENT_FOG_COORDINATE = 33875;
    public static final int GL_FOG_COORDINATE_ARRAY_TYPE = 33876;
    public static final int GL_FOG_COORDINATE_ARRAY_STRIDE = 33877;
    public static final int GL_FOG_COORDINATE_ARRAY_POINTER = 33878;
    public static final int GL_FOG_COORDINATE_ARRAY = 33879;
    public static final int GL_POINT_SIZE_MIN = 33062;
    public static final int GL_POINT_SIZE_MAX = 33063;
    public static final int GL_POINT_FADE_THRESHOLD_SIZE = 33064;
    public static final int GL_POINT_DISTANCE_ATTENUATION = 33065;
    public static final int GL_COLOR_SUM = 33880;
    public static final int GL_CURRENT_SECONDARY_COLOR = 33881;
    public static final int GL_SECONDARY_COLOR_ARRAY_SIZE = 33882;
    public static final int GL_SECONDARY_COLOR_ARRAY_TYPE = 33883;
    public static final int GL_SECONDARY_COLOR_ARRAY_STRIDE = 33884;
    public static final int GL_SECONDARY_COLOR_ARRAY_POINTER = 33885;
    public static final int GL_SECONDARY_COLOR_ARRAY = 33886;
    public static final int GL_BLEND_DST_RGB = 32968;
    public static final int GL_BLEND_SRC_RGB = 32969;
    public static final int GL_BLEND_DST_ALPHA = 32970;
    public static final int GL_BLEND_SRC_ALPHA = 32971;
    public static final int GL_INCR_WRAP = 34055;
    public static final int GL_DECR_WRAP = 34056;
    public static final int GL_TEXTURE_FILTER_CONTROL = 34048;
    public static final int GL_TEXTURE_LOD_BIAS = 34049;
    public static final int GL_MAX_TEXTURE_LOD_BIAS = 34045;
    public static final int GL_MIRRORED_REPEAT = 33648;
    public static final int GL_BLEND_COLOR = 32773;
    public static final int GL_BLEND_EQUATION = 32777;
    public static final int GL_FUNC_ADD = 32774;
    public static final int GL_FUNC_SUBTRACT = 32778;
    public static final int GL_FUNC_REVERSE_SUBTRACT = 32779;
    public static final int GL_MIN = 32775;
    public static final int GL_MAX = 32776;
    
    private GL14() {
    }
    
    public static void glBlendEquation(final int n) {
        final long glBlendEquation = GLContext.getCapabilities().glBlendEquation;
        BufferChecks.checkFunctionAddress(glBlendEquation);
        nglBlendEquation(n, glBlendEquation);
    }
    
    static native void nglBlendEquation(final int p0, final long p1);
    
    public static void glBlendColor(final float n, final float n2, final float n3, final float n4) {
        final long glBlendColor = GLContext.getCapabilities().glBlendColor;
        BufferChecks.checkFunctionAddress(glBlendColor);
        nglBlendColor(n, n2, n3, n4, glBlendColor);
    }
    
    static native void nglBlendColor(final float p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glFogCoordf(final float n) {
        final long glFogCoordf = GLContext.getCapabilities().glFogCoordf;
        BufferChecks.checkFunctionAddress(glFogCoordf);
        nglFogCoordf(n, glFogCoordf);
    }
    
    static native void nglFogCoordf(final float p0, final long p1);
    
    public static void glFogCoordd(final double n) {
        final long glFogCoordd = GLContext.getCapabilities().glFogCoordd;
        BufferChecks.checkFunctionAddress(glFogCoordd);
        nglFogCoordd(n, glFogCoordd);
    }
    
    static native void nglFogCoordd(final double p0, final long p1);
    
    public static void glFogCoordPointer(final int n, final DoubleBuffer gl14_glFogCoordPointer_data) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glFogCoordPointer = capabilities.glFogCoordPointer;
        BufferChecks.checkFunctionAddress(glFogCoordPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(gl14_glFogCoordPointer_data);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).GL14_glFogCoordPointer_data = gl14_glFogCoordPointer_data;
        }
        nglFogCoordPointer(5130, n, MemoryUtil.getAddress(gl14_glFogCoordPointer_data), glFogCoordPointer);
    }
    
    public static void glFogCoordPointer(final int n, final FloatBuffer gl14_glFogCoordPointer_data) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glFogCoordPointer = capabilities.glFogCoordPointer;
        BufferChecks.checkFunctionAddress(glFogCoordPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(gl14_glFogCoordPointer_data);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).GL14_glFogCoordPointer_data = gl14_glFogCoordPointer_data;
        }
        nglFogCoordPointer(5126, n, MemoryUtil.getAddress(gl14_glFogCoordPointer_data), glFogCoordPointer);
    }
    
    static native void nglFogCoordPointer(final int p0, final int p1, final long p2, final long p3);
    
    public static void glFogCoordPointer(final int n, final int n2, final long n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glFogCoordPointer = capabilities.glFogCoordPointer;
        BufferChecks.checkFunctionAddress(glFogCoordPointer);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglFogCoordPointerBO(n, n2, n3, glFogCoordPointer);
    }
    
    static native void nglFogCoordPointerBO(final int p0, final int p1, final long p2, final long p3);
    
    public static void glMultiDrawArrays(final int n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long glMultiDrawArrays = GLContext.getCapabilities().glMultiDrawArrays;
        BufferChecks.checkFunctionAddress(glMultiDrawArrays);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkBuffer(intBuffer2, intBuffer.remaining());
        nglMultiDrawArrays(n, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), intBuffer.remaining(), glMultiDrawArrays);
    }
    
    static native void nglMultiDrawArrays(final int p0, final long p1, final long p2, final int p3, final long p4);
    
    public static void glPointParameteri(final int n, final int n2) {
        final long glPointParameteri = GLContext.getCapabilities().glPointParameteri;
        BufferChecks.checkFunctionAddress(glPointParameteri);
        nglPointParameteri(n, n2, glPointParameteri);
    }
    
    static native void nglPointParameteri(final int p0, final int p1, final long p2);
    
    public static void glPointParameterf(final int n, final float n2) {
        final long glPointParameterf = GLContext.getCapabilities().glPointParameterf;
        BufferChecks.checkFunctionAddress(glPointParameterf);
        nglPointParameterf(n, n2, glPointParameterf);
    }
    
    static native void nglPointParameterf(final int p0, final float p1, final long p2);
    
    public static void glPointParameter(final int n, final IntBuffer intBuffer) {
        final long glPointParameteriv = GLContext.getCapabilities().glPointParameteriv;
        BufferChecks.checkFunctionAddress(glPointParameteriv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglPointParameteriv(n, MemoryUtil.getAddress(intBuffer), glPointParameteriv);
    }
    
    static native void nglPointParameteriv(final int p0, final long p1, final long p2);
    
    public static void glPointParameter(final int n, final FloatBuffer floatBuffer) {
        final long glPointParameterfv = GLContext.getCapabilities().glPointParameterfv;
        BufferChecks.checkFunctionAddress(glPointParameterfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglPointParameterfv(n, MemoryUtil.getAddress(floatBuffer), glPointParameterfv);
    }
    
    static native void nglPointParameterfv(final int p0, final long p1, final long p2);
    
    public static void glSecondaryColor3b(final byte b, final byte b2, final byte b3) {
        final long glSecondaryColor3b = GLContext.getCapabilities().glSecondaryColor3b;
        BufferChecks.checkFunctionAddress(glSecondaryColor3b);
        nglSecondaryColor3b(b, b2, b3, glSecondaryColor3b);
    }
    
    static native void nglSecondaryColor3b(final byte p0, final byte p1, final byte p2, final long p3);
    
    public static void glSecondaryColor3f(final float n, final float n2, final float n3) {
        final long glSecondaryColor3f = GLContext.getCapabilities().glSecondaryColor3f;
        BufferChecks.checkFunctionAddress(glSecondaryColor3f);
        nglSecondaryColor3f(n, n2, n3, glSecondaryColor3f);
    }
    
    static native void nglSecondaryColor3f(final float p0, final float p1, final float p2, final long p3);
    
    public static void glSecondaryColor3d(final double n, final double n2, final double n3) {
        final long glSecondaryColor3d = GLContext.getCapabilities().glSecondaryColor3d;
        BufferChecks.checkFunctionAddress(glSecondaryColor3d);
        nglSecondaryColor3d(n, n2, n3, glSecondaryColor3d);
    }
    
    static native void nglSecondaryColor3d(final double p0, final double p1, final double p2, final long p3);
    
    public static void glSecondaryColor3ub(final byte b, final byte b2, final byte b3) {
        final long glSecondaryColor3ub = GLContext.getCapabilities().glSecondaryColor3ub;
        BufferChecks.checkFunctionAddress(glSecondaryColor3ub);
        nglSecondaryColor3ub(b, b2, b3, glSecondaryColor3ub);
    }
    
    static native void nglSecondaryColor3ub(final byte p0, final byte p1, final byte p2, final long p3);
    
    public static void glSecondaryColorPointer(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSecondaryColorPointer = capabilities.glSecondaryColorPointer;
        BufferChecks.checkFunctionAddress(glSecondaryColorPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        nglSecondaryColorPointer(n, 5130, n2, MemoryUtil.getAddress(doubleBuffer), glSecondaryColorPointer);
    }
    
    public static void glSecondaryColorPointer(final int n, final int n2, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSecondaryColorPointer = capabilities.glSecondaryColorPointer;
        BufferChecks.checkFunctionAddress(glSecondaryColorPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        nglSecondaryColorPointer(n, 5126, n2, MemoryUtil.getAddress(floatBuffer), glSecondaryColorPointer);
    }
    
    public static void glSecondaryColorPointer(final int n, final boolean b, final int n2, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSecondaryColorPointer = capabilities.glSecondaryColorPointer;
        BufferChecks.checkFunctionAddress(glSecondaryColorPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglSecondaryColorPointer(n, b ? 5121 : 5120, n2, MemoryUtil.getAddress(byteBuffer), glSecondaryColorPointer);
    }
    
    static native void nglSecondaryColorPointer(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glSecondaryColorPointer(final int n, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSecondaryColorPointer = capabilities.glSecondaryColorPointer;
        BufferChecks.checkFunctionAddress(glSecondaryColorPointer);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglSecondaryColorPointerBO(n, n2, n3, n4, glSecondaryColorPointer);
    }
    
    static native void nglSecondaryColorPointerBO(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glBlendFuncSeparate(final int n, final int n2, final int n3, final int n4) {
        final long glBlendFuncSeparate = GLContext.getCapabilities().glBlendFuncSeparate;
        BufferChecks.checkFunctionAddress(glBlendFuncSeparate);
        nglBlendFuncSeparate(n, n2, n3, n4, glBlendFuncSeparate);
    }
    
    static native void nglBlendFuncSeparate(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glWindowPos2f(final float n, final float n2) {
        final long glWindowPos2f = GLContext.getCapabilities().glWindowPos2f;
        BufferChecks.checkFunctionAddress(glWindowPos2f);
        nglWindowPos2f(n, n2, glWindowPos2f);
    }
    
    static native void nglWindowPos2f(final float p0, final float p1, final long p2);
    
    public static void glWindowPos2d(final double n, final double n2) {
        final long glWindowPos2d = GLContext.getCapabilities().glWindowPos2d;
        BufferChecks.checkFunctionAddress(glWindowPos2d);
        nglWindowPos2d(n, n2, glWindowPos2d);
    }
    
    static native void nglWindowPos2d(final double p0, final double p1, final long p2);
    
    public static void glWindowPos2i(final int n, final int n2) {
        final long glWindowPos2i = GLContext.getCapabilities().glWindowPos2i;
        BufferChecks.checkFunctionAddress(glWindowPos2i);
        nglWindowPos2i(n, n2, glWindowPos2i);
    }
    
    static native void nglWindowPos2i(final int p0, final int p1, final long p2);
    
    public static void glWindowPos3f(final float n, final float n2, final float n3) {
        final long glWindowPos3f = GLContext.getCapabilities().glWindowPos3f;
        BufferChecks.checkFunctionAddress(glWindowPos3f);
        nglWindowPos3f(n, n2, n3, glWindowPos3f);
    }
    
    static native void nglWindowPos3f(final float p0, final float p1, final float p2, final long p3);
    
    public static void glWindowPos3d(final double n, final double n2, final double n3) {
        final long glWindowPos3d = GLContext.getCapabilities().glWindowPos3d;
        BufferChecks.checkFunctionAddress(glWindowPos3d);
        nglWindowPos3d(n, n2, n3, glWindowPos3d);
    }
    
    static native void nglWindowPos3d(final double p0, final double p1, final double p2, final long p3);
    
    public static void glWindowPos3i(final int n, final int n2, final int n3) {
        final long glWindowPos3i = GLContext.getCapabilities().glWindowPos3i;
        BufferChecks.checkFunctionAddress(glWindowPos3i);
        nglWindowPos3i(n, n2, n3, glWindowPos3i);
    }
    
    static native void nglWindowPos3i(final int p0, final int p1, final int p2, final long p3);
}
