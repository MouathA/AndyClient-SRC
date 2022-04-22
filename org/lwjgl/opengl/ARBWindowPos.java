package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ARBWindowPos
{
    private ARBWindowPos() {
    }
    
    public static void glWindowPos2fARB(final float n, final float n2) {
        final long glWindowPos2fARB = GLContext.getCapabilities().glWindowPos2fARB;
        BufferChecks.checkFunctionAddress(glWindowPos2fARB);
        nglWindowPos2fARB(n, n2, glWindowPos2fARB);
    }
    
    static native void nglWindowPos2fARB(final float p0, final float p1, final long p2);
    
    public static void glWindowPos2dARB(final double n, final double n2) {
        final long glWindowPos2dARB = GLContext.getCapabilities().glWindowPos2dARB;
        BufferChecks.checkFunctionAddress(glWindowPos2dARB);
        nglWindowPos2dARB(n, n2, glWindowPos2dARB);
    }
    
    static native void nglWindowPos2dARB(final double p0, final double p1, final long p2);
    
    public static void glWindowPos2iARB(final int n, final int n2) {
        final long glWindowPos2iARB = GLContext.getCapabilities().glWindowPos2iARB;
        BufferChecks.checkFunctionAddress(glWindowPos2iARB);
        nglWindowPos2iARB(n, n2, glWindowPos2iARB);
    }
    
    static native void nglWindowPos2iARB(final int p0, final int p1, final long p2);
    
    public static void glWindowPos2sARB(final short n, final short n2) {
        final long glWindowPos2sARB = GLContext.getCapabilities().glWindowPos2sARB;
        BufferChecks.checkFunctionAddress(glWindowPos2sARB);
        nglWindowPos2sARB(n, n2, glWindowPos2sARB);
    }
    
    static native void nglWindowPos2sARB(final short p0, final short p1, final long p2);
    
    public static void glWindowPos3fARB(final float n, final float n2, final float n3) {
        final long glWindowPos3fARB = GLContext.getCapabilities().glWindowPos3fARB;
        BufferChecks.checkFunctionAddress(glWindowPos3fARB);
        nglWindowPos3fARB(n, n2, n3, glWindowPos3fARB);
    }
    
    static native void nglWindowPos3fARB(final float p0, final float p1, final float p2, final long p3);
    
    public static void glWindowPos3dARB(final double n, final double n2, final double n3) {
        final long glWindowPos3dARB = GLContext.getCapabilities().glWindowPos3dARB;
        BufferChecks.checkFunctionAddress(glWindowPos3dARB);
        nglWindowPos3dARB(n, n2, n3, glWindowPos3dARB);
    }
    
    static native void nglWindowPos3dARB(final double p0, final double p1, final double p2, final long p3);
    
    public static void glWindowPos3iARB(final int n, final int n2, final int n3) {
        final long glWindowPos3iARB = GLContext.getCapabilities().glWindowPos3iARB;
        BufferChecks.checkFunctionAddress(glWindowPos3iARB);
        nglWindowPos3iARB(n, n2, n3, glWindowPos3iARB);
    }
    
    static native void nglWindowPos3iARB(final int p0, final int p1, final int p2, final long p3);
    
    public static void glWindowPos3sARB(final short n, final short n2, final short n3) {
        final long glWindowPos3sARB = GLContext.getCapabilities().glWindowPos3sARB;
        BufferChecks.checkFunctionAddress(glWindowPos3sARB);
        nglWindowPos3sARB(n, n2, n3, glWindowPos3sARB);
    }
    
    static native void nglWindowPos3sARB(final short p0, final short p1, final short p2, final long p3);
}
