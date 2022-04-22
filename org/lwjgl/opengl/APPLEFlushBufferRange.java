package org.lwjgl.opengl;

import org.lwjgl.*;

public final class APPLEFlushBufferRange
{
    public static final int GL_BUFFER_SERIALIZED_MODIFY_APPLE = 35346;
    public static final int GL_BUFFER_FLUSHING_UNMAP_APPLE = 35347;
    
    private APPLEFlushBufferRange() {
    }
    
    public static void glBufferParameteriAPPLE(final int n, final int n2, final int n3) {
        final long glBufferParameteriAPPLE = GLContext.getCapabilities().glBufferParameteriAPPLE;
        BufferChecks.checkFunctionAddress(glBufferParameteriAPPLE);
        nglBufferParameteriAPPLE(n, n2, n3, glBufferParameteriAPPLE);
    }
    
    static native void nglBufferParameteriAPPLE(final int p0, final int p1, final int p2, final long p3);
    
    public static void glFlushMappedBufferRangeAPPLE(final int n, final long n2, final long n3) {
        final long glFlushMappedBufferRangeAPPLE = GLContext.getCapabilities().glFlushMappedBufferRangeAPPLE;
        BufferChecks.checkFunctionAddress(glFlushMappedBufferRangeAPPLE);
        nglFlushMappedBufferRangeAPPLE(n, n2, n3, glFlushMappedBufferRangeAPPLE);
    }
    
    static native void nglFlushMappedBufferRangeAPPLE(final int p0, final long p1, final long p2, final long p3);
}
