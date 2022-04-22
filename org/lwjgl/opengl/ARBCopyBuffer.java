package org.lwjgl.opengl;

public final class ARBCopyBuffer
{
    public static final int GL_COPY_READ_BUFFER = 36662;
    public static final int GL_COPY_WRITE_BUFFER = 36663;
    
    private ARBCopyBuffer() {
    }
    
    public static void glCopyBufferSubData(final int n, final int n2, final long n3, final long n4, final long n5) {
        GL31.glCopyBufferSubData(n, n2, n3, n4, n5);
    }
}
