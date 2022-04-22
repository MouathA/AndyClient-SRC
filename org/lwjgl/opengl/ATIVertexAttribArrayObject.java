package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class ATIVertexAttribArrayObject
{
    private ATIVertexAttribArrayObject() {
    }
    
    public static void glVertexAttribArrayObjectATI(final int n, final int n2, final int n3, final boolean b, final int n4, final int n5, final int n6) {
        final long glVertexAttribArrayObjectATI = GLContext.getCapabilities().glVertexAttribArrayObjectATI;
        BufferChecks.checkFunctionAddress(glVertexAttribArrayObjectATI);
        nglVertexAttribArrayObjectATI(n, n2, n3, b, n4, n5, n6, glVertexAttribArrayObjectATI);
    }
    
    static native void nglVertexAttribArrayObjectATI(final int p0, final int p1, final int p2, final boolean p3, final int p4, final int p5, final int p6, final long p7);
    
    public static void glGetVertexAttribArrayObjectATI(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetVertexAttribArrayObjectfvATI = GLContext.getCapabilities().glGetVertexAttribArrayObjectfvATI;
        BufferChecks.checkFunctionAddress(glGetVertexAttribArrayObjectfvATI);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetVertexAttribArrayObjectfvATI(n, n2, MemoryUtil.getAddress(floatBuffer), glGetVertexAttribArrayObjectfvATI);
    }
    
    static native void nglGetVertexAttribArrayObjectfvATI(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetVertexAttribArrayObjectATI(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetVertexAttribArrayObjectivATI = GLContext.getCapabilities().glGetVertexAttribArrayObjectivATI;
        BufferChecks.checkFunctionAddress(glGetVertexAttribArrayObjectivATI);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetVertexAttribArrayObjectivATI(n, n2, MemoryUtil.getAddress(intBuffer), glGetVertexAttribArrayObjectivATI);
    }
    
    static native void nglGetVertexAttribArrayObjectivATI(final int p0, final int p1, final long p2, final long p3);
}
