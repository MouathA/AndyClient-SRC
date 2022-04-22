package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class NVBindlessTexture
{
    private NVBindlessTexture() {
    }
    
    public static long glGetTextureHandleNV(final int n) {
        final long glGetTextureHandleNV = GLContext.getCapabilities().glGetTextureHandleNV;
        BufferChecks.checkFunctionAddress(glGetTextureHandleNV);
        return nglGetTextureHandleNV(n, glGetTextureHandleNV);
    }
    
    static native long nglGetTextureHandleNV(final int p0, final long p1);
    
    public static long glGetTextureSamplerHandleNV(final int n, final int n2) {
        final long glGetTextureSamplerHandleNV = GLContext.getCapabilities().glGetTextureSamplerHandleNV;
        BufferChecks.checkFunctionAddress(glGetTextureSamplerHandleNV);
        return nglGetTextureSamplerHandleNV(n, n2, glGetTextureSamplerHandleNV);
    }
    
    static native long nglGetTextureSamplerHandleNV(final int p0, final int p1, final long p2);
    
    public static void glMakeTextureHandleResidentNV(final long n) {
        final long glMakeTextureHandleResidentNV = GLContext.getCapabilities().glMakeTextureHandleResidentNV;
        BufferChecks.checkFunctionAddress(glMakeTextureHandleResidentNV);
        nglMakeTextureHandleResidentNV(n, glMakeTextureHandleResidentNV);
    }
    
    static native void nglMakeTextureHandleResidentNV(final long p0, final long p1);
    
    public static void glMakeTextureHandleNonResidentNV(final long n) {
        final long glMakeTextureHandleNonResidentNV = GLContext.getCapabilities().glMakeTextureHandleNonResidentNV;
        BufferChecks.checkFunctionAddress(glMakeTextureHandleNonResidentNV);
        nglMakeTextureHandleNonResidentNV(n, glMakeTextureHandleNonResidentNV);
    }
    
    static native void nglMakeTextureHandleNonResidentNV(final long p0, final long p1);
    
    public static long glGetImageHandleNV(final int n, final int n2, final boolean b, final int n3, final int n4) {
        final long glGetImageHandleNV = GLContext.getCapabilities().glGetImageHandleNV;
        BufferChecks.checkFunctionAddress(glGetImageHandleNV);
        return nglGetImageHandleNV(n, n2, b, n3, n4, glGetImageHandleNV);
    }
    
    static native long nglGetImageHandleNV(final int p0, final int p1, final boolean p2, final int p3, final int p4, final long p5);
    
    public static void glMakeImageHandleResidentNV(final long n, final int n2) {
        final long glMakeImageHandleResidentNV = GLContext.getCapabilities().glMakeImageHandleResidentNV;
        BufferChecks.checkFunctionAddress(glMakeImageHandleResidentNV);
        nglMakeImageHandleResidentNV(n, n2, glMakeImageHandleResidentNV);
    }
    
    static native void nglMakeImageHandleResidentNV(final long p0, final int p1, final long p2);
    
    public static void glMakeImageHandleNonResidentNV(final long n) {
        final long glMakeImageHandleNonResidentNV = GLContext.getCapabilities().glMakeImageHandleNonResidentNV;
        BufferChecks.checkFunctionAddress(glMakeImageHandleNonResidentNV);
        nglMakeImageHandleNonResidentNV(n, glMakeImageHandleNonResidentNV);
    }
    
    static native void nglMakeImageHandleNonResidentNV(final long p0, final long p1);
    
    public static void glUniformHandleui64NV(final int n, final long n2) {
        final long glUniformHandleui64NV = GLContext.getCapabilities().glUniformHandleui64NV;
        BufferChecks.checkFunctionAddress(glUniformHandleui64NV);
        nglUniformHandleui64NV(n, n2, glUniformHandleui64NV);
    }
    
    static native void nglUniformHandleui64NV(final int p0, final long p1, final long p2);
    
    public static void glUniformHandleuNV(final int n, final LongBuffer longBuffer) {
        final long glUniformHandleui64vNV = GLContext.getCapabilities().glUniformHandleui64vNV;
        BufferChecks.checkFunctionAddress(glUniformHandleui64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglUniformHandleui64vNV(n, longBuffer.remaining(), MemoryUtil.getAddress(longBuffer), glUniformHandleui64vNV);
    }
    
    static native void nglUniformHandleui64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glProgramUniformHandleui64NV(final int n, final int n2, final long n3) {
        final long glProgramUniformHandleui64NV = GLContext.getCapabilities().glProgramUniformHandleui64NV;
        BufferChecks.checkFunctionAddress(glProgramUniformHandleui64NV);
        nglProgramUniformHandleui64NV(n, n2, n3, glProgramUniformHandleui64NV);
    }
    
    static native void nglProgramUniformHandleui64NV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glProgramUniformHandleuNV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glProgramUniformHandleui64vNV = GLContext.getCapabilities().glProgramUniformHandleui64vNV;
        BufferChecks.checkFunctionAddress(glProgramUniformHandleui64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglProgramUniformHandleui64vNV(n, n2, longBuffer.remaining(), MemoryUtil.getAddress(longBuffer), glProgramUniformHandleui64vNV);
    }
    
    static native void nglProgramUniformHandleui64vNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static boolean glIsTextureHandleResidentNV(final long n) {
        final long glIsTextureHandleResidentNV = GLContext.getCapabilities().glIsTextureHandleResidentNV;
        BufferChecks.checkFunctionAddress(glIsTextureHandleResidentNV);
        return nglIsTextureHandleResidentNV(n, glIsTextureHandleResidentNV);
    }
    
    static native boolean nglIsTextureHandleResidentNV(final long p0, final long p1);
    
    public static boolean glIsImageHandleResidentNV(final long n) {
        final long glIsImageHandleResidentNV = GLContext.getCapabilities().glIsImageHandleResidentNV;
        BufferChecks.checkFunctionAddress(glIsImageHandleResidentNV);
        return nglIsImageHandleResidentNV(n, glIsImageHandleResidentNV);
    }
    
    static native boolean nglIsImageHandleResidentNV(final long p0, final long p1);
}
