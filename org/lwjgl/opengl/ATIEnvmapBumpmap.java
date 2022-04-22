package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class ATIEnvmapBumpmap
{
    public static final int GL_BUMP_ROT_MATRIX_ATI = 34677;
    public static final int GL_BUMP_ROT_MATRIX_SIZE_ATI = 34678;
    public static final int GL_BUMP_NUM_TEX_UNITS_ATI = 34679;
    public static final int GL_BUMP_TEX_UNITS_ATI = 34680;
    public static final int GL_DUDV_ATI = 34681;
    public static final int GL_DU8DV8_ATI = 34682;
    public static final int GL_BUMP_ENVMAP_ATI = 34683;
    public static final int GL_BUMP_TARGET_ATI = 34684;
    
    private ATIEnvmapBumpmap() {
    }
    
    public static void glTexBumpParameterATI(final int n, final FloatBuffer floatBuffer) {
        final long glTexBumpParameterfvATI = GLContext.getCapabilities().glTexBumpParameterfvATI;
        BufferChecks.checkFunctionAddress(glTexBumpParameterfvATI);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglTexBumpParameterfvATI(n, MemoryUtil.getAddress(floatBuffer), glTexBumpParameterfvATI);
    }
    
    static native void nglTexBumpParameterfvATI(final int p0, final long p1, final long p2);
    
    public static void glTexBumpParameterATI(final int n, final IntBuffer intBuffer) {
        final long glTexBumpParameterivATI = GLContext.getCapabilities().glTexBumpParameterivATI;
        BufferChecks.checkFunctionAddress(glTexBumpParameterivATI);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglTexBumpParameterivATI(n, MemoryUtil.getAddress(intBuffer), glTexBumpParameterivATI);
    }
    
    static native void nglTexBumpParameterivATI(final int p0, final long p1, final long p2);
    
    public static void glGetTexBumpParameterATI(final int n, final FloatBuffer floatBuffer) {
        final long glGetTexBumpParameterfvATI = GLContext.getCapabilities().glGetTexBumpParameterfvATI;
        BufferChecks.checkFunctionAddress(glGetTexBumpParameterfvATI);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetTexBumpParameterfvATI(n, MemoryUtil.getAddress(floatBuffer), glGetTexBumpParameterfvATI);
    }
    
    static native void nglGetTexBumpParameterfvATI(final int p0, final long p1, final long p2);
    
    public static void glGetTexBumpParameterATI(final int n, final IntBuffer intBuffer) {
        final long glGetTexBumpParameterivATI = GLContext.getCapabilities().glGetTexBumpParameterivATI;
        BufferChecks.checkFunctionAddress(glGetTexBumpParameterivATI);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetTexBumpParameterivATI(n, MemoryUtil.getAddress(intBuffer), glGetTexBumpParameterivATI);
    }
    
    static native void nglGetTexBumpParameterivATI(final int p0, final long p1, final long p2);
}
