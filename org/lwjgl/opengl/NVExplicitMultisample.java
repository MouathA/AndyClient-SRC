package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class NVExplicitMultisample
{
    public static final int GL_SAMPLE_POSITION_NV = 36432;
    public static final int GL_SAMPLE_MASK_NV = 36433;
    public static final int GL_SAMPLE_MASK_VALUE_NV = 36434;
    public static final int GL_TEXTURE_BINDING_RENDERBUFFER_NV = 36435;
    public static final int GL_TEXTURE_RENDERBUFFER_DATA_STORE_BINDING_NV = 36436;
    public static final int GL_MAX_SAMPLE_MASK_WORDS_NV = 36441;
    public static final int GL_TEXTURE_RENDERBUFFER_NV = 36437;
    public static final int GL_SAMPLER_RENDERBUFFER_NV = 36438;
    public static final int GL_INT_SAMPLER_RENDERBUFFER_NV = 36439;
    public static final int GL_UNSIGNED_INT_SAMPLER_RENDERBUFFER_NV = 36440;
    
    private NVExplicitMultisample() {
    }
    
    public static void glGetBooleanIndexedEXT(final int n, final int n2, final ByteBuffer byteBuffer) {
        EXTDrawBuffers2.glGetBooleanIndexedEXT(n, n2, byteBuffer);
    }
    
    public static boolean glGetBooleanIndexedEXT(final int n, final int n2) {
        return EXTDrawBuffers2.glGetBooleanIndexedEXT(n, n2);
    }
    
    public static void glGetIntegerIndexedEXT(final int n, final int n2, final IntBuffer intBuffer) {
        EXTDrawBuffers2.glGetIntegerIndexedEXT(n, n2, intBuffer);
    }
    
    public static int glGetIntegerIndexedEXT(final int n, final int n2) {
        return EXTDrawBuffers2.glGetIntegerIndexedEXT(n, n2);
    }
    
    public static void glGetMultisampleNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetMultisamplefvNV = GLContext.getCapabilities().glGetMultisamplefvNV;
        BufferChecks.checkFunctionAddress(glGetMultisamplefvNV);
        BufferChecks.checkBuffer(floatBuffer, 2);
        nglGetMultisamplefvNV(n, n2, MemoryUtil.getAddress(floatBuffer), glGetMultisamplefvNV);
    }
    
    static native void nglGetMultisamplefvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glSampleMaskIndexedNV(final int n, final int n2) {
        final long glSampleMaskIndexedNV = GLContext.getCapabilities().glSampleMaskIndexedNV;
        BufferChecks.checkFunctionAddress(glSampleMaskIndexedNV);
        nglSampleMaskIndexedNV(n, n2, glSampleMaskIndexedNV);
    }
    
    static native void nglSampleMaskIndexedNV(final int p0, final int p1, final long p2);
    
    public static void glTexRenderbufferNV(final int n, final int n2) {
        final long glTexRenderbufferNV = GLContext.getCapabilities().glTexRenderbufferNV;
        BufferChecks.checkFunctionAddress(glTexRenderbufferNV);
        nglTexRenderbufferNV(n, n2, glTexRenderbufferNV);
    }
    
    static native void nglTexRenderbufferNV(final int p0, final int p1, final long p2);
}
