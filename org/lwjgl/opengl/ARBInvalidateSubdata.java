package org.lwjgl.opengl;

import java.nio.*;

public final class ARBInvalidateSubdata
{
    private ARBInvalidateSubdata() {
    }
    
    public static void glInvalidateTexSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        GL43.glInvalidateTexSubImage(n, n2, n3, n4, n5, n6, n7, n8);
    }
    
    public static void glInvalidateTexImage(final int n, final int n2) {
        GL43.glInvalidateTexImage(n, n2);
    }
    
    public static void glInvalidateBufferSubData(final int n, final long n2, final long n3) {
        GL43.glInvalidateBufferSubData(n, n2, n3);
    }
    
    public static void glInvalidateBufferData(final int n) {
        GL43.glInvalidateBufferData(n);
    }
    
    public static void glInvalidateFramebuffer(final int n, final IntBuffer intBuffer) {
        GL43.glInvalidateFramebuffer(n, intBuffer);
    }
    
    public static void glInvalidateSubFramebuffer(final int n, final IntBuffer intBuffer, final int n2, final int n3, final int n4, final int n5) {
        GL43.glInvalidateSubFramebuffer(n, intBuffer, n2, n3, n4, n5);
    }
}
