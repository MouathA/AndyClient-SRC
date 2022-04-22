package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class EXTTransformFeedback
{
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_EXT = 35982;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_START_EXT = 35972;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_SIZE_EXT = 35973;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_BINDING_EXT = 35983;
    public static final int GL_INTERLEAVED_ATTRIBS_EXT = 35980;
    public static final int GL_SEPARATE_ATTRIBS_EXT = 35981;
    public static final int GL_PRIMITIVES_GENERATED_EXT = 35975;
    public static final int GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN_EXT = 35976;
    public static final int GL_RASTERIZER_DISCARD_EXT = 35977;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS_EXT = 35978;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS_EXT = 35979;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS_EXT = 35968;
    public static final int GL_TRANSFORM_FEEDBACK_VARYINGS_EXT = 35971;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_MODE_EXT = 35967;
    public static final int GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH_EXT = 35958;
    
    private EXTTransformFeedback() {
    }
    
    public static void glBindBufferRangeEXT(final int n, final int n2, final int n3, final long n4, final long n5) {
        final long glBindBufferRangeEXT = GLContext.getCapabilities().glBindBufferRangeEXT;
        BufferChecks.checkFunctionAddress(glBindBufferRangeEXT);
        nglBindBufferRangeEXT(n, n2, n3, n4, n5, glBindBufferRangeEXT);
    }
    
    static native void nglBindBufferRangeEXT(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5);
    
    public static void glBindBufferOffsetEXT(final int n, final int n2, final int n3, final long n4) {
        final long glBindBufferOffsetEXT = GLContext.getCapabilities().glBindBufferOffsetEXT;
        BufferChecks.checkFunctionAddress(glBindBufferOffsetEXT);
        nglBindBufferOffsetEXT(n, n2, n3, n4, glBindBufferOffsetEXT);
    }
    
    static native void nglBindBufferOffsetEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glBindBufferBaseEXT(final int n, final int n2, final int n3) {
        final long glBindBufferBaseEXT = GLContext.getCapabilities().glBindBufferBaseEXT;
        BufferChecks.checkFunctionAddress(glBindBufferBaseEXT);
        nglBindBufferBaseEXT(n, n2, n3, glBindBufferBaseEXT);
    }
    
    static native void nglBindBufferBaseEXT(final int p0, final int p1, final int p2, final long p3);
    
    public static void glBeginTransformFeedbackEXT(final int n) {
        final long glBeginTransformFeedbackEXT = GLContext.getCapabilities().glBeginTransformFeedbackEXT;
        BufferChecks.checkFunctionAddress(glBeginTransformFeedbackEXT);
        nglBeginTransformFeedbackEXT(n, glBeginTransformFeedbackEXT);
    }
    
    static native void nglBeginTransformFeedbackEXT(final int p0, final long p1);
    
    public static void glEndTransformFeedbackEXT() {
        final long glEndTransformFeedbackEXT = GLContext.getCapabilities().glEndTransformFeedbackEXT;
        BufferChecks.checkFunctionAddress(glEndTransformFeedbackEXT);
        nglEndTransformFeedbackEXT(glEndTransformFeedbackEXT);
    }
    
    static native void nglEndTransformFeedbackEXT(final long p0);
    
    public static void glTransformFeedbackVaryingsEXT(final int n, final int n2, final ByteBuffer byteBuffer, final int n3) {
        final long glTransformFeedbackVaryingsEXT = GLContext.getCapabilities().glTransformFeedbackVaryingsEXT;
        BufferChecks.checkFunctionAddress(glTransformFeedbackVaryingsEXT);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer, n2);
        nglTransformFeedbackVaryingsEXT(n, n2, MemoryUtil.getAddress(byteBuffer), n3, glTransformFeedbackVaryingsEXT);
    }
    
    static native void nglTransformFeedbackVaryingsEXT(final int p0, final int p1, final long p2, final int p3, final long p4);
    
    public static void glTransformFeedbackVaryingsEXT(final int n, final CharSequence[] array, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTransformFeedbackVaryingsEXT = capabilities.glTransformFeedbackVaryingsEXT;
        BufferChecks.checkFunctionAddress(glTransformFeedbackVaryingsEXT);
        BufferChecks.checkArray(array);
        nglTransformFeedbackVaryingsEXT(n, array.length, APIUtil.getBufferNT(capabilities, array), n2, glTransformFeedbackVaryingsEXT);
    }
    
    public static void glGetTransformFeedbackVaryingEXT(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final ByteBuffer byteBuffer) {
        final long glGetTransformFeedbackVaryingEXT = GLContext.getCapabilities().glGetTransformFeedbackVaryingEXT;
        BufferChecks.checkFunctionAddress(glGetTransformFeedbackVaryingEXT);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkBuffer(intBuffer2, 1);
        BufferChecks.checkBuffer(intBuffer3, 1);
        BufferChecks.checkDirect(byteBuffer);
        nglGetTransformFeedbackVaryingEXT(n, n2, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(intBuffer3), MemoryUtil.getAddress(byteBuffer), glGetTransformFeedbackVaryingEXT);
    }
    
    static native void nglGetTransformFeedbackVaryingEXT(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5, final long p6, final long p7);
    
    public static String glGetTransformFeedbackVaryingEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTransformFeedbackVaryingEXT = capabilities.glGetTransformFeedbackVaryingEXT;
        BufferChecks.checkFunctionAddress(glGetTransformFeedbackVaryingEXT);
        BufferChecks.checkBuffer(intBuffer, 1);
        BufferChecks.checkBuffer(intBuffer2, 1);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n3);
        nglGetTransformFeedbackVaryingEXT(n, n2, n3, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(bufferByte), glGetTransformFeedbackVaryingEXT);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
}
