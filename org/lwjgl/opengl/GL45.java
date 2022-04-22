package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class GL45
{
    public static final int GL_NEGATIVE_ONE_TO_ONE = 37726;
    public static final int GL_ZERO_TO_ONE = 37727;
    public static final int GL_CLIP_ORIGIN = 37724;
    public static final int GL_CLIP_DEPTH_MODE = 37725;
    public static final int GL_QUERY_WAIT_INVERTED = 36375;
    public static final int GL_QUERY_NO_WAIT_INVERTED = 36376;
    public static final int GL_QUERY_BY_REGION_WAIT_INVERTED = 36377;
    public static final int GL_QUERY_BY_REGION_NO_WAIT_INVERTED = 36378;
    public static final int GL_MAX_CULL_DISTANCES = 33529;
    public static final int GL_MAX_COMBINED_CLIP_AND_CULL_DISTANCES = 33530;
    public static final int GL_TEXTURE_TARGET = 4102;
    public static final int GL_QUERY_TARGET = 33514;
    public static final int GL_TEXTURE_BINDING = 33515;
    public static final int GL_CONTEXT_RELEASE_BEHAVIOR = 33531;
    public static final int GL_CONTEXT_RELEASE_BEHAVIOR_FLUSH = 33532;
    public static final int GL_GUILTY_CONTEXT_RESET = 33363;
    public static final int GL_INNOCENT_CONTEXT_RESET = 33364;
    public static final int GL_UNKNOWN_CONTEXT_RESET = 33365;
    public static final int GL_CONTEXT_ROBUST_ACCESS = 37107;
    public static final int GL_RESET_NOTIFICATION_STRATEGY = 33366;
    public static final int GL_LOSE_CONTEXT_ON_RESET = 33362;
    public static final int GL_NO_RESET_NOTIFICATION = 33377;
    public static final int GL_CONTEXT_LOST = 1287;
    
    private GL45() {
    }
    
    public static void glClipControl(final int n, final int n2) {
        final long glClipControl = GLContext.getCapabilities().glClipControl;
        BufferChecks.checkFunctionAddress(glClipControl);
        nglClipControl(n, n2, glClipControl);
    }
    
    static native void nglClipControl(final int p0, final int p1, final long p2);
    
    public static void glCreateTransformFeedbacks(final IntBuffer intBuffer) {
        final long glCreateTransformFeedbacks = GLContext.getCapabilities().glCreateTransformFeedbacks;
        BufferChecks.checkFunctionAddress(glCreateTransformFeedbacks);
        BufferChecks.checkDirect(intBuffer);
        nglCreateTransformFeedbacks(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glCreateTransformFeedbacks);
    }
    
    static native void nglCreateTransformFeedbacks(final int p0, final long p1, final long p2);
    
    public static int glCreateTransformFeedbacks() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCreateTransformFeedbacks = capabilities.glCreateTransformFeedbacks;
        BufferChecks.checkFunctionAddress(glCreateTransformFeedbacks);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglCreateTransformFeedbacks(1, MemoryUtil.getAddress(bufferInt), glCreateTransformFeedbacks);
        return bufferInt.get(0);
    }
    
    public static void glTransformFeedbackBufferBase(final int n, final int n2, final int n3) {
        final long glTransformFeedbackBufferBase = GLContext.getCapabilities().glTransformFeedbackBufferBase;
        BufferChecks.checkFunctionAddress(glTransformFeedbackBufferBase);
        nglTransformFeedbackBufferBase(n, n2, n3, glTransformFeedbackBufferBase);
    }
    
    static native void nglTransformFeedbackBufferBase(final int p0, final int p1, final int p2, final long p3);
    
    public static void glTransformFeedbackBufferRange(final int n, final int n2, final int n3, final long n4, final long n5) {
        final long glTransformFeedbackBufferRange = GLContext.getCapabilities().glTransformFeedbackBufferRange;
        BufferChecks.checkFunctionAddress(glTransformFeedbackBufferRange);
        nglTransformFeedbackBufferRange(n, n2, n3, n4, n5, glTransformFeedbackBufferRange);
    }
    
    static native void nglTransformFeedbackBufferRange(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5);
    
    public static void glGetTransformFeedback(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetTransformFeedbackiv = GLContext.getCapabilities().glGetTransformFeedbackiv;
        BufferChecks.checkFunctionAddress(glGetTransformFeedbackiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetTransformFeedbackiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetTransformFeedbackiv);
    }
    
    static native void nglGetTransformFeedbackiv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetTransformFeedbacki(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTransformFeedbackiv = capabilities.glGetTransformFeedbackiv;
        BufferChecks.checkFunctionAddress(glGetTransformFeedbackiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTransformFeedbackiv(n, n2, MemoryUtil.getAddress(bufferInt), glGetTransformFeedbackiv);
        return bufferInt.get(0);
    }
    
    public static void glGetTransformFeedback(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetTransformFeedbacki_v = GLContext.getCapabilities().glGetTransformFeedbacki_v;
        BufferChecks.checkFunctionAddress(glGetTransformFeedbacki_v);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetTransformFeedbacki_v(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetTransformFeedbacki_v);
    }
    
    static native void nglGetTransformFeedbacki_v(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetTransformFeedbacki(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTransformFeedbacki_v = capabilities.glGetTransformFeedbacki_v;
        BufferChecks.checkFunctionAddress(glGetTransformFeedbacki_v);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTransformFeedbacki_v(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetTransformFeedbacki_v);
        return bufferInt.get(0);
    }
    
    public static void glGetTransformFeedback(final int n, final int n2, final int n3, final LongBuffer longBuffer) {
        final long glGetTransformFeedbacki64_v = GLContext.getCapabilities().glGetTransformFeedbacki64_v;
        BufferChecks.checkFunctionAddress(glGetTransformFeedbacki64_v);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglGetTransformFeedbacki64_v(n, n2, n3, MemoryUtil.getAddress(longBuffer), glGetTransformFeedbacki64_v);
    }
    
    static native void nglGetTransformFeedbacki64_v(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static long glGetTransformFeedbacki64(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTransformFeedbacki64_v = capabilities.glGetTransformFeedbacki64_v;
        BufferChecks.checkFunctionAddress(glGetTransformFeedbacki64_v);
        final LongBuffer bufferLong = APIUtil.getBufferLong(capabilities);
        nglGetTransformFeedbacki64_v(n, n2, n3, MemoryUtil.getAddress(bufferLong), glGetTransformFeedbacki64_v);
        return bufferLong.get(0);
    }
    
    public static void glCreateBuffers(final IntBuffer intBuffer) {
        final long glCreateBuffers = GLContext.getCapabilities().glCreateBuffers;
        BufferChecks.checkFunctionAddress(glCreateBuffers);
        BufferChecks.checkDirect(intBuffer);
        nglCreateBuffers(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glCreateBuffers);
    }
    
    static native void nglCreateBuffers(final int p0, final long p1, final long p2);
    
    public static int glCreateBuffers() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCreateBuffers = capabilities.glCreateBuffers;
        BufferChecks.checkFunctionAddress(glCreateBuffers);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglCreateBuffers(1, MemoryUtil.getAddress(bufferInt), glCreateBuffers);
        return bufferInt.get(0);
    }
    
    public static void glNamedBufferStorage(final int n, final ByteBuffer byteBuffer, final int n2) {
        final long glNamedBufferStorage = GLContext.getCapabilities().glNamedBufferStorage;
        BufferChecks.checkFunctionAddress(glNamedBufferStorage);
        BufferChecks.checkDirect(byteBuffer);
        nglNamedBufferStorage(n, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), n2, glNamedBufferStorage);
    }
    
    public static void glNamedBufferStorage(final int n, final DoubleBuffer doubleBuffer, final int n2) {
        final long glNamedBufferStorage = GLContext.getCapabilities().glNamedBufferStorage;
        BufferChecks.checkFunctionAddress(glNamedBufferStorage);
        BufferChecks.checkDirect(doubleBuffer);
        nglNamedBufferStorage(n, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), n2, glNamedBufferStorage);
    }
    
    public static void glNamedBufferStorage(final int n, final FloatBuffer floatBuffer, final int n2) {
        final long glNamedBufferStorage = GLContext.getCapabilities().glNamedBufferStorage;
        BufferChecks.checkFunctionAddress(glNamedBufferStorage);
        BufferChecks.checkDirect(floatBuffer);
        nglNamedBufferStorage(n, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), n2, glNamedBufferStorage);
    }
    
    public static void glNamedBufferStorage(final int n, final IntBuffer intBuffer, final int n2) {
        final long glNamedBufferStorage = GLContext.getCapabilities().glNamedBufferStorage;
        BufferChecks.checkFunctionAddress(glNamedBufferStorage);
        BufferChecks.checkDirect(intBuffer);
        nglNamedBufferStorage(n, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), n2, glNamedBufferStorage);
    }
    
    public static void glNamedBufferStorage(final int n, final ShortBuffer shortBuffer, final int n2) {
        final long glNamedBufferStorage = GLContext.getCapabilities().glNamedBufferStorage;
        BufferChecks.checkFunctionAddress(glNamedBufferStorage);
        BufferChecks.checkDirect(shortBuffer);
        nglNamedBufferStorage(n, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), n2, glNamedBufferStorage);
    }
    
    public static void glNamedBufferStorage(final int n, final LongBuffer longBuffer, final int n2) {
        final long glNamedBufferStorage = GLContext.getCapabilities().glNamedBufferStorage;
        BufferChecks.checkFunctionAddress(glNamedBufferStorage);
        BufferChecks.checkDirect(longBuffer);
        nglNamedBufferStorage(n, longBuffer.remaining() << 3, MemoryUtil.getAddress(longBuffer), n2, glNamedBufferStorage);
    }
    
    static native void nglNamedBufferStorage(final int p0, final long p1, final long p2, final int p3, final long p4);
    
    public static void glNamedBufferStorage(final int n, final long n2, final int n3) {
        final long glNamedBufferStorage = GLContext.getCapabilities().glNamedBufferStorage;
        BufferChecks.checkFunctionAddress(glNamedBufferStorage);
        nglNamedBufferStorage(n, n2, 0L, n3, glNamedBufferStorage);
    }
    
    public static void glNamedBufferData(final int n, final long n2, final int n3) {
        final long glNamedBufferData = GLContext.getCapabilities().glNamedBufferData;
        BufferChecks.checkFunctionAddress(glNamedBufferData);
        nglNamedBufferData(n, n2, 0L, n3, glNamedBufferData);
    }
    
    public static void glNamedBufferData(final int n, final ByteBuffer byteBuffer, final int n2) {
        final long glNamedBufferData = GLContext.getCapabilities().glNamedBufferData;
        BufferChecks.checkFunctionAddress(glNamedBufferData);
        BufferChecks.checkDirect(byteBuffer);
        nglNamedBufferData(n, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), n2, glNamedBufferData);
    }
    
    public static void glNamedBufferData(final int n, final DoubleBuffer doubleBuffer, final int n2) {
        final long glNamedBufferData = GLContext.getCapabilities().glNamedBufferData;
        BufferChecks.checkFunctionAddress(glNamedBufferData);
        BufferChecks.checkDirect(doubleBuffer);
        nglNamedBufferData(n, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), n2, glNamedBufferData);
    }
    
    public static void glNamedBufferData(final int n, final FloatBuffer floatBuffer, final int n2) {
        final long glNamedBufferData = GLContext.getCapabilities().glNamedBufferData;
        BufferChecks.checkFunctionAddress(glNamedBufferData);
        BufferChecks.checkDirect(floatBuffer);
        nglNamedBufferData(n, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), n2, glNamedBufferData);
    }
    
    public static void glNamedBufferData(final int n, final IntBuffer intBuffer, final int n2) {
        final long glNamedBufferData = GLContext.getCapabilities().glNamedBufferData;
        BufferChecks.checkFunctionAddress(glNamedBufferData);
        BufferChecks.checkDirect(intBuffer);
        nglNamedBufferData(n, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), n2, glNamedBufferData);
    }
    
    public static void glNamedBufferData(final int n, final ShortBuffer shortBuffer, final int n2) {
        final long glNamedBufferData = GLContext.getCapabilities().glNamedBufferData;
        BufferChecks.checkFunctionAddress(glNamedBufferData);
        BufferChecks.checkDirect(shortBuffer);
        nglNamedBufferData(n, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), n2, glNamedBufferData);
    }
    
    static native void nglNamedBufferData(final int p0, final long p1, final long p2, final int p3, final long p4);
    
    public static void glNamedBufferSubData(final int n, final long n2, final ByteBuffer byteBuffer) {
        final long glNamedBufferSubData = GLContext.getCapabilities().glNamedBufferSubData;
        BufferChecks.checkFunctionAddress(glNamedBufferSubData);
        BufferChecks.checkDirect(byteBuffer);
        nglNamedBufferSubData(n, n2, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glNamedBufferSubData);
    }
    
    public static void glNamedBufferSubData(final int n, final long n2, final DoubleBuffer doubleBuffer) {
        final long glNamedBufferSubData = GLContext.getCapabilities().glNamedBufferSubData;
        BufferChecks.checkFunctionAddress(glNamedBufferSubData);
        BufferChecks.checkDirect(doubleBuffer);
        nglNamedBufferSubData(n, n2, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glNamedBufferSubData);
    }
    
    public static void glNamedBufferSubData(final int n, final long n2, final FloatBuffer floatBuffer) {
        final long glNamedBufferSubData = GLContext.getCapabilities().glNamedBufferSubData;
        BufferChecks.checkFunctionAddress(glNamedBufferSubData);
        BufferChecks.checkDirect(floatBuffer);
        nglNamedBufferSubData(n, n2, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glNamedBufferSubData);
    }
    
    public static void glNamedBufferSubData(final int n, final long n2, final IntBuffer intBuffer) {
        final long glNamedBufferSubData = GLContext.getCapabilities().glNamedBufferSubData;
        BufferChecks.checkFunctionAddress(glNamedBufferSubData);
        BufferChecks.checkDirect(intBuffer);
        nglNamedBufferSubData(n, n2, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glNamedBufferSubData);
    }
    
    public static void glNamedBufferSubData(final int n, final long n2, final ShortBuffer shortBuffer) {
        final long glNamedBufferSubData = GLContext.getCapabilities().glNamedBufferSubData;
        BufferChecks.checkFunctionAddress(glNamedBufferSubData);
        BufferChecks.checkDirect(shortBuffer);
        nglNamedBufferSubData(n, n2, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glNamedBufferSubData);
    }
    
    static native void nglNamedBufferSubData(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    public static void glCopyNamedBufferSubData(final int n, final int n2, final long n3, final long n4, final long n5) {
        final long glCopyNamedBufferSubData = GLContext.getCapabilities().glCopyNamedBufferSubData;
        BufferChecks.checkFunctionAddress(glCopyNamedBufferSubData);
        nglCopyNamedBufferSubData(n, n2, n3, n4, n5, glCopyNamedBufferSubData);
    }
    
    static native void nglCopyNamedBufferSubData(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static void glClearNamedBufferData(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        final long glClearNamedBufferData = GLContext.getCapabilities().glClearNamedBufferData;
        BufferChecks.checkFunctionAddress(glClearNamedBufferData);
        BufferChecks.checkBuffer(byteBuffer, 1);
        nglClearNamedBufferData(n, n2, n3, n4, MemoryUtil.getAddress(byteBuffer), glClearNamedBufferData);
    }
    
    static native void nglClearNamedBufferData(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glClearNamedBufferSubData(final int n, final int n2, final long n3, final long n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        final long glClearNamedBufferSubData = GLContext.getCapabilities().glClearNamedBufferSubData;
        BufferChecks.checkFunctionAddress(glClearNamedBufferSubData);
        BufferChecks.checkBuffer(byteBuffer, 1);
        nglClearNamedBufferSubData(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(byteBuffer), glClearNamedBufferSubData);
    }
    
    static native void nglClearNamedBufferSubData(final int p0, final int p1, final long p2, final long p3, final int p4, final int p5, final long p6, final long p7);
    
    public static ByteBuffer glMapNamedBuffer(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glMapNamedBuffer = GLContext.getCapabilities().glMapNamedBuffer;
        BufferChecks.checkFunctionAddress(glMapNamedBuffer);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        final ByteBuffer nglMapNamedBuffer = nglMapNamedBuffer(n, n2, glGetNamedBufferParameteri(n, 34660), byteBuffer, glMapNamedBuffer);
        return (LWJGLUtil.CHECKS && nglMapNamedBuffer == null) ? null : nglMapNamedBuffer.order(ByteOrder.nativeOrder());
    }
    
    public static ByteBuffer glMapNamedBuffer(final int n, final int n2, final long n3, final ByteBuffer byteBuffer) {
        final long glMapNamedBuffer = GLContext.getCapabilities().glMapNamedBuffer;
        BufferChecks.checkFunctionAddress(glMapNamedBuffer);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        final ByteBuffer nglMapNamedBuffer = nglMapNamedBuffer(n, n2, n3, byteBuffer, glMapNamedBuffer);
        return (LWJGLUtil.CHECKS && nglMapNamedBuffer == null) ? null : nglMapNamedBuffer.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglMapNamedBuffer(final int p0, final int p1, final long p2, final ByteBuffer p3, final long p4);
    
    public static ByteBuffer glMapNamedBufferRange(final int n, final long n2, final long n3, final int n4, final ByteBuffer byteBuffer) {
        final long glMapNamedBufferRange = GLContext.getCapabilities().glMapNamedBufferRange;
        BufferChecks.checkFunctionAddress(glMapNamedBufferRange);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        final ByteBuffer nglMapNamedBufferRange = nglMapNamedBufferRange(n, n2, n3, n4, byteBuffer, glMapNamedBufferRange);
        return (LWJGLUtil.CHECKS && nglMapNamedBufferRange == null) ? null : nglMapNamedBufferRange.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglMapNamedBufferRange(final int p0, final long p1, final long p2, final int p3, final ByteBuffer p4, final long p5);
    
    public static boolean glUnmapNamedBuffer(final int n) {
        final long glUnmapNamedBuffer = GLContext.getCapabilities().glUnmapNamedBuffer;
        BufferChecks.checkFunctionAddress(glUnmapNamedBuffer);
        return nglUnmapNamedBuffer(n, glUnmapNamedBuffer);
    }
    
    static native boolean nglUnmapNamedBuffer(final int p0, final long p1);
    
    public static void glFlushMappedNamedBufferRange(final int n, final long n2, final long n3) {
        final long glFlushMappedNamedBufferRange = GLContext.getCapabilities().glFlushMappedNamedBufferRange;
        BufferChecks.checkFunctionAddress(glFlushMappedNamedBufferRange);
        nglFlushMappedNamedBufferRange(n, n2, n3, glFlushMappedNamedBufferRange);
    }
    
    static native void nglFlushMappedNamedBufferRange(final int p0, final long p1, final long p2, final long p3);
    
    public static void glGetNamedBufferParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetNamedBufferParameteriv = GLContext.getCapabilities().glGetNamedBufferParameteriv;
        BufferChecks.checkFunctionAddress(glGetNamedBufferParameteriv);
        BufferChecks.checkDirect(intBuffer);
        nglGetNamedBufferParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glGetNamedBufferParameteriv);
    }
    
    static native void nglGetNamedBufferParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetNamedBufferParameteri(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetNamedBufferParameteriv = capabilities.glGetNamedBufferParameteriv;
        BufferChecks.checkFunctionAddress(glGetNamedBufferParameteriv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetNamedBufferParameteriv(n, n2, MemoryUtil.getAddress(bufferInt), glGetNamedBufferParameteriv);
        return bufferInt.get(0);
    }
    
    public static void glGetNamedBufferParameter(final int n, final int n2, final LongBuffer longBuffer) {
        final long glGetNamedBufferParameteri64v = GLContext.getCapabilities().glGetNamedBufferParameteri64v;
        BufferChecks.checkFunctionAddress(glGetNamedBufferParameteri64v);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglGetNamedBufferParameteri64v(n, n2, MemoryUtil.getAddress(longBuffer), glGetNamedBufferParameteri64v);
    }
    
    static native void nglGetNamedBufferParameteri64v(final int p0, final int p1, final long p2, final long p3);
    
    public static long glGetNamedBufferParameteri64(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetNamedBufferParameteri64v = capabilities.glGetNamedBufferParameteri64v;
        BufferChecks.checkFunctionAddress(glGetNamedBufferParameteri64v);
        final LongBuffer bufferLong = APIUtil.getBufferLong(capabilities);
        nglGetNamedBufferParameteri64v(n, n2, MemoryUtil.getAddress(bufferLong), glGetNamedBufferParameteri64v);
        return bufferLong.get(0);
    }
    
    public static ByteBuffer glGetNamedBufferPointer(final int n, final int n2) {
        final long glGetNamedBufferPointerv = GLContext.getCapabilities().glGetNamedBufferPointerv;
        BufferChecks.checkFunctionAddress(glGetNamedBufferPointerv);
        final ByteBuffer nglGetNamedBufferPointerv = nglGetNamedBufferPointerv(n, n2, glGetNamedBufferParameteri(n, 34660), glGetNamedBufferPointerv);
        return (LWJGLUtil.CHECKS && nglGetNamedBufferPointerv == null) ? null : nglGetNamedBufferPointerv.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglGetNamedBufferPointerv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetNamedBufferSubData(final int n, final long n2, final ByteBuffer byteBuffer) {
        final long glGetNamedBufferSubData = GLContext.getCapabilities().glGetNamedBufferSubData;
        BufferChecks.checkFunctionAddress(glGetNamedBufferSubData);
        BufferChecks.checkDirect(byteBuffer);
        nglGetNamedBufferSubData(n, n2, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glGetNamedBufferSubData);
    }
    
    public static void glGetNamedBufferSubData(final int n, final long n2, final DoubleBuffer doubleBuffer) {
        final long glGetNamedBufferSubData = GLContext.getCapabilities().glGetNamedBufferSubData;
        BufferChecks.checkFunctionAddress(glGetNamedBufferSubData);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetNamedBufferSubData(n, n2, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glGetNamedBufferSubData);
    }
    
    public static void glGetNamedBufferSubData(final int n, final long n2, final FloatBuffer floatBuffer) {
        final long glGetNamedBufferSubData = GLContext.getCapabilities().glGetNamedBufferSubData;
        BufferChecks.checkFunctionAddress(glGetNamedBufferSubData);
        BufferChecks.checkDirect(floatBuffer);
        nglGetNamedBufferSubData(n, n2, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glGetNamedBufferSubData);
    }
    
    public static void glGetNamedBufferSubData(final int n, final long n2, final IntBuffer intBuffer) {
        final long glGetNamedBufferSubData = GLContext.getCapabilities().glGetNamedBufferSubData;
        BufferChecks.checkFunctionAddress(glGetNamedBufferSubData);
        BufferChecks.checkDirect(intBuffer);
        nglGetNamedBufferSubData(n, n2, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glGetNamedBufferSubData);
    }
    
    public static void glGetNamedBufferSubData(final int n, final long n2, final ShortBuffer shortBuffer) {
        final long glGetNamedBufferSubData = GLContext.getCapabilities().glGetNamedBufferSubData;
        BufferChecks.checkFunctionAddress(glGetNamedBufferSubData);
        BufferChecks.checkDirect(shortBuffer);
        nglGetNamedBufferSubData(n, n2, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glGetNamedBufferSubData);
    }
    
    static native void nglGetNamedBufferSubData(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    public static void glCreateFramebuffers(final IntBuffer intBuffer) {
        final long glCreateFramebuffers = GLContext.getCapabilities().glCreateFramebuffers;
        BufferChecks.checkFunctionAddress(glCreateFramebuffers);
        BufferChecks.checkDirect(intBuffer);
        nglCreateFramebuffers(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glCreateFramebuffers);
    }
    
    static native void nglCreateFramebuffers(final int p0, final long p1, final long p2);
    
    public static int glCreateFramebuffers() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCreateFramebuffers = capabilities.glCreateFramebuffers;
        BufferChecks.checkFunctionAddress(glCreateFramebuffers);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglCreateFramebuffers(1, MemoryUtil.getAddress(bufferInt), glCreateFramebuffers);
        return bufferInt.get(0);
    }
    
    public static void glNamedFramebufferRenderbuffer(final int n, final int n2, final int n3, final int n4) {
        final long glNamedFramebufferRenderbuffer = GLContext.getCapabilities().glNamedFramebufferRenderbuffer;
        BufferChecks.checkFunctionAddress(glNamedFramebufferRenderbuffer);
        nglNamedFramebufferRenderbuffer(n, n2, n3, n4, glNamedFramebufferRenderbuffer);
    }
    
    static native void nglNamedFramebufferRenderbuffer(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glNamedFramebufferParameteri(final int n, final int n2, final int n3) {
        final long glNamedFramebufferParameteri = GLContext.getCapabilities().glNamedFramebufferParameteri;
        BufferChecks.checkFunctionAddress(glNamedFramebufferParameteri);
        nglNamedFramebufferParameteri(n, n2, n3, glNamedFramebufferParameteri);
    }
    
    static native void nglNamedFramebufferParameteri(final int p0, final int p1, final int p2, final long p3);
    
    public static void glNamedFramebufferTexture(final int n, final int n2, final int n3, final int n4) {
        final long glNamedFramebufferTexture = GLContext.getCapabilities().glNamedFramebufferTexture;
        BufferChecks.checkFunctionAddress(glNamedFramebufferTexture);
        nglNamedFramebufferTexture(n, n2, n3, n4, glNamedFramebufferTexture);
    }
    
    static native void nglNamedFramebufferTexture(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glNamedFramebufferTextureLayer(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glNamedFramebufferTextureLayer = GLContext.getCapabilities().glNamedFramebufferTextureLayer;
        BufferChecks.checkFunctionAddress(glNamedFramebufferTextureLayer);
        nglNamedFramebufferTextureLayer(n, n2, n3, n4, n5, glNamedFramebufferTextureLayer);
    }
    
    static native void nglNamedFramebufferTextureLayer(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glNamedFramebufferDrawBuffer(final int n, final int n2) {
        final long glNamedFramebufferDrawBuffer = GLContext.getCapabilities().glNamedFramebufferDrawBuffer;
        BufferChecks.checkFunctionAddress(glNamedFramebufferDrawBuffer);
        nglNamedFramebufferDrawBuffer(n, n2, glNamedFramebufferDrawBuffer);
    }
    
    static native void nglNamedFramebufferDrawBuffer(final int p0, final int p1, final long p2);
    
    public static void glNamedFramebufferDrawBuffers(final int n, final IntBuffer intBuffer) {
        final long glNamedFramebufferDrawBuffers = GLContext.getCapabilities().glNamedFramebufferDrawBuffers;
        BufferChecks.checkFunctionAddress(glNamedFramebufferDrawBuffers);
        BufferChecks.checkDirect(intBuffer);
        nglNamedFramebufferDrawBuffers(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glNamedFramebufferDrawBuffers);
    }
    
    static native void nglNamedFramebufferDrawBuffers(final int p0, final int p1, final long p2, final long p3);
    
    public static void glNamedFramebufferReadBuffer(final int n, final int n2) {
        final long glNamedFramebufferReadBuffer = GLContext.getCapabilities().glNamedFramebufferReadBuffer;
        BufferChecks.checkFunctionAddress(glNamedFramebufferReadBuffer);
        nglNamedFramebufferReadBuffer(n, n2, glNamedFramebufferReadBuffer);
    }
    
    static native void nglNamedFramebufferReadBuffer(final int p0, final int p1, final long p2);
    
    public static void glInvalidateNamedFramebufferData(final int n, final IntBuffer intBuffer) {
        final long glInvalidateNamedFramebufferData = GLContext.getCapabilities().glInvalidateNamedFramebufferData;
        BufferChecks.checkFunctionAddress(glInvalidateNamedFramebufferData);
        BufferChecks.checkDirect(intBuffer);
        nglInvalidateNamedFramebufferData(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glInvalidateNamedFramebufferData);
    }
    
    static native void nglInvalidateNamedFramebufferData(final int p0, final int p1, final long p2, final long p3);
    
    public static void glInvalidateNamedFramebufferSubData(final int n, final IntBuffer intBuffer, final int n2, final int n3, final int n4, final int n5) {
        final long glInvalidateNamedFramebufferSubData = GLContext.getCapabilities().glInvalidateNamedFramebufferSubData;
        BufferChecks.checkFunctionAddress(glInvalidateNamedFramebufferSubData);
        BufferChecks.checkDirect(intBuffer);
        nglInvalidateNamedFramebufferSubData(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), n2, n3, n4, n5, glInvalidateNamedFramebufferSubData);
    }
    
    static native void nglInvalidateNamedFramebufferSubData(final int p0, final int p1, final long p2, final int p3, final int p4, final int p5, final int p6, final long p7);
    
    public static void glClearNamedFramebuffer(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glClearNamedFramebufferiv = GLContext.getCapabilities().glClearNamedFramebufferiv;
        BufferChecks.checkFunctionAddress(glClearNamedFramebufferiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglClearNamedFramebufferiv(n, n2, n3, MemoryUtil.getAddress(intBuffer), glClearNamedFramebufferiv);
    }
    
    static native void nglClearNamedFramebufferiv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glClearNamedFramebufferu(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glClearNamedFramebufferuiv = GLContext.getCapabilities().glClearNamedFramebufferuiv;
        BufferChecks.checkFunctionAddress(glClearNamedFramebufferuiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglClearNamedFramebufferuiv(n, n2, n3, MemoryUtil.getAddress(intBuffer), glClearNamedFramebufferuiv);
    }
    
    static native void nglClearNamedFramebufferuiv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glClearNamedFramebuffer(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glClearNamedFramebufferfv = GLContext.getCapabilities().glClearNamedFramebufferfv;
        BufferChecks.checkFunctionAddress(glClearNamedFramebufferfv);
        BufferChecks.checkBuffer(floatBuffer, 1);
        nglClearNamedFramebufferfv(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glClearNamedFramebufferfv);
    }
    
    static native void nglClearNamedFramebufferfv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glClearNamedFramebufferfi(final int n, final int n2, final float n3, final int n4) {
        final long glClearNamedFramebufferfi = GLContext.getCapabilities().glClearNamedFramebufferfi;
        BufferChecks.checkFunctionAddress(glClearNamedFramebufferfi);
        nglClearNamedFramebufferfi(n, n2, n3, n4, glClearNamedFramebufferfi);
    }
    
    static native void nglClearNamedFramebufferfi(final int p0, final int p1, final float p2, final int p3, final long p4);
    
    public static void glBlitNamedFramebuffer(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final int n12) {
        final long glBlitNamedFramebuffer = GLContext.getCapabilities().glBlitNamedFramebuffer;
        BufferChecks.checkFunctionAddress(glBlitNamedFramebuffer);
        nglBlitNamedFramebuffer(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, glBlitNamedFramebuffer);
    }
    
    static native void nglBlitNamedFramebuffer(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final int p11, final long p12);
    
    public static int glCheckNamedFramebufferStatus(final int n, final int n2) {
        final long glCheckNamedFramebufferStatus = GLContext.getCapabilities().glCheckNamedFramebufferStatus;
        BufferChecks.checkFunctionAddress(glCheckNamedFramebufferStatus);
        return nglCheckNamedFramebufferStatus(n, n2, glCheckNamedFramebufferStatus);
    }
    
    static native int nglCheckNamedFramebufferStatus(final int p0, final int p1, final long p2);
    
    public static void glGetNamedFramebufferParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetNamedFramebufferParameteriv = GLContext.getCapabilities().glGetNamedFramebufferParameteriv;
        BufferChecks.checkFunctionAddress(glGetNamedFramebufferParameteriv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetNamedFramebufferParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glGetNamedFramebufferParameteriv);
    }
    
    static native void nglGetNamedFramebufferParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetNamedFramebufferParameter(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetNamedFramebufferParameteriv = capabilities.glGetNamedFramebufferParameteriv;
        BufferChecks.checkFunctionAddress(glGetNamedFramebufferParameteriv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetNamedFramebufferParameteriv(n, n2, MemoryUtil.getAddress(bufferInt), glGetNamedFramebufferParameteriv);
        return bufferInt.get(0);
    }
    
    public static void glGetNamedFramebufferAttachmentParameter(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetNamedFramebufferAttachmentParameteriv = GLContext.getCapabilities().glGetNamedFramebufferAttachmentParameteriv;
        BufferChecks.checkFunctionAddress(glGetNamedFramebufferAttachmentParameteriv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetNamedFramebufferAttachmentParameteriv(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetNamedFramebufferAttachmentParameteriv);
    }
    
    static native void nglGetNamedFramebufferAttachmentParameteriv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetNamedFramebufferAttachmentParameter(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetNamedFramebufferAttachmentParameteriv = capabilities.glGetNamedFramebufferAttachmentParameteriv;
        BufferChecks.checkFunctionAddress(glGetNamedFramebufferAttachmentParameteriv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetNamedFramebufferAttachmentParameteriv(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetNamedFramebufferAttachmentParameteriv);
        return bufferInt.get(0);
    }
    
    public static void glCreateRenderbuffers(final IntBuffer intBuffer) {
        final long glCreateRenderbuffers = GLContext.getCapabilities().glCreateRenderbuffers;
        BufferChecks.checkFunctionAddress(glCreateRenderbuffers);
        BufferChecks.checkDirect(intBuffer);
        nglCreateRenderbuffers(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glCreateRenderbuffers);
    }
    
    static native void nglCreateRenderbuffers(final int p0, final long p1, final long p2);
    
    public static int glCreateRenderbuffers() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCreateRenderbuffers = capabilities.glCreateRenderbuffers;
        BufferChecks.checkFunctionAddress(glCreateRenderbuffers);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglCreateRenderbuffers(1, MemoryUtil.getAddress(bufferInt), glCreateRenderbuffers);
        return bufferInt.get(0);
    }
    
    public static void glNamedRenderbufferStorage(final int n, final int n2, final int n3, final int n4) {
        final long glNamedRenderbufferStorage = GLContext.getCapabilities().glNamedRenderbufferStorage;
        BufferChecks.checkFunctionAddress(glNamedRenderbufferStorage);
        nglNamedRenderbufferStorage(n, n2, n3, n4, glNamedRenderbufferStorage);
    }
    
    static native void nglNamedRenderbufferStorage(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glNamedRenderbufferStorageMultisample(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glNamedRenderbufferStorageMultisample = GLContext.getCapabilities().glNamedRenderbufferStorageMultisample;
        BufferChecks.checkFunctionAddress(glNamedRenderbufferStorageMultisample);
        nglNamedRenderbufferStorageMultisample(n, n2, n3, n4, n5, glNamedRenderbufferStorageMultisample);
    }
    
    static native void nglNamedRenderbufferStorageMultisample(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glGetNamedRenderbufferParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetNamedRenderbufferParameteriv = GLContext.getCapabilities().glGetNamedRenderbufferParameteriv;
        BufferChecks.checkFunctionAddress(glGetNamedRenderbufferParameteriv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetNamedRenderbufferParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glGetNamedRenderbufferParameteriv);
    }
    
    static native void nglGetNamedRenderbufferParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetNamedRenderbufferParameter(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetNamedRenderbufferParameteriv = capabilities.glGetNamedRenderbufferParameteriv;
        BufferChecks.checkFunctionAddress(glGetNamedRenderbufferParameteriv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetNamedRenderbufferParameteriv(n, n2, MemoryUtil.getAddress(bufferInt), glGetNamedRenderbufferParameteriv);
        return bufferInt.get(0);
    }
    
    public static void glCreateTextures(final int n, final IntBuffer intBuffer) {
        final long glCreateTextures = GLContext.getCapabilities().glCreateTextures;
        BufferChecks.checkFunctionAddress(glCreateTextures);
        BufferChecks.checkDirect(intBuffer);
        nglCreateTextures(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glCreateTextures);
    }
    
    static native void nglCreateTextures(final int p0, final int p1, final long p2, final long p3);
    
    public static int glCreateTextures(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCreateTextures = capabilities.glCreateTextures;
        BufferChecks.checkFunctionAddress(glCreateTextures);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglCreateTextures(n, 1, MemoryUtil.getAddress(bufferInt), glCreateTextures);
        return bufferInt.get(0);
    }
    
    public static void glTextureBuffer(final int n, final int n2, final int n3) {
        final long glTextureBuffer = GLContext.getCapabilities().glTextureBuffer;
        BufferChecks.checkFunctionAddress(glTextureBuffer);
        nglTextureBuffer(n, n2, n3, glTextureBuffer);
    }
    
    static native void nglTextureBuffer(final int p0, final int p1, final int p2, final long p3);
    
    public static void glTextureBufferRange(final int n, final int n2, final int n3, final long n4, final long n5) {
        final long glTextureBufferRange = GLContext.getCapabilities().glTextureBufferRange;
        BufferChecks.checkFunctionAddress(glTextureBufferRange);
        nglTextureBufferRange(n, n2, n3, n4, n5, glTextureBufferRange);
    }
    
    static native void nglTextureBufferRange(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5);
    
    public static void glTextureStorage1D(final int n, final int n2, final int n3, final int n4) {
        final long glTextureStorage1D = GLContext.getCapabilities().glTextureStorage1D;
        BufferChecks.checkFunctionAddress(glTextureStorage1D);
        nglTextureStorage1D(n, n2, n3, n4, glTextureStorage1D);
    }
    
    static native void nglTextureStorage1D(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glTextureStorage2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glTextureStorage2D = GLContext.getCapabilities().glTextureStorage2D;
        BufferChecks.checkFunctionAddress(glTextureStorage2D);
        nglTextureStorage2D(n, n2, n3, n4, n5, glTextureStorage2D);
    }
    
    static native void nglTextureStorage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glTextureStorage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glTextureStorage3D = GLContext.getCapabilities().glTextureStorage3D;
        BufferChecks.checkFunctionAddress(glTextureStorage3D);
        nglTextureStorage3D(n, n2, n3, n4, n5, n6, glTextureStorage3D);
    }
    
    static native void nglTextureStorage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glTextureStorage2DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) {
        final long glTextureStorage2DMultisample = GLContext.getCapabilities().glTextureStorage2DMultisample;
        BufferChecks.checkFunctionAddress(glTextureStorage2DMultisample);
        nglTextureStorage2DMultisample(n, n2, n3, n4, n5, b, glTextureStorage2DMultisample);
    }
    
    static native void nglTextureStorage2DMultisample(final int p0, final int p1, final int p2, final int p3, final int p4, final boolean p5, final long p6);
    
    public static void glTextureStorage3DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        final long glTextureStorage3DMultisample = GLContext.getCapabilities().glTextureStorage3DMultisample;
        BufferChecks.checkFunctionAddress(glTextureStorage3DMultisample);
        nglTextureStorage3DMultisample(n, n2, n3, n4, n5, n6, b, glTextureStorage3DMultisample);
    }
    
    static native void nglTextureStorage3DMultisample(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final boolean p6, final long p7);
    
    public static void glTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage1D = capabilities.glTextureSubImage1D;
        BufferChecks.checkFunctionAddress(glTextureSubImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n5, n6, n4, 1, 1));
        nglTextureSubImage1D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(byteBuffer), glTextureSubImage1D);
    }
    
    public static void glTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage1D = capabilities.glTextureSubImage1D;
        BufferChecks.checkFunctionAddress(glTextureSubImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n5, n6, n4, 1, 1));
        nglTextureSubImage1D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(doubleBuffer), glTextureSubImage1D);
    }
    
    public static void glTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage1D = capabilities.glTextureSubImage1D;
        BufferChecks.checkFunctionAddress(glTextureSubImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n5, n6, n4, 1, 1));
        nglTextureSubImage1D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(floatBuffer), glTextureSubImage1D);
    }
    
    public static void glTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage1D = capabilities.glTextureSubImage1D;
        BufferChecks.checkFunctionAddress(glTextureSubImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n5, n6, n4, 1, 1));
        nglTextureSubImage1D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(intBuffer), glTextureSubImage1D);
    }
    
    public static void glTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage1D = capabilities.glTextureSubImage1D;
        BufferChecks.checkFunctionAddress(glTextureSubImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n5, n6, n4, 1, 1));
        nglTextureSubImage1D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(shortBuffer), glTextureSubImage1D);
    }
    
    static native void nglTextureSubImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage1D = capabilities.glTextureSubImage1D;
        BufferChecks.checkFunctionAddress(glTextureSubImage1D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglTextureSubImage1DBO(n, n2, n3, n4, n5, n6, n7, glTextureSubImage1D);
    }
    
    static native void nglTextureSubImage1DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage2D = capabilities.glTextureSubImage2D;
        BufferChecks.checkFunctionAddress(glTextureSubImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n7, n8, n5, n6, 1));
        nglTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddress(byteBuffer), glTextureSubImage2D);
    }
    
    public static void glTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage2D = capabilities.glTextureSubImage2D;
        BufferChecks.checkFunctionAddress(glTextureSubImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n7, n8, n5, n6, 1));
        nglTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddress(doubleBuffer), glTextureSubImage2D);
    }
    
    public static void glTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage2D = capabilities.glTextureSubImage2D;
        BufferChecks.checkFunctionAddress(glTextureSubImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n7, n8, n5, n6, 1));
        nglTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddress(floatBuffer), glTextureSubImage2D);
    }
    
    public static void glTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage2D = capabilities.glTextureSubImage2D;
        BufferChecks.checkFunctionAddress(glTextureSubImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n7, n8, n5, n6, 1));
        nglTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddress(intBuffer), glTextureSubImage2D);
    }
    
    public static void glTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage2D = capabilities.glTextureSubImage2D;
        BufferChecks.checkFunctionAddress(glTextureSubImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n7, n8, n5, n6, 1));
        nglTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddress(shortBuffer), glTextureSubImage2D);
    }
    
    static native void nglTextureSubImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage2D = capabilities.glTextureSubImage2D;
        BufferChecks.checkFunctionAddress(glTextureSubImage2D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglTextureSubImage2DBO(n, n2, n3, n4, n5, n6, n7, n8, n9, glTextureSubImage2D);
    }
    
    static native void nglTextureSubImage2DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage3D = capabilities.glTextureSubImage3D;
        BufferChecks.checkFunctionAddress(glTextureSubImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n9, n10, n6, n7, n8));
        nglTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddress(byteBuffer), glTextureSubImage3D);
    }
    
    public static void glTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage3D = capabilities.glTextureSubImage3D;
        BufferChecks.checkFunctionAddress(glTextureSubImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n9, n10, n6, n7, n8));
        nglTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddress(doubleBuffer), glTextureSubImage3D);
    }
    
    public static void glTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage3D = capabilities.glTextureSubImage3D;
        BufferChecks.checkFunctionAddress(glTextureSubImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n9, n10, n6, n7, n8));
        nglTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddress(floatBuffer), glTextureSubImage3D);
    }
    
    public static void glTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage3D = capabilities.glTextureSubImage3D;
        BufferChecks.checkFunctionAddress(glTextureSubImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n9, n10, n6, n7, n8));
        nglTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddress(intBuffer), glTextureSubImage3D);
    }
    
    public static void glTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage3D = capabilities.glTextureSubImage3D;
        BufferChecks.checkFunctionAddress(glTextureSubImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n9, n10, n6, n7, n8));
        nglTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddress(shortBuffer), glTextureSubImage3D);
    }
    
    static native void nglTextureSubImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10, final long p11);
    
    public static void glTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final long n11) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage3D = capabilities.glTextureSubImage3D;
        BufferChecks.checkFunctionAddress(glTextureSubImage3D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglTextureSubImage3DBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, glTextureSubImage3D);
    }
    
    static native void nglTextureSubImage3DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10, final long p11);
    
    public static void glCompressedTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureSubImage1D = capabilities.glCompressedTextureSubImage1D;
        BufferChecks.checkFunctionAddress(glCompressedTextureSubImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTextureSubImage1D(n, n2, n3, n4, n5, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTextureSubImage1D);
    }
    
    static native void nglCompressedTextureSubImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glCompressedTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureSubImage1D = capabilities.glCompressedTextureSubImage1D;
        BufferChecks.checkFunctionAddress(glCompressedTextureSubImage1D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTextureSubImage1DBO(n, n2, n3, n4, n5, n6, n7, glCompressedTextureSubImage1D);
    }
    
    static native void nglCompressedTextureSubImage1DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glCompressedTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureSubImage2D = capabilities.glCompressedTextureSubImage2D;
        BufferChecks.checkFunctionAddress(glCompressedTextureSubImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTextureSubImage2D);
    }
    
    static native void nglCompressedTextureSubImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glCompressedTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureSubImage2D = capabilities.glCompressedTextureSubImage2D;
        BufferChecks.checkFunctionAddress(glCompressedTextureSubImage2D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTextureSubImage2DBO(n, n2, n3, n4, n5, n6, n7, n8, n9, glCompressedTextureSubImage2D);
    }
    
    static native void nglCompressedTextureSubImage2DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glCompressedTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureSubImage3D = capabilities.glCompressedTextureSubImage3D;
        BufferChecks.checkFunctionAddress(glCompressedTextureSubImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddress(byteBuffer), glCompressedTextureSubImage3D);
    }
    
    static native void nglCompressedTextureSubImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10, final long p11);
    
    public static void glCompressedTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final long n11) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureSubImage3D = capabilities.glCompressedTextureSubImage3D;
        BufferChecks.checkFunctionAddress(glCompressedTextureSubImage3D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTextureSubImage3DBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, glCompressedTextureSubImage3D);
    }
    
    static native void nglCompressedTextureSubImage3DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10, final long p11);
    
    public static void glCopyTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glCopyTextureSubImage1D = GLContext.getCapabilities().glCopyTextureSubImage1D;
        BufferChecks.checkFunctionAddress(glCopyTextureSubImage1D);
        nglCopyTextureSubImage1D(n, n2, n3, n4, n5, n6, glCopyTextureSubImage1D);
    }
    
    static native void nglCopyTextureSubImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glCopyTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        final long glCopyTextureSubImage2D = GLContext.getCapabilities().glCopyTextureSubImage2D;
        BufferChecks.checkFunctionAddress(glCopyTextureSubImage2D);
        nglCopyTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, glCopyTextureSubImage2D);
    }
    
    static native void nglCopyTextureSubImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    public static void glCopyTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9) {
        final long glCopyTextureSubImage3D = GLContext.getCapabilities().glCopyTextureSubImage3D;
        BufferChecks.checkFunctionAddress(glCopyTextureSubImage3D);
        nglCopyTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, glCopyTextureSubImage3D);
    }
    
    static native void nglCopyTextureSubImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9);
    
    public static void glTextureParameterf(final int n, final int n2, final float n3) {
        final long glTextureParameterf = GLContext.getCapabilities().glTextureParameterf;
        BufferChecks.checkFunctionAddress(glTextureParameterf);
        nglTextureParameterf(n, n2, n3, glTextureParameterf);
    }
    
    static native void nglTextureParameterf(final int p0, final int p1, final float p2, final long p3);
    
    public static void glTextureParameter(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glTextureParameterfv = GLContext.getCapabilities().glTextureParameterfv;
        BufferChecks.checkFunctionAddress(glTextureParameterfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglTextureParameterfv(n, n2, MemoryUtil.getAddress(floatBuffer), glTextureParameterfv);
    }
    
    static native void nglTextureParameterfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glTextureParameteri(final int n, final int n2, final int n3) {
        final long glTextureParameteri = GLContext.getCapabilities().glTextureParameteri;
        BufferChecks.checkFunctionAddress(glTextureParameteri);
        nglTextureParameteri(n, n2, n3, glTextureParameteri);
    }
    
    static native void nglTextureParameteri(final int p0, final int p1, final int p2, final long p3);
    
    public static void glTextureParameterI(final int n, final int n2, final IntBuffer intBuffer) {
        final long glTextureParameterIiv = GLContext.getCapabilities().glTextureParameterIiv;
        BufferChecks.checkFunctionAddress(glTextureParameterIiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglTextureParameterIiv(n, n2, MemoryUtil.getAddress(intBuffer), glTextureParameterIiv);
    }
    
    static native void nglTextureParameterIiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glTextureParameterIu(final int n, final int n2, final IntBuffer intBuffer) {
        final long glTextureParameterIuiv = GLContext.getCapabilities().glTextureParameterIuiv;
        BufferChecks.checkFunctionAddress(glTextureParameterIuiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglTextureParameterIuiv(n, n2, MemoryUtil.getAddress(intBuffer), glTextureParameterIuiv);
    }
    
    static native void nglTextureParameterIuiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glTextureParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glTextureParameteriv = GLContext.getCapabilities().glTextureParameteriv;
        BufferChecks.checkFunctionAddress(glTextureParameteriv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglTextureParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glTextureParameteriv);
    }
    
    static native void nglTextureParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGenerateTextureMipmap(final int n) {
        final long glGenerateTextureMipmap = GLContext.getCapabilities().glGenerateTextureMipmap;
        BufferChecks.checkFunctionAddress(glGenerateTextureMipmap);
        nglGenerateTextureMipmap(n, glGenerateTextureMipmap);
    }
    
    static native void nglGenerateTextureMipmap(final int p0, final long p1);
    
    public static void glBindTextureUnit(final int n, final int n2) {
        final long glBindTextureUnit = GLContext.getCapabilities().glBindTextureUnit;
        BufferChecks.checkFunctionAddress(glBindTextureUnit);
        nglBindTextureUnit(n, n2, glBindTextureUnit);
    }
    
    static native void nglBindTextureUnit(final int p0, final int p1, final long p2);
    
    public static void glGetTextureImage(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureImage = capabilities.glGetTextureImage;
        BufferChecks.checkFunctionAddress(glGetTextureImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglGetTextureImage(n, n2, n3, n4, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glGetTextureImage);
    }
    
    public static void glGetTextureImage(final int n, final int n2, final int n3, final int n4, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureImage = capabilities.glGetTextureImage;
        BufferChecks.checkFunctionAddress(glGetTextureImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetTextureImage(n, n2, n3, n4, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glGetTextureImage);
    }
    
    public static void glGetTextureImage(final int n, final int n2, final int n3, final int n4, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureImage = capabilities.glGetTextureImage;
        BufferChecks.checkFunctionAddress(glGetTextureImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        nglGetTextureImage(n, n2, n3, n4, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glGetTextureImage);
    }
    
    public static void glGetTextureImage(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureImage = capabilities.glGetTextureImage;
        BufferChecks.checkFunctionAddress(glGetTextureImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglGetTextureImage(n, n2, n3, n4, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glGetTextureImage);
    }
    
    public static void glGetTextureImage(final int n, final int n2, final int n3, final int n4, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureImage = capabilities.glGetTextureImage;
        BufferChecks.checkFunctionAddress(glGetTextureImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglGetTextureImage(n, n2, n3, n4, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glGetTextureImage);
    }
    
    static native void nglGetTextureImage(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glGetTextureImage(final int n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureImage = capabilities.glGetTextureImage;
        BufferChecks.checkFunctionAddress(glGetTextureImage);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetTextureImageBO(n, n2, n3, n4, n5, n6, glGetTextureImage);
    }
    
    static native void nglGetTextureImageBO(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glGetCompressedTextureImage(final int n, final int n2, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTextureImage = capabilities.glGetCompressedTextureImage;
        BufferChecks.checkFunctionAddress(glGetCompressedTextureImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglGetCompressedTextureImage(n, n2, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glGetCompressedTextureImage);
    }
    
    public static void glGetCompressedTextureImage(final int n, final int n2, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTextureImage = capabilities.glGetCompressedTextureImage;
        BufferChecks.checkFunctionAddress(glGetCompressedTextureImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglGetCompressedTextureImage(n, n2, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glGetCompressedTextureImage);
    }
    
    public static void glGetCompressedTextureImage(final int n, final int n2, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTextureImage = capabilities.glGetCompressedTextureImage;
        BufferChecks.checkFunctionAddress(glGetCompressedTextureImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglGetCompressedTextureImage(n, n2, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glGetCompressedTextureImage);
    }
    
    static native void nglGetCompressedTextureImage(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetCompressedTextureImage(final int n, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTextureImage = capabilities.glGetCompressedTextureImage;
        BufferChecks.checkFunctionAddress(glGetCompressedTextureImage);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetCompressedTextureImageBO(n, n2, n3, n4, glGetCompressedTextureImage);
    }
    
    static native void nglGetCompressedTextureImageBO(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetTextureLevelParameter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glGetTextureLevelParameterfv = GLContext.getCapabilities().glGetTextureLevelParameterfv;
        BufferChecks.checkFunctionAddress(glGetTextureLevelParameterfv);
        BufferChecks.checkBuffer(floatBuffer, 1);
        nglGetTextureLevelParameterfv(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glGetTextureLevelParameterfv);
    }
    
    static native void nglGetTextureLevelParameterfv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static float glGetTextureLevelParameterf(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureLevelParameterfv = capabilities.glGetTextureLevelParameterfv;
        BufferChecks.checkFunctionAddress(glGetTextureLevelParameterfv);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetTextureLevelParameterfv(n, n2, n3, MemoryUtil.getAddress(bufferFloat), glGetTextureLevelParameterfv);
        return bufferFloat.get(0);
    }
    
    public static void glGetTextureLevelParameter(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetTextureLevelParameteriv = GLContext.getCapabilities().glGetTextureLevelParameteriv;
        BufferChecks.checkFunctionAddress(glGetTextureLevelParameteriv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetTextureLevelParameteriv(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetTextureLevelParameteriv);
    }
    
    static native void nglGetTextureLevelParameteriv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetTextureLevelParameteri(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureLevelParameteriv = capabilities.glGetTextureLevelParameteriv;
        BufferChecks.checkFunctionAddress(glGetTextureLevelParameteriv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTextureLevelParameteriv(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetTextureLevelParameteriv);
        return bufferInt.get(0);
    }
    
    public static void glGetTextureParameter(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetTextureParameterfv = GLContext.getCapabilities().glGetTextureParameterfv;
        BufferChecks.checkFunctionAddress(glGetTextureParameterfv);
        BufferChecks.checkBuffer(floatBuffer, 1);
        nglGetTextureParameterfv(n, n2, MemoryUtil.getAddress(floatBuffer), glGetTextureParameterfv);
    }
    
    static native void nglGetTextureParameterfv(final int p0, final int p1, final long p2, final long p3);
    
    public static float glGetTextureParameterf(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureParameterfv = capabilities.glGetTextureParameterfv;
        BufferChecks.checkFunctionAddress(glGetTextureParameterfv);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetTextureParameterfv(n, n2, MemoryUtil.getAddress(bufferFloat), glGetTextureParameterfv);
        return bufferFloat.get(0);
    }
    
    public static void glGetTextureParameterI(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetTextureParameterIiv = GLContext.getCapabilities().glGetTextureParameterIiv;
        BufferChecks.checkFunctionAddress(glGetTextureParameterIiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetTextureParameterIiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetTextureParameterIiv);
    }
    
    static native void nglGetTextureParameterIiv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetTextureParameterIi(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureParameterIiv = capabilities.glGetTextureParameterIiv;
        BufferChecks.checkFunctionAddress(glGetTextureParameterIiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTextureParameterIiv(n, n2, MemoryUtil.getAddress(bufferInt), glGetTextureParameterIiv);
        return bufferInt.get(0);
    }
    
    public static void glGetTextureParameterIu(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetTextureParameterIuiv = GLContext.getCapabilities().glGetTextureParameterIuiv;
        BufferChecks.checkFunctionAddress(glGetTextureParameterIuiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetTextureParameterIuiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetTextureParameterIuiv);
    }
    
    static native void nglGetTextureParameterIuiv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetTextureParameterIui(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureParameterIuiv = capabilities.glGetTextureParameterIuiv;
        BufferChecks.checkFunctionAddress(glGetTextureParameterIuiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTextureParameterIuiv(n, n2, MemoryUtil.getAddress(bufferInt), glGetTextureParameterIuiv);
        return bufferInt.get(0);
    }
    
    public static void glGetTextureParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetTextureParameteriv = GLContext.getCapabilities().glGetTextureParameteriv;
        BufferChecks.checkFunctionAddress(glGetTextureParameteriv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetTextureParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glGetTextureParameteriv);
    }
    
    static native void nglGetTextureParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetTextureParameteri(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureParameteriv = capabilities.glGetTextureParameteriv;
        BufferChecks.checkFunctionAddress(glGetTextureParameteriv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTextureParameteriv(n, n2, MemoryUtil.getAddress(bufferInt), glGetTextureParameteriv);
        return bufferInt.get(0);
    }
    
    public static void glCreateVertexArrays(final IntBuffer intBuffer) {
        final long glCreateVertexArrays = GLContext.getCapabilities().glCreateVertexArrays;
        BufferChecks.checkFunctionAddress(glCreateVertexArrays);
        BufferChecks.checkDirect(intBuffer);
        nglCreateVertexArrays(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glCreateVertexArrays);
    }
    
    static native void nglCreateVertexArrays(final int p0, final long p1, final long p2);
    
    public static int glCreateVertexArrays() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCreateVertexArrays = capabilities.glCreateVertexArrays;
        BufferChecks.checkFunctionAddress(glCreateVertexArrays);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglCreateVertexArrays(1, MemoryUtil.getAddress(bufferInt), glCreateVertexArrays);
        return bufferInt.get(0);
    }
    
    public static void glDisableVertexArrayAttrib(final int n, final int n2) {
        final long glDisableVertexArrayAttrib = GLContext.getCapabilities().glDisableVertexArrayAttrib;
        BufferChecks.checkFunctionAddress(glDisableVertexArrayAttrib);
        nglDisableVertexArrayAttrib(n, n2, glDisableVertexArrayAttrib);
    }
    
    static native void nglDisableVertexArrayAttrib(final int p0, final int p1, final long p2);
    
    public static void glEnableVertexArrayAttrib(final int n, final int n2) {
        final long glEnableVertexArrayAttrib = GLContext.getCapabilities().glEnableVertexArrayAttrib;
        BufferChecks.checkFunctionAddress(glEnableVertexArrayAttrib);
        nglEnableVertexArrayAttrib(n, n2, glEnableVertexArrayAttrib);
    }
    
    static native void nglEnableVertexArrayAttrib(final int p0, final int p1, final long p2);
    
    public static void glVertexArrayElementBuffer(final int n, final int n2) {
        final long glVertexArrayElementBuffer = GLContext.getCapabilities().glVertexArrayElementBuffer;
        BufferChecks.checkFunctionAddress(glVertexArrayElementBuffer);
        nglVertexArrayElementBuffer(n, n2, glVertexArrayElementBuffer);
    }
    
    static native void nglVertexArrayElementBuffer(final int p0, final int p1, final long p2);
    
    public static void glVertexArrayVertexBuffer(final int n, final int n2, final int n3, final long n4, final int n5) {
        final long glVertexArrayVertexBuffer = GLContext.getCapabilities().glVertexArrayVertexBuffer;
        BufferChecks.checkFunctionAddress(glVertexArrayVertexBuffer);
        nglVertexArrayVertexBuffer(n, n2, n3, n4, n5, glVertexArrayVertexBuffer);
    }
    
    static native void nglVertexArrayVertexBuffer(final int p0, final int p1, final int p2, final long p3, final int p4, final long p5);
    
    public static void glVertexArrayVertexBuffers(final int n, final int n2, final int n3, final IntBuffer intBuffer, final PointerBuffer pointerBuffer, final IntBuffer intBuffer2) {
        final long glVertexArrayVertexBuffers = GLContext.getCapabilities().glVertexArrayVertexBuffers;
        BufferChecks.checkFunctionAddress(glVertexArrayVertexBuffers);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, n3);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, n3);
        }
        if (intBuffer2 != null) {
            BufferChecks.checkBuffer(intBuffer2, n3);
        }
        nglVertexArrayVertexBuffers(n, n2, n3, MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(intBuffer2), glVertexArrayVertexBuffers);
    }
    
    static native void nglVertexArrayVertexBuffers(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5, final long p6);
    
    public static void glVertexArrayAttribFormat(final int n, final int n2, final int n3, final int n4, final boolean b, final int n5) {
        final long glVertexArrayAttribFormat = GLContext.getCapabilities().glVertexArrayAttribFormat;
        BufferChecks.checkFunctionAddress(glVertexArrayAttribFormat);
        nglVertexArrayAttribFormat(n, n2, n3, n4, b, n5, glVertexArrayAttribFormat);
    }
    
    static native void nglVertexArrayAttribFormat(final int p0, final int p1, final int p2, final int p3, final boolean p4, final int p5, final long p6);
    
    public static void glVertexArrayAttribIFormat(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glVertexArrayAttribIFormat = GLContext.getCapabilities().glVertexArrayAttribIFormat;
        BufferChecks.checkFunctionAddress(glVertexArrayAttribIFormat);
        nglVertexArrayAttribIFormat(n, n2, n3, n4, n5, glVertexArrayAttribIFormat);
    }
    
    static native void nglVertexArrayAttribIFormat(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glVertexArrayAttribLFormat(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glVertexArrayAttribLFormat = GLContext.getCapabilities().glVertexArrayAttribLFormat;
        BufferChecks.checkFunctionAddress(glVertexArrayAttribLFormat);
        nglVertexArrayAttribLFormat(n, n2, n3, n4, n5, glVertexArrayAttribLFormat);
    }
    
    static native void nglVertexArrayAttribLFormat(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glVertexArrayAttribBinding(final int n, final int n2, final int n3) {
        final long glVertexArrayAttribBinding = GLContext.getCapabilities().glVertexArrayAttribBinding;
        BufferChecks.checkFunctionAddress(glVertexArrayAttribBinding);
        nglVertexArrayAttribBinding(n, n2, n3, glVertexArrayAttribBinding);
    }
    
    static native void nglVertexArrayAttribBinding(final int p0, final int p1, final int p2, final long p3);
    
    public static void glVertexArrayBindingDivisor(final int n, final int n2, final int n3) {
        final long glVertexArrayBindingDivisor = GLContext.getCapabilities().glVertexArrayBindingDivisor;
        BufferChecks.checkFunctionAddress(glVertexArrayBindingDivisor);
        nglVertexArrayBindingDivisor(n, n2, n3, glVertexArrayBindingDivisor);
    }
    
    static native void nglVertexArrayBindingDivisor(final int p0, final int p1, final int p2, final long p3);
    
    public static void glGetVertexArray(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetVertexArrayiv = GLContext.getCapabilities().glGetVertexArrayiv;
        BufferChecks.checkFunctionAddress(glGetVertexArrayiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetVertexArrayiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetVertexArrayiv);
    }
    
    static native void nglGetVertexArrayiv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetVertexArray(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetVertexArrayiv = capabilities.glGetVertexArrayiv;
        BufferChecks.checkFunctionAddress(glGetVertexArrayiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetVertexArrayiv(n, n2, MemoryUtil.getAddress(bufferInt), glGetVertexArrayiv);
        return bufferInt.get(0);
    }
    
    public static void glGetVertexArrayIndexed(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetVertexArrayIndexediv = GLContext.getCapabilities().glGetVertexArrayIndexediv;
        BufferChecks.checkFunctionAddress(glGetVertexArrayIndexediv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetVertexArrayIndexediv(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetVertexArrayIndexediv);
    }
    
    static native void nglGetVertexArrayIndexediv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetVertexArrayIndexed(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetVertexArrayIndexediv = capabilities.glGetVertexArrayIndexediv;
        BufferChecks.checkFunctionAddress(glGetVertexArrayIndexediv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetVertexArrayIndexediv(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetVertexArrayIndexediv);
        return bufferInt.get(0);
    }
    
    public static void glGetVertexArrayIndexed64i(final int n, final int n2, final int n3, final LongBuffer longBuffer) {
        final long glGetVertexArrayIndexed64iv = GLContext.getCapabilities().glGetVertexArrayIndexed64iv;
        BufferChecks.checkFunctionAddress(glGetVertexArrayIndexed64iv);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglGetVertexArrayIndexed64iv(n, n2, n3, MemoryUtil.getAddress(longBuffer), glGetVertexArrayIndexed64iv);
    }
    
    static native void nglGetVertexArrayIndexed64iv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static long glGetVertexArrayIndexed64i(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetVertexArrayIndexed64iv = capabilities.glGetVertexArrayIndexed64iv;
        BufferChecks.checkFunctionAddress(glGetVertexArrayIndexed64iv);
        final LongBuffer bufferLong = APIUtil.getBufferLong(capabilities);
        nglGetVertexArrayIndexed64iv(n, n2, n3, MemoryUtil.getAddress(bufferLong), glGetVertexArrayIndexed64iv);
        return bufferLong.get(0);
    }
    
    public static void glCreateSamplers(final IntBuffer intBuffer) {
        final long glCreateSamplers = GLContext.getCapabilities().glCreateSamplers;
        BufferChecks.checkFunctionAddress(glCreateSamplers);
        BufferChecks.checkDirect(intBuffer);
        nglCreateSamplers(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glCreateSamplers);
    }
    
    static native void nglCreateSamplers(final int p0, final long p1, final long p2);
    
    public static int glCreateSamplers() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCreateSamplers = capabilities.glCreateSamplers;
        BufferChecks.checkFunctionAddress(glCreateSamplers);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglCreateSamplers(1, MemoryUtil.getAddress(bufferInt), glCreateSamplers);
        return bufferInt.get(0);
    }
    
    public static void glCreateProgramPipelines(final IntBuffer intBuffer) {
        final long glCreateProgramPipelines = GLContext.getCapabilities().glCreateProgramPipelines;
        BufferChecks.checkFunctionAddress(glCreateProgramPipelines);
        BufferChecks.checkDirect(intBuffer);
        nglCreateProgramPipelines(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glCreateProgramPipelines);
    }
    
    static native void nglCreateProgramPipelines(final int p0, final long p1, final long p2);
    
    public static int glCreateProgramPipelines() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCreateProgramPipelines = capabilities.glCreateProgramPipelines;
        BufferChecks.checkFunctionAddress(glCreateProgramPipelines);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglCreateProgramPipelines(1, MemoryUtil.getAddress(bufferInt), glCreateProgramPipelines);
        return bufferInt.get(0);
    }
    
    public static void glCreateQueries(final int n, final IntBuffer intBuffer) {
        final long glCreateQueries = GLContext.getCapabilities().glCreateQueries;
        BufferChecks.checkFunctionAddress(glCreateQueries);
        BufferChecks.checkDirect(intBuffer);
        nglCreateQueries(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glCreateQueries);
    }
    
    static native void nglCreateQueries(final int p0, final int p1, final long p2, final long p3);
    
    public static int glCreateQueries(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCreateQueries = capabilities.glCreateQueries;
        BufferChecks.checkFunctionAddress(glCreateQueries);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglCreateQueries(n, 1, MemoryUtil.getAddress(bufferInt), glCreateQueries);
        return bufferInt.get(0);
    }
    
    public static void glMemoryBarrierByRegion(final int n) {
        final long glMemoryBarrierByRegion = GLContext.getCapabilities().glMemoryBarrierByRegion;
        BufferChecks.checkFunctionAddress(glMemoryBarrierByRegion);
        nglMemoryBarrierByRegion(n, glMemoryBarrierByRegion);
    }
    
    static native void nglMemoryBarrierByRegion(final int p0, final long p1);
    
    public static void glGetTextureSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureSubImage = capabilities.glGetTextureSubImage;
        BufferChecks.checkFunctionAddress(glGetTextureSubImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglGetTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glGetTextureSubImage);
    }
    
    public static void glGetTextureSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureSubImage = capabilities.glGetTextureSubImage;
        BufferChecks.checkFunctionAddress(glGetTextureSubImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glGetTextureSubImage);
    }
    
    public static void glGetTextureSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureSubImage = capabilities.glGetTextureSubImage;
        BufferChecks.checkFunctionAddress(glGetTextureSubImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        nglGetTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glGetTextureSubImage);
    }
    
    public static void glGetTextureSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureSubImage = capabilities.glGetTextureSubImage;
        BufferChecks.checkFunctionAddress(glGetTextureSubImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglGetTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glGetTextureSubImage);
    }
    
    public static void glGetTextureSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureSubImage = capabilities.glGetTextureSubImage;
        BufferChecks.checkFunctionAddress(glGetTextureSubImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglGetTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glGetTextureSubImage);
    }
    
    static native void nglGetTextureSubImage(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final long p11, final long p12);
    
    public static void glGetTextureSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final long n12) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureSubImage = capabilities.glGetTextureSubImage;
        BufferChecks.checkFunctionAddress(glGetTextureSubImage);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetTextureSubImageBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, glGetTextureSubImage);
    }
    
    static native void nglGetTextureSubImageBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final long p11, final long p12);
    
    public static void glGetCompressedTextureSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTextureSubImage = capabilities.glGetCompressedTextureSubImage;
        BufferChecks.checkFunctionAddress(glGetCompressedTextureSubImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglGetCompressedTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glGetCompressedTextureSubImage);
    }
    
    public static void glGetCompressedTextureSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTextureSubImage = capabilities.glGetCompressedTextureSubImage;
        BufferChecks.checkFunctionAddress(glGetCompressedTextureSubImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetCompressedTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glGetCompressedTextureSubImage);
    }
    
    public static void glGetCompressedTextureSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTextureSubImage = capabilities.glGetCompressedTextureSubImage;
        BufferChecks.checkFunctionAddress(glGetCompressedTextureSubImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        nglGetCompressedTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glGetCompressedTextureSubImage);
    }
    
    public static void glGetCompressedTextureSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTextureSubImage = capabilities.glGetCompressedTextureSubImage;
        BufferChecks.checkFunctionAddress(glGetCompressedTextureSubImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglGetCompressedTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glGetCompressedTextureSubImage);
    }
    
    public static void glGetCompressedTextureSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTextureSubImage = capabilities.glGetCompressedTextureSubImage;
        BufferChecks.checkFunctionAddress(glGetCompressedTextureSubImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglGetCompressedTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glGetCompressedTextureSubImage);
    }
    
    static native void nglGetCompressedTextureSubImage(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glGetCompressedTextureSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final long n10) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTextureSubImage = capabilities.glGetCompressedTextureSubImage;
        BufferChecks.checkFunctionAddress(glGetCompressedTextureSubImage);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetCompressedTextureSubImageBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, glGetCompressedTextureSubImage);
    }
    
    static native void nglGetCompressedTextureSubImageBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glTextureBarrier() {
        final long glTextureBarrier = GLContext.getCapabilities().glTextureBarrier;
        BufferChecks.checkFunctionAddress(glTextureBarrier);
        nglTextureBarrier(glTextureBarrier);
    }
    
    static native void nglTextureBarrier(final long p0);
    
    public static int glGetGraphicsResetStatus() {
        final long glGetGraphicsResetStatus = GLContext.getCapabilities().glGetGraphicsResetStatus;
        BufferChecks.checkFunctionAddress(glGetGraphicsResetStatus);
        return nglGetGraphicsResetStatus(glGetGraphicsResetStatus);
    }
    
    static native int nglGetGraphicsResetStatus(final long p0);
    
    public static void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadnPixels = capabilities.glReadnPixels;
        BufferChecks.checkFunctionAddress(glReadnPixels);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglReadnPixels(n, n2, n3, n4, n5, n6, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glReadnPixels);
    }
    
    public static void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadnPixels = capabilities.glReadnPixels;
        BufferChecks.checkFunctionAddress(glReadnPixels);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        nglReadnPixels(n, n2, n3, n4, n5, n6, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glReadnPixels);
    }
    
    public static void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadnPixels = capabilities.glReadnPixels;
        BufferChecks.checkFunctionAddress(glReadnPixels);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        nglReadnPixels(n, n2, n3, n4, n5, n6, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glReadnPixels);
    }
    
    public static void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadnPixels = capabilities.glReadnPixels;
        BufferChecks.checkFunctionAddress(glReadnPixels);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglReadnPixels(n, n2, n3, n4, n5, n6, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glReadnPixels);
    }
    
    public static void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadnPixels = capabilities.glReadnPixels;
        BufferChecks.checkFunctionAddress(glReadnPixels);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglReadnPixels(n, n2, n3, n4, n5, n6, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glReadnPixels);
    }
    
    static native void nglReadnPixels(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadnPixels = capabilities.glReadnPixels;
        BufferChecks.checkFunctionAddress(glReadnPixels);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglReadnPixelsBO(n, n2, n3, n4, n5, n6, n7, n8, glReadnPixels);
    }
    
    static native void nglReadnPixelsBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glGetnUniform(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetnUniformfv = GLContext.getCapabilities().glGetnUniformfv;
        BufferChecks.checkFunctionAddress(glGetnUniformfv);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnUniformfv(n, n2, floatBuffer.remaining(), MemoryUtil.getAddress(floatBuffer), glGetnUniformfv);
    }
    
    static native void nglGetnUniformfv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetnUniform(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetnUniformiv = GLContext.getCapabilities().glGetnUniformiv;
        BufferChecks.checkFunctionAddress(glGetnUniformiv);
        BufferChecks.checkDirect(intBuffer);
        nglGetnUniformiv(n, n2, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGetnUniformiv);
    }
    
    static native void nglGetnUniformiv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetnUniformu(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetnUniformuiv = GLContext.getCapabilities().glGetnUniformuiv;
        BufferChecks.checkFunctionAddress(glGetnUniformuiv);
        BufferChecks.checkDirect(intBuffer);
        nglGetnUniformuiv(n, n2, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGetnUniformuiv);
    }
    
    static native void nglGetnUniformuiv(final int p0, final int p1, final int p2, final long p3, final long p4);
}
