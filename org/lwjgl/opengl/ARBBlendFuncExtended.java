package org.lwjgl.opengl;

import java.nio.*;

public final class ARBBlendFuncExtended
{
    public static final int GL_SRC1_COLOR = 35065;
    public static final int GL_SRC1_ALPHA = 34185;
    public static final int GL_ONE_MINUS_SRC1_COLOR = 35066;
    public static final int GL_ONE_MINUS_SRC1_ALPHA = 35067;
    public static final int GL_MAX_DUAL_SOURCE_DRAW_BUFFERS = 35068;
    
    private ARBBlendFuncExtended() {
    }
    
    public static void glBindFragDataLocationIndexed(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        GL33.glBindFragDataLocationIndexed(n, n2, n3, byteBuffer);
    }
    
    public static void glBindFragDataLocationIndexed(final int n, final int n2, final int n3, final CharSequence charSequence) {
        GL33.glBindFragDataLocationIndexed(n, n2, n3, charSequence);
    }
    
    public static int glGetFragDataIndex(final int n, final ByteBuffer byteBuffer) {
        return GL33.glGetFragDataIndex(n, byteBuffer);
    }
    
    public static int glGetFragDataIndex(final int n, final CharSequence charSequence) {
        return GL33.glGetFragDataIndex(n, charSequence);
    }
}
