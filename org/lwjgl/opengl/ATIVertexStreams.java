package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ATIVertexStreams
{
    public static final int GL_MAX_VERTEX_STREAMS_ATI = 34667;
    public static final int GL_VERTEX_SOURCE_ATI = 34668;
    public static final int GL_VERTEX_STREAM0_ATI = 34669;
    public static final int GL_VERTEX_STREAM1_ATI = 34670;
    public static final int GL_VERTEX_STREAM2_ATI = 34671;
    public static final int GL_VERTEX_STREAM3_ATI = 34672;
    public static final int GL_VERTEX_STREAM4_ATI = 34673;
    public static final int GL_VERTEX_STREAM5_ATI = 34674;
    public static final int GL_VERTEX_STREAM6_ATI = 34675;
    public static final int GL_VERTEX_STREAM7_ATI = 34676;
    
    private ATIVertexStreams() {
    }
    
    public static void glVertexStream2fATI(final int n, final float n2, final float n3) {
        final long glVertexStream2fATI = GLContext.getCapabilities().glVertexStream2fATI;
        BufferChecks.checkFunctionAddress(glVertexStream2fATI);
        nglVertexStream2fATI(n, n2, n3, glVertexStream2fATI);
    }
    
    static native void nglVertexStream2fATI(final int p0, final float p1, final float p2, final long p3);
    
    public static void glVertexStream2dATI(final int n, final double n2, final double n3) {
        final long glVertexStream2dATI = GLContext.getCapabilities().glVertexStream2dATI;
        BufferChecks.checkFunctionAddress(glVertexStream2dATI);
        nglVertexStream2dATI(n, n2, n3, glVertexStream2dATI);
    }
    
    static native void nglVertexStream2dATI(final int p0, final double p1, final double p2, final long p3);
    
    public static void glVertexStream2iATI(final int n, final int n2, final int n3) {
        final long glVertexStream2iATI = GLContext.getCapabilities().glVertexStream2iATI;
        BufferChecks.checkFunctionAddress(glVertexStream2iATI);
        nglVertexStream2iATI(n, n2, n3, glVertexStream2iATI);
    }
    
    static native void nglVertexStream2iATI(final int p0, final int p1, final int p2, final long p3);
    
    public static void glVertexStream2sATI(final int n, final short n2, final short n3) {
        final long glVertexStream2sATI = GLContext.getCapabilities().glVertexStream2sATI;
        BufferChecks.checkFunctionAddress(glVertexStream2sATI);
        nglVertexStream2sATI(n, n2, n3, glVertexStream2sATI);
    }
    
    static native void nglVertexStream2sATI(final int p0, final short p1, final short p2, final long p3);
    
    public static void glVertexStream3fATI(final int n, final float n2, final float n3, final float n4) {
        final long glVertexStream3fATI = GLContext.getCapabilities().glVertexStream3fATI;
        BufferChecks.checkFunctionAddress(glVertexStream3fATI);
        nglVertexStream3fATI(n, n2, n3, n4, glVertexStream3fATI);
    }
    
    static native void nglVertexStream3fATI(final int p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glVertexStream3dATI(final int n, final double n2, final double n3, final double n4) {
        final long glVertexStream3dATI = GLContext.getCapabilities().glVertexStream3dATI;
        BufferChecks.checkFunctionAddress(glVertexStream3dATI);
        nglVertexStream3dATI(n, n2, n3, n4, glVertexStream3dATI);
    }
    
    static native void nglVertexStream3dATI(final int p0, final double p1, final double p2, final double p3, final long p4);
    
    public static void glVertexStream3iATI(final int n, final int n2, final int n3, final int n4) {
        final long glVertexStream3iATI = GLContext.getCapabilities().glVertexStream3iATI;
        BufferChecks.checkFunctionAddress(glVertexStream3iATI);
        nglVertexStream3iATI(n, n2, n3, n4, glVertexStream3iATI);
    }
    
    static native void nglVertexStream3iATI(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glVertexStream3sATI(final int n, final short n2, final short n3, final short n4) {
        final long glVertexStream3sATI = GLContext.getCapabilities().glVertexStream3sATI;
        BufferChecks.checkFunctionAddress(glVertexStream3sATI);
        nglVertexStream3sATI(n, n2, n3, n4, glVertexStream3sATI);
    }
    
    static native void nglVertexStream3sATI(final int p0, final short p1, final short p2, final short p3, final long p4);
    
    public static void glVertexStream4fATI(final int n, final float n2, final float n3, final float n4, final float n5) {
        final long glVertexStream4fATI = GLContext.getCapabilities().glVertexStream4fATI;
        BufferChecks.checkFunctionAddress(glVertexStream4fATI);
        nglVertexStream4fATI(n, n2, n3, n4, n5, glVertexStream4fATI);
    }
    
    static native void nglVertexStream4fATI(final int p0, final float p1, final float p2, final float p3, final float p4, final long p5);
    
    public static void glVertexStream4dATI(final int n, final double n2, final double n3, final double n4, final double n5) {
        final long glVertexStream4dATI = GLContext.getCapabilities().glVertexStream4dATI;
        BufferChecks.checkFunctionAddress(glVertexStream4dATI);
        nglVertexStream4dATI(n, n2, n3, n4, n5, glVertexStream4dATI);
    }
    
    static native void nglVertexStream4dATI(final int p0, final double p1, final double p2, final double p3, final double p4, final long p5);
    
    public static void glVertexStream4iATI(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glVertexStream4iATI = GLContext.getCapabilities().glVertexStream4iATI;
        BufferChecks.checkFunctionAddress(glVertexStream4iATI);
        nglVertexStream4iATI(n, n2, n3, n4, n5, glVertexStream4iATI);
    }
    
    static native void nglVertexStream4iATI(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glVertexStream4sATI(final int n, final short n2, final short n3, final short n4, final short n5) {
        final long glVertexStream4sATI = GLContext.getCapabilities().glVertexStream4sATI;
        BufferChecks.checkFunctionAddress(glVertexStream4sATI);
        nglVertexStream4sATI(n, n2, n3, n4, n5, glVertexStream4sATI);
    }
    
    static native void nglVertexStream4sATI(final int p0, final short p1, final short p2, final short p3, final short p4, final long p5);
    
    public static void glNormalStream3bATI(final int n, final byte b, final byte b2, final byte b3) {
        final long glNormalStream3bATI = GLContext.getCapabilities().glNormalStream3bATI;
        BufferChecks.checkFunctionAddress(glNormalStream3bATI);
        nglNormalStream3bATI(n, b, b2, b3, glNormalStream3bATI);
    }
    
    static native void nglNormalStream3bATI(final int p0, final byte p1, final byte p2, final byte p3, final long p4);
    
    public static void glNormalStream3fATI(final int n, final float n2, final float n3, final float n4) {
        final long glNormalStream3fATI = GLContext.getCapabilities().glNormalStream3fATI;
        BufferChecks.checkFunctionAddress(glNormalStream3fATI);
        nglNormalStream3fATI(n, n2, n3, n4, glNormalStream3fATI);
    }
    
    static native void nglNormalStream3fATI(final int p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glNormalStream3dATI(final int n, final double n2, final double n3, final double n4) {
        final long glNormalStream3dATI = GLContext.getCapabilities().glNormalStream3dATI;
        BufferChecks.checkFunctionAddress(glNormalStream3dATI);
        nglNormalStream3dATI(n, n2, n3, n4, glNormalStream3dATI);
    }
    
    static native void nglNormalStream3dATI(final int p0, final double p1, final double p2, final double p3, final long p4);
    
    public static void glNormalStream3iATI(final int n, final int n2, final int n3, final int n4) {
        final long glNormalStream3iATI = GLContext.getCapabilities().glNormalStream3iATI;
        BufferChecks.checkFunctionAddress(glNormalStream3iATI);
        nglNormalStream3iATI(n, n2, n3, n4, glNormalStream3iATI);
    }
    
    static native void nglNormalStream3iATI(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glNormalStream3sATI(final int n, final short n2, final short n3, final short n4) {
        final long glNormalStream3sATI = GLContext.getCapabilities().glNormalStream3sATI;
        BufferChecks.checkFunctionAddress(glNormalStream3sATI);
        nglNormalStream3sATI(n, n2, n3, n4, glNormalStream3sATI);
    }
    
    static native void nglNormalStream3sATI(final int p0, final short p1, final short p2, final short p3, final long p4);
    
    public static void glClientActiveVertexStreamATI(final int n) {
        final long glClientActiveVertexStreamATI = GLContext.getCapabilities().glClientActiveVertexStreamATI;
        BufferChecks.checkFunctionAddress(glClientActiveVertexStreamATI);
        nglClientActiveVertexStreamATI(n, glClientActiveVertexStreamATI);
    }
    
    static native void nglClientActiveVertexStreamATI(final int p0, final long p1);
    
    public static void glVertexBlendEnvfATI(final int n, final float n2) {
        final long glVertexBlendEnvfATI = GLContext.getCapabilities().glVertexBlendEnvfATI;
        BufferChecks.checkFunctionAddress(glVertexBlendEnvfATI);
        nglVertexBlendEnvfATI(n, n2, glVertexBlendEnvfATI);
    }
    
    static native void nglVertexBlendEnvfATI(final int p0, final float p1, final long p2);
    
    public static void glVertexBlendEnviATI(final int n, final int n2) {
        final long glVertexBlendEnviATI = GLContext.getCapabilities().glVertexBlendEnviATI;
        BufferChecks.checkFunctionAddress(glVertexBlendEnviATI);
        nglVertexBlendEnviATI(n, n2, glVertexBlendEnviATI);
    }
    
    static native void nglVertexBlendEnviATI(final int p0, final int p1, final long p2);
}
