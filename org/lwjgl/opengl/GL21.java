package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class GL21
{
    public static final int GL_FLOAT_MAT2x3 = 35685;
    public static final int GL_FLOAT_MAT2x4 = 35686;
    public static final int GL_FLOAT_MAT3x2 = 35687;
    public static final int GL_FLOAT_MAT3x4 = 35688;
    public static final int GL_FLOAT_MAT4x2 = 35689;
    public static final int GL_FLOAT_MAT4x3 = 35690;
    public static final int GL_PIXEL_PACK_BUFFER = 35051;
    public static final int GL_PIXEL_UNPACK_BUFFER = 35052;
    public static final int GL_PIXEL_PACK_BUFFER_BINDING = 35053;
    public static final int GL_PIXEL_UNPACK_BUFFER_BINDING = 35055;
    public static final int GL_SRGB = 35904;
    public static final int GL_SRGB8 = 35905;
    public static final int GL_SRGB_ALPHA = 35906;
    public static final int GL_SRGB8_ALPHA8 = 35907;
    public static final int GL_SLUMINANCE_ALPHA = 35908;
    public static final int GL_SLUMINANCE8_ALPHA8 = 35909;
    public static final int GL_SLUMINANCE = 35910;
    public static final int GL_SLUMINANCE8 = 35911;
    public static final int GL_COMPRESSED_SRGB = 35912;
    public static final int GL_COMPRESSED_SRGB_ALPHA = 35913;
    public static final int GL_COMPRESSED_SLUMINANCE = 35914;
    public static final int GL_COMPRESSED_SLUMINANCE_ALPHA = 35915;
    public static final int GL_CURRENT_RASTER_SECONDARY_COLOR = 33887;
    
    private GL21() {
    }
    
    public static void glUniformMatrix2x3(final int n, final boolean b, final FloatBuffer floatBuffer) {
        final long glUniformMatrix2x3fv = GLContext.getCapabilities().glUniformMatrix2x3fv;
        BufferChecks.checkFunctionAddress(glUniformMatrix2x3fv);
        BufferChecks.checkDirect(floatBuffer);
        nglUniformMatrix2x3fv(n, floatBuffer.remaining() / 6, b, MemoryUtil.getAddress(floatBuffer), glUniformMatrix2x3fv);
    }
    
    static native void nglUniformMatrix2x3fv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glUniformMatrix3x2(final int n, final boolean b, final FloatBuffer floatBuffer) {
        final long glUniformMatrix3x2fv = GLContext.getCapabilities().glUniformMatrix3x2fv;
        BufferChecks.checkFunctionAddress(glUniformMatrix3x2fv);
        BufferChecks.checkDirect(floatBuffer);
        nglUniformMatrix3x2fv(n, floatBuffer.remaining() / 6, b, MemoryUtil.getAddress(floatBuffer), glUniformMatrix3x2fv);
    }
    
    static native void nglUniformMatrix3x2fv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glUniformMatrix2x4(final int n, final boolean b, final FloatBuffer floatBuffer) {
        final long glUniformMatrix2x4fv = GLContext.getCapabilities().glUniformMatrix2x4fv;
        BufferChecks.checkFunctionAddress(glUniformMatrix2x4fv);
        BufferChecks.checkDirect(floatBuffer);
        nglUniformMatrix2x4fv(n, floatBuffer.remaining() >> 3, b, MemoryUtil.getAddress(floatBuffer), glUniformMatrix2x4fv);
    }
    
    static native void nglUniformMatrix2x4fv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glUniformMatrix4x2(final int n, final boolean b, final FloatBuffer floatBuffer) {
        final long glUniformMatrix4x2fv = GLContext.getCapabilities().glUniformMatrix4x2fv;
        BufferChecks.checkFunctionAddress(glUniformMatrix4x2fv);
        BufferChecks.checkDirect(floatBuffer);
        nglUniformMatrix4x2fv(n, floatBuffer.remaining() >> 3, b, MemoryUtil.getAddress(floatBuffer), glUniformMatrix4x2fv);
    }
    
    static native void nglUniformMatrix4x2fv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glUniformMatrix3x4(final int n, final boolean b, final FloatBuffer floatBuffer) {
        final long glUniformMatrix3x4fv = GLContext.getCapabilities().glUniformMatrix3x4fv;
        BufferChecks.checkFunctionAddress(glUniformMatrix3x4fv);
        BufferChecks.checkDirect(floatBuffer);
        nglUniformMatrix3x4fv(n, floatBuffer.remaining() / 12, b, MemoryUtil.getAddress(floatBuffer), glUniformMatrix3x4fv);
    }
    
    static native void nglUniformMatrix3x4fv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glUniformMatrix4x3(final int n, final boolean b, final FloatBuffer floatBuffer) {
        final long glUniformMatrix4x3fv = GLContext.getCapabilities().glUniformMatrix4x3fv;
        BufferChecks.checkFunctionAddress(glUniformMatrix4x3fv);
        BufferChecks.checkDirect(floatBuffer);
        nglUniformMatrix4x3fv(n, floatBuffer.remaining() / 12, b, MemoryUtil.getAddress(floatBuffer), glUniformMatrix4x3fv);
    }
    
    static native void nglUniformMatrix4x3fv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
}
