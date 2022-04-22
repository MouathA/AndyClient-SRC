package org.lwjgl.opengl;

import java.nio.*;

public final class ARBSamplerObjects
{
    public static final int GL_SAMPLER_BINDING = 35097;
    
    private ARBSamplerObjects() {
    }
    
    public static void glGenSamplers(final IntBuffer intBuffer) {
        GL33.glGenSamplers(intBuffer);
    }
    
    public static int glGenSamplers() {
        return GL33.glGenSamplers();
    }
    
    public static void glDeleteSamplers(final IntBuffer intBuffer) {
        GL33.glDeleteSamplers(intBuffer);
    }
    
    public static void glDeleteSamplers(final int n) {
        GL33.glDeleteSamplers(n);
    }
    
    public static boolean glIsSampler(final int n) {
        return GL33.glIsSampler(n);
    }
    
    public static void glBindSampler(final int n, final int n2) {
        GL33.glBindSampler(n, n2);
    }
    
    public static void glSamplerParameteri(final int n, final int n2, final int n3) {
        GL33.glSamplerParameteri(n, n2, n3);
    }
    
    public static void glSamplerParameterf(final int n, final int n2, final float n3) {
        GL33.glSamplerParameterf(n, n2, n3);
    }
    
    public static void glSamplerParameter(final int n, final int n2, final IntBuffer intBuffer) {
        GL33.glSamplerParameter(n, n2, intBuffer);
    }
    
    public static void glSamplerParameter(final int n, final int n2, final FloatBuffer floatBuffer) {
        GL33.glSamplerParameter(n, n2, floatBuffer);
    }
    
    public static void glSamplerParameterI(final int n, final int n2, final IntBuffer intBuffer) {
        GL33.glSamplerParameterI(n, n2, intBuffer);
    }
    
    public static void glSamplerParameterIu(final int n, final int n2, final IntBuffer intBuffer) {
        GL33.glSamplerParameterIu(n, n2, intBuffer);
    }
    
    public static void glGetSamplerParameter(final int n, final int n2, final IntBuffer intBuffer) {
        GL33.glGetSamplerParameter(n, n2, intBuffer);
    }
    
    public static int glGetSamplerParameteri(final int n, final int n2) {
        return GL33.glGetSamplerParameteri(n, n2);
    }
    
    public static void glGetSamplerParameter(final int n, final int n2, final FloatBuffer floatBuffer) {
        GL33.glGetSamplerParameter(n, n2, floatBuffer);
    }
    
    public static float glGetSamplerParameterf(final int n, final int n2) {
        return GL33.glGetSamplerParameterf(n, n2);
    }
    
    public static void glGetSamplerParameterI(final int n, final int n2, final IntBuffer intBuffer) {
        GL33.glGetSamplerParameterI(n, n2, intBuffer);
    }
    
    public static int glGetSamplerParameterIi(final int n, final int n2) {
        return GL33.glGetSamplerParameterIi(n, n2);
    }
    
    public static void glGetSamplerParameterIu(final int n, final int n2, final IntBuffer intBuffer) {
        GL33.glGetSamplerParameterIu(n, n2, intBuffer);
    }
    
    public static int glGetSamplerParameterIui(final int n, final int n2) {
        return GL33.glGetSamplerParameterIui(n, n2);
    }
}
