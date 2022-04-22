package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class EXTTimerQuery
{
    public static final int GL_TIME_ELAPSED_EXT = 35007;
    
    private EXTTimerQuery() {
    }
    
    public static void glGetQueryObjectEXT(final int n, final int n2, final LongBuffer longBuffer) {
        final long glGetQueryObjecti64vEXT = GLContext.getCapabilities().glGetQueryObjecti64vEXT;
        BufferChecks.checkFunctionAddress(glGetQueryObjecti64vEXT);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglGetQueryObjecti64vEXT(n, n2, MemoryUtil.getAddress(longBuffer), glGetQueryObjecti64vEXT);
    }
    
    static native void nglGetQueryObjecti64vEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static long glGetQueryObjectEXT(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetQueryObjecti64vEXT = capabilities.glGetQueryObjecti64vEXT;
        BufferChecks.checkFunctionAddress(glGetQueryObjecti64vEXT);
        final LongBuffer bufferLong = APIUtil.getBufferLong(capabilities);
        nglGetQueryObjecti64vEXT(n, n2, MemoryUtil.getAddress(bufferLong), glGetQueryObjecti64vEXT);
        return bufferLong.get(0);
    }
    
    public static void glGetQueryObjectuEXT(final int n, final int n2, final LongBuffer longBuffer) {
        final long glGetQueryObjectui64vEXT = GLContext.getCapabilities().glGetQueryObjectui64vEXT;
        BufferChecks.checkFunctionAddress(glGetQueryObjectui64vEXT);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglGetQueryObjectui64vEXT(n, n2, MemoryUtil.getAddress(longBuffer), glGetQueryObjectui64vEXT);
    }
    
    static native void nglGetQueryObjectui64vEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static long glGetQueryObjectuEXT(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetQueryObjectui64vEXT = capabilities.glGetQueryObjectui64vEXT;
        BufferChecks.checkFunctionAddress(glGetQueryObjectui64vEXT);
        final LongBuffer bufferLong = APIUtil.getBufferLong(capabilities);
        nglGetQueryObjectui64vEXT(n, n2, MemoryUtil.getAddress(bufferLong), glGetQueryObjectui64vEXT);
        return bufferLong.get(0);
    }
}
