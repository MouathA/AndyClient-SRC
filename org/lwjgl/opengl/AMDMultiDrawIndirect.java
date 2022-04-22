package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class AMDMultiDrawIndirect
{
    private AMDMultiDrawIndirect() {
    }
    
    public static void glMultiDrawArraysIndirectAMD(final int n, final ByteBuffer byteBuffer, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawArraysIndirectAMD = capabilities.glMultiDrawArraysIndirectAMD;
        BufferChecks.checkFunctionAddress(glMultiDrawArraysIndirectAMD);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, ((n3 == 0) ? 16 : n3) * n2);
        nglMultiDrawArraysIndirectAMD(n, MemoryUtil.getAddress(byteBuffer), n2, n3, glMultiDrawArraysIndirectAMD);
    }
    
    static native void nglMultiDrawArraysIndirectAMD(final int p0, final long p1, final int p2, final int p3, final long p4);
    
    public static void glMultiDrawArraysIndirectAMD(final int n, final long n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawArraysIndirectAMD = capabilities.glMultiDrawArraysIndirectAMD;
        BufferChecks.checkFunctionAddress(glMultiDrawArraysIndirectAMD);
        GLChecks.ensureIndirectBOenabled(capabilities);
        nglMultiDrawArraysIndirectAMDBO(n, n2, n3, n4, glMultiDrawArraysIndirectAMD);
    }
    
    static native void nglMultiDrawArraysIndirectAMDBO(final int p0, final long p1, final int p2, final int p3, final long p4);
    
    public static void glMultiDrawArraysIndirectAMD(final int n, final IntBuffer intBuffer, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawArraysIndirectAMD = capabilities.glMultiDrawArraysIndirectAMD;
        BufferChecks.checkFunctionAddress(glMultiDrawArraysIndirectAMD);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, ((n3 == 0) ? 4 : (n3 >> 2)) * n2);
        nglMultiDrawArraysIndirectAMD(n, MemoryUtil.getAddress(intBuffer), n2, n3, glMultiDrawArraysIndirectAMD);
    }
    
    public static void glMultiDrawElementsIndirectAMD(final int n, final int n2, final ByteBuffer byteBuffer, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawElementsIndirectAMD = capabilities.glMultiDrawElementsIndirectAMD;
        BufferChecks.checkFunctionAddress(glMultiDrawElementsIndirectAMD);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, ((n4 == 0) ? 20 : n4) * n3);
        nglMultiDrawElementsIndirectAMD(n, n2, MemoryUtil.getAddress(byteBuffer), n3, n4, glMultiDrawElementsIndirectAMD);
    }
    
    static native void nglMultiDrawElementsIndirectAMD(final int p0, final int p1, final long p2, final int p3, final int p4, final long p5);
    
    public static void glMultiDrawElementsIndirectAMD(final int n, final int n2, final long n3, final int n4, final int n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawElementsIndirectAMD = capabilities.glMultiDrawElementsIndirectAMD;
        BufferChecks.checkFunctionAddress(glMultiDrawElementsIndirectAMD);
        GLChecks.ensureIndirectBOenabled(capabilities);
        nglMultiDrawElementsIndirectAMDBO(n, n2, n3, n4, n5, glMultiDrawElementsIndirectAMD);
    }
    
    static native void nglMultiDrawElementsIndirectAMDBO(final int p0, final int p1, final long p2, final int p3, final int p4, final long p5);
    
    public static void glMultiDrawElementsIndirectAMD(final int n, final int n2, final IntBuffer intBuffer, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawElementsIndirectAMD = capabilities.glMultiDrawElementsIndirectAMD;
        BufferChecks.checkFunctionAddress(glMultiDrawElementsIndirectAMD);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, ((n4 == 0) ? 5 : (n4 >> 2)) * n3);
        nglMultiDrawElementsIndirectAMD(n, n2, MemoryUtil.getAddress(intBuffer), n3, n4, glMultiDrawElementsIndirectAMD);
    }
}
