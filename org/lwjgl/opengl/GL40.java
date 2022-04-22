package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class GL40
{
    public static final int GL_DRAW_INDIRECT_BUFFER = 36671;
    public static final int GL_DRAW_INDIRECT_BUFFER_BINDING = 36675;
    public static final int GL_GEOMETRY_SHADER_INVOCATIONS = 34943;
    public static final int GL_MAX_GEOMETRY_SHADER_INVOCATIONS = 36442;
    public static final int GL_MIN_FRAGMENT_INTERPOLATION_OFFSET = 36443;
    public static final int GL_MAX_FRAGMENT_INTERPOLATION_OFFSET = 36444;
    public static final int GL_FRAGMENT_INTERPOLATION_OFFSET_BITS = 36445;
    public static final int GL_MAX_VERTEX_STREAMS = 36465;
    public static final int GL_DOUBLE_VEC2 = 36860;
    public static final int GL_DOUBLE_VEC3 = 36861;
    public static final int GL_DOUBLE_VEC4 = 36862;
    public static final int GL_DOUBLE_MAT2 = 36678;
    public static final int GL_DOUBLE_MAT3 = 36679;
    public static final int GL_DOUBLE_MAT4 = 36680;
    public static final int GL_DOUBLE_MAT2x3 = 36681;
    public static final int GL_DOUBLE_MAT2x4 = 36682;
    public static final int GL_DOUBLE_MAT3x2 = 36683;
    public static final int GL_DOUBLE_MAT3x4 = 36684;
    public static final int GL_DOUBLE_MAT4x2 = 36685;
    public static final int GL_DOUBLE_MAT4x3 = 36686;
    public static final int GL_SAMPLE_SHADING = 35894;
    public static final int GL_MIN_SAMPLE_SHADING_VALUE = 35895;
    public static final int GL_ACTIVE_SUBROUTINES = 36325;
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORMS = 36326;
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORM_LOCATIONS = 36423;
    public static final int GL_ACTIVE_SUBROUTINE_MAX_LENGTH = 36424;
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORM_MAX_LENGTH = 36425;
    public static final int GL_MAX_SUBROUTINES = 36327;
    public static final int GL_MAX_SUBROUTINE_UNIFORM_LOCATIONS = 36328;
    public static final int GL_NUM_COMPATIBLE_SUBROUTINES = 36426;
    public static final int GL_COMPATIBLE_SUBROUTINES = 36427;
    public static final int GL_PATCHES = 14;
    public static final int GL_PATCH_VERTICES = 36466;
    public static final int GL_PATCH_DEFAULT_INNER_LEVEL = 36467;
    public static final int GL_PATCH_DEFAULT_OUTER_LEVEL = 36468;
    public static final int GL_TESS_CONTROL_OUTPUT_VERTICES = 36469;
    public static final int GL_TESS_GEN_MODE = 36470;
    public static final int GL_TESS_GEN_SPACING = 36471;
    public static final int GL_TESS_GEN_VERTEX_ORDER = 36472;
    public static final int GL_TESS_GEN_POINT_MODE = 36473;
    public static final int GL_ISOLINES = 36474;
    public static final int GL_FRACTIONAL_ODD = 36475;
    public static final int GL_FRACTIONAL_EVEN = 36476;
    public static final int GL_MAX_PATCH_VERTICES = 36477;
    public static final int GL_MAX_TESS_GEN_LEVEL = 36478;
    public static final int GL_MAX_TESS_CONTROL_UNIFORM_COMPONENTS = 36479;
    public static final int GL_MAX_TESS_EVALUATION_UNIFORM_COMPONENTS = 36480;
    public static final int GL_MAX_TESS_CONTROL_TEXTURE_IMAGE_UNITS = 36481;
    public static final int GL_MAX_TESS_EVALUATION_TEXTURE_IMAGE_UNITS = 36482;
    public static final int GL_MAX_TESS_CONTROL_OUTPUT_COMPONENTS = 36483;
    public static final int GL_MAX_TESS_PATCH_COMPONENTS = 36484;
    public static final int GL_MAX_TESS_CONTROL_TOTAL_OUTPUT_COMPONENTS = 36485;
    public static final int GL_MAX_TESS_EVALUATION_OUTPUT_COMPONENTS = 36486;
    public static final int GL_MAX_TESS_CONTROL_UNIFORM_BLOCKS = 36489;
    public static final int GL_MAX_TESS_EVALUATION_UNIFORM_BLOCKS = 36490;
    public static final int GL_MAX_TESS_CONTROL_INPUT_COMPONENTS = 34924;
    public static final int GL_MAX_TESS_EVALUATION_INPUT_COMPONENTS = 34925;
    public static final int GL_MAX_COMBINED_TESS_CONTROL_UNIFORM_COMPONENTS = 36382;
    public static final int GL_MAX_COMBINED_TESS_EVALUATION_UNIFORM_COMPONENTS = 36383;
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_CONTROL_SHADER = 34032;
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_EVALUATION_SHADER = 34033;
    public static final int GL_TESS_EVALUATION_SHADER = 36487;
    public static final int GL_TESS_CONTROL_SHADER = 36488;
    public static final int GL_TEXTURE_CUBE_MAP_ARRAY = 36873;
    public static final int GL_TEXTURE_BINDING_CUBE_MAP_ARRAY = 36874;
    public static final int GL_PROXY_TEXTURE_CUBE_MAP_ARRAY = 36875;
    public static final int GL_SAMPLER_CUBE_MAP_ARRAY = 36876;
    public static final int GL_SAMPLER_CUBE_MAP_ARRAY_SHADOW = 36877;
    public static final int GL_INT_SAMPLER_CUBE_MAP_ARRAY = 36878;
    public static final int GL_UNSIGNED_INT_SAMPLER_CUBE_MAP_ARRAY = 36879;
    public static final int GL_MIN_PROGRAM_TEXTURE_GATHER_OFFSET_ARB = 36446;
    public static final int GL_MAX_PROGRAM_TEXTURE_GATHER_OFFSET_ARB = 36447;
    public static final int GL_MAX_PROGRAM_TEXTURE_GATHER_COMPONENTS_ARB = 36767;
    public static final int GL_TRANSFORM_FEEDBACK = 36386;
    public static final int GL_TRANSFORM_FEEDBACK_PAUSED = 36387;
    public static final int GL_TRANSFORM_FEEDBACK_ACTIVE = 36388;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_PAUSED = 36387;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_ACTIVE = 36388;
    public static final int GL_TRANSFORM_FEEDBACK_BINDING = 36389;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_BUFFERS = 36464;
    
    private GL40() {
    }
    
    public static void glBlendEquationi(final int n, final int n2) {
        final long glBlendEquationi = GLContext.getCapabilities().glBlendEquationi;
        BufferChecks.checkFunctionAddress(glBlendEquationi);
        nglBlendEquationi(n, n2, glBlendEquationi);
    }
    
    static native void nglBlendEquationi(final int p0, final int p1, final long p2);
    
    public static void glBlendEquationSeparatei(final int n, final int n2, final int n3) {
        final long glBlendEquationSeparatei = GLContext.getCapabilities().glBlendEquationSeparatei;
        BufferChecks.checkFunctionAddress(glBlendEquationSeparatei);
        nglBlendEquationSeparatei(n, n2, n3, glBlendEquationSeparatei);
    }
    
    static native void nglBlendEquationSeparatei(final int p0, final int p1, final int p2, final long p3);
    
    public static void glBlendFunci(final int n, final int n2, final int n3) {
        final long glBlendFunci = GLContext.getCapabilities().glBlendFunci;
        BufferChecks.checkFunctionAddress(glBlendFunci);
        nglBlendFunci(n, n2, n3, glBlendFunci);
    }
    
    static native void nglBlendFunci(final int p0, final int p1, final int p2, final long p3);
    
    public static void glBlendFuncSeparatei(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glBlendFuncSeparatei = GLContext.getCapabilities().glBlendFuncSeparatei;
        BufferChecks.checkFunctionAddress(glBlendFuncSeparatei);
        nglBlendFuncSeparatei(n, n2, n3, n4, n5, glBlendFuncSeparatei);
    }
    
    static native void nglBlendFuncSeparatei(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glDrawArraysIndirect(final int n, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawArraysIndirect = capabilities.glDrawArraysIndirect;
        BufferChecks.checkFunctionAddress(glDrawArraysIndirect);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, 16);
        nglDrawArraysIndirect(n, MemoryUtil.getAddress(byteBuffer), glDrawArraysIndirect);
    }
    
    static native void nglDrawArraysIndirect(final int p0, final long p1, final long p2);
    
    public static void glDrawArraysIndirect(final int n, final long n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawArraysIndirect = capabilities.glDrawArraysIndirect;
        BufferChecks.checkFunctionAddress(glDrawArraysIndirect);
        GLChecks.ensureIndirectBOenabled(capabilities);
        nglDrawArraysIndirectBO(n, n2, glDrawArraysIndirect);
    }
    
    static native void nglDrawArraysIndirectBO(final int p0, final long p1, final long p2);
    
    public static void glDrawArraysIndirect(final int n, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawArraysIndirect = capabilities.glDrawArraysIndirect;
        BufferChecks.checkFunctionAddress(glDrawArraysIndirect);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglDrawArraysIndirect(n, MemoryUtil.getAddress(intBuffer), glDrawArraysIndirect);
    }
    
    public static void glDrawElementsIndirect(final int n, final int n2, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsIndirect = capabilities.glDrawElementsIndirect;
        BufferChecks.checkFunctionAddress(glDrawElementsIndirect);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, 20);
        nglDrawElementsIndirect(n, n2, MemoryUtil.getAddress(byteBuffer), glDrawElementsIndirect);
    }
    
    static native void nglDrawElementsIndirect(final int p0, final int p1, final long p2, final long p3);
    
    public static void glDrawElementsIndirect(final int n, final int n2, final long n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsIndirect = capabilities.glDrawElementsIndirect;
        BufferChecks.checkFunctionAddress(glDrawElementsIndirect);
        GLChecks.ensureIndirectBOenabled(capabilities);
        nglDrawElementsIndirectBO(n, n2, n3, glDrawElementsIndirect);
    }
    
    static native void nglDrawElementsIndirectBO(final int p0, final int p1, final long p2, final long p3);
    
    public static void glDrawElementsIndirect(final int n, final int n2, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElementsIndirect = capabilities.glDrawElementsIndirect;
        BufferChecks.checkFunctionAddress(glDrawElementsIndirect);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, 5);
        nglDrawElementsIndirect(n, n2, MemoryUtil.getAddress(intBuffer), glDrawElementsIndirect);
    }
    
    public static void glUniform1d(final int n, final double n2) {
        final long glUniform1d = GLContext.getCapabilities().glUniform1d;
        BufferChecks.checkFunctionAddress(glUniform1d);
        nglUniform1d(n, n2, glUniform1d);
    }
    
    static native void nglUniform1d(final int p0, final double p1, final long p2);
    
    public static void glUniform2d(final int n, final double n2, final double n3) {
        final long glUniform2d = GLContext.getCapabilities().glUniform2d;
        BufferChecks.checkFunctionAddress(glUniform2d);
        nglUniform2d(n, n2, n3, glUniform2d);
    }
    
    static native void nglUniform2d(final int p0, final double p1, final double p2, final long p3);
    
    public static void glUniform3d(final int n, final double n2, final double n3, final double n4) {
        final long glUniform3d = GLContext.getCapabilities().glUniform3d;
        BufferChecks.checkFunctionAddress(glUniform3d);
        nglUniform3d(n, n2, n3, n4, glUniform3d);
    }
    
    static native void nglUniform3d(final int p0, final double p1, final double p2, final double p3, final long p4);
    
    public static void glUniform4d(final int n, final double n2, final double n3, final double n4, final double n5) {
        final long glUniform4d = GLContext.getCapabilities().glUniform4d;
        BufferChecks.checkFunctionAddress(glUniform4d);
        nglUniform4d(n, n2, n3, n4, n5, glUniform4d);
    }
    
    static native void nglUniform4d(final int p0, final double p1, final double p2, final double p3, final double p4, final long p5);
    
    public static void glUniform1(final int n, final DoubleBuffer doubleBuffer) {
        final long glUniform1dv = GLContext.getCapabilities().glUniform1dv;
        BufferChecks.checkFunctionAddress(glUniform1dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglUniform1dv(n, doubleBuffer.remaining(), MemoryUtil.getAddress(doubleBuffer), glUniform1dv);
    }
    
    static native void nglUniform1dv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform2(final int n, final DoubleBuffer doubleBuffer) {
        final long glUniform2dv = GLContext.getCapabilities().glUniform2dv;
        BufferChecks.checkFunctionAddress(glUniform2dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglUniform2dv(n, doubleBuffer.remaining() >> 1, MemoryUtil.getAddress(doubleBuffer), glUniform2dv);
    }
    
    static native void nglUniform2dv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform3(final int n, final DoubleBuffer doubleBuffer) {
        final long glUniform3dv = GLContext.getCapabilities().glUniform3dv;
        BufferChecks.checkFunctionAddress(glUniform3dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglUniform3dv(n, doubleBuffer.remaining() / 3, MemoryUtil.getAddress(doubleBuffer), glUniform3dv);
    }
    
    static native void nglUniform3dv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform4(final int n, final DoubleBuffer doubleBuffer) {
        final long glUniform4dv = GLContext.getCapabilities().glUniform4dv;
        BufferChecks.checkFunctionAddress(glUniform4dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglUniform4dv(n, doubleBuffer.remaining() >> 2, MemoryUtil.getAddress(doubleBuffer), glUniform4dv);
    }
    
    static native void nglUniform4dv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniformMatrix2(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glUniformMatrix2dv = GLContext.getCapabilities().glUniformMatrix2dv;
        BufferChecks.checkFunctionAddress(glUniformMatrix2dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglUniformMatrix2dv(n, doubleBuffer.remaining() >> 2, b, MemoryUtil.getAddress(doubleBuffer), glUniformMatrix2dv);
    }
    
    static native void nglUniformMatrix2dv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glUniformMatrix3(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glUniformMatrix3dv = GLContext.getCapabilities().glUniformMatrix3dv;
        BufferChecks.checkFunctionAddress(glUniformMatrix3dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglUniformMatrix3dv(n, doubleBuffer.remaining() / 9, b, MemoryUtil.getAddress(doubleBuffer), glUniformMatrix3dv);
    }
    
    static native void nglUniformMatrix3dv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glUniformMatrix4(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glUniformMatrix4dv = GLContext.getCapabilities().glUniformMatrix4dv;
        BufferChecks.checkFunctionAddress(glUniformMatrix4dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglUniformMatrix4dv(n, doubleBuffer.remaining() >> 4, b, MemoryUtil.getAddress(doubleBuffer), glUniformMatrix4dv);
    }
    
    static native void nglUniformMatrix4dv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glUniformMatrix2x3(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glUniformMatrix2x3dv = GLContext.getCapabilities().glUniformMatrix2x3dv;
        BufferChecks.checkFunctionAddress(glUniformMatrix2x3dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglUniformMatrix2x3dv(n, doubleBuffer.remaining() / 6, b, MemoryUtil.getAddress(doubleBuffer), glUniformMatrix2x3dv);
    }
    
    static native void nglUniformMatrix2x3dv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glUniformMatrix2x4(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glUniformMatrix2x4dv = GLContext.getCapabilities().glUniformMatrix2x4dv;
        BufferChecks.checkFunctionAddress(glUniformMatrix2x4dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglUniformMatrix2x4dv(n, doubleBuffer.remaining() >> 3, b, MemoryUtil.getAddress(doubleBuffer), glUniformMatrix2x4dv);
    }
    
    static native void nglUniformMatrix2x4dv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glUniformMatrix3x2(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glUniformMatrix3x2dv = GLContext.getCapabilities().glUniformMatrix3x2dv;
        BufferChecks.checkFunctionAddress(glUniformMatrix3x2dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglUniformMatrix3x2dv(n, doubleBuffer.remaining() / 6, b, MemoryUtil.getAddress(doubleBuffer), glUniformMatrix3x2dv);
    }
    
    static native void nglUniformMatrix3x2dv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glUniformMatrix3x4(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glUniformMatrix3x4dv = GLContext.getCapabilities().glUniformMatrix3x4dv;
        BufferChecks.checkFunctionAddress(glUniformMatrix3x4dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglUniformMatrix3x4dv(n, doubleBuffer.remaining() / 12, b, MemoryUtil.getAddress(doubleBuffer), glUniformMatrix3x4dv);
    }
    
    static native void nglUniformMatrix3x4dv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glUniformMatrix4x2(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glUniformMatrix4x2dv = GLContext.getCapabilities().glUniformMatrix4x2dv;
        BufferChecks.checkFunctionAddress(glUniformMatrix4x2dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglUniformMatrix4x2dv(n, doubleBuffer.remaining() >> 3, b, MemoryUtil.getAddress(doubleBuffer), glUniformMatrix4x2dv);
    }
    
    static native void nglUniformMatrix4x2dv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glUniformMatrix4x3(final int n, final boolean b, final DoubleBuffer doubleBuffer) {
        final long glUniformMatrix4x3dv = GLContext.getCapabilities().glUniformMatrix4x3dv;
        BufferChecks.checkFunctionAddress(glUniformMatrix4x3dv);
        BufferChecks.checkDirect(doubleBuffer);
        nglUniformMatrix4x3dv(n, doubleBuffer.remaining() / 12, b, MemoryUtil.getAddress(doubleBuffer), glUniformMatrix4x3dv);
    }
    
    static native void nglUniformMatrix4x3dv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glGetUniform(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glGetUniformdv = GLContext.getCapabilities().glGetUniformdv;
        BufferChecks.checkFunctionAddress(glGetUniformdv);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetUniformdv(n, n2, MemoryUtil.getAddress(doubleBuffer), glGetUniformdv);
    }
    
    static native void nglGetUniformdv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glMinSampleShading(final float n) {
        final long glMinSampleShading = GLContext.getCapabilities().glMinSampleShading;
        BufferChecks.checkFunctionAddress(glMinSampleShading);
        nglMinSampleShading(n, glMinSampleShading);
    }
    
    static native void nglMinSampleShading(final float p0, final long p1);
    
    public static int glGetSubroutineUniformLocation(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glGetSubroutineUniformLocation = GLContext.getCapabilities().glGetSubroutineUniformLocation;
        BufferChecks.checkFunctionAddress(glGetSubroutineUniformLocation);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        return nglGetSubroutineUniformLocation(n, n2, MemoryUtil.getAddress(byteBuffer), glGetSubroutineUniformLocation);
    }
    
    static native int nglGetSubroutineUniformLocation(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetSubroutineUniformLocation(final int n, final int n2, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSubroutineUniformLocation = capabilities.glGetSubroutineUniformLocation;
        BufferChecks.checkFunctionAddress(glGetSubroutineUniformLocation);
        return nglGetSubroutineUniformLocation(n, n2, APIUtil.getBufferNT(capabilities, charSequence), glGetSubroutineUniformLocation);
    }
    
    public static int glGetSubroutineIndex(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glGetSubroutineIndex = GLContext.getCapabilities().glGetSubroutineIndex;
        BufferChecks.checkFunctionAddress(glGetSubroutineIndex);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        return nglGetSubroutineIndex(n, n2, MemoryUtil.getAddress(byteBuffer), glGetSubroutineIndex);
    }
    
    static native int nglGetSubroutineIndex(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetSubroutineIndex(final int n, final int n2, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSubroutineIndex = capabilities.glGetSubroutineIndex;
        BufferChecks.checkFunctionAddress(glGetSubroutineIndex);
        return nglGetSubroutineIndex(n, n2, APIUtil.getBufferNT(capabilities, charSequence), glGetSubroutineIndex);
    }
    
    public static void glGetActiveSubroutineUniform(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final long glGetActiveSubroutineUniformiv = GLContext.getCapabilities().glGetActiveSubroutineUniformiv;
        BufferChecks.checkFunctionAddress(glGetActiveSubroutineUniformiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetActiveSubroutineUniformiv(n, n2, n3, n4, MemoryUtil.getAddress(intBuffer), glGetActiveSubroutineUniformiv);
    }
    
    static native void nglGetActiveSubroutineUniformiv(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    @Deprecated
    public static int glGetActiveSubroutineUniform(final int n, final int n2, final int n3, final int n4) {
        return glGetActiveSubroutineUniformi(n, n2, n3, n4);
    }
    
    public static int glGetActiveSubroutineUniformi(final int n, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveSubroutineUniformiv = capabilities.glGetActiveSubroutineUniformiv;
        BufferChecks.checkFunctionAddress(glGetActiveSubroutineUniformiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetActiveSubroutineUniformiv(n, n2, n3, n4, MemoryUtil.getAddress(bufferInt), glGetActiveSubroutineUniformiv);
        return bufferInt.get(0);
    }
    
    public static void glGetActiveSubroutineUniformName(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final long glGetActiveSubroutineUniformName = GLContext.getCapabilities().glGetActiveSubroutineUniformName;
        BufferChecks.checkFunctionAddress(glGetActiveSubroutineUniformName);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(byteBuffer);
        nglGetActiveSubroutineUniformName(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetActiveSubroutineUniformName);
    }
    
    static native void nglGetActiveSubroutineUniformName(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5, final long p6);
    
    public static String glGetActiveSubroutineUniformName(final int n, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveSubroutineUniformName = capabilities.glGetActiveSubroutineUniformName;
        BufferChecks.checkFunctionAddress(glGetActiveSubroutineUniformName);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n4);
        nglGetActiveSubroutineUniformName(n, n2, n3, n4, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(bufferByte), glGetActiveSubroutineUniformName);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static void glGetActiveSubroutineName(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final long glGetActiveSubroutineName = GLContext.getCapabilities().glGetActiveSubroutineName;
        BufferChecks.checkFunctionAddress(glGetActiveSubroutineName);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(byteBuffer);
        nglGetActiveSubroutineName(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetActiveSubroutineName);
    }
    
    static native void nglGetActiveSubroutineName(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5, final long p6);
    
    public static String glGetActiveSubroutineName(final int n, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveSubroutineName = capabilities.glGetActiveSubroutineName;
        BufferChecks.checkFunctionAddress(glGetActiveSubroutineName);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n4);
        nglGetActiveSubroutineName(n, n2, n3, n4, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(bufferByte), glGetActiveSubroutineName);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static void glUniformSubroutinesu(final int n, final IntBuffer intBuffer) {
        final long glUniformSubroutinesuiv = GLContext.getCapabilities().glUniformSubroutinesuiv;
        BufferChecks.checkFunctionAddress(glUniformSubroutinesuiv);
        BufferChecks.checkDirect(intBuffer);
        nglUniformSubroutinesuiv(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glUniformSubroutinesuiv);
    }
    
    static native void nglUniformSubroutinesuiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetUniformSubroutineu(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetUniformSubroutineuiv = GLContext.getCapabilities().glGetUniformSubroutineuiv;
        BufferChecks.checkFunctionAddress(glGetUniformSubroutineuiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetUniformSubroutineuiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetUniformSubroutineuiv);
    }
    
    static native void nglGetUniformSubroutineuiv(final int p0, final int p1, final long p2, final long p3);
    
    @Deprecated
    public static int glGetUniformSubroutineu(final int n, final int n2) {
        return glGetUniformSubroutineui(n, n2);
    }
    
    public static int glGetUniformSubroutineui(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetUniformSubroutineuiv = capabilities.glGetUniformSubroutineuiv;
        BufferChecks.checkFunctionAddress(glGetUniformSubroutineuiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetUniformSubroutineuiv(n, n2, MemoryUtil.getAddress(bufferInt), glGetUniformSubroutineuiv);
        return bufferInt.get(0);
    }
    
    public static void glGetProgramStage(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetProgramStageiv = GLContext.getCapabilities().glGetProgramStageiv;
        BufferChecks.checkFunctionAddress(glGetProgramStageiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetProgramStageiv(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetProgramStageiv);
    }
    
    static native void nglGetProgramStageiv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    @Deprecated
    public static int glGetProgramStage(final int n, final int n2, final int n3) {
        return glGetProgramStagei(n, n2, n3);
    }
    
    public static int glGetProgramStagei(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetProgramStageiv = capabilities.glGetProgramStageiv;
        BufferChecks.checkFunctionAddress(glGetProgramStageiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetProgramStageiv(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetProgramStageiv);
        return bufferInt.get(0);
    }
    
    public static void glPatchParameteri(final int n, final int n2) {
        final long glPatchParameteri = GLContext.getCapabilities().glPatchParameteri;
        BufferChecks.checkFunctionAddress(glPatchParameteri);
        nglPatchParameteri(n, n2, glPatchParameteri);
    }
    
    static native void nglPatchParameteri(final int p0, final int p1, final long p2);
    
    public static void glPatchParameter(final int n, final FloatBuffer floatBuffer) {
        final long glPatchParameterfv = GLContext.getCapabilities().glPatchParameterfv;
        BufferChecks.checkFunctionAddress(glPatchParameterfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglPatchParameterfv(n, MemoryUtil.getAddress(floatBuffer), glPatchParameterfv);
    }
    
    static native void nglPatchParameterfv(final int p0, final long p1, final long p2);
    
    public static void glBindTransformFeedback(final int n, final int n2) {
        final long glBindTransformFeedback = GLContext.getCapabilities().glBindTransformFeedback;
        BufferChecks.checkFunctionAddress(glBindTransformFeedback);
        nglBindTransformFeedback(n, n2, glBindTransformFeedback);
    }
    
    static native void nglBindTransformFeedback(final int p0, final int p1, final long p2);
    
    public static void glDeleteTransformFeedbacks(final IntBuffer intBuffer) {
        final long glDeleteTransformFeedbacks = GLContext.getCapabilities().glDeleteTransformFeedbacks;
        BufferChecks.checkFunctionAddress(glDeleteTransformFeedbacks);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteTransformFeedbacks(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteTransformFeedbacks);
    }
    
    static native void nglDeleteTransformFeedbacks(final int p0, final long p1, final long p2);
    
    public static void glDeleteTransformFeedbacks(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteTransformFeedbacks = capabilities.glDeleteTransformFeedbacks;
        BufferChecks.checkFunctionAddress(glDeleteTransformFeedbacks);
        nglDeleteTransformFeedbacks(1, APIUtil.getInt(capabilities, n), glDeleteTransformFeedbacks);
    }
    
    public static void glGenTransformFeedbacks(final IntBuffer intBuffer) {
        final long glGenTransformFeedbacks = GLContext.getCapabilities().glGenTransformFeedbacks;
        BufferChecks.checkFunctionAddress(glGenTransformFeedbacks);
        BufferChecks.checkDirect(intBuffer);
        nglGenTransformFeedbacks(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenTransformFeedbacks);
    }
    
    static native void nglGenTransformFeedbacks(final int p0, final long p1, final long p2);
    
    public static int glGenTransformFeedbacks() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenTransformFeedbacks = capabilities.glGenTransformFeedbacks;
        BufferChecks.checkFunctionAddress(glGenTransformFeedbacks);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenTransformFeedbacks(1, MemoryUtil.getAddress(bufferInt), glGenTransformFeedbacks);
        return bufferInt.get(0);
    }
    
    public static boolean glIsTransformFeedback(final int n) {
        final long glIsTransformFeedback = GLContext.getCapabilities().glIsTransformFeedback;
        BufferChecks.checkFunctionAddress(glIsTransformFeedback);
        return nglIsTransformFeedback(n, glIsTransformFeedback);
    }
    
    static native boolean nglIsTransformFeedback(final int p0, final long p1);
    
    public static void glPauseTransformFeedback() {
        final long glPauseTransformFeedback = GLContext.getCapabilities().glPauseTransformFeedback;
        BufferChecks.checkFunctionAddress(glPauseTransformFeedback);
        nglPauseTransformFeedback(glPauseTransformFeedback);
    }
    
    static native void nglPauseTransformFeedback(final long p0);
    
    public static void glResumeTransformFeedback() {
        final long glResumeTransformFeedback = GLContext.getCapabilities().glResumeTransformFeedback;
        BufferChecks.checkFunctionAddress(glResumeTransformFeedback);
        nglResumeTransformFeedback(glResumeTransformFeedback);
    }
    
    static native void nglResumeTransformFeedback(final long p0);
    
    public static void glDrawTransformFeedback(final int n, final int n2) {
        final long glDrawTransformFeedback = GLContext.getCapabilities().glDrawTransformFeedback;
        BufferChecks.checkFunctionAddress(glDrawTransformFeedback);
        nglDrawTransformFeedback(n, n2, glDrawTransformFeedback);
    }
    
    static native void nglDrawTransformFeedback(final int p0, final int p1, final long p2);
    
    public static void glDrawTransformFeedbackStream(final int n, final int n2, final int n3) {
        final long glDrawTransformFeedbackStream = GLContext.getCapabilities().glDrawTransformFeedbackStream;
        BufferChecks.checkFunctionAddress(glDrawTransformFeedbackStream);
        nglDrawTransformFeedbackStream(n, n2, n3, glDrawTransformFeedbackStream);
    }
    
    static native void nglDrawTransformFeedbackStream(final int p0, final int p1, final int p2, final long p3);
    
    public static void glBeginQueryIndexed(final int n, final int n2, final int n3) {
        final long glBeginQueryIndexed = GLContext.getCapabilities().glBeginQueryIndexed;
        BufferChecks.checkFunctionAddress(glBeginQueryIndexed);
        nglBeginQueryIndexed(n, n2, n3, glBeginQueryIndexed);
    }
    
    static native void nglBeginQueryIndexed(final int p0, final int p1, final int p2, final long p3);
    
    public static void glEndQueryIndexed(final int n, final int n2) {
        final long glEndQueryIndexed = GLContext.getCapabilities().glEndQueryIndexed;
        BufferChecks.checkFunctionAddress(glEndQueryIndexed);
        nglEndQueryIndexed(n, n2, glEndQueryIndexed);
    }
    
    static native void nglEndQueryIndexed(final int p0, final int p1, final long p2);
    
    public static void glGetQueryIndexed(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetQueryIndexediv = GLContext.getCapabilities().glGetQueryIndexediv;
        BufferChecks.checkFunctionAddress(glGetQueryIndexediv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetQueryIndexediv(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetQueryIndexediv);
    }
    
    static native void nglGetQueryIndexediv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    @Deprecated
    public static int glGetQueryIndexed(final int n, final int n2, final int n3) {
        return glGetQueryIndexedi(n, n2, n3);
    }
    
    public static int glGetQueryIndexedi(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetQueryIndexediv = capabilities.glGetQueryIndexediv;
        BufferChecks.checkFunctionAddress(glGetQueryIndexediv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetQueryIndexediv(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetQueryIndexediv);
        return bufferInt.get(0);
    }
}
