package org.lwjgl.opencl;

import java.nio.*;
import org.lwjgl.*;

public final class CL12GL
{
    public static final int CL_GL_OBJECT_TEXTURE2D_ARRAY = 8206;
    public static final int CL_GL_OBJECT_TEXTURE1D = 8207;
    public static final int CL_GL_OBJECT_TEXTURE1D_ARRAY = 8208;
    public static final int CL_GL_OBJECT_TEXTURE_BUFFER = 8209;
    
    private CL12GL() {
    }
    
    public static CLMem clCreateFromGLTexture(final CLContext clContext, final long n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final long clCreateFromGLTexture = CLCapabilities.clCreateFromGLTexture;
        BufferChecks.checkFunctionAddress(clCreateFromGLTexture);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateFromGLTexture(clContext.getPointer(), n, n2, n3, n4, MemoryUtil.getAddressSafe(intBuffer), clCreateFromGLTexture), clContext);
    }
    
    static native long nclCreateFromGLTexture(final long p0, final long p1, final int p2, final int p3, final int p4, final long p5, final long p6);
}
