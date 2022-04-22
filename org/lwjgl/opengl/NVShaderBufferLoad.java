package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class NVShaderBufferLoad
{
    public static final int GL_BUFFER_GPU_ADDRESS_NV = 36637;
    public static final int GL_GPU_ADDRESS_NV = 36660;
    public static final int GL_MAX_SHADER_BUFFER_ADDRESS_NV = 36661;
    
    private NVShaderBufferLoad() {
    }
    
    public static void glMakeBufferResidentNV(final int n, final int n2) {
        final long glMakeBufferResidentNV = GLContext.getCapabilities().glMakeBufferResidentNV;
        BufferChecks.checkFunctionAddress(glMakeBufferResidentNV);
        nglMakeBufferResidentNV(n, n2, glMakeBufferResidentNV);
    }
    
    static native void nglMakeBufferResidentNV(final int p0, final int p1, final long p2);
    
    public static void glMakeBufferNonResidentNV(final int n) {
        final long glMakeBufferNonResidentNV = GLContext.getCapabilities().glMakeBufferNonResidentNV;
        BufferChecks.checkFunctionAddress(glMakeBufferNonResidentNV);
        nglMakeBufferNonResidentNV(n, glMakeBufferNonResidentNV);
    }
    
    static native void nglMakeBufferNonResidentNV(final int p0, final long p1);
    
    public static boolean glIsBufferResidentNV(final int n) {
        final long glIsBufferResidentNV = GLContext.getCapabilities().glIsBufferResidentNV;
        BufferChecks.checkFunctionAddress(glIsBufferResidentNV);
        return nglIsBufferResidentNV(n, glIsBufferResidentNV);
    }
    
    static native boolean nglIsBufferResidentNV(final int p0, final long p1);
    
    public static void glMakeNamedBufferResidentNV(final int n, final int n2) {
        final long glMakeNamedBufferResidentNV = GLContext.getCapabilities().glMakeNamedBufferResidentNV;
        BufferChecks.checkFunctionAddress(glMakeNamedBufferResidentNV);
        nglMakeNamedBufferResidentNV(n, n2, glMakeNamedBufferResidentNV);
    }
    
    static native void nglMakeNamedBufferResidentNV(final int p0, final int p1, final long p2);
    
    public static void glMakeNamedBufferNonResidentNV(final int n) {
        final long glMakeNamedBufferNonResidentNV = GLContext.getCapabilities().glMakeNamedBufferNonResidentNV;
        BufferChecks.checkFunctionAddress(glMakeNamedBufferNonResidentNV);
        nglMakeNamedBufferNonResidentNV(n, glMakeNamedBufferNonResidentNV);
    }
    
    static native void nglMakeNamedBufferNonResidentNV(final int p0, final long p1);
    
    public static boolean glIsNamedBufferResidentNV(final int n) {
        final long glIsNamedBufferResidentNV = GLContext.getCapabilities().glIsNamedBufferResidentNV;
        BufferChecks.checkFunctionAddress(glIsNamedBufferResidentNV);
        return nglIsNamedBufferResidentNV(n, glIsNamedBufferResidentNV);
    }
    
    static native boolean nglIsNamedBufferResidentNV(final int p0, final long p1);
    
    public static void glGetBufferParameteruNV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glGetBufferParameterui64vNV = GLContext.getCapabilities().glGetBufferParameterui64vNV;
        BufferChecks.checkFunctionAddress(glGetBufferParameterui64vNV);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglGetBufferParameterui64vNV(n, n2, MemoryUtil.getAddress(longBuffer), glGetBufferParameterui64vNV);
    }
    
    static native void nglGetBufferParameterui64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static long glGetBufferParameterui64NV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetBufferParameterui64vNV = capabilities.glGetBufferParameterui64vNV;
        BufferChecks.checkFunctionAddress(glGetBufferParameterui64vNV);
        final LongBuffer bufferLong = APIUtil.getBufferLong(capabilities);
        nglGetBufferParameterui64vNV(n, n2, MemoryUtil.getAddress(bufferLong), glGetBufferParameterui64vNV);
        return bufferLong.get(0);
    }
    
    public static void glGetNamedBufferParameteruNV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glGetNamedBufferParameterui64vNV = GLContext.getCapabilities().glGetNamedBufferParameterui64vNV;
        BufferChecks.checkFunctionAddress(glGetNamedBufferParameterui64vNV);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglGetNamedBufferParameterui64vNV(n, n2, MemoryUtil.getAddress(longBuffer), glGetNamedBufferParameterui64vNV);
    }
    
    static native void nglGetNamedBufferParameterui64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static long glGetNamedBufferParameterui64NV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetNamedBufferParameterui64vNV = capabilities.glGetNamedBufferParameterui64vNV;
        BufferChecks.checkFunctionAddress(glGetNamedBufferParameterui64vNV);
        final LongBuffer bufferLong = APIUtil.getBufferLong(capabilities);
        nglGetNamedBufferParameterui64vNV(n, n2, MemoryUtil.getAddress(bufferLong), glGetNamedBufferParameterui64vNV);
        return bufferLong.get(0);
    }
    
    public static void glGetIntegeruNV(final int n, final LongBuffer longBuffer) {
        final long glGetIntegerui64vNV = GLContext.getCapabilities().glGetIntegerui64vNV;
        BufferChecks.checkFunctionAddress(glGetIntegerui64vNV);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglGetIntegerui64vNV(n, MemoryUtil.getAddress(longBuffer), glGetIntegerui64vNV);
    }
    
    static native void nglGetIntegerui64vNV(final int p0, final long p1, final long p2);
    
    public static long glGetIntegerui64NV(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetIntegerui64vNV = capabilities.glGetIntegerui64vNV;
        BufferChecks.checkFunctionAddress(glGetIntegerui64vNV);
        final LongBuffer bufferLong = APIUtil.getBufferLong(capabilities);
        nglGetIntegerui64vNV(n, MemoryUtil.getAddress(bufferLong), glGetIntegerui64vNV);
        return bufferLong.get(0);
    }
    
    public static void glUniformui64NV(final int n, final long n2) {
        final long glUniformui64NV = GLContext.getCapabilities().glUniformui64NV;
        BufferChecks.checkFunctionAddress(glUniformui64NV);
        nglUniformui64NV(n, n2, glUniformui64NV);
    }
    
    static native void nglUniformui64NV(final int p0, final long p1, final long p2);
    
    public static void glUniformuNV(final int n, final LongBuffer longBuffer) {
        final long glUniformui64vNV = GLContext.getCapabilities().glUniformui64vNV;
        BufferChecks.checkFunctionAddress(glUniformui64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglUniformui64vNV(n, longBuffer.remaining(), MemoryUtil.getAddress(longBuffer), glUniformui64vNV);
    }
    
    static native void nglUniformui64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetUniformuNV(final int n, final int n2, final LongBuffer longBuffer) {
        NVGpuShader5.glGetUniformuNV(n, n2, longBuffer);
    }
    
    public static void glProgramUniformui64NV(final int n, final int n2, final long n3) {
        final long glProgramUniformui64NV = GLContext.getCapabilities().glProgramUniformui64NV;
        BufferChecks.checkFunctionAddress(glProgramUniformui64NV);
        nglProgramUniformui64NV(n, n2, n3, glProgramUniformui64NV);
    }
    
    static native void nglProgramUniformui64NV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glProgramUniformuNV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glProgramUniformui64vNV = GLContext.getCapabilities().glProgramUniformui64vNV;
        BufferChecks.checkFunctionAddress(glProgramUniformui64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglProgramUniformui64vNV(n, n2, longBuffer.remaining(), MemoryUtil.getAddress(longBuffer), glProgramUniformui64vNV);
    }
    
    static native void nglProgramUniformui64vNV(final int p0, final int p1, final int p2, final long p3, final long p4);
}
