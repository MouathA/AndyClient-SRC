package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class AMDDebugOutput
{
    public static final int GL_MAX_DEBUG_MESSAGE_LENGTH_AMD = 37187;
    public static final int GL_MAX_DEBUG_LOGGED_MESSAGES_AMD = 37188;
    public static final int GL_DEBUG_LOGGED_MESSAGES_AMD = 37189;
    public static final int GL_DEBUG_SEVERITY_HIGH_AMD = 37190;
    public static final int GL_DEBUG_SEVERITY_MEDIUM_AMD = 37191;
    public static final int GL_DEBUG_SEVERITY_LOW_AMD = 37192;
    public static final int GL_DEBUG_CATEGORY_API_ERROR_AMD = 37193;
    public static final int GL_DEBUG_CATEGORY_WINDOW_SYSTEM_AMD = 37194;
    public static final int GL_DEBUG_CATEGORY_DEPRECATION_AMD = 37195;
    public static final int GL_DEBUG_CATEGORY_UNDEFINED_BEHAVIOR_AMD = 37196;
    public static final int GL_DEBUG_CATEGORY_PERFORMANCE_AMD = 37197;
    public static final int GL_DEBUG_CATEGORY_SHADER_COMPILER_AMD = 37198;
    public static final int GL_DEBUG_CATEGORY_APPLICATION_AMD = 37199;
    public static final int GL_DEBUG_CATEGORY_OTHER_AMD = 37200;
    
    private AMDDebugOutput() {
    }
    
    public static void glDebugMessageEnableAMD(final int n, final int n2, final IntBuffer intBuffer, final boolean b) {
        final long glDebugMessageEnableAMD = GLContext.getCapabilities().glDebugMessageEnableAMD;
        BufferChecks.checkFunctionAddress(glDebugMessageEnableAMD);
        if (intBuffer != null) {
            BufferChecks.checkDirect(intBuffer);
        }
        nglDebugMessageEnableAMD(n, n2, (intBuffer == null) ? 0 : intBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), b, glDebugMessageEnableAMD);
    }
    
    static native void nglDebugMessageEnableAMD(final int p0, final int p1, final int p2, final long p3, final boolean p4, final long p5);
    
    public static void glDebugMessageInsertAMD(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final long glDebugMessageInsertAMD = GLContext.getCapabilities().glDebugMessageInsertAMD;
        BufferChecks.checkFunctionAddress(glDebugMessageInsertAMD);
        BufferChecks.checkDirect(byteBuffer);
        nglDebugMessageInsertAMD(n, n2, n3, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glDebugMessageInsertAMD);
    }
    
    static native void nglDebugMessageInsertAMD(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glDebugMessageInsertAMD(final int n, final int n2, final int n3, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDebugMessageInsertAMD = capabilities.glDebugMessageInsertAMD;
        BufferChecks.checkFunctionAddress(glDebugMessageInsertAMD);
        nglDebugMessageInsertAMD(n, n2, n3, charSequence.length(), APIUtil.getBuffer(capabilities, charSequence), glDebugMessageInsertAMD);
    }
    
    public static void glDebugMessageCallbackAMD(final AMDDebugOutputCallback amdDebugOutputCallback) {
        final long glDebugMessageCallbackAMD = GLContext.getCapabilities().glDebugMessageCallbackAMD;
        BufferChecks.checkFunctionAddress(glDebugMessageCallbackAMD);
        final long n = (amdDebugOutputCallback == null) ? 0L : CallbackUtil.createGlobalRef(amdDebugOutputCallback.getHandler());
        CallbackUtil.registerContextCallbackAMD(n);
        nglDebugMessageCallbackAMD((amdDebugOutputCallback == null) ? 0L : amdDebugOutputCallback.getPointer(), n, glDebugMessageCallbackAMD);
    }
    
    static native void nglDebugMessageCallbackAMD(final long p0, final long p1, final long p2);
    
    public static int glGetDebugMessageLogAMD(final int n, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final IntBuffer intBuffer4, final ByteBuffer byteBuffer) {
        final long glGetDebugMessageLogAMD = GLContext.getCapabilities().glGetDebugMessageLogAMD;
        BufferChecks.checkFunctionAddress(glGetDebugMessageLogAMD);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, n);
        }
        if (intBuffer2 != null) {
            BufferChecks.checkBuffer(intBuffer2, n);
        }
        if (intBuffer3 != null) {
            BufferChecks.checkBuffer(intBuffer3, n);
        }
        if (intBuffer4 != null) {
            BufferChecks.checkBuffer(intBuffer4, n);
        }
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        return nglGetDebugMessageLogAMD(n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddressSafe(intBuffer2), MemoryUtil.getAddressSafe(intBuffer3), MemoryUtil.getAddressSafe(intBuffer4), MemoryUtil.getAddressSafe(byteBuffer), glGetDebugMessageLogAMD);
    }
    
    static native int nglGetDebugMessageLogAMD(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5, final long p6, final long p7);
}
