package org.lwjgl.opencl;

import org.lwjgl.*;

public final class APPLESetMemObjectDestructor
{
    private APPLESetMemObjectDestructor() {
    }
    
    public static int clSetMemObjectDestructorAPPLE(final CLMem clMem, final CLMemObjectDestructorCallback clMemObjectDestructorCallback) {
        final long clSetMemObjectDestructorAPPLE = CLCapabilities.clSetMemObjectDestructorAPPLE;
        BufferChecks.checkFunctionAddress(clSetMemObjectDestructorAPPLE);
        final long globalRef = CallbackUtil.createGlobalRef(clMemObjectDestructorCallback);
        nclSetMemObjectDestructorAPPLE(clMem.getPointer(), clMemObjectDestructorCallback.getPointer(), globalRef, clSetMemObjectDestructorAPPLE);
        CallbackUtil.checkCallback(0, globalRef);
        return 0;
    }
    
    static native int nclSetMemObjectDestructorAPPLE(final long p0, final long p1, final long p2, final long p3);
}
