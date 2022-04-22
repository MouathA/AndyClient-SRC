package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class NVTransformFeedback2
{
    public static final int GL_TRANSFORM_FEEDBACK_NV = 36386;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_PAUSED_NV = 36387;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_ACTIVE_NV = 36388;
    public static final int GL_TRANSFORM_FEEDBACK_BINDING_NV = 36389;
    
    private NVTransformFeedback2() {
    }
    
    public static void glBindTransformFeedbackNV(final int n, final int n2) {
        final long glBindTransformFeedbackNV = GLContext.getCapabilities().glBindTransformFeedbackNV;
        BufferChecks.checkFunctionAddress(glBindTransformFeedbackNV);
        nglBindTransformFeedbackNV(n, n2, glBindTransformFeedbackNV);
    }
    
    static native void nglBindTransformFeedbackNV(final int p0, final int p1, final long p2);
    
    public static void glDeleteTransformFeedbacksNV(final IntBuffer intBuffer) {
        final long glDeleteTransformFeedbacksNV = GLContext.getCapabilities().glDeleteTransformFeedbacksNV;
        BufferChecks.checkFunctionAddress(glDeleteTransformFeedbacksNV);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteTransformFeedbacksNV(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteTransformFeedbacksNV);
    }
    
    static native void nglDeleteTransformFeedbacksNV(final int p0, final long p1, final long p2);
    
    public static void glDeleteTransformFeedbacksNV(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteTransformFeedbacksNV = capabilities.glDeleteTransformFeedbacksNV;
        BufferChecks.checkFunctionAddress(glDeleteTransformFeedbacksNV);
        nglDeleteTransformFeedbacksNV(1, APIUtil.getInt(capabilities, n), glDeleteTransformFeedbacksNV);
    }
    
    public static void glGenTransformFeedbacksNV(final IntBuffer intBuffer) {
        final long glGenTransformFeedbacksNV = GLContext.getCapabilities().glGenTransformFeedbacksNV;
        BufferChecks.checkFunctionAddress(glGenTransformFeedbacksNV);
        BufferChecks.checkDirect(intBuffer);
        nglGenTransformFeedbacksNV(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenTransformFeedbacksNV);
    }
    
    static native void nglGenTransformFeedbacksNV(final int p0, final long p1, final long p2);
    
    public static int glGenTransformFeedbacksNV() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenTransformFeedbacksNV = capabilities.glGenTransformFeedbacksNV;
        BufferChecks.checkFunctionAddress(glGenTransformFeedbacksNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenTransformFeedbacksNV(1, MemoryUtil.getAddress(bufferInt), glGenTransformFeedbacksNV);
        return bufferInt.get(0);
    }
    
    public static boolean glIsTransformFeedbackNV(final int n) {
        final long glIsTransformFeedbackNV = GLContext.getCapabilities().glIsTransformFeedbackNV;
        BufferChecks.checkFunctionAddress(glIsTransformFeedbackNV);
        return nglIsTransformFeedbackNV(n, glIsTransformFeedbackNV);
    }
    
    static native boolean nglIsTransformFeedbackNV(final int p0, final long p1);
    
    public static void glPauseTransformFeedbackNV() {
        final long glPauseTransformFeedbackNV = GLContext.getCapabilities().glPauseTransformFeedbackNV;
        BufferChecks.checkFunctionAddress(glPauseTransformFeedbackNV);
        nglPauseTransformFeedbackNV(glPauseTransformFeedbackNV);
    }
    
    static native void nglPauseTransformFeedbackNV(final long p0);
    
    public static void glResumeTransformFeedbackNV() {
        final long glResumeTransformFeedbackNV = GLContext.getCapabilities().glResumeTransformFeedbackNV;
        BufferChecks.checkFunctionAddress(glResumeTransformFeedbackNV);
        nglResumeTransformFeedbackNV(glResumeTransformFeedbackNV);
    }
    
    static native void nglResumeTransformFeedbackNV(final long p0);
    
    public static void glDrawTransformFeedbackNV(final int n, final int n2) {
        final long glDrawTransformFeedbackNV = GLContext.getCapabilities().glDrawTransformFeedbackNV;
        BufferChecks.checkFunctionAddress(glDrawTransformFeedbackNV);
        nglDrawTransformFeedbackNV(n, n2, glDrawTransformFeedbackNV);
    }
    
    static native void nglDrawTransformFeedbackNV(final int p0, final int p1, final long p2);
}
