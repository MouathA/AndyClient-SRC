package org.lwjgl.opengl;

import java.nio.*;

public final class ARBTransformFeedback2
{
    public static final int GL_TRANSFORM_FEEDBACK = 36386;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_PAUSED = 36387;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_ACTIVE = 36388;
    public static final int GL_TRANSFORM_FEEDBACK_BINDING = 36389;
    
    private ARBTransformFeedback2() {
    }
    
    public static void glBindTransformFeedback(final int n, final int n2) {
        GL40.glBindTransformFeedback(n, n2);
    }
    
    public static void glDeleteTransformFeedbacks(final IntBuffer intBuffer) {
        GL40.glDeleteTransformFeedbacks(intBuffer);
    }
    
    public static void glDeleteTransformFeedbacks(final int n) {
        GL40.glDeleteTransformFeedbacks(n);
    }
    
    public static void glGenTransformFeedbacks(final IntBuffer intBuffer) {
        GL40.glGenTransformFeedbacks(intBuffer);
    }
    
    public static int glGenTransformFeedbacks() {
        return GL40.glGenTransformFeedbacks();
    }
    
    public static boolean glIsTransformFeedback(final int n) {
        return GL40.glIsTransformFeedback(n);
    }
    
    public static void glPauseTransformFeedback() {
    }
    
    public static void glResumeTransformFeedback() {
    }
    
    public static void glDrawTransformFeedback(final int n, final int n2) {
        GL40.glDrawTransformFeedback(n, n2);
    }
}
