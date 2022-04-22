package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class GL32
{
    public static final int GL_CONTEXT_PROFILE_MASK = 37158;
    public static final int GL_CONTEXT_CORE_PROFILE_BIT = 1;
    public static final int GL_CONTEXT_COMPATIBILITY_PROFILE_BIT = 2;
    public static final int GL_MAX_VERTEX_OUTPUT_COMPONENTS = 37154;
    public static final int GL_MAX_GEOMETRY_INPUT_COMPONENTS = 37155;
    public static final int GL_MAX_GEOMETRY_OUTPUT_COMPONENTS = 37156;
    public static final int GL_MAX_FRAGMENT_INPUT_COMPONENTS = 37157;
    public static final int GL_FIRST_VERTEX_CONVENTION = 36429;
    public static final int GL_LAST_VERTEX_CONVENTION = 36430;
    public static final int GL_PROVOKING_VERTEX = 36431;
    public static final int GL_QUADS_FOLLOW_PROVOKING_VERTEX_CONVENTION = 36428;
    public static final int GL_TEXTURE_CUBE_MAP_SEAMLESS = 34895;
    public static final int GL_SAMPLE_POSITION = 36432;
    public static final int GL_SAMPLE_MASK = 36433;
    public static final int GL_SAMPLE_MASK_VALUE = 36434;
    public static final int GL_TEXTURE_2D_MULTISAMPLE = 37120;
    public static final int GL_PROXY_TEXTURE_2D_MULTISAMPLE = 37121;
    public static final int GL_TEXTURE_2D_MULTISAMPLE_ARRAY = 37122;
    public static final int GL_PROXY_TEXTURE_2D_MULTISAMPLE_ARRAY = 37123;
    public static final int GL_MAX_SAMPLE_MASK_WORDS = 36441;
    public static final int GL_MAX_COLOR_TEXTURE_SAMPLES = 37134;
    public static final int GL_MAX_DEPTH_TEXTURE_SAMPLES = 37135;
    public static final int GL_MAX_INTEGER_SAMPLES = 37136;
    public static final int GL_TEXTURE_BINDING_2D_MULTISAMPLE = 37124;
    public static final int GL_TEXTURE_BINDING_2D_MULTISAMPLE_ARRAY = 37125;
    public static final int GL_TEXTURE_SAMPLES = 37126;
    public static final int GL_TEXTURE_FIXED_SAMPLE_LOCATIONS = 37127;
    public static final int GL_SAMPLER_2D_MULTISAMPLE = 37128;
    public static final int GL_INT_SAMPLER_2D_MULTISAMPLE = 37129;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE = 37130;
    public static final int GL_SAMPLER_2D_MULTISAMPLE_ARRAY = 37131;
    public static final int GL_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 37132;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 37133;
    public static final int GL_DEPTH_CLAMP = 34383;
    public static final int GL_GEOMETRY_SHADER = 36313;
    public static final int GL_GEOMETRY_VERTICES_OUT = 36314;
    public static final int GL_GEOMETRY_INPUT_TYPE = 36315;
    public static final int GL_GEOMETRY_OUTPUT_TYPE = 36316;
    public static final int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS = 35881;
    public static final int GL_MAX_GEOMETRY_UNIFORM_COMPONENTS = 36319;
    public static final int GL_MAX_GEOMETRY_OUTPUT_VERTICES = 36320;
    public static final int GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS = 36321;
    public static final int GL_LINES_ADJACENCY = 10;
    public static final int GL_LINE_STRIP_ADJACENCY = 11;
    public static final int GL_TRIANGLES_ADJACENCY = 12;
    public static final int GL_TRIANGLE_STRIP_ADJACENCY = 13;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS = 36264;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_LAYERED = 36263;
    public static final int GL_PROGRAM_POINT_SIZE = 34370;
    public static final int GL_MAX_SERVER_WAIT_TIMEOUT = 37137;
    public static final int GL_OBJECT_TYPE = 37138;
    public static final int GL_SYNC_CONDITION = 37139;
    public static final int GL_SYNC_STATUS = 37140;
    public static final int GL_SYNC_FLAGS = 37141;
    public static final int GL_SYNC_FENCE = 37142;
    public static final int GL_SYNC_GPU_COMMANDS_COMPLETE = 37143;
    public static final int GL_UNSIGNALED = 37144;
    public static final int GL_SIGNALED = 37145;
    public static final int GL_SYNC_FLUSH_COMMANDS_BIT = 1;
    public static final long GL_TIMEOUT_IGNORED = -1L;
    public static final int GL_ALREADY_SIGNALED = 37146;
    public static final int GL_TIMEOUT_EXPIRED = 37147;
    public static final int GL_CONDITION_SATISFIED = 37148;
    public static final int GL_WAIT_FAILED = 37149;
    
    private GL32() {
    }
    
    public static void glGetBufferParameter(final int n, final int n2, final LongBuffer longBuffer) {
        final long glGetBufferParameteri64v = GLContext.getCapabilities().glGetBufferParameteri64v;
        BufferChecks.checkFunctionAddress(glGetBufferParameteri64v);
        BufferChecks.checkBuffer(longBuffer, 4);
        nglGetBufferParameteri64v(n, n2, MemoryUtil.getAddress(longBuffer), glGetBufferParameteri64v);
    }
    
    static native void nglGetBufferParameteri64v(final int p0, final int p1, final long p2, final long p3);
    
    @Deprecated
    public static long glGetBufferParameter(final int n, final int n2) {
        return glGetBufferParameteri64(n, n2);
    }
    
    public static long glGetBufferParameteri64(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetBufferParameteri64v = capabilities.glGetBufferParameteri64v;
        BufferChecks.checkFunctionAddress(glGetBufferParameteri64v);
        final LongBuffer bufferLong = APIUtil.getBufferLong(capabilities);
        nglGetBufferParameteri64v(n, n2, MemoryUtil.getAddress(bufferLong), glGetBufferParameteri64v);
        return bufferLong.get(0);
    }
    
    public static void glDrawElementsBaseVertex(final int n, final ByteBuffer byteBuffer, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsBaseVertex = capabilities.glDrawElementsBaseVertex;
        BufferChecks.checkFunctionAddress(glDrawElementsBaseVertex);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglDrawElementsBaseVertex(n, byteBuffer.remaining(), 5121, MemoryUtil.getAddress(byteBuffer), n2, glDrawElementsBaseVertex);
    }
    
    public static void glDrawElementsBaseVertex(final int n, final IntBuffer intBuffer, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsBaseVertex = capabilities.glDrawElementsBaseVertex;
        BufferChecks.checkFunctionAddress(glDrawElementsBaseVertex);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglDrawElementsBaseVertex(n, intBuffer.remaining(), 5125, MemoryUtil.getAddress(intBuffer), n2, glDrawElementsBaseVertex);
    }
    
    public static void glDrawElementsBaseVertex(final int n, final ShortBuffer shortBuffer, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsBaseVertex = capabilities.glDrawElementsBaseVertex;
        BufferChecks.checkFunctionAddress(glDrawElementsBaseVertex);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglDrawElementsBaseVertex(n, shortBuffer.remaining(), 5123, MemoryUtil.getAddress(shortBuffer), n2, glDrawElementsBaseVertex);
    }
    
    static native void nglDrawElementsBaseVertex(final int p0, final int p1, final int p2, final long p3, final int p4, final long p5);
    
    public static void glDrawElementsBaseVertex(final int n, final int n2, final int n3, final long n4, final int n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsBaseVertex = capabilities.glDrawElementsBaseVertex;
        BufferChecks.checkFunctionAddress(glDrawElementsBaseVertex);
        GLChecks.ensureElementVBOenabled(capabilities);
        nglDrawElementsBaseVertexBO(n, n2, n3, n4, n5, glDrawElementsBaseVertex);
    }
    
    static native void nglDrawElementsBaseVertexBO(final int p0, final int p1, final int p2, final long p3, final int p4, final long p5);
    
    public static void glDrawRangeElementsBaseVertex(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawRangeElementsBaseVertex = capabilities.glDrawRangeElementsBaseVertex;
        BufferChecks.checkFunctionAddress(glDrawRangeElementsBaseVertex);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglDrawRangeElementsBaseVertex(n, n2, n3, byteBuffer.remaining(), 5121, MemoryUtil.getAddress(byteBuffer), n4, glDrawRangeElementsBaseVertex);
    }
    
    public static void glDrawRangeElementsBaseVertex(final int n, final int n2, final int n3, final IntBuffer intBuffer, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawRangeElementsBaseVertex = capabilities.glDrawRangeElementsBaseVertex;
        BufferChecks.checkFunctionAddress(glDrawRangeElementsBaseVertex);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglDrawRangeElementsBaseVertex(n, n2, n3, intBuffer.remaining(), 5125, MemoryUtil.getAddress(intBuffer), n4, glDrawRangeElementsBaseVertex);
    }
    
    public static void glDrawRangeElementsBaseVertex(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawRangeElementsBaseVertex = capabilities.glDrawRangeElementsBaseVertex;
        BufferChecks.checkFunctionAddress(glDrawRangeElementsBaseVertex);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglDrawRangeElementsBaseVertex(n, n2, n3, shortBuffer.remaining(), 5123, MemoryUtil.getAddress(shortBuffer), n4, glDrawRangeElementsBaseVertex);
    }
    
    static native void nglDrawRangeElementsBaseVertex(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final int p6, final long p7);
    
    public static void glDrawRangeElementsBaseVertex(final int n, final int n2, final int n3, final int n4, final int n5, final long n6, final int n7) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawRangeElementsBaseVertex = capabilities.glDrawRangeElementsBaseVertex;
        BufferChecks.checkFunctionAddress(glDrawRangeElementsBaseVertex);
        GLChecks.ensureElementVBOenabled(capabilities);
        nglDrawRangeElementsBaseVertexBO(n, n2, n3, n4, n5, n6, n7, glDrawRangeElementsBaseVertex);
    }
    
    static native void nglDrawRangeElementsBaseVertexBO(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final int p6, final long p7);
    
    public static void glDrawElementsInstancedBaseVertex(final int n, final ByteBuffer byteBuffer, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedBaseVertex = capabilities.glDrawElementsInstancedBaseVertex;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedBaseVertex);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglDrawElementsInstancedBaseVertex(n, byteBuffer.remaining(), 5121, MemoryUtil.getAddress(byteBuffer), n2, n3, glDrawElementsInstancedBaseVertex);
    }
    
    public static void glDrawElementsInstancedBaseVertex(final int n, final IntBuffer intBuffer, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedBaseVertex = capabilities.glDrawElementsInstancedBaseVertex;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedBaseVertex);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglDrawElementsInstancedBaseVertex(n, intBuffer.remaining(), 5125, MemoryUtil.getAddress(intBuffer), n2, n3, glDrawElementsInstancedBaseVertex);
    }
    
    public static void glDrawElementsInstancedBaseVertex(final int n, final ShortBuffer shortBuffer, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedBaseVertex = capabilities.glDrawElementsInstancedBaseVertex;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedBaseVertex);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglDrawElementsInstancedBaseVertex(n, shortBuffer.remaining(), 5123, MemoryUtil.getAddress(shortBuffer), n2, n3, glDrawElementsInstancedBaseVertex);
    }
    
    static native void nglDrawElementsInstancedBaseVertex(final int p0, final int p1, final int p2, final long p3, final int p4, final int p5, final long p6);
    
    public static void glDrawElementsInstancedBaseVertex(final int n, final int n2, final int n3, final long n4, final int n5, final int n6) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedBaseVertex = capabilities.glDrawElementsInstancedBaseVertex;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedBaseVertex);
        GLChecks.ensureElementVBOenabled(capabilities);
        nglDrawElementsInstancedBaseVertexBO(n, n2, n3, n4, n5, n6, glDrawElementsInstancedBaseVertex);
    }
    
    static native void nglDrawElementsInstancedBaseVertexBO(final int p0, final int p1, final int p2, final long p3, final int p4, final int p5, final long p6);
    
    public static void glProvokingVertex(final int n) {
        final long glProvokingVertex = GLContext.getCapabilities().glProvokingVertex;
        BufferChecks.checkFunctionAddress(glProvokingVertex);
        nglProvokingVertex(n, glProvokingVertex);
    }
    
    static native void nglProvokingVertex(final int p0, final long p1);
    
    public static void glTexImage2DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) {
        final long glTexImage2DMultisample = GLContext.getCapabilities().glTexImage2DMultisample;
        BufferChecks.checkFunctionAddress(glTexImage2DMultisample);
        nglTexImage2DMultisample(n, n2, n3, n4, n5, b, glTexImage2DMultisample);
    }
    
    static native void nglTexImage2DMultisample(final int p0, final int p1, final int p2, final int p3, final int p4, final boolean p5, final long p6);
    
    public static void glTexImage3DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        final long glTexImage3DMultisample = GLContext.getCapabilities().glTexImage3DMultisample;
        BufferChecks.checkFunctionAddress(glTexImage3DMultisample);
        nglTexImage3DMultisample(n, n2, n3, n4, n5, n6, b, glTexImage3DMultisample);
    }
    
    static native void nglTexImage3DMultisample(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final boolean p6, final long p7);
    
    public static void glGetMultisample(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetMultisamplefv = GLContext.getCapabilities().glGetMultisamplefv;
        BufferChecks.checkFunctionAddress(glGetMultisamplefv);
        BufferChecks.checkBuffer(floatBuffer, 2);
        nglGetMultisamplefv(n, n2, MemoryUtil.getAddress(floatBuffer), glGetMultisamplefv);
    }
    
    static native void nglGetMultisamplefv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glSampleMaski(final int n, final int n2) {
        final long glSampleMaski = GLContext.getCapabilities().glSampleMaski;
        BufferChecks.checkFunctionAddress(glSampleMaski);
        nglSampleMaski(n, n2, glSampleMaski);
    }
    
    static native void nglSampleMaski(final int p0, final int p1, final long p2);
    
    public static void glFramebufferTexture(final int n, final int n2, final int n3, final int n4) {
        final long glFramebufferTexture = GLContext.getCapabilities().glFramebufferTexture;
        BufferChecks.checkFunctionAddress(glFramebufferTexture);
        nglFramebufferTexture(n, n2, n3, n4, glFramebufferTexture);
    }
    
    static native void nglFramebufferTexture(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static GLSync glFenceSync(final int n, final int n2) {
        final long glFenceSync = GLContext.getCapabilities().glFenceSync;
        BufferChecks.checkFunctionAddress(glFenceSync);
        return new GLSync(nglFenceSync(n, n2, glFenceSync));
    }
    
    static native long nglFenceSync(final int p0, final int p1, final long p2);
    
    public static boolean glIsSync(final GLSync glSync) {
        final long glIsSync = GLContext.getCapabilities().glIsSync;
        BufferChecks.checkFunctionAddress(glIsSync);
        return nglIsSync(glSync.getPointer(), glIsSync);
    }
    
    static native boolean nglIsSync(final long p0, final long p1);
    
    public static void glDeleteSync(final GLSync glSync) {
        final long glDeleteSync = GLContext.getCapabilities().glDeleteSync;
        BufferChecks.checkFunctionAddress(glDeleteSync);
        nglDeleteSync(glSync.getPointer(), glDeleteSync);
    }
    
    static native void nglDeleteSync(final long p0, final long p1);
    
    public static int glClientWaitSync(final GLSync glSync, final int n, final long n2) {
        final long glClientWaitSync = GLContext.getCapabilities().glClientWaitSync;
        BufferChecks.checkFunctionAddress(glClientWaitSync);
        return nglClientWaitSync(glSync.getPointer(), n, n2, glClientWaitSync);
    }
    
    static native int nglClientWaitSync(final long p0, final int p1, final long p2, final long p3);
    
    public static void glWaitSync(final GLSync glSync, final int n, final long n2) {
        final long glWaitSync = GLContext.getCapabilities().glWaitSync;
        BufferChecks.checkFunctionAddress(glWaitSync);
        nglWaitSync(glSync.getPointer(), n, n2, glWaitSync);
    }
    
    static native void nglWaitSync(final long p0, final int p1, final long p2, final long p3);
    
    public static void glGetInteger64(final int n, final LongBuffer longBuffer) {
        final long glGetInteger64v = GLContext.getCapabilities().glGetInteger64v;
        BufferChecks.checkFunctionAddress(glGetInteger64v);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglGetInteger64v(n, MemoryUtil.getAddress(longBuffer), glGetInteger64v);
    }
    
    static native void nglGetInteger64v(final int p0, final long p1, final long p2);
    
    public static long glGetInteger64(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetInteger64v = capabilities.glGetInteger64v;
        BufferChecks.checkFunctionAddress(glGetInteger64v);
        final LongBuffer bufferLong = APIUtil.getBufferLong(capabilities);
        nglGetInteger64v(n, MemoryUtil.getAddress(bufferLong), glGetInteger64v);
        return bufferLong.get(0);
    }
    
    public static void glGetInteger64(final int n, final int n2, final LongBuffer longBuffer) {
        final long glGetInteger64i_v = GLContext.getCapabilities().glGetInteger64i_v;
        BufferChecks.checkFunctionAddress(glGetInteger64i_v);
        BufferChecks.checkBuffer(longBuffer, 4);
        nglGetInteger64i_v(n, n2, MemoryUtil.getAddress(longBuffer), glGetInteger64i_v);
    }
    
    static native void nglGetInteger64i_v(final int p0, final int p1, final long p2, final long p3);
    
    public static long glGetInteger64(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetInteger64i_v = capabilities.glGetInteger64i_v;
        BufferChecks.checkFunctionAddress(glGetInteger64i_v);
        final LongBuffer bufferLong = APIUtil.getBufferLong(capabilities);
        nglGetInteger64i_v(n, n2, MemoryUtil.getAddress(bufferLong), glGetInteger64i_v);
        return bufferLong.get(0);
    }
    
    public static void glGetSync(final GLSync glSync, final int n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long glGetSynciv = GLContext.getCapabilities().glGetSynciv;
        BufferChecks.checkFunctionAddress(glGetSynciv);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(intBuffer2);
        nglGetSynciv(glSync.getPointer(), n, intBuffer2.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(intBuffer2), glGetSynciv);
    }
    
    static native void nglGetSynciv(final long p0, final int p1, final int p2, final long p3, final long p4, final long p5);
    
    @Deprecated
    public static int glGetSync(final GLSync glSync, final int n) {
        return glGetSynci(glSync, n);
    }
    
    public static int glGetSynci(final GLSync glSync, final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSynciv = capabilities.glGetSynciv;
        BufferChecks.checkFunctionAddress(glGetSynciv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetSynciv(glSync.getPointer(), n, 1, 0L, MemoryUtil.getAddress(bufferInt), glGetSynciv);
        return bufferInt.get(0);
    }
}
