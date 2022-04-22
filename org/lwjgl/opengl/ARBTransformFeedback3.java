package org.lwjgl.opengl;

import java.nio.*;

public final class ARBTransformFeedback3
{
    public static final int GL_MAX_TRANSFORM_FEEDBACK_BUFFERS = 36464;
    public static final int GL_MAX_VERTEX_STREAMS = 36465;
    
    private ARBTransformFeedback3() {
    }
    
    public static void glDrawTransformFeedbackStream(final int n, final int n2, final int n3) {
        GL40.glDrawTransformFeedbackStream(n, n2, n3);
    }
    
    public static void glBeginQueryIndexed(final int n, final int n2, final int n3) {
        GL40.glBeginQueryIndexed(n, n2, n3);
    }
    
    public static void glEndQueryIndexed(final int n, final int n2) {
        GL40.glEndQueryIndexed(n, n2);
    }
    
    public static void glGetQueryIndexed(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        GL40.glGetQueryIndexed(n, n2, n3, intBuffer);
    }
    
    public static int glGetQueryIndexedi(final int n, final int n2, final int n3) {
        return GL40.glGetQueryIndexedi(n, n2, n3);
    }
}
