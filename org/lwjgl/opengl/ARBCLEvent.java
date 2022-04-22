package org.lwjgl.opengl;

import org.lwjgl.opencl.*;
import org.lwjgl.*;

public final class ARBCLEvent
{
    public static final int GL_SYNC_CL_EVENT_ARB = 33344;
    public static final int GL_SYNC_CL_EVENT_COMPLETE_ARB = 33345;
    
    private ARBCLEvent() {
    }
    
    public static GLSync glCreateSyncFromCLeventARB(final CLContext clContext, final CLEvent clEvent, final int n) {
        final long glCreateSyncFromCLeventARB = GLContext.getCapabilities().glCreateSyncFromCLeventARB;
        BufferChecks.checkFunctionAddress(glCreateSyncFromCLeventARB);
        return new GLSync(nglCreateSyncFromCLeventARB(clContext.getPointer(), clEvent.getPointer(), n, glCreateSyncFromCLeventARB));
    }
    
    static native long nglCreateSyncFromCLeventARB(final long p0, final long p1, final int p2, final long p3);
}
