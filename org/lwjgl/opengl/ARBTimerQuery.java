package org.lwjgl.opengl;

import java.nio.*;

public final class ARBTimerQuery
{
    public static final int GL_TIME_ELAPSED = 35007;
    public static final int GL_TIMESTAMP = 36392;
    
    private ARBTimerQuery() {
    }
    
    public static void glQueryCounter(final int n, final int n2) {
        GL33.glQueryCounter(n, n2);
    }
    
    public static void glGetQueryObject(final int n, final int n2, final LongBuffer longBuffer) {
        GL33.glGetQueryObject(n, n2, longBuffer);
    }
    
    public static long glGetQueryObjecti64(final int n, final int n2) {
        return GL33.glGetQueryObjecti64(n, n2);
    }
    
    public static void glGetQueryObjectu(final int n, final int n2, final LongBuffer longBuffer) {
        GL33.glGetQueryObjectu(n, n2, longBuffer);
    }
    
    public static long glGetQueryObjectui64(final int n, final int n2) {
        return GL33.glGetQueryObjectui64(n, n2);
    }
}
