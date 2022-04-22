package org.lwjgl.opengl;

public final class ARBTransformFeedbackInstanced
{
    private ARBTransformFeedbackInstanced() {
    }
    
    public static void glDrawTransformFeedbackInstanced(final int n, final int n2, final int n3) {
        GL42.glDrawTransformFeedbackInstanced(n, n2, n3);
    }
    
    public static void glDrawTransformFeedbackStreamInstanced(final int n, final int n2, final int n3, final int n4) {
        GL42.glDrawTransformFeedbackStreamInstanced(n, n2, n3, n4);
    }
}
