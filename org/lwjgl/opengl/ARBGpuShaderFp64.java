package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class ARBGpuShaderFp64
{
    public static final int GL_DOUBLE_VEC2 = 36860;
    public static final int GL_DOUBLE_VEC3 = 36861;
    public static final int GL_DOUBLE_VEC4 = 36862;
    public static final int GL_DOUBLE_MAT2 = 36678;
    public static final int GL_DOUBLE_MAT3 = 36679;
    public static final int GL_DOUBLE_MAT4 = 36680;
    public static final int GL_DOUBLE_MAT2x3 = 36681;
    public static final int GL_DOUBLE_MAT2x4 = 36682;
    public static final int GL_DOUBLE_MAT3x2 = 36683;
    public static final int GL_DOUBLE_MAT3x4 = 36684;
    public static final int GL_DOUBLE_MAT4x2 = 36685;
    public static final int GL_DOUBLE_MAT4x3 = 36686;
    
    private ARBGpuShaderFp64() {
    }
    
    public static void glUniform1d(final int n, final double n2) {
        GL40.glUniform1d(n, n2);
    }
    
    public static void glUniform2d(final int n, final double n2, final double n3) {
        GL40.glUniform2d(n, n2, n3);
    }
    
    public static void glUniform3d(final int n, final double n2, final double n3, final double n4) {
        GL40.glUniform3d(n, n2, n3, n4);
    }
    
    public static void glUniform4d(final int n, final double n2, final double n3, final double n4, final double n5) {
        GL40.glUniform4d(n, n2, n3, n4, n5);
    }
    
    public static void glUniform1(final int n, final DoubleBuffer doubleBuffer) {
        GL40.glUniform1(n, doubleBuffer);
    }
    
    public static void glUniform2(final int n, final DoubleBuffer doubleBuffer) {
        GL40.glUniform2(n, doubleBuffer);
    }
    
    public static void glUniform3(final int n, final DoubleBuffer doubleBuffer) {
        GL40.glUniform3(n, doubleBuffer);
    }
    
    public static void glUniform4(final int n, final DoubleBuffer doubleBuffer) {
        GL40.glUniform4(n, doubleBuffer);
    }
    
    public static void glUniformMatrix2(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        GL40.glUniformMatrix2(n, b, doubleBuffer);
    }
    
    public static void glUniformMatrix3(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        GL40.glUniformMatrix3(n, b, doubleBuffer);
    }
    
    public static void glUniformMatrix4(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        GL40.glUniformMatrix4(n, b, doubleBuffer);
    }
    
    public static void glUniformMatrix2x3(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        GL40.glUniformMatrix2x3(n, b, doubleBuffer);
    }
    
    public static void glUniformMatrix2x4(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        GL40.glUniformMatrix2x4(n, b, doubleBuffer);
    }
    
    public static void glUniformMatrix3x2(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        GL40.glUniformMatrix3x2(n, b, doubleBuffer);
    }
    
    public static void glUniformMatrix3x4(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        GL40.glUniformMatrix3x4(n, b, doubleBuffer);
    }
    
    public static void glUniformMatrix4x2(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        GL40.glUniformMatrix4x2(n, b, doubleBuffer);
    }
    
    public static void glUniformMatrix4x3(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        GL40.glUniformMatrix4x3(n, b, doubleBuffer);
    }
    
    public static void glGetUniform(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        GL40.glGetUniform(n, n2, doubleBuffer);
    }
    
    public static void glProgramUniform1dEXT(final int n, final int n2, final double n3) {
        final long glProgramUniform1dEXT = GLContext.getCapabilities().glProgramUniform1dEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform1dEXT);
        nglProgramUniform1dEXT(n, n2, n3, glProgramUniform1dEXT);
    }
    
    static native void nglProgramUniform1dEXT(final int p0, final int p1, final double p2, final long p3);
    
    public static void glProgramUniform2dEXT(final int n, final int n2, final double n3, final double n4) {
        final long glProgramUniform2dEXT = GLContext.getCapabilities().glProgramUniform2dEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform2dEXT);
        nglProgramUniform2dEXT(n, n2, n3, n4, glProgramUniform2dEXT);
    }
    
    static native void nglProgramUniform2dEXT(final int p0, final int p1, final double p2, final double p3, final long p4);
    
    public static void glProgramUniform3dEXT(final int n, final int n2, final double n3, final double n4, final double n5) {
        final long glProgramUniform3dEXT = GLContext.getCapabilities().glProgramUniform3dEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform3dEXT);
        nglProgramUniform3dEXT(n, n2, n3, n4, n5, glProgramUniform3dEXT);
    }
    
    static native void nglProgramUniform3dEXT(final int p0, final int p1, final double p2, final double p3, final double p4, final long p5);
    
    public static void glProgramUniform4dEXT(final int n, final int n2, final double n3, final double n4, final double n5, final double n6) {
        final long glProgramUniform4dEXT = GLContext.getCapabilities().glProgramUniform4dEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform4dEXT);
        nglProgramUniform4dEXT(n, n2, n3, n4, n5, n6, glProgramUniform4dEXT);
    }
    
    static native void nglProgramUniform4dEXT(final int p0, final int p1, final double p2, final double p3, final double p4, final double p5, final long p6);
    
    public static void glProgramUniform1EXT(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glProgramUniform1dvEXT = GLContext.getCapabilities().glProgramUniform1dvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform1dvEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniform1dvEXT(n, n2, doubleBuffer.remaining(), MemoryUtil.getAddress(doubleBuffer), glProgramUniform1dvEXT);
    }
    
    static native void nglProgramUniform1dvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform2EXT(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glProgramUniform2dvEXT = GLContext.getCapabilities().glProgramUniform2dvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform2dvEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniform2dvEXT(n, n2, doubleBuffer.remaining() >> 1, MemoryUtil.getAddress(doubleBuffer), glProgramUniform2dvEXT);
    }
    
    static native void nglProgramUniform2dvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform3EXT(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glProgramUniform3dvEXT = GLContext.getCapabilities().glProgramUniform3dvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform3dvEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniform3dvEXT(n, n2, doubleBuffer.remaining() / 3, MemoryUtil.getAddress(doubleBuffer), glProgramUniform3dvEXT);
    }
    
    static native void nglProgramUniform3dvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform4EXT(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glProgramUniform4dvEXT = GLContext.getCapabilities().glProgramUniform4dvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniform4dvEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniform4dvEXT(n, n2, doubleBuffer.remaining() >> 2, MemoryUtil.getAddress(doubleBuffer), glProgramUniform4dvEXT);
    }
    
    static native void nglProgramUniform4dvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniformMatrix2EXT(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix2dvEXT = GLContext.getCapabilities().glProgramUniformMatrix2dvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix2dvEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix2dvEXT(n, n2, doubleBuffer.remaining() >> 2, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix2dvEXT);
    }
    
    static native void nglProgramUniformMatrix2dvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix3EXT(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix3dvEXT = GLContext.getCapabilities().glProgramUniformMatrix3dvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix3dvEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix3dvEXT(n, n2, doubleBuffer.remaining() / 9, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix3dvEXT);
    }
    
    static native void nglProgramUniformMatrix3dvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix4EXT(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix4dvEXT = GLContext.getCapabilities().glProgramUniformMatrix4dvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix4dvEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix4dvEXT(n, n2, doubleBuffer.remaining() >> 4, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix4dvEXT);
    }
    
    static native void nglProgramUniformMatrix4dvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix2x3EXT(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix2x3dvEXT = GLContext.getCapabilities().glProgramUniformMatrix2x3dvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix2x3dvEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix2x3dvEXT(n, n2, doubleBuffer.remaining() / 6, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix2x3dvEXT);
    }
    
    static native void nglProgramUniformMatrix2x3dvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix2x4EXT(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix2x4dvEXT = GLContext.getCapabilities().glProgramUniformMatrix2x4dvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix2x4dvEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix2x4dvEXT(n, n2, doubleBuffer.remaining() >> 3, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix2x4dvEXT);
    }
    
    static native void nglProgramUniformMatrix2x4dvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix3x2EXT(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix3x2dvEXT = GLContext.getCapabilities().glProgramUniformMatrix3x2dvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix3x2dvEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix3x2dvEXT(n, n2, doubleBuffer.remaining() / 6, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix3x2dvEXT);
    }
    
    static native void nglProgramUniformMatrix3x2dvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix3x4EXT(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix3x4dvEXT = GLContext.getCapabilities().glProgramUniformMatrix3x4dvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix3x4dvEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix3x4dvEXT(n, n2, doubleBuffer.remaining() / 12, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix3x4dvEXT);
    }
    
    static native void nglProgramUniformMatrix3x4dvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix4x2EXT(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix4x2dvEXT = GLContext.getCapabilities().glProgramUniformMatrix4x2dvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix4x2dvEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix4x2dvEXT(n, n2, doubleBuffer.remaining() >> 3, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix4x2dvEXT);
    }
    
    static native void nglProgramUniformMatrix4x2dvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix4x3EXT(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix4x3dvEXT = GLContext.getCapabilities().glProgramUniformMatrix4x3dvEXT;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix4x3dvEXT);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix4x3dvEXT(n, n2, doubleBuffer.remaining() / 12, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix4x3dvEXT);
    }
    
    static native void nglProgramUniformMatrix4x3dvEXT(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
}
