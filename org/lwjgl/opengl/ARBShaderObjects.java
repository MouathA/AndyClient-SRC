package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class ARBShaderObjects
{
    public static final int GL_PROGRAM_OBJECT_ARB = 35648;
    public static final int GL_OBJECT_TYPE_ARB = 35662;
    public static final int GL_OBJECT_SUBTYPE_ARB = 35663;
    public static final int GL_OBJECT_DELETE_STATUS_ARB = 35712;
    public static final int GL_OBJECT_COMPILE_STATUS_ARB = 35713;
    public static final int GL_OBJECT_LINK_STATUS_ARB = 35714;
    public static final int GL_OBJECT_VALIDATE_STATUS_ARB = 35715;
    public static final int GL_OBJECT_INFO_LOG_LENGTH_ARB = 35716;
    public static final int GL_OBJECT_ATTACHED_OBJECTS_ARB = 35717;
    public static final int GL_OBJECT_ACTIVE_UNIFORMS_ARB = 35718;
    public static final int GL_OBJECT_ACTIVE_UNIFORM_MAX_LENGTH_ARB = 35719;
    public static final int GL_OBJECT_SHADER_SOURCE_LENGTH_ARB = 35720;
    public static final int GL_SHADER_OBJECT_ARB = 35656;
    public static final int GL_FLOAT_VEC2_ARB = 35664;
    public static final int GL_FLOAT_VEC3_ARB = 35665;
    public static final int GL_FLOAT_VEC4_ARB = 35666;
    public static final int GL_INT_VEC2_ARB = 35667;
    public static final int GL_INT_VEC3_ARB = 35668;
    public static final int GL_INT_VEC4_ARB = 35669;
    public static final int GL_BOOL_ARB = 35670;
    public static final int GL_BOOL_VEC2_ARB = 35671;
    public static final int GL_BOOL_VEC3_ARB = 35672;
    public static final int GL_BOOL_VEC4_ARB = 35673;
    public static final int GL_FLOAT_MAT2_ARB = 35674;
    public static final int GL_FLOAT_MAT3_ARB = 35675;
    public static final int GL_FLOAT_MAT4_ARB = 35676;
    public static final int GL_SAMPLER_1D_ARB = 35677;
    public static final int GL_SAMPLER_2D_ARB = 35678;
    public static final int GL_SAMPLER_3D_ARB = 35679;
    public static final int GL_SAMPLER_CUBE_ARB = 35680;
    public static final int GL_SAMPLER_1D_SHADOW_ARB = 35681;
    public static final int GL_SAMPLER_2D_SHADOW_ARB = 35682;
    public static final int GL_SAMPLER_2D_RECT_ARB = 35683;
    public static final int GL_SAMPLER_2D_RECT_SHADOW_ARB = 35684;
    
    private ARBShaderObjects() {
    }
    
    public static void glDeleteObjectARB(final int n) {
        final long glDeleteObjectARB = GLContext.getCapabilities().glDeleteObjectARB;
        BufferChecks.checkFunctionAddress(glDeleteObjectARB);
        nglDeleteObjectARB(n, glDeleteObjectARB);
    }
    
    static native void nglDeleteObjectARB(final int p0, final long p1);
    
    public static int glGetHandleARB(final int n) {
        final long glGetHandleARB = GLContext.getCapabilities().glGetHandleARB;
        BufferChecks.checkFunctionAddress(glGetHandleARB);
        return nglGetHandleARB(n, glGetHandleARB);
    }
    
    static native int nglGetHandleARB(final int p0, final long p1);
    
    public static void glDetachObjectARB(final int n, final int n2) {
        final long glDetachObjectARB = GLContext.getCapabilities().glDetachObjectARB;
        BufferChecks.checkFunctionAddress(glDetachObjectARB);
        nglDetachObjectARB(n, n2, glDetachObjectARB);
    }
    
    static native void nglDetachObjectARB(final int p0, final int p1, final long p2);
    
    public static int glCreateShaderObjectARB(final int n) {
        final long glCreateShaderObjectARB = GLContext.getCapabilities().glCreateShaderObjectARB;
        BufferChecks.checkFunctionAddress(glCreateShaderObjectARB);
        return nglCreateShaderObjectARB(n, glCreateShaderObjectARB);
    }
    
    static native int nglCreateShaderObjectARB(final int p0, final long p1);
    
    public static void glShaderSourceARB(final int n, final ByteBuffer byteBuffer) {
        final long glShaderSourceARB = GLContext.getCapabilities().glShaderSourceARB;
        BufferChecks.checkFunctionAddress(glShaderSourceARB);
        BufferChecks.checkDirect(byteBuffer);
        nglShaderSourceARB(n, 1, MemoryUtil.getAddress(byteBuffer), byteBuffer.remaining(), glShaderSourceARB);
    }
    
    static native void nglShaderSourceARB(final int p0, final int p1, final long p2, final int p3, final long p4);
    
    public static void glShaderSourceARB(final int n, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glShaderSourceARB = capabilities.glShaderSourceARB;
        BufferChecks.checkFunctionAddress(glShaderSourceARB);
        nglShaderSourceARB(n, 1, APIUtil.getBuffer(capabilities, charSequence), charSequence.length(), glShaderSourceARB);
    }
    
    public static void glShaderSourceARB(final int n, final CharSequence[] array) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glShaderSourceARB = capabilities.glShaderSourceARB;
        BufferChecks.checkFunctionAddress(glShaderSourceARB);
        BufferChecks.checkArray(array);
        nglShaderSourceARB3(n, array.length, APIUtil.getBuffer(capabilities, array), APIUtil.getLengths(capabilities, array), glShaderSourceARB);
    }
    
    static native void nglShaderSourceARB3(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static void glCompileShaderARB(final int n) {
        final long glCompileShaderARB = GLContext.getCapabilities().glCompileShaderARB;
        BufferChecks.checkFunctionAddress(glCompileShaderARB);
        nglCompileShaderARB(n, glCompileShaderARB);
    }
    
    static native void nglCompileShaderARB(final int p0, final long p1);
    
    public static int glCreateProgramObjectARB() {
        final long glCreateProgramObjectARB = GLContext.getCapabilities().glCreateProgramObjectARB;
        BufferChecks.checkFunctionAddress(glCreateProgramObjectARB);
        return nglCreateProgramObjectARB(glCreateProgramObjectARB);
    }
    
    static native int nglCreateProgramObjectARB(final long p0);
    
    public static void glAttachObjectARB(final int n, final int n2) {
        final long glAttachObjectARB = GLContext.getCapabilities().glAttachObjectARB;
        BufferChecks.checkFunctionAddress(glAttachObjectARB);
        nglAttachObjectARB(n, n2, glAttachObjectARB);
    }
    
    static native void nglAttachObjectARB(final int p0, final int p1, final long p2);
    
    public static void glLinkProgramARB(final int n) {
        final long glLinkProgramARB = GLContext.getCapabilities().glLinkProgramARB;
        BufferChecks.checkFunctionAddress(glLinkProgramARB);
        nglLinkProgramARB(n, glLinkProgramARB);
    }
    
    static native void nglLinkProgramARB(final int p0, final long p1);
    
    public static void glUseProgramObjectARB(final int n) {
        final long glUseProgramObjectARB = GLContext.getCapabilities().glUseProgramObjectARB;
        BufferChecks.checkFunctionAddress(glUseProgramObjectARB);
        nglUseProgramObjectARB(n, glUseProgramObjectARB);
    }
    
    static native void nglUseProgramObjectARB(final int p0, final long p1);
    
    public static void glValidateProgramARB(final int n) {
        final long glValidateProgramARB = GLContext.getCapabilities().glValidateProgramARB;
        BufferChecks.checkFunctionAddress(glValidateProgramARB);
        nglValidateProgramARB(n, glValidateProgramARB);
    }
    
    static native void nglValidateProgramARB(final int p0, final long p1);
    
    public static void glUniform1fARB(final int n, final float n2) {
        final long glUniform1fARB = GLContext.getCapabilities().glUniform1fARB;
        BufferChecks.checkFunctionAddress(glUniform1fARB);
        nglUniform1fARB(n, n2, glUniform1fARB);
    }
    
    static native void nglUniform1fARB(final int p0, final float p1, final long p2);
    
    public static void glUniform2fARB(final int n, final float n2, final float n3) {
        final long glUniform2fARB = GLContext.getCapabilities().glUniform2fARB;
        BufferChecks.checkFunctionAddress(glUniform2fARB);
        nglUniform2fARB(n, n2, n3, glUniform2fARB);
    }
    
    static native void nglUniform2fARB(final int p0, final float p1, final float p2, final long p3);
    
    public static void glUniform3fARB(final int n, final float n2, final float n3, final float n4) {
        final long glUniform3fARB = GLContext.getCapabilities().glUniform3fARB;
        BufferChecks.checkFunctionAddress(glUniform3fARB);
        nglUniform3fARB(n, n2, n3, n4, glUniform3fARB);
    }
    
    static native void nglUniform3fARB(final int p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glUniform4fARB(final int n, final float n2, final float n3, final float n4, final float n5) {
        final long glUniform4fARB = GLContext.getCapabilities().glUniform4fARB;
        BufferChecks.checkFunctionAddress(glUniform4fARB);
        nglUniform4fARB(n, n2, n3, n4, n5, glUniform4fARB);
    }
    
    static native void nglUniform4fARB(final int p0, final float p1, final float p2, final float p3, final float p4, final long p5);
    
    public static void glUniform1iARB(final int n, final int n2) {
        final long glUniform1iARB = GLContext.getCapabilities().glUniform1iARB;
        BufferChecks.checkFunctionAddress(glUniform1iARB);
        nglUniform1iARB(n, n2, glUniform1iARB);
    }
    
    static native void nglUniform1iARB(final int p0, final int p1, final long p2);
    
    public static void glUniform2iARB(final int n, final int n2, final int n3) {
        final long glUniform2iARB = GLContext.getCapabilities().glUniform2iARB;
        BufferChecks.checkFunctionAddress(glUniform2iARB);
        nglUniform2iARB(n, n2, n3, glUniform2iARB);
    }
    
    static native void nglUniform2iARB(final int p0, final int p1, final int p2, final long p3);
    
    public static void glUniform3iARB(final int n, final int n2, final int n3, final int n4) {
        final long glUniform3iARB = GLContext.getCapabilities().glUniform3iARB;
        BufferChecks.checkFunctionAddress(glUniform3iARB);
        nglUniform3iARB(n, n2, n3, n4, glUniform3iARB);
    }
    
    static native void nglUniform3iARB(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glUniform4iARB(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glUniform4iARB = GLContext.getCapabilities().glUniform4iARB;
        BufferChecks.checkFunctionAddress(glUniform4iARB);
        nglUniform4iARB(n, n2, n3, n4, n5, glUniform4iARB);
    }
    
    static native void nglUniform4iARB(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glUniform1ARB(final int n, final FloatBuffer floatBuffer) {
        final long glUniform1fvARB = GLContext.getCapabilities().glUniform1fvARB;
        BufferChecks.checkFunctionAddress(glUniform1fvARB);
        BufferChecks.checkDirect(floatBuffer);
        nglUniform1fvARB(n, floatBuffer.remaining(), MemoryUtil.getAddress(floatBuffer), glUniform1fvARB);
    }
    
    static native void nglUniform1fvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform2ARB(final int n, final FloatBuffer floatBuffer) {
        final long glUniform2fvARB = GLContext.getCapabilities().glUniform2fvARB;
        BufferChecks.checkFunctionAddress(glUniform2fvARB);
        BufferChecks.checkDirect(floatBuffer);
        nglUniform2fvARB(n, floatBuffer.remaining() >> 1, MemoryUtil.getAddress(floatBuffer), glUniform2fvARB);
    }
    
    static native void nglUniform2fvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform3ARB(final int n, final FloatBuffer floatBuffer) {
        final long glUniform3fvARB = GLContext.getCapabilities().glUniform3fvARB;
        BufferChecks.checkFunctionAddress(glUniform3fvARB);
        BufferChecks.checkDirect(floatBuffer);
        nglUniform3fvARB(n, floatBuffer.remaining() / 3, MemoryUtil.getAddress(floatBuffer), glUniform3fvARB);
    }
    
    static native void nglUniform3fvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform4ARB(final int n, final FloatBuffer floatBuffer) {
        final long glUniform4fvARB = GLContext.getCapabilities().glUniform4fvARB;
        BufferChecks.checkFunctionAddress(glUniform4fvARB);
        BufferChecks.checkDirect(floatBuffer);
        nglUniform4fvARB(n, floatBuffer.remaining() >> 2, MemoryUtil.getAddress(floatBuffer), glUniform4fvARB);
    }
    
    static native void nglUniform4fvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform1ARB(final int n, final IntBuffer intBuffer) {
        final long glUniform1ivARB = GLContext.getCapabilities().glUniform1ivARB;
        BufferChecks.checkFunctionAddress(glUniform1ivARB);
        BufferChecks.checkDirect(intBuffer);
        nglUniform1ivARB(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glUniform1ivARB);
    }
    
    static native void nglUniform1ivARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform2ARB(final int n, final IntBuffer intBuffer) {
        final long glUniform2ivARB = GLContext.getCapabilities().glUniform2ivARB;
        BufferChecks.checkFunctionAddress(glUniform2ivARB);
        BufferChecks.checkDirect(intBuffer);
        nglUniform2ivARB(n, intBuffer.remaining() >> 1, MemoryUtil.getAddress(intBuffer), glUniform2ivARB);
    }
    
    static native void nglUniform2ivARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform3ARB(final int n, final IntBuffer intBuffer) {
        final long glUniform3ivARB = GLContext.getCapabilities().glUniform3ivARB;
        BufferChecks.checkFunctionAddress(glUniform3ivARB);
        BufferChecks.checkDirect(intBuffer);
        nglUniform3ivARB(n, intBuffer.remaining() / 3, MemoryUtil.getAddress(intBuffer), glUniform3ivARB);
    }
    
    static native void nglUniform3ivARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform4ARB(final int n, final IntBuffer intBuffer) {
        final long glUniform4ivARB = GLContext.getCapabilities().glUniform4ivARB;
        BufferChecks.checkFunctionAddress(glUniform4ivARB);
        BufferChecks.checkDirect(intBuffer);
        nglUniform4ivARB(n, intBuffer.remaining() >> 2, MemoryUtil.getAddress(intBuffer), glUniform4ivARB);
    }
    
    static native void nglUniform4ivARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniformMatrix2ARB(final int n, final boolean b, final FloatBuffer floatBuffer) {
        final long glUniformMatrix2fvARB = GLContext.getCapabilities().glUniformMatrix2fvARB;
        BufferChecks.checkFunctionAddress(glUniformMatrix2fvARB);
        BufferChecks.checkDirect(floatBuffer);
        nglUniformMatrix2fvARB(n, floatBuffer.remaining() >> 2, b, MemoryUtil.getAddress(floatBuffer), glUniformMatrix2fvARB);
    }
    
    static native void nglUniformMatrix2fvARB(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glUniformMatrix3ARB(final int n, final boolean b, final FloatBuffer floatBuffer) {
        final long glUniformMatrix3fvARB = GLContext.getCapabilities().glUniformMatrix3fvARB;
        BufferChecks.checkFunctionAddress(glUniformMatrix3fvARB);
        BufferChecks.checkDirect(floatBuffer);
        nglUniformMatrix3fvARB(n, floatBuffer.remaining() / 9, b, MemoryUtil.getAddress(floatBuffer), glUniformMatrix3fvARB);
    }
    
    static native void nglUniformMatrix3fvARB(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glUniformMatrix4ARB(final int n, final boolean b, final FloatBuffer floatBuffer) {
        final long glUniformMatrix4fvARB = GLContext.getCapabilities().glUniformMatrix4fvARB;
        BufferChecks.checkFunctionAddress(glUniformMatrix4fvARB);
        BufferChecks.checkDirect(floatBuffer);
        nglUniformMatrix4fvARB(n, floatBuffer.remaining() >> 4, b, MemoryUtil.getAddress(floatBuffer), glUniformMatrix4fvARB);
    }
    
    static native void nglUniformMatrix4fvARB(final int p0, final int p1, final boolean p2, final long p3, final long p4);
    
    public static void glGetObjectParameterARB(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetObjectParameterfvARB = GLContext.getCapabilities().glGetObjectParameterfvARB;
        BufferChecks.checkFunctionAddress(glGetObjectParameterfvARB);
        BufferChecks.checkDirect(floatBuffer);
        nglGetObjectParameterfvARB(n, n2, MemoryUtil.getAddress(floatBuffer), glGetObjectParameterfvARB);
    }
    
    static native void nglGetObjectParameterfvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static float glGetObjectParameterfARB(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetObjectParameterfvARB = capabilities.glGetObjectParameterfvARB;
        BufferChecks.checkFunctionAddress(glGetObjectParameterfvARB);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetObjectParameterfvARB(n, n2, MemoryUtil.getAddress(bufferFloat), glGetObjectParameterfvARB);
        return bufferFloat.get(0);
    }
    
    public static void glGetObjectParameterARB(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetObjectParameterivARB = GLContext.getCapabilities().glGetObjectParameterivARB;
        BufferChecks.checkFunctionAddress(glGetObjectParameterivARB);
        BufferChecks.checkDirect(intBuffer);
        nglGetObjectParameterivARB(n, n2, MemoryUtil.getAddress(intBuffer), glGetObjectParameterivARB);
    }
    
    static native void nglGetObjectParameterivARB(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetObjectParameteriARB(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetObjectParameterivARB = capabilities.glGetObjectParameterivARB;
        BufferChecks.checkFunctionAddress(glGetObjectParameterivARB);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetObjectParameterivARB(n, n2, MemoryUtil.getAddress(bufferInt), glGetObjectParameterivARB);
        return bufferInt.get(0);
    }
    
    public static void glGetInfoLogARB(final int n, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final long glGetInfoLogARB = GLContext.getCapabilities().glGetInfoLogARB;
        BufferChecks.checkFunctionAddress(glGetInfoLogARB);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(byteBuffer);
        nglGetInfoLogARB(n, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetInfoLogARB);
    }
    
    static native void nglGetInfoLogARB(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static String glGetInfoLogARB(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetInfoLogARB = capabilities.glGetInfoLogARB;
        BufferChecks.checkFunctionAddress(glGetInfoLogARB);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n2);
        nglGetInfoLogARB(n, n2, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(bufferByte), glGetInfoLogARB);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static void glGetAttachedObjectsARB(final int n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long glGetAttachedObjectsARB = GLContext.getCapabilities().glGetAttachedObjectsARB;
        BufferChecks.checkFunctionAddress(glGetAttachedObjectsARB);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(intBuffer2);
        nglGetAttachedObjectsARB(n, intBuffer2.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(intBuffer2), glGetAttachedObjectsARB);
    }
    
    static native void nglGetAttachedObjectsARB(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static int glGetUniformLocationARB(final int n, final ByteBuffer byteBuffer) {
        final long glGetUniformLocationARB = GLContext.getCapabilities().glGetUniformLocationARB;
        BufferChecks.checkFunctionAddress(glGetUniformLocationARB);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        return nglGetUniformLocationARB(n, MemoryUtil.getAddress(byteBuffer), glGetUniformLocationARB);
    }
    
    static native int nglGetUniformLocationARB(final int p0, final long p1, final long p2);
    
    public static int glGetUniformLocationARB(final int n, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetUniformLocationARB = capabilities.glGetUniformLocationARB;
        BufferChecks.checkFunctionAddress(glGetUniformLocationARB);
        return nglGetUniformLocationARB(n, APIUtil.getBufferNT(capabilities, charSequence), glGetUniformLocationARB);
    }
    
    public static void glGetActiveUniformARB(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final ByteBuffer byteBuffer) {
        final long glGetActiveUniformARB = GLContext.getCapabilities().glGetActiveUniformARB;
        BufferChecks.checkFunctionAddress(glGetActiveUniformARB);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkBuffer(intBuffer2, 1);
        BufferChecks.checkBuffer(intBuffer3, 1);
        BufferChecks.checkDirect(byteBuffer);
        nglGetActiveUniformARB(n, n2, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(intBuffer3), MemoryUtil.getAddress(byteBuffer), glGetActiveUniformARB);
    }
    
    static native void nglGetActiveUniformARB(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5, final long p6, final long p7);
    
    public static String glGetActiveUniformARB(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveUniformARB = capabilities.glGetActiveUniformARB;
        BufferChecks.checkFunctionAddress(glGetActiveUniformARB);
        BufferChecks.checkBuffer(intBuffer, 2);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n3);
        nglGetActiveUniformARB(n, n2, n3, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer, intBuffer.position() + 1), MemoryUtil.getAddress(bufferByte), glGetActiveUniformARB);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static String glGetActiveUniformARB(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveUniformARB = capabilities.glGetActiveUniformARB;
        BufferChecks.checkFunctionAddress(glGetActiveUniformARB);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n3);
        nglGetActiveUniformARB(n, n2, n3, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress0(APIUtil.getBufferInt(capabilities)), MemoryUtil.getAddress(APIUtil.getBufferInt(capabilities), 1), MemoryUtil.getAddress(bufferByte), glGetActiveUniformARB);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static int glGetActiveUniformSizeARB(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveUniformARB = capabilities.glGetActiveUniformARB;
        BufferChecks.checkFunctionAddress(glGetActiveUniformARB);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetActiveUniformARB(n, n2, 0, 0L, MemoryUtil.getAddress(bufferInt), MemoryUtil.getAddress(bufferInt, 1), APIUtil.getBufferByte0(capabilities), glGetActiveUniformARB);
        return bufferInt.get(0);
    }
    
    public static int glGetActiveUniformTypeARB(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetActiveUniformARB = capabilities.glGetActiveUniformARB;
        BufferChecks.checkFunctionAddress(glGetActiveUniformARB);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetActiveUniformARB(n, n2, 0, 0L, MemoryUtil.getAddress(bufferInt, 1), MemoryUtil.getAddress(bufferInt), APIUtil.getBufferByte0(capabilities), glGetActiveUniformARB);
        return bufferInt.get(0);
    }
    
    public static void glGetUniformARB(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetUniformfvARB = GLContext.getCapabilities().glGetUniformfvARB;
        BufferChecks.checkFunctionAddress(glGetUniformfvARB);
        BufferChecks.checkDirect(floatBuffer);
        nglGetUniformfvARB(n, n2, MemoryUtil.getAddress(floatBuffer), glGetUniformfvARB);
    }
    
    static native void nglGetUniformfvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetUniformARB(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetUniformivARB = GLContext.getCapabilities().glGetUniformivARB;
        BufferChecks.checkFunctionAddress(glGetUniformivARB);
        BufferChecks.checkDirect(intBuffer);
        nglGetUniformivARB(n, n2, MemoryUtil.getAddress(intBuffer), glGetUniformivARB);
    }
    
    static native void nglGetUniformivARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetShaderSourceARB(final int n, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final long glGetShaderSourceARB = GLContext.getCapabilities().glGetShaderSourceARB;
        BufferChecks.checkFunctionAddress(glGetShaderSourceARB);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(byteBuffer);
        nglGetShaderSourceARB(n, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetShaderSourceARB);
    }
    
    static native void nglGetShaderSourceARB(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static String glGetShaderSourceARB(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetShaderSourceARB = capabilities.glGetShaderSourceARB;
        BufferChecks.checkFunctionAddress(glGetShaderSourceARB);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n2);
        nglGetShaderSourceARB(n, n2, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(bufferByte), glGetShaderSourceARB);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
}
