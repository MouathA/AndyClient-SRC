package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ARBDrawBuffersBlend
{
    private ARBDrawBuffersBlend() {
    }
    
    public static void glBlendEquationiARB(final int n, final int n2) {
        final long glBlendEquationiARB = GLContext.getCapabilities().glBlendEquationiARB;
        BufferChecks.checkFunctionAddress(glBlendEquationiARB);
        nglBlendEquationiARB(n, n2, glBlendEquationiARB);
    }
    
    static native void nglBlendEquationiARB(final int p0, final int p1, final long p2);
    
    public static void glBlendEquationSeparateiARB(final int n, final int n2, final int n3) {
        final long glBlendEquationSeparateiARB = GLContext.getCapabilities().glBlendEquationSeparateiARB;
        BufferChecks.checkFunctionAddress(glBlendEquationSeparateiARB);
        nglBlendEquationSeparateiARB(n, n2, n3, glBlendEquationSeparateiARB);
    }
    
    static native void nglBlendEquationSeparateiARB(final int p0, final int p1, final int p2, final long p3);
    
    public static void glBlendFunciARB(final int n, final int n2, final int n3) {
        final long glBlendFunciARB = GLContext.getCapabilities().glBlendFunciARB;
        BufferChecks.checkFunctionAddress(glBlendFunciARB);
        nglBlendFunciARB(n, n2, n3, glBlendFunciARB);
    }
    
    static native void nglBlendFunciARB(final int p0, final int p1, final int p2, final long p3);
    
    public static void glBlendFuncSeparateiARB(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glBlendFuncSeparateiARB = GLContext.getCapabilities().glBlendFuncSeparateiARB;
        BufferChecks.checkFunctionAddress(glBlendFuncSeparateiARB);
        nglBlendFuncSeparateiARB(n, n2, n3, n4, n5, glBlendFuncSeparateiARB);
    }
    
    static native void nglBlendFuncSeparateiARB(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
}
