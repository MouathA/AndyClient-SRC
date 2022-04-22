package org.lwjgl.opengl;

public final class ARBES31Compatibility
{
    private ARBES31Compatibility() {
    }
    
    public static void glMemoryBarrierByRegion(final int n) {
        GL45.glMemoryBarrierByRegion(n);
    }
}
