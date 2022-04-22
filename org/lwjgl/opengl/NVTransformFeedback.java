package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class NVTransformFeedback
{
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_NV = 35982;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_START_NV = 35972;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_SIZE_NV = 35973;
    public static final int GL_TRANSFORM_FEEDBACK_RECORD_NV = 35974;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_BINDING_NV = 35983;
    public static final int GL_INTERLEAVED_ATTRIBS_NV = 35980;
    public static final int GL_SEPARATE_ATTRIBS_NV = 35981;
    public static final int GL_PRIMITIVES_GENERATED_NV = 35975;
    public static final int GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN_NV = 35976;
    public static final int GL_RASTERIZER_DISCARD_NV = 35977;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS_NV = 35978;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS_NV = 35979;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS_NV = 35968;
    public static final int GL_TRANSFORM_FEEDBACK_ATTRIBS_NV = 35966;
    public static final int GL_ACTIVE_VARYINGS_NV = 35969;
    public static final int GL_ACTIVE_VARYING_MAX_LENGTH_NV = 35970;
    public static final int GL_TRANSFORM_FEEDBACK_VARYINGS_NV = 35971;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_MODE_NV = 35967;
    public static final int GL_BACK_PRIMARY_COLOR_NV = 35959;
    public static final int GL_BACK_SECONDARY_COLOR_NV = 35960;
    public static final int GL_TEXTURE_COORD_NV = 35961;
    public static final int GL_CLIP_DISTANCE_NV = 35962;
    public static final int GL_VERTEX_ID_NV = 35963;
    public static final int GL_PRIMITIVE_ID_NV = 35964;
    public static final int GL_GENERIC_ATTRIB_NV = 35965;
    public static final int GL_LAYER_NV = 36266;
    
    private NVTransformFeedback() {
    }
    
    public static void glBindBufferRangeNV(final int n, final int n2, final int n3, final long n4, final long n5) {
        final long glBindBufferRangeNV = GLContext.getCapabilities().glBindBufferRangeNV;
        BufferChecks.checkFunctionAddress(glBindBufferRangeNV);
        nglBindBufferRangeNV(n, n2, n3, n4, n5, glBindBufferRangeNV);
    }
    
    static native void nglBindBufferRangeNV(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5);
    
    public static void glBindBufferOffsetNV(final int n, final int n2, final int n3, final long n4) {
        final long glBindBufferOffsetNV = GLContext.getCapabilities().glBindBufferOffsetNV;
        BufferChecks.checkFunctionAddress(glBindBufferOffsetNV);
        nglBindBufferOffsetNV(n, n2, n3, n4, glBindBufferOffsetNV);
    }
    
    static native void nglBindBufferOffsetNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glBindBufferBaseNV(final int n, final int n2, final int n3) {
        final long glBindBufferBaseNV = GLContext.getCapabilities().glBindBufferBaseNV;
        BufferChecks.checkFunctionAddress(glBindBufferBaseNV);
        nglBindBufferBaseNV(n, n2, n3, glBindBufferBaseNV);
    }
    
    static native void nglBindBufferBaseNV(final int p0, final int p1, final int p2, final long p3);
    
    public static void glTransformFeedbackAttribsNV(final IntBuffer intBuffer, final int n) {
        final long glTransformFeedbackAttribsNV = GLContext.getCapabilities().glTransformFeedbackAttribsNV;
        BufferChecks.checkFunctionAddress(glTransformFeedbackAttribsNV);
        BufferChecks.checkBuffer(intBuffer, 3);
        nglTransformFeedbackAttribsNV(intBuffer.remaining() / 3, MemoryUtil.getAddress(intBuffer), n, glTransformFeedbackAttribsNV);
    }
    
    static native void nglTransformFeedbackAttribsNV(final int p0, final long p1, final int p2, final long p3);
    
    public static void glTransformFeedbackVaryingsNV(final int n, final IntBuffer intBuffer, final int n2) {
        final long glTransformFeedbackVaryingsNV = GLContext.getCapabilities().glTransformFeedbackVaryingsNV;
        BufferChecks.checkFunctionAddress(glTransformFeedbackVaryingsNV);
        BufferChecks.checkDirect(intBuffer);
        nglTransformFeedbackVaryingsNV(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), n2, glTransformFeedbackVaryingsNV);
    }
    
    static native void nglTransformFeedbackVaryingsNV(final int p0, final int p1, final long p2, final int p3, final long p4);
    
    public static void glBeginTransformFeedbackNV(final int n) {
        final long glBeginTransformFeedbackNV = GLContext.getCapabilities().glBeginTransformFeedbackNV;
        BufferChecks.checkFunctionAddress(glBeginTransformFeedbackNV);
        nglBeginTransformFeedbackNV(n, glBeginTransformFeedbackNV);
    }
    
    static native void nglBeginTransformFeedbackNV(final int p0, final long p1);
    
    public static void glEndTransformFeedbackNV() {
        final long glEndTransformFeedbackNV = GLContext.getCapabilities().glEndTransformFeedbackNV;
        BufferChecks.checkFunctionAddress(glEndTransformFeedbackNV);
        nglEndTransformFeedbackNV(glEndTransformFeedbackNV);
    }
    
    static native void nglEndTransformFeedbackNV(final long p0);
    
    public static int glGetVaryingLocationNV(final int n, final ByteBuffer byteBuffer) {
        final long glGetVaryingLocationNV = GLContext.getCapabilities().glGetVaryingLocationNV;
        BufferChecks.checkFunctionAddress(glGetVaryingLocationNV);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        return nglGetVaryingLocationNV(n, MemoryUtil.getAddress(byteBuffer), glGetVaryingLocationNV);
    }
    
    static native int nglGetVaryingLocationNV(final int p0, final long p1, final long p2);
    
    public static int glGetVaryingLocationNV(final int n, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetVaryingLocationNV = capabilities.glGetVaryingLocationNV;
        BufferChecks.checkFunctionAddress(glGetVaryingLocationNV);
        return nglGetVaryingLocationNV(n, APIUtil.getBufferNT(capabilities, charSequence), glGetVaryingLocationNV);
    }
    
    public static void glGetActiveVaryingNV(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final ByteBuffer byteBuffer) {
        final long glGetActiveVaryingNV = GLContext.getCapabilities().glGetActiveVaryingNV;
        BufferChecks.checkFunctionAddress(glGetActiveVaryingNV);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkBuffer(intBuffer2, 1);
        BufferChecks.checkBuffer(intBuffer3, 1);
        BufferChecks.checkDirect(byteBuffer);
        nglGetActiveVaryingNV(n, n2, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(intBuffer3), MemoryUtil.getAddress(byteBuffer), glGetActiveVaryingNV);
    }
    
    static native void nglGetActiveVaryingNV(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5, final long p6, final long p7);
    
    public static String glGetActiveVaryingNV(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveVaryingNV = capabilities.glGetActiveVaryingNV;
        BufferChecks.checkFunctionAddress(glGetActiveVaryingNV);
        BufferChecks.checkBuffer(intBuffer, 2);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n3);
        nglGetActiveVaryingNV(n, n2, n3, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer, intBuffer.position() + 1), MemoryUtil.getAddress(bufferByte), glGetActiveVaryingNV);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static String glGetActiveVaryingNV(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveVaryingNV = capabilities.glGetActiveVaryingNV;
        BufferChecks.checkFunctionAddress(glGetActiveVaryingNV);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n3);
        nglGetActiveVaryingNV(n, n2, n3, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress0(APIUtil.getBufferInt(capabilities)), MemoryUtil.getAddress(APIUtil.getBufferInt(capabilities), 1), MemoryUtil.getAddress(bufferByte), glGetActiveVaryingNV);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static int glGetActiveVaryingSizeNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveVaryingNV = capabilities.glGetActiveVaryingNV;
        BufferChecks.checkFunctionAddress(glGetActiveVaryingNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetActiveVaryingNV(n, n2, 0, 0L, MemoryUtil.getAddress(bufferInt), MemoryUtil.getAddress(bufferInt, 1), APIUtil.getBufferByte0(capabilities), glGetActiveVaryingNV);
        return bufferInt.get(0);
    }
    
    public static int glGetActiveVaryingTypeNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveVaryingNV = capabilities.glGetActiveVaryingNV;
        BufferChecks.checkFunctionAddress(glGetActiveVaryingNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetActiveVaryingNV(n, n2, 0, 0L, MemoryUtil.getAddress(bufferInt, 1), MemoryUtil.getAddress(bufferInt), APIUtil.getBufferByte0(capabilities), glGetActiveVaryingNV);
        return bufferInt.get(0);
    }
    
    public static void glActiveVaryingNV(final int n, final ByteBuffer byteBuffer) {
        final long glActiveVaryingNV = GLContext.getCapabilities().glActiveVaryingNV;
        BufferChecks.checkFunctionAddress(glActiveVaryingNV);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        nglActiveVaryingNV(n, MemoryUtil.getAddress(byteBuffer), glActiveVaryingNV);
    }
    
    static native void nglActiveVaryingNV(final int p0, final long p1, final long p2);
    
    public static void glActiveVaryingNV(final int n, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glActiveVaryingNV = capabilities.glActiveVaryingNV;
        BufferChecks.checkFunctionAddress(glActiveVaryingNV);
        nglActiveVaryingNV(n, APIUtil.getBufferNT(capabilities, charSequence), glActiveVaryingNV);
    }
    
    public static void glGetTransformFeedbackVaryingNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetTransformFeedbackVaryingNV = GLContext.getCapabilities().glGetTransformFeedbackVaryingNV;
        BufferChecks.checkFunctionAddress(glGetTransformFeedbackVaryingNV);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetTransformFeedbackVaryingNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetTransformFeedbackVaryingNV);
    }
    
    static native void nglGetTransformFeedbackVaryingNV(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetTransformFeedbackVaryingNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTransformFeedbackVaryingNV = capabilities.glGetTransformFeedbackVaryingNV;
        BufferChecks.checkFunctionAddress(glGetTransformFeedbackVaryingNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTransformFeedbackVaryingNV(n, n2, MemoryUtil.getAddress(bufferInt), glGetTransformFeedbackVaryingNV);
        return bufferInt.get(0);
    }
}
