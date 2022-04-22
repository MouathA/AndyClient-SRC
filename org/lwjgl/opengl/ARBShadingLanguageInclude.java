package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class ARBShadingLanguageInclude
{
    public static final int GL_SHADER_INCLUDE_ARB = 36270;
    public static final int GL_NAMED_STRING_LENGTH_ARB = 36329;
    public static final int GL_NAMED_STRING_TYPE_ARB = 36330;
    
    private ARBShadingLanguageInclude() {
    }
    
    public static void glNamedStringARB(final int n, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) {
        final long glNamedStringARB = GLContext.getCapabilities().glNamedStringARB;
        BufferChecks.checkFunctionAddress(glNamedStringARB);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglNamedStringARB(n, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), byteBuffer2.remaining(), MemoryUtil.getAddress(byteBuffer2), glNamedStringARB);
    }
    
    static native void nglNamedStringARB(final int p0, final int p1, final long p2, final int p3, final long p4, final long p5);
    
    public static void glNamedStringARB(final int n, final CharSequence charSequence, final CharSequence charSequence2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glNamedStringARB = capabilities.glNamedStringARB;
        BufferChecks.checkFunctionAddress(glNamedStringARB);
        nglNamedStringARB(n, charSequence.length(), APIUtil.getBuffer(capabilities, charSequence), charSequence2.length(), APIUtil.getBuffer(capabilities, charSequence2, charSequence.length()), glNamedStringARB);
    }
    
    public static void glDeleteNamedStringARB(final ByteBuffer byteBuffer) {
        final long glDeleteNamedStringARB = GLContext.getCapabilities().glDeleteNamedStringARB;
        BufferChecks.checkFunctionAddress(glDeleteNamedStringARB);
        BufferChecks.checkDirect(byteBuffer);
        nglDeleteNamedStringARB(byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glDeleteNamedStringARB);
    }
    
    static native void nglDeleteNamedStringARB(final int p0, final long p1, final long p2);
    
    public static void glDeleteNamedStringARB(final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteNamedStringARB = capabilities.glDeleteNamedStringARB;
        BufferChecks.checkFunctionAddress(glDeleteNamedStringARB);
        nglDeleteNamedStringARB(charSequence.length(), APIUtil.getBuffer(capabilities, charSequence), glDeleteNamedStringARB);
    }
    
    public static void glCompileShaderIncludeARB(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glCompileShaderIncludeARB = GLContext.getCapabilities().glCompileShaderIncludeARB;
        BufferChecks.checkFunctionAddress(glCompileShaderIncludeARB);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer, n2);
        nglCompileShaderIncludeARB(n, n2, MemoryUtil.getAddress(byteBuffer), 0L, glCompileShaderIncludeARB);
    }
    
    static native void nglCompileShaderIncludeARB(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static void glCompileShaderIncludeARB(final int n, final CharSequence[] array) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glCompileShaderIncludeARB = capabilities.glCompileShaderIncludeARB;
        BufferChecks.checkFunctionAddress(glCompileShaderIncludeARB);
        BufferChecks.checkArray(array);
        nglCompileShaderIncludeARB2(n, array.length, APIUtil.getBuffer(capabilities, array), APIUtil.getLengths(capabilities, array), glCompileShaderIncludeARB);
    }
    
    static native void nglCompileShaderIncludeARB2(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static boolean glIsNamedStringARB(final ByteBuffer byteBuffer) {
        final long glIsNamedStringARB = GLContext.getCapabilities().glIsNamedStringARB;
        BufferChecks.checkFunctionAddress(glIsNamedStringARB);
        BufferChecks.checkDirect(byteBuffer);
        return nglIsNamedStringARB(byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glIsNamedStringARB);
    }
    
    static native boolean nglIsNamedStringARB(final int p0, final long p1, final long p2);
    
    public static boolean glIsNamedStringARB(final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glIsNamedStringARB = capabilities.glIsNamedStringARB;
        BufferChecks.checkFunctionAddress(glIsNamedStringARB);
        return nglIsNamedStringARB(charSequence.length(), APIUtil.getBuffer(capabilities, charSequence), glIsNamedStringARB);
    }
    
    public static void glGetNamedStringARB(final ByteBuffer byteBuffer, final IntBuffer intBuffer, final ByteBuffer byteBuffer2) {
        final long glGetNamedStringARB = GLContext.getCapabilities().glGetNamedStringARB;
        BufferChecks.checkFunctionAddress(glGetNamedStringARB);
        BufferChecks.checkDirect(byteBuffer);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(byteBuffer2);
        nglGetNamedStringARB(byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), byteBuffer2.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(byteBuffer2), glGetNamedStringARB);
    }
    
    static native void nglGetNamedStringARB(final int p0, final long p1, final int p2, final long p3, final long p4, final long p5);
    
    public static void glGetNamedStringARB(final CharSequence charSequence, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetNamedStringARB = capabilities.glGetNamedStringARB;
        BufferChecks.checkFunctionAddress(glGetNamedStringARB);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(byteBuffer);
        nglGetNamedStringARB(charSequence.length(), APIUtil.getBuffer(capabilities, charSequence), byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetNamedStringARB);
    }
    
    public static String glGetNamedStringARB(final CharSequence charSequence, final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetNamedStringARB = capabilities.glGetNamedStringARB;
        BufferChecks.checkFunctionAddress(glGetNamedStringARB);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n + charSequence.length());
        nglGetNamedStringARB(charSequence.length(), APIUtil.getBuffer(capabilities, charSequence), n, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(bufferByte), glGetNamedStringARB);
        bufferByte.limit(charSequence.length() + lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static void glGetNamedStringARB(final ByteBuffer byteBuffer, final int n, final IntBuffer intBuffer) {
        final long glGetNamedStringivARB = GLContext.getCapabilities().glGetNamedStringivARB;
        BufferChecks.checkFunctionAddress(glGetNamedStringivARB);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetNamedStringivARB(byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), n, MemoryUtil.getAddress(intBuffer), glGetNamedStringivARB);
    }
    
    static native void nglGetNamedStringivARB(final int p0, final long p1, final int p2, final long p3, final long p4);
    
    public static void glGetNamedStringiARB(final CharSequence charSequence, final int n, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetNamedStringivARB = capabilities.glGetNamedStringivARB;
        BufferChecks.checkFunctionAddress(glGetNamedStringivARB);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetNamedStringivARB(charSequence.length(), APIUtil.getBuffer(capabilities, charSequence), n, MemoryUtil.getAddress(intBuffer), glGetNamedStringivARB);
    }
    
    public static int glGetNamedStringiARB(final CharSequence charSequence, final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetNamedStringivARB = capabilities.glGetNamedStringivARB;
        BufferChecks.checkFunctionAddress(glGetNamedStringivARB);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetNamedStringivARB(charSequence.length(), APIUtil.getBuffer(capabilities, charSequence), n, MemoryUtil.getAddress(bufferInt), glGetNamedStringivARB);
        return bufferInt.get(0);
    }
}
