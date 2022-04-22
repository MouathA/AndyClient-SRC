package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class GL41
{
    public static final int GL_SHADER_COMPILER = 36346;
    public static final int GL_NUM_SHADER_BINARY_FORMATS = 36345;
    public static final int GL_MAX_VERTEX_UNIFORM_VECTORS = 36347;
    public static final int GL_MAX_VARYING_VECTORS = 36348;
    public static final int GL_MAX_FRAGMENT_UNIFORM_VECTORS = 36349;
    public static final int GL_IMPLEMENTATION_COLOR_READ_TYPE = 35738;
    public static final int GL_IMPLEMENTATION_COLOR_READ_FORMAT = 35739;
    public static final int GL_FIXED = 5132;
    public static final int GL_LOW_FLOAT = 36336;
    public static final int GL_MEDIUM_FLOAT = 36337;
    public static final int GL_HIGH_FLOAT = 36338;
    public static final int GL_LOW_INT = 36339;
    public static final int GL_MEDIUM_INT = 36340;
    public static final int GL_HIGH_INT = 36341;
    public static final int GL_RGB565 = 36194;
    public static final int GL_PROGRAM_BINARY_RETRIEVABLE_HINT = 33367;
    public static final int GL_PROGRAM_BINARY_LENGTH = 34625;
    public static final int GL_NUM_PROGRAM_BINARY_FORMATS = 34814;
    public static final int GL_PROGRAM_BINARY_FORMATS = 34815;
    public static final int GL_VERTEX_SHADER_BIT = 1;
    public static final int GL_FRAGMENT_SHADER_BIT = 2;
    public static final int GL_GEOMETRY_SHADER_BIT = 4;
    public static final int GL_TESS_CONTROL_SHADER_BIT = 8;
    public static final int GL_TESS_EVALUATION_SHADER_BIT = 16;
    public static final int GL_ALL_SHADER_BITS = -1;
    public static final int GL_PROGRAM_SEPARABLE = 33368;
    public static final int GL_ACTIVE_PROGRAM = 33369;
    public static final int GL_PROGRAM_PIPELINE_BINDING = 33370;
    public static final int GL_MAX_VIEWPORTS = 33371;
    public static final int GL_VIEWPORT_SUBPIXEL_BITS = 33372;
    public static final int GL_VIEWPORT_BOUNDS_RANGE = 33373;
    public static final int GL_LAYER_PROVOKING_VERTEX = 33374;
    public static final int GL_VIEWPORT_INDEX_PROVOKING_VERTEX = 33375;
    public static final int GL_UNDEFINED_VERTEX = 33376;
    
    private GL41() {
    }
    
    public static void glReleaseShaderCompiler() {
        final long glReleaseShaderCompiler = GLContext.getCapabilities().glReleaseShaderCompiler;
        BufferChecks.checkFunctionAddress(glReleaseShaderCompiler);
        nglReleaseShaderCompiler(glReleaseShaderCompiler);
    }
    
    static native void nglReleaseShaderCompiler(final long p0);
    
    public static void glShaderBinary(final IntBuffer intBuffer, final int n, final ByteBuffer byteBuffer) {
        final long glShaderBinary = GLContext.getCapabilities().glShaderBinary;
        BufferChecks.checkFunctionAddress(glShaderBinary);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglShaderBinary(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), n, MemoryUtil.getAddress(byteBuffer), byteBuffer.remaining(), glShaderBinary);
    }
    
    static native void nglShaderBinary(final int p0, final long p1, final int p2, final long p3, final int p4, final long p5);
    
    public static void glGetShaderPrecisionFormat(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long glGetShaderPrecisionFormat = GLContext.getCapabilities().glGetShaderPrecisionFormat;
        BufferChecks.checkFunctionAddress(glGetShaderPrecisionFormat);
        BufferChecks.checkBuffer(intBuffer, 2);
        BufferChecks.checkBuffer(intBuffer2, 1);
        nglGetShaderPrecisionFormat(n, n2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), glGetShaderPrecisionFormat);
    }
    
    static native void nglGetShaderPrecisionFormat(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static void glDepthRangef(final float n, final float n2) {
        final long glDepthRangef = GLContext.getCapabilities().glDepthRangef;
        BufferChecks.checkFunctionAddress(glDepthRangef);
        nglDepthRangef(n, n2, glDepthRangef);
    }
    
    static native void nglDepthRangef(final float p0, final float p1, final long p2);
    
    public static void glClearDepthf(final float n) {
        final long glClearDepthf = GLContext.getCapabilities().glClearDepthf;
        BufferChecks.checkFunctionAddress(glClearDepthf);
        nglClearDepthf(n, glClearDepthf);
    }
    
    static native void nglClearDepthf(final float p0, final long p1);
    
    public static void glGetProgramBinary(final int n, final IntBuffer intBuffer, final IntBuffer intBuffer2, final ByteBuffer byteBuffer) {
        final long glGetProgramBinary = GLContext.getCapabilities().glGetProgramBinary;
        BufferChecks.checkFunctionAddress(glGetProgramBinary);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkBuffer(intBuffer2, 1);
        BufferChecks.checkDirect(byteBuffer);
        nglGetProgramBinary(n, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(byteBuffer), glGetProgramBinary);
    }
    
    static native void nglGetProgramBinary(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static void glProgramBinary(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glProgramBinary = GLContext.getCapabilities().glProgramBinary;
        BufferChecks.checkFunctionAddress(glProgramBinary);
        BufferChecks.checkDirect(byteBuffer);
        nglProgramBinary(n, n2, MemoryUtil.getAddress(byteBuffer), byteBuffer.remaining(), glProgramBinary);
    }
    
    static native void nglProgramBinary(final int p0, final int p1, final long p2, final int p3, final long p4);
    
    public static void glProgramParameteri(final int n, final int n2, final int n3) {
        final long glProgramParameteri = GLContext.getCapabilities().glProgramParameteri;
        BufferChecks.checkFunctionAddress(glProgramParameteri);
        nglProgramParameteri(n, n2, n3, glProgramParameteri);
    }
    
    static native void nglProgramParameteri(final int p0, final int p1, final int p2, final long p3);
    
    public static void glUseProgramStages(final int n, final int n2, final int n3) {
        final long glUseProgramStages = GLContext.getCapabilities().glUseProgramStages;
        BufferChecks.checkFunctionAddress(glUseProgramStages);
        nglUseProgramStages(n, n2, n3, glUseProgramStages);
    }
    
    static native void nglUseProgramStages(final int p0, final int p1, final int p2, final long p3);
    
    public static void glActiveShaderProgram(final int n, final int n2) {
        final long glActiveShaderProgram = GLContext.getCapabilities().glActiveShaderProgram;
        BufferChecks.checkFunctionAddress(glActiveShaderProgram);
        nglActiveShaderProgram(n, n2, glActiveShaderProgram);
    }
    
    static native void nglActiveShaderProgram(final int p0, final int p1, final long p2);
    
    public static int glCreateShaderProgram(final int n, final ByteBuffer byteBuffer) {
        final long glCreateShaderProgramv = GLContext.getCapabilities().glCreateShaderProgramv;
        BufferChecks.checkFunctionAddress(glCreateShaderProgramv);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        return nglCreateShaderProgramv(n, 1, MemoryUtil.getAddress(byteBuffer), glCreateShaderProgramv);
    }
    
    static native int nglCreateShaderProgramv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glCreateShaderProgram(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glCreateShaderProgramv = GLContext.getCapabilities().glCreateShaderProgramv;
        BufferChecks.checkFunctionAddress(glCreateShaderProgramv);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer, n2);
        return nglCreateShaderProgramv2(n, n2, MemoryUtil.getAddress(byteBuffer), glCreateShaderProgramv);
    }
    
    static native int nglCreateShaderProgramv2(final int p0, final int p1, final long p2, final long p3);
    
    public static int glCreateShaderProgram(final int n, final ByteBuffer[] array) {
        final long glCreateShaderProgramv = GLContext.getCapabilities().glCreateShaderProgramv;
        BufferChecks.checkFunctionAddress(glCreateShaderProgramv);
        BufferChecks.checkArray(array, 1);
        return nglCreateShaderProgramv3(n, array.length, array, glCreateShaderProgramv);
    }
    
    static native int nglCreateShaderProgramv3(final int p0, final int p1, final ByteBuffer[] p2, final long p3);
    
    public static int glCreateShaderProgram(final int n, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCreateShaderProgramv = capabilities.glCreateShaderProgramv;
        BufferChecks.checkFunctionAddress(glCreateShaderProgramv);
        return nglCreateShaderProgramv(n, 1, APIUtil.getBufferNT(capabilities, charSequence), glCreateShaderProgramv);
    }
    
    public static int glCreateShaderProgram(final int n, final CharSequence[] array) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCreateShaderProgramv = capabilities.glCreateShaderProgramv;
        BufferChecks.checkFunctionAddress(glCreateShaderProgramv);
        BufferChecks.checkArray(array);
        return nglCreateShaderProgramv2(n, array.length, APIUtil.getBufferNT(capabilities, array), glCreateShaderProgramv);
    }
    
    public static void glBindProgramPipeline(final int n) {
        final long glBindProgramPipeline = GLContext.getCapabilities().glBindProgramPipeline;
        BufferChecks.checkFunctionAddress(glBindProgramPipeline);
        nglBindProgramPipeline(n, glBindProgramPipeline);
    }
    
    static native void nglBindProgramPipeline(final int p0, final long p1);
    
    public static void glDeleteProgramPipelines(final IntBuffer intBuffer) {
        final long glDeleteProgramPipelines = GLContext.getCapabilities().glDeleteProgramPipelines;
        BufferChecks.checkFunctionAddress(glDeleteProgramPipelines);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteProgramPipelines(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteProgramPipelines);
    }
    
    static native void nglDeleteProgramPipelines(final int p0, final long p1, final long p2);
    
    public static void glDeleteProgramPipelines(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteProgramPipelines = capabilities.glDeleteProgramPipelines;
        BufferChecks.checkFunctionAddress(glDeleteProgramPipelines);
        nglDeleteProgramPipelines(1, APIUtil.getInt(capabilities, n), glDeleteProgramPipelines);
    }
    
    public static void glGenProgramPipelines(final IntBuffer intBuffer) {
        final long glGenProgramPipelines = GLContext.getCapabilities().glGenProgramPipelines;
        BufferChecks.checkFunctionAddress(glGenProgramPipelines);
        BufferChecks.checkDirect(intBuffer);
        nglGenProgramPipelines(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenProgramPipelines);
    }
    
    static native void nglGenProgramPipelines(final int p0, final long p1, final long p2);
    
    public static int glGenProgramPipelines() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenProgramPipelines = capabilities.glGenProgramPipelines;
        BufferChecks.checkFunctionAddress(glGenProgramPipelines);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenProgramPipelines(1, MemoryUtil.getAddress(bufferInt), glGenProgramPipelines);
        return bufferInt.get(0);
    }
    
    public static boolean glIsProgramPipeline(final int n) {
        final long glIsProgramPipeline = GLContext.getCapabilities().glIsProgramPipeline;
        BufferChecks.checkFunctionAddress(glIsProgramPipeline);
        return nglIsProgramPipeline(n, glIsProgramPipeline);
    }
    
    static native boolean nglIsProgramPipeline(final int p0, final long p1);
    
    public static void glGetProgramPipeline(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetProgramPipelineiv = GLContext.getCapabilities().glGetProgramPipelineiv;
        BufferChecks.checkFunctionAddress(glGetProgramPipelineiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetProgramPipelineiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetProgramPipelineiv);
    }
    
    static native void nglGetProgramPipelineiv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetProgramPipelinei(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetProgramPipelineiv = capabilities.glGetProgramPipelineiv;
        BufferChecks.checkFunctionAddress(glGetProgramPipelineiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetProgramPipelineiv(n, n2, MemoryUtil.getAddress(bufferInt), glGetProgramPipelineiv);
        return bufferInt.get(0);
    }
    
    public static void glProgramUniform1i(final int n, final int n2, final int n3) {
        final long glProgramUniform1i = GLContext.getCapabilities().glProgramUniform1i;
        BufferChecks.checkFunctionAddress(glProgramUniform1i);
        nglProgramUniform1i(n, n2, n3, glProgramUniform1i);
    }
    
    static native void nglProgramUniform1i(final int p0, final int p1, final int p2, final long p3);
    
    public static void glProgramUniform2i(final int n, final int n2, final int n3, final int n4) {
        final long glProgramUniform2i = GLContext.getCapabilities().glProgramUniform2i;
        BufferChecks.checkFunctionAddress(glProgramUniform2i);
        nglProgramUniform2i(n, n2, n3, n4, glProgramUniform2i);
    }
    
    static native void nglProgramUniform2i(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glProgramUniform3i(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glProgramUniform3i = GLContext.getCapabilities().glProgramUniform3i;
        BufferChecks.checkFunctionAddress(glProgramUniform3i);
        nglProgramUniform3i(n, n2, n3, n4, n5, glProgramUniform3i);
    }
    
    static native void nglProgramUniform3i(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glProgramUniform4i(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glProgramUniform4i = GLContext.getCapabilities().glProgramUniform4i;
        BufferChecks.checkFunctionAddress(glProgramUniform4i);
        nglProgramUniform4i(n, n2, n3, n4, n5, n6, glProgramUniform4i);
    }
    
    static native void nglProgramUniform4i(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glProgramUniform1f(final int n, final int n2, final float n3) {
        final long glProgramUniform1f = GLContext.getCapabilities().glProgramUniform1f;
        BufferChecks.checkFunctionAddress(glProgramUniform1f);
        nglProgramUniform1f(n, n2, n3, glProgramUniform1f);
    }
    
    static native void nglProgramUniform1f(final int p0, final int p1, final float p2, final long p3);
    
    public static void glProgramUniform2f(final int n, final int n2, final float n3, final float n4) {
        final long glProgramUniform2f = GLContext.getCapabilities().glProgramUniform2f;
        BufferChecks.checkFunctionAddress(glProgramUniform2f);
        nglProgramUniform2f(n, n2, n3, n4, glProgramUniform2f);
    }
    
    static native void nglProgramUniform2f(final int p0, final int p1, final float p2, final float p3, final long p4);
    
    public static void glProgramUniform3f(final int n, final int n2, final float n3, final float n4, final float n5) {
        final long glProgramUniform3f = GLContext.getCapabilities().glProgramUniform3f;
        BufferChecks.checkFunctionAddress(glProgramUniform3f);
        nglProgramUniform3f(n, n2, n3, n4, n5, glProgramUniform3f);
    }
    
    static native void nglProgramUniform3f(final int p0, final int p1, final float p2, final float p3, final float p4, final long p5);
    
    public static void glProgramUniform4f(final int n, final int n2, final float n3, final float n4, final float n5, final float n6) {
        final long glProgramUniform4f = GLContext.getCapabilities().glProgramUniform4f;
        BufferChecks.checkFunctionAddress(glProgramUniform4f);
        nglProgramUniform4f(n, n2, n3, n4, n5, n6, glProgramUniform4f);
    }
    
    static native void nglProgramUniform4f(final int p0, final int p1, final float p2, final float p3, final float p4, final float p5, final long p6);
    
    public static void glProgramUniform1d(final int n, final int n2, final double n3) {
        final long glProgramUniform1d = GLContext.getCapabilities().glProgramUniform1d;
        BufferChecks.checkFunctionAddress(glProgramUniform1d);
        nglProgramUniform1d(n, n2, n3, glProgramUniform1d);
    }
    
    static native void nglProgramUniform1d(final int p0, final int p1, final double p2, final long p3);
    
    public static void glProgramUniform2d(final int n, final int n2, final double n3, final double n4) {
        final long glProgramUniform2d = GLContext.getCapabilities().glProgramUniform2d;
        BufferChecks.checkFunctionAddress(glProgramUniform2d);
        nglProgramUniform2d(n, n2, n3, n4, glProgramUniform2d);
    }
    
    static native void nglProgramUniform2d(final int p0, final int p1, final double p2, final double p3, final long p4);
    
    public static void glProgramUniform3d(final int n, final int n2, final double n3, final double n4, final double n5) {
        final long glProgramUniform3d = GLContext.getCapabilities().glProgramUniform3d;
        BufferChecks.checkFunctionAddress(glProgramUniform3d);
        nglProgramUniform3d(n, n2, n3, n4, n5, glProgramUniform3d);
    }
    
    static native void nglProgramUniform3d(final int p0, final int p1, final double p2, final double p3, final double p4, final long p5);
    
    public static void glProgramUniform4d(final int n, final int n2, final double n3, final double n4, final double n5, final double n6) {
        final long glProgramUniform4d = GLContext.getCapabilities().glProgramUniform4d;
        BufferChecks.checkFunctionAddress(glProgramUniform4d);
        nglProgramUniform4d(n, n2, n3, n4, n5, n6, glProgramUniform4d);
    }
    
    static native void nglProgramUniform4d(final int p0, final int p1, final double p2, final double p3, final double p4, final double p5, final long p6);
    
    public static void glProgramUniform1(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramUniform1iv = GLContext.getCapabilities().glProgramUniform1iv;
        BufferChecks.checkFunctionAddress(glProgramUniform1iv);
        BufferChecks.checkDirect(intBuffer);
        nglProgramUniform1iv(n, n2, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glProgramUniform1iv);
    }
    
    static native void nglProgramUniform1iv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform2(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramUniform2iv = GLContext.getCapabilities().glProgramUniform2iv;
        BufferChecks.checkFunctionAddress(glProgramUniform2iv);
        BufferChecks.checkDirect(intBuffer);
        nglProgramUniform2iv(n, n2, intBuffer.remaining() >> 1, MemoryUtil.getAddress(intBuffer), glProgramUniform2iv);
    }
    
    static native void nglProgramUniform2iv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform3(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramUniform3iv = GLContext.getCapabilities().glProgramUniform3iv;
        BufferChecks.checkFunctionAddress(glProgramUniform3iv);
        BufferChecks.checkDirect(intBuffer);
        nglProgramUniform3iv(n, n2, intBuffer.remaining() / 3, MemoryUtil.getAddress(intBuffer), glProgramUniform3iv);
    }
    
    static native void nglProgramUniform3iv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform4(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramUniform4iv = GLContext.getCapabilities().glProgramUniform4iv;
        BufferChecks.checkFunctionAddress(glProgramUniform4iv);
        BufferChecks.checkDirect(intBuffer);
        nglProgramUniform4iv(n, n2, intBuffer.remaining() >> 2, MemoryUtil.getAddress(intBuffer), glProgramUniform4iv);
    }
    
    static native void nglProgramUniform4iv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform1(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glProgramUniform1fv = GLContext.getCapabilities().glProgramUniform1fv;
        BufferChecks.checkFunctionAddress(glProgramUniform1fv);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniform1fv(n, n2, floatBuffer.remaining(), MemoryUtil.getAddress(floatBuffer), glProgramUniform1fv);
    }
    
    static native void nglProgramUniform1fv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform2(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glProgramUniform2fv = GLContext.getCapabilities().glProgramUniform2fv;
        BufferChecks.checkFunctionAddress(glProgramUniform2fv);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniform2fv(n, n2, floatBuffer.remaining() >> 1, MemoryUtil.getAddress(floatBuffer), glProgramUniform2fv);
    }
    
    static native void nglProgramUniform2fv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform3(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glProgramUniform3fv = GLContext.getCapabilities().glProgramUniform3fv;
        BufferChecks.checkFunctionAddress(glProgramUniform3fv);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniform3fv(n, n2, floatBuffer.remaining() / 3, MemoryUtil.getAddress(floatBuffer), glProgramUniform3fv);
    }
    
    static native void nglProgramUniform3fv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform4(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glProgramUniform4fv = GLContext.getCapabilities().glProgramUniform4fv;
        BufferChecks.checkFunctionAddress(glProgramUniform4fv);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniform4fv(n, n2, floatBuffer.remaining() >> 2, MemoryUtil.getAddress(floatBuffer), glProgramUniform4fv);
    }
    
    static native void nglProgramUniform4fv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform1(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glProgramUniform1dv = GLContext.getCapabilities().glProgramUniform1dv;
        BufferChecks.checkFunctionAddress(glProgramUniform1dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniform1dv(n, n2, doubleBuffer.remaining(), MemoryUtil.getAddress(doubleBuffer), glProgramUniform1dv);
    }
    
    static native void nglProgramUniform1dv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform2(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glProgramUniform2dv = GLContext.getCapabilities().glProgramUniform2dv;
        BufferChecks.checkFunctionAddress(glProgramUniform2dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniform2dv(n, n2, doubleBuffer.remaining() >> 1, MemoryUtil.getAddress(doubleBuffer), glProgramUniform2dv);
    }
    
    static native void nglProgramUniform2dv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform3(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glProgramUniform3dv = GLContext.getCapabilities().glProgramUniform3dv;
        BufferChecks.checkFunctionAddress(glProgramUniform3dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniform3dv(n, n2, doubleBuffer.remaining() / 3, MemoryUtil.getAddress(doubleBuffer), glProgramUniform3dv);
    }
    
    static native void nglProgramUniform3dv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform4(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glProgramUniform4dv = GLContext.getCapabilities().glProgramUniform4dv;
        BufferChecks.checkFunctionAddress(glProgramUniform4dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniform4dv(n, n2, doubleBuffer.remaining() >> 2, MemoryUtil.getAddress(doubleBuffer), glProgramUniform4dv);
    }
    
    static native void nglProgramUniform4dv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform1ui(final int n, final int n2, final int n3) {
        final long glProgramUniform1ui = GLContext.getCapabilities().glProgramUniform1ui;
        BufferChecks.checkFunctionAddress(glProgramUniform1ui);
        nglProgramUniform1ui(n, n2, n3, glProgramUniform1ui);
    }
    
    static native void nglProgramUniform1ui(final int p0, final int p1, final int p2, final long p3);
    
    public static void glProgramUniform2ui(final int n, final int n2, final int n3, final int n4) {
        final long glProgramUniform2ui = GLContext.getCapabilities().glProgramUniform2ui;
        BufferChecks.checkFunctionAddress(glProgramUniform2ui);
        nglProgramUniform2ui(n, n2, n3, n4, glProgramUniform2ui);
    }
    
    static native void nglProgramUniform2ui(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glProgramUniform3ui(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glProgramUniform3ui = GLContext.getCapabilities().glProgramUniform3ui;
        BufferChecks.checkFunctionAddress(glProgramUniform3ui);
        nglProgramUniform3ui(n, n2, n3, n4, n5, glProgramUniform3ui);
    }
    
    static native void nglProgramUniform3ui(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glProgramUniform4ui(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glProgramUniform4ui = GLContext.getCapabilities().glProgramUniform4ui;
        BufferChecks.checkFunctionAddress(glProgramUniform4ui);
        nglProgramUniform4ui(n, n2, n3, n4, n5, n6, glProgramUniform4ui);
    }
    
    static native void nglProgramUniform4ui(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glProgramUniform1u(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramUniform1uiv = GLContext.getCapabilities().glProgramUniform1uiv;
        BufferChecks.checkFunctionAddress(glProgramUniform1uiv);
        BufferChecks.checkDirect(intBuffer);
        nglProgramUniform1uiv(n, n2, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glProgramUniform1uiv);
    }
    
    static native void nglProgramUniform1uiv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform2u(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramUniform2uiv = GLContext.getCapabilities().glProgramUniform2uiv;
        BufferChecks.checkFunctionAddress(glProgramUniform2uiv);
        BufferChecks.checkDirect(intBuffer);
        nglProgramUniform2uiv(n, n2, intBuffer.remaining() >> 1, MemoryUtil.getAddress(intBuffer), glProgramUniform2uiv);
    }
    
    static native void nglProgramUniform2uiv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform3u(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramUniform3uiv = GLContext.getCapabilities().glProgramUniform3uiv;
        BufferChecks.checkFunctionAddress(glProgramUniform3uiv);
        BufferChecks.checkDirect(intBuffer);
        nglProgramUniform3uiv(n, n2, intBuffer.remaining() / 3, MemoryUtil.getAddress(intBuffer), glProgramUniform3uiv);
    }
    
    static native void nglProgramUniform3uiv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniform4u(final int n, final int n2, final IntBuffer intBuffer) {
        final long glProgramUniform4uiv = GLContext.getCapabilities().glProgramUniform4uiv;
        BufferChecks.checkFunctionAddress(glProgramUniform4uiv);
        BufferChecks.checkDirect(intBuffer);
        nglProgramUniform4uiv(n, n2, intBuffer.remaining() >> 2, MemoryUtil.getAddress(intBuffer), glProgramUniform4uiv);
    }
    
    static native void nglProgramUniform4uiv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramUniformMatrix2(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix2fv = GLContext.getCapabilities().glProgramUniformMatrix2fv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix2fv);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix2fv(n, n2, floatBuffer.remaining() >> 2, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix2fv);
    }
    
    static native void nglProgramUniformMatrix2fv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix3(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix3fv = GLContext.getCapabilities().glProgramUniformMatrix3fv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix3fv);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix3fv(n, n2, floatBuffer.remaining() / 9, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix3fv);
    }
    
    static native void nglProgramUniformMatrix3fv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix4(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix4fv = GLContext.getCapabilities().glProgramUniformMatrix4fv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix4fv);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix4fv(n, n2, floatBuffer.remaining() >> 4, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix4fv);
    }
    
    static native void nglProgramUniformMatrix4fv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix2(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix2dv = GLContext.getCapabilities().glProgramUniformMatrix2dv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix2dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix2dv(n, n2, doubleBuffer.remaining() >> 2, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix2dv);
    }
    
    static native void nglProgramUniformMatrix2dv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix3(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix3dv = GLContext.getCapabilities().glProgramUniformMatrix3dv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix3dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix3dv(n, n2, doubleBuffer.remaining() / 9, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix3dv);
    }
    
    static native void nglProgramUniformMatrix3dv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix4(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix4dv = GLContext.getCapabilities().glProgramUniformMatrix4dv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix4dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix4dv(n, n2, doubleBuffer.remaining() >> 4, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix4dv);
    }
    
    static native void nglProgramUniformMatrix4dv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix2x3(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix2x3fv = GLContext.getCapabilities().glProgramUniformMatrix2x3fv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix2x3fv);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix2x3fv(n, n2, floatBuffer.remaining() / 6, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix2x3fv);
    }
    
    static native void nglProgramUniformMatrix2x3fv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix3x2(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix3x2fv = GLContext.getCapabilities().glProgramUniformMatrix3x2fv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix3x2fv);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix3x2fv(n, n2, floatBuffer.remaining() / 6, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix3x2fv);
    }
    
    static native void nglProgramUniformMatrix3x2fv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix2x4(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix2x4fv = GLContext.getCapabilities().glProgramUniformMatrix2x4fv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix2x4fv);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix2x4fv(n, n2, floatBuffer.remaining() >> 3, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix2x4fv);
    }
    
    static native void nglProgramUniformMatrix2x4fv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix4x2(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix4x2fv = GLContext.getCapabilities().glProgramUniformMatrix4x2fv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix4x2fv);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix4x2fv(n, n2, floatBuffer.remaining() >> 3, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix4x2fv);
    }
    
    static native void nglProgramUniformMatrix4x2fv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix3x4(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix3x4fv = GLContext.getCapabilities().glProgramUniformMatrix3x4fv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix3x4fv);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix3x4fv(n, n2, floatBuffer.remaining() / 12, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix3x4fv);
    }
    
    static native void nglProgramUniformMatrix3x4fv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix4x3(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final long glProgramUniformMatrix4x3fv = GLContext.getCapabilities().glProgramUniformMatrix4x3fv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix4x3fv);
        BufferChecks.checkDirect(floatBuffer);
        nglProgramUniformMatrix4x3fv(n, n2, floatBuffer.remaining() / 12, b, MemoryUtil.getAddress(floatBuffer), glProgramUniformMatrix4x3fv);
    }
    
    static native void nglProgramUniformMatrix4x3fv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix2x3(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix2x3dv = GLContext.getCapabilities().glProgramUniformMatrix2x3dv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix2x3dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix2x3dv(n, n2, doubleBuffer.remaining() / 6, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix2x3dv);
    }
    
    static native void nglProgramUniformMatrix2x3dv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix3x2(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix3x2dv = GLContext.getCapabilities().glProgramUniformMatrix3x2dv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix3x2dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix3x2dv(n, n2, doubleBuffer.remaining() / 6, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix3x2dv);
    }
    
    static native void nglProgramUniformMatrix3x2dv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix2x4(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix2x4dv = GLContext.getCapabilities().glProgramUniformMatrix2x4dv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix2x4dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix2x4dv(n, n2, doubleBuffer.remaining() >> 3, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix2x4dv);
    }
    
    static native void nglProgramUniformMatrix2x4dv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix4x2(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix4x2dv = GLContext.getCapabilities().glProgramUniformMatrix4x2dv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix4x2dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix4x2dv(n, n2, doubleBuffer.remaining() >> 3, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix4x2dv);
    }
    
    static native void nglProgramUniformMatrix4x2dv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix3x4(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix3x4dv = GLContext.getCapabilities().glProgramUniformMatrix3x4dv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix3x4dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix3x4dv(n, n2, doubleBuffer.remaining() / 12, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix3x4dv);
    }
    
    static native void nglProgramUniformMatrix3x4dv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glProgramUniformMatrix4x3(final int n, final int n2, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glProgramUniformMatrix4x3dv = GLContext.getCapabilities().glProgramUniformMatrix4x3dv;
        BufferChecks.checkFunctionAddress(glProgramUniformMatrix4x3dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglProgramUniformMatrix4x3dv(n, n2, doubleBuffer.remaining() / 12, b, MemoryUtil.getAddress(doubleBuffer), glProgramUniformMatrix4x3dv);
    }
    
    static native void nglProgramUniformMatrix4x3dv(final int p0, final int p1, final int p2, final boolean p3, final long p4, final long p5);
    
    public static void glValidateProgramPipeline(final int n) {
        final long glValidateProgramPipeline = GLContext.getCapabilities().glValidateProgramPipeline;
        BufferChecks.checkFunctionAddress(glValidateProgramPipeline);
        nglValidateProgramPipeline(n, glValidateProgramPipeline);
    }
    
    static native void nglValidateProgramPipeline(final int p0, final long p1);
    
    public static void glGetProgramPipelineInfoLog(final int n, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final long glGetProgramPipelineInfoLog = GLContext.getCapabilities().glGetProgramPipelineInfoLog;
        BufferChecks.checkFunctionAddress(glGetProgramPipelineInfoLog);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(byteBuffer);
        nglGetProgramPipelineInfoLog(n, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetProgramPipelineInfoLog);
    }
    
    static native void nglGetProgramPipelineInfoLog(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static String glGetProgramPipelineInfoLog(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetProgramPipelineInfoLog = capabilities.glGetProgramPipelineInfoLog;
        BufferChecks.checkFunctionAddress(glGetProgramPipelineInfoLog);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n2);
        nglGetProgramPipelineInfoLog(n, n2, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(bufferByte), glGetProgramPipelineInfoLog);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static void glVertexAttribL1d(final int n, final double n2) {
        final long glVertexAttribL1d = GLContext.getCapabilities().glVertexAttribL1d;
        BufferChecks.checkFunctionAddress(glVertexAttribL1d);
        nglVertexAttribL1d(n, n2, glVertexAttribL1d);
    }
    
    static native void nglVertexAttribL1d(final int p0, final double p1, final long p2);
    
    public static void glVertexAttribL2d(final int n, final double n2, final double n3) {
        final long glVertexAttribL2d = GLContext.getCapabilities().glVertexAttribL2d;
        BufferChecks.checkFunctionAddress(glVertexAttribL2d);
        nglVertexAttribL2d(n, n2, n3, glVertexAttribL2d);
    }
    
    static native void nglVertexAttribL2d(final int p0, final double p1, final double p2, final long p3);
    
    public static void glVertexAttribL3d(final int n, final double n2, final double n3, final double n4) {
        final long glVertexAttribL3d = GLContext.getCapabilities().glVertexAttribL3d;
        BufferChecks.checkFunctionAddress(glVertexAttribL3d);
        nglVertexAttribL3d(n, n2, n3, n4, glVertexAttribL3d);
    }
    
    static native void nglVertexAttribL3d(final int p0, final double p1, final double p2, final double p3, final long p4);
    
    public static void glVertexAttribL4d(final int n, final double n2, final double n3, final double n4, final double n5) {
        final long glVertexAttribL4d = GLContext.getCapabilities().glVertexAttribL4d;
        BufferChecks.checkFunctionAddress(glVertexAttribL4d);
        nglVertexAttribL4d(n, n2, n3, n4, n5, glVertexAttribL4d);
    }
    
    static native void nglVertexAttribL4d(final int p0, final double p1, final double p2, final double p3, final double p4, final long p5);
    
    public static void glVertexAttribL1(final int n, final DoubleBuffer doubleBuffer) {
        final long glVertexAttribL1dv = GLContext.getCapabilities().glVertexAttribL1dv;
        BufferChecks.checkFunctionAddress(glVertexAttribL1dv);
        BufferChecks.checkBuffer(doubleBuffer, 1);
        nglVertexAttribL1dv(n, MemoryUtil.getAddress(doubleBuffer), glVertexAttribL1dv);
    }
    
    static native void nglVertexAttribL1dv(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribL2(final int n, final DoubleBuffer doubleBuffer) {
        final long glVertexAttribL2dv = GLContext.getCapabilities().glVertexAttribL2dv;
        BufferChecks.checkFunctionAddress(glVertexAttribL2dv);
        BufferChecks.checkBuffer(doubleBuffer, 2);
        nglVertexAttribL2dv(n, MemoryUtil.getAddress(doubleBuffer), glVertexAttribL2dv);
    }
    
    static native void nglVertexAttribL2dv(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribL3(final int n, final DoubleBuffer doubleBuffer) {
        final long glVertexAttribL3dv = GLContext.getCapabilities().glVertexAttribL3dv;
        BufferChecks.checkFunctionAddress(glVertexAttribL3dv);
        BufferChecks.checkBuffer(doubleBuffer, 3);
        nglVertexAttribL3dv(n, MemoryUtil.getAddress(doubleBuffer), glVertexAttribL3dv);
    }
    
    static native void nglVertexAttribL3dv(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribL4(final int n, final DoubleBuffer doubleBuffer) {
        final long glVertexAttribL4dv = GLContext.getCapabilities().glVertexAttribL4dv;
        BufferChecks.checkFunctionAddress(glVertexAttribL4dv);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglVertexAttribL4dv(n, MemoryUtil.getAddress(doubleBuffer), glVertexAttribL4dv);
    }
    
    static native void nglVertexAttribL4dv(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribLPointer(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribLPointer = capabilities.glVertexAttribLPointer;
        BufferChecks.checkFunctionAddress(glVertexAttribLPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = doubleBuffer;
        }
        nglVertexAttribLPointer(n, n2, 5130, n3, MemoryUtil.getAddress(doubleBuffer), glVertexAttribLPointer);
    }
    
    static native void nglVertexAttribLPointer(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glVertexAttribLPointer(final int n, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribLPointer = capabilities.glVertexAttribLPointer;
        BufferChecks.checkFunctionAddress(glVertexAttribLPointer);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglVertexAttribLPointerBO(n, n2, 5130, n3, n4, glVertexAttribLPointer);
    }
    
    static native void nglVertexAttribLPointerBO(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glGetVertexAttribL(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glGetVertexAttribLdv = GLContext.getCapabilities().glGetVertexAttribLdv;
        BufferChecks.checkFunctionAddress(glGetVertexAttribLdv);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglGetVertexAttribLdv(n, n2, MemoryUtil.getAddress(doubleBuffer), glGetVertexAttribLdv);
    }
    
    static native void nglGetVertexAttribLdv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glViewportArray(final int n, final FloatBuffer floatBuffer) {
        final long glViewportArrayv = GLContext.getCapabilities().glViewportArrayv;
        BufferChecks.checkFunctionAddress(glViewportArrayv);
        BufferChecks.checkDirect(floatBuffer);
        nglViewportArrayv(n, floatBuffer.remaining() >> 2, MemoryUtil.getAddress(floatBuffer), glViewportArrayv);
    }
    
    static native void nglViewportArrayv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glViewportIndexedf(final int n, final float n2, final float n3, final float n4, final float n5) {
        final long glViewportIndexedf = GLContext.getCapabilities().glViewportIndexedf;
        BufferChecks.checkFunctionAddress(glViewportIndexedf);
        nglViewportIndexedf(n, n2, n3, n4, n5, glViewportIndexedf);
    }
    
    static native void nglViewportIndexedf(final int p0, final float p1, final float p2, final float p3, final float p4, final long p5);
    
    public static void glViewportIndexed(final int n, final FloatBuffer floatBuffer) {
        final long glViewportIndexedfv = GLContext.getCapabilities().glViewportIndexedfv;
        BufferChecks.checkFunctionAddress(glViewportIndexedfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglViewportIndexedfv(n, MemoryUtil.getAddress(floatBuffer), glViewportIndexedfv);
    }
    
    static native void nglViewportIndexedfv(final int p0, final long p1, final long p2);
    
    public static void glScissorArray(final int n, final IntBuffer intBuffer) {
        final long glScissorArrayv = GLContext.getCapabilities().glScissorArrayv;
        BufferChecks.checkFunctionAddress(glScissorArrayv);
        BufferChecks.checkDirect(intBuffer);
        nglScissorArrayv(n, intBuffer.remaining() >> 2, MemoryUtil.getAddress(intBuffer), glScissorArrayv);
    }
    
    static native void nglScissorArrayv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glScissorIndexed(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glScissorIndexed = GLContext.getCapabilities().glScissorIndexed;
        BufferChecks.checkFunctionAddress(glScissorIndexed);
        nglScissorIndexed(n, n2, n3, n4, n5, glScissorIndexed);
    }
    
    static native void nglScissorIndexed(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glScissorIndexed(final int n, final IntBuffer intBuffer) {
        final long glScissorIndexedv = GLContext.getCapabilities().glScissorIndexedv;
        BufferChecks.checkFunctionAddress(glScissorIndexedv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglScissorIndexedv(n, MemoryUtil.getAddress(intBuffer), glScissorIndexedv);
    }
    
    static native void nglScissorIndexedv(final int p0, final long p1, final long p2);
    
    public static void glDepthRangeArray(final int n, final DoubleBuffer doubleBuffer) {
        final long glDepthRangeArrayv = GLContext.getCapabilities().glDepthRangeArrayv;
        BufferChecks.checkFunctionAddress(glDepthRangeArrayv);
        BufferChecks.checkDirect(doubleBuffer);
        nglDepthRangeArrayv(n, doubleBuffer.remaining() >> 1, MemoryUtil.getAddress(doubleBuffer), glDepthRangeArrayv);
    }
    
    static native void nglDepthRangeArrayv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glDepthRangeIndexed(final int n, final double n2, final double n3) {
        final long glDepthRangeIndexed = GLContext.getCapabilities().glDepthRangeIndexed;
        BufferChecks.checkFunctionAddress(glDepthRangeIndexed);
        nglDepthRangeIndexed(n, n2, n3, glDepthRangeIndexed);
    }
    
    static native void nglDepthRangeIndexed(final int p0, final double p1, final double p2, final long p3);
    
    public static void glGetFloat(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetFloati_v = GLContext.getCapabilities().glGetFloati_v;
        BufferChecks.checkFunctionAddress(glGetFloati_v);
        BufferChecks.checkDirect(floatBuffer);
        nglGetFloati_v(n, n2, MemoryUtil.getAddress(floatBuffer), glGetFloati_v);
    }
    
    static native void nglGetFloati_v(final int p0, final int p1, final long p2, final long p3);
    
    public static float glGetFloat(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetFloati_v = capabilities.glGetFloati_v;
        BufferChecks.checkFunctionAddress(glGetFloati_v);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetFloati_v(n, n2, MemoryUtil.getAddress(bufferFloat), glGetFloati_v);
        return bufferFloat.get(0);
    }
    
    public static void glGetDouble(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glGetDoublei_v = GLContext.getCapabilities().glGetDoublei_v;
        BufferChecks.checkFunctionAddress(glGetDoublei_v);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetDoublei_v(n, n2, MemoryUtil.getAddress(doubleBuffer), glGetDoublei_v);
    }
    
    static native void nglGetDoublei_v(final int p0, final int p1, final long p2, final long p3);
    
    public static double glGetDouble(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetDoublei_v = capabilities.glGetDoublei_v;
        BufferChecks.checkFunctionAddress(glGetDoublei_v);
        final DoubleBuffer bufferDouble = APIUtil.getBufferDouble(capabilities);
        nglGetDoublei_v(n, n2, MemoryUtil.getAddress(bufferDouble), glGetDoublei_v);
        return bufferDouble.get(0);
    }
}
