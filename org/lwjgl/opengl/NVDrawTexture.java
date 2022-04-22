package org.lwjgl.opengl;

import org.lwjgl.*;

public final class NVDrawTexture
{
    private NVDrawTexture() {
    }
    
    public static void glDrawTextureNV(final int n, final int n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float n10, final float n11) {
        final long glDrawTextureNV = GLContext.getCapabilities().glDrawTextureNV;
        BufferChecks.checkFunctionAddress(glDrawTextureNV);
        nglDrawTextureNV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, glDrawTextureNV);
    }
    
    static native void nglDrawTextureNV(final int p0, final int p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final float p8, final float p9, final float p10, final long p11);
}
