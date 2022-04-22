package org.lwjgl.opencl;

import org.lwjgl.opengl.*;
import java.nio.*;
import org.lwjgl.*;

public final class KHRGLEvent
{
    public static final int CL_COMMAND_GL_FENCE_SYNC_OBJECT_KHR = 8205;
    
    private KHRGLEvent() {
    }
    
    public static CLEvent clCreateEventFromGLsyncKHR(final CLContext clContext, final GLSync glSync, final IntBuffer intBuffer) {
        final long clCreateEventFromGLsyncKHR = CLCapabilities.clCreateEventFromGLsyncKHR;
        BufferChecks.checkFunctionAddress(clCreateEventFromGLsyncKHR);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLEvent(nclCreateEventFromGLsyncKHR(clContext.getPointer(), glSync.getPointer(), MemoryUtil.getAddressSafe(intBuffer), clCreateEventFromGLsyncKHR), clContext);
    }
    
    static native long nclCreateEventFromGLsyncKHR(final long p0, final long p1, final long p2, final long p3);
}
