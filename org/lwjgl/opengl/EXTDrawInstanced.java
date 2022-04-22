package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class EXTDrawInstanced
{
    private EXTDrawInstanced() {
    }
    
    public static void glDrawArraysInstancedEXT(final int n, final int n2, final int n3, final int n4) {
        final long glDrawArraysInstancedEXT = GLContext.getCapabilities().glDrawArraysInstancedEXT;
        BufferChecks.checkFunctionAddress(glDrawArraysInstancedEXT);
        nglDrawArraysInstancedEXT(n, n2, n3, n4, glDrawArraysInstancedEXT);
    }
    
    static native void nglDrawArraysInstancedEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glDrawElementsInstancedEXT(final int n, final ByteBuffer byteBuffer, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedEXT = capabilities.glDrawElementsInstancedEXT;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedEXT);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglDrawElementsInstancedEXT(n, byteBuffer.remaining(), 5121, MemoryUtil.getAddress(byteBuffer), n2, glDrawElementsInstancedEXT);
    }
    
    public static void glDrawElementsInstancedEXT(final int n, final IntBuffer intBuffer, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedEXT = capabilities.glDrawElementsInstancedEXT;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedEXT);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglDrawElementsInstancedEXT(n, intBuffer.remaining(), 5125, MemoryUtil.getAddress(intBuffer), n2, glDrawElementsInstancedEXT);
    }
    
    public static void glDrawElementsInstancedEXT(final int n, final ShortBuffer shortBuffer, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedEXT = capabilities.glDrawElementsInstancedEXT;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedEXT);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglDrawElementsInstancedEXT(n, shortBuffer.remaining(), 5123, MemoryUtil.getAddress(shortBuffer), n2, glDrawElementsInstancedEXT);
    }
    
    static native void nglDrawElementsInstancedEXT(final int p0, final int p1, final int p2, final long p3, final int p4, final long p5);
    
    public static void glDrawElementsInstancedEXT(final int n, final int n2, final int n3, final long n4, final int n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstancedEXT = capabilities.glDrawElementsInstancedEXT;
        BufferChecks.checkFunctionAddress(glDrawElementsInstancedEXT);
        GLChecks.ensureElementVBOenabled(capabilities);
        nglDrawElementsInstancedEXTBO(n, n2, n3, n4, n5, glDrawElementsInstancedEXT);
    }
    
    static native void nglDrawElementsInstancedEXTBO(final int p0, final int p1, final int p2, final long p3, final int p4, final long p5);
}
