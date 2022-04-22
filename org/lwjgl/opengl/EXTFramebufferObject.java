package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class EXTFramebufferObject
{
    public static final int GL_FRAMEBUFFER_EXT = 36160;
    public static final int GL_RENDERBUFFER_EXT = 36161;
    public static final int GL_STENCIL_INDEX1_EXT = 36166;
    public static final int GL_STENCIL_INDEX4_EXT = 36167;
    public static final int GL_STENCIL_INDEX8_EXT = 36168;
    public static final int GL_STENCIL_INDEX16_EXT = 36169;
    public static final int GL_RENDERBUFFER_WIDTH_EXT = 36162;
    public static final int GL_RENDERBUFFER_HEIGHT_EXT = 36163;
    public static final int GL_RENDERBUFFER_INTERNAL_FORMAT_EXT = 36164;
    public static final int GL_RENDERBUFFER_RED_SIZE_EXT = 36176;
    public static final int GL_RENDERBUFFER_GREEN_SIZE_EXT = 36177;
    public static final int GL_RENDERBUFFER_BLUE_SIZE_EXT = 36178;
    public static final int GL_RENDERBUFFER_ALPHA_SIZE_EXT = 36179;
    public static final int GL_RENDERBUFFER_DEPTH_SIZE_EXT = 36180;
    public static final int GL_RENDERBUFFER_STENCIL_SIZE_EXT = 36181;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE_EXT = 36048;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME_EXT = 36049;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL_EXT = 36050;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE_EXT = 36051;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_3D_ZOFFSET_EXT = 36052;
    public static final int GL_COLOR_ATTACHMENT0_EXT = 36064;
    public static final int GL_COLOR_ATTACHMENT1_EXT = 36065;
    public static final int GL_COLOR_ATTACHMENT2_EXT = 36066;
    public static final int GL_COLOR_ATTACHMENT3_EXT = 36067;
    public static final int GL_COLOR_ATTACHMENT4_EXT = 36068;
    public static final int GL_COLOR_ATTACHMENT5_EXT = 36069;
    public static final int GL_COLOR_ATTACHMENT6_EXT = 36070;
    public static final int GL_COLOR_ATTACHMENT7_EXT = 36071;
    public static final int GL_COLOR_ATTACHMENT8_EXT = 36072;
    public static final int GL_COLOR_ATTACHMENT9_EXT = 36073;
    public static final int GL_COLOR_ATTACHMENT10_EXT = 36074;
    public static final int GL_COLOR_ATTACHMENT11_EXT = 36075;
    public static final int GL_COLOR_ATTACHMENT12_EXT = 36076;
    public static final int GL_COLOR_ATTACHMENT13_EXT = 36077;
    public static final int GL_COLOR_ATTACHMENT14_EXT = 36078;
    public static final int GL_COLOR_ATTACHMENT15_EXT = 36079;
    public static final int GL_DEPTH_ATTACHMENT_EXT = 36096;
    public static final int GL_STENCIL_ATTACHMENT_EXT = 36128;
    public static final int GL_FRAMEBUFFER_COMPLETE_EXT = 36053;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT = 36054;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT = 36055;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT = 36057;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT = 36058;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT = 36059;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT = 36060;
    public static final int GL_FRAMEBUFFER_UNSUPPORTED_EXT = 36061;
    public static final int GL_FRAMEBUFFER_BINDING_EXT = 36006;
    public static final int GL_RENDERBUFFER_BINDING_EXT = 36007;
    public static final int GL_MAX_COLOR_ATTACHMENTS_EXT = 36063;
    public static final int GL_MAX_RENDERBUFFER_SIZE_EXT = 34024;
    public static final int GL_INVALID_FRAMEBUFFER_OPERATION_EXT = 1286;
    
    private EXTFramebufferObject() {
    }
    
    public static boolean glIsRenderbufferEXT(final int n) {
        final long glIsRenderbufferEXT = GLContext.getCapabilities().glIsRenderbufferEXT;
        BufferChecks.checkFunctionAddress(glIsRenderbufferEXT);
        return nglIsRenderbufferEXT(n, glIsRenderbufferEXT);
    }
    
    static native boolean nglIsRenderbufferEXT(final int p0, final long p1);
    
    public static void glBindRenderbufferEXT(final int n, final int n2) {
        final long glBindRenderbufferEXT = GLContext.getCapabilities().glBindRenderbufferEXT;
        BufferChecks.checkFunctionAddress(glBindRenderbufferEXT);
        nglBindRenderbufferEXT(n, n2, glBindRenderbufferEXT);
    }
    
    static native void nglBindRenderbufferEXT(final int p0, final int p1, final long p2);
    
    public static void glDeleteRenderbuffersEXT(final IntBuffer intBuffer) {
        final long glDeleteRenderbuffersEXT = GLContext.getCapabilities().glDeleteRenderbuffersEXT;
        BufferChecks.checkFunctionAddress(glDeleteRenderbuffersEXT);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteRenderbuffersEXT(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteRenderbuffersEXT);
    }
    
    static native void nglDeleteRenderbuffersEXT(final int p0, final long p1, final long p2);
    
    public static void glDeleteRenderbuffersEXT(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteRenderbuffersEXT = capabilities.glDeleteRenderbuffersEXT;
        BufferChecks.checkFunctionAddress(glDeleteRenderbuffersEXT);
        nglDeleteRenderbuffersEXT(1, APIUtil.getInt(capabilities, n), glDeleteRenderbuffersEXT);
    }
    
    public static void glGenRenderbuffersEXT(final IntBuffer intBuffer) {
        final long glGenRenderbuffersEXT = GLContext.getCapabilities().glGenRenderbuffersEXT;
        BufferChecks.checkFunctionAddress(glGenRenderbuffersEXT);
        BufferChecks.checkDirect(intBuffer);
        nglGenRenderbuffersEXT(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenRenderbuffersEXT);
    }
    
    static native void nglGenRenderbuffersEXT(final int p0, final long p1, final long p2);
    
    public static int glGenRenderbuffersEXT() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenRenderbuffersEXT = capabilities.glGenRenderbuffersEXT;
        BufferChecks.checkFunctionAddress(glGenRenderbuffersEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenRenderbuffersEXT(1, MemoryUtil.getAddress(bufferInt), glGenRenderbuffersEXT);
        return bufferInt.get(0);
    }
    
    public static void glRenderbufferStorageEXT(final int n, final int n2, final int n3, final int n4) {
        final long glRenderbufferStorageEXT = GLContext.getCapabilities().glRenderbufferStorageEXT;
        BufferChecks.checkFunctionAddress(glRenderbufferStorageEXT);
        nglRenderbufferStorageEXT(n, n2, n3, n4, glRenderbufferStorageEXT);
    }
    
    static native void nglRenderbufferStorageEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glGetRenderbufferParameterEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetRenderbufferParameterivEXT = GLContext.getCapabilities().glGetRenderbufferParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetRenderbufferParameterivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetRenderbufferParameterivEXT(n, n2, MemoryUtil.getAddress(intBuffer), glGetRenderbufferParameterivEXT);
    }
    
    static native void nglGetRenderbufferParameterivEXT(final int p0, final int p1, final long p2, final long p3);
    
    @Deprecated
    public static int glGetRenderbufferParameterEXT(final int n, final int n2) {
        return glGetRenderbufferParameteriEXT(n, n2);
    }
    
    public static int glGetRenderbufferParameteriEXT(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetRenderbufferParameterivEXT = capabilities.glGetRenderbufferParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetRenderbufferParameterivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetRenderbufferParameterivEXT(n, n2, MemoryUtil.getAddress(bufferInt), glGetRenderbufferParameterivEXT);
        return bufferInt.get(0);
    }
    
    public static boolean glIsFramebufferEXT(final int n) {
        final long glIsFramebufferEXT = GLContext.getCapabilities().glIsFramebufferEXT;
        BufferChecks.checkFunctionAddress(glIsFramebufferEXT);
        return nglIsFramebufferEXT(n, glIsFramebufferEXT);
    }
    
    static native boolean nglIsFramebufferEXT(final int p0, final long p1);
    
    public static void glBindFramebufferEXT(final int n, final int n2) {
        final long glBindFramebufferEXT = GLContext.getCapabilities().glBindFramebufferEXT;
        BufferChecks.checkFunctionAddress(glBindFramebufferEXT);
        nglBindFramebufferEXT(n, n2, glBindFramebufferEXT);
    }
    
    static native void nglBindFramebufferEXT(final int p0, final int p1, final long p2);
    
    public static void glDeleteFramebuffersEXT(final IntBuffer intBuffer) {
        final long glDeleteFramebuffersEXT = GLContext.getCapabilities().glDeleteFramebuffersEXT;
        BufferChecks.checkFunctionAddress(glDeleteFramebuffersEXT);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteFramebuffersEXT(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteFramebuffersEXT);
    }
    
    static native void nglDeleteFramebuffersEXT(final int p0, final long p1, final long p2);
    
    public static void glDeleteFramebuffersEXT(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteFramebuffersEXT = capabilities.glDeleteFramebuffersEXT;
        BufferChecks.checkFunctionAddress(glDeleteFramebuffersEXT);
        nglDeleteFramebuffersEXT(1, APIUtil.getInt(capabilities, n), glDeleteFramebuffersEXT);
    }
    
    public static void glGenFramebuffersEXT(final IntBuffer intBuffer) {
        final long glGenFramebuffersEXT = GLContext.getCapabilities().glGenFramebuffersEXT;
        BufferChecks.checkFunctionAddress(glGenFramebuffersEXT);
        BufferChecks.checkDirect(intBuffer);
        nglGenFramebuffersEXT(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenFramebuffersEXT);
    }
    
    static native void nglGenFramebuffersEXT(final int p0, final long p1, final long p2);
    
    public static int glGenFramebuffersEXT() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenFramebuffersEXT = capabilities.glGenFramebuffersEXT;
        BufferChecks.checkFunctionAddress(glGenFramebuffersEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenFramebuffersEXT(1, MemoryUtil.getAddress(bufferInt), glGenFramebuffersEXT);
        return bufferInt.get(0);
    }
    
    public static int glCheckFramebufferStatusEXT(final int n) {
        final long glCheckFramebufferStatusEXT = GLContext.getCapabilities().glCheckFramebufferStatusEXT;
        BufferChecks.checkFunctionAddress(glCheckFramebufferStatusEXT);
        return nglCheckFramebufferStatusEXT(n, glCheckFramebufferStatusEXT);
    }
    
    static native int nglCheckFramebufferStatusEXT(final int p0, final long p1);
    
    public static void glFramebufferTexture1DEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glFramebufferTexture1DEXT = GLContext.getCapabilities().glFramebufferTexture1DEXT;
        BufferChecks.checkFunctionAddress(glFramebufferTexture1DEXT);
        nglFramebufferTexture1DEXT(n, n2, n3, n4, n5, glFramebufferTexture1DEXT);
    }
    
    static native void nglFramebufferTexture1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glFramebufferTexture2DEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glFramebufferTexture2DEXT = GLContext.getCapabilities().glFramebufferTexture2DEXT;
        BufferChecks.checkFunctionAddress(glFramebufferTexture2DEXT);
        nglFramebufferTexture2DEXT(n, n2, n3, n4, n5, glFramebufferTexture2DEXT);
    }
    
    static native void nglFramebufferTexture2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glFramebufferTexture3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glFramebufferTexture3DEXT = GLContext.getCapabilities().glFramebufferTexture3DEXT;
        BufferChecks.checkFunctionAddress(glFramebufferTexture3DEXT);
        nglFramebufferTexture3DEXT(n, n2, n3, n4, n5, n6, glFramebufferTexture3DEXT);
    }
    
    static native void nglFramebufferTexture3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glFramebufferRenderbufferEXT(final int n, final int n2, final int n3, final int n4) {
        final long glFramebufferRenderbufferEXT = GLContext.getCapabilities().glFramebufferRenderbufferEXT;
        BufferChecks.checkFunctionAddress(glFramebufferRenderbufferEXT);
        nglFramebufferRenderbufferEXT(n, n2, n3, n4, glFramebufferRenderbufferEXT);
    }
    
    static native void nglFramebufferRenderbufferEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glGetFramebufferAttachmentParameterEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetFramebufferAttachmentParameterivEXT = GLContext.getCapabilities().glGetFramebufferAttachmentParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetFramebufferAttachmentParameterivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetFramebufferAttachmentParameterivEXT(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetFramebufferAttachmentParameterivEXT);
    }
    
    static native void nglGetFramebufferAttachmentParameterivEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    @Deprecated
    public static int glGetFramebufferAttachmentParameterEXT(final int n, final int n2, final int n3) {
        return glGetFramebufferAttachmentParameteriEXT(n, n2, n3);
    }
    
    public static int glGetFramebufferAttachmentParameteriEXT(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetFramebufferAttachmentParameterivEXT = capabilities.glGetFramebufferAttachmentParameterivEXT;
        BufferChecks.checkFunctionAddress(glGetFramebufferAttachmentParameterivEXT);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetFramebufferAttachmentParameterivEXT(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetFramebufferAttachmentParameterivEXT);
        return bufferInt.get(0);
    }
    
    public static void glGenerateMipmapEXT(final int n) {
        final long glGenerateMipmapEXT = GLContext.getCapabilities().glGenerateMipmapEXT;
        BufferChecks.checkFunctionAddress(glGenerateMipmapEXT);
        nglGenerateMipmapEXT(n, glGenerateMipmapEXT);
    }
    
    static native void nglGenerateMipmapEXT(final int p0, final long p1);
}
