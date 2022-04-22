package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class EXTDirectStateAccess
{
    public static final int GL_PROGRAM_MATRIX_EXT = 36397;
    public static final int GL_TRANSPOSE_PROGRAM_MATRIX_EXT = 36398;
    public static final int GL_PROGRAM_MATRIX_STACK_DEPTH_EXT = 36399;
    
    private EXTDirectStateAccess() {
    }
    
    public static void glClientAttribDefaultEXT(final int n) {
        final long glClientAttribDefaultEXT = GLContext.getCapabilities().glClientAttribDefaultEXT;
        BufferChecks.checkFunctionAddress(glClientAttribDefaultEXT);
        nglClientAttribDefaultEXT(n, glClientAttribDefaultEXT);
    }
    
    static native void nglClientAttribDefaultEXT(final int p0, final long p1);
    
    public static void glPushClientAttribDefaultEXT(final int n) {
        final long glPushClientAttribDefaultEXT = GLContext.getCapabilities().glPushClientAttribDefaultEXT;
        BufferChecks.checkFunctionAddress(glPushClientAttribDefaultEXT);
        nglPushClientAttribDefaultEXT(n, glPushClientAttribDefaultEXT);
    }
    
    static native void nglPushClientAttribDefaultEXT(final int p0, final long p1);
    
    public static void glMatrixLoadEXT(final int n, final FloatBuffer floatBuffer) {
        final long glMatrixLoadfEXT = GLContext.getCapabilities().glMatrixLoadfEXT;
        BufferChecks.checkFunctionAddress(glMatrixLoadfEXT);
        BufferChecks.checkBuffer(floatBuffer, 16);
        nglMatrixLoadfEXT(n, MemoryUtil.getAddress(floatBuffer), glMatrixLoadfEXT);
    }
    
    static native void nglMatrixLoadfEXT(final int p0, final long p1, final long p2);
    
    public static void glMatrixLoadEXT(final int n, final DoubleBuffer doubleBuffer) {
        final long glMatrixLoaddEXT = GLContext.getCapabilities().glMatrixLoaddEXT;
        BufferChecks.checkFunctionAddress(glMatrixLoaddEXT);
        BufferChecks.checkBuffer(doubleBuffer, 16);
        nglMatrixLoaddEXT(n, MemoryUtil.getAddress(doubleBuffer), glMatrixLoaddEXT);
    }
    
    static native void nglMatrixLoaddEXT(final int p0, final long p1, final long p2);
    
    public static void glMatrixMultEXT(final int n, final FloatBuffer floatBuffer) {
        final long glMatrixMultfEXT = GLContext.getCapabilities().glMatrixMultfEXT;
        BufferChecks.checkFunctionAddress(glMatrixMultfEXT);
        BufferChecks.checkBuffer(floatBuffer, 16);
        nglMatrixMultfEXT(n, MemoryUtil.getAddress(floatBuffer), glMatrixMultfEXT);
    }
    
    static native void nglMatrixMultfEXT(final int p0, final long p1, final long p2);
    
    public static void glMatrixMultEXT(final int n, final DoubleBuffer doubleBuffer) {
        final long glMatrixMultdEXT = GLContext.getCapabilities().glMatrixMultdEXT;
        BufferChecks.checkFunctionAddress(glMatrixMultdEXT);
        BufferChecks.checkBuffer(doubleBuffer, 16);
        nglMatrixMultdEXT(n, MemoryUtil.getAddress(doubleBuffer), glMatrixMultdEXT);
    }
    
    static native void nglMatrixMultdEXT(final int p0, final long p1, final long p2);
    
    public static void glMatrixLoadIdentityEXT(final int n) {
        final long glMatrixLoadIdentityEXT = GLContext.getCapabilities().glMatrixLoadIdentityEXT;
        BufferChecks.checkFunctionAddress(glMatrixLoadIdentityEXT);
        nglMatrixLoadIdentityEXT(n, glMatrixLoadIdentityEXT);
    }
    
    static native void nglMatrixLoadIdentityEXT(final int p0, final long p1);
    
    public static void glMatrixRotatefEXT(final int n, final float n2, final float n3, final float n4, final float n5) {
        final long glMatrixRotatefEXT = GLContext.getCapabilities().glMatrixRotatefEXT;
        BufferChecks.checkFunctionAddress(glMatrixRotatefEXT);
        nglMatrixRotatefEXT(n, n2, n3, n4, n5, glMatrixRotatefEXT);
    }
    
    static native void nglMatrixRotatefEXT(final int p0, final float p1, final float p2, final float p3, final float p4, final long p5);
    
    public static void glMatrixRotatedEXT(final int n, final double n2, final double n3, final double n4, final double n5) {
        final long glMatrixRotatedEXT = GLContext.getCapabilities().glMatrixRotatedEXT;
        BufferChecks.checkFunctionAddress(glMatrixRotatedEXT);
        nglMatrixRotatedEXT(n, n2, n3, n4, n5, glMatrixRotatedEXT);
    }
    
    static native void nglMatrixRotatedEXT(final int p0, final double p1, final double p2, final double p3, final double p4, final long p5);
    
    public static void glMatrixScalefEXT(final int n, final float n2, final float n3, final float n4) {
        final long glMatrixScalefEXT = GLContext.getCapabilities().glMatrixScalefEXT;
        BufferChecks.checkFunctionAddress(glMatrixScalefEXT);
        nglMatrixScalefEXT(n, n2, n3, n4, glMatrixScalefEXT);
    }
    
    static native void nglMatrixScalefEXT(final int p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glMatrixScaledEXT(final int n, final double n2, final double n3, final double n4) {
        final long glMatrixScaledEXT = GLContext.getCapabilities().glMatrixScaledEXT;
        BufferChecks.checkFunctionAddress(glMatrixScaledEXT);
        nglMatrixScaledEXT(n, n2, n3, n4, glMatrixScaledEXT);
    }
    
    static native void nglMatrixScaledEXT(final int p0, final double p1, final double p2, final double p3, final long p4);
    
    public static void glMatrixTranslatefEXT(final int n, final float n2, final float n3, final float n4) {
        final long glMatrixTranslatefEXT = GLContext.getCapabilities().glMatrixTranslatefEXT;
        BufferChecks.checkFunctionAddress(glMatrixTranslatefEXT);
        nglMatrixTranslatefEXT(n, n2, n3, n4, glMatrixTranslatefEXT);
    }
    
    static native void nglMatrixTranslatefEXT(final int p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glMatrixTranslatedEXT(final int n, final double n2, final double n3, final double n4) {
        final long glMatrixTranslatedEXT = GLContext.getCapabilities().glMatrixTranslatedEXT;
        BufferChecks.checkFunctionAddress(glMatrixTranslatedEXT);
        nglMatrixTranslatedEXT(n, n2, n3, n4, glMatrixTranslatedEXT);
    }
    
    static native void nglMatrixTranslatedEXT(final int p0, final double p1, final double p2, final double p3, final long p4);
    
    public static void glMatrixOrthoEXT(final int n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7) {
        final long glMatrixOrthoEXT = GLContext.getCapabilities().glMatrixOrthoEXT;
        BufferChecks.checkFunctionAddress(glMatrixOrthoEXT);
        nglMatrixOrthoEXT(n, n2, n3, n4, n5, n6, n7, glMatrixOrthoEXT);
    }
    
    static native void nglMatrixOrthoEXT(final int p0, final double p1, final double p2, final double p3, final double p4, final double p5, final double p6, final long p7);
    
    public static void glMatrixFrustumEXT(final int n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7) {
        final long glMatrixFrustumEXT = GLContext.getCapabilities().glMatrixFrustumEXT;
        BufferChecks.checkFunctionAddress(glMatrixFrustumEXT);
        nglMatrixFrustumEXT(n, n2, n3, n4, n5, n6, n7, glMatrixFrustumEXT);
    }
    
    static native void nglMatrixFrustumEXT(final int p0, final double p1, final double p2, final double p3, final double p4, final double p5, final double p6, final long p7);
    
    public static void glMatrixPushEXT(final int n) {
        final long glMatrixPushEXT = GLContext.getCapabilities().glMatrixPushEXT;
        BufferChecks.checkFunctionAddress(glMatrixPushEXT);
        nglMatrixPushEXT(n, glMatrixPushEXT);
    }
    
    static native void nglMatrixPushEXT(final int p0, final long p1);
    
    public static void glMatrixPopEXT(final int n) {
        final long glMatrixPopEXT = GLContext.getCapabilities().glMatrixPopEXT;
        BufferChecks.checkFunctionAddress(glMatrixPopEXT);
        nglMatrixPopEXT(n, glMatrixPopEXT);
    }
    
    static native void nglMatrixPopEXT(final int p0, final long p1);
    
    public static void glTextureParameteriEXT(final int n, final int n2, final int n3, final int n4) {
        final long glTextureParameteriEXT = GLContext.getCapabilities().glTextureParameteriEXT;
        BufferChecks.checkFunctionAddress(glTextureParameteriEXT);
        nglTextureParameteriEXT(n, n2, n3, n4, glTextureParameteriEXT);
    }
    
    static native void nglTextureParameteriEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glTextureParameterEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glTextureParameterivEXT = GLContext.getCapabilities().glTextureParameterivEXT;
        BufferChecks.checkFunctionAddress(glTextureParameterivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglTextureParameterivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glTextureParameterivEXT);
    }
    
    static native void nglTextureParameterivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glTextureParameterfEXT(final int n, final int n2, final int n3, final float n4) {
        final long glTextureParameterfEXT = GLContext.getCapabilities().glTextureParameterfEXT;
        BufferChecks.checkFunctionAddress(glTextureParameterfEXT);
        nglTextureParameterfEXT(n, n2, n3, n4, glTextureParameterfEXT);
    }
    
    static native void nglTextureParameterfEXT(final int p0, final int p1, final int p2, final float p3, final long p4);
    
    public static void glTextureParameterEXT(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glTextureParameterfvEXT = GLContext.getCapabilities().glTextureParameterfvEXT;
        BufferChecks.checkFunctionAddress(glTextureParameterfvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglTextureParameterfvEXT(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glTextureParameterfvEXT);
    }
    
    static native void nglTextureParameterfvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glTextureImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage1DEXT = capabilities.glTextureImage1DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (byteBuffer != null) {
            BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateTexImage1DStorage(byteBuffer, n7, n8, n5));
        }
        nglTextureImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddressSafe(byteBuffer), glTextureImage1DEXT);
    }
    
    public static void glTextureImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage1DEXT = capabilities.glTextureImage1DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (doubleBuffer != null) {
            BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateTexImage1DStorage(doubleBuffer, n7, n8, n5));
        }
        nglTextureImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddressSafe(doubleBuffer), glTextureImage1DEXT);
    }
    
    public static void glTextureImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage1DEXT = capabilities.glTextureImage1DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateTexImage1DStorage(floatBuffer, n7, n8, n5));
        }
        nglTextureImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddressSafe(floatBuffer), glTextureImage1DEXT);
    }
    
    public static void glTextureImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage1DEXT = capabilities.glTextureImage1DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, GLChecks.calculateTexImage1DStorage(intBuffer, n7, n8, n5));
        }
        nglTextureImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddressSafe(intBuffer), glTextureImage1DEXT);
    }
    
    public static void glTextureImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage1DEXT = capabilities.glTextureImage1DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (shortBuffer != null) {
            BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateTexImage1DStorage(shortBuffer, n7, n8, n5));
        }
        nglTextureImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddressSafe(shortBuffer), glTextureImage1DEXT);
    }
    
    static native void nglTextureImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glTextureImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage1DEXT = capabilities.glTextureImage1DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage1DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglTextureImage1DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, glTextureImage1DEXT);
    }
    
    static native void nglTextureImage1DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glTextureImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage2DEXT = capabilities.glTextureImage2DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (byteBuffer != null) {
            BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateTexImage2DStorage(byteBuffer, n8, n9, n5, n6));
        }
        nglTextureImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddressSafe(byteBuffer), glTextureImage2DEXT);
    }
    
    public static void glTextureImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage2DEXT = capabilities.glTextureImage2DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (doubleBuffer != null) {
            BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateTexImage2DStorage(doubleBuffer, n8, n9, n5, n6));
        }
        nglTextureImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddressSafe(doubleBuffer), glTextureImage2DEXT);
    }
    
    public static void glTextureImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage2DEXT = capabilities.glTextureImage2DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateTexImage2DStorage(floatBuffer, n8, n9, n5, n6));
        }
        nglTextureImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddressSafe(floatBuffer), glTextureImage2DEXT);
    }
    
    public static void glTextureImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage2DEXT = capabilities.glTextureImage2DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, GLChecks.calculateTexImage2DStorage(intBuffer, n8, n9, n5, n6));
        }
        nglTextureImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddressSafe(intBuffer), glTextureImage2DEXT);
    }
    
    public static void glTextureImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage2DEXT = capabilities.glTextureImage2DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (shortBuffer != null) {
            BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateTexImage2DStorage(shortBuffer, n8, n9, n5, n6));
        }
        nglTextureImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddressSafe(shortBuffer), glTextureImage2DEXT);
    }
    
    static native void nglTextureImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glTextureImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final long n10) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage2DEXT = capabilities.glTextureImage2DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage2DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglTextureImage2DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, glTextureImage2DEXT);
    }
    
    static native void nglTextureImage2DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glTextureSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage1DEXT = capabilities.glTextureSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n6, n7, n5, 1, 1));
        nglTextureSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.getAddress(byteBuffer), glTextureSubImage1DEXT);
    }
    
    public static void glTextureSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage1DEXT = capabilities.glTextureSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n6, n7, n5, 1, 1));
        nglTextureSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.getAddress(doubleBuffer), glTextureSubImage1DEXT);
    }
    
    public static void glTextureSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage1DEXT = capabilities.glTextureSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n6, n7, n5, 1, 1));
        nglTextureSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.getAddress(floatBuffer), glTextureSubImage1DEXT);
    }
    
    public static void glTextureSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage1DEXT = capabilities.glTextureSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n6, n7, n5, 1, 1));
        nglTextureSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.getAddress(intBuffer), glTextureSubImage1DEXT);
    }
    
    public static void glTextureSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage1DEXT = capabilities.glTextureSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n6, n7, n5, 1, 1));
        nglTextureSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.getAddress(shortBuffer), glTextureSubImage1DEXT);
    }
    
    static native void nglTextureSubImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glTextureSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage1DEXT = capabilities.glTextureSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage1DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglTextureSubImage1DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, glTextureSubImage1DEXT);
    }
    
    static native void nglTextureSubImage1DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glTextureSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage2DEXT = capabilities.glTextureSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n8, n9, n6, n7, 1));
        nglTextureSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddress(byteBuffer), glTextureSubImage2DEXT);
    }
    
    public static void glTextureSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage2DEXT = capabilities.glTextureSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n8, n9, n6, n7, 1));
        nglTextureSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddress(doubleBuffer), glTextureSubImage2DEXT);
    }
    
    public static void glTextureSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage2DEXT = capabilities.glTextureSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n8, n9, n6, n7, 1));
        nglTextureSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddress(floatBuffer), glTextureSubImage2DEXT);
    }
    
    public static void glTextureSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage2DEXT = capabilities.glTextureSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n8, n9, n6, n7, 1));
        nglTextureSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddress(intBuffer), glTextureSubImage2DEXT);
    }
    
    public static void glTextureSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage2DEXT = capabilities.glTextureSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n8, n9, n6, n7, 1));
        nglTextureSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddress(shortBuffer), glTextureSubImage2DEXT);
    }
    
    static native void nglTextureSubImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glTextureSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final long n10) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage2DEXT = capabilities.glTextureSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage2DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglTextureSubImage2DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, glTextureSubImage2DEXT);
    }
    
    static native void nglTextureSubImage2DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glCopyTextureImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        final long glCopyTextureImage1DEXT = GLContext.getCapabilities().glCopyTextureImage1DEXT;
        BufferChecks.checkFunctionAddress(glCopyTextureImage1DEXT);
        nglCopyTextureImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, glCopyTextureImage1DEXT);
    }
    
    static native void nglCopyTextureImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    public static void glCopyTextureImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9) {
        final long glCopyTextureImage2DEXT = GLContext.getCapabilities().glCopyTextureImage2DEXT;
        BufferChecks.checkFunctionAddress(glCopyTextureImage2DEXT);
        nglCopyTextureImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, glCopyTextureImage2DEXT);
    }
    
    static native void nglCopyTextureImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9);
    
    public static void glCopyTextureSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        final long glCopyTextureSubImage1DEXT = GLContext.getCapabilities().glCopyTextureSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glCopyTextureSubImage1DEXT);
        nglCopyTextureSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, glCopyTextureSubImage1DEXT);
    }
    
    static native void nglCopyTextureSubImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7);
    
    public static void glCopyTextureSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9) {
        final long glCopyTextureSubImage2DEXT = GLContext.getCapabilities().glCopyTextureSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glCopyTextureSubImage2DEXT);
        nglCopyTextureSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, glCopyTextureSubImage2DEXT);
    }
    
    static native void nglCopyTextureSubImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9);
    
    public static void glGetTextureImageEXT(final int n, final int n2, final int n3, final int n4, final int n5, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureImageEXT = capabilities.glGetTextureImageEXT;
        BufferChecks.checkFunctionAddress(glGetTextureImageEXT);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n4, n5, 1, 1, 1));
        nglGetTextureImageEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(byteBuffer), glGetTextureImageEXT);
    }
    
    public static void glGetTextureImageEXT(final int n, final int n2, final int n3, final int n4, final int n5, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureImageEXT = capabilities.glGetTextureImageEXT;
        BufferChecks.checkFunctionAddress(glGetTextureImageEXT);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n4, n5, 1, 1, 1));
        nglGetTextureImageEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(doubleBuffer), glGetTextureImageEXT);
    }
    
    public static void glGetTextureImageEXT(final int n, final int n2, final int n3, final int n4, final int n5, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureImageEXT = capabilities.glGetTextureImageEXT;
        BufferChecks.checkFunctionAddress(glGetTextureImageEXT);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n4, n5, 1, 1, 1));
        nglGetTextureImageEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(floatBuffer), glGetTextureImageEXT);
    }
    
    public static void glGetTextureImageEXT(final int n, final int n2, final int n3, final int n4, final int n5, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureImageEXT = capabilities.glGetTextureImageEXT;
        BufferChecks.checkFunctionAddress(glGetTextureImageEXT);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n4, n5, 1, 1, 1));
        nglGetTextureImageEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(intBuffer), glGetTextureImageEXT);
    }
    
    public static void glGetTextureImageEXT(final int n, final int n2, final int n3, final int n4, final int n5, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureImageEXT = capabilities.glGetTextureImageEXT;
        BufferChecks.checkFunctionAddress(glGetTextureImageEXT);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n4, n5, 1, 1, 1));
        nglGetTextureImageEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(shortBuffer), glGetTextureImageEXT);
    }
    
    static native void nglGetTextureImageEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glGetTextureImageEXT(final int n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureImageEXT = capabilities.glGetTextureImageEXT;
        BufferChecks.checkFunctionAddress(glGetTextureImageEXT);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetTextureImageEXTBO(n, n2, n3, n4, n5, n6, glGetTextureImageEXT);
    }
    
    static native void nglGetTextureImageEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glGetTextureParameterEXT(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glGetTextureParameterfvEXT = GLContext.getCapabilities().glGetTextureParameterfvEXT;
        BufferChecks.checkFunctionAddress(glGetTextureParameterfvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetTextureParameterfvEXT(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glGetTextureParameterfvEXT);
    }
    
    static native void nglGetTextureParameterfvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static float glGetTextureParameterfEXT(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureParameterfvEXT = capabilities.glGetTextureParameterfvEXT;
        BufferChecks.checkFunctionAddress(glGetTextureParameterfvEXT);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetTextureParameterfvEXT(n, n2, n3, MemoryUtil.getAddress(bufferFloat), glGetTextureParameterfvEXT);
        return bufferFloat.get(0);
    }
    
    public static void glGetTextureParameterEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetTextureParameterivEXT = GLContext.getCapabilities().glGetTextureParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetTextureParameterivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetTextureParameterivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetTextureParameterivEXT);
    }
    
    static native void nglGetTextureParameterivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetTextureParameteriEXT(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureParameterivEXT = capabilities.glGetTextureParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetTextureParameterivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTextureParameterivEXT(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetTextureParameterivEXT);
        return bufferInt.get(0);
    }
    
    public static void glGetTextureLevelParameterEXT(final int n, final int n2, final int n3, final int n4, final FloatBuffer floatBuffer) {
        final long glGetTextureLevelParameterfvEXT = GLContext.getCapabilities().glGetTextureLevelParameterfvEXT;
        BufferChecks.checkFunctionAddress(glGetTextureLevelParameterfvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetTextureLevelParameterfvEXT(n, n2, n3, n4, MemoryUtil.getAddress(floatBuffer), glGetTextureLevelParameterfvEXT);
    }
    
    static native void nglGetTextureLevelParameterfvEXT(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static float glGetTextureLevelParameterfEXT(final int n, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureLevelParameterfvEXT = capabilities.glGetTextureLevelParameterfvEXT;
        BufferChecks.checkFunctionAddress(glGetTextureLevelParameterfvEXT);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetTextureLevelParameterfvEXT(n, n2, n3, n4, MemoryUtil.getAddress(bufferFloat), glGetTextureLevelParameterfvEXT);
        return bufferFloat.get(0);
    }
    
    public static void glGetTextureLevelParameterEXT(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final long glGetTextureLevelParameterivEXT = GLContext.getCapabilities().glGetTextureLevelParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetTextureLevelParameterivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetTextureLevelParameterivEXT(n, n2, n3, n4, MemoryUtil.getAddress(intBuffer), glGetTextureLevelParameterivEXT);
    }
    
    static native void nglGetTextureLevelParameterivEXT(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static int glGetTextureLevelParameteriEXT(final int n, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureLevelParameterivEXT = capabilities.glGetTextureLevelParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetTextureLevelParameterivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTextureLevelParameterivEXT(n, n2, n3, n4, MemoryUtil.getAddress(bufferInt), glGetTextureLevelParameterivEXT);
        return bufferInt.get(0);
    }
    
    public static void glTextureImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage3DEXT = capabilities.glTextureImage3DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (byteBuffer != null) {
            BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateTexImage3DStorage(byteBuffer, n9, n10, n5, n6, n7));
        }
        nglTextureImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddressSafe(byteBuffer), glTextureImage3DEXT);
    }
    
    public static void glTextureImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage3DEXT = capabilities.glTextureImage3DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (doubleBuffer != null) {
            BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateTexImage3DStorage(doubleBuffer, n9, n10, n5, n6, n7));
        }
        nglTextureImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddressSafe(doubleBuffer), glTextureImage3DEXT);
    }
    
    public static void glTextureImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage3DEXT = capabilities.glTextureImage3DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateTexImage3DStorage(floatBuffer, n9, n10, n5, n6, n7));
        }
        nglTextureImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddressSafe(floatBuffer), glTextureImage3DEXT);
    }
    
    public static void glTextureImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage3DEXT = capabilities.glTextureImage3DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, GLChecks.calculateTexImage3DStorage(intBuffer, n9, n10, n5, n6, n7));
        }
        nglTextureImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddressSafe(intBuffer), glTextureImage3DEXT);
    }
    
    public static void glTextureImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage3DEXT = capabilities.glTextureImage3DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (shortBuffer != null) {
            BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateTexImage3DStorage(shortBuffer, n9, n10, n5, n6, n7));
        }
        nglTextureImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddressSafe(shortBuffer), glTextureImage3DEXT);
    }
    
    static native void nglTextureImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10, final long p11);
    
    public static void glTextureImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final long n11) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureImage3DEXT = capabilities.glTextureImage3DEXT;
        BufferChecks.checkFunctionAddress(glTextureImage3DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglTextureImage3DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, glTextureImage3DEXT);
    }
    
    static native void nglTextureImage3DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10, final long p11);
    
    public static void glTextureSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage3DEXT = capabilities.glTextureSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n10, n11, n7, n8, n9));
        nglTextureSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.getAddress(byteBuffer), glTextureSubImage3DEXT);
    }
    
    public static void glTextureSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage3DEXT = capabilities.glTextureSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n10, n11, n7, n8, n9));
        nglTextureSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.getAddress(doubleBuffer), glTextureSubImage3DEXT);
    }
    
    public static void glTextureSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage3DEXT = capabilities.glTextureSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n10, n11, n7, n8, n9));
        nglTextureSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.getAddress(floatBuffer), glTextureSubImage3DEXT);
    }
    
    public static void glTextureSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage3DEXT = capabilities.glTextureSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n10, n11, n7, n8, n9));
        nglTextureSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.getAddress(intBuffer), glTextureSubImage3DEXT);
    }
    
    public static void glTextureSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage3DEXT = capabilities.glTextureSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n10, n11, n7, n8, n9));
        nglTextureSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.getAddress(shortBuffer), glTextureSubImage3DEXT);
    }
    
    static native void nglTextureSubImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final long p11, final long p12);
    
    public static void glTextureSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final long n12) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureSubImage3DEXT = capabilities.glTextureSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glTextureSubImage3DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglTextureSubImage3DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, glTextureSubImage3DEXT);
    }
    
    static native void nglTextureSubImage3DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final long p11, final long p12);
    
    public static void glCopyTextureSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        final long glCopyTextureSubImage3DEXT = GLContext.getCapabilities().glCopyTextureSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glCopyTextureSubImage3DEXT);
        nglCopyTextureSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, glCopyTextureSubImage3DEXT);
    }
    
    static native void nglCopyTextureSubImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10);
    
    public static void glBindMultiTextureEXT(final int n, final int n2, final int n3) {
        final long glBindMultiTextureEXT = GLContext.getCapabilities().glBindMultiTextureEXT;
        BufferChecks.checkFunctionAddress(glBindMultiTextureEXT);
        nglBindMultiTextureEXT(n, n2, n3, glBindMultiTextureEXT);
    }
    
    static native void nglBindMultiTextureEXT(final int p0, final int p1, final int p2, final long p3);
    
    public static void glMultiTexCoordPointerEXT(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexCoordPointerEXT = capabilities.glMultiTexCoordPointerEXT;
        BufferChecks.checkFunctionAddress(glMultiTexCoordPointerEXT);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        nglMultiTexCoordPointerEXT(n, n2, 5130, n3, MemoryUtil.getAddress(doubleBuffer), glMultiTexCoordPointerEXT);
    }
    
    public static void glMultiTexCoordPointerEXT(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexCoordPointerEXT = capabilities.glMultiTexCoordPointerEXT;
        BufferChecks.checkFunctionAddress(glMultiTexCoordPointerEXT);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        nglMultiTexCoordPointerEXT(n, n2, 5126, n3, MemoryUtil.getAddress(floatBuffer), glMultiTexCoordPointerEXT);
    }
    
    static native void nglMultiTexCoordPointerEXT(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glMultiTexCoordPointerEXT(final int n, final int n2, final int n3, final int n4, final long n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexCoordPointerEXT = capabilities.glMultiTexCoordPointerEXT;
        BufferChecks.checkFunctionAddress(glMultiTexCoordPointerEXT);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglMultiTexCoordPointerEXTBO(n, n2, n3, n4, n5, glMultiTexCoordPointerEXT);
    }
    
    static native void nglMultiTexCoordPointerEXTBO(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glMultiTexEnvfEXT(final int n, final int n2, final int n3, final float n4) {
        final long glMultiTexEnvfEXT = GLContext.getCapabilities().glMultiTexEnvfEXT;
        BufferChecks.checkFunctionAddress(glMultiTexEnvfEXT);
        nglMultiTexEnvfEXT(n, n2, n3, n4, glMultiTexEnvfEXT);
    }
    
    static native void nglMultiTexEnvfEXT(final int p0, final int p1, final int p2, final float p3, final long p4);
    
    public static void glMultiTexEnvEXT(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glMultiTexEnvfvEXT = GLContext.getCapabilities().glMultiTexEnvfvEXT;
        BufferChecks.checkFunctionAddress(glMultiTexEnvfvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglMultiTexEnvfvEXT(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glMultiTexEnvfvEXT);
    }
    
    static native void nglMultiTexEnvfvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glMultiTexEnviEXT(final int n, final int n2, final int n3, final int n4) {
        final long glMultiTexEnviEXT = GLContext.getCapabilities().glMultiTexEnviEXT;
        BufferChecks.checkFunctionAddress(glMultiTexEnviEXT);
        nglMultiTexEnviEXT(n, n2, n3, n4, glMultiTexEnviEXT);
    }
    
    static native void nglMultiTexEnviEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glMultiTexEnvEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glMultiTexEnvivEXT = GLContext.getCapabilities().glMultiTexEnvivEXT;
        BufferChecks.checkFunctionAddress(glMultiTexEnvivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglMultiTexEnvivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glMultiTexEnvivEXT);
    }
    
    static native void nglMultiTexEnvivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glMultiTexGendEXT(final int n, final int n2, final int n3, final double n4) {
        final long glMultiTexGendEXT = GLContext.getCapabilities().glMultiTexGendEXT;
        BufferChecks.checkFunctionAddress(glMultiTexGendEXT);
        nglMultiTexGendEXT(n, n2, n3, n4, glMultiTexGendEXT);
    }
    
    static native void nglMultiTexGendEXT(final int p0, final int p1, final int p2, final double p3, final long p4);
    
    public static void glMultiTexGenEXT(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final long glMultiTexGendvEXT = GLContext.getCapabilities().glMultiTexGendvEXT;
        BufferChecks.checkFunctionAddress(glMultiTexGendvEXT);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglMultiTexGendvEXT(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), glMultiTexGendvEXT);
    }
    
    static native void nglMultiTexGendvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glMultiTexGenfEXT(final int n, final int n2, final int n3, final float n4) {
        final long glMultiTexGenfEXT = GLContext.getCapabilities().glMultiTexGenfEXT;
        BufferChecks.checkFunctionAddress(glMultiTexGenfEXT);
        nglMultiTexGenfEXT(n, n2, n3, n4, glMultiTexGenfEXT);
    }
    
    static native void nglMultiTexGenfEXT(final int p0, final int p1, final int p2, final float p3, final long p4);
    
    public static void glMultiTexGenEXT(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glMultiTexGenfvEXT = GLContext.getCapabilities().glMultiTexGenfvEXT;
        BufferChecks.checkFunctionAddress(glMultiTexGenfvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglMultiTexGenfvEXT(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glMultiTexGenfvEXT);
    }
    
    static native void nglMultiTexGenfvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glMultiTexGeniEXT(final int n, final int n2, final int n3, final int n4) {
        final long glMultiTexGeniEXT = GLContext.getCapabilities().glMultiTexGeniEXT;
        BufferChecks.checkFunctionAddress(glMultiTexGeniEXT);
        nglMultiTexGeniEXT(n, n2, n3, n4, glMultiTexGeniEXT);
    }
    
    static native void nglMultiTexGeniEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glMultiTexGenEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glMultiTexGenivEXT = GLContext.getCapabilities().glMultiTexGenivEXT;
        BufferChecks.checkFunctionAddress(glMultiTexGenivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglMultiTexGenivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glMultiTexGenivEXT);
    }
    
    static native void nglMultiTexGenivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetMultiTexEnvEXT(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glGetMultiTexEnvfvEXT = GLContext.getCapabilities().glGetMultiTexEnvfvEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexEnvfvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetMultiTexEnvfvEXT(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glGetMultiTexEnvfvEXT);
    }
    
    static native void nglGetMultiTexEnvfvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetMultiTexEnvEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetMultiTexEnvivEXT = GLContext.getCapabilities().glGetMultiTexEnvivEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexEnvivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetMultiTexEnvivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetMultiTexEnvivEXT);
    }
    
    static native void nglGetMultiTexEnvivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetMultiTexGenEXT(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final long glGetMultiTexGendvEXT = GLContext.getCapabilities().glGetMultiTexGendvEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexGendvEXT);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglGetMultiTexGendvEXT(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), glGetMultiTexGendvEXT);
    }
    
    static native void nglGetMultiTexGendvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetMultiTexGenEXT(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glGetMultiTexGenfvEXT = GLContext.getCapabilities().glGetMultiTexGenfvEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexGenfvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetMultiTexGenfvEXT(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glGetMultiTexGenfvEXT);
    }
    
    static native void nglGetMultiTexGenfvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetMultiTexGenEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetMultiTexGenivEXT = GLContext.getCapabilities().glGetMultiTexGenivEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexGenivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetMultiTexGenivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetMultiTexGenivEXT);
    }
    
    static native void nglGetMultiTexGenivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glMultiTexParameteriEXT(final int n, final int n2, final int n3, final int n4) {
        final long glMultiTexParameteriEXT = GLContext.getCapabilities().glMultiTexParameteriEXT;
        BufferChecks.checkFunctionAddress(glMultiTexParameteriEXT);
        nglMultiTexParameteriEXT(n, n2, n3, n4, glMultiTexParameteriEXT);
    }
    
    static native void nglMultiTexParameteriEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glMultiTexParameterEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glMultiTexParameterivEXT = GLContext.getCapabilities().glMultiTexParameterivEXT;
        BufferChecks.checkFunctionAddress(glMultiTexParameterivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglMultiTexParameterivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glMultiTexParameterivEXT);
    }
    
    static native void nglMultiTexParameterivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glMultiTexParameterfEXT(final int n, final int n2, final int n3, final float n4) {
        final long glMultiTexParameterfEXT = GLContext.getCapabilities().glMultiTexParameterfEXT;
        BufferChecks.checkFunctionAddress(glMultiTexParameterfEXT);
        nglMultiTexParameterfEXT(n, n2, n3, n4, glMultiTexParameterfEXT);
    }
    
    static native void nglMultiTexParameterfEXT(final int p0, final int p1, final int p2, final float p3, final long p4);
    
    public static void glMultiTexParameterEXT(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glMultiTexParameterfvEXT = GLContext.getCapabilities().glMultiTexParameterfvEXT;
        BufferChecks.checkFunctionAddress(glMultiTexParameterfvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglMultiTexParameterfvEXT(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glMultiTexParameterfvEXT);
    }
    
    static native void nglMultiTexParameterfvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glMultiTexImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage1DEXT = capabilities.glMultiTexImage1DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (byteBuffer != null) {
            BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateTexImage1DStorage(byteBuffer, n7, n8, n5));
        }
        nglMultiTexImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddressSafe(byteBuffer), glMultiTexImage1DEXT);
    }
    
    public static void glMultiTexImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage1DEXT = capabilities.glMultiTexImage1DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (doubleBuffer != null) {
            BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateTexImage1DStorage(doubleBuffer, n7, n8, n5));
        }
        nglMultiTexImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddressSafe(doubleBuffer), glMultiTexImage1DEXT);
    }
    
    public static void glMultiTexImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage1DEXT = capabilities.glMultiTexImage1DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateTexImage1DStorage(floatBuffer, n7, n8, n5));
        }
        nglMultiTexImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddressSafe(floatBuffer), glMultiTexImage1DEXT);
    }
    
    public static void glMultiTexImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage1DEXT = capabilities.glMultiTexImage1DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, GLChecks.calculateTexImage1DStorage(intBuffer, n7, n8, n5));
        }
        nglMultiTexImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddressSafe(intBuffer), glMultiTexImage1DEXT);
    }
    
    public static void glMultiTexImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage1DEXT = capabilities.glMultiTexImage1DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (shortBuffer != null) {
            BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateTexImage1DStorage(shortBuffer, n7, n8, n5));
        }
        nglMultiTexImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddressSafe(shortBuffer), glMultiTexImage1DEXT);
    }
    
    static native void nglMultiTexImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glMultiTexImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage1DEXT = capabilities.glMultiTexImage1DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage1DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglMultiTexImage1DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, glMultiTexImage1DEXT);
    }
    
    static native void nglMultiTexImage1DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glMultiTexImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage2DEXT = capabilities.glMultiTexImage2DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (byteBuffer != null) {
            BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateTexImage2DStorage(byteBuffer, n8, n9, n5, n6));
        }
        nglMultiTexImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddressSafe(byteBuffer), glMultiTexImage2DEXT);
    }
    
    public static void glMultiTexImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage2DEXT = capabilities.glMultiTexImage2DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (doubleBuffer != null) {
            BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateTexImage2DStorage(doubleBuffer, n8, n9, n5, n6));
        }
        nglMultiTexImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddressSafe(doubleBuffer), glMultiTexImage2DEXT);
    }
    
    public static void glMultiTexImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage2DEXT = capabilities.glMultiTexImage2DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateTexImage2DStorage(floatBuffer, n8, n9, n5, n6));
        }
        nglMultiTexImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddressSafe(floatBuffer), glMultiTexImage2DEXT);
    }
    
    public static void glMultiTexImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage2DEXT = capabilities.glMultiTexImage2DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, GLChecks.calculateTexImage2DStorage(intBuffer, n8, n9, n5, n6));
        }
        nglMultiTexImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddressSafe(intBuffer), glMultiTexImage2DEXT);
    }
    
    public static void glMultiTexImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage2DEXT = capabilities.glMultiTexImage2DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (shortBuffer != null) {
            BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateTexImage2DStorage(shortBuffer, n8, n9, n5, n6));
        }
        nglMultiTexImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddressSafe(shortBuffer), glMultiTexImage2DEXT);
    }
    
    static native void nglMultiTexImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glMultiTexImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final long n10) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage2DEXT = capabilities.glMultiTexImage2DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage2DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglMultiTexImage2DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, glMultiTexImage2DEXT);
    }
    
    static native void nglMultiTexImage2DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glMultiTexSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage1DEXT = capabilities.glMultiTexSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n6, n7, n5, 1, 1));
        nglMultiTexSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.getAddress(byteBuffer), glMultiTexSubImage1DEXT);
    }
    
    public static void glMultiTexSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage1DEXT = capabilities.glMultiTexSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n6, n7, n5, 1, 1));
        nglMultiTexSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.getAddress(doubleBuffer), glMultiTexSubImage1DEXT);
    }
    
    public static void glMultiTexSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage1DEXT = capabilities.glMultiTexSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n6, n7, n5, 1, 1));
        nglMultiTexSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.getAddress(floatBuffer), glMultiTexSubImage1DEXT);
    }
    
    public static void glMultiTexSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage1DEXT = capabilities.glMultiTexSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n6, n7, n5, 1, 1));
        nglMultiTexSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.getAddress(intBuffer), glMultiTexSubImage1DEXT);
    }
    
    public static void glMultiTexSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage1DEXT = capabilities.glMultiTexSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n6, n7, n5, 1, 1));
        nglMultiTexSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.getAddress(shortBuffer), glMultiTexSubImage1DEXT);
    }
    
    static native void nglMultiTexSubImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glMultiTexSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage1DEXT = capabilities.glMultiTexSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage1DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglMultiTexSubImage1DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, glMultiTexSubImage1DEXT);
    }
    
    static native void nglMultiTexSubImage1DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glMultiTexSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage2DEXT = capabilities.glMultiTexSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n8, n9, n6, n7, 1));
        nglMultiTexSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddress(byteBuffer), glMultiTexSubImage2DEXT);
    }
    
    public static void glMultiTexSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage2DEXT = capabilities.glMultiTexSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n8, n9, n6, n7, 1));
        nglMultiTexSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddress(doubleBuffer), glMultiTexSubImage2DEXT);
    }
    
    public static void glMultiTexSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage2DEXT = capabilities.glMultiTexSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n8, n9, n6, n7, 1));
        nglMultiTexSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddress(floatBuffer), glMultiTexSubImage2DEXT);
    }
    
    public static void glMultiTexSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage2DEXT = capabilities.glMultiTexSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n8, n9, n6, n7, 1));
        nglMultiTexSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddress(intBuffer), glMultiTexSubImage2DEXT);
    }
    
    public static void glMultiTexSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage2DEXT = capabilities.glMultiTexSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n8, n9, n6, n7, 1));
        nglMultiTexSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddress(shortBuffer), glMultiTexSubImage2DEXT);
    }
    
    static native void nglMultiTexSubImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glMultiTexSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final long n10) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage2DEXT = capabilities.glMultiTexSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage2DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglMultiTexSubImage2DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, glMultiTexSubImage2DEXT);
    }
    
    static native void nglMultiTexSubImage2DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glCopyMultiTexImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        final long glCopyMultiTexImage1DEXT = GLContext.getCapabilities().glCopyMultiTexImage1DEXT;
        BufferChecks.checkFunctionAddress(glCopyMultiTexImage1DEXT);
        nglCopyMultiTexImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, glCopyMultiTexImage1DEXT);
    }
    
    static native void nglCopyMultiTexImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    public static void glCopyMultiTexImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9) {
        final long glCopyMultiTexImage2DEXT = GLContext.getCapabilities().glCopyMultiTexImage2DEXT;
        BufferChecks.checkFunctionAddress(glCopyMultiTexImage2DEXT);
        nglCopyMultiTexImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, glCopyMultiTexImage2DEXT);
    }
    
    static native void nglCopyMultiTexImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9);
    
    public static void glCopyMultiTexSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        final long glCopyMultiTexSubImage1DEXT = GLContext.getCapabilities().glCopyMultiTexSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glCopyMultiTexSubImage1DEXT);
        nglCopyMultiTexSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, glCopyMultiTexSubImage1DEXT);
    }
    
    static native void nglCopyMultiTexSubImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7);
    
    public static void glCopyMultiTexSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9) {
        final long glCopyMultiTexSubImage2DEXT = GLContext.getCapabilities().glCopyMultiTexSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glCopyMultiTexSubImage2DEXT);
        nglCopyMultiTexSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, glCopyMultiTexSubImage2DEXT);
    }
    
    static native void nglCopyMultiTexSubImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9);
    
    public static void glGetMultiTexImageEXT(final int n, final int n2, final int n3, final int n4, final int n5, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMultiTexImageEXT = capabilities.glGetMultiTexImageEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexImageEXT);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n4, n5, 1, 1, 1));
        nglGetMultiTexImageEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(byteBuffer), glGetMultiTexImageEXT);
    }
    
    public static void glGetMultiTexImageEXT(final int n, final int n2, final int n3, final int n4, final int n5, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMultiTexImageEXT = capabilities.glGetMultiTexImageEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexImageEXT);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n4, n5, 1, 1, 1));
        nglGetMultiTexImageEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(doubleBuffer), glGetMultiTexImageEXT);
    }
    
    public static void glGetMultiTexImageEXT(final int n, final int n2, final int n3, final int n4, final int n5, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMultiTexImageEXT = capabilities.glGetMultiTexImageEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexImageEXT);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n4, n5, 1, 1, 1));
        nglGetMultiTexImageEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(floatBuffer), glGetMultiTexImageEXT);
    }
    
    public static void glGetMultiTexImageEXT(final int n, final int n2, final int n3, final int n4, final int n5, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMultiTexImageEXT = capabilities.glGetMultiTexImageEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexImageEXT);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n4, n5, 1, 1, 1));
        nglGetMultiTexImageEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(intBuffer), glGetMultiTexImageEXT);
    }
    
    public static void glGetMultiTexImageEXT(final int n, final int n2, final int n3, final int n4, final int n5, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMultiTexImageEXT = capabilities.glGetMultiTexImageEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexImageEXT);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n4, n5, 1, 1, 1));
        nglGetMultiTexImageEXT(n, n2, n3, n4, n5, MemoryUtil.getAddress(shortBuffer), glGetMultiTexImageEXT);
    }
    
    static native void nglGetMultiTexImageEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glGetMultiTexImageEXT(final int n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMultiTexImageEXT = capabilities.glGetMultiTexImageEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexImageEXT);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetMultiTexImageEXTBO(n, n2, n3, n4, n5, n6, glGetMultiTexImageEXT);
    }
    
    static native void nglGetMultiTexImageEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glGetMultiTexParameterEXT(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glGetMultiTexParameterfvEXT = GLContext.getCapabilities().glGetMultiTexParameterfvEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexParameterfvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetMultiTexParameterfvEXT(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glGetMultiTexParameterfvEXT);
    }
    
    static native void nglGetMultiTexParameterfvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static float glGetMultiTexParameterfEXT(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMultiTexParameterfvEXT = capabilities.glGetMultiTexParameterfvEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexParameterfvEXT);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetMultiTexParameterfvEXT(n, n2, n3, MemoryUtil.getAddress(bufferFloat), glGetMultiTexParameterfvEXT);
        return bufferFloat.get(0);
    }
    
    public static void glGetMultiTexParameterEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetMultiTexParameterivEXT = GLContext.getCapabilities().glGetMultiTexParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexParameterivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetMultiTexParameterivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetMultiTexParameterivEXT);
    }
    
    static native void nglGetMultiTexParameterivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetMultiTexParameteriEXT(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMultiTexParameterivEXT = capabilities.glGetMultiTexParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexParameterivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetMultiTexParameterivEXT(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetMultiTexParameterivEXT);
        return bufferInt.get(0);
    }
    
    public static void glGetMultiTexLevelParameterEXT(final int n, final int n2, final int n3, final int n4, final FloatBuffer floatBuffer) {
        final long glGetMultiTexLevelParameterfvEXT = GLContext.getCapabilities().glGetMultiTexLevelParameterfvEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexLevelParameterfvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetMultiTexLevelParameterfvEXT(n, n2, n3, n4, MemoryUtil.getAddress(floatBuffer), glGetMultiTexLevelParameterfvEXT);
    }
    
    static native void nglGetMultiTexLevelParameterfvEXT(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static float glGetMultiTexLevelParameterfEXT(final int n, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMultiTexLevelParameterfvEXT = capabilities.glGetMultiTexLevelParameterfvEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexLevelParameterfvEXT);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetMultiTexLevelParameterfvEXT(n, n2, n3, n4, MemoryUtil.getAddress(bufferFloat), glGetMultiTexLevelParameterfvEXT);
        return bufferFloat.get(0);
    }
    
    public static void glGetMultiTexLevelParameterEXT(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final long glGetMultiTexLevelParameterivEXT = GLContext.getCapabilities().glGetMultiTexLevelParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexLevelParameterivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetMultiTexLevelParameterivEXT(n, n2, n3, n4, MemoryUtil.getAddress(intBuffer), glGetMultiTexLevelParameterivEXT);
    }
    
    static native void nglGetMultiTexLevelParameterivEXT(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static int glGetMultiTexLevelParameteriEXT(final int n, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMultiTexLevelParameterivEXT = capabilities.glGetMultiTexLevelParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexLevelParameterivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetMultiTexLevelParameterivEXT(n, n2, n3, n4, MemoryUtil.getAddress(bufferInt), glGetMultiTexLevelParameterivEXT);
        return bufferInt.get(0);
    }
    
    public static void glMultiTexImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage3DEXT = capabilities.glMultiTexImage3DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (byteBuffer != null) {
            BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateTexImage3DStorage(byteBuffer, n9, n10, n5, n6, n7));
        }
        nglMultiTexImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddressSafe(byteBuffer), glMultiTexImage3DEXT);
    }
    
    public static void glMultiTexImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage3DEXT = capabilities.glMultiTexImage3DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (doubleBuffer != null) {
            BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateTexImage3DStorage(doubleBuffer, n9, n10, n5, n6, n7));
        }
        nglMultiTexImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddressSafe(doubleBuffer), glMultiTexImage3DEXT);
    }
    
    public static void glMultiTexImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage3DEXT = capabilities.glMultiTexImage3DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateTexImage3DStorage(floatBuffer, n9, n10, n5, n6, n7));
        }
        nglMultiTexImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddressSafe(floatBuffer), glMultiTexImage3DEXT);
    }
    
    public static void glMultiTexImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage3DEXT = capabilities.glMultiTexImage3DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, GLChecks.calculateTexImage3DStorage(intBuffer, n9, n10, n5, n6, n7));
        }
        nglMultiTexImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddressSafe(intBuffer), glMultiTexImage3DEXT);
    }
    
    public static void glMultiTexImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage3DEXT = capabilities.glMultiTexImage3DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (shortBuffer != null) {
            BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateTexImage3DStorage(shortBuffer, n9, n10, n5, n6, n7));
        }
        nglMultiTexImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddressSafe(shortBuffer), glMultiTexImage3DEXT);
    }
    
    static native void nglMultiTexImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10, final long p11);
    
    public static void glMultiTexImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final long n11) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexImage3DEXT = capabilities.glMultiTexImage3DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexImage3DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglMultiTexImage3DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, glMultiTexImage3DEXT);
    }
    
    static native void nglMultiTexImage3DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10, final long p11);
    
    public static void glMultiTexSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage3DEXT = capabilities.glMultiTexSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n10, n11, n7, n8, n9));
        nglMultiTexSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.getAddress(byteBuffer), glMultiTexSubImage3DEXT);
    }
    
    public static void glMultiTexSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage3DEXT = capabilities.glMultiTexSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n10, n11, n7, n8, n9));
        nglMultiTexSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.getAddress(doubleBuffer), glMultiTexSubImage3DEXT);
    }
    
    public static void glMultiTexSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage3DEXT = capabilities.glMultiTexSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n10, n11, n7, n8, n9));
        nglMultiTexSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.getAddress(floatBuffer), glMultiTexSubImage3DEXT);
    }
    
    public static void glMultiTexSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage3DEXT = capabilities.glMultiTexSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n10, n11, n7, n8, n9));
        nglMultiTexSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.getAddress(intBuffer), glMultiTexSubImage3DEXT);
    }
    
    public static void glMultiTexSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage3DEXT = capabilities.glMultiTexSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n10, n11, n7, n8, n9));
        nglMultiTexSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.getAddress(shortBuffer), glMultiTexSubImage3DEXT);
    }
    
    static native void nglMultiTexSubImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final long p11, final long p12);
    
    public static void glMultiTexSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final long n12) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexSubImage3DEXT = capabilities.glMultiTexSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glMultiTexSubImage3DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglMultiTexSubImage3DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, glMultiTexSubImage3DEXT);
    }
    
    static native void nglMultiTexSubImage3DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final long p11, final long p12);
    
    public static void glCopyMultiTexSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        final long glCopyMultiTexSubImage3DEXT = GLContext.getCapabilities().glCopyMultiTexSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glCopyMultiTexSubImage3DEXT);
        nglCopyMultiTexSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, glCopyMultiTexSubImage3DEXT);
    }
    
    static native void nglCopyMultiTexSubImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10);
    
    public static void glEnableClientStateIndexedEXT(final int n, final int n2) {
        final long glEnableClientStateIndexedEXT = GLContext.getCapabilities().glEnableClientStateIndexedEXT;
        BufferChecks.checkFunctionAddress(glEnableClientStateIndexedEXT);
        nglEnableClientStateIndexedEXT(n, n2, glEnableClientStateIndexedEXT);
    }
    
    static native void nglEnableClientStateIndexedEXT(final int p0, final int p1, final long p2);
    
    public static void glDisableClientStateIndexedEXT(final int n, final int n2) {
        final long glDisableClientStateIndexedEXT = GLContext.getCapabilities().glDisableClientStateIndexedEXT;
        BufferChecks.checkFunctionAddress(glDisableClientStateIndexedEXT);
        nglDisableClientStateIndexedEXT(n, n2, glDisableClientStateIndexedEXT);
    }
    
    static native void nglDisableClientStateIndexedEXT(final int p0, final int p1, final long p2);
    
    public static void glEnableClientStateiEXT(final int n, final int n2) {
        final long glEnableClientStateiEXT = GLContext.getCapabilities().glEnableClientStateiEXT;
        BufferChecks.checkFunctionAddress(glEnableClientStateiEXT);
        nglEnableClientStateiEXT(n, n2, glEnableClientStateiEXT);
    }
    
    static native void nglEnableClientStateiEXT(final int p0, final int p1, final long p2);
    
    public static void glDisableClientStateiEXT(final int n, final int n2) {
        final long glDisableClientStateiEXT = GLContext.getCapabilities().glDisableClientStateiEXT;
        BufferChecks.checkFunctionAddress(glDisableClientStateiEXT);
        nglDisableClientStateiEXT(n, n2, glDisableClientStateiEXT);
    }
    
    static native void nglDisableClientStateiEXT(final int p0, final int p1, final long p2);
    
    public static void glGetFloatIndexedEXT(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetFloatIndexedvEXT = GLContext.getCapabilities().glGetFloatIndexedvEXT;
        BufferChecks.checkFunctionAddress(glGetFloatIndexedvEXT);
        BufferChecks.checkBuffer(floatBuffer, 16);
        nglGetFloatIndexedvEXT(n, n2, MemoryUtil.getAddress(floatBuffer), glGetFloatIndexedvEXT);
    }
    
    static native void nglGetFloatIndexedvEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static float glGetFloatIndexedEXT(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetFloatIndexedvEXT = capabilities.glGetFloatIndexedvEXT;
        BufferChecks.checkFunctionAddress(glGetFloatIndexedvEXT);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetFloatIndexedvEXT(n, n2, MemoryUtil.getAddress(bufferFloat), glGetFloatIndexedvEXT);
        return bufferFloat.get(0);
    }
    
    public static void glGetDoubleIndexedEXT(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glGetDoubleIndexedvEXT = GLContext.getCapabilities().glGetDoubleIndexedvEXT;
        BufferChecks.checkFunctionAddress(glGetDoubleIndexedvEXT);
        BufferChecks.checkBuffer(doubleBuffer, 16);
        nglGetDoubleIndexedvEXT(n, n2, MemoryUtil.getAddress(doubleBuffer), glGetDoubleIndexedvEXT);
    }
    
    static native void nglGetDoubleIndexedvEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static double glGetDoubleIndexedEXT(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetDoubleIndexedvEXT = capabilities.glGetDoubleIndexedvEXT;
        BufferChecks.checkFunctionAddress(glGetDoubleIndexedvEXT);
        final DoubleBuffer bufferDouble = APIUtil.getBufferDouble(capabilities);
        nglGetDoubleIndexedvEXT(n, n2, MemoryUtil.getAddress(bufferDouble), glGetDoubleIndexedvEXT);
        return bufferDouble.get(0);
    }
    
    public static ByteBuffer glGetPointerIndexedEXT(final int n, final int n2, final long n3) {
        final long glGetPointerIndexedvEXT = GLContext.getCapabilities().glGetPointerIndexedvEXT;
        BufferChecks.checkFunctionAddress(glGetPointerIndexedvEXT);
        final ByteBuffer nglGetPointerIndexedvEXT = nglGetPointerIndexedvEXT(n, n2, n3, glGetPointerIndexedvEXT);
        return (LWJGLUtil.CHECKS && nglGetPointerIndexedvEXT == null) ? null : nglGetPointerIndexedvEXT.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglGetPointerIndexedvEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetFloatEXT(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetFloati_vEXT = GLContext.getCapabilities().glGetFloati_vEXT;
        BufferChecks.checkFunctionAddress(glGetFloati_vEXT);
        BufferChecks.checkBuffer(floatBuffer, 16);
        nglGetFloati_vEXT(n, n2, MemoryUtil.getAddress(floatBuffer), glGetFloati_vEXT);
    }
    
    static native void nglGetFloati_vEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static float glGetFloatEXT(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetFloati_vEXT = capabilities.glGetFloati_vEXT;
        BufferChecks.checkFunctionAddress(glGetFloati_vEXT);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetFloati_vEXT(n, n2, MemoryUtil.getAddress(bufferFloat), glGetFloati_vEXT);
        return bufferFloat.get(0);
    }
    
    public static void glGetDoubleEXT(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glGetDoublei_vEXT = GLContext.getCapabilities().glGetDoublei_vEXT;
        BufferChecks.checkFunctionAddress(glGetDoublei_vEXT);
        BufferChecks.checkBuffer(doubleBuffer, 16);
        nglGetDoublei_vEXT(n, n2, MemoryUtil.getAddress(doubleBuffer), glGetDoublei_vEXT);
    }
    
    static native void nglGetDoublei_vEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static double glGetDoubleEXT(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetDoublei_vEXT = capabilities.glGetDoublei_vEXT;
        BufferChecks.checkFunctionAddress(glGetDoublei_vEXT);
        final DoubleBuffer bufferDouble = APIUtil.getBufferDouble(capabilities);
        nglGetDoublei_vEXT(n, n2, MemoryUtil.getAddress(bufferDouble), glGetDoublei_vEXT);
        return bufferDouble.get(0);
    }
    
    public static ByteBuffer glGetPointerEXT(final int n, final int n2, final long n3) {
        final long glGetPointeri_vEXT = GLContext.getCapabilities().glGetPointeri_vEXT;
        BufferChecks.checkFunctionAddress(glGetPointeri_vEXT);
        final ByteBuffer nglGetPointeri_vEXT = nglGetPointeri_vEXT(n, n2, n3, glGetPointeri_vEXT);
        return (LWJGLUtil.CHECKS && nglGetPointeri_vEXT == null) ? null : nglGetPointeri_vEXT.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglGetPointeri_vEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glEnableIndexedEXT(final int n, final int n2) {
        EXTDrawBuffers2.glEnableIndexedEXT(n, n2);
    }
    
    public static void glDisableIndexedEXT(final int n, final int n2) {
        EXTDrawBuffers2.glDisableIndexedEXT(n, n2);
    }
    
    public static boolean glIsEnabledIndexedEXT(final int n, final int n2) {
        return EXTDrawBuffers2.glIsEnabledIndexedEXT(n, n2);
    }
    
    public static void glGetIntegerIndexedEXT(final int n, final int n2, final IntBuffer intBuffer) {
        EXTDrawBuffers2.glGetIntegerIndexedEXT(n, n2, intBuffer);
    }
    
    public static int glGetIntegerIndexedEXT(final int n, final int n2) {
        return EXTDrawBuffers2.glGetIntegerIndexedEXT(n, n2);
    }
    
    public static void glGetBooleanIndexedEXT(final int n, final int n2, final ByteBuffer byteBuffer) {
        EXTDrawBuffers2.glGetBooleanIndexedEXT(n, n2, byteBuffer);
    }
    
    public static boolean glGetBooleanIndexedEXT(final int n, final int n2) {
        return EXTDrawBuffers2.glGetBooleanIndexedEXT(n, n2);
    }
    
    public static void glNamedProgramStringEXT(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final long glNamedProgramStringEXT = GLContext.getCapabilities().glNamedProgramStringEXT;
        BufferChecks.checkFunctionAddress(glNamedProgramStringEXT);
        BufferChecks.checkDirect(byteBuffer);
        nglNamedProgramStringEXT(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glNamedProgramStringEXT);
    }
    
    static native void nglNamedProgramStringEXT(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glNamedProgramStringEXT(final int n, final int n2, final int n3, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glNamedProgramStringEXT = capabilities.glNamedProgramStringEXT;
        BufferChecks.checkFunctionAddress(glNamedProgramStringEXT);
        nglNamedProgramStringEXT(n, n2, n3, charSequence.length(), APIUtil.getBuffer(capabilities, charSequence), glNamedProgramStringEXT);
    }
    
    public static void glNamedProgramLocalParameter4dEXT(final int n, final int n2, final int n3, final double n4, final double n5, final double n6, final double n7) {
        final long glNamedProgramLocalParameter4dEXT = GLContext.getCapabilities().glNamedProgramLocalParameter4dEXT;
        BufferChecks.checkFunctionAddress(glNamedProgramLocalParameter4dEXT);
        nglNamedProgramLocalParameter4dEXT(n, n2, n3, n4, n5, n6, n7, glNamedProgramLocalParameter4dEXT);
    }
    
    static native void nglNamedProgramLocalParameter4dEXT(final int p0, final int p1, final int p2, final double p3, final double p4, final double p5, final double p6, final long p7);
    
    public static void glNamedProgramLocalParameter4EXT(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final long glNamedProgramLocalParameter4dvEXT = GLContext.getCapabilities().glNamedProgramLocalParameter4dvEXT;
        BufferChecks.checkFunctionAddress(glNamedProgramLocalParameter4dvEXT);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglNamedProgramLocalParameter4dvEXT(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), glNamedProgramLocalParameter4dvEXT);
    }
    
    static native void nglNamedProgramLocalParameter4dvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glNamedProgramLocalParameter4fEXT(final int n, final int n2, final int n3, final float n4, final float n5, final float n6, final float n7) {
        final long glNamedProgramLocalParameter4fEXT = GLContext.getCapabilities().glNamedProgramLocalParameter4fEXT;
        BufferChecks.checkFunctionAddress(glNamedProgramLocalParameter4fEXT);
        nglNamedProgramLocalParameter4fEXT(n, n2, n3, n4, n5, n6, n7, glNamedProgramLocalParameter4fEXT);
    }
    
    static native void nglNamedProgramLocalParameter4fEXT(final int p0, final int p1, final int p2, final float p3, final float p4, final float p5, final float p6, final long p7);
    
    public static void glNamedProgramLocalParameter4EXT(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glNamedProgramLocalParameter4fvEXT = GLContext.getCapabilities().glNamedProgramLocalParameter4fvEXT;
        BufferChecks.checkFunctionAddress(glNamedProgramLocalParameter4fvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglNamedProgramLocalParameter4fvEXT(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glNamedProgramLocalParameter4fvEXT);
    }
    
    static native void nglNamedProgramLocalParameter4fvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetNamedProgramLocalParameterEXT(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final long glGetNamedProgramLocalParameterdvEXT = GLContext.getCapabilities().glGetNamedProgramLocalParameterdvEXT;
        BufferChecks.checkFunctionAddress(glGetNamedProgramLocalParameterdvEXT);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglGetNamedProgramLocalParameterdvEXT(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), glGetNamedProgramLocalParameterdvEXT);
    }
    
    static native void nglGetNamedProgramLocalParameterdvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetNamedProgramLocalParameterEXT(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glGetNamedProgramLocalParameterfvEXT = GLContext.getCapabilities().glGetNamedProgramLocalParameterfvEXT;
        BufferChecks.checkFunctionAddress(glGetNamedProgramLocalParameterfvEXT);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetNamedProgramLocalParameterfvEXT(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glGetNamedProgramLocalParameterfvEXT);
    }
    
    static native void nglGetNamedProgramLocalParameterfvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetNamedProgramEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetNamedProgramivEXT = GLContext.getCapabilities().glGetNamedProgramivEXT;
        BufferChecks.checkFunctionAddress(glGetNamedProgramivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetNamedProgramivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetNamedProgramivEXT);
    }
    
    static native void nglGetNamedProgramivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetNamedProgramEXT(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetNamedProgramivEXT = capabilities.glGetNamedProgramivEXT;
        BufferChecks.checkFunctionAddress(glGetNamedProgramivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetNamedProgramivEXT(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetNamedProgramivEXT);
        return bufferInt.get(0);
    }
    
    public static void glGetNamedProgramStringEXT(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final long glGetNamedProgramStringEXT = GLContext.getCapabilities().glGetNamedProgramStringEXT;
        BufferChecks.checkFunctionAddress(glGetNamedProgramStringEXT);
        BufferChecks.checkDirect(byteBuffer);
        nglGetNamedProgramStringEXT(n, n2, n3, MemoryUtil.getAddress(byteBuffer), glGetNamedProgramStringEXT);
    }
    
    static native void nglGetNamedProgramStringEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static String glGetNamedProgramStringEXT(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetNamedProgramStringEXT = capabilities.glGetNamedProgramStringEXT;
        BufferChecks.checkFunctionAddress(glGetNamedProgramStringEXT);
        final int glGetNamedProgramEXT = glGetNamedProgramEXT(n, n2, 34343);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, glGetNamedProgramEXT);
        nglGetNamedProgramStringEXT(n, n2, n3, MemoryUtil.getAddress(bufferByte), glGetNamedProgramStringEXT);
        bufferByte.limit(glGetNamedProgramEXT);
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static void glCompressedTextureImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureImage3DEXT = capabilities.glCompressedTextureImage3DEXT;
        BufferChecks.checkFunctionAddress(glCompressedTextureImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTextureImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTextureImage3DEXT);
    }
    
    static native void nglCompressedTextureImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glCompressedTextureImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final long n10) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureImage3DEXT = capabilities.glCompressedTextureImage3DEXT;
        BufferChecks.checkFunctionAddress(glCompressedTextureImage3DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTextureImage3DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, glCompressedTextureImage3DEXT);
    }
    
    static native void nglCompressedTextureImage3DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glCompressedTextureImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureImage2DEXT = capabilities.glCompressedTextureImage2DEXT;
        BufferChecks.checkFunctionAddress(glCompressedTextureImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTextureImage2DEXT(n, n2, n3, n4, n5, n6, n7, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTextureImage2DEXT);
    }
    
    static native void nglCompressedTextureImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glCompressedTextureImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureImage2DEXT = capabilities.glCompressedTextureImage2DEXT;
        BufferChecks.checkFunctionAddress(glCompressedTextureImage2DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTextureImage2DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, glCompressedTextureImage2DEXT);
    }
    
    static native void nglCompressedTextureImage2DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glCompressedTextureImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureImage1DEXT = capabilities.glCompressedTextureImage1DEXT;
        BufferChecks.checkFunctionAddress(glCompressedTextureImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTextureImage1DEXT(n, n2, n3, n4, n5, n6, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTextureImage1DEXT);
    }
    
    static native void nglCompressedTextureImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glCompressedTextureImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureImage1DEXT = capabilities.glCompressedTextureImage1DEXT;
        BufferChecks.checkFunctionAddress(glCompressedTextureImage1DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTextureImage1DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, glCompressedTextureImage1DEXT);
    }
    
    static native void nglCompressedTextureImage1DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glCompressedTextureSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureSubImage3DEXT = capabilities.glCompressedTextureSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glCompressedTextureSubImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTextureSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTextureSubImage3DEXT);
    }
    
    static native void nglCompressedTextureSubImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final long p11, final long p12);
    
    public static void glCompressedTextureSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final long n12) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureSubImage3DEXT = capabilities.glCompressedTextureSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glCompressedTextureSubImage3DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTextureSubImage3DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, glCompressedTextureSubImage3DEXT);
    }
    
    static native void nglCompressedTextureSubImage3DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final long p11, final long p12);
    
    public static void glCompressedTextureSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureSubImage2DEXT = capabilities.glCompressedTextureSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glCompressedTextureSubImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTextureSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTextureSubImage2DEXT);
    }
    
    static native void nglCompressedTextureSubImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glCompressedTextureSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final long n10) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureSubImage2DEXT = capabilities.glCompressedTextureSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glCompressedTextureSubImage2DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTextureSubImage2DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, glCompressedTextureSubImage2DEXT);
    }
    
    static native void nglCompressedTextureSubImage2DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glCompressedTextureSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureSubImage1DEXT = capabilities.glCompressedTextureSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glCompressedTextureSubImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTextureSubImage1DEXT(n, n2, n3, n4, n5, n6, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTextureSubImage1DEXT);
    }
    
    static native void nglCompressedTextureSubImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glCompressedTextureSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTextureSubImage1DEXT = capabilities.glCompressedTextureSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glCompressedTextureSubImage1DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTextureSubImage1DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, glCompressedTextureSubImage1DEXT);
    }
    
    static native void nglCompressedTextureSubImage1DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glGetCompressedTextureImageEXT(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTextureImageEXT = capabilities.glGetCompressedTextureImageEXT;
        BufferChecks.checkFunctionAddress(glGetCompressedTextureImageEXT);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglGetCompressedTextureImageEXT(n, n2, n3, MemoryUtil.getAddress(byteBuffer), glGetCompressedTextureImageEXT);
    }
    
    public static void glGetCompressedTextureImageEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTextureImageEXT = capabilities.glGetCompressedTextureImageEXT;
        BufferChecks.checkFunctionAddress(glGetCompressedTextureImageEXT);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglGetCompressedTextureImageEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetCompressedTextureImageEXT);
    }
    
    public static void glGetCompressedTextureImageEXT(final int n, final int n2, final int n3, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTextureImageEXT = capabilities.glGetCompressedTextureImageEXT;
        BufferChecks.checkFunctionAddress(glGetCompressedTextureImageEXT);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglGetCompressedTextureImageEXT(n, n2, n3, MemoryUtil.getAddress(shortBuffer), glGetCompressedTextureImageEXT);
    }
    
    static native void nglGetCompressedTextureImageEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetCompressedTextureImageEXT(final int n, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTextureImageEXT = capabilities.glGetCompressedTextureImageEXT;
        BufferChecks.checkFunctionAddress(glGetCompressedTextureImageEXT);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetCompressedTextureImageEXTBO(n, n2, n3, n4, glGetCompressedTextureImageEXT);
    }
    
    static native void nglGetCompressedTextureImageEXTBO(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glCompressedMultiTexImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedMultiTexImage3DEXT = capabilities.glCompressedMultiTexImage3DEXT;
        BufferChecks.checkFunctionAddress(glCompressedMultiTexImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedMultiTexImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedMultiTexImage3DEXT);
    }
    
    static native void nglCompressedMultiTexImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glCompressedMultiTexImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final long n10) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedMultiTexImage3DEXT = capabilities.glCompressedMultiTexImage3DEXT;
        BufferChecks.checkFunctionAddress(glCompressedMultiTexImage3DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedMultiTexImage3DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, glCompressedMultiTexImage3DEXT);
    }
    
    static native void nglCompressedMultiTexImage3DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glCompressedMultiTexImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedMultiTexImage2DEXT = capabilities.glCompressedMultiTexImage2DEXT;
        BufferChecks.checkFunctionAddress(glCompressedMultiTexImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedMultiTexImage2DEXT(n, n2, n3, n4, n5, n6, n7, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedMultiTexImage2DEXT);
    }
    
    static native void nglCompressedMultiTexImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glCompressedMultiTexImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedMultiTexImage2DEXT = capabilities.glCompressedMultiTexImage2DEXT;
        BufferChecks.checkFunctionAddress(glCompressedMultiTexImage2DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedMultiTexImage2DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, glCompressedMultiTexImage2DEXT);
    }
    
    static native void nglCompressedMultiTexImage2DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glCompressedMultiTexImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedMultiTexImage1DEXT = capabilities.glCompressedMultiTexImage1DEXT;
        BufferChecks.checkFunctionAddress(glCompressedMultiTexImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedMultiTexImage1DEXT(n, n2, n3, n4, n5, n6, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedMultiTexImage1DEXT);
    }
    
    static native void nglCompressedMultiTexImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glCompressedMultiTexImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedMultiTexImage1DEXT = capabilities.glCompressedMultiTexImage1DEXT;
        BufferChecks.checkFunctionAddress(glCompressedMultiTexImage1DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedMultiTexImage1DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, glCompressedMultiTexImage1DEXT);
    }
    
    static native void nglCompressedMultiTexImage1DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glCompressedMultiTexSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedMultiTexSubImage3DEXT = capabilities.glCompressedMultiTexSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glCompressedMultiTexSubImage3DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedMultiTexSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedMultiTexSubImage3DEXT);
    }
    
    static native void nglCompressedMultiTexSubImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final long p11, final long p12);
    
    public static void glCompressedMultiTexSubImage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final long n12) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedMultiTexSubImage3DEXT = capabilities.glCompressedMultiTexSubImage3DEXT;
        BufferChecks.checkFunctionAddress(glCompressedMultiTexSubImage3DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedMultiTexSubImage3DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, glCompressedMultiTexSubImage3DEXT);
    }
    
    static native void nglCompressedMultiTexSubImage3DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final long p11, final long p12);
    
    public static void glCompressedMultiTexSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedMultiTexSubImage2DEXT = capabilities.glCompressedMultiTexSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glCompressedMultiTexSubImage2DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedMultiTexSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedMultiTexSubImage2DEXT);
    }
    
    static native void nglCompressedMultiTexSubImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glCompressedMultiTexSubImage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final long n10) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedMultiTexSubImage2DEXT = capabilities.glCompressedMultiTexSubImage2DEXT;
        BufferChecks.checkFunctionAddress(glCompressedMultiTexSubImage2DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedMultiTexSubImage2DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, glCompressedMultiTexSubImage2DEXT);
    }
    
    static native void nglCompressedMultiTexSubImage2DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glCompressedMultiTexSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedMultiTexSubImage1DEXT = capabilities.glCompressedMultiTexSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glCompressedMultiTexSubImage1DEXT);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedMultiTexSubImage1DEXT(n, n2, n3, n4, n5, n6, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedMultiTexSubImage1DEXT);
    }
    
    static native void nglCompressedMultiTexSubImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glCompressedMultiTexSubImage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedMultiTexSubImage1DEXT = capabilities.glCompressedMultiTexSubImage1DEXT;
        BufferChecks.checkFunctionAddress(glCompressedMultiTexSubImage1DEXT);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedMultiTexSubImage1DEXTBO(n, n2, n3, n4, n5, n6, n7, n8, glCompressedMultiTexSubImage1DEXT);
    }
    
    static native void nglCompressedMultiTexSubImage1DEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glGetCompressedMultiTexImageEXT(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedMultiTexImageEXT = capabilities.glGetCompressedMultiTexImageEXT;
        BufferChecks.checkFunctionAddress(glGetCompressedMultiTexImageEXT);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglGetCompressedMultiTexImageEXT(n, n2, n3, MemoryUtil.getAddress(byteBuffer), glGetCompressedMultiTexImageEXT);
    }
    
    public static void glGetCompressedMultiTexImageEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedMultiTexImageEXT = capabilities.glGetCompressedMultiTexImageEXT;
        BufferChecks.checkFunctionAddress(glGetCompressedMultiTexImageEXT);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglGetCompressedMultiTexImageEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetCompressedMultiTexImageEXT);
    }
    
    public static void glGetCompressedMultiTexImageEXT(final int n, final int n2, final int n3, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedMultiTexImageEXT = capabilities.glGetCompressedMultiTexImageEXT;
        BufferChecks.checkFunctionAddress(glGetCompressedMultiTexImageEXT);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglGetCompressedMultiTexImageEXT(n, n2, n3, MemoryUtil.getAddress(shortBuffer), glGetCompressedMultiTexImageEXT);
    }
    
    static native void nglGetCompressedMultiTexImageEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetCompressedMultiTexImageEXT(final int n, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedMultiTexImageEXT = capabilities.glGetCompressedMultiTexImageEXT;
        BufferChecks.checkFunctionAddress(glGetCompressedMultiTexImageEXT);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetCompressedMultiTexImageEXTBO(n, n2, n3, n4, glGetCompressedMultiTexImageEXT);
    }
    
    static native void nglGetCompressedMultiTexImageEXTBO(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glMatrixLoadTransposeEXT(final int n, final FloatBuffer floatBuffer) {
        final long glMatrixLoadTransposefEXT = GLContext.getCapabilities().glMatrixLoadTransposefEXT;
        BufferChecks.checkFunctionAddress(glMatrixLoadTransposefEXT);
        BufferChecks.checkBuffer(floatBuffer, 16);
        nglMatrixLoadTransposefEXT(n, MemoryUtil.getAddress(floatBuffer), glMatrixLoadTransposefEXT);
    }
    
    static native void nglMatrixLoadTransposefEXT(final int p0, final long p1, final long p2);
    
    public static void glMatrixLoadTransposeEXT(final int n, final DoubleBuffer doubleBuffer) {
        final long glMatrixLoadTransposedEXT = GLContext.getCapabilities().glMatrixLoadTransposedEXT;
        BufferChecks.checkFunctionAddress(glMatrixLoadTransposedEXT);
        BufferChecks.checkBuffer(doubleBuffer, 16);
        nglMatrixLoadTransposedEXT(n, MemoryUtil.getAddress(doubleBuffer), glMatrixLoadTransposedEXT);
    }
    
    static native void nglMatrixLoadTransposedEXT(final int p0, final long p1, final long p2);
    
    public static void glMatrixMultTransposeEXT(final int n, final FloatBuffer floatBuffer) {
        final long glMatrixMultTransposefEXT = GLContext.getCapabilities().glMatrixMultTransposefEXT;
        BufferChecks.checkFunctionAddress(glMatrixMultTransposefEXT);
        BufferChecks.checkBuffer(floatBuffer, 16);
        nglMatrixMultTransposefEXT(n, MemoryUtil.getAddress(floatBuffer), glMatrixMultTransposefEXT);
    }
    
    static native void nglMatrixMultTransposefEXT(final int p0, final long p1, final long p2);
    
    public static void glMatrixMultTransposeEXT(final int n, final DoubleBuffer doubleBuffer) {
        final long glMatrixMultTransposedEXT = GLContext.getCapabilities().glMatrixMultTransposedEXT;
        BufferChecks.checkFunctionAddress(glMatrixMultTransposedEXT);
        BufferChecks.checkBuffer(doubleBuffer, 16);
        nglMatrixMultTransposedEXT(n, MemoryUtil.getAddress(doubleBuffer), glMatrixMultTransposedEXT);
    }
    
    static native void nglMatrixMultTransposedEXT(final int p0, final long p1, final long p2);
    
    public static void glNamedBufferDataEXT(final int n, final long n2, final int n3) {
        final long glNamedBufferDataEXT = GLContext.getCapabilities().glNamedBufferDataEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferDataEXT);
        nglNamedBufferDataEXT(n, n2, 0L, n3, glNamedBufferDataEXT);
    }
    
    public static void glNamedBufferDataEXT(final int n, final ByteBuffer byteBuffer, final int n2) {
        final long glNamedBufferDataEXT = GLContext.getCapabilities().glNamedBufferDataEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferDataEXT);
        BufferChecks.checkDirect(byteBuffer);
        nglNamedBufferDataEXT(n, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), n2, glNamedBufferDataEXT);
    }
    
    public static void glNamedBufferDataEXT(final int n, final DoubleBuffer doubleBuffer, final int n2) {
        final long glNamedBufferDataEXT = GLContext.getCapabilities().glNamedBufferDataEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferDataEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglNamedBufferDataEXT(n, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), n2, glNamedBufferDataEXT);
    }
    
    public static void glNamedBufferDataEXT(final int n, final FloatBuffer floatBuffer, final int n2) {
        final long glNamedBufferDataEXT = GLContext.getCapabilities().glNamedBufferDataEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferDataEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglNamedBufferDataEXT(n, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), n2, glNamedBufferDataEXT);
    }
    
    public static void glNamedBufferDataEXT(final int n, final IntBuffer intBuffer, final int n2) {
        final long glNamedBufferDataEXT = GLContext.getCapabilities().glNamedBufferDataEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferDataEXT);
        BufferChecks.checkDirect(intBuffer);
        nglNamedBufferDataEXT(n, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), n2, glNamedBufferDataEXT);
    }
    
    public static void glNamedBufferDataEXT(final int n, final ShortBuffer shortBuffer, final int n2) {
        final long glNamedBufferDataEXT = GLContext.getCapabilities().glNamedBufferDataEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferDataEXT);
        BufferChecks.checkDirect(shortBuffer);
        nglNamedBufferDataEXT(n, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), n2, glNamedBufferDataEXT);
    }
    
    static native void nglNamedBufferDataEXT(final int p0, final long p1, final long p2, final int p3, final long p4);
    
    public static void glNamedBufferSubDataEXT(final int n, final long n2, final ByteBuffer byteBuffer) {
        final long glNamedBufferSubDataEXT = GLContext.getCapabilities().glNamedBufferSubDataEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferSubDataEXT);
        BufferChecks.checkDirect(byteBuffer);
        nglNamedBufferSubDataEXT(n, n2, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glNamedBufferSubDataEXT);
    }
    
    public static void glNamedBufferSubDataEXT(final int n, final long n2, final DoubleBuffer doubleBuffer) {
        final long glNamedBufferSubDataEXT = GLContext.getCapabilities().glNamedBufferSubDataEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferSubDataEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglNamedBufferSubDataEXT(n, n2, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glNamedBufferSubDataEXT);
    }
    
    public static void glNamedBufferSubDataEXT(final int n, final long n2, final FloatBuffer floatBuffer) {
        final long glNamedBufferSubDataEXT = GLContext.getCapabilities().glNamedBufferSubDataEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferSubDataEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglNamedBufferSubDataEXT(n, n2, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glNamedBufferSubDataEXT);
    }
    
    public static void glNamedBufferSubDataEXT(final int n, final long n2, final IntBuffer intBuffer) {
        final long glNamedBufferSubDataEXT = GLContext.getCapabilities().glNamedBufferSubDataEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferSubDataEXT);
        BufferChecks.checkDirect(intBuffer);
        nglNamedBufferSubDataEXT(n, n2, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glNamedBufferSubDataEXT);
    }
    
    public static void glNamedBufferSubDataEXT(final int n, final long n2, final ShortBuffer shortBuffer) {
        final long glNamedBufferSubDataEXT = GLContext.getCapabilities().glNamedBufferSubDataEXT;
        BufferChecks.checkFunctionAddress(glNamedBufferSubDataEXT);
        BufferChecks.checkDirect(shortBuffer);
        nglNamedBufferSubDataEXT(n, n2, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glNamedBufferSubDataEXT);
    }
    
    static native void nglNamedBufferSubDataEXT(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    public static ByteBuffer glMapNamedBufferEXT(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glMapNamedBufferEXT = GLContext.getCapabilities().glMapNamedBufferEXT;
        BufferChecks.checkFunctionAddress(glMapNamedBufferEXT);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        final ByteBuffer nglMapNamedBufferEXT = nglMapNamedBufferEXT(n, n2, glGetNamedBufferParameterEXT(n, 34660), byteBuffer, glMapNamedBufferEXT);
        return (LWJGLUtil.CHECKS && nglMapNamedBufferEXT == null) ? null : nglMapNamedBufferEXT.order(ByteOrder.nativeOrder());
    }
    
    public static ByteBuffer glMapNamedBufferEXT(final int n, final int n2, final long n3, final ByteBuffer byteBuffer) {
        final long glMapNamedBufferEXT = GLContext.getCapabilities().glMapNamedBufferEXT;
        BufferChecks.checkFunctionAddress(glMapNamedBufferEXT);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        final ByteBuffer nglMapNamedBufferEXT = nglMapNamedBufferEXT(n, n2, n3, byteBuffer, glMapNamedBufferEXT);
        return (LWJGLUtil.CHECKS && nglMapNamedBufferEXT == null) ? null : nglMapNamedBufferEXT.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglMapNamedBufferEXT(final int p0, final int p1, final long p2, final ByteBuffer p3, final long p4);
    
    public static boolean glUnmapNamedBufferEXT(final int n) {
        final long glUnmapNamedBufferEXT = GLContext.getCapabilities().glUnmapNamedBufferEXT;
        BufferChecks.checkFunctionAddress(glUnmapNamedBufferEXT);
        return nglUnmapNamedBufferEXT(n, glUnmapNamedBufferEXT);
    }
    
    static native boolean nglUnmapNamedBufferEXT(final int p0, final long p1);
    
    public static void glGetNamedBufferParameterEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetNamedBufferParameterivEXT = GLContext.getCapabilities().glGetNamedBufferParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetNamedBufferParameterivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetNamedBufferParameterivEXT(n, n2, MemoryUtil.getAddress(intBuffer), glGetNamedBufferParameterivEXT);
    }
    
    static native void nglGetNamedBufferParameterivEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetNamedBufferParameterEXT(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetNamedBufferParameterivEXT = capabilities.glGetNamedBufferParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetNamedBufferParameterivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetNamedBufferParameterivEXT(n, n2, MemoryUtil.getAddress(bufferInt), glGetNamedBufferParameterivEXT);
        return bufferInt.get(0);
    }
    
    public static ByteBuffer glGetNamedBufferPointerEXT(final int n, final int n2) {
        final long glGetNamedBufferPointervEXT = GLContext.getCapabilities().glGetNamedBufferPointervEXT;
        BufferChecks.checkFunctionAddress(glGetNamedBufferPointervEXT);
        final ByteBuffer nglGetNamedBufferPointervEXT = nglGetNamedBufferPointervEXT(n, n2, glGetNamedBufferParameterEXT(n, 34660), glGetNamedBufferPointervEXT);
        return (LWJGLUtil.CHECKS && nglGetNamedBufferPointervEXT == null) ? null : nglGetNamedBufferPointervEXT.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglGetNamedBufferPointervEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetNamedBufferSubDataEXT(final int n, final long n2, final ByteBuffer byteBuffer) {
        final long glGetNamedBufferSubDataEXT = GLContext.getCapabilities().glGetNamedBufferSubDataEXT;
        BufferChecks.checkFunctionAddress(glGetNamedBufferSubDataEXT);
        BufferChecks.checkDirect(byteBuffer);
        nglGetNamedBufferSubDataEXT(n, n2, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glGetNamedBufferSubDataEXT);
    }
    
    public static void glGetNamedBufferSubDataEXT(final int n, final long n2, final DoubleBuffer doubleBuffer) {
        final long glGetNamedBufferSubDataEXT = GLContext.getCapabilities().glGetNamedBufferSubDataEXT;
        BufferChecks.checkFunctionAddress(glGetNamedBufferSubDataEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetNamedBufferSubDataEXT(n, n2, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glGetNamedBufferSubDataEXT);
    }
    
    public static void glGetNamedBufferSubDataEXT(final int n, final long n2, final FloatBuffer floatBuffer) {
        final long glGetNamedBufferSubDataEXT = GLContext.getCapabilities().glGetNamedBufferSubDataEXT;
        BufferChecks.checkFunctionAddress(glGetNamedBufferSubDataEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglGetNamedBufferSubDataEXT(n, n2, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glGetNamedBufferSubDataEXT);
    }
    
    public static void glGetNamedBufferSubDataEXT(final int n, final long n2, final IntBuffer intBuffer) {
        final long glGetNamedBufferSubDataEXT = GLContext.getCapabilities().glGetNamedBufferSubDataEXT;
        BufferChecks.checkFunctionAddress(glGetNamedBufferSubDataEXT);
        BufferChecks.checkDirect(intBuffer);
        nglGetNamedBufferSubDataEXT(n, n2, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glGetNamedBufferSubDataEXT);
    }
    
    public static void glGetNamedBufferSubDataEXT(final int n, final long n2, final ShortBuffer shortBuffer) {
        final long glGetNamedBufferSubDataEXT = GLContext.getCapabilities().glGetNamedBufferSubDataEXT;
        BufferChecks.checkFunctionAddress(glGetNamedBufferSubDataEXT);
        BufferChecks.checkDirect(shortBuffer);
        nglGetNamedBufferSubDataEXT(n, n2, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glGetNamedBufferSubDataEXT);
    }
    
    static native void nglGetNamedBufferSubDataEXT(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    public static void glProgramUniform1fEXT(final int n, final int n2, final float n3) {
        final long glProgramUniform1fEXT = GLContext.getCapabilities().glProgramUniform1fEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform1fEXT);
        nglProgramUniform1fEXT(n, n2, n3, glProgramUniform1fEXT);
    }
    
    static native void nglProgramUniform1fEXT(final int p0, final int p1, final float p2, final long p3);
    
    public static void glProgramUniform2fEXT(final int n, final int n2, final float n3, final float n4) {
        final long glProgramUniform2fEXT = GLContext.getCapabilities().glProgramUniform2fEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform2fEXT);
        nglProgramUniform2fEXT(n, n2, n3, n4, glProgramUniform2fEXT);
    }
    
    static native void nglProgramUniform2fEXT(final int p0, final int p1, final float p2, final float p3, final long p4);
    
    public static void glProgramUniform3fEXT(final int n, final int n2, final float n3, final float n4, final float n5) {
        final long glProgramUniform3fEXT = GLContext.getCapabilities().glProgramUniform3fEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform3fEXT);
        nglProgramUniform3fEXT(n, n2, n3, n4, n5, glProgramUniform3fEXT);
    }
    
    static native void nglProgramUniform3fEXT(final int p0, final int p1, final float p2, final float p3, final float p4, final long p5);
    
    public static void glProgramUniform4fEXT(final int n, final int n2, final float n3, final float n4, final float n5, final float n6) {
        final long glProgramUniform4fEXT = GLContext.getCapabilities().glProgramUniform4fEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform4fEXT);
        nglProgramUniform4fEXT(n, n2, n3, n4, n5, n6, glProgramUniform4fEXT);
    }
    
    static native void nglProgramUniform4fEXT(final int p0, final int p1, final float p2, final float p3, final float p4, final float p5, final long p6);
    
    public static void glProgramUniform1iEXT(final int n, final int n2, final int n3) {
        final long glProgramUniform1iEXT = GLContext.getCapabilities().glProgramUniform1iEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform1iEXT);
        nglProgramUniform1iEXT(n, n2, n3, glProgramUniform1iEXT);
    }
    
    static native void nglProgramUniform1iEXT(final int p0, final int p1, final int p2, final long p3);
    
    public static void glProgramUniform2iEXT(final int n, final int n2, final int n3, final int n4) {
        final long glProgramUniform2iEXT = GLContext.getCapabilities().glProgramUniform2iEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform2iEXT);
        nglProgramUniform2iEXT(n, n2, n3, n4, glProgramUniform2iEXT);
    }
    
    static native void nglProgramUniform2iEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glProgramUniform3iEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glProgramUniform3iEXT = GLContext.getCapabilities().glProgramUniform3iEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform3iEXT);
        nglProgramUniform3iEXT(n, n2, n3, n4, n5, glProgramUniform3iEXT);
    }
    
    static native void nglProgramUniform3iEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glProgramUniform4iEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glProgramUniform4iEXT = GLContext.getCapabilities().glProgramUniform4iEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform4iEXT);
        nglProgramUniform4iEXT(n, n2, n3, n4, n5, n6, glProgramUniform4iEXT);
    }
    
    static native void nglProgramUniform4iEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glProgramUniform1EXT(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glProgramUniform1fvEXT = GLContext.getCapabilities().glProgramUniform1fvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform1fvEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniform1fvEXT(n, n2, floatBuffer.remaining(), MemoryUtil.getAddress(floatBuffer), glProgramUniform1fvEXT);
    }
    
    static native void nglProgramUniform1fvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform2EXT(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glProgramUniform2fvEXT = GLContext.getCapabilities().glProgramUniform2fvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform2fvEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniform2fvEXT(n, n2, floatBuffer.remaining() >> 1, MemoryUtil.getAddress(floatBuffer), glProgramUniform2fvEXT);
    }
    
    static native void nglProgramUniform2fvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform3EXT(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glProgramUniform3fvEXT = GLContext.getCapabilities().glProgramUniform3fvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform3fvEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniform3fvEXT(n, n2, floatBuffer.remaining() / 3, MemoryUtil.getAddress(floatBuffer), glProgramUniform3fvEXT);
    }
    
    static native void nglProgramUniform3fvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform4EXT(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glProgramUniform4fvEXT = GLContext.getCapabilities().glProgramUniform4fvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform4fvEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniform4fvEXT(n, n2, floatBuffer.remaining() >> 2, MemoryUtil.getAddress(floatBuffer), glProgramUniform4fvEXT);
    }
    
    static native void nglProgramUniform4fvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform1EXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramUniform1ivEXT = GLContext.getCapabilities().glProgramUniform1ivEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform1ivEXT);
        BufferChecks.checkDirect(intBuffer);
        nglProgramUniform1ivEXT(n, n2, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glProgramUniform1ivEXT);
    }
    
    static native void nglProgramUniform1ivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform2EXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramUniform2ivEXT = GLContext.getCapabilities().glProgramUniform2ivEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform2ivEXT);
        BufferChecks.checkDirect(intBuffer);
        nglProgramUniform2ivEXT(n, n2, intBuffer.remaining() >> 1, MemoryUtil.getAddress(intBuffer), glProgramUniform2ivEXT);
    }
    
    static native void nglProgramUniform2ivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform3EXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramUniform3ivEXT = GLContext.getCapabilities().glProgramUniform3ivEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform3ivEXT);
        BufferChecks.checkDirect(intBuffer);
        nglProgramUniform3ivEXT(n, n2, intBuffer.remaining() / 3, MemoryUtil.getAddress(intBuffer), glProgramUniform3ivEXT);
    }
    
    static native void nglProgramUniform3ivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform4EXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramUniform4ivEXT = GLContext.getCapabilities().glProgramUniform4ivEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform4ivEXT);
        BufferChecks.checkDirect(intBuffer);
        nglProgramUniform4ivEXT(n, n2, intBuffer.remaining() >> 2, MemoryUtil.getAddress(intBuffer), glProgramUniform4ivEXT);
    }
    
    static native void nglProgramUniform4ivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniformMatrix2EXT(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix2fvEXT = GLContext.getCapabilities().glProgramUniformMatrix2fvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix2fvEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix2fvEXT(n, n2, floatBuffer.remaining() >> 2, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix2fvEXT);
    }
    
    static native void nglProgramUniformMatrix2fvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix3EXT(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix3fvEXT = GLContext.getCapabilities().glProgramUniformMatrix3fvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix3fvEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix3fvEXT(n, n2, floatBuffer.remaining() / 9, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix3fvEXT);
    }
    
    static native void nglProgramUniformMatrix3fvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix4EXT(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix4fvEXT = GLContext.getCapabilities().glProgramUniformMatrix4fvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix4fvEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix4fvEXT(n, n2, floatBuffer.remaining() >> 4, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix4fvEXT);
    }
    
    static native void nglProgramUniformMatrix4fvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix2x3EXT(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix2x3fvEXT = GLContext.getCapabilities().glProgramUniformMatrix2x3fvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix2x3fvEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix2x3fvEXT(n, n2, floatBuffer.remaining() / 6, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix2x3fvEXT);
    }
    
    static native void nglProgramUniformMatrix2x3fvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix3x2EXT(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix3x2fvEXT = GLContext.getCapabilities().glProgramUniformMatrix3x2fvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix3x2fvEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix3x2fvEXT(n, n2, floatBuffer.remaining() / 6, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix3x2fvEXT);
    }
    
    static native void nglProgramUniformMatrix3x2fvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix2x4EXT(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix2x4fvEXT = GLContext.getCapabilities().glProgramUniformMatrix2x4fvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix2x4fvEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix2x4fvEXT(n, n2, floatBuffer.remaining() >> 3, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix2x4fvEXT);
    }
    
    static native void nglProgramUniformMatrix2x4fvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix4x2EXT(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix4x2fvEXT = GLContext.getCapabilities().glProgramUniformMatrix4x2fvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix4x2fvEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix4x2fvEXT(n, n2, floatBuffer.remaining() >> 3, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix4x2fvEXT);
    }
    
    static native void nglProgramUniformMatrix4x2fvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix3x4EXT(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix3x4fvEXT = GLContext.getCapabilities().glProgramUniformMatrix3x4fvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix3x4fvEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix3x4fvEXT(n, n2, floatBuffer.remaining() / 12, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix3x4fvEXT);
    }
    
    static native void nglProgramUniformMatrix3x4fvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix4x3EXT(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix4x3fvEXT = GLContext.getCapabilities().glProgramUniformMatrix4x3fvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix4x3fvEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix4x3fvEXT(n, n2, floatBuffer.remaining() / 12, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix4x3fvEXT);
    }
    
    static native void nglProgramUniformMatrix4x3fvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glTextureBufferEXT(final int n, final int n2, final int n3, final int n4) {
        final long glTextureBufferEXT = GLContext.getCapabilities().glTextureBufferEXT;
        BufferChecks.checkFunctionAddress(glTextureBufferEXT);
        nglTextureBufferEXT(n, n2, n3, n4, glTextureBufferEXT);
    }
    
    static native void nglTextureBufferEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glMultiTexBufferEXT(final int n, final int n2, final int n3, final int n4) {
        final long glMultiTexBufferEXT = GLContext.getCapabilities().glMultiTexBufferEXT;
        BufferChecks.checkFunctionAddress(glMultiTexBufferEXT);
        nglMultiTexBufferEXT(n, n2, n3, n4, glMultiTexBufferEXT);
    }
    
    static native void nglMultiTexBufferEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glTextureParameterIEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glTextureParameterIivEXT = GLContext.getCapabilities().glTextureParameterIivEXT;
        BufferChecks.checkFunctionAddress(glTextureParameterIivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglTextureParameterIivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glTextureParameterIivEXT);
    }
    
    static native void nglTextureParameterIivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glTextureParameterIEXT(final int n, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureParameterIivEXT = capabilities.glTextureParameterIivEXT;
        BufferChecks.checkFunctionAddress(glTextureParameterIivEXT);
        nglTextureParameterIivEXT(n, n2, n3, APIUtil.getInt(capabilities, n4), glTextureParameterIivEXT);
    }
    
    public static void glTextureParameterIuEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glTextureParameterIuivEXT = GLContext.getCapabilities().glTextureParameterIuivEXT;
        BufferChecks.checkFunctionAddress(glTextureParameterIuivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglTextureParameterIuivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glTextureParameterIuivEXT);
    }
    
    static native void nglTextureParameterIuivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glTextureParameterIuEXT(final int n, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTextureParameterIuivEXT = capabilities.glTextureParameterIuivEXT;
        BufferChecks.checkFunctionAddress(glTextureParameterIuivEXT);
        nglTextureParameterIuivEXT(n, n2, n3, APIUtil.getInt(capabilities, n4), glTextureParameterIuivEXT);
    }
    
    public static void glGetTextureParameterIEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetTextureParameterIivEXT = GLContext.getCapabilities().glGetTextureParameterIivEXT;
        BufferChecks.checkFunctionAddress(glGetTextureParameterIivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetTextureParameterIivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetTextureParameterIivEXT);
    }
    
    static native void nglGetTextureParameterIivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetTextureParameterIiEXT(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureParameterIivEXT = capabilities.glGetTextureParameterIivEXT;
        BufferChecks.checkFunctionAddress(glGetTextureParameterIivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTextureParameterIivEXT(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetTextureParameterIivEXT);
        return bufferInt.get(0);
    }
    
    public static void glGetTextureParameterIuEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetTextureParameterIuivEXT = GLContext.getCapabilities().glGetTextureParameterIuivEXT;
        BufferChecks.checkFunctionAddress(glGetTextureParameterIuivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetTextureParameterIuivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetTextureParameterIuivEXT);
    }
    
    static native void nglGetTextureParameterIuivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetTextureParameterIuiEXT(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTextureParameterIuivEXT = capabilities.glGetTextureParameterIuivEXT;
        BufferChecks.checkFunctionAddress(glGetTextureParameterIuivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTextureParameterIuivEXT(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetTextureParameterIuivEXT);
        return bufferInt.get(0);
    }
    
    public static void glMultiTexParameterIEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glMultiTexParameterIivEXT = GLContext.getCapabilities().glMultiTexParameterIivEXT;
        BufferChecks.checkFunctionAddress(glMultiTexParameterIivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglMultiTexParameterIivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glMultiTexParameterIivEXT);
    }
    
    static native void nglMultiTexParameterIivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glMultiTexParameterIEXT(final int n, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexParameterIivEXT = capabilities.glMultiTexParameterIivEXT;
        BufferChecks.checkFunctionAddress(glMultiTexParameterIivEXT);
        nglMultiTexParameterIivEXT(n, n2, n3, APIUtil.getInt(capabilities, n4), glMultiTexParameterIivEXT);
    }
    
    public static void glMultiTexParameterIuEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glMultiTexParameterIuivEXT = GLContext.getCapabilities().glMultiTexParameterIuivEXT;
        BufferChecks.checkFunctionAddress(glMultiTexParameterIuivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglMultiTexParameterIuivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glMultiTexParameterIuivEXT);
    }
    
    static native void nglMultiTexParameterIuivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glMultiTexParameterIuEXT(final int n, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiTexParameterIuivEXT = capabilities.glMultiTexParameterIuivEXT;
        BufferChecks.checkFunctionAddress(glMultiTexParameterIuivEXT);
        nglMultiTexParameterIuivEXT(n, n2, n3, APIUtil.getInt(capabilities, n4), glMultiTexParameterIuivEXT);
    }
    
    public static void glGetMultiTexParameterIEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetMultiTexParameterIivEXT = GLContext.getCapabilities().glGetMultiTexParameterIivEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexParameterIivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetMultiTexParameterIivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetMultiTexParameterIivEXT);
    }
    
    static native void nglGetMultiTexParameterIivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetMultiTexParameterIiEXT(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMultiTexParameterIivEXT = capabilities.glGetMultiTexParameterIivEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexParameterIivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetMultiTexParameterIivEXT(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetMultiTexParameterIivEXT);
        return bufferInt.get(0);
    }
    
    public static void glGetMultiTexParameterIuEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetMultiTexParameterIuivEXT = GLContext.getCapabilities().glGetMultiTexParameterIuivEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexParameterIuivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetMultiTexParameterIuivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetMultiTexParameterIuivEXT);
    }
    
    static native void nglGetMultiTexParameterIuivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetMultiTexParameterIuiEXT(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMultiTexParameterIuivEXT = capabilities.glGetMultiTexParameterIuivEXT;
        BufferChecks.checkFunctionAddress(glGetMultiTexParameterIuivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetMultiTexParameterIuivEXT(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetMultiTexParameterIuivEXT);
        return bufferInt.get(0);
    }
    
    public static void glProgramUniform1uiEXT(final int n, final int n2, final int n3) {
        final long glProgramUniform1uiEXT = GLContext.getCapabilities().glProgramUniform1uiEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform1uiEXT);
        nglProgramUniform1uiEXT(n, n2, n3, glProgramUniform1uiEXT);
    }
    
    static native void nglProgramUniform1uiEXT(final int p0, final int p1, final int p2, final long p3);
    
    public static void glProgramUniform2uiEXT(final int n, final int n2, final int n3, final int n4) {
        final long glProgramUniform2uiEXT = GLContext.getCapabilities().glProgramUniform2uiEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform2uiEXT);
        nglProgramUniform2uiEXT(n, n2, n3, n4, glProgramUniform2uiEXT);
    }
    
    static native void nglProgramUniform2uiEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glProgramUniform3uiEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glProgramUniform3uiEXT = GLContext.getCapabilities().glProgramUniform3uiEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform3uiEXT);
        nglProgramUniform3uiEXT(n, n2, n3, n4, n5, glProgramUniform3uiEXT);
    }
    
    static native void nglProgramUniform3uiEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glProgramUniform4uiEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glProgramUniform4uiEXT = GLContext.getCapabilities().glProgramUniform4uiEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform4uiEXT);
        nglProgramUniform4uiEXT(n, n2, n3, n4, n5, n6, glProgramUniform4uiEXT);
    }
    
    static native void nglProgramUniform4uiEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glProgramUniform1uEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramUniform1uivEXT = GLContext.getCapabilities().glProgramUniform1uivEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform1uivEXT);
        BufferChecks.checkDirect(intBuffer);
        nglProgramUniform1uivEXT(n, n2, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glProgramUniform1uivEXT);
    }
    
    static native void nglProgramUniform1uivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform2uEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramUniform2uivEXT = GLContext.getCapabilities().glProgramUniform2uivEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform2uivEXT);
        BufferChecks.checkDirect(intBuffer);
        nglProgramUniform2uivEXT(n, n2, intBuffer.remaining() >> 1, MemoryUtil.getAddress(intBuffer), glProgramUniform2uivEXT);
    }
    
    static native void nglProgramUniform2uivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform3uEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramUniform3uivEXT = GLContext.getCapabilities().glProgramUniform3uivEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform3uivEXT);
        BufferChecks.checkDirect(intBuffer);
        nglProgramUniform3uivEXT(n, n2, intBuffer.remaining() / 3, MemoryUtil.getAddress(intBuffer), glProgramUniform3uivEXT);
    }
    
    static native void nglProgramUniform3uivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform4uEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramUniform4uivEXT = GLContext.getCapabilities().glProgramUniform4uivEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform4uivEXT);
        BufferChecks.checkDirect(intBuffer);
        nglProgramUniform4uivEXT(n, n2, intBuffer.remaining() >> 2, MemoryUtil.getAddress(intBuffer), glProgramUniform4uivEXT);
    }
    
    static native void nglProgramUniform4uivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glNamedProgramLocalParameters4EXT(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glNamedProgramLocalParameters4fvEXT = GLContext.getCapabilities().glNamedProgramLocalParameters4fvEXT;
        BufferChecks.checkFunctionAddress(glNamedProgramLocalParameters4fvEXT);
        BufferChecks.checkDirect(floatBuffer);
        nglNamedProgramLocalParameters4fvEXT(n, n2, n3, floatBuffer.remaining() >> 2, MemoryUtil.getAddress(floatBuffer), glNamedProgramLocalParameters4fvEXT);
    }
    
    static native void nglNamedProgramLocalParameters4fvEXT(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glNamedProgramLocalParameterI4iEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        final long glNamedProgramLocalParameterI4iEXT = GLContext.getCapabilities().glNamedProgramLocalParameterI4iEXT;
        BufferChecks.checkFunctionAddress(glNamedProgramLocalParameterI4iEXT);
        nglNamedProgramLocalParameterI4iEXT(n, n2, n3, n4, n5, n6, n7, glNamedProgramLocalParameterI4iEXT);
    }
    
    static native void nglNamedProgramLocalParameterI4iEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7);
    
    public static void glNamedProgramLocalParameterI4EXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glNamedProgramLocalParameterI4ivEXT = GLContext.getCapabilities().glNamedProgramLocalParameterI4ivEXT;
        BufferChecks.checkFunctionAddress(glNamedProgramLocalParameterI4ivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglNamedProgramLocalParameterI4ivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glNamedProgramLocalParameterI4ivEXT);
    }
    
    static native void nglNamedProgramLocalParameterI4ivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glNamedProgramLocalParametersI4EXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glNamedProgramLocalParametersI4ivEXT = GLContext.getCapabilities().glNamedProgramLocalParametersI4ivEXT;
        BufferChecks.checkFunctionAddress(glNamedProgramLocalParametersI4ivEXT);
        BufferChecks.checkDirect(intBuffer);
        nglNamedProgramLocalParametersI4ivEXT(n, n2, n3, intBuffer.remaining() >> 2, MemoryUtil.getAddress(intBuffer), glNamedProgramLocalParametersI4ivEXT);
    }
    
    static native void nglNamedProgramLocalParametersI4ivEXT(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glNamedProgramLocalParameterI4uiEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        final long glNamedProgramLocalParameterI4uiEXT = GLContext.getCapabilities().glNamedProgramLocalParameterI4uiEXT;
        BufferChecks.checkFunctionAddress(glNamedProgramLocalParameterI4uiEXT);
        nglNamedProgramLocalParameterI4uiEXT(n, n2, n3, n4, n5, n6, n7, glNamedProgramLocalParameterI4uiEXT);
    }
    
    static native void nglNamedProgramLocalParameterI4uiEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7);
    
    public static void glNamedProgramLocalParameterI4uEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glNamedProgramLocalParameterI4uivEXT = GLContext.getCapabilities().glNamedProgramLocalParameterI4uivEXT;
        BufferChecks.checkFunctionAddress(glNamedProgramLocalParameterI4uivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglNamedProgramLocalParameterI4uivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glNamedProgramLocalParameterI4uivEXT);
    }
    
    static native void nglNamedProgramLocalParameterI4uivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glNamedProgramLocalParametersI4uEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glNamedProgramLocalParametersI4uivEXT = GLContext.getCapabilities().glNamedProgramLocalParametersI4uivEXT;
        BufferChecks.checkFunctionAddress(glNamedProgramLocalParametersI4uivEXT);
        BufferChecks.checkDirect(intBuffer);
        nglNamedProgramLocalParametersI4uivEXT(n, n2, n3, intBuffer.remaining() >> 2, MemoryUtil.getAddress(intBuffer), glNamedProgramLocalParametersI4uivEXT);
    }
    
    static native void nglNamedProgramLocalParametersI4uivEXT(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glGetNamedProgramLocalParameterIEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetNamedProgramLocalParameterIivEXT = GLContext.getCapabilities().glGetNamedProgramLocalParameterIivEXT;
        BufferChecks.checkFunctionAddress(glGetNamedProgramLocalParameterIivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetNamedProgramLocalParameterIivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetNamedProgramLocalParameterIivEXT);
    }
    
    static native void nglGetNamedProgramLocalParameterIivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetNamedProgramLocalParameterIuEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetNamedProgramLocalParameterIuivEXT = GLContext.getCapabilities().glGetNamedProgramLocalParameterIuivEXT;
        BufferChecks.checkFunctionAddress(glGetNamedProgramLocalParameterIuivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetNamedProgramLocalParameterIuivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetNamedProgramLocalParameterIuivEXT);
    }
    
    static native void nglGetNamedProgramLocalParameterIuivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glNamedRenderbufferStorageEXT(final int n, final int n2, final int n3, final int n4) {
        final long glNamedRenderbufferStorageEXT = GLContext.getCapabilities().glNamedRenderbufferStorageEXT;
        BufferChecks.checkFunctionAddress(glNamedRenderbufferStorageEXT);
        nglNamedRenderbufferStorageEXT(n, n2, n3, n4, glNamedRenderbufferStorageEXT);
    }
    
    static native void nglNamedRenderbufferStorageEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glGetNamedRenderbufferParameterEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetNamedRenderbufferParameterivEXT = GLContext.getCapabilities().glGetNamedRenderbufferParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetNamedRenderbufferParameterivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetNamedRenderbufferParameterivEXT(n, n2, MemoryUtil.getAddress(intBuffer), glGetNamedRenderbufferParameterivEXT);
    }
    
    static native void nglGetNamedRenderbufferParameterivEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetNamedRenderbufferParameterEXT(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetNamedRenderbufferParameterivEXT = capabilities.glGetNamedRenderbufferParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetNamedRenderbufferParameterivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetNamedRenderbufferParameterivEXT(n, n2, MemoryUtil.getAddress(bufferInt), glGetNamedRenderbufferParameterivEXT);
        return bufferInt.get(0);
    }
    
    public static void glNamedRenderbufferStorageMultisampleEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glNamedRenderbufferStorageMultisampleEXT = GLContext.getCapabilities().glNamedRenderbufferStorageMultisampleEXT;
        BufferChecks.checkFunctionAddress(glNamedRenderbufferStorageMultisampleEXT);
        nglNamedRenderbufferStorageMultisampleEXT(n, n2, n3, n4, n5, glNamedRenderbufferStorageMultisampleEXT);
    }
    
    static native void nglNamedRenderbufferStorageMultisampleEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glNamedRenderbufferStorageMultisampleCoverageEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glNamedRenderbufferStorageMultisampleCoverageEXT = GLContext.getCapabilities().glNamedRenderbufferStorageMultisampleCoverageEXT;
        BufferChecks.checkFunctionAddress(glNamedRenderbufferStorageMultisampleCoverageEXT);
        nglNamedRenderbufferStorageMultisampleCoverageEXT(n, n2, n3, n4, n5, n6, glNamedRenderbufferStorageMultisampleCoverageEXT);
    }
    
    static native void nglNamedRenderbufferStorageMultisampleCoverageEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static int glCheckNamedFramebufferStatusEXT(final int n, final int n2) {
        final long glCheckNamedFramebufferStatusEXT = GLContext.getCapabilities().glCheckNamedFramebufferStatusEXT;
        BufferChecks.checkFunctionAddress(glCheckNamedFramebufferStatusEXT);
        return nglCheckNamedFramebufferStatusEXT(n, n2, glCheckNamedFramebufferStatusEXT);
    }
    
    static native int nglCheckNamedFramebufferStatusEXT(final int p0, final int p1, final long p2);
    
    public static void glNamedFramebufferTexture1DEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glNamedFramebufferTexture1DEXT = GLContext.getCapabilities().glNamedFramebufferTexture1DEXT;
        BufferChecks.checkFunctionAddress(glNamedFramebufferTexture1DEXT);
        nglNamedFramebufferTexture1DEXT(n, n2, n3, n4, n5, glNamedFramebufferTexture1DEXT);
    }
    
    static native void nglNamedFramebufferTexture1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glNamedFramebufferTexture2DEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glNamedFramebufferTexture2DEXT = GLContext.getCapabilities().glNamedFramebufferTexture2DEXT;
        BufferChecks.checkFunctionAddress(glNamedFramebufferTexture2DEXT);
        nglNamedFramebufferTexture2DEXT(n, n2, n3, n4, n5, glNamedFramebufferTexture2DEXT);
    }
    
    static native void nglNamedFramebufferTexture2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glNamedFramebufferTexture3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glNamedFramebufferTexture3DEXT = GLContext.getCapabilities().glNamedFramebufferTexture3DEXT;
        BufferChecks.checkFunctionAddress(glNamedFramebufferTexture3DEXT);
        nglNamedFramebufferTexture3DEXT(n, n2, n3, n4, n5, n6, glNamedFramebufferTexture3DEXT);
    }
    
    static native void nglNamedFramebufferTexture3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glNamedFramebufferRenderbufferEXT(final int n, final int n2, final int n3, final int n4) {
        final long glNamedFramebufferRenderbufferEXT = GLContext.getCapabilities().glNamedFramebufferRenderbufferEXT;
        BufferChecks.checkFunctionAddress(glNamedFramebufferRenderbufferEXT);
        nglNamedFramebufferRenderbufferEXT(n, n2, n3, n4, glNamedFramebufferRenderbufferEXT);
    }
    
    static native void nglNamedFramebufferRenderbufferEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glGetNamedFramebufferAttachmentParameterEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetNamedFramebufferAttachmentParameterivEXT = GLContext.getCapabilities().glGetNamedFramebufferAttachmentParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetNamedFramebufferAttachmentParameterivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetNamedFramebufferAttachmentParameterivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetNamedFramebufferAttachmentParameterivEXT);
    }
    
    static native void nglGetNamedFramebufferAttachmentParameterivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetNamedFramebufferAttachmentParameterEXT(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetNamedFramebufferAttachmentParameterivEXT = capabilities.glGetNamedFramebufferAttachmentParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetNamedFramebufferAttachmentParameterivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetNamedFramebufferAttachmentParameterivEXT(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetNamedFramebufferAttachmentParameterivEXT);
        return bufferInt.get(0);
    }
    
    public static void glGenerateTextureMipmapEXT(final int n, final int n2) {
        final long glGenerateTextureMipmapEXT = GLContext.getCapabilities().glGenerateTextureMipmapEXT;
        BufferChecks.checkFunctionAddress(glGenerateTextureMipmapEXT);
        nglGenerateTextureMipmapEXT(n, n2, glGenerateTextureMipmapEXT);
    }
    
    static native void nglGenerateTextureMipmapEXT(final int p0, final int p1, final long p2);
    
    public static void glGenerateMultiTexMipmapEXT(final int n, final int n2) {
        final long glGenerateMultiTexMipmapEXT = GLContext.getCapabilities().glGenerateMultiTexMipmapEXT;
        BufferChecks.checkFunctionAddress(glGenerateMultiTexMipmapEXT);
        nglGenerateMultiTexMipmapEXT(n, n2, glGenerateMultiTexMipmapEXT);
    }
    
    static native void nglGenerateMultiTexMipmapEXT(final int p0, final int p1, final long p2);
    
    public static void glFramebufferDrawBufferEXT(final int n, final int n2) {
        final long glFramebufferDrawBufferEXT = GLContext.getCapabilities().glFramebufferDrawBufferEXT;
        BufferChecks.checkFunctionAddress(glFramebufferDrawBufferEXT);
        nglFramebufferDrawBufferEXT(n, n2, glFramebufferDrawBufferEXT);
    }
    
    static native void nglFramebufferDrawBufferEXT(final int p0, final int p1, final long p2);
    
    public static void glFramebufferDrawBuffersEXT(final int n, final IntBuffer intBuffer) {
        final long glFramebufferDrawBuffersEXT = GLContext.getCapabilities().glFramebufferDrawBuffersEXT;
        BufferChecks.checkFunctionAddress(glFramebufferDrawBuffersEXT);
        BufferChecks.checkDirect(intBuffer);
        nglFramebufferDrawBuffersEXT(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glFramebufferDrawBuffersEXT);
    }
    
    static native void nglFramebufferDrawBuffersEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glFramebufferReadBufferEXT(final int n, final int n2) {
        final long glFramebufferReadBufferEXT = GLContext.getCapabilities().glFramebufferReadBufferEXT;
        BufferChecks.checkFunctionAddress(glFramebufferReadBufferEXT);
        nglFramebufferReadBufferEXT(n, n2, glFramebufferReadBufferEXT);
    }
    
    static native void nglFramebufferReadBufferEXT(final int p0, final int p1, final long p2);
    
    public static void glGetFramebufferParameterEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetFramebufferParameterivEXT = GLContext.getCapabilities().glGetFramebufferParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetFramebufferParameterivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetFramebufferParameterivEXT(n, n2, MemoryUtil.getAddress(intBuffer), glGetFramebufferParameterivEXT);
    }
    
    static native void nglGetFramebufferParameterivEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetFramebufferParameterEXT(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetFramebufferParameterivEXT = capabilities.glGetFramebufferParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetFramebufferParameterivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetFramebufferParameterivEXT(n, n2, MemoryUtil.getAddress(bufferInt), glGetFramebufferParameterivEXT);
        return bufferInt.get(0);
    }
    
    public static void glNamedCopyBufferSubDataEXT(final int n, final int n2, final long n3, final long n4, final long n5) {
        final long glNamedCopyBufferSubDataEXT = GLContext.getCapabilities().glNamedCopyBufferSubDataEXT;
        BufferChecks.checkFunctionAddress(glNamedCopyBufferSubDataEXT);
        nglNamedCopyBufferSubDataEXT(n, n2, n3, n4, n5, glNamedCopyBufferSubDataEXT);
    }
    
    static native void nglNamedCopyBufferSubDataEXT(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static void glNamedFramebufferTextureEXT(final int n, final int n2, final int n3, final int n4) {
        final long glNamedFramebufferTextureEXT = GLContext.getCapabilities().glNamedFramebufferTextureEXT;
        BufferChecks.checkFunctionAddress(glNamedFramebufferTextureEXT);
        nglNamedFramebufferTextureEXT(n, n2, n3, n4, glNamedFramebufferTextureEXT);
    }
    
    static native void nglNamedFramebufferTextureEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glNamedFramebufferTextureLayerEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glNamedFramebufferTextureLayerEXT = GLContext.getCapabilities().glNamedFramebufferTextureLayerEXT;
        BufferChecks.checkFunctionAddress(glNamedFramebufferTextureLayerEXT);
        nglNamedFramebufferTextureLayerEXT(n, n2, n3, n4, n5, glNamedFramebufferTextureLayerEXT);
    }
    
    static native void nglNamedFramebufferTextureLayerEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glNamedFramebufferTextureFaceEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glNamedFramebufferTextureFaceEXT = GLContext.getCapabilities().glNamedFramebufferTextureFaceEXT;
        BufferChecks.checkFunctionAddress(glNamedFramebufferTextureFaceEXT);
        nglNamedFramebufferTextureFaceEXT(n, n2, n3, n4, n5, glNamedFramebufferTextureFaceEXT);
    }
    
    static native void nglNamedFramebufferTextureFaceEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glTextureRenderbufferEXT(final int n, final int n2, final int n3) {
        final long glTextureRenderbufferEXT = GLContext.getCapabilities().glTextureRenderbufferEXT;
        BufferChecks.checkFunctionAddress(glTextureRenderbufferEXT);
        nglTextureRenderbufferEXT(n, n2, n3, glTextureRenderbufferEXT);
    }
    
    static native void nglTextureRenderbufferEXT(final int p0, final int p1, final int p2, final long p3);
    
    public static void glMultiTexRenderbufferEXT(final int n, final int n2, final int n3) {
        final long glMultiTexRenderbufferEXT = GLContext.getCapabilities().glMultiTexRenderbufferEXT;
        BufferChecks.checkFunctionAddress(glMultiTexRenderbufferEXT);
        nglMultiTexRenderbufferEXT(n, n2, n3, glMultiTexRenderbufferEXT);
    }
    
    static native void nglMultiTexRenderbufferEXT(final int p0, final int p1, final int p2, final long p3);
    
    public static void glVertexArrayVertexOffsetEXT(final int n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        final long glVertexArrayVertexOffsetEXT = GLContext.getCapabilities().glVertexArrayVertexOffsetEXT;
        BufferChecks.checkFunctionAddress(glVertexArrayVertexOffsetEXT);
        nglVertexArrayVertexOffsetEXT(n, n2, n3, n4, n5, n6, glVertexArrayVertexOffsetEXT);
    }
    
    static native void nglVertexArrayVertexOffsetEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glVertexArrayColorOffsetEXT(final int n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        final long glVertexArrayColorOffsetEXT = GLContext.getCapabilities().glVertexArrayColorOffsetEXT;
        BufferChecks.checkFunctionAddress(glVertexArrayColorOffsetEXT);
        nglVertexArrayColorOffsetEXT(n, n2, n3, n4, n5, n6, glVertexArrayColorOffsetEXT);
    }
    
    static native void nglVertexArrayColorOffsetEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glVertexArrayEdgeFlagOffsetEXT(final int n, final int n2, final int n3, final long n4) {
        final long glVertexArrayEdgeFlagOffsetEXT = GLContext.getCapabilities().glVertexArrayEdgeFlagOffsetEXT;
        BufferChecks.checkFunctionAddress(glVertexArrayEdgeFlagOffsetEXT);
        nglVertexArrayEdgeFlagOffsetEXT(n, n2, n3, n4, glVertexArrayEdgeFlagOffsetEXT);
    }
    
    static native void nglVertexArrayEdgeFlagOffsetEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glVertexArrayIndexOffsetEXT(final int n, final int n2, final int n3, final int n4, final long n5) {
        final long glVertexArrayIndexOffsetEXT = GLContext.getCapabilities().glVertexArrayIndexOffsetEXT;
        BufferChecks.checkFunctionAddress(glVertexArrayIndexOffsetEXT);
        nglVertexArrayIndexOffsetEXT(n, n2, n3, n4, n5, glVertexArrayIndexOffsetEXT);
    }
    
    static native void nglVertexArrayIndexOffsetEXT(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glVertexArrayNormalOffsetEXT(final int n, final int n2, final int n3, final int n4, final long n5) {
        final long glVertexArrayNormalOffsetEXT = GLContext.getCapabilities().glVertexArrayNormalOffsetEXT;
        BufferChecks.checkFunctionAddress(glVertexArrayNormalOffsetEXT);
        nglVertexArrayNormalOffsetEXT(n, n2, n3, n4, n5, glVertexArrayNormalOffsetEXT);
    }
    
    static native void nglVertexArrayNormalOffsetEXT(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glVertexArrayTexCoordOffsetEXT(final int n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        final long glVertexArrayTexCoordOffsetEXT = GLContext.getCapabilities().glVertexArrayTexCoordOffsetEXT;
        BufferChecks.checkFunctionAddress(glVertexArrayTexCoordOffsetEXT);
        nglVertexArrayTexCoordOffsetEXT(n, n2, n3, n4, n5, n6, glVertexArrayTexCoordOffsetEXT);
    }
    
    static native void nglVertexArrayTexCoordOffsetEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glVertexArrayMultiTexCoordOffsetEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        final long glVertexArrayMultiTexCoordOffsetEXT = GLContext.getCapabilities().glVertexArrayMultiTexCoordOffsetEXT;
        BufferChecks.checkFunctionAddress(glVertexArrayMultiTexCoordOffsetEXT);
        nglVertexArrayMultiTexCoordOffsetEXT(n, n2, n3, n4, n5, n6, n7, glVertexArrayMultiTexCoordOffsetEXT);
    }
    
    static native void nglVertexArrayMultiTexCoordOffsetEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glVertexArrayFogCoordOffsetEXT(final int n, final int n2, final int n3, final int n4, final long n5) {
        final long glVertexArrayFogCoordOffsetEXT = GLContext.getCapabilities().glVertexArrayFogCoordOffsetEXT;
        BufferChecks.checkFunctionAddress(glVertexArrayFogCoordOffsetEXT);
        nglVertexArrayFogCoordOffsetEXT(n, n2, n3, n4, n5, glVertexArrayFogCoordOffsetEXT);
    }
    
    static native void nglVertexArrayFogCoordOffsetEXT(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glVertexArraySecondaryColorOffsetEXT(final int n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        final long glVertexArraySecondaryColorOffsetEXT = GLContext.getCapabilities().glVertexArraySecondaryColorOffsetEXT;
        BufferChecks.checkFunctionAddress(glVertexArraySecondaryColorOffsetEXT);
        nglVertexArraySecondaryColorOffsetEXT(n, n2, n3, n4, n5, n6, glVertexArraySecondaryColorOffsetEXT);
    }
    
    static native void nglVertexArraySecondaryColorOffsetEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glVertexArrayVertexAttribOffsetEXT(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b, final int n6, final long n7) {
        final long glVertexArrayVertexAttribOffsetEXT = GLContext.getCapabilities().glVertexArrayVertexAttribOffsetEXT;
        BufferChecks.checkFunctionAddress(glVertexArrayVertexAttribOffsetEXT);
        nglVertexArrayVertexAttribOffsetEXT(n, n2, n3, n4, n5, b, n6, n7, glVertexArrayVertexAttribOffsetEXT);
    }
    
    static native void nglVertexArrayVertexAttribOffsetEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final boolean p5, final int p6, final long p7, final long p8);
    
    public static void glVertexArrayVertexAttribIOffsetEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        final long glVertexArrayVertexAttribIOffsetEXT = GLContext.getCapabilities().glVertexArrayVertexAttribIOffsetEXT;
        BufferChecks.checkFunctionAddress(glVertexArrayVertexAttribIOffsetEXT);
        nglVertexArrayVertexAttribIOffsetEXT(n, n2, n3, n4, n5, n6, n7, glVertexArrayVertexAttribIOffsetEXT);
    }
    
    static native void nglVertexArrayVertexAttribIOffsetEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glEnableVertexArrayEXT(final int n, final int n2) {
        final long glEnableVertexArrayEXT = GLContext.getCapabilities().glEnableVertexArrayEXT;
        BufferChecks.checkFunctionAddress(glEnableVertexArrayEXT);
        nglEnableVertexArrayEXT(n, n2, glEnableVertexArrayEXT);
    }
    
    static native void nglEnableVertexArrayEXT(final int p0, final int p1, final long p2);
    
    public static void glDisableVertexArrayEXT(final int n, final int n2) {
        final long glDisableVertexArrayEXT = GLContext.getCapabilities().glDisableVertexArrayEXT;
        BufferChecks.checkFunctionAddress(glDisableVertexArrayEXT);
        nglDisableVertexArrayEXT(n, n2, glDisableVertexArrayEXT);
    }
    
    static native void nglDisableVertexArrayEXT(final int p0, final int p1, final long p2);
    
    public static void glEnableVertexArrayAttribEXT(final int n, final int n2) {
        final long glEnableVertexArrayAttribEXT = GLContext.getCapabilities().glEnableVertexArrayAttribEXT;
        BufferChecks.checkFunctionAddress(glEnableVertexArrayAttribEXT);
        nglEnableVertexArrayAttribEXT(n, n2, glEnableVertexArrayAttribEXT);
    }
    
    static native void nglEnableVertexArrayAttribEXT(final int p0, final int p1, final long p2);
    
    public static void glDisableVertexArrayAttribEXT(final int n, final int n2) {
        final long glDisableVertexArrayAttribEXT = GLContext.getCapabilities().glDisableVertexArrayAttribEXT;
        BufferChecks.checkFunctionAddress(glDisableVertexArrayAttribEXT);
        nglDisableVertexArrayAttribEXT(n, n2, glDisableVertexArrayAttribEXT);
    }
    
    static native void nglDisableVertexArrayAttribEXT(final int p0, final int p1, final long p2);
    
    public static void glGetVertexArrayIntegerEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetVertexArrayIntegervEXT = GLContext.getCapabilities().glGetVertexArrayIntegervEXT;
        BufferChecks.checkFunctionAddress(glGetVertexArrayIntegervEXT);
        BufferChecks.checkBuffer(intBuffer, 16);
        nglGetVertexArrayIntegervEXT(n, n2, MemoryUtil.getAddress(intBuffer), glGetVertexArrayIntegervEXT);
    }
    
    static native void nglGetVertexArrayIntegervEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetVertexArrayIntegerEXT(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetVertexArrayIntegervEXT = capabilities.glGetVertexArrayIntegervEXT;
        BufferChecks.checkFunctionAddress(glGetVertexArrayIntegervEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetVertexArrayIntegervEXT(n, n2, MemoryUtil.getAddress(bufferInt), glGetVertexArrayIntegervEXT);
        return bufferInt.get(0);
    }
    
    public static ByteBuffer glGetVertexArrayPointerEXT(final int n, final int n2, final long n3) {
        final long glGetVertexArrayPointervEXT = GLContext.getCapabilities().glGetVertexArrayPointervEXT;
        BufferChecks.checkFunctionAddress(glGetVertexArrayPointervEXT);
        final ByteBuffer nglGetVertexArrayPointervEXT = nglGetVertexArrayPointervEXT(n, n2, n3, glGetVertexArrayPointervEXT);
        return (LWJGLUtil.CHECKS && nglGetVertexArrayPointervEXT == null) ? null : nglGetVertexArrayPointervEXT.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglGetVertexArrayPointervEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetVertexArrayIntegerEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetVertexArrayIntegeri_vEXT = GLContext.getCapabilities().glGetVertexArrayIntegeri_vEXT;
        BufferChecks.checkFunctionAddress(glGetVertexArrayIntegeri_vEXT);
        BufferChecks.checkBuffer(intBuffer, 16);
        nglGetVertexArrayIntegeri_vEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetVertexArrayIntegeri_vEXT);
    }
    
    static native void nglGetVertexArrayIntegeri_vEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetVertexArrayIntegeriEXT(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetVertexArrayIntegeri_vEXT = capabilities.glGetVertexArrayIntegeri_vEXT;
        BufferChecks.checkFunctionAddress(glGetVertexArrayIntegeri_vEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetVertexArrayIntegeri_vEXT(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetVertexArrayIntegeri_vEXT);
        return bufferInt.get(0);
    }
    
    public static ByteBuffer glGetVertexArrayPointerEXT(final int n, final int n2, final int n3, final long n4) {
        final long glGetVertexArrayPointeri_vEXT = GLContext.getCapabilities().glGetVertexArrayPointeri_vEXT;
        BufferChecks.checkFunctionAddress(glGetVertexArrayPointeri_vEXT);
        final ByteBuffer nglGetVertexArrayPointeri_vEXT = nglGetVertexArrayPointeri_vEXT(n, n2, n3, n4, glGetVertexArrayPointeri_vEXT);
        return (LWJGLUtil.CHECKS && nglGetVertexArrayPointeri_vEXT == null) ? null : nglGetVertexArrayPointeri_vEXT.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglGetVertexArrayPointeri_vEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static ByteBuffer glMapNamedBufferRangeEXT(final int n, final long n2, final long n3, final int n4, final ByteBuffer byteBuffer) {
        final long glMapNamedBufferRangeEXT = GLContext.getCapabilities().glMapNamedBufferRangeEXT;
        BufferChecks.checkFunctionAddress(glMapNamedBufferRangeEXT);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        final ByteBuffer nglMapNamedBufferRangeEXT = nglMapNamedBufferRangeEXT(n, n2, n3, n4, byteBuffer, glMapNamedBufferRangeEXT);
        return (LWJGLUtil.CHECKS && nglMapNamedBufferRangeEXT == null) ? null : nglMapNamedBufferRangeEXT.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglMapNamedBufferRangeEXT(final int p0, final long p1, final long p2, final int p3, final ByteBuffer p4, final long p5);
    
    public static void glFlushMappedNamedBufferRangeEXT(final int n, final long n2, final long n3) {
        final long glFlushMappedNamedBufferRangeEXT = GLContext.getCapabilities().glFlushMappedNamedBufferRangeEXT;
        BufferChecks.checkFunctionAddress(glFlushMappedNamedBufferRangeEXT);
        nglFlushMappedNamedBufferRangeEXT(n, n2, n3, glFlushMappedNamedBufferRangeEXT);
    }
    
    static native void nglFlushMappedNamedBufferRangeEXT(final int p0, final long p1, final long p2, final long p3);
}
