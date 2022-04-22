package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class EXTSeparateShaderObjects
{
    public static final int GL_ACTIVE_PROGRAM_EXT = 35725;
    
    private EXTSeparateShaderObjects() {
    }
    
    public static void glUseShaderProgramEXT(final int n, final int n2) {
        final long glUseShaderProgramEXT = GLContext.getCapabilities().glUseShaderProgramEXT;
        BufferChecks.checkFunctionAddress(glUseShaderProgramEXT);
        nglUseShaderProgramEXT(n, n2, glUseShaderProgramEXT);
    }
    
    static native void nglUseShaderProgramEXT(final int p0, final int p1, final long p2);
    
    public static void glActiveProgramEXT(final int n) {
        final long glActiveProgramEXT = GLContext.getCapabilities().glActiveProgramEXT;
        BufferChecks.checkFunctionAddress(glActiveProgramEXT);
        nglActiveProgramEXT(n, glActiveProgramEXT);
    }
    
    static native void nglActiveProgramEXT(final int p0, final long p1);
    
    public static int glCreateShaderProgramEXT(final int n, final ByteBuffer byteBuffer) {
        final long glCreateShaderProgramEXT = GLContext.getCapabilities().glCreateShaderProgramEXT;
        BufferChecks.checkFunctionAddress(glCreateShaderProgramEXT);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        return nglCreateShaderProgramEXT(n, MemoryUtil.getAddress(byteBuffer), glCreateShaderProgramEXT);
    }
    
    static native int nglCreateShaderProgramEXT(final int p0, final long p1, final long p2);
    
    public static int glCreateShaderProgramEXT(final int n, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCreateShaderProgramEXT = capabilities.glCreateShaderProgramEXT;
        BufferChecks.checkFunctionAddress(glCreateShaderProgramEXT);
        return nglCreateShaderProgramEXT(n, APIUtil.getBufferNT(capabilities, charSequence), glCreateShaderProgramEXT);
    }
}
