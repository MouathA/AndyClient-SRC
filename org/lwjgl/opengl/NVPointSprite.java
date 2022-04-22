package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class NVPointSprite
{
    public static final int GL_POINT_SPRITE_NV = 34913;
    public static final int GL_COORD_REPLACE_NV = 34914;
    public static final int GL_POINT_SPRITE_R_MODE_NV = 34915;
    
    private NVPointSprite() {
    }
    
    public static void glPointParameteriNV(final int n, final int n2) {
        final long glPointParameteriNV = GLContext.getCapabilities().glPointParameteriNV;
        BufferChecks.checkFunctionAddress(glPointParameteriNV);
        nglPointParameteriNV(n, n2, glPointParameteriNV);
    }
    
    static native void nglPointParameteriNV(final int p0, final int p1, final long p2);
    
    public static void glPointParameterNV(final int n, final IntBuffer intBuffer) {
        final long glPointParameterivNV = GLContext.getCapabilities().glPointParameterivNV;
        BufferChecks.checkFunctionAddress(glPointParameterivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglPointParameterivNV(n, MemoryUtil.getAddress(intBuffer), glPointParameterivNV);
    }
    
    static native void nglPointParameterivNV(final int p0, final long p1, final long p2);
}
