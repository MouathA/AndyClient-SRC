package org.lwjgl.opengl;

import java.nio.*;

public final class ARBShaderSubroutine
{
    public static final int GL_ACTIVE_SUBROUTINES = 36325;
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORMS = 36326;
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORM_LOCATIONS = 36423;
    public static final int GL_ACTIVE_SUBROUTINE_MAX_LENGTH = 36424;
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORM_MAX_LENGTH = 36425;
    public static final int GL_MAX_SUBROUTINES = 36327;
    public static final int GL_MAX_SUBROUTINE_UNIFORM_LOCATIONS = 36328;
    public static final int GL_NUM_COMPATIBLE_SUBROUTINES = 36426;
    public static final int GL_COMPATIBLE_SUBROUTINES = 36427;
    
    private ARBShaderSubroutine() {
    }
    
    public static int glGetSubroutineUniformLocation(final int n, final int n2, final ByteBuffer byteBuffer) {
        return GL40.glGetSubroutineUniformLocation(n, n2, byteBuffer);
    }
    
    public static int glGetSubroutineUniformLocation(final int n, final int n2, final CharSequence charSequence) {
        return GL40.glGetSubroutineUniformLocation(n, n2, charSequence);
    }
    
    public static int glGetSubroutineIndex(final int n, final int n2, final ByteBuffer byteBuffer) {
        return GL40.glGetSubroutineIndex(n, n2, byteBuffer);
    }
    
    public static int glGetSubroutineIndex(final int n, final int n2, final CharSequence charSequence) {
        return GL40.glGetSubroutineIndex(n, n2, charSequence);
    }
    
    public static void glGetActiveSubroutineUniform(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        GL40.glGetActiveSubroutineUniform(n, n2, n3, n4, intBuffer);
    }
    
    public static int glGetActiveSubroutineUniformi(final int n, final int n2, final int n3, final int n4) {
        return GL40.glGetActiveSubroutineUniformi(n, n2, n3, n4);
    }
    
    public static void glGetActiveSubroutineUniformName(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        GL40.glGetActiveSubroutineUniformName(n, n2, n3, intBuffer, byteBuffer);
    }
    
    public static String glGetActiveSubroutineUniformName(final int n, final int n2, final int n3, final int n4) {
        return GL40.glGetActiveSubroutineUniformName(n, n2, n3, n4);
    }
    
    public static void glGetActiveSubroutineName(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        GL40.glGetActiveSubroutineName(n, n2, n3, intBuffer, byteBuffer);
    }
    
    public static String glGetActiveSubroutineName(final int n, final int n2, final int n3, final int n4) {
        return GL40.glGetActiveSubroutineName(n, n2, n3, n4);
    }
    
    public static void glUniformSubroutinesu(final int n, final IntBuffer intBuffer) {
        GL40.glUniformSubroutinesu(n, intBuffer);
    }
    
    public static void glGetUniformSubroutineu(final int n, final int n2, final IntBuffer intBuffer) {
        GL40.glGetUniformSubroutineu(n, n2, intBuffer);
    }
    
    public static int glGetUniformSubroutineui(final int n, final int n2) {
        return GL40.glGetUniformSubroutineui(n, n2);
    }
    
    public static void glGetProgramStage(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        GL40.glGetProgramStage(n, n2, n3, intBuffer);
    }
    
    public static int glGetProgramStagei(final int n, final int n2, final int n3) {
        return GL40.glGetProgramStagei(n, n2, n3);
    }
}
