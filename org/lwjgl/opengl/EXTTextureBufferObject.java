package org.lwjgl.opengl;

import org.lwjgl.*;

public final class EXTTextureBufferObject
{
    public static final int GL_TEXTURE_BUFFER_EXT = 35882;
    public static final int GL_MAX_TEXTURE_BUFFER_SIZE_EXT = 35883;
    public static final int GL_TEXTURE_BINDING_BUFFER_EXT = 35884;
    public static final int GL_TEXTURE_BUFFER_DATA_STORE_BINDING_EXT = 35885;
    public static final int GL_TEXTURE_BUFFER_FORMAT_EXT = 35886;
    
    private EXTTextureBufferObject() {
    }
    
    public static void glTexBufferEXT(final int n, final int n2, final int n3) {
        final long glTexBufferEXT = GLContext.getCapabilities().glTexBufferEXT;
        BufferChecks.checkFunctionAddress(glTexBufferEXT);
        nglTexBufferEXT(n, n2, n3, glTexBufferEXT);
    }
    
    static native void nglTexBufferEXT(final int p0, final int p1, final int p2, final long p3);
}
