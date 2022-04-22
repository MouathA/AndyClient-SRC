package org.lwjgl.opengl;

import org.lwjgl.*;

public final class NVCopyImage
{
    private NVCopyImage() {
    }
    
    public static void glCopyImageSubDataNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final int n12, final int n13, final int n14, final int n15) {
        final long glCopyImageSubDataNV = GLContext.getCapabilities().glCopyImageSubDataNV;
        BufferChecks.checkFunctionAddress(glCopyImageSubDataNV);
        nglCopyImageSubDataNV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, glCopyImageSubDataNV);
    }
    
    static native void nglCopyImageSubDataNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final int p11, final int p12, final int p13, final int p14, final long p15);
}
