package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class NVGpuProgram4
{
    public static final int GL_PROGRAM_ATTRIB_COMPONENTS_NV = 35078;
    public static final int GL_PROGRAM_RESULT_COMPONENTS_NV = 35079;
    public static final int GL_MAX_PROGRAM_ATTRIB_COMPONENTS_NV = 35080;
    public static final int GL_MAX_PROGRAM_RESULT_COMPONENTS_NV = 35081;
    public static final int GL_MAX_PROGRAM_GENERIC_ATTRIBS_NV = 36261;
    public static final int GL_MAX_PROGRAM_GENERIC_RESULTS_NV = 36262;
    
    private NVGpuProgram4() {
    }
    
    public static void glProgramLocalParameterI4iNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glProgramLocalParameterI4iNV = GLContext.getCapabilities().glProgramLocalParameterI4iNV;
        BufferChecks.checkFunctionAddress(glProgramLocalParameterI4iNV);
        nglProgramLocalParameterI4iNV(n, n2, n3, n4, n5, n6, glProgramLocalParameterI4iNV);
    }
    
    static native void nglProgramLocalParameterI4iNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glProgramLocalParameterI4NV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramLocalParameterI4ivNV = GLContext.getCapabilities().glProgramLocalParameterI4ivNV;
        BufferChecks.checkFunctionAddress(glProgramLocalParameterI4ivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglProgramLocalParameterI4ivNV(n, n2, MemoryUtil.getAddress(intBuffer), glProgramLocalParameterI4ivNV);
    }
    
    static native void nglProgramLocalParameterI4ivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glProgramLocalParametersI4NV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramLocalParametersI4ivNV = GLContext.getCapabilities().glProgramLocalParametersI4ivNV;
        BufferChecks.checkFunctionAddress(glProgramLocalParametersI4ivNV);
        BufferChecks.checkDirect(intBuffer);
        nglProgramLocalParametersI4ivNV(n, n2, intBuffer.remaining() >> 2, MemoryUtil.getAddress(intBuffer), glProgramLocalParametersI4ivNV);
    }
    
    static native void nglProgramLocalParametersI4ivNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramLocalParameterI4uiNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glProgramLocalParameterI4uiNV = GLContext.getCapabilities().glProgramLocalParameterI4uiNV;
        BufferChecks.checkFunctionAddress(glProgramLocalParameterI4uiNV);
        nglProgramLocalParameterI4uiNV(n, n2, n3, n4, n5, n6, glProgramLocalParameterI4uiNV);
    }
    
    static native void nglProgramLocalParameterI4uiNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glProgramLocalParameterI4uNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramLocalParameterI4uivNV = GLContext.getCapabilities().glProgramLocalParameterI4uivNV;
        BufferChecks.checkFunctionAddress(glProgramLocalParameterI4uivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglProgramLocalParameterI4uivNV(n, n2, MemoryUtil.getAddress(intBuffer), glProgramLocalParameterI4uivNV);
    }
    
    static native void nglProgramLocalParameterI4uivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glProgramLocalParametersI4uNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramLocalParametersI4uivNV = GLContext.getCapabilities().glProgramLocalParametersI4uivNV;
        BufferChecks.checkFunctionAddress(glProgramLocalParametersI4uivNV);
        BufferChecks.checkDirect(intBuffer);
        nglProgramLocalParametersI4uivNV(n, n2, intBuffer.remaining() >> 2, MemoryUtil.getAddress(intBuffer), glProgramLocalParametersI4uivNV);
    }
    
    static native void nglProgramLocalParametersI4uivNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramEnvParameterI4iNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glProgramEnvParameterI4iNV = GLContext.getCapabilities().glProgramEnvParameterI4iNV;
        BufferChecks.checkFunctionAddress(glProgramEnvParameterI4iNV);
        nglProgramEnvParameterI4iNV(n, n2, n3, n4, n5, n6, glProgramEnvParameterI4iNV);
    }
    
    static native void nglProgramEnvParameterI4iNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glProgramEnvParameterI4NV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramEnvParameterI4ivNV = GLContext.getCapabilities().glProgramEnvParameterI4ivNV;
        BufferChecks.checkFunctionAddress(glProgramEnvParameterI4ivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglProgramEnvParameterI4ivNV(n, n2, MemoryUtil.getAddress(intBuffer), glProgramEnvParameterI4ivNV);
    }
    
    static native void nglProgramEnvParameterI4ivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glProgramEnvParametersI4NV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramEnvParametersI4ivNV = GLContext.getCapabilities().glProgramEnvParametersI4ivNV;
        BufferChecks.checkFunctionAddress(glProgramEnvParametersI4ivNV);
        BufferChecks.checkDirect(intBuffer);
        nglProgramEnvParametersI4ivNV(n, n2, intBuffer.remaining() >> 2, MemoryUtil.getAddress(intBuffer), glProgramEnvParametersI4ivNV);
    }
    
    static native void nglProgramEnvParametersI4ivNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramEnvParameterI4uiNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glProgramEnvParameterI4uiNV = GLContext.getCapabilities().glProgramEnvParameterI4uiNV;
        BufferChecks.checkFunctionAddress(glProgramEnvParameterI4uiNV);
        nglProgramEnvParameterI4uiNV(n, n2, n3, n4, n5, n6, glProgramEnvParameterI4uiNV);
    }
    
    static native void nglProgramEnvParameterI4uiNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glProgramEnvParameterI4uNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramEnvParameterI4uivNV = GLContext.getCapabilities().glProgramEnvParameterI4uivNV;
        BufferChecks.checkFunctionAddress(glProgramEnvParameterI4uivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglProgramEnvParameterI4uivNV(n, n2, MemoryUtil.getAddress(intBuffer), glProgramEnvParameterI4uivNV);
    }
    
    static native void nglProgramEnvParameterI4uivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glProgramEnvParametersI4uNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramEnvParametersI4uivNV = GLContext.getCapabilities().glProgramEnvParametersI4uivNV;
        BufferChecks.checkFunctionAddress(glProgramEnvParametersI4uivNV);
        BufferChecks.checkDirect(intBuffer);
        nglProgramEnvParametersI4uivNV(n, n2, intBuffer.remaining() >> 2, MemoryUtil.getAddress(intBuffer), glProgramEnvParametersI4uivNV);
    }
    
    static native void nglProgramEnvParametersI4uivNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetProgramLocalParameterINV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetProgramLocalParameterIivNV = GLContext.getCapabilities().glGetProgramLocalParameterIivNV;
        BufferChecks.checkFunctionAddress(glGetProgramLocalParameterIivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetProgramLocalParameterIivNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetProgramLocalParameterIivNV);
    }
    
    static native void nglGetProgramLocalParameterIivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetProgramLocalParameterIuNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetProgramLocalParameterIuivNV = GLContext.getCapabilities().glGetProgramLocalParameterIuivNV;
        BufferChecks.checkFunctionAddress(glGetProgramLocalParameterIuivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetProgramLocalParameterIuivNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetProgramLocalParameterIuivNV);
    }
    
    static native void nglGetProgramLocalParameterIuivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetProgramEnvParameterINV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetProgramEnvParameterIivNV = GLContext.getCapabilities().glGetProgramEnvParameterIivNV;
        BufferChecks.checkFunctionAddress(glGetProgramEnvParameterIivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetProgramEnvParameterIivNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetProgramEnvParameterIivNV);
    }
    
    static native void nglGetProgramEnvParameterIivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetProgramEnvParameterIuNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetProgramEnvParameterIuivNV = GLContext.getCapabilities().glGetProgramEnvParameterIuivNV;
        BufferChecks.checkFunctionAddress(glGetProgramEnvParameterIuivNV);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetProgramEnvParameterIuivNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetProgramEnvParameterIuivNV);
    }
    
    static native void nglGetProgramEnvParameterIuivNV(final int p0, final int p1, final long p2, final long p3);
}
