package org.lwjgl.opengl;

import java.nio.*;

public final class ARBDrawIndirect
{
    public static final int GL_DRAW_INDIRECT_BUFFER = 36671;
    public static final int GL_DRAW_INDIRECT_BUFFER_BINDING = 36675;
    
    private ARBDrawIndirect() {
    }
    
    public static void glDrawArraysIndirect(final int n, final ByteBuffer byteBuffer) {
        GL40.glDrawArraysIndirect(n, byteBuffer);
    }
    
    public static void glDrawArraysIndirect(final int n, final long n2) {
        GL40.glDrawArraysIndirect(n, n2);
    }
    
    public static void glDrawArraysIndirect(final int n, final IntBuffer intBuffer) {
        GL40.glDrawArraysIndirect(n, intBuffer);
    }
    
    public static void glDrawElementsIndirect(final int n, final int n2, final ByteBuffer byteBuffer) {
        GL40.glDrawElementsIndirect(n, n2, byteBuffer);
    }
    
    public static void glDrawElementsIndirect(final int n, final int n2, final long n3) {
        GL40.glDrawElementsIndirect(n, n2, n3);
    }
    
    public static void glDrawElementsIndirect(final int n, final int n2, final IntBuffer intBuffer) {
        GL40.glDrawElementsIndirect(n, n2, intBuffer);
    }
}
