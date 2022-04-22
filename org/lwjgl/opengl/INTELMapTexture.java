package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class INTELMapTexture
{
    public static final int GL_TEXTURE_MEMORY_LAYOUT_INTEL = 33791;
    public static final int GL_LAYOUT_DEFAULT_INTEL = 0;
    public static final int GL_LAYOUT_LINEAR_INTEL = 1;
    public static final int GL_LAYOUT_LINEAR_CPU_CACHED_INTEL = 2;
    
    private INTELMapTexture() {
    }
    
    public static ByteBuffer glMapTexture2DINTEL(final int n, final int n2, final long n3, final int n4, final IntBuffer intBuffer, final IntBuffer intBuffer2, final ByteBuffer byteBuffer) {
        final long glMapTexture2DINTEL = GLContext.getCapabilities().glMapTexture2DINTEL;
        BufferChecks.checkFunctionAddress(glMapTexture2DINTEL);
        BufferChecks.checkBuffer(intBuffer, 1);
        BufferChecks.checkBuffer(intBuffer2, 1);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        final ByteBuffer nglMapTexture2DINTEL = nglMapTexture2DINTEL(n, n2, n3, n4, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), byteBuffer, glMapTexture2DINTEL);
        return (LWJGLUtil.CHECKS && nglMapTexture2DINTEL == null) ? null : nglMapTexture2DINTEL.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglMapTexture2DINTEL(final int p0, final int p1, final long p2, final int p3, final long p4, final long p5, final ByteBuffer p6, final long p7);
    
    public static void glUnmapTexture2DINTEL(final int n, final int n2) {
        final long glUnmapTexture2DINTEL = GLContext.getCapabilities().glUnmapTexture2DINTEL;
        BufferChecks.checkFunctionAddress(glUnmapTexture2DINTEL);
        nglUnmapTexture2DINTEL(n, n2, glUnmapTexture2DINTEL);
    }
    
    static native void nglUnmapTexture2DINTEL(final int p0, final int p1, final long p2);
    
    public static void glSyncTextureINTEL(final int n) {
        final long glSyncTextureINTEL = GLContext.getCapabilities().glSyncTextureINTEL;
        BufferChecks.checkFunctionAddress(glSyncTextureINTEL);
        nglSyncTextureINTEL(n, glSyncTextureINTEL);
    }
    
    static native void nglSyncTextureINTEL(final int p0, final long p1);
}
