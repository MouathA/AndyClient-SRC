package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public class NVProgram
{
    public static final int GL_PROGRAM_TARGET_NV = 34374;
    public static final int GL_PROGRAM_LENGTH_NV = 34343;
    public static final int GL_PROGRAM_RESIDENT_NV = 34375;
    public static final int GL_PROGRAM_STRING_NV = 34344;
    public static final int GL_PROGRAM_ERROR_POSITION_NV = 34379;
    public static final int GL_PROGRAM_ERROR_STRING_NV = 34932;
    
    public static void glLoadProgramNV(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glLoadProgramNV = GLContext.getCapabilities().glLoadProgramNV;
        BufferChecks.checkFunctionAddress(glLoadProgramNV);
        BufferChecks.checkDirect(byteBuffer);
        nglLoadProgramNV(n, n2, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glLoadProgramNV);
    }
    
    static native void nglLoadProgramNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glLoadProgramNV(final int n, final int n2, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glLoadProgramNV = capabilities.glLoadProgramNV;
        BufferChecks.checkFunctionAddress(glLoadProgramNV);
        nglLoadProgramNV(n, n2, charSequence.length(), APIUtil.getBuffer(capabilities, charSequence), glLoadProgramNV);
    }
    
    public static void glBindProgramNV(final int n, final int n2) {
        final long glBindProgramNV = GLContext.getCapabilities().glBindProgramNV;
        BufferChecks.checkFunctionAddress(glBindProgramNV);
        nglBindProgramNV(n, n2, glBindProgramNV);
    }
    
    static native void nglBindProgramNV(final int p0, final int p1, final long p2);
    
    public static void glDeleteProgramsNV(final IntBuffer intBuffer) {
        final long glDeleteProgramsNV = GLContext.getCapabilities().glDeleteProgramsNV;
        BufferChecks.checkFunctionAddress(glDeleteProgramsNV);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteProgramsNV(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteProgramsNV);
    }
    
    static native void nglDeleteProgramsNV(final int p0, final long p1, final long p2);
    
    public static void glDeleteProgramsNV(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteProgramsNV = capabilities.glDeleteProgramsNV;
        BufferChecks.checkFunctionAddress(glDeleteProgramsNV);
        nglDeleteProgramsNV(1, APIUtil.getInt(capabilities, n), glDeleteProgramsNV);
    }
    
    public static void glGenProgramsNV(final IntBuffer intBuffer) {
        final long glGenProgramsNV = GLContext.getCapabilities().glGenProgramsNV;
        BufferChecks.checkFunctionAddress(glGenProgramsNV);
        BufferChecks.checkDirect(intBuffer);
        nglGenProgramsNV(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenProgramsNV);
    }
    
    static native void nglGenProgramsNV(final int p0, final long p1, final long p2);
    
    public static int glGenProgramsNV() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenProgramsNV = capabilities.glGenProgramsNV;
        BufferChecks.checkFunctionAddress(glGenProgramsNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenProgramsNV(1, MemoryUtil.getAddress(bufferInt), glGenProgramsNV);
        return bufferInt.get(0);
    }
    
    public static void glGetProgramNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetProgramivNV = GLContext.getCapabilities().glGetProgramivNV;
        BufferChecks.checkFunctionAddress(glGetProgramivNV);
        BufferChecks.checkDirect(intBuffer);
        nglGetProgramivNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetProgramivNV);
    }
    
    static native void nglGetProgramivNV(final int p0, final int p1, final long p2, final long p3);
    
    @Deprecated
    public static int glGetProgramNV(final int n, final int n2) {
        return glGetProgramiNV(n, n2);
    }
    
    public static int glGetProgramiNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetProgramivNV = capabilities.glGetProgramivNV;
        BufferChecks.checkFunctionAddress(glGetProgramivNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetProgramivNV(n, n2, MemoryUtil.getAddress(bufferInt), glGetProgramivNV);
        return bufferInt.get(0);
    }
    
    public static void glGetProgramStringNV(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glGetProgramStringNV = GLContext.getCapabilities().glGetProgramStringNV;
        BufferChecks.checkFunctionAddress(glGetProgramStringNV);
        BufferChecks.checkDirect(byteBuffer);
        nglGetProgramStringNV(n, n2, MemoryUtil.getAddress(byteBuffer), glGetProgramStringNV);
    }
    
    static native void nglGetProgramStringNV(final int p0, final int p1, final long p2, final long p3);
    
    public static String glGetProgramStringNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetProgramStringNV = capabilities.glGetProgramStringNV;
        BufferChecks.checkFunctionAddress(glGetProgramStringNV);
        final int glGetProgramiNV = glGetProgramiNV(n, 34343);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, glGetProgramiNV);
        nglGetProgramStringNV(n, n2, MemoryUtil.getAddress(bufferByte), glGetProgramStringNV);
        bufferByte.limit(glGetProgramiNV);
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static boolean glIsProgramNV(final int n) {
        final long glIsProgramNV = GLContext.getCapabilities().glIsProgramNV;
        BufferChecks.checkFunctionAddress(glIsProgramNV);
        return nglIsProgramNV(n, glIsProgramNV);
    }
    
    static native boolean nglIsProgramNV(final int p0, final long p1);
    
    public static boolean glAreProgramsResidentNV(final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final long glAreProgramsResidentNV = GLContext.getCapabilities().glAreProgramsResidentNV;
        BufferChecks.checkFunctionAddress(glAreProgramsResidentNV);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkBuffer(byteBuffer, intBuffer.remaining());
        return nglAreProgramsResidentNV(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(byteBuffer), glAreProgramsResidentNV);
    }
    
    static native boolean nglAreProgramsResidentNV(final int p0, final long p1, final long p2, final long p3);
    
    public static void glRequestResidentProgramsNV(final IntBuffer intBuffer) {
        final long glRequestResidentProgramsNV = GLContext.getCapabilities().glRequestResidentProgramsNV;
        BufferChecks.checkFunctionAddress(glRequestResidentProgramsNV);
        BufferChecks.checkDirect(intBuffer);
        nglRequestResidentProgramsNV(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glRequestResidentProgramsNV);
    }
    
    static native void nglRequestResidentProgramsNV(final int p0, final long p1, final long p2);
    
    public static void glRequestResidentProgramsNV(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glRequestResidentProgramsNV = capabilities.glRequestResidentProgramsNV;
        BufferChecks.checkFunctionAddress(glRequestResidentProgramsNV);
        nglRequestResidentProgramsNV(1, APIUtil.getInt(capabilities, n), glRequestResidentProgramsNV);
    }
}
