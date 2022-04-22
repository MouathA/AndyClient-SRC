package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class ARBDebugOutput
{
    public static final int GL_DEBUG_OUTPUT_SYNCHRONOUS_ARB = 33346;
    public static final int GL_MAX_DEBUG_MESSAGE_LENGTH_ARB = 37187;
    public static final int GL_MAX_DEBUG_LOGGED_MESSAGES_ARB = 37188;
    public static final int GL_DEBUG_LOGGED_MESSAGES_ARB = 37189;
    public static final int GL_DEBUG_NEXT_LOGGED_MESSAGE_LENGTH_ARB = 33347;
    public static final int GL_DEBUG_CALLBACK_FUNCTION_ARB = 33348;
    public static final int GL_DEBUG_CALLBACK_USER_PARAM_ARB = 33349;
    public static final int GL_DEBUG_SOURCE_API_ARB = 33350;
    public static final int GL_DEBUG_SOURCE_WINDOW_SYSTEM_ARB = 33351;
    public static final int GL_DEBUG_SOURCE_SHADER_COMPILER_ARB = 33352;
    public static final int GL_DEBUG_SOURCE_THIRD_PARTY_ARB = 33353;
    public static final int GL_DEBUG_SOURCE_APPLICATION_ARB = 33354;
    public static final int GL_DEBUG_SOURCE_OTHER_ARB = 33355;
    public static final int GL_DEBUG_TYPE_ERROR_ARB = 33356;
    public static final int GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR_ARB = 33357;
    public static final int GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR_ARB = 33358;
    public static final int GL_DEBUG_TYPE_PORTABILITY_ARB = 33359;
    public static final int GL_DEBUG_TYPE_PERFORMANCE_ARB = 33360;
    public static final int GL_DEBUG_TYPE_OTHER_ARB = 33361;
    public static final int GL_DEBUG_SEVERITY_HIGH_ARB = 37190;
    public static final int GL_DEBUG_SEVERITY_MEDIUM_ARB = 37191;
    public static final int GL_DEBUG_SEVERITY_LOW_ARB = 37192;
    
    private ARBDebugOutput() {
    }
    
    public static void glDebugMessageControlARB(final int n, final int n2, final int n3, final IntBuffer intBuffer, final boolean b) {
        final long glDebugMessageControlARB = GLContext.getCapabilities().glDebugMessageControlARB;
        BufferChecks.checkFunctionAddress(glDebugMessageControlARB);
        if (intBuffer != null) {
            BufferChecks.checkDirect(intBuffer);
        }
        nglDebugMessageControlARB(n, n2, n3, (intBuffer == null) ? 0 : intBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), b, glDebugMessageControlARB);
    }
    
    static native void nglDebugMessageControlARB(final int p0, final int p1, final int p2, final int p3, final long p4, final boolean p5, final long p6);
    
    public static void glDebugMessageInsertARB(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        final long glDebugMessageInsertARB = GLContext.getCapabilities().glDebugMessageInsertARB;
        BufferChecks.checkFunctionAddress(glDebugMessageInsertARB);
        BufferChecks.checkDirect(byteBuffer);
        nglDebugMessageInsertARB(n, n2, n3, n4, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glDebugMessageInsertARB);
    }
    
    static native void nglDebugMessageInsertARB(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glDebugMessageInsertARB(final int n, final int n2, final int n3, final int n4, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDebugMessageInsertARB = capabilities.glDebugMessageInsertARB;
        BufferChecks.checkFunctionAddress(glDebugMessageInsertARB);
        nglDebugMessageInsertARB(n, n2, n3, n4, charSequence.length(), APIUtil.getBuffer(capabilities, charSequence), glDebugMessageInsertARB);
    }
    
    public static void glDebugMessageCallbackARB(final ARBDebugOutputCallback arbDebugOutputCallback) {
        final long glDebugMessageCallbackARB = GLContext.getCapabilities().glDebugMessageCallbackARB;
        BufferChecks.checkFunctionAddress(glDebugMessageCallbackARB);
        final long n = (arbDebugOutputCallback == null) ? 0L : CallbackUtil.createGlobalRef(arbDebugOutputCallback.getHandler());
        CallbackUtil.registerContextCallbackARB(n);
        nglDebugMessageCallbackARB((arbDebugOutputCallback == null) ? 0L : arbDebugOutputCallback.getPointer(), n, glDebugMessageCallbackARB);
    }
    
    static native void nglDebugMessageCallbackARB(final long p0, final long p1, final long p2);
    
    public static int glGetDebugMessageLogARB(final int n, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final IntBuffer intBuffer4, final IntBuffer intBuffer5, final ByteBuffer byteBuffer) {
        final long glGetDebugMessageLogARB = GLContext.getCapabilities().glGetDebugMessageLogARB;
        BufferChecks.checkFunctionAddress(glGetDebugMessageLogARB);
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
        if (intBuffer5 != null) {
            BufferChecks.checkBuffer(intBuffer5, n);
        }
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        return nglGetDebugMessageLogARB(n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddressSafe(intBuffer2), MemoryUtil.getAddressSafe(intBuffer3), MemoryUtil.getAddressSafe(intBuffer4), MemoryUtil.getAddressSafe(intBuffer5), MemoryUtil.getAddressSafe(byteBuffer), glGetDebugMessageLogARB);
    }
    
    static native int nglGetDebugMessageLogARB(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5, final long p6, final long p7, final long p8);
}
