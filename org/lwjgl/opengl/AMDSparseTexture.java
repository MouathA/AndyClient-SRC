package org.lwjgl.opengl;

import org.lwjgl.*;

public final class AMDSparseTexture
{
    public static final int GL_TEXTURE_STORAGE_SPARSE_BIT_AMD = 1;
    public static final int GL_VIRTUAL_PAGE_SIZE_X_AMD = 37269;
    public static final int GL_VIRTUAL_PAGE_SIZE_Y_AMD = 37270;
    public static final int GL_VIRTUAL_PAGE_SIZE_Z_AMD = 37271;
    public static final int GL_MAX_SPARSE_TEXTURE_SIZE_AMD = 37272;
    public static final int GL_MAX_SPARSE_3D_TEXTURE_SIZE_AMD = 37273;
    public static final int GL_MAX_SPARSE_ARRAY_TEXTURE_LAYERS = 37274;
    public static final int GL_MIN_SPARSE_LEVEL_AMD = 37275;
    public static final int GL_MIN_LOD_WARNING_AMD = 37276;
    
    private AMDSparseTexture() {
    }
    
    public static void glTexStorageSparseAMD(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        final long glTexStorageSparseAMD = GLContext.getCapabilities().glTexStorageSparseAMD;
        BufferChecks.checkFunctionAddress(glTexStorageSparseAMD);
        nglTexStorageSparseAMD(n, n2, n3, n4, n5, n6, n7, glTexStorageSparseAMD);
    }
    
    static native void nglTexStorageSparseAMD(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7);
    
    public static void glTextureStorageSparseAMD(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        final long glTextureStorageSparseAMD = GLContext.getCapabilities().glTextureStorageSparseAMD;
        BufferChecks.checkFunctionAddress(glTextureStorageSparseAMD);
        nglTextureStorageSparseAMD(n, n2, n3, n4, n5, n6, n7, n8, glTextureStorageSparseAMD);
    }
    
    static native void nglTextureStorageSparseAMD(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
}
