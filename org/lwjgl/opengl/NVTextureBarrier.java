package org.lwjgl.opengl;

import org.lwjgl.*;

public final class NVTextureBarrier
{
    private NVTextureBarrier() {
    }
    
    public static void glTextureBarrierNV() {
        final long glTextureBarrierNV = GLContext.getCapabilities().glTextureBarrierNV;
        BufferChecks.checkFunctionAddress(glTextureBarrierNV);
        nglTextureBarrierNV(glTextureBarrierNV);
    }
    
    static native void nglTextureBarrierNV(final long p0);
}
