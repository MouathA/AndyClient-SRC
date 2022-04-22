package org.lwjgl.opengl;

import org.lwjgl.*;

public final class EXTFramebufferBlit
{
    public static final int GL_READ_FRAMEBUFFER_EXT = 36008;
    public static final int GL_DRAW_FRAMEBUFFER_EXT = 36009;
    public static final int GL_DRAW_FRAMEBUFFER_BINDING_EXT = 36006;
    public static final int GL_READ_FRAMEBUFFER_BINDING_EXT = 36010;
    
    private EXTFramebufferBlit() {
    }
    
    public static void glBlitFramebufferEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        final long glBlitFramebufferEXT = GLContext.getCapabilities().glBlitFramebufferEXT;
        BufferChecks.checkFunctionAddress(glBlitFramebufferEXT);
        nglBlitFramebufferEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, glBlitFramebufferEXT);
    }
    
    static native void nglBlitFramebufferEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10);
}
