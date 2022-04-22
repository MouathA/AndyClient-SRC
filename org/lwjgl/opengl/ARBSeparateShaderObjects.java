package org.lwjgl.opengl;

import java.nio.*;

public final class ARBSeparateShaderObjects
{
    public static final int GL_VERTEX_SHADER_BIT = 1;
    public static final int GL_FRAGMENT_SHADER_BIT = 2;
    public static final int GL_GEOMETRY_SHADER_BIT = 4;
    public static final int GL_TESS_CONTROL_SHADER_BIT = 8;
    public static final int GL_TESS_EVALUATION_SHADER_BIT = 16;
    public static final int GL_ALL_SHADER_BITS = -1;
    public static final int GL_PROGRAM_SEPARABLE = 33368;
    public static final int GL_ACTIVE_PROGRAM = 33369;
    public static final int GL_PROGRAM_PIPELINE_BINDING = 33370;
    
    private ARBSeparateShaderObjects() {
    }
    
    public static void glUseProgramStages(final int n, final int n2, final int n3) {
        GL41.glUseProgramStages(n, n2, n3);
    }
    
    public static void glActiveShaderProgram(final int n, final int n2) {
        GL41.glActiveShaderProgram(n, n2);
    }
    
    public static int glCreateShaderProgram(final int n, final ByteBuffer byteBuffer) {
        return GL41.glCreateShaderProgram(n, byteBuffer);
    }
    
    public static int glCreateShaderProgram(final int n, final int n2, final ByteBuffer byteBuffer) {
        return GL41.glCreateShaderProgram(n, n2, byteBuffer);
    }
    
    public static int glCreateShaderProgram(final int n, final ByteBuffer[] array) {
        return GL41.glCreateShaderProgram(n, array);
    }
    
    public static int glCreateShaderProgram(final int n, final CharSequence charSequence) {
        return GL41.glCreateShaderProgram(n, charSequence);
    }
    
    public static int glCreateShaderProgram(final int n, final CharSequence[] array) {
        return GL41.glCreateShaderProgram(n, array);
    }
    
    public static void glBindProgramPipeline(final int n) {
        GL41.glBindProgramPipeline(n);
    }
    
    public static void glDeleteProgramPipelines(final IntBuffer intBuffer) {
        GL41.glDeleteProgramPipelines(intBuffer);
    }
    
    public static void glDeleteProgramPipelines(final int n) {
        GL41.glDeleteProgramPipelines(n);
    }
    
    public static void glGenProgramPipelines(final IntBuffer intBuffer) {
        GL41.glGenProgramPipelines(intBuffer);
    }
    
    public static int glGenProgramPipelines() {
        return GL41.glGenProgramPipelines();
    }
    
    public static boolean glIsProgramPipeline(final int n) {
        return GL41.glIsProgramPipeline(n);
    }
    
    public static void glProgramParameteri(final int n, final int n2, final int n3) {
        GL41.glProgramParameteri(n, n2, n3);
    }
    
    public static void glGetProgramPipeline(final int n, final int n2, final IntBuffer intBuffer) {
        GL41.glGetProgramPipeline(n, n2, intBuffer);
    }
    
    public static int glGetProgramPipelinei(final int n, final int n2) {
        return GL41.glGetProgramPipelinei(n, n2);
    }
    
    public static void glProgramUniform1i(final int n, final int n2, final int n3) {
        GL41.glProgramUniform1i(n, n2, n3);
    }
    
    public static void glProgramUniform2i(final int n, final int n2, final int n3, final int n4) {
        GL41.glProgramUniform2i(n, n2, n3, n4);
    }
    
    public static void glProgramUniform3i(final int n, final int n2, final int n3, final int n4, final int n5) {
        GL41.glProgramUniform3i(n, n2, n3, n4, n5);
    }
    
    public static void glProgramUniform4i(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        GL41.glProgramUniform4i(n, n2, n3, n4, n5, n6);
    }
    
    public static void glProgramUniform1f(final int n, final int n2, final float n3) {
        GL41.glProgramUniform1f(n, n2, n3);
    }
    
    public static void glProgramUniform2f(final int n, final int n2, final float n3, final float n4) {
        GL41.glProgramUniform2f(n, n2, n3, n4);
    }
    
    public static void glProgramUniform3f(final int n, final int n2, final float n3, final float n4, final float n5) {
        GL41.glProgramUniform3f(n, n2, n3, n4, n5);
    }
    
    public static void glProgramUniform4f(final int n, final int n2, final float n3, final float n4, final float n5, final float n6) {
        GL41.glProgramUniform4f(n, n2, n3, n4, n5, n6);
    }
    
    public static void glProgramUniform1d(final int n, final int n2, final double n3) {
        GL41.glProgramUniform1d(n, n2, n3);
    }
    
    public static void glProgramUniform2d(final int n, final int n2, final double n3, final double n4) {
        GL41.glProgramUniform2d(n, n2, n3, n4);
    }
    
    public static void glProgramUniform3d(final int n, final int n2, final double n3, final double n4, final double n5) {
        GL41.glProgramUniform3d(n, n2, n3, n4, n5);
    }
    
    public static void glProgramUniform4d(final int n, final int n2, final double n3, final double n4, final double n5, final double n6) {
        GL41.glProgramUniform4d(n, n2, n3, n4, n5, n6);
    }
    
    public static void glProgramUniform1(final int n, final int n2, final IntBuffer intBuffer) {
        GL41.glProgramUniform1(n, n2, intBuffer);
    }
    
    public static void glProgramUniform2(final int n, final int n2, final IntBuffer intBuffer) {
        GL41.glProgramUniform2(n, n2, intBuffer);
    }
    
    public static void glProgramUniform3(final int n, final int n2, final IntBuffer intBuffer) {
        GL41.glProgramUniform3(n, n2, intBuffer);
    }
    
    public static void glProgramUniform4(final int n, final int n2, final IntBuffer intBuffer) {
        GL41.glProgramUniform4(n, n2, intBuffer);
    }
    
    public static void glProgramUniform1(final int n, final int n2, final FloatBuffer floatBuffer) {
        GL41.glProgramUniform1(n, n2, floatBuffer);
    }
    
    public static void glProgramUniform2(final int n, final int n2, final FloatBuffer floatBuffer) {
        GL41.glProgramUniform2(n, n2, floatBuffer);
    }
    
    public static void glProgramUniform3(final int n, final int n2, final FloatBuffer floatBuffer) {
        GL41.glProgramUniform3(n, n2, floatBuffer);
    }
    
    public static void glProgramUniform4(final int n, final int n2, final FloatBuffer floatBuffer) {
        GL41.glProgramUniform4(n, n2, floatBuffer);
    }
    
    public static void glProgramUniform1(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        GL41.glProgramUniform1(n, n2, doubleBuffer);
    }
    
    public static void glProgramUniform2(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        GL41.glProgramUniform2(n, n2, doubleBuffer);
    }
    
    public static void glProgramUniform3(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        GL41.glProgramUniform3(n, n2, doubleBuffer);
    }
    
    public static void glProgramUniform4(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        GL41.glProgramUniform4(n, n2, doubleBuffer);
    }
    
    public static void glProgramUniform1ui(final int n, final int n2, final int n3) {
        GL41.glProgramUniform1ui(n, n2, n3);
    }
    
    public static void glProgramUniform2ui(final int n, final int n2, final int n3, final int n4) {
        GL41.glProgramUniform2ui(n, n2, n3, n4);
    }
    
    public static void glProgramUniform3ui(final int n, final int n2, final int n3, final int n4, final int n5) {
        GL41.glProgramUniform3ui(n, n2, n3, n4, n5);
    }
    
    public static void glProgramUniform4ui(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        GL41.glProgramUniform4ui(n, n2, n3, n4, n5, n6);
    }
    
    public static void glProgramUniform1u(final int n, final int n2, final IntBuffer intBuffer) {
        GL41.glProgramUniform1u(n, n2, intBuffer);
    }
    
    public static void glProgramUniform2u(final int n, final int n2, final IntBuffer intBuffer) {
        GL41.glProgramUniform2u(n, n2, intBuffer);
    }
    
    public static void glProgramUniform3u(final int n, final int n2, final IntBuffer intBuffer) {
        GL41.glProgramUniform3u(n, n2, intBuffer);
    }
    
    public static void glProgramUniform4u(final int n, final int n2, final IntBuffer intBuffer) {
        GL41.glProgramUniform4u(n, n2, intBuffer);
    }
    
    public static void glProgramUniformMatrix2(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        GL41.glProgramUniformMatrix2(n, n2, b, floatBuffer);
    }
    
    public static void glProgramUniformMatrix3(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        GL41.glProgramUniformMatrix3(n, n2, b, floatBuffer);
    }
    
    public static void glProgramUniformMatrix4(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        GL41.glProgramUniformMatrix4(n, n2, b, floatBuffer);
    }
    
    public static void glProgramUniformMatrix2(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        GL41.glProgramUniformMatrix2(n, n2, b, doubleBuffer);
    }
    
    public static void glProgramUniformMatrix3(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        GL41.glProgramUniformMatrix3(n, n2, b, doubleBuffer);
    }
    
    public static void glProgramUniformMatrix4(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        GL41.glProgramUniformMatrix4(n, n2, b, doubleBuffer);
    }
    
    public static void glProgramUniformMatrix2x3(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        GL41.glProgramUniformMatrix2x3(n, n2, b, floatBuffer);
    }
    
    public static void glProgramUniformMatrix3x2(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        GL41.glProgramUniformMatrix3x2(n, n2, b, floatBuffer);
    }
    
    public static void glProgramUniformMatrix2x4(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        GL41.glProgramUniformMatrix2x4(n, n2, b, floatBuffer);
    }
    
    public static void glProgramUniformMatrix4x2(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        GL41.glProgramUniformMatrix4x2(n, n2, b, floatBuffer);
    }
    
    public static void glProgramUniformMatrix3x4(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        GL41.glProgramUniformMatrix3x4(n, n2, b, floatBuffer);
    }
    
    public static void glProgramUniformMatrix4x3(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        GL41.glProgramUniformMatrix4x3(n, n2, b, floatBuffer);
    }
    
    public static void glProgramUniformMatrix2x3(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        GL41.glProgramUniformMatrix2x3(n, n2, b, doubleBuffer);
    }
    
    public static void glProgramUniformMatrix3x2(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        GL41.glProgramUniformMatrix3x2(n, n2, b, doubleBuffer);
    }
    
    public static void glProgramUniformMatrix2x4(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        GL41.glProgramUniformMatrix2x4(n, n2, b, doubleBuffer);
    }
    
    public static void glProgramUniformMatrix4x2(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        GL41.glProgramUniformMatrix4x2(n, n2, b, doubleBuffer);
    }
    
    public static void glProgramUniformMatrix3x4(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        GL41.glProgramUniformMatrix3x4(n, n2, b, doubleBuffer);
    }
    
    public static void glProgramUniformMatrix4x3(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        GL41.glProgramUniformMatrix4x3(n, n2, b, doubleBuffer);
    }
    
    public static void glValidateProgramPipeline(final int n) {
        GL41.glValidateProgramPipeline(n);
    }
    
    public static void glGetProgramPipelineInfoLog(final int n, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        GL41.glGetProgramPipelineInfoLog(n, intBuffer, byteBuffer);
    }
    
    public static String glGetProgramPipelineInfoLog(final int n, final int n2) {
        return GL41.glGetProgramPipelineInfoLog(n, n2);
    }
}
