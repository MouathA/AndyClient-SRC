package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class EXTGpuShader4
{
    public static final int GL_VERTEX_ATTRIB_ARRAY_INTEGER_EXT = 35069;
    public static final int GL_SAMPLER_1D_ARRAY_EXT = 36288;
    public static final int GL_SAMPLER_2D_ARRAY_EXT = 36289;
    public static final int GL_SAMPLER_BUFFER_EXT = 36290;
    public static final int GL_SAMPLER_1D_ARRAY_SHADOW_EXT = 36291;
    public static final int GL_SAMPLER_2D_ARRAY_SHADOW_EXT = 36292;
    public static final int GL_SAMPLER_CUBE_SHADOW_EXT = 36293;
    public static final int GL_UNSIGNED_INT_VEC2_EXT = 36294;
    public static final int GL_UNSIGNED_INT_VEC3_EXT = 36295;
    public static final int GL_UNSIGNED_INT_VEC4_EXT = 36296;
    public static final int GL_INT_SAMPLER_1D_EXT = 36297;
    public static final int GL_INT_SAMPLER_2D_EXT = 36298;
    public static final int GL_INT_SAMPLER_3D_EXT = 36299;
    public static final int GL_INT_SAMPLER_CUBE_EXT = 36300;
    public static final int GL_INT_SAMPLER_2D_RECT_EXT = 36301;
    public static final int GL_INT_SAMPLER_1D_ARRAY_EXT = 36302;
    public static final int GL_INT_SAMPLER_2D_ARRAY_EXT = 36303;
    public static final int GL_INT_SAMPLER_BUFFER_EXT = 36304;
    public static final int GL_UNSIGNED_INT_SAMPLER_1D_EXT = 36305;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_EXT = 36306;
    public static final int GL_UNSIGNED_INT_SAMPLER_3D_EXT = 36307;
    public static final int GL_UNSIGNED_INT_SAMPLER_CUBE_EXT = 36308;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_RECT_EXT = 36309;
    public static final int GL_UNSIGNED_INT_SAMPLER_1D_ARRAY_EXT = 36310;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_ARRAY_EXT = 36311;
    public static final int GL_UNSIGNED_INT_SAMPLER_BUFFER_EXT = 36312;
    public static final int GL_MIN_PROGRAM_TEXEL_OFFSET_EXT = 35076;
    public static final int GL_MAX_PROGRAM_TEXEL_OFFSET_EXT = 35077;
    
    private EXTGpuShader4() {
    }
    
    public static void glVertexAttribI1iEXT(final int n, final int n2) {
        final long glVertexAttribI1iEXT = GLContext.getCapabilities().glVertexAttribI1iEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI1iEXT);
        nglVertexAttribI1iEXT(n, n2, glVertexAttribI1iEXT);
    }
    
    static native void nglVertexAttribI1iEXT(final int p0, final int p1, final long p2);
    
    public static void glVertexAttribI2iEXT(final int n, final int n2, final int n3) {
        final long glVertexAttribI2iEXT = GLContext.getCapabilities().glVertexAttribI2iEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI2iEXT);
        nglVertexAttribI2iEXT(n, n2, n3, glVertexAttribI2iEXT);
    }
    
    static native void nglVertexAttribI2iEXT(final int p0, final int p1, final int p2, final long p3);
    
    public static void glVertexAttribI3iEXT(final int n, final int n2, final int n3, final int n4) {
        final long glVertexAttribI3iEXT = GLContext.getCapabilities().glVertexAttribI3iEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI3iEXT);
        nglVertexAttribI3iEXT(n, n2, n3, n4, glVertexAttribI3iEXT);
    }
    
    static native void nglVertexAttribI3iEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glVertexAttribI4iEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glVertexAttribI4iEXT = GLContext.getCapabilities().glVertexAttribI4iEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI4iEXT);
        nglVertexAttribI4iEXT(n, n2, n3, n4, n5, glVertexAttribI4iEXT);
    }
    
    static native void nglVertexAttribI4iEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glVertexAttribI1uiEXT(final int n, final int n2) {
        final long glVertexAttribI1uiEXT = GLContext.getCapabilities().glVertexAttribI1uiEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI1uiEXT);
        nglVertexAttribI1uiEXT(n, n2, glVertexAttribI1uiEXT);
    }
    
    static native void nglVertexAttribI1uiEXT(final int p0, final int p1, final long p2);
    
    public static void glVertexAttribI2uiEXT(final int n, final int n2, final int n3) {
        final long glVertexAttribI2uiEXT = GLContext.getCapabilities().glVertexAttribI2uiEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI2uiEXT);
        nglVertexAttribI2uiEXT(n, n2, n3, glVertexAttribI2uiEXT);
    }
    
    static native void nglVertexAttribI2uiEXT(final int p0, final int p1, final int p2, final long p3);
    
    public static void glVertexAttribI3uiEXT(final int n, final int n2, final int n3, final int n4) {
        final long glVertexAttribI3uiEXT = GLContext.getCapabilities().glVertexAttribI3uiEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI3uiEXT);
        nglVertexAttribI3uiEXT(n, n2, n3, n4, glVertexAttribI3uiEXT);
    }
    
    static native void nglVertexAttribI3uiEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glVertexAttribI4uiEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glVertexAttribI4uiEXT = GLContext.getCapabilities().glVertexAttribI4uiEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI4uiEXT);
        nglVertexAttribI4uiEXT(n, n2, n3, n4, n5, glVertexAttribI4uiEXT);
    }
    
    static native void nglVertexAttribI4uiEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glVertexAttribI1EXT(final int n, final IntBuffer intBuffer) {
        final long glVertexAttribI1ivEXT = GLContext.getCapabilities().glVertexAttribI1ivEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI1ivEXT);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglVertexAttribI1ivEXT(n, MemoryUtil.getAddress(intBuffer), glVertexAttribI1ivEXT);
    }
    
    static native void nglVertexAttribI1ivEXT(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI2EXT(final int n, final IntBuffer intBuffer) {
        final long glVertexAttribI2ivEXT = GLContext.getCapabilities().glVertexAttribI2ivEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI2ivEXT);
        BufferChecks.checkBuffer(intBuffer, 2);
        nglVertexAttribI2ivEXT(n, MemoryUtil.getAddress(intBuffer), glVertexAttribI2ivEXT);
    }
    
    static native void nglVertexAttribI2ivEXT(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI3EXT(final int n, final IntBuffer intBuffer) {
        final long glVertexAttribI3ivEXT = GLContext.getCapabilities().glVertexAttribI3ivEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI3ivEXT);
        BufferChecks.checkBuffer(intBuffer, 3);
        nglVertexAttribI3ivEXT(n, MemoryUtil.getAddress(intBuffer), glVertexAttribI3ivEXT);
    }
    
    static native void nglVertexAttribI3ivEXT(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI4EXT(final int n, final IntBuffer intBuffer) {
        final long glVertexAttribI4ivEXT = GLContext.getCapabilities().glVertexAttribI4ivEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI4ivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglVertexAttribI4ivEXT(n, MemoryUtil.getAddress(intBuffer), glVertexAttribI4ivEXT);
    }
    
    static native void nglVertexAttribI4ivEXT(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI1uEXT(final int n, final IntBuffer intBuffer) {
        final long glVertexAttribI1uivEXT = GLContext.getCapabilities().glVertexAttribI1uivEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI1uivEXT);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglVertexAttribI1uivEXT(n, MemoryUtil.getAddress(intBuffer), glVertexAttribI1uivEXT);
    }
    
    static native void nglVertexAttribI1uivEXT(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI2uEXT(final int n, final IntBuffer intBuffer) {
        final long glVertexAttribI2uivEXT = GLContext.getCapabilities().glVertexAttribI2uivEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI2uivEXT);
        BufferChecks.checkBuffer(intBuffer, 2);
        nglVertexAttribI2uivEXT(n, MemoryUtil.getAddress(intBuffer), glVertexAttribI2uivEXT);
    }
    
    static native void nglVertexAttribI2uivEXT(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI3uEXT(final int n, final IntBuffer intBuffer) {
        final long glVertexAttribI3uivEXT = GLContext.getCapabilities().glVertexAttribI3uivEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI3uivEXT);
        BufferChecks.checkBuffer(intBuffer, 3);
        nglVertexAttribI3uivEXT(n, MemoryUtil.getAddress(intBuffer), glVertexAttribI3uivEXT);
    }
    
    static native void nglVertexAttribI3uivEXT(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI4uEXT(final int n, final IntBuffer intBuffer) {
        final long glVertexAttribI4uivEXT = GLContext.getCapabilities().glVertexAttribI4uivEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI4uivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglVertexAttribI4uivEXT(n, MemoryUtil.getAddress(intBuffer), glVertexAttribI4uivEXT);
    }
    
    static native void nglVertexAttribI4uivEXT(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI4EXT(final int n, final ByteBuffer byteBuffer) {
        final long glVertexAttribI4bvEXT = GLContext.getCapabilities().glVertexAttribI4bvEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI4bvEXT);
        BufferChecks.checkBuffer(byteBuffer, 4);
        nglVertexAttribI4bvEXT(n, MemoryUtil.getAddress(byteBuffer), glVertexAttribI4bvEXT);
    }
    
    static native void nglVertexAttribI4bvEXT(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI4EXT(final int n, final ShortBuffer shortBuffer) {
        final long glVertexAttribI4svEXT = GLContext.getCapabilities().glVertexAttribI4svEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI4svEXT);
        BufferChecks.checkBuffer(shortBuffer, 4);
        nglVertexAttribI4svEXT(n, MemoryUtil.getAddress(shortBuffer), glVertexAttribI4svEXT);
    }
    
    static native void nglVertexAttribI4svEXT(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI4uEXT(final int n, final ByteBuffer byteBuffer) {
        final long glVertexAttribI4ubvEXT = GLContext.getCapabilities().glVertexAttribI4ubvEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI4ubvEXT);
        BufferChecks.checkBuffer(byteBuffer, 4);
        nglVertexAttribI4ubvEXT(n, MemoryUtil.getAddress(byteBuffer), glVertexAttribI4ubvEXT);
    }
    
    static native void nglVertexAttribI4ubvEXT(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI4uEXT(final int n, final ShortBuffer shortBuffer) {
        final long glVertexAttribI4usvEXT = GLContext.getCapabilities().glVertexAttribI4usvEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribI4usvEXT);
        BufferChecks.checkBuffer(shortBuffer, 4);
        nglVertexAttribI4usvEXT(n, MemoryUtil.getAddress(shortBuffer), glVertexAttribI4usvEXT);
    }
    
    static native void nglVertexAttribI4usvEXT(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribIPointerEXT(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribIPointerEXT = capabilities.glVertexAttribIPointerEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribIPointerEXT);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = byteBuffer;
        }
        nglVertexAttribIPointerEXT(n, n2, n3, n4, MemoryUtil.getAddress(byteBuffer), glVertexAttribIPointerEXT);
    }
    
    public static void glVertexAttribIPointerEXT(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribIPointerEXT = capabilities.glVertexAttribIPointerEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribIPointerEXT);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = intBuffer;
        }
        nglVertexAttribIPointerEXT(n, n2, n3, n4, MemoryUtil.getAddress(intBuffer), glVertexAttribIPointerEXT);
    }
    
    public static void glVertexAttribIPointerEXT(final int n, final int n2, final int n3, final int n4, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribIPointerEXT = capabilities.glVertexAttribIPointerEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribIPointerEXT);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = shortBuffer;
        }
        nglVertexAttribIPointerEXT(n, n2, n3, n4, MemoryUtil.getAddress(shortBuffer), glVertexAttribIPointerEXT);
    }
    
    static native void nglVertexAttribIPointerEXT(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glVertexAttribIPointerEXT(final int n, final int n2, final int n3, final int n4, final long n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribIPointerEXT = capabilities.glVertexAttribIPointerEXT;
        BufferChecks.checkFunctionAddress(glVertexAttribIPointerEXT);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglVertexAttribIPointerEXTBO(n, n2, n3, n4, n5, glVertexAttribIPointerEXT);
    }
    
    static native void nglVertexAttribIPointerEXTBO(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glGetVertexAttribIEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetVertexAttribIivEXT = GLContext.getCapabilities().glGetVertexAttribIivEXT;
        BufferChecks.checkFunctionAddress(glGetVertexAttribIivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetVertexAttribIivEXT(n, n2, MemoryUtil.getAddress(intBuffer), glGetVertexAttribIivEXT);
    }
    
    static native void nglGetVertexAttribIivEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetVertexAttribIuEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetVertexAttribIuivEXT = GLContext.getCapabilities().glGetVertexAttribIuivEXT;
        BufferChecks.checkFunctionAddress(glGetVertexAttribIuivEXT);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetVertexAttribIuivEXT(n, n2, MemoryUtil.getAddress(intBuffer), glGetVertexAttribIuivEXT);
    }
    
    static native void nglGetVertexAttribIuivEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform1uiEXT(final int n, final int n2) {
        final long glUniform1uiEXT = GLContext.getCapabilities().glUniform1uiEXT;
        BufferChecks.checkFunctionAddress(glUniform1uiEXT);
        nglUniform1uiEXT(n, n2, glUniform1uiEXT);
    }
    
    static native void nglUniform1uiEXT(final int p0, final int p1, final long p2);
    
    public static void glUniform2uiEXT(final int n, final int n2, final int n3) {
        final long glUniform2uiEXT = GLContext.getCapabilities().glUniform2uiEXT;
        BufferChecks.checkFunctionAddress(glUniform2uiEXT);
        nglUniform2uiEXT(n, n2, n3, glUniform2uiEXT);
    }
    
    static native void nglUniform2uiEXT(final int p0, final int p1, final int p2, final long p3);
    
    public static void glUniform3uiEXT(final int n, final int n2, final int n3, final int n4) {
        final long glUniform3uiEXT = GLContext.getCapabilities().glUniform3uiEXT;
        BufferChecks.checkFunctionAddress(glUniform3uiEXT);
        nglUniform3uiEXT(n, n2, n3, n4, glUniform3uiEXT);
    }
    
    static native void nglUniform3uiEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glUniform4uiEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glUniform4uiEXT = GLContext.getCapabilities().glUniform4uiEXT;
        BufferChecks.checkFunctionAddress(glUniform4uiEXT);
        nglUniform4uiEXT(n, n2, n3, n4, n5, glUniform4uiEXT);
    }
    
    static native void nglUniform4uiEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glUniform1uEXT(final int n, final IntBuffer intBuffer) {
        final long glUniform1uivEXT = GLContext.getCapabilities().glUniform1uivEXT;
        BufferChecks.checkFunctionAddress(glUniform1uivEXT);
        BufferChecks.checkDirect(intBuffer);
        nglUniform1uivEXT(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glUniform1uivEXT);
    }
    
    static native void nglUniform1uivEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform2uEXT(final int n, final IntBuffer intBuffer) {
        final long glUniform2uivEXT = GLContext.getCapabilities().glUniform2uivEXT;
        BufferChecks.checkFunctionAddress(glUniform2uivEXT);
        BufferChecks.checkDirect(intBuffer);
        nglUniform2uivEXT(n, intBuffer.remaining() >> 1, MemoryUtil.getAddress(intBuffer), glUniform2uivEXT);
    }
    
    static native void nglUniform2uivEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform3uEXT(final int n, final IntBuffer intBuffer) {
        final long glUniform3uivEXT = GLContext.getCapabilities().glUniform3uivEXT;
        BufferChecks.checkFunctionAddress(glUniform3uivEXT);
        BufferChecks.checkDirect(intBuffer);
        nglUniform3uivEXT(n, intBuffer.remaining() / 3, MemoryUtil.getAddress(intBuffer), glUniform3uivEXT);
    }
    
    static native void nglUniform3uivEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform4uEXT(final int n, final IntBuffer intBuffer) {
        final long glUniform4uivEXT = GLContext.getCapabilities().glUniform4uivEXT;
        BufferChecks.checkFunctionAddress(glUniform4uivEXT);
        BufferChecks.checkDirect(intBuffer);
        nglUniform4uivEXT(n, intBuffer.remaining() >> 2, MemoryUtil.getAddress(intBuffer), glUniform4uivEXT);
    }
    
    static native void nglUniform4uivEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetUniformuEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetUniformuivEXT = GLContext.getCapabilities().glGetUniformuivEXT;
        BufferChecks.checkFunctionAddress(glGetUniformuivEXT);
        BufferChecks.checkDirect(intBuffer);
        nglGetUniformuivEXT(n, n2, MemoryUtil.getAddress(intBuffer), glGetUniformuivEXT);
    }
    
    static native void nglGetUniformuivEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glBindFragDataLocationEXT(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glBindFragDataLocationEXT = GLContext.getCapabilities().glBindFragDataLocationEXT;
        BufferChecks.checkFunctionAddress(glBindFragDataLocationEXT);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        nglBindFragDataLocationEXT(n, n2, MemoryUtil.getAddress(byteBuffer), glBindFragDataLocationEXT);
    }
    
    static native void nglBindFragDataLocationEXT(final int p0, final int p1, final long p2, final long p3);
    
    public static void glBindFragDataLocationEXT(final int n, final int n2, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glBindFragDataLocationEXT = capabilities.glBindFragDataLocationEXT;
        BufferChecks.checkFunctionAddress(glBindFragDataLocationEXT);
        nglBindFragDataLocationEXT(n, n2, APIUtil.getBufferNT(capabilities, charSequence), glBindFragDataLocationEXT);
    }
    
    public static int glGetFragDataLocationEXT(final int n, final ByteBuffer byteBuffer) {
        final long glGetFragDataLocationEXT = GLContext.getCapabilities().glGetFragDataLocationEXT;
        BufferChecks.checkFunctionAddress(glGetFragDataLocationEXT);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        return nglGetFragDataLocationEXT(n, MemoryUtil.getAddress(byteBuffer), glGetFragDataLocationEXT);
    }
    
    static native int nglGetFragDataLocationEXT(final int p0, final long p1, final long p2);
    
    public static int glGetFragDataLocationEXT(final int n, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetFragDataLocationEXT = capabilities.glGetFragDataLocationEXT;
        BufferChecks.checkFunctionAddress(glGetFragDataLocationEXT);
        return nglGetFragDataLocationEXT(n, APIUtil.getBufferNT(capabilities, charSequence), glGetFragDataLocationEXT);
    }
}
