package org.lwjgl.opengl;

import java.nio.*;

public final class KHRRobustness
{
    public static final int GL_GUILTY_CONTEXT_RESET = 33363;
    public static final int GL_INNOCENT_CONTEXT_RESET = 33364;
    public static final int GL_UNKNOWN_CONTEXT_RESET = 33365;
    public static final int GL_CONTEXT_ROBUST_ACCESS = 37107;
    public static final int GL_RESET_NOTIFICATION_STRATEGY = 33366;
    public static final int GL_LOSE_CONTEXT_ON_RESET = 33362;
    public static final int GL_NO_RESET_NOTIFICATION = 33377;
    public static final int GL_CONTEXT_LOST = 1287;
    
    private KHRRobustness() {
    }
    
    public static int glGetGraphicsResetStatus() {
        return GL45.glGetGraphicsResetStatus();
    }
    
    public static void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        GL45.glReadnPixels(n, n2, n3, n4, n5, n6, byteBuffer);
    }
    
    public static void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final DoubleBuffer doubleBuffer) {
        GL45.glReadnPixels(n, n2, n3, n4, n5, n6, doubleBuffer);
    }
    
    public static void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final FloatBuffer floatBuffer) {
        GL45.glReadnPixels(n, n2, n3, n4, n5, n6, floatBuffer);
    }
    
    public static void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IntBuffer intBuffer) {
        GL45.glReadnPixels(n, n2, n3, n4, n5, n6, intBuffer);
    }
    
    public static void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ShortBuffer shortBuffer) {
        GL45.glReadnPixels(n, n2, n3, n4, n5, n6, shortBuffer);
    }
    
    public static void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        GL45.glReadnPixels(n, n2, n3, n4, n5, n6, n7, n8);
    }
    
    public static void glGetnUniform(final int n, final int n2, final FloatBuffer floatBuffer) {
        GL45.glGetnUniform(n, n2, floatBuffer);
    }
    
    public static void glGetnUniform(final int n, final int n2, final IntBuffer intBuffer) {
        GL45.glGetnUniform(n, n2, intBuffer);
    }
    
    public static void glGetnUniformu(final int n, final int n2, final IntBuffer intBuffer) {
        GL45.glGetnUniformu(n, n2, intBuffer);
    }
}
