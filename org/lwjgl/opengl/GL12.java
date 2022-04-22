package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class GL12
{
    public static final int GL_TEXTURE_BINDING_3D = 32874;
    public static final int GL_PACK_SKIP_IMAGES = 32875;
    public static final int GL_PACK_IMAGE_HEIGHT = 32876;
    public static final int GL_UNPACK_SKIP_IMAGES = 32877;
    public static final int GL_UNPACK_IMAGE_HEIGHT = 32878;
    public static final int GL_TEXTURE_3D = 32879;
    public static final int GL_PROXY_TEXTURE_3D = 32880;
    public static final int GL_TEXTURE_DEPTH = 32881;
    public static final int GL_TEXTURE_WRAP_R = 32882;
    public static final int GL_MAX_3D_TEXTURE_SIZE = 32883;
    public static final int GL_BGR = 32992;
    public static final int GL_BGRA = 32993;
    public static final int GL_UNSIGNED_BYTE_3_3_2 = 32818;
    public static final int GL_UNSIGNED_BYTE_2_3_3_REV = 33634;
    public static final int GL_UNSIGNED_SHORT_5_6_5 = 33635;
    public static final int GL_UNSIGNED_SHORT_5_6_5_REV = 33636;
    public static final int GL_UNSIGNED_SHORT_4_4_4_4 = 32819;
    public static final int GL_UNSIGNED_SHORT_4_4_4_4_REV = 33637;
    public static final int GL_UNSIGNED_SHORT_5_5_5_1 = 32820;
    public static final int GL_UNSIGNED_SHORT_1_5_5_5_REV = 33638;
    public static final int GL_UNSIGNED_INT_8_8_8_8 = 32821;
    public static final int GL_UNSIGNED_INT_8_8_8_8_REV = 33639;
    public static final int GL_UNSIGNED_INT_10_10_10_2 = 32822;
    public static final int GL_UNSIGNED_INT_2_10_10_10_REV = 33640;
    public static final int GL_RESCALE_NORMAL = 32826;
    public static final int GL_LIGHT_MODEL_COLOR_CONTROL = 33272;
    public static final int GL_SINGLE_COLOR = 33273;
    public static final int GL_SEPARATE_SPECULAR_COLOR = 33274;
    public static final int GL_CLAMP_TO_EDGE = 33071;
    public static final int GL_TEXTURE_MIN_LOD = 33082;
    public static final int GL_TEXTURE_MAX_LOD = 33083;
    public static final int GL_TEXTURE_BASE_LEVEL = 33084;
    public static final int GL_TEXTURE_MAX_LEVEL = 33085;
    public static final int GL_MAX_ELEMENTS_VERTICES = 33000;
    public static final int GL_MAX_ELEMENTS_INDICES = 33001;
    public static final int GL_ALIASED_POINT_SIZE_RANGE = 33901;
    public static final int GL_ALIASED_LINE_WIDTH_RANGE = 33902;
    public static final int GL_SMOOTH_POINT_SIZE_RANGE = 2834;
    public static final int GL_SMOOTH_POINT_SIZE_GRANULARITY = 2835;
    public static final int GL_SMOOTH_LINE_WIDTH_RANGE = 2850;
    public static final int GL_SMOOTH_LINE_WIDTH_GRANULARITY = 2851;
    
    private GL12() {
    }
    
    public static void glDrawRangeElements(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawRangeElements = capabilities.glDrawRangeElements;
        BufferChecks.checkFunctionAddress(glDrawRangeElements);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglDrawRangeElements(n, n2, n3, byteBuffer.remaining(), 5121, MemoryUtil.getAddress(byteBuffer), glDrawRangeElements);
    }
    
    public static void glDrawRangeElements(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawRangeElements = capabilities.glDrawRangeElements;
        BufferChecks.checkFunctionAddress(glDrawRangeElements);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglDrawRangeElements(n, n2, n3, intBuffer.remaining(), 5125, MemoryUtil.getAddress(intBuffer), glDrawRangeElements);
    }
    
    public static void glDrawRangeElements(final int n, final int n2, final int n3, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawRangeElements = capabilities.glDrawRangeElements;
        BufferChecks.checkFunctionAddress(glDrawRangeElements);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglDrawRangeElements(n, n2, n3, shortBuffer.remaining(), 5123, MemoryUtil.getAddress(shortBuffer), glDrawRangeElements);
    }
    
    static native void nglDrawRangeElements(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glDrawRangeElements(final int n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawRangeElements = capabilities.glDrawRangeElements;
        BufferChecks.checkFunctionAddress(glDrawRangeElements);
        GLChecks.ensureElementVBOenabled(capabilities);
        nglDrawRangeElementsBO(n, n2, n3, n4, n5, n6, glDrawRangeElements);
    }
    
    static native void nglDrawRangeElementsBO(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage3D = capabilities.glTexImage3D;
        BufferChecks.checkFunctionAddress(glTexImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (byteBuffer != null) {
            BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateTexImage3DStorage(byteBuffer, n8, n9, n4, n5, n6));
        }
        nglTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddressSafe(byteBuffer), glTexImage3D);
    }
    
    public static void glTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage3D = capabilities.glTexImage3D;
        BufferChecks.checkFunctionAddress(glTexImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (doubleBuffer != null) {
            BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateTexImage3DStorage(doubleBuffer, n8, n9, n4, n5, n6));
        }
        nglTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddressSafe(doubleBuffer), glTexImage3D);
    }
    
    public static void glTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage3D = capabilities.glTexImage3D;
        BufferChecks.checkFunctionAddress(glTexImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateTexImage3DStorage(floatBuffer, n8, n9, n4, n5, n6));
        }
        nglTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddressSafe(floatBuffer), glTexImage3D);
    }
    
    public static void glTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage3D = capabilities.glTexImage3D;
        BufferChecks.checkFunctionAddress(glTexImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, GLChecks.calculateTexImage3DStorage(intBuffer, n8, n9, n4, n5, n6));
        }
        nglTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddressSafe(intBuffer), glTexImage3D);
    }
    
    public static void glTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage3D = capabilities.glTexImage3D;
        BufferChecks.checkFunctionAddress(glTexImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (shortBuffer != null) {
            BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateTexImage3DStorage(shortBuffer, n8, n9, n4, n5, n6));
        }
        nglTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddressSafe(shortBuffer), glTexImage3D);
    }
    
    static native void nglTexImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final long n10) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage3D = capabilities.glTexImage3D;
        BufferChecks.checkFunctionAddress(glTexImage3D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglTexImage3DBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, glTexImage3D);
    }
    
    static native void nglTexImage3DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage3D = capabilities.glTexSubImage3D;
        BufferChecks.checkFunctionAddress(glTexSubImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n9, n10, n6, n7, n8));
        nglTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddress(byteBuffer), glTexSubImage3D);
    }
    
    public static void glTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage3D = capabilities.glTexSubImage3D;
        BufferChecks.checkFunctionAddress(glTexSubImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n9, n10, n6, n7, n8));
        nglTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddress(doubleBuffer), glTexSubImage3D);
    }
    
    public static void glTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage3D = capabilities.glTexSubImage3D;
        BufferChecks.checkFunctionAddress(glTexSubImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n9, n10, n6, n7, n8));
        nglTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddress(floatBuffer), glTexSubImage3D);
    }
    
    public static void glTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage3D = capabilities.glTexSubImage3D;
        BufferChecks.checkFunctionAddress(glTexSubImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n9, n10, n6, n7, n8));
        nglTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddress(intBuffer), glTexSubImage3D);
    }
    
    public static void glTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage3D = capabilities.glTexSubImage3D;
        BufferChecks.checkFunctionAddress(glTexSubImage3D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n9, n10, n6, n7, n8));
        nglTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.getAddress(shortBuffer), glTexSubImage3D);
    }
    
    static native void nglTexSubImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10, final long p11);
    
    public static void glTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final long n11) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage3D = capabilities.glTexSubImage3D;
        BufferChecks.checkFunctionAddress(glTexSubImage3D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglTexSubImage3DBO(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, glTexSubImage3D);
    }
    
    static native void nglTexSubImage3DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10, final long p11);
    
    public static void glCopyTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9) {
        final long glCopyTexSubImage3D = GLContext.getCapabilities().glCopyTexSubImage3D;
        BufferChecks.checkFunctionAddress(glCopyTexSubImage3D);
        nglCopyTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, glCopyTexSubImage3D);
    }
    
    static native void nglCopyTexSubImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9);
}
