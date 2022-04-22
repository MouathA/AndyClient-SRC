package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class ARBClearBufferObject
{
    private ARBClearBufferObject() {
    }
    
    public static void glClearBufferData(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        GL43.glClearBufferData(n, n2, n3, n4, byteBuffer);
    }
    
    public static void glClearBufferSubData(final int n, final int n2, final long n3, final long n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        GL43.glClearBufferSubData(n, n2, n3, n4, n5, n6, byteBuffer);
    }
    
    public static void glClearNamedBufferDataEXT(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        final long glClearNamedBufferDataEXT = GLContext.getCapabilities().glClearNamedBufferDataEXT;
        BufferChecks.checkFunctionAddress(glClearNamedBufferDataEXT);
        BufferChecks.checkBuffer(byteBuffer, 1);
        nglClearNamedBufferDataEXT(n, n2, n3, n4, MemoryUtil.getAddress(byteBuffer), glClearNamedBufferDataEXT);
    }
    
    static native void nglClearNamedBufferDataEXT(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glClearNamedBufferSubDataEXT(final int n, final int n2, final long n3, final long n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        final long glClearNamedBufferSubDataEXT = GLContext.getCapabilities().glClearNamedBufferSubDataEXT;
        BufferChecks.checkFunctionAddress(glClearNamedBufferSubDataEXT);
        BufferChecks.checkBuffer(byteBuffer, 1);
        nglClearNamedBufferSubDataEXT(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(byteBuffer), glClearNamedBufferSubDataEXT);
    }
    
    static native void nglClearNamedBufferSubDataEXT(final int p0, final int p1, final long p2, final long p3, final int p4, final int p5, final long p6, final long p7);
}
