package org.lwjgl.opengl;

import org.lwjgl.*;

public final class GREMEDYFrameTerminator
{
    private GREMEDYFrameTerminator() {
    }
    
    public static void glFrameTerminatorGREMEDY() {
        final long glFrameTerminatorGREMEDY = GLContext.getCapabilities().glFrameTerminatorGREMEDY;
        BufferChecks.checkFunctionAddress(glFrameTerminatorGREMEDY);
        nglFrameTerminatorGREMEDY(glFrameTerminatorGREMEDY);
    }
    
    static native void nglFrameTerminatorGREMEDY(final long p0);
}
