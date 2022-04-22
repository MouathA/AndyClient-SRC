package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class ARBIndirectParameters
{
    public static final int GL_PARAMETER_BUFFER_ARB = 33006;
    public static final int GL_PARAMETER_BUFFER_BINDING_ARB = 33007;
    
    private ARBIndirectParameters() {
    }
    
    public static void glMultiDrawArraysIndirectCountARB(final int n, final ByteBuffer byteBuffer, final long n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawArraysIndirectCountARB = capabilities.glMultiDrawArraysIndirectCountARB;
        BufferChecks.checkFunctionAddress(glMultiDrawArraysIndirectCountARB);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, ((n4 == 0) ? 16 : n4) * n3);
        nglMultiDrawArraysIndirectCountARB(n, MemoryUtil.getAddress(byteBuffer), n2, n3, n4, glMultiDrawArraysIndirectCountARB);
    }
    
    static native void nglMultiDrawArraysIndirectCountARB(final int p0, final long p1, final long p2, final int p3, final int p4, final long p5);
    
    public static void glMultiDrawArraysIndirectCountARB(final int n, final long n2, final long n3, final int n4, final int n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawArraysIndirectCountARB = capabilities.glMultiDrawArraysIndirectCountARB;
        BufferChecks.checkFunctionAddress(glMultiDrawArraysIndirectCountARB);
        GLChecks.ensureIndirectBOenabled(capabilities);
        nglMultiDrawArraysIndirectCountARBBO(n, n2, n3, n4, n5, glMultiDrawArraysIndirectCountARB);
    }
    
    static native void nglMultiDrawArraysIndirectCountARBBO(final int p0, final long p1, final long p2, final int p3, final int p4, final long p5);
    
    public static void glMultiDrawArraysIndirectCountARB(final int n, final IntBuffer intBuffer, final long n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawArraysIndirectCountARB = capabilities.glMultiDrawArraysIndirectCountARB;
        BufferChecks.checkFunctionAddress(glMultiDrawArraysIndirectCountARB);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, ((n4 == 0) ? 4 : (n4 >> 2)) * n3);
        nglMultiDrawArraysIndirectCountARB(n, MemoryUtil.getAddress(intBuffer), n2, n3, n4, glMultiDrawArraysIndirectCountARB);
    }
    
    public static void glMultiDrawElementsIndirectCountARB(final int n, final int n2, final ByteBuffer byteBuffer, final long n3, final int n4, final int n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawElementsIndirectCountARB = capabilities.glMultiDrawElementsIndirectCountARB;
        BufferChecks.checkFunctionAddress(glMultiDrawElementsIndirectCountARB);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, ((n5 == 0) ? 20 : n5) * n4);
        nglMultiDrawElementsIndirectCountARB(n, n2, MemoryUtil.getAddress(byteBuffer), n3, n4, n5, glMultiDrawElementsIndirectCountARB);
    }
    
    static native void nglMultiDrawElementsIndirectCountARB(final int p0, final int p1, final long p2, final long p3, final int p4, final int p5, final long p6);
    
    public static void glMultiDrawElementsIndirectCountARB(final int n, final int n2, final long n3, final long n4, final int n5, final int n6) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawElementsIndirectCountARB = capabilities.glMultiDrawElementsIndirectCountARB;
        BufferChecks.checkFunctionAddress(glMultiDrawElementsIndirectCountARB);
        GLChecks.ensureIndirectBOenabled(capabilities);
        nglMultiDrawElementsIndirectCountARBBO(n, n2, n3, n4, n5, n6, glMultiDrawElementsIndirectCountARB);
    }
    
    static native void nglMultiDrawElementsIndirectCountARBBO(final int p0, final int p1, final long p2, final long p3, final int p4, final int p5, final long p6);
    
    public static void glMultiDrawElementsIndirectCountARB(final int n, final int n2, final IntBuffer intBuffer, final long n3, final int n4, final int n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawElementsIndirectCountARB = capabilities.glMultiDrawElementsIndirectCountARB;
        BufferChecks.checkFunctionAddress(glMultiDrawElementsIndirectCountARB);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, ((n5 == 0) ? 5 : (n5 >> 2)) * n4);
        nglMultiDrawElementsIndirectCountARB(n, n2, MemoryUtil.getAddress(intBuffer), n3, n4, n5, glMultiDrawElementsIndirectCountARB);
    }
}
