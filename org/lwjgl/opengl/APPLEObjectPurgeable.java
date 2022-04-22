package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class APPLEObjectPurgeable
{
    public static final int GL_RELEASED_APPLE = 35353;
    public static final int GL_VOLATILE_APPLE = 35354;
    public static final int GL_RETAINED_APPLE = 35355;
    public static final int GL_UNDEFINED_APPLE = 35356;
    public static final int GL_PURGEABLE_APPLE = 35357;
    public static final int GL_BUFFER_OBJECT_APPLE = 34227;
    
    private APPLEObjectPurgeable() {
    }
    
    public static int glObjectPurgeableAPPLE(final int n, final int n2, final int n3) {
        final long glObjectPurgeableAPPLE = GLContext.getCapabilities().glObjectPurgeableAPPLE;
        BufferChecks.checkFunctionAddress(glObjectPurgeableAPPLE);
        return nglObjectPurgeableAPPLE(n, n2, n3, glObjectPurgeableAPPLE);
    }
    
    static native int nglObjectPurgeableAPPLE(final int p0, final int p1, final int p2, final long p3);
    
    public static int glObjectUnpurgeableAPPLE(final int n, final int n2, final int n3) {
        final long glObjectUnpurgeableAPPLE = GLContext.getCapabilities().glObjectUnpurgeableAPPLE;
        BufferChecks.checkFunctionAddress(glObjectUnpurgeableAPPLE);
        return nglObjectUnpurgeableAPPLE(n, n2, n3, glObjectUnpurgeableAPPLE);
    }
    
    static native int nglObjectUnpurgeableAPPLE(final int p0, final int p1, final int p2, final long p3);
    
    public static void glGetObjectParameterAPPLE(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetObjectParameterivAPPLE = GLContext.getCapabilities().glGetObjectParameterivAPPLE;
        BufferChecks.checkFunctionAddress(glGetObjectParameterivAPPLE);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetObjectParameterivAPPLE(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetObjectParameterivAPPLE);
    }
    
    static native void nglGetObjectParameterivAPPLE(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetObjectParameteriAPPLE(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetObjectParameterivAPPLE = capabilities.glGetObjectParameterivAPPLE;
        BufferChecks.checkFunctionAddress(glGetObjectParameterivAPPLE);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetObjectParameterivAPPLE(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetObjectParameterivAPPLE);
        return bufferInt.get(0);
    }
}
