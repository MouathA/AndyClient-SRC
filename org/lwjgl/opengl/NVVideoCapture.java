package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class NVVideoCapture
{
    public static final int GL_VIDEO_BUFFER_NV = 36896;
    public static final int GL_VIDEO_BUFFER_BINDING_NV = 36897;
    public static final int GL_FIELD_UPPER_NV = 36898;
    public static final int GL_FIELD_LOWER_NV = 36899;
    public static final int GL_NUM_VIDEO_CAPTURE_STREAMS_NV = 36900;
    public static final int GL_NEXT_VIDEO_CAPTURE_BUFFER_STATUS_NV = 36901;
    public static final int GL_LAST_VIDEO_CAPTURE_STATUS_NV = 36903;
    public static final int GL_VIDEO_BUFFER_PITCH_NV = 36904;
    public static final int GL_VIDEO_CAPTURE_FRAME_WIDTH_NV = 36920;
    public static final int GL_VIDEO_CAPTURE_FRAME_HEIGHT_NV = 36921;
    public static final int GL_VIDEO_CAPTURE_FIELD_UPPER_HEIGHT_NV = 36922;
    public static final int GL_VIDEO_CAPTURE_FIELD_LOWER_HEIGHT_NV = 36923;
    public static final int GL_VIDEO_CAPTURE_TO_422_SUPPORTED_NV = 36902;
    public static final int GL_VIDEO_COLOR_CONVERSION_MATRIX_NV = 36905;
    public static final int GL_VIDEO_COLOR_CONVERSION_MAX_NV = 36906;
    public static final int GL_VIDEO_COLOR_CONVERSION_MIN_NV = 36907;
    public static final int GL_VIDEO_COLOR_CONVERSION_OFFSET_NV = 36908;
    public static final int GL_VIDEO_BUFFER_INTERNAL_FORMAT_NV = 36909;
    public static final int GL_VIDEO_CAPTURE_SURFACE_ORIGIN_NV = 36924;
    public static final int GL_PARTIAL_SUCCESS_NV = 36910;
    public static final int GL_SUCCESS_NV = 36911;
    public static final int GL_FAILURE_NV = 36912;
    public static final int GL_YCBYCR8_422_NV = 36913;
    public static final int GL_YCBAYCR8A_4224_NV = 36914;
    public static final int GL_Z6Y10Z6CB10Z6Y10Z6CR10_422_NV = 36915;
    public static final int GL_Z6Y10Z6CB10Z6A10Z6Y10Z6CR10Z6A10_4224_NV = 36916;
    public static final int GL_Z4Y12Z4CB12Z4Y12Z4CR12_422_NV = 36917;
    public static final int GL_Z4Y12Z4CB12Z4A12Z4Y12Z4CR12Z4A12_4224_NV = 36918;
    public static final int GL_Z4Y12Z4CB12Z4CR12_444_NV = 36919;
    public static final int GL_NUM_VIDEO_CAPTURE_SLOTS_NV = 8399;
    public static final int GL_UNIQUE_ID_NV = 8398;
    
    private NVVideoCapture() {
    }
    
    public static void glBeginVideoCaptureNV(final int n) {
        final long glBeginVideoCaptureNV = GLContext.getCapabilities().glBeginVideoCaptureNV;
        BufferChecks.checkFunctionAddress(glBeginVideoCaptureNV);
        nglBeginVideoCaptureNV(n, glBeginVideoCaptureNV);
    }
    
    static native void nglBeginVideoCaptureNV(final int p0, final long p1);
    
    public static void glBindVideoCaptureStreamBufferNV(final int n, final int n2, final int n3, final long n4) {
        final long glBindVideoCaptureStreamBufferNV = GLContext.getCapabilities().glBindVideoCaptureStreamBufferNV;
        BufferChecks.checkFunctionAddress(glBindVideoCaptureStreamBufferNV);
        nglBindVideoCaptureStreamBufferNV(n, n2, n3, n4, glBindVideoCaptureStreamBufferNV);
    }
    
    static native void nglBindVideoCaptureStreamBufferNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glBindVideoCaptureStreamTextureNV(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glBindVideoCaptureStreamTextureNV = GLContext.getCapabilities().glBindVideoCaptureStreamTextureNV;
        BufferChecks.checkFunctionAddress(glBindVideoCaptureStreamTextureNV);
        nglBindVideoCaptureStreamTextureNV(n, n2, n3, n4, n5, glBindVideoCaptureStreamTextureNV);
    }
    
    static native void nglBindVideoCaptureStreamTextureNV(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glEndVideoCaptureNV(final int n) {
        final long glEndVideoCaptureNV = GLContext.getCapabilities().glEndVideoCaptureNV;
        BufferChecks.checkFunctionAddress(glEndVideoCaptureNV);
        nglEndVideoCaptureNV(n, glEndVideoCaptureNV);
    }
    
    static native void nglEndVideoCaptureNV(final int p0, final long p1);
    
    public static void glGetVideoCaptureNV(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetVideoCaptureivNV = GLContext.getCapabilities().glGetVideoCaptureivNV;
        BufferChecks.checkFunctionAddress(glGetVideoCaptureivNV);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetVideoCaptureivNV(n, n2, MemoryUtil.getAddress(intBuffer), glGetVideoCaptureivNV);
    }
    
    static native void nglGetVideoCaptureivNV(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetVideoCaptureiNV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetVideoCaptureivNV = capabilities.glGetVideoCaptureivNV;
        BufferChecks.checkFunctionAddress(glGetVideoCaptureivNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetVideoCaptureivNV(n, n2, MemoryUtil.getAddress(bufferInt), glGetVideoCaptureivNV);
        return bufferInt.get(0);
    }
    
    public static void glGetVideoCaptureStreamNV(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetVideoCaptureStreamivNV = GLContext.getCapabilities().glGetVideoCaptureStreamivNV;
        BufferChecks.checkFunctionAddress(glGetVideoCaptureStreamivNV);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetVideoCaptureStreamivNV(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetVideoCaptureStreamivNV);
    }
    
    static native void nglGetVideoCaptureStreamivNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetVideoCaptureStreamiNV(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetVideoCaptureStreamivNV = capabilities.glGetVideoCaptureStreamivNV;
        BufferChecks.checkFunctionAddress(glGetVideoCaptureStreamivNV);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetVideoCaptureStreamivNV(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetVideoCaptureStreamivNV);
        return bufferInt.get(0);
    }
    
    public static void glGetVideoCaptureStreamNV(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glGetVideoCaptureStreamfvNV = GLContext.getCapabilities().glGetVideoCaptureStreamfvNV;
        BufferChecks.checkFunctionAddress(glGetVideoCaptureStreamfvNV);
        BufferChecks.checkBuffer(floatBuffer, 1);
        nglGetVideoCaptureStreamfvNV(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glGetVideoCaptureStreamfvNV);
    }
    
    static native void nglGetVideoCaptureStreamfvNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static float glGetVideoCaptureStreamfNV(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetVideoCaptureStreamfvNV = capabilities.glGetVideoCaptureStreamfvNV;
        BufferChecks.checkFunctionAddress(glGetVideoCaptureStreamfvNV);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetVideoCaptureStreamfvNV(n, n2, n3, MemoryUtil.getAddress(bufferFloat), glGetVideoCaptureStreamfvNV);
        return bufferFloat.get(0);
    }
    
    public static void glGetVideoCaptureStreamNV(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final long glGetVideoCaptureStreamdvNV = GLContext.getCapabilities().glGetVideoCaptureStreamdvNV;
        BufferChecks.checkFunctionAddress(glGetVideoCaptureStreamdvNV);
        BufferChecks.checkBuffer(doubleBuffer, 1);
        nglGetVideoCaptureStreamdvNV(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), glGetVideoCaptureStreamdvNV);
    }
    
    static native void nglGetVideoCaptureStreamdvNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static double glGetVideoCaptureStreamdNV(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetVideoCaptureStreamdvNV = capabilities.glGetVideoCaptureStreamdvNV;
        BufferChecks.checkFunctionAddress(glGetVideoCaptureStreamdvNV);
        final DoubleBuffer bufferDouble = APIUtil.getBufferDouble(capabilities);
        nglGetVideoCaptureStreamdvNV(n, n2, n3, MemoryUtil.getAddress(bufferDouble), glGetVideoCaptureStreamdvNV);
        return bufferDouble.get(0);
    }
    
    public static int glVideoCaptureNV(final int n, final IntBuffer intBuffer, final LongBuffer longBuffer) {
        final long glVideoCaptureNV = GLContext.getCapabilities().glVideoCaptureNV;
        BufferChecks.checkFunctionAddress(glVideoCaptureNV);
        BufferChecks.checkBuffer(intBuffer, 1);
        BufferChecks.checkBuffer(longBuffer, 1);
        return nglVideoCaptureNV(n, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(longBuffer), glVideoCaptureNV);
    }
    
    static native int nglVideoCaptureNV(final int p0, final long p1, final long p2, final long p3);
    
    public static void glVideoCaptureStreamParameterNV(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glVideoCaptureStreamParameterivNV = GLContext.getCapabilities().glVideoCaptureStreamParameterivNV;
        BufferChecks.checkFunctionAddress(glVideoCaptureStreamParameterivNV);
        BufferChecks.checkBuffer(intBuffer, 16);
        nglVideoCaptureStreamParameterivNV(n, n2, n3, MemoryUtil.getAddress(intBuffer), glVideoCaptureStreamParameterivNV);
    }
    
    static native void nglVideoCaptureStreamParameterivNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glVideoCaptureStreamParameterNV(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glVideoCaptureStreamParameterfvNV = GLContext.getCapabilities().glVideoCaptureStreamParameterfvNV;
        BufferChecks.checkFunctionAddress(glVideoCaptureStreamParameterfvNV);
        BufferChecks.checkBuffer(floatBuffer, 16);
        nglVideoCaptureStreamParameterfvNV(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glVideoCaptureStreamParameterfvNV);
    }
    
    static native void nglVideoCaptureStreamParameterfvNV(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glVideoCaptureStreamParameterNV(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final long glVideoCaptureStreamParameterdvNV = GLContext.getCapabilities().glVideoCaptureStreamParameterdvNV;
        BufferChecks.checkFunctionAddress(glVideoCaptureStreamParameterdvNV);
        BufferChecks.checkBuffer(doubleBuffer, 16);
        nglVideoCaptureStreamParameterdvNV(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), glVideoCaptureStreamParameterdvNV);
    }
    
    static native void nglVideoCaptureStreamParameterdvNV(final int p0, final int p1, final int p2, final long p3, final long p4);
}
