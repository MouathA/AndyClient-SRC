package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class GL42
{
    public static final int GL_COMPRESSED_RGBA_BPTC_UNORM = 36492;
    public static final int GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM = 36493;
    public static final int GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT = 36494;
    public static final int GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT = 36495;
    public static final int GL_UNPACK_COMPRESSED_BLOCK_WIDTH = 37159;
    public static final int GL_UNPACK_COMPRESSED_BLOCK_HEIGHT = 37160;
    public static final int GL_UNPACK_COMPRESSED_BLOCK_DEPTH = 37161;
    public static final int GL_UNPACK_COMPRESSED_BLOCK_SIZE = 37162;
    public static final int GL_PACK_COMPRESSED_BLOCK_WIDTH = 37163;
    public static final int GL_PACK_COMPRESSED_BLOCK_HEIGHT = 37164;
    public static final int GL_PACK_COMPRESSED_BLOCK_DEPTH = 37165;
    public static final int GL_PACK_COMPRESSED_BLOCK_SIZE = 37166;
    public static final int GL_ATOMIC_COUNTER_BUFFER = 37568;
    public static final int GL_ATOMIC_COUNTER_BUFFER_BINDING = 37569;
    public static final int GL_ATOMIC_COUNTER_BUFFER_START = 37570;
    public static final int GL_ATOMIC_COUNTER_BUFFER_SIZE = 37571;
    public static final int GL_ATOMIC_COUNTER_BUFFER_DATA_SIZE = 37572;
    public static final int GL_ATOMIC_COUNTER_BUFFER_ACTIVE_ATOMIC_COUNTERS = 37573;
    public static final int GL_ATOMIC_COUNTER_BUFFER_ACTIVE_ATOMIC_COUNTER_INDICES = 37574;
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_VERTEX_SHADER = 37575;
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_TESS_CONTROL_SHADER = 37576;
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_TESS_EVALUATION_SHADER = 37577;
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_GEOMETRY_SHADER = 37578;
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_FRAGMENT_SHADER = 37579;
    public static final int GL_MAX_VERTEX_ATOMIC_COUNTER_BUFFERS = 37580;
    public static final int GL_MAX_TESS_CONTROL_ATOMIC_COUNTER_BUFFERS = 37581;
    public static final int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTER_BUFFERS = 37582;
    public static final int GL_MAX_GEOMETRY_ATOMIC_COUNTER_BUFFERS = 37583;
    public static final int GL_MAX_FRAGMENT_ATOMIC_COUNTER_BUFFERS = 37584;
    public static final int GL_MAX_COMBINED_ATOMIC_COUNTER_BUFFERS = 37585;
    public static final int GL_MAX_VERTEX_ATOMIC_COUNTERS = 37586;
    public static final int GL_MAX_TESS_CONTROL_ATOMIC_COUNTERS = 37587;
    public static final int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTERS = 37588;
    public static final int GL_MAX_GEOMETRY_ATOMIC_COUNTERS = 37589;
    public static final int GL_MAX_FRAGMENT_ATOMIC_COUNTERS = 37590;
    public static final int GL_MAX_COMBINED_ATOMIC_COUNTERS = 37591;
    public static final int GL_MAX_ATOMIC_COUNTER_BUFFER_SIZE = 37592;
    public static final int GL_MAX_ATOMIC_COUNTER_BUFFER_BINDINGS = 37596;
    public static final int GL_ACTIVE_ATOMIC_COUNTER_BUFFERS = 37593;
    public static final int GL_UNIFORM_ATOMIC_COUNTER_BUFFER_INDEX = 37594;
    public static final int GL_UNSIGNED_INT_ATOMIC_COUNTER = 37595;
    public static final int GL_TEXTURE_IMMUTABLE_FORMAT = 37167;
    public static final int GL_MAX_IMAGE_UNITS = 36664;
    public static final int GL_MAX_COMBINED_IMAGE_UNITS_AND_FRAGMENT_OUTPUTS = 36665;
    public static final int GL_MAX_IMAGE_SAMPLES = 36973;
    public static final int GL_MAX_VERTEX_IMAGE_UNIFORMS = 37066;
    public static final int GL_MAX_TESS_CONTROL_IMAGE_UNIFORMS = 37067;
    public static final int GL_MAX_TESS_EVALUATION_IMAGE_UNIFORMS = 37068;
    public static final int GL_MAX_GEOMETRY_IMAGE_UNIFORMS = 37069;
    public static final int GL_MAX_FRAGMENT_IMAGE_UNIFORMS = 37070;
    public static final int GL_MAX_COMBINED_IMAGE_UNIFORMS = 37071;
    public static final int GL_IMAGE_BINDING_NAME = 36666;
    public static final int GL_IMAGE_BINDING_LEVEL = 36667;
    public static final int GL_IMAGE_BINDING_LAYERED = 36668;
    public static final int GL_IMAGE_BINDING_LAYER = 36669;
    public static final int GL_IMAGE_BINDING_ACCESS = 36670;
    public static final int GL_IMAGE_BINDING_FORMAT = 36974;
    public static final int GL_VERTEX_ATTRIB_ARRAY_BARRIER_BIT = 1;
    public static final int GL_ELEMENT_ARRAY_BARRIER_BIT = 2;
    public static final int GL_UNIFORM_BARRIER_BIT = 4;
    public static final int GL_TEXTURE_FETCH_BARRIER_BIT = 8;
    public static final int GL_SHADER_IMAGE_ACCESS_BARRIER_BIT = 32;
    public static final int GL_COMMAND_BARRIER_BIT = 64;
    public static final int GL_PIXEL_BUFFER_BARRIER_BIT = 128;
    public static final int GL_TEXTURE_UPDATE_BARRIER_BIT = 256;
    public static final int GL_BUFFER_UPDATE_BARRIER_BIT = 512;
    public static final int GL_FRAMEBUFFER_BARRIER_BIT = 1024;
    public static final int GL_TRANSFORM_FEEDBACK_BARRIER_BIT = 2048;
    public static final int GL_ATOMIC_COUNTER_BARRIER_BIT = 4096;
    public static final int GL_ALL_BARRIER_BITS = -1;
    public static final int GL_IMAGE_1D = 36940;
    public static final int GL_IMAGE_2D = 36941;
    public static final int GL_IMAGE_3D = 36942;
    public static final int GL_IMAGE_2D_RECT = 36943;
    public static final int GL_IMAGE_CUBE = 36944;
    public static final int GL_IMAGE_BUFFER = 36945;
    public static final int GL_IMAGE_1D_ARRAY = 36946;
    public static final int GL_IMAGE_2D_ARRAY = 36947;
    public static final int GL_IMAGE_CUBE_MAP_ARRAY = 36948;
    public static final int GL_IMAGE_2D_MULTISAMPLE = 36949;
    public static final int GL_IMAGE_2D_MULTISAMPLE_ARRAY = 36950;
    public static final int GL_INT_IMAGE_1D = 36951;
    public static final int GL_INT_IMAGE_2D = 36952;
    public static final int GL_INT_IMAGE_3D = 36953;
    public static final int GL_INT_IMAGE_2D_RECT = 36954;
    public static final int GL_INT_IMAGE_CUBE = 36955;
    public static final int GL_INT_IMAGE_BUFFER = 36956;
    public static final int GL_INT_IMAGE_1D_ARRAY = 36957;
    public static final int GL_INT_IMAGE_2D_ARRAY = 36958;
    public static final int GL_INT_IMAGE_CUBE_MAP_ARRAY = 36959;
    public static final int GL_INT_IMAGE_2D_MULTISAMPLE = 36960;
    public static final int GL_INT_IMAGE_2D_MULTISAMPLE_ARRAY = 36961;
    public static final int GL_UNSIGNED_INT_IMAGE_1D = 36962;
    public static final int GL_UNSIGNED_INT_IMAGE_2D = 36963;
    public static final int GL_UNSIGNED_INT_IMAGE_3D = 36964;
    public static final int GL_UNSIGNED_INT_IMAGE_2D_RECT = 36965;
    public static final int GL_UNSIGNED_INT_IMAGE_CUBE = 36966;
    public static final int GL_UNSIGNED_INT_IMAGE_BUFFER = 36967;
    public static final int GL_UNSIGNED_INT_IMAGE_1D_ARRAY = 36968;
    public static final int GL_UNSIGNED_INT_IMAGE_2D_ARRAY = 36969;
    public static final int GL_UNSIGNED_INT_IMAGE_CUBE_MAP_ARRAY = 36970;
    public static final int GL_UNSIGNED_INT_IMAGE_2D_MULTISAMPLE = 36971;
    public static final int GL_UNSIGNED_INT_IMAGE_2D_MULTISAMPLE_ARRAY = 36972;
    public static final int GL_IMAGE_FORMAT_COMPATIBILITY_TYPE = 37063;
    public static final int GL_IMAGE_FORMAT_COMPATIBILITY_BY_SIZE = 37064;
    public static final int IMAGE_FORMAT_COMPATIBILITY_BY_CLASS = 37065;
    public static final int GL_NUM_SAMPLE_COUNTS = 37760;
    public static final int GL_MIN_MAP_BUFFER_ALIGNMENT = 37052;
    
    private GL42() {
    }
    
    public static void glGetActiveAtomicCounterBuffer(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetActiveAtomicCounterBufferiv = GLContext.getCapabilities().glGetActiveAtomicCounterBufferiv;
        BufferChecks.checkFunctionAddress(glGetActiveAtomicCounterBufferiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetActiveAtomicCounterBufferiv(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetActiveAtomicCounterBufferiv);
    }
    
    static native void nglGetActiveAtomicCounterBufferiv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetActiveAtomicCounterBuffer(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveAtomicCounterBufferiv = capabilities.glGetActiveAtomicCounterBufferiv;
        BufferChecks.checkFunctionAddress(glGetActiveAtomicCounterBufferiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetActiveAtomicCounterBufferiv(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetActiveAtomicCounterBufferiv);
        return bufferInt.get(0);
    }
    
    public static void glTexStorage1D(final int n, final int n2, final int n3, final int n4) {
        final long glTexStorage1D = GLContext.getCapabilities().glTexStorage1D;
        BufferChecks.checkFunctionAddress(glTexStorage1D);
        nglTexStorage1D(n, n2, n3, n4, glTexStorage1D);
    }
    
    static native void nglTexStorage1D(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glTexStorage2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glTexStorage2D = GLContext.getCapabilities().glTexStorage2D;
        BufferChecks.checkFunctionAddress(glTexStorage2D);
        nglTexStorage2D(n, n2, n3, n4, n5, glTexStorage2D);
    }
    
    static native void nglTexStorage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glTexStorage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glTexStorage3D = GLContext.getCapabilities().glTexStorage3D;
        BufferChecks.checkFunctionAddress(glTexStorage3D);
        nglTexStorage3D(n, n2, n3, n4, n5, n6, glTexStorage3D);
    }
    
    static native void nglTexStorage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glDrawTransformFeedbackInstanced(final int n, final int n2, final int n3) {
        final long glDrawTransformFeedbackInstanced = GLContext.getCapabilities().glDrawTransformFeedbackInstanced;
        BufferChecks.checkFunctionAddress(glDrawTransformFeedbackInstanced);
        nglDrawTransformFeedbackInstanced(n, n2, n3, glDrawTransformFeedbackInstanced);
    }
    
    static native void nglDrawTransformFeedbackInstanced(final int p0, final int p1, final int p2, final long p3);
    
    public static void glDrawTransformFeedbackStreamInstanced(final int n, final int n2, final int n3, final int n4) {
        final long glDrawTransformFeedbackStreamInstanced = GLContext.getCapabilities().glDrawTransformFeedbackStreamInstanced;
        BufferChecks.checkFunctionAddress(glDrawTransformFeedbackStreamInstanced);
        nglDrawTransformFeedbackStreamInstanced(n, n2, n3, n4, glDrawTransformFeedbackStreamInstanced);
    }
    
    static native void nglDrawTransformFeedbackStreamInstanced(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glDrawArraysInstancedBaseInstance(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glDrawArraysInstancedBaseInstance = GLContext.getCapabilities().glDrawArraysInstancedBaseInstance;
        BufferChecks.checkFunctionAddress(glDrawArraysInstancedBaseInstance);
        nglDrawArraysInstancedBaseInstance(n, n2, n3, n4, n5, glDrawArraysInstancedBaseInstance);
    }
    
    static native void nglDrawArraysInstancedBaseInstance(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glDrawElementsInstancedBaseInstance(final int n, final ByteBuffer byteBuffer, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedBaseInstance = capabilities.glDrawElementsInstancedBaseInstance;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedBaseInstance);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglDrawElementsInstancedBaseInstance(n, byteBuffer.remaining(), 5121, MemoryUtil.getAddress(byteBuffer), n2, n3, glDrawElementsInstancedBaseInstance);
    }
    
    public static void glDrawElementsInstancedBaseInstance(final int n, final IntBuffer intBuffer, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedBaseInstance = capabilities.glDrawElementsInstancedBaseInstance;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedBaseInstance);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglDrawElementsInstancedBaseInstance(n, intBuffer.remaining(), 5125, MemoryUtil.getAddress(intBuffer), n2, n3, glDrawElementsInstancedBaseInstance);
    }
    
    public static void glDrawElementsInstancedBaseInstance(final int n, final ShortBuffer shortBuffer, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedBaseInstance = capabilities.glDrawElementsInstancedBaseInstance;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedBaseInstance);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglDrawElementsInstancedBaseInstance(n, shortBuffer.remaining(), 5123, MemoryUtil.getAddress(shortBuffer), n2, n3, glDrawElementsInstancedBaseInstance);
    }
    
    static native void nglDrawElementsInstancedBaseInstance(final int p0, final int p1, final int p2, final long p3, final int p4, final int p5, final long p6);
    
    public static void glDrawElementsInstancedBaseInstance(final int n, final int n2, final int n3, final long n4, final int n5, final int n6) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedBaseInstance = capabilities.glDrawElementsInstancedBaseInstance;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedBaseInstance);
        GLChecks.ensureElementVBOenabled(capabilities);
        nglDrawElementsInstancedBaseInstanceBO(n, n2, n3, n4, n5, n6, glDrawElementsInstancedBaseInstance);
    }
    
    static native void nglDrawElementsInstancedBaseInstanceBO(final int p0, final int p1, final int p2, final long p3, final int p4, final int p5, final long p6);
    
    public static void glDrawElementsInstancedBaseVertexBaseInstance(final int n, final ByteBuffer byteBuffer, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedBaseVertexBaseInstance = capabilities.glDrawElementsInstancedBaseVertexBaseInstance;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedBaseVertexBaseInstance);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglDrawElementsInstancedBaseVertexBaseInstance(n, byteBuffer.remaining(), 5121, MemoryUtil.getAddress(byteBuffer), n2, n3, n4, glDrawElementsInstancedBaseVertexBaseInstance);
    }
    
    public static void glDrawElementsInstancedBaseVertexBaseInstance(final int n, final IntBuffer intBuffer, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedBaseVertexBaseInstance = capabilities.glDrawElementsInstancedBaseVertexBaseInstance;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedBaseVertexBaseInstance);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglDrawElementsInstancedBaseVertexBaseInstance(n, intBuffer.remaining(), 5125, MemoryUtil.getAddress(intBuffer), n2, n3, n4, glDrawElementsInstancedBaseVertexBaseInstance);
    }
    
    public static void glDrawElementsInstancedBaseVertexBaseInstance(final int n, final ShortBuffer shortBuffer, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedBaseVertexBaseInstance = capabilities.glDrawElementsInstancedBaseVertexBaseInstance;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedBaseVertexBaseInstance);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglDrawElementsInstancedBaseVertexBaseInstance(n, shortBuffer.remaining(), 5123, MemoryUtil.getAddress(shortBuffer), n2, n3, n4, glDrawElementsInstancedBaseVertexBaseInstance);
    }
    
    static native void nglDrawElementsInstancedBaseVertexBaseInstance(final int p0, final int p1, final int p2, final long p3, final int p4, final int p5, final int p6, final long p7);
    
    public static void glDrawElementsInstancedBaseVertexBaseInstance(final int n, final int n2, final int n3, final long n4, final int n5, final int n6, final int n7) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedBaseVertexBaseInstance = capabilities.glDrawElementsInstancedBaseVertexBaseInstance;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedBaseVertexBaseInstance);
        GLChecks.ensureElementVBOenabled(capabilities);
        nglDrawElementsInstancedBaseVertexBaseInstanceBO(n, n2, n3, n4, n5, n6, n7, glDrawElementsInstancedBaseVertexBaseInstance);
    }
    
    static native void nglDrawElementsInstancedBaseVertexBaseInstanceBO(final int p0, final int p1, final int p2, final long p3, final int p4, final int p5, final int p6, final long p7);
    
    public static void glBindImageTexture(final int n, final int n2, final int n3, final boolean b, final int n4, final int n5, final int n6) {
        final long glBindImageTexture = GLContext.getCapabilities().glBindImageTexture;
        BufferChecks.checkFunctionAddress(glBindImageTexture);
        nglBindImageTexture(n, n2, n3, b, n4, n5, n6, glBindImageTexture);
    }
    
    static native void nglBindImageTexture(final int p0, final int p1, final int p2, final boolean p3, final int p4, final int p5, final int p6, final long p7);
    
    public static void glMemoryBarrier(final int n) {
        final long glMemoryBarrier = GLContext.getCapabilities().glMemoryBarrier;
        BufferChecks.checkFunctionAddress(glMemoryBarrier);
        nglMemoryBarrier(n, glMemoryBarrier);
    }
    
    static native void nglMemoryBarrier(final int p0, final long p1);
    
    public static void glGetInternalformat(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetInternalformativ = GLContext.getCapabilities().glGetInternalformativ;
        BufferChecks.checkFunctionAddress(glGetInternalformativ);
        BufferChecks.checkDirect(intBuffer);
        nglGetInternalformativ(n, n2, n3, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGetInternalformativ);
    }
    
    static native void nglGetInternalformativ(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static int glGetInternalformat(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetInternalformativ = capabilities.glGetInternalformativ;
        BufferChecks.checkFunctionAddress(glGetInternalformativ);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetInternalformativ(n, n2, n3, 1, MemoryUtil.getAddress(bufferInt), glGetInternalformativ);
        return bufferInt.get(0);
    }
}
