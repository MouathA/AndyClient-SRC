package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class NVFragmentProgram extends NVProgram
{
    public static final int GL_FRAGMENT_PROGRAM_NV = 34928;
    public static final int GL_MAX_TEXTURE_COORDS_NV = 34929;
    public static final int GL_MAX_TEXTURE_IMAGE_UNITS_NV = 34930;
    public static final int GL_FRAGMENT_PROGRAM_BINDING_NV = 34931;
    public static final int GL_MAX_FRAGMENT_PROGRAM_LOCAL_PARAMETERS_NV = 34920;
    
    private NVFragmentProgram() {
    }
    
    public static void glProgramNamedParameter4fNV(final int n, final ByteBuffer byteBuffer, final float n2, final float n3, final float n4, final float n5) {
        final long glProgramNamedParameter4fNV = GLContext.getCapabilities().glProgramNamedParameter4fNV;
        BufferChecks.checkFunctionAddress(glProgramNamedParameter4fNV);
        BufferChecks.checkDirect(byteBuffer);
        nglProgramNamedParameter4fNV(n, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), n2, n3, n4, n5, glProgramNamedParameter4fNV);
    }
    
    static native void nglProgramNamedParameter4fNV(final int p0, final int p1, final long p2, final float p3, final float p4, final float p5, final float p6, final long p7);
    
    public static void glProgramNamedParameter4dNV(final int n, final ByteBuffer byteBuffer, final double n2, final double n3, final double n4, final double n5) {
        final long glProgramNamedParameter4dNV = GLContext.getCapabilities().glProgramNamedParameter4dNV;
        BufferChecks.checkFunctionAddress(glProgramNamedParameter4dNV);
        BufferChecks.checkDirect(byteBuffer);
        nglProgramNamedParameter4dNV(n, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), n2, n3, n4, n5, glProgramNamedParameter4dNV);
    }
    
    static native void nglProgramNamedParameter4dNV(final int p0, final int p1, final long p2, final double p3, final double p4, final double p5, final double p6, final long p7);
    
    public static void glGetProgramNamedParameterNV(final int n, final ByteBuffer byteBuffer, final FloatBuffer floatBuffer) {
        final long glGetProgramNamedParameterfvNV = GLContext.getCapabilities().glGetProgramNamedParameterfvNV;
        BufferChecks.checkFunctionAddress(glGetProgramNamedParameterfvNV);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetProgramNamedParameterfvNV(n, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(floatBuffer), glGetProgramNamedParameterfvNV);
    }
    
    static native void nglGetProgramNamedParameterfvNV(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static void glGetProgramNamedParameterNV(final int n, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer) {
        final long glGetProgramNamedParameterdvNV = GLContext.getCapabilities().glGetProgramNamedParameterdvNV;
        BufferChecks.checkFunctionAddress(glGetProgramNamedParameterdvNV);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglGetProgramNamedParameterdvNV(n, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(doubleBuffer), glGetProgramNamedParameterdvNV);
    }
    
    static native void nglGetProgramNamedParameterdvNV(final int p0, final int p1, final long p2, final long p3, final long p4);
}
