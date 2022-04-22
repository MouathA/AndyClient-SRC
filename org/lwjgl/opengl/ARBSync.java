package org.lwjgl.opengl;

import java.nio.*;

public final class ARBSync
{
    public static final int GL_MAX_SERVER_WAIT_TIMEOUT = 37137;
    public static final int GL_OBJECT_TYPE = 37138;
    public static final int GL_SYNC_CONDITION = 37139;
    public static final int GL_SYNC_STATUS = 37140;
    public static final int GL_SYNC_FLAGS = 37141;
    public static final int GL_SYNC_FENCE = 37142;
    public static final int GL_SYNC_GPU_COMMANDS_COMPLETE = 37143;
    public static final int GL_UNSIGNALED = 37144;
    public static final int GL_SIGNALED = 37145;
    public static final int GL_SYNC_FLUSH_COMMANDS_BIT = 1;
    public static final long GL_TIMEOUT_IGNORED = -1L;
    public static final int GL_ALREADY_SIGNALED = 37146;
    public static final int GL_TIMEOUT_EXPIRED = 37147;
    public static final int GL_CONDITION_SATISFIED = 37148;
    public static final int GL_WAIT_FAILED = 37149;
    
    private ARBSync() {
    }
    
    public static GLSync glFenceSync(final int n, final int n2) {
        return GL32.glFenceSync(n, n2);
    }
    
    public static boolean glIsSync(final GLSync glSync) {
        return GL32.glIsSync(glSync);
    }
    
    public static void glDeleteSync(final GLSync glSync) {
        GL32.glDeleteSync(glSync);
    }
    
    public static int glClientWaitSync(final GLSync glSync, final int n, final long n2) {
        return GL32.glClientWaitSync(glSync, n, n2);
    }
    
    public static void glWaitSync(final GLSync glSync, final int n, final long n2) {
        GL32.glWaitSync(glSync, n, n2);
    }
    
    public static void glGetInteger64(final int n, final LongBuffer longBuffer) {
        GL32.glGetInteger64(n, longBuffer);
    }
    
    public static long glGetInteger64(final int n) {
        return GL32.glGetInteger64(n);
    }
    
    public static void glGetSync(final GLSync glSync, final int n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        GL32.glGetSync(glSync, n, intBuffer, intBuffer2);
    }
    
    @Deprecated
    public static int glGetSync(final GLSync glSync, final int n) {
        return GL32.glGetSynci(glSync, n);
    }
    
    public static int glGetSynci(final GLSync glSync, final int n) {
        return GL32.glGetSynci(glSync, n);
    }
}
