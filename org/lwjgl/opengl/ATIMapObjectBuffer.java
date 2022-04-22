package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class ATIMapObjectBuffer
{
    private ATIMapObjectBuffer() {
    }
    
    public static ByteBuffer glMapObjectBufferATI(final int n, final ByteBuffer byteBuffer) {
        final long glMapObjectBufferATI = GLContext.getCapabilities().glMapObjectBufferATI;
        BufferChecks.checkFunctionAddress(glMapObjectBufferATI);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        final ByteBuffer nglMapObjectBufferATI = nglMapObjectBufferATI(n, ATIVertexArrayObject.glGetObjectBufferiATI(n, 34660), byteBuffer, glMapObjectBufferATI);
        return (LWJGLUtil.CHECKS && nglMapObjectBufferATI == null) ? null : nglMapObjectBufferATI.order(ByteOrder.nativeOrder());
    }
    
    public static ByteBuffer glMapObjectBufferATI(final int n, final long n2, final ByteBuffer byteBuffer) {
        final long glMapObjectBufferATI = GLContext.getCapabilities().glMapObjectBufferATI;
        BufferChecks.checkFunctionAddress(glMapObjectBufferATI);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        final ByteBuffer nglMapObjectBufferATI = nglMapObjectBufferATI(n, n2, byteBuffer, glMapObjectBufferATI);
        return (LWJGLUtil.CHECKS && nglMapObjectBufferATI == null) ? null : nglMapObjectBufferATI.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglMapObjectBufferATI(final int p0, final long p1, final ByteBuffer p2, final long p3);
    
    public static void glUnmapObjectBufferATI(final int n) {
        final long glUnmapObjectBufferATI = GLContext.getCapabilities().glUnmapObjectBufferATI;
        BufferChecks.checkFunctionAddress(glUnmapObjectBufferATI);
        nglUnmapObjectBufferATI(n, glUnmapObjectBufferATI);
    }
    
    static native void nglUnmapObjectBufferATI(final int p0, final long p1);
}
