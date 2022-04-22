package org.lwjgl.opengl;

import java.nio.*;

public final class ARBVertexArrayObject
{
    public static final int GL_VERTEX_ARRAY_BINDING = 34229;
    
    private ARBVertexArrayObject() {
    }
    
    public static void glBindVertexArray(final int n) {
        GL30.glBindVertexArray(n);
    }
    
    public static void glDeleteVertexArrays(final IntBuffer intBuffer) {
        GL30.glDeleteVertexArrays(intBuffer);
    }
    
    public static void glDeleteVertexArrays(final int n) {
        GL30.glDeleteVertexArrays(n);
    }
    
    public static void glGenVertexArrays(final IntBuffer intBuffer) {
        GL30.glGenVertexArrays(intBuffer);
    }
    
    public static int glGenVertexArrays() {
        return GL30.glGenVertexArrays();
    }
    
    public static boolean glIsVertexArray(final int n) {
        return GL30.glIsVertexArray(n);
    }
}
