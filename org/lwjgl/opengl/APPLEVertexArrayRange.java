package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class APPLEVertexArrayRange
{
    public static final int GL_VERTEX_ARRAY_RANGE_APPLE = 34077;
    public static final int GL_VERTEX_ARRAY_RANGE_LENGTH_APPLE = 34078;
    public static final int GL_MAX_VERTEX_ARRAY_RANGE_ELEMENT_APPLE = 34080;
    public static final int GL_VERTEX_ARRAY_RANGE_POINTER_APPLE = 34081;
    public static final int GL_VERTEX_ARRAY_STORAGE_HINT_APPLE = 34079;
    public static final int GL_STORAGE_CACHED_APPLE = 34238;
    public static final int GL_STORAGE_SHARED_APPLE = 34239;
    public static final int GL_DRAW_PIXELS_APPLE = 35338;
    public static final int GL_FENCE_APPLE = 35339;
    
    private APPLEVertexArrayRange() {
    }
    
    public static void glVertexArrayRangeAPPLE(final ByteBuffer byteBuffer) {
        final long glVertexArrayRangeAPPLE = GLContext.getCapabilities().glVertexArrayRangeAPPLE;
        BufferChecks.checkFunctionAddress(glVertexArrayRangeAPPLE);
        BufferChecks.checkDirect(byteBuffer);
        nglVertexArrayRangeAPPLE(byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glVertexArrayRangeAPPLE);
    }
    
    static native void nglVertexArrayRangeAPPLE(final int p0, final long p1, final long p2);
    
    public static void glFlushVertexArrayRangeAPPLE(final ByteBuffer byteBuffer) {
        final long glFlushVertexArrayRangeAPPLE = GLContext.getCapabilities().glFlushVertexArrayRangeAPPLE;
        BufferChecks.checkFunctionAddress(glFlushVertexArrayRangeAPPLE);
        BufferChecks.checkDirect(byteBuffer);
        nglFlushVertexArrayRangeAPPLE(byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glFlushVertexArrayRangeAPPLE);
    }
    
    static native void nglFlushVertexArrayRangeAPPLE(final int p0, final long p1, final long p2);
    
    public static void glVertexArrayParameteriAPPLE(final int n, final int n2) {
        final long glVertexArrayParameteriAPPLE = GLContext.getCapabilities().glVertexArrayParameteriAPPLE;
        BufferChecks.checkFunctionAddress(glVertexArrayParameteriAPPLE);
        nglVertexArrayParameteriAPPLE(n, n2, glVertexArrayParameteriAPPLE);
    }
    
    static native void nglVertexArrayParameteriAPPLE(final int p0, final int p1, final long p2);
}
