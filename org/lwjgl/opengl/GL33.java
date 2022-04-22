package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class GL33
{
    public static final int GL_SRC1_COLOR = 35065;
    public static final int GL_ONE_MINUS_SRC1_COLOR = 35066;
    public static final int GL_ONE_MINUS_SRC1_ALPHA = 35067;
    public static final int GL_MAX_DUAL_SOURCE_DRAW_BUFFERS = 35068;
    public static final int GL_ANY_SAMPLES_PASSED = 35887;
    public static final int GL_SAMPLER_BINDING = 35097;
    public static final int GL_RGB10_A2UI = 36975;
    public static final int GL_TEXTURE_SWIZZLE_R = 36418;
    public static final int GL_TEXTURE_SWIZZLE_G = 36419;
    public static final int GL_TEXTURE_SWIZZLE_B = 36420;
    public static final int GL_TEXTURE_SWIZZLE_A = 36421;
    public static final int GL_TEXTURE_SWIZZLE_RGBA = 36422;
    public static final int GL_TIME_ELAPSED = 35007;
    public static final int GL_TIMESTAMP = 36392;
    public static final int GL_VERTEX_ATTRIB_ARRAY_DIVISOR = 35070;
    public static final int GL_INT_2_10_10_10_REV = 36255;
    
    private GL33() {
    }
    
    public static void glBindFragDataLocationIndexed(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final long glBindFragDataLocationIndexed = GLContext.getCapabilities().glBindFragDataLocationIndexed;
        BufferChecks.checkFunctionAddress(glBindFragDataLocationIndexed);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        nglBindFragDataLocationIndexed(n, n2, n3, MemoryUtil.getAddress(byteBuffer), glBindFragDataLocationIndexed);
    }
    
    static native void nglBindFragDataLocationIndexed(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glBindFragDataLocationIndexed(final int n, final int n2, final int n3, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glBindFragDataLocationIndexed = capabilities.glBindFragDataLocationIndexed;
        BufferChecks.checkFunctionAddress(glBindFragDataLocationIndexed);
        nglBindFragDataLocationIndexed(n, n2, n3, APIUtil.getBufferNT(capabilities, charSequence), glBindFragDataLocationIndexed);
    }
    
    public static int glGetFragDataIndex(final int n, final ByteBuffer byteBuffer) {
        final long glGetFragDataIndex = GLContext.getCapabilities().glGetFragDataIndex;
        BufferChecks.checkFunctionAddress(glGetFragDataIndex);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        return nglGetFragDataIndex(n, MemoryUtil.getAddress(byteBuffer), glGetFragDataIndex);
    }
    
    static native int nglGetFragDataIndex(final int p0, final long p1, final long p2);
    
    public static int glGetFragDataIndex(final int n, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetFragDataIndex = capabilities.glGetFragDataIndex;
        BufferChecks.checkFunctionAddress(glGetFragDataIndex);
        return nglGetFragDataIndex(n, APIUtil.getBufferNT(capabilities, charSequence), glGetFragDataIndex);
    }
    
    public static void glGenSamplers(final IntBuffer intBuffer) {
        final long glGenSamplers = GLContext.getCapabilities().glGenSamplers;
        BufferChecks.checkFunctionAddress(glGenSamplers);
        BufferChecks.checkDirect(intBuffer);
        nglGenSamplers(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenSamplers);
    }
    
    static native void nglGenSamplers(final int p0, final long p1, final long p2);
    
    public static int glGenSamplers() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenSamplers = capabilities.glGenSamplers;
        BufferChecks.checkFunctionAddress(glGenSamplers);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenSamplers(1, MemoryUtil.getAddress(bufferInt), glGenSamplers);
        return bufferInt.get(0);
    }
    
    public static void glDeleteSamplers(final IntBuffer intBuffer) {
        final long glDeleteSamplers = GLContext.getCapabilities().glDeleteSamplers;
        BufferChecks.checkFunctionAddress(glDeleteSamplers);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteSamplers(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteSamplers);
    }
    
    static native void nglDeleteSamplers(final int p0, final long p1, final long p2);
    
    public static void glDeleteSamplers(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteSamplers = capabilities.glDeleteSamplers;
        BufferChecks.checkFunctionAddress(glDeleteSamplers);
        nglDeleteSamplers(1, APIUtil.getInt(capabilities, n), glDeleteSamplers);
    }
    
    public static boolean glIsSampler(final int n) {
        final long glIsSampler = GLContext.getCapabilities().glIsSampler;
        BufferChecks.checkFunctionAddress(glIsSampler);
        return nglIsSampler(n, glIsSampler);
    }
    
    static native boolean nglIsSampler(final int p0, final long p1);
    
    public static void glBindSampler(final int n, final int n2) {
        final long glBindSampler = GLContext.getCapabilities().glBindSampler;
        BufferChecks.checkFunctionAddress(glBindSampler);
        nglBindSampler(n, n2, glBindSampler);
    }
    
    static native void nglBindSampler(final int p0, final int p1, final long p2);
    
    public static void glSamplerParameteri(final int n, final int n2, final int n3) {
        final long glSamplerParameteri = GLContext.getCapabilities().glSamplerParameteri;
        BufferChecks.checkFunctionAddress(glSamplerParameteri);
        nglSamplerParameteri(n, n2, n3, glSamplerParameteri);
    }
    
    static native void nglSamplerParameteri(final int p0, final int p1, final int p2, final long p3);
    
    public static void glSamplerParameterf(final int n, final int n2, final float n3) {
        final long glSamplerParameterf = GLContext.getCapabilities().glSamplerParameterf;
        BufferChecks.checkFunctionAddress(glSamplerParameterf);
        nglSamplerParameterf(n, n2, n3, glSamplerParameterf);
    }
    
    static native void nglSamplerParameterf(final int p0, final int p1, final float p2, final long p3);
    
    public static void glSamplerParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glSamplerParameteriv = GLContext.getCapabilities().glSamplerParameteriv;
        BufferChecks.checkFunctionAddress(glSamplerParameteriv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglSamplerParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glSamplerParameteriv);
    }
    
    static native void nglSamplerParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glSamplerParameter(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glSamplerParameterfv = GLContext.getCapabilities().glSamplerParameterfv;
        BufferChecks.checkFunctionAddress(glSamplerParameterfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglSamplerParameterfv(n, n2, MemoryUtil.getAddress(floatBuffer), glSamplerParameterfv);
    }
    
    static native void nglSamplerParameterfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glSamplerParameterI(final int n, final int n2, final IntBuffer intBuffer) {
        final long glSamplerParameterIiv = GLContext.getCapabilities().glSamplerParameterIiv;
        BufferChecks.checkFunctionAddress(glSamplerParameterIiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglSamplerParameterIiv(n, n2, MemoryUtil.getAddress(intBuffer), glSamplerParameterIiv);
    }
    
    static native void nglSamplerParameterIiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glSamplerParameterIu(final int n, final int n2, final IntBuffer intBuffer) {
        final long glSamplerParameterIuiv = GLContext.getCapabilities().glSamplerParameterIuiv;
        BufferChecks.checkFunctionAddress(glSamplerParameterIuiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglSamplerParameterIuiv(n, n2, MemoryUtil.getAddress(intBuffer), glSamplerParameterIuiv);
    }
    
    static native void nglSamplerParameterIuiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetSamplerParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetSamplerParameteriv = GLContext.getCapabilities().glGetSamplerParameteriv;
        BufferChecks.checkFunctionAddress(glGetSamplerParameteriv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetSamplerParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glGetSamplerParameteriv);
    }
    
    static native void nglGetSamplerParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetSamplerParameteri(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSamplerParameteriv = capabilities.glGetSamplerParameteriv;
        BufferChecks.checkFunctionAddress(glGetSamplerParameteriv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetSamplerParameteriv(n, n2, MemoryUtil.getAddress(bufferInt), glGetSamplerParameteriv);
        return bufferInt.get(0);
    }
    
    public static void glGetSamplerParameter(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetSamplerParameterfv = GLContext.getCapabilities().glGetSamplerParameterfv;
        BufferChecks.checkFunctionAddress(glGetSamplerParameterfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetSamplerParameterfv(n, n2, MemoryUtil.getAddress(floatBuffer), glGetSamplerParameterfv);
    }
    
    static native void nglGetSamplerParameterfv(final int p0, final int p1, final long p2, final long p3);
    
    public static float glGetSamplerParameterf(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSamplerParameterfv = capabilities.glGetSamplerParameterfv;
        BufferChecks.checkFunctionAddress(glGetSamplerParameterfv);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetSamplerParameterfv(n, n2, MemoryUtil.getAddress(bufferFloat), glGetSamplerParameterfv);
        return bufferFloat.get(0);
    }
    
    public static void glGetSamplerParameterI(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetSamplerParameterIiv = GLContext.getCapabilities().glGetSamplerParameterIiv;
        BufferChecks.checkFunctionAddress(glGetSamplerParameterIiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetSamplerParameterIiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetSamplerParameterIiv);
    }
    
    static native void nglGetSamplerParameterIiv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetSamplerParameterIi(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSamplerParameterIiv = capabilities.glGetSamplerParameterIiv;
        BufferChecks.checkFunctionAddress(glGetSamplerParameterIiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetSamplerParameterIiv(n, n2, MemoryUtil.getAddress(bufferInt), glGetSamplerParameterIiv);
        return bufferInt.get(0);
    }
    
    public static void glGetSamplerParameterIu(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetSamplerParameterIuiv = GLContext.getCapabilities().glGetSamplerParameterIuiv;
        BufferChecks.checkFunctionAddress(glGetSamplerParameterIuiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetSamplerParameterIuiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetSamplerParameterIuiv);
    }
    
    static native void nglGetSamplerParameterIuiv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetSamplerParameterIui(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSamplerParameterIuiv = capabilities.glGetSamplerParameterIuiv;
        BufferChecks.checkFunctionAddress(glGetSamplerParameterIuiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetSamplerParameterIuiv(n, n2, MemoryUtil.getAddress(bufferInt), glGetSamplerParameterIuiv);
        return bufferInt.get(0);
    }
    
    public static void glQueryCounter(final int n, final int n2) {
        final long glQueryCounter = GLContext.getCapabilities().glQueryCounter;
        BufferChecks.checkFunctionAddress(glQueryCounter);
        nglQueryCounter(n, n2, glQueryCounter);
    }
    
    static native void nglQueryCounter(final int p0, final int p1, final long p2);
    
    public static void glGetQueryObject(final int n, final int n2, final LongBuffer longBuffer) {
        final long glGetQueryObjecti64v = GLContext.getCapabilities().glGetQueryObjecti64v;
        BufferChecks.checkFunctionAddress(glGetQueryObjecti64v);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglGetQueryObjecti64v(n, n2, MemoryUtil.getAddress(longBuffer), glGetQueryObjecti64v);
    }
    
    static native void nglGetQueryObjecti64v(final int p0, final int p1, final long p2, final long p3);
    
    @Deprecated
    public static long glGetQueryObject(final int n, final int n2) {
        return glGetQueryObjecti64(n, n2);
    }
    
    public static long glGetQueryObjecti64(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetQueryObjecti64v = capabilities.glGetQueryObjecti64v;
        BufferChecks.checkFunctionAddress(glGetQueryObjecti64v);
        final LongBuffer bufferLong = APIUtil.getBufferLong(capabilities);
        nglGetQueryObjecti64v(n, n2, MemoryUtil.getAddress(bufferLong), glGetQueryObjecti64v);
        return bufferLong.get(0);
    }
    
    public static void glGetQueryObjectu(final int n, final int n2, final LongBuffer longBuffer) {
        final long glGetQueryObjectui64v = GLContext.getCapabilities().glGetQueryObjectui64v;
        BufferChecks.checkFunctionAddress(glGetQueryObjectui64v);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglGetQueryObjectui64v(n, n2, MemoryUtil.getAddress(longBuffer), glGetQueryObjectui64v);
    }
    
    static native void nglGetQueryObjectui64v(final int p0, final int p1, final long p2, final long p3);
    
    @Deprecated
    public static long glGetQueryObjectu(final int n, final int n2) {
        return glGetQueryObjectui64(n, n2);
    }
    
    public static long glGetQueryObjectui64(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetQueryObjectui64v = capabilities.glGetQueryObjectui64v;
        BufferChecks.checkFunctionAddress(glGetQueryObjectui64v);
        final LongBuffer bufferLong = APIUtil.getBufferLong(capabilities);
        nglGetQueryObjectui64v(n, n2, MemoryUtil.getAddress(bufferLong), glGetQueryObjectui64v);
        return bufferLong.get(0);
    }
    
    public static void glVertexAttribDivisor(final int n, final int n2) {
        final long glVertexAttribDivisor = GLContext.getCapabilities().glVertexAttribDivisor;
        BufferChecks.checkFunctionAddress(glVertexAttribDivisor);
        nglVertexAttribDivisor(n, n2, glVertexAttribDivisor);
    }
    
    static native void nglVertexAttribDivisor(final int p0, final int p1, final long p2);
    
    public static void glVertexP2ui(final int n, final int n2) {
        final long glVertexP2ui = GLContext.getCapabilities().glVertexP2ui;
        BufferChecks.checkFunctionAddress(glVertexP2ui);
        nglVertexP2ui(n, n2, glVertexP2ui);
    }
    
    static native void nglVertexP2ui(final int p0, final int p1, final long p2);
    
    public static void glVertexP3ui(final int n, final int n2) {
        final long glVertexP3ui = GLContext.getCapabilities().glVertexP3ui;
        BufferChecks.checkFunctionAddress(glVertexP3ui);
        nglVertexP3ui(n, n2, glVertexP3ui);
    }
    
    static native void nglVertexP3ui(final int p0, final int p1, final long p2);
    
    public static void glVertexP4ui(final int n, final int n2) {
        final long glVertexP4ui = GLContext.getCapabilities().glVertexP4ui;
        BufferChecks.checkFunctionAddress(glVertexP4ui);
        nglVertexP4ui(n, n2, glVertexP4ui);
    }
    
    static native void nglVertexP4ui(final int p0, final int p1, final long p2);
    
    public static void glVertexP2u(final int n, final IntBuffer intBuffer) {
        final long glVertexP2uiv = GLContext.getCapabilities().glVertexP2uiv;
        BufferChecks.checkFunctionAddress(glVertexP2uiv);
        BufferChecks.checkBuffer(intBuffer, 2);
        nglVertexP2uiv(n, MemoryUtil.getAddress(intBuffer), glVertexP2uiv);
    }
    
    static native void nglVertexP2uiv(final int p0, final long p1, final long p2);
    
    public static void glVertexP3u(final int n, final IntBuffer intBuffer) {
        final long glVertexP3uiv = GLContext.getCapabilities().glVertexP3uiv;
        BufferChecks.checkFunctionAddress(glVertexP3uiv);
        BufferChecks.checkBuffer(intBuffer, 3);
        nglVertexP3uiv(n, MemoryUtil.getAddress(intBuffer), glVertexP3uiv);
    }
    
    static native void nglVertexP3uiv(final int p0, final long p1, final long p2);
    
    public static void glVertexP4u(final int n, final IntBuffer intBuffer) {
        final long glVertexP4uiv = GLContext.getCapabilities().glVertexP4uiv;
        BufferChecks.checkFunctionAddress(glVertexP4uiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglVertexP4uiv(n, MemoryUtil.getAddress(intBuffer), glVertexP4uiv);
    }
    
    static native void nglVertexP4uiv(final int p0, final long p1, final long p2);
    
    public static void glTexCoordP1ui(final int n, final int n2) {
        final long glTexCoordP1ui = GLContext.getCapabilities().glTexCoordP1ui;
        BufferChecks.checkFunctionAddress(glTexCoordP1ui);
        nglTexCoordP1ui(n, n2, glTexCoordP1ui);
    }
    
    static native void nglTexCoordP1ui(final int p0, final int p1, final long p2);
    
    public static void glTexCoordP2ui(final int n, final int n2) {
        final long glTexCoordP2ui = GLContext.getCapabilities().glTexCoordP2ui;
        BufferChecks.checkFunctionAddress(glTexCoordP2ui);
        nglTexCoordP2ui(n, n2, glTexCoordP2ui);
    }
    
    static native void nglTexCoordP2ui(final int p0, final int p1, final long p2);
    
    public static void glTexCoordP3ui(final int n, final int n2) {
        final long glTexCoordP3ui = GLContext.getCapabilities().glTexCoordP3ui;
        BufferChecks.checkFunctionAddress(glTexCoordP3ui);
        nglTexCoordP3ui(n, n2, glTexCoordP3ui);
    }
    
    static native void nglTexCoordP3ui(final int p0, final int p1, final long p2);
    
    public static void glTexCoordP4ui(final int n, final int n2) {
        final long glTexCoordP4ui = GLContext.getCapabilities().glTexCoordP4ui;
        BufferChecks.checkFunctionAddress(glTexCoordP4ui);
        nglTexCoordP4ui(n, n2, glTexCoordP4ui);
    }
    
    static native void nglTexCoordP4ui(final int p0, final int p1, final long p2);
    
    public static void glTexCoordP1u(final int n, final IntBuffer intBuffer) {
        final long glTexCoordP1uiv = GLContext.getCapabilities().glTexCoordP1uiv;
        BufferChecks.checkFunctionAddress(glTexCoordP1uiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglTexCoordP1uiv(n, MemoryUtil.getAddress(intBuffer), glTexCoordP1uiv);
    }
    
    static native void nglTexCoordP1uiv(final int p0, final long p1, final long p2);
    
    public static void glTexCoordP2u(final int n, final IntBuffer intBuffer) {
        final long glTexCoordP2uiv = GLContext.getCapabilities().glTexCoordP2uiv;
        BufferChecks.checkFunctionAddress(glTexCoordP2uiv);
        BufferChecks.checkBuffer(intBuffer, 2);
        nglTexCoordP2uiv(n, MemoryUtil.getAddress(intBuffer), glTexCoordP2uiv);
    }
    
    static native void nglTexCoordP2uiv(final int p0, final long p1, final long p2);
    
    public static void glTexCoordP3u(final int n, final IntBuffer intBuffer) {
        final long glTexCoordP3uiv = GLContext.getCapabilities().glTexCoordP3uiv;
        BufferChecks.checkFunctionAddress(glTexCoordP3uiv);
        BufferChecks.checkBuffer(intBuffer, 3);
        nglTexCoordP3uiv(n, MemoryUtil.getAddress(intBuffer), glTexCoordP3uiv);
    }
    
    static native void nglTexCoordP3uiv(final int p0, final long p1, final long p2);
    
    public static void glTexCoordP4u(final int n, final IntBuffer intBuffer) {
        final long glTexCoordP4uiv = GLContext.getCapabilities().glTexCoordP4uiv;
        BufferChecks.checkFunctionAddress(glTexCoordP4uiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglTexCoordP4uiv(n, MemoryUtil.getAddress(intBuffer), glTexCoordP4uiv);
    }
    
    static native void nglTexCoordP4uiv(final int p0, final long p1, final long p2);
    
    public static void glMultiTexCoordP1ui(final int n, final int n2, final int n3) {
        final long glMultiTexCoordP1ui = GLContext.getCapabilities().glMultiTexCoordP1ui;
        BufferChecks.checkFunctionAddress(glMultiTexCoordP1ui);
        nglMultiTexCoordP1ui(n, n2, n3, glMultiTexCoordP1ui);
    }
    
    static native void nglMultiTexCoordP1ui(final int p0, final int p1, final int p2, final long p3);
    
    public static void glMultiTexCoordP2ui(final int n, final int n2, final int n3) {
        final long glMultiTexCoordP2ui = GLContext.getCapabilities().glMultiTexCoordP2ui;
        BufferChecks.checkFunctionAddress(glMultiTexCoordP2ui);
        nglMultiTexCoordP2ui(n, n2, n3, glMultiTexCoordP2ui);
    }
    
    static native void nglMultiTexCoordP2ui(final int p0, final int p1, final int p2, final long p3);
    
    public static void glMultiTexCoordP3ui(final int n, final int n2, final int n3) {
        final long glMultiTexCoordP3ui = GLContext.getCapabilities().glMultiTexCoordP3ui;
        BufferChecks.checkFunctionAddress(glMultiTexCoordP3ui);
        nglMultiTexCoordP3ui(n, n2, n3, glMultiTexCoordP3ui);
    }
    
    static native void nglMultiTexCoordP3ui(final int p0, final int p1, final int p2, final long p3);
    
    public static void glMultiTexCoordP4ui(final int n, final int n2, final int n3) {
        final long glMultiTexCoordP4ui = GLContext.getCapabilities().glMultiTexCoordP4ui;
        BufferChecks.checkFunctionAddress(glMultiTexCoordP4ui);
        nglMultiTexCoordP4ui(n, n2, n3, glMultiTexCoordP4ui);
    }
    
    static native void nglMultiTexCoordP4ui(final int p0, final int p1, final int p2, final long p3);
    
    public static void glMultiTexCoordP1u(final int n, final int n2, final IntBuffer intBuffer) {
        final long glMultiTexCoordP1uiv = GLContext.getCapabilities().glMultiTexCoordP1uiv;
        BufferChecks.checkFunctionAddress(glMultiTexCoordP1uiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglMultiTexCoordP1uiv(n, n2, MemoryUtil.getAddress(intBuffer), glMultiTexCoordP1uiv);
    }
    
    static native void nglMultiTexCoordP1uiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glMultiTexCoordP2u(final int n, final int n2, final IntBuffer intBuffer) {
        final long glMultiTexCoordP2uiv = GLContext.getCapabilities().glMultiTexCoordP2uiv;
        BufferChecks.checkFunctionAddress(glMultiTexCoordP2uiv);
        BufferChecks.checkBuffer(intBuffer, 2);
        nglMultiTexCoordP2uiv(n, n2, MemoryUtil.getAddress(intBuffer), glMultiTexCoordP2uiv);
    }
    
    static native void nglMultiTexCoordP2uiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glMultiTexCoordP3u(final int n, final int n2, final IntBuffer intBuffer) {
        final long glMultiTexCoordP3uiv = GLContext.getCapabilities().glMultiTexCoordP3uiv;
        BufferChecks.checkFunctionAddress(glMultiTexCoordP3uiv);
        BufferChecks.checkBuffer(intBuffer, 3);
        nglMultiTexCoordP3uiv(n, n2, MemoryUtil.getAddress(intBuffer), glMultiTexCoordP3uiv);
    }
    
    static native void nglMultiTexCoordP3uiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glMultiTexCoordP4u(final int n, final int n2, final IntBuffer intBuffer) {
        final long glMultiTexCoordP4uiv = GLContext.getCapabilities().glMultiTexCoordP4uiv;
        BufferChecks.checkFunctionAddress(glMultiTexCoordP4uiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglMultiTexCoordP4uiv(n, n2, MemoryUtil.getAddress(intBuffer), glMultiTexCoordP4uiv);
    }
    
    static native void nglMultiTexCoordP4uiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glNormalP3ui(final int n, final int n2) {
        final long glNormalP3ui = GLContext.getCapabilities().glNormalP3ui;
        BufferChecks.checkFunctionAddress(glNormalP3ui);
        nglNormalP3ui(n, n2, glNormalP3ui);
    }
    
    static native void nglNormalP3ui(final int p0, final int p1, final long p2);
    
    public static void glNormalP3u(final int n, final IntBuffer intBuffer) {
        final long glNormalP3uiv = GLContext.getCapabilities().glNormalP3uiv;
        BufferChecks.checkFunctionAddress(glNormalP3uiv);
        BufferChecks.checkBuffer(intBuffer, 3);
        nglNormalP3uiv(n, MemoryUtil.getAddress(intBuffer), glNormalP3uiv);
    }
    
    static native void nglNormalP3uiv(final int p0, final long p1, final long p2);
    
    public static void glColorP3ui(final int n, final int n2) {
        final long glColorP3ui = GLContext.getCapabilities().glColorP3ui;
        BufferChecks.checkFunctionAddress(glColorP3ui);
        nglColorP3ui(n, n2, glColorP3ui);
    }
    
    static native void nglColorP3ui(final int p0, final int p1, final long p2);
    
    public static void glColorP4ui(final int n, final int n2) {
        final long glColorP4ui = GLContext.getCapabilities().glColorP4ui;
        BufferChecks.checkFunctionAddress(glColorP4ui);
        nglColorP4ui(n, n2, glColorP4ui);
    }
    
    static native void nglColorP4ui(final int p0, final int p1, final long p2);
    
    public static void glColorP3u(final int n, final IntBuffer intBuffer) {
        final long glColorP3uiv = GLContext.getCapabilities().glColorP3uiv;
        BufferChecks.checkFunctionAddress(glColorP3uiv);
        BufferChecks.checkBuffer(intBuffer, 3);
        nglColorP3uiv(n, MemoryUtil.getAddress(intBuffer), glColorP3uiv);
    }
    
    static native void nglColorP3uiv(final int p0, final long p1, final long p2);
    
    public static void glColorP4u(final int n, final IntBuffer intBuffer) {
        final long glColorP4uiv = GLContext.getCapabilities().glColorP4uiv;
        BufferChecks.checkFunctionAddress(glColorP4uiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglColorP4uiv(n, MemoryUtil.getAddress(intBuffer), glColorP4uiv);
    }
    
    static native void nglColorP4uiv(final int p0, final long p1, final long p2);
    
    public static void glSecondaryColorP3ui(final int n, final int n2) {
        final long glSecondaryColorP3ui = GLContext.getCapabilities().glSecondaryColorP3ui;
        BufferChecks.checkFunctionAddress(glSecondaryColorP3ui);
        nglSecondaryColorP3ui(n, n2, glSecondaryColorP3ui);
    }
    
    static native void nglSecondaryColorP3ui(final int p0, final int p1, final long p2);
    
    public static void glSecondaryColorP3u(final int n, final IntBuffer intBuffer) {
        final long glSecondaryColorP3uiv = GLContext.getCapabilities().glSecondaryColorP3uiv;
        BufferChecks.checkFunctionAddress(glSecondaryColorP3uiv);
        BufferChecks.checkBuffer(intBuffer, 3);
        nglSecondaryColorP3uiv(n, MemoryUtil.getAddress(intBuffer), glSecondaryColorP3uiv);
    }
    
    static native void nglSecondaryColorP3uiv(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribP1ui(final int n, final int n2, final boolean b, final int n3) {
        final long glVertexAttribP1ui = GLContext.getCapabilities().glVertexAttribP1ui;
        BufferChecks.checkFunctionAddress(glVertexAttribP1ui);
        nglVertexAttribP1ui(n, n2, b, n3, glVertexAttribP1ui);
    }
    
    static native void nglVertexAttribP1ui(final int p0, final int p1, final boolean p2, final int p3, final long p4);
    
    public static void glVertexAttribP2ui(final int n, final int n2, final boolean b, final int n3) {
        final long glVertexAttribP2ui = GLContext.getCapabilities().glVertexAttribP2ui;
        BufferChecks.checkFunctionAddress(glVertexAttribP2ui);
        nglVertexAttribP2ui(n, n2, b, n3, glVertexAttribP2ui);
    }
    
    static native void nglVertexAttribP2ui(final int p0, final int p1, final boolean p2, final int p3, final long p4);
    
    public static void glVertexAttribP3ui(final int n, final int n2, final boolean b, final int n3) {
        final long glVertexAttribP3ui = GLContext.getCapabilities().glVertexAttribP3ui;
        BufferChecks.checkFunctionAddress(glVertexAttribP3ui);
        nglVertexAttribP3ui(n, n2, b, n3, glVertexAttribP3ui);
    }
    
    static native void nglVertexAttribP3ui(final int p0, final int p1, final boolean p2, final int p3, final long p4);
    
    public static void glVertexAttribP4ui(final int n, final int n2, final boolean b, final int n3) {
        final long glVertexAttribP4ui = GLContext.getCapabilities().glVertexAttribP4ui;
        BufferChecks.checkFunctionAddress(glVertexAttribP4ui);
        nglVertexAttribP4ui(n, n2, b, n3, glVertexAttribP4ui);
    }
    
    static native void nglVertexAttribP4ui(final int p0, final int p1, final boolean p2, final int p3, final long p4);
    
    public static void glVertexAttribP1u(final int n, final int n2, final boolean b, final IntBuffer intBuffer) {
        final long glVertexAttribP1uiv = GLContext.getCapabilities().glVertexAttribP1uiv;
        BufferChecks.checkFunctionAddress(glVertexAttribP1uiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglVertexAttribP1uiv(n, n2, b, MemoryUtil.getAddress(intBuffer), glVertexAttribP1uiv);
    }
    
    static native void nglVertexAttribP1uiv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glVertexAttribP2u(final int n, final int n2, final boolean b, final IntBuffer intBuffer) {
        final long glVertexAttribP2uiv = GLContext.getCapabilities().glVertexAttribP2uiv;
        BufferChecks.checkFunctionAddress(glVertexAttribP2uiv);
        BufferChecks.checkBuffer(intBuffer, 2);
        nglVertexAttribP2uiv(n, n2, b, MemoryUtil.getAddress(intBuffer), glVertexAttribP2uiv);
    }
    
    static native void nglVertexAttribP2uiv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glVertexAttribP3u(final int n, final int n2, final boolean b, final IntBuffer intBuffer) {
        final long glVertexAttribP3uiv = GLContext.getCapabilities().glVertexAttribP3uiv;
        BufferChecks.checkFunctionAddress(glVertexAttribP3uiv);
        BufferChecks.checkBuffer(intBuffer, 3);
        nglVertexAttribP3uiv(n, n2, b, MemoryUtil.getAddress(intBuffer), glVertexAttribP3uiv);
    }
    
    static native void nglVertexAttribP3uiv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glVertexAttribP4u(final int n, final int n2, final boolean b, final IntBuffer intBuffer) {
        final long glVertexAttribP4uiv = GLContext.getCapabilities().glVertexAttribP4uiv;
        BufferChecks.checkFunctionAddress(glVertexAttribP4uiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglVertexAttribP4uiv(n, n2, b, MemoryUtil.getAddress(intBuffer), glVertexAttribP4uiv);
    }
    
    static native void nglVertexAttribP4uiv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
}
