package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class EXTDrawRangeElements
{
    public static final int GL_MAX_ELEMENTS_VERTICES_EXT = 33000;
    public static final int GL_MAX_ELEMENTS_INDICES_EXT = 33001;
    
    private EXTDrawRangeElements() {
    }
    
    public static void glDrawRangeElementsEXT(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawRangeElementsEXT = capabilities.glDrawRangeElementsEXT;
        BufferChecks.checkFunctionAddress(glDrawRangeElementsEXT);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglDrawRangeElementsEXT(n, n2, n3, byteBuffer.remaining(), 5121, MemoryUtil.getAddress(byteBuffer), glDrawRangeElementsEXT);
    }
    
    public static void glDrawRangeElementsEXT(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawRangeElementsEXT = capabilities.glDrawRangeElementsEXT;
        BufferChecks.checkFunctionAddress(glDrawRangeElementsEXT);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglDrawRangeElementsEXT(n, n2, n3, intBuffer.remaining(), 5125, MemoryUtil.getAddress(intBuffer), glDrawRangeElementsEXT);
    }
    
    public static void glDrawRangeElementsEXT(final int n, final int n2, final int n3, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawRangeElementsEXT = capabilities.glDrawRangeElementsEXT;
        BufferChecks.checkFunctionAddress(glDrawRangeElementsEXT);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglDrawRangeElementsEXT(n, n2, n3, shortBuffer.remaining(), 5123, MemoryUtil.getAddress(shortBuffer), glDrawRangeElementsEXT);
    }
    
    static native void nglDrawRangeElementsEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glDrawRangeElementsEXT(final int n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawRangeElementsEXT = capabilities.glDrawRangeElementsEXT;
        BufferChecks.checkFunctionAddress(glDrawRangeElementsEXT);
        GLChecks.ensureElementVBOenabled(capabilities);
        nglDrawRangeElementsEXTBO(n, n2, n3, n4, n5, n6, glDrawRangeElementsEXT);
    }
    
    static native void nglDrawRangeElementsEXTBO(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
}
