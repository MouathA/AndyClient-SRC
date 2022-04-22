package org.lwjgl.opengl;

import java.nio.*;

public final class ARBClearTexture
{
    public static final int GL_CLEAR_TEXTURE = 37733;
    
    private ARBClearTexture() {
    }
    
    public static void glClearTexImage(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        GL44.glClearTexImage(n, n2, n3, n4, byteBuffer);
    }
    
    public static void glClearTexImage(final int n, final int n2, final int n3, final int n4, final DoubleBuffer doubleBuffer) {
        GL44.glClearTexImage(n, n2, n3, n4, doubleBuffer);
    }
    
    public static void glClearTexImage(final int n, final int n2, final int n3, final int n4, final FloatBuffer floatBuffer) {
        GL44.glClearTexImage(n, n2, n3, n4, floatBuffer);
    }
    
    public static void glClearTexImage(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        GL44.glClearTexImage(n, n2, n3, n4, intBuffer);
    }
    
    public static void glClearTexImage(final int n, final int n2, final int n3, final int n4, final ShortBuffer shortBuffer) {
        GL44.glClearTexImage(n, n2, n3, n4, shortBuffer);
    }
    
    public static void glClearTexImage(final int n, final int n2, final int n3, final int n4, final LongBuffer longBuffer) {
        GL44.glClearTexImage(n, n2, n3, n4, longBuffer);
    }
    
    public static void glClearTexSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ByteBuffer byteBuffer) {
        GL44.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, byteBuffer);
    }
    
    public static void glClearTexSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final DoubleBuffer doubleBuffer) {
        GL44.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, doubleBuffer);
    }
    
    public static void glClearTexSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final FloatBuffer floatBuffer) {
        GL44.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, floatBuffer);
    }
    
    public static void glClearTexSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final IntBuffer intBuffer) {
        GL44.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, intBuffer);
    }
    
    public static void glClearTexSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ShortBuffer shortBuffer) {
        GL44.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, shortBuffer);
    }
    
    public static void glClearTexSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final LongBuffer longBuffer) {
        GL44.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, longBuffer);
    }
}
