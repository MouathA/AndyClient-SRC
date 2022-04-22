package org.lwjgl.opengl;

import java.nio.*;

public final class ARBGetProgramBinary
{
    public static final int GL_PROGRAM_BINARY_RETRIEVABLE_HINT = 33367;
    public static final int GL_PROGRAM_BINARY_LENGTH = 34625;
    public static final int GL_NUM_PROGRAM_BINARY_FORMATS = 34814;
    public static final int GL_PROGRAM_BINARY_FORMATS = 34815;
    
    private ARBGetProgramBinary() {
    }
    
    public static void glGetProgramBinary(final int n, final IntBuffer intBuffer, final IntBuffer intBuffer2, final ByteBuffer byteBuffer) {
        GL41.glGetProgramBinary(n, intBuffer, intBuffer2, byteBuffer);
    }
    
    public static void glProgramBinary(final int n, final int n2, final ByteBuffer byteBuffer) {
        GL41.glProgramBinary(n, n2, byteBuffer);
    }
    
    public static void glProgramParameteri(final int n, final int n2, final int n3) {
        GL41.glProgramParameteri(n, n2, n3);
    }
}
