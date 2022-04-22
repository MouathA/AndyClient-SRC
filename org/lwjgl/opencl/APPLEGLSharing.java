package org.lwjgl.opencl;

import java.nio.*;
import org.lwjgl.*;

public final class APPLEGLSharing
{
    public static final int CL_CONTEXT_PROPERTY_USE_CGL_SHAREGROUP_APPLE = 268435456;
    public static final int CL_CGL_DEVICE_FOR_CURRENT_VIRTUAL_SCREEN_APPLE = 268435458;
    public static final int CL_CGL_DEVICES_FOR_SUPPORTED_VIRTUAL_SCREENS_APPLE = 268435459;
    public static final int CL_INVALID_GL_CONTEXT_APPLE = -1000;
    
    private APPLEGLSharing() {
    }
    
    public static int clGetGLContextInfoAPPLE(final CLContext clContext, final PointerBuffer pointerBuffer, final int n, final ByteBuffer byteBuffer, PointerBuffer bufferPointer) {
        final long clGetGLContextInfoAPPLE = CLCapabilities.clGetGLContextInfoAPPLE;
        BufferChecks.checkFunctionAddress(clGetGLContextInfoAPPLE);
        BufferChecks.checkBuffer(pointerBuffer, 1);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (bufferPointer != null) {
            BufferChecks.checkBuffer(bufferPointer, 1);
        }
        if (bufferPointer == null && APIUtil.isDevicesParam(n)) {
            bufferPointer = APIUtil.getBufferPointer();
        }
        final int nclGetGLContextInfoAPPLE = nclGetGLContextInfoAPPLE(clContext.getPointer(), MemoryUtil.getAddress(pointerBuffer), n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(bufferPointer), clGetGLContextInfoAPPLE);
        if (nclGetGLContextInfoAPPLE == 0 && byteBuffer != null && APIUtil.isDevicesParam(n)) {
            ((CLPlatform)clContext.getParent()).registerCLDevices(byteBuffer, bufferPointer);
        }
        return nclGetGLContextInfoAPPLE;
    }
    
    static native int nclGetGLContextInfoAPPLE(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5, final long p6);
}
