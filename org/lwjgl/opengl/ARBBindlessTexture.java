package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class ARBBindlessTexture
{
    public static final int GL_UNSIGNED_INT64_ARB = 5135;
    
    private ARBBindlessTexture() {
    }
    
    public static long glGetTextureHandleARB(final int n) {
        final long glGetTextureHandleARB = GLContext.getCapabilities().glGetTextureHandleARB;
        BufferChecks.checkFunctionAddress(glGetTextureHandleARB);
        return nglGetTextureHandleARB(n, glGetTextureHandleARB);
    }
    
    static native long nglGetTextureHandleARB(final int p0, final long p1);
    
    public static long glGetTextureSamplerHandleARB(final int n, final int n2) {
        final long glGetTextureSamplerHandleARB = GLContext.getCapabilities().glGetTextureSamplerHandleARB;
        BufferChecks.checkFunctionAddress(glGetTextureSamplerHandleARB);
        return nglGetTextureSamplerHandleARB(n, n2, glGetTextureSamplerHandleARB);
    }
    
    static native long nglGetTextureSamplerHandleARB(final int p0, final int p1, final long p2);
    
    public static void glMakeTextureHandleResidentARB(final long n) {
        final long glMakeTextureHandleResidentARB = GLContext.getCapabilities().glMakeTextureHandleResidentARB;
        BufferChecks.checkFunctionAddress(glMakeTextureHandleResidentARB);
        nglMakeTextureHandleResidentARB(n, glMakeTextureHandleResidentARB);
    }
    
    static native void nglMakeTextureHandleResidentARB(final long p0, final long p1);
    
    public static void glMakeTextureHandleNonResidentARB(final long n) {
        final long glMakeTextureHandleNonResidentARB = GLContext.getCapabilities().glMakeTextureHandleNonResidentARB;
        BufferChecks.checkFunctionAddress(glMakeTextureHandleNonResidentARB);
        nglMakeTextureHandleNonResidentARB(n, glMakeTextureHandleNonResidentARB);
    }
    
    static native void nglMakeTextureHandleNonResidentARB(final long p0, final long p1);
    
    public static long glGetImageHandleARB(final int n, final int n2, final boolean b, final int n3, final int n4) {
        final long glGetImageHandleARB = GLContext.getCapabilities().glGetImageHandleARB;
        BufferChecks.checkFunctionAddress(glGetImageHandleARB);
        return nglGetImageHandleARB(n, n2, b, n3, n4, glGetImageHandleARB);
    }
    
    static native long nglGetImageHandleARB(final int p0, final int p1, final boolean p2, final int p3, final int p4, final long p5);
    
    public static void glMakeImageHandleResidentARB(final long n, final int n2) {
        final long glMakeImageHandleResidentARB = GLContext.getCapabilities().glMakeImageHandleResidentARB;
        BufferChecks.checkFunctionAddress(glMakeImageHandleResidentARB);
        nglMakeImageHandleResidentARB(n, n2, glMakeImageHandleResidentARB);
    }
    
    static native void nglMakeImageHandleResidentARB(final long p0, final int p1, final long p2);
    
    public static void glMakeImageHandleNonResidentARB(final long n) {
        final long glMakeImageHandleNonResidentARB = GLContext.getCapabilities().glMakeImageHandleNonResidentARB;
        BufferChecks.checkFunctionAddress(glMakeImageHandleNonResidentARB);
        nglMakeImageHandleNonResidentARB(n, glMakeImageHandleNonResidentARB);
    }
    
    static native void nglMakeImageHandleNonResidentARB(final long p0, final long p1);
    
    public static void glUniformHandleui64ARB(final int n, final long n2) {
        final long glUniformHandleui64ARB = GLContext.getCapabilities().glUniformHandleui64ARB;
        BufferChecks.checkFunctionAddress(glUniformHandleui64ARB);
        nglUniformHandleui64ARB(n, n2, glUniformHandleui64ARB);
    }
    
    static native void nglUniformHandleui64ARB(final int p0, final long p1, final long p2);
    
    public static void glUniformHandleuARB(final int n, final LongBuffer longBuffer) {
        final long glUniformHandleui64vARB = GLContext.getCapabilities().glUniformHandleui64vARB;
        BufferChecks.checkFunctionAddress(glUniformHandleui64vARB);
        BufferChecks.checkDirect(longBuffer);
        nglUniformHandleui64vARB(n, longBuffer.remaining(), MemoryUtil.getAddress(longBuffer), glUniformHandleui64vARB);
    }
    
    static native void nglUniformHandleui64vARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glProgramUniformHandleui64ARB(final int n, final int n2, final long n3) {
        final long glProgramUniformHandleui64ARB = GLContext.getCapabilities().glProgramUniformHandleui64ARB;
        BufferChecks.checkFunctionAddress(glProgramUniformHandleui64ARB);
        nglProgramUniformHandleui64ARB(n, n2, n3, glProgramUniformHandleui64ARB);
    }
    
    static native void nglProgramUniformHandleui64ARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glProgramUniformHandleuARB(final int n, final int n2, final LongBuffer longBuffer) {
        final long glProgramUniformHandleui64vARB = GLContext.getCapabilities().glProgramUniformHandleui64vARB;
        BufferChecks.checkFunctionAddress(glProgramUniformHandleui64vARB);
        BufferChecks.checkDirect(longBuffer);
        nglProgramUniformHandleui64vARB(n, n2, longBuffer.remaining(), MemoryUtil.getAddress(longBuffer), glProgramUniformHandleui64vARB);
    }
    
    static native void nglProgramUniformHandleui64vARB(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static boolean glIsTextureHandleResidentARB(final long n) {
        final long glIsTextureHandleResidentARB = GLContext.getCapabilities().glIsTextureHandleResidentARB;
        BufferChecks.checkFunctionAddress(glIsTextureHandleResidentARB);
        return nglIsTextureHandleResidentARB(n, glIsTextureHandleResidentARB);
    }
    
    static native boolean nglIsTextureHandleResidentARB(final long p0, final long p1);
    
    public static boolean glIsImageHandleResidentARB(final long n) {
        final long glIsImageHandleResidentARB = GLContext.getCapabilities().glIsImageHandleResidentARB;
        BufferChecks.checkFunctionAddress(glIsImageHandleResidentARB);
        return nglIsImageHandleResidentARB(n, glIsImageHandleResidentARB);
    }
    
    static native boolean nglIsImageHandleResidentARB(final long p0, final long p1);
    
    public static void glVertexAttribL1ui64ARB(final int n, final long n2) {
        final long glVertexAttribL1ui64ARB = GLContext.getCapabilities().glVertexAttribL1ui64ARB;
        BufferChecks.checkFunctionAddress(glVertexAttribL1ui64ARB);
        nglVertexAttribL1ui64ARB(n, n2, glVertexAttribL1ui64ARB);
    }
    
    static native void nglVertexAttribL1ui64ARB(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribL1uARB(final int n, final LongBuffer longBuffer) {
        final long glVertexAttribL1ui64vARB = GLContext.getCapabilities().glVertexAttribL1ui64vARB;
        BufferChecks.checkFunctionAddress(glVertexAttribL1ui64vARB);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglVertexAttribL1ui64vARB(n, MemoryUtil.getAddress(longBuffer), glVertexAttribL1ui64vARB);
    }
    
    static native void nglVertexAttribL1ui64vARB(final int p0, final long p1, final long p2);
    
    public static void glGetVertexAttribLuARB(final int n, final int n2, final LongBuffer longBuffer) {
        final long glGetVertexAttribLui64vARB = GLContext.getCapabilities().glGetVertexAttribLui64vARB;
        BufferChecks.checkFunctionAddress(glGetVertexAttribLui64vARB);
        BufferChecks.checkBuffer(longBuffer, 4);
        nglGetVertexAttribLui64vARB(n, n2, MemoryUtil.getAddress(longBuffer), glGetVertexAttribLui64vARB);
    }
    
    static native void nglGetVertexAttribLui64vARB(final int p0, final int p1, final long p2, final long p3);
}
