package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ARBTextureStorage
{
    public static final int GL_TEXTURE_IMMUTABLE_FORMAT = 37167;
    
    private ARBTextureStorage() {
    }
    
    public static void glTexStorage1D(final int n, final int n2, final int n3, final int n4) {
        GL42.glTexStorage1D(n, n2, n3, n4);
    }
    
    public static void glTexStorage2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        GL42.glTexStorage2D(n, n2, n3, n4, n5);
    }
    
    public static void glTexStorage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        GL42.glTexStorage3D(n, n2, n3, n4, n5, n6);
    }
    
    public static void glTextureStorage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glTextureStorage1DEXT = GLContext.getCapabilities().glTextureStorage1DEXT;
        BufferChecks.checkFunctionAddress(glTextureStorage1DEXT);
        nglTextureStorage1DEXT(n, n2, n3, n4, n5, glTextureStorage1DEXT);
    }
    
    static native void nglTextureStorage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glTextureStorage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glTextureStorage2DEXT = GLContext.getCapabilities().glTextureStorage2DEXT;
        BufferChecks.checkFunctionAddress(glTextureStorage2DEXT);
        nglTextureStorage2DEXT(n, n2, n3, n4, n5, n6, glTextureStorage2DEXT);
    }
    
    static native void nglTextureStorage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glTextureStorage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        final long glTextureStorage3DEXT = GLContext.getCapabilities().glTextureStorage3DEXT;
        BufferChecks.checkFunctionAddress(glTextureStorage3DEXT);
        nglTextureStorage3DEXT(n, n2, n3, n4, n5, n6, n7, glTextureStorage3DEXT);
    }
    
    static native void nglTextureStorage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7);
}
