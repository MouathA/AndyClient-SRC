package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class NVPresentVideo
{
    public static final int GL_FRAME_NV = 36390;
    public static final int FIELDS_NV = 36391;
    public static final int GL_CURRENT_TIME_NV = 36392;
    public static final int GL_NUM_FILL_STREAMS_NV = 36393;
    public static final int GL_PRESENT_TIME_NV = 36394;
    public static final int GL_PRESENT_DURATION_NV = 36395;
    public static final int GL_NUM_VIDEO_SLOTS_NV = 8432;
    
    private NVPresentVideo() {
    }
    
    public static void glPresentFrameKeyedNV(final int n, final long n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11) {
        final long glPresentFrameKeyedNV = GLContext.getCapabilities().glPresentFrameKeyedNV;
        BufferChecks.checkFunctionAddress(glPresentFrameKeyedNV);
        nglPresentFrameKeyedNV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, glPresentFrameKeyedNV);
    }
    
    static native void nglPresentFrameKeyedNV(final int p0, final long p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final long p11);
    
    public static void glPresentFrameDualFillNV(final int n, final long n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final int n12, final int n13) {
        final long glPresentFrameDualFillNV = GLContext.getCapabilities().glPresentFrameDualFillNV;
        BufferChecks.checkFunctionAddress(glPresentFrameDualFillNV);
        nglPresentFrameDualFillNV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, glPresentFrameDualFillNV);
    }
    
    static native void nglPresentFrameDualFillNV(final int p0, final long p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final int p11, final int p12, final long p13);
    
    public static void glGetVideoNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetVideoivNV = GLContext.getCapabilities().glGetVideoivNV;
        BufferChecks.checkFunctionAddress(glGetVideoivNV);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetVideoivNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetVideoivNV);
    }
    
    static native void nglGetVideoivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetVideoiNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetVideoivNV = capabilities.glGetVideoivNV;
        BufferChecks.checkFunctionAddress(glGetVideoivNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetVideoivNV(n, n2, MemoryUtil.getAddress(bufferInt), glGetVideoivNV);
        return bufferInt.get(0);
    }
    
    public static void glGetVideouNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetVideouivNV = GLContext.getCapabilities().glGetVideouivNV;
        BufferChecks.checkFunctionAddress(glGetVideouivNV);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetVideouivNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetVideouivNV);
    }
    
    static native void nglGetVideouivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetVideouiNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetVideouivNV = capabilities.glGetVideouivNV;
        BufferChecks.checkFunctionAddress(glGetVideouivNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetVideouivNV(n, n2, MemoryUtil.getAddress(bufferInt), glGetVideouivNV);
        return bufferInt.get(0);
    }
    
    public static void glGetVideoNV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glGetVideoi64vNV = GLContext.getCapabilities().glGetVideoi64vNV;
        BufferChecks.checkFunctionAddress(glGetVideoi64vNV);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglGetVideoi64vNV(n, n2, MemoryUtil.getAddress(longBuffer), glGetVideoi64vNV);
    }
    
    static native void nglGetVideoi64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static long glGetVideoi64NV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetVideoi64vNV = capabilities.glGetVideoi64vNV;
        BufferChecks.checkFunctionAddress(glGetVideoi64vNV);
        final LongBuffer bufferLong = APIUtil.getBufferLong(capabilities);
        nglGetVideoi64vNV(n, n2, MemoryUtil.getAddress(bufferLong), glGetVideoi64vNV);
        return bufferLong.get(0);
    }
    
    public static void glGetVideouNV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glGetVideoui64vNV = GLContext.getCapabilities().glGetVideoui64vNV;
        BufferChecks.checkFunctionAddress(glGetVideoui64vNV);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglGetVideoui64vNV(n, n2, MemoryUtil.getAddress(longBuffer), glGetVideoui64vNV);
    }
    
    static native void nglGetVideoui64vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static long glGetVideoui64NV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetVideoui64vNV = capabilities.glGetVideoui64vNV;
        BufferChecks.checkFunctionAddress(glGetVideoui64vNV);
        final LongBuffer bufferLong = APIUtil.getBufferLong(capabilities);
        nglGetVideoui64vNV(n, n2, MemoryUtil.getAddress(bufferLong), glGetVideoui64vNV);
        return bufferLong.get(0);
    }
}
