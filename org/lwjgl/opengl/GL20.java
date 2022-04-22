package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class GL20
{
    public static final int GL_SHADING_LANGUAGE_VERSION = 35724;
    public static final int GL_CURRENT_PROGRAM = 35725;
    public static final int GL_SHADER_TYPE = 35663;
    public static final int GL_DELETE_STATUS = 35712;
    public static final int GL_COMPILE_STATUS = 35713;
    public static final int GL_LINK_STATUS = 35714;
    public static final int GL_VALIDATE_STATUS = 35715;
    public static final int GL_INFO_LOG_LENGTH = 35716;
    public static final int GL_ATTACHED_SHADERS = 35717;
    public static final int GL_ACTIVE_UNIFORMS = 35718;
    public static final int GL_ACTIVE_UNIFORM_MAX_LENGTH = 35719;
    public static final int GL_ACTIVE_ATTRIBUTES = 35721;
    public static final int GL_ACTIVE_ATTRIBUTE_MAX_LENGTH = 35722;
    public static final int GL_SHADER_SOURCE_LENGTH = 35720;
    public static final int GL_SHADER_OBJECT = 35656;
    public static final int GL_FLOAT_VEC2 = 35664;
    public static final int GL_FLOAT_VEC3 = 35665;
    public static final int GL_FLOAT_VEC4 = 35666;
    public static final int GL_INT_VEC2 = 35667;
    public static final int GL_INT_VEC3 = 35668;
    public static final int GL_INT_VEC4 = 35669;
    public static final int GL_BOOL = 35670;
    public static final int GL_BOOL_VEC2 = 35671;
    public static final int GL_BOOL_VEC3 = 35672;
    public static final int GL_BOOL_VEC4 = 35673;
    public static final int GL_FLOAT_MAT2 = 35674;
    public static final int GL_FLOAT_MAT3 = 35675;
    public static final int GL_FLOAT_MAT4 = 35676;
    public static final int GL_SAMPLER_1D = 35677;
    public static final int GL_SAMPLER_2D = 35678;
    public static final int GL_SAMPLER_3D = 35679;
    public static final int GL_SAMPLER_CUBE = 35680;
    public static final int GL_SAMPLER_1D_SHADOW = 35681;
    public static final int GL_SAMPLER_2D_SHADOW = 35682;
    public static final int GL_VERTEX_SHADER = 35633;
    public static final int GL_MAX_VERTEX_UNIFORM_COMPONENTS = 35658;
    public static final int GL_MAX_VARYING_FLOATS = 35659;
    public static final int GL_MAX_VERTEX_ATTRIBS = 34921;
    public static final int GL_MAX_TEXTURE_IMAGE_UNITS = 34930;
    public static final int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS = 35660;
    public static final int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS = 35661;
    public static final int GL_MAX_TEXTURE_COORDS = 34929;
    public static final int GL_VERTEX_PROGRAM_POINT_SIZE = 34370;
    public static final int GL_VERTEX_PROGRAM_TWO_SIDE = 34371;
    public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED = 34338;
    public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE = 34339;
    public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE = 34340;
    public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE = 34341;
    public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED = 34922;
    public static final int GL_CURRENT_VERTEX_ATTRIB = 34342;
    public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER = 34373;
    public static final int GL_FRAGMENT_SHADER = 35632;
    public static final int GL_MAX_FRAGMENT_UNIFORM_COMPONENTS = 35657;
    public static final int GL_FRAGMENT_SHADER_DERIVATIVE_HINT = 35723;
    public static final int GL_MAX_DRAW_BUFFERS = 34852;
    public static final int GL_DRAW_BUFFER0 = 34853;
    public static final int GL_DRAW_BUFFER1 = 34854;
    public static final int GL_DRAW_BUFFER2 = 34855;
    public static final int GL_DRAW_BUFFER3 = 34856;
    public static final int GL_DRAW_BUFFER4 = 34857;
    public static final int GL_DRAW_BUFFER5 = 34858;
    public static final int GL_DRAW_BUFFER6 = 34859;
    public static final int GL_DRAW_BUFFER7 = 34860;
    public static final int GL_DRAW_BUFFER8 = 34861;
    public static final int GL_DRAW_BUFFER9 = 34862;
    public static final int GL_DRAW_BUFFER10 = 34863;
    public static final int GL_DRAW_BUFFER11 = 34864;
    public static final int GL_DRAW_BUFFER12 = 34865;
    public static final int GL_DRAW_BUFFER13 = 34866;
    public static final int GL_DRAW_BUFFER14 = 34867;
    public static final int GL_DRAW_BUFFER15 = 34868;
    public static final int GL_POINT_SPRITE = 34913;
    public static final int GL_COORD_REPLACE = 34914;
    public static final int GL_POINT_SPRITE_COORD_ORIGIN = 36000;
    public static final int GL_LOWER_LEFT = 36001;
    public static final int GL_UPPER_LEFT = 36002;
    public static final int GL_STENCIL_BACK_FUNC = 34816;
    public static final int GL_STENCIL_BACK_FAIL = 34817;
    public static final int GL_STENCIL_BACK_PASS_DEPTH_FAIL = 34818;
    public static final int GL_STENCIL_BACK_PASS_DEPTH_PASS = 34819;
    public static final int GL_STENCIL_BACK_REF = 36003;
    public static final int GL_STENCIL_BACK_VALUE_MASK = 36004;
    public static final int GL_STENCIL_BACK_WRITEMASK = 36005;
    public static final int GL_BLEND_EQUATION_RGB = 32777;
    public static final int GL_BLEND_EQUATION_ALPHA = 34877;
    
    private GL20() {
    }
    
    public static void glShaderSource(final int n, final ByteBuffer byteBuffer) {
        final long glShaderSource = GLContext.getCapabilities().glShaderSource;
        BufferChecks.checkFunctionAddress(glShaderSource);
        BufferChecks.checkDirect(byteBuffer);
        nglShaderSource(n, 1, MemoryUtil.getAddress(byteBuffer), byteBuffer.remaining(), glShaderSource);
    }
    
    static native void nglShaderSource(final int p0, final int p1, final long p2, final int p3, final long p4);
    
    public static void glShaderSource(final int n, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glShaderSource = capabilities.glShaderSource;
        BufferChecks.checkFunctionAddress(glShaderSource);
        nglShaderSource(n, 1, APIUtil.getBuffer(capabilities, charSequence), charSequence.length(), glShaderSource);
    }
    
    public static void glShaderSource(final int n, final CharSequence[] array) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glShaderSource = capabilities.glShaderSource;
        BufferChecks.checkFunctionAddress(glShaderSource);
        BufferChecks.checkArray(array);
        nglShaderSource3(n, array.length, APIUtil.getBuffer(capabilities, array), APIUtil.getLengths(capabilities, array), glShaderSource);
    }
    
    static native void nglShaderSource3(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static int glCreateShader(final int n) {
        final long glCreateShader = GLContext.getCapabilities().glCreateShader;
        BufferChecks.checkFunctionAddress(glCreateShader);
        return nglCreateShader(n, glCreateShader);
    }
    
    static native int nglCreateShader(final int p0, final long p1);
    
    public static boolean glIsShader(final int n) {
        final long glIsShader = GLContext.getCapabilities().glIsShader;
        BufferChecks.checkFunctionAddress(glIsShader);
        return nglIsShader(n, glIsShader);
    }
    
    static native boolean nglIsShader(final int p0, final long p1);
    
    public static void glCompileShader(final int n) {
        final long glCompileShader = GLContext.getCapabilities().glCompileShader;
        BufferChecks.checkFunctionAddress(glCompileShader);
        nglCompileShader(n, glCompileShader);
    }
    
    static native void nglCompileShader(final int p0, final long p1);
    
    public static void glDeleteShader(final int n) {
        final long glDeleteShader = GLContext.getCapabilities().glDeleteShader;
        BufferChecks.checkFunctionAddress(glDeleteShader);
        nglDeleteShader(n, glDeleteShader);
    }
    
    static native void nglDeleteShader(final int p0, final long p1);
    
    public static int glCreateProgram() {
        final long glCreateProgram = GLContext.getCapabilities().glCreateProgram;
        BufferChecks.checkFunctionAddress(glCreateProgram);
        return nglCreateProgram(glCreateProgram);
    }
    
    static native int nglCreateProgram(final long p0);
    
    public static boolean glIsProgram(final int n) {
        final long glIsProgram = GLContext.getCapabilities().glIsProgram;
        BufferChecks.checkFunctionAddress(glIsProgram);
        return nglIsProgram(n, glIsProgram);
    }
    
    static native boolean nglIsProgram(final int p0, final long p1);
    
    public static void glAttachShader(final int n, final int n2) {
        final long glAttachShader = GLContext.getCapabilities().glAttachShader;
        BufferChecks.checkFunctionAddress(glAttachShader);
        nglAttachShader(n, n2, glAttachShader);
    }
    
    static native void nglAttachShader(final int p0, final int p1, final long p2);
    
    public static void glDetachShader(final int n, final int n2) {
        final long glDetachShader = GLContext.getCapabilities().glDetachShader;
        BufferChecks.checkFunctionAddress(glDetachShader);
        nglDetachShader(n, n2, glDetachShader);
    }
    
    static native void nglDetachShader(final int p0, final int p1, final long p2);
    
    public static void glLinkProgram(final int n) {
        final long glLinkProgram = GLContext.getCapabilities().glLinkProgram;
        BufferChecks.checkFunctionAddress(glLinkProgram);
        nglLinkProgram(n, glLinkProgram);
    }
    
    static native void nglLinkProgram(final int p0, final long p1);
    
    public static void glUseProgram(final int n) {
        final long glUseProgram = GLContext.getCapabilities().glUseProgram;
        BufferChecks.checkFunctionAddress(glUseProgram);
        nglUseProgram(n, glUseProgram);
    }
    
    static native void nglUseProgram(final int p0, final long p1);
    
    public static void glValidateProgram(final int n) {
        final long glValidateProgram = GLContext.getCapabilities().glValidateProgram;
        BufferChecks.checkFunctionAddress(glValidateProgram);
        nglValidateProgram(n, glValidateProgram);
    }
    
    static native void nglValidateProgram(final int p0, final long p1);
    
    public static void glDeleteProgram(final int n) {
        final long glDeleteProgram = GLContext.getCapabilities().glDeleteProgram;
        BufferChecks.checkFunctionAddress(glDeleteProgram);
        nglDeleteProgram(n, glDeleteProgram);
    }
    
    static native void nglDeleteProgram(final int p0, final long p1);
    
    public static void glUniform1f(final int n, final float n2) {
        final long glUniform1f = GLContext.getCapabilities().glUniform1f;
        BufferChecks.checkFunctionAddress(glUniform1f);
        nglUniform1f(n, n2, glUniform1f);
    }
    
    static native void nglUniform1f(final int p0, final float p1, final long p2);
    
    public static void glUniform2f(final int n, final float n2, final float n3) {
        final long glUniform2f = GLContext.getCapabilities().glUniform2f;
        BufferChecks.checkFunctionAddress(glUniform2f);
        nglUniform2f(n, n2, n3, glUniform2f);
    }
    
    static native void nglUniform2f(final int p0, final float p1, final float p2, final long p3);
    
    public static void glUniform3f(final int n, final float n2, final float n3, final float n4) {
        final long glUniform3f = GLContext.getCapabilities().glUniform3f;
        BufferChecks.checkFunctionAddress(glUniform3f);
        nglUniform3f(n, n2, n3, n4, glUniform3f);
    }
    
    static native void nglUniform3f(final int p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glUniform4f(final int n, final float n2, final float n3, final float n4, final float n5) {
        final long glUniform4f = GLContext.getCapabilities().glUniform4f;
        BufferChecks.checkFunctionAddress(glUniform4f);
        nglUniform4f(n, n2, n3, n4, n5, glUniform4f);
    }
    
    static native void nglUniform4f(final int p0, final float p1, final float p2, final float p3, final float p4, final long p5);
    
    public static void glUniform1i(final int n, final int n2) {
        final long glUniform1i = GLContext.getCapabilities().glUniform1i;
        BufferChecks.checkFunctionAddress(glUniform1i);
        nglUniform1i(n, n2, glUniform1i);
    }
    
    static native void nglUniform1i(final int p0, final int p1, final long p2);
    
    public static void glUniform2i(final int n, final int n2, final int n3) {
        final long glUniform2i = GLContext.getCapabilities().glUniform2i;
        BufferChecks.checkFunctionAddress(glUniform2i);
        nglUniform2i(n, n2, n3, glUniform2i);
    }
    
    static native void nglUniform2i(final int p0, final int p1, final int p2, final long p3);
    
    public static void glUniform3i(final int n, final int n2, final int n3, final int n4) {
        final long glUniform3i = GLContext.getCapabilities().glUniform3i;
        BufferChecks.checkFunctionAddress(glUniform3i);
        nglUniform3i(n, n2, n3, n4, glUniform3i);
    }
    
    static native void nglUniform3i(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glUniform4i(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glUniform4i = GLContext.getCapabilities().glUniform4i;
        BufferChecks.checkFunctionAddress(glUniform4i);
        nglUniform4i(n, n2, n3, n4, n5, glUniform4i);
    }
    
    static native void nglUniform4i(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glUniform1(final int n, final FloatBuffer floatBuffer) {
        final long glUniform1fv = GLContext.getCapabilities().glUniform1fv;
        BufferChecks.checkFunctionAddress(glUniform1fv);
        BufferChecks.checkDirect(floatBuffer);
        nglUniform1fv(n, floatBuffer.remaining(), MemoryUtil.getAddress(floatBuffer), glUniform1fv);
    }
    
    static native void nglUniform1fv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform2(final int n, final FloatBuffer floatBuffer) {
        final long glUniform2fv = GLContext.getCapabilities().glUniform2fv;
        BufferChecks.checkFunctionAddress(glUniform2fv);
        BufferChecks.checkDirect(floatBuffer);
        nglUniform2fv(n, floatBuffer.remaining() >> 1, MemoryUtil.getAddress(floatBuffer), glUniform2fv);
    }
    
    static native void nglUniform2fv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform3(final int n, final FloatBuffer floatBuffer) {
        final long glUniform3fv = GLContext.getCapabilities().glUniform3fv;
        BufferChecks.checkFunctionAddress(glUniform3fv);
        BufferChecks.checkDirect(floatBuffer);
        nglUniform3fv(n, floatBuffer.remaining() / 3, MemoryUtil.getAddress(floatBuffer), glUniform3fv);
    }
    
    static native void nglUniform3fv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform4(final int n, final FloatBuffer floatBuffer) {
        final long glUniform4fv = GLContext.getCapabilities().glUniform4fv;
        BufferChecks.checkFunctionAddress(glUniform4fv);
        BufferChecks.checkDirect(floatBuffer);
        nglUniform4fv(n, floatBuffer.remaining() >> 2, MemoryUtil.getAddress(floatBuffer), glUniform4fv);
    }
    
    static native void nglUniform4fv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform1(final int n, final IntBuffer intBuffer) {
        final long glUniform1iv = GLContext.getCapabilities().glUniform1iv;
        BufferChecks.checkFunctionAddress(glUniform1iv);
        BufferChecks.checkDirect(intBuffer);
        nglUniform1iv(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glUniform1iv);
    }
    
    static native void nglUniform1iv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform2(final int n, final IntBuffer intBuffer) {
        final long glUniform2iv = GLContext.getCapabilities().glUniform2iv;
        BufferChecks.checkFunctionAddress(glUniform2iv);
        BufferChecks.checkDirect(intBuffer);
        nglUniform2iv(n, intBuffer.remaining() >> 1, MemoryUtil.getAddress(intBuffer), glUniform2iv);
    }
    
    static native void nglUniform2iv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform3(final int n, final IntBuffer intBuffer) {
        final long glUniform3iv = GLContext.getCapabilities().glUniform3iv;
        BufferChecks.checkFunctionAddress(glUniform3iv);
        BufferChecks.checkDirect(intBuffer);
        nglUniform3iv(n, intBuffer.remaining() / 3, MemoryUtil.getAddress(intBuffer), glUniform3iv);
    }
    
    static native void nglUniform3iv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform4(final int n, final IntBuffer intBuffer) {
        final long glUniform4iv = GLContext.getCapabilities().glUniform4iv;
        BufferChecks.checkFunctionAddress(glUniform4iv);
        BufferChecks.checkDirect(intBuffer);
        nglUniform4iv(n, intBuffer.remaining() >> 2, MemoryUtil.getAddress(intBuffer), glUniform4iv);
    }
    
    static native void nglUniform4iv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniformMatrix2(final int n, final boolean b, final FloatBuffer floatBuffer) {
        final long glUniformMatrix2fv = GLContext.getCapabilities().glUniformMatrix2fv;
        BufferChecks.checkFunctionAddress(glUniformMatrix2fv);
        BufferChecks.checkDirect(floatBuffer);
        nglUniformMatrix2fv(n, floatBuffer.remaining() >> 2, b, MemoryUtil.getAddress(floatBuffer), glUniformMatrix2fv);
    }
    
    static native void nglUniformMatrix2fv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glUniformMatrix3(final int n, final boolean b, final FloatBuffer floatBuffer) {
        final long glUniformMatrix3fv = GLContext.getCapabilities().glUniformMatrix3fv;
        BufferChecks.checkFunctionAddress(glUniformMatrix3fv);
        BufferChecks.checkDirect(floatBuffer);
        nglUniformMatrix3fv(n, floatBuffer.remaining() / 9, b, MemoryUtil.getAddress(floatBuffer), glUniformMatrix3fv);
    }
    
    static native void nglUniformMatrix3fv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glUniformMatrix4(final int n, final boolean b, final FloatBuffer floatBuffer) {
        final long glUniformMatrix4fv = GLContext.getCapabilities().glUniformMatrix4fv;
        BufferChecks.checkFunctionAddress(glUniformMatrix4fv);
        BufferChecks.checkDirect(floatBuffer);
        nglUniformMatrix4fv(n, floatBuffer.remaining() >> 4, b, MemoryUtil.getAddress(floatBuffer), glUniformMatrix4fv);
    }
    
    static native void nglUniformMatrix4fv(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glGetShader(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetShaderiv = GLContext.getCapabilities().glGetShaderiv;
        BufferChecks.checkFunctionAddress(glGetShaderiv);
        BufferChecks.checkDirect(intBuffer);
        nglGetShaderiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetShaderiv);
    }
    
    static native void nglGetShaderiv(final int p0, final int p1, final long p2, final long p3);
    
    @Deprecated
    public static int glGetShader(final int n, final int n2) {
        return glGetShaderi(n, n2);
    }
    
    public static int glGetShaderi(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetShaderiv = capabilities.glGetShaderiv;
        BufferChecks.checkFunctionAddress(glGetShaderiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetShaderiv(n, n2, MemoryUtil.getAddress(bufferInt), glGetShaderiv);
        return bufferInt.get(0);
    }
    
    public static void glGetProgram(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetProgramiv = GLContext.getCapabilities().glGetProgramiv;
        BufferChecks.checkFunctionAddress(glGetProgramiv);
        BufferChecks.checkDirect(intBuffer);
        nglGetProgramiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetProgramiv);
    }
    
    static native void nglGetProgramiv(final int p0, final int p1, final long p2, final long p3);
    
    @Deprecated
    public static int glGetProgram(final int n, final int n2) {
        return glGetProgrami(n, n2);
    }
    
    public static int glGetProgrami(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetProgramiv = capabilities.glGetProgramiv;
        BufferChecks.checkFunctionAddress(glGetProgramiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetProgramiv(n, n2, MemoryUtil.getAddress(bufferInt), glGetProgramiv);
        return bufferInt.get(0);
    }
    
    public static void glGetShaderInfoLog(final int n, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final long glGetShaderInfoLog = GLContext.getCapabilities().glGetShaderInfoLog;
        BufferChecks.checkFunctionAddress(glGetShaderInfoLog);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(byteBuffer);
        nglGetShaderInfoLog(n, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetShaderInfoLog);
    }
    
    static native void nglGetShaderInfoLog(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static String glGetShaderInfoLog(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetShaderInfoLog = capabilities.glGetShaderInfoLog;
        BufferChecks.checkFunctionAddress(glGetShaderInfoLog);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n2);
        nglGetShaderInfoLog(n, n2, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(bufferByte), glGetShaderInfoLog);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static void glGetProgramInfoLog(final int n, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final long glGetProgramInfoLog = GLContext.getCapabilities().glGetProgramInfoLog;
        BufferChecks.checkFunctionAddress(glGetProgramInfoLog);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(byteBuffer);
        nglGetProgramInfoLog(n, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetProgramInfoLog);
    }
    
    static native void nglGetProgramInfoLog(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static String glGetProgramInfoLog(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetProgramInfoLog = capabilities.glGetProgramInfoLog;
        BufferChecks.checkFunctionAddress(glGetProgramInfoLog);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n2);
        nglGetProgramInfoLog(n, n2, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(bufferByte), glGetProgramInfoLog);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static void glGetAttachedShaders(final int n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long glGetAttachedShaders = GLContext.getCapabilities().glGetAttachedShaders;
        BufferChecks.checkFunctionAddress(glGetAttachedShaders);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(intBuffer2);
        nglGetAttachedShaders(n, intBuffer2.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(intBuffer2), glGetAttachedShaders);
    }
    
    static native void nglGetAttachedShaders(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static int glGetUniformLocation(final int n, final ByteBuffer byteBuffer) {
        final long glGetUniformLocation = GLContext.getCapabilities().glGetUniformLocation;
        BufferChecks.checkFunctionAddress(glGetUniformLocation);
        BufferChecks.checkBuffer(byteBuffer, 1);
        BufferChecks.checkNullTerminated(byteBuffer);
        return nglGetUniformLocation(n, MemoryUtil.getAddress(byteBuffer), glGetUniformLocation);
    }
    
    static native int nglGetUniformLocation(final int p0, final long p1, final long p2);
    
    public static int glGetUniformLocation(final int n, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetUniformLocation = capabilities.glGetUniformLocation;
        BufferChecks.checkFunctionAddress(glGetUniformLocation);
        return nglGetUniformLocation(n, APIUtil.getBufferNT(capabilities, charSequence), glGetUniformLocation);
    }
    
    public static void glGetActiveUniform(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final ByteBuffer byteBuffer) {
        final long glGetActiveUniform = GLContext.getCapabilities().glGetActiveUniform;
        BufferChecks.checkFunctionAddress(glGetActiveUniform);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkBuffer(intBuffer2, 1);
        BufferChecks.checkBuffer(intBuffer3, 1);
        BufferChecks.checkDirect(byteBuffer);
        nglGetActiveUniform(n, n2, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(intBuffer3), MemoryUtil.getAddress(byteBuffer), glGetActiveUniform);
    }
    
    static native void nglGetActiveUniform(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5, final long p6, final long p7);
    
    public static String glGetActiveUniform(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveUniform = capabilities.glGetActiveUniform;
        BufferChecks.checkFunctionAddress(glGetActiveUniform);
        BufferChecks.checkBuffer(intBuffer, 2);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n3);
        nglGetActiveUniform(n, n2, n3, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer, intBuffer.position() + 1), MemoryUtil.getAddress(bufferByte), glGetActiveUniform);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static String glGetActiveUniform(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveUniform = capabilities.glGetActiveUniform;
        BufferChecks.checkFunctionAddress(glGetActiveUniform);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n3);
        nglGetActiveUniform(n, n2, n3, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress0(APIUtil.getBufferInt(capabilities)), MemoryUtil.getAddress(APIUtil.getBufferInt(capabilities), 1), MemoryUtil.getAddress(bufferByte), glGetActiveUniform);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static int glGetActiveUniformSize(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveUniform = capabilities.glGetActiveUniform;
        BufferChecks.checkFunctionAddress(glGetActiveUniform);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetActiveUniform(n, n2, 1, 0L, MemoryUtil.getAddress(bufferInt), MemoryUtil.getAddress(bufferInt, 1), APIUtil.getBufferByte0(capabilities), glGetActiveUniform);
        return bufferInt.get(0);
    }
    
    public static int glGetActiveUniformType(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveUniform = capabilities.glGetActiveUniform;
        BufferChecks.checkFunctionAddress(glGetActiveUniform);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetActiveUniform(n, n2, 0, 0L, MemoryUtil.getAddress(bufferInt, 1), MemoryUtil.getAddress(bufferInt), APIUtil.getBufferByte0(capabilities), glGetActiveUniform);
        return bufferInt.get(0);
    }
    
    public static void glGetUniform(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetUniformfv = GLContext.getCapabilities().glGetUniformfv;
        BufferChecks.checkFunctionAddress(glGetUniformfv);
        BufferChecks.checkDirect(floatBuffer);
        nglGetUniformfv(n, n2, MemoryUtil.getAddress(floatBuffer), glGetUniformfv);
    }
    
    static native void nglGetUniformfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetUniform(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetUniformiv = GLContext.getCapabilities().glGetUniformiv;
        BufferChecks.checkFunctionAddress(glGetUniformiv);
        BufferChecks.checkDirect(intBuffer);
        nglGetUniformiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetUniformiv);
    }
    
    static native void nglGetUniformiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetShaderSource(final int n, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final long glGetShaderSource = GLContext.getCapabilities().glGetShaderSource;
        BufferChecks.checkFunctionAddress(glGetShaderSource);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(byteBuffer);
        nglGetShaderSource(n, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetShaderSource);
    }
    
    static native void nglGetShaderSource(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static String glGetShaderSource(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetShaderSource = capabilities.glGetShaderSource;
        BufferChecks.checkFunctionAddress(glGetShaderSource);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n2);
        nglGetShaderSource(n, n2, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(bufferByte), glGetShaderSource);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static void glVertexAttrib1s(final int n, final short n2) {
        final long glVertexAttrib1s = GLContext.getCapabilities().glVertexAttrib1s;
        BufferChecks.checkFunctionAddress(glVertexAttrib1s);
        nglVertexAttrib1s(n, n2, glVertexAttrib1s);
    }
    
    static native void nglVertexAttrib1s(final int p0, final short p1, final long p2);
    
    public static void glVertexAttrib1f(final int n, final float n2) {
        final long glVertexAttrib1f = GLContext.getCapabilities().glVertexAttrib1f;
        BufferChecks.checkFunctionAddress(glVertexAttrib1f);
        nglVertexAttrib1f(n, n2, glVertexAttrib1f);
    }
    
    static native void nglVertexAttrib1f(final int p0, final float p1, final long p2);
    
    public static void glVertexAttrib1d(final int n, final double n2) {
        final long glVertexAttrib1d = GLContext.getCapabilities().glVertexAttrib1d;
        BufferChecks.checkFunctionAddress(glVertexAttrib1d);
        nglVertexAttrib1d(n, n2, glVertexAttrib1d);
    }
    
    static native void nglVertexAttrib1d(final int p0, final double p1, final long p2);
    
    public static void glVertexAttrib2s(final int n, final short n2, final short n3) {
        final long glVertexAttrib2s = GLContext.getCapabilities().glVertexAttrib2s;
        BufferChecks.checkFunctionAddress(glVertexAttrib2s);
        nglVertexAttrib2s(n, n2, n3, glVertexAttrib2s);
    }
    
    static native void nglVertexAttrib2s(final int p0, final short p1, final short p2, final long p3);
    
    public static void glVertexAttrib2f(final int n, final float n2, final float n3) {
        final long glVertexAttrib2f = GLContext.getCapabilities().glVertexAttrib2f;
        BufferChecks.checkFunctionAddress(glVertexAttrib2f);
        nglVertexAttrib2f(n, n2, n3, glVertexAttrib2f);
    }
    
    static native void nglVertexAttrib2f(final int p0, final float p1, final float p2, final long p3);
    
    public static void glVertexAttrib2d(final int n, final double n2, final double n3) {
        final long glVertexAttrib2d = GLContext.getCapabilities().glVertexAttrib2d;
        BufferChecks.checkFunctionAddress(glVertexAttrib2d);
        nglVertexAttrib2d(n, n2, n3, glVertexAttrib2d);
    }
    
    static native void nglVertexAttrib2d(final int p0, final double p1, final double p2, final long p3);
    
    public static void glVertexAttrib3s(final int n, final short n2, final short n3, final short n4) {
        final long glVertexAttrib3s = GLContext.getCapabilities().glVertexAttrib3s;
        BufferChecks.checkFunctionAddress(glVertexAttrib3s);
        nglVertexAttrib3s(n, n2, n3, n4, glVertexAttrib3s);
    }
    
    static native void nglVertexAttrib3s(final int p0, final short p1, final short p2, final short p3, final long p4);
    
    public static void glVertexAttrib3f(final int n, final float n2, final float n3, final float n4) {
        final long glVertexAttrib3f = GLContext.getCapabilities().glVertexAttrib3f;
        BufferChecks.checkFunctionAddress(glVertexAttrib3f);
        nglVertexAttrib3f(n, n2, n3, n4, glVertexAttrib3f);
    }
    
    static native void nglVertexAttrib3f(final int p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glVertexAttrib3d(final int n, final double n2, final double n3, final double n4) {
        final long glVertexAttrib3d = GLContext.getCapabilities().glVertexAttrib3d;
        BufferChecks.checkFunctionAddress(glVertexAttrib3d);
        nglVertexAttrib3d(n, n2, n3, n4, glVertexAttrib3d);
    }
    
    static native void nglVertexAttrib3d(final int p0, final double p1, final double p2, final double p3, final long p4);
    
    public static void glVertexAttrib4s(final int n, final short n2, final short n3, final short n4, final short n5) {
        final long glVertexAttrib4s = GLContext.getCapabilities().glVertexAttrib4s;
        BufferChecks.checkFunctionAddress(glVertexAttrib4s);
        nglVertexAttrib4s(n, n2, n3, n4, n5, glVertexAttrib4s);
    }
    
    static native void nglVertexAttrib4s(final int p0, final short p1, final short p2, final short p3, final short p4, final long p5);
    
    public static void glVertexAttrib4f(final int n, final float n2, final float n3, final float n4, final float n5) {
        final long glVertexAttrib4f = GLContext.getCapabilities().glVertexAttrib4f;
        BufferChecks.checkFunctionAddress(glVertexAttrib4f);
        nglVertexAttrib4f(n, n2, n3, n4, n5, glVertexAttrib4f);
    }
    
    static native void nglVertexAttrib4f(final int p0, final float p1, final float p2, final float p3, final float p4, final long p5);
    
    public static void glVertexAttrib4d(final int n, final double n2, final double n3, final double n4, final double n5) {
        final long glVertexAttrib4d = GLContext.getCapabilities().glVertexAttrib4d;
        BufferChecks.checkFunctionAddress(glVertexAttrib4d);
        nglVertexAttrib4d(n, n2, n3, n4, n5, glVertexAttrib4d);
    }
    
    static native void nglVertexAttrib4d(final int p0, final double p1, final double p2, final double p3, final double p4, final long p5);
    
    public static void glVertexAttrib4Nub(final int n, final byte b, final byte b2, final byte b3, final byte b4) {
        final long glVertexAttrib4Nub = GLContext.getCapabilities().glVertexAttrib4Nub;
        BufferChecks.checkFunctionAddress(glVertexAttrib4Nub);
        nglVertexAttrib4Nub(n, b, b2, b3, b4, glVertexAttrib4Nub);
    }
    
    static native void nglVertexAttrib4Nub(final int p0, final byte p1, final byte p2, final byte p3, final byte p4, final long p5);
    
    public static void glVertexAttribPointer(final int n, final int n2, final boolean b, final int n3, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointer = capabilities.glVertexAttribPointer;
        BufferChecks.checkFunctionAddress(glVertexAttribPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = doubleBuffer;
        }
        nglVertexAttribPointer(n, n2, 5130, b, n3, MemoryUtil.getAddress(doubleBuffer), glVertexAttribPointer);
    }
    
    public static void glVertexAttribPointer(final int n, final int n2, final boolean b, final int n3, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointer = capabilities.glVertexAttribPointer;
        BufferChecks.checkFunctionAddress(glVertexAttribPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = floatBuffer;
        }
        nglVertexAttribPointer(n, n2, 5126, b, n3, MemoryUtil.getAddress(floatBuffer), glVertexAttribPointer);
    }
    
    public static void glVertexAttribPointer(final int n, final int n2, final boolean b, final boolean b2, final int n3, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointer = capabilities.glVertexAttribPointer;
        BufferChecks.checkFunctionAddress(glVertexAttribPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = byteBuffer;
        }
        nglVertexAttribPointer(n, n2, b ? 5121 : 5120, b2, n3, MemoryUtil.getAddress(byteBuffer), glVertexAttribPointer);
    }
    
    public static void glVertexAttribPointer(final int n, final int n2, final boolean b, final boolean b2, final int n3, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointer = capabilities.glVertexAttribPointer;
        BufferChecks.checkFunctionAddress(glVertexAttribPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = intBuffer;
        }
        nglVertexAttribPointer(n, n2, b ? 5125 : 5124, b2, n3, MemoryUtil.getAddress(intBuffer), glVertexAttribPointer);
    }
    
    public static void glVertexAttribPointer(final int n, final int n2, final boolean b, final boolean b2, final int n3, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointer = capabilities.glVertexAttribPointer;
        BufferChecks.checkFunctionAddress(glVertexAttribPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = shortBuffer;
        }
        nglVertexAttribPointer(n, n2, b ? 5123 : 5122, b2, n3, MemoryUtil.getAddress(shortBuffer), glVertexAttribPointer);
    }
    
    static native void nglVertexAttribPointer(final int p0, final int p1, final int p2, final boolean p3, final int p4, final long p5, final long p6);
    
    public static void glVertexAttribPointer(final int n, final int n2, final int n3, final boolean b, final int n4, final long n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointer = capabilities.glVertexAttribPointer;
        BufferChecks.checkFunctionAddress(glVertexAttribPointer);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglVertexAttribPointerBO(n, n2, n3, b, n4, n5, glVertexAttribPointer);
    }
    
    static native void nglVertexAttribPointerBO(final int p0, final int p1, final int p2, final boolean p3, final int p4, final long p5, final long p6);
    
    public static void glVertexAttribPointer(final int n, final int n2, final int n3, final boolean b, final int n4, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribPointer = capabilities.glVertexAttribPointer;
        BufferChecks.checkFunctionAddress(glVertexAttribPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = byteBuffer;
        }
        nglVertexAttribPointer(n, n2, n3, b, n4, MemoryUtil.getAddress(byteBuffer), glVertexAttribPointer);
    }
    
    public static void glEnableVertexAttribArray(final int n) {
        final long glEnableVertexAttribArray = GLContext.getCapabilities().glEnableVertexAttribArray;
        BufferChecks.checkFunctionAddress(glEnableVertexAttribArray);
        nglEnableVertexAttribArray(n, glEnableVertexAttribArray);
    }
    
    static native void nglEnableVertexAttribArray(final int p0, final long p1);
    
    public static void glDisableVertexAttribArray(final int n) {
        final long glDisableVertexAttribArray = GLContext.getCapabilities().glDisableVertexAttribArray;
        BufferChecks.checkFunctionAddress(glDisableVertexAttribArray);
        nglDisableVertexAttribArray(n, glDisableVertexAttribArray);
    }
    
    static native void nglDisableVertexAttribArray(final int p0, final long p1);
    
    public static void glGetVertexAttrib(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetVertexAttribfv = GLContext.getCapabilities().glGetVertexAttribfv;
        BufferChecks.checkFunctionAddress(glGetVertexAttribfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetVertexAttribfv(n, n2, MemoryUtil.getAddress(floatBuffer), glGetVertexAttribfv);
    }
    
    static native void nglGetVertexAttribfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetVertexAttrib(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glGetVertexAttribdv = GLContext.getCapabilities().glGetVertexAttribdv;
        BufferChecks.checkFunctionAddress(glGetVertexAttribdv);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglGetVertexAttribdv(n, n2, MemoryUtil.getAddress(doubleBuffer), glGetVertexAttribdv);
    }
    
    static native void nglGetVertexAttribdv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetVertexAttrib(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetVertexAttribiv = GLContext.getCapabilities().glGetVertexAttribiv;
        BufferChecks.checkFunctionAddress(glGetVertexAttribiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetVertexAttribiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetVertexAttribiv);
    }
    
    static native void nglGetVertexAttribiv(final int p0, final int p1, final long p2, final long p3);
    
    public static ByteBuffer glGetVertexAttribPointer(final int n, final int n2, final long n3) {
        final long glGetVertexAttribPointerv = GLContext.getCapabilities().glGetVertexAttribPointerv;
        BufferChecks.checkFunctionAddress(glGetVertexAttribPointerv);
        final ByteBuffer nglGetVertexAttribPointerv = nglGetVertexAttribPointerv(n, n2, n3, glGetVertexAttribPointerv);
        return (LWJGLUtil.CHECKS && nglGetVertexAttribPointerv == null) ? null : nglGetVertexAttribPointerv.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglGetVertexAttribPointerv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetVertexAttribPointer(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glGetVertexAttribPointerv = GLContext.getCapabilities().glGetVertexAttribPointerv;
        BufferChecks.checkFunctionAddress(glGetVertexAttribPointerv);
        BufferChecks.checkBuffer(byteBuffer, PointerBuffer.getPointerSize());
        nglGetVertexAttribPointerv2(n, n2, MemoryUtil.getAddress(byteBuffer), glGetVertexAttribPointerv);
    }
    
    static native void nglGetVertexAttribPointerv2(final int p0, final int p1, final long p2, final long p3);
    
    public static void glBindAttribLocation(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glBindAttribLocation = GLContext.getCapabilities().glBindAttribLocation;
        BufferChecks.checkFunctionAddress(glBindAttribLocation);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        nglBindAttribLocation(n, n2, MemoryUtil.getAddress(byteBuffer), glBindAttribLocation);
    }
    
    static native void nglBindAttribLocation(final int p0, final int p1, final long p2, final long p3);
    
    public static void glBindAttribLocation(final int n, final int n2, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glBindAttribLocation = capabilities.glBindAttribLocation;
        BufferChecks.checkFunctionAddress(glBindAttribLocation);
        nglBindAttribLocation(n, n2, APIUtil.getBufferNT(capabilities, charSequence), glBindAttribLocation);
    }
    
    public static void glGetActiveAttrib(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final ByteBuffer byteBuffer) {
        final long glGetActiveAttrib = GLContext.getCapabilities().glGetActiveAttrib;
        BufferChecks.checkFunctionAddress(glGetActiveAttrib);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkBuffer(intBuffer2, 1);
        BufferChecks.checkBuffer(intBuffer3, 1);
        BufferChecks.checkDirect(byteBuffer);
        nglGetActiveAttrib(n, n2, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(intBuffer3), MemoryUtil.getAddress(byteBuffer), glGetActiveAttrib);
    }
    
    static native void nglGetActiveAttrib(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5, final long p6, final long p7);
    
    public static String glGetActiveAttrib(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveAttrib = capabilities.glGetActiveAttrib;
        BufferChecks.checkFunctionAddress(glGetActiveAttrib);
        BufferChecks.checkBuffer(intBuffer, 2);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n3);
        nglGetActiveAttrib(n, n2, n3, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer, intBuffer.position() + 1), MemoryUtil.getAddress(bufferByte), glGetActiveAttrib);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static String glGetActiveAttrib(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveAttrib = capabilities.glGetActiveAttrib;
        BufferChecks.checkFunctionAddress(glGetActiveAttrib);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n3);
        nglGetActiveAttrib(n, n2, n3, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress0(APIUtil.getBufferInt(capabilities)), MemoryUtil.getAddress(APIUtil.getBufferInt(capabilities), 1), MemoryUtil.getAddress(bufferByte), glGetActiveAttrib);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static int glGetActiveAttribSize(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveAttrib = capabilities.glGetActiveAttrib;
        BufferChecks.checkFunctionAddress(glGetActiveAttrib);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetActiveAttrib(n, n2, 0, 0L, MemoryUtil.getAddress(bufferInt), MemoryUtil.getAddress(bufferInt, 1), APIUtil.getBufferByte0(capabilities), glGetActiveAttrib);
        return bufferInt.get(0);
    }
    
    public static int glGetActiveAttribType(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveAttrib = capabilities.glGetActiveAttrib;
        BufferChecks.checkFunctionAddress(glGetActiveAttrib);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetActiveAttrib(n, n2, 0, 0L, MemoryUtil.getAddress(bufferInt, 1), MemoryUtil.getAddress(bufferInt), APIUtil.getBufferByte0(capabilities), glGetActiveAttrib);
        return bufferInt.get(0);
    }
    
    public static int glGetAttribLocation(final int n, final ByteBuffer byteBuffer) {
        final long glGetAttribLocation = GLContext.getCapabilities().glGetAttribLocation;
        BufferChecks.checkFunctionAddress(glGetAttribLocation);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        return nglGetAttribLocation(n, MemoryUtil.getAddress(byteBuffer), glGetAttribLocation);
    }
    
    static native int nglGetAttribLocation(final int p0, final long p1, final long p2);
    
    public static int glGetAttribLocation(final int n, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetAttribLocation = capabilities.glGetAttribLocation;
        BufferChecks.checkFunctionAddress(glGetAttribLocation);
        return nglGetAttribLocation(n, APIUtil.getBufferNT(capabilities, charSequence), glGetAttribLocation);
    }
    
    public static void glDrawBuffers(final IntBuffer intBuffer) {
        final long glDrawBuffers = GLContext.getCapabilities().glDrawBuffers;
        BufferChecks.checkFunctionAddress(glDrawBuffers);
        BufferChecks.checkDirect(intBuffer);
        nglDrawBuffers(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDrawBuffers);
    }
    
    static native void nglDrawBuffers(final int p0, final long p1, final long p2);
    
    public static void glDrawBuffers(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawBuffers = capabilities.glDrawBuffers;
        BufferChecks.checkFunctionAddress(glDrawBuffers);
        nglDrawBuffers(1, APIUtil.getInt(capabilities, n), glDrawBuffers);
    }
    
    public static void glStencilOpSeparate(final int n, final int n2, final int n3, final int n4) {
        final long glStencilOpSeparate = GLContext.getCapabilities().glStencilOpSeparate;
        BufferChecks.checkFunctionAddress(glStencilOpSeparate);
        nglStencilOpSeparate(n, n2, n3, n4, glStencilOpSeparate);
    }
    
    static native void nglStencilOpSeparate(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glStencilFuncSeparate(final int n, final int n2, final int n3, final int n4) {
        final long glStencilFuncSeparate = GLContext.getCapabilities().glStencilFuncSeparate;
        BufferChecks.checkFunctionAddress(glStencilFuncSeparate);
        nglStencilFuncSeparate(n, n2, n3, n4, glStencilFuncSeparate);
    }
    
    static native void nglStencilFuncSeparate(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glStencilMaskSeparate(final int n, final int n2) {
        final long glStencilMaskSeparate = GLContext.getCapabilities().glStencilMaskSeparate;
        BufferChecks.checkFunctionAddress(glStencilMaskSeparate);
        nglStencilMaskSeparate(n, n2, glStencilMaskSeparate);
    }
    
    static native void nglStencilMaskSeparate(final int p0, final int p1, final long p2);
    
    public static void glBlendEquationSeparate(final int n, final int n2) {
        final long glBlendEquationSeparate = GLContext.getCapabilities().glBlendEquationSeparate;
        BufferChecks.checkFunctionAddress(glBlendEquationSeparate);
        nglBlendEquationSeparate(n, n2, glBlendEquationSeparate);
    }
    
    static native void nglBlendEquationSeparate(final int p0, final int p1, final long p2);
}
