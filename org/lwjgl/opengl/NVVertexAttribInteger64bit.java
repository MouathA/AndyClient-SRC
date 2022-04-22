package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class NVVertexAttribInteger64bit
{
    public static final int GL_INT64_NV = 5134;
    public static final int GL_UNSIGNED_INT64_NV = 5135;
    
    private NVVertexAttribInteger64bit() {
    }
    
    public static void glVertexAttribL1i64NV(final int n, final long n2) {
        final long glVertexAttribL1i64NV = GLContext.getCapabilities().glVertexAttribL1i64NV;
        BufferChecks.checkFunctionAddress(glVertexAttribL1i64NV);
        nglVertexAttribL1i64NV(n, n2, glVertexAttribL1i64NV);
    }
    
    static native void nglVertexAttribL1i64NV(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribL2i64NV(final int n, final long n2, final long n3) {
        final long glVertexAttribL2i64NV = GLContext.getCapabilities().glVertexAttribL2i64NV;
        BufferChecks.checkFunctionAddress(glVertexAttribL2i64NV);
        nglVertexAttribL2i64NV(n, n2, n3, glVertexAttribL2i64NV);
    }
    
    static native void nglVertexAttribL2i64NV(final int p0, final long p1, final long p2, final long p3);
    
    public static void glVertexAttribL3i64NV(final int n, final long n2, final long n3, final long n4) {
        final long glVertexAttribL3i64NV = GLContext.getCapabilities().glVertexAttribL3i64NV;
        BufferChecks.checkFunctionAddress(glVertexAttribL3i64NV);
        nglVertexAttribL3i64NV(n, n2, n3, n4, glVertexAttribL3i64NV);
    }
    
    static native void nglVertexAttribL3i64NV(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    public static void glVertexAttribL4i64NV(final int n, final long n2, final long n3, final long n4, final long n5) {
        final long glVertexAttribL4i64NV = GLContext.getCapabilities().glVertexAttribL4i64NV;
        BufferChecks.checkFunctionAddress(glVertexAttribL4i64NV);
        nglVertexAttribL4i64NV(n, n2, n3, n4, n5, glVertexAttribL4i64NV);
    }
    
    static native void nglVertexAttribL4i64NV(final int p0, final long p1, final long p2, final long p3, final long p4, final long p5);
    
    public static void glVertexAttribL1NV(final int n, final LongBuffer longBuffer) {
        final long glVertexAttribL1i64vNV = GLContext.getCapabilities().glVertexAttribL1i64vNV;
        BufferChecks.checkFunctionAddress(glVertexAttribL1i64vNV);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglVertexAttribL1i64vNV(n, MemoryUtil.getAddress(longBuffer), glVertexAttribL1i64vNV);
    }
    
    static native void nglVertexAttribL1i64vNV(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribL2NV(final int n, final LongBuffer longBuffer) {
        final long glVertexAttribL2i64vNV = GLContext.getCapabilities().glVertexAttribL2i64vNV;
        BufferChecks.checkFunctionAddress(glVertexAttribL2i64vNV);
        BufferChecks.checkBuffer(longBuffer, 2);
        nglVertexAttribL2i64vNV(n, MemoryUtil.getAddress(longBuffer), glVertexAttribL2i64vNV);
    }
    
    static native void nglVertexAttribL2i64vNV(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribL3NV(final int n, final LongBuffer longBuffer) {
        final long glVertexAttribL3i64vNV = GLContext.getCapabilities().glVertexAttribL3i64vNV;
        BufferChecks.checkFunctionAddress(glVertexAttribL3i64vNV);
        BufferChecks.checkBuffer(longBuffer, 3);
        nglVertexAttribL3i64vNV(n, MemoryUtil.getAddress(longBuffer), glVertexAttribL3i64vNV);
    }
    
    static native void nglVertexAttribL3i64vNV(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribL4NV(final int n, final LongBuffer longBuffer) {
        final long glVertexAttribL4i64vNV = GLContext.getCapabilities().glVertexAttribL4i64vNV;
        BufferChecks.checkFunctionAddress(glVertexAttribL4i64vNV);
        BufferChecks.checkBuffer(longBuffer, 4);
        nglVertexAttribL4i64vNV(n, MemoryUtil.getAddress(longBuffer), glVertexAttribL4i64vNV);
    }
    
    static native void nglVertexAttribL4i64vNV(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribL1ui64NV(final int n, final long n2) {
        final long glVertexAttribL1ui64NV = GLContext.getCapabilities().glVertexAttribL1ui64NV;
        BufferChecks.checkFunctionAddress(glVertexAttribL1ui64NV);
        nglVertexAttribL1ui64NV(n, n2, glVertexAttribL1ui64NV);
    }
    
    static native void nglVertexAttribL1ui64NV(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribL2ui64NV(final int n, final long n2, final long n3) {
        final long glVertexAttribL2ui64NV = GLContext.getCapabilities().glVertexAttribL2ui64NV;
        BufferChecks.checkFunctionAddress(glVertexAttribL2ui64NV);
        nglVertexAttribL2ui64NV(n, n2, n3, glVertexAttribL2ui64NV);
    }
    
    static native void nglVertexAttribL2ui64NV(final int p0, final long p1, final long p2, final long p3);
    
    public static void glVertexAttribL3ui64NV(final int n, final long n2, final long n3, final long n4) {
        final long glVertexAttribL3ui64NV = GLContext.getCapabilities().glVertexAttribL3ui64NV;
        BufferChecks.checkFunctionAddress(glVertexAttribL3ui64NV);
        nglVertexAttribL3ui64NV(n, n2, n3, n4, glVertexAttribL3ui64NV);
    }
    
    static native void nglVertexAttribL3ui64NV(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    public static void glVertexAttribL4ui64NV(final int n, final long n2, final long n3, final long n4, final long n5) {
        final long glVertexAttribL4ui64NV = GLContext.getCapabilities().glVertexAttribL4ui64NV;
        BufferChecks.checkFunctionAddress(glVertexAttribL4ui64NV);
        nglVertexAttribL4ui64NV(n, n2, n3, n4, n5, glVertexAttribL4ui64NV);
    }
    
    static native void nglVertexAttribL4ui64NV(final int p0, final long p1, final long p2, final long p3, final long p4, final long p5);
    
    public static void glVertexAttribL1uNV(final int n, final LongBuffer longBuffer) {
        final long glVertexAttribL1ui64vNV = GLContext.getCapabilities().glVertexAttribL1ui64vNV;
        BufferChecks.checkFunctionAddress(glVertexAttribL1ui64vNV);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglVertexAttribL1ui64vNV(n, MemoryUtil.getAddress(longBuffer), glVertexAttribL1ui64vNV);
    }
    
    static native void nglVertexAttribL1ui64vNV(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribL2uNV(final int n, final LongBuffer longBuffer) {
        final long glVertexAttribL2ui64vNV = GLContext.getCapabilities().glVertexAttribL2ui64vNV;
        BufferChecks.checkFunctionAddress(glVertexAttribL2ui64vNV);
        BufferChecks.checkBuffer(longBuffer, 2);
        nglVertexAttribL2ui64vNV(n, MemoryUtil.getAddress(longBuffer), glVertexAttribL2ui64vNV);
    }
    
    static native void nglVertexAttribL2ui64vNV(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribL3uNV(final int n, final LongBuffer longBuffer) {
        final long glVertexAttribL3ui64vNV = GLContext.getCapabilities().glVertexAttribL3ui64vNV;
        BufferChecks.checkFunctionAddress(glVertexAttribL3ui64vNV);
        BufferChecks.checkBuffer(longBuffer, 3);
        nglVertexAttribL3ui64vNV(n, MemoryUtil.getAddress(longBuffer), glVertexAttribL3ui64vNV);
    }
    
    static native void nglVertexAttribL3ui64vNV(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribL4uNV(final int n, final LongBuffer longBuffer) {
        final long glVertexAttribL4ui64vNV = GLContext.getCapabilities().glVertexAttribL4ui64vNV;
        BufferChecks.checkFunctionAddress(glVertexAttribL4ui64vNV);
        BufferChecks.checkBuffer(longBuffer, 4);
        nglVertexAttribL4ui64vNV(n, MemoryUtil.getAddress(longBuffer), glVertexAttribL4ui64vNV);
    }
    
    static native void nglVertexAttribL4ui64vNV(final int p0, final long p1, final long p2);
    
    public static void glGetVertexAttribLNV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glGetVertexAttribLi64vNV = GLContext.getCapabilities().glGetVertexAttribLi64vNV;
        BufferChecks.checkFunctionAddress(glGetVertexAttribLi64vNV);
        BufferChecks.checkBuffer(longBuffer, 4);
        nglGetVertexAttribLi64vNV(n, n2, MemoryUtil.getAddress(longBuffer), glGetVertexAttribLi64vNV);
    }
    
    static native void nglGetVertexAttribLi64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetVertexAttribLuNV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glGetVertexAttribLui64vNV = GLContext.getCapabilities().glGetVertexAttribLui64vNV;
        BufferChecks.checkFunctionAddress(glGetVertexAttribLui64vNV);
        BufferChecks.checkBuffer(longBuffer, 4);
        nglGetVertexAttribLui64vNV(n, n2, MemoryUtil.getAddress(longBuffer), glGetVertexAttribLui64vNV);
    }
    
    static native void nglGetVertexAttribLui64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVertexAttribLFormatNV(final int n, final int n2, final int n3, final int n4) {
        final long glVertexAttribLFormatNV = GLContext.getCapabilities().glVertexAttribLFormatNV;
        BufferChecks.checkFunctionAddress(glVertexAttribLFormatNV);
        nglVertexAttribLFormatNV(n, n2, n3, n4, glVertexAttribLFormatNV);
    }
    
    static native void nglVertexAttribLFormatNV(final int p0, final int p1, final int p2, final int p3, final long p4);
}
