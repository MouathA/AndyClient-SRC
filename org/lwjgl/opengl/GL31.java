package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class GL31
{
    public static final int GL_RED_SNORM = 36752;
    public static final int GL_RG_SNORM = 36753;
    public static final int GL_RGB_SNORM = 36754;
    public static final int GL_RGBA_SNORM = 36755;
    public static final int GL_R8_SNORM = 36756;
    public static final int GL_RG8_SNORM = 36757;
    public static final int GL_RGB8_SNORM = 36758;
    public static final int GL_RGBA8_SNORM = 36759;
    public static final int GL_R16_SNORM = 36760;
    public static final int GL_RG16_SNORM = 36761;
    public static final int GL_RGB16_SNORM = 36762;
    public static final int GL_RGBA16_SNORM = 36763;
    public static final int GL_SIGNED_NORMALIZED = 36764;
    public static final int GL_COPY_READ_BUFFER_BINDING = 36662;
    public static final int GL_COPY_WRITE_BUFFER_BINDING = 36663;
    public static final int GL_COPY_READ_BUFFER = 36662;
    public static final int GL_COPY_WRITE_BUFFER = 36663;
    public static final int GL_PRIMITIVE_RESTART = 36765;
    public static final int GL_PRIMITIVE_RESTART_INDEX = 36766;
    public static final int GL_TEXTURE_BUFFER = 35882;
    public static final int GL_MAX_TEXTURE_BUFFER_SIZE = 35883;
    public static final int GL_TEXTURE_BINDING_BUFFER = 35884;
    public static final int GL_TEXTURE_BUFFER_DATA_STORE_BINDING = 35885;
    public static final int GL_TEXTURE_BUFFER_FORMAT = 35886;
    public static final int GL_TEXTURE_RECTANGLE = 34037;
    public static final int GL_TEXTURE_BINDING_RECTANGLE = 34038;
    public static final int GL_PROXY_TEXTURE_RECTANGLE = 34039;
    public static final int GL_MAX_RECTANGLE_TEXTURE_SIZE = 34040;
    public static final int GL_SAMPLER_2D_RECT = 35683;
    public static final int GL_SAMPLER_2D_RECT_SHADOW = 35684;
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
    
    private GL31() {
    }
    
    public static void glDrawArraysInstanced(final int n, final int n2, final int n3, final int n4) {
        final long glDrawArraysInstanced = GLContext.getCapabilities().glDrawArraysInstanced;
        BufferChecks.checkFunctionAddress(glDrawArraysInstanced);
        nglDrawArraysInstanced(n, n2, n3, n4, glDrawArraysInstanced);
    }
    
    static native void nglDrawArraysInstanced(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glDrawElementsInstanced(final int n, final ByteBuffer byteBuffer, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstanced = capabilities.glDrawElementsInstanced;
        BufferChecks.checkFunctionAddress(glDrawElementsInstanced);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglDrawElementsInstanced(n, byteBuffer.remaining(), 5121, MemoryUtil.getAddress(byteBuffer), n2, glDrawElementsInstanced);
    }
    
    public static void glDrawElementsInstanced(final int n, final IntBuffer intBuffer, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstanced = capabilities.glDrawElementsInstanced;
        BufferChecks.checkFunctionAddress(glDrawElementsInstanced);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglDrawElementsInstanced(n, intBuffer.remaining(), 5125, MemoryUtil.getAddress(intBuffer), n2, glDrawElementsInstanced);
    }
    
    public static void glDrawElementsInstanced(final int n, final ShortBuffer shortBuffer, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstanced = capabilities.glDrawElementsInstanced;
        BufferChecks.checkFunctionAddress(glDrawElementsInstanced);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglDrawElementsInstanced(n, shortBuffer.remaining(), 5123, MemoryUtil.getAddress(shortBuffer), n2, glDrawElementsInstanced);
    }
    
    static native void nglDrawElementsInstanced(final int p0, final int p1, final int p2, final long p3, final int p4, final long p5);
    
    public static void glDrawElementsInstanced(final int n, final int n2, final int n3, final long n4, final int n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsInstanced = capabilities.glDrawElementsInstanced;
        BufferChecks.checkFunctionAddress(glDrawElementsInstanced);
        GLChecks.ensureElementVBOenabled(capabilities);
        nglDrawElementsInstancedBO(n, n2, n3, n4, n5, glDrawElementsInstanced);
    }
    
    static native void nglDrawElementsInstancedBO(final int p0, final int p1, final int p2, final long p3, final int p4, final long p5);
    
    public static void glCopyBufferSubData(final int n, final int n2, final long n3, final long n4, final long n5) {
        final long glCopyBufferSubData = GLContext.getCapabilities().glCopyBufferSubData;
        BufferChecks.checkFunctionAddress(glCopyBufferSubData);
        nglCopyBufferSubData(n, n2, n3, n4, n5, glCopyBufferSubData);
    }
    
    static native void nglCopyBufferSubData(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static void glPrimitiveRestartIndex(final int n) {
        final long glPrimitiveRestartIndex = GLContext.getCapabilities().glPrimitiveRestartIndex;
        BufferChecks.checkFunctionAddress(glPrimitiveRestartIndex);
        nglPrimitiveRestartIndex(n, glPrimitiveRestartIndex);
    }
    
    static native void nglPrimitiveRestartIndex(final int p0, final long p1);
    
    public static void glTexBuffer(final int n, final int n2, final int n3) {
        final long glTexBuffer = GLContext.getCapabilities().glTexBuffer;
        BufferChecks.checkFunctionAddress(glTexBuffer);
        nglTexBuffer(n, n2, n3, glTexBuffer);
    }
    
    static native void nglTexBuffer(final int p0, final int p1, final int p2, final long p3);
    
    public static void glGetUniformIndices(final int n, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        final long glGetUniformIndices = GLContext.getCapabilities().glGetUniformIndices;
        BufferChecks.checkFunctionAddress(glGetUniformIndices);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer, intBuffer.remaining());
        BufferChecks.checkDirect(intBuffer);
        nglGetUniformIndices(n, intBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(intBuffer), glGetUniformIndices);
    }
    
    static native void nglGetUniformIndices(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static void glGetUniformIndices(final int n, final CharSequence[] array, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetUniformIndices = capabilities.glGetUniformIndices;
        BufferChecks.checkFunctionAddress(glGetUniformIndices);
        BufferChecks.checkArray(array);
        BufferChecks.checkBuffer(intBuffer, array.length);
        nglGetUniformIndices(n, array.length, APIUtil.getBufferNT(capabilities, array), MemoryUtil.getAddress(intBuffer), glGetUniformIndices);
    }
    
    public static void glGetActiveUniforms(final int n, final IntBuffer intBuffer, final int n2, final IntBuffer intBuffer2) {
        final long glGetActiveUniformsiv = GLContext.getCapabilities().glGetActiveUniformsiv;
        BufferChecks.checkFunctionAddress(glGetActiveUniformsiv);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkBuffer(intBuffer2, intBuffer.remaining());
        nglGetActiveUniformsiv(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), n2, MemoryUtil.getAddress(intBuffer2), glGetActiveUniformsiv);
    }
    
    static native void nglGetActiveUniformsiv(final int p0, final int p1, final long p2, final int p3, final long p4, final long p5);
    
    @Deprecated
    public static int glGetActiveUniforms(final int n, final int n2, final int n3) {
        return glGetActiveUniformsi(n, n2, n3);
    }
    
    public static int glGetActiveUniformsi(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveUniformsiv = capabilities.glGetActiveUniformsiv;
        BufferChecks.checkFunctionAddress(glGetActiveUniformsiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetActiveUniformsiv(n, 1, MemoryUtil.getAddress(bufferInt.put(1, n2), 1), n3, MemoryUtil.getAddress(bufferInt), glGetActiveUniformsiv);
        return bufferInt.get(0);
    }
    
    public static void glGetActiveUniformName(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final long glGetActiveUniformName = GLContext.getCapabilities().glGetActiveUniformName;
        BufferChecks.checkFunctionAddress(glGetActiveUniformName);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(byteBuffer);
        nglGetActiveUniformName(n, n2, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetActiveUniformName);
    }
    
    static native void nglGetActiveUniformName(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5);
    
    public static String glGetActiveUniformName(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveUniformName = capabilities.glGetActiveUniformName;
        BufferChecks.checkFunctionAddress(glGetActiveUniformName);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n3);
        nglGetActiveUniformName(n, n2, n3, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(bufferByte), glGetActiveUniformName);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static int glGetUniformBlockIndex(final int n, final ByteBuffer byteBuffer) {
        final long glGetUniformBlockIndex = GLContext.getCapabilities().glGetUniformBlockIndex;
        BufferChecks.checkFunctionAddress(glGetUniformBlockIndex);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        return nglGetUniformBlockIndex(n, MemoryUtil.getAddress(byteBuffer), glGetUniformBlockIndex);
    }
    
    static native int nglGetUniformBlockIndex(final int p0, final long p1, final long p2);
    
    public static int glGetUniformBlockIndex(final int n, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetUniformBlockIndex = capabilities.glGetUniformBlockIndex;
        BufferChecks.checkFunctionAddress(glGetUniformBlockIndex);
        return nglGetUniformBlockIndex(n, APIUtil.getBufferNT(capabilities, charSequence), glGetUniformBlockIndex);
    }
    
    public static void glGetActiveUniformBlock(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetActiveUniformBlockiv = GLContext.getCapabilities().glGetActiveUniformBlockiv;
        BufferChecks.checkFunctionAddress(glGetActiveUniformBlockiv);
        BufferChecks.checkBuffer(intBuffer, 16);
        nglGetActiveUniformBlockiv(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetActiveUniformBlockiv);
    }
    
    static native void nglGetActiveUniformBlockiv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    @Deprecated
    public static int glGetActiveUniformBlock(final int n, final int n2, final int n3) {
        return glGetActiveUniformBlocki(n, n2, n3);
    }
    
    public static int glGetActiveUniformBlocki(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveUniformBlockiv = capabilities.glGetActiveUniformBlockiv;
        BufferChecks.checkFunctionAddress(glGetActiveUniformBlockiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetActiveUniformBlockiv(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetActiveUniformBlockiv);
        return bufferInt.get(0);
    }
    
    public static void glGetActiveUniformBlockName(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final long glGetActiveUniformBlockName = GLContext.getCapabilities().glGetActiveUniformBlockName;
        BufferChecks.checkFunctionAddress(glGetActiveUniformBlockName);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(byteBuffer);
        nglGetActiveUniformBlockName(n, n2, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetActiveUniformBlockName);
    }
    
    static native void nglGetActiveUniformBlockName(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5);
    
    public static String glGetActiveUniformBlockName(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveUniformBlockName = capabilities.glGetActiveUniformBlockName;
        BufferChecks.checkFunctionAddress(glGetActiveUniformBlockName);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n3);
        nglGetActiveUniformBlockName(n, n2, n3, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(bufferByte), glGetActiveUniformBlockName);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static void glUniformBlockBinding(final int n, final int n2, final int n3) {
        final long glUniformBlockBinding = GLContext.getCapabilities().glUniformBlockBinding;
        BufferChecks.checkFunctionAddress(glUniformBlockBinding);
        nglUniformBlockBinding(n, n2, n3, glUniformBlockBinding);
    }
    
    static native void nglUniformBlockBinding(final int p0, final int p1, final int p2, final long p3);
}
