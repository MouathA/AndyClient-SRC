package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class EXTGpuProgramParameters
{
    private EXTGpuProgramParameters() {
    }
    
    public static void glProgramEnvParameters4EXT(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glProgramEnvParameters4fvEXT = GLContext.getCapabilities().glProgramEnvParameters4fvEXT;
        BufferChecks.checkFunctionAddress(glProgramEnvParameters4fvEXT);
        BufferChecks.checkBuffer(floatBuffer, n3 << 2);
        nglProgramEnvParameters4fvEXT(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glProgramEnvParameters4fvEXT);
    }
    
    static native void nglProgramEnvParameters4fvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramLocalParameters4EXT(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glProgramLocalParameters4fvEXT = GLContext.getCapabilities().glProgramLocalParameters4fvEXT;
        BufferChecks.checkFunctionAddress(glProgramLocalParameters4fvEXT);
        BufferChecks.checkBuffer(floatBuffer, n3 << 2);
        nglProgramLocalParameters4fvEXT(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glProgramLocalParameters4fvEXT);
    }
    
    static native void nglProgramLocalParameters4fvEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
}
