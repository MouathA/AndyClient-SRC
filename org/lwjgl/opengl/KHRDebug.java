package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class KHRDebug
{
    public static final int GL_DEBUG_OUTPUT = 37600;
    public static final int GL_DEBUG_OUTPUT_SYNCHRONOUS = 33346;
    public static final int GL_CONTEXT_FLAG_DEBUG_BIT = 2;
    public static final int GL_MAX_DEBUG_MESSAGE_LENGTH = 37187;
    public static final int GL_MAX_DEBUG_LOGGED_MESSAGES = 37188;
    public static final int GL_DEBUG_LOGGED_MESSAGES = 37189;
    public static final int GL_DEBUG_NEXT_LOGGED_MESSAGE_LENGTH = 33347;
    public static final int GL_MAX_DEBUG_GROUP_STACK_DEPTH = 33388;
    public static final int GL_DEBUG_GROUP_STACK_DEPTH = 33389;
    public static final int GL_MAX_LABEL_LENGTH = 33512;
    public static final int GL_DEBUG_CALLBACK_FUNCTION = 33348;
    public static final int GL_DEBUG_CALLBACK_USER_PARAM = 33349;
    public static final int GL_DEBUG_SOURCE_API = 33350;
    public static final int GL_DEBUG_SOURCE_WINDOW_SYSTEM = 33351;
    public static final int GL_DEBUG_SOURCE_SHADER_COMPILER = 33352;
    public static final int GL_DEBUG_SOURCE_THIRD_PARTY = 33353;
    public static final int GL_DEBUG_SOURCE_APPLICATION = 33354;
    public static final int GL_DEBUG_SOURCE_OTHER = 33355;
    public static final int GL_DEBUG_TYPE_ERROR = 33356;
    public static final int GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR = 33357;
    public static final int GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR = 33358;
    public static final int GL_DEBUG_TYPE_PORTABILITY = 33359;
    public static final int GL_DEBUG_TYPE_PERFORMANCE = 33360;
    public static final int GL_DEBUG_TYPE_OTHER = 33361;
    public static final int GL_DEBUG_TYPE_MARKER = 33384;
    public static final int GL_DEBUG_TYPE_PUSH_GROUP = 33385;
    public static final int GL_DEBUG_TYPE_POP_GROUP = 33386;
    public static final int GL_DEBUG_SEVERITY_HIGH = 37190;
    public static final int GL_DEBUG_SEVERITY_MEDIUM = 37191;
    public static final int GL_DEBUG_SEVERITY_LOW = 37192;
    public static final int GL_DEBUG_SEVERITY_NOTIFICATION = 33387;
    public static final int GL_BUFFER = 33504;
    public static final int GL_SHADER = 33505;
    public static final int GL_PROGRAM = 33506;
    public static final int GL_QUERY = 33507;
    public static final int GL_PROGRAM_PIPELINE = 33508;
    public static final int GL_SAMPLER = 33510;
    public static final int GL_DISPLAY_LIST = 33511;
    
    private KHRDebug() {
    }
    
    public static void glDebugMessageControl(final int n, final int n2, final int n3, final IntBuffer intBuffer, final boolean b) {
        GL43.glDebugMessageControl(n, n2, n3, intBuffer, b);
    }
    
    public static void glDebugMessageInsert(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        GL43.glDebugMessageInsert(n, n2, n3, n4, byteBuffer);
    }
    
    public static void glDebugMessageInsert(final int n, final int n2, final int n3, final int n4, final CharSequence charSequence) {
        GL43.glDebugMessageInsert(n, n2, n3, n4, charSequence);
    }
    
    public static void glDebugMessageCallback(final KHRDebugCallback khrDebugCallback) {
        GL43.glDebugMessageCallback(khrDebugCallback);
    }
    
    public static int glGetDebugMessageLog(final int n, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final IntBuffer intBuffer4, final IntBuffer intBuffer5, final ByteBuffer byteBuffer) {
        return GL43.glGetDebugMessageLog(n, intBuffer, intBuffer2, intBuffer3, intBuffer4, intBuffer5, byteBuffer);
    }
    
    public static void glPushDebugGroup(final int n, final int n2, final ByteBuffer byteBuffer) {
        GL43.glPushDebugGroup(n, n2, byteBuffer);
    }
    
    public static void glPushDebugGroup(final int n, final int n2, final CharSequence charSequence) {
        GL43.glPushDebugGroup(n, n2, charSequence);
    }
    
    public static void glPopDebugGroup() {
    }
    
    public static void glObjectLabel(final int n, final int n2, final ByteBuffer byteBuffer) {
        GL43.glObjectLabel(n, n2, byteBuffer);
    }
    
    public static void glObjectLabel(final int n, final int n2, final CharSequence charSequence) {
        GL43.glObjectLabel(n, n2, charSequence);
    }
    
    public static void glGetObjectLabel(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        GL43.glGetObjectLabel(n, n2, intBuffer, byteBuffer);
    }
    
    public static String glGetObjectLabel(final int n, final int n2, final int n3) {
        return GL43.glGetObjectLabel(n, n2, n3);
    }
    
    public static void glObjectPtrLabel(final PointerWrapper pointerWrapper, final ByteBuffer byteBuffer) {
        GL43.glObjectPtrLabel(pointerWrapper, byteBuffer);
    }
    
    public static void glObjectPtrLabel(final PointerWrapper pointerWrapper, final CharSequence charSequence) {
        GL43.glObjectPtrLabel(pointerWrapper, charSequence);
    }
    
    public static void glGetObjectPtrLabel(final PointerWrapper pointerWrapper, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        GL43.glGetObjectPtrLabel(pointerWrapper, intBuffer, byteBuffer);
    }
    
    public static String glGetObjectPtrLabel(final PointerWrapper pointerWrapper, final int n) {
        return GL43.glGetObjectPtrLabel(pointerWrapper, n);
    }
}
