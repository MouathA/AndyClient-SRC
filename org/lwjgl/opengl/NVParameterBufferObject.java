package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class NVParameterBufferObject
{
    public static final int GL_MAX_PROGRAM_PARAMETER_BUFFER_BINDINGS_NV = 36256;
    public static final int GL_MAX_PROGRAM_PARAMETER_BUFFER_SIZE_NV = 36257;
    public static final int GL_VERTEX_PROGRAM_PARAMETER_BUFFER_NV = 36258;
    public static final int GL_GEOMETRY_PROGRAM_PARAMETER_BUFFER_NV = 36259;
    public static final int GL_FRAGMENT_PROGRAM_PARAMETER_BUFFER_NV = 36260;
    
    private NVParameterBufferObject() {
    }
    
    public static void glProgramBufferParametersNV(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glProgramBufferParametersfvNV = GLContext.getCapabilities().glProgramBufferParametersfvNV;
        BufferChecks.checkFunctionAddress(glProgramBufferParametersfvNV);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramBufferParametersfvNV(n, n2, n3, floatBuffer.remaining() >> 2, MemoryUtil.getAddress(floatBuffer), glProgramBufferParametersfvNV);
    }
    
    static native void nglProgramBufferParametersfvNV(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glProgramBufferParametersINV(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glProgramBufferParametersIivNV = GLContext.getCapabilities().glProgramBufferParametersIivNV;
        BufferChecks.checkFunctionAddress(glProgramBufferParametersIivNV);
        BufferChecks.checkDirect(intBuffer);
        nglProgramBufferParametersIivNV(n, n2, n3, intBuffer.remaining() >> 2, MemoryUtil.getAddress(intBuffer), glProgramBufferParametersIivNV);
    }
    
    static native void nglProgramBufferParametersIivNV(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glProgramBufferParametersIuNV(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glProgramBufferParametersIuivNV = GLContext.getCapabilities().glProgramBufferParametersIuivNV;
        BufferChecks.checkFunctionAddress(glProgramBufferParametersIuivNV);
        BufferChecks.checkDirect(intBuffer);
        nglProgramBufferParametersIuivNV(n, n2, n3, intBuffer.remaining() >> 2, MemoryUtil.getAddress(intBuffer), glProgramBufferParametersIuivNV);
    }
    
    static native void nglProgramBufferParametersIuivNV(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
}
