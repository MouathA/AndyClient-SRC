package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public class ARBProgram
{
    public static final int GL_PROGRAM_FORMAT_ASCII_ARB = 34933;
    public static final int GL_PROGRAM_LENGTH_ARB = 34343;
    public static final int GL_PROGRAM_FORMAT_ARB = 34934;
    public static final int GL_PROGRAM_BINDING_ARB = 34423;
    public static final int GL_PROGRAM_INSTRUCTIONS_ARB = 34976;
    public static final int GL_MAX_PROGRAM_INSTRUCTIONS_ARB = 34977;
    public static final int GL_PROGRAM_NATIVE_INSTRUCTIONS_ARB = 34978;
    public static final int GL_MAX_PROGRAM_NATIVE_INSTRUCTIONS_ARB = 34979;
    public static final int GL_PROGRAM_TEMPORARIES_ARB = 34980;
    public static final int GL_MAX_PROGRAM_TEMPORARIES_ARB = 34981;
    public static final int GL_PROGRAM_NATIVE_TEMPORARIES_ARB = 34982;
    public static final int GL_MAX_PROGRAM_NATIVE_TEMPORARIES_ARB = 34983;
    public static final int GL_PROGRAM_PARAMETERS_ARB = 34984;
    public static final int GL_MAX_PROGRAM_PARAMETERS_ARB = 34985;
    public static final int GL_PROGRAM_NATIVE_PARAMETERS_ARB = 34986;
    public static final int GL_MAX_PROGRAM_NATIVE_PARAMETERS_ARB = 34987;
    public static final int GL_PROGRAM_ATTRIBS_ARB = 34988;
    public static final int GL_MAX_PROGRAM_ATTRIBS_ARB = 34989;
    public static final int GL_PROGRAM_NATIVE_ATTRIBS_ARB = 34990;
    public static final int GL_MAX_PROGRAM_NATIVE_ATTRIBS_ARB = 34991;
    public static final int GL_MAX_PROGRAM_LOCAL_PARAMETERS_ARB = 34996;
    public static final int GL_MAX_PROGRAM_ENV_PARAMETERS_ARB = 34997;
    public static final int GL_PROGRAM_UNDER_NATIVE_LIMITS_ARB = 34998;
    public static final int GL_PROGRAM_STRING_ARB = 34344;
    public static final int GL_PROGRAM_ERROR_POSITION_ARB = 34379;
    public static final int GL_CURRENT_MATRIX_ARB = 34369;
    public static final int GL_TRANSPOSE_CURRENT_MATRIX_ARB = 34999;
    public static final int GL_CURRENT_MATRIX_STACK_DEPTH_ARB = 34368;
    public static final int GL_MAX_PROGRAM_MATRICES_ARB = 34351;
    public static final int GL_MAX_PROGRAM_MATRIX_STACK_DEPTH_ARB = 34350;
    public static final int GL_PROGRAM_ERROR_STRING_ARB = 34932;
    public static final int GL_MATRIX0_ARB = 35008;
    public static final int GL_MATRIX1_ARB = 35009;
    public static final int GL_MATRIX2_ARB = 35010;
    public static final int GL_MATRIX3_ARB = 35011;
    public static final int GL_MATRIX4_ARB = 35012;
    public static final int GL_MATRIX5_ARB = 35013;
    public static final int GL_MATRIX6_ARB = 35014;
    public static final int GL_MATRIX7_ARB = 35015;
    public static final int GL_MATRIX8_ARB = 35016;
    public static final int GL_MATRIX9_ARB = 35017;
    public static final int GL_MATRIX10_ARB = 35018;
    public static final int GL_MATRIX11_ARB = 35019;
    public static final int GL_MATRIX12_ARB = 35020;
    public static final int GL_MATRIX13_ARB = 35021;
    public static final int GL_MATRIX14_ARB = 35022;
    public static final int GL_MATRIX15_ARB = 35023;
    public static final int GL_MATRIX16_ARB = 35024;
    public static final int GL_MATRIX17_ARB = 35025;
    public static final int GL_MATRIX18_ARB = 35026;
    public static final int GL_MATRIX19_ARB = 35027;
    public static final int GL_MATRIX20_ARB = 35028;
    public static final int GL_MATRIX21_ARB = 35029;
    public static final int GL_MATRIX22_ARB = 35030;
    public static final int GL_MATRIX23_ARB = 35031;
    public static final int GL_MATRIX24_ARB = 35032;
    public static final int GL_MATRIX25_ARB = 35033;
    public static final int GL_MATRIX26_ARB = 35034;
    public static final int GL_MATRIX27_ARB = 35035;
    public static final int GL_MATRIX28_ARB = 35036;
    public static final int GL_MATRIX29_ARB = 35037;
    public static final int GL_MATRIX30_ARB = 35038;
    public static final int GL_MATRIX31_ARB = 35039;
    
    public static void glProgramStringARB(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glProgramStringARB = GLContext.getCapabilities().glProgramStringARB;
        BufferChecks.checkFunctionAddress(glProgramStringARB);
        BufferChecks.checkDirect(byteBuffer);
        nglProgramStringARB(n, n2, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glProgramStringARB);
    }
    
    static native void nglProgramStringARB(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glProgramStringARB(final int n, final int n2, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glProgramStringARB = capabilities.glProgramStringARB;
        BufferChecks.checkFunctionAddress(glProgramStringARB);
        nglProgramStringARB(n, n2, charSequence.length(), APIUtil.getBuffer(capabilities, charSequence), glProgramStringARB);
    }
    
    public static void glBindProgramARB(final int n, final int n2) {
        final long glBindProgramARB = GLContext.getCapabilities().glBindProgramARB;
        BufferChecks.checkFunctionAddress(glBindProgramARB);
        nglBindProgramARB(n, n2, glBindProgramARB);
    }
    
    static native void nglBindProgramARB(final int p0, final int p1, final long p2);
    
    public static void glDeleteProgramsARB(final IntBuffer intBuffer) {
        final long glDeleteProgramsARB = GLContext.getCapabilities().glDeleteProgramsARB;
        BufferChecks.checkFunctionAddress(glDeleteProgramsARB);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteProgramsARB(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteProgramsARB);
    }
    
    static native void nglDeleteProgramsARB(final int p0, final long p1, final long p2);
    
    public static void glDeleteProgramsARB(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteProgramsARB = capabilities.glDeleteProgramsARB;
        BufferChecks.checkFunctionAddress(glDeleteProgramsARB);
        nglDeleteProgramsARB(1, APIUtil.getInt(capabilities, n), glDeleteProgramsARB);
    }
    
    public static void glGenProgramsARB(final IntBuffer intBuffer) {
        final long glGenProgramsARB = GLContext.getCapabilities().glGenProgramsARB;
        BufferChecks.checkFunctionAddress(glGenProgramsARB);
        BufferChecks.checkDirect(intBuffer);
        nglGenProgramsARB(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenProgramsARB);
    }
    
    static native void nglGenProgramsARB(final int p0, final long p1, final long p2);
    
    public static int glGenProgramsARB() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenProgramsARB = capabilities.glGenProgramsARB;
        BufferChecks.checkFunctionAddress(glGenProgramsARB);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenProgramsARB(1, MemoryUtil.getAddress(bufferInt), glGenProgramsARB);
        return bufferInt.get(0);
    }
    
    public static void glProgramEnvParameter4fARB(final int n, final int n2, final float n3, final float n4, final float n5, final float n6) {
        final long glProgramEnvParameter4fARB = GLContext.getCapabilities().glProgramEnvParameter4fARB;
        BufferChecks.checkFunctionAddress(glProgramEnvParameter4fARB);
        nglProgramEnvParameter4fARB(n, n2, n3, n4, n5, n6, glProgramEnvParameter4fARB);
    }
    
    static native void nglProgramEnvParameter4fARB(final int p0, final int p1, final float p2, final float p3, final float p4, final float p5, final long p6);
    
    public static void glProgramEnvParameter4dARB(final int n, final int n2, final double n3, final double n4, final double n5, final double n6) {
        final long glProgramEnvParameter4dARB = GLContext.getCapabilities().glProgramEnvParameter4dARB;
        BufferChecks.checkFunctionAddress(glProgramEnvParameter4dARB);
        nglProgramEnvParameter4dARB(n, n2, n3, n4, n5, n6, glProgramEnvParameter4dARB);
    }
    
    static native void nglProgramEnvParameter4dARB(final int p0, final int p1, final double p2, final double p3, final double p4, final double p5, final long p6);
    
    public static void glProgramEnvParameter4ARB(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glProgramEnvParameter4fvARB = GLContext.getCapabilities().glProgramEnvParameter4fvARB;
        BufferChecks.checkFunctionAddress(glProgramEnvParameter4fvARB);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglProgramEnvParameter4fvARB(n, n2, MemoryUtil.getAddress(floatBuffer), glProgramEnvParameter4fvARB);
    }
    
    static native void nglProgramEnvParameter4fvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glProgramEnvParameter4ARB(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glProgramEnvParameter4dvARB = GLContext.getCapabilities().glProgramEnvParameter4dvARB;
        BufferChecks.checkFunctionAddress(glProgramEnvParameter4dvARB);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglProgramEnvParameter4dvARB(n, n2, MemoryUtil.getAddress(doubleBuffer), glProgramEnvParameter4dvARB);
    }
    
    static native void nglProgramEnvParameter4dvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glProgramLocalParameter4fARB(final int n, final int n2, final float n3, final float n4, final float n5, final float n6) {
        final long glProgramLocalParameter4fARB = GLContext.getCapabilities().glProgramLocalParameter4fARB;
        BufferChecks.checkFunctionAddress(glProgramLocalParameter4fARB);
        nglProgramLocalParameter4fARB(n, n2, n3, n4, n5, n6, glProgramLocalParameter4fARB);
    }
    
    static native void nglProgramLocalParameter4fARB(final int p0, final int p1, final float p2, final float p3, final float p4, final float p5, final long p6);
    
    public static void glProgramLocalParameter4dARB(final int n, final int n2, final double n3, final double n4, final double n5, final double n6) {
        final long glProgramLocalParameter4dARB = GLContext.getCapabilities().glProgramLocalParameter4dARB;
        BufferChecks.checkFunctionAddress(glProgramLocalParameter4dARB);
        nglProgramLocalParameter4dARB(n, n2, n3, n4, n5, n6, glProgramLocalParameter4dARB);
    }
    
    static native void nglProgramLocalParameter4dARB(final int p0, final int p1, final double p2, final double p3, final double p4, final double p5, final long p6);
    
    public static void glProgramLocalParameter4ARB(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glProgramLocalParameter4fvARB = GLContext.getCapabilities().glProgramLocalParameter4fvARB;
        BufferChecks.checkFunctionAddress(glProgramLocalParameter4fvARB);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglProgramLocalParameter4fvARB(n, n2, MemoryUtil.getAddress(floatBuffer), glProgramLocalParameter4fvARB);
    }
    
    static native void nglProgramLocalParameter4fvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glProgramLocalParameter4ARB(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glProgramLocalParameter4dvARB = GLContext.getCapabilities().glProgramLocalParameter4dvARB;
        BufferChecks.checkFunctionAddress(glProgramLocalParameter4dvARB);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglProgramLocalParameter4dvARB(n, n2, MemoryUtil.getAddress(doubleBuffer), glProgramLocalParameter4dvARB);
    }
    
    static native void nglProgramLocalParameter4dvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetProgramEnvParameterARB(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetProgramEnvParameterfvARB = GLContext.getCapabilities().glGetProgramEnvParameterfvARB;
        BufferChecks.checkFunctionAddress(glGetProgramEnvParameterfvARB);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetProgramEnvParameterfvARB(n, n2, MemoryUtil.getAddress(floatBuffer), glGetProgramEnvParameterfvARB);
    }
    
    static native void nglGetProgramEnvParameterfvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetProgramEnvParameterARB(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glGetProgramEnvParameterdvARB = GLContext.getCapabilities().glGetProgramEnvParameterdvARB;
        BufferChecks.checkFunctionAddress(glGetProgramEnvParameterdvARB);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglGetProgramEnvParameterdvARB(n, n2, MemoryUtil.getAddress(doubleBuffer), glGetProgramEnvParameterdvARB);
    }
    
    static native void nglGetProgramEnvParameterdvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetProgramLocalParameterARB(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetProgramLocalParameterfvARB = GLContext.getCapabilities().glGetProgramLocalParameterfvARB;
        BufferChecks.checkFunctionAddress(glGetProgramLocalParameterfvARB);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetProgramLocalParameterfvARB(n, n2, MemoryUtil.getAddress(floatBuffer), glGetProgramLocalParameterfvARB);
    }
    
    static native void nglGetProgramLocalParameterfvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetProgramLocalParameterARB(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glGetProgramLocalParameterdvARB = GLContext.getCapabilities().glGetProgramLocalParameterdvARB;
        BufferChecks.checkFunctionAddress(glGetProgramLocalParameterdvARB);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglGetProgramLocalParameterdvARB(n, n2, MemoryUtil.getAddress(doubleBuffer), glGetProgramLocalParameterdvARB);
    }
    
    static native void nglGetProgramLocalParameterdvARB(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetProgramARB(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetProgramivARB = GLContext.getCapabilities().glGetProgramivARB;
        BufferChecks.checkFunctionAddress(glGetProgramivARB);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetProgramivARB(n, n2, MemoryUtil.getAddress(intBuffer), glGetProgramivARB);
    }
    
    static native void nglGetProgramivARB(final int p0, final int p1, final long p2, final long p3);
    
    @Deprecated
    public static int glGetProgramARB(final int n, final int n2) {
        return glGetProgramiARB(n, n2);
    }
    
    public static int glGetProgramiARB(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetProgramivARB = capabilities.glGetProgramivARB;
        BufferChecks.checkFunctionAddress(glGetProgramivARB);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetProgramivARB(n, n2, MemoryUtil.getAddress(bufferInt), glGetProgramivARB);
        return bufferInt.get(0);
    }
    
    public static void glGetProgramStringARB(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glGetProgramStringARB = GLContext.getCapabilities().glGetProgramStringARB;
        BufferChecks.checkFunctionAddress(glGetProgramStringARB);
        BufferChecks.checkDirect(byteBuffer);
        nglGetProgramStringARB(n, n2, MemoryUtil.getAddress(byteBuffer), glGetProgramStringARB);
    }
    
    static native void nglGetProgramStringARB(final int p0, final int p1, final long p2, final long p3);
    
    public static String glGetProgramStringARB(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetProgramStringARB = capabilities.glGetProgramStringARB;
        BufferChecks.checkFunctionAddress(glGetProgramStringARB);
        final int glGetProgramiARB = glGetProgramiARB(n, 34343);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, glGetProgramiARB);
        nglGetProgramStringARB(n, n2, MemoryUtil.getAddress(bufferByte), glGetProgramStringARB);
        bufferByte.limit(glGetProgramiARB);
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static boolean glIsProgramARB(final int n) {
        final long glIsProgramARB = GLContext.getCapabilities().glIsProgramARB;
        BufferChecks.checkFunctionAddress(glIsProgramARB);
        return nglIsProgramARB(n, glIsProgramARB);
    }
    
    static native boolean nglIsProgramARB(final int p0, final long p1);
}
