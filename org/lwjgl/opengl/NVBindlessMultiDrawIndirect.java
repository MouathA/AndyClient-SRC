package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class NVBindlessMultiDrawIndirect
{
    private NVBindlessMultiDrawIndirect() {
    }
    
    public static void glMultiDrawArraysIndirectBindlessNV(final int n, final ByteBuffer byteBuffer, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawArraysIndirectBindlessNV = capabilities.glMultiDrawArraysIndirectBindlessNV;
        BufferChecks.checkFunctionAddress(glMultiDrawArraysIndirectBindlessNV);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, ((n3 == 0) ? (20 + 24 * n4) : n3) * n2);
        nglMultiDrawArraysIndirectBindlessNV(n, MemoryUtil.getAddress(byteBuffer), n2, n3, n4, glMultiDrawArraysIndirectBindlessNV);
    }
    
    static native void nglMultiDrawArraysIndirectBindlessNV(final int p0, final long p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glMultiDrawArraysIndirectBindlessNV(final int n, final long n2, final int n3, final int n4, final int n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawArraysIndirectBindlessNV = capabilities.glMultiDrawArraysIndirectBindlessNV;
        BufferChecks.checkFunctionAddress(glMultiDrawArraysIndirectBindlessNV);
        GLChecks.ensureIndirectBOenabled(capabilities);
        nglMultiDrawArraysIndirectBindlessNVBO(n, n2, n3, n4, n5, glMultiDrawArraysIndirectBindlessNV);
    }
    
    static native void nglMultiDrawArraysIndirectBindlessNVBO(final int p0, final long p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glMultiDrawElementsIndirectBindlessNV(final int n, final int n2, final ByteBuffer byteBuffer, final int n3, final int n4, final int n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawElementsIndirectBindlessNV = capabilities.glMultiDrawElementsIndirectBindlessNV;
        BufferChecks.checkFunctionAddress(glMultiDrawElementsIndirectBindlessNV);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, ((n4 == 0) ? (48 + 24 * n5) : n4) * n3);
        nglMultiDrawElementsIndirectBindlessNV(n, n2, MemoryUtil.getAddress(byteBuffer), n3, n4, n5, glMultiDrawElementsIndirectBindlessNV);
    }
    
    static native void nglMultiDrawElementsIndirectBindlessNV(final int p0, final int p1, final long p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glMultiDrawElementsIndirectBindlessNV(final int n, final int n2, final long n3, final int n4, final int n5, final int n6) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawElementsIndirectBindlessNV = capabilities.glMultiDrawElementsIndirectBindlessNV;
        BufferChecks.checkFunctionAddress(glMultiDrawElementsIndirectBindlessNV);
        GLChecks.ensureIndirectBOenabled(capabilities);
        nglMultiDrawElementsIndirectBindlessNVBO(n, n2, n3, n4, n5, n6, glMultiDrawElementsIndirectBindlessNV);
    }
    
    static native void nglMultiDrawElementsIndirectBindlessNVBO(final int p0, final int p1, final long p2, final int p3, final int p4, final int p5, final long p6);
}
