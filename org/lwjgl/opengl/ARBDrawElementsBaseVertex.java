package org.lwjgl.opengl;

import java.nio.*;

public final class ARBDrawElementsBaseVertex
{
    private ARBDrawElementsBaseVertex() {
    }
    
    public static void glDrawElementsBaseVertex(final int n, final ByteBuffer byteBuffer, final int n2) {
        GL32.glDrawElementsBaseVertex(n, byteBuffer, n2);
    }
    
    public static void glDrawElementsBaseVertex(final int n, final IntBuffer intBuffer, final int n2) {
        GL32.glDrawElementsBaseVertex(n, intBuffer, n2);
    }
    
    public static void glDrawElementsBaseVertex(final int n, final ShortBuffer shortBuffer, final int n2) {
        GL32.glDrawElementsBaseVertex(n, shortBuffer, n2);
    }
    
    public static void glDrawElementsBaseVertex(final int n, final int n2, final int n3, final long n4, final int n5) {
        GL32.glDrawElementsBaseVertex(n, n2, n3, n4, n5);
    }
    
    public static void glDrawRangeElementsBaseVertex(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final int n4) {
        GL32.glDrawRangeElementsBaseVertex(n, n2, n3, byteBuffer, n4);
    }
    
    public static void glDrawRangeElementsBaseVertex(final int n, final int n2, final int n3, final IntBuffer intBuffer, final int n4) {
        GL32.glDrawRangeElementsBaseVertex(n, n2, n3, intBuffer, n4);
    }
    
    public static void glDrawRangeElementsBaseVertex(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final int n4) {
        GL32.glDrawRangeElementsBaseVertex(n, n2, n3, shortBuffer, n4);
    }
    
    public static void glDrawRangeElementsBaseVertex(final int n, final int n2, final int n3, final int n4, final int n5, final long n6, final int n7) {
        GL32.glDrawRangeElementsBaseVertex(n, n2, n3, n4, n5, n6, n7);
    }
    
    public static void glDrawElementsInstancedBaseVertex(final int n, final ByteBuffer byteBuffer, final int n2, final int n3) {
        GL32.glDrawElementsInstancedBaseVertex(n, byteBuffer, n2, n3);
    }
    
    public static void glDrawElementsInstancedBaseVertex(final int n, final IntBuffer intBuffer, final int n2, final int n3) {
        GL32.glDrawElementsInstancedBaseVertex(n, intBuffer, n2, n3);
    }
    
    public static void glDrawElementsInstancedBaseVertex(final int n, final ShortBuffer shortBuffer, final int n2, final int n3) {
        GL32.glDrawElementsInstancedBaseVertex(n, shortBuffer, n2, n3);
    }
    
    public static void glDrawElementsInstancedBaseVertex(final int n, final int n2, final int n3, final long n4, final int n5, final int n6) {
        GL32.glDrawElementsInstancedBaseVertex(n, n2, n3, n4, n5, n6);
    }
}
