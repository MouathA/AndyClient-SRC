package org.lwjgl.opengl;

import org.lwjgl.*;

public final class EXTCompiledVertexArray
{
    public static final int GL_ARRAY_ELEMENT_LOCK_FIRST_EXT = 33192;
    public static final int GL_ARRAY_ELEMENT_LOCK_COUNT_EXT = 33193;
    
    private EXTCompiledVertexArray() {
    }
    
    public static void glLockArraysEXT(final int n, final int n2) {
        final long glLockArraysEXT = GLContext.getCapabilities().glLockArraysEXT;
        BufferChecks.checkFunctionAddress(glLockArraysEXT);
        nglLockArraysEXT(n, n2, glLockArraysEXT);
    }
    
    static native void nglLockArraysEXT(final int p0, final int p1, final long p2);
    
    public static void glUnlockArraysEXT() {
        final long glUnlockArraysEXT = GLContext.getCapabilities().glUnlockArraysEXT;
        BufferChecks.checkFunctionAddress(glUnlockArraysEXT);
        nglUnlockArraysEXT(glUnlockArraysEXT);
    }
    
    static native void nglUnlockArraysEXT(final long p0);
}
