package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ARBTextureStorageMultisample
{
    private ARBTextureStorageMultisample() {
    }
    
    public static void glTexStorage2DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) {
        GL43.glTexStorage2DMultisample(n, n2, n3, n4, n5, b);
    }
    
    public static void glTexStorage3DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        GL43.glTexStorage3DMultisample(n, n2, n3, n4, n5, n6, b);
    }
    
    public static void glTextureStorage2DMultisampleEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        final long glTextureStorage2DMultisampleEXT = GLContext.getCapabilities().glTextureStorage2DMultisampleEXT;
        BufferChecks.checkFunctionAddress(glTextureStorage2DMultisampleEXT);
        nglTextureStorage2DMultisampleEXT(n, n2, n3, n4, n5, n6, b, glTextureStorage2DMultisampleEXT);
    }
    
    static native void nglTextureStorage2DMultisampleEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final boolean p6, final long p7);
    
    public static void glTextureStorage3DMultisampleEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        final long glTextureStorage3DMultisampleEXT = GLContext.getCapabilities().glTextureStorage3DMultisampleEXT;
        BufferChecks.checkFunctionAddress(glTextureStorage3DMultisampleEXT);
        nglTextureStorage3DMultisampleEXT(n, n2, n3, n4, n5, n6, n7, b, glTextureStorage3DMultisampleEXT);
    }
    
    static native void nglTextureStorage3DMultisampleEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final boolean p7, final long p8);
}
