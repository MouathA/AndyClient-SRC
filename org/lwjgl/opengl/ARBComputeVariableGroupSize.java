package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ARBComputeVariableGroupSize
{
    public static final int GL_MAX_COMPUTE_VARIABLE_GROUP_INVOCATIONS_ARB = 37700;
    public static final int GL_MAX_COMPUTE_FIXED_GROUP_INVOCATIONS_ARB = 37099;
    public static final int GL_MAX_COMPUTE_VARIABLE_GROUP_SIZE_ARB = 37701;
    public static final int GL_MAX_COMPUTE_FIXED_GROUP_SIZE_ARB = 37311;
    
    private ARBComputeVariableGroupSize() {
    }
    
    public static void glDispatchComputeGroupSizeARB(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glDispatchComputeGroupSizeARB = GLContext.getCapabilities().glDispatchComputeGroupSizeARB;
        BufferChecks.checkFunctionAddress(glDispatchComputeGroupSizeARB);
        nglDispatchComputeGroupSizeARB(n, n2, n3, n4, n5, n6, glDispatchComputeGroupSizeARB);
    }
    
    static native void nglDispatchComputeGroupSizeARB(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
}
