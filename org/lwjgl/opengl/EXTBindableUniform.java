package org.lwjgl.opengl;

import org.lwjgl.*;

public final class EXTBindableUniform
{
    public static final int GL_MAX_VERTEX_BINDABLE_UNIFORMS_EXT = 36322;
    public static final int GL_MAX_FRAGMENT_BINDABLE_UNIFORMS_EXT = 36323;
    public static final int GL_MAX_GEOMETRY_BINDABLE_UNIFORMS_EXT = 36324;
    public static final int GL_MAX_BINDABLE_UNIFORM_SIZE_EXT = 36333;
    public static final int GL_UNIFORM_BUFFER_BINDING_EXT = 36335;
    public static final int GL_UNIFORM_BUFFER_EXT = 36334;
    
    private EXTBindableUniform() {
    }
    
    public static void glUniformBufferEXT(final int n, final int n2, final int n3) {
        final long glUniformBufferEXT = GLContext.getCapabilities().glUniformBufferEXT;
        BufferChecks.checkFunctionAddress(glUniformBufferEXT);
        nglUniformBufferEXT(n, n2, n3, glUniformBufferEXT);
    }
    
    static native void nglUniformBufferEXT(final int p0, final int p1, final int p2, final long p3);
    
    public static int glGetUniformBufferSizeEXT(final int n, final int n2) {
        final long glGetUniformBufferSizeEXT = GLContext.getCapabilities().glGetUniformBufferSizeEXT;
        BufferChecks.checkFunctionAddress(glGetUniformBufferSizeEXT);
        return nglGetUniformBufferSizeEXT(n, n2, glGetUniformBufferSizeEXT);
    }
    
    static native int nglGetUniformBufferSizeEXT(final int p0, final int p1, final long p2);
    
    public static long glGetUniformOffsetEXT(final int n, final int n2) {
        final long glGetUniformOffsetEXT = GLContext.getCapabilities().glGetUniformOffsetEXT;
        BufferChecks.checkFunctionAddress(glGetUniformOffsetEXT);
        return nglGetUniformOffsetEXT(n, n2, glGetUniformOffsetEXT);
    }
    
    static native long nglGetUniformOffsetEXT(final int p0, final int p1, final long p2);
}
