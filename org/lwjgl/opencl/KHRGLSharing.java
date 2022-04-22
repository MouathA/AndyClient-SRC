package org.lwjgl.opencl;

import java.nio.*;
import org.lwjgl.*;

public final class KHRGLSharing
{
    public static final int CL_INVALID_GL_SHAREGROUP_REFERENCE_KHR = -1000;
    public static final int CL_CURRENT_DEVICE_FOR_GL_CONTEXT_KHR = 8198;
    public static final int CL_DEVICES_FOR_GL_CONTEXT_KHR = 8199;
    public static final int CL_GL_CONTEXT_KHR = 8200;
    public static final int CL_EGL_DISPLAY_KHR = 8201;
    public static final int CL_GLX_DISPLAY_KHR = 8202;
    public static final int CL_WGL_HDC_KHR = 8203;
    public static final int CL_CGL_SHAREGROUP_KHR = 8204;
    
    private KHRGLSharing() {
    }
    
    public static int clGetGLContextInfoKHR(final PointerBuffer pointerBuffer, final int n, final ByteBuffer byteBuffer, PointerBuffer bufferPointer) {
        final long clGetGLContextInfoKHR = CLCapabilities.clGetGLContextInfoKHR;
        BufferChecks.checkFunctionAddress(clGetGLContextInfoKHR);
        BufferChecks.checkDirect(pointerBuffer);
        BufferChecks.checkNullTerminated(pointerBuffer);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (bufferPointer != null) {
            BufferChecks.checkBuffer(bufferPointer, 1);
        }
        if (bufferPointer == null && APIUtil.isDevicesParam(n)) {
            bufferPointer = APIUtil.getBufferPointer();
        }
        final int nclGetGLContextInfoKHR = nclGetGLContextInfoKHR(MemoryUtil.getAddress(pointerBuffer), n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(bufferPointer), clGetGLContextInfoKHR);
        if (nclGetGLContextInfoKHR == 0 && byteBuffer != null && APIUtil.isDevicesParam(n)) {
            APIUtil.getCLPlatform(pointerBuffer).registerCLDevices(byteBuffer, bufferPointer);
        }
        return nclGetGLContextInfoKHR;
    }
    
    static native int nclGetGLContextInfoKHR(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5);
}
