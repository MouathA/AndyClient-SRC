package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ARBTextureBufferRange
{
    public static final int GL_TEXTURE_BUFFER_OFFSET = 37277;
    public static final int GL_TEXTURE_BUFFER_SIZE = 37278;
    public static final int GL_TEXTURE_BUFFER_OFFSET_ALIGNMENT = 37279;
    
    private ARBTextureBufferRange() {
    }
    
    public static void glTexBufferRange(final int n, final int n2, final int n3, final long n4, final long n5) {
        GL43.glTexBufferRange(n, n2, n3, n4, n5);
    }
    
    public static void glTextureBufferRangeEXT(final int n, final int n2, final int n3, final int n4, final long n5, final long n6) {
        final long glTextureBufferRangeEXT = GLContext.getCapabilities().glTextureBufferRangeEXT;
        BufferChecks.checkFunctionAddress(glTextureBufferRangeEXT);
        nglTextureBufferRangeEXT(n, n2, n3, n4, n5, n6, glTextureBufferRangeEXT);
    }
    
    static native void nglTextureBufferRangeEXT(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5, final long p6);
}
