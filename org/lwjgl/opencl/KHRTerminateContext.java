package org.lwjgl.opencl;

import org.lwjgl.*;

public final class KHRTerminateContext
{
    public static final int CL_DEVICE_TERMINATE_CAPABILITY_KHR = 8207;
    public static final int CL_CONTEXT_TERMINATE_KHR = 8208;
    
    private KHRTerminateContext() {
    }
    
    public static int clTerminateContextKHR(final CLContext clContext) {
        final long clTerminateContextKHR = CLCapabilities.clTerminateContextKHR;
        BufferChecks.checkFunctionAddress(clTerminateContextKHR);
        return nclTerminateContextKHR(clContext.getPointer(), clTerminateContextKHR);
    }
    
    static native int nclTerminateContextKHR(final long p0, final long p1);
}
