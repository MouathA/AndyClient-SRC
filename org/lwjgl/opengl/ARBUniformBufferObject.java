package org.lwjgl.opengl;

import java.nio.*;

public final class ARBUniformBufferObject
{
    public static final int GL_UNIFORM_BUFFER = 35345;
    public static final int GL_UNIFORM_BUFFER_BINDING = 35368;
    public static final int GL_UNIFORM_BUFFER_START = 35369;
    public static final int GL_UNIFORM_BUFFER_SIZE = 35370;
    public static final int GL_MAX_VERTEX_UNIFORM_BLOCKS = 35371;
    public static final int GL_MAX_GEOMETRY_UNIFORM_BLOCKS = 35372;
    public static final int GL_MAX_FRAGMENT_UNIFORM_BLOCKS = 35373;
    public static final int GL_MAX_COMBINED_UNIFORM_BLOCKS = 35374;
    public static final int GL_MAX_UNIFORM_BUFFER_BINDINGS = 35375;
    public static final int GL_MAX_UNIFORM_BLOCK_SIZE = 35376;
    public static final int GL_MAX_COMBINED_VERTEX_UNIFORM_COMPONENTS = 35377;
    public static final int GL_MAX_COMBINED_GEOMETRY_UNIFORM_COMPONENTS = 35378;
    public static final int GL_MAX_COMBINED_FRAGMENT_UNIFORM_COMPONENTS = 35379;
    public static final int GL_UNIFORM_BUFFER_OFFSET_ALIGNMENT = 35380;
    public static final int GL_ACTIVE_UNIFORM_BLOCK_MAX_NAME_LENGTH = 35381;
    public static final int GL_ACTIVE_UNIFORM_BLOCKS = 35382;
    public static final int GL_UNIFORM_TYPE = 35383;
    public static final int GL_UNIFORM_SIZE = 35384;
    public static final int GL_UNIFORM_NAME_LENGTH = 35385;
    public static final int GL_UNIFORM_BLOCK_INDEX = 35386;
    public static final int GL_UNIFORM_OFFSET = 35387;
    public static final int GL_UNIFORM_ARRAY_STRIDE = 35388;
    public static final int GL_UNIFORM_MATRIX_STRIDE = 35389;
    public static final int GL_UNIFORM_IS_ROW_MAJOR = 35390;
    public static final int GL_UNIFORM_BLOCK_BINDING = 35391;
    public static final int GL_UNIFORM_BLOCK_DATA_SIZE = 35392;
    public static final int GL_UNIFORM_BLOCK_NAME_LENGTH = 35393;
    public static final int GL_UNIFORM_BLOCK_ACTIVE_UNIFORMS = 35394;
    public static final int GL_UNIFORM_BLOCK_ACTIVE_UNIFORM_INDICES = 35395;
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_VERTEX_SHADER = 35396;
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_GEOMETRY_SHADER = 35397;
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_FRAGMENT_SHADER = 35398;
    public static final int GL_INVALID_INDEX = -1;
    
    private ARBUniformBufferObject() {
    }
    
    public static void glGetUniformIndices(final int n, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        GL31.glGetUniformIndices(n, byteBuffer, intBuffer);
    }
    
    public static void glGetUniformIndices(final int n, final CharSequence[] array, final IntBuffer intBuffer) {
        GL31.glGetUniformIndices(n, array, intBuffer);
    }
    
    public static void glGetActiveUniforms(final int n, final IntBuffer intBuffer, final int n2, final IntBuffer intBuffer2) {
        GL31.glGetActiveUniforms(n, intBuffer, n2, intBuffer2);
    }
    
    @Deprecated
    public static int glGetActiveUniforms(final int n, final int n2, final int n3) {
        return GL31.glGetActiveUniformsi(n, n2, n3);
    }
    
    public static int glGetActiveUniformsi(final int n, final int n2, final int n3) {
        return GL31.glGetActiveUniformsi(n, n2, n3);
    }
    
    public static void glGetActiveUniformName(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        GL31.glGetActiveUniformName(n, n2, intBuffer, byteBuffer);
    }
    
    public static String glGetActiveUniformName(final int n, final int n2, final int n3) {
        return GL31.glGetActiveUniformName(n, n2, n3);
    }
    
    public static int glGetUniformBlockIndex(final int n, final ByteBuffer byteBuffer) {
        return GL31.glGetUniformBlockIndex(n, byteBuffer);
    }
    
    public static int glGetUniformBlockIndex(final int n, final CharSequence charSequence) {
        return GL31.glGetUniformBlockIndex(n, charSequence);
    }
    
    public static void glGetActiveUniformBlock(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        GL31.glGetActiveUniformBlock(n, n2, n3, intBuffer);
    }
    
    @Deprecated
    public static int glGetActiveUniformBlock(final int n, final int n2, final int n3) {
        return GL31.glGetActiveUniformBlocki(n, n2, n3);
    }
    
    public static int glGetActiveUniformBlocki(final int n, final int n2, final int n3) {
        return GL31.glGetActiveUniformBlocki(n, n2, n3);
    }
    
    public static void glGetActiveUniformBlockName(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        GL31.glGetActiveUniformBlockName(n, n2, intBuffer, byteBuffer);
    }
    
    public static String glGetActiveUniformBlockName(final int n, final int n2, final int n3) {
        return GL31.glGetActiveUniformBlockName(n, n2, n3);
    }
    
    public static void glBindBufferRange(final int n, final int n2, final int n3, final long n4, final long n5) {
        GL30.glBindBufferRange(n, n2, n3, n4, n5);
    }
    
    public static void glBindBufferBase(final int n, final int n2, final int n3) {
        GL30.glBindBufferBase(n, n2, n3);
    }
    
    public static void glGetInteger(final int n, final int n2, final IntBuffer intBuffer) {
        GL30.glGetInteger(n, n2, intBuffer);
    }
    
    public static int glGetInteger(final int n, final int n2) {
        return GL30.glGetInteger(n, n2);
    }
    
    public static void glUniformBlockBinding(final int n, final int n2, final int n3) {
        GL31.glUniformBlockBinding(n, n2, n3);
    }
}
