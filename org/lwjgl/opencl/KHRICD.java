package org.lwjgl.opencl;

import java.nio.*;
import org.lwjgl.*;

public final class KHRICD
{
    public static final int CL_PLATFORM_ICD_SUFFIX_KHR = 2336;
    public static final int CL_PLATFORM_NOT_FOUND_KHR = -1001;
    
    private KHRICD() {
    }
    
    public static int clIcdGetPlatformIDsKHR(final PointerBuffer pointerBuffer, final IntBuffer intBuffer) {
        final long clIcdGetPlatformIDsKHR = CLCapabilities.clIcdGetPlatformIDsKHR;
        BufferChecks.checkFunctionAddress(clIcdGetPlatformIDsKHR);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return nclIcdGetPlatformIDsKHR((pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(intBuffer), clIcdGetPlatformIDsKHR);
    }
    
    static native int nclIcdGetPlatformIDsKHR(final int p0, final long p1, final long p2, final long p3);
}
