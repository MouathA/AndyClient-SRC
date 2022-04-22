package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class ARBTextureCompression
{
    public static final int GL_COMPRESSED_ALPHA_ARB = 34025;
    public static final int GL_COMPRESSED_LUMINANCE_ARB = 34026;
    public static final int GL_COMPRESSED_LUMINANCE_ALPHA_ARB = 34027;
    public static final int GL_COMPRESSED_INTENSITY_ARB = 34028;
    public static final int GL_COMPRESSED_RGB_ARB = 34029;
    public static final int GL_COMPRESSED_RGBA_ARB = 34030;
    public static final int GL_TEXTURE_COMPRESSION_HINT_ARB = 34031;
    public static final int GL_TEXTURE_COMPRESSED_IMAGE_SIZE_ARB = 34464;
    public static final int GL_TEXTURE_COMPRESSED_ARB = 34465;
    public static final int GL_NUM_COMPRESSED_TEXTURE_FORMATS_ARB = 34466;
    public static final int GL_COMPRESSED_TEXTURE_FORMATS_ARB = 34467;
    
    private ARBTextureCompression() {
    }
    
    public static void glCompressedTexImage1DARB(final int n, final int n2, final int n3, final int n4, final int n5, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexImage1DARB = capabilities.glCompressedTexImage1DARB;
        BufferChecks.checkFunctionAddress(glCompressedTexImage1DARB);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTexImage1DARB(n, n2, n3, n4, n5, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTexImage1DARB);
    }
    
    static native void nglCompressedTexImage1DARB(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glCompressedTexImage1DARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexImage1DARB = capabilities.glCompressedTexImage1DARB;
        BufferChecks.checkFunctionAddress(glCompressedTexImage1DARB);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTexImage1DARBBO(n, n2, n3, n4, n5, n6, n7, glCompressedTexImage1DARB);
    }
    
    static native void nglCompressedTexImage1DARBBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glCompressedTexImage2DARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexImage2DARB = capabilities.glCompressedTexImage2DARB;
        BufferChecks.checkFunctionAddress(glCompressedTexImage2DARB);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTexImage2DARB(n, n2, n3, n4, n5, n6, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTexImage2DARB);
    }
    
    static native void nglCompressedTexImage2DARB(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glCompressedTexImage2DARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexImage2DARB = capabilities.glCompressedTexImage2DARB;
        BufferChecks.checkFunctionAddress(glCompressedTexImage2DARB);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTexImage2DARBBO(n, n2, n3, n4, n5, n6, n7, n8, glCompressedTexImage2DARB);
    }
    
    static native void nglCompressedTexImage2DARBBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glCompressedTexImage3DARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexImage3DARB = capabilities.glCompressedTexImage3DARB;
        BufferChecks.checkFunctionAddress(glCompressedTexImage3DARB);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTexImage3DARB(n, n2, n3, n4, n5, n6, n7, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTexImage3DARB);
    }
    
    static native void nglCompressedTexImage3DARB(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glCompressedTexImage3DARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexImage3DARB = capabilities.glCompressedTexImage3DARB;
        BufferChecks.checkFunctionAddress(glCompressedTexImage3DARB);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTexImage3DARBBO(n, n2, n3, n4, n5, n6, n7, n8, n9, glCompressedTexImage3DARB);
    }
    
    static native void nglCompressedTexImage3DARBBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glCompressedTexSubImage1DARB(final int n, final int n2, final int n3, final int n4, final int n5, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexSubImage1DARB = capabilities.glCompressedTexSubImage1DARB;
        BufferChecks.checkFunctionAddress(glCompressedTexSubImage1DARB);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTexSubImage1DARB(n, n2, n3, n4, n5, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTexSubImage1DARB);
    }
    
    static native void nglCompressedTexSubImage1DARB(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glCompressedTexSubImage1DARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexSubImage1DARB = capabilities.glCompressedTexSubImage1DARB;
        BufferChecks.checkFunctionAddress(glCompressedTexSubImage1DARB);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTexSubImage1DARBBO(n, n2, n3, n4, n5, n6, n7, glCompressedTexSubImage1DARB);
    }
    
    static native void nglCompressedTexSubImage1DARBBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glCompressedTexSubImage2DARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexSubImage2DARB = capabilities.glCompressedTexSubImage2DARB;
        BufferChecks.checkFunctionAddress(glCompressedTexSubImage2DARB);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTexSubImage2DARB(n, n2, n3, n4, n5, n6, n7, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTexSubImage2DARB);
    }
    
    static native void nglCompressedTexSubImage2DARB(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glCompressedTexSubImage2DARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexSubImage2DARB = capabilities.glCompressedTexSubImage2DARB;
        BufferChecks.checkFunctionAddress(glCompressedTexSubImage2DARB);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTexSubImage2DARBBO(n, n2, n3, n4, n5, n6, n7, n8, n9, glCompressedTexSubImage2DARB);
    }
    
    static native void nglCompressedTexSubImage2DARBBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glCompressedTexSubImage3DARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexSubImage3DARB = capabilities.glCompressedTexSubImage3DARB;
        BufferChecks.checkFunctionAddress(glCompressedTexSubImage3DARB);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglCompressedTexSubImage3DARB(n, n2, n3, n4, n5, n6, n7, n8, n9, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glCompressedTexSubImage3DARB);
    }
    
    static native void nglCompressedTexSubImage3DARB(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10, final long p11);
    
    public static void glCompressedTexSubImage3DARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final long n11) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompressedTexSubImage3DARB = capabilities.glCompressedTexSubImage3DARB;
        BufferChecks.checkFunctionAddress(glCompressedTexSubImage3DARB);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglCompressedTexSubImage3DARBBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, glCompressedTexSubImage3DARB);
    }
    
    static native void nglCompressedTexSubImage3DARBBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10, final long p11);
    
    public static void glGetCompressedTexImageARB(final int n, final int n2, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTexImageARB = capabilities.glGetCompressedTexImageARB;
        BufferChecks.checkFunctionAddress(glGetCompressedTexImageARB);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglGetCompressedTexImageARB(n, n2, MemoryUtil.getAddress(byteBuffer), glGetCompressedTexImageARB);
    }
    
    static native void nglGetCompressedTexImageARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetCompressedTexImageARB(final int n, final int n2, final long n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetCompressedTexImageARB = capabilities.glGetCompressedTexImageARB;
        BufferChecks.checkFunctionAddress(glGetCompressedTexImageARB);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetCompressedTexImageARBBO(n, n2, n3, glGetCompressedTexImageARB);
    }
    
    static native void nglGetCompressedTexImageARBBO(final int p0, final int p1, final long p2, final long p3);
}
