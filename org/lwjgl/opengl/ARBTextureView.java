package org.lwjgl.opengl;

public final class ARBTextureView
{
    public static final int GL_TEXTURE_VIEW_MIN_LEVEL = 33499;
    public static final int GL_TEXTURE_VIEW_NUM_LEVELS = 33500;
    public static final int GL_TEXTURE_VIEW_MIN_LAYER = 33501;
    public static final int GL_TEXTURE_VIEW_NUM_LAYERS = 33502;
    public static final int GL_TEXTURE_IMMUTABLE_LEVELS = 33503;
    
    private ARBTextureView() {
    }
    
    public static void glTextureView(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        GL43.glTextureView(n, n2, n3, n4, n5, n6, n7, n8);
    }
}
