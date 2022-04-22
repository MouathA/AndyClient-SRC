package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class ARBRobustness
{
    public static final int GL_GUILTY_CONTEXT_RESET_ARB = 33363;
    public static final int GL_INNOCENT_CONTEXT_RESET_ARB = 33364;
    public static final int GL_UNKNOWN_CONTEXT_RESET_ARB = 33365;
    public static final int GL_RESET_NOTIFICATION_STRATEGY_ARB = 33366;
    public static final int GL_LOSE_CONTEXT_ON_RESET_ARB = 33362;
    public static final int GL_NO_RESET_NOTIFICATION_ARB = 33377;
    public static final int GL_CONTEXT_FLAG_ROBUST_ACCESS_BIT_ARB = 4;
    
    private ARBRobustness() {
    }
    
    public static int glGetGraphicsResetStatusARB() {
        final long glGetGraphicsResetStatusARB = GLContext.getCapabilities().glGetGraphicsResetStatusARB;
        BufferChecks.checkFunctionAddress(glGetGraphicsResetStatusARB);
        return nglGetGraphicsResetStatusARB(glGetGraphicsResetStatusARB);
    }
    
    static native int nglGetGraphicsResetStatusARB(final long p0);
    
    public static void glGetnMapdvARB(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glGetnMapdvARB = GLContext.getCapabilities().glGetnMapdvARB;
        BufferChecks.checkFunctionAddress(glGetnMapdvARB);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnMapdvARB(n, n2, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glGetnMapdvARB);
    }
    
    static native void nglGetnMapdvARB(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetnMapfvARB(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetnMapfvARB = GLContext.getCapabilities().glGetnMapfvARB;
        BufferChecks.checkFunctionAddress(glGetnMapfvARB);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnMapfvARB(n, n2, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glGetnMapfvARB);
    }
    
    static native void nglGetnMapfvARB(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetnMapivARB(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetnMapivARB = GLContext.getCapabilities().glGetnMapivARB;
        BufferChecks.checkFunctionAddress(glGetnMapivARB);
        BufferChecks.checkDirect(intBuffer);
        nglGetnMapivARB(n, n2, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glGetnMapivARB);
    }
    
    static native void nglGetnMapivARB(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetnPixelMapfvARB(final int n, final FloatBuffer floatBuffer) {
        final long glGetnPixelMapfvARB = GLContext.getCapabilities().glGetnPixelMapfvARB;
        BufferChecks.checkFunctionAddress(glGetnPixelMapfvARB);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnPixelMapfvARB(n, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glGetnPixelMapfvARB);
    }
    
    static native void nglGetnPixelMapfvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetnPixelMapuivARB(final int n, final IntBuffer intBuffer) {
        final long glGetnPixelMapuivARB = GLContext.getCapabilities().glGetnPixelMapuivARB;
        BufferChecks.checkFunctionAddress(glGetnPixelMapuivARB);
        BufferChecks.checkDirect(intBuffer);
        nglGetnPixelMapuivARB(n, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glGetnPixelMapuivARB);
    }
    
    static native void nglGetnPixelMapuivARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetnPixelMapusvARB(final int n, final ShortBuffer shortBuffer) {
        final long glGetnPixelMapusvARB = GLContext.getCapabilities().glGetnPixelMapusvARB;
        BufferChecks.checkFunctionAddress(glGetnPixelMapusvARB);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnPixelMapusvARB(n, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glGetnPixelMapusvARB);
    }
    
    static native void nglGetnPixelMapusvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetnPolygonStippleARB(final ByteBuffer byteBuffer) {
        final long glGetnPolygonStippleARB = GLContext.getCapabilities().glGetnPolygonStippleARB;
        BufferChecks.checkFunctionAddress(glGetnPolygonStippleARB);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnPolygonStippleARB(byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glGetnPolygonStippleARB);
    }
    
    static native void nglGetnPolygonStippleARB(final int p0, final long p1, final long p2);
    
    public static void glGetnTexImageARB(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnTexImageARB = capabilities.glGetnTexImageARB;
        BufferChecks.checkFunctionAddress(glGetnTexImageARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnTexImageARB(n, n2, n3, n4, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glGetnTexImageARB);
    }
    
    public static void glGetnTexImageARB(final int n, final int n2, final int n3, final int n4, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnTexImageARB = capabilities.glGetnTexImageARB;
        BufferChecks.checkFunctionAddress(glGetnTexImageARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnTexImageARB(n, n2, n3, n4, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glGetnTexImageARB);
    }
    
    public static void glGetnTexImageARB(final int n, final int n2, final int n3, final int n4, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnTexImageARB = capabilities.glGetnTexImageARB;
        BufferChecks.checkFunctionAddress(glGetnTexImageARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnTexImageARB(n, n2, n3, n4, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glGetnTexImageARB);
    }
    
    public static void glGetnTexImageARB(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnTexImageARB = capabilities.glGetnTexImageARB;
        BufferChecks.checkFunctionAddress(glGetnTexImageARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglGetnTexImageARB(n, n2, n3, n4, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glGetnTexImageARB);
    }
    
    public static void glGetnTexImageARB(final int n, final int n2, final int n3, final int n4, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnTexImageARB = capabilities.glGetnTexImageARB;
        BufferChecks.checkFunctionAddress(glGetnTexImageARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnTexImageARB(n, n2, n3, n4, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glGetnTexImageARB);
    }
    
    static native void nglGetnTexImageARB(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glGetnTexImageARB(final int n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnTexImageARB = capabilities.glGetnTexImageARB;
        BufferChecks.checkFunctionAddress(glGetnTexImageARB);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetnTexImageARBBO(n, n2, n3, n4, n5, n6, glGetnTexImageARB);
    }
    
    static native void nglGetnTexImageARBBO(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glReadnPixelsARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadnPixelsARB = capabilities.glReadnPixelsARB;
        BufferChecks.checkFunctionAddress(glReadnPixelsARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglReadnPixelsARB(n, n2, n3, n4, n5, n6, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glReadnPixelsARB);
    }
    
    public static void glReadnPixelsARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadnPixelsARB = capabilities.glReadnPixelsARB;
        BufferChecks.checkFunctionAddress(glReadnPixelsARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        nglReadnPixelsARB(n, n2, n3, n4, n5, n6, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glReadnPixelsARB);
    }
    
    public static void glReadnPixelsARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadnPixelsARB = capabilities.glReadnPixelsARB;
        BufferChecks.checkFunctionAddress(glReadnPixelsARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        nglReadnPixelsARB(n, n2, n3, n4, n5, n6, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glReadnPixelsARB);
    }
    
    public static void glReadnPixelsARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadnPixelsARB = capabilities.glReadnPixelsARB;
        BufferChecks.checkFunctionAddress(glReadnPixelsARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglReadnPixelsARB(n, n2, n3, n4, n5, n6, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glReadnPixelsARB);
    }
    
    public static void glReadnPixelsARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadnPixelsARB = capabilities.glReadnPixelsARB;
        BufferChecks.checkFunctionAddress(glReadnPixelsARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglReadnPixelsARB(n, n2, n3, n4, n5, n6, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glReadnPixelsARB);
    }
    
    static native void nglReadnPixelsARB(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glReadnPixelsARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadnPixelsARB = capabilities.glReadnPixelsARB;
        BufferChecks.checkFunctionAddress(glReadnPixelsARB);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglReadnPixelsARBBO(n, n2, n3, n4, n5, n6, n7, n8, glReadnPixelsARB);
    }
    
    static native void nglReadnPixelsARBBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glGetnColorTableARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final long glGetnColorTableARB = GLContext.getCapabilities().glGetnColorTableARB;
        BufferChecks.checkFunctionAddress(glGetnColorTableARB);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnColorTableARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glGetnColorTableARB);
    }
    
    public static void glGetnColorTableARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final long glGetnColorTableARB = GLContext.getCapabilities().glGetnColorTableARB;
        BufferChecks.checkFunctionAddress(glGetnColorTableARB);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnColorTableARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glGetnColorTableARB);
    }
    
    public static void glGetnColorTableARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glGetnColorTableARB = GLContext.getCapabilities().glGetnColorTableARB;
        BufferChecks.checkFunctionAddress(glGetnColorTableARB);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnColorTableARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glGetnColorTableARB);
    }
    
    static native void nglGetnColorTableARB(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glGetnConvolutionFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnConvolutionFilterARB = capabilities.glGetnConvolutionFilterARB;
        BufferChecks.checkFunctionAddress(glGetnConvolutionFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnConvolutionFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glGetnConvolutionFilterARB);
    }
    
    public static void glGetnConvolutionFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnConvolutionFilterARB = capabilities.glGetnConvolutionFilterARB;
        BufferChecks.checkFunctionAddress(glGetnConvolutionFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnConvolutionFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glGetnConvolutionFilterARB);
    }
    
    public static void glGetnConvolutionFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnConvolutionFilterARB = capabilities.glGetnConvolutionFilterARB;
        BufferChecks.checkFunctionAddress(glGetnConvolutionFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnConvolutionFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glGetnConvolutionFilterARB);
    }
    
    public static void glGetnConvolutionFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnConvolutionFilterARB = capabilities.glGetnConvolutionFilterARB;
        BufferChecks.checkFunctionAddress(glGetnConvolutionFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglGetnConvolutionFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glGetnConvolutionFilterARB);
    }
    
    public static void glGetnConvolutionFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnConvolutionFilterARB = capabilities.glGetnConvolutionFilterARB;
        BufferChecks.checkFunctionAddress(glGetnConvolutionFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnConvolutionFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glGetnConvolutionFilterARB);
    }
    
    static native void nglGetnConvolutionFilterARB(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glGetnConvolutionFilterARB(final int n, final int n2, final int n3, final int n4, final long n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnConvolutionFilterARB = capabilities.glGetnConvolutionFilterARB;
        BufferChecks.checkFunctionAddress(glGetnConvolutionFilterARB);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetnConvolutionFilterARBBO(n, n2, n3, n4, n5, glGetnConvolutionFilterARB);
    }
    
    static native void nglGetnConvolutionFilterARBBO(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final ByteBuffer byteBuffer3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        BufferChecks.checkDirect(byteBuffer3);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), byteBuffer2.remaining(), MemoryUtil.getAddress(byteBuffer2), MemoryUtil.getAddress(byteBuffer3), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), byteBuffer2.remaining(), MemoryUtil.getAddress(byteBuffer2), MemoryUtil.getAddress(doubleBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), byteBuffer2.remaining(), MemoryUtil.getAddress(byteBuffer2), MemoryUtil.getAddress(floatBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        BufferChecks.checkDirect(intBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), byteBuffer2.remaining(), MemoryUtil.getAddress(byteBuffer2), MemoryUtil.getAddress(intBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), byteBuffer2.remaining(), MemoryUtil.getAddress(byteBuffer2), MemoryUtil.getAddress(shortBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(byteBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(doubleBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(floatBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(intBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(shortBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final FloatBuffer floatBuffer, final ByteBuffer byteBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(byteBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final FloatBuffer floatBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(doubleBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(floatBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(floatBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final FloatBuffer floatBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(intBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final FloatBuffer floatBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(shortBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final IntBuffer intBuffer, final ByteBuffer byteBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(byteBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(doubleBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final IntBuffer intBuffer, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(floatBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final IntBuffer intBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(shortBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(byteBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(doubleBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(floatBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(intBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(shortBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(doubleBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(floatBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(intBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(shortBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), doubleBuffer2.remaining() << 3, MemoryUtil.getAddress(doubleBuffer2), MemoryUtil.getAddress(byteBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final DoubleBuffer doubleBuffer3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        BufferChecks.checkDirect(doubleBuffer3);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), doubleBuffer2.remaining() << 3, MemoryUtil.getAddress(doubleBuffer2), MemoryUtil.getAddress(doubleBuffer3), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), doubleBuffer2.remaining() << 3, MemoryUtil.getAddress(doubleBuffer2), MemoryUtil.getAddress(floatBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        BufferChecks.checkDirect(intBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), doubleBuffer2.remaining() << 3, MemoryUtil.getAddress(doubleBuffer2), MemoryUtil.getAddress(intBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), doubleBuffer2.remaining() << 3, MemoryUtil.getAddress(doubleBuffer2), MemoryUtil.getAddress(shortBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final FloatBuffer floatBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(byteBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final FloatBuffer floatBuffer, final DoubleBuffer doubleBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(doubleBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(floatBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(floatBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final FloatBuffer floatBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(intBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final FloatBuffer floatBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(shortBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(doubleBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(floatBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(shortBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(byteBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(doubleBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(floatBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(intBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(shortBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(doubleBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ByteBuffer byteBuffer, final FloatBuffer floatBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(floatBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(floatBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(intBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(shortBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(byteBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(doubleBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final DoubleBuffer doubleBuffer, final FloatBuffer floatBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(floatBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(floatBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(intBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(shortBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(floatBuffer2);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), floatBuffer2.remaining() << 2, MemoryUtil.getAddress(floatBuffer2), MemoryUtil.getAddress(byteBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(floatBuffer2);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), floatBuffer2.remaining() << 2, MemoryUtil.getAddress(floatBuffer2), MemoryUtil.getAddress(doubleBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final FloatBuffer floatBuffer3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(floatBuffer2);
        BufferChecks.checkDirect(floatBuffer3);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), floatBuffer2.remaining() << 2, MemoryUtil.getAddress(floatBuffer2), MemoryUtil.getAddress(floatBuffer3), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(floatBuffer2);
        BufferChecks.checkDirect(intBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), floatBuffer2.remaining() << 2, MemoryUtil.getAddress(floatBuffer2), MemoryUtil.getAddress(intBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(floatBuffer2);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), floatBuffer2.remaining() << 2, MemoryUtil.getAddress(floatBuffer2), MemoryUtil.getAddress(shortBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(doubleBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final IntBuffer intBuffer, final FloatBuffer floatBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(floatBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(floatBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final IntBuffer intBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(shortBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(byteBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(doubleBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ShortBuffer shortBuffer, final FloatBuffer floatBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(floatBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(floatBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ShortBuffer shortBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(intBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(shortBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(doubleBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(floatBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(intBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(shortBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(byteBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(doubleBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(floatBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(intBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(shortBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final FloatBuffer floatBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(byteBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final FloatBuffer floatBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(doubleBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(floatBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(floatBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final FloatBuffer floatBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(intBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(intBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final FloatBuffer floatBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(shortBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), intBuffer2.remaining() << 2, MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(byteBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), intBuffer2.remaining() << 2, MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(doubleBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), intBuffer2.remaining() << 2, MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(floatBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        BufferChecks.checkDirect(intBuffer3);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), intBuffer2.remaining() << 2, MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(intBuffer3), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), intBuffer2.remaining() << 2, MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(shortBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(byteBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(doubleBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ShortBuffer shortBuffer, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(floatBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ShortBuffer shortBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(intBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(shortBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(doubleBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(floatBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(intBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(shortBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(byteBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(doubleBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(floatBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(intBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(shortBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final FloatBuffer floatBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(byteBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final FloatBuffer floatBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(doubleBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(floatBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(floatBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final FloatBuffer floatBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(intBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final FloatBuffer floatBuffer, final ShortBuffer shortBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(shortBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(doubleBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final IntBuffer intBuffer, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(floatBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final IntBuffer intBuffer, final ShortBuffer shortBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(shortBuffer2), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), shortBuffer2.remaining() << 1, MemoryUtil.getAddress(shortBuffer2), MemoryUtil.getAddress(byteBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), shortBuffer2.remaining() << 1, MemoryUtil.getAddress(shortBuffer2), MemoryUtil.getAddress(doubleBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), shortBuffer2.remaining() << 1, MemoryUtil.getAddress(shortBuffer2), MemoryUtil.getAddress(floatBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        BufferChecks.checkDirect(intBuffer);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), shortBuffer2.remaining() << 1, MemoryUtil.getAddress(shortBuffer2), MemoryUtil.getAddress(intBuffer), glGetnSeparableFilterARB);
    }
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2, final ShortBuffer shortBuffer3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        BufferChecks.checkDirect(shortBuffer3);
        nglGetnSeparableFilterARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), shortBuffer2.remaining() << 1, MemoryUtil.getAddress(shortBuffer2), MemoryUtil.getAddress(shortBuffer3), glGetnSeparableFilterARB);
    }
    
    static native void nglGetnSeparableFilterARB(final int p0, final int p1, final int p2, final int p3, final long p4, final int p5, final long p6, final long p7, final long p8);
    
    public static void glGetnSeparableFilterARB(final int n, final int n2, final int n3, final int n4, final long n5, final int n6, final long n7, final long n8) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnSeparableFilterARB = capabilities.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(glGetnSeparableFilterARB);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetnSeparableFilterARBBO(n, n2, n3, n4, n5, n6, n7, n8, glGetnSeparableFilterARB);
    }
    
    static native void nglGetnSeparableFilterARBBO(final int p0, final int p1, final int p2, final int p3, final long p4, final int p5, final long p6, final long p7, final long p8);
    
    public static void glGetnHistogramARB(final int n, final boolean b, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnHistogramARB = capabilities.glGetnHistogramARB;
        BufferChecks.checkFunctionAddress(glGetnHistogramARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnHistogramARB(n, b, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glGetnHistogramARB);
    }
    
    public static void glGetnHistogramARB(final int n, final boolean b, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnHistogramARB = capabilities.glGetnHistogramARB;
        BufferChecks.checkFunctionAddress(glGetnHistogramARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnHistogramARB(n, b, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glGetnHistogramARB);
    }
    
    public static void glGetnHistogramARB(final int n, final boolean b, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnHistogramARB = capabilities.glGetnHistogramARB;
        BufferChecks.checkFunctionAddress(glGetnHistogramARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnHistogramARB(n, b, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glGetnHistogramARB);
    }
    
    public static void glGetnHistogramARB(final int n, final boolean b, final int n2, final int n3, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnHistogramARB = capabilities.glGetnHistogramARB;
        BufferChecks.checkFunctionAddress(glGetnHistogramARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglGetnHistogramARB(n, b, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glGetnHistogramARB);
    }
    
    public static void glGetnHistogramARB(final int n, final boolean b, final int n2, final int n3, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnHistogramARB = capabilities.glGetnHistogramARB;
        BufferChecks.checkFunctionAddress(glGetnHistogramARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnHistogramARB(n, b, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glGetnHistogramARB);
    }
    
    static native void nglGetnHistogramARB(final int p0, final boolean p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glGetnHistogramARB(final int n, final boolean b, final int n2, final int n3, final int n4, final long n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnHistogramARB = capabilities.glGetnHistogramARB;
        BufferChecks.checkFunctionAddress(glGetnHistogramARB);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetnHistogramARBBO(n, b, n2, n3, n4, n5, glGetnHistogramARB);
    }
    
    static native void nglGetnHistogramARBBO(final int p0, final boolean p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glGetnMinmaxARB(final int n, final boolean b, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnMinmaxARB = capabilities.glGetnMinmaxARB;
        BufferChecks.checkFunctionAddress(glGetnMinmaxARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnMinmaxARB(n, b, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glGetnMinmaxARB);
    }
    
    public static void glGetnMinmaxARB(final int n, final boolean b, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnMinmaxARB = capabilities.glGetnMinmaxARB;
        BufferChecks.checkFunctionAddress(glGetnMinmaxARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnMinmaxARB(n, b, n2, n3, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glGetnMinmaxARB);
    }
    
    public static void glGetnMinmaxARB(final int n, final boolean b, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnMinmaxARB = capabilities.glGetnMinmaxARB;
        BufferChecks.checkFunctionAddress(glGetnMinmaxARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnMinmaxARB(n, b, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glGetnMinmaxARB);
    }
    
    public static void glGetnMinmaxARB(final int n, final boolean b, final int n2, final int n3, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnMinmaxARB = capabilities.glGetnMinmaxARB;
        BufferChecks.checkFunctionAddress(glGetnMinmaxARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglGetnMinmaxARB(n, b, n2, n3, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glGetnMinmaxARB);
    }
    
    public static void glGetnMinmaxARB(final int n, final boolean b, final int n2, final int n3, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnMinmaxARB = capabilities.glGetnMinmaxARB;
        BufferChecks.checkFunctionAddress(glGetnMinmaxARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnMinmaxARB(n, b, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glGetnMinmaxARB);
    }
    
    static native void nglGetnMinmaxARB(final int p0, final boolean p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glGetnMinmaxARB(final int n, final boolean b, final int n2, final int n3, final int n4, final long n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnMinmaxARB = capabilities.glGetnMinmaxARB;
        BufferChecks.checkFunctionAddress(glGetnMinmaxARB);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetnMinmaxARBBO(n, b, n2, n3, n4, n5, glGetnMinmaxARB);
    }
    
    static native void nglGetnMinmaxARBBO(final int p0, final boolean p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glGetnCompressedTexImageARB(final int n, final int n2, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnCompressedTexImageARB = capabilities.glGetnCompressedTexImageARB;
        BufferChecks.checkFunctionAddress(glGetnCompressedTexImageARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglGetnCompressedTexImageARB(n, n2, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glGetnCompressedTexImageARB);
    }
    
    public static void glGetnCompressedTexImageARB(final int n, final int n2, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnCompressedTexImageARB = capabilities.glGetnCompressedTexImageARB;
        BufferChecks.checkFunctionAddress(glGetnCompressedTexImageARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglGetnCompressedTexImageARB(n, n2, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glGetnCompressedTexImageARB);
    }
    
    public static void glGetnCompressedTexImageARB(final int n, final int n2, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnCompressedTexImageARB = capabilities.glGetnCompressedTexImageARB;
        BufferChecks.checkFunctionAddress(glGetnCompressedTexImageARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglGetnCompressedTexImageARB(n, n2, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glGetnCompressedTexImageARB);
    }
    
    static native void nglGetnCompressedTexImageARB(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetnCompressedTexImageARB(final int n, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetnCompressedTexImageARB = capabilities.glGetnCompressedTexImageARB;
        BufferChecks.checkFunctionAddress(glGetnCompressedTexImageARB);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetnCompressedTexImageARBBO(n, n2, n3, n4, glGetnCompressedTexImageARB);
    }
    
    static native void nglGetnCompressedTexImageARBBO(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetnUniformfvARB(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetnUniformfvARB = GLContext.getCapabilities().glGetnUniformfvARB;
        BufferChecks.checkFunctionAddress(glGetnUniformfvARB);
        BufferChecks.checkDirect(floatBuffer);
        nglGetnUniformfvARB(n, n2, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glGetnUniformfvARB);
    }
    
    static native void nglGetnUniformfvARB(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetnUniformivARB(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetnUniformivARB = GLContext.getCapabilities().glGetnUniformivARB;
        BufferChecks.checkFunctionAddress(glGetnUniformivARB);
        BufferChecks.checkDirect(intBuffer);
        nglGetnUniformivARB(n, n2, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glGetnUniformivARB);
    }
    
    static native void nglGetnUniformivARB(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetnUniformuivARB(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetnUniformuivARB = GLContext.getCapabilities().glGetnUniformuivARB;
        BufferChecks.checkFunctionAddress(glGetnUniformuivARB);
        BufferChecks.checkDirect(intBuffer);
        nglGetnUniformuivARB(n, n2, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glGetnUniformuivARB);
    }
    
    static native void nglGetnUniformuivARB(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetnUniformdvARB(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glGetnUniformdvARB = GLContext.getCapabilities().glGetnUniformdvARB;
        BufferChecks.checkFunctionAddress(glGetnUniformdvARB);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetnUniformdvARB(n, n2, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glGetnUniformdvARB);
    }
    
    static native void nglGetnUniformdvARB(final int p0, final int p1, final int p2, final long p3, final long p4);
}
