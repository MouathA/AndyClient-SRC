package org.lwjgl.opengl;

import org.lwjgl.*;

public final class EXTFramebufferMultisample
{
    public static final int GL_RENDERBUFFER_SAMPLES_EXT = 36011;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE_EXT = 36182;
    public static final int GL_MAX_SAMPLES_EXT = 36183;
    
    private EXTFramebufferMultisample() {
    }
    
    public static void glRenderbufferStorageMultisampleEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glRenderbufferStorageMultisampleEXT = GLContext.getCapabilities().glRenderbufferStorageMultisampleEXT;
        BufferChecks.checkFunctionAddress(glRenderbufferStorageMultisampleEXT);
        nglRenderbufferStorageMultisampleEXT(n, n2, n3, n4, n5, glRenderbufferStorageMultisampleEXT);
    }
    
    static native void nglRenderbufferStorageMultisampleEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
}
