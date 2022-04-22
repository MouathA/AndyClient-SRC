package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class NVPresentVideoUtil
{
    private NVPresentVideoUtil() {
    }
    
    private static void checkExtension() {
        if (LWJGLUtil.CHECKS && !GLContext.getCapabilities().GL_NV_present_video) {
            throw new IllegalStateException("NV_present_video is not supported");
        }
    }
    
    private static ByteBuffer getPeerInfo() {
        return ContextGL.getCurrentContext().getPeerInfo().getHandle();
    }
    
    public static int glEnumerateVideoDevicesNV(final LongBuffer longBuffer) {
        if (longBuffer != null) {
            BufferChecks.checkBuffer(longBuffer, 1);
        }
        return nglEnumerateVideoDevicesNV(getPeerInfo(), longBuffer, (longBuffer == null) ? 0 : longBuffer.position());
    }
    
    private static native int nglEnumerateVideoDevicesNV(final ByteBuffer p0, final LongBuffer p1, final int p2);
    
    public static boolean glBindVideoDeviceNV(final int n, final long n2, final IntBuffer intBuffer) {
        if (intBuffer != null) {
            BufferChecks.checkNullTerminated(intBuffer);
        }
        return nglBindVideoDeviceNV(getPeerInfo(), n, n2, intBuffer, (intBuffer == null) ? 0 : intBuffer.position());
    }
    
    private static native boolean nglBindVideoDeviceNV(final ByteBuffer p0, final int p1, final long p2, final IntBuffer p3, final int p4);
    
    public static boolean glQueryContextNV(final int n, final IntBuffer intBuffer) {
        BufferChecks.checkBuffer(intBuffer, 1);
        final ContextGL currentContext = ContextGL.getCurrentContext();
        return nglQueryContextNV(currentContext.getPeerInfo().getHandle(), currentContext.getHandle(), n, intBuffer, intBuffer.position());
    }
    
    private static native boolean nglQueryContextNV(final ByteBuffer p0, final ByteBuffer p1, final int p2, final IntBuffer p3, final int p4);
}
