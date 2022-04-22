package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class NVGpuShader5
{
    public static final int GL_INT64_NV = 5134;
    public static final int GL_UNSIGNED_INT64_NV = 5135;
    public static final int GL_INT8_NV = 36832;
    public static final int GL_INT8_VEC2_NV = 36833;
    public static final int GL_INT8_VEC3_NV = 36834;
    public static final int GL_INT8_VEC4_NV = 36835;
    public static final int GL_INT16_NV = 36836;
    public static final int GL_INT16_VEC2_NV = 36837;
    public static final int GL_INT16_VEC3_NV = 36838;
    public static final int GL_INT16_VEC4_NV = 36839;
    public static final int GL_INT64_VEC2_NV = 36841;
    public static final int GL_INT64_VEC3_NV = 36842;
    public static final int GL_INT64_VEC4_NV = 36843;
    public static final int GL_UNSIGNED_INT8_NV = 36844;
    public static final int GL_UNSIGNED_INT8_VEC2_NV = 36845;
    public static final int GL_UNSIGNED_INT8_VEC3_NV = 36846;
    public static final int GL_UNSIGNED_INT8_VEC4_NV = 36847;
    public static final int GL_UNSIGNED_INT16_NV = 36848;
    public static final int GL_UNSIGNED_INT16_VEC2_NV = 36849;
    public static final int GL_UNSIGNED_INT16_VEC3_NV = 36850;
    public static final int GL_UNSIGNED_INT16_VEC4_NV = 36851;
    public static final int GL_UNSIGNED_INT64_VEC2_NV = 36853;
    public static final int GL_UNSIGNED_INT64_VEC3_NV = 36854;
    public static final int GL_UNSIGNED_INT64_VEC4_NV = 36855;
    public static final int GL_FLOAT16_NV = 36856;
    public static final int GL_FLOAT16_VEC2_NV = 36857;
    public static final int GL_FLOAT16_VEC3_NV = 36858;
    public static final int GL_FLOAT16_VEC4_NV = 36859;
    public static final int GL_PATCHES = 14;
    
    private NVGpuShader5() {
    }
    
    public static void glUniform1i64NV(final int n, final long n2) {
        final long glUniform1i64NV = GLContext.getCapabilities().glUniform1i64NV;
        BufferChecks.checkFunctionAddress(glUniform1i64NV);
        nglUniform1i64NV(n, n2, glUniform1i64NV);
    }
    
    static native void nglUniform1i64NV(final int p0, final long p1, final long p2);
    
    public static void glUniform2i64NV(final int n, final long n2, final long n3) {
        final long glUniform2i64NV = GLContext.getCapabilities().glUniform2i64NV;
        BufferChecks.checkFunctionAddress(glUniform2i64NV);
        nglUniform2i64NV(n, n2, n3, glUniform2i64NV);
    }
    
    static native void nglUniform2i64NV(final int p0, final long p1, final long p2, final long p3);
    
    public static void glUniform3i64NV(final int n, final long n2, final long n3, final long n4) {
        final long glUniform3i64NV = GLContext.getCapabilities().glUniform3i64NV;
        BufferChecks.checkFunctionAddress(glUniform3i64NV);
        nglUniform3i64NV(n, n2, n3, n4, glUniform3i64NV);
    }
    
    static native void nglUniform3i64NV(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    public static void glUniform4i64NV(final int n, final long n2, final long n3, final long n4, final long n5) {
        final long glUniform4i64NV = GLContext.getCapabilities().glUniform4i64NV;
        BufferChecks.checkFunctionAddress(glUniform4i64NV);
        nglUniform4i64NV(n, n2, n3, n4, n5, glUniform4i64NV);
    }
    
    static native void nglUniform4i64NV(final int p0, final long p1, final long p2, final long p3, final long p4, final long p5);
    
    public static void glUniform1NV(final int n, final LongBuffer longBuffer) {
        final long glUniform1i64vNV = GLContext.getCapabilities().glUniform1i64vNV;
        BufferChecks.checkFunctionAddress(glUniform1i64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglUniform1i64vNV(n, longBuffer.remaining(), MemoryUtil.getAddress(longBuffer), glUniform1i64vNV);
    }
    
    static native void nglUniform1i64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform2NV(final int n, final LongBuffer longBuffer) {
        final long glUniform2i64vNV = GLContext.getCapabilities().glUniform2i64vNV;
        BufferChecks.checkFunctionAddress(glUniform2i64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglUniform2i64vNV(n, longBuffer.remaining() >> 1, MemoryUtil.getAddress(longBuffer), glUniform2i64vNV);
    }
    
    static native void nglUniform2i64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform3NV(final int n, final LongBuffer longBuffer) {
        final long glUniform3i64vNV = GLContext.getCapabilities().glUniform3i64vNV;
        BufferChecks.checkFunctionAddress(glUniform3i64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglUniform3i64vNV(n, longBuffer.remaining() / 3, MemoryUtil.getAddress(longBuffer), glUniform3i64vNV);
    }
    
    static native void nglUniform3i64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform4NV(final int n, final LongBuffer longBuffer) {
        final long glUniform4i64vNV = GLContext.getCapabilities().glUniform4i64vNV;
        BufferChecks.checkFunctionAddress(glUniform4i64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglUniform4i64vNV(n, longBuffer.remaining() >> 2, MemoryUtil.getAddress(longBuffer), glUniform4i64vNV);
    }
    
    static native void nglUniform4i64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform1ui64NV(final int n, final long n2) {
        final long glUniform1ui64NV = GLContext.getCapabilities().glUniform1ui64NV;
        BufferChecks.checkFunctionAddress(glUniform1ui64NV);
        nglUniform1ui64NV(n, n2, glUniform1ui64NV);
    }
    
    static native void nglUniform1ui64NV(final int p0, final long p1, final long p2);
    
    public static void glUniform2ui64NV(final int n, final long n2, final long n3) {
        final long glUniform2ui64NV = GLContext.getCapabilities().glUniform2ui64NV;
        BufferChecks.checkFunctionAddress(glUniform2ui64NV);
        nglUniform2ui64NV(n, n2, n3, glUniform2ui64NV);
    }
    
    static native void nglUniform2ui64NV(final int p0, final long p1, final long p2, final long p3);
    
    public static void glUniform3ui64NV(final int n, final long n2, final long n3, final long n4) {
        final long glUniform3ui64NV = GLContext.getCapabilities().glUniform3ui64NV;
        BufferChecks.checkFunctionAddress(glUniform3ui64NV);
        nglUniform3ui64NV(n, n2, n3, n4, glUniform3ui64NV);
    }
    
    static native void nglUniform3ui64NV(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    public static void glUniform4ui64NV(final int n, final long n2, final long n3, final long n4, final long n5) {
        final long glUniform4ui64NV = GLContext.getCapabilities().glUniform4ui64NV;
        BufferChecks.checkFunctionAddress(glUniform4ui64NV);
        nglUniform4ui64NV(n, n2, n3, n4, n5, glUniform4ui64NV);
    }
    
    static native void nglUniform4ui64NV(final int p0, final long p1, final long p2, final long p3, final long p4, final long p5);
    
    public static void glUniform1uNV(final int n, final LongBuffer longBuffer) {
        final long glUniform1ui64vNV = GLContext.getCapabilities().glUniform1ui64vNV;
        BufferChecks.checkFunctionAddress(glUniform1ui64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglUniform1ui64vNV(n, longBuffer.remaining(), MemoryUtil.getAddress(longBuffer), glUniform1ui64vNV);
    }
    
    static native void nglUniform1ui64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform2uNV(final int n, final LongBuffer longBuffer) {
        final long glUniform2ui64vNV = GLContext.getCapabilities().glUniform2ui64vNV;
        BufferChecks.checkFunctionAddress(glUniform2ui64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglUniform2ui64vNV(n, longBuffer.remaining() >> 1, MemoryUtil.getAddress(longBuffer), glUniform2ui64vNV);
    }
    
    static native void nglUniform2ui64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform3uNV(final int n, final LongBuffer longBuffer) {
        final long glUniform3ui64vNV = GLContext.getCapabilities().glUniform3ui64vNV;
        BufferChecks.checkFunctionAddress(glUniform3ui64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglUniform3ui64vNV(n, longBuffer.remaining() / 3, MemoryUtil.getAddress(longBuffer), glUniform3ui64vNV);
    }
    
    static native void nglUniform3ui64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform4uNV(final int n, final LongBuffer longBuffer) {
        final long glUniform4ui64vNV = GLContext.getCapabilities().glUniform4ui64vNV;
        BufferChecks.checkFunctionAddress(glUniform4ui64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglUniform4ui64vNV(n, longBuffer.remaining() >> 2, MemoryUtil.getAddress(longBuffer), glUniform4ui64vNV);
    }
    
    static native void nglUniform4ui64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetUniformNV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glGetUniformi64vNV = GLContext.getCapabilities().glGetUniformi64vNV;
        BufferChecks.checkFunctionAddress(glGetUniformi64vNV);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglGetUniformi64vNV(n, n2, MemoryUtil.getAddress(longBuffer), glGetUniformi64vNV);
    }
    
    static native void nglGetUniformi64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetUniformuNV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glGetUniformui64vNV = GLContext.getCapabilities().glGetUniformui64vNV;
        BufferChecks.checkFunctionAddress(glGetUniformui64vNV);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglGetUniformui64vNV(n, n2, MemoryUtil.getAddress(longBuffer), glGetUniformui64vNV);
    }
    
    static native void nglGetUniformui64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glProgramUniform1i64NV(final int n, final int n2, final long n3) {
        final long glProgramUniform1i64NV = GLContext.getCapabilities().glProgramUniform1i64NV;
        BufferChecks.checkFunctionAddress(glProgramUniform1i64NV);
        nglProgramUniform1i64NV(n, n2, n3, glProgramUniform1i64NV);
    }
    
    static native void nglProgramUniform1i64NV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glProgramUniform2i64NV(final int n, final int n2, final long n3, final long n4) {
        final long glProgramUniform2i64NV = GLContext.getCapabilities().glProgramUniform2i64NV;
        BufferChecks.checkFunctionAddress(glProgramUniform2i64NV);
        nglProgramUniform2i64NV(n, n2, n3, n4, glProgramUniform2i64NV);
    }
    
    static native void nglProgramUniform2i64NV(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static void glProgramUniform3i64NV(final int n, final int n2, final long n3, final long n4, final long n5) {
        final long glProgramUniform3i64NV = GLContext.getCapabilities().glProgramUniform3i64NV;
        BufferChecks.checkFunctionAddress(glProgramUniform3i64NV);
        nglProgramUniform3i64NV(n, n2, n3, n4, n5, glProgramUniform3i64NV);
    }
    
    static native void nglProgramUniform3i64NV(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static void glProgramUniform4i64NV(final int n, final int n2, final long n3, final long n4, final long n5, final long n6) {
        final long glProgramUniform4i64NV = GLContext.getCapabilities().glProgramUniform4i64NV;
        BufferChecks.checkFunctionAddress(glProgramUniform4i64NV);
        nglProgramUniform4i64NV(n, n2, n3, n4, n5, n6, glProgramUniform4i64NV);
    }
    
    static native void nglProgramUniform4i64NV(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5, final long p6);
    
    public static void glProgramUniform1NV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glProgramUniform1i64vNV = GLContext.getCapabilities().glProgramUniform1i64vNV;
        BufferChecks.checkFunctionAddress(glProgramUniform1i64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglProgramUniform1i64vNV(n, n2, longBuffer.remaining(), MemoryUtil.getAddress(longBuffer), glProgramUniform1i64vNV);
    }
    
    static native void nglProgramUniform1i64vNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform2NV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glProgramUniform2i64vNV = GLContext.getCapabilities().glProgramUniform2i64vNV;
        BufferChecks.checkFunctionAddress(glProgramUniform2i64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglProgramUniform2i64vNV(n, n2, longBuffer.remaining() >> 1, MemoryUtil.getAddress(longBuffer), glProgramUniform2i64vNV);
    }
    
    static native void nglProgramUniform2i64vNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform3NV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glProgramUniform3i64vNV = GLContext.getCapabilities().glProgramUniform3i64vNV;
        BufferChecks.checkFunctionAddress(glProgramUniform3i64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglProgramUniform3i64vNV(n, n2, longBuffer.remaining() / 3, MemoryUtil.getAddress(longBuffer), glProgramUniform3i64vNV);
    }
    
    static native void nglProgramUniform3i64vNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform4NV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glProgramUniform4i64vNV = GLContext.getCapabilities().glProgramUniform4i64vNV;
        BufferChecks.checkFunctionAddress(glProgramUniform4i64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglProgramUniform4i64vNV(n, n2, longBuffer.remaining() >> 2, MemoryUtil.getAddress(longBuffer), glProgramUniform4i64vNV);
    }
    
    static native void nglProgramUniform4i64vNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform1ui64NV(final int n, final int n2, final long n3) {
        final long glProgramUniform1ui64NV = GLContext.getCapabilities().glProgramUniform1ui64NV;
        BufferChecks.checkFunctionAddress(glProgramUniform1ui64NV);
        nglProgramUniform1ui64NV(n, n2, n3, glProgramUniform1ui64NV);
    }
    
    static native void nglProgramUniform1ui64NV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glProgramUniform2ui64NV(final int n, final int n2, final long n3, final long n4) {
        final long glProgramUniform2ui64NV = GLContext.getCapabilities().glProgramUniform2ui64NV;
        BufferChecks.checkFunctionAddress(glProgramUniform2ui64NV);
        nglProgramUniform2ui64NV(n, n2, n3, n4, glProgramUniform2ui64NV);
    }
    
    static native void nglProgramUniform2ui64NV(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static void glProgramUniform3ui64NV(final int n, final int n2, final long n3, final long n4, final long n5) {
        final long glProgramUniform3ui64NV = GLContext.getCapabilities().glProgramUniform3ui64NV;
        BufferChecks.checkFunctionAddress(glProgramUniform3ui64NV);
        nglProgramUniform3ui64NV(n, n2, n3, n4, n5, glProgramUniform3ui64NV);
    }
    
    static native void nglProgramUniform3ui64NV(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static void glProgramUniform4ui64NV(final int n, final int n2, final long n3, final long n4, final long n5, final long n6) {
        final long glProgramUniform4ui64NV = GLContext.getCapabilities().glProgramUniform4ui64NV;
        BufferChecks.checkFunctionAddress(glProgramUniform4ui64NV);
        nglProgramUniform4ui64NV(n, n2, n3, n4, n5, n6, glProgramUniform4ui64NV);
    }
    
    static native void nglProgramUniform4ui64NV(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5, final long p6);
    
    public static void glProgramUniform1uNV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glProgramUniform1ui64vNV = GLContext.getCapabilities().glProgramUniform1ui64vNV;
        BufferChecks.checkFunctionAddress(glProgramUniform1ui64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglProgramUniform1ui64vNV(n, n2, longBuffer.remaining(), MemoryUtil.getAddress(longBuffer), glProgramUniform1ui64vNV);
    }
    
    static native void nglProgramUniform1ui64vNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform2uNV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glProgramUniform2ui64vNV = GLContext.getCapabilities().glProgramUniform2ui64vNV;
        BufferChecks.checkFunctionAddress(glProgramUniform2ui64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglProgramUniform2ui64vNV(n, n2, longBuffer.remaining() >> 1, MemoryUtil.getAddress(longBuffer), glProgramUniform2ui64vNV);
    }
    
    static native void nglProgramUniform2ui64vNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform3uNV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glProgramUniform3ui64vNV = GLContext.getCapabilities().glProgramUniform3ui64vNV;
        BufferChecks.checkFunctionAddress(glProgramUniform3ui64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglProgramUniform3ui64vNV(n, n2, longBuffer.remaining() / 3, MemoryUtil.getAddress(longBuffer), glProgramUniform3ui64vNV);
    }
    
    static native void nglProgramUniform3ui64vNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform4uNV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glProgramUniform4ui64vNV = GLContext.getCapabilities().glProgramUniform4ui64vNV;
        BufferChecks.checkFunctionAddress(glProgramUniform4ui64vNV);
        BufferChecks.checkDirect(longBuffer);
        nglProgramUniform4ui64vNV(n, n2, longBuffer.remaining() >> 2, MemoryUtil.getAddress(longBuffer), glProgramUniform4ui64vNV);
    }
    
    static native void nglProgramUniform4ui64vNV(final int p0, final int p1, final int p2, final long p3, final long p4);
}
