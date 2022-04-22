package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class APPLETextureRange
{
    public static final int GL_TEXTURE_STORAGE_HINT_APPLE = 34236;
    public static final int GL_STORAGE_PRIVATE_APPLE = 34237;
    public static final int GL_STORAGE_CACHED_APPLE = 34238;
    public static final int GL_STORAGE_SHARED_APPLE = 34239;
    public static final int GL_TEXTURE_RANGE_LENGTH_APPLE = 34231;
    public static final int GL_TEXTURE_RANGE_POINTER_APPLE = 34232;
    
    private APPLETextureRange() {
    }
    
    public static void glTextureRangeAPPLE(final int n, final ByteBuffer byteBuffer) {
        final long glTextureRangeAPPLE = GLContext.getCapabilities().glTextureRangeAPPLE;
        BufferChecks.checkFunctionAddress(glTextureRangeAPPLE);
        BufferChecks.checkDirect(byteBuffer);
        nglTextureRangeAPPLE(n, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glTextureRangeAPPLE);
    }
    
    static native void nglTextureRangeAPPLE(final int p0, final int p1, final long p2, final long p3);
    
    public static Buffer glGetTexParameterPointervAPPLE(final int n, final int n2, final long n3) {
        final long glGetTexParameterPointervAPPLE = GLContext.getCapabilities().glGetTexParameterPointervAPPLE;
        BufferChecks.checkFunctionAddress(glGetTexParameterPointervAPPLE);
        return nglGetTexParameterPointervAPPLE(n, n2, n3, glGetTexParameterPointervAPPLE);
    }
    
    static native Buffer nglGetTexParameterPointervAPPLE(final int p0, final int p1, final long p2, final long p3);
}
