package org.lwjgl.opengl;

import java.nio.*;

public final class ARBInternalformatQuery
{
    public static final int GL_NUM_SAMPLE_COUNTS = 37760;
    
    private ARBInternalformatQuery() {
    }
    
    public static void glGetInternalformat(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        GL42.glGetInternalformat(n, n2, n3, intBuffer);
    }
    
    public static int glGetInternalformat(final int n, final int n2, final int n3) {
        return GL42.glGetInternalformat(n, n2, n3);
    }
}
