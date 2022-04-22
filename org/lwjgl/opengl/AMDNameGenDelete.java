package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class AMDNameGenDelete
{
    public static final int GL_DATA_BUFFER_AMD = 37201;
    public static final int GL_PERFORMANCE_MONITOR_AMD = 37202;
    public static final int GL_QUERY_OBJECT_AMD = 37203;
    public static final int GL_VERTEX_ARRAY_OBJECT_AMD = 37204;
    public static final int GL_SAMPLER_OBJECT_AMD = 37205;
    
    private AMDNameGenDelete() {
    }
    
    public static void glGenNamesAMD(final int n, final IntBuffer intBuffer) {
        final long glGenNamesAMD = GLContext.getCapabilities().glGenNamesAMD;
        BufferChecks.checkFunctionAddress(glGenNamesAMD);
        BufferChecks.checkDirect(intBuffer);
        nglGenNamesAMD(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenNamesAMD);
    }
    
    static native void nglGenNamesAMD(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGenNamesAMD(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenNamesAMD = capabilities.glGenNamesAMD;
        BufferChecks.checkFunctionAddress(glGenNamesAMD);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenNamesAMD(n, 1, MemoryUtil.getAddress(bufferInt), glGenNamesAMD);
        return bufferInt.get(0);
    }
    
    public static void glDeleteNamesAMD(final int n, final IntBuffer intBuffer) {
        final long glDeleteNamesAMD = GLContext.getCapabilities().glDeleteNamesAMD;
        BufferChecks.checkFunctionAddress(glDeleteNamesAMD);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteNamesAMD(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteNamesAMD);
    }
    
    static native void nglDeleteNamesAMD(final int p0, final int p1, final long p2, final long p3);
    
    public static void glDeleteNamesAMD(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteNamesAMD = capabilities.glDeleteNamesAMD;
        BufferChecks.checkFunctionAddress(glDeleteNamesAMD);
        nglDeleteNamesAMD(n, 1, APIUtil.getInt(capabilities, n2), glDeleteNamesAMD);
    }
    
    public static boolean glIsNameAMD(final int n, final int n2) {
        final long glIsNameAMD = GLContext.getCapabilities().glIsNameAMD;
        BufferChecks.checkFunctionAddress(glIsNameAMD);
        return nglIsNameAMD(n, n2, glIsNameAMD);
    }
    
    static native boolean nglIsNameAMD(final int p0, final int p1, final long p2);
}
