package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class ARBFramebufferNoAttachments
{
    public static final int GL_FRAMEBUFFER_DEFAULT_WIDTH = 37648;
    public static final int GL_FRAMEBUFFER_DEFAULT_HEIGHT = 37649;
    public static final int GL_FRAMEBUFFER_DEFAULT_LAYERS = 37650;
    public static final int GL_FRAMEBUFFER_DEFAULT_SAMPLES = 37651;
    public static final int GL_FRAMEBUFFER_DEFAULT_FIXED_SAMPLE_LOCATIONS = 37652;
    public static final int GL_MAX_FRAMEBUFFER_WIDTH = 37653;
    public static final int GL_MAX_FRAMEBUFFER_HEIGHT = 37654;
    public static final int GL_MAX_FRAMEBUFFER_LAYERS = 37655;
    public static final int GL_MAX_FRAMEBUFFER_SAMPLES = 37656;
    
    private ARBFramebufferNoAttachments() {
    }
    
    public static void glFramebufferParameteri(final int n, final int n2, final int n3) {
        GL43.glFramebufferParameteri(n, n2, n3);
    }
    
    public static void glGetFramebufferParameter(final int n, final int n2, final IntBuffer intBuffer) {
        GL43.glGetFramebufferParameter(n, n2, intBuffer);
    }
    
    public static int glGetFramebufferParameteri(final int n, final int n2) {
        return GL43.glGetFramebufferParameteri(n, n2);
    }
    
    public static void glNamedFramebufferParameteriEXT(final int n, final int n2, final int n3) {
        final long glNamedFramebufferParameteriEXT = GLContext.getCapabilities().glNamedFramebufferParameteriEXT;
        BufferChecks.checkFunctionAddress(glNamedFramebufferParameteriEXT);
        nglNamedFramebufferParameteriEXT(n, n2, n3, glNamedFramebufferParameteriEXT);
    }
    
    static native void nglNamedFramebufferParameteriEXT(final int p0, final int p1, final int p2, final long p3);
    
    public static void glGetNamedFramebufferParameterEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetNamedFramebufferParameterivEXT = GLContext.getCapabilities().glGetNamedFramebufferParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetNamedFramebufferParameterivEXT);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetNamedFramebufferParameterivEXT(n, n2, MemoryUtil.getAddress(intBuffer), glGetNamedFramebufferParameterivEXT);
    }
    
    static native void nglGetNamedFramebufferParameterivEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetNamedFramebufferParameterEXT(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetNamedFramebufferParameterivEXT = capabilities.glGetNamedFramebufferParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetNamedFramebufferParameterivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetNamedFramebufferParameterivEXT(n, n2, MemoryUtil.getAddress(bufferInt), glGetNamedFramebufferParameterivEXT);
        return bufferInt.get(0);
    }
}
