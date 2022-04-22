package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ARBMultitexture
{
    public static final int GL_TEXTURE0_ARB = 33984;
    public static final int GL_TEXTURE1_ARB = 33985;
    public static final int GL_TEXTURE2_ARB = 33986;
    public static final int GL_TEXTURE3_ARB = 33987;
    public static final int GL_TEXTURE4_ARB = 33988;
    public static final int GL_TEXTURE5_ARB = 33989;
    public static final int GL_TEXTURE6_ARB = 33990;
    public static final int GL_TEXTURE7_ARB = 33991;
    public static final int GL_TEXTURE8_ARB = 33992;
    public static final int GL_TEXTURE9_ARB = 33993;
    public static final int GL_TEXTURE10_ARB = 33994;
    public static final int GL_TEXTURE11_ARB = 33995;
    public static final int GL_TEXTURE12_ARB = 33996;
    public static final int GL_TEXTURE13_ARB = 33997;
    public static final int GL_TEXTURE14_ARB = 33998;
    public static final int GL_TEXTURE15_ARB = 33999;
    public static final int GL_TEXTURE16_ARB = 34000;
    public static final int GL_TEXTURE17_ARB = 34001;
    public static final int GL_TEXTURE18_ARB = 34002;
    public static final int GL_TEXTURE19_ARB = 34003;
    public static final int GL_TEXTURE20_ARB = 34004;
    public static final int GL_TEXTURE21_ARB = 34005;
    public static final int GL_TEXTURE22_ARB = 34006;
    public static final int GL_TEXTURE23_ARB = 34007;
    public static final int GL_TEXTURE24_ARB = 34008;
    public static final int GL_TEXTURE25_ARB = 34009;
    public static final int GL_TEXTURE26_ARB = 34010;
    public static final int GL_TEXTURE27_ARB = 34011;
    public static final int GL_TEXTURE28_ARB = 34012;
    public static final int GL_TEXTURE29_ARB = 34013;
    public static final int GL_TEXTURE30_ARB = 34014;
    public static final int GL_TEXTURE31_ARB = 34015;
    public static final int GL_ACTIVE_TEXTURE_ARB = 34016;
    public static final int GL_CLIENT_ACTIVE_TEXTURE_ARB = 34017;
    public static final int GL_MAX_TEXTURE_UNITS_ARB = 34018;
    
    private ARBMultitexture() {
    }
    
    public static void glClientActiveTextureARB(final int n) {
        final long glClientActiveTextureARB = GLContext.getCapabilities().glClientActiveTextureARB;
        BufferChecks.checkFunctionAddress(glClientActiveTextureARB);
        nglClientActiveTextureARB(n, glClientActiveTextureARB);
    }
    
    static native void nglClientActiveTextureARB(final int p0, final long p1);
    
    public static void glActiveTextureARB(final int n) {
        final long glActiveTextureARB = GLContext.getCapabilities().glActiveTextureARB;
        BufferChecks.checkFunctionAddress(glActiveTextureARB);
        nglActiveTextureARB(n, glActiveTextureARB);
    }
    
    static native void nglActiveTextureARB(final int p0, final long p1);
    
    public static void glMultiTexCoord1fARB(final int n, final float n2) {
        final long glMultiTexCoord1fARB = GLContext.getCapabilities().glMultiTexCoord1fARB;
        BufferChecks.checkFunctionAddress(glMultiTexCoord1fARB);
        nglMultiTexCoord1fARB(n, n2, glMultiTexCoord1fARB);
    }
    
    static native void nglMultiTexCoord1fARB(final int p0, final float p1, final long p2);
    
    public static void glMultiTexCoord1dARB(final int n, final double n2) {
        final long glMultiTexCoord1dARB = GLContext.getCapabilities().glMultiTexCoord1dARB;
        BufferChecks.checkFunctionAddress(glMultiTexCoord1dARB);
        nglMultiTexCoord1dARB(n, n2, glMultiTexCoord1dARB);
    }
    
    static native void nglMultiTexCoord1dARB(final int p0, final double p1, final long p2);
    
    public static void glMultiTexCoord1iARB(final int n, final int n2) {
        final long glMultiTexCoord1iARB = GLContext.getCapabilities().glMultiTexCoord1iARB;
        BufferChecks.checkFunctionAddress(glMultiTexCoord1iARB);
        nglMultiTexCoord1iARB(n, n2, glMultiTexCoord1iARB);
    }
    
    static native void nglMultiTexCoord1iARB(final int p0, final int p1, final long p2);
    
    public static void glMultiTexCoord1sARB(final int n, final short n2) {
        final long glMultiTexCoord1sARB = GLContext.getCapabilities().glMultiTexCoord1sARB;
        BufferChecks.checkFunctionAddress(glMultiTexCoord1sARB);
        nglMultiTexCoord1sARB(n, n2, glMultiTexCoord1sARB);
    }
    
    static native void nglMultiTexCoord1sARB(final int p0, final short p1, final long p2);
    
    public static void glMultiTexCoord2fARB(final int n, final float n2, final float n3) {
        final long glMultiTexCoord2fARB = GLContext.getCapabilities().glMultiTexCoord2fARB;
        BufferChecks.checkFunctionAddress(glMultiTexCoord2fARB);
        nglMultiTexCoord2fARB(n, n2, n3, glMultiTexCoord2fARB);
    }
    
    static native void nglMultiTexCoord2fARB(final int p0, final float p1, final float p2, final long p3);
    
    public static void glMultiTexCoord2dARB(final int n, final double n2, final double n3) {
        final long glMultiTexCoord2dARB = GLContext.getCapabilities().glMultiTexCoord2dARB;
        BufferChecks.checkFunctionAddress(glMultiTexCoord2dARB);
        nglMultiTexCoord2dARB(n, n2, n3, glMultiTexCoord2dARB);
    }
    
    static native void nglMultiTexCoord2dARB(final int p0, final double p1, final double p2, final long p3);
    
    public static void glMultiTexCoord2iARB(final int n, final int n2, final int n3) {
        final long glMultiTexCoord2iARB = GLContext.getCapabilities().glMultiTexCoord2iARB;
        BufferChecks.checkFunctionAddress(glMultiTexCoord2iARB);
        nglMultiTexCoord2iARB(n, n2, n3, glMultiTexCoord2iARB);
    }
    
    static native void nglMultiTexCoord2iARB(final int p0, final int p1, final int p2, final long p3);
    
    public static void glMultiTexCoord2sARB(final int n, final short n2, final short n3) {
        final long glMultiTexCoord2sARB = GLContext.getCapabilities().glMultiTexCoord2sARB;
        BufferChecks.checkFunctionAddress(glMultiTexCoord2sARB);
        nglMultiTexCoord2sARB(n, n2, n3, glMultiTexCoord2sARB);
    }
    
    static native void nglMultiTexCoord2sARB(final int p0, final short p1, final short p2, final long p3);
    
    public static void glMultiTexCoord3fARB(final int n, final float n2, final float n3, final float n4) {
        final long glMultiTexCoord3fARB = GLContext.getCapabilities().glMultiTexCoord3fARB;
        BufferChecks.checkFunctionAddress(glMultiTexCoord3fARB);
        nglMultiTexCoord3fARB(n, n2, n3, n4, glMultiTexCoord3fARB);
    }
    
    static native void nglMultiTexCoord3fARB(final int p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glMultiTexCoord3dARB(final int n, final double n2, final double n3, final double n4) {
        final long glMultiTexCoord3dARB = GLContext.getCapabilities().glMultiTexCoord3dARB;
        BufferChecks.checkFunctionAddress(glMultiTexCoord3dARB);
        nglMultiTexCoord3dARB(n, n2, n3, n4, glMultiTexCoord3dARB);
    }
    
    static native void nglMultiTexCoord3dARB(final int p0, final double p1, final double p2, final double p3, final long p4);
    
    public static void glMultiTexCoord3iARB(final int n, final int n2, final int n3, final int n4) {
        final long glMultiTexCoord3iARB = GLContext.getCapabilities().glMultiTexCoord3iARB;
        BufferChecks.checkFunctionAddress(glMultiTexCoord3iARB);
        nglMultiTexCoord3iARB(n, n2, n3, n4, glMultiTexCoord3iARB);
    }
    
    static native void nglMultiTexCoord3iARB(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glMultiTexCoord3sARB(final int n, final short n2, final short n3, final short n4) {
        final long glMultiTexCoord3sARB = GLContext.getCapabilities().glMultiTexCoord3sARB;
        BufferChecks.checkFunctionAddress(glMultiTexCoord3sARB);
        nglMultiTexCoord3sARB(n, n2, n3, n4, glMultiTexCoord3sARB);
    }
    
    static native void nglMultiTexCoord3sARB(final int p0, final short p1, final short p2, final short p3, final long p4);
    
    public static void glMultiTexCoord4fARB(final int n, final float n2, final float n3, final float n4, final float n5) {
        final long glMultiTexCoord4fARB = GLContext.getCapabilities().glMultiTexCoord4fARB;
        BufferChecks.checkFunctionAddress(glMultiTexCoord4fARB);
        nglMultiTexCoord4fARB(n, n2, n3, n4, n5, glMultiTexCoord4fARB);
    }
    
    static native void nglMultiTexCoord4fARB(final int p0, final float p1, final float p2, final float p3, final float p4, final long p5);
    
    public static void glMultiTexCoord4dARB(final int n, final double n2, final double n3, final double n4, final double n5) {
        final long glMultiTexCoord4dARB = GLContext.getCapabilities().glMultiTexCoord4dARB;
        BufferChecks.checkFunctionAddress(glMultiTexCoord4dARB);
        nglMultiTexCoord4dARB(n, n2, n3, n4, n5, glMultiTexCoord4dARB);
    }
    
    static native void nglMultiTexCoord4dARB(final int p0, final double p1, final double p2, final double p3, final double p4, final long p5);
    
    public static void glMultiTexCoord4iARB(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glMultiTexCoord4iARB = GLContext.getCapabilities().glMultiTexCoord4iARB;
        BufferChecks.checkFunctionAddress(glMultiTexCoord4iARB);
        nglMultiTexCoord4iARB(n, n2, n3, n4, n5, glMultiTexCoord4iARB);
    }
    
    static native void nglMultiTexCoord4iARB(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glMultiTexCoord4sARB(final int n, final short n2, final short n3, final short n4, final short n5) {
        final long glMultiTexCoord4sARB = GLContext.getCapabilities().glMultiTexCoord4sARB;
        BufferChecks.checkFunctionAddress(glMultiTexCoord4sARB);
        nglMultiTexCoord4sARB(n, n2, n3, n4, n5, glMultiTexCoord4sARB);
    }
    
    static native void nglMultiTexCoord4sARB(final int p0, final short p1, final short p2, final short p3, final short p4, final long p5);
}
