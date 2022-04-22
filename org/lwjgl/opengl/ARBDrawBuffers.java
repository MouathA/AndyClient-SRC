package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class ARBDrawBuffers
{
    public static final int GL_MAX_DRAW_BUFFERS_ARB = 34852;
    public static final int GL_DRAW_BUFFER0_ARB = 34853;
    public static final int GL_DRAW_BUFFER1_ARB = 34854;
    public static final int GL_DRAW_BUFFER2_ARB = 34855;
    public static final int GL_DRAW_BUFFER3_ARB = 34856;
    public static final int GL_DRAW_BUFFER4_ARB = 34857;
    public static final int GL_DRAW_BUFFER5_ARB = 34858;
    public static final int GL_DRAW_BUFFER6_ARB = 34859;
    public static final int GL_DRAW_BUFFER7_ARB = 34860;
    public static final int GL_DRAW_BUFFER8_ARB = 34861;
    public static final int GL_DRAW_BUFFER9_ARB = 34862;
    public static final int GL_DRAW_BUFFER10_ARB = 34863;
    public static final int GL_DRAW_BUFFER11_ARB = 34864;
    public static final int GL_DRAW_BUFFER12_ARB = 34865;
    public static final int GL_DRAW_BUFFER13_ARB = 34866;
    public static final int GL_DRAW_BUFFER14_ARB = 34867;
    public static final int GL_DRAW_BUFFER15_ARB = 34868;
    
    private ARBDrawBuffers() {
    }
    
    public static void glDrawBuffersARB(final IntBuffer intBuffer) {
        final long glDrawBuffersARB = GLContext.getCapabilities().glDrawBuffersARB;
        BufferChecks.checkFunctionAddress(glDrawBuffersARB);
        BufferChecks.checkDirect(intBuffer);
        nglDrawBuffersARB(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDrawBuffersARB);
    }
    
    static native void nglDrawBuffersARB(final int p0, final long p1, final long p2);
    
    public static void glDrawBuffersARB(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawBuffersARB = capabilities.glDrawBuffersARB;
        BufferChecks.checkFunctionAddress(glDrawBuffersARB);
        nglDrawBuffersARB(1, APIUtil.getInt(capabilities, n), glDrawBuffersARB);
    }
}
