package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class NVVideoCaptureUtil
{
    private NVVideoCaptureUtil() {
    }
    
    private static void checkExtension() {
        if (LWJGLUtil.CHECKS && !GLContext.getCapabilities().GL_NV_video_capture) {
            throw new IllegalStateException("NV_video_capture is not supported");
        }
    }
    
    private static ByteBuffer getPeerInfo() {
        return ContextGL.getCurrentContext().getPeerInfo().getHandle();
    }
    
    public static boolean glBindVideoCaptureDeviceNV(final int n, final long n2) {
        return nglBindVideoCaptureDeviceNV(getPeerInfo(), n, n2);
    }
    
    private static native boolean nglBindVideoCaptureDeviceNV(final ByteBuffer p0, final int p1, final long p2);
    
    public static int glEnumerateVideoCaptureDevicesNV(final LongBuffer longBuffer) {
        if (longBuffer != null) {
            BufferChecks.checkBuffer(longBuffer, 1);
        }
        return nglEnumerateVideoCaptureDevicesNV(getPeerInfo(), longBuffer, (longBuffer == null) ? 0 : longBuffer.position());
    }
    
    private static native int nglEnumerateVideoCaptureDevicesNV(final ByteBuffer p0, final LongBuffer p1, final int p2);
    
    public static boolean glLockVideoCaptureDeviceNV(final long n) {
        return nglLockVideoCaptureDeviceNV(getPeerInfo(), n);
    }
    
    private static native boolean nglLockVideoCaptureDeviceNV(final ByteBuffer p0, final long p1);
    
    public static boolean glQueryVideoCaptureDeviceNV(final long n, final int n2, final IntBuffer intBuffer) {
        BufferChecks.checkBuffer(intBuffer, 1);
        return nglQueryVideoCaptureDeviceNV(getPeerInfo(), n, n2, intBuffer, intBuffer.position());
    }
    
    private static native boolean nglQueryVideoCaptureDeviceNV(final ByteBuffer p0, final long p1, final int p2, final IntBuffer p3, final int p4);
    
    public static boolean glReleaseVideoCaptureDeviceNV(final long n) {
        return nglReleaseVideoCaptureDeviceNV(getPeerInfo(), n);
    }
    
    private static native boolean nglReleaseVideoCaptureDeviceNV(final ByteBuffer p0, final long p1);
}
