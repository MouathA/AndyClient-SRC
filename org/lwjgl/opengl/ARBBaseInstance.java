package org.lwjgl.opengl;

import java.nio.*;

public final class ARBBaseInstance
{
    private ARBBaseInstance() {
    }
    
    public static void glDrawArraysInstancedBaseInstance(final int n, final int n2, final int n3, final int n4, final int n5) {
        GL42.glDrawArraysInstancedBaseInstance(n, n2, n3, n4, n5);
    }
    
    public static void glDrawElementsInstancedBaseInstance(final int n, final ByteBuffer byteBuffer, final int n2, final int n3) {
        GL42.glDrawElementsInstancedBaseInstance(n, byteBuffer, n2, n3);
    }
    
    public static void glDrawElementsInstancedBaseInstance(final int n, final IntBuffer intBuffer, final int n2, final int n3) {
        GL42.glDrawElementsInstancedBaseInstance(n, intBuffer, n2, n3);
    }
    
    public static void glDrawElementsInstancedBaseInstance(final int n, final ShortBuffer shortBuffer, final int n2, final int n3) {
        GL42.glDrawElementsInstancedBaseInstance(n, shortBuffer, n2, n3);
    }
    
    public static void glDrawElementsInstancedBaseInstance(final int n, final int n2, final int n3, final long n4, final int n5, final int n6) {
        GL42.glDrawElementsInstancedBaseInstance(n, n2, n3, n4, n5, n6);
    }
    
    public static void glDrawElementsInstancedBaseVertexBaseInstance(final int n, final ByteBuffer byteBuffer, final int n2, final int n3, final int n4) {
        GL42.glDrawElementsInstancedBaseVertexBaseInstance(n, byteBuffer, n2, n3, n4);
    }
    
    public static void glDrawElementsInstancedBaseVertexBaseInstance(final int n, final IntBuffer intBuffer, final int n2, final int n3, final int n4) {
        GL42.glDrawElementsInstancedBaseVertexBaseInstance(n, intBuffer, n2, n3, n4);
    }
    
    public static void glDrawElementsInstancedBaseVertexBaseInstance(final int n, final ShortBuffer shortBuffer, final int n2, final int n3, final int n4) {
        GL42.glDrawElementsInstancedBaseVertexBaseInstance(n, shortBuffer, n2, n3, n4);
    }
    
    public static void glDrawElementsInstancedBaseVertexBaseInstance(final int n, final int n2, final int n3, final long n4, final int n5, final int n6, final int n7) {
        GL42.glDrawElementsInstancedBaseVertexBaseInstance(n, n2, n3, n4, n5, n6, n7);
    }
}
