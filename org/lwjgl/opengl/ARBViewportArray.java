package org.lwjgl.opengl;

import java.nio.*;

public final class ARBViewportArray
{
    public static final int GL_MAX_VIEWPORTS = 33371;
    public static final int GL_VIEWPORT_SUBPIXEL_BITS = 33372;
    public static final int GL_VIEWPORT_BOUNDS_RANGE = 33373;
    public static final int GL_LAYER_PROVOKING_VERTEX = 33374;
    public static final int GL_VIEWPORT_INDEX_PROVOKING_VERTEX = 33375;
    public static final int GL_FIRST_VERTEX_CONVENTION = 36429;
    public static final int GL_LAST_VERTEX_CONVENTION = 36430;
    public static final int GL_PROVOKING_VERTEX = 36431;
    public static final int GL_UNDEFINED_VERTEX = 33376;
    
    private ARBViewportArray() {
    }
    
    public static void glViewportArray(final int n, final FloatBuffer floatBuffer) {
        GL41.glViewportArray(n, floatBuffer);
    }
    
    public static void glViewportIndexedf(final int n, final float n2, final float n3, final float n4, final float n5) {
        GL41.glViewportIndexedf(n, n2, n3, n4, n5);
    }
    
    public static void glViewportIndexed(final int n, final FloatBuffer floatBuffer) {
        GL41.glViewportIndexed(n, floatBuffer);
    }
    
    public static void glScissorArray(final int n, final IntBuffer intBuffer) {
        GL41.glScissorArray(n, intBuffer);
    }
    
    public static void glScissorIndexed(final int n, final int n2, final int n3, final int n4, final int n5) {
        GL41.glScissorIndexed(n, n2, n3, n4, n5);
    }
    
    public static void glScissorIndexed(final int n, final IntBuffer intBuffer) {
        GL41.glScissorIndexed(n, intBuffer);
    }
    
    public static void glDepthRangeArray(final int n, final DoubleBuffer doubleBuffer) {
        GL41.glDepthRangeArray(n, doubleBuffer);
    }
    
    public static void glDepthRangeIndexed(final int n, final double n2, final double n3) {
        GL41.glDepthRangeIndexed(n, n2, n3);
    }
    
    public static void glGetFloat(final int n, final int n2, final FloatBuffer floatBuffer) {
        GL41.glGetFloat(n, n2, floatBuffer);
    }
    
    public static float glGetFloat(final int n, final int n2) {
        return GL41.glGetFloat(n, n2);
    }
    
    public static void glGetDouble(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        GL41.glGetDouble(n, n2, doubleBuffer);
    }
    
    public static double glGetDouble(final int n, final int n2) {
        return GL41.glGetDouble(n, n2);
    }
    
    public static void glGetIntegerIndexedEXT(final int n, final int n2, final IntBuffer intBuffer) {
        EXTDrawBuffers2.glGetIntegerIndexedEXT(n, n2, intBuffer);
    }
    
    public static int glGetIntegerIndexedEXT(final int n, final int n2) {
        return EXTDrawBuffers2.glGetIntegerIndexedEXT(n, n2);
    }
    
    public static void glEnableIndexedEXT(final int n, final int n2) {
        EXTDrawBuffers2.glEnableIndexedEXT(n, n2);
    }
    
    public static void glDisableIndexedEXT(final int n, final int n2) {
        EXTDrawBuffers2.glDisableIndexedEXT(n, n2);
    }
    
    public static boolean glIsEnabledIndexedEXT(final int n, final int n2) {
        return EXTDrawBuffers2.glIsEnabledIndexedEXT(n, n2);
    }
}
