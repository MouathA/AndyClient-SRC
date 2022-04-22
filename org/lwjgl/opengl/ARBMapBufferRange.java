package org.lwjgl.opengl;

import java.nio.*;

public final class ARBMapBufferRange
{
    public static final int GL_MAP_READ_BIT = 1;
    public static final int GL_MAP_WRITE_BIT = 2;
    public static final int GL_MAP_INVALIDATE_RANGE_BIT = 4;
    public static final int GL_MAP_INVALIDATE_BUFFER_BIT = 8;
    public static final int GL_MAP_FLUSH_EXPLICIT_BIT = 16;
    public static final int GL_MAP_UNSYNCHRONIZED_BIT = 32;
    
    private ARBMapBufferRange() {
    }
    
    public static ByteBuffer glMapBufferRange(final int n, final long n2, final long n3, final int n4, final ByteBuffer byteBuffer) {
        return GL30.glMapBufferRange(n, n2, n3, n4, byteBuffer);
    }
    
    public static void glFlushMappedBufferRange(final int n, final long n2, final long n3) {
        GL30.glFlushMappedBufferRange(n, n2, n3);
    }
}
