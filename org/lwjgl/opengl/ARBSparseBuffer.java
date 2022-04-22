package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ARBSparseBuffer
{
    public static final int GL_SPARSE_STORAGE_BIT_ARB = 1024;
    public static final int GL_SPARSE_BUFFER_PAGE_SIZE_ARB = 33528;
    
    private ARBSparseBuffer() {
    }
    
    public static void glBufferPageCommitmentARB(final int n, final long n2, final long n3, final boolean b) {
        final long glBufferPageCommitmentARB = GLContext.getCapabilities().glBufferPageCommitmentARB;
        BufferChecks.checkFunctionAddress(glBufferPageCommitmentARB);
        nglBufferPageCommitmentARB(n, n2, n3, b, glBufferPageCommitmentARB);
    }
    
    static native void nglBufferPageCommitmentARB(final int p0, final long p1, final long p2, final boolean p3, final long p4);
}
