package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class EXTMultiDrawArrays
{
    private EXTMultiDrawArrays() {
    }
    
    public static void glMultiDrawArraysEXT(final int n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long glMultiDrawArraysEXT = GLContext.getCapabilities().glMultiDrawArraysEXT;
        BufferChecks.checkFunctionAddress(glMultiDrawArraysEXT);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkBuffer(intBuffer2, intBuffer.remaining());
        nglMultiDrawArraysEXT(n, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), intBuffer.remaining(), glMultiDrawArraysEXT);
    }
    
    static native void nglMultiDrawArraysEXT(final int p0, final long p1, final long p2, final int p3, final long p4);
}
