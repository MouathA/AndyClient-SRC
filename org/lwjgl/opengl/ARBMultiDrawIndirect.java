package org.lwjgl.opengl;

import java.nio.*;

public final class ARBMultiDrawIndirect
{
    private ARBMultiDrawIndirect() {
    }
    
    public static void glMultiDrawArraysIndirect(final int n, final ByteBuffer byteBuffer, final int n2, final int n3) {
        GL43.glMultiDrawArraysIndirect(n, byteBuffer, n2, n3);
    }
    
    public static void glMultiDrawArraysIndirect(final int n, final long n2, final int n3, final int n4) {
        GL43.glMultiDrawArraysIndirect(n, n2, n3, n4);
    }
    
    public static void glMultiDrawArraysIndirect(final int n, final IntBuffer intBuffer, final int n2, final int n3) {
        GL43.glMultiDrawArraysIndirect(n, intBuffer, n2, n3);
    }
    
    public static void glMultiDrawElementsIndirect(final int n, final int n2, final ByteBuffer byteBuffer, final int n3, final int n4) {
        GL43.glMultiDrawElementsIndirect(n, n2, byteBuffer, n3, n4);
    }
    
    public static void glMultiDrawElementsIndirect(final int n, final int n2, final long n3, final int n4, final int n5) {
        GL43.glMultiDrawElementsIndirect(n, n2, n3, n4, n5);
    }
    
    public static void glMultiDrawElementsIndirect(final int n, final int n2, final IntBuffer intBuffer, final int n3, final int n4) {
        GL43.glMultiDrawElementsIndirect(n, n2, intBuffer, n3, n4);
    }
}
