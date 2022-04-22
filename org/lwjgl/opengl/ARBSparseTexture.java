package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ARBSparseTexture
{
    public static final int GL_TEXTURE_SPARSE_ARB = 37286;
    public static final int GL_VIRTUAL_PAGE_SIZE_INDEX_ARB = 37287;
    public static final int GL_NUM_SPARSE_LEVELS_ARB = 37290;
    public static final int GL_NUM_VIRTUAL_PAGE_SIZES_ARB = 37288;
    public static final int GL_VIRTUAL_PAGE_SIZE_X_ARB = 37269;
    public static final int GL_VIRTUAL_PAGE_SIZE_Y_ARB = 37270;
    public static final int GL_VIRTUAL_PAGE_SIZE_Z_ARB = 37271;
    public static final int GL_MAX_SPARSE_TEXTURE_SIZE_ARB = 37272;
    public static final int GL_MAX_SPARSE_3D_TEXTURE_SIZE_ARB = 37273;
    public static final int GL_MAX_SPARSE_ARRAY_TEXTURE_LAYERS_ARB = 37274;
    public static final int GL_SPARSE_TEXTURE_FULL_ARRAY_CUBE_MIPMAPS_ARB = 37289;
    
    private ARBSparseTexture() {
    }
    
    public static void glTexPageCommitmentARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final boolean b) {
        final long glTexPageCommitmentARB = GLContext.getCapabilities().glTexPageCommitmentARB;
        BufferChecks.checkFunctionAddress(glTexPageCommitmentARB);
        nglTexPageCommitmentARB(n, n2, n3, n4, n5, n6, n7, n8, b, glTexPageCommitmentARB);
    }
    
    static native void nglTexPageCommitmentARB(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final boolean p8, final long p9);
    
    public static void glTexturePageCommitmentEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final boolean b) {
        final long glTexturePageCommitmentEXT = GLContext.getCapabilities().glTexturePageCommitmentEXT;
        BufferChecks.checkFunctionAddress(glTexturePageCommitmentEXT);
        nglTexturePageCommitmentEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, b, glTexturePageCommitmentEXT);
    }
    
    static native void nglTexturePageCommitmentEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final boolean p9, final long p10);
}
