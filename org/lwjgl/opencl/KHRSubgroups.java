package org.lwjgl.opencl;

import java.nio.*;
import org.lwjgl.*;

public final class KHRSubgroups
{
    private KHRSubgroups() {
    }
    
    public static int clGetKernelSubGroupInfoKHR(final CLKernel clKernel, final CLDevice clDevice, final int n, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final PointerBuffer pointerBuffer) {
        final long clGetKernelSubGroupInfoKHR = CLCapabilities.clGetKernelSubGroupInfoKHR;
        BufferChecks.checkFunctionAddress(clGetKernelSubGroupInfoKHR);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (byteBuffer2 != null) {
            BufferChecks.checkDirect(byteBuffer2);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, 1);
        }
        return nclGetKernelSubGroupInfoKHR(clKernel.getPointer(), (clDevice == null) ? 0L : clDevice.getPointer(), n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), (byteBuffer2 == null) ? 0 : byteBuffer2.remaining(), MemoryUtil.getAddressSafe(byteBuffer2), MemoryUtil.getAddressSafe(pointerBuffer), clGetKernelSubGroupInfoKHR);
    }
    
    static native int nclGetKernelSubGroupInfoKHR(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5, final long p6, final long p7, final long p8);
}
