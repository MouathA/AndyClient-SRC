package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class ARBDrawInstanced
{
    private ARBDrawInstanced() {
    }
    
    public static void glDrawArraysInstancedARB(final int n, final int n2, final int n3, final int n4) {
        final long glDrawArraysInstancedARB = GLContext.getCapabilities().glDrawArraysInstancedARB;
        BufferChecks.checkFunctionAddress(glDrawArraysInstancedARB);
        nglDrawArraysInstancedARB(n, n2, n3, n4, glDrawArraysInstancedARB);
    }
    
    static native void nglDrawArraysInstancedARB(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glDrawElementsInstancedARB(final int n, final ByteBuffer byteBuffer, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedARB = capabilities.glDrawElementsInstancedARB;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedARB);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglDrawElementsInstancedARB(n, byteBuffer.remaining(), 5121, MemoryUtil.getAddress(byteBuffer), n2, glDrawElementsInstancedARB);
    }
    
    public static void glDrawElementsInstancedARB(final int n, final IntBuffer intBuffer, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedARB = capabilities.glDrawElementsInstancedARB;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedARB);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglDrawElementsInstancedARB(n, intBuffer.remaining(), 5125, MemoryUtil.getAddress(intBuffer), n2, glDrawElementsInstancedARB);
    }
    
    public static void glDrawElementsInstancedARB(final int n, final ShortBuffer shortBuffer, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedARB = capabilities.glDrawElementsInstancedARB;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedARB);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglDrawElementsInstancedARB(n, shortBuffer.remaining(), 5123, MemoryUtil.getAddress(shortBuffer), n2, glDrawElementsInstancedARB);
    }
    
    static native void nglDrawElementsInstancedARB(final int p0, final int p1, final int p2, final long p3, final int p4, final long p5);
    
    public static void glDrawElementsInstancedARB(final int n, final int n2, final int n3, final long n4, final int n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedARB = capabilities.glDrawElementsInstancedARB;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedARB);
        GLChecks.ensureElementVBOenabled(capabilities);
        nglDrawElementsInstancedARBBO(n, n2, n3, n4, n5, glDrawElementsInstancedARB);
    }
    
    static native void nglDrawElementsInstancedARBBO(final int p0, final int p1, final int p2, final long p3, final int p4, final long p5);
}
